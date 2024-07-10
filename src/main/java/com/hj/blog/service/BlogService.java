package com.hj.blog.service;

import com.hj.blog.mapper.BlogMapper;
import com.hj.blog.model.BlogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;

    public List<BlogInfo> getList() {
        return blogMapper.selectAll();
    }

    public BlogInfo getBlogDetail(Integer blogId) {
        return blogMapper.selectById(blogId);
    }

    public boolean insertBook(BlogInfo blogInfo) {
        try {
            Integer result = blogMapper.insertBlog(blogInfo);
            if (result==1){
                return true;
            }
        }catch (Exception e){
            log.error("添加失败，e:{}",e.getMessage());
        }
        return false;
    }
    public Integer update(BlogInfo blogInfo) {
        return blogMapper.updateBlog(blogInfo);
    }
}
