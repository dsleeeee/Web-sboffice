package kr.co.solbipos.sale.day.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dayService")
public class DayServiceImpl implements DayService {
    private final DayMapper dayMapper;
    private final MessageService messageService;

    @Autowired
    public DayServiceImpl(DayMapper dayMapper, MessageService messageService) {
        this.dayMapper = dayMapper;
        this.messageService = messageService;
    }


    /** 일자별 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        return dayMapper.getPayColList(dayVO);
    }


    /** 일자별 - 할인 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        return dayMapper.getDcColList(dayVO);
    }


    /** 일자별(일별종합 탭) - 일별종합 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayTotalList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        dayVO.setArrPayCol(dayVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dayVO.setPivotPayCol(pivotPayCol);

        return dayMapper.getDayTotalList(dayVO);
    }


    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayStoreDtlList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dayMapper.getDayStoreDtlList(dayVO);
    }


    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayStoreDcList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 할인구분 array 값 세팅
        dayVO.setArrDcCol(dayVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = dayVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        dayVO.setPivotDcCol(pivotDcCol);

        return dayMapper.getDayStoreDcList(dayVO);
    }


    /** 일자별(할인구분별 탭) - 할인구분 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayDcList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        // 할인구분 array 값 세팅
        dayVO.setArrDcCol(dayVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = dayVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        dayVO.setPivotDcCol(pivotDcCol);

        return dayMapper.getDayDcList(dayVO);
    }
}
