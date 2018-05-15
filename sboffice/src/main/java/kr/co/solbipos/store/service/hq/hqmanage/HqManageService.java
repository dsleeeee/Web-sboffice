package kr.co.solbipos.store.service.hq.hqmanage;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.domain.hq.hqmanage.HqManageVO;

/**
 * 가맹점관리 > 본사정보 > 본사정보관리
 * 
 * @author 김지은
 */
public interface HqManageService {
    
    List<DefaultMap<String>> selectList(HqManageVO hqManave);
}
