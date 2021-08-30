package io.github.zbytes.demo.interfaces.rest.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

class WebUtils {

	private WebUtils(){}

	static <T> HttpHeaders pageHeaders(Page<T> all) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Page", String.valueOf(all.getNumber()));
		headers.add("X-Page-Size", String.valueOf(all.getSize()));
		headers.add("X-Total-Count", String.valueOf(all.getTotalElements()));
		headers.add("X-Sort-By", all.getSort().toString());
		return headers;
	}

	static <T, E, I> ResponseEntity<T> applyResult(CrudRepository<E, I> repository, I id, Function<E, T> func) {
		Optional<E> entity = repository.findById(id);
		if (entity.isPresent()) {
			T result = func.apply(entity.get());
			return Objects.nonNull(result) ? ResponseEntity.ok(result) : ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	static <E, I> ResponseEntity<Void> applyVoid(CrudRepository<E, I> repository, I id, Consumer<E> func) {
		return applyResult(repository, id, e -> {
			func.accept(e);
			return null;
		});
	}

}
