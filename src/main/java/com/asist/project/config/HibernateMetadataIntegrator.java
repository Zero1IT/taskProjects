package com.asist.project.config;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Component
public class HibernateMetadataIntegrator implements Integrator {

    private Metadata metadata;

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        this.metadata = metadata;
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        // not used
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
