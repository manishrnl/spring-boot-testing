package testing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
public class UnitTesting {

    @Test
    void dockerSanityCheck() {
        System.out.println("DOCKER_HOST = [" + System.getenv("DOCKER_HOST") + "]");
        try (var postgres = new PostgreSQLContainer<>("postgres:16.2")) {
            postgres.start();
        }
    }




}
