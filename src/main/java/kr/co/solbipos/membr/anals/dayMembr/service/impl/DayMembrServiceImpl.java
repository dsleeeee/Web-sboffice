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
    public List<DefaultMap<Object>> getDayMembrList(DayMembrVO dayMembrVO, SessionInfoVO sessionInfoVO) {

        dayMembrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dayMembrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayMembrVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        dayMembrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayMembrVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 결제수단 array 값 세팅
        dayMembrVO.setArrPayCol(dayMembrVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayMembrVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dayMembrVO.setPivotPayCol(pivotPayCol);

        return mapper.getDayMembrList(dayMembrVO);
    }

    /** 매출정보 상세조회 - 팝업 */
    @Override
    public List<DefaultMap<Object>> getDayMembrPurchsList(DayMembrVO dayMembrVO, SessionInfoVO sessionInfoVO) {

        dayMembrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayMembrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayMembrVO.setEmpNo(sessionInfoVO.getEmpNo());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayMembrVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getDayMembrPurchsList(dayMembrVO);
    }

    /** 회원정보 상세조회 - 팝업 */
    @Override
    public DefaultMap<String> getDayMembrDetail(DayMembrVO dayMembrVO, SessionInfoVO sessionInfoVO) {

        dayMembrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayMembrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayMembrVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayMembrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        return mapper.getDayMembrDetail(dayMembrVO);
    }

    /** 회원정보 매출 상세조회 - 팝업 */
    @Override
    public List<DefaultMap<Object>> getDayMembrDetailPurchsList(DayMembrVO dayMembrVO, SessionInfoVO sessionInfoVO) {

        dayMembrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayMembrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayMembrVO.setEmpNo(sessionInfoVO.getEmpNo());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayMembrVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getDayMembrDetailPurchsList(dayMembrVO);
    }
}