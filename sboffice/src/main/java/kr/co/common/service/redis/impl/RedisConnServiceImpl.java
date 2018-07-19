package kr.co.common.service.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import kr.co.common.service.redis.RedisConnService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 정용길 레디스 connection 관리
 */
@Slf4j
@Data
@Component
public class RedisConnServiceImpl implements RedisConnService {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * Redis server 장애 여부 판단
     */
    private boolean available = true;

    /**
     * health check delay seconds (defalut 5 seconds)
     */
    private int healthCheckDelaySeconds = 5;

    public void setHealthCheckDelaySeconds(int healthCheckDelaySeconds) {
        this.healthCheckDelaySeconds = healthCheckDelaySeconds;
    }

    @Override
    public void enable() {

        log.error(
                "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                        + "      Redis server enable......"
                        + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");

        this.available = true;
    }

    @Override
    public void disable() {

        log.error(
                "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                        + "      Redis server disable......"
                        + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");

        this.available = false;

        log.error(
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

        log.debug("Redis server to reconnect after {} seconds.", healthCheckDelaySeconds);

        while (isNotAvailable()) {

            // default 5 second delay
            try {
                String dot = ".";
                String progress = dot;

                for (int i = healthCheckDelaySeconds; i >= 1; i--) {
                    Thread.sleep(1000);
                    log.debug("Redis server to reconnect {} {}.", i, progress);
                    progress += dot;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("RedisConnServiceImpl.healthCheck : {}", e);

                log.error(
                        "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                                + "      Redis 서버가 죽었습니다. Redis 서버 상태를 확인해주세요...... "
                                + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
            }

            try {
                this.ping();
                this.enable();
            } catch (Exception e) {
                log.error("RedisConnServiceImpl.ping / enable: {}", e);

                log.error(
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

}


