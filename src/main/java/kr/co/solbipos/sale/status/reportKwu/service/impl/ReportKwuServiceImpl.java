package kr.co.solbipos.sale.status.reportKwu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.reportKwu.service.ReportKwuService;
import kr.co.solbipos.sale.status.reportKwu.service.ReportKwuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : ReportKwuServiceImpl.java
 * @Description : 광운대 > 리포트 > 분류별결제수단별 매출내역
 *                광운대 > 리포트 > 결제수단별 매출내역
 *                광운대 > 리포트 > 신용카드 매출내역
 *                광운대 > 리포트 > 현금영수증 발행내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.13  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("reportKwuService")
public class ReportKwuServiceImpl implements ReportKwuService {
    private final ReportKwuMapper reportKwuMapper;

    @Autowired
    public ReportKwuServiceImpl(ReportKwuMapper reportKwuMapper) {
        this.reportKwuMapper = reportKwuMapper;
    }

    /** 분류별결제수단별 매출내역 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdClassPayFgSaleList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO) {

        reportKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        reportKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        reportKwuVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(reportKwuVO.getStoreCd()).equals("")) {
            reportKwuVO.setArrStoreCd(reportKwuVO.getStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        reportKwuVO.setArrPayCol(reportKwuVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = reportKwuVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        reportKwuVO.setPivotPayCol(pivotPayCol);

        return reportKwuMapper.getProdClassPayFgSaleList(reportKwuVO);
    }

    /** 결제수단별 매출내역 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayFgSaleList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO) {

       reportKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
       reportKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
       reportKwuVO.setEmpNo(sessionInfoVO.getEmpNo());

       if(!StringUtil.getOrBlank(reportKwuVO.getStoreCd()).equals("")) {
           reportKwuVO.setArrStoreCd(reportKwuVO.getStoreCd().split(","));
       }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = reportKwuVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        reportKwuVO.setPivotPayCol(pivotPayCol);
        reportKwuVO.setArrPayCol(payCol.split(","));

       return reportKwuMapper.getPayFgSaleList(reportKwuVO);
    }

    /** 신용카드 매출내역 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayCardSaleList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO) {

       reportKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
       reportKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
       reportKwuVO.setEmpNo(sessionInfoVO.getEmpNo());

       if(!StringUtil.getOrBlank(reportKwuVO.getStoreCd()).equals("")) {
           reportKwuVO.setArrStoreCd(reportKwuVO.getStoreCd().split(","));
       }

       return reportKwuMapper.getPayCardSaleList(reportKwuVO);
    }

    /** 현금영수증 발행내역 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCashBillInfoList(ReportKwuVO reportKwuVO, SessionInfoVO sessionInfoVO) {

       reportKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
       reportKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
       reportKwuVO.setEmpNo(sessionInfoVO.getEmpNo());

       if(!StringUtil.getOrBlank(reportKwuVO.getStoreCd()).equals("")) {
           reportKwuVO.setArrStoreCd(reportKwuVO.getStoreCd().split(","));
       }

       return reportKwuMapper.getCashBillInfoList(reportKwuVO);
    }

}
