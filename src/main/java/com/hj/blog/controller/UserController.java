package com.hj.blog.controller;

import com.hj.blog.constant.Constants;
import com.hj.blog.model.Result;
import com.hj.blog.model.UserInfo;
import com.hj.blog.service.UserService;
import com.hj.blog.untils.JwtUtils;
import com.hj.blog.untils.JwtUtils;
import com.hj.blog.untils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result login(String username, String password){
        log.info("UserController#login接收参数: username:{}, password:{}", username, password);
        //1. 参数校验合法性
        //2. 校验密码是否正确
        //3. 密码正确, 返回token
        //4. 密码错误, 返回错误信息
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return Result.fail("账号或密码不能为空");
        }
        //从数据库中查找用户
        UserInfo userInfo = userService.selectByName(username);
        //用户不存在
        if (userInfo==null){
            log.error("用户不存在");
            return Result.fail("用户不存在");
        }
        //密码错误
        if (!SecurityUtils.verify(password,userInfo.getPassword())){
            log.error("密码错误");
            return Result.fail("密码错误");
        }
        //密码正确, 返回token
        Map<String,Object> claim = new HashMap<>();
        claim.put(Constants.TOKEN_ID, userInfo.getId());
        claim.put(Constants.TOKEN_USERNAME, userInfo.getUserName());
        String token = JwtUtils.genJwtToken(claim);
        log.info("UserController#login 返回结果token:{}",token);
        return Result.success(token);
    }

    /**
     * 获取当前登录用户的信息
     * @return
     */
    @RequestMapping("/getUserInfo")
    public UserInfo getLoginUserInfo(HttpServletRequest request){
        log.info("获取用户信息....");
        //获取token
        String token = request.getHeader(Constants.REQUEST_HEADER_TOKEN);
        //从token中获取登录用户ID
        Integer userId = JwtUtils.getIdByToken(token);
        if (userId==null){
            //用户未登录
            return null;
        }
        UserInfo userInfo = userService.selectById(userId);
        return userInfo;
    }

    /**
     * 根据日记ID, 获取作者信息
     * @param blogId
     * @return
     */
    @RequestMapping("/getAuthorInfo")
    public UserInfo getAuthorInfo(Integer blogId){

        if (blogId<=0){
            return null;
        }
        UserInfo userInfo = userService.getAuthorInfo(blogId);
        if (userInfo==null){
            return null;
        }
        return userInfo;
    }
}

