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
</body>
</html>

<script type="text/javascript">
    //console.log("userId : " + sessionStorage.getItem("userId"));
    //console.log("storeCd : " + sessionStorage.getItem("storeCd"));
    //console.log("popUrl : " + sessionStorage.getItem("popUrl"));

    // 이전 화면(연동 단계 파악)
    var prePage = "${prePage}";

    // uniqueId
    var uniqueId = "${uniqueId}";

    if (prePage == "login") {

        // 연동 매장 리스트 div hidden
        document.getElementById('divWrap').style.display = "none";

        // 약관동의 팝업 후
        //var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb?uniqueId=" + uniqueId);
        var popupUrl = sessionStorage.getItem("popUrl") + "/embed/terms?service=lynk_pos,mybiz,booking&to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=750, height=1000");

    } else if (prePage == "agree") {

        // 연동 매장 리스트 div show
        document.getElementById('divWrap').style.display = "block";

        // 매장 조회
        getStoreList(0);

    } else {
        // 연동 매장 리스트 div hidden
        document.getElementById('divWrap').style.display = "none";
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
                                innerHtml += "<button class=\"btn btn-blue\" onClick=\"mappingStore('" + arr[i].placeId + "','" + arr[i].businessName + "')\"><s:message code="naverPlacePlusLink.link" /></button>";
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

    // 연동 하기
    function mappingStore(placeId, businessName) {

        var params = {};
        params.userId = sessionStorage.getItem("userId");
        params.storeCd = sessionStorage.getItem("storeCd");
        params.uniqueId = uniqueId;
        params.placeId = placeId;
        params.businessName = businessName;

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

</style>