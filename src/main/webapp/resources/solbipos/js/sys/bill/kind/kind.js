/****************************************************************
 *
 * 파일명 : kind.js
 * 설  명 : 출력물종류 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.10     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function() {

    // 조회버튼 클릭 
    $("#btnSearch").click(function(e){
        search();
    });

    // 출력물종류 그리드 
    var dataPrint =
        [
            { binding:"gChk", header:messages["kind.chk"], dataType:wijmo.DataType.Boolean, width:50},
            { binding:"prtClassCd", header:messages["kind.prtClassCd"], width:70, isReadOnly:true},
            { binding:"prtClassNm", header:messages["kind.prtClassNm"], width:"*"},
            { binding:"general", header:messages["kind.general"], dataType:wijmo.DataType.Boolean
                , width:50},
            { binding:"food", header:messages["kind.food"], dataType:wijmo.DataType.Boolean, width:50}
        ];
    // 출력물종류 그리드 생성 
    var gridPrint = wgrid.genGrid("#gridPrint", dataPrint);
    gridPrint.isReadOnly = false;

    // 출력물종류 그리드 포맷 
    gridPrint.formatItem.addHandler(function(s, e) {
        if (e.panel == s.cells) {
            var col = s.columns[e.col];
            if( col.binding == "prtClassCd" ) {
                wijmo.addClass(e.cell, 'wijLink');
            }
        }
    });

    // 출력물종류 그리드 클릭 이벤트 
    gridPrint.addEventListener(gridPrint.hostElement, 'click', function(e) {
        var ht = gridPrint.hitTest(e);
        if ( ht.cellType == wijmo.grid.CellType.Cell ) {
            var selectedRow = gridPrint.rows[ht.row].dataItem;
            if ( selectedRow.status != "I" ) {
                var col = ht.panel.columns[ht.col];
                if ( col.binding == "prtClassCd" ) {
                    searchMapng(selectedRow.prtClassCd);
                }
            }
        }
    });

    // 출력물종류 코드목록 조회 
    function search() {

        var param = {};

        $.postJSON("/sys/bill/kind/bill/list.sb", param,
            function(result) {
                if(result.status === "FAIL") {
                    s_alert.pop(result.message);
                    return;
                }

                var list = result.data.list;
                gridPrint.itemsSource = new wijmo.collections.CollectionView(list);
                gridPrint.itemsSource.trackChanges = true;

                // 버튼 Show 
                $("#btnAddPrint").show();
                $("#btnDelPrint").show();
                $("#btnSavePrint").show();

                if ( list.length === undefined || list.length == 0 ) {
                    s_alert.pop(result.message);
                    return;
                } else {

                    param = {};
                    param.prtClassCd = list[0].prtClassCd;
                    $("#prtClassCd").val(list[0].prtClassCd);

                    $.postJSON("/sys/bill/kind/mapng/list.sb", param,
                        function(result) {
                            if(result.status === "FAIL") {
                                s_alert.pop(result.message);
                                return;
                            }

                            var list = result.data.list;
                            gridMapng.itemsSource = new wijmo.collections.CollectionView(list);
                            gridMapng.itemsSource.trackChanges = true;

                            // 버튼 Show 
                            $("#btnUpMapng").show();
                            $("#btnDownMapng").show();
                            $("#btnAddMapng").show();
                            $("#btnDelMapng").show();
                            $("#btnSaveMapng").show();

                            if ( list.length === undefined || list.length == 0 ) {
                                // 그리드 초기화 
                                gridMapng.itemsSource = [];
                            }

                        },
                        function(){
                            s_alert.pop("Ajax Fail");
                        }
                    );
                }

            },
            function(){
                s_alert.pop("Ajax Fail");
            }
        );

    };

    // 출력물종류 추가 버튼 클릭 
    $("#btnAddPrint").click(function(e) {
        gridPrint.collectionView.trackChanges = true;
        var newRow = gridPrint.collectionView.addNew();
        newRow.status = "I";
        newRow.general = '0';
        newRow.food = '0';
        newRow.gChk = true;

        gridPrint.collectionView.commitNew();
        // 추가된 Row 선택
        gridPrint.select(gridPrint.rows.length, 1);
    });

    // 출력물종류 저장 버튼 클릭 
    $("#btnSavePrint").click(function(e) {

        var paramArr = new Array();

        for ( var i = 0; i < gridPrint.collectionView.itemsEdited.length; i++ ) {
            gridPrint.collectionView.itemsEdited[i].status = "U";
            paramArr.push(gridPrint.collectionView.itemsEdited[i]);
        }
        for ( var i = 0; i < gridPrint.collectionView.itemsAdded.length; i++ ) {
            gridPrint.collectionView.itemsAdded[i].status = "I";
            paramArr.push(gridPrint.collectionView.itemsAdded[i]);
        }
        for ( var i = 0; i < gridPrint.collectionView.itemsRemoved.length; i++ ) {
            gridPrint.collectionView.itemsRemoved[i].status = "D";
            paramArr.push(gridPrint.collectionView.itemsRemoved[i]);
        }

        if ( paramArr.length <= 0 ) {
            s_alert.pop(messages["cmm.not.modify"]);
            return;
        }

        $.postJSONArray("/sys/bill/kind/bill/save.sb", paramArr, function(result) {
                s_alert.pop(messages["cmm.saveSucc"]);
                gridPrint.collectionView.clearChanges();
            },
            function(result) {
                s_alert.pop(result.message);
            }
        );

    });

    // 출력물매핑 그리드 
    var dataMapng =
        [
            {"binding":"gChk", header:messages["kind.chk"], dataType:wijmo.DataType.Boolean, width:50},
            {"binding":"prtCd", header:messages["kind.prtCd"], width:"*"},
        ];
    // 출력물매핑 그리드 생성 
    var gridMapng = wgrid.genGrid("#gridMapng", dataMapng);
    gridMapng.isReadOnly = false;

    // 출력물매핑 목록 조회 
    function searchMapng(value) {

        $("#prtClassCd").val(value);
        var param = {};
        param.prtClassCd = value;

        $.postJSON("/sys/bill/kind/mapng/list.sb", param,
            function(result) {
                if(result.status === "FAIL") {
                    s_alert.pop(result.message);
                    return;
                }

                var list = result.data.list;
                gridMapng.itemsSource = new wijmo.collections.CollectionView(list);
                gridMapng.itemsSource.trackChanges = true;

                // 버튼 Show 
                $("#btnUpMapng").show();
                $("#btnDownMapng").show();
                $("#btnAddMapng").show();
                $("#btnDelMapng").show();
                $("#btnSaveMapng").show();

                if ( list.length === undefined || list.length == 0 ) {
                    // 그리드 초기화 
                    gridMapng.itemsSource = [];
                }

            },
            function(){
                s_alert.pop("Ajax Fail");
            }
        );

    };

    // 출력물매핑 UP 버튼 클릭 
    $("#btnUpMapng").click(function(e) {
        var movedRows = 0;
        for ( var i = 0; i < gridMapng.collectionView.itemCount; i++ ) {
            var item = gridMapng.collectionView.items[i];
            if ( i > 0 && item.chk ) {
                if ( !gridMapng.collectionView.items[i-1].chk ) {
                    movedRows = i-1;
                    var tmpItem = gridMapng.collectionView.items[movedRows];
                    gridMapng.collectionView.items[movedRows] = gridMapng.collectionView.items[i];
                    gridMapng.collectionView.items[i] = tmpItem;
                    gridMapng.collectionView.commitEdit();
                    gridMapng.collectionView.refresh();
                }
            }
        }
        gridMapng.select(movedRows, 1);
    });

    // 출력물매핑 DOWN 버튼 클릭 
    $("#btnDownMapng").click(function(e){
        var movedRows = 0;
        for ( var i = gridMapng.itemsSource.itemCount-1; i >= 0; i-- ) {
            var item = gridMapng.collectionView.items[i];
            if ( ( i < gridMapng.itemsSource.itemCount-1 ) && item.chk ) {
                if ( !gridMapng.collectionView.items[i+1].chk ) {
                    movedRows = i+1;
                    var tmpItem = gridMapng.collectionView.items[movedRows];
                    gridMapng.collectionView.items[movedRows] = gridMapng.collectionView.items[i];
                    gridMapng.collectionView.items[i] = tmpItem;
                    gridMapng.collectionView.commitEdit();
                    gridMapng.collectionView.refresh();
                }
            }
        }
        gridMapng.select(movedRows, 1);
    });

    // 출력물매핑 추가 버튼 클릭 
    $("#btnAddMapng").click(function(e) {
        var selectedRow = gridPrint.selectedRows[0]._data;
        showItemLayer(selectedRow.prtClassCd);
    });

    // 출력물매핑 저장 버튼 클릭 
    $("#btnSaveMapng").click(function(e) {

        gridMapng.collectionView.trackChanges = true;

        // dispSeq 재설정 
        for ( var i = 0; i < gridMapng.collectionView.itemCount; i++ ) {
            gridMapng.collectionView.editItem(gridMapng.collectionView.items[i]);
            gridMapng.collectionView.items[i].prtClassCd = $("#prtClassCd").val();
            gridMapng.collectionView.items[i].dispSeq = ( i + 1 );
            gridMapng.collectionView.commitEdit();
        }

        var paramArr = new Array();

        for ( var i = 0; i < gridMapng.collectionView.itemsEdited.length; i++ ) {
            gridMapng.collectionView.itemsEdited[i].status = "U";
            paramArr.push(gridMapng.collectionView.itemsEdited[i]);
        }
        for ( var i = 0; i < gridMapng.collectionView.itemsAdded.length; i++ ) {
            gridMapng.collectionView.itemsAdded[i].status = "I";
            paramArr.push(gridMapng.collectionView.itemsAdded[i]);
        }

        if ( paramArr.length <= 0 ) {
            s_alert.pop(messages["cmm.not.modify"]);
            return;
        }

        $.postJSONArray("/sys/bill/kind/mapng/save.sb", paramArr, function(result) {
                s_alert.pop(messages["cmm.saveSucc"]);
                gridMapng.collectionView.clearChanges();
            },
            function(result) {
                s_alert.pop(result.message);
            }
        );

    });

    // 출력물코드구성 선택 레이어 
        // 레이어 보이기 
    function showItemLayer(value) {

        $("#prtClassCd").val(value);

        $("#itemSelTent, #itemSelLayer").show();
        // 자동조회 
        setTimeout(function() {
            var param = {};

            $.postJSON("/sys/bill/item/item/list.sb", param,
                function(result) {
                    if(result.status === "FAIL") {
                        s_alert.pop(result.message);
                        return;
                    }

                    var list = result.data.list;
                    theGrid.itemsSource = new wijmo.collections.CollectionView(list);
                    theGrid.itemsSource.trackChanges = true;

                    if ( list.length === undefined || list.length == 0 ) {
                        // 그리드 초기화 
                        theGrid.itemsSource = [];
                        s_alert.pop(result.message);
                        return;
                    }

                },
                function(){
                    s_alert.pop("Ajax Fail");
                }
            );
        }, 50);

    }
        // 레이어 감추기 
    $(".itemSelClose").click(function(e) {
        $("#itemSelTent, #itemSelLayer").hide();
    });

    // 출력코드구성 그리드 
    var data =
        [
            { binding:"gChk", header:messages["item.chk"], dataType:wijmo.DataType.Boolean, width:40},
            { binding:"prtCd", header:messages["item.prtCd"], width:100, isReadOnly: true },
            { binding:"prtNm", header:messages["item.prtNm"], width:100, isReadOnly: true },
            { binding:"samplYn", header:messages["item.samplYn"], dataType:wijmo.DataType.Boolean, isReadOnly: true, width:60},
            { binding:"content", header:messages["item.content"], width:"*", isReadOnly: true },
        ];
    // 출력코드구성 그리드 생성 
    var theGrid = wgrid.genGrid("#theGrid", data);
    theGrid.isReadOnly = false;

    // 출력코드구성 그리드 포맷 
    theGrid.formatItem.addHandler(function(s, e) {
        if (e.panel == s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
        }
    });

    // 저장버튼 클릭 
    $("#btnSaveItem").click(function(e) {

        for ( var i = 0; i < theGrid.collectionView.itemCount; i++ ) {
            var item = theGrid.collectionView.items[i];
            if ( item.gChk ) {
                var dupCheck = false;
                for ( var j = 0; j < gridMapng.collectionView.itemCount; j++ ) {
                    var savedItem = gridMapng.collectionView.items[j];
                    if ( savedItem.prtCd == item.prtCd ) {
                        dupCheck = true;
                        break;
                    }
                }

                if ( !dupCheck ) {
                    gridMapng.collectionView.trackChanges = true;
                    var newRow = gridMapng.collectionView.addNew();
                    newRow.status = "I";
                    newRow.prtClassCd = $("#prtClassCd").val();
                    newRow.prtCd = item.prtCd;
                    newRow.gChk = true;

                    gridMapng.collectionView.commitNew();
                }
            }
        }
        // 추가된 Row 선택
        gridMapng.select(gridMapng.rows.length, 1);
        $("#itemSelTent, #itemSelLayer").hide();
    });


});
