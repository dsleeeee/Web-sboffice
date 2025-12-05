package kr.co.solbipos.base.prod.qrOrderKeyMap.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : QrOrderKeyMapService.java
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
public interface QrOrderKeyMapService {

    /** QR오더 카테고리 (분류) 조회 */
    List<DefaultMap<Object>> getQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO);

    /** QR오더 - QR오더 카테고리(분류) 저장 */
    int saveQrOrderCategory(QrOrderKeyMapVO[] qrOrderKeyMapVOS, SessionInfoVO sessionInfoVO);

    /** QR오더 키맵 조회 */
    List<DefaultMap<Object>> getQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO);

    /** QR오더 - QR오더 키맵 수정 */
    int updateQrOrderKeyMap(QrOrderKeyMapVO[] qrOrderKeyMapVOS, SessionInfoVO sessionInfoVO);

    /** QR오더 상품 조회 */
    List<DefaultMap<String>> getQrOrderProdList(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO);

    /** QR오더 - QR오더 키맵 수정 */
    int saveQrOrderKeyMap(QrOrderKeyMapVO[] qrOrderKeyMapVOS, SessionInfoVO sessionInfoVO);

    /** 개발/운영 URL 조회 */
    List<DefaultMap<Object>> getApiEnvNm(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO);
}
