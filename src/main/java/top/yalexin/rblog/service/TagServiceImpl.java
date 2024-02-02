/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yalexin.rblog.entity.Tag;
import top.yalexin.rblog.mapper.TagMapper;
import top.yalexin.rblog.util.PageResult;
import top.yalexin.rblog.util.PageUtils;

import java.util.List;

@Transactional
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagListWithBlogCnt() {
        return tagMapper.findAllTagsWithBlogCnt();
    }

    @Override
    public Tag addTag(Tag tag) {
        if (tag == null) return null;
        Long newId = tagMapper.insertTag(tag);
        if (newId == null) return null;
        else tag.setId(newId);
        return tag;
    }

    @Override
    public PageResult getTagByPage(long requestPageNum, long requestPageSize) {
        Long totalSize = tagMapper.countTag();
        // 前端传来的 size 若为非正数，则默认 size=1
        long pageSize = Math.max(requestPageSize, 1);
        // 前端传来的页面编号若非数， 则默认 请求第一页
        long pageNum = requestPageNum > 0 ? (requestPageNum - 1) : 0;

        long totalPage = totalSize / pageSize;

        List<Tag> tags = tagMapper.findTagByInterval(pageNum * pageSize, pageSize);

        PageResult pageResult = PageUtils.setResult(tags, pageNum + 1, pageSize, totalPage, totalSize);
        return pageResult;
    }

    @Override
    public Tag updateTag(Tag tag) {
        if (tag == null || tag.getId() == null || tag.getId() < 0 ||
                tag.getName() == null || "".equals(tag.getName())) {
            return null;
        }
        Long aLong = tagMapper.updateTag(tag);
        if (aLong < 0) return null;
        else return tag;
    }

    @Override
    public Long deleteTagById(Long id) {
        if (id == null || id < 0)return null;
        else return tagMapper.deleteTag(id);
    }
}
