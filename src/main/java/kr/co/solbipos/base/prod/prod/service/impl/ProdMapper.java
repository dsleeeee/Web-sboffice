package kr.co.solbipos.base.prod.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdMapper.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       상품조회 관련 변경
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ProdMapper {

    /**
     * 상품 조회
     * @param prodVO
     * @return List
     */
    List<DefaultMap<String>> getProdList(ProdVO prodVO);

    /**
     * 상품 상세 조회
     * @param prodVO
     * @return DefaultMap
     */
    DefaultMap<String> getProdDetail(ProdVO prodVO);

    /**
     * 연결상품 조회
     * @param prodVO
     * @return List
     */
    List<DefaultMap<String>> getLinkedProdList(ProdVO prodVO);

    /** 상품정보 저장 */
    int saveProductInfo(ProdVO prodVO);

    /** 본사에서는 프로시저 호출에 사용할 상품의 존재여부를 미리 체크함 */
    int getProdExistInfo(ProdVO prodVO);

    /** 본사 상품정보 등록시 매장 상품정보에 등록 */
    String insertHqProdToStoreProd(ProdVO prodVO);

    /** 본사 상품정보 수정시 매장 상품정보 수정 */
    String updateHqProdToStoreProd(ProdVO prodVO);

    /** 상품 적용/미적용 매장 조회 */
    List<DefaultMap<String>> getStoreList(ProdVO prodVO);

    /** 상품 적용 매장 등록 */
    int insertProdStore(ProdVO prodVO);

    /** 상품 적용 매장 삭제 */
    int deleteProdStore(ProdVO prodVO);
}
