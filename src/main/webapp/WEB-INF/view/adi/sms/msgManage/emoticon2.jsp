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
        function addMsgEmo2(value)
        {
            var msgManageScope = agrid.getScope('msgManageDtlCtrl'); // 메세지관리
            msgManageScope.addMsg(value);
        }

        // 이모티콘 탭 변경
        function msgManageEmoChange(value)
        {
            if(value == "0") {
                // 이모티콘
                $("#msgManageEmos0").css("display", "");
                $("#msgManageEmos1").css("display", "none");
                $("#msgManageEmos2").css("display", "none");
                $("#msgManageEmos3").css("display", "none");
                $("#msgManageEmos4").css("display", "none");
                $("#msgManageEmos5").css("display", "none");
                $("#msgManageEmos6").css("display", "none");
                // 탭 색상
                $("#msgManageTab0").css("color", "#ff6600");
                $("#msgManageTab1").css("color", "#666666");
                $("#msgManageTab2").css("color", "#666666");
                $("#msgManageTab3").css("color", "#666666");
                $("#msgManageTab4").css("color", "#666666");
                $("#msgManageTab5").css("color", "#666666");
                $("#msgManageTab6").css("color", "#666666");
            } else if(value == "1") {
                // 이모티콘
                $("#msgManageEmos0").css("display", "none");
                $("#msgManageEmos1").css("display", "");
                $("#msgManageEmos2").css("display", "none");
                $("#msgManageEmos3").css("display", "none");
                $("#msgManageEmos4").css("display", "none");
                $("#msgManageEmos5").css("display", "none");
                $("#msgManageEmos6").css("display", "none");
                // 탭 색상
                $("#msgManageTab0").css("color", "#666666");
                $("#msgManageTab1").css("color", "#ff6600");
                $("#msgManageTab2").css("color", "#666666");
                $("#msgManageTab3").css("color", "#666666");
                $("#msgManageTab4").css("color", "#666666");
                $("#msgManageTab5").css("color", "#666666");
                $("#msgManageTab6").css("color", "#666666");
            } else if(value == "2") {
                // 이모티콘
                $("#msgManageEmos0").css("display", "none");
                $("#msgManageEmos1").css("display", "none");
                $("#msgManageEmos2").css("display", "");
                $("#msgManageEmos3").css("display", "none");
                $("#msgManageEmos4").css("display", "none");
                $("#msgManageEmos5").css("display", "none");
                $("#msgManageEmos6").css("display", "none");
                // 탭 색상
                $("#msgManageTab0").css("color", "#666666");
                $("#msgManageTab1").css("color", "#666666");
                $("#msgManageTab2").css("color", "#ff6600");
                $("#msgManageTab3").css("color", "#666666");
                $("#msgManageTab4").css("color", "#666666");
                $("#msgManageTab5").css("color", "#666666");
                $("#msgManageTab6").css("color", "#666666");
            } else if(value == "3") {
                // 이모티콘
                $("#msgManageEmos0").css("display", "none");
                $("#msgManageEmos1").css("display", "none");
                $("#msgManageEmos2").css("display", "none");
                $("#msgManageEmos3").css("display", "");
                $("#msgManageEmos4").css("display", "none");
                $("#msgManageEmos5").css("display", "none");
                $("#msgManageEmos6").css("display", "none");
                // 탭 색상
                $("#msgManageTab0").css("color", "#666666");
                $("#msgManageTab1").css("color", "#666666");
                $("#msgManageTab2").css("color", "#666666");
                $("#msgManageTab3").css("color", "#ff6600");
                $("#msgManageTab4").css("color", "#666666");
                $("#msgManageTab5").css("color", "#666666");
                $("#msgManageTab6").css("color", "#666666");
            } else if(value == "4") {
                // 이모티콘
                $("#msgManageEmos0").css("display", "none");
                $("#msgManageEmos1").css("display", "none");
                $("#msgManageEmos2").css("display", "none");
                $("#msgManageEmos3").css("display", "none");
                $("#msgManageEmos4").css("display", "");
                $("#msgManageEmos5").css("display", "none");
                $("#msgManageEmos6").css("display", "none");
                // 탭 색상
                $("#msgManageTab0").css("color", "#666666");
                $("#msgManageTab1").css("color", "#666666");
                $("#msgManageTab2").css("color", "#666666");
                $("#msgManageTab3").css("color", "#666666");
                $("#msgManageTab4").css("color", "#ff6600");
                $("#msgManageTab5").css("color", "#666666");
                $("#msgManageTab6").css("color", "#666666");
            } else if(value == "5") {
                // 이모티콘
                $("#msgManageEmos0").css("display", "none");
                $("#msgManageEmos1").css("display", "none");
                $("#msgManageEmos2").css("display", "none");
                $("#msgManageEmos3").css("display", "none");
                $("#msgManageEmos4").css("display", "none");
                $("#msgManageEmos5").css("display", "");
                $("#msgManageEmos6").css("display", "none");
                // 탭 색상
                $("#msgManageTab0").css("color", "#666666");
                $("#msgManageTab1").css("color", "#666666");
                $("#msgManageTab2").css("color", "#666666");
                $("#msgManageTab3").css("color", "#666666");
                $("#msgManageTab4").css("color", "#666666");
                $("#msgManageTab5").css("color", "#ff6600");
                $("#msgManageTab6").css("color", "#666666");
            } else if(value == "6") {
                // 이모티콘
                $("#msgManageEmos0").css("display", "none");
                $("#msgManageEmos1").css("display", "none");
                $("#msgManageEmos2").css("display", "none");
                $("#msgManageEmos3").css("display", "none");
                $("#msgManageEmos4").css("display", "none");
                $("#msgManageEmos5").css("display", "none");
                $("#msgManageEmos6").css("display", "");
                // 탭 색상
                $("#msgManageTab0").css("color", "#666666");
                $("#msgManageTab1").css("color", "#666666");
                $("#msgManageTab2").css("color", "#666666");
                $("#msgManageTab3").css("color", "#666666");
                $("#msgManageTab4").css("color", "#666666");
                $("#msgManageTab5").css("color", "#666666");
                $("#msgManageTab6").css("color", "#ff6600");
            }
        }
    </script>

    <body>
        <div style="margin:0px;">
            <div class="emoWrap01">
                <a href="#" onclick="addMsgEmo2('★');">★</a>
                <a href="#" onclick="addMsgEmo2('☆');">☆</a>
                <a href="#" onclick="addMsgEmo2('☎');">☎</a>
                <a href="#" onclick="addMsgEmo2('☏');">☏</a>
                <a href="#" onclick="addMsgEmo2('☜');">☜</a>
                <a href="#" onclick="addMsgEmo2('☞');">☞</a>
                <a href="#" onclick="addMsgEmo2('※');">※</a>
                <a href="#" onclick="addMsgEmo2('♥');">♥</a>
                <a href="#" onclick="addMsgEmo2('♡');">♡</a>
                <a href="#" onclick="addMsgEmo2('♣');">♣</a>
                <a href="#" onclick="addMsgEmo2('♠');">♠</a>
                <a href="#" onclick="addMsgEmo2('♤');">♤</a>
                <a href="#" onclick="addMsgEmo2('♧');">♧</a>
                <a href="#" onclick="addMsgEmo2('♨');">♨</a>
                <a href="#" onclick="addMsgEmo2('ㆀ');">ㆀ</a>
                <a href="#" onclick="addMsgEmo2('♩');">♩</a>
                <%--<a href="#" onclick="addMsgEmo2('♪');">♪</a>--%>
                <a href="#" onclick="addMsgEmo2('♬');">♬</a>
                <a href="#" onclick="addMsgEmo2('■');">■</a>
                <a href="#" onclick="addMsgEmo2('□');">□</a>
                <a href="#" onclick="addMsgEmo2('▣');">▣</a>
                <a href="#" onclick="addMsgEmo2('▤');">▤</a>
                <a href="#" onclick="addMsgEmo2('▦');">▦</a>
                <a href="#" onclick="addMsgEmo2('▨');">▨</a>
                <a href="#" onclick="addMsgEmo2('▩');">▩</a>
                <a href="#" onclick="addMsgEmo2('▒');">▒</a>
                <a href="#" onclick="addMsgEmo2('▲');">▲</a>
                <a href="#" onclick="addMsgEmo2('△');">△</a>
                <a href="#" onclick="addMsgEmo2('▶');">▶</a>
                <a href="#" onclick="addMsgEmo2('▷');">▷</a>
                <a href="#" onclick="addMsgEmo2('▼');">▼</a>
                <a href="#" onclick="addMsgEmo2('▽');">▽</a>
                <a href="#" onclick="addMsgEmo2('◀');">◀</a>
                <a href="#" onclick="addMsgEmo2('◁');">◁</a>
                <a href="#" onclick="addMsgEmo2('◆');">◆</a>
                <a href="#" onclick="addMsgEmo2('◇');">◇</a>
                <a href="#" onclick="addMsgEmo2('◈');">◈</a>
                <a href="#" onclick="addMsgEmo2('●');">●</a>
                <a href="#" onclick="addMsgEmo2('○');">○</a>
                <a href="#" onclick="addMsgEmo2('◎');">◎</a>
                <a href="#" onclick="addMsgEmo2('⊙');">⊙</a>
                <a href="#" onclick="addMsgEmo2('◐');">◐</a>
                <a href="#" onclick="addMsgEmo2('◑');">◑</a>
                <a href="#" onclick="addMsgEmo2('←');">←</a>
                <a href="#" onclick="addMsgEmo2('↑');">↑</a>
                <a href="#" onclick="addMsgEmo2('→');">→</a>
                <a href="#" onclick="addMsgEmo2('↓');">↓</a>
                <a href="#" onclick="addMsgEmo2('⇒');">⇒</a>
                <a href="#" onclick="addMsgEmo2('§');">§</a>
                <a href="#" onclick="addMsgEmo2('Ø');">Ø</a>
                <a href="#" onclick="addMsgEmo2('∀');">∀</a>
                <a href="#" onclick="addMsgEmo2('∃');">∃</a>
                <a href="#" onclick="addMsgEmo2('∏');">∏</a>
                <a href="#" onclick="addMsgEmo2('∞');">∞</a>
                <a href="#" onclick="addMsgEmo2('∧');">∧</a>
                <a href="#" onclick="addMsgEmo2('∪');">∪</a>
                <a href="#" onclick="addMsgEmo2('∬');">∬</a>
                <a href="#" onclick="addMsgEmo2('∴');">∴</a>
                <a href="#" onclick="addMsgEmo2('∽');">∽</a>
                <a href="#" onclick="addMsgEmo2('≠');">≠</a>
                <a href="#" onclick="addMsgEmo2('⊃');">⊃</a>
                <a href="#" onclick="addMsgEmo2('￠');">￠</a>
                <a href="#" onclick="addMsgEmo2('￥');">￥</a>
            </div>
            <div class="emoWrap02">
                <div class="tab02_sub">
                    <a href="#" onclick="msgManageEmoChange('0')" id="msgManageTab0" style="color:#ff6600;">웃음</a>
                    <a href="#" onclick="msgManageEmoChange('1')" id="msgManageTab1">사랑</a>
                    <a href="#" onclick="msgManageEmoChange('2')" id="msgManageTab2">놀람</a>
                    <a href="#" onclick="msgManageEmoChange('3')" id="msgManageTab3">슬픔</a>
                    <a href="#" onclick="msgManageEmoChange('4')" id="msgManageTab4">피곤</a>
                    <a href="#" onclick="msgManageEmoChange('5')" id="msgManageTab5">화남</a>
                    <a href="#" onclick="msgManageEmoChange('6')" id="msgManageTab6">기타</a>
                </div>
                <span id="msgManageEmos0" style="display:inline">
                    <span><a href="#" onclick="addMsgEmo2('n.n');">n.n</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^Δ^');">^Δ^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^v^');">^v^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^.^');">^.^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^_^');">^_^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^0^');">^0^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^L^');">^L^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('⌒⌒');">⌒⌒</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^.~');">^.~</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^ε^');">^ε^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^-^b');">^-^b</a></span>
                    <span><a href="#" onclick="addMsgEmo2('*^^*');">*^^*</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^▽^');">^▽^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('=^.^=');">=^.^=</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(^^)γ');">(^^)γ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('&amp;lt;^O^&amp;gt;');">&lt;^O^&gt;</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*^-^)');">(*^-^)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*^o^*)');">(*^o^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('^o^~~♬');">^o^~~♬</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☞^.^☜');">☞^.^☜</a></span>
                    <span><a href="#" onclick="addMsgEmo2('o(^-^)o');">o(^-^)o</a></span>
                    <span><a href="#" onclick="addMsgEmo2('S(^.^)b');">S(^.^)b</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(￣∇￣)');">(￣∇￣)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('♬(^0^)~♩');">♬(^0^)~♩</a></span>
                    <span><a href="#" onclick="addMsgEmo2('s(￣▽￣)/');">s(￣▽￣)/</a></span>
                </span>
                <span id="msgManageEmos1" style="display:none">
                    <span><a href="#" onclick="addMsgEmo2('♥.♥');">♥.♥</a></span>
                    <span><a href="#" onclick="addMsgEmo2('♡.♡');">♡.♡</a></span>
                    <span><a href="#" onclick="addMsgEmo2('*♥o♥*');">*♥o♥*</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(~.^)s');">(~.^)s</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☞♡☜');">☞♡☜</a></span>
                    <span><a href="#" onclick="addMsgEmo2('γ^ε^γ');">γ^ε^γ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(♡.♡)');">(♡.♡)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*`0`*)');">(*`0`*)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(⌒ε⌒*)');">(⌒ε⌒*)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*^}{^*)');">(*^}{^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('づ^0^)づ');">づ^0^)づ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ⓛⓞⓥⓔ');">ⓛⓞⓥⓔ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☜(^^*)☞');">☜(^^*)☞</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*^3(^^*)');">(*^3(^^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(^*^)kiss');">(^*^)kiss</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(つ＾з＾)つ');">(つ＾з＾)つ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*⌒.^)(^ε⌒*)');">(*⌒.^)(^ε⌒*)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*^-^)♡(^o^*)');">(*^-^)♡(^o^*)</a></span>
                </span>
                <span id="msgManageEmos2" style="display:none">
                    <span><a href="#" onclick="addMsgEmo2('?o?');">?o?</a></span>
                    <span><a href="#" onclick="addMsgEmo2('O_O');">O_O</a></span>
                    <span><a href="#" onclick="addMsgEmo2('@_@');">@_@</a></span>
                    <span><a href="#" onclick="addMsgEmo2('!.!');">!.!</a></span>
                    <span><a href="#" onclick="addMsgEmo2('⊙⊙');">⊙⊙</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(o_o)');">(o_o)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(@.@)');">(@.@)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(\'o\')');">('o')</a></span>
                    <span><a href="#" onclick="addMsgEmo2('★.★');">★.★</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☆_☆');">☆_☆</a></span>
                    <span><a href="#" onclick="addMsgEmo2('⊙.⊙');">⊙.⊙</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☆_☆');">☆_☆</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☞_☜');">☞_☜</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(o)(o)');">(o)(o)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(=_=;)');">(=_=;)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('⊙⊙ㆀ');">⊙⊙ㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ㆀ-_-ㆀ');">ㆀ-_-ㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ご,.ごㆀ');">ご,.ごㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ㅡ..ㅡㆀ');">ㅡ..ㅡㆀ</a></span>
                </span>
                <span id="msgManageEmos3" style="display:none">
                    <span><a href="#" onclick="addMsgEmo2('T.T');">T.T</a></span>
                    <span><a href="#" onclick="addMsgEmo2(';_;');">;_;</a></span>
                    <span><a href="#" onclick="addMsgEmo2('TmT');">TmT</a></span>
                    <span><a href="#" onclick="addMsgEmo2('Θ_Θ');">Θ_Θ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ㅠ.ㅠ');">ㅠ.ㅠ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ㅜ.ㅜ');">ㅜ.ㅜ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ㅡ.ㅜ');">ㅡ.ㅜ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(v.v)');">(v.v)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(∏.∏)');">(∏.∏)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ご.ご');">ご.ご</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(X_X)');">(X_X)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(&amp;gt;_&amp;lt;)');">(&gt;_&lt;)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('+_+;;');">+_+;;</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(づ_T)');">(づ_T)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(づ_ど)');">(づ_ど)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('o(T^T)o');">o(T^T)o</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(ㅜ.ㅜ)');">(ㅜ.ㅜ)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(ㅠ.ㅠ)');">(ㅠ.ㅠ)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(♨_♨)');">(♨_♨)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(T(oo)T)');">(T(oo)T)</a></span>
                </span>
                <span id="msgManageEmos4" style="display:none">
                    <span><a href="#" onclick="addMsgEmo2('=.=');">=.=</a></span>
                    <span><a href="#" onclick="addMsgEmo2('θ.θ');">θ.θ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('@L@');">@L@</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-0-)');">(-0-)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(@.@)');">(@.@)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(Z_Z)');">(Z_Z)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('∋.∈');">∋.∈</a></span>
                    <span><a href="#" onclick="addMsgEmo2('⊇.⊆');">⊇.⊆</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(u_u)');">(u_u)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(g_g)');">(g_g)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(づ_-)');">(づ_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-_ど)');">(-_ど)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(=.&amp;amp;=)');">(=.&amp;=)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-.-)Zzz..');">(-.-)Zzz..</a></span>
                    <span><a href="#" onclick="addMsgEmo2('[(￣.￣)]zZ');">[(￣.￣)]zZ</a></span>
                </span>
                <span id="msgManageEmos5" style="display:none">
                    <span><a href="#" onclick="addMsgEmo2(':-&amp;lt;');">:-&lt;</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ㅡㅡ+');">ㅡㅡ+</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(`o\')');">(`o')</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(#_-)');">(#_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ㅡㅡ^');">ㅡㅡ^</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(@_@)');">(@_@)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☞_☜');">☞_☜</a></span>
                    <span><a href="#" onclick="addMsgEmo2('s(￣へ￣ )z');">s(￣へ￣ )z</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-.￥)');">(-.￥)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(｀へ´)');">(｀へ´)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('o(-&amp;quot;-)o');">o(-&quot;-)o</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-_-メ)');">(-_-メ)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-&amp;quot;-メ)');">(-&quot;-メ)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(↗∇↖)');">(↗∇↖)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-(oo)-)');">(-(oo)-)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-.-&amp;quot;)凸');">(-.-&quot;)凸</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(*｀Д´)/');">(*｀Д´)/</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(/ㅡ_-)/~');">(/ㅡ_-)/~</a></span>
                    <span><a href="#" onclick="addMsgEmo2('＼(*｀Д´)/');">＼(*｀Д´)/</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(-ヘㅡメ)');">(-ヘㅡメ)</a></span>
                </span>
                <span id="msgManageEmos6" style="display:none">
                    <span><a href="#" onclick="addMsgEmo2('(-_-)');">(-_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('($_$)');">($_$)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('( ㅅ )');">( ㅅ )</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(\'⌒\')');">('⌒')</a></span>
                    <span><a href="#" onclick="addMsgEmo2('ご,,ご');">ご,,ご</a></span>
                    <span><a href="#" onclick="addMsgEmo2('o(&amp;gt;_&amp;lt;)o');">o(&gt;_&lt;)o</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(\'º\')/)/)');">('º')/)/)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('( ㅅ)=333');">( ㅅ)=333</a></span>
                    <span><a href="#" onclick="addMsgEmo2('∠(- o -)');">∠(- o -)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('☞(&amp;gt;.&amp;lt;)☜');">☞(&gt;.&lt;)☜</a></span>
                    <span><a href="#" onclick="addMsgEmo2('&amp;lt;(&amp;gt;.&amp;lt;ㆀ)&amp;gt;');">&lt;(&gt;.&lt;ㆀ)&gt;</a></span>
                    <span><a href="#" onclick="addMsgEmo2('┌(ㆀ_ _)┐');">┌(ㆀ_ _)┐</a></span>
                    <span><a href="#" onclick="addMsgEmo2('s(ごoご)グ');">s(ごoご)グ</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(^(**)^))~');">(^(**)^))~</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(. .)|/)/)');">(. .)|/)/)</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(\'\' )( \'\')');">('' )( '')</a></span>
                    <span><a href="#" onclick="addMsgEmo2('(=`.`=)@@');">(=`.`=)@@</a></span>
                </span>
            </div>
        </div>
    </body>
</html>