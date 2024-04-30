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

    /** 출고확정 - 출고확정전 상태 확인 */
    int getOutstockConfirmCnt(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정시 DTL 수정 */
    int updateOutstockDtlConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정시 HD 수정 */
    int updateOutstockConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정전 PROD 수정 확정하지 않는 데이터 삭제*/
    int deleteOutstockProdConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정시 PROD 수정 */
    int insertOutstockProdConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정시 PROD 수정 */
    int mergeOutstockProdConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정시 PROD 수정 CONFIRM_YN만 처리 */
    int updateOutstockProdConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 자동입고 DTL 수정*/
    int updateAutoInstockDtl(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 자동입고 DTL 수정*/
    int updateAutoInstockDtlConfirm(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - PROD 입력전에 이전값 삭제*/
    int deleteAutoInstockProdAll(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int mergeAutoInstockConfmProd(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 상세 HD 수정 */
    int updateAutoInstockConfmHd(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 상태 확인 PROC_FG > 20 */
    int getAutoInstockConfirmCnt(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int updateAutoInstockProdConfirm(OutstockConfmVO outstockConfmVO);



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

    /** 출고확정 - 출고 처리전 prod삭제 */
    int deleteOutstockProdAll(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int mergeInstockConfmProd(OutstockConfmVO outstockConfmVO);

    /** 출고창고 콤보조회(권한에 상관없이 본사창고 또는 매장창고 조회) */
    List<DefaultMap<String>> getOutStorageCombo2(OutstockConfmVO outstockConfmVO);
}
