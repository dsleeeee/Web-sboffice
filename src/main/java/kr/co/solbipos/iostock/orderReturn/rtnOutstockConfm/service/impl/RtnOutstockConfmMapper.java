package kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdVO;
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

    /** 반품매장출고 - 반품매장출고시 PROD 수정 */
    int updateOutstockProdConfirm(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고시 HD 수정 */
    int updateOutstockConfirm(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 자동입고 DTL 수정*/
    int updateAutoInstockDtl(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 자동입고 HD 수정*/
    int updateAutoInstock(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - PROD 삭제 */
    int deleteAutoRtnOutstockProdAll(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int mergeAutoRtnOutstockConfmProd(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 입고확정 상세 HD 수정 */
    int updateAutoRtnOutstockConfmHd(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 입고확정상태 확인 PROC_FG > 20 */
    int getAutoRtnOutstockConfirmCnt(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 입고확정시 DTL 수정 */
    int updateRtnOutstockDtlConfirm(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 입고확정시 PROD 수정 */
    int updateRtnOutstockProdConfirm(RtnOutstockConfmVO rtnOutstockConfmVO);

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

    /** 반품등록 PROD 수정 */
    int deleteRtnOutStockProdAll(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품등록 PROD 등록 */
    int mergeRtnOutStockProd(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품등록 PROD 수정 */
    int saveRtnStoreOrderProd(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품등록 PROD 수정 */
    int deleteOrderProd(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품등록 PROD 수정 */
    int deleteOutStockProd(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품등록 PROD 입력 */
    int insertOutStockProd(RtnOutstockConfmVO rtnOutstockConfmVO);
}
