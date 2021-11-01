<div id="smsChargeView" style="display: none;">

<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<%
    /* ============================================================================== */
    /* =   PAGE : ���� ��û PAGE                                                    = */
    /* = -------------------------------------------------------------------------- = */
    /* =   �� �������� ǥ������ ���ؼ� �����ڰ� ���� ��û�� �ϴ� ������             = */
    /* =   �Դϴ�. �Ʒ��� �� �ʼ�, �� �ɼ� �κа� �Ŵ����� �����ϼż� ������        = */
    /* =   �����Ͽ� �ֽñ� �ٶ��ϴ�.                                                = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2020  NHN KCP Inc.   All Rights Reserverd.                = */
    /* ============================================================================== */
%>
<%
    /* ============================================================================== */
    /* =   ȯ�� ���� ���� Include                                                   = */
    /* = -------------------------------------------------------------------------- = */
    /* =   �� �ʼ�                                                                  = */
    /* =   �׽�Ʈ �� �ǰ��� ������ site_conf_inc.jsp ������ �����Ͻñ� �ٶ��ϴ�.    = */
    /* = -------------------------------------------------------------------------- = */
%>
<%@ include file="../smsCharge/cfg/site_conf_inc.jsp"%>
<%
    request.setCharacterEncoding ( "euc-kr" ) ;
    /* = -------------------------------------------------------------------------- = */
    /* =   ȯ�� ���� ���� Include END                                               = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null ���� ó���ϴ� �޼ҵ�                                                = */
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
    <title>SMS����</title>

    <!-- ����: font preload -->
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Regular.woff" type="font/woff" as="font" crossorigin>
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Medium.woff" type="font/woff" as="font" crossorigin>
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Bold.woff" type="font/woff" as="font" crossorigin>
    <!-- //����: font preload -->

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <style type="text/css" >
        a {text-decoration:none; color:inherit;}
        p.txt {line-height:23px; color:#4383b0; white-space: pre;}
        p.txt span {font-weight:bold; color:#e44541;}

        #sample_wrap h1 {width:700px; margin:50px auto 0 ; font-size:12px; background:#0e66a4; color:#fff; height:35px; line-height:35px; text-align:center;}
        .sample {margin:0 auto; width:698px; border-left:1px solid #0e66a4; border-right:1px solid #0e66a4; border-bottom:1px solid #0e66a4;}
    </style>

    <script type="text/javascript">
        /****************************************************************/
        /* m_Completepayment  ����                                      */
        /****************************************************************/
        /* �����Ϸ�� ��� �Լ�                                         */
        /* �ش� �Լ����� ���� �����ϸ� �ȵ˴ϴ�.                        */
        /* �ش� �Լ��� ��ġ�� payplus.js ���ٸ��� ����Ǿ �մϴ�.    */
        /* Web ����� ��� ���� ���� form ���� �Ѿ��                   */
        /****************************************************************/
        function m_Completepayment( FormOrJson, closeEvent )
        {
            var frm = document.order_info;

            /********************************************************************/
            /* FormOrJson�� ������ ���� Ȱ�� ����                               */
            /* frm ���� FormOrJson ���� ���� �� frm ������ Ȱ�� �ϼž� �˴ϴ�.  */
            /* FormOrJson ���� Ȱ�� �Ͻ÷��� ������������� ���ǹٶ��ϴ�.       */
            /********************************************************************/
            GetField( frm, FormOrJson );


            if( frm.res_cd.value == "0000" )
            {
                // alert("���� ���� ��û ��,\n\n�ݵ�� ����â���� ������ ���� ���� �Ϸ� ��\n\n���� ���� ordr_chk �� ��ü �� �ֹ�������\n\n�ٽ� �ѹ� ���� �� ���� ���� ��û�Ͻñ� �ٶ��ϴ�."); //��ü ���� �� �ʼ� Ȯ�� ����.
                /*
                    ������ ���ϰ� ó�� ����
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
        /* =   �� �ʼ�                                                                  = */
        /* =   �׽�Ʈ �� �ǰ��� ������ site_conf_inc.jsp������ �����Ͻñ� �ٶ��ϴ�.     = */
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
            }

            else if (frm.amt[1].checked)
            {
                frm.good_mny.value = frm.amt[1].value;
            }

            else if (frm.amt[2].checked)
            {
                frm.good_mny.value = frm.amt[2].value;
            }

            else if (frm.amt[3].checked)
            {
                frm.good_mny.value = frm.amt[3].value;
            }

            else if (frm.amt[4].checked)
            {
                frm.good_mny.value = frm.amt[4].value;
            }

            else if (frm.amt[5].checked)
            {
                frm.good_mny.value = frm.amt[5].value;
            }

            else if (frm.amt[6].checked)
            {
                frm.good_mny.value = frm.amt[6].value;
            }
        }

        /* ǥ���� ���� */
        function jsf__pay( pay, form )
        {
            form.pay_method.value = pay; //�ſ�ī��, ������ü, �޴�������

            amt();

            try
            {
                KCP_Pay_Execute( form );
            }
            catch (e)
            {
                /* IE ���� ���� ��������� throw�� ��ũ��Ʈ ���� */
            }
        }

        /* �ֹ���ȣ ���� ���� */
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

            document.order_info.ordr_idxx.value = order_idxx; // �ֹ���ȣ

            document.order_info.orgnCd.value = "${orgnCd}"; // �Ҽ��ڵ�
            document.order_info.userId.value = "${userId}"; // �����ID
        }
    </script>
</head>

<body onload="init_orderid();">
<!--wrap-->
<div class="wrap">


    <!-- SMS�������� ��� -->
    <div ng-controller="smsChargeCtrl">
    </div>
    <script type="text/javascript">
        var app = agrid.getApp();

        // SMS�������� ���
        function goChargeResult(use_pay_method, app_time, amount, tno, res_cd, res_msg)
        {
            var params = {};
            if(use_pay_method == "100000000000") {
                params.pgresource = "11"; // �ſ�ī��
            }
            params.chargeDate = app_time.substring(0,8); // ���νð�
            params.chargeTime = app_time.substring(8,14); // ���νð�
            params.chargeAmt = amount; // KCP ���� �ŷ� �ݾ�
            params.controlno = tno; // KCP �ŷ���ȣ
            params.resultcode = res_cd; // ��� �ڵ�
            params.resultmessage = res_msg; // ��� �޼���

            var scope = agrid.getScope('smsChargeCtrl');
            scope.chargeResultShow(params);
        }

        app.controller('smsChargeCtrl', ['$scope', '$http', function ($scope, $http) {

            // ���� ��ü ��� : T/F �� picker
            angular.extend(this, new RootController('smsChargeCtrl', $scope, $http, false));

            // grid �ʱ�ȭ : �����Ǳ��� �ʱ�ȭ�Ǹ鼭 �����ȴ�
            $scope.initGrid = function (s, e) {
            };

            // <-- �˻� ȣ�� -->
            $scope.$on("smsChargeCtrl", function(event, data) {
                event.preventDefault();
            });
            // <-- //�˻� ȣ�� -->

            // SMS�������� ���
            $scope.chargeResultShow = function(data){
                $scope.setSelectedSmsChargeResult(data);
                $scope.wjSmsChargeResultLayer.show(true);
            };

            // ����
            $scope.selectedSmsChargeResult;
            $scope.setSelectedSmsChargeResult = function(store) {
                $scope.selectedSmsChargeResult = store;
            };
            $scope.getSelectedSmsChargeResult = function() {
                return $scope.selectedSmsChargeResult;
            };

            // ȭ�� ready �� �� ����
            angular.element(document).ready(function () {

                // SMS�������� ��� �˾� �ڵ鷯 �߰�
                $scope.wjSmsChargeResultLayer.shown.addHandler(function (s) {
                    setTimeout(function() {
                        $scope._broadcast('smsChargeResultCtrl', $scope.getSelectedSmsChargeResult());
                    }, 50)
                });
            });
        }]);
    </script>
    <%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
    <%-- SMS�������� ��� �˾� --%>
    <c:import url="/WEB-INF/view/adi/sms/smsCharge/smsChargeResult.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
    <!-- //SMS�������� ��� -->


    <!-- �ֹ����� �Է� form : order_info -->
    <form name="order_info" method="post" action="/adi/sms/smsCharge/smsCharge/charge.sb" target="chargeFrm">
        <iframe name="chargeFrm" style="display:none;"></iframe>

        <%
            /* ============================================================================== */
            /* =   1. �ֹ� ���� �Է�                                                        = */
            /* = -------------------------------------------------------------------------- = */
            /* =   ������ �ʿ��� �ֹ� ������ �Է� �� �����մϴ�.                            = */
            /* = -------------------------------------------------------------------------- = */
        %>
        <!-- contents -->
        <div id="sample_wrap">
            <h1>SMS ����</h1>
            <!-- ��� ���� -->
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
                                    <td colspan='3' style='color:#585858; font-size:10pt;'><img src='/resource/solbipos/css/img/sms/list_icon_b.jpg' align='absmiddle'/><b> �����ݾ��� �����Ͻñ� �ٶ��ϴ�.</b></td>
                                </tr>
                                <tr bgcolor='#79a9d2' height='3'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td style='color:#585858;'><b>����</b></td>
                                    <td style='color:#585858;'><b>�ݾ�</b></td>
                                    <%--<td style='color:#585858;'><b>��������</b></td>--%>
                                </tr>
                                <tr bgcolor='#79a9d2' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='8250' checked/>
                                        <%--<input type='hidden' name='qty' value='500'/>--%>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>7,500��</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>500��</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='16500'/>
                                        <%--<input type='hidden' name='qty' value='1000'/>--%>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>15,000��</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>1,000��</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='33000'/>
                                        <%--<input type='hidden' name='qty' value='2000'/>--%>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>30,000��</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>2,000��</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='49500'/>
                                        <%--<input type='hidden' name='qty' value='3000'/>--%>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>45,000��</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>3,000��</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='82500'/>
                                        <%--<input type='hidden' name='qty' value='5000'/>--%>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>75,000��</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>5,000��</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='100'/>
                                        <%--<input type='hidden' name='qty' value='100'/>--%>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>100��</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>100��</font></td>--%>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td>
                                        <input type='radio' name='amt' value='1004'/>
                                        <%--<input type='hidden' name='qty' value='1004'/>--%>
                                    </td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>1004��</font></td>
                                    <%--<td><font style='width:50px; text-align:right; color:#585858;                  '>1004��</font></td>--%>
                                </tr>
                                <tr bgcolor='#bfccd8' height='1'><td colspan='3'></td></tr>
                                <tr height='30'>
                                    <td colspan='3' style='color:#585858;' align='right'>* �ΰ��� �����ݾ� �Դϴ�.</td>
                                </tr>
                                <tr height='34'>
                                    <td colspan='3' style='color:#585858; font-size:10pt;'><img src='/resource/solbipos/css/img/sms/list_icon_b.jpg' align='absmiddle'/><b> ���������� �����Ͻñ� �ٶ��ϴ�.</b></td>
                                </tr>
                                <tr bgcolor='#79a9d2' height='3'><td colspan='3'></td></tr>
                                <tr height='10'><td colspan='3'></td></tr>
                                <tr>
                                    <td colspan='3' align='center'>
                                        <%-- �ſ�ī�� --%>
                                        <img src='/resource/solbipos/css/img/sms/btn_pay_card.jpg'    onclick="jsf__pay('100000000000', document.order_info);"/>
                                        <%--&nbsp;--%>
                                        <%-- ������ü --%>
                                        <%--<img src='/resource/solbipos/css/img/sms/btn_pay_account.jpg'    onclick="jsf__pay('010000000000', document.order_info);"/>--%>
                                        <%--&nbsp;--%>
                                        <%-- �޴������� --%>
                                        <%--<img src='/resource/solbipos/css/img/sms/btn_pay_hp.jpg'    onclick="jsf__pay('000010000000', document.order_info);"/>--%>
                                    </td>
                                </tr>
                                <tr height='20'><td></td></tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <!-- Payplus Plug-in ��ġ �ȳ� -->
                <%--<div id="display_setup_message" style="display:none">--%>
                <%--<div id="display_setup_message">--%>
                    <%--<p class="txt">--%>
            <%--������ ��� �Ͻ÷��� ����� ����� ǥ������ Ŭ�� �Ͻðų� <a href="http://pay.kcp.co.kr/plugin_new/file/KCPUXWizard.exe"><span>[������ġ]</span></a>�� ����--%>
            <%--Payplus Plug-in�� ��ġ�Ͻñ� �ٶ��ϴ�.--%>
            <%--[������ġ]�� ���� ��ġ�Ͻ� ��� ���ΰ�ħ(F5)Ű�� ���� �����Ͻñ� �ٶ��ϴ�.--%>
                    <%--</p>--%>
                <%--</div>--%>
            </div>
        </div>
        <!-- //contents -->

        <!-- ����: js -->
        <%--<script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>--%>
        <script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/front.js"></script>
        <!-- //����: js -->



        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   1. �ֹ� ���� �Է� END                                                    = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   2. ������ �ʼ� ���� ����                                                 = */
            /* = -------------------------------------------------------------------------- = */
            /* =   �� �ʼ� - ������ �ݵ�� �ʿ��� �����Դϴ�.                               = */
            /* =   site_conf_inc.jsp ������ �����ϼż� �����Ͻñ� �ٶ��ϴ�.                 = */
            /* = -------------------------------------------------------------------------- = */
            // ��û���� : ����(pay)/���,����(mod) ��û�� ���
        %>
        <input type="hidden" name="req_tx"          value="pay" />
        <input type="hidden" name="site_cd"         value="<%= g_conf_site_cd   %>" />
        <input type="hidden" name="site_name"       value="<%= g_conf_site_name %>" />
        <input type="hidden" name="pay_method" value="100000000000"> <!-- ���� ���� - ����Ʈ�� : �ſ�ī�� -->
        <input type="hidden" name="ordr_idxx" value="" maxlength="40" /> <!-- �ֹ���ȣ -->
        <input type="hidden" name="good_mny" value="" /> <!-- �����ݾ�(good_mny) - �� �ʼ� : �� ������ ,(�޸�)�� ������ ���ڸ� �Է��Ͽ� �ֽʽÿ�. -->
        <%
            /*
               �Һοɼ� : ǥ�������� ī������� �ִ�� ǥ���� �Һΰ��� ���� �����մϴ�.(0 ~ 18 ���� ���� ����)
               �� ����  - �Һ� ������ �����ݾ��� 50,000�� �̻��� ��쿡�� ����, 50000�� �̸��� �ݾ��� �Ͻúҷθ� ǥ��˴ϴ�
                          ��) value ���� "5" �� �������� ��� => ī������� ����â�� �ϽúҺ��� 5�������� ���ð���
            */
        %>
        <input type="hidden" name="quotaopt"        value="12"/>
        <!-- �ʼ� �׸� : ���� �ݾ�/ȭ����� -->
        <input type="hidden" name="currency"        value="WON"/>
        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   2. ������ �ʼ� ���� ���� END                                             = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   3. ǥ���� �ʼ� ����(���� �Ұ�)                                   = */
            /* = -------------------------------------------------------------------------- = */
            /* =   ������ �ʿ��� �ֹ� ������ �Է� �� �����մϴ�.                            = */
            /* = -------------------------------------------------------------------------- = */
        %>
        <!-- ǥ���� ���� �����Դϴ�(���� �Ұ�) -->
        <input type="hidden" name="module_type"     value="<%= module_type %>"/>
        <!--
              �� �� ��
                  �ʼ� �׸� : ǥ�������� ���� �����ϴ� �κ����� �ݵ�� ���ԵǾ�� �մϴ�
                  ���� �������� ���ʽÿ�
        -->
        <input type="hidden" name="res_cd"          value=""/>
        <input type="hidden" name="res_msg"         value=""/>
        <input type="hidden" name="enc_info"        value=""/>
        <input type="hidden" name="enc_data"        value=""/>
        <input type="hidden" name="ret_pay_method"  value=""/>
        <input type="hidden" name="tran_cd"         value=""/>
        <input type="hidden" name="use_pay_method"  value=""/>

        <!-- �ֹ����� ���� ���� ���� : ǥ���� ���� �����ϴ� �����Դϴ� -->
        <input type="hidden" name="ordr_chk"        value=""/>

        <!--  ���ݿ����� ���� ���� : ǥ���� ���� �����ϴ� �����Դϴ� -->
        <input type="hidden" name="cash_yn"         value=""/>
        <input type="hidden" name="cash_tr_code"    value=""/>
        <input type="hidden" name="cash_id_info"    value=""/>

        <!-- 2012�� 8�� 18�� ���ڻ�ŷ��� ���� ���� ���� �κ� -->
        <!-- ���� �Ⱓ ���� 0:��ȸ�� 1:�Ⱓ����(ex 1:2012010120120131)  -->
        <input type="hidden" name="good_expr" value="0">

        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   3. ǥ���� �ʼ� ���� END                                          = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   4. �ɼ� ����                                                             = */
            /* = -------------------------------------------------------------------------- = */
            /* =   �� �ɼ� - ������ �ʿ��� �߰� �ɼ� ������ �Է� �� �����մϴ�.             = */
            /* = -------------------------------------------------------------------------- = */

    /* ���ī�� ���� ���� �Ķ���� �Դϴ�.(���հ���â ���� ����)
    <input type="hidden" name="used_card_YN"        value="Y"/> */
    /* ���ī�� ���� �Ķ���� �Դϴ�. (�ش� ī�常 ����â�� ���̰� �����ϴ� �Ķ�����Դϴ�. used_card_YN ���� Y�϶� ����˴ϴ�.
    /<input type="hidden" name="used_card"        value="CCBC:CCKM:CCSS"/> */

    /* �ſ�ī�� ������ OKĳ���� ���� ���θ� ���� â�� �����ϴ� �Ķ���� �Դϴ�
         ����Ʈ �������� ��쿡�� â�� �������ϴ�
        <input type="hidden" name="save_ocb"        value="Y"/> */

    /* ���� �Һ� ���� �� ����
        value���� "7" �� �������� ��� => ī������� ����â�� �Һ� 7������ ���ð���
    <input type="hidden" name="fix_inst"        value="07"/> */

    /*  ������ �ɼ�
            �� �����Һ�    (������ ������ �������� ���� �� ������ ������ ������)                             - "" �� ����
            �� �Ϲ��Һ�    (KCP �̺�Ʈ �̿ܿ� ���� �� ��� ������ ������ �����Ѵ�)                           - "N" �� ����
            �� ������ �Һ� (������ ������ �������� ���� �� ������ �̺�Ʈ �� ���ϴ� ������ ������ �����Ѵ�)   - "Y" �� ����
    <input type="hidden" name="kcp_noint"       value=""/> */

    /*  ������ ����
            �� ���� 1 : �Һδ� �����ݾ��� 50,000 �� �̻��� ��쿡�� ����
            �� ���� 2 : ������ �������� ������ �ɼ��� Y�� ��쿡�� ���� â�� ����
            ��) BC 2,3,6����, ���� 3,6����, �Ｚ 6,9���� ������ : CCBC-02:03:06,CCKM-03:06,CCSS-03:06:04
    <input type="hidden" name="kcp_noint_quota" value="CCBC-02:03:06,CCKM-03:06,CCSS-03:06:09"/> */


    /* �ؿ�ī�� �����ϴ� �Ķ���� �Դϴ�.(�ؿܺ���, �ؿܸ�����, �ؿ�JCB�� �����Ͽ� ǥ��)
    <input type="hidden" name="used_card_CCXX"        value="Y"/> */

    /*  ������� ���� ���� �Ķ����
         �� �ش� ������ ����â���� ���̰� �մϴ�.(�����ڵ�� �Ŵ����� ����)
    <input type="hidden" name="wish_vbank_list" value="05:03:04:07:11:23:26:32:34:81:71"/> */

    /*  ������� �Ա� ���� �����ϴ� �Ķ���� - �߱��� + 3��
    <input type="hidden" name="vcnt_expire_term" value="3"/> */

    /*  ������� �Ա� �ð� �����ϴ� �Ķ����
         HHMMSS�������� �Է��Ͻñ� �ٶ��ϴ�
         ������ ���Ͻô°�� �⺻������ 23��59��59�ʰ� ������ �˴ϴ�
         <input type="hidden" name="vcnt_expire_term_time" value="120000"/> */

    /* ����Ʈ ������ ���� ����(�ſ�ī��+����Ʈ) ���θ� ������ �� �ֽ��ϴ�.- N �ϰ�� ���հ��� ������
        <input type="hidden" name="complex_pnt_yn" value="N"/>    */

    /* ���ݿ����� ��� â�� ��� ���θ� �����ϴ� �Ķ���� �Դϴ�
         �� Y : ���ݿ����� ��� â ���
         �� N : ���ݿ����� ��� â ��� ����
    �� ���� : ���ݿ����� ��� �� KCP ���������� ���������� ���ݿ����� ��� ���Ǹ� �ϼž� �մϴ�
        <input type="hidden" name="disp_tax_yn"     value="Y"/> */

    /* ����â�� ������ ����Ʈ�� �ΰ� ǥ���� ���� ��ܿ� ����ϴ� �Ķ���� �Դϴ�
       ��ü�� �ΰ� �ִ� URL�� ��Ȯ�� �Է��ϼž� �ϸ�, �ִ� 150 X 50  �̸� ũ�� ����

    �� ���� : �ΰ� �뷮�� 150 X 50 �̻��� ��� site_name ���� ǥ�õ˴ϴ�.
        <input type="hidden" name="site_logo"       value="" /> */

    /* ����â ���� ǥ�� �Ķ���� �Դϴ�. ������ �⺻���� ����Ͻ÷��� Y�� �����Ͻñ� �ٶ��ϴ�
        2010-06�� ���� �ſ�ī��� ������¸� �����˴ϴ�
        <input type="hidden" name="eng_flag"      value="Y"> */

    /* KCP�� ������ǰ�� �������ǰ�� ���ÿ� �Ǹ��ϴ� ��ü���� ���������� ���� ���Ǽ��� �����ص帮����,
        ���հ��� ���� ����Ʈ�ڵ带 ������ �帮�� �� �ݾ׿� ���� ���հ��� ó���� �����ϵ��� �����ϰ� �ֽ��ϴ�
        ���հ��� ���� ����Ʈ �ڵ�� ����Ͻ� ���������� �ش��� �˴ϴ�
        ��ǰ���� �ƴ϶� �ݾ����� �����Ͽ� ��û�ϼž� �մϴ�
        �Ѱ��� �ݾ��� �����ݾ� + �ΰ��� + ������ݾ��� �հ� ���ƾ� �մϴ�.
        (good_mny = comm_tax_mny + comm_vat_mny + comm_free_mny)

        <input type="hidden" name="tax_flag"       value="TG03">  <!-- ����Ұ�    -->
        <input type="hidden" name="comm_tax_mny"   value=""    >  <!-- �����ݾ�    -->
        <input type="hidden" name="comm_vat_mny"   value=""    >  <!-- �ΰ���     -->
        <input type="hidden" name="comm_free_mny"  value=""    >  <!-- ����� �ݾ� --> */

    /* skin_indx ���� ��Ų�� ������ �� �ִ� �Ķ�����̸� �� 7������ �����˴ϴ�.
        ������ ���Ͻø� 1���� 7���� ���� �־��ֽñ� �ٶ��ϴ�.

        <input type="hidden" name="skin_indx"      value="1"> */

    /* ��ǰ�ڵ� ���� �Ķ���� �Դϴ�.(��ǰ���� ���� �����Ͽ� ó���� �� �ִ� �ɼǱ���Դϴ�.)
        <input type="hidden" name="good_cd"      value=""> */

    /* ���������� �����ϴ� �� ���̵� ������ �ؾ� �մϴ�. ��ǰ�� ���� �� �ݵ�� �Է��Ͻñ� �ٶ��ϴ�.
        <input type="hidden" name="shop_user_id"    value=""/> */

    /* ��������Ʈ ������ �������� �Ҵ�Ǿ��� �ڵ� ���� �Է��ؾ��մϴ�.
        <input type="hidden" name="pt_memcorp_cd"   value=""/> */

    /* ����â�� ��ܹ����� ������ �� �ִ� �Ķ���� �Դϴ�.
       <input type="hidden" name="kcp_pay_title"   value="��ܹ����߰�"/> */
            /* = -------------------------------------------------------------------------- = */
            /* =   4. �ɼ� ���� END                                                         = */
            /* ============================================================================== */
        %>

        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   5. �߰�����                                 = */
            /* ============================================================================== */
        %>
        <input type="hidden" name="orgnCd" value="" /> <!-- �Ҽ��ڵ� -->
        <input type="hidden" name="userId" value="" /> <!-- �����ID -->
        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   5. �߰����� END                                             = */
            /* ============================================================================== */
        %>

    </form>
</div>
<!--//wrap-->
</body>
</html>

</div>