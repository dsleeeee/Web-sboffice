package kr.co.solbipos.base.store.posfunc.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.posfunc.service.PosFuncService;
import kr.co.solbipos.base.store.posfunc.service.PosFuncVO;

/**
 * @Class Name : PosFuncServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 포스기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.26  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.26
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("posFunService")
public class PosFuncServiceImpl implements PosFuncService{

    @Autowired
    PosFuncMapper mapper;
    
    /** 포스목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosList(PosFuncVO posFuncVO) {
        return mapper.getPosList(posFuncVO);
    }
    
    /** 포스기능목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosFuncList(PosFuncVO posFuncVO) {
        return mapper.getPosFuncList(posFuncVO);
    }
    
    /** 포스기능상세 조회 */
    @Override
    public List<DefaultMap<String>> getPosConfDetail(PosFuncVO posFuncVO) {
        return mapper.getPosConfDetail(posFuncVO);
    }
    
    /** 포스기능상세 저장 */
    @Override
    public int savePosConf(PosFuncVO[] posFuncVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();
        
        for(PosFuncVO posFuncVO : posFuncVOs) {
            posFuncVO.setModDt(dt);
            posFuncVO.setModId(sessionInfoVO.getUserId());
            
            if(posFuncVO.getStatus() == GridDataFg.UPDATE){
                procCnt += mapper.savePosConfDetail(posFuncVO);
            }
        }
        return procCnt;
    }
}
