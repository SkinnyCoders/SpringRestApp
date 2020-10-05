package com.tanto;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tanto.service.ServiceUpload;

@SpringBootApplication
public class RestAppApplication {

	@Resource
	ServiceUpload servUpload;
	
	public static void main(String[] args) {
		SpringApplication.run(RestAppApplication.class, args);
	}
	
	public void run(String... arg) throws Exception {
		servUpload.init();
	}

}
