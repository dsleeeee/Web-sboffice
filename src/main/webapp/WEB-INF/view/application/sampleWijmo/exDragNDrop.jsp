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
.leftGrid{
  width:370px;
  height:520px;
}
.rightGrid{
  width:370px;
  height:520px;
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
  <button id="btnSave" class="btn btn-default">저장</button>
  <br>
  <br>
  <div id="leftGrid" class='leftGrid'></div>
  <div id="rightGrid" class='rightGrid'></div>
  <br>
  <br>
  <div id="edited" class='changed edited'></div>
  <br>
  <div id="added" class='changed edited'></div>
  <br>
  <div id="removed" class='changed edited'></div>
  <br>
  
</div>

<script>

var ldata = ${leftData};
var rdata = ${rightData};


var ldataView = new wijmo.collections.CollectionView(ldata, {
  sortDescriptions: ['comCd'],
  trackChanges: true
});

var rdataView = new wijmo.collections.CollectionView(rdata, {
  sortDescriptions: ['comCd'],
  trackChanges: true
});



// 그리드 생성
var leftGrid = new wijmo.grid.FlexGrid('#leftGrid', {
  itemsSource: ldata,
  columns: [
    { binding: 'status', header: '상태', visible:false},
    { binding: 'comCd', header: '코드'},
    { binding: 'comCdNm', header: '코드명'}, 
    { binding: 'comFg', header: '비고'}
  ]
  , rowEditEnded : function (s,e) {
    if ( !e.cancel) {
      console.log(e.row)
      leftGrid.rows[e.row].dataItem.status = "E";
      console.log('  leftGrid.rows[e.row].dataItem.status : '+   leftGrid.rows[e.row].dataItem.status)
    }
  }
});

var rightGrid = new wijmo.grid.FlexGrid('#rightGrid', {
  itemsSource: rdata,
  columns: [
    { binding: 'status', header: '상태', visible:false},
    { binding: 'comCd', header: '코드'},
    { binding: 'comCdNm', header: '코드명'}, 
    { binding: 'comFg', header: '비고'}
  ] 
});

//allow dragging from the grid 
makeDragSource(leftGrid);
makeDragSource(rightGrid);

//allow dropping into target
makeDropTarget(document.getElementById('rightGrid'));
makeDropTarget(document.getElementById('leftGrid'));

var dragObj = "";
var dropObj = "";

// make grid rows draggable
function makeDragSource(s) {

  // make rows draggable
  s.itemFormatter = function (panel, r, c, cell) {
    if (panel.cellType == wijmo.grid.CellType.RowHeader) {
      cell.textContent = (r + 1).toString();
      cell.draggable = true;
    }
  };

  // disable built-in row drag/drop
  s.hostElement.addEventListener('mousedown', function (e) {
    if (s.hitTest(e).cellType == wijmo.grid.CellType.RowHeader) {
      e.stopPropagation();
    };
  }, true);

  // handle drag start
  s.hostElement.addEventListener('dragstart', function (e) {
    dragObj = $(this).attr('id');
    var ht = s.hitTest(e);
    if (ht.cellType == wijmo.grid.CellType.RowHeader) {
      s.select(new wijmo.grid.CellRange(ht.row, 0, ht.row, s.columns.length - 1));
      e.dataTransfer.effectAllowed = 'copy';
      e.dataTransfer.setData('text', ht.row.toString());
    };
  }, true);
}

// enable drop operations on an element
function makeDropTarget(s) {

  s.addEventListener('dragover', function (e) {
    var dragRow = e.dataTransfer.getData('text');
    if (dragRow != null) {
      e.dataTransfer.dropEffect = 'copy';
      e.preventDefault();
    }
  });
  
  s.addEventListener('drop', function (e) {
    dropObj = this.id;
    var dragRow = e.dataTransfer.getData('text');
    if (dragRow != null) {

      //TODO 이 부분 동적으로 변경하면 좋을텐데(객체명)
      if(dropObj == 'leftGrid') {
        var item = rightGrid.rows[parseInt(dragRow)].dataItem;
        rightGrid.rows[parseInt(dragRow)].dataItem.comFg = "1";
        ldata.push(item);
        rightGrid.collectionView.remove(item);
        rightGrid.collectionView.refresh();
        leftGrid.collectionView.refresh();
      }
      else {
        var item = leftGrid.rows[parseInt(dragRow)].dataItem;
        leftGrid.rows[parseInt(dragRow)].dataItem.comFg = "2";
        rdata.push(item);
        leftGrid.collectionView.remove(item);
        leftGrid.collectionView.refresh();
        rightGrid.collectionView.refresh();
      }
    }
  });
}



// create grids to show changes
var edited = new wijmo.grid.FlexGrid('#edited', {
  itemsSource: ldataView.itemsEdited,
  isReadOnly: true
});
var added = new wijmo.grid.FlexGrid('#added', {
  itemsSource: ldataView.itemsAdded,
  isReadOnly: true
});
var removed = new wijmo.grid.FlexGrid('#removed', {
  itemsSource: ldataView.itemsRemoved,
  isReadOnly: true
});


document.getElementById('btnSave').addEventListener('click', function () {
  console.log("::: leftGrid.itemsSource : " + JSON.stringify(leftGrid.itemsSource));
  console.log("::: leftGrid.itemsSource : " + JSON.stringify(rightGrid.itemsSource));
  
  //TODO 저장로직 구현
  $.ajax({
    type: "POST",
    url: "/saveDragNDrop.sb",
    data: JSON.stringify(leftGrid.itemsSource.concat(rightGrid.itemsSource)),
    success: function(data){
    },
    cache: false,
    dataType: "json",
    contentType : 'application/json'
  });
  
});

</script>
