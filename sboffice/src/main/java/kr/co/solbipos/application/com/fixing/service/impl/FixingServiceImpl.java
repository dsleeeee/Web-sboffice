package kr.co.solbipos.application.com.fixing.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.com.fixing.service.FixingService;
import kr.co.solbipos.application.com.fixing.service.FixingVO;

@Service
public class FixingServiceImpl implements FixingService {

    @Autowired
    FixingMapper fixingMapper;

    @Override
    public int saveFixing(FixingVO fixingVO, String userId) {

        int result = 0;

        fixingVO.setUserId(userId);
        fixingVO.setRegDt(currentDateTimeString());
        fixingVO.setRegId(userId);
        // 삭제후 재등록
        if ( fixingMapper.deleteFixing(fixingVO) > -1 ) {
            if ( fixingVO.getResrceCds() != null && fixingVO.getResrceCds().length > 0 ) {
                result = fixingMapper.insertFixing(fixingVO);
            }
        }
        return result;
    }

}
