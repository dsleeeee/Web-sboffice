<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<head>
<style>
    .divOne * {
        text-align: center;
        margin: 5px 5px 5px 5px;
    }
    .divOne input,
    .divOne > button {
        display: block;
        margin: 0 auto;
        width: 90%;
    }
    .keyPad > div >button {
        background-color: white;
        border:1px solid #ccc;
        width:25%;
        height:100%;
    }
    .keyPad > div {
        height:20%;
    }
    .divOne input{
        border:2px solid #222;
    }
    .divOne h1{
        margin-bottom: 15px;
    }

</style>
</head>
<wj-popup id="menuPasswordChkLayer" control="menuPasswordChkLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:400px; height:450px;">
    <div style="width: 100%; height:100%" ng-controller="menuPasswordChkCtrl">
            <%-- header --%>
            <div class="wj-dialog-header wj-dialog-header-font">
                <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
            </div>

            <%-- body --%>
            <div style="width: 100%; height:100%" class="wj-dialog-body divOne">

                <h1>매출 조회 로그인</h1>
                <div style="font-size: 12px;">비밀번호 4자리를 입력 해주세요</div>

                <!-- 비밀번호 입력창 -->
                <input class="sb-input" type="password" id="pwdTxt" ng-model="password" maxlength="4" autocomplete="new-password"/>
                <p class="s14 mt5 lh15 red" id="chkMsg"></p>

                <!-- 숫자 키패드 -->
                <div style="height:50%;width:100%;" class="keyPad">
                    <div>
                        <button ng-click="pressKey('7')">7</button>
                        <button ng-click="pressKey('8')">8</button>
                        <button ng-click="pressKey('9')">9</button>
                    </div>
                    <div>
                        <button ng-click="pressKey('4')">4</button>
                        <button ng-click="pressKey('5')">5</button>
                        <button ng-click="pressKey('6')">6</button>
                    </div>
                    <div>
                        <button ng-click="pressKey('1')">1</button>
                        <button ng-click="pressKey('2')">2</button>
                        <button ng-click="pressKey('3')">3</button>
                    </div>
                    <div>
                        <button ng-click="clearPwd()">CLR</button>
                        <button ng-click="pressKey('0')">0</button>
                        <button ng-click="backSpace()">←</button>
                    </div>
                </div>

                <!-- 로그인 버튼 -->
                <button class="btn_blue" ng-click="loginPwdChk()">로그인</button>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/layout/basic/menuPasswordChk.js?ver=20250722.01" charset="utf-8"></script>
