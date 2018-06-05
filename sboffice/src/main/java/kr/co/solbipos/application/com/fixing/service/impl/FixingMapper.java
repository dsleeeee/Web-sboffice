package kr.co.solbipos.application.com.fixing.service.impl;

import kr.co.solbipos.application.com.fixing.service.FixingVO;

/**
 * 고정메뉴 관리
 *
 * @author 노현수
 */
public interface FixingMapper {

    /** 고정메뉴 등록 */
    int insertFixing( FixingVO fixingVO );

    /** 고정메뉴 삭제 */
    int deleteFixing( FixingVO fixingVO );

}
