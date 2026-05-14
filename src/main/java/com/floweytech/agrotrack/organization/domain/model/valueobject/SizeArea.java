package com.floweytech.agrotrack.organization.domain.model.valueobject;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SizeArea {
    private Double value;
    private String unit;

    protected SizeArea() {
    }

    public SizeArea(Double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SizeArea)) return false;
        SizeArea that = (SizeArea) o;
        return Objects.equals(value, that.value) &&
               Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }

    @Override
    public String toString() {
        return "SizeArea{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}