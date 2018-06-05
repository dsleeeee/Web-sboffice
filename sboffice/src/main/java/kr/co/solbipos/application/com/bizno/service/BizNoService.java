package kr.co.solbipos.application.com.bizno.service;

/**
 * 사업자번호 유효성검사
 *
 * @author 노현수
 */
public interface BizNoService {

    /** 사업자번호 유효성검사 */
    boolean bizNoVerify(BizNoVO bizNoVO);

}
