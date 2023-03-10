package com.synergysuite.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@NamedQuery(name = "findAllEmployees", query = "SELECT e FROM Employee e")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
    @NotNull
    private String jobTitle;
    @NotNull
    private Float yearsInCompany;
    private Integer vacationDays;
    @OneToMany(fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JoinColumn(name = "employee_id")
    private List<Holiday> holidays;
    @OneToMany(fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JoinColumn(name = "employee_id")
    private List<Experience> experiences;

    public Employee() {}

    public Employee(String firstName, String lastName, LocalDate startDate, String jobTitle,
                    List<Experience> experiences, List<Holiday> holidays) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.jobTitle = jobTitle;
        this.experiences = experiences;
        this.holidays = holidays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Float getYearsInCompany() {
        return yearsInCompany;
    }

    public void setYearsInCompany(Float yearsInCompany) {
        this.yearsInCompany = yearsInCompany;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }

    @PrePersist
    @PreUpdate
    public void calculateYearsAndVacation() {
        if (startDate != null) {
            LocalDate todaysDate = LocalDate.now();

            Period age = Period.between(getStartDate(), todaysDate);
            yearsInCompany = age.getYears() + (age.getMonths() / 12f);
        }

        float totalExp = yearsInCompany;
        for (Experience exp: experiences) {
            if (exp.getYearsWorked() != null)
                totalExp += exp.getYearsWorked();
        }
        totalExp = 20 + (int) (totalExp / 5);
        for (Holiday hol: holidays) {
            if (hol.getDaysOff() != null)
                totalExp -= hol.getDaysOff();
        }
        vacationDays = (int) totalExp;
    }
}
