package kr.co.common.service.code.impl;

import kr.co.common.data.domain.CommonCodeVO;
import kr.co.common.data.domain.CustomComboVO;
import kr.co.common.service.code.CmmCodeService;
import kr.co.common.service.redis.RedisConnService;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.etc.vancard.service.VanCmpnyVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 공통 코드 관련 서비스
 *
 * @author 정용길
 *
 */
@Service("cmmCodeService")
public class CmmCodeServiceImpl implements CmmCodeService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final CmmCodeMapper cmmCodeMapper;
    private final RedisConnService redisConnService;
    private final RedisCustomTemplate<String, CommonCodeVO> redisCustomTemplate;
    private final SqlSession sqlSession;

    /** Constructor Injection */
    @Autowired
    public CmmCodeServiceImpl(CmmCodeMapper cmmCodeMapper,
        RedisConnService redisConnService,
        RedisCustomTemplate<String, CommonCodeVO> redisCustomTemplate, SqlSession sqlSession) {
        this.cmmCodeMapper = cmmCodeMapper;
        this.redisConnService = redisConnService;
        this.redisCustomTemplate = redisCustomTemplate;
        this.sqlSession = sqlSession;
    }

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
                LOGGER.error("Redis server not available!! setSessionInfo {}", e);
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
                LOGGER.error("Redis server not available!! getSessionInfo {}", e);
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
                LOGGER.error("Redis server not available!! CommonCode update fail...");
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

    @Override
    public <E> List<E> getVanList(String vanFg) {
        VanCmpnyVO vanCmpnyVO = new VanCmpnyVO();
        vanCmpnyVO.setVanFg(vanFg);

        return cmmCodeMapper.selectVanList(vanCmpnyVO);
    }

    /** 본사 목록 조회*/
    @Override
    public <E> List<E> getHqOfficeList() {
        return cmmCodeMapper.getHqOfficeList();
    }

    /** 본사 코드 조회 (총판이 관리하는 본사 코드만 가져온다)*/
    @Override
    public <E> List<E> getHqOfficeListChkAgency(String agencyCd) {
        return cmmCodeMapper.getHqOfficeListChkAgency(agencyCd);
    }

    /** 커스텀 콤보박스 데이터 조회 */
    @Override
    public List<CustomComboVO> getCustomCombo(CustomComboVO customComboVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        customComboVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        customComboVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        customComboVO.setStoreCd(sessionInfoVO.getStoreCd());
        // 매퍼를 namespace와 ID를 이용하여 직접 호출.
        List<CustomComboVO> list = sqlSession.selectList("CmmCombo." + customComboVO.getQueryId(), customComboVO);

        return list;
    }
}
