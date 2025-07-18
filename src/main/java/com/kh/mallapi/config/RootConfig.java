package com.kh.mallapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
	@Bean
	public ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		//1. 2개 클래스에서 필드 이름이 같으면 매핑
		//2. 접근제어자 private 매핑
		//3. 접근전략 글자가 완전히 같지 않아도 유사해도 매핑
		modelMapper.getConfiguration().setFieldMatchingEnabled(true)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
				.setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}
}
