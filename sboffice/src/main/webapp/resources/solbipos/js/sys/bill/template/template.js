/****************************************************************
 * 
 * 파일명 : template.js
 * 설  명 : 출력물샘플 JavaScript
 * 
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.07.30     노현수      1.0            
 * 
 * **************************************************************/
$(document).ready(function() {
  
  // 출력물종류 선택 콤보박스
  var srchPrtTypeList = wcombo.genCommonBoxFun("#srchPrtTypeList", cData, function(e) {
    searchPrintCodeList();
  });
  srchPrtTypeList.inputElement.disabled = true;
  
  // 템플릿 그리드 생성
  var gridTemplate = wgrid.genGrid("#gridTemplate", dataTemplate, menuCd, 1, coulmnLayout1);
  gridTemplate.isReadOnly = false;
  
  // 대표명칭 그리드 선택변경 이벤트
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
  
  // 조회버튼 클릭
  $("#btnSearch").click(function(e){
    searchPrintCodeList();
  });
  
  // 그리드 조회
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
          
          // 버튼 Show
          $("#btnAddPrint").show();
          $("#btnDelPrint").show();
          $("#btnSavePrint").show();
          
          if ( list.length === undefined || list.length == 0 ) {
            // 그리드 초기화
            gridTemplate.itemsSource = [];
            // 편집/미리보기 폼 초기화
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
  
  // 리스트박스 생성
  var listBox = new wijmo.input.ListBox('#listBoxCode', 
    {
    // 보여지는 데이터
    displayMemberPath: 'prtCd',
    // 선택시 데이터
    selectedValuePath: 'prtCd',
    // 드래그 사용하도록 설정
    formatItem: function(s, e) { 
      e.item.setAttribute('draggable', 'true');
    }
  });
  
  // 출력물코드 목록 조회
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
  
  // 클릭드래그시 선택이벤트 발생
  listBox.hostElement.addEventListener("mousedown",function(e){
    listBox.selectedValue = e.target.innerText;
  });
  
  // 드래그 시작 이벤트
  listBox.hostElement.addEventListener('dragstart', function (e) {
    
    var mData = {
        prtCd: listBox.selectedValue,
        content: listBox.itemsSource[listBox.selectedIndex].content
    };
    
    var dragRow = JSON.stringify(mData);
    // 드래그 데이터 set
    e.dataTransfer.setData("text", dragRow);
    
  }, true);
  
  // 편집/미리보기 폼 element 할당
  var theTarget = document.getElementById('editTextArea');
  var thePreview = document.getElementById('preview');
  
  // 드래그시 이벤트 설정
  theTarget.addEventListener('dragover', function (e) {
    // prtCd 값으로 판단하여 copy 모드 설정
    var dragRow = e.dataTransfer.getData("text");
    if ( dragRow != null ) {
      e.dataTransfer.dropEffect = 'copy';
      e.preventDefault();
    }
  });
   
  // listBox 아이템 드랍이벤트
  theTarget.addEventListener('drop', function (e) {
    
    var dragRow = JSON.parse(e.dataTransfer.getData("text"));
    var prtCd = dragRow.prtCd;
    var content = dragRow.content;
    
    // 출력물코드가 있는 경우에만 작동
    if ( prtCd != null ) {
      var strOriginal = theTarget.value;
      var iStartPos   = theTarget.selectionStart;
      var iEndPos     = theTarget.selectionEnd;
      var strFront    = "";
      var strEnd      = "";
      // textarea 의 커서 위치 구해서 커서위치에 값 넣기
      if ( iStartPos == iEndPos ) {
        strFront = strOriginal.substring(0, iStartPos);
        strEnd = strOriginal.substring(iStartPos, strOriginal.length);
      } else {
        return;
      }
      
      theTarget.value = strFront + prtCd + strEnd;
      // 미리보기 적용
      thePreview.innerHTML = replacePrtCd(theTarget.value);
      
    }
    e.preventDefault();
  });
  
  // 키이벤트 (키보드수정시 이벤트발생)
  theTarget.addEventListener('keyup', function(e) {
    thePreview.innerHTML = replacePrtCd(theTarget.value);
  })
  
  function strPad(value) {
    var padNum = value.slice(1);
    console.log(padNum);
  }
  
  // 미리보기 적용
  function replacePrtCd(value) {
    // 리스트박스 데이터 가져옴
    var listData = listBox.itemsSource;
    // 정규식으로 {} 코드값 단위로 끊기
    var matches = value.match(/\{([^}]+)\}/gm);
    if ( matches != null ) {
      // 정규식처리된 문자 처리
      for ( var i = 0; i < matches.length; i++ ) {
        if ( matches[i].indexOf(":") > 0 ) {
          if ( matches[i].replace(/{|}|:/g, "").slice(0,1) === "C" ) {
            strPad(matches[i].replace(/{|}|\/|:/g, ""));
            
            value = value.replace(matches[i], matches[i].replace(/{/g, "<").replace(/}/g, ">").replace(/:/g, "").replace("C", "CENTER"));
          }
        } else {
          for ( var j = 0; j < listData.length; j++ ) {
            if ( listData[j].prtCd == matches[i] && listData[j].content != null ) {
              // 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환
              value = value.replace(matches[i], listData[j].content);
            }
          }
        }
      }
    }
    
    return value;
  }
  
});