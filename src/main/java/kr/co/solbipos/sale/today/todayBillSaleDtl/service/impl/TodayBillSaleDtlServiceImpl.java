package kr.co.solbipos.sale.today.todayBillSaleDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.today.todayBillSaleDtl.service.TodayBillSaleDtlService;
import kr.co.solbipos.sale.today.todayBillSaleDtl.service.TodayBillSaleDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("todayBillSaleDtlService")
public class TodayBillSaleDtlServiceImpl implements TodayBillSaleDtlService {
    private final TodayBillSaleDtlMapper todayBillSaleDtlMapper;
    private final MessageService messageService;

    @Autowired
    public TodayBillSaleDtlServiceImpl(TodayBillSaleDtlMapper todayBillSaleDtlMapper, MessageService messageService) {
        this.todayBillSaleDtlMapper = todayBillSaleDtlMapper;
        this.messageService = messageService;
    }


    /** 영수증별매출상세현황 - 영수증별매출상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTodayBillSaleDtlList(TodayBillSaleDtlVO todayBillSaleDtlVO, SessionInfoVO sessionInfoVO) {
        todayBillSaleDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return todayBillSaleDtlMapper.getTodayBillSaleDtlList(todayBillSaleDtlVO);
    }

}
