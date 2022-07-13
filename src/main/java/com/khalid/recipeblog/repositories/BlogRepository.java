package com.khalid.recipeblog.repositories;

import com.khalid.recipeblog.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    List<Blog> findByUserId(Long id);

    //to get a blog using blogId
    @Query(value = "SELECT b FROM Blog b WHERE b.blogId = :blogId")
    Blog  getBlogById(@Param("blogId") Integer blogId);

    //to delete a blog by its blogId
    @Modifying
    @Query(value = "DELETE FROM Blog b WHERE b.blogId = :blogId")
    void deleteBlog(@Param("blogId") Integer blogId);
}
