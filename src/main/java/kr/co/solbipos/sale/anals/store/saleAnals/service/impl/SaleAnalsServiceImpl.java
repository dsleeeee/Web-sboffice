package kr.co.solbipos.sale.anals.store.saleAnals.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.store.saleAnals.service.SaleAnalsService;
import kr.co.solbipos.sale.anals.store.saleAnals.service.SaleAnalsVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.day.day.service.impl.DayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("saleAnalsService")
public class SaleAnalsServiceImpl implements SaleAnalsService {
    private final SaleAnalsMapper saleAnalsMapper;
    private final MessageService messageService;

    @Autowired
    public SaleAnalsServiceImpl(SaleAnalsMapper saleAnalsMapper, MessageService messageService) {
        this.saleAnalsMapper = saleAnalsMapper;
        this.messageService = messageService;
    }


    /** 일별 - 일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayList(SaleAnalsVO saleAnalsVO, SessionInfoVO sessionInfoVO) {
        saleAnalsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleAnalsVO.setEmpNo(sessionInfoVO.getEmpNo());
        saleAnalsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(saleAnalsVO.getStoreCd()).equals("")) {
            saleAnalsVO.setArrStoreCd(saleAnalsVO.getStoreCd().split(","));
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = saleAnalsVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        saleAnalsVO.setPivotPayCol(pivotPayCol);
        saleAnalsVO.setArrPayCol(payCol.split(","));

        return saleAnalsMapper.getDayList(saleAnalsVO);
    }

    /** 월별 - 월별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMonthList(SaleAnalsVO saleAnalsVO, SessionInfoVO sessionInfoVO) {

        saleAnalsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleAnalsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleAnalsVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            saleAnalsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = saleAnalsVO.getStoreCds().split(",");
        saleAnalsVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        saleAnalsVO.setArrPayCol(saleAnalsVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = saleAnalsVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        saleAnalsVO.setPivotPayCol(pivotPayCol);

        return saleAnalsMapper.getMonthList(saleAnalsVO);
    }
}