package kr.co.solbipos.sale.cmmSalePopup.dcInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.cmmSalePopup.dcInfo.service.DcInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DcInfoMapper {
    /** 매출공통팝업 - 일반할인 상세 리스트 조회 */
    List<DefaultMap<String>> getGeneralDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - 쿠폰할인 상세 리스트 조회 */
    List<DefaultMap<String>> getCoupnDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - 회원할인 상세 리스트 조회 */
    List<DefaultMap<String>> getMembrDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - 제휴할인 상세 리스트 조회 */
    List<DefaultMap<String>> getPartnerDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - 서비스할인 상세 리스트 조회 */
    List<DefaultMap<String>> getServiceDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - 프로모션할인 상세 리스트 조회 */
    List<DefaultMap<String>> getPromtnDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - 포장할인 상세 리스트 조회 */
    List<DefaultMap<String>> getPackDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - 현장할인 상세 리스트 조회 */
    List<DefaultMap<String>> getSiteDcList(DcInfoVO dcInfoVO);

    /** 매출공통팝업 - VMEM 쿠폰할인 상세 리스트 조회 */
    List<DefaultMap<String>> getVcoupnDcList(DcInfoVO dcInfoVO);

}
