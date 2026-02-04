<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="naverPlaceLinkCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">네이버플레이스 연동</a>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn1" ng-click="btn1();">인증 API Access Token 조회</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn2" onclick="naverLoginPopup();">네이버 로그인</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn3" ng-click="btn3();">동의여부확인 API 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn4" onclick="naverTermsPopup();">동의여부화면 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn5" ng-click="btn5();">업체목록조회 API 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn6" onclick="naverChgOwnerPopup();">플레이스 주인권한 변경 화면 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn7" ng-click="btn7();">업체 수정/등록 API 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn8" ng-click="btn8();">연동 추가 API</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn9" ng-click="btn9();">연동 해지 API</button>
    </div>


</div>

<script type="text/javascript">

    // 네이버 로그인 팝업창
    function naverLoginPopup() {
        var clientId = "7GIne8NXVpOnxoKv9ecI";      // 발급받은 clientId
        var redirectUrl = "http://" + window.location.host + "/NaverLoginCallback";
        //var redirectUrl = "http://" + window.location.host + "/naverPlace/naverPlace/naverPlaceLink/saveNaverUniqueId.sb";
        var popupUrl = 'https://nid.naver.com/oauth2.0/authorize?' +
            'response_type=code' +                  // 인증과정에 대한 내부 구분값 code 로 전공 (고정값)
            '&client_id=' + clientId +
            '&state=' + generateState() +           // CORS를 방지하기 위한 특정 토큰값 (임의값 사용)
            '&redirect_uri=' + redirectUrl;   // 어플케이션에서 등록했던 CallBack URL를 입력

        window.open(popupUrl, "popup", "width=600, height=1000");
    }

    function generateState() {
        const bytes = new Uint8Array(16);
        crypto.getRandomValues(bytes);

        const base64 = btoa(String.fromCharCode(...bytes));
        return base64.replace(/\+/g, "-").replace(/\//g, "_").replace(/=+$/, "");
    }

    // 네이버 동의여부화면 호출
    function naverTermsPopup() {
        var redirectUrl = "http://" + window.location.host + "/naverPlace/naverPlace/naverPlaceLink/view.sb";
        var popupUrl = "https://new.smartplace.naver.com/embed/terms?service=lynk_pos&to=" + redirectUrl;
        window.open(popupUrl, "popup", "width=600, height=1000");
    }

    // 플레이스 주인권한 변경
    function naverChgOwnerPopup() {
        var redirectUrl = "http://" + window.location.host + "/naverPlace/naverPlace/naverPlaceLink/view.sb";
        var popupUrl = "https://new.smartplace.naver.com/bizes/lookup?to=" + encodeURIComponent(redirectUrl);
        window.open(popupUrl, "popup", "width=600, height=1000");
    }

</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverPlaceLink/naverPlaceLink.js?ver=20260126.01" charset="utf-8"></script>

<style>
    /* 단순버튼 01 */
    .btn01 {
        box-sizing: border-box;
        appearance: none;
        background-color: #2c3e50;
        border: 2px solid blue;
        border-radius: 0.6em;
        color: #fff;
        cursor: pointer;
        display: flex;
        align-self: center;
        font-size: 1rem;
        font-weight: 400;
        line-height: 1;
        margin: 20px 200px 20px 200px;
        padding: 1.2em 2.8em;
        text-decoration: none;
        text-align: center;
        text-transform: uppercase;
        font-family: 'Montserrat', sans-serif;
        justify-content: center;
        align-items: center;
    }

    .btn01:hover {
        color: #fff;
        outline: 0;
    }

    .btn01:focus {
        color: #fff;
        outline: 0;
    }

    .first01 {
        transition: box-shadow 300ms ease-in-out, color 300ms ease-in-out;
    }

    .first01:hover {
        box-shadow: 0 0 40px 40px blue inset;
    }
</style>