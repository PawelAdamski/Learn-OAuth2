package io.padamski.upskill.authcodeflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.padamski.upskill.authcodeflow.config.OAuth2Configuration;
import io.padamski.upskill.authcodeflow.dto.TokenResponse;
import io.padamski.upskill.authcodeflow.dto.OAuth2UserDetails;
import io.padamski.upskill.authcodeflow.state.LoggedUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
@AllArgsConstructor
public class OAuth2Service {

    private final ObjectMapper objectMapper;
    private final LoggedUserService loggedUserService;
    private final OAuth2Configuration oAuth2Configuration;

    public String buildLoginUrl() {
        return String.format("https://%s/authorize?" +
                        "response_type=code&" +
                        "client_id=%s&" +
                        "scope=openid profile&" +
                        "redirect_uri=%s",
                oAuth2Configuration.getDomain(),
                oAuth2Configuration.getClientId(),
                oAuth2Configuration.getRedirectUri());
    }

    public String exchangeAuthorizationCodes(String code) throws IOException, InterruptedException {
        TokenResponse tokenResponse = this.callTokenEndpoint(code);
        OAuth2UserDetails oAuth2UserDetails = this.parseIdToken(tokenResponse.getIdToken());
        return loggedUserService.logUserIn(oAuth2UserDetails);
    }

    private TokenResponse callTokenEndpoint(String code) throws IOException, InterruptedException {
        String reqBody = buildTokenRequestBody(code);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://%s/oauth/token", oAuth2Configuration.getDomain())))
                .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), TokenResponse.class);
    }

    private String buildTokenRequestBody(String code) {
        return String.format(
                "grant_type=authorization_code&" +
                        "client_id=%s&" +
                        "client_secret=%s&" +
                        "code=%s&" +
                        "redirect_uri=%s",
                oAuth2Configuration.getClientId(), oAuth2Configuration.getClientSecret(), code, oAuth2Configuration.getRedirectUri()
        );
    }

    private OAuth2UserDetails parseIdToken(String idToken) throws JsonProcessingException {
        String encodedJwtPayload = idToken.split("\\.")[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String decodedJwtPayload = new String(decoder.decode(encodedJwtPayload));
        return objectMapper.readValue(decodedJwtPayload, OAuth2UserDetails.class);
    }
}
