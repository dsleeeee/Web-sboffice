package kr.co.solbipos.sale.today.todayGnrlz.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.today.todayGnrlz.service.TodayGnrlzService;
import kr.co.solbipos.sale.today.todayGnrlz.service.TodayGnrlzVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("todayGnrlzService")
public class TodayGnrlzServiceImpl implements TodayGnrlzService {
    private final TodayGnrlzMapper todayGnrlzMapper;
    private final MessageService messageService;

    @Autowired
    public TodayGnrlzServiceImpl(TodayGnrlzMapper todayGnrlzMapper, MessageService messageService) {
        this.todayGnrlzMapper = todayGnrlzMapper;
        this.messageService = messageService;
    }


    /** 당일매출종합현황 - 매출종합 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTodayGnrlzList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO) {
        todayGnrlzVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todayGnrlzVO.getStoreCd()).equals("")) {
            todayGnrlzVO.setArrStoreCd(todayGnrlzVO.getStoreCd().split(","));
        }
        return todayGnrlzMapper.getTodayGnrlzList(todayGnrlzVO);
    }


    /** 당일매출종합현황 - 결제수단별 매출 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTodayGnrlzPayList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO) {
        todayGnrlzVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todayGnrlzVO.getStoreCd()).equals("")) {
            todayGnrlzVO.setArrStoreCd(todayGnrlzVO.getStoreCd().split(","));
        }
        return todayGnrlzMapper.getTodayGnrlzPayList(todayGnrlzVO);
    }


    /** 당일매출종합현황 - 회원 Point 적립/사용 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTodayGnrlzMemberList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO) {
        todayGnrlzVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        todayGnrlzVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        if(!StringUtil.getOrBlank(todayGnrlzVO.getStoreCd()).equals("")) {
            todayGnrlzVO.setArrStoreCd(todayGnrlzVO.getStoreCd().split(","));
        }
        return todayGnrlzMapper.getTodayGnrlzMemberList(todayGnrlzVO);
    }


    /** 당일매출종합현황 - 상품별 매출현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTodayGnrlzProdList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO) {
        todayGnrlzVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todayGnrlzVO.getStoreCd()).equals("")) {
            todayGnrlzVO.setArrStoreCd(todayGnrlzVO.getStoreCd().split(","));
        }
        return todayGnrlzMapper.getTodayGnrlzProdList(todayGnrlzVO);
    }

}
