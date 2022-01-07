//그리드의 크기
mxGraph.prototype.gridSize = 10;
mxGraph.prototype.foldingEnabled = false;
mxGraphView.prototype.gridColor = '#e0e0e0';
var currentCell;
var currentCells = [];
var backgroundColor;
var currentGraph;

//테이블 단건등록 플레그
var singleTableLayer = 'N';

// 지정된 영역에만 구성요소를 넣을 수 있도록 처리
mxGraph.prototype.allowNegativeCoordinates = false;

// 셀 가이드 사용 여부
mxGraphHandler.prototype.guidesEnabled = true;
mxDragSource.guideEnabled = true;


/**
 * 메인 Class
 */
TableLayout = function(themes) {
    mxEventSource.call(this);
    this.container = document.getElementById('contentLayout');

    // 그래프 객체 생성
    this.graph = new GraphLayout(this.container, themes);

    // 왼쪽 구성요소
    this.comp = new SidebarLayout(this.graph);

    // 상단 층설정 영역 생성
    this.floor = new Floor(this.graph);

    // 오른쪽 설정 영역 생성
    this.format = new FormatLayout(this);

    this.init();

};

// Extends mxEditor
mxUtils.extend(TableLayout, mxEventSource);

// 변수 생성
TableLayout.prototype.graph = null;
TableLayout.prototype.comp = null;
TableLayout.prototype.layer = null;
TableLayout.prototype.format = null;

/**
 * 메인 - 초기화
 */
TableLayout.prototype.init = function() {
    // 마우스 오른쪽 클릭 - 컨텍스트 메뉴
    mxEvent.disableContextMenu(this.container);

    // 매장영역에서 이벤트 발생 시 설정영역을 새로 그려주기 위한 이벤트 핸들러
    this.graph.getSelectionModel().addListener(mxEvent.CHANGE, this.format.update);
    this.graph.addListener(mxEvent.EDITING_STARTED, this.format.update);
    this.graph.addListener(mxEvent.EDITING_STOPPED, this.format.update);
    this.graph.getModel().addListener(mxEvent.CHANGE, this.format.update);
    this.graph.addListener(mxEvent.ROOT, this.format.update);

    // 서버의 초기 설정 로드
    var openResult = this.format.open(true);
};
/**
 * 메인 - 기존 설정 opn 시 값 초기화
 */
TableLayout.prototype.initValue = function() {
    // 층 설정 툴바 초기화
    this.floor.refresh();

    // 그래프 변수값 초기화
    this.graph.initValue();

    //레이어 초기화 화면 표시
    var layer = this.graph.getDefaultParent();
    this.floor.visibleLayer(layer);
};

/**
 * DOM element 클릭 이벤트 추가
 */
function addClickHandler(elt, funct) {
    if (funct != null) {
        mxEvent.addListener(elt, 'click', function(evt) {
            funct(evt);
            mxEvent.consume(evt);
        });

        if (document.documentMode != null && document.documentMode >= 9) {
            // Prevents focus
            mxEvent.addListener(elt, 'mousedown', function(evt) {
                evt.preventDefault();
            });
        }
    }
};

/**
 * 왼쪽 매장구성요소 처리
 */
SidebarLayout = function(graph) {
    this.container = document.getElementById('component');
    this.graph = graph;

    this.init();
}

/**
 * 매장구성요소 영역 초기화
 */
SidebarLayout.prototype.init = function() {
    var tableSize = this.graph.defaultTableSize;

    // ts2는 xml과 message 파일에 정의되어야 함
    // tblTypeFg:1:사각,2:원탁,3:포장,4:배달,5:최소
    this.addIcon('ts2;tblTypeFg=1;tblSeatCnt=2;', 'tblTypeFg=1;tblSeatCnt=2;', STENCIL_PATH + '/ts2.png', tableSize.width, tableSize.height);
    this.addIcon('ts4;tblTypeFg=1;tblSeatCnt=4;', 'tblTypeFg=1;tblSeatCnt=4;', STENCIL_PATH + '/ts4.png', tableSize.width, tableSize.height);
};

/**
 * 왼쪽 매장구성요소 아이콘 추가
 */
SidebarLayout.prototype.addIcon = function(type, style, icon, w, h) {
    var graph = this.graph;
    var toolbar = this.toolbar;

    function lpad(s, padLength, padString) {
        s = '' + s;
        while (s.length < padLength)
            s = padString + s;
        return s;
    }

    // Drop 이벤트 처리
    var funct = function(graph, evt, cell) {
        graph.stopEditing(false);

        var parent = graph.getDefaultParent();
        var pt = graph.getPointForEvent(evt);

        var x = pt.x - (w / 2);
        var y = pt.y - (h / 2);

        // 다른 테이블과 겹치는 경우 Drop 금지
        /*var isCollision = graph.checkCollision(new mxPoint(x, y), new mxPoint(x + w, y + h), null);

        if (isCollision) {
            mxEvent.consume(evt);
            return;
        }*/

        // 테이블 이미지 추가
        var image = graph.insertVertex(parent, null, lpad(graph.getModel().getChildCount(parent) + 1, 3, '0'), x, y, w, h, type + "imageAspect=0;fontStyle=1;fontSize=18;");

        graph.setSelectionCells(image);
        graph.refresh();
    }

    // 영역에 이미지를 넣고 DnD 처리
    var template = '<li><a href="#"><img src="{icon}" alt="{alt}" /></a></li>';
    var tag = wijmo.format(template, {
        icon: icon,
        alt: mxResources.get(type)
    });

    var elt = wijmo.createElement(tag);
    // DnD 활성화
    var ds = mxUtils.makeDraggable(elt, graph, funct, elt, -(w / 2), -(h / 2));
    var divCompos = document.getElementById('divCompos');
    divCompos.appendChild(elt);

};

/**
 * 층 설정 영역
 */
Floor = function(graph) {
    this.graph = graph;
    this.init();
    this.initValue();
    this.refresh();
}

Floor.prototype.floorGrid = null;

/**
 * 층 설정 엘리멘트 생성
 */
Floor.prototype.init = function() {

    var floor = this;
    var graph = this.graph;
    var model = graph.getModel();

    addClickHandler(document.getElementById('btnfloorConfig'), function() {
        // 데이터 재생성
        floor.initValue();

        // 레이어 show/hide
        document.getElementById('floorMask').style.display = 'block';
        document.getElementById('floorLayer').style.display = 'table';

        // 레이어를 show 했을 때 refresh를 해줘야 모양이 제대로 표시되었음
        wijmo.grid.FlexGrid.refreshAll();
    });

    // 그룹구분 설정
    // 그룹 등록 시 선택한 값을 레이어에 등록해 두었다가 팝업 재 생성 시 사용
    var tblGrpFg = [{
            id: '1',
            name: mxResources.get('tblGrpFgNormal')
        }, // 일반
        {
            id: '2',
//            name: mxResources.get('tblGrpFgTogo')
            name: mxResources.get('tblGrpFgDelivery')
        } // 배달
//        , // 포장
//        {
//            id: '3',
//            name: mxResources.get('tblGrpFgDelivery')
//        } // 배달
    ];
    var tblGrpFgMap = new wijmo.grid.DataMap(tblGrpFg, 'id', 'name');

    // 그룹(층) 설정 FlexGrid 생성
    this.floorGrid = new wijmo.grid.FlexGrid('#floorGrid', {
        autoGenerateColumns: false,
        selectionMode: 'Row',
        columns: [{
                binding: 'id',
                isReadOnly: true,
                visible: false
            },
            {
                binding: 'name',
                header: mxResources.get('floorName'),
                isRequired: true,
                width: 90
            },
            {
                binding: 'tblGrpFg',
                header: mxResources.get('tblGrpFg'),
                dataMap: tblGrpFgMap,
                width: 70
            },
            {
                binding: 'btnDel',
                header: mxResources.get('del'),
                isReadOnly: true,
                width: 65
            },
            {
                binding: 'layer',
                visible: false
            }
        ],
        formatItem: function(s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 삭제 버튼 생성
                if (item && col.binding == "btnDel" && e.row != 0) {
                    e.cell.innerHTML = '<a href="#" class="btn_grayS2">' + mxResources.get('del') + '</a>';
                }
            }
        }
    });

    var floorGrid = this.floorGrid;

    // RowHeader 없애기
    // floorGrid.rowHeaders.columns.splice(0, 1);

    // 삭제버튼 높이에 맞춰 그리드 크기 조정
    floorGrid.rows.defaultSize = 40;

    // 삭제버튼 이벤트 처리
    floorGrid.hostElement.addEventListener('click', function(e) {
        var target = wijmo.closest(e.target, 'a');
        var ht = floorGrid.hitTest(e);
        if (target instanceof HTMLAnchorElement) {
            if (ht.row != 0) {
                floorGrid.collectionView.remove(floorGrid.rows[ht.row].dataItem);
            }
        }
    });

    // 추가버튼 이벤트
    addClickHandler(document.getElementById('btnAdd'), function() {
        floorGrid.collectionView.newItemCreator = function() {
            return {
                tblGrpFg: '1'
            }
        };
        var newItem = floorGrid.collectionView.addNew();
        floorGrid.collectionView.commitNew();
    });

    // 적용 버튼 추가 이벤트
    addClickHandler(document.getElementById('btnFloorApply'), function() {
        var view = floorGrid.collectionView;
        var failCnt = 0;

        if (graph.isEnabled()) {

            // 그룹명 오류 처리
            for (var i = 0; i < view.items.length; i++) {
                var item = view.items[i];
                var itemName = nvl(item.name, ' ');

                if(itemName.trim() == '')
                {
                    mxUtils.alert(mxResources.get('checkFloorName'));
                    failCnt++;
                    return false;
                }
            }

            graph.model.beginUpdate();
            try {

                // 신규 생성된 레이어 추가
                for (var i = 0; i < view.itemsAdded.length; i++) {
                    var item = view.itemsAdded[i];
                    var layer = graph.addCell(new mxCell(), graph.model.root);
                    layer.value = item.name;
                    graph.setCellStyles('tblGrpFg', item.tblGrpFg, [layer]);
                }

                // 변경된 레이어 반영
                for (var i = 0; i < view.itemsEdited.length; i++) {
                    var item = view.itemsEdited[i];
                    model.setValue(item.layer, item.name);
                    graph.setCellStyles('tblGrpFg', item.tblGrpFg, [item.layer]);
                }

                // 삭제된 레이어 처리
                for (var i = 0; i < view.itemsRemoved.length; i++) {
                    var item = view.itemsRemoved[i];
                    var index = graph.model.root.getIndex(item.layer);
                    graph.removeCells([item.layer], false);

                    // Creates default layer if no layer exists
                    if (graph.model.getChildCount(graph.model.root) == 0) {
                        graph.model.add(graph.model.root, new mxCell());
                        graph.setDefaultParent(null);
                    } else if (index > 0 && index <= graph.model.getChildCount(graph.model.root)) {
                        graph.setDefaultParent(graph.model.getChildAt(graph.model.root, index - 1));
                    } else {
                        graph.setDefaultParent(null);
                    }
                }

            } finally {
                if(failCnt == 0) graph.model.endUpdate();
            }
        }

        if(failCnt == 0) floor.refresh();

        // 적용한 후에는 view에 데이터 clear
        if(failCnt == 0) view.clearChanges();

        // _btnClose 적용
        if(failCnt == 0)
        {
            $("div.floorLayer").hide();
            $("div.tableAddLayer").hide();
            $("div.tblAttrLayer").hide();
        }

    });
};

/**
 * 데이터 생성
 */
Floor.prototype.initValue = function() {

    var floor = this;
    var graph = this.graph;
    var model = graph.getModel();
    var layerCount = model.getChildCount(model.root);

    var floorGrid = this.floorGrid;

    var data = [];
    floorGrid.itemsSource = data;

    for (var i = 0; i < layerCount; i++) {
        (mxUtils.bind(this, function(layer) {
            data.push({
                id: layer.id,
                name: nvl(layer.value, ' ') || mxResources.get('background'),
                tblGrpFg: graph.getCellStyle(layer)['tblGrpFg'] || '1',
                layer: layer // graph Cell 정보 추가, add/remove/rename에 사용!
            });
        }))(model.getChildAt(model.root, i));
    }

    graph.container.style.backgroundColor = '#CCCCCC'

    // 데이터 생성, 변경사항 추적하도록 설정
    floorGrid.itemsSource = new wijmo.collections.CollectionView(data, {
        trackChanges: true
    });

};

/**
 * 화면 새로 그리기 - 층설정이 변경되었을 때 마다
 */
Floor.prototype.refresh = function() {

    var floor = this;
    var graph = this.graph;

    // 층버튼 생성
    var model = graph.getModel();
    var layerCount = model.getChildCount(model.root);

    var template = '<span><a href="#" class="{on}">{name}</a></span>';
    var divFloor = document.getElementById('divLayers');
    divFloor.innerHTML = '';

    // 레이어 수 만큼 버튼 생성
    for (var i = 0; i < layerCount; i++) {

        (mxUtils.bind(this, function(layer) {
            var tag = wijmo.format(template, {
                name: nvl(layer.value, ' '),
                on: (layer == graph.getDefaultParent() ? 'on' : '')
            });
            var elt = wijmo.createElement(tag);

            addClickHandler(elt, function() {

                // 클릭된 레이어 view 처리
                floor.visibleLayer(layer);

                // 툴바 초기화로 refresh
                floor.refresh();
            });

            divFloor.appendChild(elt);

        }))(model.getChildAt(model.root, i));
    }

};

Floor.prototype.visibleLayer = function(layer) {

	// 모든 층을 visible = false로 하고 선택한 층만 true로 변경

	var floor = this;
    var graph = this.graph;

	// 층버튼 생성
    var model = graph.getModel();
    var layerCount = model.getChildCount(model.root);

	// 클릭이벤트 - 모든 레이어 visible false
    for (var layerIdx = 0; layerIdx < layerCount; layerIdx++) {
        (mxUtils.bind(this, function(layerCell) {
            model.setVisible(layerCell, false);
        }))(model.getChildAt(model.root, layerIdx));
    }

    // 선택된 레이어를 기본값으로 설정
    var index = model.root.getIndex(layer);
    if (index > 0 && index <= model.getChildCount(model.root)) {
        graph.setDefaultParent(layer);
    } else {
        graph.setDefaultParent(null);
    }

    // 해당레이어의 style을 가져와서 화면 컨테이너에 적용
    var styles = graph.getCellStyle(layer);

    // 백그라운드컬러
//    graph.container.style.backgroundColor = styles[mxConstants.STYLE_FILLCOLOR];
//    backgroundColor.value = styles[mxConstants.STYLE_FILLCOLOR];
    graph.container.style.backgroundColor = '#CCCCCC'
    backgroundColor.value = '#CCCCCC';

    // 백그라운드이미지
    if (styles[mxConstants.STYLE_IMAGE] != null) {

    	//console.log(styles[mxConstants.STYLE_IMAGE]);

        var img = new mxImage(decodeURIComponent(styles[mxConstants.STYLE_IMAGE]),
            styles[mxConstants.STYLE_IMAGE_WIDTH],
            styles[mxConstants.STYLE_IMAGE_HEIGHT]);
        graph.setBackgroundImage(img);
        graph.view.validateBackgroundImage();
    } else {
        graph.setBackgroundImage();
        graph.view.validateBackgroundImage();
    }

    // 클릭이벤트 - 선택된 레이어 visible true
    model.setVisible(layer, true);
}

/**
 * 그래픽 영역
 */
function GraphLayout(container, themes) {

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
mxUtils.extend(GraphLayout, mxGraph);

GraphLayout.prototype.defaultThemes = {};
GraphLayout.prototype.defaultVertexStyle = {};

GraphLayout.prototype.defaultThemeName = 'tablelayout';

// 그래픽 영역 사이즈
GraphLayout.prototype.minimumGraphSize = new mxRectangle(0, 0, 886, 636);
GraphLayout.prototype.maximumGraphBounds = new mxRectangle(0, 0, 886, 636);

// DnD에서 생성되는 크기
GraphLayout.prototype.defaultTableSize = {
    width: 98,
    height: 50
};

/**
 * 영역 초기화
 */
GraphLayout.prototype.init = function() {

    mxGraph.prototype.init.apply(this, arguments);

    var graph = this;

    // 그래프 영역의 배경 색 설정 - 기본설정
    // graph.setBackgroundImage(new mxImage(this.graphBackgroundImage, 600,
    // 640));

    // 마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
    graph.setPanning(false);

    // Enables HTML labels
    graph.setHtmlLabels(true);

    // 셀을 이동했을 때 스크롤 금지
    graph.graphHandler.scrollOnMove = false;
    graph.autoScroll = false;

    // UNDO, 키 핸들러 추가
    graph.undoManager = graph.createUndoManager(graph);
    var rubberband = new mxRubberband(graph);
    graph.keyHandler = graph.createKeyHandler(graph);


    mxGraphHandler.prototype.isDelayedSelection = function(cell, me) {

        return true;
    };

    mxGraphHandler.prototype.getCells = function(initialCell) {
        if (this.graph.getSelectionCells()) {
            graph.container.focus();
            return this.graph.getMovableCells(this.graph.getSelectionCells());
        }
        return this.graph.getMovableCells([initialCell]);
    };

    mxDragSource.prototype.getDropTarget = function (graph, x, y) {
        var cell = graph.getCellAt(x, y);
        return cell && cell._type == 'phase' ? cell : null;
    };

    var graphHandlermouseUp = mxGraphHandler.prototype.mouseUp;
    graph.graphHandler.mouseUp = function(sender, me) {

    	currentGraph = graph;

    	var tblTypeFgComboBox = wijmo.Control.getControl("#tblTypeFgComboBox");
    	var tblSeatCntComboBox = wijmo.Control.getControl("#tblSeatCntComboBox");

    	//테이블 단건 생성일때는 초기화 금지
    	if(singleTableLayer == "Y"){
			return false;
		}

    	if (me.state != null && me.state != undefined) {

    		var selectedCell = me.state.cell;
        	var cell = selectedCell.geometry;
        	var cellNm = selectedCell.value;
        	var cellStyle = selectedCell.style;

        	currentCell = selectedCell;
        	//console.log(graph.getSelectionCells());
        	currentCells = graph.getSelectionCells();

        	var styleObjArr = getCellStyle(cellStyle);

        	$("#cellX").val(Math.floor(cell.x));
        	$("#cellY").val(Math.floor(cell.y));
        	$("#cellW").val(cell.width);
        	$("#cellH").val(cell.height);
        	$("#tableName").val(cellNm);


        	tblTypeFgComboBox.selectedValue = styleObjArr[0].tblTypeFg;
        	tblSeatCntComboBox.selectedValue = styleObjArr[1].tblSeatCnt;

        	$("#btnTblAttConfig").show();

        	//console.log(styleObjArr);

    	} else {

    		currentCell = null;
    		currentCells = null;

    		$("#cellX").val("");
        	$("#cellY").val("");
        	$("#cellW").val("");
        	$("#cellH").val("");
        	$("#tableName").val("");

        	tblSeatCntComboBox.selectedValue = "0";
        	tblTypeFgComboBox.selectedValue = "1";

        	$("#btnTblAttConfig").hide();
    	}

    	graphHandlermouseUp.apply(this, arguments);
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

    	$("#cellX").val(Math.floor(x1));
    	$("#cellY").val(Math.floor(y1));

        // 다른 테이블과 겹치는 경우 이동 금지
        /*var isCollision = graph.checkCollision(new mxPoint(x1, y1), new mxPoint(x2, y2), cells);
        if (isCollision) {
            mxEvent.consume(evt);
            return;
        }*/

    	var node = null;
        var enc = new mxCodec(mxUtils.createXmlDocument());
        node = enc.encode(graph.getModel());

       /* var xmlPretty = mxUtils.getPrettyXml(node); mxLog.show();
        console.log(xmlPretty);*/

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

    	$("#cellW").val(bounds.width);
    	$("#cellH").val(bounds.height);

        // 다른 테이블과 겹치는 경우 이동 금지
        /*var isCollision = graph.checkCollision(new mxPoint(x1, y1), new mxPoint(x2, y2), [cell]);
        if (isCollision) {
            return;
        }*/

        mxGraphResizeCell.apply(this, arguments);
    };

    /* 이하 라벨 텍스트 선택 및 변경에 사용 */
    // graph.vertexLabelsMovable = false;
    // graph.setCellsMovable(false);
    // console.log(graph);

    // 라벨 삭제 금지 - Del 키는 이벤트 핸들러 때문에 삭제가 됨.
    graph.graphHandler.removeCellsFromParent = false;
    // Autosize labels on insert where autosize=1
    graph.autoSizeCellsOnAdd = true;

    // 라벨일 때만 이동 금지
    graph.isCellMovable = function(cell) {
        if (cell.style == 'label') {
            return false;
        }
        return this.isCellsMovable();
    };

    // Allows moving of relative cells
    graph.isCellLocked = function(cell) {
        return this.isCellsLocked();
    };

    graph.isCellResizable = function(cell) {
        var geo = this.model.getGeometry(cell);
        return geo == null || !geo.relative;
    };

    // Truncates the label to the size of the vertex
    graph.getLabel = function(cell) {
        var label = (this.labelsVisible) ? this.convertValueToString(cell) : '';
        var geometry = this.model.getGeometry(cell);

        if (!this.model.isCollapsed(cell) && geometry != null && (geometry.offset == null ||
                (geometry.offset.x == 0 && geometry.offset.y == 0)) && this.model.isVertex(cell) &&
            geometry.width >= 2) {
            var style = this.getCellStyle(cell);
            var fontSize = style[mxConstants.STYLE_FONTSIZE] || mxConstants.DEFAULT_FONTSIZE;
            var max = geometry.width / (fontSize * 0.625);
            if (max < label.length) {
                return label.substring(0, max) + '...';
            }
        }
        return label;
    };

    graph.isWrapping = function(cell) {
        return this.model.isCollapsed(cell);
    };
    // Enables clipping of vertex labels if no offset is defined
    graph.isLabelClipped = function(cell) {
        var geometry = this.model.getGeometry(cell);
        return geometry != null && !geometry.relative && (geometry.offset == null ||
            (geometry.offset.x == 0 && geometry.offset.y == 0));
    };
    /* 이상 라벨 텍스트 선택 및 변경에 사용 */

    // 그래프가 생성될 때 첫번째 child의 이름 초기값 지정
    var firstChild = graph.getModel().getChildAt(graph.getModel().root, 0);
    firstChild.setValue(mxResources.get('background'));
    // 첫번째 child 속성에 tblGrpFg 기본값(1) 셋팅
    graph.setCellStyles('tblGrpFg', '1', [firstChild]);

};

/**
 * 초기 데이터 조회 시 변수 초기화
 */
GraphLayout.prototype.initValue = function() {
    var graph = this;

    var model = graph.getModel();
    var parent = graph.getDefaultParent();

    $("#tblAddAmount").val(1);
	$("#tblAddWidth").val(100);
	$("#tblAddHeight").val(100);
};

/**
 * Sanitizes the given HTML markup.
 */
GraphLayout.prototype.sanitizeHtml = function(value, editing) {
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
 * 그래프 영역 레이어 활성화 처리
 */
GraphLayout.prototype.switchLayer = function(layer) {
    var graph = this;
    var model = graph.getModel();
    var layerCount = model.getChildCount(model.root);
    // 모든 레이어 visible false
    for (var layerIdx = 0; layerIdx < layerCount; layerIdx++) {
        (mxUtils.bind(this, function(visibleLayer) {
        	//console.log("switchLayer : " + layer);
            model.setVisible(visibleLayer, false);
        }))(model.getChildAt(model.root, layerIdx));
    }

    graph.setDefaultParent(layer);
    // 클릭이벤트 - 선택된 레이어  true
    model.setVisible(layer, true);

};

/**
 * 테이블 간 겹치지 않는지 체크
 */
GraphLayout.prototype.checkCollision = function(pt1, pt2, cells) {

    var graph = this;

    // 마우스 포인터를 그래픽 영역 밖에 놓았을 때 마이너스 위치 0으로 보정
    pt1 = new mxPoint(Math.max(pt1.x, 0), Math.max(pt1.y, 0));
    pt2 = new mxPoint(Math.max(pt2.x, 0), Math.max(pt2.y, 0));

    // 이동범위의 셀 중에 자신이 있는지 체크
    var isMyself = function(cell) {
        var isMyself = false;
        if (cells == null || cell == null) return isMyself;

        for (var i = 0; i < cells.length; i++) {
        	if (cells[i] == cell) {
                isMyself = true;
                break;
            } else {
                if (cell.getParent() != null) {
                    if (cells[i] == cell.getParent()) {
                        isMyself = true;
                        break;
                    }
                }
            }
        }
        return isMyself;
    };

    // 네 모서리 기준 해당 위치에 셀이 있는지 체크
    var cellLT = graph.getCellAt(pt1.x, pt1.y);
    var cellLB = graph.getCellAt(pt1.x, pt2.y);
    var cellRT = graph.getCellAt(pt2.x, pt1.y);
    var cellRB = graph.getCellAt(pt2.x, pt2.y);
    if ((cellLT != null && !isMyself(cellLT)) ||
        (cellLB != null && !isMyself(cellLB)) ||
        (cellRT != null && !isMyself(cellRT)) ||
        (cellRB != null && !isMyself(cellRB))) {
        return true;
    }

    // 큰 테이블로 작은 테이블을 덮는 경우 금지
    var incCells = graph.getCells(pt1.x, pt1.y, pt2.x, pt2.y);
    for (var i = 0; i < incCells.length; i++) {
        if (graph.isSwimlane(incCells[i]) && !isMyself(incCells[i])) {
            return true;
        }
    }

    return false;

};

/**
 * UNDO/REDO 이벤트 생성
 */
GraphLayout.prototype.createUndoManager = function(graph) {

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
GraphLayout.prototype.createKeyHandler = function(graph) {
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

    // Ctrl + z
    keyHandler.bindControlKey(90, function(evt) {
        graph.undoManager.undo()
    });
    // Ctrl + Shift + z
    keyHandler.bindControlShiftKey(90, function(evt) {
        graph.undoManager.redo()
    });

    return keyHandler;
};

/**
 * 오른쪽 설정 기능 패널
 */
function FormatLayout(tablelayout) {

    this.container = document.getElementById('format');

    this.main = tablelayout;
    this.graph = tablelayout.graph;

    this.init();
}

/**
 * 기능 패널 초기화
 */
FormatLayout.prototype.init = function() {

    this.update = mxUtils.bind(this, function(sender, evt) {
        this.refresh();
    });

    // 모든 구성 요소 생성
    this.initElements();
};

/**
 * 화면 새로 그리기
 */
FormatLayout.prototype.refresh = function() {

    var graph = this.graph;

    // 셀을 2개 이상 선택 했을 때 정렬 기능 활성화 & 셀을 1개 이상 선택 했을 때 셀 이미지 변경 기능 활성화
    document.getElementById('tableAlign').style.display = 'none';
    document.getElementById('cellImage').style.display = 'none';

    var cells = graph.getSelectionCells();
    var model = graph.getModel();
    var vertexCnt = 0;
    for (var i = 0; i < cells.length; i++) {
        if (model.isVertex(cells[i])) {
            vertexCnt++;
        }
    }

    if (vertexCnt > 0) {
        // 셀 이미지
        document.getElementById('cellImage').style.display = 'block';

//      $("#btnTblAttConfig").show();
    }

    if (vertexCnt > 1) {
    	// 셀 이미지
        document.getElementById('cellImage').style.display = 'none';
        // 테이블 정렬
        document.getElementById('tableAlign').style.display = 'block';
        // 테이블속성 설정
        $("#btnTblAttConfig").hide();

//        $("#btnTblAttConfig").show();

    }

};

/**
 * 테이블 추가
 */
FormatLayout.prototype.tableAdd = function(style, image, x, y, w, h) {

	var graph = this.graph;
    var toolbar = this.toolbar;

    function lpad(s, padLength, padString) {
        s = '' + s;
        while (s.length < padLength)
            s = padString + s;
        return s;
    }

    graph.stopEditing(false);

    var parent = graph.getDefaultParent();

    style += "shape=image;";
    style += "image=" + image + ";";
    style += "perimeter=rectanglePerimeter;";
    style += "fontStyle=1;";
    style += "fontSize=18;";
    style += "align=center;";
    style += "verticalAlign=middle;";
    style += "imageAspect=0;";


    var image = graph.insertVertex(parent, null, lpad(graph.getModel().getChildCount(parent) + 1, 3, '0'), x, y, w, h, style);

    graph.setSelectionCells(image);
    graph.refresh();
    graph.stopEditing(true);
};


function getTableWhSize(tableCnt) {

	 var tmpCnt;
	   for(var k=0; k<=10; k++){
	//   for(var k=0; k<=100; k++){
	      //5 <= 100
	      if(tableCnt <= (k*(k-1))){
	         tmpCnt = (k*(k-1));
	         break;
	      }
	      else if(tableCnt <= (k*k)){
	         tmpCnt = (k*k);
	         break;
	      }
	   }
	var list = [];
	var cnt = -1;
    for(var i=1; i<=tableCnt; i++){
       var ival = [];
       if(tableCnt % i == 0){
          cnt += 1;
          ival[0] = i;
          ival[1] = (tableCnt / i);
          list[cnt] = ival;
       }
    }
    var pos = -1;
    if(list.length == 1){
       pos = 0;
    }if(list.length>1){
       pos = Math.floor(eval(list.length/2));
    }

    if((list[pos][0] - list[pos][1]) > 1){

    	var list = [];
    	var cnt = -1;
        for(var j=1; j<=tmpCnt; j++){
           var ival = [];

           if(tmpCnt % j == 0){
              cnt += 1;
              ival[0] = j;
              ival[1] = (tmpCnt / j);
              list[cnt] = ival;
           }
        }

        var pos = -1;
        if(list.length == 1){
           pos = 0;
        }if(list.length>1){
           pos = Math.floor(eval(list.length/2));
        }
    }

    return list[pos];


}
/**
 * 폰트/정렬 설정 초기화
 */
FormatLayout.prototype.initElements = function() {

    var graph = this.graph;
    var format = this;
    var graphContainer = this.main.container;

    // 초기화 버튼
    addClickHandler(document.getElementById('btnInitLayout'), function() {
        format.open(false);
    });

    // 초기화 버튼2
    addClickHandler(document.getElementById('btnInitLayout2'), function() {
        format.initLayout(false);
    });

    // 저장 버튼
    addClickHandler(document.getElementById('btnSaveLayout'), function() {
        format.save();
    });

    // 테이블 추가 버튼
    addClickHandler(document.getElementById('btnAddTbl'), function() {

    	var layoutWidth = 891;
    	var layoutHeight = 641;

    	var tableWidthSum = 0;
    	var tableHeightSum = 0;

//    	var horizontalCnt = 0;
//    	var verticalCnt = 0;

    	var tableAmout = $("#tblAddAmount").val();

    	if(tableAmout > 100){
    		$("#tblAddAmount").val("100");
    		tableAmout = 100;
    		mxUtils.alert(mxResources.get('maxTblCnt'));
    	}

    	var list = getTableWhSize(tableAmout);

    	var horizontalCnt = list[0] + 1;
    	var verticalCnt = list[1];

    	var layoutVWidth = 800;
    	var layoutVHeight = 600;

//    	var tableWidth = Math.floor(layoutWidth/list[0]) - (list[0]*20);
//    	var tableHeight = Math.floor(layoutHeight/list[1]) - (list[1]*20);
    	var tableWidth = Math.floor(layoutVWidth/list[0]) ;
    	var tableHeight = Math.floor(layoutVHeight/list[1]) ;


//    	if(tableWidth > 390){
    		//tableWidth = 389;
//    	}

    	var tblTypeFgComboBox = wijmo.Control.getControl("#tblTypeFgComboBox");
    	var tblSeatCntComboBox = wijmo.Control.getControl("#tblSeatCntComboBox");
    	var cellImage = "";
    	var styleTemp = "";

    	switch (tblTypeFgComboBox.selectedValue){
    	    case "1" :
    	    	cellImage = STENCIL_PATH + "/img_squere.png";
    	    	styleTemp = "tblTypeFg=1;tblSeatCnt="+tblSeatCntComboBox.selectedValue+";";
    	        break;
    	    case "2" :
    	    	cellImage = STENCIL_PATH + "/img_circle.png";
    	    	styleTemp = "tblTypeFg=2;tblSeatCnt="+tblSeatCntComboBox.selectedValue+";";
    	        break;
    	    case "3" :
//    	    	cellImage = STENCIL_PATH + "/img_squere.png";
    	    	cellImage = STENCIL_PATH + "/img_etc.png";
    	    	styleTemp = "tblTypeFg=3;tblSeatCnt=0;";
    	        break;
    	    default :
    	    	cellImage = STENCIL_PATH + "/img_squere.png";
    	    	styleTemp = "tblTypeFg=1;tblSeatCnt=2;";
    	}

    	if (tableAmout == undefined || tableAmout == "") {
    		tableAmout = 1;
    	} else {
    		tableAmout = Number(tableAmout);
    	}

    	if (tableWidth == undefined || tableWidth == "") {
    		tableWidth = 100;
    	} else {
    		tableWidth = Number(tableWidth);
    	}

    	if (tableHeight == undefined || tableHeight == "") {
    		tableHeight = 100;
    	} else {
    		tableHeight = Number(tableHeight);
    	}

//    	while ((horizontalCnt * tableWidth) + 20 < layoutHeight) {
//    		while ((horizontalCnt * tableWidth) + 20 < layoutWidth) {
//    		horizontalCnt++;
//    	}

    	horizontalCnt = horizontalCnt -1;
    	// layoutWidth :: 891, horizontalCnt :: 6, tableWidth :: 100

    	var horizontalSpace = -1;
    	if(horizontalCnt > 1){
    		horizontalSpace = (layoutWidth - ((horizontalCnt * tableWidth) + 20)) / (horizontalCnt-1);
    	}else{
    		horizontalSpace = (layoutWidth - tableWidth)/2;
    	}
    	//horizontalSpace = 10;

//    	while ((verticalCnt * tableHeight) + 20 < layoutHeight) {
//    		verticalCnt++;
//    	}

    	var tableCnt = 0;
    	var verticalCntTemp = 0;

    	for (var j = 0; j < verticalCnt; j++) {
    		for (var i = 0; i < horizontalCnt; i++) {

    			tableCnt++;

    			if ( tableCnt <= tableAmout) {
    				verticalCntTemp = j;
    			}
    		}
    	}

    	verticalCnt = verticalCntTemp + 1;

    	var verticalSpace = 0;
    	if(verticalCntTemp != 0 ){
    		verticalSpace = (layoutHeight - (((verticalCnt) * tableHeight) + 20)) / (verticalCnt-1 );
    	}
    	else {
    		verticalSpace = 10;
    	}

    	var tableCnt = 0;

    	for (var j = 0; j < verticalCnt; j++) {
    		for (var i = 0; i < horizontalCnt; i++) {

    			tableCnt++;

    			if ( tableCnt <= tableAmout) {
    				format.tableAdd(
    						styleTemp
    						, cellImage
	    					, 10 + (horizontalSpace * i) + (tableWidth * i)
	    					, 10 + (verticalSpace * j) + (tableHeight * j)
	    					, tableWidth
	    					, tableHeight
					);
    			}
    		}
    	}
    });


 // 테이블 추가 버튼(단건)
    addClickHandler(document.getElementById('contentLayout'), function() {
    	if(singleTableLayer != 'Y'){
    		return false;
    	}
    	var layoutWidth = 891;
    	var layoutHeight = 641;

    	var tableWidthSum = 0;
    	var tableHeightSum = 0;

//    	var horizontalCnt = 0;
//    	var verticalCnt = 0;

    	var tableAmout = $("#tblAddAmount").val();

    	if(tableAmout > 100){
    		$("#tblAddAmount").val("100");
    		tableAmout = 100;
    		mxUtils.alert(mxResources.get('maxTblCnt'));
    	}

    	var list = getTableWhSize(tableAmout);

    	var horizontalCnt = list[0] + 1;
    	var verticalCnt = list[1];

    	var layoutVWidth = 800;
    	var layoutVHeight = 600;

//    	var tableWidth = Math.floor(layoutWidth/list[0]) - (list[0]*20);
//    	var tableHeight = Math.floor(layoutHeight/list[1]) - (list[1]*20);
//    	var tableWidth = Math.floor(layoutVWidth/list[0]) ;
//    	var tableHeight = Math.floor(layoutVHeight/list[1]) ;
    	var tableWidth = $("#cellW").val();
    	var tableHeight = $("#cellH").val();

//    	if(tableWidth > 390){
    		//tableWidth = 389;
//    	}

    	var tblTypeFgComboBox = wijmo.Control.getControl("#tblTypeFgComboBox");
    	var tblSeatCntComboBox = wijmo.Control.getControl("#tblSeatCntComboBox");

    	var cellImage = "";
    	var styleTemp = "";

    	switch (tblTypeFgComboBox.selectedValue){
    	    case "1" :
    	    	cellImage = STENCIL_PATH + "/img_squere.png";
    	    	styleTemp = "tblTypeFg=1;tblSeatCnt="+tblSeatCntComboBox.selectedValue+";";
    	        break;
    	    case "2" :
    	    	cellImage = STENCIL_PATH + "/img_circle.png";
    	    	styleTemp = "tblTypeFg=2;tblSeatCnt="+tblSeatCntComboBox.selectedValue+";";
    	        break;
    	    case "3" :
//    	    	cellImage = STENCIL_PATH + "/img_squere.png";
    	    	cellImage = STENCIL_PATH + "/img_etc.png";
    	    	styleTemp = "tblTypeFg=3;tblSeatCnt=0;";
    	        break;
    	    default :
    	    	cellImage = STENCIL_PATH + "/img_squere.png";
    	    	styleTemp = "tblTypeFg=1;tblSeatCnt=2;";
    	}

    	if (tableAmout == undefined || tableAmout == "") {
    		tableAmout = 1;
    	} else {
    		tableAmout = Number(tableAmout);
    	}

    	if (tableWidth == undefined || tableWidth == "") {
    		tableWidth = 100;
    	} else {
    		tableWidth = Number(tableWidth);
    	}

    	if (tableHeight == undefined || tableHeight == "") {
    		tableHeight = 100;
    	} else {
    		tableHeight = Number(tableHeight);
    	}

//    	while ((horizontalCnt * tableWidth) + 20 < layoutHeight) {
//    		while ((horizontalCnt * tableWidth) + 20 < layoutWidth) {
//    		horizontalCnt++;
//    	}

    	horizontalCnt = horizontalCnt -1;
    	// layoutWidth :: 891, horizontalCnt :: 6, tableWidth :: 100

    	var horizontalSpace = -1;
    	if(horizontalCnt > 1){
    		horizontalSpace = (layoutWidth - ((horizontalCnt * tableWidth) + 20)) / (horizontalCnt-1);
    	}else{
    		horizontalSpace = (layoutWidth - tableWidth)/2;
    	}
    	//horizontalSpace = 10;

//    	while ((verticalCnt * tableHeight) + 20 < layoutHeight) {
//    		verticalCnt++;
//    	}

    	var tableCnt = 0;
    	var verticalCntTemp = 0;

    	for (var j = 0; j < verticalCnt; j++) {
    		for (var i = 0; i < horizontalCnt; i++) {

    			tableCnt++;

    			if ( tableCnt <= tableAmout) {
    				verticalCntTemp = j;
    			}
    		}
    	}

    	verticalCnt = verticalCntTemp + 1;

    	var verticalSpace = 0;
    	if(verticalCntTemp != 0 ){
    		verticalSpace = (layoutHeight - (((verticalCnt) * tableHeight) + 20)) / (verticalCnt-1 );
    	}
    	else {
    		verticalSpace = 10;
    	}

    	var tableCnt = 0;
    	for (var j = 0; j < verticalCnt; j++) {
    		for (var i = 0; i < horizontalCnt; i++) {

    			tableCnt++;

    			if ( tableCnt <= tableAmout) {
    				format.tableAdd(
    						styleTemp
    						, cellImage
	    					, event.clientX - 240
	    					, event.clientY - 255
	    					, tableWidth
	    					, tableHeight
					);
    			}
    		}
    	}
    });

    //위치 X 수정
    mxEvent.addListener(document.getElementById('cellX'), 'keyup', function(e) {
    	if (currentCell != null && currentCell != undefined) {
    		if (e.target.value != undefined && e.target.value != "") {
    			currentCell.geometry.x = parseFloat(e.target.value);
        		graph.refresh();
    		}
    	}
    });

    //위치 Y 수정
    mxEvent.addListener(document.getElementById('cellY'), 'keyup', function(e) {
    	if (currentCell != null && currentCell != undefined) {
    		if (e.target.value != undefined && e.target.value != "") {
    			currentCell.geometry.y = parseFloat(e.target.value);
        		graph.refresh();
    		}
    	}
    });

    //크기 W 수정
    mxEvent.addListener(document.getElementById('cellW'), 'keyup', function(e) {
    	if (currentCell != null && currentCell != undefined) {
    		if (e.target.value != undefined && e.target.value != "") {
    			currentCell.geometry.width = parseFloat(e.target.value);
        		graph.refresh();
    		}
    	}
    });

    //크기 H 수정
    mxEvent.addListener(document.getElementById('cellH'), 'keyup', function(e) {
    	if (currentCell != null && currentCell != undefined) {
    		if (e.target.value != undefined && e.target.value != "") {
    			currentCell.geometry.height = parseFloat(e.target.value);
        		graph.refresh();
    		}
    	}
    });

    //테이블명 수정
    mxEvent.addListener(document.getElementById('tableName'), 'keyup', function(e) {
    	if (currentCell != null && currentCell != undefined) {
    		if (e.target.value != undefined && e.target.value != "") {
    			currentCell.value = e.target.value;
        		graph.refresh();
        		//graph.container.focus();
    		}
    	}
    });

    // 배경색 wijmo 컴포넌트 생성
    backgroundColor = new wijmo.input.InputColor('#bgColor', {
        value: graphContainer.style.backgroundColor,
        valueChanged: function(s, e) {
            var layer = graph.getDefaultParent();
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, [layer]);
            graphContainer.style.backgroundColor = s.value;
        }
    });


    // 셀 이미지 버튼
    var tblTypeFgCell = mxUtils.bind(this, function(evt) {
    	var cells = graph.getSelectionCells();
    	var model = graph.getModel();

		for (var i = 0; i < cells.length; i++) {
            if (model.isVertex(cells[i])) {
            	var tblTypeFgComboBox = wijmo.Control.getControl("#tblTypeFgComboBox");
            	var cellImage = "";
            	var styleTemp = "";
            	switch (tblTypeFgComboBox.selectedValue){
            	    case "1" :
            	    	cellImage = STENCIL_PATH + "/img_squere.png";
            	        break;
            	    case "2" :
            	    	cellImage = STENCIL_PATH + "/img_circle.png";
            	        break;
            	    case "3" :
            	    	cellImage = STENCIL_PATH + "/img_etc.png";
            	        break;
            	    default :
            	    	cellImage = STENCIL_PATH + "/img_squere.png";
            	}
                graph.setCellStyles(mxConstants.STYLE_IMAGE, cellImage, [cells[i]]);
                graph.refresh();
            }
        }

    });

    mxEvent.addListener(document.getElementById('tblTypeFgComboBox'), 'change', tblTypeFgCell);

    // 배경 이미지 버튼
    var apply = mxUtils.bind(this, function(evt) {
        var canvas = graph.view.canvas;
        if (canvas.ownerSVGElement != null) {
            canvas = canvas.ownerSVGElement;
        }
        var readURL = function(input) {
            if (input.files && input.files[0]) {

            	if (fileCheck(input.files[0])) {
            		var reader = new FileReader();
                    reader.onload = function(e) {
                        var img = new mxImage(e.target.result, graph.container.offsetWidth, graph.container.offsetHeight);
                        graph.setBackgroundImage(img);
                        graph.view.validateBackgroundImage();

                        // 해당 레이어에 이미지 정보 저장
                        var layer = graph.getDefaultParent();
                        graph.setCellStyles(mxConstants.STYLE_IMAGE, encodeURIComponent(img.src), [layer]);
                        graph.setCellStyles(mxConstants.STYLE_IMAGE_WIDTH, img.width, [layer]);
                        graph.setCellStyles(mxConstants.STYLE_IMAGE_HEIGHT, img.height, [layer]);
                    }
                    reader.readAsDataURL(input.files[0]);
            	} else {
            		input.files[0] = null;
            	}
            }
        }
        readURL(evt.target);

    });

    mxEvent.addListener(document.getElementById('btnBgFile'), 'change', apply);

    // 셀 이미지 버튼
    var applyCell = mxUtils.bind(this, function(evt) {

    	var cells = graph.getSelectionCells();
    	var model = graph.getModel();
    	var params = {};

        var readURL = function(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {

                	var file = $("#btnCellBgFile")[0].files[0];

            	    if (fileCheck(file)) {

            	    	// ajax로 전달할 폼 객체
            	        var formData = new FormData();
            	        // 폼 객체에 파일추가, append("변수명", 값)
            	        formData.append("file", file);

            	        if(document.getElementsByName('sessionId')[0]){
            	        	formData.append("sid", document.getElementsByName('sessionId')[0].value);
                	    }

            	        $.ajax({
            	            type: "POST",
            	            url: TABLELAYOUT_UPLOADIMAGE_URL,
            	            data: formData,
            	            dataType: "text",
            	            processData: false,
            	            contentType: false,
            	            success: function(data){

            	            	for (var i = 0; i < cells.length; i++) {
                                    if (model.isVertex(cells[i])) {
                                    	var storeCd = $("#tableSelectStoreCd").val();
                    	            	var imagePath = IMAGE_PATH + "/" + storeCd + "/" + data;
                    	            	//console.log(imagePath);
                    	                graph.setCellStyles(mxConstants.STYLE_IMAGE, imagePath, [cells[i]]);
                    	                graph.refresh();
                                    }
                                }
            	            }
            	        });


            	    }
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
        readURL(evt.target);
    });

    mxEvent.addListener(document.getElementById('btnCellBgFile'), 'change', applyCell);

    // 배경 이미지 삭제 버튼
    addClickHandler(document.getElementById('btnDelBgImg'), function() {

    	var agent = navigator.userAgent.toLowerCase();
    	if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
    		// ie 일때
    		$("#btnBgFile").replaceWith( $("#btnBgFile").clone(true) );
    	} else {
    		// other browser 일때
    		$("#btnBgFile").val("");
    	}

        graph.setBackgroundImage();
        graph.view.validateBackgroundImage();
        // 해당 레이어에 이미지 정보 저장
        var layer = graph.getDefaultParent();
        graph.setCellStyles(mxConstants.STYLE_IMAGE, '', [layer]);
        graph.refresh();
    });

    // 셀 이미지 삭제 버튼
    addClickHandler(document.getElementById('btnDelCellImg'), function() {

    	var cells = graph.getSelectionCells();
    	var model = graph.getModel();
    	var params = {};
    	var selectedCell;

    	for (var i = 0; i < cells.length; i++) {
            if (model.isVertex(cells[i])) {
            	selectedCell = cells[i];
            	graph.setCellStyles(mxConstants.STYLE_IMAGE, "", [selectedCell]);
                graph.refresh();
            }
        }
    });

    // 테이블 정렬 옵션(왼쪽/가운데/오른쪽, 상단/중단/하단)
    addClickHandler(document.getElementById('btnLeft'), function() {
        graph.alignCells(mxConstants.ALIGN_LEFT);
    });
    addClickHandler(document.getElementById('btnCenter'), function() {
        graph.alignCells(mxConstants.ALIGN_CENTER);
    });
    addClickHandler(document.getElementById('btnRight'), function() {
        graph.alignCells(mxConstants.ALIGN_RIGHT);
    });

    addClickHandler(document.getElementById('btnTop'), function() {
        graph.alignCells(mxConstants.ALIGN_TOP);
    });
    addClickHandler(document.getElementById('btnMiddle'), function() {
        graph.alignCells(mxConstants.ALIGN_MIDDLE);
    });
    addClickHandler(document.getElementById('btnBottom'), function() {
        graph.alignCells(mxConstants.ALIGN_BOTTOM);
    });

};

/**
 * 기존 구성 조회
 */
FormatLayout.prototype.open = function(isLoad) {
    var graph = this.graph;
    var main = this.main;
    // open

    var sid = "";

    if(document.getElementsByName('sessionId')[0]){
    	sid = document.getElementsByName('sessionId')[0].value;
    }

    var reqGroup = mxUtils.post(TABLELAYOUT_OPEN_URL, 'sid=' + sid,
        mxUtils.bind(this, function(req) {
            // var enabled = req.getStatus() != 404;
            if (req.getStatus() == 200) {
                var jsonStr = JSON.parse(req.getText());
                var xmlStr = jsonStr.data;
                if (xmlStr != null) {
                    try {
                        var xml = mxUtils.parseXml(xmlStr);
                        this.setGraphXml(graph, xml.documentElement);
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
 * 최초 구성으로 초기화
*/
FormatLayout.prototype.initLayout = function(isLoad) {
    var params = {};

    var sid = "";

    var floor = this;
    var graph = this.graph;
    var main = this.main;


    if(document.getElementsByName('sessionId')[0]){
        sid = document.getElementsByName('sessionId')[0].value;
    }

    if (confirm("최초 매장생성시 구성으로 돌아갑니다. 초기화 하시겠습니까?")) {
        $.ajax({
            type: "POST",
            url: "/base/store/tableLayout/tableNewLayout/initLayout.sb?sid=" + sid,
            data: params,
            dataType: "json",
            processData: false,
            contentType: false,
            cache: false,
            success: function(result){
                if (result.status === "OK") {
                    alert("초기화 되었습니다.");

                    location.reload(true);
                } else {
                    alert("최초 구성으로 초기화하는데 실패하였습니다.");
                }
            }
        });

    }
}
/**
 * Sets the XML node for the current diagram.
 */
FormatLayout.prototype.setGraphXml = function(graph, node) {
	// 미리보기 라이브러리 수정으로 인한 플래그값 추가 (20201209)
	if(previewYN == 'N'){
		previewYN = 'Y';
	}

    if (node != null) {
        var dec = new mxCodec(node.ownerDocument);
        if (node.nodeName == 'mxGraphModel') {
            graph.model.beginUpdate();
            try {
                graph.model.clear();
                dec.decode(node, graph.getModel());
            } finally {
                graph.model.endUpdate();
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
/**
 * 화면구성 XML을 서버에 저장
 */
FormatLayout.prototype.save = function() {

	var graph = this.graph;

	if ($("#btnBgFile")[0].files && $("#btnBgFile")[0].files[0]) {

    	var file = $("#btnBgFile")[0].files[0];
	    if (fileCheck(file)) {

	    	// ajax로 전달할 폼 객체
	        var formData = new FormData();
	        // 폼 객체에 파일추가, append("변수명", 값)
	        formData.append("file", file);

	        if(document.getElementsByName('sessionId')[0]){
	        	formData.append("sid", document.getElementsByName('sessionId')[0].value);
    	    }

	        $.ajax({
	            type: "POST",
	            url: TABLELAYOUT_UPLOADIMAGE_URL,
	            data: formData,
	            dataType: "text",
	            processData: false,
	            contentType: false,
	            success: function(data){
	                saveXml(data, graph);
	            }
	        });

	    } else {
	    	saveXml("", graph);
	    }

    } else {
    	saveXml("", graph);
    }

};

function saveXml(data, graph) {

    if (graph.isEditing()) {
        graph.stopEditing();
    }
    graph.stopEditing(false);
    var layer = graph.getDefaultParent();

	if (data != "") {
		var storeCd = $("#tableSelectStoreCd").val();
		var imagePath = IMAGE_PATH + "/" + storeCd + "/" + data;
        graph.setCellStyles('bgImg', imagePath, [layer]);
	    // 첫번째 child 속성에 bgImg 배경이미지 파일명 셋팅
	} else {
        graph.setCellStyles('bgImg', '', [layer]);
	}

    var node = null;
    var enc = new mxCodec(mxUtils.createXmlDocument());
    node = enc.encode(graph.getModel());

    var sid = "";

    if(document.getElementsByName('sessionId')[0]){
    	sid = document.getElementsByName('sessionId')[0].value;
    }

    /*var xmlPretty = mxUtils.getPrettyXml(node); mxLog.show();
    console.log(xmlPretty);*/
    //저장 될 XML을 보고 싶을 때 사용

//    var xmlPretty = mxUtils.getPrettyXml(node);
//    mxLog.show();
//    mxLog.write(xmlPretty);

    var xml = encodeURIComponent(mxUtils.getXml(node));

    var params = {};
    params.sid = sid;
    params.xml = xml;

    //console.log(params);

    if (xml.length < MAX_REQUEST_SIZE) {

    	$.ajaxSettings.traditional = true;

    	// ajax 통신 설정
    	$.ajax({
    	    type: "POST",
    	    url : TABLELAYOUT_SAVE_URL,
    	    data : params,
            contentType : "application/x-www-form-urlencoded; charset=UTF-8",
    	    success : function(data) {
    	    	if(data.status == 'TBLNM_FAIL'){
    	    		// TBLNM_FAIL시
    	    		mxUtils.alert(mxResources.get('invaildTblNm'));
    	    	}else {
        	        // Sucess시, 처리
        	    	mxUtils.alert(mxResources.get('saved'));
    	    	}
    	    	graph.container.focus();
    	    },
    	    error : function(xhr, textStatus, errorThrown){
    	    	mxUtils.alert(mxResources.get('errorSavingFile'));
    	    }
    	});

    } else {
        mxUtils.alert(mxResources.get('drawingTooLarge'));
    }


    /*

    try {
        if (xml.length < MAX_REQUEST_SIZE) {
            var onload = function(req) {
            	alert("req::"+JSON.stringify(req))
                mxUtils.alert(mxResources.get('saved'));
                graph.container.focus();
            }
            var onerror = function(req) {
                mxUtils.alert('Error');
            }

            new mxXmlRequest(TABLELAYOUT_SAVE_URL, 'sid=' + sid + '&xml=' + xml).send(onload, onerror);
        } else {
            mxUtils.alert(mxResources.get('drawingTooLarge'));
            return;
        }
    } catch (e) {
    	console.log(e);
        mxUtils.alert(mxResources.get('errorSavingFile'));
    }

    */

}

function getCellStyle(cellStyle) {

	var styleObjArr = new Array;

	if (cellStyle != null && cellStyle != undefined) {

		var styleArr = cellStyle.split(";");

		styleArr.forEach(function (str) {
		    var strArr = str.split("=");
		    if (strArr[0] != "") {
		    	var styleObj = new Object();
	    	    styleObj[strArr[0]] = strArr[1];
	    	    styleObjArr.push(styleObj);
		    }
		});

	}

	return styleObjArr;
}

function setCellStyle(cellStyle, key, value) {

	var styleObjArr = new Array;
	var styleArr = cellStyle.split(";");
	var style = "";

	for (var styleValue in styleArr) {
		var valueTemp = String(styleArr[styleValue]);
		var styleObj = valueTemp.split("=");
		for (var prop in styleObj) {
	    	if (styleObj[0] == key) {
	    		styleObj[1] = value;
	    	}
	    }

		if (styleObj[0] != undefined && styleObj[0] != "" && String(styleObj[0]).substr(0, 8) != "function") {
			if (styleObj[1] != undefined && styleObj[1] != "") {
				style += styleObj[0] + "=" + styleObj[1] + ";";
			}
		}
	}

	return style;
}

/* 업로드 체크 */
function fileCheck(file) {
	// 사이즈체크
	var maxSize = 5 * 1024 * 1024    //30MB
	var fileSize = file.size;

	if(fileSize > maxSize) {
		alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다.    ");
		return false;
	}

	return true;
}

/**
 * 로딩 시점에 초기화
 *
 * @returns
 */
(function() {
    // Sets colors for handles
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