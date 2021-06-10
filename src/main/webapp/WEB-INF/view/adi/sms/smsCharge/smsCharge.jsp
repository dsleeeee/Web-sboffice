<%--<%@ page language="java" contentType="text/html;charset=euc-kr"%>--%>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <title>*** KCP [AX-HUB Version] ***</title>
        <meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
        <%--<link rel="stylesheet" href="/common/css/bok.css" type="text/css"/>--%>
        <style type="text/css" >
            a {text-decoration:none; color:inherit;}
            p.txt {line-height:23px; color:#4383b0; white-space: pre;}
            p.txt span {font-weight:bold; color:#e44541;}

            #sample_wrap h1 {width:700px; margin:50px auto 0 ; font-size:12px; background:#0e66a4; color:#fff; height:35px; line-height:35px; text-align:center;}
            .sample {margin:0 auto; width:698px; border-left:1px solid #0e66a4; border-right:1px solid #0e66a4; border-bottom:1px solid #0e66a4;}
        </style>
    </head>

    <%--<body onload="init_orderid();">--%>
    <body>
        <div id="sample_wrap">
            <h1>SMS 충전</h1>
            <!-- 상단 문구 -->
            <div class="sample">
                <table class="tbl" cellpadding="0" cellspacing="0" align="center">
                    <tr>
                        <td>
                            <table width='640' border='0' cellpadding='2' cellspacing='0' style="margin-left: 20px; margin-right: 20px;">
                                <colgroup>
                                    <col width="30%"/>
                                    <col width="35%"/>
                                    <col width="35%"/>
                                </colgroup>
                                <tr height='34'>
                                    <td colspan='3' style='color:#585858; font-size:10pt;'><img src='/resource/solbipos/css/img/sms/list_icon_b.jpg' align='absmiddle'/><b> 충전금액을 선택하시기 바랍니다.</b></td>
                                </tr>
                                <tr bgcolor='#79a9d2' height='3'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td style='color:#585858;'><b>선택</b></td>
                                    <td style='color:#585858;'><b>금액</b></td>
                                    <td style='color:#585858;'><b>충전수량</b></td>
                                </tr>
                                <tr bgcolor='#79a9d2' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td><input type='radio' name='amt' value='8250'/><input type='hidden' name='qty' value='500'/></td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>7,500원</font></td>
                                    <td><font style='width:50px; text-align:right; color:#585858;                  '>500통</font></td>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td><input type='radio' name='amt' value='16500'/><input type='hidden' name='qty' value='1000'/></td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>15,000원</font></td>
                                    <td><font style='width:50px; text-align:right; color:#585858;                  '>1,000통</font></td>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td><input type='radio' name='amt' value='33000'/><input type='hidden' name='qty' value='2000'/></td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>30,000원</font></td>
                                    <td><font style='width:50px; text-align:right; color:#585858;                  '>2,000통</font></td>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td><input type='radio' name='amt' value='49500'/><input type='hidden' name='qty' value='3000'/></td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>45,000원</font></td>
                                    <td><font style='width:50px; text-align:right; color:#585858;                  '>3,000통</font></td>
                                </tr>
                                <tr bgcolor='#ebebeb' height='1'><td colspan='3'></td></tr>
                                <tr height='26' align='center'>
                                    <td><input type='radio' name='amt' value='82500'/><input type='hidden' name='qty' value='5000'/></td>
                                    <td><font style='width:60px; text-align:right; color:#3187ca; font-weight:bold;'>75,000원</font></td>
                                    <td><font style='width:50px; text-align:right; color:#585858;                  '>5,000통</font></td>
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
                                        <%--<img src='/resource/solbipos/css/img/sms/btn_pay_card.jpg'    onclick="fnSubmit('100000000000');"/>--%>
                                        <img src='/resource/solbipos/css/img/sms/btn_pay_card.jpg'/>
                                        &nbsp;
                                        <%-- 계좌이체 --%>
                                        <%--<img src='/resource/solbipos/css/img/sms/btn_pay_account.jpg' onclick="fnSubmit('010000000000');"/>--%>
                                        <img src='/resource/solbipos/css/img/sms/btn_pay_account.jpg'/>
                                        &nbsp;
                                        <%-- 가상계좌 --%>
                                        <%--<img src='/resource/solbipos/css/img/sms/btn_pay_hp.jpg' onclick="fnSubmit('001000000000');"/>--%>
                                        <img src='/resource/solbipos/css/img/sms/btn_pay_hp.jpg'/>
                                    </td>
                                </tr>
                                <tr height='20'><td></td></tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <!-- Payplus Plug-in 설치 안내 -->
                <%--<div id="display_setup_message" style="display:none">--%>
                <div id="display_setup_message">
                    <p class="txt">
            결제를 계속 하시려면 상단의 노란색 표시줄을 클릭 하시거나 <a href="http://pay.kcp.co.kr/plugin_new/file/KCPUXWizard.exe"><span>[수동설치]</span></a>를 눌러
            Payplus Plug-in을 설치하시기 바랍니다.
            [수동설치]를 눌러 설치하신 경우 새로고침(F5)키를 눌러 진행하시기 바랍니다.
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>