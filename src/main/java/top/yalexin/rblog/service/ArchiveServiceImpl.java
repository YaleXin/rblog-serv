/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Blog;
import top.yalexin.rblog.mapper.BlogMapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Map getBlogListWithYearMonth() {
        HashMap<String, Object> map = new HashMap<>();
        List<Long> yearMonths = blogMapper.countBlogYearMonth();
        LinkedList<HashMap> maps = new LinkedList<>();
        for (long yearMonth : yearMonths) {
            List<Blog> blogYearMonth = blogMapper.findBlogYearMonth(yearMonth);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("content", blogYearMonth);
            hashMap.put("yearMonth", yearMonth);
            maps.addLast(hashMap);
        }
        map.put("blogs", maps);
        return map;
    }

}
