package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolesMapper {

    int addRoles(@Param("roles") String[] roles, @Param("uid") Integer uid);

    List<Role> getRolesByUid(Long uid);
}
