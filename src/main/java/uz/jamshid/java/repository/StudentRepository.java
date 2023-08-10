package uz.jamshid.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jamshid.java.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
