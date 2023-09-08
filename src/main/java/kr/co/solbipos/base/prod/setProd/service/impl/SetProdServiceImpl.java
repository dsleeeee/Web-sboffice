package kr.co.solbipos.base.prod.setProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.setProd.service.SetProdService;
import kr.co.solbipos.base.prod.setProd.service.SetProdVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuSelGroupVO;
import kr.co.solbipos.base.prod.sidemenu.service.impl.SideMenuMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SetProdServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 세트메뉴구성(BBQ전용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.04  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.09.04
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("setProdService")
public class SetProdServiceImpl implements SetProdService {

    private final SetProdMapper setProdMapper;
    private final SideMenuMapper sideMenuMapper;

    public SetProdServiceImpl(SetProdMapper setProdMapper, SideMenuMapper sideMenuMapper) {
        this.setProdMapper = setProdMapper;
        this.sideMenuMapper = sideMenuMapper;
    }

    // 상품조회
    @Override
    public List<DefaultMap<String>> getProdList(SetProdVO setProdVO, SessionInfoVO sessionInfoVO) {
        setProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        setProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        setProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        return setProdMapper.getProdList(setProdVO);
    }

    // 상품 사이드상품여부 저장
    @Override
    public int saveSideProdYn(SetProdVO[] setProdVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( SetProdVO setProdVO : setProdVOs ) {
            setProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            setProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            setProdVO.setStoreCd(sessionInfoVO.getStoreCd());

            setProdVO.setRegDt(currentDt);
            setProdVO.setRegId(sessionInfoVO.getUserId());
            setProdVO.setModDt(currentDt);
            setProdVO.setModId(sessionInfoVO.getUserId());

            if(setProdVO.getSideProdYn().equals("Y")){
                // 사이드그룹코드가 없으면 생성
                if(setProdVO.getSdselGrpCd() == null || (!setProdVO.getSdselTypeFg().equals(setProdVO.getGubun()))) {
                    SideMenuSelGroupVO sideMenuSelGroupVO = new SideMenuSelGroupVO();
                    sideMenuSelGroupVO.setOrgnFg(setProdVO.getOrgnFg());
                    sideMenuSelGroupVO.setHqOfficeCd(setProdVO.getHqOfficeCd());
                    sideMenuSelGroupVO.setStoreCd(setProdVO.getStoreCd());
                    if(setProdVO.getProdNm().getBytes(StandardCharsets.UTF_8).length > 50){
                        sideMenuSelGroupVO.setSdselGrpNm(setProdVO.getProdNm().substring(0, 16));
                    } else {
                        sideMenuSelGroupVO.setSdselGrpNm(setProdVO.getProdNm());
                    }
                    sideMenuSelGroupVO.setFixProdFg("0");
                    sideMenuSelGroupVO.setSdselTypeFg(setProdVO.getGubun());

                    sideMenuSelGroupVO.setRegDt(currentDt);
                    sideMenuSelGroupVO.setRegId(sessionInfoVO.getUserId());
                    sideMenuSelGroupVO.setModDt(currentDt);
                    sideMenuSelGroupVO.setModId(sessionInfoVO.getUserId());

                    // 그룹코드 생성
                    sideMenuSelGroupVO.setSdselGrpCd(sideMenuMapper.getMenuGrpCode(sideMenuSelGroupVO));

                    result += sideMenuMapper.insertMenuGrpList(sideMenuSelGroupVO);
                    // TODO :: 그룹추가시 매장에 자동으로 내려줄것인가? : 20181231 노현수
                    // 본사에서 접속시
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                        sideMenuMapper.insertHqMenuGrpListToStore(sideMenuSelGroupVO);
                    }
                    setProdVO.setSdselGrpCd(sideMenuSelGroupVO.getSdselGrpCd());
                }
            }
            // prod테이블 side_prod_yn 을 변경
            result += setProdMapper.saveSideProdYn(setProdVO);
        }

        return result;
    }

    @Override
    public List<DefaultMap<String>> getSdselGrpList(SetProdVO setProdVO, SessionInfoVO sessionInfoVO) {
        setProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        setProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        setProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        return setProdMapper.getSdselGrpList(setProdVO);
    }
}
