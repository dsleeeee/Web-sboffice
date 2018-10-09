package kr.co.solbipos.iostock.order.outstockConfm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OutstockConfmMapper {
    /** 출고확정 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(OutstockConfmVO outstockConfmVO);

    /** 출고확정 리스트 조회 */
    List<DefaultMap<String>> getOutstockConfmList(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정시 자동입고 환경변수 조회 */
    String getEnv176(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 DTL 수정 */
    int updateOutstockDtlConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 HD 수정 */
    int updateOutstockConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 자동입고 DTL 수정*/
    int updateAutoInstockDtl(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 자동입고 HD 수정*/
    int updateAutoInstock(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(OutstockConfmVO outstockConfmVO);

}
