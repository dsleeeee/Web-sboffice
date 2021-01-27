package kr.co.solbipos.sale.cmmSalePopup.dcInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.cmmSalePopup.dcInfo.service.DcInfoService;
import kr.co.solbipos.sale.cmmSalePopup.dcInfo.service.DcInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dcInfoService")
public class DcInfoServiceImpl implements DcInfoService {
    private final DcInfoMapper dcInfoMapper;
    private final MessageService messageService;

    public DcInfoServiceImpl(DcInfoMapper dcInfoMapper, MessageService messageService) {
        this.dcInfoMapper = dcInfoMapper;
        this.messageService = messageService;
    }


    /** 매출공통팝업 - 일반할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getGeneralDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dcInfoMapper.getGeneralDcList(dcInfoVO);
    }


    /** 매출공통팝업 - 쿠폰할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCoupnDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dcInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dcInfoVO.getStoreCd()).equals("")) {
            dcInfoVO.setArrStoreCd(dcInfoVO.getStoreCd().split(","));
        }
        return dcInfoMapper.getCoupnDcList(dcInfoVO);
    }


    /** 매출공통팝업 - 회원할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMembrDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dcInfoVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());
        return dcInfoMapper.getMembrDcList(dcInfoVO);
    }


    /** 매출공통팝업 - 제휴할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPartnerDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dcInfoMapper.getPartnerDcList(dcInfoVO);
    }


    /** 매출공통팝업 - 서비스 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getServiceDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dcInfoMapper.getServiceDcList(dcInfoVO);
    }


    /** 매출공통팝업 - 프로모션할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromtnDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dcInfoMapper.getPromtnDcList(dcInfoVO);
    }


    /** 매출공통팝업 - 포장할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPackDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dcInfoMapper.getPackDcList(dcInfoVO);
    }


    /** 매출공통팝업 - 현장할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSiteDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dcInfoMapper.getSiteDcList(dcInfoVO);
    }


    /** 매출공통팝업 - VMEM 쿠폰할인 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVcoupnDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO) {
        dcInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dcInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dcInfoVO.getStoreCd()).equals("")) {
            dcInfoVO.setArrStoreCd(dcInfoVO.getStoreCd().split(","));
        }
        return dcInfoMapper.getVcoupnDcList(dcInfoVO);
    }

}
