package testing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UnitTesting {

    @Test
    void dockerSanityCheck() {
        try (var postgres = new PostgreSQLContainer<>("postgres:16.2")) {
            postgres.start();
            assertTrue(postgres.isRunning(), "PostgreSQL container should be running");
            assertNotNull(postgres.getJdbcUrl(), "Should have JDBC URL");
        }
    }



}
