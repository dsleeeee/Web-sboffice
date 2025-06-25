package kr.co.common.service.treePopup.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.treePopup.TreePopupService;
import kr.co.common.service.treePopup.TreePopupVO;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name  : TreePopupServiceImpl.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("treePopupService")
@Transactional
public class TreePopupServiceImpl implements TreePopupService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final TreePopupMapper treePopupMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public TreePopupServiceImpl(TreePopupMapper treePopupMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.treePopupMapper = treePopupMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }


    @Override
    public List<TreePopupVO> getProdClassTree3(TreePopupVO treePopupVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if (treePopupVO.getPageFg() != null && treePopupVO.getPageFg().equals("1")) {
            treePopupVO.setOrgnFg("S");
        } else {
            treePopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            treePopupVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        treePopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = null;

        if (sessionInfoVO.getHqOfficeCd().equals("A0001") || sessionInfoVO.getHqOfficeCd().equals("A0007")) {
            // (A0001, A0007 본사/하위매장은 기존 상품분류 테이블 사용)
            list = treePopupMapper.getProdClassTreeArtisee(treePopupVO);
        } else {
            list = treePopupMapper.getProdClassTree3(treePopupVO);
        }

        return makeTreeData(list);
    }


    @Override
    public String getProdClassCdNm(TreePopupVO treePopupVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if (treePopupVO.getPageFg() != null && treePopupVO.getPageFg().equals("1")) {
            treePopupVO.setOrgnFg("S");
        } else {
            treePopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            treePopupVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        treePopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return treePopupMapper.getProdClassCdNm(treePopupVO);
    }

    /**
     * 트리 데이터 생성
     */
    public List<TreePopupVO> makeTreeData(List<DefaultMap<String>> lists) {

        List<TreePopupVO> treePopupVOs = new ArrayList<TreePopupVO>();

        for (DefaultMap<String> list : lists) {
            TreePopupVO treePopupVO = new TreePopupVO();
            treePopupVO.setProdClassCd(list.getStr("prodClassCd"));
            treePopupVO.setpProdClassCd(list.getStr("pProdClassCd"));
            treePopupVO.setProdClassNm(list.getStr("prodClassNm"));
            treePopupVO.setItems(new ArrayList<TreePopupVO>());
            treePopupVOs.add(treePopupVO);
        }

        Map<String, TreePopupVO> hm = new LinkedHashMap<String, TreePopupVO>();

        TreePopupVO child;
        TreePopupVO parent;

        for (TreePopupVO treePopupVO : treePopupVOs) {
            if (!hm.containsKey(treePopupVO.getProdClassCd())) {
                hm.put(treePopupVO.getProdClassCd(), treePopupVO);
            }

            child = hm.get(treePopupVO.getProdClassCd());

            if (child != null && !"".equals(treePopupVO.getpProdClassCd()) && !treePopupVO.getpProdClassCd().equals("00000")) {
                if (hm.containsKey(treePopupVO.getpProdClassCd())) {
                    parent = hm.get(treePopupVO.getpProdClassCd());
                    parent.getItems().add(child);
                }
            }
        }

        List<TreePopupVO> returnData = new ArrayList<TreePopupVO>();
        for (TreePopupVO treePopupVO : hm.values()) {
            if (treePopupVO.getpProdClassCd() == null || "".equals(treePopupVO.getpProdClassCd()) || treePopupVO.getpProdClassCd().equals("00000")) {
                returnData.add(treePopupVO);
            }
        }

        return returnData;
    }

}