package kr.co.solbipos.base.prod.prodBarcd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prodBarcd.service.ProdBarcdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BarcdMapper.java
 * @Description : 기초관리 - 상품관리 - 상품바코드등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.01   권지현
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdBarcdMapper {

    /**
     * 상품 조회
     * @param prodBarcdVO
     * @return List
     */
    List<DefaultMap<String>> getProdList(ProdBarcdVO prodBarcdVO);

    /**
     * 상품 조회(엑셀다운로드용)
     * @param prodBarcdVO
     * @return List
     */
    List<DefaultMap<String>> getProdExcelList(ProdBarcdVO prodBarcdVO);

    /**
     * 상품 상세 조회
     * @param prodBarcdVO
     * @return DefaultMap
     */
    DefaultMap<String> getProdDetail(ProdBarcdVO prodBarcdVO);

    /** 바코드 중복체크*/
    List<DefaultMap<String>> chkBarCd(ProdBarcdVO prodBarcdVO);

    /** 바코드 중복체크*/
    List<DefaultMap<String>> chkBarCds(ProdBarcdVO prodBarcdVO);

    /** 바코드 중복체크(본사)*/
    List<DefaultMap<String>> chkBarCdsHq(ProdBarcdVO prodBarcdVO);

    /**
     * 바코드 삭제(프차본사에서 삭제시 하위매장에것도 삭제)
     * @param prodBarcdVO
     * @return List
     */
    int deleteProdBarcdStoreHq(ProdBarcdVO prodBarcdVO);
    /**
     * 연결상품 조회
     * @param prodBarcdVO
     * @return List
     */
    List<DefaultMap<String>> getLinkedProdList(ProdBarcdVO prodBarcdVO);

    /**
     * 바코드 저장
     * @param prodBarcdVO
     * @return List
     */
    int saveBarcd(ProdBarcdVO prodBarcdVO);

    /**
     * 바코드 저장(프차본사에서 삭제시 하위매장에것도 삭제)
     * @param prodBarcdVO
     * @return List
     */
    int saveProdBarcdStore(ProdBarcdVO prodBarcdVO);
    
    /**
     * 바코드 삭제
     * @param prodBarcdVO
     * @return List
     */
    int deleteBarcd(ProdBarcdVO prodBarcdVO);

    /**
     * 바코드 삭제(프차본사에서 삭제시 하위매장에것도 삭제)
     * @param prodBarcdVO
     * @return List
     */
    int deleteProdBarcdStore(ProdBarcdVO prodBarcdVO);

    /**
     * 검증내역삭제
     * @param prodBarcdVO
     * @return List
     */
    int getExcelUploadCheckDeleteAll(ProdBarcdVO prodBarcdVO);

    /**
     * 검증내역저장
     * @param prodBarcdVO
     * @return List
     */
    int getExcelUploadCheckSave(ProdBarcdVO prodBarcdVO);

    /** 임시테이블 정보 조회 */
    List<DefaultMap<String>>getExcelList(ProdBarcdVO prodBarcdVO);

    /** 바코드 엑셀등록 시 검증*/
    List<DefaultMap<String>> chkExcelUpload(ProdBarcdVO prodBarcdVO);

    /**
     * 바코드 저장
     * @param prodBarcdVO
     * @return List
     */
    int saveBarcdExcel(ProdBarcdVO prodBarcdVO);

}