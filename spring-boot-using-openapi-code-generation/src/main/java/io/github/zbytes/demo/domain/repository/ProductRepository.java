package io.github.zbytes.demo.domain.repository;

import io.github.zbytes.demo.domain.model.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ProductRepository
		extends PagingAndSortingRepository<ProductEntity, String>, QueryByExampleExecutor<ProductEntity> {

}
