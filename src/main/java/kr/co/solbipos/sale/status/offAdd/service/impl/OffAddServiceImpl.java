package kr.co.solbipos.sale.status.offAdd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.offAdd.service.OffAddService;
import kr.co.solbipos.sale.status.offAdd.service.OffAddVO;
import kr.co.solbipos.sale.status.side.service.impl.SideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : OffAddServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 오프라인추가매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.14  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("offAddService")
public class OffAddServiceImpl implements OffAddService {
    private final OffAddMapper offAddMapper;
    private final MessageService messageService;

    @Autowired
    public OffAddServiceImpl(OffAddMapper offAddMapper, MessageService messageService) {
        this.offAddMapper = offAddMapper;
        this.messageService = messageService;
    }

    /** 일별 조회 */
    @Override
    public List<DefaultMap<String>> getOffAddDayList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO) {

        offAddVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        offAddVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        offAddVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(offAddVO.getStoreCd()).equals("")) {
            offAddVO.setArrStoreCd(offAddVO.getStoreCd().split(","));
        }

        return offAddMapper.getOffAddDayList(offAddVO);
    }

    /** 일별상세 조회 */
    @Override
    public List<DefaultMap<String>> getOffAddDayDetailList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO) {

        offAddVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        offAddVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        offAddVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(offAddVO.getStoreCd()).equals("")) {
            offAddVO.setArrStoreCd(offAddVO.getStoreCd().split(","));
        }

        return offAddMapper.getOffAddDayDetailList(offAddVO);
    }

    /** 월별 조회 */
    @Override
    public List<DefaultMap<String>> getOffAddMonthList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO) {

        offAddVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        offAddVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        offAddVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(offAddVO.getStoreCd()).equals("")) {
            offAddVO.setArrStoreCd(offAddVO.getStoreCd().split(","));
        }

        return offAddMapper.getOffAddMonthList(offAddVO);
    }

    /** 월별상세 조회 */
    @Override
    public List<DefaultMap<String>> getOffAddMonthDetailList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO) {

        offAddVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        offAddVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        offAddVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(offAddVO.getStoreCd()).equals("")) {
            offAddVO.setArrStoreCd(offAddVO.getStoreCd().split(","));
        }

        return offAddMapper.getOffAddMonthDetailList(offAddVO);
    }

    /** 상품별 조회 */
    @Override
    public List<DefaultMap<String>> getOffAddProdList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO) {

        offAddVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        offAddVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        offAddVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(offAddVO.getStoreCd()).equals("")) {
            offAddVO.setArrStoreCd(offAddVO.getStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        offAddVO.setArrPayCol(offAddVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = offAddVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        offAddVO.setPivotPayCol(pivotPayCol);

        return offAddMapper.getOffAddProdList(offAddVO);
    }

    /** 상품별 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getOffAddProdExcelList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO) {

        offAddVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        offAddVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        offAddVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(offAddVO.getStoreCd()).equals("")) {
            offAddVO.setArrStoreCd(offAddVO.getStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        offAddVO.setArrPayCol(offAddVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = offAddVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        offAddVO.setPivotPayCol(pivotPayCol);

        return offAddMapper.getOffAddProdExcelList(offAddVO);
    }

}