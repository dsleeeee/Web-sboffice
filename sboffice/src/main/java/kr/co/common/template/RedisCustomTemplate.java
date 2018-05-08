package kr.co.common.template;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 정용길
 *
 * @param <K>
 * @param <V>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RedisCustomTemplate<K, V> extends RedisTemplate<K, V> {

    /**
     * 웹 유져 세션 정보에 사용
     */
    private String keyPrefix;

    /**
     * 공통 코드에 사용
     */
    private String keyPrefixCode;

    public String makeKeyCode(String key) {
        return keyPrefixCode + key;
    }

    public String makeKey(String key) {
        return keyPrefix + key;
    }

    public V get(K key) {
        return super.opsForValue().get(key);
    }

    public Set<K> keys(K pattern) {
        return super.keys(pattern);
    }

    public void set(K key, V value) {
        super.opsForValue().set(key, value);
    }

    public void set(K key, V value, long timeout, TimeUnit minutes) {
        super.opsForValue().set(key, value, timeout, minutes);
    }

    public void delete(K key) {
        super.delete(key);
    }

    public void delete(Collection<K> keys) {
        super.delete(keys);
    }

    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return super.expire(key, timeout, unit);
    }

    public Boolean hasKey(K key) {
        return super.hasKey(key);
    }

}


