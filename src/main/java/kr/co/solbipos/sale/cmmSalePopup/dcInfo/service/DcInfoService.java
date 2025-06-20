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

    /** 매출공통팝업 - 스마트오더 할인 상세 리스트 조회 */
    List<DefaultMap<String>> getSmartorderDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 쿠폰할인(BBQ용) 상세 리스트 조회 */
    List<DefaultMap<String>> getCoupnBbqDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 땡겨요정산할인 상세 리스트 조회 */
    List<DefaultMap<String>> getDdangyoBbqDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 쿠폰할인 명칭 조회 */
    DefaultMap<String> getCoupnDcNm(DcInfoVO dcInfoVO, SessionInfoVO sessionInfo);

    /** 매출공통팝업 - 미스터피자 제휴할인 상세 리스트 조회 */
    List<DefaultMap<String>> getPartnerMrpizzaDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 미스터피자 카드사할인 상세 리스트 조회 */
    List<DefaultMap<String>> getCarddcDcList(DcInfoVO dcInfoVO, SessionInfoVO sessionInfoVO);
}