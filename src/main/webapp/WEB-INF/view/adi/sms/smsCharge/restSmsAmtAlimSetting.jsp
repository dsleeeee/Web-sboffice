<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjRestSmsAmtAlimSettingLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:590px;height:300px;" fade-in="false" fade-out="false">
    <div ng-controller="restSmsAmtAlimSettingCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="restSmsAmtAlimSetting.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="w100 mb20">
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15">※ 잔여금액 알림 설정이란?</p>
                    <p class="tl s14 mt5 lh15">- 충전하신 잔여금액이 '잔여금액 설정값' 이하시 입력하신 번호로 문자서비스를 받으실 수</p>
                    <p class="tl s14 mt5 lh15">&nbsp;&nbsp;있습니다.</p>
                    <p class="tl s14 mt5 lh15">※ 해당 서비스 이용시 비용은 '솔비포스'에서 부담합니다.</p>
                </div>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w80"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 잔여금액 설정값 --%>
                    <th>
                        <s:message code="restSmsAmtAlimSetting.restSmsAmt"/>
                    </th>
                    <td>
                        <button type="button" class="btn_gray_alk" id="btRmSmsAmt1" ng-click="rmSmsAmtChange(null)">
                            미사용
                        </button>
                        <button type="button" class="btn_gray_alk" id="btRmSmsAmt2" ng-click="rmSmsAmtChange(5000)">
                            5,000원
                        </button>
                        <button type="button" class="btn_gray_alk" id="btRmSmsAmt3" ng-click="rmSmsAmtChange(10000)">
                            10,000원
                        </button>
                        <button type="button" class="btn_gray_alk" id="btRmSmsAmt4" ng-click="rmSmsAmtChange(20000)">
                            20,000원
                        </button>
                        <button type="button" class="btn_gray_alk" id="btRmSmsAmt5" ng-click="rmSmsAmtChange(50000)">
                            50,000원
                        </button>
                        <button type="button" class="btn_gray_alk" id="btRmSmsAmt6" ng-click="rmSmsAmtChange(100000)">
                            100,000원
                        </button>
                    </td>
                </tr>
                <tr>
                    <%-- 알림받을번호 --%>
                    <th>
                        <s:message code="restSmsAmtAlimSetting.telNo"/>
                    </th>
                    <td>
                        <label id="lblRmSmsAmt" style="display: none;"></label>
                        <input type="text" class="sb-input w100" id="restSmsAmtAlimSettingTelNo" ng-model="restSmsAmtAlimSettingTelNo" placeholder="'-'를 제외한 번호 입력" />
                    </td>
                </tr>
                </tbody>
            </table>
            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/restSmsAmtAlimSetting.js?ver=20220514.01" charset="utf-8"></script>