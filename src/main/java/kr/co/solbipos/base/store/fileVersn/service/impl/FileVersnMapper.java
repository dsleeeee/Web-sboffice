package kr.co.solbipos.base.store.fileVersn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.fileVersn.service.FileVersnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : FileVersnMapper.java
 * @Description : 맘스터치 > 기타관리 > 점포별 파일 버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.17  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface FileVersnMapper {

    /** 조회 */
    List<DefaultMap<Object>> getFileVersnList(FileVersnVO fileVersnVO);
    List<DefaultMap<Object>> getFileVersnExcelList(FileVersnVO fileVersnVO);
}