package kr.co.solbipos.store.hq.brand.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.hq.brand.service.HqBrandService;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : HqBrandServiceImpl.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("hqBrandService")
public class HqBrandServiceImpl implements HqBrandService{

    private final HqBrandMapper mapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public HqBrandServiceImpl(HqBrandMapper mapper, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
    }

    /**  브랜드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getBrandlist(HqBrandVO hqBrandVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> list = null;
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            list = mapper.getHqBrandlist(hqBrandVO);
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            list = mapper.getMsBrandlist(hqBrandVO);
        }

        return list;

    }

    /** 브랜드 저장 */
    @Override
    public int save(HqBrandVO[] hqBrandVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(HqBrandVO hqBrandVO: hqBrandVOs) {
            hqBrandVO.setRegDt(dt);
            hqBrandVO.setRegId(sessionInfoVO.getUserId());
            hqBrandVO.setModDt(dt);
            hqBrandVO.setModId(sessionInfoVO.getUserId());
            hqBrandVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            /*if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                List<DefaultMap<String>> storeList = mapper.getStoreList(hqBrandVO);
                String[] arrStoreCd = storeList.toString().replace(" ", "").replace("[", "").replace("]", "").split(",");
                hqBrandVO.setArrStoreCd(arrStoreCd);
            }*/

            if(hqBrandVO.getStatus() == GridDataFg.INSERT) {

                // 본사는 브랜드코드 직접입력, 매장은 자동채번
                /*if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    hqBrandVO.setHqBrandCd(mapper.getHqBrandCd(hqBrandVO));
                }*/
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    hqBrandVO.setMsBrandCd(mapper.getHqBrandCd(hqBrandVO));
                }

                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procCnt += mapper.insertHqBrand(hqBrandVO);
                    //mapper.insertHqMsBrand(hqBrandVO);
                } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    procCnt += mapper.insertMsBrand(hqBrandVO);
                }
            }
            else if(hqBrandVO.getStatus() == GridDataFg.UPDATE) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procCnt += mapper.updateHqBrand(hqBrandVO);
                    //mapper.updateHqMsBrand(hqBrandVO);
                } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    procCnt += mapper.updateMsBrand(hqBrandVO);
                }
            }
        }
        return procCnt;
    }

    /** 본사 브랜드코드 중복체크 */
    @Override
    public String chkHqBrandCd(HqBrandVO hqBrand, SessionInfoVO sessionInfoVO) {

        hqBrand.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqBrand.setArrHqBrandCd(hqBrand.getHqBrandCd().split(","));
        return mapper.chkHqBrandCd(hqBrand);
    }

    /** 환경설정 조회 */
    @Override
    public List<DefaultMap<String>> getConfigList(HqBrandVO hqBrand) {
        return mapper.getConfigList(hqBrand);
    }

    @Override
    public int saveConfig(HqEnvstVO[] hqEnvsts, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(HqEnvstVO hqEnvst: hqEnvsts) {

            hqEnvst.setRegDt(dt);
            hqEnvst.setRegId(sessionInfoVO.getUserId());
            hqEnvst.setModDt(dt);
            hqEnvst.setModId(sessionInfoVO.getUserId());

            if(hqEnvst.getStatus() == GridDataFg.INSERT) {
                procCnt += mapper.insertConfig(hqEnvst);
            }
            else if(hqEnvst.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateConfig(hqEnvst);
            }

            // 적용 타겟이 본사기준 매장까지인 경우, 매장까지 적용
            // 단독매장 제외(프렌차이즈만 해당)
            if(!"00000".equals(hqEnvst.getHqOfficeCd()) && "X".equals(hqEnvst.getTargtFg())) {
                procCnt += mapper.updateConfigStore(hqEnvst);
            }
        }
        return procCnt;
    }
}
