<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/bill/template/" />

<div class="subCon">
  
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
  </div>
  
  <table class="searchTbl">
    <colgroup>
      <col class="w10" />
      <col class="w20" />
      <col class="w70" />
    </colgroup>
    <tbody>
      <tr>
        <th><s:message code="template.srchNm" /></th>
        <td colspan="2" class="oh">
          <div class="sb-select fl w200 mr10">
            <div id="srchPrtTypeCombo"></div>
          </div>
          <a href="#" id="btnApplyTemplate" class="btn_grayS"><s:message code="template.btnNm" /></a>
        </td>
        <td>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="wj-TblWrap mt20">

    <%-- 템플릿 --%>
    <div class="w25 fl">
      <%--위즈모 테이블--%>
      <div class="wj-TblWrapBr pd20" style="height:470px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.gridNm' /></span>
          <button class="btn_skyblue" id="btnAddTemplate" style="display: none;">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelTemplate" style="display: none;">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveTemplate" style="display: none;">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="gridTemplate"></div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 코드리스트 --%>
    <div class="w15 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:470px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.listNm' /></span>
        </div>
        <div class="app-input-group">
          <div id="listBoxCode" style="height:335px;"></div>
        </div>
      </div>
    </div>

    <div class="w30 fl">
      <div class="wj-TblWrapBr ml10 pd20 templateEdit" style="height:470px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.editNm' /></span>
          <button class="btn_skyblue" id="btnSaveEditTemplate" style="display: none;">
            <s:message code="cmm.save" />
          </button>
        </div>
        <div>
          <textarea id="editTextArea" class="w100 tArea1" cols="42" style="height:380px;"></textarea>
        </div>
      </div>
    </div>

    <div class="w30 fl">
      <div class="wj-TblWrapBr ml10 pd20 templateEdit" style="height:470px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.viewNm' /></span>
        </div>
        <div id="preview" class="s12 lh15" style="height:380px;">
        </div>
      </div>
    </div>

  </div>
</div>

<%-- 미적용 본사/단독매장 레이어 --%>
<div id="applyTemplateDim" class="fullDimmed" style="display:none;"></div>
<div id="applyTemplateLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w700">
      <%-- 레이어 타이틀 :  --%>
      <p class="tit"><s:message code="template.layer.nm" /></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col width="10%" />
              <col width="30%" />
              <col width="10%" />
              <col width="20%" />
              <col width="10%" />
              <col width="20%" />
            </colgroup>
            <tbody>
            <tr>
              <th><s:message code="template.layer.template" /></th>
              <td>
                <div class="sb-select">
                  <div id="srchTemplateTypeCombo"></div>
                </div>
              </td>
              <th><s:message code="template.layer.sysStatFg" /></th>
              <td>
                <div class="sb-select">
                  <div id="srchSysStatFgCombo"></div>
                </div>
              </td>
              <th><s:message code="template.layer.clsFg" /></th>
              <td>
                <div class="sb-select">
                  <div id="srchClsFgCombo"></div>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>

        <%-- 미적용 본사/단독매장 --%>
        <div id="storeInfoArea" class="con sc2" style="height:500px;"><!--높이는 style로 조정-->
          <div class="tr">
            <%-- 저장 --%>
            <a href="javascript:;" id="btnApplyStore" class="btn_grayS2" style="display: none;"><s:message code="template.layer.applyBtn" /></a>
          </div>
          <!--위즈모 테이블-->
          <div>
            <div id="gridApplyStoreTemplate" class="mt10 mb20" style="height:350px;"></div>
          </div>
          <!--//위즈모 테이블-->
        </div>

      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
var printTypeComboData = ${listPrintType};
var sysStatFgComboData = ${ccu.getCommCode("009")};
var clsFgComboData = ${ccu.getCommCode("003")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/template/template.js?ver=20180806" charset="utf-8"></script>
