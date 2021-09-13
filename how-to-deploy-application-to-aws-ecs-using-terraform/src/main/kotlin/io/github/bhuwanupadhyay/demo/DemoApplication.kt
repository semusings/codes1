package io.github.bhuwanupadhyay.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Component
class NameHandler {

    fun findGivenName(req: ServerRequest): Mono<ServerResponse> {
        return Optional.ofNullable(req.pathVariable("given-name"))
                .map { t -> ok().bodyValue("{ \"givenName\": \"$t\"}") }
                .orElseGet { badRequest().build() }
    }

}

@Configuration
class NameRoutes(private val handler: NameHandler) {

    @Bean
    fun router() = router {
        accept(APPLICATION_JSON).nest {
            GET("/names/{given-name}", handler::findGivenName)
        }
    }

}