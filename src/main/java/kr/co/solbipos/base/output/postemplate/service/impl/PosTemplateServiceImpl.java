package kr.co.solbipos.base.output.postemplate.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.output.postemplate.service.PosTemplateService;
import kr.co.solbipos.base.output.postemplate.service.PosTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PosTemplateServiceImpl.java
 * @Description : 기초관리 > 출력물관리 > 포스출력물관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("posService")
public class PosTemplateServiceImpl implements PosTemplateService {
    
    private final PosTemplateMapper posTemplateMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PosTemplateServiceImpl(PosTemplateMapper posTemplateMapper, MessageService messageService) {
        this.posTemplateMapper = posTemplateMapper;
        this.messageService = messageService;
    }

    /** 출력물종류 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintTypeList(PosTemplateVO posTemplateVO) {
        return posTemplateMapper.getPrintTypeList(posTemplateVO);
    }
    
    /** 출력물코드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintCodeList(PosTemplateVO posTemplateVO) {
        return posTemplateMapper.getPrintCodeList(posTemplateVO);
    }

    /** 출력물템플릿 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosTemplateList(PosTemplateVO posTemplateVO) {
        // 실제출력물 없는경우 대비해서 저장처리
        posTemplateMapper.insertPosTemplatePrint(posTemplateVO);

        return posTemplateMapper.getPosTemplateList(posTemplateVO);
    }

    /** 출력물템플릿 목록 저장 */
    @Override
    public int savePosTemplateList(PosTemplateVO[] posTemplateVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( PosTemplateVO posTemplateVO : posTemplateVOs ) {

            // 소속구분 설정
            posTemplateVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            posTemplateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            posTemplateVO.setStoreCd(sessionInfoVO.getStoreCd());

            posTemplateVO.setRegDt(currentDt);
            posTemplateVO.setRegId(sessionInfoVO.getUserId());
            posTemplateVO.setModDt(currentDt);
            posTemplateVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( posTemplateVO.getStatus() == GridDataFg.INSERT ) {
                result += posTemplateMapper.insertPosTemplateList(posTemplateVO);
                // 수정
            } else if ( posTemplateVO.getStatus() == GridDataFg.UPDATE ) {
                result += posTemplateMapper.updatePosTemplateList(posTemplateVO);
                // 삭제
            } else if ( posTemplateVO.getStatus() == GridDataFg.DELETE ) {
                result += posTemplateMapper.deletePosTemplateList(posTemplateVO);
            }

        }

        if ( result == posTemplateVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 출력물템플릿 저장 */
    @Override
    public int savePosTemplate(PosTemplateVO posTemplateVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        // 소속구분 설정
        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        posTemplateVO.setOrgnFg(orgnFg);
        posTemplateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posTemplateVO.setStoreCd(sessionInfoVO.getStoreCd());

        posTemplateVO.setRegDt(currentDt);
        posTemplateVO.setRegId(sessionInfoVO.getUserId());
        posTemplateVO.setModDt(currentDt);
        posTemplateVO.setModId(sessionInfoVO.getUserId());

        // 본사 또는 매장에서 등록한 템플릿 만 업데이트 처리
        if (orgnFg.equals(posTemplateVO.getTempltRegFg())) {
            result = posTemplateMapper.savePosTemplate(posTemplateVO);
        }

        if ( result >= 0 ) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 출력물템플릿 실제출력물 적용 */
    @Override
    public int updatePosTemplatePrint(PosTemplateVO posTemplateVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        // 소속구분 설정
        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        posTemplateVO.setOrgnFg(orgnFg);
        posTemplateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posTemplateVO.setStoreCd(sessionInfoVO.getStoreCd());

        posTemplateVO.setModDt(currentDt);
        posTemplateVO.setModId(sessionInfoVO.getUserId());

        // 선택된 템플릿 내용으로 실제출력물에 적용
        result = posTemplateMapper.updatePosTemplatePrint(posTemplateVO);

        if ( result >= 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 출력물템플릿 매장적용 */
    @Override
    public int applyToStoreTemplate(PosTemplateVO posTemplateVO, SessionInfoVO sessionInfoVO) {

        posTemplateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posTemplateVO.setModId(sessionInfoVO.getUserId());
        // 프로시저호출 : 호출하면서 VO에 결과값 담겨있다.
        posTemplateMapper.updatePosTemplateForStoreFromHq(posTemplateVO);
        // 결과값 판단.
        if ( "0000".equals(posTemplateVO.getResult()) ) {
            return 1;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

}
