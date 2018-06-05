package kr.co.solbipos.store.service.hq.hqbrand;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import kr.co.solbipos.store.domain.hq.hqbrand.HqBrandVO;
import kr.co.solbipos.store.domain.hq.hqbrand.HqEnvstVO;
import kr.co.solbipos.store.enums.TargtFg;
import kr.co.solbipos.store.persistence.hq.hqbrand.HqBrandMapper;

@Service
public class HqBrandServiceImpl implements HqBrandService{

    @Autowired
    HqBrandMapper mapper;

    @Override
    public List<DefaultMap<String>> list(HqBrandVO hqBrand) {
        return mapper.list(hqBrand);
    }
    
    @Override
    public int save(HqBrandVO[] hqBrandVOs, SessionInfoVO sessionInfoVO) {
        
        int procCnt = 0;
        String dt = currentDateTimeString();

        for(HqBrandVO hqBrandVO: hqBrandVOs) {
            
            hqBrandVO.setRegDt(dt);
            hqBrandVO.setRegId(sessionInfoVO.getUserId());
            hqBrandVO.setModDt(dt);
            hqBrandVO.setModId(sessionInfoVO.getUserId());
            
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
