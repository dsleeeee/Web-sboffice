package kr.co.common.service.treePopupTwo.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.treePopup.TreePopupVO;
import kr.co.common.service.treePopupTwo.TreePopupTwoService;
import kr.co.common.service.treePopupTwo.TreePopupTwoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * @Class Name  : TreePopupTwoServiceImpl.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.27  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("treePopupTwoService")
@Transactional
public class TreePopupTwoServiceImpl implements TreePopupTwoService {

    private final TreePopupTwoMapper treePopupTwoMapper;

    @Autowired
    public TreePopupTwoServiceImpl(TreePopupTwoMapper treePopupTwoMapper) {
        this.treePopupTwoMapper = treePopupTwoMapper;
    }

    /** 상품정보 분류 트리(체크박스) 조회2 */
    @Override
    public List<TreePopupTwoVO> getProdClassTreeCheck2(TreePopupTwoVO treePopupTwoVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if (treePopupTwoVO.getPageFg() != null && treePopupTwoVO.getPageFg().equals("1")) {
            treePopupTwoVO.setOrgnFg("S");
        } else {
            treePopupTwoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            treePopupTwoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        treePopupTwoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = null;

        if (sessionInfoVO.getHqOfficeCd().equals("A0001") || sessionInfoVO.getHqOfficeCd().equals("A0007")) {
            // (A0001, A0007 본사/하위매장은 기존 상품분류 테이블 사용)
            list = treePopupTwoMapper.getProdClassTreeArtiseeCheck2(treePopupTwoVO);
        } else {
            list = treePopupTwoMapper.getProdClassTreeCheck2(treePopupTwoVO);
        }

        return makeTreeData(list);
    }

    /** 상품분류 플랫 조회2 */
    @Override
    public String getProdClassCdNmCheck2(TreePopupTwoVO treePopupTwoVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        if (treePopupTwoVO.getPageFg() != null && treePopupTwoVO.getPageFg().equals("1")) {
            treePopupTwoVO.setOrgnFg("S");
        } else {
            treePopupTwoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            treePopupTwoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        treePopupTwoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return treePopupTwoMapper.getProdClassCdNmCheck2(treePopupTwoVO);
    }

    /**
     * 트리 데이터 생성
     */
    public List<TreePopupTwoVO> makeTreeData(List<DefaultMap<String>> lists) {

        List<TreePopupTwoVO> treePopupTwoVOs = new ArrayList<TreePopupTwoVO>();

        for (DefaultMap<String> list : lists) {
            TreePopupTwoVO treePopupTwoVO = new TreePopupTwoVO();
            treePopupTwoVO.setProdClassCd(list.getStr("prodClassCd"));
            treePopupTwoVO.setpProdClassCd(list.getStr("pProdClassCd"));
            treePopupTwoVO.setProdClassNm(list.getStr("prodClassNm"));
            treePopupTwoVO.setItems(new ArrayList<TreePopupTwoVO>());
            treePopupTwoVOs.add(treePopupTwoVO);
        }

        Map<String, TreePopupTwoVO> hm = new LinkedHashMap<String, TreePopupTwoVO>();

        TreePopupTwoVO child;
        TreePopupTwoVO parent;

        for (TreePopupTwoVO treePopupTwoVO : treePopupTwoVOs) {
            if (!hm.containsKey(treePopupTwoVO.getProdClassCd())) {
                hm.put(treePopupTwoVO.getProdClassCd(), treePopupTwoVO);
            }

            child = hm.get(treePopupTwoVO.getProdClassCd());

            if (child != null && !"".equals(treePopupTwoVO.getpProdClassCd()) && !treePopupTwoVO.getpProdClassCd().equals("00000")) {
                if (hm.containsKey(treePopupTwoVO.getpProdClassCd())) {
                    parent = hm.get(treePopupTwoVO.getpProdClassCd());
                    parent.getItems().add(child);
                }
            }
        }


        List<TreePopupTwoVO> returnData = new ArrayList<TreePopupTwoVO>();
        for (TreePopupTwoVO treePopupTwoVO : hm.values()) {
            if (treePopupTwoVO.getpProdClassCd() == null || "".equals(treePopupTwoVO.getpProdClassCd()) || treePopupTwoVO.getpProdClassCd().equals("00000")) {
                returnData.add(treePopupTwoVO);
            }
        }

        return returnData;
    }
}
