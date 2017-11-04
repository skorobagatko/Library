package com.skorobahatko.library.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class LoginController {

    public String login() {
        return "main";
    }

    public String register() {
        return "main";
    }
}
