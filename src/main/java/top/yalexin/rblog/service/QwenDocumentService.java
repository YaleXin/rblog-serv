package top.yalexin.rblog.service;


import java.nio.file.Path;
import java.util.List;

public interface QwenDocumentService {
    /**
     * 批量上传多个文档，返回fileId列表
     */
    List<String> uploadMultipleDocuments(List<Path> files);




    /**
     * 提供多个fileId，然后分析文档（非流式输出）
     * @param fileIds 文档fileId列表
     * @return 分析结果 （非流式输出）
     */
    String analyzeDocumentsWithoutStream(List<String> fileIds);


}
