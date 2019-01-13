package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

// @Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中   @MapperScan("com.sstl.sharebike.mapper")
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(Integer id);

    /*@Select("SELECT * FROM user")
    public List<User> getUserList();*/

    public List<User> getUserList();//xml版本

    @Insert("insert into user(username, age, ctm) values(#{username}, #{age}, now())")
    public int add(User user);

    @Update("UPDATE user SET username = #{user.username} , age = #{user.age} WHERE id = #{id}")
    public int update(@Param("id") Integer id, @Param("user") User user);

    @Delete("DELETE from user where id = #{id} ")
    public int delete(Integer id);




    User loadUserByUsername(@Param("username") String username);

    long reg(User user);
}
