package kr.co.solbipos.store.storeMoms.storePosVersion.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.verrecv.enums.VerRecvFg;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.StorePosVersionService;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.StorePosVersionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

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

    /** 포스버전 조회 */
    @Override
    public List<DefaultMap<String>> getSelectVerList(SessionInfoVO sessionInfoVO) {
        
        StorePosVersionVO storePosVersionVO = new StorePosVersionVO();
        storePosVersionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storePosVersionMapper.getSelectVerList(storePosVersionVO);

        return list;
    }

    /** 포스용도 조회 */
    @Override
    public List<DefaultMap<String>> getSelectSubPos() {
        return storePosVersionMapper.getSelectSubPos();
    }

    /** 패치정보 상세 팝업 */
    @Override
    public List<DefaultMap<String>> getPatchDtlList(StorePosVersionVO storePosVersionVO) {
        return storePosVersionMapper.getPatchDtlList(storePosVersionVO);
    }

    /** 버전 적용 매장 등록 */
    @Override
    public int registStore(StorePosVersionVO storePosVersionVO, SessionInfoVO sessionInfo) {

        int cnt = 0;

        String dt = currentDateTimeString();

        storePosVersionVO.setRegDt(dt);
        storePosVersionVO.setModDt(dt);
        storePosVersionVO.setRegId(sessionInfo.getUserId());
        storePosVersionVO.setModId(sessionInfo.getUserId());
        storePosVersionVO.setVerRecvFg(VerRecvFg.REG);
        storePosVersionVO.setVerRecvDt(dt);

        cnt = storePosVersionMapper.registStore(storePosVersionVO);

        return cnt;
    }

    @Override
    public List<DefaultMap<String>> getPosPatchLogList(StorePosVersionVO storePosVersionVO, SessionInfoVO sessionInfoVO) {

        storePosVersionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때
        if (storePosVersionVO.getStoreHqBrandCd() == "" || storePosVersionVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            String[] userBrandList = storePosVersionVO.getUserBrands().split(",");
            storePosVersionVO.setUserBrandList(userBrandList);
        }

        return storePosVersionMapper.getPosPatchLogList(storePosVersionVO);
    }
}