package kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.ProdSaleDayStoreMomsService;
import kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.ProdSaleDayStoreMomsVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;

/**
 * @Class Name : ProdSaleDayStoreMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출일별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSaleDayStoreMomsService")
@Transactional
public class ProdSaleDayStoreMomsServiceImpl implements ProdSaleDayStoreMomsService {
    private final ProdSaleDayStoreMomsMapper prodSaleDayStoreMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayStoreMomsServiceImpl(ProdSaleDayStoreMomsMapper prodSaleDayStoreMomsMapper, PopupMapper popupMapper) {
        this.prodSaleDayStoreMomsMapper = prodSaleDayStoreMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출일별(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreMomsList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreMomsVO.getStoreCds(), 3900));
            prodSaleDayStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayStoreMomsVO.getProdCds() != null && !"".equals(prodSaleDayStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayStoreMomsVO.getProdCds().split(",");
            prodSaleDayStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleDayStoreMomsVO.getStoreHqBrandCd() == null || prodSaleDayStoreMomsVO.getProdHqBrandCd() == "" || prodSaleDayStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreMomsVO.getUserBrands().split(",");
                prodSaleDayStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreMomsMapper.getProdSaleDayStoreMomsList(prodSaleDayStoreMomsVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreMomsExcelList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreMomsVO.getStoreCds(), 3900));
            prodSaleDayStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayStoreMomsVO.getProdCds() != null && !"".equals(prodSaleDayStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayStoreMomsVO.getProdCds().split(",");
            prodSaleDayStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleDayStoreMomsVO.getStoreHqBrandCd() == null || prodSaleDayStoreMomsVO.getProdHqBrandCd() == "" || prodSaleDayStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreMomsVO.getUserBrands().split(",");
                prodSaleDayStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreMomsMapper.getProdSaleDayStoreMomsExcelList(prodSaleDayStoreMomsVO);
    }

    /** 상품매출일별(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreMomsExcelDivisionList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreMomsVO.getStoreCds(), 3900));
            prodSaleDayStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayStoreMomsVO.getProdCds() != null && !"".equals(prodSaleDayStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayStoreMomsVO.getProdCds().split(",");
            prodSaleDayStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleDayStoreMomsVO.getStoreHqBrandCd() == null || prodSaleDayStoreMomsVO.getProdHqBrandCd() == "" || prodSaleDayStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreMomsVO.getUserBrands().split(",");
                prodSaleDayStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreMomsMapper.getProdSaleDayStoreMomsExcelDivisionList(prodSaleDayStoreMomsVO);
    }

    /** 상품매출일별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    @Override
    public int getDivisionExcelDownloadUserIdChk(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodSaleDayStoreMomsVO.setUserId(sessionInfoVO.getUserId());

        return prodSaleDayStoreMomsMapper.getDivisionExcelDownloadUserIdChk(prodSaleDayStoreMomsVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 기능 사용자 저장 isnert */
    @Override
    public int getDivisionExcelDownloadSaveInsert(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        prodSaleDayStoreMomsVO.setRegDt(currentDt);
        prodSaleDayStoreMomsVO.setRegId(sessionInfoVO.getUserId());
        prodSaleDayStoreMomsVO.setModDt(currentDt);
        prodSaleDayStoreMomsVO.setModId(sessionInfoVO.getUserId());

        prodSaleDayStoreMomsVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        prodSaleDayStoreMomsVO.setUserId(sessionInfoVO.getUserId());

        // 화면별 건당 다운로드 예상시간(초)
        String expectedTimeSecond = prodSaleDayStoreMomsMapper.getExpectedTimeSecond(prodSaleDayStoreMomsVO);

        // 다운로드 예상종료시간 계산
        Date downloadStartDt = DateUtil.getDatetime(prodSaleDayStoreMomsVO.getRegDt()); // 다운로드 사용시작
        int seconds = prodSaleDayStoreMomsVO.getDownloadFileCount() * Integer.parseInt(expectedTimeSecond); // 예상시간(초) = 다운로드 파일수 X 화면별 건당 다운로드 예상시간(초)
        Date downloadExpectedEndDt = DateUtils.addSeconds(downloadStartDt, seconds); // 다운로드 사용시작 + 예상시간(초)
        prodSaleDayStoreMomsVO.setDownloadExpectedEndDt(DateFormatUtils.format(downloadExpectedEndDt, "yyyyMMddHHmmss"));

        return prodSaleDayStoreMomsMapper.getDivisionExcelDownloadSaveInsert(prodSaleDayStoreMomsVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    @Override
    public String getDivisionExcelDownloadCntChk(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        return prodSaleDayStoreMomsMapper.getDivisionExcelDownloadCntChk(prodSaleDayStoreMomsVO);
    }
}