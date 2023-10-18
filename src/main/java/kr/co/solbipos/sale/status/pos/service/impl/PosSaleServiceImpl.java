package kr.co.solbipos.sale.status.pos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.service.PosSaleService;
import kr.co.solbipos.sale.status.pos.service.PosSaleVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("posSaleService")
public class PosSaleServiceImpl implements PosSaleService {
    private final PosSaleMapper posSaleMapper;
    private final MessageService messageService;
    private final PopupMapper popupMapper;

    @Autowired
    public PosSaleServiceImpl(PosSaleMapper posSaleMapper, MessageService messageService, PopupMapper popupMapper) {
        this.posSaleMapper = posSaleMapper;
        this.messageService = messageService;
        this.popupMapper = popupMapper;
    }

    /** 포스별매출일자별 - 매장 포스 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorePosList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return posSaleMapper.getStorePosList(posSaleVO);
    }

    /** 포스별매출일자별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayVO.getStoreCd()).equals("")) {
//    		posDayVO.setArrStoreCd(posDayVO.getStoreCd().split(","));
//		}

        return posSaleMapper.getPosDayList(posSaleVO);
    }

    /** 포스별매출일자별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayVO.getStoreCd()).equals("")) {
//    		posDayVO.setArrStoreCd(posDayVO.getStoreCd().split(","));
//		}

        return posSaleMapper.getPosDayExcelList(posSaleVO);
    }

    /** 포스별매출 - 매장 포스 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosNmList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getHqOfficeCd() != null && !"".equals(sessionInfoVO.getHqOfficeCd())) {
            posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }

        return posSaleMapper.getPosNmList(posSaleVO);
    }

    /** 포스별매출요일별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayOfWeekList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayOfWeekVO.getStoreCd()).equals("")) {
//    		posDayOfWeekVO.setArrStoreCd(posDayOfWeekVO.getStoreCd().split(","));
//		}

        return posSaleMapper.getPosDayOfWeekList(posSaleVO);
    }

    /** 포스별매출요일별 - 차트 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayOfWeekChartList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayOfWeekVO.getStoreCd()).equals("")) {
//    		posDayOfWeekVO.setArrStoreCd(posDayOfWeekVO.getStoreCd().split(","));
//		}

        return posSaleMapper.getPosDayOfWeekChartList(posSaleVO);
    }

    /** 포스별매출월별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosMonthList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posMonthVO.getStoreCd()).equals("")) {
//    		posMonthVO.setArrStoreCd(posMonthVO.getStoreCd().split(","));
//		}

        return posSaleMapper.getPosMonthList(posSaleVO);
    }

    /** 포스별매출 월별 탭 - 엑셀리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosMonthExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posMonthVO.getStoreCd()).equals("")) {
//    		posMonthVO.setArrStoreCd(posMonthVO.getStoreCd().split(","));
//		}

        return posSaleMapper.getPosMonthExcelList(posSaleVO);
    }

    /** 상품별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getPosProdList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return posSaleMapper.getPosProdList(posSaleVO);
    }

    /** 상품별탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getPosProdExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return posSaleMapper.getPosProdExcelList(posSaleVO);
    }

    /** 설정기간별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayPeriodList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        posSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(posSaleVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(posSaleVO.getStoreCd(), 3900));
            posSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return posSaleMapper.getPosDayPeriodList(posSaleVO);
    }

    /** 설정기간별탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayPeriodExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        posSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(posSaleVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(posSaleVO.getStoreCd(), 3900));
            posSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return posSaleMapper.getPosDayPeriodExcelList(posSaleVO);
    }

    /** 설정기간별탭 - 상세 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayPeriodDtlList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return posSaleMapper.getPosDayPeriodDtlList(posSaleVO);
    }

    /** 설정기간별탭 - 상세 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getPosDayPeriodDtlExcelList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return posSaleMapper.getPosDayPeriodDtlExcelList(posSaleVO);
    }

    /** 시간대별별 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosHourList(PosSaleVO posSaleVO, SessionInfoVO sessionInfoVO) {

        posSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//    	if (!StringUtil.getOrBlank(posDayVO.getStoreCd()).equals("")) {
//    		posDayVO.setArrStoreCd(posDayVO.getStoreCd().split(","));
//		}

        return posSaleMapper.getPosHourList(posSaleVO);
    }
}