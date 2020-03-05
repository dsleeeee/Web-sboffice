package kr.co.solbipos.base.prod.corner.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.corner.service.CornerService;
import kr.co.solbipos.base.prod.corner.service.CornerVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import kr.co.solbipos.base.prod.prod.service.impl.ProdMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("cornerService")
public class CornerServiceImpl implements CornerService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CornerMapper cornerMapper;

    /** Constructor Injection */
    @Autowired
    public CornerServiceImpl(CornerMapper cornerMapper) {
        this.cornerMapper = cornerMapper;
    }

    /** 상품별 코너변경 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdCornerList(@RequestBody CornerVO cornerVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        cornerVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        cornerVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        cornerVO.setStoreCd(sessionInfoVO.getStoreCd());

        return cornerMapper.getProdCornerList(cornerVO);
    }

    /** 상품별 코너 이동 */
    @Override
    public int changeProdCorner(CornerVO[] cornerVOs, SessionInfoVO sessionInfoVO) {

        CornerVO cornerVO = new CornerVO();
        String dt = currentDateTimeString();
        String str = "";

        cornerVO.setCornrCd(cornerVOs[0].getCornrCd());
        cornerVO.setStoreCd(cornerVOs[0].getStoreCd());
        cornerVO.setModDt(dt);
        cornerVO.setModId(sessionInfoVO.getUserId());

        for(CornerVO item : cornerVOs) {
            str += item.getProdCd() + ",";
        }

        cornerVO.setArrProdCd(str.substring(0, str.length() - 1).split(","));

        return cornerMapper.changeProdCorner(cornerVO);

    }
}
