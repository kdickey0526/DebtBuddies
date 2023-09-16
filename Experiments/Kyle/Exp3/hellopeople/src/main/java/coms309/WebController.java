package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
@RestController
public class WebController {

    @GetMapping("/test")
    public String test(@RequestParam("testValue") String testValue, HttpServletResponse httpResponse) throws Exception {
        if(testValue.equals("redirect")){
            httpResponse.sendRedirect("/");
            return null;
        }
        return "<h1>success: " + testValue + "</h1>";
    }

}
