package kr.co.solbipos.base.prod.menuRank.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.corner.service.CornerVO;
import kr.co.solbipos.base.prod.menuRank.service.MenuRankService;
import kr.co.solbipos.base.prod.menuRank.service.MenuRankVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.hq.brand.enums.TargtFg;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("menuRankService")
public class MenuRankServiceImpl implements MenuRankService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MenuRankMapper menuRankMapper;
    private final MessageService messageService;

    @Autowired
    public MenuRankServiceImpl(MenuRankMapper menuRankMapper,  MessageService messageService) {
        this.menuRankMapper = menuRankMapper;
        this.messageService = messageService;
    }

    /** 메뉴 순위 표시 관리 조회 */
    @Override
    public List<DefaultMap<String>> getRankInfo(@RequestBody MenuRankVO menuRankVO, SessionInfoVO sessionInfoVO) {

        // 권한에 따라 조회하는 데이터가 다름.
        if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            menuRankVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            return menuRankMapper.getRankCnt(menuRankVO);
        }else{
            menuRankVO.setStoreCd(sessionInfoVO.getStoreCd());
            return menuRankMapper.getRankUse(menuRankVO);
        }
    }

    /** 메뉴 순위 표시 사용/미사용 매장 조회 */
    @Override
    public List<DefaultMap<String>> getRegStore(@RequestBody MenuRankVO menuRankVO, SessionInfoVO sessionInfoVO) {

        menuRankVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return menuRankMapper.getRegStore(menuRankVO);
    }

    /** 메뉴 순위 표시 미사용 처리 */
    @Override
    public int deleteStore(MenuRankVO[] menuRanks, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(MenuRankVO menuRank: menuRanks) {

            menuRank.setInsFg(sessionInfoVO.getOrgnFg().getCode());
            menuRank.setModDt(dt);
            menuRank.setModId(sessionInfoVO.getUserId());

            int result = menuRankMapper.deleteStore(menuRank);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                procCnt += result;
            }
        }
        return procCnt;
    }

    /** 메뉴 순위 표시 사용 처리 */
    @Override
    public int insertStore(MenuRankVO[] menuRanks, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(MenuRankVO menuRank: menuRanks) {

            menuRank.setInsFg(sessionInfoVO.getOrgnFg().getCode());
            menuRank.setRegDt(dt);
            menuRank.setRegId(sessionInfoVO.getUserId());
            menuRank.setModDt(dt);
            menuRank.setModId(sessionInfoVO.getUserId());

            int result = menuRankMapper.saveRankUse(menuRank);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                procCnt += result;
            }
        }
        return procCnt;
    }

    /** 메뉴 순위 표시 관리 저장 */
    @Override
    public int saveRankUse(MenuRankVO[] menuRanks, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(MenuRankVO menuRank: menuRanks) {

            menuRank.setStoreCd(sessionInfoVO.getStoreCd());
            menuRank.setInsFg(sessionInfoVO.getOrgnFg().getCode());
            menuRank.setRegDt(dt);
            menuRank.setRegId(sessionInfoVO.getUserId());
            menuRank.setModDt(dt);
            menuRank.setModId(sessionInfoVO.getUserId());

            int result = menuRankMapper.saveRankUse(menuRank);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                procCnt += result;
            }
        }
        return procCnt;
    }

}
