package kr.co.solbipos.sys.etc.vanManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.etc.vanManage.service.VanManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : VanManageMapper.java
 * @Description : 시스템관리 > Van/Card사 관리 > 밴사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.13  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface VanManageMapper {

    /** 밴사정보관리 - 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdList(VanManageVO vanManageVo);

    /** 밴사정보관리 - 상세조회 */
    List<DefaultMap<String>> getVanManageList(VanManageVO vanManageVo);

    /**  밴사정보관리 - 밴사정보 저장 */
    int getVanSave(VanManageVO vanManageVO);

    /** 밴사정보관리 - 밴사정보 삭제 */
    int getVanDelete(VanManageVO vanManageVO);
}
