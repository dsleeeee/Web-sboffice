package kr.co.solbipos.iostock.move.standMove.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.move.standMove.service.StandMoveVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataVO;
import kr.co.solbipos.stock.curr.storageHqCurr.service.StorageHqCurrVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StandMoveMapper {
    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    List<DefaultMap<String>> getStandMoveList(StandMoveVO standMoveVO);

    /** 매장이동관리 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getStandMoveDtlList(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    List<DefaultMap<String>> getStandMoveRegistList(StandMoveVO standMoveVO);
    
    /** 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getStandMoveProdList(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 DTL 수정 */
    int updateStandMoveDtl(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 DTL 등록 */
    int insertStandMoveDtl(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 DTL 삭제 */
    int deleteAllStandMoveDtl(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 HD 수정 */
    int updateStandMoveHd(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 HD 등록 */
    int insertStandMoveHd(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 DTL 삭제 */
    int deleteStandMoveDtl(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 확정 */
    int updateStandMoveConfirm(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 HD 수정 */
    int updateStandMoveAddProdHd(StandMoveVO standMoveVO);

    /** 매장이동관리 - 신규전표번호 조회 */
    String getNewSlipNo(StandMoveVO standMoveVO);

    /** 매장이동관리 - 매장이동관리 확정시 출고, 반품 신규전표번호 조회 */
    String getIostockNewSlipNo(StandMoveVO standMoveVO);
    
	/** 창고별현재고현황 - 매장 창고별현재고현황 매장창고 리스트 조회 */
	List<DefaultMap<String>> getStorageList(StandMoveVO standMoveVO);
	
    /** 출고확정 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int mergeStandMoveDtl(StandMoveVO standMoveVO);
    
    /** 출고확정 - [본사_수불] 출고전표_매장출고내역_상품 수정 */
    int mergeStandMoveProd(StandMoveVO standMoveVO);
    
    /** 반품자료생성 - TB_PO_HQ_STORE_OUTSTOCK_PROD 자료입력*/
    int insertRtnStoreOutStockProd(StandMoveVO standMoveVO);
}
