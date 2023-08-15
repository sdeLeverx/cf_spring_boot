package uz.jamshid.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import uz.jamshid.java.model.Student;
import uz.jamshid.java.payload.StudentDto;
import uz.jamshid.java.repository.StudentRepository;

@Component
@RequestScope
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentRepository studentRepository;

    @GetMapping()
    public HttpEntity<?> getStudents() {
        return ResponseEntity.status(200).body(studentRepository.findAll());
    }

    @PostMapping()
    public HttpEntity<?> addStudents(@RequestBody StudentDto studentDto) {
        Student savedStudent = studentRepository.save(Student.builder()
                .name(studentDto.getName())
                .build());

        return ResponseEntity.status(200).body(savedStudent);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteStudents(@PathVariable Integer id) {
        studentRepository.deleteById(id);
        return ResponseEntity.status(200).body("Student deleted");
    }
}
