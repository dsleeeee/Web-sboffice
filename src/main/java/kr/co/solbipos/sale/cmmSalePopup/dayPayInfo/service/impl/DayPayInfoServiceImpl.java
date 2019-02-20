package kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service.DayPayInfoService;
import kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service.DayPayInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dayPayInfoService")
public class DayPayInfoServiceImpl implements DayPayInfoService {
    private final DayPayInfoMapper dayPayInfoMapper;
    private final MessageService messageService;

    public DayPayInfoServiceImpl(DayPayInfoMapper dayPayInfoMapper, MessageService messageService) {
        this.dayPayInfoMapper = dayPayInfoMapper;
        this.messageService = messageService;
    }


    /** 일자별 매출공통팝업 - 신용카드 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayCardList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayCardList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 현금 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayCashList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayCashList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - PAYCO 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayPaycoList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayPaycoList(dayPayInfoVO);
    }

    /** 일자별 매출공통팝업 - VMEM 포인트 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayVpointList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayVpointList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - VMEM 쿠폰 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayVcoupnList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayVcoupnList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - VMEM 전자상품권 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayVchargeList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayVchargeList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 모바일페이 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayMpayList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayMpayList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 모바일쿠폰 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayMcoupnList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayMcoupnList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 포인트 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayPointList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPayInfoVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayPointList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 회원선불 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayPrepaidList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPayInfoVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayPrepaidList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 회원후불 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayPostpaidList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPayInfoVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayPostpaidList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 상품권 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayGiftList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayGiftList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 식권 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayFstmpList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayFstmpList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 제휴카드 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayPartnerList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayPartnerList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 사원카드 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayEmpCardList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayEmpCardList(dayPayInfoVO);
    }


    /** 일자별 매출공통팝업 - 가승인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayTemporaryList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO) {
        dayPayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayPayInfoVO.getStoreCd()).equals("")) {
            dayPayInfoVO.setArrStoreCd(dayPayInfoVO.getStoreCd().split(","));
        }

        return dayPayInfoMapper.getDayTemporaryList(dayPayInfoVO);
    }
}
