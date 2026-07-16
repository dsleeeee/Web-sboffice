package kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service.ProdSaleMonthStoreBensonService;
import kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service.ProdSaleMonthStoreBensonVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdSaleMonthStoreBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 상품매출월별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("prodSaleMonthStoreBensonService")
@Transactional
public class ProdSaleMonthStoreBensonServiceImpl implements ProdSaleMonthStoreBensonService {

    private final ProdSaleMonthStoreBensonMapper prodSaleMonthStoreBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleMonthStoreBensonServiceImpl(ProdSaleMonthStoreBensonMapper prodSaleMonthStoreBensonMapper, PopupMapper popupMapper) {
        this.prodSaleMonthStoreBensonMapper = prodSaleMonthStoreBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출월별(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleMonthStoreBensonList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO) {
        prodSaleMonthStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleMonthStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleMonthStoreBensonVO.getStoreCds(), 3900));
            prodSaleMonthStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleMonthStoreBensonVO.getProdClassCd() != null && !"".equals(prodSaleMonthStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleMonthStoreBensonVO.getProdClassCd().split(",");
            prodSaleMonthStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleMonthStoreBensonVO.getProdCds(), 3900));
            prodSaleMonthStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleMonthStoreBensonVO.getStoreHqBrandCd() == "" || prodSaleMonthStoreBensonVO.getStoreHqBrandCd() == null || prodSaleMonthStoreBensonVO.getProdHqBrandCd() == "" || prodSaleMonthStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleMonthStoreBensonVO.getUserBrands().split(",");
                prodSaleMonthStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleMonthStoreBensonMapper.getProdSaleMonthStoreBensonList(prodSaleMonthStoreBensonVO);
    }

    /** 상품매출월별(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleMonthStoreBensonExcelList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleMonthStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleMonthStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleMonthStoreBensonVO.getStoreCds(), 3900));
            prodSaleMonthStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleMonthStoreBensonVO.getProdClassCd() != null && !"".equals(prodSaleMonthStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleMonthStoreBensonVO.getProdClassCd().split(",");
            prodSaleMonthStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleMonthStoreBensonVO.getProdCds(), 3900));
            prodSaleMonthStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleMonthStoreBensonVO.getStoreHqBrandCd() == "" || prodSaleMonthStoreBensonVO.getStoreHqBrandCd() == null || prodSaleMonthStoreBensonVO.getProdHqBrandCd() == "" || prodSaleMonthStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleMonthStoreBensonVO.getUserBrands().split(",");
                prodSaleMonthStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleMonthStoreBensonMapper.getProdSaleMonthStoreBensonExcelList(prodSaleMonthStoreBensonVO);
    }

    /** 상품매출월별(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleMonthStoreBensonExcelDivisionList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleMonthStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleMonthStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleMonthStoreBensonVO.getStoreCds(), 3900));
            prodSaleMonthStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleMonthStoreBensonVO.getProdClassCd() != null && !"".equals(prodSaleMonthStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleMonthStoreBensonVO.getProdClassCd().split(",");
            prodSaleMonthStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleMonthStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleMonthStoreBensonVO.getProdCds(), 3900));
            prodSaleMonthStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleMonthStoreBensonVO.getStoreHqBrandCd() == "" || prodSaleMonthStoreBensonVO.getStoreHqBrandCd() == null || prodSaleMonthStoreBensonVO.getProdHqBrandCd() == "" || prodSaleMonthStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleMonthStoreBensonVO.getUserBrands().split(",");
                prodSaleMonthStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleMonthStoreBensonMapper.getProdSaleMonthStoreBensonExcelDivisionList(prodSaleMonthStoreBensonVO);
    }

    /** 상품매출월별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    @Override
    public int getDivisionExcelDownloadUserIdChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleMonthStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodSaleMonthStoreBensonVO.setUserId(sessionInfoVO.getUserId());

        return prodSaleMonthStoreBensonMapper.getDivisionExcelDownloadUserIdChk(prodSaleMonthStoreBensonVO);
    }

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 isnert */
    @Override
    public String getDivisionExcelDownloadSaveInsert(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        prodSaleMonthStoreBensonVO.setRegDt(currentDt);
        prodSaleMonthStoreBensonVO.setRegId(sessionInfoVO.getUserId());
        prodSaleMonthStoreBensonVO.setModDt(currentDt);
        prodSaleMonthStoreBensonVO.setModId(sessionInfoVO.getUserId());

        prodSaleMonthStoreBensonVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        prodSaleMonthStoreBensonVO.setUserId(sessionInfoVO.getUserId());

        // 순번(자동채번)
        String seq = prodSaleMonthStoreBensonMapper.getDownloadSeq(prodSaleMonthStoreBensonVO);
        prodSaleMonthStoreBensonVO.setSeq(seq);

        // 화면별 건당 다운로드 예상시간(초)
        String expectedTimeSecond = prodSaleMonthStoreBensonMapper.getExpectedTimeSecond(prodSaleMonthStoreBensonVO);

        // 다운로드 예상종료시간 계산
        Date downloadStartDt = DateUtil.getDatetime(prodSaleMonthStoreBensonVO.getRegDt()); // 다운로드 사용시작
        int seconds = prodSaleMonthStoreBensonVO.getDownloadFileCount() * Integer.parseInt(expectedTimeSecond); // 예상시간(초) = 다운로드 파일수 X 화면별 건당 다운로드 예상시간(초)
        Date downloadExpectedEndDt = DateUtils.addSeconds(downloadStartDt, seconds); // 다운로드 사용시작 + 예상시간(초)
        prodSaleMonthStoreBensonVO.setDownloadExpectedEndDt(DateFormatUtils.format(downloadExpectedEndDt, "yyyyMMddHHmmss"));

        // 엑셀다운로드 진행 사용자 저장 isnert
        prodSaleMonthStoreBensonMapper.getDivisionExcelDownloadSaveInsert(prodSaleMonthStoreBensonVO);

        return seq;
    }

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    @Override
    public String getDivisionExcelDownloadCntChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO) {

        return prodSaleMonthStoreBensonMapper.getDivisionExcelDownloadCntChk(prodSaleMonthStoreBensonVO);
    }

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 update */
    @Override
    public int getDivisionExcelDownloadSaveUpdate(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        prodSaleMonthStoreBensonVO.setModDt(currentDt);
        prodSaleMonthStoreBensonVO.setModId(sessionInfoVO.getUserId());

        prodSaleMonthStoreBensonVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        prodSaleMonthStoreBensonVO.setUserId(sessionInfoVO.getUserId());

        return prodSaleMonthStoreBensonMapper.getDivisionExcelDownloadSaveUpdate(prodSaleMonthStoreBensonVO);
    }
}
