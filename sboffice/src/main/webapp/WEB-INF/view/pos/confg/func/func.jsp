<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<!--right-->
<div class="contents">
    <!--서브컨텐츠-->
    <div class="subCon">
        <div class="updownSet">
            <button class="btn_up"><s:message code="cmm.up" /></button>
            <button class="btn_down"><s:message code="cmm.down" /></button>
            <button class="btn_skyblue"><s:message code="cmm.add" /></button>
            <button class="btn_skyblue"><s:message code="cmm.copy" /></button> 
            <button class="btn_skyblue"><s:message code="cmm.delete" /></button>
            <button class="btn_skyblue"><s:message code="cmm.save" /></button>
        </div>
        
        <!--위즈모 테이블-->
        <div class="wj-TblWrap mt10" style="height:500px;"><!--개발시 높이 조절해서 사용-->
            <div id="theGrid"></div><!--tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요.-->
        </div>
        <!--//위즈모 테이블-->
    </div>
    <!--//서브컨텐츠-->
</div>
<!--//right-->

<script>

$(document).ready(function(){
  
  var funcList    = ${funcList};
  var fnkeyFgCode = ${fnkeyFgCode};
  var storeFgCode = new wijmo.grid.DataMap(${storeFgCode}, 'nmcodeCd', 'nmcodeNm');
  var posFgCode   = ${posFgCode};
  var useYnData   = new wijmo.grid.DataMap([{id:"Y", name:"Y"},{id:"N", name:"N"}], 'id', 'name');

  var rdata = 
    [
      // NO 추가 필요
      {binding:"chk", header:"<s:message code='kitchenMemo.chk' />", allowMerging:true},
      {binding:"fnkeyFg", header:"<s:message code='func.fnkeyFg' />", dataMap:fnkeyFgCode, allowMerging:true},
      {binding:"fnkeyNo", header:"<s:message code='func.fnkeyNo' />", maxLength:20, allowMerging:true},
      {binding:"stroeFg", header:"<s:message code='func.stroeFg' />", dataMap:storeFgCode, allowMerging:true},
      {binding:"posFg", header:"<s:message code='func.posFg' />", dataMap:posFgCode, allowMerging:true},
      {binding:"fnkeyMainUseYn0", header:"<s:message code='func.fnkeyMainUseYn0' />", dataMap:useYnData, format: 'n2' },
      {binding:"fnkeyUseYn0", header:"<s:message code='func.fnkeyUseYn0' />", dataMap:useYnData, format: 'n2' },
      {binding:"fnkeyMainUseYn1", header:"<s:message code='func.fnkeyMainUseYn1' />", dataMap:useYnData, format: 'n2' },
      {binding:"fnkeyUseYn1", header:"<s:message code='func.fnkeyUseYn1' />", dataMap:useYnData, format: 'n2' },
      {binding:"imgFileNm0", header:"<s:message code='func.imgFileNm0' />", format: 'n2' },
      {binding:"imgFileNm1", header:"<s:message code='func.imgFileNm1' />", format: 'n2' }
    ];
  
  <%-- 기능정의 리스트 --%>
  var funcList = ${funcList};
  
  <%-- 체크박스 초기화 --%>
  funcList.forEach(function(item){
    item.chk = false;
  });
  
  <%-- function --%>
  var funcCollection          = new wijmo.collections.CollectionView(funcList);
  funcCollection.trackChanges = true;

  <%-- 그리드 div, column data, 화면명, 화면 그리드 순서 --%>
  var grid             = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  grid.itemsSource     = funcCollection;
  grid.allowMerging = wijmo.grid.AllowMerging.All;
  grid.isReadOnly      = false;

  <%-- merge할 헤더 추가 --%>
  for (var i = 0; i < 2; i++) {
    var hr = new wijmo.grid.Row();
    grid.columnHeaders.rows.push(hr);
  }
  
  for (var r = 0; r < grid.columnHeaders.rows.length; r++) {
    grid.columnHeaders.rows[r].allowMerging = true;
    for (var c = 0; c < grid.columns.length; c++) {
        if ((r == 0 && c == 0) || r == 2 && c == 0 || (r == 1 && c == 0)) {
            grid.columnHeaders.setCellData(r, c, "2 Row Header");
        }
        else if ((r == 0 && c == 1) || (r == 0 && c == 2) || (r == 1 && c == 1) || r == 1 && c == 2) {
            grid.columnHeaders.setCellData(r, c, <s:message code="func.useYnHeader" />);  // 프로그램용도 사용여부
        }
        else if ((r == 0 && c == 1) || (r == 0 && c == 2) || (r == 1 && c == 1) || r == 1 && c == 2) {
          grid.columnHeaders.setCellData(r, c, <s:message code="func.useYnHeader" />);  // 이미지파일명
      }
        grid.columns[c].allowMerging = true;
        grid.columns[c].align = "center";
    }
  }
  
  
  
  
  
  
  // 여기여기여기여기 -> 이거말고 아까 진행하던 방식으로 해야하나봄
  var mm = new wijmo.grid.MergeManager(grid);
  var rng0 = new wijmo.grid.CellRange(0, 0, 0, 0, 0, 4, 0, 0, 0, 2, 0);
  var rng1 = new wijmo.grid.CellRange(0, 0, 0, 0, 0, 2, 0, 2, 0, 1, 1);
  var rng2 = new wijmo.grid.CellRange(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
  
  mm.getMergedRange = function(panel, r, c) {
    if (panel.cellType == wijmo.grid.CellType.ColumnHeader) {
      if (r >= 0 && rng0.contains(r, c)) {
        return rng0;
      } else if (r >= 0 && rng1.contains(r, c)) {
        return rng1;
      } else if (r >= 0 && rng2.contains(r, c)) {
        return rng2;
      }
    }
    return null;
  };
  grid.mergeManager = mm;
  
  
  
});

</script>