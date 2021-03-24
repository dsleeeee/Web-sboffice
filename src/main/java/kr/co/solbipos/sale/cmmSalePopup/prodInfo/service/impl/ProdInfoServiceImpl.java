package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoService;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("prodInfoService")
public class ProdInfoServiceImpl implements ProdInfoService {
    private final ProdInfoMapper prodInfoMapper;

    public ProdInfoServiceImpl(ProdInfoMapper prodInfoMapper) {
        this.prodInfoMapper = prodInfoMapper;
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDtlList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoVO.setLevel("Level" + prodInfoVO.getLevel());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            // 매장 array 값 세팅
            String[] storeCds = prodInfoVO.getStoreCds().split(",");
            prodInfoVO.setStoreCdList(storeCds);
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodInfoVO.setStoreCds(sessionInfoVO.getStoreCd());
            String[] storeCds = prodInfoVO.getStoreCds().split(",");
            prodInfoVO.setStoreCdList(storeCds);
        }

        // 기간별매출 > 일자별 탭 > 상품분류별 탭
        // 기간별매출 > 월별 탭 > 상품분류별 탭
        if(("dayProdClass").equals(prodInfoVO.getGubun()) || ("monthProdClass").equals(prodInfoVO.getGubun())) {
            if(prodInfoVO.getStrProdClassCd() != null && prodInfoVO.getStrProdClassCd().length() > 0) {
                // 레벨에 따른 분류값 가져와서 배열변수에 넣음.
                prodInfoVO.setArrProdClassCd(prodInfoVO.getStrProdClassCd().split(","));
            }
        }

        return prodInfoMapper.getProdSaleDtlList(prodInfoVO);
    }

}