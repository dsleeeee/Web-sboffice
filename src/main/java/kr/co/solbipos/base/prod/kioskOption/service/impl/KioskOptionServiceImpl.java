package kr.co.solbipos.base.prod.kioskOption.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionService;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskOptionServiceImpl(KioskOptionMapper kioskOptionMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.kioskOptionMapper = kioskOptionMapper;
        this.messageService = messageService;
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

    /** 키오스크옵션 옵션상품 매장적용 */
    @Override
    public int saveStoreOptionProd(KioskOptionVO[] kioskOptionVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskOptionVO kioskOptionVO : kioskOptionVOs) {

            kioskOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskOptionVO.setRegDt(currentDt);
            kioskOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskOptionVO.setModDt(currentDt);
            kioskOptionVO.setModId(sessionInfoVO.getUserId());

            // 매장에서 사용중인 기존 옵션상품 삭제
            kioskOptionMapper.deleteStoreOptionProd(kioskOptionVO);

            // 본사에서 사용중인 옵션상품 매장등록
            result = kioskOptionMapper.insertStoreOptionProd(kioskOptionVO);
            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        return result;
    }
}