package com.example.springmyitems.controller;

import com.example.springmyitems.entity.User;
import com.example.springmyitems.service.MailService;
import com.example.springmyitems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
private final MailService mailService;
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/addUser")
    public String addUserPage() {
        return "saveUser";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user, ModelMap map) {
        List<String> errorMsg = new ArrayList<>();

        if (user.getName() == null || user.getName().equals("")) {
            errorMsg.add("name is required");
        }
        if (user.getSurname() == null || user.getSurname().equals("")) {
            errorMsg.add("surname is required");
        }

        if (user.getEmail() == null || user.getEmail().equals("")) {
            errorMsg.add("email is required");
        }
        if (!errorMsg.isEmpty()) {
            map.addAttribute("errors", errorMsg);
            return "saveUser";
        }
        userService.save(user);
mailService.sendMail(user.getEmail(),"Welcome,"+user.getSurname(),"You have successfully register, "+user.getName());

        return "redirect:/";
    }

    @GetMapping("/editUser/{id}")
    public String editUserPage(ModelMap map, @PathVariable("id") int id) {
        map.addAttribute("user", userService.findById(id));
        return "saveUser";

    }


}

