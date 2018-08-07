<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
  
  <div class="fl templateEdit" style="width:310px;">
    <div class="wj-TblWrapBr ml10 pd10" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='template.editNm' /></span>
        <button class="btn_skyblue" id="btnSaveEditTemplate" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <div style="height:335px;">
        <textarea id="editTextArea" cols="42"></textarea>
      </div>
    </div>
  </div>
  
  <div class="fl templateEdit" style="width:310px;">
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

<script type="text/javascript">
<%-- 출력물종류 선택 콤보박스 --%>
var cData = ${listPrintType};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/template/template.js?ver=20180806" charset="utf-8"></script>
