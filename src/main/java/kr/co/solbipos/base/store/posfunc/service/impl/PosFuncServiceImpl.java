package kr.co.solbipos.base.store.posfunc.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;

import kr.co.common.data.enums.UseYn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("posFunService")
public class PosFuncServiceImpl implements PosFuncService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

        List<DefaultMap<String>> list = mapper.getPosFuncList(posFuncVO);

        return list;
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
            posFuncVO.setRegDt(dt);
            posFuncVO.setRegId(sessionInfoVO.getUserId());
            posFuncVO.setModDt(dt);
            posFuncVO.setModId(sessionInfoVO.getUserId());

            if(posFuncVO.getStatus() == GridDataFg.UPDATE){
                procCnt += mapper.savePosConf(posFuncVO);
            }
        }
        return procCnt;
    }

    /** 포스기능 복사 */
    @Override
    public int copyPosFunc(PosFuncVO posFuncVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        posFuncVO.setRegDt(dt);
        posFuncVO.setRegId(sessionInfoVO.getUserId());
        posFuncVO.setModDt(dt);
        posFuncVO.setModId(sessionInfoVO.getUserId());


        LOGGER.info("====================================");
        LOGGER.info("copyPos : "+ posFuncVO.getStoreCd());
        LOGGER.info("copyPos : "+ posFuncVO.getCopyPos());
        LOGGER.info("targetPos : "+ posFuncVO.getTargetPos());

        procCnt += mapper.deletePosFunc(posFuncVO);
        procCnt += mapper.copyPosFunc(posFuncVO);

        return procCnt;
    }

    /** 포스기능 인증목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosConfAuthDetail(PosFuncVO posFuncVO) {
        return mapper.getPosConfAuthDetail(posFuncVO);
    }

    /** 포스기능 인증허용대상 조회 */
    @Override public List<DefaultMap<String>> getAuthEmpList(PosFuncVO posFuncVO) {
        return mapper.getAuthEmpList(posFuncVO);
    }

    /** 포스기능 인증허용대상 저장 */
    @Override public int saveAuthEmp(PosFuncVO[] posFuncVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PosFuncVO posFuncVO : posFuncVOs) {

            posFuncVO.setRegDt(dt);
            posFuncVO.setRegId(sessionInfoVO.getUserId());
            posFuncVO.setModDt(dt);
            posFuncVO.setModId(sessionInfoVO.getUserId());

            procCnt += mapper.saveAuthEmp(posFuncVO);
        }
        return procCnt;
    }
}
