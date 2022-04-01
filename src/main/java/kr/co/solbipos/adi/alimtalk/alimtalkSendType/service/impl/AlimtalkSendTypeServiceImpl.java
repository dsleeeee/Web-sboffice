package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendType.service.AlimtalkSendTypeVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : AlimtalkSendTypeServiceImpl.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송유형
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("alimtalkSendTypeService")
@Transactional
public class AlimtalkSendTypeServiceImpl implements AlimtalkSendTypeService {
    private final AlimtalkSendTypeMapper alimtalkSendTypeMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendTypeServiceImpl(AlimtalkSendTypeMapper alimtalkSendTypeMapper) {
        this.alimtalkSendTypeMapper = alimtalkSendTypeMapper;
    }

    /** 알림톡 전송유형 - 계정정보 체크 조회 */
    @Override
    public DefaultMap<String> getAlimtalkIdRegisterChk(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendTypeMapper.getAlimtalkIdRegisterChk(alimtalkSendTypeVO);
    }

    /** 알림톡 전송유형 - 전송유형 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkSendTypeList(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        return alimtalkSendTypeMapper.getAlimtalkSendTypeList(alimtalkSendTypeVO);
    }

    /** 알림톡 전송유형 - 전송유형 상세 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkSendTypeDetailList(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendTypeMapper.getAlimtalkSendTypeDetailList(alimtalkSendTypeVO);
    }

    /** 알림톡 전송유형 - 전송유형 상세 저장 */
    @Override
    public int getAlimtalkSendTypeDetailSave(AlimtalkSendTypeVO[] alimtalkSendTypeVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(AlimtalkSendTypeVO alimtalkSendTypeVO : alimtalkSendTypeVOs) {

            alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            alimtalkSendTypeVO.setRegDt(currentDt);
            alimtalkSendTypeVO.setRegId(sessionInfoVO.getUserId());
            alimtalkSendTypeVO.setModDt(currentDt);
            alimtalkSendTypeVO.setModId(sessionInfoVO.getUserId());

            if(alimtalkSendTypeVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = alimtalkSendTypeMapper.getAlimtalkSendTypeDetailSaveMerge(alimtalkSendTypeVO);

            }
        }

        return procCnt;
    }

    /** 알림톡 전송유형 - 템플릿 상세 조회 */
    @Override
    public DefaultMap<String> getAlimtalkSendTypeDetailTemplate(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendTypeMapper.getAlimtalkSendTypeDetailTemplate(alimtalkSendTypeVO);
    }

    /** 알림톡 전송유형 - 템플릿 상세 저장 */
    @Override
    public int getAlimtalkSendTypeDetailTemplateSave(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        alimtalkSendTypeVO.setRegDt(currentDt);
        alimtalkSendTypeVO.setRegId(sessionInfoVO.getUserId());
        alimtalkSendTypeVO.setModDt(currentDt);
        alimtalkSendTypeVO.setModId(sessionInfoVO.getUserId());

        procCnt = alimtalkSendTypeMapper.getAlimtalkSendTypeDetailTemplateSaveMerge(alimtalkSendTypeVO);

        return procCnt;
    }

    /** 템플릿 선택 팝업 - 템플릿 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkSendTypeDetailTemplateList(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendTypeMapper.getAlimtalkSendTypeDetailTemplateList(alimtalkSendTypeVO);
    }

    /** 알림톡 계정등록 팝업 - 계정정보 체크 조회 */
    @Override
    public DefaultMap<String> getAlimtalkIdRegisterAllChk(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendTypeMapper.getAlimtalkIdRegisterAllChk(alimtalkSendTypeVO);
    }

    /** 알림톡 계정등록 팝업 - 그룹-계정정보 체크 조회 */
    @Override
    public DefaultMap<String> getAlimtalkRegisterGroupChk(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        alimtalkSendTypeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendTypeMapper.getAlimtalkRegisterGroupChk(alimtalkSendTypeVO);
    }

    /** 알림톡 계정등록 팝업 - 사업자 카테고리 조회 */
    @Override
    public List<DefaultMap<Object>> getCategoryCodeComboList(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        return alimtalkSendTypeMapper.getCategoryCodeComboList(alimtalkSendTypeVO);
    }

    /** 알림톡 계정등록 팝업 - 키값 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAlimtalkKeyColList(SessionInfoVO sessionInfoVO) {

        return alimtalkSendTypeMapper.getAlimtalkKeyColList(sessionInfoVO);
    }

    /** 알림톡 계정등록 팝업 - 알림톡 계정정보 저장 insert */
    @Override
    public int getAlimtalkSenderSaveInsert(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        alimtalkSendTypeVO.setRegDt(currentDt);
        alimtalkSendTypeVO.setModDt(currentDt);

        procCnt = alimtalkSendTypeMapper.getAlimtalkSenderSaveInsert(alimtalkSendTypeVO);

        return procCnt;
    }

    /** 알림톡 계정등록 팝업 - 알림톡 계정정보 저장 update */
    @Override
    public int getAlimtalkSenderSaveUpdate(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        alimtalkSendTypeVO.setModDt(currentDt);

        procCnt = alimtalkSendTypeMapper.getAlimtalkSenderSaveUpdate(alimtalkSendTypeVO);

        return procCnt;
    }

    /** 알림톡 계정등록 팝업 - 알림톡 그룹-계정정보 저장 insert */
    @Override
    public int getAlimtalkSenderGroupSaveInsert(AlimtalkSendTypeVO alimtalkSendTypeVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        alimtalkSendTypeVO.setRegDt(currentDt);
        alimtalkSendTypeVO.setModDt(currentDt);

        procCnt = alimtalkSendTypeMapper.getAlimtalkSenderGroupSaveInsert(alimtalkSendTypeVO);

        return procCnt;
    }
}
