package kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadService;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("excelUploadService")
public class ExcelUploadServiceImpl implements ExcelUploadService {
    private final ExcelUploadMapper excelUploadMapper;
    private final MessageService messageService;

    @Autowired
    public ExcelUploadServiceImpl(ExcelUploadMapper excelUploadMapper, MessageService messageService) {
        this.excelUploadMapper = excelUploadMapper;
        this.messageService = messageService;
    }


    /** 엑셀업로드 - 엑셀업로드 저장 */
    @Override
    public int saveExcelUpload(ExcelUploadVO[] excelUploadVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        int returnResult = 0;

        String currentDt = currentDateTimeString();

        for (ExcelUploadVO excelUploadVO : excelUploadVOs) {
            excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
            excelUploadVO.setRegId(sessionInfoVO.getUserId());
            excelUploadVO.setRegDt(currentDt);
            excelUploadVO.setModId(sessionInfoVO.getUserId());
            excelUploadVO.setModDt(currentDt);

            excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                excelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

            // 상품코드 or 바코드 "'" 문자 제거
            if (excelUploadVO.getProdBarcdCd() != null && !"".equals(excelUploadVO.getProdBarcdCd())) {
                if(excelUploadVO.getProdBarcdCd().contains("'")) {
                    excelUploadVO.setProdBarcdCd(excelUploadVO.getProdBarcdCd().replaceAll("'",""));
                }
            }

            // 상품코드 "'" 문자 제거
            if (excelUploadVO.getProdCd() != null && !"".equals(excelUploadVO.getProdCd())) {
                if(excelUploadVO.getProdCd().contains("'")) {
                    excelUploadVO.setProdCd(excelUploadVO.getProdCd().replaceAll("'",""));
                }
            }

            // 바코드 "'" 문자 제거
            if (excelUploadVO.getBarcdCd() != null && !"".equals(excelUploadVO.getBarcdCd())) {
                if(excelUploadVO.getBarcdCd().contains("'")) {
                    excelUploadVO.setBarcdCd(excelUploadVO.getBarcdCd().replaceAll("'",""));
                }
            }

            result = excelUploadMapper.insertExcelUpload(excelUploadVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
        }

        return returnResult;
    }


    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    @Override
    public int saveUpdateProdCd(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다.
        result = excelUploadMapper.updateExcelUploadProdCd(excelUploadVO);

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다.
        result = excelUploadMapper.updateExcelUploadBarcdCd(excelUploadVO);

        // 거래처 수불인 경우 UPRC 를 LAST_COST_UPRC 로 UPDATE 한다.
        if(excelUploadVO.getUploadFg().equals("vendrOrder")) {
            result = excelUploadMapper.updateExcelUploadUprc(excelUploadVO);
        }

        return result;
    }


    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    @Override
    public int deleteExcelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadVO.setRegId(sessionInfoVO.getUserId());
        excelUploadVO.setRegDt(currentDt);
        excelUploadVO.setModId(sessionInfoVO.getUserId());
        excelUploadVO.setModDt(currentDt);

        excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        result = excelUploadMapper.deleteExcelUpload(excelUploadVO);
//        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    @Override
    public List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO) {
        excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return excelUploadMapper.getExcelUploadErrInfoList(excelUploadVO);
    }

}
