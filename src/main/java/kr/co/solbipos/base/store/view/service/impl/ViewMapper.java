package kr.co.solbipos.base.store.view.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.view.service.CopyStoreEnvVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosEnvVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Class Name : ViewMapper.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.13  김영근      최초생성
 * @ 2018.11.20  김지은      angular 방식으로 수정
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface ViewMapper {

    /** 매장정보 목록 조회 */
    List<DefaultMap<String>> getViewList(ViewVO viewVO);

    /** 매장정보 리스트 엑셀 조회 */
    List<DefaultMap<String>> getStoreListExcel(ViewVO viewVO);

    /** 매장정보 상세 조회 */
    DefaultMap<String> getViewDetail(ViewVO viewVO);

    /** 코너 사용여부 조회 */
    String getCornerUseYnVal(StoreEnvVO storeEnvVO);
    /** 포스별 승인 목록 조회*/
    List<DefaultMap<String>> getPosTerminalList(VanConfigVO vanConfigVO);

    /** 코너별 승인 목록 조회 */
    List<DefaultMap<String>> getCornerTerminalList(VanConfigVO vanConfigVO);

    /** 포스목록 조회 */
    List<DefaultMap<String>> getPosList(StorePosEnvVO storePosEnvVO);

    /** 매장 공통코드 복사 */
    String copyStoreNmcode(CopyStoreEnvVO copyStoreEnvVO);

    /** 매장 환경 복사 */
    String copyStoreEnv(CopyStoreEnvVO copyStoreEnvVO);

    /** 포스 환경 복사 */
    String copyPosEnv(CopyStoreEnvVO copyStoreEnvVO);

    /** 상품 복사 */
    String copyProduct(CopyStoreEnvVO copyStoreEnvVO);

    /** 판매가격 복사 */
    String copySalePrice(CopyStoreEnvVO copyStoreEnvVO);

    /** 포스 기능키 XML 조회 */
    String getFuncKeyXml(DefaultMap<String> param);

    /** 포스 기능키 XML 복사 */
    String copyPosFnKeyXML(CopyStoreEnvVO copyStoreEnvVO);

    /** 포스기능키 복사 */
    String copyPosFnKey(CopyStoreEnvVO copyStoreEnvVO);

    /** 판매터치키 전체복사 */
    String copyTouchKey(CopyStoreEnvVO copyStoreEnvVO);

    /** 터치키 XML 조회 */
    String getTouchKeyXml(DefaultMap<String> param);

    /** 터치키 XML 전체복사 */
    String copyTouchKeyXML(CopyStoreEnvVO copyStoreEnvVO);

    /** 쿠폰분류 복사 */
    String copyCouponClass(CopyStoreEnvVO copyStoreEnvVO);

    /** 상품권 복사 */
    String copyGift(CopyStoreEnvVO copyStoreEnvVO);

    /** 입금/출금계정 복사 */
    String copyAccount(CopyStoreEnvVO copyStoreEnvVO);

    /** 원산지 복사 */
    String copyRecpOrigin(CopyStoreEnvVO copyStoreEnvVO);

    /** 식품 알레르기 복사 */
    String copyFoodAllergy(CopyStoreEnvVO copyStoreEnvVO);

    /** 배달상품명칭매핑 복사 */
    String copyDlvrProd(CopyStoreEnvVO copyStoreEnvVO);

    /** 매장환경 복사를 위한 정보 조회 */
    List<DefaultMap<String>> getStoreEnvInfoList(CopyStoreEnvVO copyStoreEnvVO);

    /** 매장 리스트 조회 */
    List<DefaultMap<String>> getStoreList(ViewVO viewVO);

    /** 매장 판매터치키 콤보박스 데이터 조회 */
    List<DefaultMap<String>> getStoreTouchKeyGrpCombo(ViewVO viewVO);

    /** 판매터치키 선택그룹 복사 */
    String copyTouchKeyGrp(CopyStoreEnvVO copyStoreEnvVO);

    /** 판매터치키 선택그룹 XML 복사 */
    String copyTouchKeyGrpXML(CopyStoreEnvVO copyStoreEnvVO);

    /** 판매터치키 그룹명 삭제 */
    int deleteTouchKeyGrpNm(CopyStoreEnvVO copyStoreEnvVO);
    /** 판매터치키 그룹명 복사 */
    int copyTouchKeyGrpNm(CopyStoreEnvVO copyStoreEnvVO);
}
