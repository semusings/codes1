package io.github.bhuwanupadhyay.demo.domain.repository;

import io.github.bhuwanupadhyay.demo.domain.model.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ProductRepository
		extends PagingAndSortingRepository<ProductEntity, String>, QueryByExampleExecutor<ProductEntity> {

}
