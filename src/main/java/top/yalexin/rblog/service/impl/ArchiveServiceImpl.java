/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.mapper.BlogMapper;
import top.yalexin.rblog.service.ArchiveService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Map getBlogListByYear(Long year) {

        assert year != null && 1000 <= year && year <= 9999 ;

        // 已经按照创建时间顺序排列
        List<Blog> blogByYear = blogMapper.findBlogByYear(year);

        LinkedList<HashMap> maps = new LinkedList<>();
        // 按照月份编排，每个月份一个列表

        // 刚刚处理的月份
        String lastHandleMonth = "";
        for (Blog blog : blogByYear){
            Date createTime = blog.getCreateTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            String datef = df.format(createTime);
            String monthStr = datef.split("-")[1];
            if (!lastHandleMonth.equals(monthStr)){
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("yearMonth", Long.parseLong(String.valueOf(year) + monthStr));
                LinkedList<Blog> blogByMonth = new LinkedList<>();
                blogByMonth.add(blog);
                hashMap.put("content", blogByMonth);
                maps.addLast(hashMap);

                lastHandleMonth = monthStr;
            }else{
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

}
