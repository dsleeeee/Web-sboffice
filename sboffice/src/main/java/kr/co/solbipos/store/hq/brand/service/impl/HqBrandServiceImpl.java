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

@Service
public class HqBrandServiceImpl implements HqBrandService{

    @Autowired
    HqBrandMapper mapper;

    @Autowired
    MessageService messageService;
    
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

    @Override
    public List<HqClsVO> getClsList(HqBrandVO hqBrand) {
        
        List<DefaultMap<String>> clsList = mapper.getClsList(hqBrand);
        
        return makeTreeData(clsList);
    }
    
    public List<HqClsVO> makeTreeData(List<DefaultMap<String>> lists) {
        
        List<HqClsVO> clsVOs = new ArrayList<HqClsVO>();
        
        for(DefaultMap<String> list : lists){
            HqClsVO clsVO = new HqClsVO();
            clsVO.setHqBrandCd(list.getStr("hqBrandCd"));
            clsVO.setProdClassCd(list.getStr("prodClassCd"));
            clsVO.setProdClassNm(list.getStr("prodClassNm"));
            clsVO.setPProdClassCd(list.getStr("pProdClassCd"));
            clsVO.setItems(new ArrayList<HqClsVO>());
            clsVOs.add(clsVO);
        }
        
        Map<String, HqClsVO> hm = new LinkedHashMap<String, HqClsVO>();
        HqClsVO child;
        HqClsVO parent;
        
        for(HqClsVO clsVO : clsVOs){
            if(!hm.containsKey(clsVO.getProdClassCd())) {
                hm.put(clsVO.getProdClassCd(), clsVO);
            }
            
            child = hm.get(clsVO.getProdClassCd());
            if ( child != null && !"".equals( clsVO.getPProdClassCd() ) && !"000000".equals( clsVO.getPProdClassCd() ) ) {
                if(hm.containsKey(clsVO.getPProdClassCd())){
                    parent = hm.get(clsVO.getPProdClassCd());
                    parent.getItems().add(child);
                }
            }
        }
        List<HqClsVO> returnData = new ArrayList<HqClsVO>();
        for(HqClsVO clsVO : hm.values()) {
            if(clsVO.getPProdClassCd() == null || "".equals(clsVO.getPProdClassCd()) || "00000".equals(clsVO.getPProdClassCd())) {
                returnData.add(clsVO);
            } 
        }
       
        return returnData;
    }

    @Override
    public int clsSave(HqClsVO[] hqClsVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();
        
        for(HqClsVO hqClsVO : hqClsVOs) {
            
            hqClsVO.setRegDt(dt);
            hqClsVO.setModDt(dt);
            hqClsVO.setRegId(sessionInfoVO.getUserId());
            hqClsVO.setModId(sessionInfoVO.getUserId());
            
            // 새 분류코드 생성시, 분류코드 조회부터
            if("".equals(hqClsVO.getProdClassCd()) || hqClsVO.getProdClassCd() == null) {
                String prodClassCd = "";
                prodClassCd = mapper.getClsCd(hqClsVO);
                hqClsVO.setProdClassCd(prodClassCd);
            }
            // 상위 분류까지 신규로 만들 경우 상위 분류코드 조회
            if("".equals(hqClsVO.getPProdClassCd()) || hqClsVO.getPProdClassCd() == null) {
                String pprodClassCd = "";
                pprodClassCd = mapper.getPProdClsCd(hqClsVO);
                hqClsVO.setPProdClassCd(pprodClassCd);
            }
            
            if(hqClsVO.getStatus() == GridDataFg.INSERT) {
                procCnt += mapper.insertCls(hqClsVO);
            }
            else if(hqClsVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateCls(hqClsVO);
            }
            else if(hqClsVO.getStatus() == GridDataFg.DELETE) {
                
                // 해당 분류로 상품이 등록되어있으면 삭제 불가능
                int chkProdCnt = mapper.chkProdCnt(hqClsVO);
                
                if(chkProdCnt > 0) {
                    throw new JsonException(Status.FAIL, messageService.get("hqBrand.delete.fail"));
                }
                else {
                    procCnt += mapper.deleteCls(hqClsVO);
                }
            }
        }
        
        if(procCnt == hqClsVOs.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
