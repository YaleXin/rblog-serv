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

import java.util.List;
@Transactional
@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> getTagList() {
        return tagMapper.findAllTags();
    }

    @Override
    public Tag addTag(Tag tag) {
        if (tag == null) return null;
        Long newId = tagMapper.insertTag(tag);
        if(newId == null)return null;
        else tag.setId(newId);
        return tag;
    }
}
