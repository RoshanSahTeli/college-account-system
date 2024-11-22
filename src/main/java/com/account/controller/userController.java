package com.account.controller;


import com.account.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class userController {

    @Autowired
    private com.account.service.adminService aservice;

    @GetMapping("/home")
    public String userHome(Model model, HttpSession session, Principal principal){
        model.addAttribute("user",aservice.findByEmail(principal.getName()));
        session.setAttribute("user",aservice.findByEmail(principal.getName()));
        return "userHome";
    }
    @GetMapping("/statement")
    public String userStatement(Model model,HttpSession session){
        User u= (User) session.getAttribute("user");
        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("statement",aservice.findPaymentByUid(u.getUid()));
        return "userStatement";
    }
    @GetMapping("/message")
    public String message(Model model, HttpSession session, HttpServletResponse response){
        User u= (User) session.getAttribute("user");
        model.addAttribute("message",u.getMessage());
        model.addAttribute("user",u);
        return "userMessage";
    }

}
