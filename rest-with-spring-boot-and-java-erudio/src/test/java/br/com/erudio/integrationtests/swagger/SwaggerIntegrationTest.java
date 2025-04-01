package br.com.erudio.integrationtests.swagger;

import br.com.erudio.config.TestConfig;

import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	@DisplayName("Swagger should be available")
	void testShouldDisplaySwaggerUIPage() {

		String content = given()
						.basePath("/swagger-ui/index.html")
						.port(TestConfig.SERVER_PORT)
						.when()
							.get()
						.then()
							.statusCode(200)
						.extract()
							.body()
								.asString();

		assertTrue(content.contains("Swagger UI"));

	}

}
