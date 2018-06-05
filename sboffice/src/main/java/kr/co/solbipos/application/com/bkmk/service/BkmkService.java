package kr.co.solbipos.application.com.bkmk.service;

/**
 * 즐겨찾기 관리
 *
 * @author 노현수
 */
public interface BkmkService {

    /** 즐겨찾기 메뉴 관리 */
    int saveBkmk( BkmkVO bkmkVO, String userId );

}
