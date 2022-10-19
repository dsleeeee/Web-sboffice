package kr.co.solbipos.iostock.cmm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmm.service.IostockCmmService;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iostockCmmService")
public class IostockCmmServiceImpl implements IostockCmmService {
    private final IostockCmmMapper iostockCmmMapper;
    private final MessageService messageService;

    @Autowired
    public IostockCmmServiceImpl(IostockCmmMapper iostockCmmMapper, MessageService messageService) {
        this.iostockCmmMapper = iostockCmmMapper;
        this.messageService = messageService;
    }

    /** 수불&재고관련 공통 - 매장선택 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectStoreList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        iostockCmmVO.setEmpNo(sessionInfoVO.getEmpNo());
        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return iostockCmmMapper.selectStoreList(iostockCmmVO);
    }

    /** 수불&재고관련 공통 - 매장선택 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectStoreMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        iostockCmmVO.setEmpNo(sessionInfoVO.getEmpNo());
        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return iostockCmmMapper.selectStoreMomsList(iostockCmmVO);
    }

    /** 수불&재고관련 공통 - 상품선택 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectProdMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        iostockCmmVO.setEmpNo(sessionInfoVO.getEmpNo());
        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(iostockCmmVO.getOrgnFg() == OrgnFg.STORE.getCode()){
            iostockCmmVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return iostockCmmMapper.selectProdMomsList(iostockCmmVO);
    }

    /** 수불&재고관련 공통 - 매장선택 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectBrandMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        iostockCmmVO.setEmpNo(sessionInfoVO.getEmpNo());
        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return iostockCmmMapper.selectBrandMomsList(iostockCmmVO);
    }

    /** 수불&재고관련 공통 - 거래처 선택모듈 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        iostockCmmVO.setStoreCd(sessionInfoVO.getStoreCd());
        iostockCmmVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return iostockCmmMapper.getVendrList(iostockCmmVO);
    }

    /** 수불&재고관련 공통 - 창고선택 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectStorageList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        iostockCmmVO.setStoreCd(sessionInfoVO.getStoreCd());
        iostockCmmVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        return iostockCmmMapper.selectStorageList(iostockCmmVO);
    }


    /** 수불&재고관련 공통 - 공통 명칭 콤보조회 */
    @Override
    public List<DefaultMap<String>> selectCmmCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        return iostockCmmMapper.selectCmmCodeList(iostockCmmVO);
    }


    /** 수불&재고관련 공통 - 본사 명칭 콤보조회 */
    @Override
    public List<DefaultMap<String>> selectHqCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return iostockCmmMapper.selectHqCodeList(iostockCmmVO);
    }


    /** 수불&재고관련 공통 - 매장 명칭 콤보조회 */
    @Override
    public List<DefaultMap<String>> selectStoreCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        iostockCmmVO.setStoreCd(sessionInfoVO.getStoreCd());
        return iostockCmmMapper.selectStoreCodeList(iostockCmmVO);
    }


    /** 수불&재고관련 공통 - 본사/매장 명칭 콤보조회 (본사인 경우 본사, 매장인 경우 매장의 명칭 콤보조회) */
    @Override
    public List<DefaultMap<String>> selectOrgnCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = iostockCmmMapper.selectHqCodeList(iostockCmmVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            iostockCmmVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = iostockCmmMapper.selectStoreCodeList(iostockCmmVO);
        }

        return result;
    }


    /** 수불&재고관련 공통 - 다이나믹 콤보조회 */
    @Override
    public List<DefaultMap<String>> selectDynamicCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {
        return iostockCmmMapper.selectDynamicCodeList(iostockCmmVO);
    }
}
