package testing.services.impl;

import jakarta.persistence.PreRemove;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import testing.services.DataService;
@Service
@Profile("dev")
public class DataServiceImplDev implements DataService {

    @Override
    public String getData() {
        return "Dev Data";
    }
}
