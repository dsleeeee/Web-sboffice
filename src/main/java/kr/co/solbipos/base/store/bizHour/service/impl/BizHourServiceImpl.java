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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /** 요일별 리스트 조회 (피벗 : 매장×요일 매트릭스) */
    @Override
    public List<DefaultMap<String>> getDaysPivotList(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 사용자는 자기 매장만 조회(본사는 매장 선택 조회 허용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        if (!StringUtil.getOrBlank(bizHourVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(bizHourVO.getStoreCd(), 3900));
            bizHourVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return bizHourMapper.getDaysPivotList(bizHourVO);
    }

    /** 요일별 리스트 조회 (매장 : 요일별 한 줄씩, 일~토 기본 표시) */
    @Override
    public List<DefaultMap<String>> getDaysStoreList(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 사용자는 자기 매장 고정(본사가 호출하면 전달된 storeCd 사용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return bizHourMapper.getDaysStoreList(bizHourVO);
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

    /** 요일별 수정 (매트릭스 저장 : 수정된 매장 통째 삭제 후 재삽입) */
    @Override
    public int saveDays(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();

        // 세션 세팅 + 수정 매장 수집(빈칸으로 비운 매장도 포함 : delete-all 대상)
        Set<String> storeCds = new LinkedHashSet<>();
        for (BizHourVO vo : bizHourVOs) {
            vo.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 매장 사용자는 자기 매장만(본사는 선택 매장 허용)
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                vo.setStoreCd(sessionInfoVO.getStoreCd());
            }
            vo.setRegId(sessionInfoVO.getUserId());
            vo.setRegDt(currentDt);
            vo.setModId(sessionInfoVO.getUserId());
            vo.setModDt(currentDt);
            storeCds.add(vo.getStoreCd());
        }

        // 저장 대상(시작/종료 모두 채워진 셀)만 추출 + 유효성 검증
        List<BizHourVO> validList = new ArrayList<>();
        for (BizHourVO vo : bizHourVOs) {
            boolean hasStart = !StringUtil.getOrBlank(vo.getStartTime()).equals("");
            boolean hasEnd   = !StringUtil.getOrBlank(vo.getEndTime()).equals("");
            // 빈칸(둘 다 없음) = 삭제이므로 재삽입 안 함
            if (!hasStart && !hasEnd) {
                continue;
            }
            // 한쪽만 입력 = 오류
            if (!hasStart || !hasEnd) {
                throw new JsonException(Status.FAIL, messageService.get("bizHour.invalidTime"));
            }
            // 시작 < 종료
            if (vo.getStartTime().compareTo(vo.getEndTime()) >= 0) {
                throw new JsonException(Status.FAIL, messageService.get("bizHour.invalidTime"));
            }
            validList.add(vo);
        }

        // 같은 (매장+요일) 내 시간 겹침 검증
        for (int i = 0; i < validList.size(); i++) {
            for (int j = i + 1; j < validList.size(); j++) {
                BizHourVO a = validList.get(i);
                BizHourVO b = validList.get(j);
                if (isSameDayGroup(a, b) && isTimeOverlap(a, b)) {
                    throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
                }
            }
        }

        // 수정 매장 통째 삭제
        for (String storeCd : storeCds) {
            BizHourVO del = new BizHourVO();
            del.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            del.setStoreCd(storeCd);
            bizHourMapper.deleteDaysByStore(del);
        }

        // 재삽입 (SEQ는 매장+요일별 1부터 채번)
        int returnResult = 0;
        Map<String, Integer> seqMap = new HashMap<>();
        for (BizHourVO vo : validList) {
            String key = vo.getStoreCd() + "|" + vo.getDayFg();
            int seq = seqMap.containsKey(key) ? seqMap.get(key) + 1 : 1;
            seqMap.put(key, seq);
            vo.setSeq(seq);
            returnResult += bizHourMapper.insertDays(vo);
        }

        return returnResult;
    }

    /** 요일별 수정 (매장 : 행 단위 — 시간O+seqO=수정, 시간O+seqX=등록, 시간X+seqO=삭제) */
    @Override
    public int saveDaysStore(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();

        // 세션/일시 세팅 (매장 사용자는 자기 매장 고정)
        for (BizHourVO vo : bizHourVOs) {
            vo.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                vo.setStoreCd(sessionInfoVO.getStoreCd());
            }
            vo.setRegId(sessionInfoVO.getUserId());
            vo.setRegDt(currentDt);
            vo.setModId(sessionInfoVO.getUserId());
            vo.setModDt(currentDt);
        }

        // 시간 채워진 행 유효성 + 겹침 검증 (전체 행이 전달되므로 메모리 비교)
        List<BizHourVO> filled = new ArrayList<>();
        for (BizHourVO vo : bizHourVOs) {
            boolean hasStart = !StringUtil.getOrBlank(vo.getStartTime()).equals("");
            boolean hasEnd   = !StringUtil.getOrBlank(vo.getEndTime()).equals("");
            if (!hasStart && !hasEnd) {
                continue;   // 빈칸(삭제 또는 빈 기본행)
            }
            if (!hasStart || !hasEnd) {
                throw new JsonException(Status.FAIL, messageService.get("bizHour.invalidTime"));
            }
            if (vo.getStartTime().compareTo(vo.getEndTime()) >= 0) {
                throw new JsonException(Status.FAIL, messageService.get("bizHour.invalidTime"));
            }
            filled.add(vo);
        }
        for (int i = 0; i < filled.size(); i++) {
            for (int j = i + 1; j < filled.size(); j++) {
                if (isSameDayGroup(filled.get(i), filled.get(j)) && isTimeOverlap(filled.get(i), filled.get(j))) {
                    throw new JsonException(Status.FAIL, messageService.get("bizHour.overlapTime"));
                }
            }
        }

        // 적용 : 시간O+seqO=수정 / 시간O+seqX=등록 / 시간X+seqO=삭제
        int returnResult = 0;
        for (BizHourVO vo : bizHourVOs) {
            boolean hasTime = !StringUtil.getOrBlank(vo.getStartTime()).equals("")
                           && !StringUtil.getOrBlank(vo.getEndTime()).equals("");
            boolean hasSeq  = vo.getSeq() != null;
            if (hasTime) {
                if (hasSeq) {
                    returnResult += bizHourMapper.updateDays(vo);
                } else {
                    vo.setSeq(bizHourMapper.getDaysMaxSeq(vo));
                    returnResult += bizHourMapper.insertDays(vo);
                }
            } else if (hasSeq) {
                returnResult += bizHourMapper.deleteDays(vo);   // 기존 행 비움 = 삭제
            }
        }
        return returnResult;
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

    /** 영업시간 복사 (원본 매장 → 복사대상 매장들, 요일별+특정일, 타겟 삭제 후 교체) */
    @Override
    public int bizHourCopy(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        // 영업시간 복사는 본사(HQ) 전용 기능 (매장 사용자의 직접 호출 차단)
        if (sessionInfoVO.getOrgnFg() != OrgnFg.HQ) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }

        String currentDt = currentDateTimeString();
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        bizHourVO.setModId(sessionInfoVO.getUserId());
        bizHourVO.setModDt(currentDt);

        // 원본/복사대상 매장 필수
        if (StringUtil.getOrBlank(bizHourVO.getStoreCd()).equals("")) {
            throw new JsonException(Status.FAIL, messageService.get("bizHour.require.selectStore"));
        }
        if (StringUtil.getOrBlank(bizHourVO.getCopyStoreCd()).equals("")) {
            throw new JsonException(Status.FAIL, messageService.get("bizHour.require.selectCopyStore"));
        }

        // 복사대상(타겟)에 원본 매장이 포함되면 차단 : 타겟을 먼저 전체삭제하므로 원본이 섞이면 원본 데이터가 유실됨
        for (String target : bizHourVO.getCopyStoreCd().split(",")) {
            if (bizHourVO.getStoreCd().equals(target.trim())) {
                throw new JsonException(Status.FAIL, messageService.get("bizHour.duplicate.targetStore"));
            }
        }

        // 복사대상(타겟) 매장 멀티 → IN 쿼리 문자열
        StoreVO storeVO = new StoreVO();
        storeVO.setArrSplitStoreCd(CmmUtil.splitText(bizHourVO.getCopyStoreCd(), 3900));
        bizHourVO.setCopyStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));

        // 요일별 : 타겟 전체삭제 후 원본 복사
        bizHourMapper.deleteDaysByCopyStore(bizHourVO);
        int result = bizHourMapper.copyDaysToStore(bizHourVO);

        // 특정일 : 타겟 전체삭제 후 원본 복사
        bizHourMapper.deleteSpecificByCopyStore(bizHourVO);
        result += bizHourMapper.copySpecificToStore(bizHourVO);

        return result;
    }

    /** 매장영업시간관리 사용여부 조회 (매장 자기 값) */
    @Override
    public String getStoreUseYn(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        // 매장 사용자는 자기 매장 고정
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return bizHourMapper.getStoreUseYn(bizHourVO);
    }

    /** 매장영업시간관리 사용여부 매장목록 조회 (본사 팝업 전용) */
    @Override
    public List<DefaultMap<String>> getStoreUseYnList(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        if (sessionInfoVO.getOrgnFg() != OrgnFg.HQ) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }
        bizHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return bizHourMapper.getStoreUseYnList(bizHourVO);
    }

    /** 매장영업시간관리 사용여부 저장 (매장 : 자기 매장) */
    @Override
    public int saveStoreUseYn(BizHourVO bizHourVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();
        // 매장 사용자는 자기 매장 고정
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            bizHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        bizHourVO.setRegId(sessionInfoVO.getUserId());
        bizHourVO.setRegDt(currentDt);
        bizHourVO.setModId(sessionInfoVO.getUserId());
        bizHourVO.setModDt(currentDt);
        return bizHourMapper.saveStoreUseYn(bizHourVO);
    }

    /** 매장영업시간관리 사용여부 일괄 저장 (본사 팝업 전용) */
    @Override
    public int saveStoreUseYnList(BizHourVO[] bizHourVOs, SessionInfoVO sessionInfoVO) {
        if (sessionInfoVO.getOrgnFg() != OrgnFg.HQ) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }
        String currentDt = currentDateTimeString();
        int result = 0;
        for (BizHourVO vo : bizHourVOs) {
            vo.setRegId(sessionInfoVO.getUserId());
            vo.setRegDt(currentDt);
            vo.setModId(sessionInfoVO.getUserId());
            vo.setModDt(currentDt);
            result += bizHourMapper.saveStoreUseYn(vo);
        }
        return result;
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
