package jeff.baseproject.repository;

import jeff.baseproject.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach()
    {
        repository.clearStore();
    }
    @Test
    public void save(){
        User user = new User();
        user.Set("성빈", "souladin@naver.com", "",1, true);
        repository.save(user);

        User result = repository.findById(user.getId()).get();
        Assertions.assertEquals(user, result);
        assertThat(user).isEqualTo(result);
    }
    @Test
    public void findByName()
    {
        User user1 = new User();
        user1.Set("성빈", "souladin@naver.com", "", 1, true);
        repository.save(user1);

        User user2 = new User();
        user2.Set("성빈2", "souladin@naver.com", "",1, true);
        repository.save(user2);

        User result = repository.findByName("성빈").get();
        Assertions.assertEquals(user1, result);
    }

    @Test
    public void findAll()
    {
        User user1 = new User();
        user1.Set("성빈", "souladin@naver.com", "",1, true);
        repository.save(user1);

        User user2 = new User();
        user2.Set("성빈2", "souladin@naver.com", "",1, true);
        repository.save(user2);

        List<User> result = repository.findAll();
        Assertions.assertEquals(2, result.size());
    }
}