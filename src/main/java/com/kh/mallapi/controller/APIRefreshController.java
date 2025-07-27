package com.kh.mallapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.mallapi.util.CustomJWTException;
import com.kh.mallapi.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {
	@RequestMapping("/api/member/refresh")
	public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken) {

		/*
		 * 조건: access token, refresh token 두개다 받는다. access token Header, refresh token: url 파라미터 받는다.
		 * 1. access token 시간이 만료가 안되었다. => 리액트(access token, refresh token) 되돌려준다.
		 * 2. access token 이 만료되었다. => newAccess 토큰 생성한다.
		 * 2.1 refresh token 이 한시간 미만 남을때 => refresh token 새로 생성한다 . 그리고 (newAccess, newRefresh) 돌려준다
		 * 2.2 refresh token이 한시간 이상 남을때 => 그리고 (newAccess, 기존 newRefresh) 돌려준다
		 */

		if (refreshToken == null) {
			throw new CustomJWTException("NULL_REFRASH");
		}
		if (authHeader == null || authHeader.length() < 7) {
			throw new CustomJWTException("INVALID_STRING");
		}
		String accessToken = authHeader.substring(7);
		// Access 토큰이 만료되지 않았다면 기존 토큰값 리턴
		// refresh 토큰을 입력할 경우 만료가 되면 true,
		if (checkExpiredToken(accessToken) == false) {
			return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
		}
		// Access 토큰을 입력할경우 만료가 되었다. (지금부터는 refresh 토큰만 점검하면 된다)
		// 지금부터는 refresh 토큰만 점검하면된다.
		// Refresh토큰 검증
		Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
		log.info("refresh ... claims: " + claims);

		// 10분 access 토큰생성
		String newAccessToken = JWTUtil.generateToken(claims, 10);
		// refresh 토큰이 1시간이 안남았으면 => 1일 refresh token 생성
		String newRefreshToken = checkTime((Integer) claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60 * 24)
				: refreshToken;
		return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
	}

	// 만료가 되지 않는 refresh 토큰에서 만료시간 exp를 가져와서 시간이 1시간 미만으로 남았는지 점검.
	// 1시간 이상이 남았으면 false
	private boolean checkTime(Integer exp) {
		// JWT exp를 날짜로 변환
		java.util.Date expDate = new java.util.Date((long) exp * (1000));
		// 현재 시간과의 차이 계산 - 밀리세컨즈
		long gap = expDate.getTime() - System.currentTimeMillis();
		// 분단위 계산
		long leftMin = gap / (1000 * 60);
		// 1시간도 안남았는지.. true, 1시강
		return leftMin < 60;
	}

	// refresh 토큰을 입력할 경우 만료가 되면 true , 만료가 되지 않으면 false
	private boolean checkExpiredToken(String token) {
		try {
			JWTUtil.validateToken(token);
		} catch (CustomJWTException ex) {
			if (ex.getMessage().equals("Expired")) {
				return true;
			}
		}
		return false;
	}
}
