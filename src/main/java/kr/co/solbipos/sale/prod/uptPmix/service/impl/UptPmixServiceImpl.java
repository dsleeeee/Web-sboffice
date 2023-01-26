package kr.co.solbipos.sale.prod.uptPmix.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.uptPmix.service.UptPmixService;
import kr.co.solbipos.sale.prod.uptPmix.service.UptPmixVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : UptPmixServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > UPT & P.mix
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.17   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("uptPmixService")
@Transactional
public class UptPmixServiceImpl implements UptPmixService {

    private final UptPmixMapper uptPmixMapper;

    public UptPmixServiceImpl(UptPmixMapper uptPmixMapper) {
        this.uptPmixMapper = uptPmixMapper;
    }

    /** UPT & P.mix 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getUptPmixList(UptPmixVO uptPmixVO, SessionInfoVO sessionInfoVO) {

        uptPmixVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            uptPmixVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = uptPmixVO.getStoreCds().split(",");
        uptPmixVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (uptPmixVO.getProdCds() != null && !"".equals(uptPmixVO.getProdCds())) {
            String[] prodCdList = uptPmixVO.getProdCds().split(",");
            uptPmixVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (uptPmixVO.getStoreHqBrandCd() == "" || uptPmixVO.getStoreHqBrandCd() == null || uptPmixVO.getProdHqBrandCd() == "" || uptPmixVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = uptPmixVO.getUserBrands().split(",");
                uptPmixVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "SUM(";
        for(int j = 1; j <= 20; j++) {
            sQuery1 += "tsdp.PAY_CNT_"  + (j < 10 ? "0" + j : j);
            sQuery1 += (j < 20 ? " + ":"");
        }
        sQuery1 += ") AS GUEST_CNT,";
        uptPmixVO.setsQuery1(sQuery1);

        return uptPmixMapper.getUptPmixList(uptPmixVO);
    }

    /** UPT & P.mix 엑셀 다운로드 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getUptPmixExcelList(UptPmixVO uptPmixVO, SessionInfoVO sessionInfoVO) {

        uptPmixVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            uptPmixVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = uptPmixVO.getStoreCds().split(",");
        uptPmixVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (uptPmixVO.getProdCds() != null && !"".equals(uptPmixVO.getProdCds())) {
            String[] prodCdList = uptPmixVO.getProdCds().split(",");
            uptPmixVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (uptPmixVO.getStoreHqBrandCd() == "" || uptPmixVO.getStoreHqBrandCd() == null || uptPmixVO.getProdHqBrandCd() == "" || uptPmixVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = uptPmixVO.getUserBrands().split(",");
                uptPmixVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "SUM(";
        for(int j = 1; j <= 20; j++) {
            sQuery1 += "tsdp.PAY_CNT_"  + (j < 10 ? "0" + j : j);
            sQuery1 += (j < 20 ? " + ":"");
        }
        sQuery1 += ") AS GUEST_CNT,";
        uptPmixVO.setsQuery1(sQuery1);

        return uptPmixMapper.getUptPmixExcelList(uptPmixVO);
    }
}
