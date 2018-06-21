package kr.co.common.validate;

import java.util.HashMap;
import java.util.List;
import kr.co.solbipos.application.common.service.ResrceInfoVO;

/**
 *
 * 시스템 관리 > 메뉴관리 > 프로그램 메뉴관리
 *
 * @author 정용길
 *
 */
public interface WebMenuService {

    /**
     * 리소스 하위 기능 정보 포함 조회
     *
     * @param resrceInfoVO
     * @return
     */
    List<String> selectWebMenu(ResrceInfoVO resrceInfoVO);

    /**
     * 리소스 트리 레벨로 조회
     *
     * @param level <br>
     *        0: depth 1<br>
     *        1: depth 2<br>
     *        2: depth 3
     * @return
     */
    List<ResrceInfoVO> selectWebMenuLevel(int level);

    /**
     * 리소스 저장 {@code selectKey} 를 사용해서 추가한 {@code resrceCd} 를 사용할수 있음
     *
     * @param resrceInfoVO
     * @return
     */
    int insertWebMenu(ResrceInfoVO resrceInfoVO);

    /**
     * 리소스 정보 업데이트
     *
     * @param resrceInfoVO
     * @return
     */
    int updateWebMenu(ResrceInfoVO resrceInfoVO);

    /**
     * 리소스 정보 삭제 {@code USE_YN} : N 으로 처리
     *
     * @param resrceInfoVO
     * @return
     */
    int deleteWebMenu(ResrceInfoVO resrceInfoVO);

    /**
     * 위즈모 트리에 사용하는 데이터를 만듬
     *
     * @return
     */
    public List<HashMap<String, Object>> makeupTree();

    /**
     * 리소스 메뉴, 기능을 저장한다.
     *
     * @param resrceInfoVO
     * @return
     */
    boolean insertMenu(ResrceInfoVO resrceInfoVO);

}
