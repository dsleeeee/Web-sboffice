<!--
//==================================================================================
// 프로그램명 : 이모티콘 모음
// 작      성 : 2021-06-10 김설아
// 비      고 :
//----------------------------------------------------------------------------------
// 수      정 :
//==================================================================================
-->
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style>
            element.style {
                color:#666666;
                font-family:"굴림";
                font-size:9pt;
                line-height:17px;
                text-decoration:none;
            }
            body {
                /*color:#666666;*/
                font-family:"굴림";
                /*font-size:9pt;*/
                line-height:17px;
                text-decoration:none;
            }
            .emoWrap01 {
                border: 1px solid #CDCDCD;
                color: #000000;
                font-family: dotum;
                height: 130px;
                line-height: 1.25em;
                padding: 6px 0 0 2px;
                width: 225px;
            }

            a:link {
                /*color: #666666;*/
                text-decoration: none;
            } .emoWrap01 a {
                    font-size:9pt;
                    color: #000000;
                    margin: 0 0 0 3px;
                } .emoWrap02 {
                        font-size:9pt;
                        background: #FFFFFF none repeat scroll 0 0;
                        border: 1px solid #CDCDCD;
                        color: #000000;
                        font-family: dotum;
                        height: 130px;
                        margin: 5px 0 0 0;
                        width: 225px;
                    } .tab02_sub {
                            background: #F3F3F3 none repeat scroll 0 0;
                            font-family: dotum;
                            font-size: 8pt;
                            height: 19px;
                            margin: 0 0 5px;
                            padding: 5px 0 0;
                        } .tab02_sub a {
                                margin: 0 0 0 8px;
                                color: #666666;
                            } .emoWrap02 span {
                                    display: block;
                                    line-height: 1.3em;
                                } .emoWrap02 span span {
                                        float: left;
                                        padding: 0 10px 0 5px;
                                        white-space: nowrap;
                                    } .emoWrap02 span span a {
                                            color: #666666;
                                        }
        </style>
    </head>

    <script type="text/javascript">
        // 문자 삽입
        function addMsgEmo(value, pageName)
        {
            var smsSendScope = agrid.getScope('smsSendCtrl'); // SMS전송(회원정보, 문자전송현황)
            smsSendScope.addMsg(value);

            // if(pageName == "000316") {
            //     var msgManageScope = agrid.getScope('msgManageDtlCtrl'); // 메세지관리
            //     msgManageScope.addMsg(value);
            // } else if(pageName == "000317") {
            //     var smsSendScope = agrid.getScope('smsSendCtrl'); // SMS전송(문자전송현황)
            //     smsSendScope.addMsg(value);
            // } else if(pageName == "000203") {
            //     var smsSendScope = agrid.getScope('smsSendCtrl'); // SMS전송(회원정보)
            //     smsSendScope.addMsg(value);
            // } else {
            //     var marketingSmsSendScope = agrid.getScope('marketingSmsSendCtrl'); // 마케팅용 SMS전송
            //     marketingSmsSendScope.addMsg(value);
            // }
        }

        // 이모티콘 탭 변경
        function emoChange(value)
        {
            if(value == "0") {
                // 이모티콘
                $("#emos0").css("display", "");
                $("#emos1").css("display", "none");
                $("#emos2").css("display", "none");
                $("#emos3").css("display", "none");
                $("#emos4").css("display", "none");
                $("#emos5").css("display", "none");
                $("#emos6").css("display", "none");
                // 탭 색상
                $("#tab0").css("color", "#ff6600");
                $("#tab1").css("color", "#666666");
                $("#tab2").css("color", "#666666");
                $("#tab3").css("color", "#666666");
                $("#tab4").css("color", "#666666");
                $("#tab5").css("color", "#666666");
                $("#tab6").css("color", "#666666");
            } else if(value == "1") {
                // 이모티콘
                $("#emos0").css("display", "none");
                $("#emos1").css("display", "");
                $("#emos2").css("display", "none");
                $("#emos3").css("display", "none");
                $("#emos4").css("display", "none");
                $("#emos5").css("display", "none");
                $("#emos6").css("display", "none");
                // 탭 색상
                $("#tab0").css("color", "#666666");
                $("#tab1").css("color", "#ff6600");
                $("#tab2").css("color", "#666666");
                $("#tab3").css("color", "#666666");
                $("#tab4").css("color", "#666666");
                $("#tab5").css("color", "#666666");
                $("#tab6").css("color", "#666666");
            } else if(value == "2") {
                // 이모티콘
                $("#emos0").css("display", "none");
                $("#emos1").css("display", "none");
                $("#emos2").css("display", "");
                $("#emos3").css("display", "none");
                $("#emos4").css("display", "none");
                $("#emos5").css("display", "none");
                $("#emos6").css("display", "none");
                // 탭 색상
                $("#tab0").css("color", "#666666");
                $("#tab1").css("color", "#666666");
                $("#tab2").css("color", "#ff6600");
                $("#tab3").css("color", "#666666");
                $("#tab4").css("color", "#666666");
                $("#tab5").css("color", "#666666");
                $("#tab6").css("color", "#666666");
            } else if(value == "3") {
                // 이모티콘
                $("#emos0").css("display", "none");
                $("#emos1").css("display", "none");
                $("#emos2").css("display", "none");
                $("#emos3").css("display", "");
                $("#emos4").css("display", "none");
                $("#emos5").css("display", "none");
                $("#emos6").css("display", "none");
                // 탭 색상
                $("#tab0").css("color", "#666666");
                $("#tab1").css("color", "#666666");
                $("#tab2").css("color", "#666666");
                $("#tab3").css("color", "#ff6600");
                $("#tab4").css("color", "#666666");
                $("#tab5").css("color", "#666666");
                $("#tab6").css("color", "#666666");
            } else if(value == "4") {
                // 이모티콘
                $("#emos0").css("display", "none");
                $("#emos1").css("display", "none");
                $("#emos2").css("display", "none");
                $("#emos3").css("display", "none");
                $("#emos4").css("display", "");
                $("#emos5").css("display", "none");
                $("#emos6").css("display", "none");
                // 탭 색상
                $("#tab0").css("color", "#666666");
                $("#tab1").css("color", "#666666");
                $("#tab2").css("color", "#666666");
                $("#tab3").css("color", "#666666");
                $("#tab4").css("color", "#ff6600");
                $("#tab5").css("color", "#666666");
                $("#tab6").css("color", "#666666");
            } else if(value == "5") {
                // 이모티콘
                $("#emos0").css("display", "none");
                $("#emos1").css("display", "none");
                $("#emos2").css("display", "none");
                $("#emos3").css("display", "none");
                $("#emos4").css("display", "none");
                $("#emos5").css("display", "");
                $("#emos6").css("display", "none");
                // 탭 색상
                $("#tab0").css("color", "#666666");
                $("#tab1").css("color", "#666666");
                $("#tab2").css("color", "#666666");
                $("#tab3").css("color", "#666666");
                $("#tab4").css("color", "#666666");
                $("#tab5").css("color", "#ff6600");
                $("#tab6").css("color", "#666666");
            } else if(value == "6") {
                // 이모티콘
                $("#emos0").css("display", "none");
                $("#emos1").css("display", "none");
                $("#emos2").css("display", "none");
                $("#emos3").css("display", "none");
                $("#emos4").css("display", "none");
                $("#emos5").css("display", "none");
                $("#emos6").css("display", "");
                // 탭 색상
                $("#tab0").css("color", "#666666");
                $("#tab1").css("color", "#666666");
                $("#tab2").css("color", "#666666");
                $("#tab3").css("color", "#666666");
                $("#tab4").css("color", "#666666");
                $("#tab5").css("color", "#666666");
                $("#tab6").css("color", "#ff6600");
            }
        }
    </script>

    <body>
        <div style="margin:0px;">
            <div class="emoWrap01">
                <a href="#" onclick="addMsgEmo('★', '${menuCd}');">★</a>
                <a href="#" onclick="addMsgEmo('☆', '${menuCd}');">☆</a>
                <a href="#" onclick="addMsgEmo('☎', '${menuCd}');">☎</a>
                <a href="#" onclick="addMsgEmo('☏', '${menuCd}');">☏</a>
                <a href="#" onclick="addMsgEmo('☜', '${menuCd}');">☜</a>
                <a href="#" onclick="addMsgEmo('☞', '${menuCd}');">☞</a>
                <a href="#" onclick="addMsgEmo('※', '${menuCd}');">※</a>
                <a href="#" onclick="addMsgEmo('♥', '${menuCd}');">♥</a>
                <a href="#" onclick="addMsgEmo('♡', '${menuCd}');">♡</a>
                <a href="#" onclick="addMsgEmo('♣', '${menuCd}');">♣</a>
                <a href="#" onclick="addMsgEmo('♠', '${menuCd}');">♠</a>
                <a href="#" onclick="addMsgEmo('♤', '${menuCd}');">♤</a>
                <a href="#" onclick="addMsgEmo('♧', '${menuCd}');">♧</a>
                <a href="#" onclick="addMsgEmo('♨', '${menuCd}');">♨</a>
                <a href="#" onclick="addMsgEmo('ㆀ', '${menuCd}');">ㆀ</a>
                <a href="#" onclick="addMsgEmo('♩', '${menuCd}');">♩</a>
                <%--<a href="#" onclick="addMsgEmo('♪', '${menuCd}');">♪</a>--%>
                <a href="#" onclick="addMsgEmo('♬', '${menuCd}');">♬</a>
                <a href="#" onclick="addMsgEmo('■', '${menuCd}');">■</a>
                <a href="#" onclick="addMsgEmo('□', '${menuCd}');">□</a>
                <a href="#" onclick="addMsgEmo('▣', '${menuCd}');">▣</a>
                <a href="#" onclick="addMsgEmo('▤', '${menuCd}');">▤</a>
                <a href="#" onclick="addMsgEmo('▦', '${menuCd}');">▦</a>
                <a href="#" onclick="addMsgEmo('▨', '${menuCd}');">▨</a>
                <a href="#" onclick="addMsgEmo('▩', '${menuCd}');">▩</a>
                <a href="#" onclick="addMsgEmo('▒', '${menuCd}');">▒</a>
                <a href="#" onclick="addMsgEmo('▲', '${menuCd}');">▲</a>
                <a href="#" onclick="addMsgEmo('△', '${menuCd}');">△</a>
                <a href="#" onclick="addMsgEmo('▶', '${menuCd}');">▶</a>
                <a href="#" onclick="addMsgEmo('▷', '${menuCd}');">▷</a>
                <a href="#" onclick="addMsgEmo('▼', '${menuCd}');">▼</a>
                <a href="#" onclick="addMsgEmo('▽', '${menuCd}');">▽</a>
                <a href="#" onclick="addMsgEmo('◀', '${menuCd}');">◀</a>
                <a href="#" onclick="addMsgEmo('◁', '${menuCd}');">◁</a>
                <a href="#" onclick="addMsgEmo('◆', '${menuCd}');">◆</a>
                <a href="#" onclick="addMsgEmo('◇', '${menuCd}');">◇</a>
                <a href="#" onclick="addMsgEmo('◈', '${menuCd}');">◈</a>
                <a href="#" onclick="addMsgEmo('●', '${menuCd}');">●</a>
                <a href="#" onclick="addMsgEmo('○', '${menuCd}');">○</a>
                <a href="#" onclick="addMsgEmo('◎', '${menuCd}');">◎</a>
                <a href="#" onclick="addMsgEmo('⊙', '${menuCd}');">⊙</a>
                <a href="#" onclick="addMsgEmo('◐', '${menuCd}');">◐</a>
                <a href="#" onclick="addMsgEmo('◑', '${menuCd}');">◑</a>
                <a href="#" onclick="addMsgEmo('←', '${menuCd}');">←</a>
                <a href="#" onclick="addMsgEmo('↑', '${menuCd}');">↑</a>
                <a href="#" onclick="addMsgEmo('→', '${menuCd}');">→</a>
                <a href="#" onclick="addMsgEmo('↓', '${menuCd}');">↓</a>
                <a href="#" onclick="addMsgEmo('⇒', '${menuCd}');">⇒</a>
                <a href="#" onclick="addMsgEmo('§', '${menuCd}');">§</a>
                <a href="#" onclick="addMsgEmo('Ø', '${menuCd}');">Ø</a>
                <a href="#" onclick="addMsgEmo('∀', '${menuCd}');">∀</a>
                <a href="#" onclick="addMsgEmo('∃', '${menuCd}');">∃</a>
                <a href="#" onclick="addMsgEmo('∏', '${menuCd}');">∏</a>
                <a href="#" onclick="addMsgEmo('∞', '${menuCd}');">∞</a>
                <a href="#" onclick="addMsgEmo('∧', '${menuCd}');">∧</a>
                <a href="#" onclick="addMsgEmo('∪', '${menuCd}');">∪</a>
                <a href="#" onclick="addMsgEmo('∬', '${menuCd}');">∬</a>
                <a href="#" onclick="addMsgEmo('∴', '${menuCd}');">∴</a>
                <a href="#" onclick="addMsgEmo('∽', '${menuCd}');">∽</a>
                <a href="#" onclick="addMsgEmo('≠', '${menuCd}');">≠</a>
                <a href="#" onclick="addMsgEmo('⊃', '${menuCd}');">⊃</a>
                <a href="#" onclick="addMsgEmo('￠', '${menuCd}');">￠</a>
                <a href="#" onclick="addMsgEmo('￥', '${menuCd}');">￥</a>
            </div>
            <div class="emoWrap02">
                <div class="tab02_sub">
                    <a href="#" onclick="emoChange('0')" id="tab0" style="color:#ff6600;">웃음</a>
                    <a href="#" onclick="emoChange('1')" id="tab1">사랑</a>
                    <a href="#" onclick="emoChange('2')" id="tab2">놀람</a>
                    <a href="#" onclick="emoChange('3')" id="tab3">슬픔</a>
                    <a href="#" onclick="emoChange('4')" id="tab4">피곤</a>
                    <a href="#" onclick="emoChange('5')" id="tab5">화남</a>
                    <a href="#" onclick="emoChange('6')" id="tab6">기타</a>
                </div>
                <span id="emos0" style="display:inline">
                    <span><a href="#" onclick="addMsgEmo('n.n', '${menuCd}');">n.n</a></span>
                    <span><a href="#" onclick="addMsgEmo('^Δ^', '${menuCd}');">^Δ^</a></span>
                    <span><a href="#" onclick="addMsgEmo('^v^', '${menuCd}');">^v^</a></span>
                    <span><a href="#" onclick="addMsgEmo('^.^', '${menuCd}');">^.^</a></span>
                    <span><a href="#" onclick="addMsgEmo('^_^', '${menuCd}');">^_^</a></span>
                    <span><a href="#" onclick="addMsgEmo('^0^', '${menuCd}');">^0^</a></span>
                    <span><a href="#" onclick="addMsgEmo('^L^', '${menuCd}');">^L^</a></span>
                    <span><a href="#" onclick="addMsgEmo('⌒⌒', '${menuCd}');">⌒⌒</a></span>
                    <span><a href="#" onclick="addMsgEmo('^.~', '${menuCd}');">^.~</a></span>
                    <span><a href="#" onclick="addMsgEmo('^ε^', '${menuCd}');">^ε^</a></span>
                    <span><a href="#" onclick="addMsgEmo('^-^b', '${menuCd}');">^-^b</a></span>
                    <span><a href="#" onclick="addMsgEmo('*^^*', '${menuCd}');">*^^*</a></span>
                    <span><a href="#" onclick="addMsgEmo('^▽^', '${menuCd}');">^▽^</a></span>
                    <span><a href="#" onclick="addMsgEmo('=^.^=', '${menuCd}');">=^.^=</a></span>
                    <span><a href="#" onclick="addMsgEmo('(^^)γ', '${menuCd}');">(^^)γ</a></span>
                    <span><a href="#" onclick="addMsgEmo('&amp;lt;^O^&amp;gt;', '${menuCd}');">&lt;^O^&gt;</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*^-^)', '${menuCd}');">(*^-^)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*^o^*)', '${menuCd}');">(*^o^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo('^o^~~♬', '${menuCd}');">^o^~~♬</a></span>
                    <span><a href="#" onclick="addMsgEmo('☞^.^☜', '${menuCd}');">☞^.^☜</a></span>
                    <span><a href="#" onclick="addMsgEmo('o(^-^)o', '${menuCd}');">o(^-^)o</a></span>
                    <span><a href="#" onclick="addMsgEmo('S(^.^)b', '${menuCd}');">S(^.^)b</a></span>
                    <span><a href="#" onclick="addMsgEmo('(￣∇￣)', '${menuCd}');">(￣∇￣)</a></span>
                    <span><a href="#" onclick="addMsgEmo('♬(^0^)~♩', '${menuCd}');">♬(^0^)~♩</a></span>
                    <span><a href="#" onclick="addMsgEmo('s(￣▽￣)/', '${menuCd}');">s(￣▽￣)/</a></span>
                </span>
                <span id="emos1" style="display:none">
                    <span><a href="#" onclick="addMsgEmo('♥.♥', '${menuCd}');">♥.♥</a></span>
                    <span><a href="#" onclick="addMsgEmo('♡.♡', '${menuCd}');">♡.♡</a></span>
                    <span><a href="#" onclick="addMsgEmo('*♥o♥*', '${menuCd}');">*♥o♥*</a></span>
                    <span><a href="#" onclick="addMsgEmo('(~.^)s', '${menuCd}');">(~.^)s</a></span>
                    <span><a href="#" onclick="addMsgEmo('☞♡☜', '${menuCd}');">☞♡☜</a></span>
                    <span><a href="#" onclick="addMsgEmo('γ^ε^γ', '${menuCd}');">γ^ε^γ</a></span>
                    <span><a href="#" onclick="addMsgEmo('(♡.♡)', '${menuCd}');">(♡.♡)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*`0`*)', '${menuCd}');">(*`0`*)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(⌒ε⌒*)', '${menuCd}');">(⌒ε⌒*)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*^}{^*)', '${menuCd}');">(*^}{^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo('づ^0^)づ', '${menuCd}');">づ^0^)づ</a></span>
                    <span><a href="#" onclick="addMsgEmo('ⓛⓞⓥⓔ', '${menuCd}');">ⓛⓞⓥⓔ</a></span>
                    <span><a href="#" onclick="addMsgEmo('☜(^^*)☞', '${menuCd}');">☜(^^*)☞</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*^3(^^*)', '${menuCd}');">(*^3(^^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(^*^)kiss', '${menuCd}');">(^*^)kiss</a></span>
                    <span><a href="#" onclick="addMsgEmo('(つ＾з＾)つ', '${menuCd}');">(つ＾з＾)つ</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*⌒.^)(^ε⌒*)', '${menuCd}');">(*⌒.^)(^ε⌒*)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*^-^)♡(^o^*)', '${menuCd}');">(*^-^)♡(^o^*)</a></span>
                </span>
                <span id="emos2" style="display:none">
                    <span><a href="#" onclick="addMsgEmo('?o?', '${menuCd}');">?o?</a></span>
                    <span><a href="#" onclick="addMsgEmo('O_O', '${menuCd}');">O_O</a></span>
                    <span><a href="#" onclick="addMsgEmo('@_@', '${menuCd}');">@_@</a></span>
                    <span><a href="#" onclick="addMsgEmo('!.!', '${menuCd}');">!.!</a></span>
                    <span><a href="#" onclick="addMsgEmo('⊙⊙', '${menuCd}');">⊙⊙</a></span>
                    <span><a href="#" onclick="addMsgEmo('(o_o)', '${menuCd}');">(o_o)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(@.@)', '${menuCd}');">(@.@)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(\'o\')', '${menuCd}');">('o')</a></span>
                    <span><a href="#" onclick="addMsgEmo('★.★', '${menuCd}');">★.★</a></span>
                    <span><a href="#" onclick="addMsgEmo('☆_☆', '${menuCd}');">☆_☆</a></span>
                    <span><a href="#" onclick="addMsgEmo('⊙.⊙', '${menuCd}');">⊙.⊙</a></span>
                    <span><a href="#" onclick="addMsgEmo('☆_☆', '${menuCd}');">☆_☆</a></span>
                    <span><a href="#" onclick="addMsgEmo('☞_☜', '${menuCd}');">☞_☜</a></span>
                    <span><a href="#" onclick="addMsgEmo('(o)(o)', '${menuCd}');">(o)(o)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(=_=;)', '${menuCd}');">(=_=;)</a></span>
                    <span><a href="#" onclick="addMsgEmo('⊙⊙ㆀ', '${menuCd}');">⊙⊙ㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo('ㆀ-_-ㆀ', '${menuCd}');">ㆀ-_-ㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo('ご,.ごㆀ', '${menuCd}');">ご,.ごㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo('ㅡ..ㅡㆀ', '${menuCd}');">ㅡ..ㅡㆀ</a></span>
                </span>
                <span id="emos3" style="display:none">
                    <span><a href="#" onclick="addMsgEmo('T.T', '${menuCd}');">T.T</a></span>
                    <span><a href="#" onclick="addMsgEmo(';_;', '${menuCd}');">;_;</a></span>
                    <span><a href="#" onclick="addMsgEmo('TmT', '${menuCd}');">TmT</a></span>
                    <span><a href="#" onclick="addMsgEmo('Θ_Θ', '${menuCd}');">Θ_Θ</a></span>
                    <span><a href="#" onclick="addMsgEmo('ㅠ.ㅠ', '${menuCd}');">ㅠ.ㅠ</a></span>
                    <span><a href="#" onclick="addMsgEmo('ㅜ.ㅜ', '${menuCd}');">ㅜ.ㅜ</a></span>
                    <span><a href="#" onclick="addMsgEmo('ㅡ.ㅜ', '${menuCd}');">ㅡ.ㅜ</a></span>
                    <span><a href="#" onclick="addMsgEmo('(v.v)', '${menuCd}');">(v.v)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(∏.∏)', '${menuCd}');">(∏.∏)</a></span>
                    <span><a href="#" onclick="addMsgEmo('ご.ご', '${menuCd}');">ご.ご</a></span>
                    <span><a href="#" onclick="addMsgEmo('(X_X)', '${menuCd}');">(X_X)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(&amp;gt;_&amp;lt;)', '${menuCd}');">(&gt;_&lt;)</a></span>
                    <span><a href="#" onclick="addMsgEmo('+_+;;', '${menuCd}');">+_+;;</a></span>
                    <span><a href="#" onclick="addMsgEmo('(づ_T)', '${menuCd}');">(づ_T)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(づ_ど)', '${menuCd}');">(づ_ど)</a></span>
                    <span><a href="#" onclick="addMsgEmo('o(T^T)o', '${menuCd}');">o(T^T)o</a></span>
                    <span><a href="#" onclick="addMsgEmo('(ㅜ.ㅜ)', '${menuCd}');">(ㅜ.ㅜ)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(ㅠ.ㅠ)', '${menuCd}');">(ㅠ.ㅠ)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(♨_♨)', '${menuCd}');">(♨_♨)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(T(oo)T)', '${menuCd}');">(T(oo)T)</a></span>
                </span>
                <span id="emos4" style="display:none">
                    <span><a href="#" onclick="addMsgEmo('=.=', '${menuCd}');">=.=</a></span>
                    <span><a href="#" onclick="addMsgEmo('θ.θ', '${menuCd}');">θ.θ</a></span>
                    <span><a href="#" onclick="addMsgEmo('@L@', '${menuCd}');">@L@</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-0-)', '${menuCd}');">(-0-)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(@.@)', '${menuCd}');">(@.@)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(Z_Z)', '${menuCd}');">(Z_Z)</a></span>
                    <span><a href="#" onclick="addMsgEmo('∋.∈', '${menuCd}');">∋.∈</a></span>
                    <span><a href="#" onclick="addMsgEmo('⊇.⊆', '${menuCd}');">⊇.⊆</a></span>
                    <span><a href="#" onclick="addMsgEmo('(u_u)', '${menuCd}');">(u_u)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(g_g)', '${menuCd}');">(g_g)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(づ_-)', '${menuCd}');">(づ_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-_ど)', '${menuCd}');">(-_ど)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(=.&amp;amp;=)', '${menuCd}');">(=.&amp;=)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-.-)Zzz..', '${menuCd}');">(-.-)Zzz..</a></span>
                    <span><a href="#" onclick="addMsgEmo('[(￣.￣)]zZ', '${menuCd}');">[(￣.￣)]zZ</a></span>
                </span>
                <span id="emos5" style="display:none">
                    <span><a href="#" onclick="addMsgEmo(':-&amp;lt;', '${menuCd}');">:-&lt;</a></span>
                    <span><a href="#" onclick="addMsgEmo('ㅡㅡ+', '${menuCd}');">ㅡㅡ+</a></span>
                    <span><a href="#" onclick="addMsgEmo('(`o\')', '${menuCd}');">(`o')</a></span>
                    <span><a href="#" onclick="addMsgEmo('(#_-)', '${menuCd}');">(#_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo('ㅡㅡ^', '${menuCd}');">ㅡㅡ^</a></span>
                    <span><a href="#" onclick="addMsgEmo('(@_@)', '${menuCd}');">(@_@)</a></span>
                    <span><a href="#" onclick="addMsgEmo('☞_☜', '${menuCd}');">☞_☜</a></span>
                    <span><a href="#" onclick="addMsgEmo('s(￣へ￣ )z', '${menuCd}');">s(￣へ￣ )z</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-.￥)', '${menuCd}');">(-.￥)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(｀へ´)', '${menuCd}');">(｀へ´)</a></span>
                    <span><a href="#" onclick="addMsgEmo('o(-&amp;quot;-)o', '${menuCd}');">o(-&quot;-)o</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-_-メ)', '${menuCd}');">(-_-メ)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-&amp;quot;-メ)', '${menuCd}');">(-&quot;-メ)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(↗∇↖)', '${menuCd}');">(↗∇↖)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-(oo)-)', '${menuCd}');">(-(oo)-)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-.-&amp;quot;)凸', '${menuCd}');">(-.-&quot;)凸</a></span>
                    <span><a href="#" onclick="addMsgEmo('(*｀Д´)/', '${menuCd}');">(*｀Д´)/</a></span>
                    <span><a href="#" onclick="addMsgEmo('(/ㅡ_-)/~', '${menuCd}');">(/ㅡ_-)/~</a></span>
                    <span><a href="#" onclick="addMsgEmo('＼(*｀Д´)/', '${menuCd}');">＼(*｀Д´)/</a></span>
                    <span><a href="#" onclick="addMsgEmo('(-ヘㅡメ)', '${menuCd}');">(-ヘㅡメ)</a></span>
                </span>
                <span id="emos6" style="display:none">
                    <span><a href="#" onclick="addMsgEmo('(-_-)', '${menuCd}');">(-_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo('($_$)', '${menuCd}');">($_$)</a></span>
                    <span><a href="#" onclick="addMsgEmo('( ㅅ )', '${menuCd}');">( ㅅ )</a></span>
                    <span><a href="#" onclick="addMsgEmo('(\'⌒\')', '${menuCd}');">('⌒')</a></span>
                    <span><a href="#" onclick="addMsgEmo('ご,,ご', '${menuCd}');">ご,,ご</a></span>
                    <span><a href="#" onclick="addMsgEmo('o(&amp;gt;_&amp;lt;)o', '${menuCd}');">o(&gt;_&lt;)o</a></span>
                    <span><a href="#" onclick="addMsgEmo('(\'º\')/)/)', '${menuCd}');">('º')/)/)</a></span>
                    <span><a href="#" onclick="addMsgEmo('( ㅅ)=333', '${menuCd}');">( ㅅ)=333</a></span>
                    <span><a href="#" onclick="addMsgEmo('∠(- o -)', '${menuCd}');">∠(- o -)</a></span>
                    <span><a href="#" onclick="addMsgEmo('☞(&amp;gt;.&amp;lt;)☜', '${menuCd}');">☞(&gt;.&lt;)☜</a></span>
                    <span><a href="#" onclick="addMsgEmo('&amp;lt;(&amp;gt;.&amp;lt;ㆀ)&amp;gt;', '${menuCd}');">&lt;(&gt;.&lt;ㆀ)&gt;</a></span>
                    <span><a href="#" onclick="addMsgEmo('┌(ㆀ_ _)┐', '${menuCd}');">┌(ㆀ_ _)┐</a></span>
                    <span><a href="#" onclick="addMsgEmo('s(ごoご)グ', '${menuCd}');">s(ごoご)グ</a></span>
                    <span><a href="#" onclick="addMsgEmo('(^(**)^))~', '${menuCd}');">(^(**)^))~</a></span>
                    <span><a href="#" onclick="addMsgEmo('(. .)|/)/)', '${menuCd}');">(. .)|/)/)</a></span>
                    <span><a href="#" onclick="addMsgEmo('(\'\' )( \'\')', '${menuCd}');">('' )( '')</a></span>
                    <span><a href="#" onclick="addMsgEmo('(=`.`=)@@', '${menuCd}');">(=`.`=)@@</a></span>
                </span>
            </div>
        </div>
    </body>
</html>