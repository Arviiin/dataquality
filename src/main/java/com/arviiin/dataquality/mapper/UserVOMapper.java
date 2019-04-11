package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.Role;
import com.arviiin.dataquality.model.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserVOMapper {

    UserVO loadUserByUsername(@Param("username") String username);

    long reg(UserVO user);

    int updateUserEmail(@Param("email") String email, @Param("id") Long id);

    List<UserVO> getUserByNickname(@Param("nickname") String nickname);

    List<Role> getAllRole();

    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    int deleteUserById(Long uid);

    int deleteUserRolesByUid(Long id);

    int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

    UserVO getUserById(@Param("id") Long id);
}
