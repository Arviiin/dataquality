package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
@Mapper
public interface RolesMapper {

    int addRoles(@Param("roles") String[] roles, @Param("uid") Integer uid);

    List<Role> getRolesByUid(Long uid);
}
