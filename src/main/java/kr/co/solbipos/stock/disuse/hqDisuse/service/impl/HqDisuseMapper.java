package kr.co.solbipos.stock.disuse.hqDisuse.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.disuse.hqDisuse.service.HqDisuseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HqDisuseMapper {
    /** 폐기관리 - 폐기관리 리스트 조회 */
    List<DefaultMap<String>> getHqDisuseList(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 DTL 전부 삭제 */
    int deleteAllHqDisuseDtl(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 HD 삭제 */
    int deleteHqDisuseHd(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqDisuseRegistList(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 신규 SEQ 조회 */
    String getNewSeqNo(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 DTL 등록 */
    int insertHqDisuseDtl(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 DTL 수정 */
    int updateHqDisuseDtl(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 DTL 삭제 */
    int deleteHqDisuseDtl(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 HD 등록 */
    int insertHqDisuseHd(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 HD 수정 */
    int updateHqDisuseHd(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기상세 상품 리스트 조회 */
    List<DefaultMap<String>> getHqDisuseDtlList(HqDisuseVO hqDisuseVO);
}
