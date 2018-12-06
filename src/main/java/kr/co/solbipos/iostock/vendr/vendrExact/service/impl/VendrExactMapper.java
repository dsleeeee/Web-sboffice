package kr.co.solbipos.iostock.vendr.vendrExact.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.vendr.vendrExact.service.VendrExactVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VendrExactMapper {
    /** 거래처 정산관리 - 거래처별 정산 리스트 조회 */
    List<DefaultMap<String>> getVendrExactList(VendrExactVO vendrExactVO);

    /** 거래처 정산관리 - 거래처 정산 상세 리스트 조회 */
    List<DefaultMap<String>> getVendrExactDtlList(VendrExactVO vendrExactVO);

    /** 거래처 정산관리 - 지급액 상세 조회 */
    DefaultMap<String> getExactInfo(VendrExactVO vendrExactVO);

    /** 거래처 정산관리 - 지급액 등록 */
    int insertVendrExact(VendrExactVO vendrExactVO);

    /** 거래처 정산관리 - 지급액 수정 */
    int updateVendrExact(VendrExactVO vendrExactVO);

    /** 거래처 정산관리 - 지급액 삭제 */
    int deleteVendrExact(VendrExactVO vendrExactVO);

}
