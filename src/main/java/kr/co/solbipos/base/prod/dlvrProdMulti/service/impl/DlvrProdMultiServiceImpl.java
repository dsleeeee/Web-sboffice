package kr.co.solbipos.base.prod.dlvrProdMulti.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.dlvrProdMulti.service.DlvrProdMultiService;
import kr.co.solbipos.base.prod.dlvrProdMulti.service.DlvrProdMultiVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DlvrProdMultiServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 맵핑2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.25  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dlvrProdMultiService")
public class DlvrProdMultiServiceImpl implements DlvrProdMultiService {

    private final DlvrProdMultiMapper dlvrProdMultiMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public DlvrProdMultiServiceImpl(MessageService messageService, DlvrProdMultiMapper dlvrProdMultiMapper) {
        this.messageService = messageService;
        this.dlvrProdMultiMapper = dlvrProdMultiMapper;
    }

    /** 배달시스템 상품 명칭 매핑2 - 배달앱구분코드 */
    @Override
    public List<DefaultMap<String>> getDlvrColList(@RequestBody DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO) {
        return dlvrProdMultiMapper.getDlvrColList(dlvrProdMultiVO);
    }

    /**  배달시스템 상품 명칭 매핑2 - 상품목록조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        // 배달앱 구분코드 array 값 세팅
        dlvrProdMultiVO.setArrDlvrCol(dlvrProdMultiVO.getDlvrCol().split(","));

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDlvrCol = "";
        String arrDlvrCol[] = dlvrProdMultiVO.getDlvrCol().split(",");
        for(int i=0; i < arrDlvrCol.length; i++) {
            pivotDlvrCol += (pivotDlvrCol.equals("") ? "" : ",") + "'"+arrDlvrCol[i]+"'"+" AS DLVR_PROD_NM_"+arrDlvrCol[i];
        }
        dlvrProdMultiVO.setPivotDlvrCol(pivotDlvrCol);

        dlvrProdMultiVO.setUserId(sessionInfoVO.getUserId());

        return dlvrProdMultiMapper.getProdList(dlvrProdMultiVO);
    }

    /** 배달시스템 상품 명칭 매핑2 - 전체 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getDlvrProdMultiExcelList(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        // 배달앱 구분코드 array 값 세팅
        dlvrProdMultiVO.setArrDlvrCol(dlvrProdMultiVO.getDlvrCol().split(","));

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDlvrCol = "";
        String arrDlvrCol[] = dlvrProdMultiVO.getDlvrCol().split(",");
        for(int i=0; i < arrDlvrCol.length; i++) {
            pivotDlvrCol += (pivotDlvrCol.equals("") ? "" : ",") + "'"+arrDlvrCol[i]+"'"+" AS DLVR_PROD_NM_"+arrDlvrCol[i];
        }
        dlvrProdMultiVO.setPivotDlvrCol(pivotDlvrCol);

        dlvrProdMultiVO.setUserId(sessionInfoVO.getUserId());

        return dlvrProdMultiMapper.getDlvrProdMultiExcelList(dlvrProdMultiVO);

    }

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 저장 */
    @Override
    public int saveDlvrProdMultiNm(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( DlvrProdMultiVO dlvrProdMultiVO : dlvrProdMultiVOs ) {

            dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            dlvrProdMultiVO.setRegDt(currentDt);
            dlvrProdMultiVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdMultiVO.setModDt(currentDt);
            dlvrProdMultiVO.setModId(sessionInfoVO.getUserId());

            if ( dlvrProdMultiVO.getStatus() == GridDataFg.UPDATE ) {

                // 1. 기존 배달상품명칭 삭제
                dlvrProdMultiMapper.deleteDlvrProdMultiNm(dlvrProdMultiVO);


                // 2. 값이 있는 경우, 배달상품명칭 INSERT
                if(dlvrProdMultiVO.getDlvrProdNm() != null && dlvrProdMultiVO.getDlvrProdNm() != "" && dlvrProdMultiVO.getDlvrProdNm().length() > 0){

                    if(dlvrProdMultiVO.getSeq() == null || dlvrProdMultiVO.getSeq().toString() == ""){
                        dlvrProdMultiVO.setSeq(1);
                    }

                    result = dlvrProdMultiMapper.insertDlvrProdMultiNm(dlvrProdMultiVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                procCnt ++;
            }

        }

        if ( procCnt == dlvrProdMultiVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 복사 */
    @Override
    public int copyDlvrProdMultiNm(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        dlvrProdMultiVO.setRegDt(currentDt);
        dlvrProdMultiVO.setRegId(sessionInfoVO.getUserId());
        dlvrProdMultiVO.setModDt(currentDt);
        dlvrProdMultiVO.setModId(sessionInfoVO.getUserId());

        // 필수 값 체크
        if(dlvrProdMultiVO.getOriginalStoreFg() != null && !"".equals(dlvrProdMultiVO.getOriginalStoreFg()) &&
            dlvrProdMultiVO.getOriginalStoreCd() != null && !"".equals(dlvrProdMultiVO.getOriginalStoreCd()) &&
            dlvrProdMultiVO.getTargetStoreCd() != null && !"".equals(dlvrProdMultiVO.getTargetStoreCd())){

            // 1. 배달상품명칭 복사 전 기존데이터 삭제
            dlvrProdMultiMapper.deleteStoreDlvrProdMultiNm(dlvrProdMultiVO);

            // 2. 기준매장의 배달상품명칭을 복사하여 적용대상매장에 등록
            result = dlvrProdMultiMapper.copyStoreDlvrProdMultiNm(dlvrProdMultiVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }else{
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 팝업 조회 */
    @Override
    public List<DefaultMap<Object>> getDlvrProdMultiNmStoreRegistList(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO) {

        dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return dlvrProdMultiMapper.getDlvrProdMultiNmStoreRegistList(dlvrProdMultiVO);
    }

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 저장 */
    @Override
    public int getDlvrProdMultiNmStoreRegistSave(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(DlvrProdMultiVO dlvrProdMultiVO : dlvrProdMultiVOs) {

            dlvrProdMultiVO.setRegDt(currentDt);
            dlvrProdMultiVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdMultiVO.setModDt(currentDt);
            dlvrProdMultiVO.setModId(sessionInfoVO.getUserId());

            dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            String selectProdCd = "";
            String arrSelectProdCd[] = dlvrProdMultiVO.getSelectProdCd().split(",");
            for(int i=0; i < arrSelectProdCd.length; i++) {
                selectProdCd += (selectProdCd.equals("") ? "" : ",") + "'"+arrSelectProdCd[i]+"'";
            }
            dlvrProdMultiVO.setSelectProdCd(selectProdCd);

            // 삭제
            procCnt = dlvrProdMultiMapper.getDlvrProdMultiNmStoreRegistSaveDelete(dlvrProdMultiVO);

            // 등록
            procCnt = dlvrProdMultiMapper.getDlvrProdMultiNmStoreRegistSaveInsert(dlvrProdMultiVO);
        }

        return procCnt;
    }

    /** 배달시스템 상품 명칭 매핑2 - 엑셀 업로드 전 상품코드 유효여부 체크 */
    @Override
    public int chkDlvrProdMulti(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        // 상품코드 array 값 세팅
        dlvrProdMultiVO.setArrProdCdCol(dlvrProdMultiVO.getProdCdCol().split(","));

        return dlvrProdMultiMapper.chkDlvrProdMulti(dlvrProdMultiVO);
    }

    /** 배달시스템 상품 명칭 매핑2 - 데이터 임시 저장 */
    @Override
    public int getDlvrProdCdSaveInsert(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();
        int i = 1;

        for(DlvrProdMultiVO dlvrProdMultiVO : dlvrProdMultiVOs) {

            dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }

            dlvrProdMultiVO.setRegDt(currentDt);
            dlvrProdMultiVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdMultiVO.setModDt(currentDt);
            dlvrProdMultiVO.setModId(sessionInfoVO.getUserId());
            dlvrProdMultiVO.setSessionId(sessionInfoVO.getUserId());

            // 임시테이블 저장 전
            if(i == 1) {
                result = dlvrProdMultiMapper.getDlvrProdMultiNmMappingChkSaveDeleteAll(dlvrProdMultiVO);
            }

            if(dlvrProdMultiVO.getDlvrProdNm() != null && dlvrProdMultiVO.getDlvrProdNm() != "") {
                result = dlvrProdMultiMapper.getDlvrProdMultiNmMappingChkSaveInsert(dlvrProdMultiVO);
            }

            i++;
        }

        return result;
    }

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 중복 체크 */
    @Override
    public String getDlvrProdMultiNmMappingChk(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        int i = 1;
        String dlvrProdMultiNm = "";
        String currentDt = currentDateTimeString();

        for(DlvrProdMultiVO dlvrProdMultiVO : dlvrProdMultiVOs) {

            dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                dlvrProdMultiVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            dlvrProdMultiVO.setRegDt(currentDt);
            dlvrProdMultiVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdMultiVO.setModDt(currentDt);
            dlvrProdMultiVO.setModId(sessionInfoVO.getUserId());
            dlvrProdMultiVO.setSessionId(sessionInfoVO.getUserId());

            // 임시테이블 저장 전
            if(i == 1) {
                // 임시테이블 삭제
                procCnt = dlvrProdMultiMapper.getDlvrProdMultiNmMappingChkSaveDeleteAll(dlvrProdMultiVO);
            }

            if( dlvrProdMultiVO.getStatus() == GridDataFg.UPDATE ){
                procCnt = dlvrProdMultiMapper.getDlvrProdMultiNmMappingChkSaveInsert(dlvrProdMultiVO);
            }else {
                // 임시테이블 저장
                if (dlvrProdMultiVO.getDlvrProdNm() != null && dlvrProdMultiVO.getDlvrProdNm() != "") {
                    procCnt = dlvrProdMultiMapper.getDlvrProdMultiNmMappingChkSaveInsert(dlvrProdMultiVO);
                }
            }


            // 임시테이블 저장 끝
            if(i == dlvrProdMultiVOs.length) {
                // 명칭 중복 체크
                dlvrProdMultiNm = dlvrProdMultiMapper.getDlvrProdMultiNmMappingChk(dlvrProdMultiVO);
            }

            i++;
        }

        return dlvrProdMultiNm;
    }

    /** 배달시스템 상품 명칭 매핑2 - 입력값 확인 */
    @Override
    public int getChkProdCdChk(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
         if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        String[] str = dlvrProdMultiVO.getProdCdCol().split(",");
        int result = 1;

        if(dlvrProdMultiVO.getMappFg() != null &&
                (dlvrProdMultiVO.getMappFg().equals("I") || dlvrProdMultiVO.getMappFg().equals("S"))) {

            // 상품코드 array 값 세팅
            for(int i=0; i<str.length; i++) {
                dlvrProdMultiVO.setProdCd(str[i]);
                dlvrProdMultiVO.setSessionId(sessionInfoVO.getUserId());
                dlvrProdMultiMapper.getChkProdCdInsert(dlvrProdMultiVO);
            }
        }

        for(int i=0; i< str.length; i++){
            if(str[i] != null && str[i] != "") {
                dlvrProdMultiVO.setProdCd(str[i]);
                result = dlvrProdMultiMapper.getChkProdCdChk(dlvrProdMultiVO);
            }
            if (result == 0) {
                return result;
            }
        }
        return result;
    }

    /** 배달시스템 상품 명칭 매핑2 - 엑셀업로드(기존값 삭제) */
    @Override
    public int excelDeleteDlvrProdNm(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for ( DlvrProdMultiVO dlvrProdMultiVO : dlvrProdMultiVOs ) {

            dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }

            if (dlvrProdMultiVO.getMappFg() != null && dlvrProdMultiVO.getMappFg().equals("D")) {
                result = dlvrProdMultiMapper.excelDeleteDlvrProdNm(dlvrProdMultiVO);
            }

        }
        return result;
    }

    /** 배달시스템 상품 명칭 매핑2 - 엑셀업로드(저장) */
    @Override
    public int excelUploadsave(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( DlvrProdMultiVO dlvrProdMultiVO : dlvrProdMultiVOs ) {

            dlvrProdMultiVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrProdMultiVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                dlvrProdMultiVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            dlvrProdMultiVO.setRegDt(currentDt);
            dlvrProdMultiVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdMultiVO.setModDt(currentDt);
            dlvrProdMultiVO.setModId(sessionInfoVO.getUserId());

            if(dlvrProdMultiVO.getProdCd() != null && dlvrProdMultiVO.getProdCd() != ""
                    && dlvrProdMultiVO.getDlvrProdNm() !=null && dlvrProdMultiVO.getDlvrProdNm() != "") {
                if (dlvrProdMultiVO.getMappFg() != null ) {
                    result = dlvrProdMultiMapper.excelInsertDlvrProdNm(dlvrProdMultiVO);
                }
            }
        }
        return result;
    }
}
