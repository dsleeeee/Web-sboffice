package kr.co.solbipos.base.prod.prodImg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @Class Name : ProdImgService.java
 * @Description : 기초관리 - 상품관리 - 상품이미지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface ProdImgService {

    /** 상품이미지관리 - 상품목록조회 */
    List<DefaultMap<String>> getProdList(ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO);

    /** 상품이미지관리 - 상품이미지조회 */
    List<DefaultMap<String>> getProdImg(ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO);

    /** 상품이미지관리 - 상품이미지저장 */
    boolean saveProdImg(MultipartHttpServletRequest multi, ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO);

    /** 상품이미지관리 - 상품이미지삭제 */
    boolean delProdImg(ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO);
}
