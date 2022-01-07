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
                height: 150px;
                line-height: 1.25em;
                padding: 6px 0 0 2px;
                width: 190px;
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
                    height: 150px;
                    margin: 5px 0 0 0;
                    width: 190px;
                } .tab02_sub {
                      background: #F3F3F3 none repeat scroll 0 0;
                      font-family: dotum;
                      font-size: 8pt;
                      height: 19px;
                      margin: 0 0 5px;
                      padding: 5px 0 0;
                  } .tab02_sub a {
                        margin: 0 0 0 4px;
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
        function addMsgEmo3(value)
        {
            var marketingSmsSendScope = agrid.getScope('mobileMarketingSmsSendCtrl'); // (모바일) 마케팅용 SMS전송
            marketingSmsSendScope.addMsg(value);
        }

        // 이모티콘 탭 변경
        function marketingSmsSendEmoChange(value)
        {
            if(value == "0") {
                // 이모티콘
                $("#marketingSmsSendEmos0").css("display", "");
                $("#marketingSmsSendEmos1").css("display", "none");
                $("#marketingSmsSendEmos2").css("display", "none");
                $("#marketingSmsSendEmos3").css("display", "none");
                $("#marketingSmsSendEmos4").css("display", "none");
                $("#marketingSmsSendEmos5").css("display", "none");
                $("#marketingSmsSendEmos6").css("display", "none");
                // 탭 색상
                $("#marketingSmsSendTab0").css("color", "#ff6600");
                $("#marketingSmsSendTab1").css("color", "#666666");
                $("#marketingSmsSendTab2").css("color", "#666666");
                $("#marketingSmsSendTab3").css("color", "#666666");
                $("#marketingSmsSendTab4").css("color", "#666666");
                $("#marketingSmsSendTab5").css("color", "#666666");
                $("#marketingSmsSendTab6").css("color", "#666666");
            } else if(value == "1") {
                // 이모티콘
                $("#marketingSmsSendEmos0").css("display", "none");
                $("#marketingSmsSendEmos1").css("display", "");
                $("#marketingSmsSendEmos2").css("display", "none");
                $("#marketingSmsSendEmos3").css("display", "none");
                $("#marketingSmsSendEmos4").css("display", "none");
                $("#marketingSmsSendEmos5").css("display", "none");
                $("#marketingSmsSendEmos6").css("display", "none");
                // 탭 색상
                $("#marketingSmsSendTab0").css("color", "#666666");
                $("#marketingSmsSendTab1").css("color", "#ff6600");
                $("#marketingSmsSendTab2").css("color", "#666666");
                $("#marketingSmsSendTab3").css("color", "#666666");
                $("#marketingSmsSendTab4").css("color", "#666666");
                $("#marketingSmsSendTab5").css("color", "#666666");
                $("#marketingSmsSendTab6").css("color", "#666666");
            } else if(value == "2") {
                // 이모티콘
                $("#marketingSmsSendEmos0").css("display", "none");
                $("#marketingSmsSendEmos1").css("display", "none");
                $("#marketingSmsSendEmos2").css("display", "");
                $("#marketingSmsSendEmos3").css("display", "none");
                $("#marketingSmsSendEmos4").css("display", "none");
                $("#marketingSmsSendEmos5").css("display", "none");
                $("#marketingSmsSendEmos6").css("display", "none");
                // 탭 색상
                $("#marketingSmsSendTab0").css("color", "#666666");
                $("#marketingSmsSendTab1").css("color", "#666666");
                $("#marketingSmsSendTab2").css("color", "#ff6600");
                $("#marketingSmsSendTab3").css("color", "#666666");
                $("#marketingSmsSendTab4").css("color", "#666666");
                $("#marketingSmsSendTab5").css("color", "#666666");
                $("#marketingSmsSendTab6").css("color", "#666666");
            } else if(value == "3") {
                // 이모티콘
                $("#marketingSmsSendEmos0").css("display", "none");
                $("#marketingSmsSendEmos1").css("display", "none");
                $("#marketingSmsSendEmos2").css("display", "none");
                $("#marketingSmsSendEmos3").css("display", "");
                $("#marketingSmsSendEmos4").css("display", "none");
                $("#marketingSmsSendEmos5").css("display", "none");
                $("#marketingSmsSendEmos6").css("display", "none");
                // 탭 색상
                $("#marketingSmsSendTab0").css("color", "#666666");
                $("#marketingSmsSendTab1").css("color", "#666666");
                $("#marketingSmsSendTab2").css("color", "#666666");
                $("#marketingSmsSendTab3").css("color", "#ff6600");
                $("#marketingSmsSendTab4").css("color", "#666666");
                $("#marketingSmsSendTab5").css("color", "#666666");
                $("#marketingSmsSendTab6").css("color", "#666666");
            } else if(value == "4") {
                // 이모티콘
                $("#marketingSmsSendEmos0").css("display", "none");
                $("#marketingSmsSendEmos1").css("display", "none");
                $("#marketingSmsSendEmos2").css("display", "none");
                $("#marketingSmsSendEmos3").css("display", "none");
                $("#marketingSmsSendEmos4").css("display", "");
                $("#marketingSmsSendEmos5").css("display", "none");
                $("#marketingSmsSendEmos6").css("display", "none");
                // 탭 색상
                $("#marketingSmsSendTab0").css("color", "#666666");
                $("#marketingSmsSendTab1").css("color", "#666666");
                $("#marketingSmsSendTab2").css("color", "#666666");
                $("#marketingSmsSendTab3").css("color", "#666666");
                $("#marketingSmsSendTab4").css("color", "#ff6600");
                $("#marketingSmsSendTab5").css("color", "#666666");
                $("#marketingSmsSendTab6").css("color", "#666666");
            } else if(value == "5") {
                // 이모티콘
                $("#marketingSmsSendEmos0").css("display", "none");
                $("#marketingSmsSendEmos1").css("display", "none");
                $("#marketingSmsSendEmos2").css("display", "none");
                $("#marketingSmsSendEmos3").css("display", "none");
                $("#marketingSmsSendEmos4").css("display", "none");
                $("#marketingSmsSendEmos5").css("display", "");
                $("#marketingSmsSendEmos6").css("display", "none");
                // 탭 색상
                $("#marketingSmsSendTab0").css("color", "#666666");
                $("#marketingSmsSendTab1").css("color", "#666666");
                $("#marketingSmsSendTab2").css("color", "#666666");
                $("#marketingSmsSendTab3").css("color", "#666666");
                $("#marketingSmsSendTab4").css("color", "#666666");
                $("#marketingSmsSendTab5").css("color", "#ff6600");
                $("#marketingSmsSendTab6").css("color", "#666666");
            } else if(value == "6") {
                // 이모티콘
                $("#marketingSmsSendEmos0").css("display", "none");
                $("#marketingSmsSendEmos1").css("display", "none");
                $("#marketingSmsSendEmos2").css("display", "none");
                $("#marketingSmsSendEmos3").css("display", "none");
                $("#marketingSmsSendEmos4").css("display", "none");
                $("#marketingSmsSendEmos5").css("display", "none");
                $("#marketingSmsSendEmos6").css("display", "");
                // 탭 색상
                $("#marketingSmsSendTab0").css("color", "#666666");
                $("#marketingSmsSendTab1").css("color", "#666666");
                $("#marketingSmsSendTab2").css("color", "#666666");
                $("#marketingSmsSendTab3").css("color", "#666666");
                $("#marketingSmsSendTab4").css("color", "#666666");
                $("#marketingSmsSendTab5").css("color", "#666666");
                $("#marketingSmsSendTab6").css("color", "#ff6600");
            }
        }
    </script>

    <body>
        <div style="margin:0px;">
            <div class="emoWrap01">
                <a href="#" onclick="addMsgEmo3('★');">★</a>
                <a href="#" onclick="addMsgEmo3('☆');">☆</a>
                <a href="#" onclick="addMsgEmo3('☎');">☎</a>
                <a href="#" onclick="addMsgEmo3('☏');">☏</a>
                <a href="#" onclick="addMsgEmo3('☜');">☜</a>
                <a href="#" onclick="addMsgEmo3('☞');">☞</a>
                <a href="#" onclick="addMsgEmo3('※');">※</a>
                <a href="#" onclick="addMsgEmo3('♥');">♥</a>
                <a href="#" onclick="addMsgEmo3('♡');">♡</a>
                <a href="#" onclick="addMsgEmo3('♣');">♣</a>
                <a href="#" onclick="addMsgEmo3('♠');">♠</a>
                <a href="#" onclick="addMsgEmo3('♤');">♤</a>
                <a href="#" onclick="addMsgEmo3('♧');">♧</a>
                <a href="#" onclick="addMsgEmo3('♨');">♨</a>
                <a href="#" onclick="addMsgEmo3('ㆀ');">ㆀ</a>
                <a href="#" onclick="addMsgEmo3('♩');">♩</a>
                <%--<a href="#" onclick="addMsgEmo3('♪');">♪</a>--%>
                <a href="#" onclick="addMsgEmo3('♬');">♬</a>
                <a href="#" onclick="addMsgEmo3('■');">■</a>
                <a href="#" onclick="addMsgEmo3('□');">□</a>
                <a href="#" onclick="addMsgEmo3('▣');">▣</a>
                <a href="#" onclick="addMsgEmo3('▤');">▤</a>
                <a href="#" onclick="addMsgEmo3('▦');">▦</a>
                <a href="#" onclick="addMsgEmo3('▨');">▨</a>
                <a href="#" onclick="addMsgEmo3('▩');">▩</a>
                <a href="#" onclick="addMsgEmo3('▒');">▒</a>
                <a href="#" onclick="addMsgEmo3('▲');">▲</a>
                <a href="#" onclick="addMsgEmo3('△');">△</a>
                <a href="#" onclick="addMsgEmo3('▶');">▶</a>
                <a href="#" onclick="addMsgEmo3('▷');">▷</a>
                <a href="#" onclick="addMsgEmo3('▼');">▼</a>
                <a href="#" onclick="addMsgEmo3('▽');">▽</a>
                <a href="#" onclick="addMsgEmo3('◀');">◀</a>
                <a href="#" onclick="addMsgEmo3('◁');">◁</a>
                <a href="#" onclick="addMsgEmo3('◆');">◆</a>
                <a href="#" onclick="addMsgEmo3('◇');">◇</a>
                <a href="#" onclick="addMsgEmo3('◈');">◈</a>
                <a href="#" onclick="addMsgEmo3('●');">●</a>
                <a href="#" onclick="addMsgEmo3('○');">○</a>
                <a href="#" onclick="addMsgEmo3('◎');">◎</a>
                <a href="#" onclick="addMsgEmo3('⊙');">⊙</a>
                <a href="#" onclick="addMsgEmo3('◐');">◐</a>
                <a href="#" onclick="addMsgEmo3('◑');">◑</a>
                <a href="#" onclick="addMsgEmo3('←');">←</a>
                <a href="#" onclick="addMsgEmo3('↑');">↑</a>
                <a href="#" onclick="addMsgEmo3('→');">→</a>
                <a href="#" onclick="addMsgEmo3('↓');">↓</a>
                <a href="#" onclick="addMsgEmo3('⇒');">⇒</a>
                <a href="#" onclick="addMsgEmo3('§');">§</a>
                <a href="#" onclick="addMsgEmo3('Ø');">Ø</a>
                <a href="#" onclick="addMsgEmo3('∀');">∀</a>
                <a href="#" onclick="addMsgEmo3('∃');">∃</a>
                <a href="#" onclick="addMsgEmo3('∏');">∏</a>
                <a href="#" onclick="addMsgEmo3('∞');">∞</a>
                <a href="#" onclick="addMsgEmo3('∧');">∧</a>
                <a href="#" onclick="addMsgEmo3('∪');">∪</a>
                <a href="#" onclick="addMsgEmo3('∬');">∬</a>
                <a href="#" onclick="addMsgEmo3('∴');">∴</a>
                <a href="#" onclick="addMsgEmo3('∽');">∽</a>
                <a href="#" onclick="addMsgEmo3('≠');">≠</a>
                <a href="#" onclick="addMsgEmo3('⊃');">⊃</a>
                <a href="#" onclick="addMsgEmo3('￠');">￠</a>
                <a href="#" onclick="addMsgEmo3('￥');">￥</a>
            </div>
            <div class="emoWrap02">
                <div class="tab02_sub">
                    <a href="#" onclick="marketingSmsSendEmoChange('0')" id="marketingSmsSendTab0" style="color:#ff6600;">웃음</a>
                    <a href="#" onclick="marketingSmsSendEmoChange('1')" id="marketingSmsSendTab1">사랑</a>
                    <a href="#" onclick="marketingSmsSendEmoChange('2')" id="marketingSmsSendTab2">놀람</a>
                    <a href="#" onclick="marketingSmsSendEmoChange('3')" id="marketingSmsSendTab3">슬픔</a>
                    <a href="#" onclick="marketingSmsSendEmoChange('4')" id="marketingSmsSendTab4">피곤</a>
                    <a href="#" onclick="marketingSmsSendEmoChange('5')" id="marketingSmsSendTab5">화남</a>
                    <a href="#" onclick="marketingSmsSendEmoChange('6')" id="marketingSmsSendTab6">기타</a>
                </div>
                <span id="marketingSmsSendEmos0" style="display:inline">
                    <span><a href="#" onclick="addMsgEmo3('n.n');">n.n</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^Δ^');">^Δ^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^v^');">^v^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^.^');">^.^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^_^');">^_^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^0^');">^0^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^L^');">^L^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('⌒⌒');">⌒⌒</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^.~');">^.~</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^ε^');">^ε^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^-^b');">^-^b</a></span>
                    <span><a href="#" onclick="addMsgEmo3('*^^*');">*^^*</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^▽^');">^▽^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('=^.^=');">=^.^=</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(^^)γ');">(^^)γ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('&amp;lt;^O^&amp;gt;');">&lt;^O^&gt;</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*^-^)');">(*^-^)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*^o^*)');">(*^o^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('^o^~~♬');">^o^~~♬</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☞^.^☜');">☞^.^☜</a></span>
                    <span><a href="#" onclick="addMsgEmo3('o(^-^)o');">o(^-^)o</a></span>
                    <span><a href="#" onclick="addMsgEmo3('S(^.^)b');">S(^.^)b</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(￣∇￣)');">(￣∇￣)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('♬(^0^)~♩');">♬(^0^)~♩</a></span>
                    <span><a href="#" onclick="addMsgEmo3('s(￣▽￣)/');">s(￣▽￣)/</a></span>
                </span>
                <span id="marketingSmsSendEmos1" style="display:none">
                    <span><a href="#" onclick="addMsgEmo3('♥.♥');">♥.♥</a></span>
                    <span><a href="#" onclick="addMsgEmo3('♡.♡');">♡.♡</a></span>
                    <span><a href="#" onclick="addMsgEmo3('*♥o♥*');">*♥o♥*</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(~.^)s');">(~.^)s</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☞♡☜');">☞♡☜</a></span>
                    <span><a href="#" onclick="addMsgEmo3('γ^ε^γ');">γ^ε^γ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(♡.♡)');">(♡.♡)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*`0`*)');">(*`0`*)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(⌒ε⌒*)');">(⌒ε⌒*)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*^}{^*)');">(*^}{^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('づ^0^)づ');">づ^0^)づ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ⓛⓞⓥⓔ');">ⓛⓞⓥⓔ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☜(^^*)☞');">☜(^^*)☞</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*^3(^^*)');">(*^3(^^*)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(^*^)kiss');">(^*^)kiss</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(つ＾з＾)つ');">(つ＾з＾)つ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*⌒.^)(^ε⌒*)');">(*⌒.^)(^ε⌒*)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*^-^)♡(^o^*)');">(*^-^)♡(^o^*)</a></span>
                </span>
                <span id="marketingSmsSendEmos2" style="display:none">
                    <span><a href="#" onclick="addMsgEmo3('?o?');">?o?</a></span>
                    <span><a href="#" onclick="addMsgEmo3('O_O');">O_O</a></span>
                    <span><a href="#" onclick="addMsgEmo3('@_@');">@_@</a></span>
                    <span><a href="#" onclick="addMsgEmo3('!.!');">!.!</a></span>
                    <span><a href="#" onclick="addMsgEmo3('⊙⊙');">⊙⊙</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(o_o)');">(o_o)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(@.@)');">(@.@)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(\'o\')');">('o')</a></span>
                    <span><a href="#" onclick="addMsgEmo3('★.★');">★.★</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☆_☆');">☆_☆</a></span>
                    <span><a href="#" onclick="addMsgEmo3('⊙.⊙');">⊙.⊙</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☆_☆');">☆_☆</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☞_☜');">☞_☜</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(o)(o)');">(o)(o)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(=_=;)');">(=_=;)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('⊙⊙ㆀ');">⊙⊙ㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ㆀ-_-ㆀ');">ㆀ-_-ㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ご,.ごㆀ');">ご,.ごㆀ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ㅡ..ㅡㆀ');">ㅡ..ㅡㆀ</a></span>
                </span>
                <span id="marketingSmsSendEmos3" style="display:none">
                    <span><a href="#" onclick="addMsgEmo3('T.T');">T.T</a></span>
                    <span><a href="#" onclick="addMsgEmo3(';_;');">;_;</a></span>
                    <span><a href="#" onclick="addMsgEmo3('TmT');">TmT</a></span>
                    <span><a href="#" onclick="addMsgEmo3('Θ_Θ');">Θ_Θ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ㅠ.ㅠ');">ㅠ.ㅠ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ㅜ.ㅜ');">ㅜ.ㅜ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ㅡ.ㅜ');">ㅡ.ㅜ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(v.v)');">(v.v)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(∏.∏)');">(∏.∏)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ご.ご');">ご.ご</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(X_X)');">(X_X)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(&amp;gt;_&amp;lt;)');">(&gt;_&lt;)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('+_+;;');">+_+;;</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(づ_T)');">(づ_T)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(づ_ど)');">(づ_ど)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('o(T^T)o');">o(T^T)o</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(ㅜ.ㅜ)');">(ㅜ.ㅜ)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(ㅠ.ㅠ)');">(ㅠ.ㅠ)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(♨_♨)');">(♨_♨)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(T(oo)T)');">(T(oo)T)</a></span>
                </span>
                <span id="marketingSmsSendEmos4" style="display:none">
                    <span><a href="#" onclick="addMsgEmo3('=.=');">=.=</a></span>
                    <span><a href="#" onclick="addMsgEmo3('θ.θ');">θ.θ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('@L@');">@L@</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-0-)');">(-0-)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(@.@)');">(@.@)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(Z_Z)');">(Z_Z)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('∋.∈');">∋.∈</a></span>
                    <span><a href="#" onclick="addMsgEmo3('⊇.⊆');">⊇.⊆</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(u_u)');">(u_u)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(g_g)');">(g_g)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(づ_-)');">(づ_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-_ど)');">(-_ど)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(=.&amp;amp;=)');">(=.&amp;=)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-.-)Zzz..');">(-.-)Zzz..</a></span>
                    <span><a href="#" onclick="addMsgEmo3('[(￣.￣)]zZ');">[(￣.￣)]zZ</a></span>
                </span>
                <span id="marketingSmsSendEmos5" style="display:none">
                    <span><a href="#" onclick="addMsgEmo3(':-&amp;lt;');">:-&lt;</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ㅡㅡ+');">ㅡㅡ+</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(`o\')');">(`o')</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(#_-)');">(#_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ㅡㅡ^');">ㅡㅡ^</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(@_@)');">(@_@)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☞_☜');">☞_☜</a></span>
                    <span><a href="#" onclick="addMsgEmo3('s(￣へ￣ )z');">s(￣へ￣ )z</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-.￥)');">(-.￥)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(｀へ´)');">(｀へ´)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('o(-&amp;quot;-)o');">o(-&quot;-)o</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-_-メ)');">(-_-メ)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-&amp;quot;-メ)');">(-&quot;-メ)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(↗∇↖)');">(↗∇↖)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-(oo)-)');">(-(oo)-)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-.-&amp;quot;)凸');">(-.-&quot;)凸</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(*｀Д´)/');">(*｀Д´)/</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(/ㅡ_-)/~');">(/ㅡ_-)/~</a></span>
                    <span><a href="#" onclick="addMsgEmo3('＼(*｀Д´)/');">＼(*｀Д´)/</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(-ヘㅡメ)');">(-ヘㅡメ)</a></span>
                </span>
                <span id="marketingSmsSendEmos6" style="display:none">
                    <span><a href="#" onclick="addMsgEmo3('(-_-)');">(-_-)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('($_$)');">($_$)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('( ㅅ )');">( ㅅ )</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(\'⌒\')');">('⌒')</a></span>
                    <span><a href="#" onclick="addMsgEmo3('ご,,ご');">ご,,ご</a></span>
                    <span><a href="#" onclick="addMsgEmo3('o(&amp;gt;_&amp;lt;)o');">o(&gt;_&lt;)o</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(\'º\')/)/)');">('º')/)/)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('( ㅅ)=333');">( ㅅ)=333</a></span>
                    <span><a href="#" onclick="addMsgEmo3('∠(- o -)');">∠(- o -)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('☞(&amp;gt;.&amp;lt;)☜');">☞(&gt;.&lt;)☜</a></span>
                    <span><a href="#" onclick="addMsgEmo3('&amp;lt;(&amp;gt;.&amp;lt;ㆀ)&amp;gt;');">&lt;(&gt;.&lt;ㆀ)&gt;</a></span>
                    <span><a href="#" onclick="addMsgEmo3('┌(ㆀ_ _)┐');">┌(ㆀ_ _)┐</a></span>
                    <span><a href="#" onclick="addMsgEmo3('s(ごoご)グ');">s(ごoご)グ</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(^(**)^))~');">(^(**)^))~</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(. .)|/)/)');">(. .)|/)/)</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(\'\' )( \'\')');">('' )( '')</a></span>
                    <span><a href="#" onclick="addMsgEmo3('(=`.`=)@@');">(=`.`=)@@</a></span>
                </span>
            </div>
        </div>
    </body>
</html>