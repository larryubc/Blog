package com.larry.blog.web.admin;


import com.larry.blog.po.User;
import com.larry.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;



    @GetMapping
    public String loginPage(){
            return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user =userService.checkUser(username,password);



        if(user != null){
            if(user.getId()==2){
                attributes.addFlashAttribute("message","不允许傻子登陆，sorry");

                return "redirect:/admin";
            }else {


                user.setPassword(null);
                session.setAttribute("user", user);
                System.out.println(user.getUsername());
                return "admin/index";
            }
        }else{
            attributes.addFlashAttribute("message","wrong username and password");

            return "redirect:/admin";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String login(

                        HttpSession session,
                        RedirectAttributes attributes){

//        session.setAttribute("user", user);
//                System.out.println(user.getUsername());
//                return "admin/index";
//
//            attributes.addFlashAttribute("message","wrong username and password");

            return "admin/index";

    }



}
