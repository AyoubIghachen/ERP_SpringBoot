package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.AuthenticatedBackend.utils.RSAKeyProperties;
import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.dto.LeavesDto;
import com.allianceever.projectERP.service.EmployeeService;
import com.allianceever.projectERP.service.LeavesService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.allianceever.projectERP.controller.EmployeeController.getStrings;

@RestController
@RequestMapping("/leaves")
@ComponentScan(basePackages = "com.allianceever.projectERP")
@AllArgsConstructor
public class LeavesController {
    private LeavesService leavesService;
    private EmployeeService employeeService;
    private final RSAKeyProperties rsaKeyProperties;

    @PostMapping("/create")
    public ModelAndView createLeaves(@ModelAttribute() LeavesDto leavesDto ,@RequestParam("activePage") String activePage, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
        String username = "";
        String role = "";
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(rsaKeyProperties.getPublicKey()) // Use the public key for verification
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // Retrieve username and role from the jwt
            username = (String) claims.get("sub");
            role = (String) claims.get("roles");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            if(role.equals("ADMIN")){
                leavesDto.setUsername(username);
                leavesDto.setEmployeeName(username);
            }else {
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeName = employeeDto.getFirst_Name() + " " + employeeDto.getLast_Name();
                leavesDto.setUsername(username);
                leavesDto.setEmployeeName(employeeName);
            }
            leavesService.create(leavesDto);

            String redirectPage="redirect:/leaves-employee.html";
            if ("leaves".equals(activePage)) {
                redirectPage = "redirect:/leaves.html";
            } else if ("leaves-employee".equals(activePage)) {
                redirectPage = "redirect:/leaves-employee.html";
            }
            return new ModelAndView(redirectPage);
        }else{
            return new ModelAndView("redirect:/login.html");
        }
    }

    @GetMapping("/{Leaves}")
    public ResponseEntity<LeavesDto> getLeavesByLeavesID(@PathVariable("Leaves") Integer LeavesID, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
        String username = "";
        String role = "";
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(rsaKeyProperties.getPublicKey()) // Use the public key for verification
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // Retrieve username and role from the jwt
            username = (String) claims.get("sub");
            role = (String) claims.get("roles");

        } catch (Exception e) {
            e.printStackTrace();
        }

        LeavesDto leavesDto = leavesService.getByLeavesID(LeavesID);
        if (leavesDto != null) {
            if(!role.equals("")){
                if(leavesDto.getUsername().equals(username) || role.equals("ADMIN") || role.equals("Human_Capital")){
                    return ResponseEntity.ok(leavesDto);
                }else {
                    return ResponseEntity.notFound().build();
                }
            }else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/updateLeaves")
    public ResponseEntity<LeavesDto> updateLeaves(@ModelAttribute LeavesDto leavesDto, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        Integer LeavesID = leavesDto.getLeavesID();
        LeavesDto existingLeaves = leavesService.getByLeavesID(LeavesID);
        if (existingLeaves == null) {
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(leavesDto, existingLeaves, getNullPropertyNames(leavesDto));

        if(leavesDto.getStatus() != null){
            if(!leavesDto.equals("")){
                if(role.equals("ADMIN") || role.equals("Human_Capital")){
                    String approvedBy;
                    if(role.equals("Human_Capital")){
                        EmployeeDto employeeDto = employeeService.getByUsername(username);
                        approvedBy = employeeDto.getFirst_Name() + " " + employeeDto.getLast_Name();
                    }else {
                        approvedBy = "ADMIN";
                    }
                    existingLeaves.setApprovedBy(approvedBy);
                    LeavesDto updatedLeaves = leavesService.update(LeavesID,existingLeaves);
                    return ResponseEntity.ok(updatedLeaves);
                }else {
                    return ResponseEntity.notFound().build();
                }
            }else{
                if(existingLeaves.getUsername().equals(username) || role.equals("ADMIN") || role.equals("Human_Capital")){
                    LeavesDto updatedLeaves = leavesService.update(LeavesID,existingLeaves);
                    return ResponseEntity.ok(updatedLeaves);
                }
                return ResponseEntity.notFound().build();
            }
        }else {
            if(existingLeaves.getUsername().equals(username) || role.equals("ADMIN") || role.equals("Human_Capital")){
                LeavesDto updatedLeaves = leavesService.update(LeavesID,existingLeaves);
                return ResponseEntity.ok(updatedLeaves);
            }
            return ResponseEntity.notFound().build();
        }
    }


    // Build Delete Employee REST API
    @DeleteMapping("/delete/{LeavesID}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("LeavesID")  Integer LeavesID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        LeavesDto leavesDto = leavesService.getByLeavesID(LeavesID);

        if(leavesDto.getUsername().equals(username) || role.equals("ADMIN") || role.equals("Human_Capital")){
            leavesService.delete(LeavesID);
            return ResponseEntity.ok("Leave deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }


    public static String[] getNullPropertyNames(Object source) {
        return getStrings(source);
    }
}
