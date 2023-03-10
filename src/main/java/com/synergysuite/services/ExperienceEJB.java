package com.synergysuite.services;

import com.synergysuite.jpa.Experience;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Stateless
public class ExperienceEJB {

    @PersistenceContext
    EntityManager em;

    public @NotNull Experience addExperience(@NotNull Experience experience) {
        em.persist(experience);

        return experience;
    }

    public @NotNull Experience updateExperience(@NotNull Experience experience) {
        return em.merge(experience);
    }

    public void deleteExperience(@NotNull Experience experience) {
        em.remove(em.merge(experience));
    }
}
