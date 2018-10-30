package kr.co.solbipos.iostock.move.hqStoreMove.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.move.hqStoreMove.service.HqStoreMoveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HqStoreMoveMapper {
    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveDtlList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록, 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveRegistList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 DTL 수정 */
    int updateHqStoreMoveDtl(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 DTL 등록 */
    int insertHqStoreMoveDtl(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 DTL 삭제 */
    int deleteAllHqStoreMoveDtl(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 HD 수정 */
    int updateHqStoreMoveHd(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 HD 등록 */
    int insertHqStoreMoveHd(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 HD 삭제 */
    int deleteHqStoreMoveHd(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 확정 */
    int updateHqStoreMoveConfirm(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 HD 수정 */
    int updateHqStoreMoveAddProdHd(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 신규전표번호 조회 */
    String getNewSlipNo(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 확정시 출고, 반품 신규전표번호 조회 */
    String getIostockNewSlipNo(HqStoreMoveVO hqStoreMoveVO);

}
