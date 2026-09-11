package kr.co.solbipos.sale.benson.orderEmpBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.orderEmpBenson.service.OrderEmpBensonService;
import kr.co.solbipos.sale.benson.orderEmpBenson.service.OrderEmpBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : OrderEmpBensonServiceImpl.java
 * @Description : 벤슨 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승        최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("orderEmpBensonService")
public class OrderEmpBensonServiceImpl implements OrderEmpBensonService {
    private final OrderEmpBensonMapper orderEmpBensonMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public OrderEmpBensonServiceImpl(OrderEmpBensonMapper orderEmpBensonMapper, PopupMapper popupMapper, MessageService messageService) {
        this.orderEmpBensonMapper = orderEmpBensonMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 기간별탭 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO) {

        orderEmpBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderEmpBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(orderEmpBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderEmpBensonVO.getStoreCd(), 3900));
            orderEmpBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return orderEmpBensonMapper.getOrderEmpPeriodList(orderEmpBensonVO);
    }

    /** 기간별탭 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodExcelList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO) {

        orderEmpBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderEmpBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(orderEmpBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderEmpBensonVO.getStoreCd(), 3900));
            orderEmpBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return orderEmpBensonMapper.getOrderEmpPeriodExcelList(orderEmpBensonVO);
    }

    /** 기간별탭 상세 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodDtlList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO) {

        orderEmpBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // empNo는 왼쪽 그리드에서 선택한 주문자 사원번호를 그대로 사용 (세션 사원번호로 덮어쓰지 않음)

        return orderEmpBensonMapper.getOrderEmpPeriodDtlList(orderEmpBensonVO);
    }

    /** 기간별탭 상세 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodDtlExcelList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO) {

        orderEmpBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // empNo는 왼쪽 그리드에서 선택한 주문자 사원번호를 그대로 사용 (세션 사원번호로 덮어쓰지 않음)

        return orderEmpBensonMapper.getOrderEmpPeriodDtlExcelList(orderEmpBensonVO);
    }


    /** 일자별 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpDayList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO) {

        orderEmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(orderEmpBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderEmpBensonVO.getStoreCd(), 3900));
            orderEmpBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";

        List<DefaultMap<String>> empNo = orderEmpBensonMapper.getEmpMebList(orderEmpBensonVO);

        for(int i = 0; i < empNo.size(); i++) {
            String j = empNo.get(i).get("nmcodeCd");
            String k = empNo.get(i).get("storeCd");

            sQuery1 +=", NVL(SUM(A.REAL_SALE_AMT" + i + "), 0) AS REAL_SALE_AMT" + i +  "\n";
            sQuery1 +=", NVL(SUM(A.BILL_CNT" + i + "), 0) AS BILL_CNT" + i +  "\n";

            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(tssd.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(CASE WHEN tssd.SALE_FG = '1' THEN 1 ELSE 0 END) + SUM(CASE WHEN tssd.SALE_FG = '-1' THEN -1 ELSE 0 END) ELSE NULL END AS BILL_CNT" + i +  "\n";
        }

        orderEmpBensonVO.setsQuery1(sQuery1);
        orderEmpBensonVO.setsQuery2(sQuery2);

        return orderEmpBensonMapper.getOrderEmpDayList(orderEmpBensonVO);
    }

    /** 일자별 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpDayExcelList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO) {

        orderEmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(orderEmpBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderEmpBensonVO.getStoreCd(), 3900));
            orderEmpBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";

        List<DefaultMap<String>> empNo = orderEmpBensonMapper.getEmpMebList(orderEmpBensonVO);

        for(int i = 0; i < empNo.size(); i++) {
            String j = empNo.get(i).get("nmcodeCd");
            String k = empNo.get(i).get("storeCd");

            sQuery1 +=", NVL(SUM(A.REAL_SALE_AMT" + i + "), 0) AS REAL_SALE_AMT" + i +  "\n";
            sQuery1 +=", NVL(SUM(A.BILL_CNT" + i + "), 0) AS BILL_CNT" + i +  "\n";

            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(tssd.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(CASE WHEN tssd.SALE_FG = '1' THEN 1 ELSE 0 END) + SUM(CASE WHEN tssd.SALE_FG = '-1' THEN -1 ELSE 0 END) ELSE NULL END AS BILL_CNT" + i +  "\n";
        }

        orderEmpBensonVO.setsQuery1(sQuery1);
        orderEmpBensonVO.setsQuery2(sQuery2);

        return orderEmpBensonMapper.getOrderEmpDayExcelList(orderEmpBensonVO);
    }

}
