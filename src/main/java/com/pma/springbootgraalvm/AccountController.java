package com.pma.springbootgraalvm;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("title", "Sign in");
        model.addAttribute("loginError", error != null);
        return "login";
    }

    @GetMapping("/my-account")
    public String myAccount(@AuthenticationPrincipal OAuth2User principal, Authentication authentication,
        CsrfToken csrfToken, Model model) {
        Map<String, Object> attributes = principal.getAttributes();
        model.addAttribute("title", "My account");
        model.addAttribute("displayName", firstValue(attributes, "name", "login", "given_name"));
        model.addAttribute("email", attributes.get("email"));
        model.addAttribute("avatarUrl", avatarUrl(attributes));
        model.addAttribute("providerName", providerName(authentication));
        model.addAttribute("csrfParameterName", csrfToken.getParameterName());
        model.addAttribute("csrfToken", csrfToken.getToken());
        return "my-account";
    }

    private String firstValue(Map<String, Object> attributes, String... keys) {
        for (String key : keys) {
            Object value = attributes.get(key);
            if (value instanceof String text && !text.isBlank()) {
                return text;
            }
        }
        return "Your account";
    }

    private String avatarUrl(Map<String, Object> attributes) {
        Object avatar = attributes.get("avatar_url");
        if (avatar instanceof String url) {
            return url;
        }
        Object picture = attributes.get("picture");
        if (picture instanceof String url) {
            return url;
        }
        if (picture instanceof Map<?, ?> pictureData && pictureData.get("data") instanceof Map<?, ?> data
            && data.get("url") instanceof String url) {
            return url;
        }
        return null;
    }

    private String providerName(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oauth2) {
            return oauth2.getAuthorizedClientRegistrationId();
        }
        return "OAuth2";
    }
}