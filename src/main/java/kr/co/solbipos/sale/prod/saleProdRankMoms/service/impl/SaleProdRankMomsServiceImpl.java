package kr.co.solbipos.sale.prod.saleProdRankMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.saleProdRankMoms.service.SaleProdRankMomsService;
import kr.co.solbipos.sale.prod.saleProdRankMoms.service.SaleProdRankMomsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : SaleProdRankMomsServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별매출순위
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.06   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.12.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("saleProdRankMomsService")
@Transactional
public class SaleProdRankMomsServiceImpl implements SaleProdRankMomsService {

    private final  SaleProdRankMomsMapper saleProdRankMomsMapper;

    public SaleProdRankMomsServiceImpl(SaleProdRankMomsMapper saleProdRankMomsMapper) {
        this.saleProdRankMomsMapper = saleProdRankMomsMapper;
    }

    /** 상품별매출순위 조회 */
    @Override
    public List<DefaultMap<String>> getSaleProdRankList(SaleProdRankMomsVO saleProdRankMomsVO, SessionInfoVO sessionInfoVO){
        saleProdRankMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleProdRankMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            saleProdRankMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = saleProdRankMomsVO.getStoreCds().split(",");
        saleProdRankMomsVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (saleProdRankMomsVO.getProdCds() != null && !"".equals(saleProdRankMomsVO.getProdCds())) {
            String[] prodCdList = saleProdRankMomsVO.getProdCds().split(",");
            saleProdRankMomsVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (saleProdRankMomsVO.getStoreHqBrandCd() == "" || saleProdRankMomsVO.getStoreHqBrandCd() == null || saleProdRankMomsVO.getProdHqBrandCd() == "" || saleProdRankMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = saleProdRankMomsVO.getUserBrands().split(",");
                saleProdRankMomsVO.setUserBrandList(userBrandList);
            }
        }

        return saleProdRankMomsMapper.getSaleProdRankList(saleProdRankMomsVO);
    }

    /** 상품별매출순위 조회(엑셀용) */
    @Override
    public List<DefaultMap<String>> getSaleProdRankExcelList(SaleProdRankMomsVO saleProdRankMomsVO, SessionInfoVO sessionInfoVO){
        saleProdRankMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleProdRankMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            saleProdRankMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = saleProdRankMomsVO.getStoreCds().split(",");
        saleProdRankMomsVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (saleProdRankMomsVO.getProdCds() != null && !"".equals(saleProdRankMomsVO.getProdCds())) {
            String[] prodCdList = saleProdRankMomsVO.getProdCds().split(",");
            saleProdRankMomsVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (saleProdRankMomsVO.getStoreHqBrandCd() == "" || saleProdRankMomsVO.getStoreHqBrandCd() == null || saleProdRankMomsVO.getProdHqBrandCd() == "" || saleProdRankMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = saleProdRankMomsVO.getUserBrands().split(",");
                saleProdRankMomsVO.setUserBrandList(userBrandList);
            }
        }

        return saleProdRankMomsMapper.getSaleProdRankExcelList(saleProdRankMomsVO);

    }
}
