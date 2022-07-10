package jeff.baseproject;

import jeff.baseproject.Service.UserService;
import jeff.baseproject.repository.JpaUserRepository;
import jeff.baseproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Configuration
public class SpringConfig {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em)
    {
        this.em = em;
    }

    @Bean
    public UserService userService ()
    {
        return new UserService(UserRepository());
    }

    @Bean
    public UserRepository UserRepository ()
    {

        return new JpaUserRepository(em);
    }
}
