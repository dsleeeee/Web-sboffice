package kr.co.solbipos.sys.etc.vanManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.etc.vanManage.service.VanManageService;
import kr.co.solbipos.sys.etc.vanManage.service.VanManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name : VanManageServiceImpl.java
 * @Description : 시스템관리 > Van/Card사 관리 > 밴사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.13  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("VanManageService")
public class VanManageServiceImpl implements VanManageService {

    private final VanManageMapper vanManageMapper;

    @Autowired
    public VanManageServiceImpl(VanManageMapper vanManageMapper) {
        this.vanManageMapper = vanManageMapper;
    }

    /** 밴사정보관리 - 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdList(VanManageVO vanManageVo) {
        return vanManageMapper.getNmcodeGrpCdList(vanManageVo);
    }

    /** 밴사정보관리 - 상세조회 */
    @Override
    public List<DefaultMap<String>> getVanManageList(VanManageVO vanManageVo) {
        return vanManageMapper.getVanManageList(vanManageVo);
    }

    /**  밴사정보관리 - 밴사정보 저장 */
    @Override
    public int getVanSave(VanManageVO[] vanManageVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        String currentDt = currentDateTimeString();

        for(VanManageVO vanManageVO : vanManageVOs) {

            vanManageVO.setRegDt(currentDt);
            vanManageVO.setRegId(sessionInfoVO.getUserId());
            vanManageVO.setModDt(currentDt);
            vanManageVO.setModId(sessionInfoVO.getUserId());

            result = vanManageMapper.getVanSave(vanManageVO);

        }
        return result;
    }

    /** 밴사정보관리 - 밴사정보 삭제 */
    @Override
    public int getVanDelete(VanManageVO[] vanManageVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for(VanManageVO vanManageVO : vanManageVOs) {
            result = vanManageMapper.getVanDelete(vanManageVO);
        }
        return result;
    }
}
