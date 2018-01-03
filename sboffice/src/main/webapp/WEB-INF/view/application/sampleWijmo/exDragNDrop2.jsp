<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.min.js"></script>

<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.xlsx.min.js"></script>
<script src="http://cdn.wijmo.com/5.20163.259/controls/wijmo.grid.xlsx.min.js"></script>
<script src="/resource/vender/jszip/js/jszip.js"></script>

<link rel="stylesheet" type="text/css" href="/resource/vender/wijmo/css/wijmo.min.css" />

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
</div>

<script>

onload = function() {

  // create the grid
  var leftGrid = new wijmo.grid.FlexGrid('#leftGrid', {
    itemsSource: getData(),
    allowAddNew: true,
    allowDelete: true
  });  
  
  var rightGrid = new wijmo.grid.FlexGrid('#rightGrid', {
    itemsSource: getData2(),
    allowAddNew: true,
    allowDelete: true
  });
  
  // allow dragging from the grid
  makeDragSource(leftGrid);
  makeDragSource(rightGrid);
  
  // allow dropping into target
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
        
        //TODO dragObj
        console.log("dragObj : "+ dragObj)
        console.log("dragObj.rows[parseInt(dragRow)].dataItem : "+leftGrid.rows[parseInt(dragRow)].dataItem)
        //console.log("dragObj.rows[parseInt(dragRow)].dataItem : "+dragObj.rows[parseInt(dragRow)].dataItem)
        
        var item = leftGrid.rows[parseInt(dragRow)].dataItem;
        
        rightGrid.collectionView.addNew();

        var addRow = 0;
        rightGrid.rows.forEach(function(row, index) {
          if(JSON.stringify(rightGrid.rows[index].dataItem) == '{}') addRow = index;
        });
        
        rightGrid.rows[addRow].dataItem = item;
        
        leftGrid.collectionView.remove(item);
        leftGrid.collectionView.refresh(); 
        
        rightGrid.collectionView.refresh(); 
      }
    });
    
    // remove test 
    s.addEventListener('keydown', function (e) {
      var view = leftGrid.collectionView;
      if (e.ctrlKey && e.keyCode == wijmo.Key.Delete && view.currentItem) {
        e.preventDefault();
        view.remove(view.currentItem);
        leftGrid.focus();
      }
    }, true);
  }

  // create some random data
  function getData() {
    var countries = 'US,Germany,UK,Japan,Italy,Greece'.split(','),
        data = [];
    for (var i = 0; i < countries.length; i++) {
        data.push({
            country: countries[i],
            downloads: Math.round(Math.random() * 20000),
            sales: Math.random() * 10000,
            expenses: Math.random() * 5000
        });
    }
    return data;
  }
  
  function getData2() {
    var countries = 'US,Germany,Greece'.split(','),
        data = [];
    for (var i = 0; i < countries.length; i++) {
        data.push({
            country: countries[i],
            downloads: Math.round(Math.random() * 20000),
            sales: Math.random() * 10000,
            expenses: Math.random() * 5000
        });
    }
    return data;
  }
  
  document.getElementById('btnSave').addEventListener('click', function () {
    //leftGrid
    //rightGrid
    
    
    
    
  });
  
}
</script>
