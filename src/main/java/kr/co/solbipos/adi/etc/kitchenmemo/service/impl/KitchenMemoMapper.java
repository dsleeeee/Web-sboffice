package kr.co.solbipos.adi.etc.kitchenmemo.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import org.apache.ibatis.annotations.Mapper;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoVO;
import org.springframework.stereotype.Repository;

/**
* @Class Name : KitchenMemoMapper.java
* @Description : 부가서비스 > 주방메모관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface KitchenMemoMapper {

    /** 주방메모 조회 */
    List<DefaultMap<String>> getKitchenMemoList(KitchenMemoVO kitchenMemoVO);

    /** 주방메모코드 조회 */
    String getKitchnMemoCd(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 등록 */
    int insertKitchenMemo(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 수정 */
    int updateKitchenMemo(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 삭제 */
    int deleteKitchenMemo(KitchenMemoVO kitchenMemoVO);
}
