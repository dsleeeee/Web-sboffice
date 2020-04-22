package kr.co.solbipos.sale.anals.versusPeriod.cls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.versusPeriod.cls.service.VersusPeriodClassService;
import kr.co.solbipos.sale.anals.versusPeriod.cls.service.VersusPeriodClassVO;

@Service("VersusPeriodClassService")
public class VersusPeriodClassServiceImpl implements VersusPeriodClassService {
    private final VersusPeriodClassMapper versusPeriodClassMapper;
    private final MessageService messageService;

    @Autowired
    public VersusPeriodClassServiceImpl(VersusPeriodClassMapper versusPeriodClassMapper, MessageService messageService) {
    	this.versusPeriodClassMapper = versusPeriodClassMapper;
        this.messageService = messageService;
    }


    /** 대비기간매출분석 - 분류상품별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVersusPeriodClassList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO) {

    	versusPeriodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(versusPeriodClassVO.getStoreCd()).equals("")) {
        	versusPeriodClassVO.setArrStoreCd(versusPeriodClassVO.getStoreCd().split(","));
        }

        return versusPeriodClassMapper.getVersusPeriodClassList(versusPeriodClassVO);
    }

    /** 대비기간매출분석 - 분류상품별 리스트 상세 조회 */
	@Override
	public List<DefaultMap<String>> getVersusPeriodClassDtlList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO) {

		versusPeriodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(versusPeriodClassVO.getStoreCd()).equals("")) {
        	versusPeriodClassVO.setArrStoreCd(versusPeriodClassVO.getStoreCd().split(","));
        }

		return versusPeriodClassMapper.getVersusPeriodClassDtlList(versusPeriodClassVO);
	}

	/** 대비기간매출분석 - 분류상품별 리스트 상세 차트 조회 */
	@Override
	public List<DefaultMap<String>> getVersusPeriodClassDtlChartList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO) {

		versusPeriodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(versusPeriodClassVO.getStoreCd()).equals("")) {
        	versusPeriodClassVO.setArrStoreCd(versusPeriodClassVO.getStoreCd().split(","));
        }

		return versusPeriodClassMapper.getVersusPeriodClassDtlChartList(versusPeriodClassVO);
	}

	/** 대비기간매출분석 - 브랜드 코드 조회조건 */
	@Override
	public List<DefaultMap<String>> getBrandCdList(VersusPeriodClassVO versusPeriodClassVO, SessionInfoVO sessionInfoVO) {
		versusPeriodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		return versusPeriodClassMapper.getBrandCdList(versusPeriodClassVO);
	}


}
