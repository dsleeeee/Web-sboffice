package kr.co.solbipos.common.method.service.impl;

import kr.co.solbipos.common.method.service.CommonMethodVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : CommonMethodMapper.java
 * @Description : (공통) 화면 공통 사용 메소드 모음
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.13  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface CommonMethodMapper {

    /** 사용자 행위 기록 */
    int saveUserAct(CommonMethodVO commonMethodVO);
}
