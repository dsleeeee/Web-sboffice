package kr.co.solbipos.sale.status.orderEmp.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.orderEmp.service.OrderEmpService;
import kr.co.solbipos.sale.status.orderEmp.service.OrderEmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : OrderEmpServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 상품별(사이드)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.01.21  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.01.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("orderEmpService")
public class OrderEmpServiceImpl implements OrderEmpService {
    private final OrderEmpMapper orderEmpMapper;
    private final MessageService messageService;

    @Autowired
    public OrderEmpServiceImpl(OrderEmpMapper orderEmpMapper, MessageService messageService) {
        this.orderEmpMapper = orderEmpMapper;
        this.messageService = messageService;
    }

    /** 기간별탭 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO) {

        orderEmpVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderEmpVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(orderEmpVO.getStoreCd()).equals("")) {
            orderEmpVO.setArrStoreCd(orderEmpVO.getStoreCd().split(","));
        }
        return orderEmpMapper.getOrderEmpPeriodList(orderEmpVO);
    }

    /** 기간별탭 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodExcelList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO) {

        orderEmpVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderEmpVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(orderEmpVO.getStoreCd()).equals("")) {
            orderEmpVO.setArrStoreCd(orderEmpVO.getStoreCd().split(","));
        }
        return orderEmpMapper.getOrderEmpPeriodExcelList(orderEmpVO);
    }

    /** 기간별탭 상세 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodDtlList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO) {

        orderEmpVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderEmpVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(orderEmpVO.getStoreCd()).equals("")) {
            orderEmpVO.setArrStoreCd(orderEmpVO.getStoreCd().split(","));
        }
        return orderEmpMapper.getOrderEmpPeriodDtlList(orderEmpVO);
    }

    /** 기간별탭 상세 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpPeriodDtlExcelList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO) {

        orderEmpVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderEmpVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(orderEmpVO.getStoreCd()).equals("")) {
            orderEmpVO.setArrStoreCd(orderEmpVO.getStoreCd().split(","));
        }
        return orderEmpMapper.getOrderEmpPeriodDtlExcelList(orderEmpVO);
    }


    /** 일자별 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpDayList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO) {

        orderEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(orderEmpVO.getStoreCd()).equals("")) {
            orderEmpVO.setArrStoreCd(orderEmpVO.getStoreCd().split(","));
        }

        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";

        List<DefaultMap<String>> empNo = orderEmpMapper.getEmpMebList(orderEmpVO);

        for(int i = 0; i < empNo.size(); i++) {
            String j = empNo.get(i).get("nmcodeCd");
            String k = empNo.get(i).get("storeCd");

            sQuery1 +=", NVL(SUM(A.REAL_SALE_AMT" + i + "), 0) AS REAL_SALE_AMT" + i +  "\n";
            sQuery1 +=", NVL(SUM(A.BILL_CNT" + i + "), 0) AS BILL_CNT" + i +  "\n";

            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(tssd.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(CASE WHEN tssd.SALE_FG = '1' THEN 1 ELSE 0 END) + SUM(CASE WHEN tssd.SALE_FG = '-1' THEN -1 ELSE 0 END) ELSE NULL END AS BILL_CNT" + i +  "\n";
        }

        orderEmpVO.setsQuery1(sQuery1);
        orderEmpVO.setsQuery2(sQuery2);

        return orderEmpMapper.getOrderEmpDayList(orderEmpVO);
    }

    /** 일자별 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOrderEmpDayExcelList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO) {

        orderEmpVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(orderEmpVO.getStoreCd()).equals("")) {
            orderEmpVO.setArrStoreCd(orderEmpVO.getStoreCd().split(","));
        }

        // 판매자별 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";

        List<DefaultMap<String>> empNo = orderEmpMapper.getEmpMebList(orderEmpVO);

        for(int i = 0; i < empNo.size(); i++) {
            String j = empNo.get(i).get("nmcodeCd");
            String k = empNo.get(i).get("storeCd");

            sQuery1 +=", NVL(SUM(A.REAL_SALE_AMT" + i + "), 0) AS REAL_SALE_AMT" + i +  "\n";
            sQuery1 +=", NVL(SUM(A.BILL_CNT" + i + "), 0) AS BILL_CNT" + i +  "\n";

            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(tssd.REAL_SALE_AMT) ELSE NULL END AS REAL_SALE_AMT" + i +  "\n";
            sQuery2 +=", CASE WHEN tssd.STORE_CD =" + "'"+k+"'" + " AND tssd.ORDER_EMP_NO = " + "'"+j+"'" + " THEN SUM(CASE WHEN tssd.SALE_FG = '1' THEN 1 ELSE 0 END) + SUM(CASE WHEN tssd.SALE_FG = '-1' THEN -1 ELSE 0 END) ELSE NULL END AS BILL_CNT" + i +  "\n";
        }

        orderEmpVO.setsQuery1(sQuery1);
        orderEmpVO.setsQuery2(sQuery2);

        return orderEmpMapper.getOrderEmpDayExcelList(orderEmpVO);
    }

}