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

    /** 알림톡 키값 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAlimtalkKeyColList(SessionInfoVO sessionInfoVO) {

        return alimtalkSendTypeMapper.getAlimtalkKeyColList(sessionInfoVO);
    }

    /** 알림톡 템플릿 치환값 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAlimtalkTemplateChangeKeyColList(SessionInfoVO sessionInfoVO) {

        return alimtalkSendTypeMapper.getAlimtalkTemplateChangeKeyColList(sessionInfoVO);
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

            // 현재 사용여부만 변경가능함으로
            if(alimtalkSendTypeVO.getUseYn().equals("Y")) {
                System.out.println("WEB_ALIMTALK >>> 알림톡 설정 >>> 전송유형 상세 저장 >>> 사용여부 : " + alimtalkSendTypeVO.getUseYn());

                // 전송유형 설정에 등록된 템플릿 조회
                String alimtalkSendTypeTemplateCd = alimtalkSendTypeMapper.getAlimtalkSendTypeTemplateCd(alimtalkSendTypeVO);
                System.out.println("WEB_ALIMTALK >>> 알림톡 설정 >>> 전송유형 상세 저장 >>> 전송유형 설정에 등록된 템플릿 : " + alimtalkSendTypeTemplateCd);

                // 첫번째 템플릿 초기설정
                if(alimtalkSendTypeTemplateCd == null) {

                    // 첫번째 템플릿 조회
                    List<DefaultMap<String>> templateCdList = alimtalkSendTypeMapper.getTemplateCd(alimtalkSendTypeVO);
                    System.out.println("WEB_ALIMTALK >>> 알림톡 설정 >>> 전송유형 상세 저장 >>> 첫번째 템플릿 templateCd : " + templateCdList.get(0).getStr("templateCd"));
                    System.out.println("WEB_ALIMTALK >>> 알림톡 설정 >>> 전송유형 상세 저장 >>> 첫번째 템플릿 templateGrpFg : " + templateCdList.get(0).getStr("templateGrpFg"));

                    alimtalkSendTypeVO.setTemplateCd(templateCdList.get(0).getStr("templateCd"));
                    alimtalkSendTypeVO.setTemplateGrpFg(templateCdList.get(0).getStr("templateGrpFg"));

                    // 전송유형 : 대기 -> 대기중 일때만
                    if(alimtalkSendTypeVO.getSendTypeCd().equals("001") && alimtalkSendTypeVO.getSendTypeDtlCd().equals("02")) {
                        alimtalkSendTypeVO.setSendPeriodFg("02");
                        alimtalkSendTypeVO.setSendPeriod("0");
                    } else {
                        alimtalkSendTypeVO.setSendPeriodFg("01");
                        alimtalkSendTypeVO.setSendPeriod("0");
                    }

                    procCnt = alimtalkSendTypeMapper.getAlimtalkSendTypeDetailTemplateSaveMerge(alimtalkSendTypeVO);
                }
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

    /** 알림톡 전송유형 - 템플릿 목록 조회 */
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
