package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.EstimatesInvoicesDto;
import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.model.dto.ItemDto;
import com.allianceever.projectERP.model.entity.EstimatesInvoices;
import com.allianceever.projectERP.model.entity.Holiday;
import com.allianceever.projectERP.model.entity.Item;
import com.allianceever.projectERP.repository.EstimatesInvoicesRepo;
import com.allianceever.projectERP.service.EstimatesInvoicesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstimatesInvoicesServiceImpl implements EstimatesInvoicesService {

    private ModelMapper mapper;

    private EstimatesInvoicesRepo estimatesInvoicesRepo;



    @Autowired
    public EstimatesInvoicesServiceImpl(ModelMapper mapper, EstimatesInvoicesRepo estimatesInvoicesRepo) {
        this.mapper = mapper;
        this.estimatesInvoicesRepo = estimatesInvoicesRepo;
    }

    @Override
    public EstimatesInvoicesDto create(EstimatesInvoicesDto estimatesInvoicesDto) {
        // convert DTO to entity
        EstimatesInvoices estimatesInvoices = mapper.map(estimatesInvoicesDto, EstimatesInvoices.class);

        EstimatesInvoices newEstimatesInvoices = estimatesInvoicesRepo.save(estimatesInvoices);

        // convert entity to DTO
        return mapper.map(newEstimatesInvoices, EstimatesInvoicesDto.class);



    }

    @Override
    public EstimatesInvoicesDto getById(Integer id) {
        EstimatesInvoices estimatesInvoices = estimatesInvoicesRepo.findById(id);
        return mapper.map(estimatesInvoices, EstimatesInvoicesDto.class);

    }

    @Override
    public EstimatesInvoicesDto update(Integer id, EstimatesInvoicesDto estimatesInvoicesDto) {
        // Find the existing holiday entity by name
        Optional<EstimatesInvoices> estimatesInvoicesOptional = Optional.ofNullable(estimatesInvoicesRepo.findById(id));

        EstimatesInvoices existingEstimatesInvoices = estimatesInvoicesOptional.orElseThrow(() ->
                new ResourceNotFoundException("Estimates / Invoices does not exist with the given if: " + id)
        );

        // Update the fields of existingHoliday with the corresponding fields from holidayDto
        existingEstimatesInvoices.setEstimateDate(estimatesInvoicesDto.getEstimateDate());
        existingEstimatesInvoices.setCreateDate(estimatesInvoicesDto.getCreateDate());
        existingEstimatesInvoices.setExpiryDate(estimatesInvoicesDto.getExpiryDate());
        existingEstimatesInvoices.setOtherInfo(estimatesInvoicesDto.getOtherInfo());
        existingEstimatesInvoices.setStatus(estimatesInvoicesDto.getStatus());
        existingEstimatesInvoices.setProjectID(estimatesInvoicesDto.getProjectID());
        existingEstimatesInvoices.setClientID(estimatesInvoicesDto.getClientID());
        existingEstimatesInvoices.setTotal(estimatesInvoicesDto.getTotal());
        existingEstimatesInvoices.setTax(estimatesInvoicesDto.getTax());


        List<ItemDto> itemDtoList = estimatesInvoicesDto.getItems();
        List<Item> itemList = new ArrayList<>();

        for (ItemDto itemDto : itemDtoList) {
            Item item = new Item();
            // Map properties from itemDto to item
            item.setName(itemDto.getName());
            item.setAmount(itemDto.getAmount());
            item.setDescription(itemDto.getDescription());
            item.setQuantity(itemDto.getQuantity());
            item.setUniteCost((itemDto.getUniteCost()));

            // ... other mappings

            itemList.add(item);
        }

        existingEstimatesInvoices.setItems(itemList);



        // Save the updated entity back to the database
        EstimatesInvoices updatedEstimatesInvoices = estimatesInvoicesRepo.save(existingEstimatesInvoices);

        // Convert the updated entity to DTO and return it
        return mapper.map(updatedEstimatesInvoices, EstimatesInvoicesDto.class);
    }

    @Override
    public List<EstimatesInvoicesDto> getAllEstimates() {


        List<EstimatesInvoices> estimatesInvoices = estimatesInvoicesRepo.findAll();

        return estimatesInvoices.stream()
                .filter(estimatesInvoice -> "estimate".equals(estimatesInvoice.getType()))
                .map(estimatesInvoice -> mapper.map(estimatesInvoice, EstimatesInvoicesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EstimatesInvoicesDto> getAllInvoices() {


        List<EstimatesInvoices> estimatesInvoices = estimatesInvoicesRepo.findAll();

        return estimatesInvoices.stream()
                .filter(estimatesInvoice -> "invoice".equals(estimatesInvoice.getType()))
                .map(estimatesInvoice -> mapper.map(estimatesInvoice, EstimatesInvoicesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer id) {

        Optional<EstimatesInvoices> estimatesInvoicesOptional = Optional.ofNullable(estimatesInvoicesRepo.findById(id));

        if (estimatesInvoicesOptional.isPresent()) {
            EstimatesInvoices estimatesInvoices = estimatesInvoicesOptional.get();
            estimatesInvoicesRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("invoice / estimate is not exist with given id: " + id);
        }

    }




}
