package testing.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import testing.services.DataService;

@Service
@Profile("test")
public class DataServiceImplTest implements DataService {
    @Override
    public String getData() {
        return "Test Environment Data";
    }
}
