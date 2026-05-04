/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.entity.BlogsSummary;
import top.yalexin.rblog.mapper.BlogMapper;
import top.yalexin.rblog.service.ArchiveService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class ArchiveServiceImpl implements ArchiveService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Map getBlogListByYear(Long year) {

        assert year != null && 1000 <= year && year <= 9999;

        // 已经按照创建时间顺序排列
        List<Blog> blogByYear = blogMapper.findBlogByYear(year);

        LinkedList<HashMap> maps = new LinkedList<>();
        // 按照月份编排，每个月份一个列表

        // 刚刚处理的月份
        String lastHandleMonth = "";
        for (Blog blog : blogByYear) {
            Date createTime = blog.getCreateTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            String datef = df.format(createTime);
            String monthStr = datef.split("-")[1];
            if (!lastHandleMonth.equals(monthStr)) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("yearMonth", Long.parseLong(String.valueOf(year) + monthStr));
                LinkedList<Blog> blogByMonth = new LinkedList<>();
                blogByMonth.add(blog);
                hashMap.put("content", blogByMonth);
                maps.addLast(hashMap);

                lastHandleMonth = monthStr;
            } else {
                LinkedList<Blog> content = (LinkedList<Blog>) maps.getLast().get("content");
                content.add(blog);
            }
        }
        HashMap<String, Object> ans = new HashMap<>();
        ans.put("blogs", maps);
        return ans;
    }

    @Override
    public List<Long> getAllyears() {
        return blogMapper.getAllYears();
    }

    @Override
    public BlogsSummary getBlogsSummary() {
        List<Blog> allBlog = blogMapper.findAllBlog();
        BlogsSummary blogsSummary = new BlogsSummary();
        // 文章总数
        blogsSummary.setTotalBlogs((long) allBlog.size());
        // 文章总字数
        blogsSummary.setTotalBlogWords(allBlog.stream().mapToLong(this::getWordsCount).sum());
        // 总阅览数
        blogsSummary.setTotalRead(allBlog.stream().mapToLong(Blog::getViews).sum());
        long nowTime = new Date().getTime();
        // 最早发文时间
        blogsSummary.setEarliest(allBlog.stream().mapToLong(blog -> blog.getCreateTime().getTime()).min().orElse(nowTime));

        // 将2022-01-01转换为时间戳
        long latestDateTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            latestDateTime = sdf.parse("2021-01-01").getTime();
        } catch (ParseException e) {
            // 硬编码日期解析失败时抛出运行时异常（理论上不会发生）
            throw new RuntimeException("Failed to parse default latest date", e);
        }
        // 最晚发文时间
        blogsSummary.setLatest(allBlog.stream().mapToLong(blog -> blog.getCreateTime().getTime()).max().orElse(latestDateTime));
        // 单篇文章最高阅览数
        blogsSummary.setMaxRead(allBlog.stream().mapToLong(Blog::getViews).max().orElse(0));
        return blogsSummary;
    }


    // 统计markdown形式的文章中有效字符
    public long getWordsCount(Blog blog) {
        String content = blog.getContent();
        if (content == null || content.isEmpty()) {
            return 0;
        }
        // 移除图片标记
        String processed = content.replaceAll("!\\[([^]]*)\\]\\([^)]+\\)", "");
        // 移除链接标记，保留链接文本
        processed = processed.replaceAll("\\[([^]]+)\\]\\([^)]+\\)", "$1");
        // 移除粗体/斜体标记，保留文本内容
        processed = processed.replaceAll("(\\*\\*|__)(.*?)\\1|(\\*|_)(.*?)\\3", "$2$4");
        // 移除代码块（包括多行代码块和行内代码）
        // processed = processed.replaceAll("(?s)`{3}.*?`{3}|`.*?`", "");
        // 移除标题标记（#）
        processed = processed.replaceAll("(?m)^#{1,6}\\s+", "");
        // 移除引用标记（>）
        processed = processed.replaceAll("(?m)^>\\s+", "");
        // 移除列表标记（*或-）
        processed = processed.replaceAll("(?m)^\\s*[*-]\\s+", "");
        // 移除水平分割线（---）
        processed = processed.replaceAll("(?m)^---+", "");
        // 移除HTML标签
        processed = processed.replaceAll("<[^>]+>", "");
        // 标准化空白字符（多个空格合并为一个，去除首尾空格）
        processed = processed.replaceAll("\\s+", " ").trim();
        // 统计单词数（如果处理后为空则返回0）
        return processed.isEmpty() ? 0 : processed.split("\\s+").length;
    }

}
