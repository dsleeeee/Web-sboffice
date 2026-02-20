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
        <button class="btn01 first01" id="btn2" ng-click="btn2();">네이버 로그인</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn3" ng-click="btn3();">동의여부확인 API 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn4" ng-click="btn4();">동의화면 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn5" ng-click="btn5();">업체목록조회 API 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn6" ng-click="btn6();">플레이스 주인권한 변경 화면 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn7" ng-click="btn7();">업체 등록/수정 API 호출</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn8" ng-click="btn8();">연동 추가 API</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn9" ng-click="btn9();">연동 해지 API</button>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btn10" ng-click="btn10();">업종 조회 API</button>
    </div>
</div>

<script type="text/javascript">

    // state 값 생성 (네이버로그인후 기존세션 확인을 위한 임의값)
    function generateState() {
        const bytes = new Uint8Array(16);
        crypto.getRandomValues(bytes);

        const base64 = btoa(String.fromCharCode(...bytes));
        return base64.replace(/\+/g, "-").replace(/\//g, "_").replace(/=+$/, "");
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverPlaceLink/naverPlaceLink.js?ver=20260220.01" charset="utf-8"></script>

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