package kr.co.solbipos.iostock.order.outstockReqDate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OutstockReqDateMapper {
    /** 출고요청일관리 요일별 리스트 조회 */
    List<DefaultMap<String>> getDaysList(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 요일별 요일 수정 */
    int updateReqDateDays(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 요일별 요일 등록 */
    int insertReqDateDays(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 특정일 리스트 조회 */
    List<DefaultMap<String>> getSpecificDateList(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 특정일 신규 등록 */
    int insertSpecificDate(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 특정일 수정 */
    int updateSpecificDate(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 특정일 삭제 */
    int deleteSpecificDate(OutstockReqDateVO outstockReqDateVO);

    /** 복사할 매장의 이전 출고요청일관리 특정일 삭제 */
    int deleteCopySpecificDate(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 특정일 복사 */
    int insertCopySpecificDate(OutstockReqDateVO outstockReqDateVO);

    /** 복사할 매장의 이전 출고요청일관리 요일 삭제 */
    int deleteAllCopyDays(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 요일 복사 */
    int insertCopyDays(OutstockReqDateVO outstockReqDateVO);

}
