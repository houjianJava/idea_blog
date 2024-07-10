package com.hj.blog.service;

import com.hj.blog.mapper.BlogMapper;
import com.hj.blog.mapper.UserInfoMapper;
import com.hj.blog.model.BlogInfo;
import com.hj.blog.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    private BlogMapper blogMapper;
    public UserInfo selectByName(String username) {
        return userInfoMapper.selectByName(username);
    }
    public UserInfo selectById(Integer id) {
        return userInfoMapper.selectById(id);
    }

    public UserInfo getAuthorInfo(Integer blogId) {
        //1.根据日记ID，获取作者
        //2.根据作者ID，获取作者信息
        BlogInfo blogInfo = blogMapper.selectById(blogId);
        if (blogInfo==null && blogInfo.getUserId()<=0){
            log.error("不存在或者作者信息不合法,blogId:{}",blogId);
            return null;
        }
        UserInfo userInfo = userInfoMapper.selectById(blogInfo.getUserId());
        return userInfo;
    }
}