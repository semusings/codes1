package io.github.bhuwanupadhyay.app;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "examples", path = "examples")
public interface ExampleRepository extends CrudRepository<Example, Long> {
}
