/****************************************************************
 *
 * 파일명 : item.js
 * 설  명 : 출력코드구성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.10     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function() {

    var srchPrtCd = wcombo.genInput("#srchPrtCd");
    var srchPrtNm = wcombo.genInput("#srchPrtNm");

    // 출력코드구성 그리드
    var gridData =
        [
            { binding:"gChk", header:messages["item.chk"], dataType:wijmo.DataType.Boolean, width:40},
            { binding:"prtCd", header:messages["item.prtCd"], width:150},
            { binding:"prtNm", header:messages["item.prtNm"], width:150},
            { binding:"samplYn", header:messages["item.samplYn"], dataType:wijmo.DataType.Boolean, width:60},
            { binding:"content", header:messages["item.content"], width:"*"},
        ];
    // 출력코드구성 그리드 생성
    var theGrid = wgrid.genGrid("#theGrid", gridData);
    theGrid.isReadOnly = false;

    // ReadOnly 효과설정
    theGrid.formatItem.addHandler(function (s, e) {
        if (e.panel == s.cells) {
            var col = s.columns[e.col];
            if (col.binding === "prtCd") {
                var item = s.rows[e.row].dataItem;
                if (item.status != "I") {
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                } else {
                    wijmo.removeClass(e.cell, 'wj-custom-readonly');
                }
            }
        }
    });

    // 출력코드구성 그리드 에디팅 방지
    theGrid.beginningEdit.addHandler(function (s, e) {
        var col = s.columns[e.col];
        if (col.binding === "prtCd") {
            var dataItem = theGrid.rows[e.row].dataItem;
            if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
                e.cancel = true;
            }
        }
    });

    var contentColumn = theGrid.columns.getColumn("content");
    contentColumn.multiLine = true;
    contentColumn.wordWrap = true;

    // auto-size visible rows
    function autoSizeVisibleRows(flex, force) {
        var rng = flex.viewRange;
        for (var r = rng.row; r <= rng.row2; r++) {
            if (force || flex.rows[r].height == null) {
                // flex.autoSizeRow(r, false)
            }
        }
    }

    // validation
    theGrid.cellEditEnded.addHandler(function (s, e){
        // autoSizeVisibleRows(s, true);
    });

    // 조회버튼 클릭
    $("#btnSearch").click(function(e){
        search();
    });

    // 출력코드구성 목록 조회
    function search() {

        var param = {};
        param.prtCd = srchPrtCd.text;
        param.prtNm = srchPrtNm.text;

        $.postJSON("/sys/bill/item/item/list.sb", param,
            function(result) {
                if(result.status === "FAIL") {
                    s_alert.pop(result.message);
                    return;
                }

                // 버튼 Show
                $("#btnAdd").show();
                $("#btnDel").show();
                $("#btnSave").show();

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

    };

    // 출력코드구성 저장 버튼 클릭
    $("#btnSave").click(function(e) {

        var paramArr = new Array();

        for ( var i = 0; i < theGrid.collectionView.itemsEdited.length; i++ ) {
            theGrid.collectionView.itemsEdited[i].status = "U";
            paramArr.push(theGrid.collectionView.itemsEdited[i]);
        }
        for ( var i = 0; i < theGrid.collectionView.itemsAdded.length; i++ ) {
            theGrid.collectionView.itemsAdded[i].status = "I";
            paramArr.push(theGrid.collectionView.itemsAdded[i]);
        }

        if ( paramArr.length <= 0 ) {
            s_alert.pop(messages["cmm.not.modify"]);
            return;
        }

        $.postJSONArray("/sys/bill/item/item/save.sb", paramArr, function(result) {
                s_alert.pop(messages["cmm.saveSucc"]);
                theGrid.collectionView.clearChanges();
            },
            function(result) {
                s_alert.pop(result.message);
            }
        );

    });

    // 출력코드구성 추가 버튼 클릭
    $("#btnAdd").click(function(e) {
        theGrid.collectionView.trackChanges = true;
        var newRow = theGrid.collectionView.addNew();
        newRow.status = "I";
        newRow.gChk = true;
        newRow.samplYn = false;

        theGrid.collectionView.commitNew();
        // 추가된 Row 선택
        theGrid.select(theGrid.rows.length, 1);
    });

});
