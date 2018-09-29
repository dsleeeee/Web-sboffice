/**
 * 상품 영역 고유 특성 처리
 */
Graph.prototype.initProdArea = function (group, sidebar) {

  var graph = this;
  this.group = group;
  this.sidebar = sidebar;
  // 상품키 정보 (Custom 변수)
  graph.touchKeyInfo = {width: 99, height: 74, x: 100, y: 75};
  //현재 선택한 버튼 정보 (Custom 변수)
  graph.touchKeyInit = {fontSize: 0, fontColor: '', fillColor: ''};
  //현재 선택한 하위속성 정보 (Custom 변수)
  graph.orgChildren = {id: '', parent: new mxCell(), cell: []};
  var theGrid = this.sidebar.grid;
  // 상품영역은 그룹이 아님.
  graph.isGroup = false;
  // vertex에 child 삽입시 접는 기능 제거 : 20180906 노현수
  graph.foldingEnabled = false;
  // cell 에디팅 불가 : 20180906 노현수
  graph.setCellsEditable(false);
  // vertex에 삽입된 child 밖으로 드래그 불가 설정 : 20180906 노현수
  graph.graphHandler.setRemoveCellsFromParent(false);
  //색상스타일값 초기화
  graph.initStyle();
  //상품영역은 6줄로 초기화
  var prodsLine = 8 - window.MAX_GROUP_ROW;
  graph.initValue(prodsLine);
  //멀티선택방지
  graph.getSelectionModel().setSingleSelection(true);
  //상품 영역에만 UNDO, 키 핸들러 추가
  graph.undoManager = graph.createUndoManager(graph);
  var rubberband = new mxRubberband(graph);
  graph.keyHandler = graph.createKeyHandler(graph);

  //셀 삭제 시 그리드에 반영
  var cellsRemoved = graph.cellsRemoved;
  graph.cellsRemoved = function (cells) {
    cellsRemoved.apply(this, arguments);
    sidebar.initUsed();
  };

  //셀 추가 시 그리드에 반영
  var cellsAdded = graph.cellsAdded;
  graph.cellsAdded = function (cells) {
    cellsAdded.apply(this, arguments);
    sidebar.initUsed();
  };

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
  };
  graph.undoManager.addListener(mxEvent.UNDO, undoHandler);
  graph.undoManager.addListener(mxEvent.REDO, undoHandler);

  //override 마우스 이벤트 - 상품영역
  graph.addMouseListener({
    currentState: null,
    previousStyle: null,
    // 상품버튼 클릭시 버튼/상품명/금액 구분하여 속성설정 예외처리
    mouseDown: function (sender, me) {
      // 마우스오버를 위해 초기화
      if (this.currentState != null) {
        this.dragLeave(me.getEvent(), this.currentState);
        this.currentState = null;
      }
      // 클릭 영역에 셀이 있는 경우에만...
      if (me.state != null) {
        // 그룹영역 선택 초기화
        graph.group.getSelectionModel().clear();
        // 버튼속성 뷰 활성화
        document.getElementById('keyStyle').classList.remove("hideNav");
        //선택된 레이어를 기본값으로 설정
        var cell = me.state.cell;
        //정규식으로 tukeyFg 추출
        var tukeyFg;
        var regex = /tukeyFg=([^=]*.(?=;))/gm;
        var match = regex.exec(cell.getStyle());
        if (match) {
          tukeyFg = match[1];
        }
        var colorStyleWrap = document.getElementById('colorStyleWrap');
        // 하위 셀 ( 상품명/금액 ) 인 경우 채우기 기능 제한
        if (tukeyFg === "02" || tukeyFg === "03") {
          colorStyleWrap.classList.add("hideNav");
        } else {
          colorStyleWrap.classList.remove("hideNav");
        }
      } else {
        document.getElementById('keyStyle').classList.add("hideNav");
      }
    },
    mouseMove: function (sender, me) {
      if (this.currentState != null && me.getState() === this.currentState) {
        return;
      }
      var tmp = graph.view.getState(me.getCell());
      // Ignores everything but vertices
      if (graph.isMouseDown || (tmp !== null && !graph.getModel().isVertex(tmp.cell))) {
        tmp = null;
      }
      if (tmp !== this.currentState) {
        if (this.currentState != null) {
          this.dragLeave(me.getEvent(), this.currentState);
        }
        this.currentState = tmp;
        if (this.currentState != null) {
          this.dragEnter(me.getEvent(), this.currentState);
        }
      }
    },
    mouseUp: function (sender, me) {
    },
    dragEnter: function (evt, state) {
      if (state != null) {
        this.previousStyle = state.style;
        state.style = mxUtils.clone(state.style);
        graph.updateHoverStyle(state, true);
      }
    },
    dragLeave: function (evt, state) {
      if (state != null && !state.invalid) {
        state.style = this.previousStyle;
        graph.updateHoverStyle(state, false);
      }
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
