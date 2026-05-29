<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title></title>
    <script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>
</head>
<body>
<div id="divWrap" class="wrap" style="display: none;">
    <h2 class="page-title"><s:message code="naverPlacePlusLink.info1" /></h2>
    <div class="top-actions">
        <span class="desc">
          <s:message code="naverPlacePlusLink.info2" />
        </span>
        <div class="btn-group">
            <button class="btn btn-green" id="btnSearch" onclick="btnSearch();"><s:message code="naverPlacePlusLink.searchStore" /></button>
            <%--<button class="btn btn-green" id="btnReg" onclick="btnReg();"><s:message code="naverPlacePlusLink.regStore" /></button>--%>
        </div>
    </div>
    <%-- 업체 목록 --%>
    <div id="divStoreList" class="store-list"></div>
    <div id="divPaging" class="pagination">
    </div>
</div>

<!-- 개인정보 제3자 정보 제공 동의 팝업 -->
<div id="modalOverlay" class="modal-overlay" onclick="closeModal();">
    <div class="modal-box" onclick="event.stopPropagation();">

        <div class="modal-logo">링크POS × <span>N SmartPlace</span></div>
        <div class="modal-store-name"><input type="hidden" id="hdPlaceId"/><span><label id="lblBusinessName"></label></span></div>

        <h3 class="modal-title">네이버 스마트플레이스</h3>
        <p class="modal-subtitle">링크POS에서 연동해 관리하세요.</p>

        <p style="font-size:13px; font-weight:600; color:#333; margin:0 0 8px;">
            개인(신용)정보 제3자 제공 동의 <span style="color:red; font-size:11px;">필수</span>
        </p>

        <div class="modal-scroll-body">
            본인은 링크 주식회사(이하 “회사”)가 제공하는 네이버 스마트플레이스 연동 서비스(이하”서비스)를 이용하기 위해,
            아래와 같이 ‘회사'가 본인의 개인(신용)정보를 제 3자에게 제공하는 것에 동의합니다.
            <span class="label">제공 받는자</span>
            네이버 주식회사
            <span class="label">제공 목적</span>
            네이버 스마트 플레이스 서비스 내 업체 등록<br>
            기타 위 서비스와 POS 간 연동<br>
            네이버 서비스 등록 업체에 대한 통계 데이터 제공 및 이용자 경험 향상 등을 위한 네이버 플레이스 서비스 및
            네이버 플랫폼(네이버 예약, 주문 서비스, MY 플레이스 포함)의 운영 및 개발, 신규 서비스 개발 및 제공, 프로모션 및 각종 홍보
            <span class="label">제공 항목</span>
            POS 매출데이터 (상품정보, 결제정보, 할인정보, 주문정보, 메뉴정보), POS 영업현황, POS 테이블 현황, 대표자이름, 이메일주소, 휴대전화번호
            <span class="label">이용 및 보유 기간</span>
            고객의 동의 철회 요청과 같은 별도의 의사표시가 없는 한 네이버 주식회사가 해당 정보에 대한 사용권을 계속 보유·이용
            단, 서비스 이용 해지 또는 제3자 제공 동의 철회 후에도 네이버 스마트플레이스를 계속 이용하는 경우,
            필수 항목(대표자이름, 이메일주소, 휴대전화번호, 그외 필수정보)은 스마트플레이스 탈퇴 시까지 유지됩니다.
            <span class="label">동의를 거부하는 경우에 대한 안내</span>
            본 개인정보 제 3자 제공 동의는 “서비스”의 이용을 위해서 필수적인 사항이므로, 동의를 하지 않으시면 “서비스"를 이용하실 수 없습니다.
            본 개인정보 제 3자 제공 동의를 거부하더라도 회사가 제공하는 기타 서비스의 이용에는 영향이 없습니다.
        </div>

        <label class="modal-agree">
            <input type="checkbox" id="chkAgree" />
            위 약관에 동의합니다.
        </label>

        <div class="modal-actions">
            <button class="btn btn-green" onclick="mappingStore();">동의하고 연동하기</button>
            <button class="btn btn-gray" onclick="closeModal();">취소</button>
        </div>

    </div>
</div>

</body>
</html>

<script type="text/javascript">
    //console.log("userId : " + sessionStorage.getItem("userId"));
    //console.log("storeCd : " + sessionStorage.getItem("storeCd"));
    //console.log("hqOfficeCd : " + sessionStorage.getItem("hqOfficeCd"));
    //console.log("popUrl : " + sessionStorage.getItem("popUrl"));

    // 이전 화면(연동 단계 파악)
    var prePage = "${prePage}";

    // uniqueId
    var uniqueId = "${uniqueId}";

    if (prePage == "login") {

        // 동의여부확인 API 호출
        /*var params = {};
        params.hqOfficeCd = sessionStorage.getItem("hqOfficeCd");
        params.storeCd = sessionStorage.getItem("storeCd");
        params.userId = sessionStorage.getItem("userId");

        $.ajax({
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            url: '/naverPlace/naverPlace/naverPlacePlusLink/getAgreeYn.sb',
            data: params,
            success: function (data) {
                //console.log(JSON.stringify(data));
                if (data.status === "OK") {
                    var vAgreeYn = data.data.list;
                    if (vAgreeYn != null && vAgreeYn != undefined) {
                        if (vAgreeYn.ownerMemberStatus == "REGULAR" && vAgreeYn.isJoinedMember == true) {
                            var arr = vAgreeYn.agreedPlacePrivacyAgreementTypes;
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
                                // 연동 매장 리스트 div show
                                document.getElementById('divWrap').style.display = "block";
                                // 매장 조회
                                getStoreList(0);
                            } else {
                                // 약관동의 팝업 이동
                                goAgreePop();
                            }
                        } else {
                            // 약관동의 팝업 이동
                            goAgreePop();
                        }
                    } else {
                        // 약관동의 팝업 이동
                        goAgreePop();
                    }
                }
            }
        });*/

        goAgreePop();

    } else if (prePage == "agree") {

        // 연동 매장 리스트 div show
        document.getElementById('divWrap').style.display = "block";

        // 매장 조회
        getStoreList(0);

    } else {
        // 연동 매장 리스트 div hidden
        document.getElementById('divWrap').style.display = "none";
    }

    // 약관동의 팝업 이동
    function goAgreePop() {

        // 연동 매장 리스트 div hidden
        document.getElementById('divWrap').style.display = "none";

        // 약관동의 팝업 후
        //var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var popupUrl = sessionStorage.getItem("popUrl") + "/embed/terms?service=lynk_pos,mybiz,booking&to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=750, height=1000");
    }

    // 업체 찾기
    function btnSearch() {
        //var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var popupUrl = sessionStorage.getItem("popUrl") + "/bizes/lookup?to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=750, height=1000");
    }

    // 신규 등록
    function btnReg() {
        //var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var popupUrl = sessionStorage.getItem("popUrl") + "/bizes/new?to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=750, height=1000");
    }

    // 연동 매장 조회
    function getStoreList(page) {

        // 업체 목록 조회 API 호출
        var params = {};
        params.page = (1 > page ? 0 : page);
        params.storeCd = sessionStorage.getItem("storeCd");
        params.uniqueId = uniqueId;

        $.ajax({
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            url: '/naverPlace/naverPlace/naverPlacePlusLink/getPlaceList.sb',
            data: params,
            success: function (data) {
                //console.log(JSON.stringify(data));
                if (data.status === "OK") {
                    var arr = data.data.list;
                    var innerHtml = "";
                    var innerHtmlPaging = "";

                    if (arr.length > 0) {
                        for (var i = 0; i < arr.length; i++) {
                            if (i < arr.length - 1) {
                                // 매장 리스트
                                innerHtml += "<div class=\"store-card\">";
                                if (arr[i].businessImage.primary != null) {
                                    if (arr[i].businessImage.primary.url != null) {
                                        innerHtml += "<div class=\"thumb\"><img src=\"" + decodeURIComponent(arr[i].businessImage.primary.url) + "\" alt=\"\" width=\"70px\" height=\"70px\"/></div>";
                                    } else {
                                        innerHtml += "<div class=\"thumb\"></div>";
                                    }
                                } else {
                                    innerHtml += "<div class=\"thumb\"></div>";
                                }
                                innerHtml += "<div class=\"store-info\">";
                                innerHtml += "<h3>" + arr[i].businessName + "</h3>";
                                innerHtml += "<p>" + arr[i].address + "</p>";

                                if (arr[i].phone != null) {
                                    innerHtml += "<p>" + arr[i].phone + "</p>";
                                } else {
                                    innerHtml += "<p></p>";
                                }

                                innerHtml += "</div>";
                                innerHtml += "<button class=\"btn btn-blue\" onClick=\"openModal('" + arr[i].placeId + "','" + arr[i].businessName + "')\"><s:message code="naverPlacePlusLink.link" /></button>";
                                innerHtml += "</div>";

                            } else {
                                // 페이징
                                var totalPages = Math.ceil(arr[i].totCnt / 10);
                                if (totalPages > 0) {
                                    innerHtmlPaging += "<button class=\"page-btn\" onClick=\"getList(0)\">〈〈</button>";
                                    for (var j = 1; j <= totalPages; j++) {
                                        innerHtmlPaging += "<button class=\"page-btn " + (page + 1 === j ? "active" : "") + "\" onClick=\"getList(" + (j - 1) + ")\">" + j + "</button>";
                                    }
                                    innerHtmlPaging += "<button class=\"page-btn\" onClick=\"getList(" + (totalPages - 1) + ")\">〉〉</button>";
                                }
                            }
                        }
                    } else {
                        innerHtml += "<div class=\"store-card-none\">";
                        innerHtml += "<span class=\"desc\"><s:message code="naverPlacePlusLink.info3" /></span>";
                        innerHtml += "</div>";
                    }

                    $("#divStoreList").html(innerHtml);
                    $("#divPaging").html(innerHtmlPaging);
                }
            }
        });
    }

    // 개인(신용)정보 제3자 제공 동의 팝업 오픈
    function openModal(placeId, businessName) {
        $("#hdPlaceId").val(placeId);
        $("#lblBusinessName").text(businessName);
        document.getElementById('modalOverlay').classList.add('active');
    }

    // 개인(신용)정보 제3자 제공 동의 팝업 닫기
    function closeModal() {
        $("#hdPlaceId").val("");
        $("#lblBusinessName").text("");
        document.getElementById('chkAgree').checked = false;
        document.getElementById('modalOverlay').classList.remove('active');
    }

    // 동의하고 연동하기
    function mappingStore() {

        // 체크박스 요소 선택
        var checkbox = document.getElementById("chkAgree");

        // checked 여부 확인
        if (!checkbox.checked) {
            alert("약관동의 체크박스를 먼저 선택해주세요.");
            return false;
        }

        var params = {};
        params.hqOfficeCd = sessionStorage.getItem("hqOfficeCd");
        params.userId = sessionStorage.getItem("userId");
        params.storeCd = sessionStorage.getItem("storeCd");
        params.uniqueId = uniqueId;
        params.placeId = $("#hdPlaceId").val();
        params.businessName = $("#lblBusinessName").text();

        $.ajax({
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            url: '/naverPlace/naverPlace/naverPlacePlusLink/mappingPlace.sb',
            data: params,
            success: function (data) {
                console.log(JSON.stringify(data));
                if (data.status === "OK") {

                    // 부모창 재조회
                    const bc = new BroadcastChannel('refresh_channel');
                    bc.postMessage('refresh');

                    // 닫기
                    window.close();
                }
            }
        });
    }
</script>

<style>
    /* 전체 배경 */
    body {
        margin: 0;
        font-family: "Noto Sans KR", sans-serif;
    }

    /* 래퍼 */
    .wrap {
        max-width: 1000px;
        margin: 60px auto;
        padding: 0 20px;
    }

    /* 제목 */
    .page-title {
        text-align: center;
        font-size: 22px;
        font-weight: 600;
        margin-bottom: 30px;
    }

    /* 상단 영역 */
    .top-actions {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
    }

    .desc {
        text-align: center;
        font-size: 14px;
        color: #777;
        width: 100%;
    }

    .btn-group {
        /* 버튼 사이 간격 */
        gap: 10px;

        /* 원하는 크기로 고정 */
        width: 90px;
        height: 40px;

        /* 핵심: 부모가 좁아져도 버튼 크기가 줄어들지 않게 함 */
        flex-shrink: 0;

        /* 내용 중앙 정렬을 위한 팁 */
        display: flex;
        align-items: center;
        justify-content: center;
    }

    /* 버튼 공통 */
    .btn {
        padding: 8px 16px;
        border-radius: 6px;
        border: none;
        font-size: 14px;
        cursor: pointer;
        font-weight: 500;
        transition: 0.2s;
    }

    /* 초록 버튼 */
    .btn-green {
        background-color: #03c75a;
        color: #fff;
    }

    .btn-green:hover {
        background-color: #02b351;
    }

    /* 파란 버튼 */
    .btn-blue {
        background-color: #3b82f6;
        color: #fff;
        height: 38px;
    }

    .btn-blue:hover {
        background-color: #2563eb;
    }

    /* 매장 리스트 */
    .store-list {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    /* 매장 카드 */
    .store-card {
        display: flex;
        align-items: center;
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        border: 1px solid #e5e7eb;
    }

    /* 매장 카드 */
    .store-card-none {
        display: flex;
        align-items: center;
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        border: 1px solid #e5e7eb;
        height: 300px;
    }

    /* 썸네일 */
    .thumb {
        width: 70px;
        height: 70px;
        background: #e5e7eb;
        border-radius: 6px;
        margin-right: 20px;
    }

    /* 매장 정보 */
    .store-info {
        flex: 1;
    }

    .store-info h3 {
        font-size: 16px;
        margin: 0 0 6px;
    }

    .store-info p {
        margin: 2px 0;
        font-size: 14px;
        color: #666;
    }

    /* 페이징 */
    .pagination {
        display: flex;
        justify-content: center;
        margin-top: 30px;
        gap: 6px;
    }

    .page-btn {
        width: 34px;
        height: 31px;
        border-radius: 4px;
        border: 1px solid #ddd;
        background: #fff;
        cursor: pointer;
        color: #BBBDC0;
    }

    .page-btn.active {
        background: #3b82f6;
        color: #fff;
        border-color: #3b82f6;
    }

    /* 모달 오버레이 */
    .modal-overlay {
        display: none;
        position: fixed;
        inset: 0;
        background: rgba(0, 0, 0, 0.45);
        z-index: 1000;
        align-items: center;
        justify-content: center;
    }

    .modal-overlay.active {
        display: flex;
    }

    /* 모달 박스 */
    .modal-box {
        position: relative;
        background: #fff;
        border-radius: 14px;
        padding: 36px 32px 30px;
        width: 460px;
        max-width: 90vw;
        box-shadow: 0 4px 24px rgba(0,0,0,0.13);
    }

    /* 상단 로고 영역 */
    .modal-logo {
        text-align: center;
        font-size: 18px;
        font-weight: 700;
        color: #333;
        margin-bottom: 8px;
        letter-spacing: -0.3px;
    }

    .modal-logo span {
        color: #03c75a;
    }

    /* 매장명 태그 */
    .modal-store-name {
        text-align: center;
        margin-bottom: 22px;
    }

    .modal-store-name span {
        display: inline-block;
        background: #f1f3f5;
        border-radius: 20px;
        padding: 6px 22px;
        font-size: 15px;
        color: #444;
    }

    /* 모달 제목 */
    .modal-title {
        font-size: 18px;
        font-weight: 700;
        color: #222;
        margin: 0 0 5px;
    }

    .modal-subtitle {
        font-size: 14px;
        color: #03c75a;
        margin: 0 0 14px;
    }

    /* 본문 스크롤 영역 */
    .modal-scroll-body {
        background: #f8f9fa;
        border: 1px solid #e5e7eb;
        border-radius: 6px;
        padding: 16px;
        font-size: 14px;
        color: #555;
        line-height: 1.8;
        max-height: 220px;
        overflow-y: auto;
        margin-bottom: 16px;
    }

    .modal-scroll-body .label {
        font-weight: 600;
        color: #333;
        margin-top: 12px;
        margin-bottom: 2px;
        display: block;
    }

    /* 동의 체크박스 */
    .modal-agree {
        display: flex;
        align-items: center;
        gap: 8px;
        font-size: 15px;
        color: #444;
        margin-bottom: 22px;
    }

    .modal-agree input[type="checkbox"] {
        width: 18px;
        height: 18px;
        accent-color: #03c75a;
        cursor: pointer;
    }

    /* 하단 버튼 영역 */
    .modal-actions {
        display: flex;
        gap: 10px;
    }

    .modal-actions .btn {
        flex: 1;
        height: 50px;
        font-size: 16px;
        font-weight: 600;
        border-radius: 8px;
    }

    .btn-gray {
        background-color: #f1f3f5;
        color: #555;
        border: none;
    }

    .btn-gray:hover {
        background-color: #e5e7eb;
    }

</style>