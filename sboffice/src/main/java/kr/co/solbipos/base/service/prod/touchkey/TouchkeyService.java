package kr.co.solbipos.base.service.prod.touchkey;

import java.util.List;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 기초관리 - 상품관리 - 판매터치키등록
 * 
 * @author 조병준
 *
 */
public interface TouchkeyService {

    <E> List<E> selectTouchkey(DefaultMap<String> param);

}
