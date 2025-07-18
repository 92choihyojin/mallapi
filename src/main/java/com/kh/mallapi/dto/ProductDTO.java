package com.kh.mallapi.dto;

import lombok.*;
import java.util.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	
	private Long pno;
	private String pname;
	private int price;
	private String pdesc;
	
	// MultipartFile 클라이언트 자료 업로드된 정보를 저장하고 있는 클래스(파일명, 파일사이즈, 파일타입, 파일정보)
	@Builder.Default
	private List<MultipartFile> files = new ArrayList<>();
	
	//실제 서버 API 저장되어진 이름 (UUID_실제 파일명, 파일타입)
	@Builder.Default
	private List<String> uploadFileNames = new ArrayList<>();
	
}
