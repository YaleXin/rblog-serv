/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.service;

import top.yalexin.rblog.entity.Category;
import top.yalexin.rblog.entity.Tag;

import java.util.List;

public interface TagService {
   List<Tag> getTagList();
   Tag addTag(Tag tag);
}
