package kr.co.solbipos.sale.status.emp.dayPeriod.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
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
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public EmpDayPeriodServiceImpl(EmpDayPeriodMapper empDayPeriodMapper, PopupMapper popupMapper, MessageService messageService) {
        this.empDayPeriodMapper = empDayPeriodMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }


    /** 판매자별 설정기간별 탭 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEmpDayPeriodList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO) {
        empDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        empDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        empDayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(empDayPeriodVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(empDayPeriodVO.getStoreCd(), 3900));
            empDayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	
        return empDayPeriodMapper.getEmpDayPeriodList(empDayPeriodVO);
    }

    /** 판매자별 설정기간별 탭 - 리스트(엑셀) 조회 */
    @Override
    public List<DefaultMap<String>> getEmpDayPeriodExcelList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO) {
        empDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        empDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        empDayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(empDayPeriodVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(empDayPeriodVO.getStoreCd(), 3900));
            empDayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    	
        return empDayPeriodMapper.getEmpDayPeriodExcelList(empDayPeriodVO);
    }
    
    /** 판매자별 설정기간별 탭 - 리스트 상세 조회 */
	@Override
	public List<DefaultMap<String>> getEmpDayPeriodDtlList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO) {
		empDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return empDayPeriodMapper.getEmpDayPeriodDtlList(empDayPeriodVO);
	}
	
    /** 판매자별 설정기간별 탭 - 리스트 상세(엑셀) 조회 */
	@Override
	public List<DefaultMap<String>> getEmpDayPeriodDtlExcelList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO) {
		empDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return empDayPeriodMapper.getEmpDayPeriodDtlExcelList(empDayPeriodVO);
	}

}
