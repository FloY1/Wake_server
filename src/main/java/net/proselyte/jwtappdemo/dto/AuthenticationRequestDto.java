package net.proselyte.jwtappdemo.dto;

import lombok.Data;

/**
 * DTO class for authentication (login) request.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Data
public class AuthenticationRequestDto {
    private String phone;
    private String userName;
    private String password;
}
