package jeff.baseproject.repository;
import jeff.baseproject.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User member);
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> findAll();
}
