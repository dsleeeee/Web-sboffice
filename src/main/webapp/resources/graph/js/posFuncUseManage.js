/****************************************************************
 *
 * 파일명 : posFuncUseManage.js
 * 설  명 : 포스기능키 등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.01    노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// angular 그리드 생성
app.controller('funcKeyCtrl', ['$scope', '$http', 'saveInfo', function ($scope, $http, saveInfo) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('funcKeyCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.areAllRowsSelected = function(flex) {
      for (var i = 0; i < flex.rows.length; i++) {
        if (!flex.rows[i].isSelected) {
          return false;
        }
      }
      return true;
    };
    // ReadOnly 효과설정 : checkbox disabled
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "fnkeyUsed") {
          e.cell.children[0].disabled = true;
        }
      }
    });
    // mouse click
    s.hostElement.addEventListener('mousedown', function (e) {
      var ht = s.hitTest(e);
      // allow sorting/resizing/dragging
      if (ht.cellType === wijmo.grid.CellType.ColumnHeader) {
        return;
      }
      // row 클릭시 선택되도록 설정 : 자연스러운 드래그를 위함
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        for (var i = 0; i < s.rows.length; i++) {
          s.rows[i].isSelected = false;
        }
        s.rows[ht.row].isSelected = true;
      }
      // cancel default handling
      e.preventDefault();
      e.stopPropagation();
      e.stopImmediatePropagation();
    }, true);
    // 필터생성
    $scope.filter = new wijmo.grid.filter.FlexGridFilter(s);
    $scope.filter.showFilterIcons = false;
    $scope.fnkeyUsedFilterType = wijmo.grid.filter.FilterType.value;
    $scope.$apply();

  };
  // 출력코드구성 그리드 조회
  $scope.$on("funcKeyCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.storeCd = selectedRow.storeCd;
    params.fnkeyFg = selectedRow.fnkeyFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/base/store/posfunc/use/getPosFuncKeyList.sb", params, function() {
      // 조회내용 없을 경우 팝업메시지 별도 처리
      if ($scope.flex.collectionView.items.length < 1) {
        $scope._popMsg(messages["posFunc.grid.noFuncKeyData"]);
      } else {
        if (funckeyGraph) {
          funckeyGraph.funcKeyList.initUsed();
        }
      }
      // row 선택 클리어
      $scope.flex.select(-1, -1);
      // 필터적용
      $scope.filter.filterDefinition = '{"defaultFilterType":3,"filters":[{"binding":"fnkeyUsed","type":"value","filterText":"","showValues":{"false":true}}]}';
      $scope.filter.apply();
    }, false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // prtClassCd Data Setter
  $scope.setSaveInfo = function (id, data) {
    saveInfo.set(id, data);
  };
  // prtClassCd Data Getter
  $scope.getSaveInfo = function (id) {
    return saveInfo.get(id);
  };
  // update filter type for "fnkeyUsed" column
  $scope.$watch('fnkeyUsedFilterType', function () {
    var f = $scope.filter;
    if (f) {
      var col = f.grid.columns.getColumn('fnkeyUsed'),
      cf = f.getColumnFilter(col, true);
      cf.filterType = $scope.fnkeyUsedFilterType;
    }
  });
}]);
app.factory('saveInfo', function () {
  var saveInfo = [];
  saveInfo.set = function (id, data) {
    saveInfo[id] = data;
  }
  saveInfo.get = function (id) {
    return saveInfo[id];
  }
  return saveInfo;
});

/**
 * 기능키영역은 화면이 로드된 후 그린다.
 */
var funckeyGraph;
$(document).ready(function() {
  var funcKeyInit = FuncKey.prototype.init;
  FuncKey.prototype.init = function () {
    funcKeyInit.apply(this, arguments);
  };

  if (!mxClient.isBrowserSupported()) {
    // Displays an error message if the browser is not supported.
    mxUtils.error('Browser is not supported!', 200, false);
  }
  else {
    // Adds required resources (disables loading of fallback properties, this can only
    // be used if we know that all keys are defined in the language specific file)
    mxResources.loadDefaultBundle = false;
    var bundle = mxResources.getDefaultBundle(RESOURCE_BASE, mxLanguage)
      || mxResources.getSpecialBundle(RESOURCE_BASE, mxLanguage);

    // Fixes possible asynchronous requests
    mxUtils.getAll(
      [bundle, STYLE_PATH + '/posFuncKey.xml'],
      function (xhr) {
        // Adds bundle text to resources
        mxResources.parse(xhr[0].getText());

        // Configures the default graph theme
        var themes = new Object();
        themes[Graph.prototype.defaultThemeName] = xhr[1].getDocumentElement();

        // Main
        var funckey = new FuncKey(themes);
        funckeyGraph = funckey;
      },
      function () {
        document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
      });
  }

});


//지정된 영역에만 터치키를 넣을 수 있도록 처리
mxGraph.prototype.allowNegativeCoordinates = false;

/**
 * 메인 Class
 */
FuncKey = function (themes) {
  mxEventSource.call(this);
  //기능키 컨테이너
  this.funcKeyContainer = document.getElementById("funcKeyGraph");
  //기능키 영역 생성
  this.funcKey = new Graph(this.funcKeyContainer, themes);
  //상단 Wijmo 그리드 생성
  this.funcKeyList = new FuncKeyList(this.funcKey);
  //설정 영역 생성 : 화면에 보이진 않음
  this.format = new Format(this);
  this.init();

  var funcKey = this.funcKey;
  // 영역 외부 클릭시 이벤트
  $(document).click(function (e) {
    if(!$(event.target).closest('#funcKeyDiv').length) {
      // 그래프영역 선택 초기화
      funcKey.getSelectionModel().clear();
      // 분류영역 에디팅 판단하여 에디팅 취소 처리
      if (funcKey.cellEditor.getEditingCell() != null) {
        funcKey.cellEditor.stopEditing(true);
      }
    }
  });
  // 영역 외부 마우스 이동시.
  $('#funcKeyGraph').mouseleave(function(e){
    var model = funcKey.getModel();
    var parent = funcKey.getDefaultParent();
    //로드 후 생성되는 셀의 인덱스 초기화
    var childCount = model.getChildCount(parent);
    var cell, state;
      console.log(funcKey.view);
    for (var i = 0; i < childCount; i++) {
      cell = model.getChildAt(parent, i);
      var state = funcKey.view.getState(cell);

      console.log(cell);
      console.log(state);

      state.style[mxConstants.STYLE_FILLCOLOR] = funcKey.buttonStyles.off;
      state.style[mxConstants.STYLE_FONTCOLOR] = funcKey.fontStyles.off;
      state.style[mxConstants.STYLE_FONTSIZE] = funcKey.fontStyles.size;
      state.shape.apply(state);
      state.shape.redraw();

      if (state.text != null) {
        state.text.apply(state);
        state.text.redraw();
      }
    }
    // this.currentState = null;
    e.preventDefault();
  });
};

//Extends mxEditor
mxUtils.extend(FuncKey, mxEventSource);

//변수 생성
FuncKey.prototype.funcKey = null;
FuncKey.prototype.funcKeyList = null;

/**
 * 메인 - 초기화
 */
FuncKey.prototype.init = function () {
  //기능키 영역 고유 특성 정의
  this.funcKey.initFuncKeyArea(this.funcKeyList);
  var funcKeyList = this.funcKeyList;
  var mxGraphModelSetVisible = mxGraphModel.prototype.setVisible;
  this.funcKey.model.setVisible = function (cell, visible) {
    //visible = true 인 레이어의 상품을 그리드에서 체크
    if (funcKeyList != null && visible) {
      funcKeyList.initUsed(cell);
    }
    mxGraphModelSetVisible.apply(this, arguments);
  };
  var format = this.format;
  //셀 클릭 시 설정 패널 초기화
  var funcKey = this.funcKey;
  funcKey.addListener(mxEvent.CLICK, function (sender, evt) {
    format.update(funcKey);
  });

  //마우스 오른쪽 클릭 - 컨텍스트 메뉴
  mxEvent.disableContextMenu(this.funcKeyContainer);
  //서버의 초기 설정 로드
  this.format.open(true);

};

FuncKey.prototype.getFuncKeyFormat = function() {
  return this.format;
}


/**
 * 메모리 삭제
 */
FuncKey.prototype.destroy = function () {
  if (this.funcKey != null) {
    this.funcKey.destroy();
    this.funcKey = null;
  }
  if (this.keyHandler != null) {
    this.keyHandler.destroy();
    this.keyHandler = null;
  }

  var c = [this.funcKeyContainer];
  for (var i = 0; i < c.length; i++) {
    if (c[i] != null && c[i].parentNode != null) {
      c[i].parentNode.removeChild(c[i]);
    }
  }
};

/**
 * DOM element 클릭 이벤트 추가
 */
function addClickHandler(elt, funct) {
  if (funct) {
    mxEvent.addListener(elt, 'click', function (evt) {
      funct(evt);
      mxEvent.consume(evt);
    });

    if (document.documentMode !== null && document.documentMode >= 9) {
      // Prevents focus
      mxEvent.addListener(elt, 'mousedown', function (evt) {
        evt.preventDefault();
      });
    }
  }
}

/**
 * 포스 기능키 조회 그리드 처리
 */
function FuncKeyList(graph) {
  this.graph = graph;
  this.init();
}

/**
 * 그리드 영역 초기화
 */
FuncKeyList.prototype.init = function () {
  this.grid = this.makeGrid();
  this.makeDragSource();
};

/**
 * 사용여부 초기화
 */
FuncKeyList.prototype.initUsed = function (layer) {

  var graph = this.graph;
  var theGrid = this.grid;
  var scope = agrid.getScope("funcKeyCtrl");

  var layer = layer || graph.getDefaultParent();
  //그리드의 모든 항목 사용여부 false로 셋팅
  for (i = 0; i < theGrid.rows.length; i++) {
    theGrid.setCellData(theGrid.rows[i].index, 'fnkeyUsed', false);
  }
  //기능키번호로 wijmo 그리드에서 해당 인덱스 추출
  var getIdByFnkeyNo = function (fnkeyNo) {
    var id = -1;
    for (var g = 0; g < theGrid.rows.length; g++) {
      if (theGrid.rows[g]._data.fnkeyNo === fnkeyNo) {
        id = theGrid.rows[g].index;
        break;
      }
    }
    return id;
  };

  //각 상품의 기능키번호로 그리드에서 체크 표시
  var model = graph.getModel();
  var childCount = model.getChildCount(layer);
  var cell, match, regex, fnkeyNo;
  for (var i = 0; i < childCount; i++) {
    cell = model.getChildAt(layer, i);
    regex = /fnkeyNo=([^=]*.(?=;))/gm;
    match = regex.exec(cell.getStyle());
    if (match) {
      fnkeyNo = match[1];
    }
    var id = getIdByFnkeyNo(fnkeyNo);
    if (id >= 0) {
      theGrid.setCellData(id, 'fnkeyUsed', true);
    }
  }

  // 필터적용
  if (scope.filter) {
    scope.filter.filterDefinition = '{"defaultFilterType":3,"filters":[{"binding":"fnkeyUsed","type":"value","filterText":"","showValues":{"false":true}}]}';
    scope.filter.apply();
  }

};

/**
 * 사용여부 초기화
 */
FuncKeyList.prototype.undoFilter = function (cell) {

  var theGrid = this.grid;
  var scope = agrid.getScope("funcKeyCtrl");

  var fnkeyNo;
  var regex = /fnkeyNo=([^=]*.(?=;))/gm;
  var match = regex.exec(cell.getStyle());
  if (match) {
    fnkeyNo = match[1];
  }

  for (var e = 0; e < theGrid.itemsSource.itemsEdited.length; e++) {
    if (theGrid.itemsSource.itemsEdited[e].fnkeyNo === fnkeyNo) {
      var id = theGrid.itemsSource.itemsEdited[e].dispSeq;
      var row = theGrid.itemsSource.itemsEdited[e];
      row.fnkeyUsed = false;
      theGrid.rows.insert(id, row);
      break;
    }
  }

  // 필터적용
  if (scope.filter) {
    scope.filter.filterDefinition = '{"defaultFilterType":3,"filters":[{"binding":"fnkeyUsed","type":"value","filterText":"","showValues":{"false":true}}]}';
    scope.filter.apply();
  }
};


/**
 * 생성된 angularJS 그리드를 가져온다.
 */
FuncKeyList.prototype.makeGrid = function () {

  var grid = agrid.getScope("funcKeyCtrl").flex;

  //선택 Clear
  grid.select(-1, -1);

  return grid;
};


/**
 * Cell을 드래그 할수 있도록 처리
 */
FuncKeyList.prototype.makeDragSource = function () {

  var graph = this.graph;
  var funcKeyList = this;
  var grid = this.grid;

  //드래그 이벤트 생성
  var dropEvent = function (graph, evt, cell, x, y) {

    var parent = graph.getDefaultParent();
    var model = graph.getModel();

    //Drop 할 때 오브젝트 생성
    var pt = graph.getPointForEvent(evt);

    //Grid에서 선택된 데이터 대상
    for (var selected = 0; selected < grid.selectedItems.length; selected++) {

      //마우스 포인터 위치를 기준으로 Drop 가능한 위치 찾기
      var pos = graph.findPosition(pt);
      if (pos == null) {
        mxEvent.consume(evt);
        return;
      }
      //Drop 된 포지션과 다음 포지션에 터치키 생성
      var rows = grid.selectedRows[selected];
      var item = rows.dataItem;
      model.beginUpdate();

      try {
        // 버튼
        var btn = graph.insertVertex(parent, null,
          item.fnkeyNm,
          pos.x, pos.y,
          graph.funcKeyInfo.width, graph.funcKeyInfo.height,
          "fnkeyNo=" + item.fnkeyNo + ";dispSeq=" + item.dispSeq + ";styleCd=01;rounded=0;"
        );
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles.off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles.off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles.size, new Array(btn));

      } finally {
        model.endUpdate();
        funcKeyList.initUsed();
        // 그리드 선택 Clear
        rows.isSelected = false;
      }
    }
  };
  //--dropEvent

  //드래그할 항목 생성
  var previewElt = document.createElement('div');
  previewElt.style.border = 'dashed black 1px';
  previewElt.style.width = graph.funcKeyInfo.x + 'px';
  previewElt.style.height = graph.funcKeyInfo.y + 'px';

  //DnD 처리
  var ds = mxUtils.makeDraggable(grid.cells.hostElement, graph, dropEvent, previewElt, -(graph.funcKeyInfo.x / 2), -(graph.funcKeyInfo.y / 2));
  ds.highlightDropTargets = true;

};

/**
 * 그래픽 영역 - 기능키
 */
function Graph(container, themes) {

  mxGraph.call(this, container);
  this.themes = themes || this.defaultThemes;

  var loadStylesheet = function (graph) {
    var node = (graph.themes != null) ? graph.themes[graph.defaultThemeName] :
      (!mxStyleRegistry.dynamicLoading) ? null :
        mxUtils.load(STYLE_PATH + '/touchKey.xml').getDocumentElement();

    if (node != null) {
      var dec = new mxCodec(node.ownerDocument);
      dec.decode(node, graph.getStylesheet());
    }
  };
  this.currentVertexStyle = mxUtils.clone(this.defaultVertexStyle);
  loadStylesheet(this);
}

/**
 * Graph inherits from mxGraph.
 */
mxUtils.extend(Graph, mxGraph);

Graph.prototype.defaultThemes = {};
Graph.prototype.defaultVertexStyle = {};
//키 사이 간격 두께 : px (Custom 변수)
Graph.prototype.btnBorder = 1;
//터치키 정보 (Custom 변수)
Graph.prototype.funcKeyInfo = { width: 99, height: 60, x: 100, y: 61 };
//현재 선택한 버튼 정보 (Custom 변수)
Graph.prototype.funcKeyInit = { fontSize: 0, fontColor: '', fillColor: ''};
//현재 선택한 하위속성 정보 (Custom 변수)
Graph.prototype.orgChildren = { id: '', parent: new mxCell(), cell: [] };
//최대 페이지 갯수
Graph.prototype.MAX_PAGE = 1;
//한페이지에 컬럼 갯수
Graph.prototype.COL_PER_PAGE = 5;
// 페이지당 줄 수
Graph.prototype.ROW_PER_PAGE = window.MAX_GROUP_ROW;
// 텍스트 에디팅 방지
Graph.prototype.textEditing = false;
Graph.prototype.defaultThemeName = 'funcKey';
//포스기능 키 영역 셀의 prefix
Graph.prototype.groupPrefix = 'P';
//현재 페이지 번호
Graph.prototype.pageNo = 1;
//스타일색상
Graph.prototype.buttonStyles = {};
Graph.prototype.fontStyles = {};


/**
 * 포스기능 키 영역 초기화
 */
Graph.prototype.init = function () {

  mxGraph.prototype.init.apply(this, arguments);

  var graph = this;
  //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
  graph.setPanning(false);

  //Enables HTML labels > true 하는 경우 vertex는 strictHTML 형태로 삽입된다.
  //strictHTML 일때는 shape redraw 가 안먹는다.
  //로직으로 우겨넣으려면 cell.getState()의 dialect 값 조정(DIALECT_SVG) 해서 사용하여야 한다.
  //그래서 일단은 주석처리 해둠 : 20180928 노현수
  // graph.setHtmlLabels(true);

  //셀을 이동했을 때 스크롤 금지
  graph.graphHandler.scrollOnMove = false;
  graph.autoScroll = false;

  //기능키 이동 시 처리
  //대상 셀에 이미 기능키가 있을 경우 이동 금지
  var mxGraphHandlerMoveCells = mxGraphHandler.prototype.moveCells;
  graph.graphHandler.moveCells = function (cells, dx, dy, clone, target, evt) {

    var pt = this.graph.getPointForEvent(evt);
    //박스 크기에 맞게 사이즈 조정
    var newPoint = this.graph.adjustCellPoint(dx, dy);
    dx = newPoint.x;
    dy = newPoint.y;

    //vertex 이동 시 이동될 위치에 vertex가 있는 경우 이동 금지
    var checkCollision = function (bounds, dx, dy, selectedCells) {
      var startX = bounds.x + dx + 1;
      var startY = bounds.y + dy + 1;

      //이동될 위치에 있는 Object가 자신인지 체크
      //자신일 경우에는 이동 가능
      var isMyself = function (cell) {
        var isMy = false;
        for (var i = 0; i < selectedCells.length; i++) {
          if (selectedCells[i] === cell) {
            isMy = true;
            break;
          }
        }
        return isMy;
      };

      //한칸씩 이동 하면서 해당 위치에 셀이 있는지 체크
      var isColl = false;
      for (var x = 0; x < bounds.width; (x += graph.funcKeyInfo.x)) {
        for (var y = 0; y < bounds.height; (y += graph.funcKeyInfo.y)) {
          var cell = graph.getCellAt(startX + x, startY + y);
          if (cell != null && !isMyself(cell)) {
            isColl = true;
            break;
          }
        }
        if (isColl) {
          break;
        }
      }
      return isColl;
    };

    //이동될 위치에 셀이 있는 경우 이동 취소
    if (checkCollision(this.bounds, dx, dy, cells)) {
      mxEvent.consume(evt);
      return;
    }

    //이동될 위치의 좌표 계산
    var dstX1 = this.bounds.x + dx;
    var dstY1 = this.bounds.y + dy;
    var dstX2 = dstX1 + this.bounds.width;
    var dstY2 = dstY1 + this.bounds.height;

    //페이지 범위를 넘는 경우 이동 취소
    var maxBounds = this.graph.getMaximumGraphBounds();
    var startX = (maxBounds.width / this.graph.MAX_PAGE) * (this.graph.pageNo - 1);
    var endX = (maxBounds.width / this.graph.MAX_PAGE) * this.graph.pageNo;
    if (dstX1 < startX || dstX2 > endX) {
      mxEvent.consume(evt);
      return;
    }

    mxGraphHandlerMoveCells.apply(this, arguments);
  };

  //셀의 사이즈가 변경되었을 때 배경 크기에 맞게 보정
  graph.resizeCell = function (cell, bounds, recurse) {
    // 위치 재계산
    var newPoint = this.adjustCellPoint(bounds.x, bounds.y);
    // 사이즈 재계산
    var newSize = this.adjustCellSize(bounds.width, bounds.height);
    // 셀크기 설정
    bounds = new mxRectangle(newPoint.x, newPoint.y, newSize.x, newSize.y);
    //vertex 리사이즈 시 다른 vertex를 덮는 경우 리턴
    var checkCollision = function () {
      var isColl = false;
      for (var x = 0; x < bounds.width; (x += graph.funcKeyInfo.x)) {
        for (var y = 0; y < bounds.height; (y += graph.funcKeyInfo.y)) {
          var cellAt = graph.getCellAt(bounds.x + x, bounds.y + y);
          if (cellAt !== null && cell !== cellAt) {
            isColl = true;
            break;
          }
        }
        if (isColl) {
          break;
        }
      }
      return isColl;
    };
    if (checkCollision()) {
      return;
    }

    mxGraph.prototype.resizeCell.apply(this, arguments);
  };

  //마우스 클릭 할 때 focus 처리
  //https://jgraph.github.io/mxgraph/docs/known-issues.html
  if (mxClient.IS_NS) {
    mxEvent.addListener(graph.container, 'mousedown', function () {
      if (!graph.isEditing()) {
        graph.container.setAttribute('tabindex', '-1');
        graph.container.focus();
      }
    });
  }

};

/**
 * 터치키의 사이즈에 따라 위치이동 보정
 */
Graph.prototype.adjustCellPoint = function (w, h) {
  var kw = this.funcKeyInfo.x;
  var kh = this.funcKeyInfo.y;

  var mw = w % kw;
  var mh = h % kh;
  // 선택된 영역 count ( 가로/세로 )
  var dw = Math.round(w / kw);
  var dh = Math.round(h / kh);
  //터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 크기 보정
  var dx = Math.abs(mw) <= (kw / 2) ? (kw * dw) : (kw * dw);
  var dy = Math.abs(mh) <= (kh / 2) ? (kh * dh) : (kh * dh);

  return new mxPoint(dx, dy);
};

/**
 * 터치키의 셀크기 변경 시 사이즈 보정
 */
Graph.prototype.adjustCellSize = function (w, h) {
  var kw = this.funcKeyInfo.width;
  var kh = this.funcKeyInfo.height;

  var mw = w % kw;
  var mh = h % kh;
  // 선택된 영역 count ( 가로/세로 )
  var dw = Math.round(w / kw);
  var dh = Math.round(h / kh);
  // 터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 크기 보정
  // 터치키 사이의 1px 간격 유지를 위해 해당 사이즈만큼 추가 계산 (btnBorder)
  var dx = ( Math.abs(mw) <= (kw / 2) ? (kw * dw) : (kw * dw) ) + ( ( dw - 1 ) * this.btnBorder );
  var dy = ( Math.abs(mh) <= (kh / 2) ? (kh * dh) : (kh * dh) ) + ( ( dh - 1 ) * this.btnBorder );

  return new mxPoint(dx, dy);
};


/**
 * Sanitizes the given HTML markup.
 */
Graph.prototype.sanitizeHtml = function (value, editing) {
  // Uses https://code.google.com/p/google-caja/wiki/JsHtmlSanitizer
  // NOTE: Original minimized sanitizer was modified to support
  // data URIs for images, and mailto and special data:-links.
  // LATER: Add MathML to whitelisted tags
  function urlX(link) {
    if (link != null && link.toString().toLowerCase().substring(0, 11) !== 'javascript:') {
      return link;
    }
    return null;
  }

  function idX(id) {
    return id
  }
  return html_sanitize(value, urlX, idX);
};

/**
 * 초기 데이터 조회 시 변수 초기화
 */
Graph.prototype.initValue = function (rowPerPage) {
  var graph = this;

  //새로운 셀 생성을 위한 다음 index 값 초기값 계산
  var model = graph.getModel();
  var parent = graph.getDefaultParent();

  //로드 후 생성되는 셀의 인덱스 초기화
  var childCount = model.getChildCount(parent);
  graph.nextGrpId = (childCount + 1);

  //xml에 설정했던 페이지당 줄 수 적용
  graph.ROW_PER_PAGE = parseInt(rowPerPage) || graph.ROW_PER_PAGE;
  // graph 영역 크기 설정
  var graphSizeX = graph.funcKeyInfo.width * graph.COL_PER_PAGE;
  var graphSizeY = graph.funcKeyInfo.height * graph.ROW_PER_PAGE;
  // 버튼사이의 간격 1px 적용
  graphSizeX += (graph.COL_PER_PAGE - 1) * this.btnBorder;
  graphSizeY += (graph.ROW_PER_PAGE - 1) * this.btnBorder;
  // x축 길이 설정
  graphSizeX = graphSizeX * graph.MAX_PAGE;

  // graph 에 길이 설정
  graph.minimumGraphSize = new mxRectangle(0, 0, graphSizeX, graphSizeY);
  graph.maximumGraphBounds = new mxRectangle(0, 0, graphSizeX, graphSizeY);

  // 객체의 class 제거
  var removeClass = function (elt, name) {
    elt.className = elt.className.replace(new RegExp(name, 'g'), '').trim();
  };
  // 객체에 class 추가
  var addClass = function (elt, name) {
    elt.className += ' ' + name;
  };

  //좌우 이동 버튼 위치 초기화
  graph.container.scrollLeft = 0;
  graph.pageNo = 1;

  graph.refresh();
};

/**
 * UNDO/REDO 이벤트 생성 - 상품영역만 적용
 * - 상품분류 생성 시 여러 동작을 하여 불필요한 history가 생성되었음
 */
Graph.prototype.createUndoManager = function (graph) {

  var undoManager = new mxUndoManager();
  var listener = function (sender, evt) {
    undoManager.undoableEditHappened(evt.getProperty('edit'));
  };
  graph.getModel().addListener(mxEvent.UNDO, listener);
  graph.getView().addListener(mxEvent.UNDO, listener);

  return undoManager;
};

/**
 * 키보드 이벤트 생성
 * - 키보드 이벤트를 각각 graph에 적용하는 것이 기능 상 불가능 한것으로 확인.
 */
Graph.prototype.createKeyHandler = function (graph) {
  var keyHandler = new mxKeyHandler(graph);
  // Delete
  keyHandler.bindKey(46, function (evt) {
    // 최초클릭으로 item 선택시에는 delete 가 바로 되지 않는 현상으로 주석처리
//    graph.escape();
    var cells = graph.getDeletableCells(graph.getSelectionCells());

    if (cells != null && cells.length > 0) {
      var parents = graph.model.getParents(cells);
      graph.removeCells(cells);

      // Selects parents for easier editing of groups
      if (parents != null) {
        var select = [];
        for (var i = 0; i < parents.length; i++) {
          if (graph.model.contains(parents[i]) && graph.model.isVertex(parents[i])) {
            select.push(parents[i]);
          }
        }
        graph.setSelectionCells(select);
      }
    }
    graph.funcKeyList.undoFilter(cells[0]);
  });

  //TODO 상품분류에서 상품레이어 처리 시 2개의 트랜잭션 처리되는 문제. 일단 주석 처리
  //Ctrl + z
  keyHandler.bindControlKey(90, function (evt) {
    graph.undoManager.undo();
  });
  //Ctrl + Shift + z
  keyHandler.bindControlShiftKey(90, function (evt) {
    graph.undoManager.redo();
  });

  return keyHandler;
};

/**
 * 그리드 사이즈 만큼 이동하면서 셀의 위치 찾기
 */
Graph.prototype.findPosition = function (pt) {
  var graph = this;
  var cntFind = 0;
  var cx = (graph.funcKeyInfo.x * graph.COL_PER_PAGE * graph.pageNo);
  var cy = (graph.funcKeyInfo.y * graph.ROW_PER_PAGE);
  var px = parseInt(pt.x / graph.funcKeyInfo.x) * graph.funcKeyInfo.x;
  var py = parseInt(pt.y / graph.funcKeyInfo.y) * graph.funcKeyInfo.y;
  var lastX = cx - graph.funcKeyInfo.x;
  var lastY = cy - graph.funcKeyInfo.y;

  for (var posY = py; posY < cy; posY += graph.funcKeyInfo.y) {
    for (var posX = px; posX < cx; posX += graph.funcKeyInfo.x) {
      if (graph.getCellAt((posX + 1), (posY + 1)) == null) {
        return new mxPoint(posX, posY);
      }
      //긴급탈출(만약의 경우..)
      if (cntFind++ > 30) return null;
    }
    //width 끝까지 갔을 때 다음 줄은 0부터 시작
    px = 0;
  }
};

/**
 * 그리드 색상스타일 값 초기화
 */
Graph.prototype.initStyle = function() {

  var buttonStyles = {};
  var fontStyles = {};
  buttonStyles.on = "#f58220";
  buttonStyles.off = "#006c8a";
  fontStyles.on = "#ffffff";
  fontStyles.off = "#ffffff";
  fontStyles.size = 15;

  // 개별 영역의 변수에 할당
  this.buttonStyles = buttonStyles;
  this.fontStyles = fontStyles;

};

/**
 * 포스기능 키 삭제
 * @param cell
 */
function deleteFnkey(format) {
  var graph = format.touchkey.funcKey;
  var dCells = graph.getDeletableCells(graph.getSelectionCells());
  if (dCells != null && dCells.length > 0) {
    var parents = graph.model.getParents(dCells);
    graph.removeCells(dCells);
    graph.getSelectionModel().clear();
    // Selects parents for easier editing of groups
    if (parents != null) {
      var select = [];
      for (var i = 0; i < parents.length; i++) {
        if (graph.model.contains(parents[i]) && graph.model.isVertex(parents[i])) {
          select.push(parents[i]);
        }
      }
    }
    graph.funcKeyList.undoFilter(dCells[0]);
  }
}

/**
 * 오른쪽 설정 기능 패널
 */
function Format(touchkey) {

  this.touchkey = touchkey;
  this.container = document.getElementById('format');
  this.graph = touchkey.group;

  this.init();

}

/**
 * 기능패널의 변수 선언
 */
Format.prototype.fontColor = null;
Format.prototype.fontSize = null;
Format.prototype.fillColor = null;

/**
 * 기능 패널 초기화
 */
Format.prototype.init = function () {

  //분류, 상품 선택 변경 시 설정 패널 초기화
  this.update = mxUtils.bind(this, function (graph) {
    this.graph = graph;
    this.refresh();
  });

  //모든 구성 요소 생성
  this.initElements();
};

/**
 * 화면 새로 그리기
 */
Format.prototype.refresh = function () {
  var graph = this.graph;
  //선택된 셀이 있을 때만 활성화 되는 부분
  var cells = graph.getSelectionCells();
  if (cells.length > 0) {
    //설정 값 초기화
    this.setElementsValue();
  }
};

/**
 * 폰트/정렬 설정 초기화
 */
Format.prototype.initElements = function () {

  var graph = this.graph;
  var format = this;

  // 구성초기화 버튼
  // addClickHandler(document.getElementById('btnFuncInit'), function () {
  //   format.open(false);
  // });
  //
  // // 삭제 버튼
  addClickHandler(document.getElementById('btnFuncDelete'), function () {
    deleteFnkey(format);
  });

  // 저장 버튼
  addClickHandler(document.getElementById('btnFuncSave'), function () {
    format.save(format.touchkey.funcKey);
  });

};

/**
 * 마우스 오버시 색상 스타일 테마 적용
 */
Graph.prototype.updateHoverStyle = function(state, hover) {
  if (hover) {
    state.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles.on;
    state.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles.on;
    state.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles.size;
  } else {
    state.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles.off;
    state.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles.off;
    state.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles.size;
  }
  state.shape.apply(state);
  state.shape.redraw();

  if (state.text != null) {
    state.text.apply(state);
    state.text.redraw();
  }
};


/**
 * 폰트/정렬 설정값 Set
 */
Format.prototype.setElementsValue = function () {
  var graph = this.graph;
  var format = this;
  //선택된 셀에서 스타일 정보 읽기
  var cells = graph.getSelectionCells();
  var initFontSize = 15;
  var initFontColor;
  var initFillColor;
  for (var i = 0; i < cells.length; i++) {
    var cell = cells[i];
    var state = graph.view.getState(cell);
    if (state != null) {
      initFontSize = Math.max(0, parseInt(mxUtils.getValue(state.style, mxConstants.STYLE_FONTSIZE, null)));
      initFontColor = mxUtils.getValue(state.style, mxConstants.STYLE_FONTCOLOR, null);
      initFillColor = mxUtils.getValue(state.style, mxConstants.STYLE_FILLCOLOR, null);
    }
  }

  graph.funcKeyInit.fontSize = initFontSize;
  graph.funcKeyInit.fontColor = initFontColor;
  graph.funcKeyInit.fillColor = initFillColor;

};

/**
 * 기존 구성 조회 : 그리드 클릭시
 */
Format.prototype.openByGrid = function (isLoad) {
  this.open(isLoad);
};

/**
 * 기존 구성 조회
 */
Format.prototype.open = function (isLoad) {
  var funcKey = this.touchkey.funcKey;

  var scope = agrid.getScope("funcKeyCtrl");
  var storeCd = scope.getSaveInfo("storeCd");
  var posNo = scope.getSaveInfo("posNo");
  var fnkeyFg = scope.getSaveInfo("fnkeyFg");
  var params = 'storeCd=' + storeCd + "&posNo=" + posNo + "&fnkeyFg=" + fnkeyFg;

  //open
  var reqFuncKey = mxUtils.post(FUNCKEY_OPEN_URL, params,
    mxUtils.bind(this, function (req) {
      //var enabled = req.getStatus() != 404;
      if (req.getStatus() === 200) {
        try {
          var jsonStr = JSON.parse(req.getText());
          var xmlStr = jsonStr.data;
          if (xmlStr != null) {
            var funcKeyXml = mxUtils.parseXml(xmlStr);
            this.setGraphXml(funcKey, funcKeyXml.documentElement);
          } else {
            // xml이 없는경우 초기화
            this.setGraphXml(funcKey, null);
          }
        }
        catch (e) {
          mxUtils.alert(mxResources.get('errorOpeningFile'));
        }
        if (!isLoad) {
          mxUtils.alert(mxResources.get('opened'));
        }
      }
      else {
        mxUtils.alert(mxResources.get('errorOpeningFile'));
      }
    })
  );
};

/**
 * Sets the XML node for the current diagram.
 */
Format.prototype.setGraphXml = function (graph, node) {

  if (node != null) {
    var dec = new mxCodec(node.ownerDocument);
    if (node.nodeName === 'mxGraphModel') {
      graph.model.beginUpdate();
      try {
        graph.model.clear();
        dec.decode(node, graph.getModel());
      }
      finally {
        graph.model.endUpdate();
        //로드 후 변수 초기화
        graph.initValue(graph.ROW_PER_PAGE);
      }
    }
    else {
      throw {
        message: mxResources.get('cannotOpenFile'),
        node: node,
        toString: function () {
          return this.message;
        }
      };
    }
  }
  else {
    graph.model.clear();
  }
};

/**
 * 화면구성 XML을 서버에 저장
 */
Format.prototype.save = function () {

  var funcKey = this.touchkey.funcKey;
  var scope = agrid.getScope("funcKeyCtrl");

  if (funcKey.isEditing()) {
    funcKey.stopEditing();
  }

  var node = null;
  var enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(funcKey.getModel());
  var funcKeyXml = mxUtils.getXml(node);

  var storeCd = scope.getSaveInfo("storeCd");
  var posNo = scope.getSaveInfo("posNo");
  var fnkeyFg = scope.getSaveInfo("fnkeyFg");
  var params = '&storeCd=' + storeCd + "&posNo=" + posNo + "&fnkeyFg=" + fnkeyFg;

  var xml = encodeURIComponent(funcKeyXml);

  try {
    if (xml.length < MAX_REQUEST_SIZE) {
      var onload = function (req) {
        mxUtils.alert(mxResources.get('saved'));
      }
      var onerror = function (req) {
        mxUtils.alert('Error');
      }
      new mxXmlRequest(FUNCKEY_SAVE_URL, 'xml=' + xml + params).send(onload, onerror);
    }
    else {
      mxUtils.alert(mxResources.get('drawingTooLarge'));
      return;
    }
  }
  catch (e) {
    mxUtils.alert(mxResources.get('errorSavingFile'));
  }

};

/**
 * 기능키 영역 초기화
 */
Graph.prototype.initFuncKeyArea = function (funcKeyList) {

  // Fixes ignored clipping if foreignObject used in Webkit
  mxClient.NO_FO = mxClient.NO_FO || mxClient.IS_SF || mxClient.IS_GC;

  //영역변수 설정
  var graph = this;
  this.funcKeyList = funcKeyList;
  var theGrid = this.funcKeyList.grid;
  //키 정보 (Custom 변수)
  graph.funcKeyInfo = {width: 99, height: 60, x: 100, y: 61};
  //현재 선택한 버튼 정보 (Custom 변수)
  graph.funcKeyInit = {fontSize: 0, fontColor: '', fillColor: ''};
  //기능키 영역은 2줄로 초기화
  graph.initValue(2);
  //색상스타일값 초기화
  graph.initStyle();
  //멀티선택방지
  graph.getSelectionModel().setSingleSelection(true);
  //UNDO, 키 핸들러 추가
  graph.undoManager = graph.createUndoManager(graph);
  var rubberband = new mxRubberband(graph);
  graph.keyHandler = graph.createKeyHandler(graph);

  //셀 삭제 시 그리드에 반영
  var cellsRemoved = graph.cellsRemoved;
  graph.cellsRemoved = function (cells) {
    cellsRemoved.apply(this, arguments);
    funcKeyList.initUsed();
  };

  //셀 추가 시 그리드에 반영
  var cellsAdded = graph.cellsAdded;
  graph.cellsAdded = function (cells) {
    cellsAdded.apply(this, arguments);
    funcKeyList.initUsed();
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
    funcKeyList.initUsed();
  };
  graph.undoManager.addListener(mxEvent.UNDO, undoHandler);
  graph.undoManager.addListener(mxEvent.REDO, undoHandler);

  //override 마우스 이벤트
  graph.addMouseListener({
    currentState: null,
    previousStyle: null,
    mouseDown: function (sender, me) {
      // 마우스오버를 위해 초기화
      if (this.currentState != null) {
        this.dragLeave(me.getEvent(), this.currentState);
        this.currentState = null;
      }
    },
    mouseMove: function (sender, me) {

      console.log("me.getState()) ", me.getState());
      console.log("this.currentState ", this.currentState);


      // 에디팅모드(더블클릭) 이후 마우스커서 이동시 라벨에 잔상남는 현상으로
      // graph.cellEditor.getEditingCell() 으로 에디팅 셀 있는지 판단해서 이벤트 적용 설정 : 20180929 노현수
      if (graph.cellEditor.getEditingCell() == null) {
        if (this.currentState != null && me.getState() === this.currentState) {
          return;
        }
        var tmp = graph.view.getState(me.getCell());
        // Ignores everything but vertices
        if (graph.isMouseDown || (tmp !== null && !graph.getModel().isVertex(tmp.cell))) {
          tmp = null;
        }

        console.log("tmp", tmp);

        if (tmp !== this.currentState) {
          if (this.currentState != null  ) {
            this.dragLeave(me.getEvent(), this.currentState);
          }
          this.currentState = tmp;
          if (this.currentState != null) {
            this.dragEnter(me.getEvent(), this.currentState);
          }
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

};


/**
 * 로딩 시점에 초기화
 * @returns
 */
(function () {
  //Sets colors for handles
  mxConstants.HANDLE_FILLCOLOR = '#99ccff';
  mxConstants.HANDLE_STROKECOLOR = '#0088cf';
  mxConstants.VERTEX_SELECTION_COLOR = '#00a8ff';
  mxConstants.OUTLINE_COLOR = '#00a8ff';
  mxConstants.OUTLINE_HANDLE_FILLCOLOR = '#99ccff';
  mxConstants.OUTLINE_HANDLE_STROKECOLOR = '#00a8ff';
  mxConstants.CONNECT_HANDLE_FILLCOLOR = '#cee7ff';
  mxConstants.EDGE_SELECTION_COLOR = '#00a8ff';
  mxConstants.DEFAULT_VALID_COLOR = '#00a8ff';
  mxConstants.LABEL_HANDLE_FILLCOLOR = '#cee7ff';
  mxConstants.GUIDE_COLOR = '#0088cf';
  mxConstants.HIGHLIGHT_OPACITY = 30;
  mxConstants.HIGHLIGHT_SIZE = 8;
})();
