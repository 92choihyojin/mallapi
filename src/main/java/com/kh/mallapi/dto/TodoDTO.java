package com.kh.mallapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

		private Long tno; // primary key (기본값)
		private String title; // 제목
		private String writer; // 작성자
		private boolean complete; // 완성
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		private LocalDate dueDate; // 작성날짜
}
