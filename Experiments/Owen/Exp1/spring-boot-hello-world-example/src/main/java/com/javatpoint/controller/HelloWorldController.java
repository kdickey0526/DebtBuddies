package com.javatpoint.controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {
    @RequestMapping("/")
    public String hello() {
        return "Hello my name is Owen Parker, This is my demo1";
    }


    @PostMapping("/people")
    public @ResponseBody String createPerson(@RequestBody String person) {
        System.out.println(person);
//        peopleList.put(person.getFirstName(), person);
        return "New person " + person + " Saved";
    }

/*    @getMapping("/people/{firstName}")
    public @ResponseBody String getPerson(@PathVariable String firstName) {
//        Person p = peopleList.get(firstName);
        return firstName;
    }*/
}