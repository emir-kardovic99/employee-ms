package com.synergysuite.services;

import com.synergysuite.jpa.Employee;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Stateless
public class EmployeeEJB {

    @PersistenceContext
    EntityManager em;

    public List<Employee> findEmployees() {
        TypedQuery<Employee> query = em.createNamedQuery("findAllEmployees", Employee.class);
        return query.getResultList();
    }

    public List<Employee> findEmployeeByName(String fName) {
        fName = "%" + fName + "%";

        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE lower(e.firstName) LIKE :fName", Employee.class);
        query.setParameter("fName", fName);
        return query.getResultList();
    }

    public Employee findEmployeeById(Long id) {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public @NotNull Employee addEmployee(@NotNull Employee employee) {
        em.persist(employee);
        return employee;
    }

    public @NotNull Employee updateEmployee(@NotNull Employee employee) {
        return em.merge(employee);
    }

    public void deleteEmployee(@NotNull Employee employee) {
        em.remove(em.merge(employee));
    }

}
