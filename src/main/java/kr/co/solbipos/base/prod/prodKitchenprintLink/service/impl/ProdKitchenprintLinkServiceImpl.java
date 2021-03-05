package kr.co.solbipos.base.prod.prodKitchenprintLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkService;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkVO;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdKitchenprintLinkServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 상품-매장주방프린터연결
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.09  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.02.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodKitchenprintLinkService")
@Transactional
public class ProdKitchenprintLinkServiceImpl implements ProdKitchenprintLinkService {
    private final ProdKitchenprintLinkMapper prodKitchenprintLinkMapper; // 상품엑셀업로드

    private final String SYS_CLOSURE_DATE = "99991231"; // 시스템 종료일

    public ProdKitchenprintLinkServiceImpl(ProdKitchenprintLinkMapper prodKitchenprintLinkMapper) {
        this.prodKitchenprintLinkMapper = prodKitchenprintLinkMapper;
    }

    /** 상품목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {

        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();

        prodKitchenprintLinkVO.setHqOfficeCd(hqOfficeCd);

        return prodKitchenprintLinkMapper.getProdList(prodKitchenprintLinkVO);
    }

    @Override
    public List<DefaultMap<String>> getLinkedList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        prodKitchenprintLinkVO.setHqOfficeCd(hqOfficeCd);

        // 매장상태구분
        if(SysStatFg.CLOSE == prodKitchenprintLinkVO.getSysStatFg()) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else  if(SysStatFg.DEMO == prodKitchenprintLinkVO.getSysStatFg() ) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else {
            prodKitchenprintLinkVO.setSysClosureDate(currentDateString());
        }

        // 매장검색
        if(!StringUtil.getOrBlank(prodKitchenprintLinkVO.getStoreCd()).equals("")) {
            prodKitchenprintLinkVO.setArrStoreCd(prodKitchenprintLinkVO.getStoreCd().split(","));
        }

        return prodKitchenprintLinkMapper.getLinkedList(prodKitchenprintLinkVO);
    }

    @Override
    public int unlinkPrter(ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        for(ProdKitchenprintLinkVO Unlink : prodKitchenprintLinkVO){
            result += prodKitchenprintLinkMapper.unlinkPrter(Unlink);
        }
        return result;
    }

    @Override
    public List<DefaultMap<String>> getUnlinkList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        prodKitchenprintLinkVO.setHqOfficeCd(hqOfficeCd);

        // 매장상태구분
        if(SysStatFg.CLOSE == prodKitchenprintLinkVO.getSysStatFg()) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else  if(SysStatFg.DEMO == prodKitchenprintLinkVO.getSysStatFg() ) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else {
            prodKitchenprintLinkVO.setSysClosureDate(currentDateString());
        }

        // 매장검색
        if(!StringUtil.getOrBlank(prodKitchenprintLinkVO.getStoreCd()).equals("")) {
            prodKitchenprintLinkVO.setArrStoreCd(prodKitchenprintLinkVO.getStoreCd().split(","));
        }

        return prodKitchenprintLinkMapper.getUnlinkList(prodKitchenprintLinkVO);
    }

    @Override
    public int linkedPrter(ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
        for(ProdKitchenprintLinkVO Linked : prodKitchenprintLinkVO){

            Linked.setRegDt(dt);
            Linked.setRegId(sessionInfoVO.getUserId());
            Linked.setModDt(dt);
            Linked.setModId(sessionInfoVO.getUserId());

            result += prodKitchenprintLinkMapper.linkedPrter(Linked);
        }
        return result;
    }

}