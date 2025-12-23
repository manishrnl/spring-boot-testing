package testing.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class DataServiceTest {
    @Autowired
    private  DataService dataService;


  //  @Test
    void getData() {
        System.out.println("DataServiceTest.getData :" + dataService.getData());
    }
}
