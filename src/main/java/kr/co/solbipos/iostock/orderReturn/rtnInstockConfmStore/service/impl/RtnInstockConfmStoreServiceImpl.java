package kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service.RtnInstockConfmStoreService;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service.RtnInstockConfmStoreVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rtnInstockConfmStoreService")
public class RtnInstockConfmStoreServiceImpl implements RtnInstockConfmStoreService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RtnInstockConfmStoreMapper rtnInstockConfmStoreMapper;

    public RtnInstockConfmStoreServiceImpl(RtnInstockConfmStoreMapper rtnInstockConfmStoreMapper) {
        this.rtnInstockConfmStoreMapper = rtnInstockConfmStoreMapper;
    }

    /** 반품본사입고현황 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnInstockConfmStoreList(RtnInstockConfmStoreVO rtnInstockConfmStoreVO, SessionInfoVO sessionInfoVO){

        rtnInstockConfmStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        rtnInstockConfmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        rtnInstockConfmStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        return rtnInstockConfmStoreMapper.getRtnInstockConfmStoreList(rtnInstockConfmStoreVO);
    }

    /** 반품본사입고현황 - 전표 상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(RtnInstockConfmStoreVO rtnInstockConfmStoreVO, SessionInfoVO sessionInfoVO){

        rtnInstockConfmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return rtnInstockConfmStoreMapper.getSlipNoInfo(rtnInstockConfmStoreVO);
    }

    /** 반품본사입고현황 - 전표 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnInstockConfmStoreDtlList(RtnInstockConfmStoreVO rtnInstockConfmStoreVO, SessionInfoVO sessionInfoVO){

        rtnInstockConfmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return rtnInstockConfmStoreMapper.getRtnInstockConfmStoreDtlList(rtnInstockConfmStoreVO);
    }

}
