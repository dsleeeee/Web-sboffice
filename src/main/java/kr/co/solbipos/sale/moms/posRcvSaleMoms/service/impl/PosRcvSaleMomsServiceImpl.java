package kr.co.solbipos.sale.moms.posRcvSaleMoms.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.BizException;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsService;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : PosRcvSaleMomsServiceImpl.java
 * @Description : 맘스터치 > 정산 > POS내역수신(매출)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.18  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("posRcvSaleMomsService")
public class PosRcvSaleMomsServiceImpl implements PosRcvSaleMomsService {

    private final PosRcvSaleMomsMapper posRcvSaleMomsMapper;

    @Autowired
    public PosRcvSaleMomsServiceImpl(PosRcvSaleMomsMapper posRcvSaleMomsMapper) {
        this.posRcvSaleMomsMapper = posRcvSaleMomsMapper;
    }

    /** POS내역수신(매출) - 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getPosRcvSaleMomsList(PosRcvSaleMomsVO posRcvSaleMomsVO, SessionInfoVO sessionInfoVO) {

        // 필수값 확인
        if (posRcvSaleMomsVO.getSaleDate() == null || posRcvSaleMomsVO.getSaleDate().trim().isEmpty()
                || posRcvSaleMomsVO.getStoreCd() == null || posRcvSaleMomsVO.getStoreCd().trim().isEmpty()) {
            throw new BizException(Status.FAIL, "조회일자와 매장코드는 필수입니다.");
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = posRcvSaleMomsVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                posRcvSaleMomsVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = posRcvSaleMomsVO.getArrDlvrInFgCol(); // 주문채널


        // 내점, 배달, 포장의 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";

            sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
        }

        // 내점, 배달, 포장의 주문채널별 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            for (int i = 0; i < list.length; i++) {
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";

                sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery2 += ", (CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";

            }
        }

        posRcvSaleMomsVO.setsQuery1(sQuery1);
        posRcvSaleMomsVO.setsQuery2(sQuery2);

        posRcvSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return posRcvSaleMomsMapper.getPosRcvSaleMomsList(posRcvSaleMomsVO);
    }
}
