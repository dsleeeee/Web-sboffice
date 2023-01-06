package kr.co.solbipos.store.manage.posHwInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;
import kr.co.solbipos.store.manage.posHwInfo.service.PosHwInfoService;
import kr.co.solbipos.store.manage.posHwInfo.service.PosHwInfoVO;
import kr.co.solbipos.store.manage.virtuallogin.service.impl.VirtualLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : PosHwInfoServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 포스 H/W정보 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("posHwInfoService")
public class PosHwInfoServiceImpl implements PosHwInfoService {

    private final PosHwInfoMapper posHwInfoMapper;
    private final AuthMapper authMapper;

    /** Constructor Injection */
    @Autowired
    public PosHwInfoServiceImpl(PosHwInfoMapper posHwInfoMapper, AuthMapper authMapper) {
        this.posHwInfoMapper = posHwInfoMapper;
        this.authMapper = authMapper;
    }

    /** 포스 H/W정보 현황 - 조회 */
    @Override
    public List<DefaultMap<String>> getPosHwInfo(PosHwInfoVO posHwInfoVO, SessionInfoVO sessionInfoVO) {
        posHwInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if("H".equals(posHwInfoVO.getOrgnFg())) { // 본사권한으로 해당 본사코드로만 조회
            posHwInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (posHwInfoVO.getStoreHqBrandCd() == "" || posHwInfoVO.getStoreHqBrandCd() == null) {
              // 사용자별 브랜드 array 값 세팅
              if (posHwInfoVO.getUserBrands() != null && !"".equals(posHwInfoVO.getUserBrands())) {
                  String[] userBrandList = posHwInfoVO.getUserBrands().split(",");
                  if (userBrandList.length > 0) {
                      posHwInfoVO.setUserBrandList(userBrandList);
                  }
              }
            }
        }

        if("A".equals(posHwInfoVO.getOrgnFg())) { //총판권한으로 해당 총판코드로만 조회
            posHwInfoVO.setAgencyCd(sessionInfoVO.getOrgnCd());
            posHwInfoVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());
        }
        return posHwInfoMapper.getPosHwInfo(posHwInfoVO);
    }
}
