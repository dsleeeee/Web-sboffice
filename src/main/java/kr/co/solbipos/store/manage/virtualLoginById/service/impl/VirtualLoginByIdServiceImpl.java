package kr.co.solbipos.store.manage.virtualLoginById.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;
import kr.co.solbipos.store.manage.virtualLoginById.service.VirtualLoginByIdService;
import kr.co.solbipos.store.manage.virtualLoginById.service.VirtualLoginByIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : VirtualLoginByIdServiceImpl.java
 * @Description : 기초관리 > 가상로그인 > 가상 로그인(아이디별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.06  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2025.02.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("virtualLoginServiceById")
public class VirtualLoginByIdServiceImpl implements VirtualLoginByIdService {

    private final VirtualLoginByIdMapper virtualLoginByIdMapper;
    private final AuthMapper authMapper;

    /** Constructor Injection */
    @Autowired
    public VirtualLoginByIdServiceImpl(VirtualLoginByIdMapper virtualLoginByIdMapper, AuthMapper authMapper) {
        this.virtualLoginByIdMapper = virtualLoginByIdMapper;
        this.authMapper = authMapper;
    }

    /** 가상로그인(아이디별) - 조회 */
    @Override
    public List<DefaultMap<String>> getVirtualLoginByIdList(VirtualLoginByIdVO virtualLoginByIdVO, SessionInfoVO sessionInfoVO) {
        virtualLoginByIdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        virtualLoginByIdVO.setSystemId(sessionInfoVO.getUserId());

        if("H".equals(virtualLoginByIdVO.getOrgnFg())) { // 본사권한으로 해당 본사코드로만 조회
            virtualLoginByIdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (virtualLoginByIdVO.getStoreHqBrandCd() == "" || virtualLoginByIdVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (virtualLoginByIdVO.getUserBrands() != null && !"".equals(virtualLoginByIdVO.getUserBrands())) {
                    String[] userBrandList = virtualLoginByIdVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        virtualLoginByIdVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        if("A".equals(virtualLoginByIdVO.getOrgnFg())) { //총판권한으로 해당 총판코드로만 조회
            virtualLoginByIdVO.setAgencyCd(sessionInfoVO.getOrgnCd());
            virtualLoginByIdVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());
        }

        return virtualLoginByIdMapper.getVirtualLoginByIdList(virtualLoginByIdVO);
    }
}
