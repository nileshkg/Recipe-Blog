package com.khalid.recipeblog.services;

import com.khalid.recipeblog.entities.Blog;
import com.khalid.recipeblog.entities.User;
import com.khalid.recipeblog.repositories.BlogRepository;
import com.khalid.recipeblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    //to create a new blog in database
    @Override
    public void create(Blog blog, String email) {
      User user =  this.userRepository.findByEmail(email);
      blog.setUser(user);
      this.blogRepository.save(blog);
    }

    //to get a blog using blogId
    @Override
    public Blog getBlog(Integer blogId) {
        return this.blogRepository.getBlogById(blogId);
    }

    //to get all the blogs present in the database
    @Override
    public List<Blog> getAllBlogs() {
        return this.blogRepository.findAll();
    }

    //to get all the blogs of a particular user
    @Override
    public List<Blog> getAllBlogsByUser(String email) {
        User user = this.userRepository.findByEmail(email);
        return this.blogRepository.findByUserId(user.getId());
    }

    //to update the blog  by its admin
    @Override
    public void update(Blog blog, Integer blogId) {
     Blog blog2 = this.blogRepository.getById(blogId);
     User user = blog2.getUser();
     blog.setBlogId(blog2.getBlogId());
     blog.setUser(user);
     this.blogRepository.save(blog);
    }

    //to delete a blog using blogId
    @Override
    public void delete(Integer blogId) {
        Blog blog = this.blogRepository.findById(blogId).get();
        this.blogRepository.deleteBlog(blogId);
    }
}
