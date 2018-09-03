package kr.co.solbipos.iostock.order.outstockReqDate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateService;
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OutstockReqDateService")
public class OutstockReqDateServiceImpl implements OutstockReqDateService {
    @Autowired
    OutstockReqDateMapper outstockReqDateMapper;

    /** 출고요청일관리 요일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDaysList(OutstockReqDateVO outstockReqDateVO) {
        return outstockReqDateMapper.getDaysList(outstockReqDateVO);
    }
}
