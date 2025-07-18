package com.kh.mallapi.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {
	@Value("${com.kh.upload.path}")
	private String uploadPath;

	@PostConstruct
	// 파일 업로드용 폴더가 없으면 자동으로 생성하는 초기화 로직
	// 생성자 → 의존성 주입(DI) → @PostConstruct 메서드 실행
	// uploadPath 값이 주입된 이후에 실행됨.
	// init() : 업로드할 파링링 저장된 폴더 위치를 uploadPath에 저장하는 설정하는 함수
	public void init() {
		File tempFolder = new File(uploadPath);
		if (tempFolder.exists() == false) {
			tempFolder.mkdir();
		}
		uploadPath = tempFolder.getAbsolutePath();
		log.info(" ");
		log.info(uploadPath);
	}

	// 업로드 된 이미지파일과 썸네일 이미지 파일을 만들어서 저장폴더에 저장한다.
	public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {
		if (files == null || files.size() == 0) {
			return null;
		}
		List<String> uploadNames = new ArrayList<>();

		// MultiparFile : 사용자가 업로드한 파일정보 들어있는 객체
		for (MultipartFile multipartFile : files) {

			// UUID_chj.jpg
			String savedName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
			Path savePath = Paths.get(uploadPath, savedName);
			try {
				// 사용자가 올린 ABC.jpg =>
				Files.copy(multipartFile.getInputStream(), savePath);

				// 업로드한 파일이 이미지 파일 타입 체크
				String contentType = multipartFile.getContentType();
				// 이미지 파일인지 아닌지 체크
				if (contentType != null && contentType.startsWith("image")) {
					// savePath
					Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);
					// 썸네일 이미지 가로 400px, 세로 400px 만들어서 해당 경로에 저장
					Thumbnails.of(savePath.toFile()).size(400, 400).toFile(thumbnailPath.toFile());
				}

				uploadNames.add(savedName);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		} // end for
		return uploadNames;
	}

	// 파일 읽기
	public ResponseEntity<Resource> getFile(String fileName) {
		// uploadPath=C:/SpringBootProject/workspace/mallapi/upload/UUID_피카츄.jpg
		Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
		// 내가 요청한 파일이 없으면 디폴트 사진을 전송해준다.
		if (!resource.exists()) {
			resource = new FileSystemResource(uploadPath + File.separator + "default.jpg");
		}
		HttpHeaders headers = new HttpHeaders();
		try {
			// Files.probeContentType()은 파일 경로를 분석하여 MIME 타입을 자동 감지 jpg → image/jpeg, png →
			// //image/pngpdf → application/pdf 이 정보를 HTTP 응답 헤더에 Content-Type으로 추가한다
			headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().headers(headers).body(resource);
	}

	// 파일 삭제
	public void deleteFiles(List<String> fileNames) {
		if (fileNames == null || fileNames.size() == 0) {
			return;
		}
		
		fileNames.forEach(fileName -> {
			// 썸네일이 있는지 확인하고 삭제
			String thumbnailFileName = "s_" + fileName;
			Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
			Path filePath = Paths.get(uploadPath, fileName);
			try {
				Files.deleteIfExists(filePath);
				Files.deleteIfExists(thumbnailPath);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		});
	}

}
