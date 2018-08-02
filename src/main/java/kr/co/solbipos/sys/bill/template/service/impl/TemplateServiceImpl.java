package kr.co.solbipos.sys.bill.template.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.bill.template.service.TemplateService;
import kr.co.solbipos.sys.bill.template.service.TemplateVO;

/**
 * @Class Name : TemplateService.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("templateService")
public class TemplateServiceImpl implements TemplateService {
    
    @Autowired
    TemplateMapper templateMapper;
    
    /** 출력물종류 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintTypeList(TemplateVO templateVO) {
        return templateMapper.getPrintTypeList(templateVO);
    }
    
    /** 출력물코드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintCodeList(TemplateVO templateVO) {
        return templateMapper.getPrintCodeList(templateVO);
    }
    /** 출력물템플릿 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTemplateList(TemplateVO templateVO) {
        return templateMapper.getTemplateList(templateVO);
    }
    
}
