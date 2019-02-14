package kr.co.solbipos.sale.cmmSalePopup.payInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.payInfo.service.PayInfoService;
import kr.co.solbipos.sale.cmmSalePopup.payInfo.service.PayInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("payInfoService")
public class PayInfoServiceImpl implements PayInfoService {
    private final PayInfoMapper payInfoMapper;
    private final MessageService messageService;

    public PayInfoServiceImpl(PayInfoMapper payInfoMapper, MessageService messageService) {
        this.payInfoMapper = payInfoMapper;
        this.messageService = messageService;
    }


    /** 매출공통팝업 - 신용카드 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCardList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getCardList(payInfoVO);
    }


    /** 매출공통팝업 - 현금 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCashList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getCashList(payInfoVO);
    }


    /** 매출공통팝업 - PAYCO 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPaycoList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getPaycoList(payInfoVO);
    }

    /** 매출공통팝업 - VMEM 포인트 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVpointList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getVpointList(payInfoVO);
    }


    /** 매출공통팝업 - VMEM 쿠폰 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVcoupnList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getVcoupnList(payInfoVO);
    }


    /** 매출공통팝업 - VMEM 전자상품권 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVchargeList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getVchargeList(payInfoVO);
    }


    /** 매출공통팝업 - 모바일페이 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMpayList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getMpayList(payInfoVO);
    }


    /** 매출공통팝업 - 모바일쿠폰 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMcoupnList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getMcoupnList(payInfoVO);
    }


    /** 매출공통팝업 - 포인트 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPointList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        payInfoVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());
        return payInfoMapper.getPointList(payInfoVO);
    }


    /** 매출공통팝업 - 회원선불 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPrepaidList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        payInfoVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());
        return payInfoMapper.getPrepaidList(payInfoVO);
    }


    /** 매출공통팝업 - 회원후불 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPostpaidList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        payInfoVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());
        return payInfoMapper.getPostpaidList(payInfoVO);
    }


    /** 매출공통팝업 - 상품권 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getGiftList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getGiftList(payInfoVO);
    }


    /** 매출공통팝업 - 식권 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getFstmpList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getFstmpList(payInfoVO);
    }


    /** 매출공통팝업 - 제휴카드 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPartnerList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getPartnerList(payInfoVO);
    }


    /** 매출공통팝업 - 사원카드 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEmpCardList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getEmpCardList(payInfoVO);
    }


    /** 매출공통팝업 - 가승인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTemporaryList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO) {
        payInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return payInfoMapper.getTemporaryList(payInfoVO);
    }










}
