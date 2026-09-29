package com.pma.springbootgraalvm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloPageRendersJson() throws Exception {
        mockMvc.perform(get("/hello")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Hello from Spring Boot 3"));
    }

    @Test
    void helloPageRendersMustacheView() throws Exception {
        mockMvc.perform(get("/hello")
            .accept(MediaType.TEXT_HTML_VALUE))
            .andExpect(status().isOk())
            .andExpect(view().name("hello"))
            .andExpect(model().attribute("message", "Hello from Spring Boot 3"))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("<h1>Hello from Spring Boot 3</h1>")));
    }

    @Test
    void accountRequiresLogin() throws Exception {
        mockMvc.perform(get("/my-account"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }

    @Test
    void accountLoginRedirectUsesForwardedPublicHost() throws Exception {
        mockMvc.perform(get("/my-account")
            .header("X-Forwarded-Host", "app.example.com")
            .header("X-Forwarded-Proto", "https"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }

    @Test
    void loginPageOffersAllConfiguredProviders() throws Exception {
        mockMvc.perform(get("/login").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("/oauth2/authorization/github")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("/oauth2/authorization/google")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("/oauth2/authorization/facebook")));
    }

    @Test
    void authenticatedAccountShowsProfileAndProvider() throws Exception {
        mockMvc.perform(get("/my-account").with(oauth2Login()
            .attributes(attributes -> {
                attributes.put("name", "Taylor Example");
                attributes.put("email", "taylor@example.com");
                attributes.put("avatar_url", "https://example.com/avatar.png");
            })))
            .andExpect(status().isOk())
            .andExpect(view().name("my-account"))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Taylor Example")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("taylor@example.com")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("test")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("name=\"_csrf\"")));
    }
}

