package kr.co.solbipos.store.storeMoms.storeInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.storeMoms.storeInfo.service.StoreInfoService;
import kr.co.solbipos.store.storeMoms.storeInfo.service.StoreInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreInfoServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > 매장정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.14  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2022.12.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("storeInfoService")
public class StoreInfoServiceImpl implements StoreInfoService {
    private final StoreInfoMapper storeInfoMapper;
    private final MessageService messageService;

    @Autowired
    public StoreInfoServiceImpl(StoreInfoMapper storeInfoMapper, MessageService messageService) {
        this.storeInfoMapper = storeInfoMapper;
        this.messageService = messageService;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<String>> getStoreInfoList(StoreInfoVO storeInfoVO, SessionInfoVO sessionInfoVO) {

        storeInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때
        if (storeInfoVO.getStoreHqBrandCd() == "" || storeInfoVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            String[] userBrandList = storeInfoVO.getUserBrands().split(",");
            storeInfoVO.setUserBrandList(userBrandList);
        }

        return storeInfoMapper.getStoreInfoList(storeInfoVO);
    }

}