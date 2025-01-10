package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.config.security.JwtUtils;
import com.aegis.laporan.penjualan.dto.request.LoginRequest;
import com.aegis.laporan.penjualan.dto.request.RegisterRequest;
import com.aegis.laporan.penjualan.dto.response.LoginResponse;
import com.aegis.laporan.penjualan.model.User;
import com.aegis.laporan.penjualan.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Auth")
public class AuthController extends BaseController{
    @Value("${jwtCookieName}")
    private String jwtCookie;

    @Autowired
    AuthService authService;
    @Autowired
    JwtUtils jwtUtils;


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
            ResponseCookie cookie = ResponseCookie.from(jwtCookie, loginResponse.getAuthtoken()).path("/v1").maxAge(24 * 60 * 60).httpOnly(true).build();
            return this.okLogin(cookie);
        } catch (Exception e) {
            return this.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Success Sign Out");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activate/{userId}")
    public ResponseEntity<User> activateUser(@PathVariable String userName) {
        User user = authService.activateUser(userName);
        return ResponseEntity.ok(user);
    }


}
