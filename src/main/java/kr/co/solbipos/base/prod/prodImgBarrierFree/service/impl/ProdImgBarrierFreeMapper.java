package kr.co.solbipos.base.prod.prodImgBarrierFree.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prodImgBarrierFree.service.ProdImgBarrierFreeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdImgBarrierFreeMapper.java
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
@Mapper
@Repository
public interface ProdImgBarrierFreeMapper {

    /** 베리어프리-이미지관리 - 조회 */
    List<DefaultMap<Object>> getProdImgBarrierFreeList(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 - 이미지 조회 */
    List<DefaultMap<Object>> getProdImgBarrierFreeImageList(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 - 기존 이미지 정보가 있는지 확인 */
    String getProdImgBarrierFreeImageChk(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 - 이미지 저장 insert */
    int getProdImgBarrierFreeImageSaveInsert(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 - 이미지 저장 update */
    int getProdImgBarrierFreeImageSaveUpdate(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 - 이미지 삭제 */
    int getProdImgBarrierFreeImageDelete(ProdImgBarrierFreeVO prodImgBarrierFreeVO);


    /** 베리어프리-이미지관리 매장적용 팝업 - 조회 */
    List<DefaultMap<Object>> getProdImgBarrierFreeStoreRegistList(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 매장적용 팝업 - 이미지매장적용 전 기존 매장상품이미지 정보 삭제 */
    int getProdImgBarrierFreeStoreRegistDelete(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 매장적용 팝업 - 이미지 정보 조회 */
    List<DefaultMap<String>> getProdImgBarrierFreeStoreRegistFileNm(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 매장적용 팝업 - 본사상품이미지 매장적용 */
    int getProdImgBarrierFreeStoreRegistSave(ProdImgBarrierFreeVO prodImgBarrierFreeVO);


    /** 베리어프리-이미지관리 이미지복사 팝업 - 이미지 복사 */
    int getProdImgBarrierFreeCopySave(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 이미지복사 팝업 - 이미지 전체 복사 */
    int getProdImgBarrierFreeCopySaveAll(ProdImgBarrierFreeVO prodImgBarrierFreeVO);

    /** 베리어프리-이미지관리 이미지복사 팝업 - 이미지 정보 조회 */
    String getProdImgBarrierFreeCopyFileNm(ProdImgBarrierFreeVO prodImgBarrierFreeVO);


    /** 베리어프리-이미지관리 이미지전체삭제 팝업 - 전체삭제 */
    int getProdImgBarrierFreeDeleteAll(ProdImgBarrierFreeVO prodImgBarrierFreeVO);
}
