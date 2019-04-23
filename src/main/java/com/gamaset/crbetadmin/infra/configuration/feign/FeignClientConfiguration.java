package com.gamaset.crbetadmin.infra.configuration.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gamaset.crbetadmin.client.crbetsportal.CrbetsPortalClient;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
@EnableFeignClients
public class FeignClientConfiguration {

//	private String crbetsPortal = "http://18.231.188.87:8280/crbets/api";
	private String crbetsPortal = "http://localhost:8280/crbets/api";

	@Bean
	public CrbetsPortalClient businessOriginClient() {
		return Feign.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(CrbetsPortalClient.class, crbetsPortal);
	}
	
}
