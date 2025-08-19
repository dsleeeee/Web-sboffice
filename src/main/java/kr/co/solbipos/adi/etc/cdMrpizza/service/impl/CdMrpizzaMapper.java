package kr.co.solbipos.adi.etc.cdMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.cdMrpizza.service.CdMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : CdMrpizzaMapper.java
 * @Description : 미스터피자 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.14  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface CdMrpizzaMapper {
    
    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdMrpizzaList(CdMrpizzaVO cdMrpizzaVO);

    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdMrpizzaList(CdMrpizzaVO cdMrpizzaVO);

    /** 시스템 명칭관리 - 추가 */
    int getNmcodeCdMrpizzaSaveInsert(CdMrpizzaVO cdMrpizzaVO);

    /** 시스템 명칭관리 - 수정 */
    int getNmcodeCdMrpizzaSaveUpdate(CdMrpizzaVO cdMrpizzaVO);

    /** 시스템 명칭관리 - 삭제 */
    int getNmcodeCdMrpizzaSaveDelete(CdMrpizzaVO cdMrpizzaVO);
}
