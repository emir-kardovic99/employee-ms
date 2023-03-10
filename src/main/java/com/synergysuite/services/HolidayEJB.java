package com.synergysuite.services;

import com.synergysuite.jpa.Holiday;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;

@Stateless
public class HolidayEJB {

    @PersistenceContext
    EntityManager em;
    
    public @NotNull Holiday addHoliday(@NotNull Holiday Holiday) {
        em.persist(Holiday);
        return Holiday;
    }

    public @NotNull Holiday updateHoliday(@NotNull Holiday Holiday) {
        return em.merge(Holiday);
    }

    public void deleteHoliday(@NotNull Holiday Holiday) {
        em.remove(em.merge(Holiday));
    }
}
