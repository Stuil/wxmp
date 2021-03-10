package com.stuil.wx.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @title: IndexController
 * @description:
 * @date: 2021/3/9
 * @author: stuil
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */

@Controller
public class IndexController {
    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "success";

    }
}
