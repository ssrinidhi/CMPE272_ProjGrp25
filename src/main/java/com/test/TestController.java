package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value="/taest", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
 
		model.addAttribute("message", "JSON DB2 Connector");
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "portfolio";
	}
 
	@RequestMapping(value="/welcome/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String name, ModelMap model) {
 
		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - " + name);
		return "portfolio";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String getIndexPage() {
		return "index";
	}
}
