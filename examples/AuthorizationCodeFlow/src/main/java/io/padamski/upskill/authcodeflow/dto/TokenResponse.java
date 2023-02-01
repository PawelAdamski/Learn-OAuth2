package io.padamski.upskill.authcodeflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
    @JsonProperty("access_token") private String accessToken;
    @JsonProperty("id_token") private String idToken;
    @JsonProperty("scope") private String scope;
    @JsonProperty("expires_in") private String expiresIn;
    @JsonProperty("token_type") private String tokenType;
}