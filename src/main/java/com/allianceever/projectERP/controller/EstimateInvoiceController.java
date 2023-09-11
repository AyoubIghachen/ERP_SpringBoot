package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EstimatesInvoicesDto;
import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.model.dto.ItemDto;
import com.allianceever.projectERP.model.dto.LeavesDto;
import com.allianceever.projectERP.model.entity.Item;
import com.allianceever.projectERP.repository.ItemRepo;
import com.allianceever.projectERP.service.EstimatesInvoicesService;
import com.allianceever.projectERP.service.HolidayService;
import com.allianceever.projectERP.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.allianceever.projectERP.controller.EmployeeController.getStrings;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/createEstimateInvoice")
@ComponentScan(basePackages = "com.allianceever.projectERP")
@AllArgsConstructor // Add this annotation to generate a constructor with all required dependencies
public class EstimateInvoiceController {

    private EstimatesInvoicesService estimatesInvoicesService;
    private ItemService itemService;


    @PostMapping("/create")
    public ModelAndView createEstimatesInvoices(@RequestBody EstimatesInvoicesDto estimatesInvoicesDto){
        EstimatesInvoicesDto createdEstimatesInvoices = estimatesInvoicesService.create(estimatesInvoicesDto);


        // Create and save ItemDto entities
        for (ItemDto itemDto : estimatesInvoicesDto.getItems()) {
           itemDto.setEstimateInvoices(createdEstimatesInvoices); // Establish the relationship
            ItemDto createdItem = itemService.create(itemDto);
        }


        return new ModelAndView("redirect:/estimates.html");
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewEstimatesInvoices(@PathVariable("id")  Integer id ,RedirectAttributes redirectAttributes){
        EstimatesInvoicesDto estimatesInvoicesDto = estimatesInvoicesService.getById(id);

        String type=estimatesInvoicesDto.getType();
        if("estimate".equals(type)){
            redirectAttributes.addFlashAttribute("estimate", estimatesInvoicesDto);
            return new ModelAndView("redirect:/estimate-view.html");
        }else{
            redirectAttributes.addFlashAttribute("estimate", estimatesInvoicesDto);
            return new ModelAndView("redirect:/invoice-view.html");
        }


    }


    // Build Delete Employee REST API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id")  Integer id){
        EstimatesInvoicesDto estimatesInvoicesDto = estimatesInvoicesService.getById(id);

        // if (employeeDto != null) {
        //   employeeService.delete(employeeID);
        //   return ResponseEntity.ok("Employee deleted successfully!");
        //  } else {
        //    return ResponseEntity.notFound().build();
        //  }

        estimatesInvoicesService.delete(id);
        return ResponseEntity.ok("Estimate/invoice deleted successfully!");
    }


    @GetMapping("/edit/{id}") // Keep this mapping as it is
    public ModelAndView editEstimate(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        EstimatesInvoicesDto estimatesInvoicesDto = estimatesInvoicesService.getById(id);
        String type=estimatesInvoicesDto.getType();

        if("estimate".equals(type)){
            redirectAttributes.addFlashAttribute("estimate", estimatesInvoicesDto);
            return  new ModelAndView("redirect:/edit-estimate.html");  // Directly return the template name
        }else{

            redirectAttributes.addFlashAttribute("estimate", estimatesInvoicesDto);
            return  new ModelAndView("redirect:/edit-invoice.html");  // Directly return the template name
        }

    }



    @PutMapping("/updateEstimatesInvoices")
    public ResponseEntity<EstimatesInvoicesDto> updateLeaves(@RequestBody EstimatesInvoicesDto estimatesInvoicesDto){
        Integer id = estimatesInvoicesDto.getId();
        EstimatesInvoicesDto existingEstimatesInvoices = estimatesInvoicesService.getById(id);
        if (existingEstimatesInvoices == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingEmployee using the employeeDto data
        BeanUtils.copyProperties(estimatesInvoicesDto, existingEstimatesInvoices, getNullPropertyNames(estimatesInvoicesDto));

        // Save the updated employee data back to the database
        EstimatesInvoicesDto updatedEstimatesInvoices = estimatesInvoicesService.update(id,existingEstimatesInvoices);
        return ResponseEntity.ok(updatedEstimatesInvoices);
    }



    public static String[] getNullPropertyNames(Object source) {
        return getStrings(source);
    }




}
