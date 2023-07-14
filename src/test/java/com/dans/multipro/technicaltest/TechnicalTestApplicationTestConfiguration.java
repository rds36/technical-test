package com.dans.multipro.technicaltest;

import com.dans.multipro.technicaltest.configuration.ObjectMapperConfiguration;
import com.dans.multipro.technicaltest.repository.UserRepository;
import com.dans.multipro.technicaltest.service.PositionService;
import com.dans.multipro.technicaltest.service.PositionServiceImpl;
import com.dans.multipro.technicaltest.service.UserService;
import com.dans.multipro.technicaltest.service.UserServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.dans.multipro.technicaltest")
public class TechnicalTestApplicationTestConfiguration {

    public ObjectMapperConfiguration objectMapperConfiguration(){
        return Mockito.mock(ObjectMapperConfiguration.class);
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserServiceImpl.class);
    }

    @Bean
    public PositionService positionService(){
        return Mockito.mock(PositionServiceImpl.class);
    }

}
