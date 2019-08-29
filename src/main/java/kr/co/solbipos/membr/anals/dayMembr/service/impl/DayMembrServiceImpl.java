package kr.co.solbipos.membr.anals.dayMembr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.dayMembr.service.DayMembrService;
import kr.co.solbipos.membr.anals.dayMembr.service.DayMembrVO;
import kr.co.solbipos.sale.day.day.service.impl.DayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.solbipos.sale.day.day.service.DayVO;

import java.util.List;

/**
 * @Class Name : DayMembrServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 일자별회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("DayMembrService")
@Transactional
public class DayMembrServiceImpl implements DayMembrService {
    private final DayMembrMapper mapper;
    private final DayMapper dayMapper;

    /** Constructor Injection */
    @Autowired
    public DayMembrServiceImpl(DayMembrMapper mapper, DayMapper dayMapper) {
        this.mapper = mapper;
        this.dayMapper = dayMapper;
    }

    /** 일자별회원 구매내역*/
    @Override
    public List<DefaultMap<Object>> getDayMembrPurchaseList(DayMembrVO dayMembrVO,
                                                             SessionInfoVO sessionInfoVO) {
        // <-- PIVOT -->
        DayVO dayVO = new DayVO();

        // 결제수단 조회
        List<DefaultMap<String>> payColList = dayMapper.getPayColList(dayVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String pivotPayCol = "";
        for(int i=0; i < payColList.size(); i++) {

            //System.out.println(i +"번째 : "+ payColList.get(i).getStr("payCd"));
            pivotPayCol += (pivotPayCol.equals("") ? "" : " ,") + "'" + payColList.get(i).getStr("payCd") + "'" +  " AS PAY" + payColList.get(i).getStr("payCd");
        }
        //System.out.println("PivotPayCol : " + pivotPayCol);
        // <-- /PIVOT -->


        dayMembrVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayMembrVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayMembrVO.setPivotPayCol(pivotPayCol);

        return mapper.getDayMembrPurchaseList(dayMembrVO);
    }
}