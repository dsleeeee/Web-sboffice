<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>

<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.grid.xlsx.min.js"></script>
<script src="/resource/vender/jszip/js/jszip.js"></script>

<style>
#theGrid.wj-flexgrid {
	max-height: 500px;
}
</style>

exGridHeader

<br>

<div class="container">
  <div class="page-header">
    <h3>Create Wijmo Controls</h3>
  </div>

  <div class="jumbotron">
    
    <!-- 그리드 -->
    <div id="theGrid"></div>
    
  </div>
</div>

<script>
var rdata = ${data};
var cdata = ${columnList};

var grid = new wijmo.grid.FlexGrid('#theGrid', {
	itemsSource : rdata,
	isReadOnly : true,
	columns : cdata
}); 
</script>


























