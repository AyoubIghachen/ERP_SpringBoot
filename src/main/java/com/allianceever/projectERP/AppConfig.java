package com.allianceever.projectERP;

import com.allianceever.projectERP.model.entity.Holiday;
import com.allianceever.projectERP.repository.HolidayRepo;
import com.allianceever.projectERP.repository.LeaveTypeRepo;
import com.allianceever.projectERP.repository.LeavesRepo;
import com.allianceever.projectERP.service.HolidayService;
import com.allianceever.projectERP.service.LeaveTypeService;
import com.allianceever.projectERP.service.LeavesService;
import com.allianceever.projectERP.service.implementation.HolidayServiceImpl;
import com.allianceever.projectERP.service.implementation.LeaveTypeServiceImpl;
import com.allianceever.projectERP.service.implementation.LeavesServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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