package kr.co.solbipos.naverPlace.naverPlace.naverPlaceLink.service;

public class NaverPlaceApiVO {

    private static final long serialVersionUID = -522464773162578497L;


    /** 인증과정에 대한 내부 구분값(고정값) */
    private String code;
    /** 네이버로그인후 기존세션 확인을 위한 임의값 */
    private String state;
    /** 스마플 개인정보 약관 동의 목록 */
    private String projections;
    /** 네.아.로 Unique ID */
    private String uniqueId;
    /** 인증 토큰 */
    private String accessToken;
    /** 페이지 */
    private String page;
    /** 업체명 */
    private String businessName;
    /** 전화번호(유선) */
    private String phone;
    /** 주소 */
    private String address;
    /** 업종(업종 조회 API를 통해 얻은 categoryId) */
    private String categoryId;
    /** 업종(업종 조회 API를 통해 얻은 categoryId) */
    private String brandCategoryId;
    /** 사업자등록번호 */
    private String registrationNumber;
    /** 업체 중복 체크 API로부터 얻은 스마트플레이스 업체 ID */
    private String placeId;
    /** 업체 영업시간/휴무일 정보 */
    private String bizDays;
    /** PDF 파일 URL */
    private String registrationCertificate;
    /** 검색어 */
    private String search;
    /** 검색하고자 하는 categoryId 목록 */
    private String categoryIds;
    /**  검색하고자 하는 brandCategoryId 목록 */
    private String brandCategoryIds;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProjections() {
        return projections;
    }

    public void setProjections(String projections) {
        this.projections = projections;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandCategoryId() {
        return brandCategoryId;
    }

    public void setBrandCategoryId(String brandCategoryId) {
        this.brandCategoryId = brandCategoryId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getBizDays() {
        return bizDays;
    }

    public void setBizDays(String bizDays) {
        this.bizDays = bizDays;
    }

    public String getRegistrationCertificate() {
        return registrationCertificate;
    }

    public void setRegistrationCertificate(String registrationCertificate) {
        this.registrationCertificate = registrationCertificate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getBrandCategoryIds() {
        return brandCategoryIds;
    }

    public void setBrandCategoryIds(String brandCategoryIds) {
        this.brandCategoryIds = brandCategoryIds;
    }
}
