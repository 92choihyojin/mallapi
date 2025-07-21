package com.kh.mallapi;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.kh.mallapi.domain.Todo;
import com.kh.mallapi.dto.PageRequestDTO;
import com.kh.mallapi.dto.PageResponseDTO;
import com.kh.mallapi.dto.TodoDTO;
import com.kh.mallapi.repository.TodoRepository;
import com.kh.mallapi.service.TodoService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class MallapiApplicationTests {

	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private TodoService todoService;

	// Todo 테이블에 Insert 기능 = jpa.save(entity) 기능 같다
	 @Test
	void contextLoads() {

		for (int i = 1; i <= 100; i++) {
			Todo todo = Todo.builder().title("Title" + i).dueDate(LocalDate.of(2025, 07, 15)).writer("chj").build();
			todoRepository.save(todo);
		}
	}
	
	
	// Todo , get select * from todo where tno = ? === findById(id)
	// @Test
	void testRead() {
		Long tno = 200L;
		Optional<Todo> result = todoRepository.findById(tno);
		Todo todo = result.orElseThrow();
		
		log.info(todo);
	}

	// 수정을 하기 위해서 findById(id) => 생성 Todo(정보가 이미 있다.) => Todo setter 수정한다. =>
	// save(Entity)
	// save(Entity) 해당되는 tno 가 있으면, update를 하고 , tno가 없으면 insert를 한다.
	// @Test
	void testModify() {
		Long tno = 200L;
		Optional<Todo> result = todoRepository.findById(tno);
		Todo todo = result.orElseThrow();
		todo.changeComplete(true);
		todo.changeWriter("lee00");
		todo.changeDueDate(LocalDate.of(2024, 7, 7));
		todoRepository.save(todo);
	}

	// tno를 삭제하는 기능
	// @Test
	void testDelete() {
		Long tno = 201L;
		todoRepository.deleteById(tno);
	}

	// paging 페이징 처리방식
	// @Test
	void testPaging() {
		// 1페이지, 10개만 보이기 , (tno 내림차순으로)[리액트]
		Pageable pageable = PageRequest.of(2, 10, Sort.by("tno").descending());
		Page<Todo> result = todoRepository.findAll(pageable);
		log.info("전체갯수" + result.getTotalElements());
		// 현재 페이지 10개
		result.getContent().stream().forEach(todo -> log.info(todo));
	}

	// TodoDTO 값을 서비스를 이용해서 다형성처리
	// @Test
	void testRegiseter() {
		TodoDTO todoDTO = TodoDTO.builder().title("입력테스트").writer("chj").dueDate(LocalDate.of(2025, 07, 16)).build();
		Long tno = todoService.register(todoDTO);
		log.info("TNO" + tno);
	}

	// @Test
	public void testGet() {
		Long tno = 101L;
		TodoDTO todoDTO = todoService.get(tno);
		TodoDTO _todoDTO = todoService.get(tno);
		log.info(todoDTO);
	}

	// 넘버리스트
	@Test
	void testNumber() {
	}

	// 페이징리스트
	@Test
	public void testList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(2).size(10).build();
		PageResponseDTO<TodoDTO> response = todoService.list(pageRequestDTO);
		log.info(response);
	}

}
