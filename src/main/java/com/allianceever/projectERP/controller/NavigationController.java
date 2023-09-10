package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.entity.Expenses;
import com.allianceever.projectERP.model.entity.Leaves;
import com.allianceever.projectERP.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@CrossOrigin("*")

public class NavigationController {
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



    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/index.html")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/login.html")
    public String getLogin(){
        return "login";
    }


    //**** Part Ayoub Ighachen:
    //
    @GetMapping("/employees.html")
    public String ListEmployees(Model model){
        model.addAttribute("employees", employeeService.getAll());
        return "employees";
    }

    @RequestMapping("/profile.html")
    public String Profile(){
        return "profile";
    }

    @GetMapping("/profile.html/{id}")
    public String getProfile(@PathVariable("id") Long employeeID, Model model){
        model.addAttribute("employee", employeeService.getById(employeeID));
        return "profile";
    }

    //
    @GetMapping("/departments.html")
    public String ListDepartments(Model model){
        model.addAttribute("departments", departmentService.getAll());
        return "departments";
    }

    //
    @GetMapping("/designations.html")
    public String ListDesignations(Model model){
        model.addAttribute("designations", designationService.getAll());
        return "designations";
    }

    //
    @GetMapping("/clients.html")
    public String ListClients(Model model){
        model.addAttribute("clients", clientService.getAll());
        return "clients";
    }

    @RequestMapping("/client-profile.html")
    public String ClientProfile(){
        return "client-profile";
    }

    @GetMapping("/client-profile.html/{id}")
    public String getClientProfile(@PathVariable("id") Long clientID, Model model){
        model.addAttribute("client", clientService.getById(clientID));
        return "client-profile";
    }

    //
    @GetMapping("/projects.html")
    public String ListProjects(Model model){
        model.addAttribute("projects", projectService.getAll());
        return "projects";
    }

    @RequestMapping("/project-view.html")
    public String ViewProject(){
        return "project-view";
    }

    @GetMapping("/project-view.html/{id}")
    public String getViewProject(@PathVariable("id") Long projectID, Model model){
        model.addAttribute("project", projectService.getById(projectID));
        model.addAttribute("imageProjects", imageProjectService.findAll(String.valueOf(projectID)));
        model.addAttribute("fileProjects", fileProjectService.findAll(String.valueOf(projectID)));
        model.addAttribute("employeeProjects", employeeProjectService.findAll(String.valueOf(projectID)));
        model.addAttribute("leaderProjects", leaderProjectService.findAll(String.valueOf(projectID)));
        model.addAttribute("tasks", taskService.findAll(String.valueOf(projectID)));
        return "project-view";
    }

    @RequestMapping("/tasks.html")
    public String Tasks(){
        return "tasks";
    }

    @GetMapping("/tasks.html/{id}")
    public String getTasks(@PathVariable("id") Long projectID, Model model){
        model.addAttribute("projects", projectService.getAll());
        model.addAttribute("projectTasks", projectService.getById(projectID));
        model.addAttribute("tasks", taskService.findAll(String.valueOf(projectID)));
        return "tasks";
    }

    //************************




    @GetMapping("/holidays.html")
    public String ListHolidays(Model model){
        model.addAttribute("holidays", holidayService.getAllHolidaysOrderedByDate());
        return "holidays";
    }


    @GetMapping("/leave-type.html")
    public String ListLeaveTypes(Model model){
        model.addAttribute("leaveTypes", leaveTypeService.getAllLeaveType());
        return "leave-type";
    }


    @GetMapping("/leaves-employee.html")
    public String ListLeaves(Model model){

        model.addAttribute("leaves", leavesService.getAllLeavesOrderedByDate());
        Leaves leave = new Leaves();
        model.addAttribute("oneLeave", leave);

        model.addAttribute("leavesTypes",leaveTypeService.getAllLeaveType());

        return "leaves-employee";

    }


    @GetMapping("/leaves.html")
    public String Leaves(Model model){

        model.addAttribute("leaves", leavesService.getAllLeavesOrderedByDate());
        Leaves leave = new Leaves();
        model.addAttribute("oneLeave", leave);

        model.addAttribute("leavesTypes",leaveTypeService.getAllLeaveType());

        return "leaves";

    }

    @GetMapping("/create-estimate.html")
    public String create_estimate(Model model){

        return "create-estimate";

    }

    @GetMapping("/estimates.html")
    public String estimates(Model model){
        model.addAttribute("estimates", estimatesInvoicesService.getAllEstimates());

        return "estimates";

    }



    @GetMapping("/edit-estimate.html")
    public String editEstimates(Model model){
        return "edit-estimate";
    }


    @GetMapping("/estimate-view.html")
    public String viewEstimates(Model model){
        return "estimate-view";
    }
    ////////////////
    @GetMapping("/create-invoice.html")
    public String create_invoice(Model model){

        return "create-invoice";

    }

    @GetMapping("/invoices.html")
    public String invoices(Model model){
        model.addAttribute("estimates", estimatesInvoicesService.getAllInvoices());
        return "invoices";

    }

    @GetMapping("/edit-invoice.html")
    public String editInvoices(Model model){
        return "edit-invoice";
    }

    @GetMapping("/invoice-view.html")
    public String viewInvoices(Model model){
        return "invoice-view";
    }


    ////////////payment

    @GetMapping("/payments.html")
    public String payments(Model model){

        model.addAttribute("payments", paymentService.getAll());

        return "payments";
    }

    ///////Expenses

    @GetMapping("/expenses.html")
    public String expenses(Model model){
        Expenses expense = new Expenses();
        model.addAttribute("oneExpense", expense);
        model.addAttribute("expenses", expensesService.getAllExpensesOrderedByDate());

        return "expenses";
    }



}
