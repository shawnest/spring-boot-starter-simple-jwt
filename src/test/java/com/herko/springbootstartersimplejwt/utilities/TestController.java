package com.herko.springbootstartersimplejwt.utilities;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestController.ENDPOINT)
public class TestController {
    public static final String ENDPOINT = "/test";

    @GetMapping
    String example() {
        return "Example";
    }
}
