package jeff.baseproject;

import jeff.baseproject.Service.UserService;
import jeff.baseproject.repository.MemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public UserService userService ()
    {
        return new UserService(memoryUserRepository());
    }

    @Bean
    public MemoryUserRepository memoryUserRepository ()
    {
        return new MemoryUserRepository();
    }
}
