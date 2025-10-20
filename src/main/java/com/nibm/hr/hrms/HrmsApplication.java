package com.nibm.hr.hrms;

// --- ADD THESE IMPORTS ---
import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.repository.EmployeeRepository;
import java.time.LocalDate;
// --- END IMPORTS ---

import com.nibm.hr.hrms.model.Role;
import com.nibm.hr.hrms.model.User;
import com.nibm.hr.hrms.repository.RoleRepository;
import com.nibm.hr.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class HrmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmsApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This bean will run on application startup
    @Bean
    public CommandLineRunner initialData(RoleRepository roleRepository,
                                         UserRepository userRepository,
                                         EmployeeRepository employeeRepository) { // <-- ADDED EmployeeRepository
        return args -> {

            // --- Create Roles ---
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            Role hrRole = roleRepository.findByName("ROLE_HR");
            if (hrRole == null) {
                hrRole = new Role();
                hrRole.setName("ROLE_HR");
                roleRepository.save(hrRole);
            }

            Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE");
            if (employeeRole == null) {
                employeeRole = new Role();
                employeeRole.setName("ROLE_EMPLOYEE");
                roleRepository.save(employeeRole);
            }

            // --- Create Admin User ---
            if (userRepository.findByUsername("admin") == null) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
                adminUser.setRoles(Set.of(adminRole));
                userRepository.save(adminUser);

                // --- THIS IS THE FIX: Create matching Employee record ---
                // We use "admin" as the email to link to the user "admin"
                Employee adminEmployee = new Employee("Admin", "User", "admin", "System Admin", "IT", LocalDate.now());
                employeeRepository.save(adminEmployee);
                // --- END FIX ---
            }

            // --- Create HR User ---
            if (userRepository.findByUsername("hr_manager") == null) {
                User hrUser = new User();
                hrUser.setUsername("hr_manager");
                hrUser.setPassword(passwordEncoder.encode("hr123"));
                hrUser.setRoles(Set.of(hrRole));
                userRepository.save(hrUser);

                // --- THIS IS THE FIX: Create matching Employee record ---
                // We use "hr_manager" as the email to link to the user "hr_manager"
                Employee hrEmployee = new Employee("HR", "Manager", "hr_manager", "HR Manager", "Human Resources", LocalDate.now());
                employeeRepository.save(hrEmployee);
                // --- END FIX ---
            }

            // --- Create Regular Employee User ---
            if (userRepository.findByUsername("employee1") == null) {
                User empUser = new User();
                empUser.setUsername("employee1");
                empUser.setPassword(passwordEncoder.encode("emp123"));
                empUser.setRoles(Set.of(employeeRole));
                userRepository.save(empUser);

                // --- THIS IS THE FIX: Create matching Employee record ---
                // We use "employee1" as the email to link to the user "employee1"
                Employee empEmployee = new Employee("Demo", "Employee", "employee1", "Software Engineer", "Engineering", LocalDate.now());
                employeeRepository.save(empEmployee);
                // --- END FIX ---
            }
        };
    }
}