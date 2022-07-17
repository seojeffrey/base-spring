package jeff.baseproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<Object, String> redisTemplate;

    @AfterEach
    public void afterEach()
    {
        // 아니 왜 flush 가 없지..?
        Set<Object> keys = redisTemplate.keys("*");

        /*
        for (Object key: keys.toArray()) {
            redisTemplate.delete(key);
        }*/
        //redisTemplate.delete(Objects.requireNonNull(keys).toArray());
    }

    @Test
    void testStrings() {
        // given
        ValueOperations<Object, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        // then
        String value = valueOperations.get(key);
        assertThat(value).isEqualTo("hello");
    }


    @Test
    void testSet() {
        // given
        SetOperations<Object, String> setOperations = redisTemplate.opsForSet();
        String key = "setKey";

        // when
        setOperations.add(key, "h", "e", "l", "l", "o");

        // then
        Set<String> members = setOperations.members(key);
        Long size = setOperations.size(key);

        assertThat(members).containsOnly("h", "e", "l", "o");
        assertThat(size).isEqualTo(4);
    }

    @Test
    void testHash() {
        // given
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        String key = "hashKey";

        // when
        hashOperations.put(key, "hello", "world");

        // then
        Object value = hashOperations.get(key, "hello");
        assertThat(value).isEqualTo("world");

        Map<Object, Object> entries = hashOperations.entries(key);
        assertThat(entries.keySet()).containsExactly("hello");
        assertThat(entries.values()).containsExactly("world");

        Long size = hashOperations.size(key);
        assertThat(size).isEqualTo(entries.size());
    }

    @Test
    void testSortedSet() {
        // given
        ZSetOperations<Object, String> zSetOperations = redisTemplate.opsForZSet();
        String key = "zSetKey";

        // when
        zSetOperations.add(key, "Hello", 2);
        zSetOperations.add(key, "World", 1);

        // then
        Set<String> value = zSetOperations.reverseRange(key, 0, -1);

        Set<String> expect = new HashSet<String>(){{
            add("Hello");
            add("World");
        }};
        assertThat(value).isEqualTo(expect);
        // 동점이 있을 경우를 대비해 같은 score entries 를 가져옴(동점=같은 랭킹 일 경우)
        Set<String> entries = zSetOperations.reverseRangeByScore(key, 1, 1);
        // score 를 이용해 가장 높은 친구를 가져옴
        long rank = zSetOperations.reverseRank(key, Objects.requireNonNull(entries).toArray()[0]);
        assertThat(rank).isEqualTo(1);

        Long size = zSetOperations.size(key);
        assertThat(size).isEqualTo(expect.size());
    }

    @Test
    void testTransactionSet() {
        // given
        String key = "zScoreKey";
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                operations.watch(key);  // key값 변경 체크
                operations.multi(); // 트랜잭션 Start
                for(int i=0; i<10000; i++) {
                    operations.opsForValue().increment(key, 1);
                }
                operations.exec(); // 트랜잭션 Commit
                return null;
            }
        });
        String value = redisTemplate.opsForValue().get(key);
        assertThat(value).isEqualTo(Integer.toString(10000));
    }
}