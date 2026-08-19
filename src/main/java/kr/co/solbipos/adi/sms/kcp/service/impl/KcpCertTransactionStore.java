package kr.co.solbipos.adi.sms.kcp.service.impl;

import kr.co.common.service.redis.RedisConnService;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertException;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * KCP 거래정보를 SHA-256 {@code reg_cert_key} 키로 Redis에 TTL 저장한다.
 * {@link #claim(String)}과 {@link #release(String, String)}의 Lua 원자연산으로 중복 콜백을 막는다.
 */
@Component
class KcpCertTransactionStore {

    /** 거래정보 키와 중복 콜백 처리권 키는 같은 SHA-256 digest를 사용한다. */
    private static final String KEY_PREFIX = "kcp:cert:v2:txn:";
    private static final String CLAIM_KEY_PREFIX = "kcp:cert:v2:claim:";
    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();

    /**
     * 거래가 존재할 때만 {@code SET NX EX}로 처리권을 만든다.
     * 반환값은 거래 없음 -1, 다른 요청이 처리 중이면 0, 처리권 획득 성공이면 1이다.
     */
    private static final DefaultRedisScript<Long> CLAIM_SCRIPT = new DefaultRedisScript<Long>(
            "if redis.call('exists', KEYS[1]) == 0 then return -1 end " +
            "if redis.call('set', KEYS[2], ARGV[1], 'NX', 'EX', ARGV[2]) then return 1 else return 0 end",
            Long.class);

    /** 자신이 발급받은 토큰과 일치할 때만 claim을 지워 다른 요청의 처리권을 보호한다. */
    private static final DefaultRedisScript<Long> RELEASE_SCRIPT = new DefaultRedisScript<Long>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end",
            Long.class);

    private final RedisConnService redisConnService;
    private final RedisCustomTemplate<String, KcpCertTransaction> redisTemplate;
    private final long transactionTtlMinutes;

    /** 설정된 TTL은 거래정보와 claim 모두에 적용되며, 잘못된 무기한 보관을 허용하지 않는다. */
    @Autowired
    public KcpCertTransactionStore(RedisConnService redisConnService,
                                   RedisCustomTemplate<String, KcpCertTransaction> redisTemplate,
                                   @Value("#{config['kcp.cert.v2.transactionTtlMinutes']}") long transactionTtlMinutes) {
        this.redisConnService = redisConnService;
        this.redisTemplate = redisTemplate;
        if (transactionTtlMinutes <= 0L) {
            throw new IllegalArgumentException("KCP transaction TTL must be greater than zero");
        }
        this.transactionTtlMinutes = transactionTtlMinutes;
    }

    /** DTO의 논리 만료시각 계산에 사용할 Redis TTL을 밀리초로 반환한다. */
    long getTransactionTtlMillis() {
        return TimeUnit.MINUTES.toMillis(transactionTtlMinutes);
    }

    /** KCP 거래정보를 {@code reg_cert_key} 해시 키로 TTL 저장한다. */
    void save(String regCertKey, KcpCertTransaction transaction) {
        // Redis 연결상태 확인
        requireRedis();
        try {
            // Redis 거래정보 TTL 저장
            redisTemplate.set(transactionKey(regCertKey), transaction,
                    transactionTtlMinutes, TimeUnit.MINUTES);
        } catch (RuntimeException e) {
            // Redis 장애 상태 반영
            redisConnService.disable();
            // Redis 저장 오류 변환
            throw redisFailure("KCP 인증 거래를 저장하지 못했습니다.", e);
        }
    }

    /** {@code reg_cert_key} 해시 키로 Redis 거래정보를 조회한다. */
    KcpCertTransaction get(String regCertKey) {
        // Redis 연결상태 확인
        requireRedis();
        try {
            // Redis 거래정보 조회
            return redisTemplate.get(transactionKey(regCertKey));
        } catch (RuntimeException e) {
            // Redis 장애 상태 반영
            redisConnService.disable();
            // Redis 조회 오류 변환
            throw redisFailure("KCP 인증 거래를 조회하지 못했습니다.", e);
        }
    }

    /**
     * Lua 원자연산으로 중복 콜백 처리권을 획득한다.
     * 반환 토큰은 {@link #release(String, String)}가 자신의 처리권만 해제할 때 사용한다.
     */
    String claim(String regCertKey) {
        // Redis 연결상태 확인
        requireRedis();
        // 거래등록키 SHA-256 변환
        String digest = digest(regCertKey);
        String transactionKey = redisTemplate.makeKey(KEY_PREFIX + digest);
        String claimKey = redisTemplate.makeKey(CLAIM_KEY_PREFIX + digest);
        String claimToken = java.util.UUID.randomUUID().toString();
        long claimTtlSeconds = Math.max(1L, TimeUnit.MINUTES.toSeconds(transactionTtlMinutes));

        try {
            // 중복 콜백 처리권 원자적 획득
            Long result = redisTemplate.execute(CLAIM_SCRIPT, STRING_SERIALIZER, null,
                    Arrays.asList(transactionKey, claimKey), claimToken, String.valueOf(claimTtlSeconds));
            if (result == null || result.longValue() < 0L) {
                throw new KcpCertException("TRANSACTION_NOT_FOUND", "인증 거래가 없거나 만료되었습니다.");
            }
            if (result.longValue() == 0L) {
                throw new KcpCertException("TRANSACTION_ALREADY_PROCESSING", "이미 처리 중인 인증 거래입니다.");
            }
            return claimToken;
        } catch (KcpCertException e) {
            throw e;
        } catch (RuntimeException e) {
            // Redis 장애 상태 반영
            redisConnService.disable();
            // Redis 처리권 오류 변환
            throw redisFailure("KCP 인증 거래 처리권을 획득하지 못했습니다.", e);
        }
    }

    /** 실패한 결과조회가 획득한 처리권만 해제해 콜백 재시도를 허용한다. */
    void release(String regCertKey, String claimToken) {
        if (claimToken == null || !redisConnService.isAvailable()) {
            return;
        }
        // 거래등록키 SHA-256 변환
        String claimKey = redisTemplate.makeKey(CLAIM_KEY_PREFIX + digest(regCertKey));
        try {
            // 현재 요청의 처리권만 원자적 해제
            redisTemplate.execute(RELEASE_SCRIPT, STRING_SERIALIZER, null,
                    Arrays.asList(claimKey), claimToken);
        } catch (RuntimeException e) {
            // Redis 장애 상태 반영
            redisConnService.disable();
        }
    }

    /** 업무 처리가 끝난 거래정보와 중복 콜백 처리권을 함께 삭제한다. */
    void remove(String regCertKey) {
        // Redis 연결상태 확인
        requireRedis();
        // 거래등록키 SHA-256 변환
        String digest = digest(regCertKey);
        try {
            // Redis 거래정보와 처리권 삭제
            redisTemplate.delete(Arrays.asList(
                    redisTemplate.makeKey(KEY_PREFIX + digest),
                    redisTemplate.makeKey(CLAIM_KEY_PREFIX + digest)));
        } catch (RuntimeException e) {
            // Redis 장애 상태 반영
            redisConnService.disable();
            // Redis 삭제 오류 변환
            throw redisFailure("KCP 인증 거래를 삭제하지 못했습니다.", e);
        }
    }

    /** 외부 거래등록키 원문 대신 사용할 Redis 거래 키를 만든다. */
    private String transactionKey(String regCertKey) {
        // 거래등록키 SHA-256 변환
        return redisTemplate.makeKey(KEY_PREFIX + digest(regCertKey));
    }

    /** {@code reg_cert_key} 원문이 Redis 키에 노출되지 않도록 SHA-256 해시를 만든다. */
    private String digest(String regCertKey) {
        if (regCertKey == null || regCertKey.trim().isEmpty()) {
            throw new KcpCertException("INVALID_CALLBACK", "거래등록키가 없습니다.");
        }
        try {
            // 거래등록키 SHA-256 해시 생성
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(regCertKey.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(bytes.length * 2);
            for (byte value : bytes) {
                hex.append(String.format("%02x", value & 0xff));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new KcpCertException("CRYPTO_CONFIGURATION_ERROR", "SHA-256을 사용할 수 없습니다.", e);
        }
    }

    /** 공용 Redis 연결이 비활성 상태이면 KCP 거래 처리를 즉시 중단한다. */
    private void requireRedis() {
        if (!redisConnService.isAvailable()) {
            throw new KcpCertException("REDIS_UNAVAILABLE", "인증 거래 저장소를 사용할 수 없습니다.");
        }
    }

    /** Redis 런타임 오류를 KCP 거래 저장소의 공통 예외 형식으로 변환한다. */
    private KcpCertException redisFailure(String message, RuntimeException cause) {
        return new KcpCertException("REDIS_ERROR", message, cause);
    }
}
