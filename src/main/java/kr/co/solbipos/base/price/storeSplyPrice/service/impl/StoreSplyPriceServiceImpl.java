package kr.co.solbipos.base.price.storeSplyPrice.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.storeSplyPrice.service.StoreSplyPriceService;
import kr.co.solbipos.base.price.storeSplyPrice.service.StoreSplyPriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreSplyPriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 매장공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.16  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeSplyPriceService")
public class StoreSplyPriceServiceImpl implements StoreSplyPriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreSplyPriceMapper storeSplyPriceMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreSplyPriceServiceImpl(StoreSplyPriceMapper storeSplyPriceMapper, PopupMapper popupMapper, MessageService messageService) {
        this.storeSplyPriceMapper = storeSplyPriceMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 매장공급가관리 - 상품의 본사 가격정보 조회 */
    @Override
    public DefaultMap<String> getProdPriceInfo(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO) {

        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeSplyPriceMapper.getProdPriceInfo(storeSplyPriceVO);
    }

    /** 매장공급가관리 - 상품별 공급가관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getByProdSplyPriceList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO){

        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (!StringUtil.getOrBlank(storeSplyPriceVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSplyPriceVO.getStoreCd(), 3900));
            storeSplyPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (storeSplyPriceVO.getStoreHqBrandCd() == "" || storeSplyPriceVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSplyPriceVO.getUserBrands() != null && !"".equals(storeSplyPriceVO.getUserBrands())) {
                    String[] userBrandList = storeSplyPriceVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSplyPriceVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSplyPriceMapper.getByProdSplyPriceList(storeSplyPriceVO);
    }

    /** 매장공급가관리 - 상품별 공급가관리 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getByProdSplyPriceExcelList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO){

        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (!StringUtil.getOrBlank(storeSplyPriceVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSplyPriceVO.getStoreCd(), 3900));
            storeSplyPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (storeSplyPriceVO.getStoreHqBrandCd() == "" || storeSplyPriceVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSplyPriceVO.getUserBrands() != null && !"".equals(storeSplyPriceVO.getUserBrands())) {
                    String[] userBrandList = storeSplyPriceVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSplyPriceVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSplyPriceMapper.getByProdSplyPriceExcelList(storeSplyPriceVO);
    }

    /** 매장공급가관리 - 매장별 공급가관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getByStoreSplyPriceList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO){

        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeSplyPriceVO.getProdHqBrandCd() == "" || storeSplyPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSplyPriceVO.getUserProdBrands() != null && !"".equals(storeSplyPriceVO.getUserProdBrands())) {
                    String[] userBrandList = storeSplyPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSplyPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSplyPriceMapper.getByStoreSplyPriceList(storeSplyPriceVO);
    }

    /** 매장공급가관리 - 매장별 공급가관리 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getByStoreSplyPriceExcelList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO){

        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeSplyPriceVO.getProdHqBrandCd() == "" || storeSplyPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSplyPriceVO.getUserProdBrands() != null && !"".equals(storeSplyPriceVO.getUserProdBrands())) {
                    String[] userBrandList = storeSplyPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSplyPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSplyPriceMapper.getByStoreSplyPriceExcelList(storeSplyPriceVO);
    }

    /** 매장공급가관리 - 매장공급가 저장 */
    @Override
    public int saveStoreSplyPrice(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (StoreSplyPriceVO storeSplyPriceVO : storeSplyPriceVOs) {

            storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeSplyPriceVO.setRegDt(currentDt);
            storeSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            storeSplyPriceVO.setModDt(currentDt);
            storeSplyPriceVO.setModId(sessionInfoVO.getUserId());

            // 매장공급가 저장
            result += storeSplyPriceMapper.saveStoreSplyPrice(storeSplyPriceVO);
        }
        return result;
    }

    /** 매장공급가관리 - 매장공급가 복사 */
    @Override
    public int copyStoreSplyPrice(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        storeSplyPriceVO.setRegDt(currentDt);
        storeSplyPriceVO.setRegId(sessionInfoVO.getUserId());
        storeSplyPriceVO.setModDt(currentDt);
        storeSplyPriceVO.setModId(sessionInfoVO.getUserId());

        if(storeSplyPriceVO.getOriginalStoreCd() != null && !"".equals(storeSplyPriceVO.getOriginalStoreCd()) &&
           storeSplyPriceVO.getTargetStoreCd() != null && !"".equals(storeSplyPriceVO.getTargetStoreCd())){

            // 매장공급가 복사
            result = storeSplyPriceMapper.copyStoreSplyPrice(storeSplyPriceVO);
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    /** 매장공급가관리 - 엑셀업로드 엑셀 양식다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSplyPriceExcelUploadSampleList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO) {

        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(storeSplyPriceVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSplyPriceVO.getStoreCds(), 3900));
            storeSplyPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return storeSplyPriceMapper.getStoreSplyPriceExcelUploadSampleList(storeSplyPriceVO);
    }

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 전체 삭제 */
    @Override
    public int deleteSplyPriceExcelUploadCheckAll(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        storeSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        procCnt += storeSplyPriceMapper.deleteSplyPriceExcelUploadCheckAll(storeSplyPriceVO);

        return procCnt;
    }

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 삭제 */
    @Override
    public int deleteSplyPriceExcelUploadCheck(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for (StoreSplyPriceVO storeSplyPriceVO : storeSplyPriceVOs) {
            storeSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt += storeSplyPriceMapper.deleteSplyPriceExcelUploadCheck(storeSplyPriceVO);
        }

        return procCnt;
    }

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 저장 */
    @Override
    public int saveSplyPriceExcelUploadCheck(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for (StoreSplyPriceVO storeSplyPriceVO : storeSplyPriceVOs) {
            storeSplyPriceVO.setRegDt(currentDt);
            storeSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            storeSplyPriceVO.setModDt(currentDt);
            storeSplyPriceVO.setModId(sessionInfoVO.getUserId());

            storeSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeSplyPriceVO.setSeq(i);

            storeSplyPriceVO.setResult("검증전");

            procCnt += storeSplyPriceMapper.saveSplyPriceExcelUploadCheck(storeSplyPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 데이터 조회 */
    @Override
    public List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO) {

        storeSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
        storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeSplyPriceMapper.getSplyPriceExcelUploadCheckList(storeSplyPriceVO);
    }

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 검증결과 저장 */
    @Override
    public int saveSplyPriceExcelUploadCheckResult(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();
        String pattern = "^[0-9]*$"; //숫자만

        for (StoreSplyPriceVO storeSplyPriceVO : storeSplyPriceVOs) {
            storeSplyPriceVO.setRegDt(currentDt);
            storeSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            storeSplyPriceVO.setModDt(currentDt);
            storeSplyPriceVO.setModId(sessionInfoVO.getUserId());

            storeSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeSplyPriceVO.setSeq(i);

            // 공급가
            if (storeSplyPriceVO.getSplyUprc() != null && !"".equals(storeSplyPriceVO.getSplyUprc())) {
                if (Pattern.matches(pattern, storeSplyPriceVO.getSplyUprc())) {
                    storeSplyPriceVO.setSplyUprc(storeSplyPriceVO.getSplyUprc());
                } else {
                    storeSplyPriceVO.setResult("공급가는 숫자만 입력가능합니다.");
                }
            }

            // 상품코드 존재여부 체크
            if (storeSplyPriceMapper.getStoreProdCdChk(storeSplyPriceVO) > 0) {
            } else {
                storeSplyPriceVO.setResult("존재하지 않는 상품코드입니다");
            }

            if (storeSplyPriceVO.getResult() == null || storeSplyPriceVO.getResult() == "") {
                storeSplyPriceVO.setResult("검증성공");
            }

            procCnt += storeSplyPriceMapper.saveSplyPriceExcelUploadCheck(storeSplyPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 매장공급가관리 - 엑셀업로드 매장 공급가 엑셀업로드 저장 */
    @Override
    public int saveStoreSplyPriceExcelUpload(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (StoreSplyPriceVO storeSplyPriceVO : storeSplyPriceVOs) {

            storeSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            storeSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeSplyPriceVO.setRegDt(currentDt);
            storeSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            storeSplyPriceVO.setModDt(currentDt);
            storeSplyPriceVO.setModId(sessionInfoVO.getUserId());

            if (("검증성공").equals(storeSplyPriceVO.getResult())) {

                // 매장 판매가 변경
                result = storeSplyPriceMapper.saveStoreSplyPrice(storeSplyPriceVO);

                // 저장완료된 검증결과만 삭제
                result += storeSplyPriceMapper.deleteSplyPriceExcelUploadCheck(storeSplyPriceVO);

            }
        }

        return result;
    }
}
