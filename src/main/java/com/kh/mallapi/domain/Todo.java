package com.kh.mallapi.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//domain == table  도메인은 테이블과 같다.

@Entity
@Table(name = "tbl_todo")
@SequenceGenerator(name = "TODO_SEQ_GEN", // 시퀀스 제너레이터 이름
		sequenceName = "TODO_SEQ", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1) // 메모리를 통해 할당할 범위 사이즈
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TODO_SEQ_GEN")
	private Long tno; // primary key (기본값)
	private String title; // 제목
	private String writer; // 작성자
	private boolean complete; // 완성
	private LocalDate dueDate; // 작성날짜

	public void changeTitle(String title) {
		this.title = title;
	}
	
	public void changeWriter(String writer) {
		this.writer = writer;
	}
	
	public void changeComplete(boolean complete) {
		this.complete = complete;
	}

	public void changeDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

}
