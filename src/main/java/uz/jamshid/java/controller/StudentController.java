package uz.jamshid.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jamshid.java.repository.StudentRepository;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository studentRepository;

    @GetMapping("/student")
    public HttpEntity<?> getStudents() {
        return ResponseEntity.status(200).body(studentRepository.findAll());
    }
}
