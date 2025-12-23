package testing;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ApplicationTests {

    @BeforeEach
        // Runs before each test method
    void setUp() {
        log.info("Setting up tests...");
    }

    @AfterEach
        // Runs after each test method
    void tearDown() {
        log.info("Tearing down after tests...\n");
    }

    @BeforeAll // Runs once before all test methods starts
    static void beforeAll() {
        log.info("# # # # # # # # # # # # # # # # # # # # # # # # Starting Application " +
                "Tests...");
    }

    @AfterAll // Runs once after all test methods have finished
    static void afterAll() {
        log.info("# # # # # # # # # # # # # # # # # # # # # # # # Finished Application Tests.");

    }

    @Test
    @DisplayName("Hello Tests")
        //Displays the name of the test
    void contextLoads() {
        log.info("test 1 runs successfully");

    }

    @Test
    @DisplayName("Sample Test")
//  @Disabled  // To disable this test
    void sampleTest() {
        log.info("test 2 runs successfully");
    }

    @Test
    void checkAddition() {
        int result = addNumbers(2, 4);
        Assertions.assertThat(result)
                .isEqualTo(6)
                .isEven();
        log.info("Addition test passed successfully");
    }

    @Test
    void checkString() {
        String string = "Checking Value";
        Assertions.assertThat(string).isEqualTo("Checking Value")
                .contains("Value")
                .startsWith("Check")
                .endsWith("Value")
                .hasSize(14);

    }

    int addNumbers(int a, int b) {
        return a + b;
    }
}
