package auca.AiGrading.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.AiGrading.repository.CourseRepository;
import auca.AiGrading.model.Course;

@Service 
public class CourseService {

    @Autowired 
    private CourseRepository courseRepo;

    public String save(Course course) {
        courseRepo.save(course);
        return "Course saved successfully";
    }

    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    public Optional<Course> getById(UUID id) {
        return courseRepo.findById(id);
    }

    public String update(UUID id, Course course) {
        Optional<Course> found = courseRepo.findById(id);
        if (found.isEmpty()) {
            return "Course with that id does not exist";
        }
        Course existing = found.get();
        existing.setCourseNumber(course.getCourseNumber());
        existing.setName(course.getName());
        courseRepo.save(existing);
        return "Course updated successfully";
    }

    public String delete(UUID id) {
        if (!courseRepo.existsById(id)) {
            return "Course with that id does not exist";
        }
        courseRepo.deleteById(id);
        return "Course deleted successfully";
    }

    public List<Course> getFreeCourses() {
        return courseRepo.findFreeCourses();
    }
}
