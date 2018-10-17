package kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.RtnOutstockConfmVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnOutstockConfmMapper {
    /** 반품매장출고 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockConfmList(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고시 DTL 수정 */
    int updateOutstockDtlConfirm(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고시 HD 수정 */
    int updateOutstockConfirm(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 자동입고 DTL 수정*/
    int updateAutoInstockDtl(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 자동입고 HD 수정*/
    int updateAutoInstock(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockConfmDtlList(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 상세 DTL 수정 */
    int updateRtnOutstockConfmDtl(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 상세 HD 수정 */
    int updateRtnOutstockConfmHd(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 이후 저장시 HD 수정 */
    int updateOutstockAfterHd(RtnOutstockConfmVO rtnOutstockConfmVO);
}
