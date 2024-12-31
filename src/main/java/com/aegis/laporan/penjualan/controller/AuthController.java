package com.aegis.laporan.penjualan.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    @PostMapping
    public Object login(){
        return new Object();
    }

    @PostMapping
    public Object register(){
        return new Object();
    }

    @PostMapping
    public Object changePassword(){
        return new Object();
    }

}
