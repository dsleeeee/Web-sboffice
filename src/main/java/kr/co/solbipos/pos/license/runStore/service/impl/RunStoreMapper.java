package kr.co.solbipos.pos.license.runStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.license.runStore.service.RunStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : RunStoreMapper.java
 * @Description : 포스관리 > 라이선스 관리 > 런닝매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.11  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface RunStoreMapper {

    /** 런닝매출현황 탭 - 조회 */
    List<DefaultMap<Object>> getRunStoreList(RunStoreVO runStoreVO);

    /** 런닝COPY수 - 런닝/신규/폐점 매장 수 조회 */
    DefaultMap<String> getRunCopyCnt(RunStoreVO runStoreVO);

    /** 런닝COPY수 탭 - 조회 */
    List<DefaultMap<Object>> getRunCopyList(RunStoreVO runStoreVO);

    /** 런닝COPY수 탭 - 조회 */
    List<DefaultMap<Object>> getRunTrnsitnList(RunStoreVO runStoreVO);
}
