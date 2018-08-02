<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
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
        <td>
          <div class="sb-select">
            <div id="srchPrtTypeList"></div>
          </div>
        </td>
        <td>
          <%-- 버튼 --%>
          <div class="updownSet oh">
            <button class="btn_blue fl mr10" id="btnSearch"><s:message code="template.srchBtnNm" /></button>
            <button class="btn_blue fl " id="btnSearchStore"><s:message code="template.btnNm" /></button>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
  
  <div class="mt10 pdb20 oh">
  </div>
  
  <%-- 템플릿 --%>
  <div class="w30 fl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr pd10" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='template.gridNm' /></span>
        <button class="btn_skyblue" id="btnAddPrint" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelPrint" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSavePrint" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridTemplate" style="height:335px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <%-- 코드리스트 --%>
  <div class="w15 fl" style="width:15%;">
    <div class="wj-TblWrapBr ml10 pd10" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='template.listNm' /></span>
      </div>
      <div class="app-input-group">
        <div id="listBoxCode" style="height:335px;"></div>
      </div>
    </div>
  </div>
  
  <div class="fl receiptEdit" style="width:310px;">
    <div class="wj-TblWrapBr ml10 pd10" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='template.editNm' /></span>
        <button class="btn_skyblue" id="btnSaveTemplate" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <div style="height:335px;">
        <textarea id="editTextArea" cols="42"></textarea>
      </div>
    </div>
  </div>
  
  <div class="fl receiptEdit" style="width:310px;">
    <div class="wj-TblWrapBr ml10 pd10" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='template.viewNm' /></span>
      </div>
      <div style="height:335px;">
          <div id="preview"></div>
      </div>
    </div>
  </div>
  
</div>

<%-- 출력물코드 선택 레이어 --%>
<div id="itemSelTent" class="fullDimmed" style="display: none;"></div>
<div id="itemSelLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w800">
      <p class="tit"><s:message code="kind.gridNm" /></p>
      <a href="javascript:;" class="btn_close itemSelClose"></a>
      <div class="con">
          <%--위즈모 테이블--%>
          <div class="wj-TblWrapBr mt10" style="height: 400px;">
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div id="theGrid" style="width:100%;height:393px;"></div>
          </div>
      </div>
      <%-- 저장 --%>
      <div class="btnSet">
        <span><a href="javascript:;" id="btnSaveItem" class="btn_blue"><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">

  var menuCd = "${menuCd}";
  var coulmnLayout1 = ${clo.getColumnLayout(1)};
  <%-- 출력물종류 선택 콤보박스 --%>
  var cData       = ${listPrintType};
  <%-- 템플릿 그리드 Data --%>
  var dataTemplate =
    [
      { binding:"gChk", header:"<s:message code='template.chk' />", dataType:wijmo.DataType.Boolean, width:45},
      { binding:"templtNm", header:"<s:message code='template.templtNm'/>", width:"*", isReadOnly: true },
    ];
  
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/template/template.js"></script>
