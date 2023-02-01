package io.padamski.upskill.authcodeflow.dto;

import lombok.Builder;

@Builder
public class AuthorizationCodeRequest {
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirect;
}
