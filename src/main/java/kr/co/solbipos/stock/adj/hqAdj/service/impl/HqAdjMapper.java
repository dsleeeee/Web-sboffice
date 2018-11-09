package kr.co.solbipos.stock.adj.hqAdj.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.adj.hqAdj.service.HqAdjVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HqAdjMapper {
    /** 조정관리 - 조정관리 리스트 조회 */
    List<DefaultMap<String>> getHqAdjList(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 DTL 전부 삭제 */
    int deleteAllHqAdjDtl(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 HD 삭제 */
    int deleteHqAdjHd(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAdjRegistList(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 신규 SEQ 조회 */
    String getNewSeqNo(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 DTL 등록 */
    int insertHqAdjDtl(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 DTL 수정 */
    int updateHqAdjDtl(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 DTL 삭제 */
    int deleteHqAdjDtl(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 HD 등록 */
    int insertHqAdjHd(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 HD 수정 */
    int updateHqAdjHd(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정상세 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAdjDtlList(HqAdjVO hqAdjVO);
}
