package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.model.request.LoginRequest;
import com.aegis.laporan.penjualan.model.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    @PostMapping
    public Object login(@RequestBody LoginRequest loginRequest){
        return new Object();
    }

    @PostMapping
    public Object register(@RequestBody @Valid RegisterRequest registerRequest){
        return new Object();
    }

    @PostMapping
    public Object changePassword(@RequestBody String username){
        return new Object();
    }

}
