package kr.co.solbipos.sys.persistence.menu.webmenu;

import java.util.List;
import kr.co.solbipos.application.domain.resource.ResrceInfo;

/**
 * 
 * 시스템 관리 > 메뉴관리 > 프로그램 메뉴관리
 * 
 * @author 정용길
 *
 */
public interface WebMenuMapper {

    /**
     * 리소스 하위 기능 정보 포함 조회
     * 
     * @param resrceInfo
     * @return
     */
    List<String> selectWebMenu(ResrceInfo resrceInfo);

    /**
     * 리소스 트리 레벨로 조회
     * 
     * @param level <br>
     *        0: depth 1<br>
     *        1: depth 2<br>
     *        2: depth 3
     * @return
     */
    List<ResrceInfo> selectWebMenuLevel(int level);

    /**
     * resrceCd 로 리소스 정보 조회
     * 
     * @param resrceInfo
     * @return
     */
    ResrceInfo selectWebMenuByResrceCd(ResrceInfo resrceInfo);

    /**
     * 리소스 저장 {@code selectKey} 를 사용해서 추가한 {@code resrceCd} 를 사용할수 있음
     * 
     * @param resrceInfo
     * @return
     */
    int insertWebMenu(ResrceInfo resrceInfo);

    /**
     * 리소스 정보 업데이트
     * 
     * @param resrceInfo
     * @return
     */
    int updateWebMenu(ResrceInfo resrceInfo);

    /**
     * 리소스 정보 삭제 {@code USE_YN} : N 으로 처리
     * 
     * @param resrceInfo
     * @return
     */
    int deleteWebMenu(ResrceInfo resrceInfo);

    /**
     * 해당 리소스의 하위 정보를 포함해서 삭제 {@code USE_YN} : N 으로 처리
     * 
     * @param resrceInfo
     * @return
     */
    int deleteWebMenuAll(ResrceInfo resrceInfo);
}
