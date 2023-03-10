package com.synergysuite.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.Period;

@Entity
public class Experience {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate dateFrom;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate dateTo;
    private Float yearsWorked;

    public Experience() {}

    public Experience(String name, LocalDate dateFrom, LocalDate dateTo) {
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Float getYearsWorked() {
        return yearsWorked;
    }

    public void setYearsWorked(Float yearsWorked) {
        this.yearsWorked = yearsWorked;
    }

    @PrePersist
    @PreUpdate
    public void calculateYearsWorked() {
        Period age = Period.between(getDateFrom(), getDateTo());

        yearsWorked = age.getYears() + (age.getMonths() / 12f);
    }
}
