package apiEngine.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    public String token;
    public String expires;
    public String status;
    public String result;
}
