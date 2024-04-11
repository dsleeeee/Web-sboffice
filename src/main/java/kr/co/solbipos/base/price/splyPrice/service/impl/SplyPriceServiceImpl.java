package kr.co.solbipos.base.price.splyPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.splyPrice.service.SplyPriceService;
import kr.co.solbipos.base.price.splyPrice.service.SplyPriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SplyPriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.04  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("splyPriceService")
public class SplyPriceServiceImpl implements SplyPriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SplyPriceMapper splyPriceMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SplyPriceServiceImpl(SplyPriceMapper splyPriceMapper, PopupMapper popupMapper) {
        this.splyPriceMapper = splyPriceMapper;
        this.popupMapper = popupMapper;
    }

    /** 본사 공급가관리 조회*/
    @Override
    public List<DefaultMap<String>> getHqSplyPriceList(SplyPriceVO splyPriceVO, SessionInfoVO sessionInfoVO) {

        splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        splyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (splyPriceVO.getProdHqBrandCd() == "" || splyPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (splyPriceVO.getUserProdBrands() != null && !"".equals(splyPriceVO.getUserProdBrands())) {
                    String[] userBrandList = splyPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        splyPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return splyPriceMapper.getHqSplyPriceList(splyPriceVO);
    }

    /** 본사 공급가관리 엑셀다운로드 조회*/
    @Override
    public List<DefaultMap<String>> getHqSplyPriceExcelList(SplyPriceVO splyPriceVO, SessionInfoVO sessionInfoVO) {

        splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        splyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (splyPriceVO.getProdHqBrandCd() == "" || splyPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (splyPriceVO.getUserProdBrands() != null && !"".equals(splyPriceVO.getUserProdBrands())) {
                    String[] userBrandList = splyPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        splyPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return splyPriceMapper.getHqSplyPriceExcelList(splyPriceVO);
    }

    /** 본사 공급가 저장 */
    @Override
    public int saveHqSplyPrice(SplyPriceVO[] splyPriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (SplyPriceVO splyPriceVO : splyPriceVOs) {

            splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            splyPriceVO.setRegDt(currentDt);
            splyPriceVO.setRegId(sessionInfoVO.getUserId());
            splyPriceVO.setModDt(currentDt);
            splyPriceVO.setModId(sessionInfoVO.getUserId());

            // 본사 공급가 변경
            result = splyPriceMapper.saveHqSplyPrice(splyPriceVO);

            // all - 전매장적용(판매가변경제한매장 미포함)
            // none - 미적용
            // tot - 전매장적용(판매가변경제한매장 포함)
            // choice - 선택한 매장만 적용
            if (splyPriceVO.getApplyFg().equals("all") || splyPriceVO.getApplyFg().equals("tot") || splyPriceVO.getApplyFg().equals("choice")) {

                if (splyPriceVO.getApplyFg().equals("choice")) {
                    if (!StringUtil.getOrBlank(splyPriceVO.getSaveStoreCds()).equals("")) {
                        StoreVO storeVO = new StoreVO();
                        storeVO.setArrSplitStoreCd(CmmUtil.splitText(splyPriceVO.getSaveStoreCds(), 3900));
                        splyPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
                    }
                }

                // 본사 공급가 변경에 따른 매장 공급가 변경
                splyPriceMapper.saveStoreSplyPrice(splyPriceVO);
            }
        }
        return result;
    }

    /** 본사 공급가 엑셀 양식다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getHqSplyPriceExcelUploadSampleList(SplyPriceVO splyPriceVO, SessionInfoVO sessionInfoVO) {

        splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        splyPriceVO.setUserId(sessionInfoVO.getUserId());

        return splyPriceMapper.getHqSplyPriceExcelUploadSampleList(splyPriceVO);
    }

    /** 공급가 업로드 임시테이블 전체 삭제 */
    @Override
    public int deleteSplyPriceExcelUploadCheckAll(SplyPriceVO splyPriceVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        splyPriceVO.setSessionId(sessionInfoVO.getSessionId());
        splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        procCnt += splyPriceMapper.deleteSplyPriceExcelUploadCheckAll(splyPriceVO);

        return procCnt;
    }

    /** 공급가 업로드 임시테이블 삭제 */
    @Override
    public int deleteSplyPriceExcelUploadCheck(SplyPriceVO[] splyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for (SplyPriceVO splyPriceVO : splyPriceVOs) {
            splyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt += splyPriceMapper.deleteSplyPriceExcelUploadCheck(splyPriceVO);
        }

        return procCnt;
    }

    /** 공급가 업로드 임시테이블 저장 */
    @Override
    public int saveSplyPriceExcelUploadCheck(SplyPriceVO[] splyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for (SplyPriceVO splyPriceVO : splyPriceVOs) {
            splyPriceVO.setRegDt(currentDt);
            splyPriceVO.setRegId(sessionInfoVO.getUserId());
            splyPriceVO.setModDt(currentDt);
            splyPriceVO.setModId(sessionInfoVO.getUserId());

            splyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            splyPriceVO.setSeq(i);

            splyPriceVO.setResult("검증전");

            procCnt += splyPriceMapper.saveSplyPriceExcelUploadCheck(splyPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 공급가 업로드 임시테이블 데이터 조회 */
    @Override
    public List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(SplyPriceVO splyPriceVO, SessionInfoVO sessionInfoVO) {

        splyPriceVO.setSessionId(sessionInfoVO.getSessionId());
        splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return splyPriceMapper.getSplyPriceExcelUploadCheckList(splyPriceVO);
    }

    /** 공급가 검증결과 저장 */
    @Override
    public int saveSplyPriceExcelUploadCheckResult(SplyPriceVO[] splyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();
        String pattern = "^[0-9]*$"; //숫자만

        for (SplyPriceVO splyPriceVO : splyPriceVOs) {
            splyPriceVO.setRegDt(currentDt);
            splyPriceVO.setRegId(sessionInfoVO.getUserId());
            splyPriceVO.setModDt(currentDt);
            splyPriceVO.setModId(sessionInfoVO.getUserId());

            splyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            splyPriceVO.setSeq(i);

            // 공급가
            if (splyPriceVO.getSplyUprc() != null && !"".equals(splyPriceVO.getSplyUprc())) {
                if (Pattern.matches(pattern, splyPriceVO.getSplyUprc())) {
                    splyPriceVO.setSplyUprc(splyPriceVO.getSplyUprc());
                } else {
                    splyPriceVO.setResult("공급가는 숫자만 입력가능합니다.");
                }
            }

            // 상품코드 존재여부 체크
            if (splyPriceMapper.getProdCdChk(splyPriceVO) > 0) {
            } else {
                splyPriceVO.setResult("존재하지 않는 상품코드입니다");
            }

            if (splyPriceVO.getResult() == null || splyPriceVO.getResult() == "") {
                splyPriceVO.setResult("검증성공");
            }

            procCnt += splyPriceMapper.saveSplyPriceExcelUploadCheck(splyPriceVO);
            i++;
        }

        return procCnt;
    }

    /**
     * 공급가 엑셀업로드 저장
     */
    @Override
    public int saveHqSplyPriceExcelUpload(SplyPriceVO[] splyPriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (SplyPriceVO splyPriceVO : splyPriceVOs) {

            splyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            splyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            splyPriceVO.setRegDt(currentDt);
            splyPriceVO.setRegId(sessionInfoVO.getUserId());
            splyPriceVO.setModDt(currentDt);
            splyPriceVO.setModId(sessionInfoVO.getUserId());

            if (("검증성공").equals(splyPriceVO.getResult())) {

                // 본사 판매가 변경
                result = splyPriceMapper.saveHqSplyPrice(splyPriceVO);

                // all - 전매장적용(판매가변경제한매장 미포함)
                // none - 미적용
                // tot - 전매장적용(판매가변경제한매장 포함)
                // choice - 선택한 매장만 적용
                if (splyPriceVO.getApplyFg().equals("all") || splyPriceVO.getApplyFg().equals("tot") || splyPriceVO.getApplyFg().equals("choice")) {

                    if (splyPriceVO.getApplyFg().equals("choice")) {
                        if (!StringUtil.getOrBlank(splyPriceVO.getSaveStoreCds()).equals("")) {
                            StoreVO storeVO = new StoreVO();
                            storeVO.setArrSplitStoreCd(CmmUtil.splitText(splyPriceVO.getSaveStoreCds(), 3900));
                            splyPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
                        }
                    }

                    // 본사 공급가 변경에 따른 매장 공급가 변경
                    splyPriceMapper.saveStoreSplyPrice(splyPriceVO);
                }

                // 저장완료된 검증결과만 삭제
                result += splyPriceMapper.deleteSplyPriceExcelUploadCheck(splyPriceVO);

            }
        }

        return result;
    }
}
