package kr.co.solbipos.base.prod.vendr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.vendr.service.VendrVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class Name : TouchkeyMapper.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.08  노해민      최초생성
 *
 * @author NHN한국사이버결제 KCP 노해민
 * @since 2018. 08.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface VendrMapper {

    /** 거래처 조회 ( 본사 ) */
    List<DefaultMap<String>> getHqVendrList(VendrVO vendrVO);

    /** 거래처 조회 ( 매장 ) */
    List<DefaultMap<String>> getMsVendrList(VendrVO vendrVO);

    /** 거래처(본사) 상세 조회 */
    DefaultMap<String> getHqDtlInfo(VendrVO vendrVO);
    
    /** 거래처(매장) 상세 조회 */
    DefaultMap<String> getMsDtlInfo(VendrVO vendrVO);
    
    /** 브랜드 코드 조회 */
    String getHqBrandCd(VendrVO vendrVO);
    
    /** 거래처(본사) 등록 */
    int insertHqVendr(VendrVO vendrVO);
    
    /**  거래처(매장) 등록 */
    int insertMsVendr(VendrVO vendrVO);
    
    /** 거래처(본사) 수정 */
    int modifyHqVendr(VendrVO vendrVO);
    
    /**  거래처(매장) 수정 */
    int modifyMsVendr(VendrVO vendrVO);
    
    /** 취급상품 조회 ( 본사 ) */
    List<DefaultMap<String>> getHqVendrProdList(VendrVO vendrVO);

    /** 취급상품 조회 ( 매장 ) */
    List<DefaultMap<String>> getMsVendrProdList(VendrVO vendrVO);
    
    /** 미취급상품 조회 ( 본사 ) */
    List<DefaultMap<String>> getHqProdList(VendrVO vendrVO);

    /** 미취급상품 조회 ( 매장 ) */
    List<DefaultMap<String>> getMsProdList(VendrVO vendrVO);
    
    /** 취급/미취급상품 수정(본사) */
    int mergeHqVendrProd(VendrVO vendrVO);
    
    /** 취급/미취급상품 상태변경(본사) */
    int updateHqVendrProdStatus(VendrVO vendrVO);
    
    /** 취급/미취급상품 수정(매장) */
    int mergeMsVendrProd(VendrVO vendrVO);
    
    /** 취급/미취급상품 상태변경(매장) */
    int updateMsVendrProdStatus(VendrVO vendrVO);
    
}
