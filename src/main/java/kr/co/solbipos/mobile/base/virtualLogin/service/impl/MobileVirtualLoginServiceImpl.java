package kr.co.solbipos.mobile.base.virtualLogin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.DateUtil;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.session.auth.service.impl.MobileAuthMapper;
import kr.co.solbipos.mobile.base.virtualLogin.service.MobileVirtualLoginService;
import kr.co.solbipos.mobile.base.virtualLogin.service.MobileVirtualLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : MobileVirtualLoginServiceImpl.java
 * @Description : 모바일 > 기초관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.06  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MobileVirtualLoginService")
public class MobileVirtualLoginServiceImpl implements MobileVirtualLoginService {

    private final MobileVirtualLoginMapper mobileVirtualLoginMapper;
    private final MobileAuthMapper mobileAuthMapper;

    /** Constructor Injection */
    @Autowired
    public MobileVirtualLoginServiceImpl(MobileVirtualLoginMapper mobileVirtualLoginMapper, MobileAuthMapper mobileAuthMapper) {
        this.mobileVirtualLoginMapper = mobileVirtualLoginMapper;
        this.mobileAuthMapper = mobileAuthMapper;
    }

    /** (모바일)가상로그인 - 조회 */
    @Override
    public List<DefaultMap<String>> getMobileVirtualLoginList(MobileVirtualLoginVO mobileVirtualLoginVO, SessionInfoVO sessionInfoVO) {
        mobileVirtualLoginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if("H".equals(mobileVirtualLoginVO.getOrgnFg())) { // 본사권한으로 해당 본사코드로만 조회
            mobileVirtualLoginVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (mobileVirtualLoginVO.getStoreHqBrandCd() == "" || mobileVirtualLoginVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (mobileVirtualLoginVO.getUserBrands() != null && !"".equals(mobileVirtualLoginVO.getUserBrands())) {
                    String[] userBrandList = mobileVirtualLoginVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        mobileVirtualLoginVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        if("A".equals(mobileVirtualLoginVO.getOrgnFg())) { //총판권한으로 해당 총판코드로만 조회
            mobileVirtualLoginVO.setAgencyCd(sessionInfoVO.getOrgnCd());
            mobileVirtualLoginVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());
        }

        return mobileVirtualLoginMapper.getMobileVirtualLoginList(mobileVirtualLoginVO);
    }

    @Override
    public int checkMobileVirtualLoginAuth(String userId) {
        
        int abc = mobileVirtualLoginMapper.checkMobileVirtualLoginAuth(userId);
        System.out.println(abc + "뭐지 이거");
        return mobileVirtualLoginMapper.checkMobileVirtualLoginAuth(userId);
    }

    @Override
    public int checkMobileVirtualLoginAuthCheck(MobileVirtualLoginVO mobileVirtualLoginVO, SessionInfoVO sessionInfoVO) {
        mobileVirtualLoginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if("H".equals(mobileVirtualLoginVO.getOrgnFg())) { // 본사권한으로 해당 본사코드로만 조회
            mobileVirtualLoginVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }

        if("A".equals(mobileVirtualLoginVO.getOrgnFg())) { //총판권한으로 해당 총판코드로만 조회
            mobileVirtualLoginVO.setAgencyCd(sessionInfoVO.getOrgnCd());
            mobileVirtualLoginVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());
        }

        return mobileVirtualLoginMapper.checkMobileVirtualLoginAuthCheck(mobileVirtualLoginVO);
    }

    @Override
    public int insertMobileLoginHistory(SessionInfoVO sessionInfoVO) {
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

        return mobileAuthMapper.insertLoginHist(loginHistVO);
    }
}
