package kr.co.solbipos.base.prod.recpOrigin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
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
 * @author 솔비포스 개발본부 백엔드PT 김설아
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

   /** 재료-상품 저장 insert */
   int getRecpProdSaveInsert(RecpOriginVO recpOriginVO);

   /** 재료-상품 저장 delete */
   int getRecpProdSaveDelete(RecpOriginVO recpOriginVO);
}