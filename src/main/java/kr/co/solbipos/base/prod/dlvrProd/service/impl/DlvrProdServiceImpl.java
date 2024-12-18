package kr.co.solbipos.base.prod.dlvrProd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.dlvrProd.service.DlvrProdService;
import kr.co.solbipos.base.prod.dlvrProd.service.DlvrProdVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("dlvrProdService")
public class DlvrProdServiceImpl implements DlvrProdService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrProdMapper dlvrProdMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public DlvrProdServiceImpl(MessageService messageService, DlvrProdMapper dlvrProdMapper) {
        this.messageService = messageService;
        this.dlvrProdMapper = dlvrProdMapper;
    }

    /** 배달시스템 상품 명칭 매핑 - 배달앱구분코드 */
    @Override
    public List<DefaultMap<String>> getDlvrColList(@RequestBody DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO) {
        return dlvrProdMapper.getDlvrColList(dlvrProdVO);
    }

    /**  배달시스템 상품 명칭 매핑 - 상품목록조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        dlvrProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            dlvrProdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            dlvrProdVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        // 배달앱 구분코드 array 값 세팅
        dlvrProdVO.setArrDlvrCol(dlvrProdVO.getDlvrCol().split(","));

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDlvrCol = "";
        String arrDlvrCol[] = dlvrProdVO.getDlvrCol().split(",");
        for(int i=0; i < arrDlvrCol.length; i++) {
            pivotDlvrCol += (pivotDlvrCol.equals("") ? "" : ",") + "'"+arrDlvrCol[i]+"'"+" AS DLVR_PROD_NM_"+arrDlvrCol[i];
        }
        dlvrProdVO.setPivotDlvrCol(pivotDlvrCol);

        dlvrProdVO.setUserId(sessionInfoVO.getUserId());

        return dlvrProdMapper.getProdList(dlvrProdVO);
    }

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭 저장 */
    @Override
    public int saveDlvrProdNm(DlvrProdVO[] dlvrProdVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( DlvrProdVO dlvrProdVO : dlvrProdVOs ) {

            dlvrProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrProdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                dlvrProdVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            dlvrProdVO.setRegDt(currentDt);
            dlvrProdVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdVO.setModDt(currentDt);
            dlvrProdVO.setModId(sessionInfoVO.getUserId());

            if ( dlvrProdVO.getStatus() == GridDataFg.UPDATE ) {

                // 1. 기존 배달상품명칭 삭제
                dlvrProdMapper.deleteDlvrProdNm(dlvrProdVO);

                // 2. 값이 있는 경우, 배달상품명칭 INSERT
                if(dlvrProdVO.getDlvrProdNm() != null && dlvrProdVO.getDlvrProdNm() != "" && dlvrProdVO.getDlvrProdNm().length() > 0){

                    result = dlvrProdMapper.insertDlvrProdNm(dlvrProdVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                procCnt ++;
            }
        }

        if ( procCnt == dlvrProdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭 복사 */
    @Override
    public int copyDlvrProdNm(DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        dlvrProdVO.setRegDt(currentDt);
        dlvrProdVO.setRegId(sessionInfoVO.getUserId());
        dlvrProdVO.setModDt(currentDt);
        dlvrProdVO.setModId(sessionInfoVO.getUserId());

        // 필수 값 체크
        if(dlvrProdVO.getOriginalStoreFg() != null && !"".equals(dlvrProdVO.getOriginalStoreFg()) &&
                dlvrProdVO.getOriginalStoreCd() != null && !"".equals(dlvrProdVO.getOriginalStoreCd()) &&
                dlvrProdVO.getTargetStoreCd() != null && !"".equals(dlvrProdVO.getTargetStoreCd())){

            // 1. 배달상품명칭 복사 전 기존데이터 삭제
            dlvrProdMapper.deleteStoreDlvrProdNm(dlvrProdVO);

            // 2. 기준매장의 배달상품명칭을 복사하여 적용대상매장에 등록
            result = dlvrProdMapper.copyStoreDlvrProdNm(dlvrProdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }else{
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    /** 배달시스템 상품 명칭 매핑 - 상품명칭 엑셀 업로드 전 상품코드 유효여부 체크 */
    @Override
    public int chkDlvrProd(DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        dlvrProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            dlvrProdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            dlvrProdVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        // 상품코드 array 값 세팅
        dlvrProdVO.setArrProdCdCol(dlvrProdVO.getProdCdCol().split(","));

        return dlvrProdMapper.chkDlvrProd(dlvrProdVO);
    }

    /** 상품명칭 매장적용 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDlvrProdNmStoreRegistList(DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO) {

        dlvrProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return dlvrProdMapper.getDlvrProdNmStoreRegistList(dlvrProdVO);
    }

    /** 상품명칭 매장적용 팝업 - 저장 */
    @Override
    public int getDlvrProdNmStoreRegistSave(DlvrProdVO[] dlvrProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(DlvrProdVO dlvrProdVO : dlvrProdVOs) {

            dlvrProdVO.setRegDt(currentDt);
            dlvrProdVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdVO.setModDt(currentDt);
            dlvrProdVO.setModId(sessionInfoVO.getUserId());

            dlvrProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            String selectProdCd = "";
            String arrSelectProdCd[] = dlvrProdVO.getSelectProdCd().split(",");
            for(int i=0; i < arrSelectProdCd.length; i++) {
                selectProdCd += (selectProdCd.equals("") ? "" : ",") + "'"+arrSelectProdCd[i]+"'";
            }
            dlvrProdVO.setSelectProdCd(selectProdCd);

            // 삭제
            procCnt = dlvrProdMapper.getDlvrProdNmStoreRegistSaveDelete(dlvrProdVO);

            // 등록
            procCnt = dlvrProdMapper.getDlvrProdNmStoreRegistSaveInsert(dlvrProdVO);
        }

        return procCnt;
    }

    /**  배달시스템 상품 명칭 매핑 - 전체 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getDlvrProdNmExcelList(@RequestBody DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        dlvrProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            dlvrProdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            dlvrProdVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }

        // 배달앱 구분코드 array 값 세팅
        dlvrProdVO.setArrDlvrCol(dlvrProdVO.getDlvrCol().split(","));

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDlvrCol = "";
        String arrDlvrCol[] = dlvrProdVO.getDlvrCol().split(",");
        for(int i=0; i < arrDlvrCol.length; i++) {
            pivotDlvrCol += (pivotDlvrCol.equals("") ? "" : ",") + "'"+arrDlvrCol[i]+"'"+" AS DLVR_PROD_NM_"+arrDlvrCol[i];
        }
        dlvrProdVO.setPivotDlvrCol(pivotDlvrCol);

        dlvrProdVO.setUserId(sessionInfoVO.getUserId());

        return dlvrProdMapper.getDlvrProdNmExcelList(dlvrProdVO);
    }

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭 중복 체크 */
    @Override
    public String getDlvrProdNmMappingChk(DlvrProdVO[] dlvrProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String dlvrProdNm = "";
        String currentDt = currentDateTimeString();

        for(DlvrProdVO dlvrProdVO : dlvrProdVOs) {

            dlvrProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            dlvrProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                dlvrProdVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            dlvrProdVO.setRegDt(currentDt);
            dlvrProdVO.setRegId(sessionInfoVO.getUserId());
            dlvrProdVO.setModDt(currentDt);
            dlvrProdVO.setModId(sessionInfoVO.getUserId());

            dlvrProdVO.setSessionId(sessionInfoVO.getUserId());
            dlvrProdVO.setSeq(i);

            // 임시테이블 저장 전
            if(i == 1) {
                // 임시테이블 삭제
                procCnt = dlvrProdMapper.getDlvrProdNmMappingChkSaveDeleteAll(dlvrProdVO);
            }

            // 임시테이블 저장
            procCnt = dlvrProdMapper.getDlvrProdNmMappingChkSaveInsert(dlvrProdVO);

            // 임시테이블 저장 끝
            if(i == dlvrProdVOs.length) {
                // 명칭 중복 체크
                dlvrProdNm = dlvrProdMapper.getDlvrProdNmMappingChk(dlvrProdVO);
            }

            i++;
        }

        return dlvrProdNm;
    }
}
