package kr.co.solbipos.store.storeMoms.storePosVersion.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.StorePosVersionService;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.StorePosVersionVO;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.impl.StorePosVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StorePosVersionServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > 매장포스버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.30  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.03.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("storePosVersionService")
public class StorePosVersionServiceImpl implements StorePosVersionService {
    private final StorePosVersionMapper storePosVersionMapper;
    private final MessageService messageService;

    @Autowired
    public StorePosVersionServiceImpl(StorePosVersionMapper storePosVersionMapper, MessageService messageService) {
        this.storePosVersionMapper = storePosVersionMapper;
        this.messageService = messageService;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<String>> getStorePosVersionList(StorePosVersionVO storePosVersionVO, SessionInfoVO sessionInfoVO) {

        storePosVersionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때
        if (storePosVersionVO.getStoreHqBrandCd() == "" || storePosVersionVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            String[] userBrandList = storePosVersionVO.getUserBrands().split(",");
            storePosVersionVO.setUserBrandList(userBrandList);
        }

        return storePosVersionMapper.getStorePosVersionList(storePosVersionVO);
    }

}