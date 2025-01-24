package kr.co.solbipos.base.prod.recpOrigin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : RecpOriginMapper.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface RecpOriginMapper {

   /** 원산지관리 조회 */
   List<DefaultMap<Object>> getRecpOriginList(RecpOriginVO recpOriginVO);

   /** 브랜드 콤보박스 리스트 조회 */
   List<DefaultMap<Object>> getBrandComboList(RecpOriginVO recpOriginVO);

   /** 재료코드(자동채번) */
   String getRecpOriginRecipesCd(RecpOriginVO recpOriginVO);

   /** 원산지관리 저장 insert */
   int getRecpOriginSaveInsert(RecpOriginVO recpOriginVO);

   /** 원산지관리 저장 update */
   int getRecpOriginSaveUpdate(RecpOriginVO recpOriginVO);

   /** 원산지관리 저장 delete */
   int getRecpOriginSaveDelete(RecpOriginVO recpOriginVO);

   /** 원산지관리 저장 delete 시, 재료-상품 저장 delete */
   int getRecpProdSaveDeleteAll(RecpOriginVO recpOriginVO);

   /** 재료-상품 조회 */
   List<DefaultMap<Object>> getRecpOriginDetailList(RecpOriginVO recpOriginVO);

   /** 재료-상품 등록 팝업 - 상품조회 */
   List<DefaultMap<Object>> getRecpProdList(RecpOriginVO recpOriginVO);

   /** 재료-상품 등록 팝업 - 재료-상품 저장 insert */
   int getRecpProdSaveInsert(RecpOriginVO recpOriginVO);

   /** 재료-상품 등록 팝업 - 재료-상품 저장 delete */
   int getRecpProdSaveDelete(RecpOriginVO recpOriginVO);

   /** 재료-상품 등록 팝업 - 재료-상품 저장 update */
   int getRecpProdSaveUpdate(RecpOriginVO recpOriginVO);

   /** 상품-원산지관리탭 - 조회 */
   List<DefaultMap<Object>> getProdRecpOriginList(RecpOriginVO recpOriginVO);

   /** 상품-원산지관리탭 - 재료 및 원산지 등록 조회 */
   List<DefaultMap<Object>> getProdRecpOriginDetailList(RecpOriginVO recpOriginVO);

   /** 재료 및 원산지 등록 팝업 - 조회 */
   List<DefaultMap<Object>> getProdRecpOriginAddList(RecpOriginVO recpOriginVO);

   /** 원산지출력순서 조회(자동채번)  */
   String getRecpSeq(RecpOriginVO recpOriginVO);

   /** 상품-원산지관리탭 - 저장 update */
   int getProdRecpOriginSave(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 조회 */
   List<DefaultMap<Object>> getRecpOriginInfoList(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 - TEXT 조회 */
   List<DefaultMap<Object>> getRecpOriginInfoDetailList(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 - 관리코드(자동채번) */
   String getRecpOriginInfoOriginCd(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 - 관리코드 저장 */
   int getRecpOriginInfoSaveInsert(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 - 관리코드 수정 */
   int getRecpOriginInfoSaveUpdate(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 - 관리코드 삭제 */
   int getRecpOriginInfoSaveDelete(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 - 정보 TEXT 수정 */
   int getRecpOriginTxtUpdate(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 매장적용 팝업 원산지 코드 조회 */
    List<DefaultMap<String>> getSelectOriginCdList(RecpOriginVO recpOriginVO);

   /** 원산지관리-정보입력 매장적용 팝업 매장리스트 조회 */
   List<DefaultMap<String>> getOriginInfoStoreList(SelectStoreVO selectStoreVO);

   /** 원산지관리-정보입력 매장적용 팝업 매장적용 */
   int getOriginInfoRegStore(RecpOriginVO recpOriginVO);
}