package kr.co.solbipos.sale.status.empCard.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.empCard.service.EmpCardService;
import kr.co.solbipos.sale.status.empCard.service.EmpCardVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : EmpCardServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 사원카드매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.05  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("empCardService")
@Transactional
public class EmpCardServiceImpl implements EmpCardService {
    private final EmpCardMapper empCardMapper;

    public EmpCardServiceImpl(EmpCardMapper empCardMapper) {
        this.empCardMapper = empCardMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpCardList(EmpCardVO empCardVO, SessionInfoVO sessionInfoVO) {

        empCardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            empCardVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        /** PAY_CD = 02 현금,현금영수증 분리 */
        // 결제수단 array 값 세팅
//        todayDtlVO.setArrPayCol(todayDtlVO.getPayCol().split(","));
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = empCardVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        empCardVO.setPivotPayCol(pivotPayCol);
        empCardVO.setArrPayCol(payCol.split(","));


        // 할인구분 array 값 세팅
        empCardVO.setArrDcCol(empCardVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = empCardVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        empCardVO.setPivotDcCol(pivotDcCol);

        return empCardMapper.getEmpCardList(empCardVO);
    }


}