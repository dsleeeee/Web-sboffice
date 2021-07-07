package kr.co.solbipos.sale.today.todayDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("todayDtlService")
public class TodayDtlServiceImpl implements TodayDtlService {
    private final TodayDtlMapper todayDtlMapper;
    private final MessageService messageService;

    @Autowired
    public TodayDtlServiceImpl(TodayDtlMapper todayDtlMapper, MessageService messageService) {
        this.todayDtlMapper = todayDtlMapper;
        this.messageService = messageService;
    }


    /** 당일매출상세현황 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO) {
        return todayDtlMapper.getPayColList(todayDtlVO);
    }


    /** 당일매출상세현황 - 매장 포스 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorePosList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO) {
        return todayDtlMapper.getStorePosList(todayDtlVO);
    }


    /** 당일매출상세현황 - 할인 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcColList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO) {
        return todayDtlMapper.getDcColList(todayDtlVO);
    }


    /** 당일매출상세현황 - 객수 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getGuestColList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO) {

        todayDtlVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        todayDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            todayDtlVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return todayDtlMapper.getGuestColList(todayDtlVO);
    }


    /** 당일매출상세현황 - 매출종합 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTodayDtlList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO) {
        todayDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 결제수단, 할인구분 컬럼 세팅
        todayDtlVO = setCol(todayDtlVO, sessionInfoVO);

        return todayDtlMapper.getTodayDtlList(todayDtlVO);
    }


    /** 당일매출상세현황 - 매출상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTodayDtlDetailList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO) {
        todayDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 결제수단, 할인구분 컬럼 세팅
        todayDtlVO = setCol(todayDtlVO, sessionInfoVO);

        return todayDtlMapper.getTodayDtlDetailList(todayDtlVO);
    }


    /** 결제수단, 할인구분 컬럼 세팅  */
    public TodayDtlVO setCol(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO) {
        // 결제수단 array 값 세팅
        todayDtlVO.setArrPayCol(todayDtlVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = todayDtlVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        todayDtlVO.setPivotPayCol(pivotPayCol);


        // 할인구분 array 값 세팅
        todayDtlVO.setArrDcCol(todayDtlVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = todayDtlVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        todayDtlVO.setPivotDcCol(pivotDcCol);

        return todayDtlVO;
    }

}
