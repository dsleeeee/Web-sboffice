package kr.co.solbipos.sale.anals.versusPeriod.hour.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.versusPeriod.hour.service.VersusPeriodHourService;
import kr.co.solbipos.sale.anals.versusPeriod.hour.service.VersusPeriodHourVO;

@Service("VersusPeriodHourService")
public class VersusPeriodHourServiceImpl implements VersusPeriodHourService {
    private final VersusPeriodHourMapper versusPeriodHourMapper;
    private final MessageService messageService;

    @Autowired
    public VersusPeriodHourServiceImpl(VersusPeriodHourMapper versusPeriodHourMapper, MessageService messageService) {
    	this.versusPeriodHourMapper = versusPeriodHourMapper;
        this.messageService = messageService;
    }


    /** 상품별 매출 - 시간대별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVersusPeriodHourList(VersusPeriodHourVO versusPeriodHourVO, SessionInfoVO sessionInfoVO) {

        versusPeriodHourVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        versusPeriodHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        versusPeriodHourVO.setEmpNo(sessionInfoVO.getEmpNo());
    	
    	if(!StringUtil.getOrBlank(versusPeriodHourVO.getStoreCd()).equals("")) {
        	versusPeriodHourVO.setArrStoreCd(versusPeriodHourVO.getStoreCd().split(","));
        }
    	
        return versusPeriodHourMapper.getVersusPeriodHourList(versusPeriodHourVO);
    }

}
