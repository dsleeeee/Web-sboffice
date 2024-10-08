<div id="smsChargeView" style="display: none;">

<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<%
    /* ============================================================================== */
    /* =   PAGE : 결제 요청 PAGE                                                    = */
    /* = -------------------------------------------------------------------------- = */
    /* =   이 페이지는 표준웹을 통해서 결제자가 결제 요청을 하는 페이지             = */
    /* =   입니다. 아래의 ※ 필수, ※ 옵션 부분과 매뉴얼을 참조하셔서 연동을        = */
    /* =   진행하여 주시기 바랍니다.                                                = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2020  NHN KCP Inc.   All Rights Reserverd.                = */
    /* ============================================================================== */
%>
<%
    /* ============================================================================== */
    /* =   환경 설정 파일 Include                                                   = */
    /* = -------------------------------------------------------------------------- = */
    /* =   ※ 필수                                                                  = */
    /* =   테스트 및 실결제 연동시 site_conf_inc.jsp 파일을 수정하시기 바랍니다.    = */
    /* = -------------------------------------------------------------------------- = */
%>
<%@ include file="../smsCharge/cfg/site_conf_inc.jsp"%>
<%
    request.setCharacterEncoding ( "euc-kr" ) ;
    /* = -------------------------------------------------------------------------- = */
    /* =   환경 설정 파일 Include END                                               = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null 값을 처리하는 메소드                                                = */
    /* = -------------------------------------------------------------------------- = */
    public String f_get_parm( String val )
    {
        if ( val == null ) val = "";
        return  val;
    }
    /* ============================================================================== */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>SMS충전</title>

    <!-- 공통: font preload -->
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Regular.woff" type="font/woff" as="font" crossorigin>
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Medium.woff" type="font/woff" as="font" crossorigin>
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Bold.woff" type="font/woff" as="font" crossorigin>
    <!-- //공통: font preload -->

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <style type="text/css" >
        a {text-decoration:none; color:inherit;}
        p.txt {line-height:23px; color:#4383b0; white-space: pre;}
        p.txt span {font-weight:bold; color:#e44541;}

        #sample_wrap h1 {width:700px; margin:0px auto 0 ; font-size:12px; background:#0e66a4; color:#fff; height:35px; line-height:35px; text-align:center;}
        .sample {margin:0 auto; width:698px; border-left:1px solid #0e66a4; border-right:1px solid #0e66a4; border-bottom:1px solid #0e66a4;}
    </style>

    <script type="text/javascript">
        /****************************************************************/
        /* m_Completepayment  설명                                      */
        /****************************************************************/
        /* 인증완료시 재귀 함수                                         */
        /* 해당 함수명은 절대 변경하면 안됩니다.                        */
        /* 해당 함수의 위치는 payplus.js 보다먼저 선언되어여 합니다.    */
        /* Web 방식의 경우 리턴 값이 form 으로 넘어옴                   */
        /****************************************************************/
        function m_Completepayment( FormOrJson, closeEvent )
        {
            var frm = document.order_info;

            /********************************************************************/
            /* FormOrJson은 가맹점 임의 활용 금지                               */
            /* frm 값에 FormOrJson 값이 설정 됨 frm 값으로 활용 하셔야 됩니다.  */
            /* FormOrJson 값을 활용 하시려면 기술지원팀으로 문의바랍니다.       */
            /********************************************************************/
            GetField( frm, FormOrJson );


            if( frm.res_cd.value == "0000" )
            {
                // alert("결제 승인 요청 전,\n\n반드시 결제창에서 고객님이 결제 인증 완료 후\n\n리턴 받은 ordr_chk 와 업체 측 주문정보를\n\n다시 한번 검증 후 결제 승인 요청하시기 바랍니다."); //업체 연동 시 필수 확인 사항.
                /*
                    가맹점 리턴값 처리 영역
                */
                // alert(frm.res_cd.value);
                // alert(frm.res_msg.value);
                // alert(frm.enc_info.value);
                // alert(frm.enc_data.value);

                frm.submit();
            }
            else
            {
                alert( "[" + frm.res_cd.value + "] " + frm.res_msg.value );

                closeEvent();
            }
        }
    </script>

    <%
        /* ============================================================================== */
        /* =   Javascript source Include                                                = */
        /* = -------------------------------------------------------------------------- = */
        /* =   ※ 필수                                                                  = */
        /* =   테스트 및 실결제 연동시 site_conf_inc.jsp파일을 수정하시기 바랍니다.     = */
        /* = -------------------------------------------------------------------------- = */
    %>
    <script type="text/javascript" src="<%= g_conf_js_url %>"></script>
    <%
        /* = -------------------------------------------------------------------------- = */
        /* =   Javascript source Include END                                            = */
        /* ============================================================================== */
    %>
    <script type="text/javascript">
        function amt()
        {
            var frm = document.order_info;

            if (frm.amt[0].checked)
            {
                frm.good_mny.value = frm.amt[0].value;
                frm.good_mny_qty.value = frm.qty[0].value;
            }

            else if (frm.amt[1].checked)
            {
                frm.good_mny.value = frm.amt[1].value;
                frm.good_mny_qty.value = frm.qty[1].value;
            }

            else if (frm.amt[2].checked)
            {
                frm.good_mny.value = frm.amt[2].value;
                frm.good_mny_qty.value = frm.qty[2].value;
            }

            else if (frm.amt[3].checked)
            {
                frm.good_mny.value = frm.amt[3].value;
                frm.good_mny_qty.value = frm.qty[3].value;
            }

            else if (frm.amt[4].checked)
            {
                frm.good_mny.value = frm.amt[4].value;
                frm.good_mny_qty.value = frm.qty[4].value;
            }

            else if (frm.amt[5].checked)
            {
                frm.good_mny.value = frm.amt[5].value;
                frm.good_mny_qty.value = frm.qty[5].value;
            }

            else if (frm.amt[6].checked)
            {
                frm.good_mny.value = frm.amt[6].value;
                frm.good_mny_qty.value = frm.qty[6].value;
            }

            else if (frm.amt[7].checked)
            {
                frm.good_mny.value = frm.amt[7].value;
                frm.good_mny_qty.value = frm.qty[7].value;
            }

            else if (frm.amt[8].checked)
            {
                frm.good_mny.value = frm.amt[8].value;
                frm.good_mny_qty.value = frm.qty[8].value;
            }

            else if (frm.amt[9].checked)
            {
                frm.good_mny.value = frm.amt[9].value;
                frm.good_mny_qty.value = frm.qty[9].value;
            }
        }

        /* 표준웹 실행 */
        function jsf__pay( pay, form )
        {
            form.pay_method.value = pay; //신용카드, 계좌이체, 휴대폰결제

            amt();

            try
            {
                KCP_Pay_Execute( form );
            }
            catch (e)
            {
                /* IE 에서 결제 정상종료시 throw로 스크립트 종료 */
            }
        }

        /* 주문번호 생성 예제 */
        function init_orderid()
        {
            var today = new Date();
            var year  = today.getFullYear();
            var month = today.getMonth() + 1;
            var date  = today.getDate();
            var time  = today.getTime();

            if(parseInt(month) < 10) {
                month = "0" + month;
            }

            if(parseInt(date) < 10) {
                date = "0" + date;
            }

            // var order_idxx = "TEST" + year + "" + month + "" + date + "" + time;
            var order_idxx = year + "" + month + "" + date + "" + time;

            document.order_info.ordr_idxx.value = order_idxx; // 주문번호

            document.order_info.orgnCd.value = "${orgnCd}"; // 소속코드
            document.order_info.userId.value = "${userId}"; // 사용자ID
        }
    </script>
</head>

<body onload="init_orderid();">
<!--wrap-->
<div class="wrap">


    <!-- SMS충전결제 결과 -->
    <div ng-controller="smsChargeCtrl">
        <div class="subCon">
            <%-- 메세지 건당 가격안내 --%>
            <button class="btn_skyblue fl ml30" style="line-height:30px;" ng-click="msgOneAmtGuidePopup()">
                건별 가격안내
            </button>
            <%-- 잔여금액 알림 설정 --%>
            <button class="btn_skyblue fl ml10" style="line-height:30px;" ng-click="restSmsAmtAlimSettingPopup()">
                잔여금액 알림 설정
            </button>
            <%-- 현재잔여금액 --%>
            <button class="btn_skyblue fl ml10" style="line-height:30px;" ng-click="restSmsAmtPopup()">
                현재잔여금액
            </button>
        </div>
    </div>
    <script type="text/javascript">
        var app = agrid.getApp();

        // SMS충전결제 결과
        function goChargeResult(use_pay_method, app_time, amount, amount_qty, tno, res_cd, res_msg)
        {
            var params = {};
            if(use_pay_method == "100000000000") {
                params.pgresource = "11"; // 신용카드
            }
            params.chargeDate = app_time.substring(0,8); // 승인시간
            params.chargeTime = app_time.substring(8,14); // 승인시간
            params.chargeTot = amount; // 결제금액(KCP 실제 거래 금액)
            params.chargeAmt = amount_qty; // 충전금액
            params.controlno = tno; // KCP 거래번호
            params.resultcode = res_cd; // 결과 코드
            params.resultmessage = res_msg; // 결과 메세지

            var scope = agrid.getScope('smsChargeCtrl');
            scope.chargeResultShow(params);
        }

        app.controller('smsChargeCtrl', ['$scope', '$http', function ($scope, $http) {

            // 상위 객체 상속 : T/F 는 picker
            angular.extend(this, new RootController('smsChargeCtrl', $scope, $http, false));

            // grid 초기화 : 생성되기전 초기화되면서 생성된다
            $scope.initGrid = function (s, e) {
            };

            // <-- 검색 호출 -->
            $scope.$on("smsChargeCtrl", function(event, data) {
                event.preventDefault();
            });
            // <-- //검색 호출 -->

            // SMS충전결제 결과
            $scope.chargeResultShow = function(data){
                $scope.setSelectedSmsChargeResult(data);
                $scope.wjSmsChargeResultLayer.show(true);
            };

            // 선택
            $scope.selectedSmsChargeResult;
            $scope.setSelectedSmsChargeResult = function(store) {
                $scope.selectedSmsChargeResult = store;
            };
            $scope.getSelectedSmsChargeResult = function() {
                return $scope.selectedSmsChargeResult;
            };

            // 메세지 건당 가격안내
            $scope.msgOneAmtGuidePopup = function(){
                $scope.wjMsgOneAmtGuideLayer.show(true);
            };

            // 잔여금액 알림 설정
            $scope.restSmsAmtAlimSettingPopup = function(){
                $scope.wjRestSmsAmtAlimSettingLayer.show(true);
            };

            // 현재잔여금액
            $scope.restSmsAmtPopup = function(){
                $scope.wjRestSmsAmtLayer.show(true);
            };

            // 화면 ready 된 후 설정
            angular.element(document).ready(function () {

                // SMS충전결제 결과 팝업 핸들러 추가
                $scope.wjSmsChargeResultLayer.shown.addHandler(function (s) {
                    setTimeout(function() {
                        $scope._broadcast('smsChargeResultCtrl', $scope.getSelectedSmsChargeResult());
                    }, 50)
                });

                // 메세지 건당 가격안내 팝업 핸들러 추가
                $scope.wjMsgOneAmtGuideLayer.shown.addHandler(function (s) {
                    setTimeout(function() {
                        $scope._broadcast('msgOneAmtGuideCtrl', null);
                    }, 50)
                });

                // 잔여금액 알림 설정 팝업 핸들러 추가
                $scope.wjRestSmsAmtAlimSettingLayer.shown.addHandler(function (s) {
                    setTimeout(function() {
                        $scope._broadcast('restSmsAmtAlimSettingCtrl', null);
                    }, 50)
                });

                // 현재잔여금액 팝업 핸들러 추가
                $scope.wjRestSmsAmtLayer.shown.addHandler(function (s) {
                    setTimeout(function() {
                        $scope._broadcast('restSmsAmtCtrl', null);
                    }, 50)
                });
            });
        }]);
    </script>
    <%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
    <%-- SMS충전결제 결과 팝업 --%>
    <c:import url="/WEB-INF/view/adi/sms/smsCharge/smsChargeResult.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 메세지 건당 가격안내 팝업 --%>
    <c:import url="/WEB-INF/view/adi/sms/smsCharge/msgOneAmtGuide.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 잔여금액 알림 설정 팝업 --%>
    <c:import url="/WEB-INF/view/adi/sms/smsCharge/restSmsAmtAlimSetting.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 현재잔여금액 팝업 --%>
    <c:import url="/WEB-INF/view/adi/sms/smsCharge/restSmsAmt.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
    <!-- //SMS충전결제 결과 -->


    <!-- 주문정보 입력 form : order_info -->
    <form name="order_info" method="post" action="/adi/sms/smsCharge/smsCharge/charge.sb" target="chargeFrm">
        <iframe name="chargeFrm" style="display:none;"></iframe>

        <%
            /* ============================================================================== */
            /* =   1. 주문 정보 입력                                                        = */
            /* = -------------------------------------------------------------------------- = */
            /* =   결제에 필요한 주문 정보를 입력 및 설정합니다.                            = */
            /* = -------------------------------------------------------------------------- = */
        %>
        <!-- contents -->
        <div id="sample_wrap">
            <h1>SMS/알림톡 충전</h1>
            <!-- 상단 문구 -->
            <div class="sample">
                <table class="tbl" cellpadding="0" cellspacing="0" align="center">
                    <tr>
                        <td>
                            <table width='640' border='0' cellpadding='2' cellspacing='0' style="margin-left: 20px; margin-right: 20px;">
                                <colgroup>
                                    <col width="30%"/>
                                    <col width="70%"/>
                                    <%--<col width="35%"/>--%>
                                </colgroup>
                                <tr height='34'>
                                    <td colspan='3' style='color:#585858; font-size:10pt;'><img src='/resource/solbipos/css/img/sms/list_icon_b.jpg' align='absmiddle'/><b> 충전된 금액은 SMS/알림톡에 모두 사용할 수 있습니다.</b></td>
                                </tr>
                                <tr height='34'>
                                    <td colspan='3' style='color:#585858; font-size:10pt;'><img src='/resource/solbipos/css/img/sms/list_icon_b.jpg' align='absmiddle'/><b> 충전금액을 선택하시기 바랍니다.</b></td>
                                </tr>
                                <tr bgcolor='#79a9d2' height='3'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td style='color:#585858;'><b>선택</b></td>
                                    <td style='color:#585858;'><b>금액</b></td>
                                    <%--<td style='color:#585858;'><b>충전수량</b></td>--%>
                                </tr>
                                <tr bgcolor='#79a9d2' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='11000' checked/>
                                        <input type='hidden' name='qty' value='10000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>10,000원</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>500통</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='22000'/>
                                        <input type='hidden' name='qty' value='20000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>20,000원</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>1,000통</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='33000'/>
                                        <input type='hidden' name='qty' value='30000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>30,000원</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>2,000통</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='55000'/>
                                        <input type='hidden' name='qty' value='50000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>50,000원</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>3,000통</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='77000'/>
                                        <input type='hidden' name='qty' value='70000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>70,000원</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>5,000통</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='110000'/>
                                        <input type='hidden' name='qty' value='100000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>100,000원</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>100통</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='165000'/>
                                        <input type='hidden' name='qty' value='150000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>150,000원</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>1004통</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='220000'/>
                                        <input type='hidden' name='qty' value='200000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>200,000원</font></td>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='550000'/>
                                        <input type='hidden' name='qty' value='500000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>500,000원</font></td>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='1100000'/>
                                        <input type='hidden' name='qty' value='1000000'/>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>1,000,000원</font></td>
                                </tr>
                                <tr bgcolor='#bfccd8' height='1'><td colspan='3'></td></tr>
                                <tr height='30'>
                                    <td colspan='3' style='color:#585858;' align='right'>* 부가세 별도금액 입니다.</td>
                                </tr>
                                <tr height='34'>
                                    <td colspan='3' style='color:#585858; font-size:10pt;'><img src='/resource/solbipos/css/img/sms/list_icon_b.jpg' align='absmiddle'/><b> 결제수단을 선택하시기 바랍니다.</b></td>
                                </tr>
                                <tr bgcolor='#79a9d2' height='3'><td colspan='3'></td></tr>
                                <tr height='10'><td colspan='3'></td></tr>
                                <tr>
                                    <td colspan='3' align='center'>
                                        <%-- 신용카드 --%>
                                        <img src='/resource/solbipos/css/img/sms/btn_pay_card.jpg'    onclick="jsf__pay('100000000000', document.order_info);"/>
                                        <%--&nbsp;--%>
                                        <%-- 계좌이체 --%>
                                        <%--<img src='/resource/solbipos/css/img/sms/btn_pay_account.jpg'    onclick="jsf__pay('010000000000', document.order_info);"/>--%>
                                        <%--&nbsp;--%>
                                        <%-- 휴대폰결제 --%>
                                        <%--<img src='/resource/solbipos/css/img/sms/btn_pay_hp.jpg'    onclick="jsf__pay('000010000000', document.order_info);"/>--%>
                                    </td>
                                </tr>
                                <tr height='20'><td></td></tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <!-- Payplus Plug-in 설치 안내 -->
                <%--<div id="display_setup_message" style="display:none">--%>
                <%--<div id="display_setup_message">--%>
                    <%--<p class="txt">--%>
            <%--결제를 계속 하시려면 상단의 노란색 표시줄을 클릭 하시거나 <a href="http://pay.kcp.co.kr/plugin_new/file/KCPUXWizard.exe"><span>[수동설치]</span></a>를 눌러--%>
            <%--Payplus Plug-in을 설치하시기 바랍니다.--%>
            <%--[수동설치]를 눌러 설치하신 경우 새로고침(F5)키를 눌러 진행하시기 바랍니다.--%>
                    <%--</p>--%>
                <%--</div>--%>
            </div>
        </div>
        <!-- //contents -->

        <!-- 공통: js -->
        <%--<script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>--%>
        <script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/front.js"></script>
        <!-- //공통: js -->



        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   1. 주문 정보 입력 END                                                    = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   2. 가맹점 필수 정보 설정                                                 = */
            /* = -------------------------------------------------------------------------- = */
            /* =   ※ 필수 - 결제에 반드시 필요한 정보입니다.                               = */
            /* =   site_conf_inc.jsp 파일을 참고하셔서 수정하시기 바랍니다.                 = */
            /* = -------------------------------------------------------------------------- = */
            // 요청종류 : 승인(pay)/취소,매입(mod) 요청시 사용
        %>
        <input type="hidden" name="req_tx"          value="pay" />
        <input type="hidden" name="site_cd"         value="<%= g_conf_site_cd   %>" />
        <input type="hidden" name="site_name"       value="<%= g_conf_site_name %>" />
        <input type="hidden" name="pay_method" value="100000000000"> <!-- 결제 수단 - 디폴트값 : 신용카드 -->
        <input type="hidden" name="ordr_idxx" value="" maxlength="40" /> <!-- 주문번호 -->
        <input type="hidden" name="good_mny" value="" /> <!-- 결제금액(good_mny) - ※ 필수 : 값 설정시 ,(콤마)를 제외한 숫자만 입력하여 주십시오. -->
        <input type="hidden" name="good_mny_qty" value="" /> <!-- 충전금액(good_mny_qty) - ※ 필수 : 값 설정시 ,(콤마)를 제외한 숫자만 입력하여 주십시오. -->
        <%
            /*
               할부옵션 : 표준웹에서 카드결제시 최대로 표시할 할부개월 수를 설정합니다.(0 ~ 18 까지 설정 가능)
               ※ 주의  - 할부 선택은 결제금액이 50,000원 이상일 경우에만 가능, 50000원 미만의 금액은 일시불로만 표기됩니다
                          예) value 값을 "5" 로 설정했을 경우 => 카드결제시 결제창에 일시불부터 5개월까지 선택가능
            */
        %>
        <input type="hidden" name="quotaopt"        value="12"/>
        <!-- 필수 항목 : 결제 금액/화폐단위 -->
        <input type="hidden" name="currency"        value="WON"/>
        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   2. 가맹점 필수 정보 설정 END                                             = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   3. 표준웹 필수 정보(변경 불가)                                   = */
            /* = -------------------------------------------------------------------------- = */
            /* =   결제에 필요한 주문 정보를 입력 및 설정합니다.                            = */
            /* = -------------------------------------------------------------------------- = */
        %>
        <!-- 표준웹 설정 정보입니다(변경 불가) -->
        <input type="hidden" name="module_type"     value="<%= module_type %>"/>
        <!--
              ※ 필 수
                  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다
                  값을 설정하지 마십시오
        -->
        <input type="hidden" name="res_cd"          value=""/>
        <input type="hidden" name="res_msg"         value=""/>
        <input type="hidden" name="enc_info"        value=""/>
        <input type="hidden" name="enc_data"        value=""/>
        <input type="hidden" name="ret_pay_method"  value=""/>
        <input type="hidden" name="tran_cd"         value=""/>
        <input type="hidden" name="use_pay_method"  value=""/>

        <!-- 주문정보 검증 관련 정보 : 표준웹 에서 설정하는 정보입니다 -->
        <input type="hidden" name="ordr_chk"        value=""/>

        <!--  현금영수증 관련 정보 : 표준웹 에서 설정하는 정보입니다 -->
        <input type="hidden" name="cash_yn"         value=""/>
        <input type="hidden" name="cash_tr_code"    value=""/>
        <input type="hidden" name="cash_id_info"    value=""/>

        <!-- 2012년 8월 18일 전자상거래법 개정 관련 설정 부분 -->
        <!-- 제공 기간 설정 0:일회성 1:기간설정(ex 1:2012010120120131)  -->
        <input type="hidden" name="good_expr" value="0">

        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   3. 표준웹 필수 정보 END                                          = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   4. 옵션 정보                                                             = */
            /* = -------------------------------------------------------------------------- = */
            /* =   ※ 옵션 - 결제에 필요한 추가 옵션 정보를 입력 및 설정합니다.             = */
            /* = -------------------------------------------------------------------------- = */

    /* 사용카드 설정 여부 파라미터 입니다.(통합결제창 노출 유무)
    <input type="hidden" name="used_card_YN"        value="Y"/> */
    /* 사용카드 설정 파라미터 입니다. (해당 카드만 결제창에 보이게 설정하는 파라미터입니다. used_card_YN 값이 Y일때 적용됩니다.
    /<input type="hidden" name="used_card"        value="CCBC:CCKM:CCSS"/> */

    /* 신용카드 결제시 OK캐쉬백 적립 여부를 묻는 창을 설정하는 파라미터 입니다
         포인트 가맹점의 경우에만 창이 보여집니다
        <input type="hidden" name="save_ocb"        value="Y"/> */

    /* 고정 할부 개월 수 선택
        value값을 "7" 로 설정했을 경우 => 카드결제시 결제창에 할부 7개월만 선택가능
    <input type="hidden" name="fix_inst"        value="07"/> */

    /*  무이자 옵션
            ※ 설정할부    (가맹점 관리자 페이지에 설정 된 무이자 설정을 따른다)                             - "" 로 설정
            ※ 일반할부    (KCP 이벤트 이외에 설정 된 모든 무이자 설정을 무시한다)                           - "N" 로 설정
            ※ 무이자 할부 (가맹점 관리자 페이지에 설정 된 무이자 이벤트 중 원하는 무이자 설정을 세팅한다)   - "Y" 로 설정
    <input type="hidden" name="kcp_noint"       value=""/> */

    /*  무이자 설정
            ※ 주의 1 : 할부는 결제금액이 50,000 원 이상일 경우에만 가능
            ※ 주의 2 : 무이자 설정값은 무이자 옵션이 Y일 경우에만 결제 창에 적용
            예) BC 2,3,6개월, 국민 3,6개월, 삼성 6,9개월 무이자 : CCBC-02:03:06,CCKM-03:06,CCSS-03:06:04
    <input type="hidden" name="kcp_noint_quota" value="CCBC-02:03:06,CCKM-03:06,CCSS-03:06:09"/> */


    /* 해외카드 구분하는 파라미터 입니다.(해외비자, 해외마스터, 해외JCB로 구분하여 표시)
    <input type="hidden" name="used_card_CCXX"        value="Y"/> */

    /*  가상계좌 은행 선택 파라미터
         ※ 해당 은행을 결제창에서 보이게 합니다.(은행코드는 매뉴얼을 참조)
    <input type="hidden" name="wish_vbank_list" value="05:03:04:07:11:23:26:32:34:81:71"/> */

    /*  가상계좌 입금 기한 설정하는 파라미터 - 발급일 + 3일
    <input type="hidden" name="vcnt_expire_term" value="3"/> */

    /*  가상계좌 입금 시간 설정하는 파라미터
         HHMMSS형식으로 입력하시기 바랍니다
         설정을 안하시는경우 기본적으로 23시59분59초가 세팅이 됩니다
         <input type="hidden" name="vcnt_expire_term_time" value="120000"/> */

    /* 포인트 결제시 복합 결제(신용카드+포인트) 여부를 결정할 수 있습니다.- N 일경우 복합결제 사용안함
        <input type="hidden" name="complex_pnt_yn" value="N"/>    */

    /* 현금영수증 등록 창을 출력 여부를 설정하는 파라미터 입니다
         ※ Y : 현금영수증 등록 창 출력
         ※ N : 현금영수증 등록 창 출력 안함
    ※ 주의 : 현금영수증 사용 시 KCP 상점관리자 페이지에서 현금영수증 사용 동의를 하셔야 합니다
        <input type="hidden" name="disp_tax_yn"     value="Y"/> */

    /* 결제창에 가맹점 사이트의 로고를 표준웹 좌측 상단에 출력하는 파라미터 입니다
       업체의 로고가 있는 URL을 정확히 입력하셔야 하며, 최대 150 X 50  미만 크기 지원

    ※ 주의 : 로고 용량이 150 X 50 이상일 경우 site_name 값이 표시됩니다.
        <input type="hidden" name="site_logo"       value="" /> */

    /* 결제창 영문 표시 파라미터 입니다. 영문을 기본으로 사용하시려면 Y로 세팅하시기 바랍니다
        2010-06월 현재 신용카드와 가상계좌만 지원됩니다
        <input type="hidden" name="eng_flag"      value="Y"> */

    /* KCP는 과세상품과 비과세상품을 동시에 판매하는 업체들의 결제관리에 대한 편의성을 제공해드리고자,
        복합과세 전용 사이트코드를 지원해 드리며 총 금액에 대해 복합과세 처리가 가능하도록 제공하고 있습니다
        복합과세 전용 사이트 코드로 계약하신 가맹점에만 해당이 됩니다
        상품별이 아니라 금액으로 구분하여 요청하셔야 합니다
        총결제 금액은 과세금액 + 부과세 + 비과세금액의 합과 같아야 합니다.
        (good_mny = comm_tax_mny + comm_vat_mny + comm_free_mny)

        <input type="hidden" name="tax_flag"       value="TG03">  <!-- 변경불가    -->
        <input type="hidden" name="comm_tax_mny"   value=""    >  <!-- 과세금액    -->
        <input type="hidden" name="comm_vat_mny"   value=""    >  <!-- 부가세     -->
        <input type="hidden" name="comm_free_mny"  value=""    >  <!-- 비과세 금액 --> */

    /* skin_indx 값은 스킨을 변경할 수 있는 파라미터이며 총 7가지가 지원됩니다.
        변경을 원하시면 1부터 7까지 값을 넣어주시기 바랍니다.

        <input type="hidden" name="skin_indx"      value="1"> */

    /* 상품코드 설정 파라미터 입니다.(상품권을 따로 구분하여 처리할 수 있는 옵션기능입니다.)
        <input type="hidden" name="good_cd"      value=""> */

    /* 가맹점에서 관리하는 고객 아이디 설정을 해야 합니다. 상품권 결제 시 반드시 입력하시기 바랍니다.
        <input type="hidden" name="shop_user_id"    value=""/> */

    /* 복지포인트 결제시 가맹점에 할당되어진 코드 값을 입력해야합니다.
        <input type="hidden" name="pt_memcorp_cd"   value=""/> */

    /* 결제창의 상단문구를 변경할 수 있는 파라미터 입니다.
       <input type="hidden" name="kcp_pay_title"   value="상단문구추가"/> */
            /* = -------------------------------------------------------------------------- = */
            /* =   4. 옵션 정보 END                                                         = */
            /* ============================================================================== */
        %>

        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   5. 추가정보                                 = */
            /* ============================================================================== */
        %>
        <input type="hidden" name="orgnCd" value="" /> <!-- 소속코드 -->
        <input type="hidden" name="userId" value="" /> <!-- 사용자ID -->
        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   5. 추가정보 END                                             = */
            /* ============================================================================== */
        %>

    </form>
</div>
<!--//wrap-->
</body>
</html>

</div>