package kr.co.solbipos.application.service.com;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.com.BkmkVO;
import kr.co.solbipos.application.persistence.com.BkmkMapper;

@Service
public class BkmkServiceImpl implements BkmkService {

    @Autowired
    BkmkMapper bkmkMapper;

    @Override
    public int saveBkmk( BkmkVO bkmkVO, String userId ) {

        bkmkVO.setUserId( userId );
        bkmkVO.setUseYn( "Y" );
        bkmkVO.setRegDt( currentDateTimeString() );
        bkmkVO.setRegId( userId );

        return bkmkMapper.saveBkmk( bkmkVO );
    }

}
