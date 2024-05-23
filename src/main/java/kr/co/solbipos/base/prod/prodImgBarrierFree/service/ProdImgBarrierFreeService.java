package kr.co.solbipos.base.prod.prodImgBarrierFree.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @Class Name : ProdImgBarrierFreeService.java
 * @Description : 기초관리 > 상품관리2 > 베리어프리-이미지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.05.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdImgBarrierFreeService {

    /** 베리어프리-이미지관리 - 조회 */
    List<DefaultMap<Object>> getProdImgBarrierFreeList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO);

    /** 베리어프리-이미지관리 - 이미지 조회 */
    List<DefaultMap<Object>> getProdImgBarrierFreeImageList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO);

    /** 베리어프리-이미지관리 - 이미지 저장 */
    String getProdImgBarrierFreeImageSave(MultipartHttpServletRequest multi, ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO);

    /** 베리어프리-이미지관리 - 이미지 삭제 */
    boolean getProdImgBarrierFreeImageDelete(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO);

    /** 베리어프리-이미지관리 매장적용 팝업 - 조회 */
    List<DefaultMap<Object>> getProdImgBarrierFreeStoreRegistList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO);

    /** 베리어프리-이미지관리 매장적용 팝업 - 저장 */
    int getProdImgBarrierFreeStoreRegistSave(ProdImgBarrierFreeVO[] prodImgBarrierFreeVOs, SessionInfoVO sessionInfoVO);

    /** 베리어프리-이미지관리 이미지복사 팝업 - 저장 */
    int getProdImgBarrierFreeCopySave(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO);

    /** 베리어프리-이미지관리 이미지전체삭제 팝업 - 전체삭제 */
    int getProdImgBarrierFreeDeleteAll(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO);
}