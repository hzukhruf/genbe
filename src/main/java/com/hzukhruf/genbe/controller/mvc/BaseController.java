package com.hzukhruf.genbe.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forms")
public class BaseController {

	@GetMapping("/biodata.html")
    public String biodata1() {
        return "biodata/biodata";
    }
}
