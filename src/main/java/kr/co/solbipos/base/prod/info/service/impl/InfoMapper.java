package kr.co.solbipos.base.prod.info.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : InfoMapper.java
 * @Description : 기초관리 > 상품관리 > 분류코드등록(상품기초정보등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김지은      최초생성
 * @ 2019.08.12  이다솜      상품분류관리 본사 > 매장으로 데이터 등록/수정/삭제
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.03
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface InfoMapper {

    /** 본사 분류 조회 */
    List<DefaultMap<String>> getHqProdClsList(ProductClassVO prodClsVO);

    /** 매장 분류 조회 */
    List<DefaultMap<String>> getStoreProdClsList(ProductClassVO prodClsVO);

    /** 신규 분류 코드 조회 */
    String getClsCd(ProductClassVO productClassVO);

    /** 상위분류 코드 조회 */
    String getPProdClsCd(ProductClassVO productClassVO);

    /** 상위분류 코드 조회 - 엑셀업로드 - 기초마스터등록 시 사용 */
    String getPProdClsCd2(ProductClassVO productClassVO);

    /** 분류 등록 */
    int insertCls(ProductClassVO productClassVO);

    /** 본사 분류 수정 */
    int updateHqCls(ProductClassVO productClassVO);

    /** 본사 분류 수정 */
    int updateStoreCls(ProductClassVO productClassVO);

    /** 해당 분류로 등록된 상품 조회 */
    int chkProdCnt(ProductClassVO productClassVO);

    /** 분류 삭제 */
    int deleteCls(ProductClassVO productClassVO);

    /** 분류 생성시 매장적용 */
    String insertClsToStore(ProductClassVO productClassVO);

    /** 분류 수정시 매장적용 */
    String updateClsToStore(ProductClassVO productClassVO);

    /** 분류 삭제시 매장적용 */
    String deleteClsToStore(ProductClassVO productClassVO);

    /** 해당 분류로 등록된 상품 조회(매장전체조회) */
    int chkProdCntinStore(ProductClassVO productClassVO);

    /** 저장하려는 분류의 Level값 조회 */
    String getProdClsLevel(ProductClassVO productClassVO);

    /** 상품분류정보관리(3단계) - 분류 조회 */
    List<DefaultMap<String>> getProdClass(ProductClassVO productClassVO);

    /** 상품분류코드 채번방식 조회 */
    String getProdClassCdInputType(ProductClassVO productClassVO);

    /** 상품분류코드 중복체크 */
    List<DefaultMap<Object>> getChkProdClassCd(ProductClassVO productClassVO);
}
