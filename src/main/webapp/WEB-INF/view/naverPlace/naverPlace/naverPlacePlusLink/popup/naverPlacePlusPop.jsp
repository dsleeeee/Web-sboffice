<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title></title>
    <script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>
</head>
<body>
    <div id="divWrap" class="wrap" style="display: none;">
        <h2 class="page-title">네이버 플레이스+에 연동하실 매장을 선택 해주세요.</h2>
        <div class="top-actions">
            <span class="desc">
              찾으시는 매장이 없는 경우, 업체찾기를 통해 확인 하시거나 신규로 등록 해주세요.
            </span>
            <div class="btn-group">
                <button class="btn btn-green" id="btnSearch" onclick="btnSearch();">업체 찾기</button>
                <button class="btn btn-green" id="btnReg" onclick="btnReg();">신규 등록 +</button>
            </div>
        </div>
        <%-- 업체 목록 --%>
        <div id="divStoreList" class="store-list"></div>
        <div id="divPaging" class="pagination">
            <button class="page-btn">&laquo;</button>
            <button class="page-btn active">1</button>
            <button class="page-btn">&raquo;</button>
        </div>
    </div>
</body>
</html>

<script type="text/javascript">

    // 네이버 로그인 팝업창 닫기
    //window.open('about:blank', '_self').self.close();

    // 이전 화면(연동 단계 파악)
    var prePage = "${prePage}";

    //
    var uniqueId = "${uniqueId}";

    if (prePage == "login") {

        //
        document.getElementById('divWrap').style.display = "none";

        //var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb");
        var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusPop.sb");
        //var redirectURL = encodeURIComponent("https://new.smartplace.naver.com/bizes");

        var popupUrl = "https://new.smartplace.naver.com/embed/terms?service=pos_lynk&to=" + redirectURL;  // service=lynk_pos,mybiz,booking
        var popup = window.open(popupUrl, "popup", "width=750, height=1000");

    } else if (prePage == "agree") {

        //
        document.getElementById('divWrap').style.display = "block";

        // 조회
        getList(0);

    } else {

        //
        document.getElementById('divWrap').style.display = "none";
    }

    //
    function btnSearch() {
        //var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverPlaceLink/viewPop2.sb");
        var redirectURL = encodeURIComponent("https://blog.naver.com");
        var popupUrl = "https://new.smartplace.naver.com/bizes/lookup?to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=750, height=1000");
    }

    //
    function btnReg() {

    }

    //
    function getList(pageNo) {

        // 업체 목록 조회 API 호출
        var params = {};
        params.page = (1 > pageNo ? 0 : pageNo);
        params.uniqueId = uniqueId;

        $.ajax({
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            url: '/naverPlace/naverPlace/naverPlacePlusLink/getPlaceList.sb',
            data: params,
            success: function (data) {
                console.log(JSON.stringify(data));
                if (data.status === "OK") {
                    var arr = data.data.list;
                    var innerHtml = "";
                    var innerHtmlPaging = "";

                    if (arr.length > 0) {
                        for (var i = 0; i < arr.length; i++) {

                            if (i < arr.length - 1){

                                innerHtml += "<div class=\"store-card\">";
                                innerHtml += "<div class=\"thumb\" img src=\"" + arr[i].businessImage + "\"></div>";
                                innerHtml += "<div class=\"store-info\">";
                                innerHtml += "<h3>" + arr[i].businessName + "</h3>";
                                innerHtml += "<p>" + arr[i].address + "</p>";
                                innerHtml += "<p>" + arr[i].phone + "</p>";
                                innerHtml += "</div>";
                                innerHtml += "<button class=\"btn btn-blue\">연동하기</button>";
                                innerHtml += "</div>";

                            }else{

                                // 페이징
                                var totalPages = Math.ceil(arr[i].totCnt / 10);

                                if (totalPages > 0) {
                                    innerHtmlPaging += "<button class=\"page-btn\">&laquo;</button>";
                                    for (var j = 1; j <= totalPages; j++) {
                                        innerHtmlPaging += "<button class=\"page-btn active\" onClick=\"getList(" + (j-1) + ")\">" + j + "</button>";
                                    }
                                    innerHtmlPaging += "<button class=\"page-btn\">&raquo;</button>";
                                }
                            }
                        }
                    } else {
                        innerHtml += "<div class=\"store-card-none\">";
                        innerHtml += "<span class=\"desc\">등록된 매장이 없습니다.</br>신규 등록을 진행해 주세요.</span>";
                        innerHtml += "</div>";
                    }

                    $("#divStoreList").html(innerHtml);
                    $("#divPaging").html(innerHtmlPaging);
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
        width: 200px;
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
        height: 34px;
        border-radius: 4px;
        border: 1px solid #ddd;
        background: #fff;
        cursor: pointer;
    }

    .page-btn.active {
        background: #3b82f6;
        color: #fff;
        border-color: #3b82f6;
    }

</style>