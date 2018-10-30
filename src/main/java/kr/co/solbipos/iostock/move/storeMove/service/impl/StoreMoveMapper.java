package kr.co.solbipos.iostock.move.storeMove.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.move.storeMove.service.StoreMoveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreMoveMapper {
    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    List<DefaultMap<String>> getStoreMoveList(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getStoreMoveDtlList(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록, 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getStoreMoveRegistList(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 DTL 수정 */
    int updateStoreMoveDtl(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 DTL 등록 */
    int insertStoreMoveDtl(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 DTL 삭제 */
    int deleteAllStoreMoveDtl(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 HD 수정 */
    int updateStoreMoveHd(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 HD 등록 */
    int insertStoreMoveHd(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 HD 삭제 */
    int deleteStoreMoveHd(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 확정 */
    int updateStoreMoveConfirm(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 HD 수정 */
    int updateStoreMoveAddProdHd(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 신규전표번호 조회 */
    String getNewSlipNo(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 확정시 출고, 반품 신규전표번호 조회 */
    String getIostockNewSlipNo(StoreMoveVO storeMoveVO);
}
