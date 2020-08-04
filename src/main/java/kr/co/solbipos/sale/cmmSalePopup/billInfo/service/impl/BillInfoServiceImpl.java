package kr.co.solbipos.sale.cmmSalePopup.billInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.billInfo.service.BillInfoService;
import kr.co.solbipos.sale.cmmSalePopup.billInfo.service.BillInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BillInfoService")
public class BillInfoServiceImpl implements BillInfoService {
    private final BillInfoMapper billInfoMapper;
    private final MessageService messageService;

    public BillInfoServiceImpl(BillInfoMapper billInfoMapper, MessageService messageService) {
        this.billInfoMapper = billInfoMapper;
        this.messageService = messageService;
    }

    /** 매출공통팝업 - 영수증상세 종합내역 조회 */
    @Override
    public DefaultMap<String> getBillInfo(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO) {
        //billInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return billInfoMapper.getBillInfo(billInfoVO);
    }


    /** 매출공통팝업 - 영수증상세 결제내역 조회 */
    @Override
    public DefaultMap<String> getBillPayInfo(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO) {
       // billInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 결제수단 array 값 세팅
        billInfoVO.setArrPayCol(billInfoVO.getPayCol().split(","));

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = billInfoVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        billInfoVO.setPivotPayCol(pivotPayCol);

        return billInfoMapper.getBillPayInfo(billInfoVO);
    }


    /** 매출공통팝업 - 영수증상세 방문인원 조회 */
    @Override
    public DefaultMap<String> getBillGuestInfo(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO) {
        //billInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return billInfoMapper.getBillGuestInfo(billInfoVO);
    }


    /** 매출공통팝업 - 영수증상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getBillProdList(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO) {
        //billInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 할인구분 array 값 세팅
        billInfoVO.setArrDcCol(billInfoVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = billInfoVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        billInfoVO.setPivotDcCol(pivotDcCol);

        return billInfoMapper.getBillProdList(billInfoVO);
    }
}
