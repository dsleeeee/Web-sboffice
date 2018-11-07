package kr.co.solbipos.stock.acins.hqAcins.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.acins.hqAcins.service.HqAcinsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HqAcinsMapper {
    /** 실사관리 - 실사관리 리스트 조회 */
    List<DefaultMap<String>> getHqAcinsList(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 DTL 전부 삭제 */
    int deleteAllHqAcinsDtl(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 HD 삭제 */
    int deleteHqAcinsHd(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAcinsRegistList(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 신규 SEQ 조회 */
    String getNewSeqNo(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 DTL 등록 */
    int insertHqAcinsDtl(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 DTL 수정 */
    int updateHqAcinsDtl(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 DTL 삭제 */
    int deleteHqAcinsDtl(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 HD 등록 */
    int insertHqAcinsHd(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 HD 수정 */
    int updateHqAcinsHd(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사상세 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAcinsDtlList(HqAcinsVO hqAcinsVO);


}
