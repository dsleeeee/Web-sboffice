package kr.co.solbipos.stock.status.currUnity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.currUnity.service.CurrUnityService;
import kr.co.solbipos.stock.status.currUnity.service.CurrUnityVO;

@Service("CurrUnitykService")
public class CurrUnityServiceImpl implements CurrUnityService {

	private final CurrUnityMapper currUnityMapper;
    private final MessageService messageService;

    @Autowired
    public CurrUnityServiceImpl(CurrUnityMapper currUnityMapper, MessageService messageService) {
        this.currUnityMapper = currUnityMapper;
        this.messageService = messageService;
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCurrUnityList(CurrUnityVO currUnityVO, SessionInfoVO sessionInfoVO) {

		currUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        currUnityVO.setEmpNo(sessionInfoVO.getEmpNo());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(currUnityVO.getVendrCd()).equals("")) {
            currUnityVO.setArrVendrCd(currUnityVO.getVendrCd().split(","));
        }

        return currUnityMapper.getCurrUnityList(currUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCurrUnityHqDtlList(CurrUnityVO currUnityVO, SessionInfoVO sessionInfoVO) {

		currUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(currUnityVO.getVendrCd()).equals("")) {
            currUnityVO.setArrVendrCd(currUnityVO.getVendrCd().split(","));
        }

        return currUnityMapper.getCurrUnityHqDtlList(currUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCurrUnityStoreDtlList(CurrUnityVO currUnityVO, SessionInfoVO sessionInfoVO) {

		currUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        currUnityVO.setEmpNo(sessionInfoVO.getEmpNo());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(currUnityVO.getVendrCd()).equals("")) {
            currUnityVO.setArrVendrCd(currUnityVO.getVendrCd().split(","));
        }

        return currUnityMapper.getCurrUnityStoreDtlList(currUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 전체 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCurrUnityExcelList(CurrUnityVO currUnityVO, SessionInfoVO sessionInfoVO) {
		currUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(currUnityVO.getVendrCd()).equals("")) {
            currUnityVO.setArrVendrCd(currUnityVO.getVendrCd().split(","));
        }

        return currUnityMapper.getCurrUnityExcelList(currUnityVO);
	}

    /** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 전체 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCurrUnityHqDtlExcelList(CurrUnityVO currUnityVO, SessionInfoVO sessionInfoVO) {
		currUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(currUnityVO.getVendrCd()).equals("")) {
            currUnityVO.setArrVendrCd(currUnityVO.getVendrCd().split(","));
        }

        return currUnityMapper.getCurrUnityHqDtlExcelList(currUnityVO);
	}

    /** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 전체 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getCurrUnityStoreDtlExcelList(CurrUnityVO currUnityVO,
			SessionInfoVO sessionInfoVO) {
		currUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(currUnityVO.getVendrCd()).equals("")) {
            currUnityVO.setArrVendrCd(currUnityVO.getVendrCd().split(","));
        }

        return currUnityMapper.getCurrUnityStoreDtlExcelList(currUnityVO);
	}
}
