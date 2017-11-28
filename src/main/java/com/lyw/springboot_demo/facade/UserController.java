package com.lyw.springboot_demo.facade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    @ResponseBody
    public String query() {
        return "user";
    }
}
