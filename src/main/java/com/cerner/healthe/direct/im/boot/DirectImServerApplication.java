package com.cerner.healthe.direct.im.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cerner.healthe.direct.im.boot", "com.cerner.healthe.direct.im.springconfig"})
public class DirectImServerApplication 
{	
	public static void main(String[] args) 
	{
		SpringApplication.run(DirectImServerApplication.class, args);
	}

}
