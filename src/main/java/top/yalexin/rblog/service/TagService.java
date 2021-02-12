/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.entity.Tag;
import top.yalexin.rblog.util.PageResult;

import java.util.List;

public interface TagService {
    List<Tag> getTagList();

    Tag addTag(Tag tag);

    PageResult getTagByPage(long pageNum, long pageSize);

    Tag updateTag(Tag tag);

    Long deleteTagById(Long id);
}
