package testing;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfiguration {

    // ── MySQL ───────────────────────────────────────────────────────────────
//    static final MySQLContainer<?> MYSQL_CONTAINER =
//            new MySQLContainer<>(DockerImageName.parse("mysql:8.0.36"));


    // ── PostgreSQL ──────────────────────────────────────────────────────────
    static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2"));

    // Choose which one you want to use by uncommenting the desired @Bean
//
//    @Bean
//    @ServiceConnection
//    MySQLContainer<?> mysqlContainer() {
//        return MYSQL_CONTAINER;
//    }

     @Bean
     @ServiceConnection
     PostgreSQLContainer<?> postgresContainer() {
         return POSTGRES_CONTAINER;
     }

    // Bonus: if you want to start them eagerly (recommended in most cases)
    static {
       //  MYSQL_CONTAINER.start();     // uncomment if you want MySQL always started
        POSTGRES_CONTAINER.start();     // most common choice in modern Spring Boot projects
    }
}