package com.hzukhruf.genbe.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

	@GetMapping("/biodata.html")
    public String biodata() {
        return "biodata/biodata";
    }
	
	@GetMapping("/pendidikan.html")
    public String pendidikan() {
        return "biodata/pendidikan";
    }
	
	@GetMapping("/getBiodataNik.html")
    public String getNik() {
        return "biodata/nikbio";
    }
}
