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

    /** 입고확정 - 입고확정시 PROD 수정 */
    int updateInstockProdConfirm(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - PROD 삭제 */
    int deleteRtnInstockProdAll(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int mergeInstockConfmProd(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 입고확정상태 확인 PROC_FG > 20 */
    int getInstockConfirmCnt(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 물량오류 DTL 등록 */
    int insertInstockErrDtl(RtnInstockConfmVO rtnInstockConfmVO);

    /** 입고확정 - 물량오류 HD 등록 */
    int insertInstockErrHd(RtnInstockConfmVO rtnInstockConfmVO);
}
