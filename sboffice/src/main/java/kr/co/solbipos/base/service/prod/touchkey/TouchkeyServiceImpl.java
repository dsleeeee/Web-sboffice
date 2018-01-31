package kr.co.solbipos.base.service.prod.touchkey;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.base.persistence.prod.touchkey.TouchkeyMapper;
import kr.co.solbipos.structure.DefaultMap;

@Service
public class TouchkeyServiceImpl implements TouchkeyService {

    @Autowired
    private TouchkeyMapper mapper;

    @Override
    public <E> List<E> selectTouchkey(DefaultMap<String> param) {
        return mapper.selectTouchkey(param);
    }

}
