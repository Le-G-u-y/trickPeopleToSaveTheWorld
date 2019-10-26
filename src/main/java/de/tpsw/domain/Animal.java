package de.tpsw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.tpsw.domain.enumeration.AnimalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Animal.
 */
@Entity
@Table(name = "animal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type", nullable = false)
    private AnimalType animalType;

    @NotNull
    @Column(name = "max_health", nullable = false)
    private Integer maxHealth;

    @NotNull
    @Column(name = "current_health", nullable = false)
    private Integer currentHealth;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @NotNull
    @Column(name = "happiness", nullable = false)
    private Boolean happiness;

    @Column(name = "death_notified")
    private Boolean deathNotified;

    @ManyToOne
    @JsonIgnoreProperties("animals")
    private Planet planet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Animal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public Animal animalType(AnimalType animalType) {
        this.animalType = animalType;
        return this;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    public Animal maxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    public void setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public Animal currentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public void setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Animal creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isHappiness() {
        return happiness;
    }

    public Animal happiness(Boolean happiness) {
        this.happiness = happiness;
        return this;
    }

    public void setHappiness(Boolean happiness) {
        this.happiness = happiness;
    }

    public Boolean isDeathNotified() {
        return deathNotified;
    }

    public Animal deathNotified(Boolean deathNotified) {
        this.deathNotified = deathNotified;
        return this;
    }

    public void setDeathNotified(Boolean deathNotified) {
        this.deathNotified = deathNotified;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Animal planet(Planet planet) {
        this.planet = planet;
        return this;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    // Health Management
    /**
     * add health to animal and return the health diff
     * @param healthChange
     * @return health diff, thats over the max health of animal
     */
    public Integer alterHealth(Integer healthChange) {

        this.setCurrentHealth(this.getCurrentHealth() + healthChange);

        // calculate an overflow of health (for adding it to a baby)
        Integer healthDiff = 0;
        if (this.isHealthFull()) {

            healthDiff = this.getCurrentHealth() - this.getMaxHealth();
            if (healthDiff > 0) {
                // limit current health to max
                this.setCurrentHealth(this.getMaxHealth());
            }
        }

        return healthDiff;
    }

    public boolean isHealthFull() {
        if (currentHealth >= maxHealth) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Animal)) {
            return false;
        }
        return id != null && id.equals(((Animal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Animal{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", animalType='" + getAnimalType() + "'" +
            ", maxHealth=" + getMaxHealth() +
            ", currentHealth=" + getCurrentHealth() +
            ", creationDate='" + getCreationDate() + "'" +
            ", happiness='" + isHappiness() + "'" +
            ", deathNotified='" + isDeathNotified() + "'" +
            "}";
    }
}
