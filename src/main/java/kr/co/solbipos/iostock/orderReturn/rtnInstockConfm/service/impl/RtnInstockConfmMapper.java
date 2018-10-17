package kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.RtnInstockConfmVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnInstockConfmMapper {
    /** 입고확정 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmList(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmDtlList(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 입고확정 상세 DTL 수정 */
    int updateRtnInstockConfmDtl(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 입고확정 상세 HD 수정 */
    int updateRtnInstockConfmHd(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 입고확정시 DTL 수정 */
    int updateInstockDtlConfirm(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 입고확정시 HD 수정 */
    int updateInstockConfirm(RtnInstockConfmVO rtnInstockConfmVO);
}
