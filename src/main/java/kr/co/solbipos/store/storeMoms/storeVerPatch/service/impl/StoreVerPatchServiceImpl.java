package kr.co.solbipos.store.storeMoms.storeVerPatch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.storeVerPatch.service.StoreVerPatchService;
import kr.co.solbipos.store.storeMoms.storeVerPatch.service.StoreVerPatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreVerPatchServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > 버전패치현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.04  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("storeVerPatchService")
public class StoreVerPatchServiceImpl implements StoreVerPatchService {

    private final StoreVerPatchMapper storeVerPatchMapper;

    @Autowired
    public StoreVerPatchServiceImpl(StoreVerPatchMapper storeVerPatchMapper) {
        this.storeVerPatchMapper = storeVerPatchMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<String>> getStoreVerPatchList(StoreVerPatchVO storeVerPatchVO, SessionInfoVO sessionInfoVO) {

        storeVerPatchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때
//        if (storeVerPatchVO.getStoreHqBrandCd() == "" || storeVerPatchVO.getStoreHqBrandCd() == null) {
//            // 사용자별 브랜드 array 값 세팅
//            String[] userBrandList = storeVerPatchVO.getUserBrands().split(",");
//            storeVerPatchVO.setUserBrandList(userBrandList);
//        }
        return storeVerPatchMapper.getStoreVerPatchList(storeVerPatchVO);
    }

    /** 매장 정보 팝업 - 조회 */
    @Override
    public List<DefaultMap<String>> getPatchStoreDtlList(StoreVerPatchVO storeVerPatchVO, SessionInfoVO sessionInfoVO) {
        storeVerPatchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeVerPatchMapper.getPatchStoreDtlList(storeVerPatchVO);
    }
}
