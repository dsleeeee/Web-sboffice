/**
 * 상품 영역 고유 특성 처리
 */
Graph.prototype.initProdArea = function (group, sidebar) {

  var graph = this;
  this.group = group;
  this.sidebar = sidebar;

  // 상품 버튼영역은 버튼 높이 다름
  graph.tKeySize.height = 74;

  var theGrid = this.sidebar.grid;

  graph.isGroup = false;
  // vertex에 child 삽입시 접는 기능 제거 : 20180906 노현수
  graph.foldingEnabled = false;
  // cell 에디팅 불가 : 20180906 노현수
  graph.setCellsEditable(false);
  // vertex에 삽입된 child 밖으로 드래그 불가 설정 : 20180906 노현수
  graph.graphHandler.setRemoveCellsFromParent(false);

  //console.log(graph);
  //상품영역은 6줄로 초기화
  graph.initValue(6);
  //상품 영역에만 UNDO, 키 핸들러 추가
  graph.undoManager = graph.createUndoManager(graph);
  var rubberband = new mxRubberband(graph);
  graph.keyHandler = graph.createKeyHandler(graph);
  //console.log(graph);

  //셀 삭제 시 그리드에 반영
  var cellsRemoved = graph.cellsRemoved;
  graph.cellsRemoved = function (cells) {
    cellsRemoved.apply(this, arguments);
    sidebar.initUsed();
  }
  //셀 추가 시 그리드에 반영
  var cellsAdded = graph.cellsAdded;
  graph.cellsAdded = function (cells) {
    cellsAdded.apply(this, arguments);
    sidebar.initUsed();
  }

  //UNDO 를 했을 때 그리드의 사용여부 업데이트
  var undoHandler = function (sender, evt) {
    var model = graph.getModel();
    var cells = [];

    //추가된 경우 사용여부 true & 선택
    var cand = graph.getSelectionCellsForChanges(evt.getProperty('edit').changes);
    for (var i = 0; i < cand.length; i++) {
      if ((model.isVertex(cand[i]) || model.isEdge(cand[i])) && graph.view.getState(cand[i]) != null) {
        cells.push(cand[i]);
      }
    }
    graph.setSelectionCells(cells);

    //삭제된 경우 사용여부 false

    sidebar.initUsed();
  }
  graph.undoManager.addListener(mxEvent.UNDO, undoHandler);
  graph.undoManager.addListener(mxEvent.REDO, undoHandler);

  /**
   * 페이징 버튼
   */
    //페이지 이동 위치 셀의크기 * 페이지에 컬럼 수
  var scrollWidth = graph.tKeySize.width * graph.COL_PER_PAGE;
  var div = document.getElementById('prodNav');
  var wrap = document.getElementById('prodWrap');
  addClickHandler(document.getElementById('prp'), function () {
    if (graph.pageNo > 1) {
      graph.pageNo -= 1;
      wrap.scrollLeft -= scrollWidth;
      console.log("scrollWidth-", scrollWidth);
    }
  });
  addClickHandler(document.getElementById('prn'), function () {
    if (graph.pageNo < graph.MAX_PAGE) {
      graph.pageNo += 1;
      wrap.scrollLeft += scrollWidth;
      console.log("scrollWidth+", scrollWidth);
    }
  });

};
