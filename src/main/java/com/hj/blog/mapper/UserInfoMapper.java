package com.hj.blog.mapper;

import com.hj.blog.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    /**
     * 根据用户名, 查询用户信息
     * @return
     */
    @Select("select * from user where delete_flag =0 and user_name = #{userName}")
    UserInfo selectByName(String userName);

    /**
     * 根据用户ID, 查询用户信息
     * @return
     */
    @Select("select * from user where delete_flag =0 and id = #{id}")
    UserInfo selectById(Integer id);
}
