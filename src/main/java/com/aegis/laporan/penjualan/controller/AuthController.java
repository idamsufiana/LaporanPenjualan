package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.dto.request.LoginRequest;
import com.aegis.laporan.penjualan.dto.request.RegisterRequest;
import com.aegis.laporan.penjualan.dto.response.LoginResponse;
import com.aegis.laporan.penjualan.model.User;
import com.aegis.laporan.penjualan.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/Auth")
public class AuthController extends BaseController{

    @Autowired
    AuthService authService;

    @PostMapping({"/register"})
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest registerRequest) {
        try {
            User registerResponse = authService.register(registerRequest);
            return this.success(registerResponse);
        } catch (Exception var3) {
            Exception exception = var3;
            exception.printStackTrace();
            return this.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PostMapping({"login"})
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);

            return this.success(loginResponse);
        } catch (Exception e) {
            return this.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
