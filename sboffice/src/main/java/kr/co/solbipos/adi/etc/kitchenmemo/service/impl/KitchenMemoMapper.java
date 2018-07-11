package kr.co.solbipos.adi.etc.kitchenmemo.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

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
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
public interface KitchenMemoMapper {

    /** 주방메모 조회 */
    public <E> List<E> selectKitchenMemo(SessionInfoVO sessionInfoVO);

    /** 주방메모 건수 조회 */
    public int selectKitchenMemoCnt(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 저장 */
    public int save(KitchenMemoVO[] kitchenMemoVOs, SessionInfoVO sessionInfoVO);

    /** 주방메모 등록 */
//    void insertKitchenMemo(KitchenMemo kitchenMemoVO);
    int insertKitchenMemo(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 수정 */
//    void updateKitchenMemo(KitchenMemo kitchenMemoVO);
    int updateKitchenMemo(KitchenMemoVO kitchenMemoVO);

    /** 주방메모 삭제 */
//    void deleteKitchenMemo(KitchenMemo kitchenMemoVO);
    int deleteKitchenMemo(KitchenMemoVO kitchenMemoVO);

}
