package com.pma.springbootgraalvm.advice;

import com.samskivert.mustache.Mustache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class GlobalLayoutAdvice {

    @Autowired
    private Mustache.Compiler mustacheCompiler;

    @Autowired
    private ResourceLoader resourceLoader;

    @ModelAttribute("layout")
    public void addGlobalAttributes(HttpServletRequest request, Map<String, Object> model) {
        // 1. Get URI path (e.g., "/user/profile" or "/")
        String uri = request.getRequestURI();
        
        // 2. Normalize root or convert paths to a clean naming convention
        String pageName = (uri.equals("/") || uri.isEmpty()) ? "index" : uri.substring(1).replace("/", "-");
        
        // 3. Inject asset names only when the corresponding static resources exist
        String pageCss = pageName + ".css";
        String pageJs = pageName + ".js";
        if (resourceLoader.getResource("classpath:/static/" + pageCss).exists()) {
            model.put("pageCss", pageCss);
        }
        if (resourceLoader.getResource("classpath:/static/" + pageJs).exists()) {
            model.put("pageJs", pageJs);
        }

        // 4. Attach layout lambda frame
        model.put("layout", (Mustache.Lambda) (frag, out) -> {
            model.put("mainContent", frag.execute());
            mustacheCompiler.compile("{{> layout}}").execute(model, out);
        });
    }
}
