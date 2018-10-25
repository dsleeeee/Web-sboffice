package kr.co.solbipos.sys.menu.webmenu.service.impl;

import kr.co.solbipos.application.common.service.ResrceInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 시스템 관리 > 메뉴관리 > 프로그램 메뉴관리
 *
 * @author 정용길
 *
 */
@Mapper
@Repository
public interface WebMenuMapper {

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
     * resrceCd 로 리소스 정보 조회
     *
     * @param resrceInfoVO
     * @return
     */
    ResrceInfoVO selectWebMenuByResrceCd(ResrceInfoVO resrceInfoVO);

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
     * 해당 리소스의 하위 정보를 포함해서 삭제 {@code USE_YN} : N 으로 처리
     *
     * @param resrceInfoVO
     * @return
     */
    int deleteWebMenuAll(ResrceInfoVO resrceInfoVO);
}
