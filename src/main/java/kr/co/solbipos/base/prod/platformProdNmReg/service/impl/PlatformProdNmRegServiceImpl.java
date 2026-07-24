package kr.co.solbipos.base.prod.platformProdNmReg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.platformProdNmReg.service.PlatformProdNmRegService;
import kr.co.solbipos.base.prod.platformProdNmReg.service.PlatformProdNmRegVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("platformProdNmRegService")
@Transactional
public class PlatformProdNmRegServiceImpl implements PlatformProdNmRegService {
    private final PlatformProdNmRegMapper platformProdNmRegMapper;

    public PlatformProdNmRegServiceImpl(PlatformProdNmRegMapper platformProdNmRegMapper) {
        this.platformProdNmRegMapper = platformProdNmRegMapper;
    }

    /** 플랫폼 상품명 등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPlatformProdNmRegList(PlatformProdNmRegVO platformProdNmRegVO, SessionInfoVO sessionInfoVO) {

        platformProdNmRegVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        platformProdNmRegVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (platformProdNmRegVO.getProdHqBrandCd() == "" || platformProdNmRegVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (platformProdNmRegVO.getUserBrands() != null && !"".equals(platformProdNmRegVO.getUserBrands())) {
                    String[] userBrandList = platformProdNmRegVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        platformProdNmRegVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return platformProdNmRegMapper.getPlatformProdNmRegList(platformProdNmRegVO);
    }

    /** 플랫폼 상품명 등록 저장 */
    @Override
    public int savePlatformProdNm(PlatformProdNmRegVO[] platformProdNmRegVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for (PlatformProdNmRegVO platformProdNmRegVO : platformProdNmRegVOs) {

            platformProdNmRegVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            platformProdNmRegVO.setRegDt(dt);
            platformProdNmRegVO.setRegId(sessionInfoVO.getUserId());
            platformProdNmRegVO.setModDt(dt);
            platformProdNmRegVO.setModId(sessionInfoVO.getUserId());

            result += platformProdNmRegMapper.savePlatformProdNm(platformProdNmRegVO);
        }

        return result;
    }

}
