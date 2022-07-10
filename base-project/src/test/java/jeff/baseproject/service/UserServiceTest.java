package jeff.baseproject.service;

import jeff.baseproject.Service.UserService;
import jeff.baseproject.domain.User;
import jeff.baseproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;


    @Test
    void join() {
        //given
        User user = new User();
        user.Set("spring", "asdsd@hotmail.com","akakaka",0,false);
        //when
        Long saveId = userService.join(user);
        //then
        User finduser = userService.findOne(saveId).get();
        Assertions.assertEquals(user.getName(), finduser.getName());
    }

    @Test
    void assertJoin() {
        //given
        User user1 = new User();
        user1.Set("spring", "asdsd@hotmail.com","akakaka",0,false);
        User user2 = new User();
        user2.Set("spring", "asdsd2@hotmail.com","akakaka",0,false);

        //when
        userService.join(user1);
        // ctrl + alt + v
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));



        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}