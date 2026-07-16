package kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.service.ProdSaleDayStoreBensonService;
import kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.service.ProdSaleDayStoreBensonVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;

/**
 * @Class Name : ProdSaleDayStoreBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 상품매출일별(매장)
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
@Service("prodSaleDayStoreBensonService")
@Transactional
public class ProdSaleDayStoreBensonServiceImpl implements ProdSaleDayStoreBensonService {
    private final ProdSaleDayStoreBensonMapper prodSaleDayStoreBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayStoreBensonServiceImpl(ProdSaleDayStoreBensonMapper prodSaleDayStoreBensonMapper, PopupMapper popupMapper) {
        this.prodSaleDayStoreBensonMapper = prodSaleDayStoreBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출일별(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreBensonList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreBensonVO.getStoreCds(), 3900));
            prodSaleDayStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleDayStoreBensonVO.getProdClassCd() != null && !"".equals(prodSaleDayStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleDayStoreBensonVO.getProdClassCd().split(",");
            prodSaleDayStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleDayStoreBensonVO.getProdCds(), 3900));
            prodSaleDayStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreBensonVO.getStoreHqBrandCd() == "" || prodSaleDayStoreBensonVO.getStoreHqBrandCd() == null || prodSaleDayStoreBensonVO.getProdHqBrandCd() == "" || prodSaleDayStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreBensonVO.getUserBrands().split(",");
                prodSaleDayStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreBensonMapper.getProdSaleDayStoreBensonList(prodSaleDayStoreBensonVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreBensonExcelList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreBensonVO.getStoreCds(), 3900));
            prodSaleDayStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleDayStoreBensonVO.getProdClassCd() != null && !"".equals(prodSaleDayStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleDayStoreBensonVO.getProdClassCd().split(",");
            prodSaleDayStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleDayStoreBensonVO.getProdCds(), 3900));
            prodSaleDayStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreBensonVO.getStoreHqBrandCd() == "" || prodSaleDayStoreBensonVO.getStoreHqBrandCd() == null || prodSaleDayStoreBensonVO.getProdHqBrandCd() == "" || prodSaleDayStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreBensonVO.getUserBrands().split(",");
                prodSaleDayStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreBensonMapper.getProdSaleDayStoreBensonExcelList(prodSaleDayStoreBensonVO);
    }

    /** 상품매출일별(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreBensonExcelDivisionList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreBensonVO.getStoreCds(), 3900));
            prodSaleDayStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleDayStoreBensonVO.getProdClassCd() != null && !"".equals(prodSaleDayStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleDayStoreBensonVO.getProdClassCd().split(",");
            prodSaleDayStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleDayStoreBensonVO.getProdCds(), 3900));
            prodSaleDayStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreBensonVO.getStoreHqBrandCd() == "" || prodSaleDayStoreBensonVO.getStoreHqBrandCd() == null || prodSaleDayStoreBensonVO.getProdHqBrandCd() == "" || prodSaleDayStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreBensonVO.getUserBrands().split(",");
                prodSaleDayStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreBensonMapper.getProdSaleDayStoreBensonExcelDivisionList(prodSaleDayStoreBensonVO);
    }

    /** 상품매출일별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    @Override
    public int getDivisionExcelDownloadUserIdChk(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodSaleDayStoreBensonVO.setUserId(sessionInfoVO.getUserId());

        return prodSaleDayStoreBensonMapper.getDivisionExcelDownloadUserIdChk(prodSaleDayStoreBensonVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 isnert */
    @Override
    public String getDivisionExcelDownloadSaveInsert(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        prodSaleDayStoreBensonVO.setRegDt(currentDt);
        prodSaleDayStoreBensonVO.setRegId(sessionInfoVO.getUserId());
        prodSaleDayStoreBensonVO.setModDt(currentDt);
        prodSaleDayStoreBensonVO.setModId(sessionInfoVO.getUserId());

        prodSaleDayStoreBensonVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        prodSaleDayStoreBensonVO.setUserId(sessionInfoVO.getUserId());

        // 순번(자동채번)
        String seq = prodSaleDayStoreBensonMapper.getDownloadSeq(prodSaleDayStoreBensonVO);
        prodSaleDayStoreBensonVO.setSeq(seq);

        // 화면별 건당 다운로드 예상시간(초)
        String expectedTimeSecond = prodSaleDayStoreBensonMapper.getExpectedTimeSecond(prodSaleDayStoreBensonVO);

        // 다운로드 예상종료시간 계산
        Date downloadStartDt = DateUtil.getDatetime(prodSaleDayStoreBensonVO.getRegDt()); // 다운로드 사용시작
        int seconds = prodSaleDayStoreBensonVO.getDownloadFileCount() * Integer.parseInt(expectedTimeSecond); // 예상시간(초) = 다운로드 파일수 X 화면별 건당 다운로드 예상시간(초)
        Date downloadExpectedEndDt = DateUtils.addSeconds(downloadStartDt, seconds); // 다운로드 사용시작 + 예상시간(초)
        prodSaleDayStoreBensonVO.setDownloadExpectedEndDt(DateFormatUtils.format(downloadExpectedEndDt, "yyyyMMddHHmmss"));

        // 엑셀다운로드 진행 사용자 저장 isnert
        prodSaleDayStoreBensonMapper.getDivisionExcelDownloadSaveInsert(prodSaleDayStoreBensonVO);

        return seq;
    }

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    @Override
    public String getDivisionExcelDownloadCntChk(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, SessionInfoVO sessionInfoVO) {

        return prodSaleDayStoreBensonMapper.getDivisionExcelDownloadCntChk(prodSaleDayStoreBensonVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 update */
    @Override
    public int getDivisionExcelDownloadSaveUpdate(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        prodSaleDayStoreBensonVO.setModDt(currentDt);
        prodSaleDayStoreBensonVO.setModId(sessionInfoVO.getUserId());

        prodSaleDayStoreBensonVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        prodSaleDayStoreBensonVO.setUserId(sessionInfoVO.getUserId());

        return prodSaleDayStoreBensonMapper.getDivisionExcelDownloadSaveUpdate(prodSaleDayStoreBensonVO);
    }
}
