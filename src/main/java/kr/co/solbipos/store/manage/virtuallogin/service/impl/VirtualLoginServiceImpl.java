package kr.co.solbipos.store.manage.virtuallogin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.DateUtil;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : VirtualLoginServiceImpl.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
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
@Service("virtualLoginService")
public class VirtualLoginServiceImpl implements VirtualLoginService {

    private final VirtualLoginMapper virtualLoginMapper;
    private final AuthMapper authMapper;

    /** Constructor Injection */
    @Autowired
    public VirtualLoginServiceImpl(VirtualLoginMapper virtualLoginMapper, AuthMapper authMapper) {
        this.virtualLoginMapper = virtualLoginMapper;
        this.authMapper = authMapper;
    }

    /** 가상로그인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getVirtualLoginList(VirtualLoginVO virtualLoginVO, SessionInfoVO sessionInfoVO) {

        virtualLoginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if("H".equals(virtualLoginVO.getOrgnFg())) { // 본사권한으로 해당 본사코드로만 조회
            virtualLoginVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (virtualLoginVO.getStoreHqBrandCd() == "" || virtualLoginVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (virtualLoginVO.getUserBrands() != null && !"".equals(virtualLoginVO.getUserBrands())) {
                    String[] userBrandList = virtualLoginVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        virtualLoginVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        if("A".equals(virtualLoginVO.getOrgnFg())) { //총판권한으로 해당 총판코드로만 조회
            virtualLoginVO.setAgencyCd(sessionInfoVO.getOrgnCd());
            virtualLoginVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());
        }

        return virtualLoginMapper.getVirtualLoginList(virtualLoginVO);
    }

    /** 가상로그인 권한 조회 */
    @Override
    public int checkVirtualLoginAuth(String userId) {
        return virtualLoginMapper.checkVirtualLoginAuth(userId);
    }

    /** 가상로그인 권한 조회 실제권한체크 */
    @Override
    public int checkVirtualLoginAuthCheck(VirtualLoginVO virtualLoginVO, SessionInfoVO sessionInfoVO) {

        virtualLoginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if("H".equals(virtualLoginVO.getOrgnFg())) { // 본사권한으로 해당 본사코드로만 조회
            virtualLoginVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }

        if("A".equals(virtualLoginVO.getOrgnFg())) { //총판권한으로 해당 총판코드로만 조회
            virtualLoginVO.setAgencyCd(sessionInfoVO.getOrgnCd());
            virtualLoginVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());
        }

        return virtualLoginMapper.checkVirtualLoginAuthCheck(virtualLoginVO);
    }

    /** 가상로그인 이력 생성 */
    @Override
    public int insertLoginHistory(SessionInfoVO sessionInfoVO) {

        // 로그인 히스토리 생성
        LoginHistVO loginHistVO = new LoginHistVO();
        // 로그인 결과
        loginHistVO.setUserId(sessionInfoVO.getUserId());
        loginHistVO.setStatCd(sessionInfoVO.getLoginResult());
        loginHistVO.setLoginOrgn(LoginOrigin.VIR);
        loginHistVO.setBrwsrInfo(sessionInfoVO.getBrwsrInfo());
        loginHistVO.setLoginIp(sessionInfoVO.getLoginIp());
        loginHistVO.setLoginDate(DateUtil.currentDateString());
        loginHistVO.setLoginDt(DateUtil.currentDateTimeString());
        loginHistVO.setvUserId(sessionInfoVO.getvUserId());
        loginHistVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return authMapper.insertLoginHist(loginHistVO);
    }



}
