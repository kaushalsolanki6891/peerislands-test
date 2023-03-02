package com.peerislands;

import com.peerislands.dto.EmployeeDTO;
import com.peerislands.dto.HobbyDTO;
import com.peerislands.repository.EmployeeRepository;
import com.peerislands.repository.impl.ExtendedRepositoryImpl;
import com.peerislands.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.peerislands.repository",
        repositoryBaseClass = ExtendedRepositoryImpl.class)
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Bean
    public CommandLineRunner demo(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        return (args) -> {

            System.out.println("Data Loading start");

            HobbyDTO hobbyDTO1 = buildHobby("Chess");
            HobbyDTO hobbyDTO2 = buildHobby("Cooking");
            HobbyDTO hobbyDTO3 = buildHobby("Football");
            HobbyDTO hobbyDTO4 = buildHobby("Tennis");
            HobbyDTO hobbyDTO5 = buildHobby("Blogging");
            HobbyDTO hobbyDTO6 = buildHobby("Playing");
            HobbyDTO hobbyDTO7 = buildHobby("Singing");
            HobbyDTO hobbyDTO8 = buildHobby("Dancing");
            HobbyDTO hobbyDTO9 = buildHobby("Study");
            HobbyDTO hobbyDTO10 = buildHobby("Reading");
            HobbyDTO hobbyDTO11 = buildHobby("Writing");


            Set<HobbyDTO> hobbies1 = new HashSet<>();
            hobbies1.add(hobbyDTO1);
            hobbies1.add(hobbyDTO2);

            Set<HobbyDTO> hobbies2 = new HashSet<>();
            hobbies2.add(hobbyDTO3);
            hobbies2.add(hobbyDTO4);

            Set<HobbyDTO> hobbies3 = new HashSet<>();
            hobbies3.add(hobbyDTO5);
            hobbies3.add(hobbyDTO6);

            Set<HobbyDTO> hobbies4 = new HashSet<>();
            hobbies4.add(hobbyDTO7);
            hobbies4.add(hobbyDTO8);

            Set<HobbyDTO> hobbies5 = new HashSet<>();
            hobbies5.add(hobbyDTO9);
            hobbies5.add(hobbyDTO10);
            hobbies5.add(hobbyDTO11);

            EmployeeDTO emp1 = buildEmployee("Kaushal", "Solanki",
                    "k.b.solanki49@gmaiil.com", 31, hobbies1);
            EmployeeDTO emp2 = buildEmployee("Rajesh", "vinayagam",
                    "rajesh.vinayagam@peerislands.io", 35, hobbies2);
            EmployeeDTO emp3 = buildEmployee("first3", "last3",
                    "first3.last3@peerislands.io", 37, hobbies3);
            EmployeeDTO emp4 = buildEmployee("first4", "last4",
                    "first4.last4@peerislands.io", 24, hobbies4);
            EmployeeDTO emp5 = buildEmployee("first5", "last5",
                    "first4.last5@peerislands.io", 40, hobbies5);

            employeeService.save(emp1);
            employeeService.save(emp2);
            employeeService.save(emp3);
            employeeService.save(emp4);
            employeeService.save(emp5);

            for (com.peerislands.model.Employee emp : employeeRepository.findAll()) {
                System.out.println(emp);
            }
            System.out.println("Data Loading Finish");
        };
    }


    private HobbyDTO buildHobby(String hobby) {
        return HobbyDTO.builder()
                .hobby(hobby)
                .build();
    }

    private EmployeeDTO buildEmployee(String firstName, String lastName, String email, Integer age, Set<HobbyDTO> hobbies) {
        return EmployeeDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .hobbies(hobbies)
                .build();
    }

}
