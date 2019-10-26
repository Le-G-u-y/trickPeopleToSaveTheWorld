package de.tpsw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A LightingData.
 */
@Entity
@Table(name = "lighting_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LightingData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "on_seconds")
    private Long onSeconds;

    @Column(name = "off_seconds")
    private Long offSeconds;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOnSeconds() {
        return onSeconds;
    }

    public LightingData onSeconds(Long onSeconds) {
        this.onSeconds = onSeconds;
        return this;
    }

    public void setOnSeconds(Long onSeconds) {
        this.onSeconds = onSeconds;
    }

    public Long getOffSeconds() {
        return offSeconds;
    }

    public LightingData offSeconds(Long offSeconds) {
        this.offSeconds = offSeconds;
        return this;
    }

    public void setOffSeconds(Long offSeconds) {
        this.offSeconds = offSeconds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LightingData)) {
            return false;
        }
        return id != null && id.equals(((LightingData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LightingData{" +
            "id=" + getId() +
            ", onSeconds=" + getOnSeconds() +
            ", offSeconds=" + getOffSeconds() +
            "}";
    }
}
