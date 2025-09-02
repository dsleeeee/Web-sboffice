package kr.co.solbipos.base.pay.mCoupnProdMapping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.pay.mCoupnProdMapping.service.MCoupnProdMappingVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MCoupnProdMappingMapper.java
 * @Description : 기초관리 > 결제수단 > 모바일쿠폰상품매핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MCoupnProdMappingMapper {

    /** 모바일쿠폰상품매핑 - 조회 */
    List<DefaultMap<Object>> getMCoupnProdMappingList(MCoupnProdMappingVO mCoupnProdMappingVO);
    List<DefaultMap<Object>> getMCoupnProdMappingList2(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 - 모바일쿠폰사-상품코드 최대수 */
    DefaultMap<Object> getMCoupnProdMappingCnt(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 - 엑셀업로드 양식 조회 */
    List<DefaultMap<Object>> getMCoupnProdMappingExcelUploadSampleList(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 엑셀업로드 팝업 - 검증결과 전체 삭제 */
    int getMCoupnProdMappingExcelUploadCheckDeleteAll(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 엑셀업로드 팝업 - 업로드시 임시테이블 저장 */
    int getMCoupnProdMappingExcelUploadCheckSave(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 조회 */
    List<DefaultMap<Object>> getMCoupnProdMappingExcelUploadResultList(MCoupnProdMappingVO mCoupnProdMappingVO);
    List<DefaultMap<Object>> getMCoupnProdMappingExcelUploadResultList2(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 저장 (백업) insert */
    int getMCoupnProdMappingExcelUploadBackUpSaveInsert(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 저장 delete */
    int getMCoupnProdMappingExcelUploadResultSaveDelete(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 저장 insert */
    int getMCoupnProdMappingExcelUploadResultSaveInsert(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 저장 update */
    int getMCoupnProdMappingExcelUploadResultSaveUpdate(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 이력조회 팝업 - 조회 */
    List<DefaultMap<Object>> getMCoupnProdMappingHistList(MCoupnProdMappingVO mCoupnProdMappingVO);
    List<DefaultMap<Object>> getMCoupnProdMappingHistList2(MCoupnProdMappingVO mCoupnProdMappingVO);

    /** 모바일쿠폰상품매핑 이력조회 팝업 - 모바일쿠폰사-상품코드 최대수 */
    DefaultMap<Object> getMCoupnProdMappingHistCnt(MCoupnProdMappingVO mCoupnProdMappingVO);
}