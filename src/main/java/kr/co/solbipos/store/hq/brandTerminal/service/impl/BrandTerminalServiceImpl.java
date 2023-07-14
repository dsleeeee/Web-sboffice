package kr.co.solbipos.store.hq.brandTerminal.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brandTerminal.service.BrandTerminalVO;
import kr.co.solbipos.store.hq.brandTerminal.service.BrandTerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BrandTerminalServiceImpl.java
 * @Description : 기초관리 > 본사정보관리 > 브랜드별 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class BrandTerminalServiceImpl implements BrandTerminalService{


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final BrandTerminalMapper mapper;
    private final String POS_ENVST_CD = "2028"; // 코너, VAN 설정 환경변수

    /** Constructor Injection */
    @Autowired
    public BrandTerminalServiceImpl(BrandTerminalMapper mapper) {
        this.mapper = mapper;
    }

    /** 벤더 조회 */
    @Override
    public List<DefaultMap<String>> getVendorList() {

        return mapper.getVendorList();
    }

    /** 브랜드 조회 */
    @Override
    public List<DefaultMap<String>> getBrandList(BrandTerminalVO brandTerminalVO, SessionInfoVO sessionInfoVO) {
        brandTerminalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getBrandList(brandTerminalVO);
    }

    /** 터미널 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTerminalList(BrandTerminalVO brandTerminalVO, SessionInfoVO sessionInfoVO) {
        brandTerminalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getTerminalList(brandTerminalVO);
    }

    /** 포스 터미널 정보 저장 */
    @Override
    public int saveTerminalInfo(BrandTerminalVO[] brandTerminalVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for(BrandTerminalVO brandTerminalVO :  brandTerminalVOs) {
            brandTerminalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            brandTerminalVO.setRegDt(dt);
            brandTerminalVO.setRegId(sessionInfoVO.getUserId());
            brandTerminalVO.setModDt(dt);
            brandTerminalVO.setModId(sessionInfoVO.getUserId());

            if(brandTerminalVO.getStatus() == GridDataFg.INSERT) {
                result += mapper.insertTerminalInfo(brandTerminalVO);
                result += mapper.insertStoreTerminalInfo(brandTerminalVO);
            } else if(brandTerminalVO.getStatus() == GridDataFg.UPDATE) {
                result += mapper.updateTerminalInfo(brandTerminalVO);
                result += mapper.insertStoreTerminalInfo(brandTerminalVO);
            } else if(brandTerminalVO.getStatus() == GridDataFg.DELETE) {
                result += mapper.deleteTerminalInfo(brandTerminalVO);
                result += mapper.deleteStoreTerminalInfo(brandTerminalVO);
            }
        }
        return result;
    }

}
