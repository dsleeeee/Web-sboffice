package kr.co.solbipos.base.prod.kioskOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionService;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskOptionServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 키오스크옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("kioskOptionService")
@Transactional
public class KioskOptionServiceImpl implements KioskOptionService {
    private final KioskOptionMapper kioskOptionMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskOptionServiceImpl(KioskOptionMapper kioskOptionMapper, CmmEnvUtil cmmEnvUtil) {
        this.kioskOptionMapper = kioskOptionMapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 상품목록 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskOptionList(KioskOptionVO kioskOptionVO, SessionInfoVO sessionInfoVO) {

        kioskOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskOptionVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            kioskOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

         /*
          단독매장의 경우 SALE_PRC_FG = '2'
          프랜차이즈의 경우, 상품 판매가 본사통제여부 조회하여
          본사통제구분이 '본사'일때, SALE_PRC_FG = '1'
          본사통제구분이 '매장'일때, SALE_PRC_FG = '2'
        */
        if("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            kioskOptionVO.setSalePrcFg("2");
        } else {

            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

            if( StringUtil.isEmpties(sessionInfoVO.getStoreCd())) { // 본사일때
                kioskOptionVO.setSalePrcFg("1");
            } else {                             // 매장일때
                if("1".equals(envstVal)) kioskOptionVO.setSalePrcFg("1");
                else                     kioskOptionVO.setSalePrcFg("2");
            }
        }

        return kioskOptionMapper.getKioskOptionList(kioskOptionVO);
    }

    /** 키오스크옵션 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskOptionDetailList(KioskOptionVO kioskOptionVO, SessionInfoVO sessionInfoVO) {

        kioskOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskOptionVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            kioskOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

         /*
          단독매장의 경우 SALE_PRC_FG = '2'
          프랜차이즈의 경우, 상품 판매가 본사통제여부 조회하여
          본사통제구분이 '본사'일때, SALE_PRC_FG = '1'
          본사통제구분이 '매장'일때, SALE_PRC_FG = '2'
        */
        if("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            kioskOptionVO.setSalePrcFg("2");
        } else {

            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

            if( StringUtil.isEmpties(sessionInfoVO.getStoreCd())) { // 본사일때
                kioskOptionVO.setSalePrcFg("1");
            } else {                             // 매장일때
                if("1".equals(envstVal)) kioskOptionVO.setSalePrcFg("1");
                else                     kioskOptionVO.setSalePrcFg("2");
            }
        }

        return kioskOptionMapper.getKioskOptionDetailList(kioskOptionVO);
    }

    /** 키오스크옵션 삭제 */
    @Override
    public int getKioskOptionSaveDelete(KioskOptionVO[] kioskOptionVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(KioskOptionVO kioskOptionVO : kioskOptionVOs) {

            kioskOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskOptionVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                kioskOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            procCnt = kioskOptionMapper.getKioskOptionSaveDelete(kioskOptionVO);
        }

        return procCnt;
    }

    /** 키오스크옵션 저장 */
    @Override
    public int getKioskOptionSaveUpdate(KioskOptionVO[] kioskOptionVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(KioskOptionVO kioskOptionVO : kioskOptionVOs) {

            kioskOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskOptionVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                kioskOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskOptionVO.setModDt(currentDt);
            kioskOptionVO.setModId(sessionInfoVO.getUserId());

            procCnt = kioskOptionMapper.getKioskOptionSaveUpdate(kioskOptionVO);
        }

        return procCnt;
    }

    /** 키오스크옵션 상품등록 팝업 - 상품목록 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskOptionProdList(KioskOptionVO kioskOptionVO, SessionInfoVO sessionInfoVO) {

        kioskOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskOptionVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            kioskOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

         /*
          단독매장의 경우 SALE_PRC_FG = '2'
          프랜차이즈의 경우, 상품 판매가 본사통제여부 조회하여
          본사통제구분이 '본사'일때, SALE_PRC_FG = '1'
          본사통제구분이 '매장'일때, SALE_PRC_FG = '2'
        */
        if("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            kioskOptionVO.setSalePrcFg("2");
        } else {

            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

            if( StringUtil.isEmpties(sessionInfoVO.getStoreCd())) { // 본사일때
                kioskOptionVO.setSalePrcFg("1");
            } else {                             // 매장일때
                if("1".equals(envstVal)) kioskOptionVO.setSalePrcFg("1");
                else                     kioskOptionVO.setSalePrcFg("2");
            }
        }

        return kioskOptionMapper.getKioskOptionProdList(kioskOptionVO);
    }

    /** 키오스크옵션 상품등록 팝업 - 표기순번 조회 */
    @Override
    public DefaultMap<String> getKioskOptionProdDispSeq(KioskOptionVO kioskOptionVO,  SessionInfoVO sessionInfoVO) {

        DefaultMap<String> resultMap = new DefaultMap<String>();

        kioskOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskOptionVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            kioskOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        resultMap = kioskOptionMapper.getKioskOptionProdDispSeq(kioskOptionVO);

        return resultMap;
    }

    /** 키오스크옵션 상품등록 팝업 - 키오스크옵션 저장 */
    @Override
    public int getKioskOptionProdSave(KioskOptionVO[] kioskOptionVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

//        KioskOptionVO kioskOptionVO = new kioskOptionVO();
//
//        // 표기순번 (자동 채번)
//        int DispSeq = kioskOptionMapper.getKioskOptionProdDispSeq(kioskOptionVO);
//        kioskOptionVO.setDispSeq(DispSeq);

        for(KioskOptionVO kioskOptionVO : kioskOptionVOs) {

            kioskOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskOptionVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                kioskOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskOptionVO.setRegDt(currentDt);
            kioskOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskOptionVO.setModDt(currentDt);
            kioskOptionVO.setModId(sessionInfoVO.getUserId());

            procCnt = kioskOptionMapper.getKioskOptionProdSave(kioskOptionVO);
        }

        return procCnt;
    }
}