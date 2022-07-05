package kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service;

import java.io.File;

/**
 * @Class Name : ApiTemplateImageVO.java
 * @Description : NHN API 호출(템플릿 이미지 등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.06.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ApiTemplateImageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 이미지파일 */
    private File file;

    public File getFile() { return file; }

    public void setFile(File file) { this.file = file; }
}