<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
#theGrid.wj-flexgrid {
  max-height: 300px;
}
</style>

sampleView4

<br>

<div class="container">
  <div class="page-header">
    <h3>Create Wijmo Controls</h3>
  </div>
  <div class="jumbotron">

    <p style="font-size: 15px">Wijmo FlexGrid:</p>

    <div id="theGrid"></div>

  </div>
</div>

<script>
	onload = function()
	{

		var paramStr = "";

		$.postJSON("/samplejson.sb", paramStr, function(result)
		{
			var rData = result.data;

			var theGrid = new wijmo.grid.FlexGrid('#theGrid', {
				itemsSource : rData,
				isReadOnly : true, // 그리드 수정 여부
				showMarquee : true,
				showSelectedHeaders : 'All',
				showAlternatingRows : false
				, columns: [
					{ binding: 'dcmSaleAmt', header: '테스트', aggregate: 'Sum'}
					, { binding: 'shopCd', header: '테스트1'}
				]
    			,
    		    scrollPositionChanged: function(s, e) {
    		    
    		        // 스크롤을 끝으로 내일때 마다 20개의 새로운 데이터 추가
    		        if (s.viewRange.bottomRow >= s.rows.length - 1) {
    		                addData(rData, 20);
    		        		s.collectionView.refresh();
    		      }
    		    }
			});

			theGrid.columnFooters.rows.push(new wijmo.grid.GroupRow());
			theGrid.bottomLeftCells.setCellData(0, 0, 'Σ');

			theGrid.autoSizeColumns();	// 열 넓이 auto resize
			
			var temp = "";
		})
		// 오류발생 시 
		.fail(function()
		{
			alert("Ajax Fail");
		});

	}
</script>


























