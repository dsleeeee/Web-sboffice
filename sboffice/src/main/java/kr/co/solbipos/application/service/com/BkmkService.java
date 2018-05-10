package kr.co.solbipos.application.service.com;

import kr.co.solbipos.application.domain.com.BkmkVO;

/**
 * 즐겨찾기관리
 *
 * @author 노현수
 */
public interface BkmkService {

    /** 즐겨찾기 메뉴 관리 */
    int saveBkmk( BkmkVO bkmkVO, String userId );

}
