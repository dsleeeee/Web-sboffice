package kr.co.solbipos.iostock.order.instockConfm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstockConfmMapper {
    /** 입고확정 리스트 조회 */
    List<DefaultMap<String>> getInstockConfmList(InstockConfmVO instockConfmVO);

    /** 입고확정 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(InstockConfmVO instockConfmVO);

    /** 입고확정 상세 리스트 조회 */
    List<DefaultMap<String>> getInstockConfmDtlList(InstockConfmVO instockConfmVO);

    /** 입고확정 - 입고확정 상세 DTL 수정 */
    int updateInstockConfmDtl(InstockConfmVO instockConfmVO);

    /** 입고확정 - 입고확정 상세 HD 수정 */
    int updateInstockConfmHd(InstockConfmVO instockConfmVO);

    /** 입고확정 - 입고확정시 DTL 수정 */
    int updateInstockDtlConfirm(InstockConfmVO instockConfmVO);

    /** 입고확정 - 입고확정시 HD 수정 */
    int updateInstockConfirm(InstockConfmVO instockConfmVO);

    /** 입고확정 - 물량오류 DTL 등록 */
    int insertInstockErrDtl(InstockConfmVO instockConfmVO);

    /** 입고확정 - 물량오류 HD 등록 */
    int insertInstockErrHd(InstockConfmVO instockConfmVO);

}
