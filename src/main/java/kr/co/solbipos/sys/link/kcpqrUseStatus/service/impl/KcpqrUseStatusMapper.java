package kr.co.solbipos.sys.link.kcpqrUseStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.link.kcpqrUseStatus.service.KcpqrUseStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : KcpqrUseStatusMapper.java
 * @Description : 시스템관리 > 연동 > KCPQR현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface KcpqrUseStatusMapper {

    /** KCPQR현황 - 조회 */
    List<DefaultMap<String>> getSearchKcpqrStatus(KcpqrUseStatusVO kcpqrUseStatusVO);
}
