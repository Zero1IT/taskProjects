package com.asist.project;

import com.asist.project.config.HibernateMetadataIntegrator;
import com.asist.project.models.Project;
import com.asist.project.models.Role;
import com.asist.project.models.User;
import com.asist.project.repository.ProjectRepository;
import com.asist.project.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

/**
 * createdAt: 11/13/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        WebSocketServletAutoConfiguration.class,
        TaskExecutionAutoConfiguration.class,
        AopAutoConfiguration.class,
        TaskSchedulingAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        RestTemplateAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        JmxAutoConfiguration.class,
        SpringApplicationAdminJmxAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class SpringRunner {
    public static void main(String[] args) {
        final ApplicationContext context = SpringApplication.run(SpringRunner.class, args);
        System.out.println("wake up " + context.getBeanDefinitionCount());
        if (Arrays.asList(args).contains("--force-db")) {
            final HibernateMetadataIntegrator metadataIntegrator = context.getBean(HibernateMetadataIntegrator.class);
            new DatabaseInitializer(metadataIntegrator.getMetadata()).initialize();
            if (Arrays.asList(args).contains("--fill-random")) {
                fillRandomData(context);
            }
        }
        final PasswordEncoder pe = context.getBean(PasswordEncoder.class);
        final UserRepository ur = context.getBean(UserRepository.class);
        User user = new User();
        user.setRole(Role.ADMIN);
        user.setNickname("admin");
        user.setPassword(pe.encode("admin"));
        ur.save(user);
    }

    private static void fillRandomData(ApplicationContext context) {
        final ProjectRepository projectRepository = context.getBean(ProjectRepository.class);
        final UserRepository userRepository = context.getBean(UserRepository.class);
        final PasswordEncoder pe = context.getBean(PasswordEncoder.class);
        for (int i = 0; i < 15; i++) {
            User user = randomUser(pe, i);
            userRepository.save(user);
            Project project = new Project();
            project.setCreator(user);
            project.setName("project " + i);
            project.setDescription("description " + i);
            projectRepository.save(project);
        }
        /*int start = 10;
        final SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        try (Session session = sessionFactory.openSession()) {
            final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            final CriteriaQuery<Project> query = criteriaBuilder.createQuery(Project.class);
            final CriteriaQuery<Project> all = query.select(query.from(Project.class));
            final List<Project> projects = session.createQuery(all).getResultList();
            session.beginTransaction();
            for (Project project : projects) {
                final Set<User> projectUsers = project.getUsers();
                for (int i = start; i < start + 10; i++) {
                    final User us = randomUser(pe, i);
                    userRepository.save(us);
                    projectUsers.add(us);
                }
                session.update(project);
                start += 10;
            }
            session.getTransaction().commit();
        }*/
    }

    @NotNull
    private static User randomUser(PasswordEncoder pe, int i) {
        User user = new User();
        user.setRole(Role.USER);
        user.setNickname("user" + i);
        user.setPassword(pe.encode("user" + i));
        return user;
    }
}
