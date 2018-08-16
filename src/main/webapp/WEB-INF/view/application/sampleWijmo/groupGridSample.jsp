<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.xlsx.min.js"></script>

<%-- 
<script src="/resource/vendor/wijmo/js/wijmo.min.js"></script>
 --%>
 
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
  
  rdata.forEach(function (item){
    console.log(item.shopCd);
    item.idx = (i % 4 != 0);  
    console.log(item.idx);
  });
  
  var theGrid = new wijmo.grid.FlexGrid('#theGrid', {
    itemsSource: new wijmo.collections.CollectionView(rdata, {
      sortDescriptions: [ 'hdShopCdNm'], // grouping 컬럼
      groupDescriptions: [ 'hdShopCdNm'] // grouping 후 Descriptions으로 보여지는 컬럼 
    })
    ,columns: [
      /* { binding: 'idx', header: 'idx'  , width: '*' }, */
      { binding: 'shopCd', header: 'shopCd'  , width: '*' },
      { binding: 'shopNm', header: 'shopNm'  , width: '*' },
      { binding: 'shopCdNm', header: 'shopCdNm'  , visible: false },
      { binding: 'shopPgmTypeFg', header: 'shopPgmTypeFg' , visible: false },
      { binding: 'shopStatFg', header: 'shopStatFg' , visible: false  },
      { binding: 'clsMgrLevelFg', header: 'clsMgrLevelFg' , visible: false },
      { binding: 'brandMgrYn', header: 'brandMgrYn' , visible: false },
      { binding: 'shopCnt', header: 'shopCnt' , visible: false },
      { binding: 'insId', header: 'insId' , visible: false },
      { binding: 'insDt', header: 'insDt' , visible: false },
    ]
  });
}

</script>
















