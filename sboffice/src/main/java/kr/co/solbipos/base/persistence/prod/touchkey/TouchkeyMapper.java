package kr.co.solbipos.base.persistence.prod.touchkey;

import java.util.List;
import kr.co.solbipos.base.domain.prod.touchkey.Touch;
import kr.co.solbipos.base.domain.prod.touchkey.TouchClass;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 기초관리 - 상품관리 - 판매터치키등록
 * 
 * @author 조병준
 *
 */
public interface TouchkeyMapper {

    /** 
     * 상품 조회
     * @param storeCd 매장코드
     * @return DefaultMap
     */
    List<DefaultMap<String>> selectProdByStore(String storeCd);
    
    /** 
     * 상품 터치키 그룹 삭제
     * @param storeCd 매장코드
     * @return
     */
    int deleteTouchClassByStore(String storeCd);

    /** 
     * 상품 터치키 그룹 등록
     * @param tableGroup
     * @return
     */
    int insertTouchClassByStore(TouchClass touchClass);

    /** 
     * 상품 터치키 삭제
     * @param storeCd 매장코드
     * @return
     */
    int deleteTouchByStore(String storeCd);

    /** 
     * 상품 터치키 등록
     * @param table
     * @return
     */
    int insertTouchByStore(Touch touch);

}
