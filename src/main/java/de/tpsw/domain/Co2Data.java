package de.tpsw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Co2Data.
 */
@Entity
@Table(name = "co_2_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Co2Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private LocalDate timestamp;

    @NotNull
    @Column(name = "co_2_value", nullable = false)
    private Integer co2Value;

    @NotNull
    @Column(name = "temp", nullable = false)
    private Float temp;

    @Column(name = "humidity")
    private Float humidity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public Co2Data timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCo2Value() {
        return co2Value;
    }

    public Co2Data co2Value(Integer co2Value) {
        this.co2Value = co2Value;
        return this;
    }

    public void setCo2Value(Integer co2Value) {
        this.co2Value = co2Value;
    }

    public Float getTemp() {
        return temp;
    }

    public Co2Data temp(Float temp) {
        this.temp = temp;
        return this;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getHumidity() {
        return humidity;
    }

    public Co2Data humidity(Float humidity) {
        this.humidity = humidity;
        return this;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Co2Data)) {
            return false;
        }
        return id != null && id.equals(((Co2Data) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Co2Data{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", co2Value=" + getCo2Value() +
            ", temp=" + getTemp() +
            ", humidity=" + getHumidity() +
            "}";
    }
}
