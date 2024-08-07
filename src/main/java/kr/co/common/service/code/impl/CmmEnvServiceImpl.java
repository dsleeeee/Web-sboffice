package kr.co.common.service.code.impl;

import kr.co.common.service.code.CmmEnvService;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
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

    private final CmmEnvMapper cmmEnvMapper;

    /** Constructor Injection */
    @Autowired
    public CmmEnvServiceImpl(CmmEnvMapper cmmEnvMapper) {
        this.cmmEnvMapper = cmmEnvMapper;
    }

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

    /** 매장 환경변수 값 조회 */
    @Override
    public String getStoreEnvst(StoreEnvVO storeEnvVO) {
        return cmmEnvMapper.getStoreEnvst(storeEnvVO);
    }

    /** 환경변수명 조회 */
    @Override
    public String getEnvNm(EnvstVO envstVO) {
        return cmmEnvMapper.getEnvNm(envstVO);
    }
}
