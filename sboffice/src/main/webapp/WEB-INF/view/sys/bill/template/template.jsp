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
  <div class="w20 fl">
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
      <div id="gridTemplate" style="height:310px;"></div>
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
        <div id="listBoxCode" style="height:310px;"></div>
      </div>
    </div>
  </div>
  
  <div class="fl receiptEdit" style="width:42ch;">
    <div class="wj-TblWrapBr ml10 pd10" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='template.editNm' /></span>
        <button class="btn_skyblue" id="btnSaveTemplate" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <div style="height:310px;">
        <textarea id="editTextArea" style="width:100%;height:100%;resize:none;overflow: auto;"></textarea>
      </div>
    </div>
  </div>
  
  <div class="fl receiptEdit" style="width:42ch;">
    <div class="wj-TblWrapBr ml10 pd10" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='template.viewNm' /></span>
      </div>
      <div style="height:310px;">
        <pre id="preview" style="font-family:'굴림체', 'sans-serif';word-wrap: break-word;white-space: pre-wrap;white-space: -moz-pre-wrap;white-space: -pre-wrap;white-space: -o-pre-wrap;word-break:break-all;"></pre>
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

<script>

  $(document).ready(function() {
    
    <%-- 출력물종류 선택 콤보박스 --%>
    var cData       = ${listPrintType};
    var srchPrtTypeList = wcombo.genCommonBoxFun("#srchPrtTypeList", cData, function(e) {
      searchPrintCodeList();
    });
    srchPrtTypeList.inputElement.disabled = true;
    
    <%-- 템플릿 그리드 --%>
    var dataTemplate =
      [
        { binding:"chkGrid", header:"<s:message code='template.chk' />", dataType:wijmo.DataType.Boolean, width:45},
        { binding:"templtNm", header:"<s:message code='template.templtNm'/>", width:"*"},
      ];
    <%-- 템플릿 그리드 생성 --%>
    var gridTemplate = wgrid.genGrid("#gridTemplate", dataTemplate, "${menuCd}", 1, ${clo.getColumnLayout(1)});
    gridTemplate.isReadOnly = false;
    
    <%-- 대표명칭 그리드 선택변경 이벤트 --%>
    gridTemplate.selectionChanged.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if ( col.binding == "templtNm" ) {
        var selectedRow = gridTemplate.rows[e.row].dataItem;
        if ( selectedRow.prtForm != null ) {
          theTarget.value = selectedRow.prtForm;
          thePreview.innerHTML = replacePrtCd(selectedRow.prtForm);
        } else {
          theTarget.value = "";
          thePreview.innerHTML = "";
        }
        $("#btnSaveTemplate").show();
      }
    });
    
    <%-- 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      searchPrintCodeList();
    });
    
    <%-- 그리드 조회 --%>
    function searchGrid(value) {
      
      var param = {};
      param.prtClassCd = value; 
      
      $.postJSON("/sys/bill/template/item/list.sb", param, 
          function(result) {
            if(result.status === "FAIL") {
              s_alert.pop(result.message);
              return;
            }
            
            var list = result.data.list;
            gridTemplate.itemsSource = new wijmo.collections.CollectionView(list);
            gridTemplate.itemsSource.trackChanges = true;
            
            <%-- 버튼 Show --%>
            $("#btnAddPrint").show();
            $("#btnDelPrint").show();
            $("#btnSavePrint").show();
            
            if ( list.length === undefined || list.length == 0 ) {
              <%-- 그리드 초기화 --%>
              gridTemplate.itemsSource = [];
              <%-- 편집/미리보기 폼 초기화 --%>
              theTarget.value = "";
              thePreview.innerHTML = "";
            } else {
              gridTemplate.select(0, 1);
            }
            
          },
          function(){
            s_alert.pop("Ajax Fail");
          }
      );
      
    }
    
    <%-- 리스트박스 생성 --%>
    var listBox = new wijmo.input.ListBox('#listBoxCode', 
      {
      <%-- 보여지는 데이터 --%>
      displayMemberPath: 'prtCd',
      <%-- 선택시 데이터 --%>
      selectedValuePath: 'prtCd',
      <%-- 드래그 사용하도록 설정 --%>
      formatItem: function(s, e) { 
        e.item.setAttribute('draggable', 'true');
      }
    });
    
    <%-- 출력물코드 목록 조회 --%>
    function searchPrintCodeList() {
      
      var param = {};
      param.prtClassCd = srchPrtTypeList.selectedItem["value"]; 
      
      $.postJSON("/sys/bill/template/code/list.sb", param, 
          function(result) {
            if(result.status === "FAIL") {
              s_alert.pop(result.message);
              return;
            }
            
            var list = result.data.list;
            listBox.itemsSource = list;
            
            if ( list.length === undefined || list.length == 0 ) {
              listBox.itemsSource = [];
            } else {
              searchGrid(srchPrtTypeList.selectedItem["value"]);
            }
            
          },
          function(){
            s_alert.pop("Ajax Fail");
          }
      );
      
    };
    
    <%-- 클릭드래그시 선택이벤트 발생 --%>
    listBox.hostElement.addEventListener("mousedown",function(e){
      listBox.selectedValue = e.target.innerText;
    });
    
    <%-- 드래그 시작 이벤트 --%>
    listBox.hostElement.addEventListener('dragstart', function (e) {
      
      var mData = {
          prtCd: listBox.selectedValue,
          content: listBox.itemsSource[listBox.selectedIndex].content
      };
      
      var dragRow = JSON.stringify(mData);
      <%-- 드래그 데이터 set --%>
      e.dataTransfer.setData("text", dragRow);
      
    }, true);
    
    <%-- 편집/미리보기 폼 element 할당 --%>
    var theTarget = document.getElementById('editTextArea');
    var thePreview = document.getElementById('preview');
    
    <%-- 드래그시 이벤트 설정 --%>
    theTarget.addEventListener('dragover', function (e) {
      <%-- prtCd 값으로 판단하여 copy 모드 설정 --%>
      var dragRow = e.dataTransfer.getData("text");
      if ( dragRow != null ) {
        e.dataTransfer.dropEffect = 'copy';
        e.preventDefault();
      }
    });
     
    <%-- listBox 아이템 드랍이벤트 --%>
    theTarget.addEventListener('drop', function (e) {
      
      var dragRow = JSON.parse(e.dataTransfer.getData("text"));
      var prtCd = dragRow.prtCd;
      var content = dragRow.content;
      
      <%-- 출력물코드가 있는 경우에만 작동 --%>
      if ( prtCd != null ) {
        var strOriginal = theTarget.value;
        var iStartPos   = theTarget.selectionStart;
        var iEndPos     = theTarget.selectionEnd;
        var strFront    = "";
        var strEnd      = "";
        <%-- textarea 의 커서 위치 구해서 커서위치에 값 넣기 --%>
        if ( iStartPos == iEndPos ) {
          strFront = strOriginal.substring(0, iStartPos);
          strEnd = strOriginal.substring(iStartPos, strOriginal.length);
        } else {
          return;
        }
        theTarget.value = strFront + prtCd + strEnd;
        
        <%-- 미리보기 적용 --%>
        thePreview.innerHTML = replacePrtCd(theTarget.value);
      }
      e.preventDefault();
    });
    
    <%-- 키이벤트 (키보드수정시 이벤트발생) --%>
    theTarget.addEventListener('keyup', function(e) {
      thePreview.innerHTML = replacePrtCd(theTarget.value);
    })
    
    <%-- 미리보기 적용 --%>
    function replacePrtCd(value) {
      
      var listData = listBox.itemsSource;
      <%-- 정규식으로 {} 코드값 단위로 끊기--%>
      var matches = value.match(/\{([^}]+)\}/gm);
      if ( matches != null ) {
        <%-- 정규식처리된 문자 처리 --%>
        for ( var i = 0; i < matches.length; i++ ) {
          for ( var j = 0; j < listData.length; j++ ) {
            if ( listData[j].prtCd == matches[i] && listData[j].content != null ) {
              <%-- 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환 --%>
              value = value.replace(matches[i], listData[j].content);
            }
          }
        }
      }
      
      return value;
    }
    
  });
  
</script>
