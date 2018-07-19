package kr.co.solbipos.application.common.service;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 메뉴 Tree 구조<br>
 * 
 * @author 조병준
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVO {

    /** 메뉴명 */
    private String nm;

    /** URL */
    private String url;

    /** 메뉴 아이콘명 */
    private String icon;

    /** 상위 리소스 코드 */
    private String pcd;
    /** 리소스 코드 */
    private String cd;
    
    /** Child Items */
    private List<MenuVO> items;
    
    /** 가상로그인 ID */
    private String vLoginId;
}
