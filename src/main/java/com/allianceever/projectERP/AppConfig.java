package com.allianceever.projectERP;

import com.allianceever.projectERP.repository.HolidayRepo;
import com.allianceever.projectERP.service.HolidayService;
import com.allianceever.projectERP.service.implementation.HolidayServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public HolidayService holidayService(HolidayRepo h, ModelMapper m) {
        return new HolidayServiceImpl(h, m);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }
}