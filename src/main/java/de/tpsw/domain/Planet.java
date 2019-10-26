package de.tpsw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Planet.
 */
@Entity
@Table(name = "planet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Planet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "forest_points", nullable = false)
    private Long forestPoints;

    @NotNull
    @Column(name = "water_points", nullable = false)
    private Long waterPoints;

    @NotNull
    @Column(name = "smog_points", nullable = false)
    private Long smogPoints;

    @OneToMany(mappedBy = "planet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Animal> animals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "planet_achievement",
               joinColumns = @JoinColumn(name = "planet_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "achievement_id", referencedColumnName = "id"))
    private Set<Achievement> achievements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getForestPoints() {
        return forestPoints;
    }

    public Planet forestPoints(Long forestPoints) {
        this.forestPoints = forestPoints;
        return this;
    }

    public void setForestPoints(Long forestPoints) {
        this.forestPoints = forestPoints;
    }

    public Long getWaterPoints() {
        return waterPoints;
    }

    public Planet waterPoints(Long waterPoints) {
        this.waterPoints = waterPoints;
        return this;
    }

    public void setWaterPoints(Long waterPoints) {
        this.waterPoints = waterPoints;
    }

    public Long getSmogPoints() {
        return smogPoints;
    }

    public Planet smogPoints(Long smogPoints) {
        this.smogPoints = smogPoints;
        return this;
    }

    public void setSmogPoints(Long smogPoints) {
        this.smogPoints = smogPoints;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public Planet animals(Set<Animal> animals) {
        this.animals = animals;
        return this;
    }

    public Planet addAnimal(Animal animal) {
        this.animals.add(animal);
        animal.setPlanet(this);
        return this;
    }

    public Planet removeAnimal(Animal animal) {
        this.animals.remove(animal);
        animal.setPlanet(null);
        return this;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public Planet achievements(Set<Achievement> achievements) {
        this.achievements = achievements;
        return this;
    }

    public Planet addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
        achievement.getPlanets().add(this);
        return this;
    }

    public Planet removeAchievement(Achievement achievement) {
        this.achievements.remove(achievement);
        achievement.getPlanets().remove(this);
        return this;
    }

    public void setAchievements(Set<Achievement> achievements) {
        this.achievements = achievements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Planet)) {
            return false;
        }
        return id != null && id.equals(((Planet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Planet{" +
            "id=" + getId() +
            ", forestPoints=" + getForestPoints() +
            ", waterPoints=" + getWaterPoints() +
            ", smogPoints=" + getSmogPoints() +
            "}";
    }
}
