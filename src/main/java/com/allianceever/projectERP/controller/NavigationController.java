package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.AuthenticatedBackend.utils.RSAKeyProperties;
import com.allianceever.projectERP.model.dto.*;
import com.allianceever.projectERP.model.entity.Employee;
import com.allianceever.projectERP.model.entity.Expenses;
import com.allianceever.projectERP.model.entity.Leaves;
import com.allianceever.projectERP.service.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor

public class NavigationController {
    private final RSAKeyProperties rsaKeyProperties;

    private EmployeeService employeeService;
    private HolidayService holidayService;
    private LeavesService leavesService;
    private LeaveTypeService leaveTypeService;
    private EstimatesInvoicesService estimatesInvoicesService;
    private PaymentService paymentService;
    private ExpensesService expensesService;

    private DepartmentService departmentService;
    private DesignationService designationService;
    private ClientService clientService;
    private ProjectService projectService;
    private ImageProjectService imageProjectService;
    private FileProjectService fileProjectService;
    private EmployeeProjectService employeeProjectService;
    private LeaderProjectService leaderProjectService;
    private TaskService taskService;


    @RequestMapping(value = {"/index.html", "/"})
    public String getIndex(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            if(role.equals("ADMIN")){
                // nombre des employees
                Integer nombre_employees = employeeService.getAll().size();
                model.addAttribute("nombre_employees", nombre_employees);

                // nombre des tasks
                Integer nombre_tasks = taskService.getAll().size();
                model.addAttribute("nombre_tasks", nombre_tasks);

                // 5 dernier Projects + nombre des projects
                List<ProjectDto> originalList = projectService.getAll();
                List<ProjectDto> newList = originalList.subList(0, Math.min(originalList.size(), 5));
                model.addAttribute("projects", newList);
                model.addAttribute("nombre_projects", originalList.size());

                // 5 dernier clients + nombre des clients
                List<ClientDto> originalList2 = clientService.getAll();
                List<ClientDto> newList2 = originalList2.subList(0, Math.min(originalList2.size(), 5));
                model.addAttribute("clients", newList2);
                model.addAttribute("nombre_clients", originalList2.size());

                // 3 derniers payments
                List<PaymentDto> originalList3 = paymentService.getAll();
                List<PaymentDto> newList3 = originalList3.subList(0, Math.min(originalList3.size(), 3));
                model.addAttribute("payments", newList3);

                // 3 derniers invoices
                List<EstimatesInvoicesDto> originalList4 = estimatesInvoicesService.getAllInvoices();
                List<EstimatesInvoicesDto> newList4 = originalList4.subList(0, Math.min(originalList4.size(), 3));
                model.addAttribute("invoices", newList4);

                // Sum of expenses :
                List<ExpensesDto> originalList5 = expensesService.getAllExpensesOrderedByDate();
                double SumExpenses = 0;
                for(ExpensesDto expensesDto : originalList5){
                    SumExpenses = SumExpenses + Double.valueOf(expensesDto.getAmount());
                }
                model.addAttribute("SumExpenses", SumExpenses);

                // Earning = Sum payments
                double SumPayments = 0;
                for(PaymentDto paymentDto : originalList3){
                    SumPayments = SumPayments + paymentDto.getPaidAmount().doubleValue();
                }
                model.addAttribute("Earning", SumPayments);

                // Profit = Earning - Expenses
                double Profit = SumPayments - SumExpenses;
                model.addAttribute("Profit", Profit);
                

                return "index";
            }else{
                return "error-404";// 401 unauthorized
            }
        }else{
            return "login";
        }
    }

    @RequestMapping("/login.html")
    public String getLogin(){
        return "login";
    }

    @RequestMapping("/change-password.html")
    public String getChangePassword(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            return "change-password";
        }else{
            return "login";
        }
    }


    @RequestMapping("/error-404.html")
    public String getError404(){
        return "error-404";
    }


    //**** Part Ayoub Ighachen:
    //
    @GetMapping("/employees.html")
    public String ListEmployees(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            if(role.equals("ADMIN") || role.equals("Human_Capital")){
                model.addAttribute("employees", employeeService.getAll());
                return "employees";
            }else{
                return "error-404";// 401 unauthorized
            }
        }else{
            return "login";
        }
    }


    @GetMapping("/profile.html/{id}")
    public String getProfile(@PathVariable("id") Long employeeID, Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            EmployeeDto employeeDto = employeeService.getById(employeeID);
            if(employeeDto.getUserName().equals(username) || role.equals("ADMIN") || role.equals("Human_Capital")){
                model.addAttribute("employee", employeeService.getById(employeeID));
                //Return the projects assigned for this employee
                List<ProjectDto> projectDtoList = projectService.getAllByUsername(employeeDto.getUserName());
                for(ProjectDto projectDto : projectDtoList){
                    String projectID = String.valueOf(projectDto.getProjectID());
                    projectDto.setLeaderProjectDtoList(leaderProjectService.findAll(projectID));
                    projectDto.setEmployeeProjectDtoList(employeeProjectService.findAll(projectID));
                }
                model.addAttribute("projects", projectDtoList);

                return "profile";
            }else{
                return "error-404";// 401 unauthorized
            }
        }else{
            return "login";
        }
    }

    //
    @GetMapping("/departments.html")
    public String ListDepartments(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Human_Capital")){
            model.addAttribute("departments", departmentService.getAll());
            return "departments";
        }else {
            return "error-404";
        }
    }

    //
    @GetMapping("/designations.html")
    public String ListDesignations(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Human_Capital")){
            model.addAttribute("designations", designationService.getAll());
            return "designations";
        }else {
            return "error-404";
        }
    }

    //
    @GetMapping("/clients.html")
    public String ListClients(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            model.addAttribute("clients", clientService.getAll());
            return "clients";
        }else {
            return "error-404";
        }
    }

    @GetMapping("/client-profile.html/{id}")
    public String getClientProfile(@PathVariable("id") Long clientID, Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            model.addAttribute("client", clientService.getById(clientID));
            //Return the projects assigned for this client
            ClientDto clientDto = clientService.getById(clientID);
            List<ProjectDto> projectDtoList = projectService.getAllByCompany_Name(clientDto.getCompany_Name());
            for(ProjectDto projectDto : projectDtoList){
                String projectID = String.valueOf(projectDto.getProjectID());
                projectDto.setLeaderProjectDtoList(leaderProjectService.findAll(projectID));
                projectDto.setEmployeeProjectDtoList(employeeProjectService.findAll(projectID));
            }
            model.addAttribute("projects", projectDtoList);

            return "client-profile";
        }else {
            return "error-404";
        }
    }

    //
    @GetMapping("/projects.html")
    public String ListProjects(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                List<ProjectDto> projectDtoList = projectService.getAll();
                for(ProjectDto projectDto : projectDtoList){
                    String projectID = String.valueOf(projectDto.getProjectID());
                    projectDto.setLeaderProjectDtoList(leaderProjectService.findAll(projectID));
                    projectDto.setEmployeeProjectDtoList(employeeProjectService.findAll(projectID));
                }
                model.addAttribute("projects", projectDtoList);
            }else {
                //Return the projects assigned for this username like a Leader or an Employee
                List<ProjectDto> projectDtoList = projectService.getAllByUsername(username);
                for(ProjectDto projectDto : projectDtoList){
                    String projectID = String.valueOf(projectDto.getProjectID());
                    projectDto.setLeaderProjectDtoList(leaderProjectService.findAll(projectID));
                    projectDto.setEmployeeProjectDtoList(employeeProjectService.findAll(projectID));
                }
                model.addAttribute("projects", projectDtoList);
            }
            return "projects";
        }else{
            return "login";
        }
    }

    @GetMapping("/project-view.html/{id}")
    public String getViewProject(@PathVariable("id") Long projectID, Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                model.addAttribute("project", projectService.getById(projectID));
                model.addAttribute("imageProjects", imageProjectService.findAll(String.valueOf(projectID)));
                model.addAttribute("fileProjects", fileProjectService.findAll(String.valueOf(projectID)));
                model.addAttribute("employeeProjects", employeeProjectService.findAll(String.valueOf(projectID)));
                model.addAttribute("leaderProjects", leaderProjectService.findAll(String.valueOf(projectID)));
                model.addAttribute("tasks", taskService.findAll(String.valueOf(projectID)));
                return "project-view";
            }else {
                // We check if the project is affected for this username
                List<ProjectDto> projectDtoList = projectService.getAllByUsername(username);
                for(ProjectDto projectDto : projectDtoList){
                    if(projectDto.getProjectID() == projectID){
                        model.addAttribute("project", projectService.getById(projectID));
                        model.addAttribute("imageProjects", imageProjectService.findAll(String.valueOf(projectID)));
                        model.addAttribute("fileProjects", fileProjectService.findAll(String.valueOf(projectID)));
                        model.addAttribute("employeeProjects", employeeProjectService.findAll(String.valueOf(projectID)));
                        model.addAttribute("leaderProjects", leaderProjectService.findAll(String.valueOf(projectID)));
                        model.addAttribute("tasks", taskService.findAll(String.valueOf(projectID)));
                        return "project-view";
                    }
                }
                return "error-404";
            }
        }else{
            return "login";
        }
    }

    @GetMapping("/tasks.html/{id}")
    public String getTasks(@PathVariable("id") Long projectID, Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                model.addAttribute("projects", projectService.getAll());
                model.addAttribute("projectTasks", projectService.getById(projectID));
                model.addAttribute("tasks", taskService.findAll(String.valueOf(projectID)));
                return "tasks";
            }else {
                // We check if the project is affected for this username
                List<ProjectDto> projectDtoList = projectService.getAllByUsername(username);
                for(ProjectDto projectDto : projectDtoList){
                    if(projectDto.getProjectID() == projectID){
                        model.addAttribute("projects", projectDtoList);
                        model.addAttribute("projectTasks", projectService.getById(projectID));
                        model.addAttribute("tasks", taskService.findAll(String.valueOf(projectID)));
                        return "tasks";
                    }
                }
                return "error-404";
            }
        }else{
            return "login";
        }
    }

    //************************




    @GetMapping("/holidays.html")
    public String ListHolidays(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            model.addAttribute("holidays", holidayService.getAllHolidaysOrderedByDate());
            return "holidays";
        }else{
            return "login";
        }
    }


    @GetMapping("/leave-type.html")
    public String ListLeaveTypes(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!role.equals("")){
            if(role.equals("ADMIN") || role.equals("Human_Capital")){
                model.addAttribute("leaveTypes", leaveTypeService.getAllLeaveType());
            }else{
                model.addAttribute("leaveTypes", leaveTypeService.getAllLeaveTypeByUsername(username));
            }
            return "leave-type";
        }
        return "login";
    }


    @GetMapping("/leaves-employee.html")
    public String ListLeaves(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            model.addAttribute("leaves", leavesService.getAllLeavesByUsernameOrderedByDate(username));
            model.addAttribute("oneLeave", new Leaves());
            model.addAttribute("leavesTypes",leaveTypeService.getAllLeaveTypeByUsername(username));

            return "leaves-employee";
        }else{
            return "login";
        }
    }


    @GetMapping("/leaves.html")
    public String Leaves(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!role.equals("")){
            if(role.equals("ADMIN") || role.equals("Human_Capital")){
                model.addAttribute("leaves", leavesService.getAllLeavesOrderedByDate());
                model.addAttribute("oneLeave", new Leaves());
                model.addAttribute("leavesTypes",leaveTypeService.getAllLeaveTypeByUsername(username));

                return "leaves";
            }else{
                return "error-404";
            }
        }else{
            return "login";
        }
    }


    //////////////////////////////////////// Estimate

    @GetMapping("/estimates.html")
    public String estimates(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            model.addAttribute("estimates", estimatesInvoicesService.getAllEstimates());
            return "estimates";
        }else {
            return "error-404";
        }
    }

    @GetMapping("/create-estimate.html")
    public String create_estimate(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            return "create-estimate";
        }else {
            return "error-404";
        }
    }

    @GetMapping("/edit-estimate.html")
    public String editEstimates(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            return "edit-estimate";
        }else {
            return "error-404";
        }
    }

    @GetMapping("/estimate-view.html")
    public String viewEstimates(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            return "estimate-view";
        }else {
            return "error-404";
        }
    }

    ////////////////

    @GetMapping("/invoices.html")
    public String invoices(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            model.addAttribute("estimates", estimatesInvoicesService.getAllInvoices());
            return "invoices";
        }else {
            return "error-404";
        }
    }

    @GetMapping("/create-invoice.html")
    public String create_invoice(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            return "create-invoice";
        }else {
            return "error-404";
        }
    }

    @GetMapping("/edit-invoice.html")
    public String editInvoices(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            return "edit-invoice";
        }else {
            return "error-404";
        }
    }

    @GetMapping("/invoice-view.html")
    public String viewInvoices(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            return "invoice-view";
        }else {
            return "error-404";
        }
    }


    ////////////payment

    @GetMapping("/payments.html")
    public String payments(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            model.addAttribute("payments", paymentService.getAll());
            return "payments";
        }else {
            return "error-404";
        }
    }

    ///////Expenses

    @GetMapping("/expenses.html")
    public String expenses(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken){
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
            if(role.equals("ADMIN")){
                Employee user = new Employee();
                user.setFirst_Name("ADMIN");
                user.setImageName("admin.png");
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", employeeService.getByUsername(username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(role.equals("ADMIN") || role.equals("Marketing") || role.equals("Business_Development")){
            model.addAttribute("oneExpense", new Expenses());
            model.addAttribute("expenses", expensesService.getAllExpensesOrderedByDate());
            return "expenses";
        }else {
            return "error-404";
        }
    }

}
