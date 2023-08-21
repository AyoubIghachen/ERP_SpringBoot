package com.allianceever.projectERP.service;


import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.model.dto.ItemDto;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    ItemDto create(ItemDto itemDto);

}
