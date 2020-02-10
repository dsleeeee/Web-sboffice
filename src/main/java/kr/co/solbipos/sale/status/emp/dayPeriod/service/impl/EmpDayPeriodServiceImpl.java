package kr.co.solbipos.sale.status.emp.dayPeriod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.dayPeriod.service.EmpDayPeriodService;
import kr.co.solbipos.sale.status.emp.dayPeriod.service.EmpDayPeriodVO;

@Service("empDayPeriodService")
public class EmpDayPeriodServiceImpl implements EmpDayPeriodService {
    private final EmpDayPeriodMapper empDayPeriodMapper;
    private final MessageService messageService;

    @Autowired
    public EmpDayPeriodServiceImpl(EmpDayPeriodMapper empDayPeriodMapper, MessageService messageService) {
        this.empDayPeriodMapper = empDayPeriodMapper;
        this.messageService = messageService;
    }


    /** 코너별매출 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEmpDayPeriodList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO) {
    	empDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

    	if(!StringUtil.getOrBlank(empDayPeriodVO.getStoreCd()).equals("")) {
        	empDayPeriodVO.setArrStoreCd(empDayPeriodVO.getStoreCd().split(","));
        }
    	
        return empDayPeriodMapper.getEmpDayPeriodList(empDayPeriodVO);
    }

    
    /** 코너별매출 - 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getEmpDayPeriodDtlList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO) {
		empDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return empDayPeriodMapper.getEmpDayPeriodDtlList(empDayPeriodVO);
	}

}
