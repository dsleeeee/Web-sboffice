package kr.co.solbipos.base.store.bizHour.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.bizHour.service.BizHourService;
import kr.co.solbipos.base.store.bizHour.service.BizHourVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BizHourServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 매장영업시간관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.01  김유승      최초생성
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("bizHourService")
@Transactional
public class BizHourServiceImpl implements BizHourService {

    private final BizHourMapper bizHourMapper;
    private final PopupMapper popupMapper;

    @Autowired
    MessageService messageService;

    public BizHourServiceImpl(BizHourMapper bizHourMapper, PopupMapper popupMapper) {
        this.bizHourMapper = bizHourMapper;
        this.popupMapper = popupMapper;
    }

    /** 요일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDaysList(BizHourVO bizHourVO) {
        if (!StringUtil.getOrBlank(bizHourVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(bizHourVO.getStoreCd(), 3900));
            bizHourVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return bizHourMapper.getDaysList(bizHourVO);
    }

    /** 요일별 신규 등록 */
    @Override
    public int saveNewDays(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 사용자는 자기 매장만 등록 가능하도록 세션 매장으로 고정(본사는 매장 선택 등록 허용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        bizHourVO.setRegId(sessionInfoVO.getUserId());
        bizHourVO.setRegDt(currentDt);
        bizHourVO.setModId(sessionInfoVO.getUserId());
        bizHourVO.setModDt(currentDt);

        // 시간중복 체크 (신규: 자기 자신 제외 없음)
        bizHourVO.setSeq(null);
        if (bizHourMapper.getDaysOverlapCnt(bizHourVO) > 0) {
            throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
        }

        bizHourVO.setSeq(bizHourMapper.getDaysMaxSeq(bizHourVO));
        int result = bizHourMapper.insertDays(bizHourVO);

        if (result > 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 요일별 수정 */
    @Override
    public int saveDays(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        String currentDt = currentDateTimeString();

        // 세션/일시 세팅
        for (BizHourVO bizHourVO : bizHourVOs) {
            bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 매장 사용자는 자기 매장만 수정 가능하도록 세션 매장으로 고정(본사는 매장 선택 수정 허용)
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            bizHourVO.setModId(sessionInfoVO.getUserId());
            bizHourVO.setModDt(currentDt);
        }

        // 시간중복 체크 (최종상태 기준 : 편집행끼리는 메모리 비교, 미편집 DB행은 편집 seq 전체 제외하고 비교)
        for (int i = 0; i < bizHourVOs.length; i++) {
            BizHourVO cur = bizHourVOs[i];
            List<Integer> grpEditSeqs = new ArrayList<>();
            for (int j = 0; j < bizHourVOs.length; j++) {
                BizHourVO other = bizHourVOs[j];
                if (isSameDayGroup(cur, other)) {
                    grpEditSeqs.add(other.getSeq());
                    // 같은 그룹의 다른 편집행과 새 값끼리 겹침 검사
                    if (i != j && isTimeOverlap(cur, other)) {
                        throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
                    }
                }
            }
            // 미편집 DB행과 겹침 검사 (편집 대상 seq 전체 제외)
            cur.setArrExcludeSeq(grpEditSeqs.toArray(new Integer[0]));
            if (bizHourMapper.getDaysOverlapCnt(cur) > 0) {
                throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
            }
        }

        // 수정
        for (BizHourVO bizHourVO : bizHourVOs) {
            returnResult += bizHourMapper.updateDays(bizHourVO);
        }

        if (returnResult == bizHourVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 요일별 삭제 */
    @Override
    public int deleteDays(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;

        for (BizHourVO bizHourVO : bizHourVOs) {
            bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 매장 사용자는 자기 매장만 삭제 가능하도록 세션 매장으로 고정(본사는 매장 선택 삭제 허용)
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            returnResult += bizHourMapper.deleteDays(bizHourVO);
        }

        if (returnResult == bizHourVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 특정일 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSpecificList(BizHourVO bizHourVO) {
        if (!StringUtil.getOrBlank(bizHourVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(bizHourVO.getStoreCd(), 3900));
            bizHourVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return bizHourMapper.getSpecificList(bizHourVO);
    }

    /** 특정일 신규 등록 */
    @Override
    public int saveNewSpecific(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 사용자는 자기 매장만 등록 가능하도록 세션 매장으로 고정(본사는 매장 선택 등록 허용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        bizHourVO.setRegId(sessionInfoVO.getUserId());
        bizHourVO.setRegDt(currentDt);
        bizHourVO.setModId(sessionInfoVO.getUserId());
        bizHourVO.setModDt(currentDt);

        // 시간중복 체크 (신규: 자기 자신 제외 없음, 영업/휴게 구분 무관 전체)
        bizHourVO.setSeq(null);
        if (bizHourMapper.getSpecificOverlapCnt(bizHourVO) > 0) {
            throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
        }

        bizHourVO.setSeq(bizHourMapper.getSpecificMaxSeq(bizHourVO));
        int result = bizHourMapper.insertSpecific(bizHourVO);

        if (result > 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 특정일 수정 */
    @Override
    public int saveSpecific(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        String currentDt = currentDateTimeString();

        // 세션/일시 세팅
        for (BizHourVO bizHourVO : bizHourVOs) {
            bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 매장 사용자는 자기 매장만 수정 가능하도록 세션 매장으로 고정(본사는 매장 선택 수정 허용)
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            bizHourVO.setModId(sessionInfoVO.getUserId());
            bizHourVO.setModDt(currentDt);
        }

        // 시간중복 체크 (최종상태 기준, 영업/휴게 구분 무관 전체 : 편집행끼리 메모리 비교 + 미편집 DB행은 편집 seq 전체 제외)
        for (int i = 0; i < bizHourVOs.length; i++) {
            BizHourVO cur = bizHourVOs[i];
            List<Integer> grpEditSeqs = new ArrayList<>();
            for (int j = 0; j < bizHourVOs.length; j++) {
                BizHourVO other = bizHourVOs[j];
                if (isSameSpecGroup(cur, other)) {
                    grpEditSeqs.add(other.getSeq());
                    if (i != j && isTimeOverlap(cur, other)) {
                        throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
                    }
                }
            }
            cur.setArrExcludeSeq(grpEditSeqs.toArray(new Integer[0]));
            if (bizHourMapper.getSpecificOverlapCnt(cur) > 0) {
                throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
            }
        }

        // 수정
        for (BizHourVO bizHourVO : bizHourVOs) {
            returnResult += bizHourMapper.updateSpecific(bizHourVO);
        }

        if (returnResult == bizHourVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 특정일 삭제 */
    @Override
    public int deleteSpecific(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;

        for (BizHourVO bizHourVO : bizHourVOs) {
            bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 매장 사용자는 자기 매장만 삭제 가능하도록 세션 매장으로 고정(본사는 매장 선택 삭제 허용)
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            returnResult += bizHourMapper.deleteSpecific(bizHourVO);
        }

        if (returnResult == bizHourVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 요일별 같은 그룹(매장+요일) 여부 */
    private boolean isSameDayGroup(BizHourVO a, BizHourVO b) {
        return StringUtil.getOrBlank(a.getStoreCd()).equals(StringUtil.getOrBlank(b.getStoreCd()))
            && StringUtil.getOrBlank(a.getDayFg()).equals(StringUtil.getOrBlank(b.getDayFg()));
    }

    /** 특정일 같은 그룹(매장+일자) 여부 */
    private boolean isSameSpecGroup(BizHourVO a, BizHourVO b) {
        return StringUtil.getOrBlank(a.getStoreCd()).equals(StringUtil.getOrBlank(b.getStoreCd()))
            && StringUtil.getOrBlank(a.getBizDate()).equals(StringUtil.getOrBlank(b.getBizDate()));
    }

    /** 시간구간 겹침 여부 (HHMM 문자열, 시작<종료 전제) */
    private boolean isTimeOverlap(BizHourVO a, BizHourVO b) {
        String as = a.getStartTime(), ae = a.getEndTime();
        String bs = b.getStartTime(), be = b.getEndTime();
        if (as == null || ae == null || bs == null || be == null) {
            return false;
        }
        return as.compareTo(be) < 0 && ae.compareTo(bs) > 0;
    }
}
