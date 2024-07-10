package com.hj.blog.controller;

import com.hj.blog.constant.Constants;
import com.hj.blog.model.BlogInfo;
import com.hj.blog.model.Result;
import com.hj.blog.service.BlogService;
import com.hj.blog.untils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/blog")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/getList")
    public List<BlogInfo>getList(){
        log.info("获取日记列表");
        return blogService.getList();
    }

    @RequestMapping("/getBlogDetail")
    public BlogInfo getBlogDetail(Integer blogId,HttpServletRequest request){
        log.info("getBlogDetail,blogId:{}",blogId);
        BlogInfo blogDetail = blogService.getBlogDetail(blogId);
        String token = request.getHeader(Constants.REQUEST_HEADER_TOKEN);
        Integer userId = JwtUtils.getIdByToken(token);
        if(userId!=null && userId==blogDetail.getUserId()){
            blogDetail.setLoginUser(true);
        }else {
            blogDetail.setLoginUser(false);
        }
        return blogDetail;
    }

    @RequestMapping("/add")
    public Boolean addBook(@RequestBody BlogInfo blogInfo, HttpServletRequest request){
        log.info("添加,blogInfo:{}",blogInfo);
        if (StringUtils.hasLength(blogInfo.getTitle()) || StringUtils.hasLength(blogInfo.getContent())){
            return false;
        }
        String token = request.getHeader(Constants.REQUEST_HEADER_TOKEN);
        Integer userId = JwtUtils.getIdByToken(token);
        if (userId!=null && userId<=0){
            return false;
        }
        blogInfo.setUserId(userId);
        return blogService.insertBook(blogInfo);
    }
    @RequestMapping("/update")
    public Boolean update(BlogInfo blogInfo){
        log.info("更新日记,blogInfo:{}",blogInfo);
        if (blogInfo.getId()==null
                || !StringUtils.hasLength(blogInfo.getTitle())
                || !StringUtils.hasLength(blogInfo.getContent())){
            return false;
        }
        blogService.update(blogInfo);
        return true;
    }
    @RequestMapping("delete")
    public Boolean delete(Integer blogId){
        log.info("删除日记,blogId:{}",blogId);
        if (blogId<=0){
            return false;
        }
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setId(blogId);
        blogInfo.setDeleteFlag(1);
        blogService.update(blogInfo);
        return true;
    }
}
