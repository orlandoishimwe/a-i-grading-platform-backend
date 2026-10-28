package auca.AiGrading.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity 
@Table (name = "discipline")
public class Discipline {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany (mappedBy = "disciplines")
    @JsonIgnore 
    private List<Examiner> examiners = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Examiner> getExaminers() {
        return examiners;
    }

    public void setExaminers(List<Examiner> examiners) {
        this.examiners = examiners;
    }
}
