package com.pma.springbootgraalvm;

import java.util.Map;
import java.util.Collections;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping(value="/hello", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> hello() {
        return Collections.singletonMap("message", "Hello from Spring Boot 3");
    }

    @GetMapping(value="/hello", produces=MediaType.TEXT_HTML_VALUE)
    public String hello(Model model) {
        model.addAttribute("message", "Hello from Spring Boot 3");
        return "hello";
    }
}

