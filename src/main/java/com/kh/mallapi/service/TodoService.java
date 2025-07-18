package com.kh.mallapi.service;

import com.kh.mallapi.dto.PageRequestDTO;
import com.kh.mallapi.dto.PageResponseDTO;
import com.kh.mallapi.dto.TodoDTO;

public interface TodoService {
	
	//insert
	public Long register(TodoDTO todoDTO);
	//update
	void modify(TodoDTO todoDTO);
	//select
	public TodoDTO get(Long tno);
	//delete
	public void remove(Long tno);
	//페이징처리 및 리스트요청
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
	
	}