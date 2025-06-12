package kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.service.ProdSaleMonthStoreMomsService;
import kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.service.ProdSaleMonthStoreMomsVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdSaleMonthStoreMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출월별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.12.20  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("ProdSaleMonthStoreMomsService")
@Transactional
public class ProdSaleMonthStoreMomsServiceImpl implements ProdSaleMonthStoreMomsService {

    private final ProdSaleMonthStoreMomsMapper prodSaleMonthStoreMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleMonthStoreMomsServiceImpl(ProdSaleMonthStoreMomsMapper prodSaleMonthStoreMomsMapper, PopupMapper popupMapper) {
        this.prodSaleMonthStoreMomsMapper = prodSaleMonthStoreMomsMapper;
        this.popupMapper = popupMapper;
    }


    /** 상품매출일별(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleMonthStoreMomsList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, SessionInfoVO sessionInfoVO) {
        prodSaleMonthStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleMonthStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleMonthStoreMomsVO.getStoreCds(), 3900));
            prodSaleMonthStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleMonthStoreMomsVO.getProdClassCd() != null && !"".equals(prodSaleMonthStoreMomsVO.getProdClassCd())) {
            String[] prodCdList = prodSaleMonthStoreMomsVO.getProdClassCd().split(",");
            prodSaleMonthStoreMomsVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (prodSaleMonthStoreMomsVO.getProdCds() != null && !"".equals(prodSaleMonthStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleMonthStoreMomsVO.getProdCds().split(",");
            prodSaleMonthStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleMonthStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleMonthStoreMomsVO.getStoreHqBrandCd() == null || prodSaleMonthStoreMomsVO.getProdHqBrandCd() == "" || prodSaleMonthStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleMonthStoreMomsVO.getUserBrands().split(",");
                prodSaleMonthStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleMonthStoreMomsMapper.getProdSaleMonthStoreMomsList(prodSaleMonthStoreMomsVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleMonthStoreMomsExcelList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleMonthStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleMonthStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleMonthStoreMomsVO.getStoreCds(), 3900));
            prodSaleMonthStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleMonthStoreMomsVO.getProdClassCd() != null && !"".equals(prodSaleMonthStoreMomsVO.getProdClassCd())) {
            String[] prodCdList = prodSaleMonthStoreMomsVO.getProdClassCd().split(",");
            prodSaleMonthStoreMomsVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (prodSaleMonthStoreMomsVO.getProdCds() != null && !"".equals(prodSaleMonthStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleMonthStoreMomsVO.getProdCds().split(",");
            prodSaleMonthStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleMonthStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleMonthStoreMomsVO.getStoreHqBrandCd() == null || prodSaleMonthStoreMomsVO.getProdHqBrandCd() == "" || prodSaleMonthStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleMonthStoreMomsVO.getUserBrands().split(",");
                prodSaleMonthStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleMonthStoreMomsMapper.getProdSaleMonthStoreMomsExcelList(prodSaleMonthStoreMomsVO);
    }

    /** 상품매출일별(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleMonthStoreMomsExcelDivisionList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleMonthStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleMonthStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleMonthStoreMomsVO.getStoreCds(), 3900));
            prodSaleMonthStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleMonthStoreMomsVO.getProdClassCd() != null && !"".equals(prodSaleMonthStoreMomsVO.getProdClassCd())) {
            String[] prodCdList = prodSaleMonthStoreMomsVO.getProdClassCd().split(",");
            prodSaleMonthStoreMomsVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (prodSaleMonthStoreMomsVO.getProdCds() != null && !"".equals(prodSaleMonthStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleMonthStoreMomsVO.getProdCds().split(",");
            prodSaleMonthStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleMonthStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleMonthStoreMomsVO.getStoreHqBrandCd() == null || prodSaleMonthStoreMomsVO.getProdHqBrandCd() == "" || prodSaleMonthStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleMonthStoreMomsVO.getUserBrands().split(",");
                prodSaleMonthStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleMonthStoreMomsMapper.getProdSaleMonthStoreMomsExcelDivisionList(prodSaleMonthStoreMomsVO);
    }

    /** 상품매출일별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    @Override
    public int getDivisionExcelDownloadUserIdChk(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleMonthStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodSaleMonthStoreMomsVO.setUserId(sessionInfoVO.getUserId());

        return prodSaleMonthStoreMomsMapper.getDivisionExcelDownloadUserIdChk(prodSaleMonthStoreMomsVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 isnert */
    @Override
    public String getDivisionExcelDownloadSaveInsert(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        prodSaleMonthStoreMomsVO.setRegDt(currentDt);
        prodSaleMonthStoreMomsVO.setRegId(sessionInfoVO.getUserId());
        prodSaleMonthStoreMomsVO.setModDt(currentDt);
        prodSaleMonthStoreMomsVO.setModId(sessionInfoVO.getUserId());

        prodSaleMonthStoreMomsVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        prodSaleMonthStoreMomsVO.setUserId(sessionInfoVO.getUserId());

        // 순번(자동채번)
        String seq = prodSaleMonthStoreMomsMapper.getDownloadSeq(prodSaleMonthStoreMomsVO);
        prodSaleMonthStoreMomsVO.setSeq(seq);

        // 화면별 건당 다운로드 예상시간(초)
        String expectedTimeSecond = prodSaleMonthStoreMomsMapper.getExpectedTimeSecond(prodSaleMonthStoreMomsVO);

        // 다운로드 예상종료시간 계산
        Date downloadStartDt = DateUtil.getDatetime(prodSaleMonthStoreMomsVO.getRegDt()); // 다운로드 사용시작
        int seconds = prodSaleMonthStoreMomsVO.getDownloadFileCount() * Integer.parseInt(expectedTimeSecond); // 예상시간(초) = 다운로드 파일수 X 화면별 건당 다운로드 예상시간(초)
        Date downloadExpectedEndDt = DateUtils.addSeconds(downloadStartDt, seconds); // 다운로드 사용시작 + 예상시간(초)
        prodSaleMonthStoreMomsVO.setDownloadExpectedEndDt(DateFormatUtils.format(downloadExpectedEndDt, "yyyyMMddHHmmss"));

        // 엑셀다운로드 진행 사용자 저장 isnert
        prodSaleMonthStoreMomsMapper.getDivisionExcelDownloadSaveInsert(prodSaleMonthStoreMomsVO);

        return seq;
    }

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    @Override
    public String getDivisionExcelDownloadCntChk(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, SessionInfoVO sessionInfoVO) {

        return prodSaleMonthStoreMomsMapper.getDivisionExcelDownloadCntChk(prodSaleMonthStoreMomsVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 update */
    @Override
    public int getDivisionExcelDownloadSaveUpdate(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        prodSaleMonthStoreMomsVO.setModDt(currentDt);
        prodSaleMonthStoreMomsVO.setModId(sessionInfoVO.getUserId());

        prodSaleMonthStoreMomsVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        prodSaleMonthStoreMomsVO.setUserId(sessionInfoVO.getUserId());

        return prodSaleMonthStoreMomsMapper.getDivisionExcelDownloadSaveUpdate(prodSaleMonthStoreMomsVO);
    }
}
