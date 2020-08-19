package kr.co.solbipos.iostock.order.outstockConfm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmProdVO;
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

    /** 출고확정 - 출고확정시 DTL 수정 */
    int updateOutstockDtlConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정시 HD 수정 */
    int updateOutstockConfirm(OutstockConfmVO outstockConfmVO);
    
    /** 출고확정 - 출고확정시 PROD 수정 */
    int insertOutstockProdConfirm(OutstockConfmVO outstockConfmVO);
    
    /** 출고확정 - 출고확정 자동입고 DTL 수정*/
    int updateAutoInstockDtl(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 자동입고 HD 수정*/
    int updateAutoInstock(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(OutstockConfmVO outstockConfmVO);

    /** 출고확정 상세 리스트 조회 */
    List<DefaultMap<String>> getOutstockConfmDtlList(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 상세 DTL 수정 */
    int updateOutstockConfmDtl(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 상세 HD 수정 */
    int updateOutstockConfmHd(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 이후 저장시 HD 수정 */
    int updateOutstockAfterHd(OutstockConfmVO outstockConfmVO);
    
    /** 출고확정 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int mergeInstockConfmProd(OutstockConfmVO outstockConfmVO);
}
