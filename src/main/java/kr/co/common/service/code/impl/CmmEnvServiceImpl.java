package kr.co.common.service.code.impl;

import kr.co.common.data.domain.CommonCodeVO;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.solbipos.application.common.service.impl.CmmEnvMapper;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : CmmEnvServiceImpl.java
 * @Description : 환경변수 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.22  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cmmEnvService")
public class CmmEnvServiceImpl implements CmmEnvService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CmmEnvMapper cmmEnvMapper;
    @Autowired
    private RedisCustomTemplate<String, CommonCodeVO> redisCustomTemplate;

    /** 환경변수 코드 조회 */
    @Override
    public <E> List<E> selectEnvCodeList(String envstCd) {
        return cmmEnvMapper.selectEnvCodeList(envstCd);
    }

    /** 본사 환경변수 값 조회 */
    @Override
    public String getHqEnvst(HqEnvstVO hqEnvstVO) {
        return cmmEnvMapper.getHqEnvst(hqEnvstVO);
    }

    /** 환경변수명 조회 */
    @Override
    public String getEnvNm(EnvstVO envstVO) {
        return cmmEnvMapper.getEnvNm(envstVO);
    }
}
