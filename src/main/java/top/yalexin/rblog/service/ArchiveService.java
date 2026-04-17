/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.BlogsSummary;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ArchiveService {
    Map getBlogListByYear(Long year);
    List<Long> getAllyears();

    BlogsSummary getBlogsSummary();

}
