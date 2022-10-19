package kr.co.solbipos.sale.prod.prodRankMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.prodRankMoms.service.ProdRankMomsService;
import kr.co.solbipos.sale.prod.prodRankMoms.service.ProdRankMomsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdRankServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별 매출 순위
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodRankMomsService")
@Transactional
public class ProdRankMomsServiceImpl implements ProdRankMomsService {
    private final ProdRankMomsMapper prodRankMomsMapper;

    public ProdRankMomsServiceImpl(ProdRankMomsMapper prodRankMomsMapper) {
        this.prodRankMomsMapper = prodRankMomsMapper;
    }


    /** 상품별 매출 순위 */
    @Override
    public List<DefaultMap<String>> getProdRankList(ProdRankMomsVO prodRankMomsVO, SessionInfoVO sessionInfoVO) {

        prodRankMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodRankMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodRankMomsVO.getStoreCds().split(",");
        prodRankMomsVO.setStoreCdList(storeCds);

        return prodRankMomsMapper.getProdRankList(prodRankMomsVO);
    }


    /** 상품매출순위탭 - 차트 조회 */
    @Override
    public List<DefaultMap<String>> getProdRankChartList(ProdRankMomsVO prodRankMomsVO, SessionInfoVO sessionInfoVO) {

        prodRankMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodRankMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodRankMomsVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodRankMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodRankMomsVO.getStoreCds().split(",");
        prodRankMomsVO.setStoreCdList(storeCds);

        return prodRankMomsMapper.getProdRankChartList(prodRankMomsVO);
    }

    /** 상품매출순위탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdRankExcelList(ProdRankMomsVO prodRankMomsVO, SessionInfoVO sessionInfoVO) {

        prodRankMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodRankMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodRankMomsVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodRankMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodRankMomsVO.getStoreCds().split(",");
        prodRankMomsVO.setStoreCdList(storeCds);

        return prodRankMomsMapper.getProdRankExcelList(prodRankMomsVO);
    }

}