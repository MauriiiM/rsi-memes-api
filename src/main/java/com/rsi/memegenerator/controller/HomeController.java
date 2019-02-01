package com.rsi.memegenerator.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rsi.memegenerator.constant.Routes.UI;

@RestController
public class HomeController {
    @GetMapping(value = "/")
    public void method(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.sendRedirect("https://www.ruralsourcing.com/");
            httpServletResponse.setStatus(302);
        } catch (IOException e) {
            System.out.println("HERE");
        }
    }
}
