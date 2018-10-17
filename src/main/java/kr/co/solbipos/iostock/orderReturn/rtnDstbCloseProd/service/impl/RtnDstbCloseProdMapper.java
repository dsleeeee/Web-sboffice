package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnDstbCloseProdMapper {
    /** 반품마감 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 리스트 확정 */
    int updateRtnDstbCloseProdConfirm(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdDtlList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 상세 리스트 수정 */
    int updateRtnDstbCloseProdDtl(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 상세 리스트 확정 */
    int updateRtnDstbCloseProdDtlConfirm(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 상세 리스트 삭제 */
    int deleteRtnDstbCloseProdDtl(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 추가등록 상품 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdAddProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 추가등록 분배등록 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdAddRegistList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 반품마감 추가등록 분배등록 리스트 등록 */
    int insertRtnDstbCloseProdAddRegist(RtnDstbCloseProdVO rtnDstbCloseProdVO);
}
