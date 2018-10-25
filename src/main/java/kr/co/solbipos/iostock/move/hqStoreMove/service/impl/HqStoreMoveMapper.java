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

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveDtlList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveRegistList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 DTL 수정 */
    int updateHqStoreMoveDtl(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 DTL 등록 */
    int insertHqStoreMoveDtl(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 HD 수정 */
    int updateHqStoreMoveHd(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 HD 등록 */
    int insertHqStoreMoveHd(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 확정 */
    int updateHqStoreMoveConfirm(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 신규전표번호 조회 */
    String getNewSlipNo(HqStoreMoveVO hqStoreMoveVO);
}
