package kr.co.solbipos.application.persistence.com;

import kr.co.solbipos.application.domain.com.BkmkVO;

/**
 * 즐겨찾기관리
 *
 * @author 노현수
 */
public interface BkmkMapper {

    /** 즐겨찾기 메뉴 등록 */
    int insertBkmk( BkmkVO bkmkVO );

    /** 즐겨찾기 메뉴 삭제 */
    int deleteBkmk( BkmkVO bkmkVO );

}
