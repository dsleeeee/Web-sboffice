package kr.co.solbipos.sys.bill.template.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.bill.template.service.TemplateService;
import kr.co.solbipos.sys.bill.template.service.TemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TemplateService.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("templateService")
public class TemplateServiceImpl implements TemplateService {
    
    @Autowired
    TemplateMapper templateMapper;
    @Autowired
    MessageService messageService;

    /** 출력물종류 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintTypeList(TemplateVO templateVO) {
        return templateMapper.getPrintTypeList(templateVO);
    }
    
    /** 출력물코드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintCodeList(TemplateVO templateVO) {
        return templateMapper.getPrintCodeList(templateVO);
    }

    /** 출력물템플릿 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTemplateList(TemplateVO templateVO) {
        return templateMapper.getTemplateList(templateVO);
    }

    /** 출력물템플릿 목록 저장 */
    @Override
    public int saveTemplateList(TemplateVO[] templateVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( TemplateVO templateVO : templateVOs ) {

            templateVO.setRegDt(currentDt);
            templateVO.setRegId(sessionInfoVO.getUserId());
            templateVO.setModDt(currentDt);
            templateVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( templateVO.getStatus() == GridDataFg.INSERT ) {
                result += templateMapper.insertTemplateList(templateVO);
                // 수정
            } else if ( templateVO.getStatus() == GridDataFg.UPDATE ) {
                result += templateMapper.updateTemplateList(templateVO);
                // 삭제
            } else if ( templateVO.getStatus() == GridDataFg.DELETE ) {
                result += templateMapper.deleteTemplateList(templateVO);
            }

        }

        if ( result == templateVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 출력물템플릿 저장 */
    @Override
    public int saveTemplate(TemplateVO templateVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        templateVO.setRegDt(currentDt);
        templateVO.setRegId(sessionInfoVO.getUserId());
        templateVO.setModDt(currentDt);
        templateVO.setModId(sessionInfoVO.getUserId());

        result = templateMapper.saveTemplate(templateVO);

        if ( result >= 0 ) {
            // 본사/매장에 존재하는 템플릿도 동시에 업데이트 처리한다.
            templateMapper.updateTemplateForHq(templateVO);
            templateMapper.updateTemplateForStore(templateVO);

            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 미적용 본사/단독매장 조회 */
    @Override
    public List<DefaultMap<String>> getUnUsedList(TemplateVO templateVO) {
        return templateMapper.getUnUsedList(templateVO);
    }

    /** 미적용 본사/단독매장 저장 */
    @Override
    public int saveUnUsedList(TemplateVO[] templateVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( TemplateVO templateVO : templateVOs ) {

            templateVO.setRegDt(currentDt);
            templateVO.setRegId(sessionInfoVO.getUserId());
            templateVO.setModDt(currentDt);
            templateVO.setModId(sessionInfoVO.getUserId());
            // 미적용 본사/단독매장 적용
            result = templateMapper.insertUnUsedList(templateVO);
            // 실제출력물코드 생성
            result += templateMapper.insertUnUsedPrintCode(templateVO);
        }

        if ( result >= 0 ) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }



}
