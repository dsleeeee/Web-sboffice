package kr.co.common.service.redis.impl;

import kr.co.common.service.redis.RedisConnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author 정용길 레디스 connection 관리
 */
@Service("redisConnService")
@Component
public class RedisConnServiceImpl implements RedisConnService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** Redis server 장애 여부 판단 */
    private boolean available = true;
    /** health check delay seconds (defalut 5 seconds) */
    private int healthCheckDelaySeconds = 5;

    private JedisConnectionFactory jedisConnectionFactory;

    /** Constructor Injection */
    @Autowired
    public RedisConnServiceImpl(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    public void setHealthCheckDelaySeconds(int healthCheckDelaySeconds) {
        this.healthCheckDelaySeconds = healthCheckDelaySeconds;
    }

    @Override
    public void enable() {

        LOGGER.error(
            "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                + "      Redis server enable......"
                + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");

        this.available = true;
    }

    @Override
    public void disable() {

        LOGGER.error(
            "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                + "      Redis server disable......"
                + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");

        this.available = false;

        LOGGER.error(
            "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                + "      Redis server health check start......"
                + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");

        this.healthCheck();
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean isNotAvailable() {
        return !available;
    }

    @Override
    @Async
    public void healthCheck() {

        LOGGER.debug("Redis server to reconnect after {} seconds.", healthCheckDelaySeconds);

        while (isNotAvailable()) {

            // default 5 second delay
            try {
                String dot = ".";
                String progress = dot;

                for (int i = healthCheckDelaySeconds; i >= 1; i--) {
                    Thread.sleep(1000);
                    LOGGER.debug("Redis server to reconnect {} {}.", i, progress);
                    progress += dot;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                LOGGER.error("RedisConnServiceImpl.healthCheck : {}", e);

                LOGGER.error(
                    "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                        + "      Redis is서버가 죽었습니다. Redis 서버 상태를 확인해주세요...... "
                        + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
            }

            try {
                this.ping();
                this.enable();
            } catch (Exception e) {
                LOGGER.error("RedisConnServiceImpl.ping / enable: {}", e);

                LOGGER.error(
                    "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                        + "      Redis 서버가 죽었습니다. Redis 서버 상태를 확인해주세요...."
                        + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
            }
        }
    }

    @Override
    @Async
    public String ping() {
        return jedisConnectionFactory.getConnection().ping();
    }


    /**
     * @return the jedisConnectionFactory
     */
    public JedisConnectionFactory getJedisConnectionFactory() {
        return jedisConnectionFactory;
    }

    /**
     * @param jedisConnectionFactory the jedisConnectionFactory to set
     */
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    /**
     * @return the healthCheckDelaySeconds
     */
    public int getHealthCheckDelaySeconds() {
        return healthCheckDelaySeconds;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
}


