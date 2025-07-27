package com.kh.mallapi.service;

import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.kh.mallapi.domain.Member;
import com.kh.mallapi.dto.MemberDTO;

@Transactional
public interface MemberService {

	public default MemberDTO getKakaoMember(String accessToken) {
		return null;
	}

	default MemberDTO entityToDTO(Member member) {
		MemberDTO dto = new MemberDTO(member.getEmail(), member.getPw(), member.isSocial(), member.getNickname(),
				member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));
		return dto;
	}
}
