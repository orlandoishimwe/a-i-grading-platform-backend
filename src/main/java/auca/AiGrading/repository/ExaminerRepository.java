package auca.AiGrading.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.AiGrading.model.Course;
import auca.AiGrading.model.Examiner;

public interface ExaminerRepository extends JpaRepository<Examiner, UUID> {
    @Query("SELECT DISTINCT e FROM Examiner e JOIN e.disciplines d " +
        "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<Examiner> findByDisciplineName(@Param("name") String name);

    boolean existsByCourse(Course course);
}
