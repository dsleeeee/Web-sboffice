package kr.co.solbipos.sale.status.daySaleReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.daySaleReport.service.DaySaleReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DaySaleReportMapper.java
 * @Description : 매출관리 > 매출현황2 > 일별매출내역 다운로드(제너시스올떡 분식대장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DaySaleReportMapper {

    /** 일별매출내역 다운로드 - 조회 */
    List<DefaultMap<Object>> getDaySaleReportList(DaySaleReportVO daySaleReportVO);

    /** 일별매출내역 다운로드 - 자료생성 저장 insert */
    int getDaySaleReportSaveInsert(DaySaleReportVO daySaleReportVO);

    /** 일별매출내역 다운로드 - 삭제 */
    int getDaySaleReportDel(DaySaleReportVO daySaleReportVO);

    /** 일별매출내역 다운로드 - 자료생성 요청건 존재여부 확인 */
    DefaultMap<String> getDaySaleReportChk(DaySaleReportVO daySaleReportVO);
}