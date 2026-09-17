package kr.co.solbipos.adi.etc.cd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.cd.service.CdService;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CdServiceImpl.java
 * @Description : 부가서비스 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.13  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cdService")
public class CdServiceImpl implements CdService {

    // Constructor Injection
    private final CdMapper cdMapper;
    private final MessageService messageService;

    /** 생성자 주입 */
    @Autowired
    public CdServiceImpl(MessageService messageService, CdMapper cdMapper) {
        this.messageService = messageService;
        this.cdMapper = cdMapper;
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdList(CdVO cdVO) {
        return cdMapper.getNmcodeGrpCdList(cdVO);
    }
    
    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeCdList(CdVO cdVO) {
        return cdMapper.getNmcodeCdList(cdVO);
    }
    
    /** 코드목록 저장 */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveNmcodeCdList(CdVO[] cdVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdVO cdVO : cdVOs ) {

            cdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                cdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                cdVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            cdVO.setRegDt(currentDt);
            cdVO.setRegId(sessionInfoVO.getUserId());
            cdVO.setModDt(currentDt);
            cdVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( cdVO.getStatus() == GridDataFg.INSERT ) {
                // 동일 그룹/코드 존재 시 명시적 실패 처리 (중복 등록 방지)
                if ( cdMapper.getNmcodeCdDupCnt(cdVO) > 0 ) {
                    throw new JsonException(Status.SERVER_ERROR, "[" + cdVO.getNmcodeCd() + "] " + messageService.get("cd.dupNmcodeCd"));
                }

                try {
                    result += cdMapper.insertNmcodeCdList(cdVO);
                } catch (DuplicateKeyException e) {
                    // 사전 체크와 INSERT 사이에 동시 등록된 경우
                    throw new JsonException(Status.SERVER_ERROR, "[" + cdVO.getNmcodeCd() + "] " + messageService.get("cd.dupNmcodeCd"));
                }
                // 수정
            } else if ( cdVO.getStatus() == GridDataFg.UPDATE ) {
                result += cdMapper.updateNmcodeCdList(cdVO);
                // 삭제
            } else if ( cdVO.getStatus() == GridDataFg.DELETE ) {
                result += cdMapper.deleteNmcodeCdList(cdVO);
            }

        }

        if ( result == cdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 본사권한 공통코드 매장수정 허용 - 대표명칭(공통) 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCdStoreAllowGrpList(CdVO cdVO) {
        return cdMapper.getCdStoreAllowGrpList(cdVO);
    }

    /** 본사권한 공통코드 매장수정 허용 - 매장목록/설정 조회 */
    @Override
    public List<DefaultMap<String>> getCdStoreAllowList(CdVO cdVO) {
        return cdMapper.getCdStoreAllowList(cdVO);
    }

    /** 본사권한 공통코드 매장수정 허용 - 저장 */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveCdStoreAllowList(CdVO[] cdVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdVO cdVO : cdVOs ) {

            // 허용설정은 항상 본사(TB_HQ_NMCODE) 기준으로 저장
            cdVO.setOrgnFg(OrgnFg.HQ.getCode());
            cdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            cdVO.setNmcodeGrpCd("251");
            cdVO.setUseYn("Y");
            cdVO.setRegDt(currentDt);
            cdVO.setRegId(sessionInfoVO.getUserId());
            cdVO.setModDt(currentDt);
            cdVO.setModId(sessionInfoVO.getUserId());

            // 허용액션(NMCODE_ITEM_2) 조립 : 추가(I)/수정(U)/삭제(D) 허용여부(Y/N) 기준
            String allowItem = "";
            if ( "Y".equals(cdVO.getInsYn()) ) { allowItem += "I"; }
            if ( "Y".equals(cdVO.getUpdYn()) ) { allowItem += "U"; }
            if ( "Y".equals(cdVO.getDelYn()) ) { allowItem += "D"; }
            cdVO.setNmcodeItem2(allowItem);

            // 추가
            if ( cdVO.getStatus() == GridDataFg.INSERT ) {
                // 동일 그룹/매장 설정 존재 시 명시적 실패 처리 (동시 등록에 의한 중복 방지)
                if ( cdMapper.getCdStoreAllowDupCnt(cdVO) > 0 ) {
                    throw new JsonException(Status.SERVER_ERROR, messageService.get("cd.storeAllow.dupExist"));
                }
                // 코드 MAX+1 채번
                cdVO.setNmcodeCd(cdMapper.getCdStoreAllowMaxCd(cdVO));
                try {
                    result += cdMapper.insertNmcodeCdList(cdVO);
                } catch (DuplicateKeyException e) {
                    // 사전 체크와 INSERT 사이에 동시 등록된 경우
                    throw new JsonException(Status.SERVER_ERROR, messageService.get("cd.storeAllow.dupExist"));
                }
                // 수정
            } else if ( cdVO.getStatus() == GridDataFg.UPDATE ) {
                result += cdMapper.updateNmcodeCdList(cdVO);
                // 삭제
            } else if ( cdVO.getStatus() == GridDataFg.DELETE ) {
                result += cdMapper.deleteNmcodeCdList(cdVO);
            }

        }

        if ( result == cdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 본사권한 공통코드 매장수정 허용 - 매장 허용값 조회 */
    @Override
    public DefaultMap<String> getCdStoreAllowItem(CdVO cdVO) {
        return cdMapper.getCdStoreAllowItem(cdVO);
    }

}
