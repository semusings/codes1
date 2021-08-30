package io.github.zbytes.demo.interfaces.rest.impl;

import io.github.zbytes.demo.domain.model.UserEntity;
import io.github.zbytes.demo.domain.repository.UserRepository;
import io.github.zbytes.demo.interfaces.rest.UserApi;
import io.github.zbytes.demo.interfaces.rest.dto.User;
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
class WebUserApi implements UserApi {

	private final UserRepository repository;

	WebUserApi(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<Void> createUser(User user) {
		repository.save(toEntity(user));
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteUser(String username) {
		return applyVoid(repository, username, e -> repository.deleteById(e.getUsername()));
	}

	@Override
	public ResponseEntity<User> getUserByName(String username, Boolean withEmail) {
		return applyResult(repository, username, this::toResource);
	}

	@Override
	public ResponseEntity<List<User>> getUsers(Integer page, Integer size, String sort) {
		PageRequest request = PageRequest.of(page, size, Sort.by(sort));
		Page<UserEntity> all = repository.findAll(request);
		List<User> list = all.stream().map(this::toResource).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).headers(pageHeaders(all)).body(list);
	}

	@Override
	public ResponseEntity<Void> updateUser(User user, String username) {
		return applyVoid(repository, username, e -> {
			e.with(user.getFirstName(), user.getLastName(), user.getEmail());
			repository.save(e);
		});
	}

	private User toResource(UserEntity entity) {
		return new User().username(entity.getUsername()).firstName(entity.getFirstName()).lastName(entity.getLastName())
				.email(entity.getEmail());
	}

	private UserEntity toEntity(User user) {
		UserEntity entity = new UserEntity(user.getUsername());
		entity.with(user.getFirstName(), user.getLastName(), user.getEmail());
		return entity;
	}

}
