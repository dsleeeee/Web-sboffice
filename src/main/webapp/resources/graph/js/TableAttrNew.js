//그리드의 크기
mxGraph.prototype.gridSize = 10;

//지정된 영역에만 구성요소를 넣을 수 있도록 처리
mxGraph.prototype.allowNegativeCoordinates = false;

//셀 가이드 사용 여부
mxGraphHandler.prototype.guidesEnabled = true;

var currentTblTypeFg = "1";
var currentTblNum = "";
var currentBgImg = "";
var currentHorizontalRatio = 0;
var currentVerticalRatio = 0;
var tblAttrGrid;
var currentWidth;
var currentHeight;

/**
 * 메인 Class
 */
TableAttr = function(themes) {
    mxEventSource.call(this);
    this.container = document.getElementById('contentTblAttr');
    this.containerPreview = document.getElementById('previewTblAttr');

    //그래프 프리뷰 객체 생성
    this.preview = new PreViewAttr(this.containerPreview, themes);
    this.preview.setEnabled(true);

    //그래프 객체 생성
    this.graph = new GraphAttr(this.container, themes, this.preview);

    //왼쪽 속성명
    this.comp = new SidebarAttr(this.graph, this.preview);

    //오른쪽 설정 영역 생성
    this.format = new FormatAttr(this);

    this.init();

};

//Extends mxEditor
mxUtils.extend(TableAttr, mxEventSource);

//변수 생성
TableAttr.prototype.graph = null;
TableAttr.prototype.comp = null;
TableAttr.prototype.layer = null;
TableAttr.prototype.format = null;

//배경색
TableAttr.prototype.defaultBackgroundColor = '#ffffff';

/**
 * 메인 - 초기화
 */
TableAttr.prototype.init = function() {

    //마우스 오른쪽 클릭 - 컨텍스트 메뉴
    mxEvent.disableContextMenu(this.container);

    //매장영역에서 이벤트 발생 시 설정영역을 새로 그려주기 위한 이벤트 핸들러
    this.graph.getSelectionModel().addListener(mxEvent.CHANGE, this.format.update);
    this.graph.addListener(mxEvent.EDITING_STARTED, this.format.update);
    this.graph.addListener(mxEvent.EDITING_STOPPED, this.format.update);
    this.graph.getModel().addListener(mxEvent.CHANGE, this.format.update);
    this.graph.addListener(mxEvent.ROOT, this.format.update);

    //서버의 초기 설정 로드
    var openResult = this.format.open(true);

};
/**
 * 메인 - 기존 설정 opn 시 값 초기화
 */
TableAttr.prototype.initValue = function() {
    //기존 설정 오픈 시 그리드 초기값 설정(사용여부)
    this.comp.initValue();
};

/**
 * 왼쪽 속성명 처리
 */
SidebarAttr = function(graph, preview) {
    this.graph = graph;
    this.preview = preview;
    this.init();
}

/**
 * 속성명 영역 초기화
 */
SidebarAttr.prototype.init = function() {

    this.tblAttrGrid = this.makeGrid();

    var graph = this.graph;
    tblAttrGrid = this.tblAttrGrid;

    //셀 삭제 시 그리드에서 사용여부 해제 처리
    var cellsRemoved = graph.cellsRemoved;
    graph.cellsRemoved = function(cells) {
        for (var i = 0; i < cells.length; i++) {
            var cell = cells[i];
            tblAttrGrid.setCellData((parseInt(cell.getId()) - 1), 'used', false);
            tblAttrGrid.setCellData((parseInt(cell.getId()) - 1), 'show', false);
        }
        cellsRemoved.apply(this, arguments);
    }

    //셀 추가 시 그리드에서 사용여부 체크 처리
    var cellsAdded = graph.cellsAdded;
    graph.cellsAdded = function(cells) {
        for (var i = 0; i < cells.length; i++) {
            var cell = cells[i];
            tblAttrGrid.setCellData((parseInt(cell.getId()) - 1), 'used', true);
            tblAttrGrid.setCellData((parseInt(cell.getId()) - 1), 'show', true);
        }
        cellsAdded.apply(this, arguments);
    }

    //UNDO 를 했을 때 그리드의 사용여부 업데이트
    var undoHandler = function(sender, evt) {
        var model = graph.getModel();
        var cells = [];

        //추가된 경우 사용여부 true
        var cand = graph.getSelectionCellsForChanges(evt.getProperty('edit').changes);
        for (var i = 0; i < cand.length; i++) {
            if ((model.isVertex(cand[i]) || model.isEdge(cand[i])) && graph.view.getState(cand[i]) != null) {
                cells.push(cand[i]);
                tblAttrGrid.setCellData(parseInt(cand[i].id) - 1, 'used', true);
                tblAttrGrid.setCellData(parseInt(cand[i].id) - 1, 'show', true);
            }
        }
        graph.setSelectionCells(cells);

        //삭제된 경우 사용여부 false
        var removes = graph.getRemovedCellsForChanges(evt.getProperty('edit').changes);
        for (var i = 0; i < removes.length; i++) {
            if ((model.isVertex(removes[i]) || model.isEdge(removes[i]))) {
                tblAttrGrid.setCellData(parseInt(removes[i].id) - 1, 'used', false);
                tblAttrGrid.setCellData(parseInt(removes[i].id) - 1, 'show', false);
            }
        }
    }
    graph.undoManager.addListener(mxEvent.UNDO, undoHandler);
    graph.undoManager.addListener(mxEvent.REDO, undoHandler);

};
/**
 * 속성명 영역 값 초기화
 */
SidebarAttr.prototype.initValue = function() {
    var graph = this.graph;
    tblAttrGrid = this.tblAttrGrid;

    //그리드에서 사용여부 초기화
    this.initUsed();
};
/**
 * 사용여부 초기화
 */
SidebarAttr.prototype.initUsed = function() {
    var graph = this.graph;
    tblAttrGrid = this.tblAttrGrid;

    //모든 속성 사용여부 false로 초기화
    for (i = 0; i < tblAttrGrid.rows.length; i++) {
        tblAttrGrid.setCellData(tblAttrGrid.rows[i].index, 'used', false);
        tblAttrGrid.setCellData(tblAttrGrid.rows[i].index, 'show', false);
    }

    //그래픽 영역의 Object를 체크해 사용여부 true 처리
    var parent = graph.getDefaultParent();
    var model = graph.getModel();
    var childCount = model.getChildCount(parent);
    for (var i = 0; i < childCount; i++) {
        var cell = model.getChildAt(parent, i);
        tblAttrGrid.setCellData((parseInt(cell.getId()) - 1), 'used', true);
        tblAttrGrid.setCellData((parseInt(cell.getId()) - 1), 'show', true);
    }
};

/**
 * 그리드 생성
 */
SidebarAttr.prototype.makeGrid = function() {

    //var sidebar = this;
    var graph = this.graph;
    var preview = this.preview;

    //속성명 FlexGrid 생성
    var tblAttrGridTemp = new wijmo.grid.FlexGrid('#tblAttrGrid', {
        autoGenerateColumns: false,
        selectionMode: 'ListBox',
        isReadOnly: false,
        itemsSource: getData(),
        columns:
        	[{
                binding: 'idx',
                visible: false
            },
            {
                binding: 'name',
                header: mxResources.get('attrName'),
                width: '*',
                isReadOnly: true
            },
            {
                binding: 'tag',
                header: mxResources.get('preview'),
                isReadOnly: true,
                visible: false
            },
            {
                binding: 'used',
                visible: false
            },
            {
                binding: 'show',
                header: mxResources.get('alreadyShow'),
                width: 60
            },
            {
                binding: 'rect',
                visible: false
            }
        ],
        showAlternatingRows: false,
        formatItem: function(s, e) {
            if (e.panel == s.cells && s.columns[e.col].binding == 'tag') {
                var item = s.rows[e.row].dataItem;
                e.cell.innerHTML = item.tag;
            }
        }
    });

    // 그리드 체크박스 클릭 이벤트
    tblAttrGridTemp.hostElement.addEventListener('mousedown', function(e) {

    	var htInfo = tblAttrGridTemp.hitTest(e);

    	if(e.target.type === 'checkbox') {

    		var rowIndex=htInfo.row;

    		console.log(rowIndex);

    		//idx가져오기(0번째 항목)
            var idx = tblAttrGridTemp.getCellData(rowIndex, 0, true);
            var used = tblAttrGridTemp.getCellData(rowIndex, 3, true);
            var show = tblAttrGridTemp.getCellData(rowIndex, 4, true);

            //사용중일 경우만 선택 활성화
            if (used) {
                var cell = graph.getModel().getCell(idx);
                graph.setSelectionCell(cell);

                //맨앞으로 가져오기
                graph.orderCells(false);
                graph.container.focus();

                if (show == 'true') {

                    var cellsTemp = [];
                    cellsTemp.push(cell);

                    graph.setCellStyles(mxConstants.STYLE_OPACITY, 100, cellsTemp);
                    preview.setCellStyles(mxConstants.STYLE_OPACITY, 100, cellsTemp);

                    graph.setCellStyles(mxConstants.STYLE_TEXT_OPACITY, 100, cellsTemp);
                    preview.setCellStyles(mxConstants.STYLE_TEXT_OPACITY, 100, cellsTemp);


                } else {

                	var cellsTemp = [];
                    cellsTemp.push(cell);

                    graph.setCellStyles(mxConstants.STYLE_OPACITY, 0, cellsTemp);
                    preview.setCellStyles(mxConstants.STYLE_OPACITY, 0, cellsTemp);

                    graph.setCellStyles(mxConstants.STYLE_TEXT_OPACITY, 0, cellsTemp);
                    preview.setCellStyles(mxConstants.STYLE_TEXT_OPACITY, 0, cellsTemp);
                }

            }

    	}
    }, false);

    //RowHeader 없애기
    tblAttrGridTemp.rowHeaders.columns.splice(0, 1);

    //ListBox 선택모드에서 cell의 hostElement를 가져오는데 문제가 있어
    //아래 2줄을 추가 하였음
    if (tblAttrGridTemp.collectionView.items.length > 0) {
        tblAttrGridTemp.select(-1, -1);
        tblAttrGridTemp.select(0, 1);
    }

    //선택한 ROW가 바뀌었을 때 그래픽 영역에서 활성화
    tblAttrGridTemp.selectionChanged.addHandler(function(s, e) {

        //idx가져오기(0번째 항목)
        var idx = tblAttrGridTemp.getCellData(e.row, 0, true);
        var used = tblAttrGridTemp.getCellData(e.row, 3, true);

        //사용중일 경우만 선택 활성화
        if (used) {
            var cell = graph.getModel().getCell(idx);
            graph.setSelectionCell(cell);

        	var selectedCell = cell.geometry;

        	$("#attrCellX").val(selectedCell.x);
        	$("#attrCellY").val(selectedCell.y);
        	$("#attrCellW").val(selectedCell.width);
        	$("#attrCellH").val(selectedCell.height);

            //맨앞으로 가져오기
            graph.orderCells(false);
            graph.container.focus();

        }
    });

    //판매금액 포맷팅
    var numberWithCommas = function(x) {
        var isInteger = function(num) {
            return (num ^ 0) === num;
        };
        if (isInteger(x)) {
            x = x.toString();
            var pattern = /(-?\d+)(\d{3})/;
            while (pattern.test(x)) {
                x = x.replace(pattern, "$1,$2");
            }
        }
        return x;
    }

    //Drop 이벤트
    var dropEvent = function(graph, evt, cell, x, y) {
        var parent = graph.getDefaultParent();
        var model = graph.getModel();

        //Drop 할 때 오브젝트 생성
        var pt = graph.getPointForEvent(evt);
        for (var selected = 0; selected < tblAttrGridTemp.selectedItems.length; selected++) {
            var rows = tblAttrGridTemp.selectedRows[selected];
            var item = rows.dataItem;
            //console.log(item.tag);
            //사용중이 아닌 항목만 넣기
            if (!item.used) {
                var lastCell;
                model.beginUpdate();
                try {
                    lastCell = graph.insertVertex(parent,
                        item.idx,
                        numberWithCommas(item.tag),
                        item.rect.x, item.rect.y,
                        item.rect.width, item.rect.height,
                        'tableAttr');
                } finally {
                    model.endUpdate();
                }
                graph.setSelectionCell(lastCell);
            }
        }
    };

    //Row에 DnD이벤트 생성
    var cells = tblAttrGridTemp.cells.hostElement.childNodes;
    for (var i = 1; i < cells.length; i++) {
        var ds = mxUtils.makeDraggable(cells[i], graph, dropEvent, cells[i], -10, -(tblAttrGridTemp.rows.defaultSize * (i - 1)));
        ds.highlightDropTargets = true;
        ds.guidesEnabled = true;
    }

    //서버에서 받은 데이터로 설정
    function getData() {

        //기본값 테이블에서 초기 위치 추출
        var findPos = function(cd) {
            var obj;
            for (x = 0; x < TABLE_ATTR_DEFAULTS.length; x++) {
                obj = TABLE_ATTR_DEFAULTS[x];
                if (cd == obj.attrCd) {
                    return new mxRectangle(obj.x, obj.y, obj.width, obj.height);
                }
            }
            return new mxRectangle(10, 10, 50, 50);
        };

        //그래프의 child node는 2부터 시작할 수 밖에 없어 코드 맞추기 위해 lpad 하였음
        function lpad(s, padLength, padString) {
            while (s.length < padLength)
                s = padString + s;
            return s;
        }

        //데이터 생성
        var data = [];
        for (i = 0; i < TABLE_ATTR_ITEMS.length; i++) {
            var cd = lpad((i + 1).toString(), 2, '0');
            data.push({
                name: TABLE_ATTR_ITEMS[i].nmcodeNm,
                tag: TABLE_ATTR_ITEMS[i].nmcodeItem1,
                used: false,
                show: true,
                rect: findPos(cd),
                idx: cd
            });
        }
        return data;
    }

    return tblAttrGridTemp;
};

/**
 * 그래픽 영역
 */
function GraphAttr(container, themes, preview) {

	this.preview = preview;

    mxGraph.call(this, container);
    this.themes = themes || this.defaultThemes;

    var loadStylesheet = function(graph) {
        var node = (graph.themes != null) ? graph.themes[graph.defaultThemeName] :
            (!mxStyleRegistry.dynamicLoading) ? null :
            mxUtils.load(STYLE_PATH + '/tableAttr.xml').getDocumentElement();

        if (node != null) {
            var dec = new mxCodec(node.ownerDocument);
            dec.decode(node, graph.getStylesheet());
        }
    };
    this.currentVertexStyle = mxUtils.clone(this.defaultVertexStyle);
    loadStylesheet(this);

}

/**
 * 그래픽 프리뷰 영역
 */
function PreViewAttr(container, themes) {

    mxGraph.call(this, container);
    this.themes = themes || this.defaultThemes;

    var loadStylesheet = function(graph) {
        var node = (graph.themes != null) ? graph.themes[graph.defaultThemeName] :
            (!mxStyleRegistry.dynamicLoading) ? null :
            mxUtils.load(STYLE_PATH + '/tableAttr.xml').getDocumentElement();

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
mxUtils.extend(GraphAttr, mxGraph);
mxUtils.extend(PreViewAttr, mxGraph);

GraphAttr.prototype.defaultThemes = {};
GraphAttr.prototype.defaultVertexStyle = {};
PreViewAttr.prototype.defaultThemes = {};
PreViewAttr.prototype.defaultVertexStyle = {};

GraphAttr.prototype.defaultThemeName = 'tableAttr';
PreViewAttr.prototype.defaultThemeName = 'tableAttr';

//배경이미지 - 기본설정
//테이블 구성 모양을 넣고 싶을 때 사용 가능
//Graph.prototype.graphBackgroundImage = IMAGE_PATH + '/gradient_background.jpg';


/**
 * 영역 초기화
 */
GraphAttr.prototype.init = function() {

    mxGraph.prototype.init.apply(this, arguments);

    var graph = this;
    //console.log(graph);

    //그래프 영역의 배경 색 설정 - 기본설정
    //graph.setBackgroundImage(new mxImage(this.graphBackgroundImage, 600, 640));

    graph.minimumGraphSize = new mxRectangle(0, 0, 500, 500);
    graph.maximumGraphBounds = new mxRectangle(0, 0, 500, 500);


    //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
    graph.setPanning(false);

    //Enables HTML labels
    graph.setHtmlLabels(true);

    //셀을 이동했을 때 스크롤 금지
    graph.graphHandler.scrollOnMove = false;
    graph.autoScroll = false;

    //UNDO, 키 핸들러 추가
    graph.undoManager = graph.createUndoManager(graph);
    var rubberband = new mxRubberband(graph);
    graph.keyHandler = graph.createKeyHandler(graph);
    //console.log(this);

    //마우스 클릭 할 때 focus 처리, jsp에서 content에 tabindex -1 로 처리 했으나 추가
    //https://jgraph.github.io/mxgraph/docs/known-issues.html
    if (mxClient.IS_NS) {
        mxEvent.addListener(graph.container, 'mousedown', function() {
            if (!graph.isEditing()) {
                graph.container.setAttribute('tabindex', '-1');
                graph.container.focus();
            }
        });
    }

    // 구성요소 이동 시 처리
    var mxGraphHandlerMoveCells = mxGraphHandler.prototype.moveCells;
    graph.graphHandler.moveCells = function(cells, dx, dy, clone, target, evt) {

        var pt = graph.getPointForEvent(evt);

        // 네 모서리에 셀이 있으면 이동 금지
        var x1 = this.bounds.x + dx;
        var y1 = this.bounds.y + dy;
        var x2 = x1 + this.bounds.width;
        var y2 = y1 + this.bounds.height;

    	$("#attrCellX").val(x1);
    	$("#attrCellY").val(y1);

        mxGraphHandlerMoveCells.apply(this, arguments);
    };

    // 셀의 사이즈가 변경되었을 때 배경 크기에 맞게 보정
    var mxGraphResizeCell = mxGraph.prototype.resizeCell;
    graph.resizeCell = function(cell, bounds, recurse) {

        // 네 모서리에 셀이 있으면 이동 금지
        var x1 = bounds.x;
        var y1 = bounds.y;
        var x2 = x1 + bounds.width;
        var y2 = y1 + bounds.height;

    	$("#attrCellW").val(bounds.width);
    	$("#attrCellH").val(bounds.height);

        mxGraphResizeCell.apply(this, arguments);
    };

    var graphHandlerMouseDown = mxGraphHandler.prototype.mouseDown;
    graph.graphHandler.mouseDown = function(sender, me) {

    	if (me.state != null && me.state != undefined) {

    		var selectedCell = me.state.cell;
        	var cell = selectedCell.geometry;
        	var cellStyle = selectedCell.style;

        	currentCell = selectedCell;

        	$("#attrCellX").val(cell.x);
        	$("#attrCellY").val(cell.y);
        	$("#attrCellW").val(cell.width);
        	$("#attrCellH").val(cell.height);

        	for (var selected = 0; selected < tblAttrGrid.rows.length; selected++) {
                var rows = tblAttrGrid.rows[selected];
                var item = rows.dataItem;

                if (item.used) {
                	if (selectedCell.id == item.idx) {
                		tblAttrGrid.select(new wijmo.grid.CellRange(selected, 1), true);
                	}
                }
            }

    	} else {

    		currentCell = null;

    		$("#attrCellX").val("");
        	$("#attrCellY").val("");
        	$("#attrCellW").val("");
        	$("#attrCellH").val("");
    	}

    	graphHandlerMouseDown.apply(this, arguments);
    }
};

/**
 * 프리뷰 영역 초기화
 */
PreViewAttr.prototype.init = function() {

    mxGraph.prototype.init.apply(this, arguments);

    var preview = this;

    //console.log(graph);

    //그래프 영역의 배경 색 설정 - 기본설정
    //graph.setBackgroundImage(new mxImage(this.graphBackgroundImage, 600, 640));

    preview.minimumGraphSize = new mxRectangle(0, 0, 100, 100);
    preview.maximumGraphBounds = new mxRectangle(0, 0, 100, 100);


    //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
    preview.setPanning(false);

    //Enables HTML labels
    preview.setHtmlLabels(true);

    //셀을 이동했을 때 스크롤 금지
    preview.graphHandler.scrollOnMove = false;
    preview.autoScroll = false;

};

/**
 * Sanitizes the given HTML markup.
 */
GraphAttr.prototype.sanitizeHtml = function(value, editing) {
    // Uses https://code.google.com/p/google-caja/wiki/JsHtmlSanitizer
    // NOTE: Original minimized sanitizer was modified to support
    // data URIs for images, and mailto and special data:-links.
    // LATER: Add MathML to whitelisted tags
    function urlX(link) {
        if (link != null && link.toString().toLowerCase().substring(0, 11) !== 'javascript:') {
            return link;
        }

        return null;
    };

    function idX(id) {
        return id
    };
    return html_sanitize(value, urlX, idX);
};

/**
 * Sanitizes the given HTML markup.
 */
PreViewAttr.prototype.sanitizeHtml = function(value, editing) {
    // Uses https://code.google.com/p/google-caja/wiki/JsHtmlSanitizer
    // NOTE: Original minimized sanitizer was modified to support
    // data URIs for images, and mailto and special data:-links.
    // LATER: Add MathML to whitelisted tags
    function urlX(link) {
        if (link != null && link.toString().toLowerCase().substring(0, 11) !== 'javascript:') {
            return link;
        }

        return null;
    };

    function idX(id) {
        return id
    };
    return html_sanitize(value, urlX, idX);
};


/**
 * UNDO/REDO 이벤트 생성
 */
GraphAttr.prototype.createUndoManager = function(graph) {

    var undoManager = new mxUndoManager();
    var listener = function(sender, evt) {
        undoManager.undoableEditHappened(evt.getProperty('edit'));
    };
    graph.getModel().addListener(mxEvent.UNDO, listener);
    graph.getView().addListener(mxEvent.UNDO, listener);

    return undoManager;
};

/**
 * 키보드 이벤트 생성
 */
GraphAttr.prototype.createKeyHandler = function(graph) {
    var keyHandler = new mxKeyHandler(graph);

    keyHandler.bindKey(46, function(evt) {
        graph.escape();
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
    });

    //Ctrl + z
    keyHandler.bindControlKey(90, function(evt) {
        graph.undoManager.undo()
    });
    //Ctrl + Shift + z
    keyHandler.bindControlShiftKey(90, function(evt) {
        graph.undoManager.redo()
    });

    return keyHandler;
};

/**
 * 오른쪽 설정 기능 패널
 */
function FormatAttr(tableattr) {

    this.container = document.getElementById('format');

    this.main = tableattr;
    this.graph = tableattr.graph;
    this.preview = tableattr.preview ;

    this.init();
}

FormatAttr.prototype.fontFamily = null;
FormatAttr.prototype.fontColor = null;
FormatAttr.prototype.fontSize = null;
FormatAttr.prototype.fontBold = null;

//TODO 폰트 종류 - POS에서 가능한 폰트로 변경할 것
FormatAttr.prototype.defaultFonts =
	[{
        name: 'NotoR',
        value: 0
    },
    {
        name: 'Hanna',
        value: 1
    },
    {
        name: 'Jeju Gothic',
        value: 2
    },
    {
        name: 'Jeju Hallasan',
        value: 3
    },
    {
        name: 'Jeju Myeongjo',
        value: 4
    },
    {
        name: 'Kopub Batang',
        value: 5
    },
    {
        name: 'Nanum Brush Script',
        value: 6
    },
    {
        name: 'Nanum Gothic',
        value: 7
    },
    {
        name: 'Nanum Gothic Coding',
        value: 8
    },
    {
        name: 'Nanum Myeongjo',
        value: 9
    },
    {
        name: 'Nanum Pen Script',
        value: 10
    },
    {
        name: 'Noto Sans KR',
        value: 11
    }
];


/**
 * 기능 패널 초기화
 */
FormatAttr.prototype.init = function() {

    var format = this;

    //그래픽 영역에서 선택 항목 변경 시 이벤트 처리
    this.update = mxUtils.bind(this, function(sender, evt) {
        format.refresh();
    });

    //모든 구성 요소 생성
    this.initElements();

};

/**
 * 화면 새로 그리기
 */
FormatAttr.prototype.refresh = function() {

    var graph = this.graph;
    var preview = this.preview;

    //설정 값 초기화
    //this.setElementsValue(graph);

    //선택된 셀이 있을 때만 활성화 되는 부분
    document.getElementById('fontStyle').style.display = 'none';
    document.getElementById('textAlign').style.display = 'none';
    if (currentCells.length > 0) {
        //폰트 설정
        document.getElementById('fontStyle').style.display = 'block';
        //정렬 옵션
        document.getElementById('textAlign').style.display = 'block';
    }

    this.previewDraw();
};

FormatAttr.prototype.previewDraw = function() {

    var graph = this.graph;
    var preview = this.preview;

    var node = null;
    var enc = new mxCodec(mxUtils.createXmlDocument());
    node = enc.encode(graph.getModel());

    this.setGraphXml(preview, node, currentWidth, currentHeight);
    this.resizeCells(preview);
};

/**
 * 폰트/정렬 설정 초기화
 */
FormatAttr.prototype.initElements = function() {

	var graph = this.graph;
	var preview = this.preview;

	var model = graph.getModel();
    var format = this;

    //초기화 버튼
    addClickHandler(document.getElementById('btnInitAttr'), function() {
        format.open(false);
    });

    //속성 변경 버튼
    addClickHandler(document.getElementById('btnChangeAttr'), function() {
        format.open(true);
    });

    //저장 버튼
    addClickHandler(document.getElementById('btnSaveAttr'), function() {
        format.save();
    });

  //저장 버튼
    addClickHandler(document.getElementById('btnSaveTypeAttr'), function() {
        format.typeSave();
    });

    /**
     * 폰트 종류
     */
    var template = '<div style="font-family:{name}">{name}</div>';
    this.fontFamily = new wijmo.input.ComboBox('#fontFamily', {
        itemsSource: format.defaultFonts,
        displayMemberPath: 'name',
        selectedValuePath: 'value',
        formatItem: function(s, e) {
            //콤보박스 안에 폰트모양 적용
            var html = wijmo.format(template, e.data, function(data, name, fmt, val) {
                return wijmo.isString(data[name]) ? wijmo.escapeHtml(data[name]) : val;
            });
            e.item.innerHTML = html;
        },
        selectedIndexChanged: function(s, e) {
            //콤보 박스 선택한 내용이 변경되었을 때 처리
        	format.getSelectedPreviewCells();
            graph.setCellStyles(mxConstants.STYLE_FONTFAMILY, s.text, graph.getSelectionCells());
            preview.setCellStyles(mxConstants.STYLE_FONTFAMILY, s.text, preview.getSelectionCells());
        }
    });

    //폰트 기본값 설정
    this.fontFamily.text = 'NotoR';

    /**
     * 폰트 색상 설정 시작
     */
    this.fontColor = new wijmo.input.InputColor('#fontColor', {
        placeholder: 'Select the color',
        value: '#000000',
        valueChanged: function(s, e) {
        	format.getSelectedPreviewCells();
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, graph.getSelectionCells());
            preview.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, preview.getSelectionCells());
        }
    });

    /**
     * 폰트 크기 설정
     */
    this.fontSize = new wijmo.input.InputNumber('#fontSize', {
        format: 'n0',
        step: 1,
        min: 1,
        max: 30,
        value: 10,
        valueChanged: function(s, e) {
        	format.getSelectedPreviewCells();
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, graph.getSelectionCells());
            preview.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, preview.getSelectionCells());
        }
    });

    /**
     * 폰트 스타일(굵게, 기울임, 밑줄)
     */
    addClickHandler(document.getElementById('btnBold'), function() {
        graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
        preview.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
    });
    addClickHandler(document.getElementById('btnItalic'), function() {
        graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_ITALIC);
        preview.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_ITALIC);
    });
    addClickHandler(document.getElementById('btnUnderline'), function() {
        graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_UNDERLINE);
        preview.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_UNDERLINE);
    });

    /**
     * 텍스트 위치(왼쪽/중앙/오른쪽, TOP/MIDDLE/BOTTOM)
     */
    addClickHandler(document.getElementById('btnLeft'), function() {
        graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
        preview.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
    });
    addClickHandler(document.getElementById('btnCenter'), function() {
        graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        preview.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
    });
    addClickHandler(document.getElementById('btnRight'), function() {
        graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT);
        preview.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT);
    });

    addClickHandler(document.getElementById('btnTop'), function() {
        graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
        preview.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
    });
    addClickHandler(document.getElementById('btnMiddle'), function() {
        graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        preview.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
    });
    addClickHandler(document.getElementById('btnBottom'), function() {
        graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        preview.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
    });

};

/**
 * 폰트/정렬 설정값 Set
 */
FormatAttr.prototype.setElementsValue = function(graph) {

    var format = this;

    //선택된 셀에서 스타일 정보 읽기
    var initFontSize = 10;
    var initFontFamily;
    var initFontColor;
    var initFontStyle;
    var initAlign;
    var initVAlign;
    var initOpacity;

    for (var i = 0; i < currentCells.length; i++) {
        var cell = currentCells[i];
        var state = graph.view.getState(cell);
        if (state != null) {
            initFontSize = Math.max(0, parseInt(mxUtils.getValue(state.style, mxConstants.STYLE_FONTSIZE, null)));
            initFontFamily = mxUtils.getValue(state.style, mxConstants.STYLE_FONTFAMILY, null);
            initFontColor = mxUtils.getValue(state.style, mxConstants.STYLE_FONTCOLOR, null);
            initFontStyle = mxUtils.getValue(state.style, mxConstants.STYLE_FONTSTYLE, 0);
            initAlign = mxUtils.getValue(state.style, mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
            initVAlign = mxUtils.getValue(state.style, mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
            initOpacity = mxUtils.getValue(state.style, mxConstants.STYLE_OPACITY, 100);
        }
    }

    format.fontFamily.text = initFontFamily;
    format.fontColor.value = initFontColor;
    format.fontSize.value = initFontSize;

    this.setSelected(document.getElementById('btnBold'), 'on', (initFontStyle & mxConstants.FONT_BOLD) == mxConstants.FONT_BOLD);
    this.setSelected(document.getElementById('btnItalic'), 'on', (initFontStyle & mxConstants.FONT_ITALIC) == mxConstants.FONT_ITALIC);
    this.setSelected(document.getElementById('btnUnderline'), 'on', (initFontStyle & mxConstants.FONT_UNDERLINE) == mxConstants.FONT_UNDERLINE);

    this.setSelected(document.getElementById('btnLeft'), 'on', initAlign == mxConstants.ALIGN_LEFT);
    this.setSelected(document.getElementById('btnCenter'), 'on', initAlign == mxConstants.ALIGN_CENTER);
    this.setSelected(document.getElementById('btnRight'), 'on', initAlign == mxConstants.ALIGN_RIGHT);

    this.setSelected(document.getElementById('btnTop'), 'on', initVAlign == mxConstants.ALIGN_TOP);
    this.setSelected(document.getElementById('btnMiddle'), 'on', initVAlign == mxConstants.ALIGN_MIDDLE);
    this.setSelected(document.getElementById('btnBottom'), 'on', initVAlign == mxConstants.ALIGN_BOTTOM);
};

/**
 * 엘리먼트 선택 표시
 */
FormatAttr.prototype.setSelected = function(elt, name, selected) {

    if (selected) {
        var arr = elt.className.split(' ');
        if (arr.indexOf('on') == -1) {
            elt.className += ' ' + name;
        }
    } else {
        elt.className = elt.className.replace(new RegExp(name, 'g'), '').trim();
    }
};

FormatAttr.prototype.getSelectedPreviewCells = function() {

	var graph = this.graph;
	var preview = this.preview;

	var parent = preview.getDefaultParent();
    var model = preview.getModel();
    var childCount = model.getChildCount(parent);
    var cellsTemp = [];

    for (var i = 0; i < currentCells.length; i++) {
        var graphCell = currentCells[i];
        for (var j = 0; j < childCount; j++) {
            var previewCell = model.getChildAt(parent, j);
            if (graphCell.id == previewCell.id) {
            	cellsTemp.push(previewCell);
            }
        }
    }

    preview.setSelectionCells(cellsTemp);
};

/**
 * 기존 구성 조회
 */
FormatAttr.prototype.open = function(isLoad) {

	var graph = this.graph;
	var preview = this.preview;
    var main = this.main;
    var param = "confgSubFg=";

    if (currentCells.length > 1) {
    	currentCell = currentCells[0];
    	$("#btnSaveTypeAttr").hide();
    } else {
    	$("#btnSaveTypeAttr").show();
    }

    if (currentCell != null && currentCell != undefined) {

    	if (currentCell.geometry != null && currentCell.geometry != undefined) {
        	currentWidth = currentCell.geometry.width;
        	currentHeight = currentCell.geometry.height;
        }

		var styleObjArr = getCellStyle(currentCell.style);

		if (styleObjArr[0].tblTypeFg == undefined) {
			currentTblTypeFg = "1";
		} else {
			currentTblTypeFg = styleObjArr[0].tblTypeFg;
		}

		if (currentCell.id != null && currentCell.id != undefined) {
			currentTblNum = currentCell.id;
		}

    	currentBgImg = styleObjArr[3].image;
	}

    if (currentTblNum != undefined && currentTblNum != "") {
    	param = "confgSubFg="+currentTblNum;
    }

    var sid = "";

    if(document.getElementsByName('sessionId')[0]){
    	param += "&sid=" + document.getElementsByName('sessionId')[0].value;
    }

    param += "&tblTypeFg=" + currentTblTypeFg;

    //open
    var reqGroup = mxUtils.post(TABLEATTR_NUM_OPEN_URL, param,
        mxUtils.bind(this, function(req) {
            //var enabled = req.getStatus() != 404;
            if (req.getStatus() == 200) {
                var jsonStr = JSON.parse(req.getText());
                var xmlStr = jsonStr.data;

                if (xmlStr != null) {
                    try {
                        var xml = mxUtils.parseXml(xmlStr);
                        this.setGraphXml(graph, xml.documentElement, graph.container.offsetWidth, graph.container.offsetHeight);
                        this.setGraphXml(preview, xml.documentElement, currentWidth, currentHeight);
                        this.resizeCells(preview);

                        main.initValue();
                        if (!isLoad) {
                            mxUtils.alert(mxResources.get('opened'));
                        }

                    } catch (e) {
                        mxUtils.alert(mxResources.get('invalidOrMissingFile') + ': ' + e.message);
                    }
                }

            } else {
                mxUtils.alert(mxResources.get('errorOpeningFile'));

            }
        })
    );
};

/**
 * Sets the XML node for the current diagram.
 */
FormatAttr.prototype.setGraphXml = function(graph, node, width, height) {

    if (node != null) {
        var dec = new mxCodec(node.ownerDocument);
        if (node.nodeName == 'mxGraphModel') {
            graph.model.beginUpdate();
            try {
                graph.model.clear();
                dec.decode(node, graph.getModel());
            } finally {
                graph.model.endUpdate();

                if (currentBgImg != undefined && currentBgImg != "") {
                	var img = new mxImage(currentBgImg, width, height);
                    graph.setBackgroundImage(img);
                    graph.view.validateBackgroundImage();
                }

            }
        } else {
            throw {
                message: mxResources.get('cannotOpenFile'),
                node: node,
                toString: function() {
                    return this.message;
                }
            };
        }
    } else {
        graph.model.clear();
    }
};

FormatAttr.prototype.resizeCells = function(graph) {

	currentHorizontalRatio = currentWidth / 500;
	currentVerticalRatio = currentHeight / 500;

    //그래픽 영역의 Object를 체크해 사용여부 true 처리
    var parent = graph.getDefaultParent();
    var model = graph.getModel();
    var childCount = model.getChildCount(parent);
    var cellsTemp = [];

    for (var i = 0; i < childCount; i++) {
        var cell = model.getChildAt(parent, i);

        var cellX = cell.geometry.x;
        var cellY = cell.geometry.y;
        var cellW = cell.geometry.width;
        var cellH = cell.geometry.height;

        cell.geometry.x = parseFloat(cellX * currentHorizontalRatio);
        cell.geometry.y = parseFloat(cellY * currentVerticalRatio);
        cell.geometry.width = parseFloat(cellW * currentHorizontalRatio);
        cell.geometry.height = parseFloat(cellH * currentVerticalRatio);

        cellsTemp.push(cell);
    }

    graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, 'none', cellsTemp);
    graph.refresh();

};

/**
 * 화면구성 XML을 서버에 저장(테이블별 저장)
 */
FormatAttr.prototype.save = function() {

	var graph = this.graph;
    var preview = this.preview;

    if (graph.isEditing()) {
        graph.stopEditing();
    }

    if (preview.isEditing()) {
    	preview.stopEditing();
    }

    var nodeGraph = null;
    var nodePreview = null;
    var arrTblCd = [];

    var enc = new mxCodec(mxUtils.createXmlDocument());

    nodeGraph = enc.encode(graph.getModel());
    nodePreview = enc.encode(preview.getModel());

    //저장 될 XML을 보고 싶을 때 사용

    //var xmlPretty = mxUtils.getPrettyXml(node);
    //console.log(xmlPretty);
    //mxLog.show();
    //mxLog.write(xmlPretty);

    var xmlGraph = mxUtils.getXml(nodeGraph);
    var xmlPreview = mxUtils.getXml(nodePreview);

    xmlGraph = encodeURIComponent(xmlGraph);
    xmlPreview = encodeURIComponent(xmlPreview);

    var sid = "";

    if(document.getElementsByName('sessionId')[0]){
    	sid = document.getElementsByName('sessionId')[0].value;
    }

	for (var i = 0; i < currentCells.length; i++) {
		arrTblCd.push(String(currentCells[i].id));
    }

    var params = {};
    params.sid = sid;
    params.xmlGraph = xmlGraph;
    params.xmlPreview = xmlPreview;
    params.confgSubFg = currentTblTypeFg;
    params.arrTblCd = arrTblCd;

    //console.log(params);

    if (xmlGraph.length < MAX_REQUEST_SIZE) {

    	$.ajaxSettings.traditional = true;

    	// ajax 통신 설정
    	$.ajax({
    	    type: "POST",
    	    url : TABLEATTR_NUM_SAVE_URL,
    	    data : params,
            contentType : "application/x-www-form-urlencoded; charset=UTF-8",
    	    success : function(data) {
    	        // Sucess시, 처리
    	    	mxUtils.alert(mxResources.get('saved'));
    	    },
    	    error : function(xhr, textStatus, errorThrown){
    	    	mxUtils.alert(mxResources.get('errorSavingFile'));
    	    }
    	});

    } else {
        mxUtils.alert(mxResources.get('drawingTooLarge'));
    }

};

/**
 * 화면구성 XML을 서버에 저장 (유형별 저장)
 */
FormatAttr.prototype.typeSave = function() {

	var graph = this.graph;
    var preview = this.preview;

    if (graph.isEditing()) {
        graph.stopEditing();
    }

    if (preview.isEditing()) {
    	preview.stopEditing();
    }

    var nodeGraph = null;
    var nodePreview = null;

    var enc = new mxCodec(mxUtils.createXmlDocument());

    nodeGraph = enc.encode(graph.getModel());
    nodePreview = enc.encode(preview.getModel());

    //저장 될 XML을 보고 싶을 때 사용

    //var xmlPretty = mxUtils.getPrettyXml(node);
    //console.log(xmlPretty);
    //mxLog.show();
    //mxLog.write(xmlPretty);

    var xmlGraph = mxUtils.getXml(nodeGraph);
    var xmlPreview = mxUtils.getXml(nodePreview);

    xmlGraph = encodeURIComponent(xmlGraph);
    xmlPreview = encodeURIComponent(xmlPreview);

    var sid = "";

    if(document.getElementsByName('sessionId')[0]){
    	sid = document.getElementsByName('sessionId')[0].value;
    }

    var params = {};
    params.sid = sid;
    params.xmlGraph = xmlGraph;
    params.xmlPreview = xmlPreview;
    params.confgSubFg = currentTblTypeFg;

    if (xmlGraph.length < MAX_REQUEST_SIZE) {

    	// ajax 통신 설정
    	$.ajax({
    	    type: "POST",
    	    url : TABLEATTR_SAVE_URL,
    	    data : params,
    	    success : function(data) {
    	        // Sucess시, 처리
    	    	mxUtils.alert(mxResources.get('saved'));
    	    },
    	    error : function(xhr, textStatus, errorThrown){
    	    	mxUtils.alert(mxResources.get('errorSavingFile'));
    	    }
    	});

    } else {
        mxUtils.alert(mxResources.get('drawingTooLarge'));
    }

};