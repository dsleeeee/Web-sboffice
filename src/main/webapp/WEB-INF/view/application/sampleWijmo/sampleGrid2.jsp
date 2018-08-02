<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.grid.xlsx.min.js"></script>
<script src="/resource/vender/jszip/js/jszip.js"></script>

<style>
#theGrid.wj-flexgrid {
	max-height: 400px;
}
</style>

sampleView5

<br>

<div class="container">
  <div class="page-header">
    <h3>Create Wijmo Controls</h3>
  </div>
  <div class="jumbotron">

    <p style="font-size: 15px">Wijmo FlexGrid:</p>

    <div id="theGrid"></div>
    
    
    <div id="pager">
      <button id="btnFirst" class="btn"><span class="wj-glyph-step-backward"></span></button>
      <button id="btnPrev" class="btn"><span class="wj-glyph-left"></span></button>
      &nbsp;&nbsp;&nbsp;<span id="spanCurrent"></span>&nbsp;&nbsp;&nbsp;
      <button id="btnNext" class="btn"><span class="wj-glyph-right"></span></button>
      <button id="btnLast" class="btn"><span class="wj-glyph-step-forward"></span></button>
    </div> 

  </div>
</div>

<br>
<button id="registBtn" type="button" class="btn btn_dark btn_md w_lg" >
  xlsx
</button>
<script>

onload = function()
{
	var rdata = ${data};

	// create a CollectionView with 20 items per page
	var view = new wijmo.collections.CollectionView(
			rdata, {
				pageSize : 20,
				pageChanged : updateCurrentPage
			});

	// update pager status
	view.onPageChanged();

	function updateCurrentPage()
	{
		var curr = wijmo.format('Page {index:n0} of {cnt:n0}', {
			index : view.pageIndex + 1,
			cnt : view.pageCount
		});
		document.getElementById('spanCurrent').textContent = curr;
	}

	document.getElementById('pager').addEventListener('click', function(e)
	{
		var btn = wijmo.closest(e.target, 'button');
		var id = btn ? btn.id : '';
		switch (id)
		{
			case 'btnFirst':
				view.moveToFirstPage();
				break;
			case 'btnPrev':
				view.moveToPreviousPage();
				break;
			case 'btnNext':
				view.moveToNextPage();
				break;
			case 'btnLast':
				view.moveToLastPage();
				break;
		}
	});

	var grid = new wijmo.grid.FlexGrid('#theGrid', {
		itemsSource : view,
		isReadOnly : true,
		stickyHeaders : true,
		showAlternatingRows : false,
		headersVisibility : 'Column'
	});
	
	grid.autoSizeColumns();

	
	document.getElementById('registBtn').addEventListener('click', function() {

		// create book with current view
		var book = wijmo.grid.xlsx.FlexGridXlsxConverter.save(grid, {
			includeColumnHeaders: true,
			includeRowHeaders: true
		});

		book.sheets[0].name = 'Learn Wijmo';
    	
		// save the book
		book.save('LearnWijmo.xlsx');
    });
	
}
</script>


























