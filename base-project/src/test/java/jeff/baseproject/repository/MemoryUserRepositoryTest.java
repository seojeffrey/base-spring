package jeff.baseproject.repository;

import jeff.baseproject.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach()
    {
        repository.clearStore();
    }
    @Test
    public void save(){
        User member = new User("성빈", "souladin@naver.com", "",1, true);
        repository.save(member);

        User result = repository.findById(member.getId()).get();
        Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
    }
    @Test
    public void findByName()
    {
        User member1 = new User("성빈", "souladin@naver.com", "", 1, true);
        repository.save(member1);

        User member2 = new User("성빈2", "souladin@naver.com", "",1, true);
        repository.save(member2);

        User result = repository.findByName("성빈").get();
        Assertions.assertEquals(member1, result);
    }

    @Test
    public void findAll()
    {
        User member1 = new User("성빈", "souladin@naver.com", "",1, true);
        repository.save(member1);

        User member2 = new User("성빈2", "souladin@naver.com", "",1, true);
        repository.save(member2);

        List<User> result = repository.findAll();
        Assertions.assertEquals(2, result.size());
    }
}