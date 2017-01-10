package com.daou.setlist.web.config;

import javax.annotation.Resource;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 톰캣 AJP 설정
 * 
 * @author suzhy
 */
@Configuration
public class TomcatAjpConfig {

	@Resource
	private Environment env;

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {

		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		if (env.getProperty("tomcat.ajp.enabled", Boolean.class)) {
			Connector ajpConnector = new Connector("AJP/1.3");
			ajpConnector.setPort(env.getProperty("tomcat.ajp.port", Integer.class));
			ajpConnector.setSecure(false);
			ajpConnector.setAllowTrace(false);
			ajpConnector.setScheme("http");
			tomcat.addAdditionalTomcatConnectors(ajpConnector);
		}

		return tomcat;
	}
}
