package kr.co.common.template;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Class Name : RedisCustomTemplate.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class RedisCustomTemplate<K, V> extends RedisTemplate<K, V> {

    /** 웹 유져 세션 정보에 사용 */
    private String keyPrefix;
    /** 공통 코드에 사용 */
    private String keyPrefixCode;

    /**
     * @return the keyPrefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }
    /**
     * @param keyPrefix the keyPrefix to set
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
    /**
     * @return the keyPrefixCode
     */
    public String getKeyPrefixCode() {
        return keyPrefixCode;
    }
    /**
     * @param keyPrefixCode the keyPrefixCode to set
     */
    public void setKeyPrefixCode(String keyPrefixCode) {
        this.keyPrefixCode = keyPrefixCode;
    }
    
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


