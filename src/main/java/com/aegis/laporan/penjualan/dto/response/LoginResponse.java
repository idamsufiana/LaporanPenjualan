package com.aegis.laporan.penjualan.dto.response;

public class LoginResponse {

    private String Authtoken;
    private String RefreshToken;

    public LoginResponse(String Authtoken, String RefreshToken) {
        this.Authtoken = Authtoken;
        this.RefreshToken = RefreshToken;
    }
}
