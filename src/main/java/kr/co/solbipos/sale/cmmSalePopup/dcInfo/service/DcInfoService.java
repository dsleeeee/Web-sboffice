package kr.co.solbipos.sale.cmmSalePopup.dcInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DcInfoService {
    /** 매출공통팝업 - 일반할인 상세 리스트 조회 */
    List<DefaultMap<String>> getGeneralDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 쿠폰할인 상세 리스트 조회 */
    List<DefaultMap<String>> getCoupnDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 회원할인 상세 리스트 조회 */
    List<DefaultMap<String>> getMembrDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 제휴할인 상세 리스트 조회 */
    List<DefaultMap<String>> getPartnerDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 서비스할인 상세 리스트 조회 */
    List<DefaultMap<String>> getServiceDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 프로모션할인 상세 리스트 조회 */
    List<DefaultMap<String>> getPromtnDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 포장할인 상세 리스트 조회 */
    List<DefaultMap<String>> getPackDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 현장할인 상세 리스트 조회 */
    List<DefaultMap<String>> getSiteDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - VMEM 쿠폰할인 상세 리스트 조회 */
    List<DefaultMap<String>> getVcoupnDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

}
