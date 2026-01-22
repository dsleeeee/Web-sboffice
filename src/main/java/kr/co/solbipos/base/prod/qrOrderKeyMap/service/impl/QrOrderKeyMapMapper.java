package kr.co.solbipos.base.prod.qrOrderKeyMap.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.qrOrderKeyMap.service.QrOrderKeyMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : QrOrderKeyMapMapper.java
 * @Description : 기초관리 > 상품관리2 > QR주문키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.28  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.28
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface QrOrderKeyMapMapper {

    /** QR오더 카테고리 (분류) 조회 */
    List<DefaultMap<Object>> getQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 카테고리(분류) 분류코드 생성 */
    String getQrOrderCategoryCode(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 카테고리(분류) 저장 */
    int insertQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 카테고리(분류) 수정 */
    int updateQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 카테고리(분류) 삭제 */
    int deleteQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 카테고리(분류) 분류 해당하는 상품 삭제 */
    int deleteAllQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 키맵 조회 */
    List<DefaultMap<Object>> getQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 키맵 수정 */
    int updateQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 키맵 삭제 */
    int deleteQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 상품 조회 */
    List<DefaultMap<String>> getQrOrderProdList(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 키 관련 코드 조회 */
    DefaultMap<String> getQrOrderKeyMapCode(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** QR오더 - QR오더 키맵 수정 */
    int saveQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** 개발/운영 URL 조회 */
    List<DefaultMap<Object>> getApiEnvNm(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** 상품 내점,배달,포장 여부 수정 */
    int updateProductSaleType(QrOrderKeyMapVO qrOrderKeyMapVO);

    /** API 호출 결과 저장 */
    int saveApiLog(QrOrderKeyMapVO qrOrderKeyMapVO);
}
