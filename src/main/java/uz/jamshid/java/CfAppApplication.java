package uz.jamshid.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CfAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfAppApplication.class, args);
		System.out.println("Hello Jamshid!");
	}

}
