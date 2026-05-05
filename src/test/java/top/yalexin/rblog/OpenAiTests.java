package top.yalexin.rblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yalexin.rblog.service.QwenDocumentService;
import top.yalexin.rblog.service.WordCloudService;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest
public class OpenAiTests {
    @Autowired
    private QwenDocumentService qwenDocumentService;

    @Autowired
    private WordCloudService wordCloudService;

    @Test
    public void testUploadMultipleDocuments() throws URISyntaxException {
        // 获取测试资源目录下的文件路径（假设 a.md 和 b.md 已放在 src/test/resources 目录）
        Path aPath = Path.of(getClass().getResource("/a.md").toURI());
        Path bPath = Path.of(getClass().getResource("/b.md").toURI());

        List<Path> files = List.of(aPath, bPath);
        List<String> fileIds = qwenDocumentService.uploadMultipleDocuments(files);
        System.out.println("上传的fileId列表: " + fileIds);
    }


    @Test
    public void testAnalyzeDocumentsWithNonStream() throws URISyntaxException {
        // 获取测试资源目录下的文件路径（假设 a.md 和 b.md 已放在 src/test/resources 目录）
        Path aPath = Path.of(getClass().getResource("/a.md").toURI());
        Path bPath = Path.of(getClass().getResource("/b.md").toURI());

        List<Path> files = List.of(aPath, bPath);
        List<String> fileIds = qwenDocumentService.uploadMultipleDocuments(files);
        System.out.println("上传的fileId列表: " + fileIds);


        String result = qwenDocumentService.analyzeDocumentsWithoutStream(fileIds);
        System.out.println("分析结果: " + result);
    }

    @Test
    public void testGenerateWordCloud() {
        String wordCloud = wordCloudService.generateWordCloud();
        System.out.println("生成的词云: " + wordCloud);
    }

    @Test
    public void testGetBlogsWordCloud() {
        Long aLong = wordCloudService.saveWordCloud();
        System.out.println("保存的词云id: " + aLong);
    }
}
