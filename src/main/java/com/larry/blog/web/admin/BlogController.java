package com.larry.blog.web.admin;


import com.larry.blog.po.Blog;
import com.larry.blog.po.User;
import com.larry.blog.service.BlogService;
import com.larry.blog.service.TagService;
import com.larry.blog.service.TypeService;
import com.larry.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST ="redirect:/admin/blogs";
    private static final String MAINPAGE ="index";


    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;


    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public  String blogs(@PageableDefault(size=3, sort={"updateTime"}) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        model.addAttribute("types",typeService.listType());
        return LIST;
    }


    @PostMapping("/blogs/search")
    public  String search(@PageableDefault(size=3, sort={"updateTime"}) Pageable pageable,
                          BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));

        return "admin/blogs :: blogList";
    }

    @PostMapping("/blogs/search1")
    public  String search1(@PageableDefault(size=3, sort={"updateTime"}) Pageable pageable,
                          BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));

        return "search1 :: blogList1";
    }




    @GetMapping("/blogs/input")
    public String input(Model model){
      setTypeAndTag(model);
      Blog blog=new Blog();
      model.addAttribute("blog",new Blog());

        return INPUT;
    }


    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog =blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);

        return INPUT;
    }






    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        System.out.println("asdasda190238791827389y98y89h7"+tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes,HttpSession session){
        blogService.deleteBlog(id);



//        if(blogService.getBlog(id).getUser() == (User) session.getAttribute("user")){
//            blogService.deleteBlog(id);

        attributes.addFlashAttribute("message","delete successful");
        return "redirect:/admin/blogs";
    }

}
