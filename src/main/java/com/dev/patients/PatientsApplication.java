package com.dev.patients;

import com.dev.patients.entities.Patient;
import com.dev.patients.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsApplication {



    public static void main(String[] args) {
        SpringApplication.run(PatientsApplication.class, args);
    }

    // @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return  args -> {
            patientRepository.save(new Patient(null, "Ali", new Date(), true, 10));
            patientRepository.save(new Patient(null, "Ahmed", new Date(), false, 15));
            patientRepository.save(new Patient(null, "Mohamed", new Date(), true, 10));
            patientRepository.save(new Patient(null, "Marwan", new Date(), false, 15));
        };
    }

}
