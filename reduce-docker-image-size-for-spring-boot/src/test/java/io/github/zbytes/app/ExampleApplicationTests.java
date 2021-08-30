package io.github.bhuwanupadhyay.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExampleApplicationTests {

	@Autowired
	private ExampleRepository repository;

	@Test
	void contextLoads() {
		assertNotNull(repository);
	}

}
