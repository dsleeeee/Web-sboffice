<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.xlsx.min.js"></script>

<%-- <script src="/resource/vendor/wijmo/js/wijmo.min.js"></script> --%>
<script src="/resource/vendor/jszip/js/jszip.js"></script>

<link rel="stylesheet" type="text/css" href="/resource/vendor/wijmo/css/wijmo.min.css" />

<style>
#theGrid.wj-flexgrid {
  height: 430px;
}
</style>

<div class="container">
  <div class="page-header">
    <h3>그리드 조회 페이지</h3>
  </div>
  
  <div>
    <div id="shopCd"></div>
  </div>
  
  <!-- 그리드 -->
  <div id="theGrid"></div>
</div>

<script>
onload = function () {
  
  var rdata = ${shopList};
  var i = 0;
  
  rdata.forEach(function(item){

    // shopStatNm
    if(item.shopStatFg == "0")          item.shopStatNm = "등록";
    else if(item.shopStatFg == "1")     item.shopStatNm = "오픈";
    else if(item.shopStatFg == "2")     item.shopStatNm = "폐점";
    else if(item.shopStatFg == "3")     item.shopStatNm = "보류";
    else if(item.shopStatFg == "4")     item.shopStatNm = "출고보류";
    else if(item.shopStatFg == "9")     item.shopStatNm = "데모";
    
    // shopPgmTypeNm
    if(item.shopPgmTypeFg == "0")       item.shopPgmTypeNm = "일반";
    else if(item.shopPgmTypeFg == "1")  item.shopPgmTypeNm = "외식";
    else if(item.shopPgmTypeFg == "2")  item.shopPgmTypeNm = "의류";
    
    // insDt
    if(item.insDt == undefined) item.insDt = "";
    else                        item.insDt = strign2Date(item.insDt);
    
  });
  
  var theGrid = new wijmo.grid.FlexGrid('#theGrid', {
  isReadOnly : true,    
  selectionMode : "RowRange",
    itemsSource : new wijmo.collections.CollectionView(rdata, {
      sortDescriptions : [ 'hdShopCdNm'], /* grouping 컬럼 */
      groupDescriptions: [ 'hdShopCdNm']  /* grouping 후 Descriptions으로 보여지는 컬럼 */
    })
    ,columns: [
      { binding: 'hdShopCdNm', header: '본사' , visible: false  , isRequired: true }, /* isRequired : not null */
      { binding: 'shopCd', header: '매장코드' , visible: false  },
      { binding: 'shopNm', header: '매장명' , visible: false  },
      { binding: 'shopCdNm', header: '매장' , width: '*' },
      { binding: 'shopPgmTypeFg', header: '용도' , visible: false },
      { binding: 'shopPgmTypeNm', header: '용도' , width: '*'},
      { binding: 'shopStatFg', header: '상태' , visible: false },    
      { binding: 'shopStatNm', header: '상태' , width: '*' },      
      { binding: 'clsMgrLevelFg', header: 'clsMgrLevelFg' , visible: false },
      { binding: 'brandMgrYn', header: 'brandMgrYn' , visible: false },
      { binding: 'shopCnt', header: 'shopCnt' , visible: false },
      { binding: 'insId', header: 'insId' , visible: false },
      { binding: 'insDt', header: '등록일' , width: '*' , format: 'yyyy-MM-dd' },
    ]
  });
  theGrid.autoSizeColumns();  // 열 넓이 auto resize
  theGrid.collapseGroupsToLevel(0); //0:전체접기, 1:(아니지만)전제펼치기 로 사용
}
</script>

<script>
// 'YYYYMMDDHH24MISS' to date
var strign2Date = function(d){
    var year = d.substr(0,4);
    var month = d.substr(4,2);
    var day = d.substr(6,2);
    return new Date(year, month, day);
}
</script>





