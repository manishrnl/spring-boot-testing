package testing.repositories;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import testing.TestContainerConfiguration;
import testing.entities.Employee;

import java.util.List;

@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@DataJpaTest //By default it uses @Transactional rollback after each test case functions

//DataJpaTest loads only JPA repository instead of @SpringBootTest which loads the entire application context.

class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @Test
    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .email("manish5@gmail.com")
                .name("Manish")
                .salary(50000L)
                .build();

    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        //Arrange  Given
        employeeRepository.save(employee);
        //Act ,When
        List<Employee> foundEmployees = employeeRepository.findByEmail(employee.getEmail());
        //Assert ,Then
        Assertions.assertThat(foundEmployees).isNotNull();
        Assertions.assertThat(foundEmployees).isNotEmpty();
        Assertions.assertThat(foundEmployees.getFirst().getEmail()).isEqualTo(employee.getEmail());
        log.info("All tests Passed.........");
    }

    @Test
    void testFindByEmail_whenEmailIsInvalid_thenReturnEmptyList() {

//        Given
        String invalidEmail = "notPresent@gmail.com";
//        When
        List<Employee> employeeList = employeeRepository.findByEmail(invalidEmail);
//        Then
        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList).isNotNull();
    }


}