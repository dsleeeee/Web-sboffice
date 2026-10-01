package kr.co.solbipos.sys.stats.userWebHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : UserWebHistMapper.java
 * @Description : 시스템관리 > 통계 > 사용자웹사용이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김유승      최초생성
 * @ 2026.09.22  김유승      조회정보(조회건수) 팝업 조회 추가
 * @ 2026.09.23  김유승      ③ 메뉴(URL)별 건수 조회 추가
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface UserWebHistMapper {

    /** 사용자웹사용이력 조회 */
    List<DefaultMap<Object>> getUserWebHistList(UserWebHistVO userWebHistVO);

    /** 사용자 아이디 일시정지 */
    int getPauseUserId(UserWebHistVO userWebHistVO);

    /** 사용자 아이디 일시정지 해제 */
    int getResumeUserId(UserWebHistVO userWebHistVO);

    /** [조회정보 팝업] 조회건수 USER_ID 종합 조회 */
    List<DefaultMap<Object>> getUserWebChkCntList(UserWebHistVO userWebHistVO);

    /** [조회정보 팝업] 조회건수 USER_ID + IP별 상세 조회 */
    List<DefaultMap<Object>> getUserWebChkCntIpList(UserWebHistVO userWebHistVO);

    /** [조회정보 팝업] ③ 메뉴(URL)별 건수 조회 */
    List<DefaultMap<Object>> getUserWebChkCntMenuList(UserWebHistVO userWebHistVO);

    /** [조회정보 팝업] ② IP별 상세 0건 시 원천 데이터 진단 */
    DefaultMap<Object> getUserWebChkCntIpDiag(UserWebHistVO userWebHistVO);

    /** [일시정지 이력] 처리 전 사용자 상태 조회 */
    String getUserStatFg(UserWebHistVO userWebHistVO);

    /** [일시정지 이력] 등록 */
    int insertUserPauseHist(UserWebHistVO userWebHistVO);

    /** [일시정지 이력] 조회 */
    List<DefaultMap<Object>> getUserPauseHistList(UserWebHistVO userWebHistVO);

    /** [조회정보 팝업] ⑤ 웹사용자정보변경이력 조회 */
    List<DefaultMap<Object>> getUserInfoLogList(UserWebHistVO userWebHistVO);
}
