package br.com.erudio.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    public AbstractIntegrationTest() {
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer mysql = new MySQLContainer("mysql:8.0.28");

        public Initializer() {
        }

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {

            Map<String, String> map = new HashMap<>();

            map.put("spring.datasource.url", mysql.getJdbcUrl());
            map.put("spring.datasource.username", mysql.getUsername());
            map.put("spring.datasource.password", mysql.getPassword());

            return map;

        }

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            startContainers();

            ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();

            MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());

            environment.getPropertySources().addFirst(testContainers);

        }

    }

}
