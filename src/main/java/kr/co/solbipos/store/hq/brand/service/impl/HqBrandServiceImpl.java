package kr.co.solbipos.store.hq.brand.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.enums.TargtFg;
import kr.co.solbipos.store.hq.brand.service.HqBrandService;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
import kr.co.solbipos.store.hq.brand.service.HqClsVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;

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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("hqBrandService")
public class HqBrandServiceImpl implements HqBrandService{

    @Autowired
    HqBrandMapper mapper;

    @Autowired
    MessageService messageService;

    /**  브랜드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getBrandlist(HqBrandVO hqBrand) {
        return mapper.getBrandlist(hqBrand);
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

            String hqBrandCd = mapper.getHqBrandCd(hqBrandVO);

            hqBrandVO.setHqBrandCd(hqBrandCd);

            if(hqBrandVO.getStatus() == GridDataFg.INSERT) {
                procCnt += mapper.insertBrand(hqBrandVO);
            }
            else if(hqBrandVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateBrand(hqBrandVO);
            }
            else if(hqBrandVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteBrand(hqBrandVO);
            }
        }
        return procCnt;
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
                hqEnvst.setUseYn(UseYn.Y);
                procCnt += mapper.insertConfig(hqEnvst);
            }
            else if(hqEnvst.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateConfig(hqEnvst);
            }

            // 적용 타겟이 본사기준 매장까지인 경우, 매장까지 적용
            // 단독매장 제외(프렌차이즈만 해당)
            if("00000".equals(hqEnvst.getHqOfficeCd()) && hqEnvst.getTargtFg() == TargtFg.BOTH ) {
                procCnt += mapper.updateConfigStore(hqEnvst);
            }
        }
        return procCnt;
    }
}
