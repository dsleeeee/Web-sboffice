package kr.co.solbipos.store.storeMoms.dataRcvStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.dataRcvStatus.service.DataRcvStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DataRcvStatusMapper.java
 * @Description : 맘스터치 > 매장관리 > 자료수신현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface DataRcvStatusMapper {

    /** 자료수신현황 헤더 조회 */
    List<DefaultMap<String>> getDataRcvStatusHdrList(DataRcvStatusVO dataRcvStatusVO);

    /** 자료수신현황 상세 조회 */
    List<DefaultMap<String>> getDataRcvStatusDtlList(DataRcvStatusVO dataRcvStatusVO);
}
