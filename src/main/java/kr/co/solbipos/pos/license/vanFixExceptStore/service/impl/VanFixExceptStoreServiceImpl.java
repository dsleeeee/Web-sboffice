package kr.co.solbipos.pos.license.vanFixExceptStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.pos.license.vanFixExceptStore.service.VanFixExceptStoreService;
import kr.co.solbipos.pos.license.vanFixExceptStore.service.VanFixExceptStoreVO;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreVO;
import kr.co.solbipos.store.manage.closeStore.service.impl.CloseStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : VanFixExceptStoreServiceImpl.java
 * @Description : 포스관리 > 라이선스 관리 > VAN사 변경허용 매장관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.09  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("VanFixExceptStoreService")
public class VanFixExceptStoreServiceImpl implements VanFixExceptStoreService {

    private VanFixExceptStoreMapper vanFixExceptStoreMapper;

    /** Constructor Injection */
    @Autowired
    public VanFixExceptStoreServiceImpl(VanFixExceptStoreMapper vanFixExceptStoreMapper) {
        this.vanFixExceptStoreMapper = vanFixExceptStoreMapper;
    }

    /** 밴사코드 조회 */
    @Override
    public List<DefaultMap<String>> getVanComboList() {
        return vanFixExceptStoreMapper.getVanComboList();
    }

    /** VAN사 변경허용매장 조회 */
    @Override
    public List<DefaultMap<String>> getVanFixExceptStore(VanFixExceptStoreVO vanFixExceptStoreVO, SessionInfoVO sessionInfoVO) {
        return vanFixExceptStoreMapper.getVanFixExceptStore(vanFixExceptStoreVO);
    }

    /** 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(VanFixExceptStoreVO vanFixExceptStoreVO, SessionInfoVO sessionInfoVO) {
        return vanFixExceptStoreMapper.getStoreList(vanFixExceptStoreVO);
    }

    /** 변경허용매장 등록 */
    @Override
    public int saveFixExceptStore(VanFixExceptStoreVO[] vanFixExceptStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (VanFixExceptStoreVO vanFixExceptStoreVO : vanFixExceptStoreVOs) {
            vanFixExceptStoreVO.setRegDt(dt);
            vanFixExceptStoreVO.setRegId(sessionInfoVO.getUserId());
            vanFixExceptStoreVO.setModDt(dt);
            vanFixExceptStoreVO.setModId(sessionInfoVO.getUserId());
            result = vanFixExceptStoreMapper.saveFixExceptStore(vanFixExceptStoreVO);
        }

        return result;
    }

    /** 변경허용매장 삭제 */
    @Override
    public int deleteFixExceptStore(VanFixExceptStoreVO[] vanFixExceptStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for (VanFixExceptStoreVO vanFixExceptStoreVO : vanFixExceptStoreVOs) {
            result = vanFixExceptStoreMapper.deleteFixExceptStore(vanFixExceptStoreVO);
        }

        return result;
    }
}
