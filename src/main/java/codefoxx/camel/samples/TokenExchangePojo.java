package codefoxx.camel.samples;

import lombok.Data;

@Data
public class TokenExchangePojo {
    private String client_id;
    private String client_secret;
    private String grant_type;
    private String subject_token;
    private String subject_issuer;
    private String subject_token_type;
    private String audience;
}
