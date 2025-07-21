package com.kh.mallapi.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList") // Lazy 방식
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ_GEN")
	private Long pno;
	private String pname;
	private int price;
	private String pdesc;
	private boolean delFlag;

	// tbl_product 테이블 외에 tbl_product_image_list 테이블이 생기고 tbl_product_id, filename,
	// ord 컬럼이 들어감
	@ElementCollection
	@Builder.Default
	private List<ProductImage> imageList = new ArrayList<>();

	public void changeName(String name) {
		this.pname = name;
	}

	public void changePrice(int price) {
		this.price = price;
	}

	public void changeDesc(String desc) {
		this.pdesc = desc;
	}

	public void changeDel(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public void addImage(ProductImage image) {
		// 이미지 추가시 순서(ord) 자동 설정 (0, 1, 2, ...)
		image.setOrd(this.imageList.size());
		imageList.add(image);
	}

	public void addImageString(String fileName) {
		ProductImage productImage = ProductImage.builder().fileName(fileName).build();
		addImage(productImage);
	}

	public void clearList() {
		this.imageList.clear();
	}

}
