package com.example.springmyitems.controller;

import com.example.springmyitems.entity.User;
import com.example.springmyitems.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {

    @ModelAttribute
    public CurrentUser currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        return currentUser;
    }


}
