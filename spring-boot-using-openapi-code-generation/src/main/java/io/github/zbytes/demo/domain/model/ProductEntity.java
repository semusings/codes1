package io.github.zbytes.demo.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table
public class ProductEntity {

	@Id
	private String productId;

	private String productName;

	public ProductEntity(String productId) {
		this.productId = productId;
	}

	public void with(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ProductEntity that = (ProductEntity) o;
		return Objects.equals(productId, that.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

}
