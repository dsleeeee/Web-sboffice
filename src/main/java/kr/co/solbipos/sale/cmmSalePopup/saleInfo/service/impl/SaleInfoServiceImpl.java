package kr.co.solbipos.sale.cmmSalePopup.saleInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.cmmSalePopup.saleInfo.service.SaleInfoService;
import kr.co.solbipos.sale.cmmSalePopup.saleInfo.service.SaleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("saleInfoService")
public class SaleInfoServiceImpl implements SaleInfoService {
    private final SaleInfoMapper saleInfoMapper;

    public SaleInfoServiceImpl(SaleInfoMapper saleInfoMapper) {
        this.saleInfoMapper = saleInfoMapper;
    }

    /** 매장정보,매출종합내역,결제내역,회원정보 조회 */
    @Override
    public DefaultMap<String> getSaleDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO) {

        // 결제수단 array 값 세팅
        saleInfoVO.setArrPayCol(saleInfoVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = saleInfoVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        saleInfoVO.setPivotPayCol(pivotPayCol);

        return saleInfoMapper.getSaleDtlList(saleInfoVO);
    }

    /** 신용카드 결재내역 조회 */
    @Override
    public List<DefaultMap<String>> getSaleCardDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO) {

        return saleInfoMapper.getSaleCardDtlList(saleInfoVO);
    }

    /** 현금영수증 결재내역 조회 */
    @Override
    public List<DefaultMap<String>> getSaleCashDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO) {

        return saleInfoMapper.getSaleCashDtlList(saleInfoVO);
    }

    /** 상품내역 조회 */
    @Override
    public List<DefaultMap<String>> getSaleProdDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO) {

        return saleInfoMapper.getSaleProdDtlList(saleInfoVO);
    }
}
