package auca.AiGrading.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.AiGrading.model.Course;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query("SELECT c FROM Course c WHERE NOT EXISTS (SELECT e FROM Examiner e WHERE Course.e = c) " +
        "ORDER BY c.courseNumber")
    List<Course> findFreeCourses();
}
