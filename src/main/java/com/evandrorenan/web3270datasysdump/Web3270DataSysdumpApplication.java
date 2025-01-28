package com.evandrorenan.web3270datasysdump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Web3270 Data Sysdump system.
 * This Spring Boot application provides a web interface for processing, storing,
 * and analyzing system dumps from IBM 3270 terminals/emulators. It integrates with
 * MongoDB for data persistence and Azure Blob Storage for file storage capabilities.
 *
 * The application enables users to:
 * - Upload and process 3270 terminal system dumps
 * - Store dump data for analysis and reference
 * - Access and manage stored dump information through a web interface
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Web3270DataSysdumpApplication {

	public static void main(String[] args) {
		SpringApplication.run(Web3270DataSysdumpApplication.class, args);
	}

}
