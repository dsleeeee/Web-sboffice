<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon" ng-controller="naverOrderLinkCtrl">
    <div class="searchBar">
        <a href="#" class="open fl" onclick="openLogout()"><s:message code="naverOrderLink.naverOrderLink" /></a>
        <button class="btn_blue fr mt5 mr10" id="btnLogout" ng-click="btnLogout();" style="display: none;">
            <s:message code="naverOrderLink.logout"/>
        </button>
    </div>

    <%-- 메인 화면 --%>
    <div id="divView1">
        <div class="mt10" style="text-align:center;">
            <img src="/resource/solbipos/css/img/orderkit/banner_260123@2x.png" alt="" style="width:100%; height: 100%;"/>
            <button class="action-btn1 mt30" id="btnLink" ng-click="btnLink();"><s:message code="naverOrderLink.naverOrderLink" /></button>
        </div>
    </div>

    <%-- 주문연동 매장선택 --%>
    <div id="divView2">
        <%-- 업체 리스트 --%>
        <div id="divStoreList" class="store-list"></div>
        <%-- 페이징 --%>
        <div id="divPaging" class="store-paging"></div>
    </div>

    <%-- 연동완료 --%>
    <div id="divView3">
        <div class="link-complete-wrap">

            <%-- 타이틀 영역 --%>
            <div class="link-complete-title-wrap">
                <h3 class="link-complete-title">
                    연동 완료 <img src="/resource/solbipos/css/img/check.png" alt="" style="width: 22px; vertical-align: middle;">
                </h3>
            </div>

            <%-- 버튼 영역 --%>
            <div class="updownSet oh mb10">
                <%-- 연동 해지 --%>
                <button class="btn_skyblue" id="btnWithdraw" ng-click="btnWithdraw();"><s:message code="naverOrderLink.withdraw" /></button>
            </div>

            <%-- 연동 정보 섹션 --%>
            <div class="info-section">
                <div class="info-section-title"><s:message code="naverOrderLink.linkInfo" /></div>
                <div class="info-grid">
                    <div class="info-row">
                        <%-- 매장명 --%>
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.storeNm" /></label>
                            <input type="text" class="info-input" readonly="readonly" id="storeNm1">
                        </div>
                        <%-- 비즈니스 아이디 --%>
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.businessId" /></label>
                            <input type="text" class="info-input" readonly="readonly" id="businessId1">
                        </div>
                    </div>
                    <div class="info-row">
                        <%-- 서비스 명 --%>
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.serviceNm" /><span class="info-label-sub">(네이버 주문 노출)</span></label>
                            <input type="text" class="info-input" readonly="readonly" id="serviceNm1">
                        </div>
                        <%-- 매장 전화 번호 --%>
                        <div class="info-cell">
                            <label class="info-label"><s:message code="naverOrderLink.phone" /></label>
                            <input type="text" class="info-input" readonly="readonly" id="phoneNo1">
                        </div>
                    </div>
                    <div class="info-row">
                        <%-- 매장 주소 --%>
                        <div class="info-cell info-cell-full">
                            <label class="info-label"><s:message code="naverOrderLink.address" /></label>
                            <input type="text" class="info-input" readonly="readonly" id="addr1" style="margin-bottom: 6px;">
                            <input type="text" class="info-input" readonly="readonly" id="addrDtl1">
                        </div>
                    </div>
                </div>
            </div>

            <%-- 버튼 영역 --%>
            <div class="updownSet oh mb10">
                <%-- 주문 유형 정보 수정 --%>
                <button class="btn_skyblue" iid="btnEdit" ng-click="btnEdit();"><s:message code="cmm.edit" /></button>
            </div>

            <%-- 주문 유형 정보 섹션 --%>
            <div class="info-section mt10">
                <div class="info-section-title"><s:message code="naverOrderLink.orderTypeInfo" /></div>
                <div class="order-type-grid">
                    <%-- 테이블 주문 --%>
                    <div class="order-type-cell">
                        <span class="order-type-label"><s:message code="naverOrderLink.tableOrder" /></span>
                        <label class="radio-label"><input type="radio" name="tableOrder1" id="rdTableOrderY1" value="Y" disabled><s:message code="naverOrderLink.exposeY" /></label>
                        <label class="radio-label"><input type="radio" name="tableOrder1" id="rdTableOrderN1" value="N" disabled><s:message code="naverOrderLink.exposeN" /></label>
                    </div>
                        <%-- 픽업 주문 --%>
                    <div class="order-type-cell">
                        <span class="order-type-label"><s:message code="naverOrderLink.pickupOrder" /></span>
                        <label class="radio-label"><input type="radio" name="pickupOrder1" id="rdPickupOrderY1" value="Y" disabled><s:message code="naverOrderLink.exposeY" /></label>
                        <label class="radio-label"><input type="radio" name="pickupOrder1" id="rdPickupOrderN1" value="N" disabled><s:message code="naverOrderLink.exposeN" /></label>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <%-- 연동실패 --%>
    <div id="divView4" style="display: none;">
        <div class="fail-wrap">

            <%-- 타이틀 --%>
            <div class="fail-title-wrap">
                <h3 class="fail-title">
                    연동 실패 &nbsp;<span class="fail-icon">!</span>
                </h3>
                <p class="fail-desc">
                    네이버 주문연동에 실패 했습니다.<br/>
                    아래 관리자를 통해 문의 해주세요.
                </p>
            </div>

            <%-- 관리자 문의 정보 카드 --%>
            <div class="fail-card">
                <p class="fail-card-title">
                    관리자 문의 정보 &nbsp;<img src="/resource/solbipos/css/img/info.png" alt="" style="width: 18px; vertical-align: middle;">
                </p>
                <table class="fail-table">
                    <colgroup>
                        <col style="width: 120px;"/>
                        <col/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <td class="fail-table-label">문의 번호</td>
                        <td class="fail-table-input">
                            <input type="text" class="info-input" readonly="readonly" id="txtCsPhone" style="width: 100%;" value="02-000-0000">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

</div>

<script type="text/javascript">
    // 메뉴코드
    var menuCd = "${menuCd}";
    // 사용자 ID
    var userId = "${userId}";
    // 매장코드
    var storeCd = "${storeCd}";
    // 본사코드
    var hqOfficeCd = "${hqOfficeCd}";
    // 팝업 Url
    var popUrl = "${popUrl}";
    // 네.아.로 uniqueId
    var uniqueId = "${uniqueId}";
    // 약관동의 여부
    //var agreeYn = ${agreeYn};
    // 네이버 주문연동 여부
    var linkYn = ${linkYn};
    // 서비스별 활성화 여부
    var serviceActiveYn = ${serviceActiveYn};
    // 연동 단계
    var linkStep = 0;

    sessionStorage.setItem("userId", userId);
    sessionStorage.setItem("storeCd", storeCd);
    sessionStorage.setItem("hqOfficeCd", hqOfficeCd);
    sessionStorage.setItem("popUrl", popUrl);

    // 연동 단계 파악
    // 0 : 네.아.로 로그인 미완료
    // 1 : 네.아.로 로그인 완료, 동의 미완료  --> 동의 받는 로직이 사라지면서 해당 단계 없어짐
    // 2 : 동의 완료, 주문연동 미완료
    // 3 : 주문연동 완료
    if (uniqueId != "" && uniqueId != null) {
        linkStep = 2; // linkStep = 1;

        /*if (agreeYn != null && agreeYn != undefined) {
            if (agreeYn.ownerMemberStatus == "REGULAR" && agreeYn.isJoinedMember == true) {
                var arr = agreeYn.agreedPlacePrivacyAgreementTypes;
                var cnt = 0;

                // 동의 항목 파악
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i] === "SMARTPLACE_INTEGRATED_TERMS") {
                        cnt++;
                    }
                    if (arr[i] === "SMARTPLACE_BUSINESS_TERMS") {
                        cnt++;
                    }
                }

                if (cnt => 2) {
                    linkStep = 2;*/

                    if (linkYn != null && linkYn != undefined) {
                        if (linkYn.status === 200 && linkYn.data) {
                            linkStep = 3;
                        }
                    }
                /*}
            }
        }*/
    }

    // 화면 셋팅
    if (2 > linkStep) {
        $("#divView1").css("display", "");
        $("#divView2").css("display", "none");
        $("#divView3").css("display", "none");
        $("#divView4").css("display", "none");

    } else if (linkStep == 2) {
        $("#divView1").css("display", "none");
        $("#divView2").css("display", "");
        $("#divView3").css("display", "none");
        $("#divView4").css("display", "none");

        // 업체리스트 조회
        getStoreList(0);

    } else if (linkStep == 3) {
        $("#divView1").css("display", "none");
        $("#divView2").css("display", "none");
        $("#divView3").css("display", "");
        $("#divView4").css("display", "none");

        // 연동 정보 셋팅
        $("#storeNm1").val(linkYn.data.name);
        $("#businessId1").val(linkYn.data.channelShopId);
        $("#serviceNm1").val(linkYn.data.serviceName);
        $("#phoneNo1").val(linkYn.data.shopTelNo);
        $("#addr1").val(linkYn.data.roadAddr ? linkYn.data.roadAddr : "");
        $("#addrDtl1").val(linkYn.data.addrDetail ? linkYn.data.addrDetail : "");

        // 주문 유형 정보 셋팅
        $("input[name='tableOrder1'][value='N']").prop("checked", true);
        $("input[name='pickupOrder1'][value='N']").prop("checked", true);

        var serviceList = (serviceActiveYn && serviceActiveYn.data) ? serviceActiveYn.data : [];
        for (var i = 0; i < serviceList.length; i++) {
            var serviceItem = serviceList[i];

            if (serviceItem.service === "TABLE") {
                if (serviceItem.useFlag) {
                    $("input[name='tableOrder1'][value='Y']").prop("checked", true);
                } else {
                    $("input[name='tableOrder1'][value='N']").prop("checked", true);
                }
            } else if (serviceItem.service === "PICKUP") {
                if (serviceItem.useFlag) {
                    $("input[name='pickupOrder1'][value='Y']").prop("checked", true);
                } else {
                    $("input[name='pickupOrder1'][value='N']").prop("checked", true);
                }
            }
        }
    }

    // state 값 생성 (네이버로그인후 기존세션 확인을 위한 임의값)
    function generateState() {
        const bytes = new Uint8Array(16);
        crypto.getRandomValues(bytes);

        const base64 = btoa(String.fromCharCode(...bytes));
        return base64.replace(/\+/g, "-").replace(/\//g, "_").replace(/=+$/, "");
    }

    // 연동 후 재조회
    const bc = new BroadcastChannel('refresh_channel');
    bc.onmessage = (event) => {
        if (event.data === 'refresh') location.reload();
    };

    /*agreeYn = {
        "ownerMemberStatus": "REGULAR",
        "isJoinedMember": true,
        "isWithdrawing": false,
        "agreedPlacePrivacyAgreementTypes": [
            "PROMOTION_RECEIVE",
            "SMARTPLACE_BUSINESS_TERMS",
            "SMARTPLACE_INTEGRATED_TERMS",
            "AGENCY_BUSINESS_DATA_PROVISION"
        ],
        "isMyBizAgreed": true
    };*/

    /*{"ownerMemberStatus":"NONMEMBER","isJoinedMember":false,"isWithdrawing":false}*/

    console.log("연동단계1 :" + linkStep + " / 네.아.로 아이디 :" + uniqueId /*+ "/ 동의 :" + agreeYn.agreedPlacePrivacyAgreementTypes*/ + " / 주문연동 :" + linkYn.placeId);

    // 로그아웃 버튼 오픈
    var clickCnt = 0;
    // 로그아웃(네아로, 네이버 동의 삭제)
    function openLogout() {
        clickCnt++;
        if (clickCnt >= 10) {
            $("#btnLogout").css("display", "block");
        } else {
            $("#btnLogout").css("display", "none");
        }
    }

    // 현재 페이지 (0-based)
    var currentPage = 0;
    // 다음 페이지 없음 여부
    var noMorePages = false;
    // 업체리스트 조회
    function getStoreList(page) {

        currentPage = page < 0 ? 0 : page;
        noMorePages = false;

        // 업체 목록 조회 API 호출
        var params = {};
        params.channelType = "NAVER";
        params.page = currentPage;
        params.size = 10;

        var url = "/naverPlace/naverPlace/naverOrderLink/getPlaceList.sb";
        // 가상로그인시 세션활용
        if (document.getElementsByName("sessionId")[0]) {
            url += '?sid=' + document.getElementsByName("sessionId")[0].value;
        }

        $.ajax({
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            url: url,
            data: params,
            success: function (data) {
                if (data.status === "OK") {
                    var arr = data.data.list.data;
                    var innerHtml = "<h3 class=\"store-list-title\">네이버 주문에 연동하실 매장을 선택 해주세요.</h3>";
                    var pagingHtml = "";

                    // 조회된 전체 매장정보 갖고있기(연동 시 사용)
                    window.storeList = arr;

                    if (arr != null && arr.length > 0) {

                        for (var i = 0; i < arr.length; i++) {
                            var item = arr[i];
                            innerHtml += "<div class=\"store-card\">";

                            // 썸네일
                            innerHtml += "<div class=\"store-thumb\"></div>";

                            // 매장 정보
                            innerHtml += "<div class=\"store-info\">";
                            innerHtml += "<p class=\"store-name\">" + (item.name || "") + "</p>";
                            innerHtml += "<p class=\"store-detail\">" + (item.ceoName || "") + "</p>";
                            innerHtml += "<p class=\"store-detail\">" + (item.roadAddr || "");
                            if (item.addressDetail) innerHtml += "<br/>" + item.addressDetail;
                            innerHtml += "</p>";
                            if (item.shopTelNo) {
                                innerHtml += "<p class=\"store-detail\">" + item.shopTelNo + "</p>";
                            }
                            innerHtml += "</div>";

                            // 연동하기 버튼
                            innerHtml += "<button class=\"btn-link\" onclick=\"btnLinkStore(" + i + ")\">연동하기</button>";
                            innerHtml += "</div>";
                        }

                        // 페이징 버튼
                        var hasNext = !noMorePages && arr.length === params.size;
                        pagingHtml += "<button class=\"page-btn page-btn-arrow\" onclick=\"getStoreList(" + (currentPage - 1) + ")\"" + (currentPage === 0 ? " disabled" : "") + ">&#8249;</button>";
                        pagingHtml += "<button class=\"page-btn page-btn-num active\">" + (currentPage + 1) + "</button>";
                        pagingHtml += "<button class=\"page-btn page-btn-arrow\" onclick=\"getStoreList(" + (currentPage + 1) + ")\"" + (!hasNext ? " disabled" : "") + ">&#8250;</button>";

                    } else {
                        if (currentPage > 0) {
                            // 다음 페이지 결과 없음
                            noMorePages = true;
                            s_alert.pop("마지막 페이지 입니다.");
                            getStoreList(currentPage - 1);
                            return false;
                        } else {
                            // 조회 결과 없을 때
                            innerHtml += "<div class=\"store-card-none\">";
                            innerHtml += "<p class=\"store-none-msg\">등록된 매장이 없습니다.<br/>신규 등록을 진행 해주세요.</p>";
                            innerHtml += "</div>";
                        }
                    }

                    $("#divStoreList").html(innerHtml);
                    $("#divPaging").html(pagingHtml);
                }
            },
            error: function (xhr, status, error) {
                console.log("AJAX 에러:", status, error, xhr.responseText);
            }
        });
    }

</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverOrderLink/naverOrderLink.js?ver=20260831.01" charset="utf-8"></script>

<%-- 네이버주문연동 정보 수정 팝업--%>
<c:import url="/WEB-INF/view/naverPlace/naverPlace/naverOrderLink/naverOrderInfo.jsp">
</c:import>

<%-- 네이버주문연동 주문유형 설정 팝업--%>
<c:import url="/WEB-INF/view/naverPlace/naverPlace/naverOrderLink/naverOrderType.jsp">
</c:import>

<%-- 네이버플레이스 연동 초기화 팝업 --%>
<c:import url="/WEB-INF/view/sys/link/naverPlaceStatus/naverPlaceStatusReset.jsp">
</c:import>

<style>
    /* ===== 매장 선택 리스트 ===== */
    .store-list-title {
        text-align: center;
        font-size: 1.3rem;
        font-weight: 700;
        color: #212529;
        margin: 30px 0 30px;
    }

    /* 매장 카드 */
    .store-card {
        display: flex;
        align-items: center;
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        border: 1px solid #e5e7eb;
        margin: 15px;
    }

    .store-thumb {
        width: 70px;
        height: 70px;
        background: #e5e7eb;
        border-radius: 6px;
        margin-right: 20px;
    }

    .store-info {
        font-size: 1.2rem;
        flex: 1;
        display: flex;
        flex-direction: column;
        gap: 2px;
    }

    .store-name {
        font-size: 1rem;
        font-weight: 700;
        color: #212529;
        margin: 0;
        line-height: 1.7;
    }

    .store-detail {
        font-size: 0.88rem;
        color: #495057;
        margin: 0;
        line-height: 1.3;
    }

    .btn-link {
        flex-shrink: 0;
        background-color: #3b82f6;
        color: #ffffff;
        border: none;
        border-radius: 6px;
        padding: 8px 18px;
        font-size: 0.9rem;
        cursor: pointer;
        white-space: nowrap;
    }

    .btn-link:hover {
        background-color: #2563eb;
    }

    /* 페이징 */
    .store-paging {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 4px;
        margin-top: 16px;
    }

    .page-btn {
        background-color: #ffffff;
        border: 1px solid #ced4da;
        border-radius: 4px;
        width: 32px;
        height: 32px;
        font-size: 0.8rem;
        color: #495057;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 0;
    }

    .page-btn:disabled {
        color: #ced4da;
        cursor: default;
    }

    .page-btn-arrow {
        font-size: 2.5rem;
        line-height: 0;
        padding-bottom: 8px;
    }

    .page-btn-num.active {
        background-color: #007bff;
        border-color: #007bff;
        color: #ffffff;
        font-weight: 700;
        cursor: default;
    }

    .store-card-none {
        border: 1px solid #d0d0d0;
        border-radius: 8px;
        background: #ffffff;
        display: flex;
        align-items: center;
        justify-content: center;
        min-height: 500px;
    }

    .store-none-msg {
        text-align: center;
        color: #adb5bd;
        font-size: 1.1rem;
        line-height: 1.5;
        margin: 0;
    }

    /* 연동 전 버튼 */
    .action-btn1 {
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: 600;
        padding: 1.2em 2.8em;
        width: 50%;
    }

    /* ===== 연동 완료 화면 ===== */
    .link-complete-wrap {
        padding: 30px 40px;
    }

    /* 타이틀 */
    .link-complete-title-wrap {
        text-align: center;
        margin-bottom: 12px;
    }

    .link-complete-title {
        font-size: 1.4rem;
        font-weight: 700;
        color: #212529;
        margin: 0;
    }

    /* 섹션 공통 */
    .info-section {
        border: 1px solid #d0d0d0;
        border-radius: 6px;
        margin-bottom: 16px;
        overflow: hidden;
    }

    .info-section-title {
        background-color: #e9ecef;
        padding: 10px 16px;
        font-size: 0.95rem;
        font-weight: 700;
        color: #212529;
        border-bottom: 1px solid #d0d0d0;
    }

    /* 연동 정보 그리드 */
    .info-grid {
        padding: 16px 20px;
        background: #ffffff;
    }

    .info-row {
        display: flex;
        gap: 30px;
        margin-bottom: 14px;
    }

    .info-row:last-child {
        margin-bottom: 0;
    }

    .info-cell {
        flex: 1;
        display: flex;
        flex-direction: column;
        gap: 4px;
    }

    .info-cell-full {
        flex: 1 0 100%;
    }

    .info-label {
        font-size: 0.85rem;
        font-weight: 600;
        color: #495057;
        line-height: 1.4;
    }

    .info-label-sub {
        font-size: 0.78rem;
        font-weight: 400;
        color: #6c757d;
    }

    .info-input {
        height: 32px;
        padding: 0 8px;
        border: 1px solid #ced4da;
        border-radius: 4px;
        font-size: 0.88rem;
        color: #212529;
        background-color: #f8f9fa;
        width: 100%;
        box-sizing: border-box;
    }

    /* 주문 유형 그리드 */
    .order-type-grid {
        display: flex;
        padding: 16px 20px;
        background: #ffffff;
        gap: 0;
    }

    .order-type-cell {
        flex: 1;
        display: flex;
        align-items: center;
        gap: 16px;
        border-right: 1px solid #e9ecef;
        padding-right: 30px;
    }

    .order-type-cell:last-child {
        border-right: none;
        padding-left: 30px;
    }

    .order-type-label {
        font-size: 0.9rem;
        font-weight: 600;
        color: #212529;
        min-width: 80px;
    }

    .radio-label {
        display: flex;
        align-items: center;
        gap: 4px;
        font-size: 0.88rem;
        color: #495057;
        cursor: default;
    }

    .radio-label input[type="radio"] {
        margin: 0;
    }

    /* ===== 연동 실패 화면 ===== */
    .fail-wrap {
        padding: 30px 40px;
        text-align: center;
    }

    .fail-title-wrap {
        margin-bottom: 40px;
    }

    .fail-title {
        font-size: 1.4rem;
        font-weight: 700;
        color: #212529;
        margin: 0 0 20px 0;
    }

    .fail-icon {
        display: inline-block;
        color: #dc3545;
        font-weight: 900;
        font-size: 1.5rem;
        vertical-align: middle;
        line-height: 1;
    }

    .fail-desc {
        font-size: 1.4rem;
        font-weight: 700;
        color: #212529;
        line-height: 1.3;
        margin: 0;
    }

    .fail-card {
        max-width: 700px;
        margin: 0 auto;
        border: 1px solid #d0d0d0;
        border-radius: 10px;
        padding: 30px 40px;
        background: #ffffff;
        text-align: left;
    }

    .fail-card-title {
        font-size: 1.1rem;
        font-weight: 700;
        color: #212529;
        text-align: center;
        margin: 0 0 24px 0;
    }

    .fail-table {
        width: auto;
        border-collapse: collapse;
        margin: 0 auto;
    }

    .fail-table-label {
        font-size: 1.1rem;
        font-weight: 600;
        color: #495057;
        padding: 6px 16px 6px 0;
        white-space: nowrap;
        text-align: right;
        width: 120px;
    }

    .fail-table-input {
        padding: 6px 0;
    }
</style>