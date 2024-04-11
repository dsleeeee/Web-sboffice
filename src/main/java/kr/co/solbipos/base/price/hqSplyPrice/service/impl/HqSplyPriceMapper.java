package kr.co.solbipos.base.price.hqSplyPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.hqSplyPrice.service.HqSplyPriceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : HqSplyPriceMapper.java
 * @Description : 기초관리 - 가격관리 - 본사공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.04  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface HqSplyPriceMapper {

    /** 본사 공급가관리 조회 */
    List<DefaultMap<String>> getHqSplyPriceList(HqSplyPriceVO hqSplyPriceVO);

    /** 본사 공급가관리 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqSplyPriceExcelList(HqSplyPriceVO hqSplyPriceVO);

    /** 본사 공급가 변경 */
    int saveHqSplyPrice(HqSplyPriceVO hqSplyPriceVO);

    /** 본사 공급가 변경에 따른 매장 공급가 변경 */
    int saveStoreSplyPrice(HqSplyPriceVO hqSplyPriceVO);

    /** 본사 공급가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getHqSplyPriceExcelUploadSampleList(HqSplyPriceVO hqSplyPriceVO);

    /** 공급가 업로드 임시테이블 전체 삭제 */
    int deleteSplyPriceExcelUploadCheckAll(HqSplyPriceVO hqSplyPriceVO);

    /** 공급가 업로드 임시테이블 삭제 */
    int deleteSplyPriceExcelUploadCheck(HqSplyPriceVO hqSplyPriceVO);

    /** 공급가 업로드 임시테이블 저장 */
    int saveSplyPriceExcelUploadCheck(HqSplyPriceVO hqSplyPriceVO);

    /** 공급가 업로드 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(HqSplyPriceVO hqSplyPriceVO);

    /** 본사 상품코드 존재여부 체크 */
    int getProdCdChk(HqSplyPriceVO hqSplyPriceVO);
}
