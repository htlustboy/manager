package com.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manager.model.User;

@SpringBootApplication
@Controller
public class Application {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	/**
	 * 程序启动入口
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("manager 程序启动>>>>>>>>>>>>>>>>>>>>>");
		SpringApplication.run(Application.class, args);
		logger.info("程序入口：localhost:8080/");
	}
	
	@RequestMapping("/")
	public String index(Model model){
		User user = new User();
		user.setUsername("hutao");
		user.setPassword("123456");
		model.addAttribute("user", user);
		return "index";
	}
}
