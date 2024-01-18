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
}
