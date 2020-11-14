package com.asist.tests.repository;

import com.asist.project.config.DatabaseConfig;
import com.asist.project.config.HibernateMetadataIntegrator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, HibernateMetadataIntegrator.class},
        loader = AnnotationConfigContextLoader.class
)
@Transactional
public class UserRepositoryTest {
    
}
