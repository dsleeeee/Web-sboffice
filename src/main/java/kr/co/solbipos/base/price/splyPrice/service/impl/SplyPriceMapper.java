package kr.co.solbipos.base.price.splyPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.splyPrice.service.SplyPriceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SplyPriceMapper.java
 * @Description : 기초관리 - 가격관리 - 공급가관리
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
public interface SplyPriceMapper {

    /** 본사 공급가관리 조회 */
    List<DefaultMap<String>> getHqSplyPriceList(SplyPriceVO splyPriceVO);

    /** 본사 공급가관리 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqSplyPriceExcelList(SplyPriceVO splyPriceVO);

    /** 본사 공급가 변경 */
    int saveHqSplyPrice(SplyPriceVO splyPriceVO);

    /** 본사 공급가 변경에 따른 매장 공급가 변경 */
    int saveStoreSplyPrice(SplyPriceVO splyPriceVO);

    /** 본사 공급가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getHqSplyPriceExcelUploadSampleList(SplyPriceVO splyPriceVO);

    /** 공급가 업로드 임시테이블 전체 삭제 */
    int deleteSplyPriceExcelUploadCheckAll(SplyPriceVO splyPriceVO);

    /** 공급가 업로드 임시테이블 삭제 */
    int deleteSplyPriceExcelUploadCheck(SplyPriceVO splyPriceVO);

    /** 공급가 업로드 임시테이블 저장 */
    int saveSplyPriceExcelUploadCheck(SplyPriceVO splyPriceVO);

    /** 공급가 업로드 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(SplyPriceVO splyPriceVO);

    /** 본사 상품코드 존재여부 체크 */
    int getProdCdChk(SplyPriceVO splyPriceVO);
}
