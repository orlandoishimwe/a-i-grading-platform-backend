package auca.AiGrading.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.AiGrading.model.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {
    @Query("SELECT d FROM Discipline d WHERE d.examiners IS EMPTY")
    List<Discipline> findUnusedDisciplines();
}
