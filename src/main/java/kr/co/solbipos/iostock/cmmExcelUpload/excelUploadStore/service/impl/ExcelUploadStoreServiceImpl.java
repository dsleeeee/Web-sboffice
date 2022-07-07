package kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreService;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ExcelUploadStoreServiceImpl.java
 * @Description : 공통팝업 실사/조정/폐기 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("excelUploadStoreService")
public class ExcelUploadStoreServiceImpl implements ExcelUploadStoreService {
    private final ExcelUploadStoreMapper excelUploadStoreMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ExcelUploadStoreServiceImpl(ExcelUploadStoreMapper excelUploadStoreMapper, MessageService messageService) {
        this.excelUploadStoreMapper = excelUploadStoreMapper;
        this.messageService = messageService;
    }

    /** 엑셀업로드 - 엑셀업로드 저장 */
    @Override
    public int saveExcelUpload(ExcelUploadStoreVO[] excelUploadStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        int returnResult = 0;
        int i = 1;

        String currentDt = currentDateTimeString();

        for (ExcelUploadStoreVO excelUploadStoreVO : excelUploadStoreVOs) {
            excelUploadStoreVO.setSessionId(sessionInfoVO.getSessionId());
            excelUploadStoreVO.setRegId(sessionInfoVO.getUserId());
            excelUploadStoreVO.setRegDt(currentDt);
            excelUploadStoreVO.setModId(sessionInfoVO.getUserId());
            excelUploadStoreVO.setModDt(currentDt);
            excelUploadStoreVO.setSeq(i);

            excelUploadStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                excelUploadStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            excelUploadStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

            // 상품코드 or 바코드 "'" 문자 제거
            if (excelUploadStoreVO.getProdBarcdCd() != null && !"".equals(excelUploadStoreVO.getProdBarcdCd())) {
                if(excelUploadStoreVO.getProdBarcdCd().contains("'")) {
                    excelUploadStoreVO.setProdBarcdCd(excelUploadStoreVO.getProdBarcdCd().replaceAll("'",""));
                }
            }

            // 상품코드 "'" 문자 제거
            if (excelUploadStoreVO.getProdCd() != null && !"".equals(excelUploadStoreVO.getProdCd())) {
                if(excelUploadStoreVO.getProdCd().contains("'")) {
                    excelUploadStoreVO.setProdCd(excelUploadStoreVO.getProdCd().replaceAll("'",""));
                }
            }

            // 바코드 "'" 문자 제거
            if (excelUploadStoreVO.getBarcdCd() != null && !"".equals(excelUploadStoreVO.getBarcdCd())) {
                if(excelUploadStoreVO.getBarcdCd().contains("'")) {
                    excelUploadStoreVO.setBarcdCd(excelUploadStoreVO.getBarcdCd().replaceAll("'",""));
                }
            }

            result = excelUploadStoreMapper.insertExcelUpload(excelUploadStoreVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
            i++;
        }

        return returnResult;
    }

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    @Override
    public int saveUpdateProdCd(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        excelUploadStoreVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다.
        result = excelUploadStoreMapper.updateExcelUploadProdCd(excelUploadStoreVO);

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다.
        result = excelUploadStoreMapper.updateExcelUploadBarcdCd(excelUploadStoreVO);

        // 거래처 수불인 경우 UPRC 를 LAST_COST_UPRC 로 UPDATE 한다.
        if(excelUploadStoreVO.getUploadFg().equals("vendrOrder")) {
            result = excelUploadStoreMapper.updateExcelUploadUprc(excelUploadStoreVO);
        }

        return result;
    }

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    @Override
    public int deleteExcelUpload(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadStoreVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadStoreVO.setRegId(sessionInfoVO.getUserId());
        excelUploadStoreVO.setRegDt(currentDt);
        excelUploadStoreVO.setModId(sessionInfoVO.getUserId());
        excelUploadStoreVO.setModDt(currentDt);

        excelUploadStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        result = excelUploadStoreMapper.deleteExcelUpload(excelUploadStoreVO);
//        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    @Override
    public List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO) {
        excelUploadStoreVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return excelUploadStoreMapper.getExcelUploadErrInfoList(excelUploadStoreVO);
    }

}