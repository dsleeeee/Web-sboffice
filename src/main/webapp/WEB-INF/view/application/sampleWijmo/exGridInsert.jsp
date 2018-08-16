<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.min.js"></script>

<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.xlsx.min.js"></script>
<script src="/resource/vendor/jszip/js/jszip.js"></script>

<link rel="stylesheet" type="text/css" href="/resource/vendor/wijmo/css/wijmo.min.css" />

<style>
.wj-flexgrid {
  max-height: 250px;
  margin-bottom: 12px;
}
body {
  margin-bottom: 24pt;
}
</style>
drag & drop sample
<br>
<div class="container">
  <div class="page-header">
    <h3>Create Wijmo Controls</h3>
  </div>
  <br>
  <br>
  <button id="btnInsert" class="btn btn-default">새 데이터 입력</button>
  <br>
  <br>
  <button id="btnSave" class="btn btn-default">저장</button>
  <br>
  <br>
  <div id="theGrid" class='theGrid'></div>
</div>

<script>

var ldata = ${list};
var cData = ${commList};

var comDataMap = new wijmo.grid.DataMap(cData, 'comCd', 'comCdNm');

var theGrid = new wijmo.grid.FlexGrid('#theGrid', {
  showAlternatingRows: false,
  autoGenerateColumns: false,
  columns: [
    { binding: 'comCd', header: '코드'},
    { binding: 'comCdNm', header: '코드명'},
    { binding: 'comFg', header: '비고' , dataMap: comDataMap }
  ],
  itemsSource: ldata
});


document.getElementById('btnInsert').addEventListener('click', function () {
  console.log('insert new row');
  theGrid.collectionView.addNew();

});

document.getElementById('btnSave').addEventListener('click', function () {
  console.log(theGrid.itemsSource);
});

</script>
