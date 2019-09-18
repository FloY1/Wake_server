package net.proselyte.jwtappdemo.dto;

import lombok.Data;


@Data
public class AuthenticationRequestDto {
    private String phone;
    private String userName;
    private String password;
}
