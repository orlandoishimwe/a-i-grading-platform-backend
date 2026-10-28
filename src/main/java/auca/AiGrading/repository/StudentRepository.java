package auca.AiGrading.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.AiGrading.model.Student;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT st FROM Submission s JOIN s.student st " +
        "WHERE s.examiner.id = :examinerId")
    List<Student> findStudentsByExaminerId(@Param("examinerId") UUID examinerId);
}
