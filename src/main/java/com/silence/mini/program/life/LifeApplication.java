package com.silence.mini.program.life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration//配置控制
@EnableAutoConfiguration//启用自动配置
@SpringBootApplication
@ComponentScan({"com.silence.mini.program.life.*"})//组件扫
public class LifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeApplication.class, args);
	}
}
