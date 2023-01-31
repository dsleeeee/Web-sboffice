<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/base/store/tblms/"/>
<c:set var="vUserIdChk" value="${sessionScope.sessionInfo.vUserId}"/>
<style type="text/css">

/*
1. IMPORT 나 LINK로 참조 시 정상 적용되지 않는 브라우저가 있어서 스타일 직접 적용
@import url("https://mob.solbipos.com/mobile/css/button_style1.css");
<link rel="stylesheet" href="https://mob.solbipos.com/mobile/css/button_style1.css" type="text/css">
*/

/* 단순버튼 01 */
.btn01
{
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
.btn01:hover
{
    color: #fff;
    outline: 0;
}
.btn01:focus
{
    color: #fff;
    outline: 0;
}

.first01
{
    transition: box-shadow 300ms ease-in-out, color 300ms ease-in-out;
}
.first01:hover
{
    box-shadow: 0 0 40px 40px blue inset;
}

.third01 {
    border-color: blue;
    color: #fff;
    box-shadow: 0 0 40px 40px blue inset, 0 0 0 0 blue;
    transition: all 150ms ease-in-out;
}
.third01:hover
{
    box-shadow: 0 0 10px 0 blue inset, 0 0 10px 4px blue;
}

/* 단순버튼 02 */

* {
	box-sizing: border-box;
}
.header {
	background: white;
	padding: 30px;
	text-align: center;
}
.header h1 {
	font-weight: 300;
	display: inline;
}
h2 {
	color: #89867e;
	text-align: center;
	font-weight: 300;
}
.color {
	width: 350px;
	margin: 0 auto;
}
.color li {
	margin: 0 15px 0 0;
	width: 30px;
	height: 30px;
	display: inline-block;
	border-radius: 100%;
}
.color .red    {background: #fa5a5a;}
.color .yellow {background: #f0d264;}
.color .green  {background: #82c8a0;}
.color .cyan   {background: #7fccde;}
.color .blue   {background: #6698cb;}
.color .purple {background: #cb99c5;}

.content,
.content-gradient,
.content-3d {
  margin: 40px auto;
}
.content {
  width: 80%;
  max-width: 700px;
}
.content-3d {
  width: 50%;
  max-width: 300px;
}
pre {
	width: 100%;
	padding: 30px;
	background-color: rgba(0, 0, 0, 0.72);
	color: #f8f8f2;
	border-radius: 0 0 4px 4px;
	margin-top: 20px;
  white-space: pre-wrap; /* css-3 */
  white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
  /* white-space: -pre-wrap; */ /* Opera 4-6 */
  white-space: -o-pre-wrap; /* Opera 7 */
  word-wrap: break-word; /* Internet Explorer 5.5+ */
}
pre .bt  {color: #f8f8f2;} /* <> */
pre .anc {color: #f92672;} /* anchor tag */
pre .att {color: #a6a926;} /* attribute */
pre .val {color: #e6db74;} /* value */

.btn02-container, .container {
	background-color: white;
	border-radius: 4px;
	text-align: center;
	margin-bottom: 40px;
}
.container h2 {
	padding-top: 30px;
	font-weight: 300;
}
.btn02, .btn02-two {
	margin: 9px;
}
.btn02-gradient {
	margin: 5px;
}
a[class*="btn02"] {text-decoration: none;}
input[class*="btn02"],
button[class*="btn02"] {border: 0;}

/* Here you can change the button sizes */
.btn02.large,
.btn02-two.large,
.btn02-effect.large {
  padding: 20px 40px;
  font-size: 22px;
}
.btn02.small,
.btn02-two.small,
.btn02-gradient.small,
.btn02-effect.small {
  padding: 8px 18px;
  font-size: 14px;
}
.btn02.mini,
.btn02-two.mini,
.btn02-gradient.mini,
.btn02-effect.mini {
  padding: 4px 12px;
  font-size: 12px;
}
.btn02.block,
.btn02-two.block,
.btn02-gradient.block,
.btn02-effect.block {
  display: block;
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}
.btn02-gradient.large {
  padding: 15px 45px;
  font-size: 22px;
}

/* Colors for .btn02 and .btn02-two */
.btn02.blue, .btn02-two.blue     {background-color: #7fb1bf;}
.btn02.green, .btn02-two.green   {background-color: #9abf7f;}
.btn02.red, .btn02-two.red       {background-color: #fa5a5a;}
.btn02.purple, .btn02-two.purple {background-color: #cb99c5;}
.btn02.cyan, .btn02-two.cyan     {background-color: #7fccde;}
.btn02.yellow, .btn02-two.yellow {background-color: #f0d264;}

.rounded {
  border-radius: 10px;
}

/* default button style */
.btn02 {
	position: relative;
	border: 0;
	padding: 15px 25px;
	display: inline-block;
	text-align: center;
	color: white;
}
.btn02:active {
	top: 4px;
}

/* color classes for .btn02 */
.btn02.blue {box-shadow: 0px 4px #74a3b0;}
.btn02.blue:active {box-shadow: 0 0 #74a3b0; background-color: #709CA8;}

.btn02.green {box-shadow: 0px 4px 0px #87a86f;}
.btn02.green:active {box-shadow: 0 0 #87a86f; background-color: #87a86f;}

.btn02.red {box-shadow:0px 4px 0px #E04342;}
.btn02.red:active {box-shadow: 0 0 #ff4c4b; background-color: #ff4c4b;}

.btn02.purple {box-shadow:0px 4px 0px #AD83A8;}
.btn02.purple:active {box-shadow: 0 0 #BA8CB5; background-color: #BA8CB5;}

.btn02.cyan {box-shadow:0px 4px 0px #73B9C9;}
.btn02.cyan:active {box-shadow: 0 0 #73B9C9; background-color: #70B4C4;}

.btn02.yellow {box-shadow:0px 4px 0px #D1B757;}
.btn02.yellow:active {box-shadow: 0 0 #ff4c4b; background-color: #D6BB59;}

/* Button two - I have no creativity for names */
.btn02-two {
	color: white;
	padding: 15px 25px;
	display: inline-block;
	border: 1px solid rgba(0,0,0,0.21);
	border-bottom-color: rgba(0,0,0,0.34);
	text-shadow:0 1px 0 rgba(0,0,0,0.15);
	box-shadow: 0 1px 0 rgba(255,255,255,0.34) inset,
				      0 2px 0 -1px rgba(0,0,0,0.13),
				      0 3px 0 -1px rgba(0,0,0,0.08),
				      0 3px 13px -1px rgba(0,0,0,0.21);
}
.btn02-two:active {
	top: 1px;
	border-color: rgba(0,0,0,0.34) rgba(0,0,0,0.21) rgba(0,0,0,0.21);
	box-shadow: 0 1px 0 rgba(255,255,255,0.89),0 1px rgba(0,0,0,0.05) inset;
	position: relative;
}
/* 3D Button */
.btn02-3d {
	position: relative;
	display: inline-block;
	font-size: 22px;
	padding: 20px 60px;
	color: white;
	margin: 20px 10px 10px;
	border-radius: 6px;
	text-align: center;
	transition: top .01s linear;
	text-shadow: 0 1px 0 rgba(0,0,0,0.15);
}
.btn02-3d.red:hover    {background-color: #e74c3c;}
.btn02-3d.blue:hover   {background-color: #699DD1;}
.btn02-3d.green:hover  {background-color: #80C49D;}
.btn02-3d.purple:hover {background-color: #D19ECB;}
.btn02-3d.yellow:hover {background-color: #F0D264;}
.btn02-3d.cyan:hover   {background-color: #82D1E3;}

.btn02-3d:active {
	top: 9px;
}

/* 3D button colors */
.btn02-3d.red {
	background-color: #e74c3c;
	box-shadow: 0 0 0 1px #c63702 inset,
        0 0 0 2px rgba(255,255,255,0.15) inset,
        0 8px 0 0 #C24032,
        0 8px 0 1px rgba(0,0,0,0.4),
				0 8px 8px 1px rgba(0,0,0,0.5);
}
.btn02-3d.red:active {
	box-shadow: 0 0 0 1px #c63702 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 0 0 1px rgba(0,0,0,0.4);
}

.btn02-3d.blue {
	background-color: #6DA2D9;
	box-shadow: 0 0 0 1px #6698cb inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 8px 0 0 rgba(110, 164, 219, .7),
				0 8px 0 1px rgba(0,0,0,.4),
				0 8px 8px 1px rgba(0,0,0,0.5);
}
.btn02-3d.blue:active {
	box-shadow: 0 0 0 1px #6191C2 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 0 0 1px rgba(0,0,0,0.4);
}

.btn02-3d.green {
	background-color: #82c8a0;
	box-shadow: 0 0 0 1px #82c8a0 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 8px 0 0 rgba(126, 194, 155, .7),
				0 8px 0 1px rgba(0,0,0,.4),
				0 8px 8px 1px rgba(0,0,0,0.5);
}
.btn02-3d.green:active {
	box-shadow: 0 0 0 1px #82c8a0 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 0 0 1px rgba(0,0,0,0.4);
}

.btn02-3d.purple {
	background-color: #cb99c5;
	box-shadow: 0 0 0 1px #cb99c5 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 8px 0 0 rgba(189, 142, 183, .7),
				0 8px 0 1px rgba(0,0,0,.4),
				0 8px 8px 1px rgba(0,0,0,0.5);
}
.btn02-3d.purple:active {
	box-shadow: 0 0 0 1px #cb99c5 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 0 0 1px rgba(0,0,0,0.4);
}

.btn02-3d.cyan {
	background-color: #7fccde;
	box-shadow: 0 0 0 1px #7fccde inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 8px 0 0 rgba(102, 164, 178, .6),
				0 8px 0 1px rgba(0,0,0,.4),
				0 8px 8px 1px rgba(0,0,0,0.5);
}
.btn02-3d.cyan:active {
	box-shadow: 0 0 0 1px #7fccde inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 0 0 1px rgba(0,0,0,0.4);
}

.btn02-3d.yellow {
	background-color: #F0D264;
	box-shadow: 0 0 0 1px #F0D264 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 8px 0 0 rgba(196, 172, 83, .7),
				0 8px 0 1px rgba(0,0,0,.4),
				0 8px 8px 1px rgba(0,0,0,0.5);
}
.btn02-3d.yellow:active {
	box-shadow: 0 0 0 1px #F0D264 inset,
				0 0 0 2px rgba(255,255,255,0.15) inset,
				0 0 0 1px rgba(0,0,0,0.4);
}

/* Gradient buttons */
.btn02-gradient {
	text-decoration: none;
	color: white;
	padding: 10px 30px;
	display: inline-block;
	position: relative;
	border: 1px solid rgba(0,0,0,0.21);
	border-bottom: 4px solid rgba(0,0,0,0.21);
	border-radius: 4px;
	text-shadow: 0 1px 0 rgba(0,0,0,0.15);
}
/* Gradient - ugly css is ugly */
.btn02-gradient.cyan {
	background: rgba(27,188,194,1);
	background: -webkit-gradient(linear, 0 0, 0 100%, from(rgba(27,188,194,1)), to(rgba(24,163,168,1)));
	background: -webkit-linear-gradient(rgba(27,188,194,1) 0%, rgba(24,163,168,1) 100%);
	background: -moz-linear-gradient(rgba(27,188,194,1) 0%, rgba(24,163,168,1) 100%);
	background: -o-linear-gradient(rgba(27,188,194,1) 0%, rgba(24,163,168,1) 100%);
	background: linear-gradient(rgba(27,188,194,1) 0%, rgba(24,163,168,1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#1bbcc2', endColorstr='#18a3a8', GradientType=0);
}

.btn02-gradient.red{
	background: rgba(250,90,90,1);
	background: -webkit-gradient(linear, 0 0, 0 100%, from(rgba(250,90,90,1)), to(rgba(232,81,81,1)));
	background: -webkit-linear-gradient(rgba(250,90,90,1) 0%, rgba(232,81,81,1) 100%);
	background: -moz-linear-gradient(rgba(250,90,90,1) 0%, rgba(232,81,81,1) 100%);
	background: -o-linear-gradient(rgba(250,90,90,1) 0%, rgba(232,81,81,1) 100%);
	background: linear-gradient(rgba(250,90,90,1) 0%, rgba(232,81,81,1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#fa5a5a', endColorstr='#e85151', GradientType=0 );
}
.btn02-gradient.orange {
	background: rgba(255,105,30,1);
	background: -webkit-gradient(linear, 0 0, 0 100%, from(rgba(255,105,30,1)), to(rgba(230,95,28,1)));
	background: -webkit-linear-gradient(rgba(255,105,30,1) 0%, rgba(230,95,28,1) 100%);
	background: -moz-linear-gradient(rgba(255,105,30,1) 0%, rgba(230,95,28,1) 100%);
	background: -o-linear-gradient(rgba(255,105,30,1) 0%, rgba(230,95,28,1) 100%);
	background: linear-gradient(rgba(255,105,30,1) 0%, rgba(230,95,28,1) 100%);
}
.btn02-gradient.blue {
	background: rgba(102,152,203,1);
	background: -moz-linear-gradient(top, rgba(102,152,203,1) 0%, rgba(92,138,184,1) 100%);
	background: -webkit-gradient(linear, 0 0, 0 100%, from(rgba(102,152,203,1)), to(rgba(92,138,184,1)));
	background: -webkit-linear-gradient(top, rgba(102,152,203,1) 0%, rgba(92,138,184,1) 100%);
	background: -o-linear-gradient(top, rgba(102,152,203,1) 0%, rgba(92,138,184,1) 100%);
	background: -ms-linear-gradient(top, rgba(102,152,203,1) 0%, rgba(92,138,184,1) 100%);
	background: linear-gradient(to bottom, rgba(102,152,203,1) 0%, rgba(92,138,184,1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#6698cb', endColorstr='#5c8ab8', GradientType=0 );
}
.btn02-gradient.purple {
	background: rgba(203,153,197,1);
	background: -moz-linear-gradient(top, rgba(203,153,197,1) 0%, rgba(181,134,176,1) 100%);
	background: -webkit-gradient(linear, 0 0, 0 100%, from(rgba(203,153,197,1)), to(rgba(181,134,176,1)));
	background: -webkit-linear-gradient(top, rgba(203,153,197,1) 0%, rgba(181,134,176,1) 100%);
	background: -o-linear-gradient(top, rgba(203,153,197,1) 0%, rgba(181,134,176,1) 100%);
	background: -ms-linear-gradient(top, rgba(203,153,197,1) 0%, rgba(181,134,176,1) 100%);
	background: linear-gradient(to bottom, rgba(203,153,197,1) 0%, rgba(181,134,176,1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#cb99c5', endColorstr='#b586b0', GradientType=0 );
}
.btn02-gradient.yellow {
	background: rgba(240,210,100,1);
	background: -webkit-gradient(linear, 0 0, 0 100%, from(rgba(240,210,100,1)), to(rgba(229,201,96,1)));
	background: -webkit-linear-gradient(rgba(240,210,100,1) 0%, rgba(229,201,96,1) 100%);
	background: -moz-linear-gradient(rgba(240,210,100,1) 0%, rgba(229,201,96,1) 100%);
	background: -o-linear-gradient(rgba(240,210,100,1) 0%, rgba(229,201,96,1) 100%);
	background: linear-gradient(rgba(240,210,100,1) 0%, rgba(229,201,96,1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#f0d264', endColorstr='#e5c960', GradientType=0 );
}
.btn02-gradient.green {
	background: rgba(130,200,160,1);
	background: -moz-linear-gradient(top, rgba(130,200,160,1) 0%, rgba(130,199,158,1) 100%);
	background: -webkit-gradient(linear, 0 0, 0 100%, from(rgba(130,200,160,1)), to(rgba(130,199,158,1)));
	background: -webkit-linear-gradient(top, rgba(130,200,160,1) 0%, rgba(130,199,158,1) 100%);
	background: -o-linear-gradient(top, rgba(130,200,160,1) 0%, rgba(130,199,158,1) 100%);
	background: -ms-linear-gradient(top, rgba(130,200,160,1) 0%, rgba(130,199,158,1) 100%);
	background: linear-gradient(to bottom, rgba(130,200,160,1) 0%, rgba(124, 185, 149, 1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#82c8a0', endColorstr='#82c79e', GradientType=0 );
}

.btn02-gradient.red:active 	  {background: #E35252;}
.btn02-gradient.orange:active {background: #E8601B;}
.btn02-gradient.cyan:active 	{background: #169499;}
.btn02-gradient.blue:active 	{background: #608FBF;}
.btn02-gradient.purple:active {background: #BD8EB7;}
.btn02-gradient.yellow:active {background: #DBC05B;}
.btn02-gradient.green:active  {background: #72B08E;}
</style>

<div class="subCon3" ng-controller="tblmsListCtrl">

  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
  </div>

    <div style="margin-top:10px; text-align:center;">
        <span><button class="btn02-gradient green" style="cursor:pointer;display:none;" id="btnSetting" ng-click="fnTblms('setting');"><s:message code="tblms.setting" /></button></span>
        &nbsp;
        <span><button class="btn02-gradient green" style="cursor:pointer;"  id="btnExplainSetting" ng-click="fnTblms('explainSetting');"><s:message code="tblms.setting" /></button></span>
        &nbsp;
        <span><button class="btn02-gradient green" style="cursor:pointer;" id="btnChromeSetup" ng-click="fnTblms('chromeSetup');"><s:message code="tblms.chromeSetup" /></button></span>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <span><a href="#" class="btn01 first01" id="tblmsOpn" ng-click="fnTblms('tblmsOpn');"><s:message code="tblms.open" /></a></span>
        <span><a href="#" class="btn01 first01" id="tblptOpn" ng-click="fnTblpt('tblptOpn');"><s:message code="tblpt.open" /></a></span>
        <span><a href="#" class="btn01 first01" id="tblptClr" ng-click="fnTblpt('tblptClr');"><s:message code="tblpt.clear" /></a></span>
    </div>

    <div style="margin-top:10px; text-align:center;display:none;">
        <button  class="btn02-gradient purple"   type='button' onclick="window.open('http://192.168.0.72:2020/login/login_check_tbl.jsp?AutoFg=M&user_id=a000005&user_pwd=&login_auto_serial=&appfg=web&tblfg=tblms', 'popup', '');" style='width:200px; height:50px;'>
        창 오픈 샘플
        </button>
    </div>

    <div style="margin-top:10px; text-align:left;display:none;">
        <span><button class="btn02-gradient orange" style="cursor:pointer;"  id="btnExplainSetting2" ng-click="fnTblms('explainSetting');"><s:message code="tblms.setting" /></button></span>
    </div>

    <div style="margin-top:10px; text-align:left;display:none;">
        1/4
    </div>
    <div id="viewStyleLayer1"   style="height: 406px; width: 924px;display:none;"></div>

    <div style="margin-top:10px; text-align:left;display:none;">
        2/4
    </div>
    <div id="viewStyleLayer2"   style="height: 406px; width: 924px;display:none;"></div>

    <div style="margin-top:10px; text-align:left;display:none;">
        3/4
    </div>
    <div id="viewStyleLayer3"   style="height: 406px; width: 924px;display:none;"></div>

    <div style="margin-top:10px; text-align:left;display:none;">
        4/4
    </div>
    <div id="viewStyleLayer4"   style="height: 406px; width: 924px;display:none;"></div>

</div>

<script type="text/javascript">
    var s_userId      = "${userId}";
    var s_storeCd     = "${storeCd}";
    var s_vUserIdChk  = "${vUserIdChk}";

    document.getElementById("viewStyleLayer1").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "01" + ".PNG" + "')";
    document.getElementById("viewStyleLayer2").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "02" + ".PNG" + "')";
    document.getElementById("viewStyleLayer3").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "03" + ".PNG" + "')";
    document.getElementById("viewStyleLayer4").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "04" + ".PNG" + "')";

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/tblms/tblms.js?ver=20220401.04" charset="utf-8"></script>

