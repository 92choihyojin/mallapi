package com.kh.mallapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kh.mallapi.domain.Member;
import com.kh.mallapi.repository.MemberRepository;
import com.kh.mallrestful.domain.MemberRole;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class MemberApplicationTests {
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testInsertMember() {
	    for (int i = 0; i < 10; i++) {
	    	//user 0 ~ user 4 권한 USER
	        Member member = Member.builder()
	            .email("user" + i + "@jjjj.com")
	            .pw(passwordEncoder.encode("1234"))
	            .nickname("USER" + i)
	            .build();

	        member.addRole(MemberRole.USER);

	        //user 5 ~ user 7 권한 USER , MNAGER
	        if (i >= 5) {
	            member.addRole(MemberRole.MANAGER);
	        }
	        //user 8 권한 USER , MNAGER , ADMIN
	        if (i >= 8) {
	            member.addRole(MemberRole.ADMIN);
	        }

	        memberRepository.save(member);
	    }
	}

	@Test
	public void testRead() {
	    String email = "user9@jjjj.com";
	    Member member = memberRepository.getWithRoles(email);
	    log.info("_________________________");
	    log.info(member);
	}

}
