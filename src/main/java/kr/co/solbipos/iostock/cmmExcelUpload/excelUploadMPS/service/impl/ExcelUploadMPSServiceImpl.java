package kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSService;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ExcelUploadMPSServiceImpl.java
 * @Description : 공통팝업 수불/재고 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("excelUploadMPSService")
public class ExcelUploadMPSServiceImpl implements ExcelUploadMPSService {
    private final ExcelUploadMPSMapper excelUploadMPSMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ExcelUploadMPSServiceImpl(ExcelUploadMPSMapper excelUploadMPSMapper, MessageService messageService) {
        this.excelUploadMPSMapper = excelUploadMPSMapper;
        this.messageService = messageService;
    }

    /** 엑셀업로드 - 엑셀업로드 저장 */
    @Override
    public int saveExcelUpload(ExcelUploadMPSVO[] excelUploadMPSVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        int returnResult = 0;

        String currentDt = currentDateTimeString();

        for (ExcelUploadMPSVO excelUploadMPSVO : excelUploadMPSVOs) {
            excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
            excelUploadMPSVO.setRegId(sessionInfoVO.getUserId());
            excelUploadMPSVO.setRegDt(currentDt);
            excelUploadMPSVO.setModId(sessionInfoVO.getUserId());
            excelUploadMPSVO.setModDt(currentDt);

            excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

            result = excelUploadMPSMapper.insertExcelUpload(excelUploadMPSVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
        }

        return returnResult;
    }

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    @Override
    public int saveUpdateProdCd(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다.
        result = excelUploadMPSMapper.updateExcelUploadProdCd(excelUploadMPSVO);

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다.
        result = excelUploadMPSMapper.updateExcelUploadBarcdCd(excelUploadMPSVO);

        // 거래처 수불인 경우 UPRC 를 LAST_COST_UPRC 로 UPDATE 한다.
        if(excelUploadMPSVO.getUploadFg().equals("vendrOrder")) {
            result = excelUploadMPSMapper.updateExcelUploadUprc(excelUploadMPSVO);
        }

        return result;
    }

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    @Override
    public int deleteExcelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadMPSVO.setRegId(sessionInfoVO.getUserId());
        excelUploadMPSVO.setRegDt(currentDt);
        excelUploadMPSVO.setModId(sessionInfoVO.getUserId());
        excelUploadMPSVO.setModDt(currentDt);

        excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        result = excelUploadMPSMapper.deleteExcelUpload(excelUploadMPSVO);
//        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    @Override
    public List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO) {
        excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return excelUploadMPSMapper.getExcelUploadErrInfoList(excelUploadMPSVO);
    }

}