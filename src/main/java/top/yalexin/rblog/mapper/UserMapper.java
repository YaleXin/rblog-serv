/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package top.yalexin.rblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.yalexin.rblog.entity.User;

@Mapper
public interface UserMapper {
    @Select("select * from t_user where username=#{username}")
    User findUser(String username);

    @Update("update t_user set password=#{newPsw} where username=#{username} and password=#{oldPsw}")
    Long updateUser(String newPsw, String username, String oldPsw);
}
