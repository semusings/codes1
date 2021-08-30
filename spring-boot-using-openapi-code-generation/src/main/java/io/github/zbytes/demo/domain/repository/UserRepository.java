package io.github.zbytes.demo.domain.repository;

import io.github.zbytes.demo.domain.model.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository
		extends PagingAndSortingRepository<UserEntity, String>, QueryByExampleExecutor<UserEntity> {

}
