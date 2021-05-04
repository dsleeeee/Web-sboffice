<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="touchKeyStyleLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:680px; height:700px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="touchKeyStyleCtrl">
  <form id="myForm" name="myForm">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="touchKey.viewStyle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2 h700">
      <div class="sb-select dkbr fl w120px" style="clear: both">
<%--        <div id="selectStyle" ng-model="viewStyle"></div>--%>
        <wj-combo-box
                id="styleCombo"
                ng-model="viewStyle"
                items-source="_getComboData('styleCombo')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                selected-index-changed="setViewStyle(s)">
        </wj-combo-box>
      </div>
        <span class="fl bk lh30">&nbsp;&nbsp;<s:message code='touchKey.msg.viewStyle' /></span>
        <br>
        <br>
      <div id="viewStyleLayer" class="fl" style="height: 580px; width: 630px"></div>
    </div>
    </form>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/touchKeyStyleView.js?ver=20210430.01" charset="utf-8"></script>