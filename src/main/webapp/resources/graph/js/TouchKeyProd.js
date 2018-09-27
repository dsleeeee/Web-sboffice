/**
 * 상품 영역 고유 특성 처리
 */
Graph.prototype.initProdArea = function (group, sidebar) {

  var graph = this;
  this.group = group;
  this.sidebar = sidebar;
  // 상품키 정보 (Custom 변수)
  graph.touchKeyInfo = { width: 99, height: 74, x: 100, y: 75 };
  //현재 선택한 버튼 정보 (Custom 변수)
  graph.touchKeyInit = { fontSize: 0, fontColor: '', fillColor: ''};
  //현재 선택한 하위속성 정보 (Custom 변수)
  graph.orgChildren = { id: '', parent: new mxCell(), cell: [] };
  var theGrid = this.sidebar.grid;
  // 상품영역은 그룹이 아님.
  graph.isGroup = false;
  // vertex에 child 삽입시 접는 기능 제거 : 20180906 노현수
  graph.foldingEnabled = false;
  // cell 에디팅 불가 : 20180906 노현수
  graph.setCellsEditable(false);
  // vertex에 삽입된 child 밖으로 드래그 불가 설정 : 20180906 노현수
  graph.graphHandler.setRemoveCellsFromParent(false);

  //console.log(graph);
  //상품영역은 6줄로 초기화
  var prodsLine = 8 - window.MAX_GROUP_ROW;
  graph.initValue(prodsLine);
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

  //override 마우스 이벤트 - 상품영역
  graph.addMouseListener({
    // 상품버튼 클릭시 버튼/상품명/금액 구분하여 속성설정 예외처리
    mouseDown: function (sender, me) {
      // 클릭 영역에 셀이 있는 경우에만...
      if (me.state != null) {
        // 그룹영역 선택 초기화
        graph.group.getSelectionModel().clear();
        // 버튼속성 뷰 활성화
        document.getElementById('keyStyle').classList.remove("hideNav");
        //선택된 레이어를 기본값으로 설정
        var cell = me.state.cell;
        // 스타일 custom 에서 하위속성 타입 가져온다
        var styles = cell.getStyle().split(";");
        for(var i = 0; i < styles.length; i++) {
          var styleKeyValue = styles[i].split("=");
          var colorStyleWrap = document.getElementById('colorStyleWrap');
          if (styleKeyValue[0] === "tukeyFg") {
            // 하위 셀 ( 상품명/금액 ) 인 경우 채우기 기능 제한
            if (styleKeyValue[1] === "02" || styleKeyValue[1] === "03") {
              colorStyleWrap.classList.add("hideNav");
            } else {
              colorStyleWrap.classList.remove("hideNav");
            }
          }
        }
      } else {
        document.getElementById('keyStyle').classList.add("hideNav");
      }
    },
    mouseMove: function () {
    },
    mouseUp: function () {
    }
  });

  /**
   * 페이징 버튼
   */
  //페이지 이동 위치 셀의크기 * 페이지에 컬럼 수
  var scrollWidth = graph.touchKeyInfo.x * graph.COL_PER_PAGE;
  var prodWrap = document.getElementById('prodWrap');
  var pageNoWrap = document.getElementById('prodPageNoText');
  addClickHandler(document.getElementById('prodNavPrev'), function () {
    if (graph.pageNo > 1) {
      graph.pageNo -= 1;
      prodWrap.scrollLeft -= scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
    }
  });
  addClickHandler(document.getElementById('prodNavNext'), function () {
    if (graph.pageNo < graph.MAX_PAGE) {
      graph.pageNo += 1;
      prodWrap.scrollLeft += scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
    }
  });

};
