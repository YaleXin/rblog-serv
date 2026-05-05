package top.yalexin.rblog.service.impl;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FileObject;
import com.openai.models.files.FilePurpose;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yalexin.rblog.constant.AIConstant;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.service.BlogService;
import top.yalexin.rblog.service.QwenDocumentService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QwenDocumentServiceImpl implements QwenDocumentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OpenAIClient openAIClient;

    @Value("${qwen.api.model}")
    private String model;





    /**
     * 批量上传多个文档，返回fileId列表
     */
    @Override
    public List<String> uploadMultipleDocuments(List<Path> files) {
        List<String> fileIds = new ArrayList<>();
        for (Path file : files) {
            try {
                String fileId = uploadDocument(file);
                fileIds.add(fileId);
            } catch (IOException e) {
                logger.error("上传文件失败: {}", file.getFileName().toString(), e);
                throw new RuntimeException("上传文件失败: " + file.getFileName().toString(), e);
            }
        }
        return fileIds;
    }

    /**
     * 提供多个fileId，然后分析文档（非流式输出）
     *
     * @param fileIds      文档fileId列表
     * @return 分析结果 （非流式输出）
     */
    @Override
    public String analyzeDocumentsWithoutStream(List<String> fileIds) {

        String systemPrompt = "你将获得一系列文件，" +
                "每个文件中包含至少一篇文章（如果存在多篇，则以" + AIConstant.NEW_FILE_SEPARATOR + "分割），请帮我汇总总结一下，这些文章总共讲了什么内容（不需要分别返回，汇总所有文章即可），" +
                "然后输出一些词语和对应的权重，我用于制作词云{keyword, weight}，返回格式为JSON格式，" +
                "例如 `[{\"word\": \"keyword1\",\"weight\": 20},{\"word\": \"keyword2\",\"weight\": 30}]`" +
                "其中每个权重为1-100，这些关键字需要基于你的理解，而不是简单地统计频次";


        // 将fileIds转为 "fileid://{FILE_ID1},fileid://{FILE_ID2}"字符串
        List<String> fileIdTmp = fileIds.stream()  // 使用Stream API简化列表构建
                .map(fileId -> "fileid://" + fileId)
                .collect(Collectors.toList());
        String fileIdMessage = String.join(",", fileIdTmp);
        // 暂时固定用户问题
       String userPrompt = "这几篇文章讲了什么？请以JSON格式返回[{\"word\": \"word1\", \"weight\": 50}]";
        // TODO 编码限定返回的格式为 json 格式  并获取消耗的 token
        // 创建聊天请求（非流式）
        ChatCompletionCreateParams chatParams = ChatCompletionCreateParams.builder()
                .addSystemMessage(systemPrompt)
                .addSystemMessage(fileIdMessage)
                .addUserMessage(userPrompt)
                .model(model)
                .build();

        try {
            // 非流式调用，直接获取完整响应
            ChatCompletion completion = openAIClient.chat().completions().create(chatParams);

            // 提取响应内容
            String responseContent = completion.choices().stream()
                    .findFirst()
                    .flatMap(choice -> choice.message().content())
                    .orElse("");

            logger.debug("文档分析完成");

            return responseContent;

        } catch (Exception e) {
            logger.error("文档分析失败: {}", e.getMessage(), e);
            throw new RuntimeException("文档分析失败: " + e.getMessage(), e);
        } finally {
            deleteFiles(fileIds);
        }
    }






    String uploadDocument(Path file) throws IOException {
        try {

            // 创建文件上传参数
            FileCreateParams fileParams = FileCreateParams.builder()
                    .file(file)
                    .purpose(FilePurpose.of("file-extract"))
                    .build();

            // 上传文件
            FileObject fileObject = openAIClient.files().create(fileParams);
            String fileId = fileObject.id();
            logger.debug("文件上传成功，fileId: {}, 文件名: {}", fileId, file.getFileName().toString());

            return fileId;
        } catch (Exception e) {
            logger.error("文件上传失败: {}", e.getMessage(), e);
            throw new IOException("文件上传失败: " + e.getMessage(), e);
        }
    }

    void deleteFiles(List<String> fileIds) {
        for (String fileId : fileIds) {
            try {
                openAIClient.files().delete(fileId);
                logger.debug("文件删除成功: {}", fileId);
            } catch (Exception e) {
                logger.error("文件删除失败: {}", fileId, e);
                // 根据业务需求决定是否抛出异常
            }
        }
    }
}
