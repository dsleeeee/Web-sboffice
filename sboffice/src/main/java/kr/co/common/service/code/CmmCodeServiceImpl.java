package kr.co.common.service.code;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.domain.CommonCodeVO;
import kr.co.common.service.redis.RedisConnService;
import kr.co.common.system.Prop;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.solbipos.application.common.service.impl.CmmCodeMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통 코드 관련 서비스
 *
 * @author 정용길
 *
 */
@Slf4j
@Service
public class CmmCodeServiceImpl implements CmmCodeService {

    @Autowired
    CmmCodeMapper cmmCodeMapper;

    @Autowired
    Prop prop;

    @Autowired
    RedisConnService redisConnService;

    @Autowired
    private RedisCustomTemplate<String, CommonCodeVO> redisCustomTemplate;

    @Override
    public void setCodeList(CommonCodeVO commonCodeVO) throws Exception {
        if (redisConnService.isAvailable()) {
            try {
                redisCustomTemplate.set(redisCustomTemplate.makeKeyCode(commonCodeVO.getComCdFg()), commonCodeVO);
            } catch (Exception e) {
                /**
                 * 레디스에서 공통코드를 조회 할때는 아래 코드로 처리 하지 않고 예외 처리함. redisConnService.disable() 레디스가 죽었다고
                 * 공통 코드가 조회 안되면 안되기 때문에 디비에서 조회해서 사용 가능
                 */
                log.error("Redis server not available!! setSessionInfo {}", e);
            }
        } else {
            throw new Exception();
        }
    }

    @Override
    public CommonCodeVO getCodeList(String comCdFg) throws Exception {
        CommonCodeVO commonCodeVO = null;
        if (redisConnService.isAvailable()) {
            try {
                commonCodeVO = redisCustomTemplate.get(redisCustomTemplate.makeKeyCode(comCdFg));
            } catch (Exception e) {
                log.error("Redis server not available!! getSessionInfo {}", e);
            }
        } else {
            throw new Exception();
        }
        return commonCodeVO;
    }

    @Override
    public boolean hasCode(String comCdFg) {
        if (redisConnService.isAvailable()) {
            try {
                return redisCustomTemplate.hasKey(redisCustomTemplate.makeKeyCode(comCdFg));
            } catch (Exception e) {
                // 레디스에 접속 못할 경우에는 디비에서 접속해야 되기 때문에 false 처리함
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateCodeList(CommonCodeVO commonCodeVO) {
        String makeKeyComCdFg = redisCustomTemplate.makeKeyCode(commonCodeVO.getComCdFg());
        if (redisConnService.isAvailable()) {
            try {
                if (redisCustomTemplate.hasKey(makeKeyComCdFg)) {
                    redisCustomTemplate.delete(makeKeyComCdFg);
                }
                redisCustomTemplate.set(makeKeyComCdFg, commonCodeVO);
                return true;
            } catch (Exception e) {
                log.error("Redis server not available!! CommonCode update fail...");
                return false;
            }
        } else {
            return false;
        }
    }


    @Override
    public <E> List<E> selectCmmCodeList(String nmcodeGrpCd) {
        return cmmCodeMapper.selectCmmCodeList(nmcodeGrpCd);
    }

    @Override
    public <E> List<E> selectEnvCodeList(String envstCd) {
        return cmmCodeMapper.selectEnvCodeList(envstCd);
    }

    @Override
    public <E> List<E> getAgencyList() {
        return cmmCodeMapper.selectAgencyList();
    }
}
