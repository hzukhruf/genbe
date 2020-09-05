package com.hzukhruf.genbe.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forms")
public class BaseController {

	@GetMapping("/biodata1.html")
    public String biodata1() {
        return "biodata/biodata1";
    }
	
	@GetMapping("/biodata2.html")
    public String biodata2() {
        return "biodata/biodata2";
    }
	
	@GetMapping("/biodata3.html")
    public String biodata3() {
        return "biodata/biodata3";
    }
}
