package kr.co.solbipos.store.manage.virtuallogin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginVO;

/**
 * @Class Name : VirtualLoginServiceImpl.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("virtualLoginService")
public class VirtualLoginServiceImpl implements VirtualLoginService {

    @Autowired
    VirtualLoginMapper virtualLoginMapper;

    /** 가상로그인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getVirtualLoginList(VirtualLoginVO virtualLoginVO) {
        return virtualLoginMapper.getVirtualLoginList(virtualLoginVO);
    }

    /** 가상로그인 권한 조회 */
    @Override
    public Integer checkVirtualLoginAuth(String userId) {
        return virtualLoginMapper.checkVirtualLoginAuth(userId);
    }
    
}