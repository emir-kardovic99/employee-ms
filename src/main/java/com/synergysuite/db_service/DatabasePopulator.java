package com.synergysuite.db_service;

import com.synergysuite.jpa.Employee;
import com.synergysuite.jpa.Experience;
import com.synergysuite.jpa.Holiday;
import com.synergysuite.services.EmployeeEJB;
import com.synergysuite.services.ExperienceEJB;
import com.synergysuite.services.HolidayEJB;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Singleton
@Startup
public class DatabasePopulator {
    @Inject
    private EmployeeEJB ejb;
    @Inject
    private ExperienceEJB experienceEJB;
    @Inject
    private HolidayEJB holidayEJB;

    @PostConstruct
    private void populateDB() {
        LocalDate date = LocalDate.now();
        List<Experience> expList = new LinkedList<>();
        List<Holiday> holList = new LinkedList<>();

        Experience exp = new Experience("ahaa", LocalDate.now(), LocalDate.now());
        Holiday hol = new Holiday(LocalDate.now(), LocalDate.now(), "no reason");

        expList.add(exp);
        holList.add(hol);

        Employee employee1 = new Employee("Test1", "Test1", date, "ovo1", expList, holList);
        Employee employee2 = new Employee("Test2", "Test2", date, "ovo2", expList, holList);
        Employee employee3 = new Employee("Test3", "Test3", date, "ovo3", expList, holList);
        ejb.addEmployee(employee1);
        ejb.addEmployee(employee2);
        ejb.addEmployee(employee3);
    }
}
