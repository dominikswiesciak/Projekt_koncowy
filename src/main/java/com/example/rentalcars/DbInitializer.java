package com.example.rentalcars;

import com.example.rentalcars.enums.*;
import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.*;
import com.example.rentalcars.service.DepartmentService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DbInitializer implements ApplicationRunner {

    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ReturnRepository returnRepository;
    private final RentalRepository rentalRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    private final DepartmentService departmentService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public DbInitializer(CarRepository carRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ReturnRepository returnRepository, RentalRepository rentalRepository, ReservationRepository reservationRepository, CustomerRepository customerRepository, CompanyRepository companyRepository, DepartmentService departmentService, UserRepository userRepository, RoleRepository roleRepository) {
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.returnRepository = returnRepository;
        this.rentalRepository = rentalRepository;
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.departmentService = departmentService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var dep1 = departmentRepository.save((new DepartmentModel(1l, "Lublin")));
        var dep2 = departmentRepository.save((new DepartmentModel(2l, "Kraków")));
        var dep3 = departmentRepository.save((new DepartmentModel(3l, "Katowice")));
        var dep4 = departmentRepository.save((new DepartmentModel(4l, "Warszawa")));

        var car1 = carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.valueOf(1111), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004));
        var car2 = carRepository.save(new CarModel(2l, "Opel", "Astra", BigDecimal.valueOf(456), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        var car3 = carRepository.save(new CarModel(3l, "BMW", "E3", BigDecimal.valueOf(124), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        var car4 = carRepository.save(new CarModel(4l, "AUDI", "80", BigDecimal.valueOf(123), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));

        departmentService.addCarToDepartment(4l,1l);
        departmentService.addCarToDepartment(3l,2l);
        departmentService.addCarToDepartment(2l,3l);
        departmentService.addCarToDepartment(1l,1l);
        departmentService.addCarToDepartment(5l,1l);

        var role1 = roleRepository.save(new RoleModel(1l, "ADMIN"));
        var role2 = roleRepository.save(new RoleModel(2l, "USER"));

        var admin = userRepository.save(new UserModel(1L, "admin","admin", "admin@co_ja_nie_potrafie.pl", true, role1));
        var user1 = userRepository.save(new UserModel(2L, "kowalski","1234", "kowalski@rentalcars.pl", true, role1));
        var user2 = userRepository.save(new UserModel(3L, "nowak","1234", "nowak@rentalcars.pl", true, role1));

        var employee1 = employeeRepository.save(new EmployeeModel(1l, "Jan", "Kowalski", EmployeePosition.EMPLOYEE));
        var employee2 = employeeRepository.save(new EmployeeModel(2l, "Karol", "Nowak", EmployeePosition.MANAGER));

        companyRepository.save(new CompanyModel(1l, "Gruz-rental", "", "", "", ""));

    }
}
