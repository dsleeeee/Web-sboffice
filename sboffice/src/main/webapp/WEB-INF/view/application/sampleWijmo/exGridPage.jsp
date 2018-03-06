<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.grid.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.grid.filter.min.js"></script>
<script src="/resource/vender/jszip/js/jszip.js"></script>

<style>
.wj-flexgrid {
	height: 430px;
}
/* .column-picker { */
/*   columns: 3; */
/*   padding: 12px; */
/*   margin-left: 12px; */
/*   margin-top: 26px; */
/*   box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23); */
/* } */
/* .column-picker-icon { */
/*   cursor: pointer; */
/*   color: #FF8754; */
/*   margin: 3px; */
/* } */
/* a, a:active, a:hover { */
/*     color: #725ed6; */
/*     text-decoration: underline; */
/*     cursor: pointer; */
/* } */
</style>

<div class="container">
  <div class="page-header">
    <h3></h3>
  </div>
  
  <div>
  
<!--   listScale -->
    <div id="listScaleBox"></div>
  
<!--   공통 코드 콤보박스 -->
    <div id="theComboBox"></div>
  
<!--   검색어 -->
    <div id="shopCd"></div>
    
<!--     검색 버튼 -->
    <button id="registBtn" type="button" class="btn btn_dark btn_md" onclick="search(1);">
      search
    </button>
    
<!--     엑셀 다운로드 -->
    <button id="xlsxBtn" type="button" class="btn btn_dark btn_md" >
      xlsx
    </button>

    <button id="saveBtn" type="button" class="btn btn_dark btn_md" >
      save
    </button>

  </div>
  
  <div class="jumbotron">
    
    <!-- 그리드 -->
    <div id="theGrid"></div>
    
<!--     페이징 -->
    <div id="pager">
      <button id="btnFirst" class="btn btn_dark btn_md"><span class="wj-glyph-step-backward"></span></button>
      <button id="btnPrev" class="btn btn_dark btn_md"><span class="wj-glyph-left"></span></button>
      &nbsp;&nbsp;&nbsp;<span id="spanCurrent"></span>&nbsp;&nbsp;&nbsp;
      <button id="btnNext" class="btn btn_dark btn_md"><span class="wj-glyph-right"></span></button>
      <button id="btnLast" class="btn btn_dark btn_md"><span class="wj-glyph-step-forward"></span></button>
    </div> 

    <div id="rowData"></div>

  </div>
  
  <div id="theGrid2"></div>
  
</div>

<script>
$(document).ready(function() { 
	updateCurrentPage(0, 0);
// 	shopCd.text = "C06419";
// 	listScaleBox.selectedValue = "100";
});
var totpage = 0;
var now = 0;

/***** Grid start *****/
var rdata = ${cl.getColumnList('SSL_TRDTL_T')};
var viewName = "${sessionScope.sessionInfo.currentMenu.resrceCd}";

var cl1 = JSON.stringify(${clo.getColumnLayout(1)});
var cl2 = JSON.stringify(${clo.getColumnLayout(2)});

// 그리드 div, column data, 화면명, 화면 그리드 순서
var grid = wgrid.genGrid("#theGrid", rdata, viewName, 1, cl1);
var grid2 = wgrid.genGrid("#theGrid2", rdata, viewName, 2, cl2);



/** 헤더의 sort 선택 이벤트 **/
grid.sortedColumn.addHandler(function(s, e){
  console.log(s.columns[e.col].binding + " : " + s.columns[e.col].currentSort);
});

/** 그리드 그려질때 이벤트 **/
grid.formatItem.addHandler(function(s, e) {
  // rnum 에 클릭 이벤트 생성
  if (e.panel == s.cells) {
    var col = s.columns[e.col];
    // rnum 일때
    if( col.binding == "rnum" ) {
      var item = s.rows[e.row].dataItem;
      item.row = e.row;
      item.col = e.col;
      var html = wijmo.format("<a onclick=\"rowClick({row}, {col})\">{rnum}</a>", item);
      e.cell.innerHTML = html;
    }
  }
});

/** 위에서 선언한 rnum 선택 이벤트 **/
function rowClick(row, col) {
	var rowData = grid.rows[row].dataItem;
	var keys = Object.keys(rowData);
	var str = "";
	for ( var i in keys) {
		var item = keys[i]+ " : "+ rowData[keys[i]] + ", ";
		str += item;
	}
	$("#rowData").text(str);
}
/***** Grid end *****/

/***** combo start *****/
// 공통코드
var cdata = ${ccu.getCommCode("061")};
var comboBox = wcombo.genCommonBox("#theComboBox", cdata);
comboBox.selectedIndexChanged.addHandler(function(s, e){
  console.log(s["selectedItem"]["name"] + s["selectedItem"]["value"]);
});

// 조회숫자
var ldata = ${ccu.getListScale()};
var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
listScaleBox.selectedIndexChanged.addHandler(function(s, e){
  console.log(s["selectedItem"]["name"] + "-" + s["selectedItem"]["value"]);
});
/***** combo end *****/

// 검색어
var shopCd = new wijmo.input.ComboBox("#shopCd");

// 엑셀 다운로드
$("#xlsxBtn").click(function() {
	wexcel.down(grid, "testSheet", "test.xlsx");
});

$("#saveBtn").click(function() {
  var param = {};
  var url = "/exGridSave.sb";
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(grid.itemsSource),
    success: function(data){
    },
    cache: false,
    dataType: "json",
    contentType : 'application/json'
  });
});

function updateCurrentPage(index, totCnt)
{
	now = index;
	totpage = totCnt;
	var curr = wijmo.format("Page {index:n0} of {cnt:n0}", {
		index : index,
		cnt : totCnt
	});
	$("#spanCurrent").text(curr);
}

function search(index) {
	var param = {};
	
	param.curr = index;
	param.listScale = listScaleBox.selectedValue;
	param.shopCd = shopCd.text;
	
	$.postJSON("/exGridPageJson.sb", param, function(result) {
		
		var list = result.data.list;
		var page = result.data.page;
		
		// 데이터 메칭 
		grid.itemsSource = list;
		
		// 조회껀수
		listScaleBox.selectedValue = page.listScale;
		// 스크롤 최상단
		grid.scrollIntoView(0,0);
		
		grid.autoSizeColumns();
		
		updateCurrentPage(index, page.totalPage);
	})
	.fail(function(){
		alert("Ajax Fail");
	});
}
 
document.getElementById("pager").addEventListener("click", function(e)
{
	var btn = wijmo.closest(e.target, "button");
	var id = btn ? btn.id : "";
	switch (id)
	{
		case "btnFirst":
			search(1);
			break;
		case "btnPrev":
			search(now-1);
			break;
		case "btnNext":
			search(now+1);
			break;
		case "btnLast":
			search(totpage);
			break;
	}
});

</script>


























