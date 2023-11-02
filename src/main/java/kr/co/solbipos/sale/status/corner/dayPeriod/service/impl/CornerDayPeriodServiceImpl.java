package kr.co.solbipos.sale.status.corner.dayPeriod.service.impl;

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
import kr.co.solbipos.sale.status.corner.dayPeriod.service.CornerDayPeriodService;
import kr.co.solbipos.sale.status.corner.dayPeriod.service.CornerDayPeriodVO;

@Service("cornerDayPeriodService")
public class CornerDayPeriodServiceImpl implements CornerDayPeriodService {
    private final CornerDayPeriodMapper cornerDayPeriodMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public CornerDayPeriodServiceImpl(CornerDayPeriodMapper cornerDayPeriodMapper, PopupMapper popupMapper, MessageService messageService) {
        this.cornerDayPeriodMapper = cornerDayPeriodMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

	/** 설정기간별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getCornerDayPeriodList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO) {
    	cornerDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	cornerDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	
    	if (cornerDayPeriodVO.getCornrCd() != null && !"".equals(cornerDayPeriodVO.getCornrCd())) {
    		String[] arrCornrCd = cornerDayPeriodVO.getCornrCd().split(",");

    		if (arrCornrCd.length > 0) {
    			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
    				cornerDayPeriodVO.setArrCornrCd(arrCornrCd);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(cornerDayPeriodVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(cornerDayPeriodVO.getStoreCd(), 3900));
                cornerDayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
        return cornerDayPeriodMapper.getCornerDayPeriodList(cornerDayPeriodVO);
    }

	/** 설정기간별탭 - 엑셀 조회 */
	@Override
	public List<DefaultMap<String>> getCornerDayPeriodExcelList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO) {
		cornerDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		cornerDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		if (cornerDayPeriodVO.getCornrCd() != null && !"".equals(cornerDayPeriodVO.getCornrCd())) {
			String[] arrCornrCd = cornerDayPeriodVO.getCornrCd().split(",");

			if (arrCornrCd.length > 0) {
				if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
					cornerDayPeriodVO.setArrCornrCd(arrCornrCd);
				}
			}
		} else {
            if(!StringUtil.getOrBlank(cornerDayPeriodVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(cornerDayPeriodVO.getStoreCd(), 3900));
                cornerDayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
		}
		return cornerDayPeriodMapper.getCornerDayPeriodExcelList(cornerDayPeriodVO);
	}

	/** 설정기간별탭 - 상세 조회 */
	@Override
	public List<DefaultMap<String>> getCornerDayPeriodDtlList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO) {

		cornerDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		cornerDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		return cornerDayPeriodMapper.getCornerDayPeriodDtlList(cornerDayPeriodVO);
	}

	/** 설정기간별탭 - 상세 엑셀 조회 */
	@Override
	public List<DefaultMap<String>> getCornerDayPeriodDtlExcelList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO) {

		cornerDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		cornerDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		return cornerDayPeriodMapper.getCornerDayPeriodDtlExcelList(cornerDayPeriodVO);
	}

}