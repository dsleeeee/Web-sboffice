package kr.co.solbipos.store.storeMoms.storeVerPatch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.storeVerPatch.service.StoreVerPatchService;
import kr.co.solbipos.store.storeMoms.storeVerPatch.service.StoreVerPatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
