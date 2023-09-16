package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        String spelling = name + ", your name is spelled: ";
        for(int i = 0; i < name.length(); i++){
            spelling = spelling + name.charAt(i);
            if(i < name.length() - 1){
                spelling = spelling + " - ";
            }
        }
        return "Hello and welcome to COMS 309: " + spelling;
    }
}
