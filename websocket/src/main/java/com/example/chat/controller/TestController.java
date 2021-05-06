package com.example.chat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @Description:
 * @Date: 2021/4/30 9:32
 * @Version: 1.0
 */
@RestController
@RequestMapping("/chat-test")
public class TestController {

    @RequestMapping("/index")
    public ModelAndView hello(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
