package io.github.zbytes.demo.interfaces.rest.impl;

import io.github.zbytes.demo.domain.model.ProductEntity;
import io.github.zbytes.demo.domain.repository.ProductRepository;
import io.github.zbytes.demo.interfaces.rest.ProductApi;
import io.github.zbytes.demo.interfaces.rest.dto.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.zbytes.demo.interfaces.rest.impl.WebUtils.*;

@RestController
class WebProductApi implements ProductApi {

	private final ProductRepository repository;

	WebProductApi(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<Void> createProduct(Product product) {
		repository.save(toEntity(product));
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteProduct(String productId) {
		return applyVoid(repository, productId, e -> repository.deleteById(e.getProductId()));
	}

	@Override
	public ResponseEntity<Product> getProductById(String productId) {
		return applyResult(repository, productId, this::toResource);
	}

	@Override
	public ResponseEntity<List<Product>> getProducts(Integer page, Integer size, String sort) {
		PageRequest request = PageRequest.of(page, size, Sort.by(sort));
		Page<ProductEntity> all = repository.findAll(request);
		List<Product> list = all.stream().map(this::toResource).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).headers(pageHeaders(all)).body(list);
	}

	@Override
	public ResponseEntity<Void> updateProduct(Product product, String productId) {
		return applyVoid(repository, productId, e -> {
			e.with(product.getProductName());
			repository.save(e);
		});
	}

	private Product toResource(ProductEntity entity) {
		return new Product().productId(entity.getProductId()).productName(entity.getProductName());
	}

	private ProductEntity toEntity(Product product) {
		ProductEntity entity = new ProductEntity(product.getProductId());
		entity.with(product.getProductName());
		return entity;
	}

}
