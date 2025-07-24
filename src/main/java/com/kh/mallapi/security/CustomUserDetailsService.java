package com.kh.mallapi.security;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.mallapi.domain.Member;
import com.kh.mallapi.dto.MemberDTO;
import com.kh.mallapi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//사용자가 로그인을 하면 로그인 정보를 loadUserByUsername(String username) 이 함수로 가져온다 (커스텀마이즈)
//시큐리티 인증 인가 획득
@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("=====");
		Member member = memberRepository.getWithRoles(username);
		if (member == null) {
			throw new UsernameNotFoundException("Not Found");
		}

		MemberDTO memberDTO = new MemberDTO(member.getEmail(), member.getPw(), member.isSocial(), member.getNickname(),
				member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));

		log.info(memberDTO);
		return memberDTO;
	}

}
