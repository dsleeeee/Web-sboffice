package kr.co.solbipos.iostock.move.hqMove.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.move.hqMove.service.HqMoveVO;
import kr.co.solbipos.iostock.move.hqStoreMove.service.HqStoreMoveVO;

@Mapper
@Repository
public interface HqMoveMapper {
    /** 본사이동관리 - 본사이동관리 리스트 조회 */
    List<DefaultMap<String>> getHqMoveList(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getHqMoveDtlList(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 신규등록, 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getHqMoveRegistList(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 상세 리스트 DTL 수정 */
    int updateHqMoveDtl(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 신규등록 DTL 등록 */
    int insertHqMoveDtl(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 상세 DTL 삭제 */
    int deleteAllHqMoveDtl(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 상세 리스트 HD 수정 */
    int updateHqMoveHd(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 신규등록 HD 등록 */
    int insertHqMoveHd(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 상세 HD 삭제 */
    int deleteHqMoveHd(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 확정 */
    int updateHqMoveConfirm(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 상품추가 리스트 HD 수정 */
    int updateHqMoveAddProdHd(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 신규전표번호 조회 */
    String getNewSlipNo(HqMoveVO hqMoveVO);

    /** 본사이동관리 - 본사이동관리 확정시 출고, 반품 신규전표번호 조회 */
    String getIostockNewSlipNo(HqMoveVO hqMoveVO);
    
    /** 본사이동관리 - 본사이동관리 확정 신규테이블 등록 */
    int insertHqStoreOutstockProd(HqMoveVO hqMoveVO);
}
