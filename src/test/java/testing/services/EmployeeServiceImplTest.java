package testing.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import testing.TestContainerConfiguration;
import testing.dto.EmployeeDto;
import testing.entities.Employee;
import testing.exceptions.ResourceNotFoundException;
import testing.repositories.EmployeeRepository;
import testing.services.impl.EmployeeServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1L)
                .email("manish@gmail.com")
                .name("Manish")
                .salary(200L)
                .build();

        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {
//        assign

        Long id = mockEmployee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee)); //stubbing

//        act

        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

//        assert

        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository, only()).findById(id);
    }

    @Test
    void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException() {
//       assign
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

//        Act and assert
        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");

        verify(employeeRepository, times(1)).findById(1L);

    }

    @Test
    void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee() {

//        assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
//        act

        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

//        assert


        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }

    @Test
    void testCreateNewEmployee_whenEmployeeWithEmailAlreadyExists_thenThrowException() {
//      Arrange
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of(mockEmployee));

//        act and assert

        Assertions.assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: " + mockEmployeeDto.getEmail());


    }

    @Test
    void testUpdateEmployee_whenEmployeeNotExists_thenThrowException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

//        Act and assert
        Assertions.assertThatThrownBy(() -> employeeService.updateEmployee(1L,
                        mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + mockEmployeeDto.getId());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).save(any());

    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setEmail("randomEmail@gmail.com");

        Assertions.assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");

        verify(employeeRepository, times(1)).findById(mockEmployeeDto.getId());

    }

    @Test
    void testUpdateEmployee_WhenValidEmployee_ThenUpdateEmployee() {
//        Assign
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setName("Random Name");
        mockEmployeeDto.setSalary(500L);
        Employee newEmployee = modelMapper.map(mockEmployeeDto, Employee.class);

//        Act
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

        EmployeeDto UpdatedEmployeeDto =
                employeeService.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto);

//        Assert
        assertThat(UpdatedEmployeeDto).isEqualTo(mockEmployeeDto);

//        verify(employeeRepository).findById(1L);
//        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee_whenEmployeeNotExists_thenThrowException() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");
        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, never()).save(any());
        verify(employeeRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee() {

        when(employeeRepository.existsById(1L)).thenReturn(true);

        assertThatCode(()->employeeService.deleteEmployee(1L))
                .doesNotThrowAnyException();
        verify(employeeRepository, times(1)).deleteById(1L);
    }


}