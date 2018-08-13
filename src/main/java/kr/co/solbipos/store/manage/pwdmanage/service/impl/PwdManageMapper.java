package kr.co.solbipos.store.manage.pwdmanage.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;

/**
 * @Class Name : PwdManageMapper.java
 * @Description : 가맹점관리 > 매장관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface PwdManageMapper {
    
    /** 비밀번호 임의변경 대상 조회 */
    List<DefaultMap<String>> getPwdManageList(PwdManageVO pwdManageVO);
    
    /** 기존 비밀번호 조회 */
    String getOldPassword(PwdManageVO pwdManageVO);
    
    /** 비밀번호 변경 */
    int updatePassword(PwdManageVO pwdManageVO);
    
    /** 비밀번호 변경이력 저장 */
    int insertPasswordHistory(PwdManageVO pwdManageVO);
    
}
