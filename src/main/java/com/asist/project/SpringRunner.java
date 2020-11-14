package com.asist.project;

import com.asist.project.config.HibernateMetadataIntegrator;
import com.asist.project.models.Role;
import com.asist.project.models.User;
import com.asist.project.repository.UserRepository;
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
        }
        final PasswordEncoder pe = context.getBean(PasswordEncoder.class);
        final UserRepository ur = context.getBean(UserRepository.class);
        User user = new User();
        user.setRole(Role.ADMIN);
        user.setNickname("admin");
        user.setPassword(pe.encode("admin"));
        ur.save(user);
    }
}
