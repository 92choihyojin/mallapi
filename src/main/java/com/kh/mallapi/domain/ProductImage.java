package com.kh.mallapi.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable  // 1 : N  방식 , ProductDTO 의 pno 를 FK (포린키) 로 설정한다.
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
	
	private String fileName;
	private int ord;  // 이미지 순서  본이미지(0) , 서브이미지 (1~4...) 순서로 됨

	public void setOrd(int ord) {
		this.ord = ord;
	}
}
