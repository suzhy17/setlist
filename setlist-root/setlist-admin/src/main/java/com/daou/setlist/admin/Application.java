package com.daou.setlist.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = { "com.daou.setlist" })
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 14400)
@PropertySource(value = "file:${app.home}", ignoreResourceNotFound = true)	// 개발/운영 실행시 필요
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)	// 로컬 실행시 필요
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
