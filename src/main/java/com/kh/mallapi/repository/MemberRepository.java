package com.kh.mallapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kh.mallapi.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	// Left 조인설정
	@EntityGraph(attributePaths = { "memberRoleList" })
	@Query("select m from Member m where m.email = :email")
	Member getWithRoles(@Param("email") String email);
}
