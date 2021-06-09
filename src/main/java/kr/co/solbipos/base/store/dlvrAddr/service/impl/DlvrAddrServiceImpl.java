package kr.co.solbipos.base.store.dlvrAddr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.foodAllergy.service.FoodAllergyVO;
import kr.co.solbipos.base.store.dlvrAddr.service.DlvrAddrService;
import kr.co.solbipos.base.store.dlvrAddr.service.DlvrAddrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;


/**
 * @Class Name : dlvrAddrServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 배달권역관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dlvrAddrService")
@Transactional
public class DlvrAddrServiceImpl implements DlvrAddrService {
    private final DlvrAddrMapper dlvrAddrMapper;
    /**
     * Constructor Injection
     */
    @Autowired
    public DlvrAddrServiceImpl(DlvrAddrMapper dlvrAddrMapper) {
        this.dlvrAddrMapper = dlvrAddrMapper;
    }

    /** 등록 배달권역 조회
     * @return*/
    @Override
    public List<DefaultMap<Object>> dlvrAddrList(DlvrAddrVO dlvrAddrVO, SessionInfoVO sessionInfoVO) {

        dlvrAddrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrAddrVO.setStoreCd(sessionInfoVO.getStoreCd());

        return dlvrAddrMapper.dlvrAddrList(dlvrAddrVO);
    }

    /** 미등록 배달권역 조회
     * @return*/
    @Override
    public List<DefaultMap<Object>> dlvrAddrCodeList(DlvrAddrVO dlvrAddrVO, SessionInfoVO sessionInfoVO) {

        dlvrAddrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrAddrVO.setStoreCd(sessionInfoVO.getStoreCd());

        return dlvrAddrMapper.dlvrAddrCodeList(dlvrAddrVO);
    }

    /** 배달권역 등록
     * @return*/
    @Override
    public int addDlvrAddr(DlvrAddrVO[] dlvrAddrVOs, SessionInfoVO sessionInfoVO) {
         int result = 0;
        String currentDt = currentDateTimeString();

        for(DlvrAddrVO dlvrAddrVO : dlvrAddrVOs) {
            dlvrAddrVO.setStoreCd(sessionInfoVO.getStoreCd());
            dlvrAddrVO.setMyAreaCd(dlvrAddrMapper.getMyAreaCd(dlvrAddrVO));
            dlvrAddrVO.setRegDt(currentDt);
            dlvrAddrVO.setRegId(sessionInfoVO.getUserId());
            dlvrAddrVO.setModDt(currentDt);
            dlvrAddrVO.setModId(sessionInfoVO.getUserId());

            result = dlvrAddrMapper.addDlvrAddr(dlvrAddrVO);
        }
        return result;
    }

    /** 배달권역 등록삭제
     * @return*/
    @Override
    public int delDlvrAddr(DlvrAddrVO[] dlvrAddrVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(DlvrAddrVO dlvrAddrVO : dlvrAddrVOs) {

            dlvrAddrVO.setModDt(currentDt);
            dlvrAddrVO.setModId(sessionInfoVO.getUserId());
            dlvrAddrVO.setStoreCd(sessionInfoVO.getStoreCd());

            result = dlvrAddrMapper.delDlvrAddr(dlvrAddrVO);
        }


        return result;
    }

}