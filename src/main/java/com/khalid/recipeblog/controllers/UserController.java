package com.khalid.recipeblog.controllers;

import com.khalid.recipeblog.entities.Blog;
import com.khalid.recipeblog.entities.User;
import com.khalid.recipeblog.repositories.BlogRepository;
import com.khalid.recipeblog.repositories.UserRepository;
import com.khalid.recipeblog.services.BlogServiceImpl;
import com.khalid.recipeblog.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogServiceImpl blogServiceImpl;

	@Autowired
	private BlogRepository blogRepository;

	//to get the login screen
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	//it checks user authentication and returns the homepage according to user role
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		String email = principal.getName();
		UserDetails details = userServiceImpl.loadUserByUsername(email);
		if (details != null && details.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
			List<Blog> blogs = this.blogServiceImpl.getAllBlogs();
			model.addAttribute("blogs", blogs);
			return "redirect:/admin";

		}
		return "redirect:/home";
	}

	//shows home page for admin user
	@GetMapping("/admin")
	public String adminHomePage(Model model, Principal principal) {
		String email =principal.getName();
		List<Blog> blogs = this.blogServiceImpl.getAllBlogs();
		List<Blog> blogs2 = this.blogServiceImpl.getAllBlogsByUser(email);
		model.addAttribute("blogs", blogs);
		model.addAttribute("blogs2", blogs2);
		return "admin";
	}
	//shows home page for normal user
	@GetMapping("/home")
	public String normalHomePage(Model model) {
		List<Blog> blogs = this.blogServiceImpl.getAllBlogs();
		model.addAttribute("blogs", blogs);
		return "home";
	}

	//form for adding blog
	@GetMapping("/addBlogForm")
	public String addBlogForm() {
		return "addBlog";
	}

	//form for updating blog
	@GetMapping("/editBlogForm/{blogId}")
	public String editBlogForm(Model model, @PathVariable Integer blogId) {
		model.addAttribute("blogId", blogId);
		return "editBlog";
	}

	//to create a blog object
	@ModelAttribute("blog")
	public Blog blog() {
		return new Blog();
	}

	//it adds a blog into database and redirects us to admin homepage
	@PostMapping("/addBlog")
	public String addBlog(@ModelAttribute("blog") Blog blog, Principal principal) {
		String email = principal.getName();
		this.blogServiceImpl.create(blog, email);
		return "redirect:/admin";
	}

	//it updates a blog  and redirects us to admin homepage
	@PostMapping("/editBlog/{blogId}")
	public String editBlog(@ModelAttribute("blog") Blog blog, @PathVariable Integer blogId) {
		this.blogServiceImpl.update(blog, blogId);
		return "redirect:/admin";
	}

	//to see a specific blog in detail
	@GetMapping("/home/recipe/{blogId}")
	public String recipe(Model model, @PathVariable Integer blogId) {
		Blog blog = this.blogServiceImpl.getBlog(blogId);
		model.addAttribute("blog", blog);
		return "recipe";
	}

	//for deleting a blog using blogId
	@GetMapping("/deleteBlog/{blogId}")
	public String deleteBlog(@PathVariable Integer blogId) {
		this.blogServiceImpl.delete(blogId);
		return "redirect:/admin";
	}
}
