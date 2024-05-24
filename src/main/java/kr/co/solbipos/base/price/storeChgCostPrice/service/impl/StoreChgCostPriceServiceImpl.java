package kr.co.solbipos.base.price.storeChgCostPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.storeChgCostPrice.service.StoreChgCostPriceService;
import kr.co.solbipos.base.price.storeChgCostPrice.service.StoreChgCostPriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreChgCostPriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 매장원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.17  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeChgCostPriceService")
public class StoreChgCostPriceServiceImpl implements StoreChgCostPriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreChgCostPriceMapper storeChgCostPriceMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreChgCostPriceServiceImpl(StoreChgCostPriceMapper storeChgCostPriceMapper, PopupMapper popupMapper) {
        this.storeChgCostPriceMapper = storeChgCostPriceMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장원가임의변경 - 상품의 본사 가격정보 조회 */
    @Override
    public DefaultMap<String> getProdPriceInfo(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> result = new DefaultMap<String>();

        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 변경항목 선택에 따른 상품 원가 조회
        if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
            // 선택상품 본사 상품 마스터 원가 조회
            result = storeChgCostPriceMapper.getProdPriceInfo(storeChgCostPriceVO);
        }else{
            // 선택상품 본사 수불 원가 조회
            result = storeChgCostPriceMapper.getIostockProdPriceInfo(storeChgCostPriceVO);
        }

        return result;
    }

    /** 매장원가임의변경 - 상품별 원가관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getByProdChgCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO){

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeChgCostPriceVO.setUserId(sessionInfoVO.getUserId());

        if (!StringUtil.getOrBlank(storeChgCostPriceVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeChgCostPriceVO.getStoreCd(), 3900));
            storeChgCostPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (storeChgCostPriceVO.getStoreHqBrandCd() == "" || storeChgCostPriceVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeChgCostPriceVO.getUserBrands() != null && !"".equals(storeChgCostPriceVO.getUserBrands())) {
                    String[] userBrandList = storeChgCostPriceVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeChgCostPriceVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        // 변경항목 선택에 따른 원가 조회
        if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
            // 상품별 매장 상품 마스터 원가 조회
            result = storeChgCostPriceMapper.getByProdStoreCostPriceList(storeChgCostPriceVO);
        } else {
            // 상품별 매장 수불 원가 조회
            result = storeChgCostPriceMapper.getByProdStoreIostockCostPriceList(storeChgCostPriceVO);
        }

        return result;
    }

    /** 매장원가임의변경 - 상품별 원가관리 리스트 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getByProdChgCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO){

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeChgCostPriceVO.setUserId(sessionInfoVO.getUserId());

        if (!StringUtil.getOrBlank(storeChgCostPriceVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeChgCostPriceVO.getStoreCd(), 3900));
            storeChgCostPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (storeChgCostPriceVO.getStoreHqBrandCd() == "" || storeChgCostPriceVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeChgCostPriceVO.getUserBrands() != null && !"".equals(storeChgCostPriceVO.getUserBrands())) {
                    String[] userBrandList = storeChgCostPriceVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeChgCostPriceVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        // 변경항목 선택에 따른 원가 조회
        if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
            // 상품별 매장 상품 마스터 원가 조회
            result = storeChgCostPriceMapper.getByProdStoreCostPriceExcelList(storeChgCostPriceVO);
        } else {
            // 상품별 매장 수불 원가 조회
            result = storeChgCostPriceMapper.getByProdStoreIostockCostPriceExcelList(storeChgCostPriceVO);
        }

        return result;
    }

    /** 매장원가임의변경 - 매장 원가 변경 */
    @Override
    public int saveStoreCostPrice(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        for (StoreChgCostPriceVO storeChgCostPriceVO : storeChgCostPriceVOs) {

            storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeChgCostPriceVO.setRegDt(currentDt);
            storeChgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            storeChgCostPriceVO.setModDt(currentDt);
            storeChgCostPriceVO.setModId(sessionInfoVO.getUserId());

            // 변경항목 선택에 따른 원가 변경
            if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
                //  매장 상품 마스터 원가 변경
                result = storeChgCostPriceMapper.saveStoreCostPrice(storeChgCostPriceVO);
            } else {
                // 매장 수불 원가 변경
                result = storeChgCostPriceMapper.saveStoreIostockCostPrice(storeChgCostPriceVO);

                // 기존원가와 수정원가가 다르면
                if(!storeChgCostPriceVO.getStoreCostUprc().equals(storeChgCostPriceVO.getCostUprc())){
                    storeChgCostPriceVO.setProcFg("U"); // 진행상태

                    // 매장 수불 원가 변경에 따른 History 등록
                    storeChgCostPriceMapper.saveStoreIostockCostPriceHistory(storeChgCostPriceVO);
                }
            }

        }
        return result;
    }

    /** 매장원가임의변경 - 매장별 원가관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getByStoreChgCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO){

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeChgCostPriceVO.setUserId(sessionInfoVO.getUserId());

        if (!StringUtil.getOrBlank(storeChgCostPriceVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeChgCostPriceVO.getStoreCd(), 3900));
            storeChgCostPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeChgCostPriceVO.getProdHqBrandCd() == "" || storeChgCostPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeChgCostPriceVO.getUserProdBrands() != null && !"".equals(storeChgCostPriceVO.getUserProdBrands())) {
                    String[] userBrandList = storeChgCostPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeChgCostPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        // 변경항목 선택에 따른 원가 조회
        if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
            // 매장별 매장 상품 마스터 원가 조회
            result = storeChgCostPriceMapper.getByStoreStoreCostPriceList(storeChgCostPriceVO);
        } else {
            // 매장별 매장 수불 원가 조회
            result = storeChgCostPriceMapper.getByStoreStoreIostockCostPriceList(storeChgCostPriceVO);
        }

        return result;
    }

    /** 매장원가임의변경 - 매장별 원가관리 리스트 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getByStoreChgCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO){

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeChgCostPriceVO.setUserId(sessionInfoVO.getUserId());

        if (!StringUtil.getOrBlank(storeChgCostPriceVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeChgCostPriceVO.getStoreCd(), 3900));
            storeChgCostPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeChgCostPriceVO.getProdHqBrandCd() == "" || storeChgCostPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeChgCostPriceVO.getUserProdBrands() != null && !"".equals(storeChgCostPriceVO.getUserProdBrands())) {
                    String[] userBrandList = storeChgCostPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeChgCostPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        // 변경항목 선택에 따른 원가 조회
        if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
            // 매장별 매장 상품 마스터 원가 조회
            result = storeChgCostPriceMapper.getByStoreStoreCostPriceExcelList(storeChgCostPriceVO);
        } else {
            // 매장별 매장 수불 원가 조회
            result = storeChgCostPriceMapper.getByStoreStoreIostockCostPriceExcelList(storeChgCostPriceVO);
        }

        return result;
    }

    /** 매장원가임의변경 - 엑셀 양식다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getStoreChgCostPriceExcelUploadSampleList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO){

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeChgCostPriceVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(storeChgCostPriceVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeChgCostPriceVO.getStoreCds(), 3900));
            storeChgCostPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 변경항목 선택에 따른 엑셀 양식다운로드 조회
        if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
            // 매장 상품 마스터 원가 엑셀 양식다운로드 조회
            result = storeChgCostPriceMapper.getStoreCostPriceExcelUploadSampleList(storeChgCostPriceVO);
        } else {
            // 매장 수불 원가 엑셀 양식다운로드 조회
            result = storeChgCostPriceMapper.getStoreIostockCostPriceExcelUploadSampleList(storeChgCostPriceVO);
        }

        return result;
    };

    /** 매장원가임의변경 - 엑셀업로드 원가 업로드 임시테이블 전체 삭제 */
    @Override
    public int deleteCostPriceExcelUploadCheckAll(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        storeChgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 엑셀업로드 원가 업로드 임시테이블 전체 삭제
        procCnt += storeChgCostPriceMapper.deleteCostPriceExcelUploadCheckAll(storeChgCostPriceVO);

        return procCnt;
    }

    /** 매장원가임의변경 - 엑셀업로드 원가 업로드 임시테이블 삭제 */
    @Override
    public int deleteCostPriceExcelUploadCheck(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for (StoreChgCostPriceVO storeChgCostPriceVO : storeChgCostPriceVOs) {
            storeChgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 엑셀업로드 원가 업로드 임시테이블 삭제
            procCnt += storeChgCostPriceMapper.deleteCostPriceExcelUploadCheck(storeChgCostPriceVO);
        }

        return procCnt;
    }

    /** 원매장원가임의변경 - 원가 업로드 임시테이블 저장 */
    @Override
    public int saveCostPriceExcelUploadCheck(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for (StoreChgCostPriceVO storeChgCostPriceVO : storeChgCostPriceVOs) {
            storeChgCostPriceVO.setRegDt(currentDt);
            storeChgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            storeChgCostPriceVO.setModDt(currentDt);
            storeChgCostPriceVO.setModId(sessionInfoVO.getUserId());
            storeChgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeChgCostPriceVO.setSeq(i);
            storeChgCostPriceVO.setResult("검증전");

            // 원가 업로드 임시테이블 저장
            procCnt += storeChgCostPriceMapper.saveCostPriceExcelUploadCheck(storeChgCostPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 매장원가임의변경 - 원가 업로드 임시테이블 데이터 조회 */
    @Override
    public List<DefaultMap<String>> getCostPriceExcelUploadCheckList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storeChgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
        storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 변경항목 선택에 따른 임시테이블 데이터 조회
        if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
            // 매장 상품 마스터 원가 임시테이블 데이터 조회
            result = storeChgCostPriceMapper.getStoreCostPriceExcelUploadCheckList(storeChgCostPriceVO);
        } else {
            // 매장 수불 원가 임시테이블 데이터 조회
            result = storeChgCostPriceMapper.getStoreIostockCostPriceExcelUploadCheckList(storeChgCostPriceVO);
        }

        return result;
    }

    /** 매장원가임의변경 - 원가 업로드 검증결과 저장 */
    @Override
    public int saveCostPriceExcelUploadCheckResult(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();
        String pattern = "^[0-9]*$"; //숫자만

        for (StoreChgCostPriceVO storeChgCostPriceVO : storeChgCostPriceVOs) {
            storeChgCostPriceVO.setRegDt(currentDt);
            storeChgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            storeChgCostPriceVO.setModDt(currentDt);
            storeChgCostPriceVO.setModId(sessionInfoVO.getUserId());
            storeChgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeChgCostPriceVO.setSeq(i);

            // 원가
            if (storeChgCostPriceVO.getCostUprc() != null && !"".equals(storeChgCostPriceVO.getCostUprc())) {
                if (Pattern.matches(pattern, storeChgCostPriceVO.getCostUprc())) {
                    storeChgCostPriceVO.setCostUprc(storeChgCostPriceVO.getCostUprc());
                } else {
                    storeChgCostPriceVO.setResult("원가는 숫자만 입력가능합니다.");
                }
            }

            // 변경항목 선택에 따른 상품코드 존재여부 체크
            if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
                // 매장 상품 상품코드 존재여부 체크
                if (storeChgCostPriceMapper.getStoreProdCdChk(storeChgCostPriceVO) > 0) {
                } else {
                    storeChgCostPriceVO.setResult("존재하지 않는 상품코드입니다");
                }
            } else {
                // 매장 수불 상품코드 존재여부 체크
                if (storeChgCostPriceMapper.getStoreIostockProdCdChk(storeChgCostPriceVO) > 0) {
                } else {
                    storeChgCostPriceVO.setResult("해당년월에 수불내역이 존재하지 않는 상품코드입니다");
                }
            }

            if (storeChgCostPriceVO.getResult() == null || storeChgCostPriceVO.getResult() == "") {
                storeChgCostPriceVO.setResult("검증성공");
            }

            procCnt += storeChgCostPriceMapper.saveCostPriceExcelUploadCheck(storeChgCostPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 매장원가임의변경 - 원가 엑셀업로드 저장 */
    @Override
    public int saveCostPriceExcelUpload(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        for (StoreChgCostPriceVO storeChgCostPriceVO : storeChgCostPriceVOs) {

            storeChgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeChgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeChgCostPriceVO.setRegDt(currentDt);
            storeChgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            storeChgCostPriceVO.setModDt(currentDt);
            storeChgCostPriceVO.setModId(sessionInfoVO.getUserId());

            if (("검증성공").equals(storeChgCostPriceVO.getResult())) {

                // 변경항목 선택에 따른 원가 변경
                if ("0".equals(storeChgCostPriceVO.getCostUprcType())) {
                    //  매장 상품 마스터 원가 변경
                    result = storeChgCostPriceMapper.saveStoreCostPrice(storeChgCostPriceVO);
                } else {
                    // 매장 수불 원가 변경
                    result = storeChgCostPriceMapper.saveStoreIostockCostPrice(storeChgCostPriceVO);

                    // 기존원가와 수정원가가 다르면
                    if(!storeChgCostPriceVO.getStoreCostUprc().equals(storeChgCostPriceVO.getCostUprc())){
                        storeChgCostPriceVO.setProcFg("U"); // 진행상태

                        // 매장 수불 원가 변경에 따른 History 등록
                        storeChgCostPriceMapper.saveStoreIostockCostPriceHistory(storeChgCostPriceVO);
                    }
                }

                // 저장완료된 검증결과만 삭제
                result += storeChgCostPriceMapper.deleteCostPriceExcelUploadCheck(storeChgCostPriceVO);

            }
        }

        return result;
    }
}
