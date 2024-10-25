/****************************************************************
 *
 * 파일명 : verEnvMng.js
 * 설  명 : 버전별환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.10.23     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var allEnvstGrp = new Array();

/**
 * 버전 그리드 생성
 */
app.controller('verCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('verCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name'); // 사용여부

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "vsCd") {
                    var item = s.rows[e.row].dataItem;
                    if (nvl(item.status, "") === "" && item.status !== "I") {
                        wijmo.addClass(e.cell, 'wijLink'); // 링크효과
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "vsCd") {
                var item = s.rows[elements.row].dataItem;
                if (nvl(item.status, "") === "" && item.status !== "I") {
                    elements.cancel = true;
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                // 버전코드 클릭시 대표명칭 조회
                if (col.binding === "vsCd") {
                    if (selectedRow.status === undefined) {
                        var scope = agrid.getScope('representCtrl');
                        scope.getRepresentList(selectedRow.vsCd);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    // 버전 그리드 조회
    $scope.$on('verCtrl', function (event, data) {

        // 대표명칭 초기화
        agrid.getScope('representCtrl')._gridDataInit();
        $("#btnRepresentSave").hide();
        
        // 세부명칭 초기화
        agrid.getScope('detailCtrl')._gridDataInit();
        $("#btnDetailSave").hide();

        // 버전 그리드 조회
        var params = {};
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sys/cd/verEnvMng/getVerList.sb", params, function () {
            // 버전 버튼 show
            $("#btnVerAdd").show();
            $("#btnVerSave").show();
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 버전 그리드 행 추가
    $scope.addRow = function () {

        // 파라미터 설정
        var params = {};
        params.vsCd = "";
        params.vsNm = "";
        params.useYn = "Y";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params, 0);
    };

    // 버전 그리드 저장
    $scope.save = function () {
        // 파라미터 설정
        var params = [];
        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/cd/verEnvMng/saveVer.sb", params, function () {
            $scope._broadcast('verCtrl');
        });
    };

}]);

/**
 * 대표명칭 그리드 생성
 */
app.controller('representCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('representCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.envstFgNmDataMap = new wijmo.grid.DataMap(envstFgNm, 'value', 'name');
        $scope.envstGrpCdNmDataMap = allEnvstGrp;
        $scope.targtFgDataMap = new wijmo.grid.DataMap(targtFg, 'value', 'name');
        $scope.dirctInYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "직접"}, {
            id: "N",
            name: "선택"
        }], 'id', 'name');
        $scope.useYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "사용"}, {
            id: "N",
            name: "사용안함"
        }], 'id', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "envstCd") {
                    wijmo.addClass(e.cell, 'wijLink'); // 링크효과
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                // 설정코드 클릭시 세부명칭 조회
                if (col.binding === "envstCd") {
                    var scope = agrid.getScope('detailCtrl');
                    scope.getDetailList(selectedRow.envstCd);
                    event.preventDefault();
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.vsUseYn        = messages["verEnvMng.useYn"];
        dataItem.envstCd        = messages["verEnvMng.envstCd"];
        dataItem.envstNm        = messages["verEnvMng.envstNm"];
        dataItem.envstFg        = messages["verEnvMng.envstFgNm"];
        dataItem.envstGrpCdNm   = messages["verEnvMng.envstGrpCdNm"];
        dataItem.dirctInYn      = messages["verEnvMng.dirctInYn"];
        dataItem.targtFg        = messages["verEnvMng.targtFgNm"];
        dataItem.useYn          = messages["verEnvMng.useYn"];
        dataItem.remark         = messages["verEnvMng.remark"];
        dataItem.envstRemark    = messages["verEnvMng.envstRemark"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });

                if ((panel.grid.columnHeaders.rows.length - 1) === r) {
                    // 헤더의 전체선택 클릭 로직
                    var flex   = panel.grid;
                    var column = flex.columns[c];
                    // check that this is a boolean column
                    if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                        // prevent sorting on click
                        column.allowSorting = false;
                        // count true values to initialize checkbox
                        var cnt             = 0;
                        for (var i = 0; i < flex.rows.length; i++) {
                            if (flex.getCellData(i, c) === true) {
                                cnt++;
                            }
                        }
                        // create and initialize checkbox
                        if (column.format === 'checkBoxText') {
                            cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />';
                        } else {
                            cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                        }
                        var cb           = cell.firstChild;
                        cb.checked       = cnt > 0;
                        cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                        // apply checkbox value to cells
                        cb.addEventListener('click', function (e) {
                            flex.beginUpdate();
                            for (var i = 0; i < flex.rows.length; i++) {
                                if(!flex.rows[i].isReadOnly) {
                                    flex.setCellData(i, c, cb.checked);
                                }
                            }
                            flex.endUpdate();
                        });
                    }
                }
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        };
        // <-- //그리드 헤더2줄 -->

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });

    };

    $scope.$on("representCtrl", function(event, data) {

    });

    // 대표명칭 그리드 조회
    $scope.getRepresentList = function(data){

        // 선택한 버전코드 hidden 변수에 set
        $("#hdVsCd").val(data);

        var params = {};
        params.vsCd = data;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sys/cd/verEnvMng/getEnvstList.sb", params, function () {
            // 대표명칭 사용여부 저장 버튼 show
            $("#btnRepresentSave").show();
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    };

    // 대표명칭 그리드 저장
    $scope.save = function () {
        // 파라미터 설정
        var params = [];
        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = "U";
            $scope.flex.collectionView.itemsEdited[u].useYn = $scope.flex.collectionView.itemsEdited[u].vsUseYn === true ? "Y" : "N";
            $scope.flex.collectionView.itemsEdited[u].vsCd = $("#hdVsCd").val();
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/cd/verEnvMng/saveEnvst.sb", params, function () {
            $scope.getRepresentList($("#hdVsCd").val());
        });
    };

}]);

/**
 * 세부명칭 그리드 생성
 */
app.controller('detailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('detailCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "사용"}, {
            id: "N",
            name: "사용안함"
        }], 'id', 'name');
        $scope.defltYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "기본"}, {
            id: "N",
            name: "기본아님"
        }], 'id', 'name');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.vsDefltYn  = messages["verEnvMng.defltYn"];
        dataItem.envstValCd = messages["verEnvMng.envstValCd"];
        dataItem.envstValNm = messages["verEnvMng.envstValNm"];
        dataItem.defltYn    = messages["verEnvMng.defltYn"];
        dataItem.useYn      = messages["verEnvMng.useYn"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });

                if ((panel.grid.columnHeaders.rows.length - 1) === r) {
                    // 헤더의 전체선택 클릭 로직
                    var flex   = panel.grid;
                    var column = flex.columns[c];
                    // check that this is a boolean column
                    if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                        // prevent sorting on click
                        column.allowSorting = false;
                        // count true values to initialize checkbox
                        var cnt             = 0;
                        for (var i = 0; i < flex.rows.length; i++) {
                            if (flex.getCellData(i, c) === true) {
                                cnt++;
                            }
                        }
                        // create and initialize checkbox
                        if (column.format === 'checkBoxText') {
                            cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />';
                        } else {
                            cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                        }
                        var cb           = cell.firstChild;
                        cb.checked       = cnt > 0;
                        cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                        // apply checkbox value to cells
                        cb.addEventListener('click', function (e) {
                            flex.beginUpdate();
                            for (var i = 0; i < flex.rows.length; i++) {
                                if(!flex.rows[i].isReadOnly) {
                                    flex.setCellData(i, c, cb.checked);
                                }
                            }
                            flex.endUpdate();
                        });
                    }
                }
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        };
        // <-- //그리드 헤더2줄 -->

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });

    };

    $scope.$on('detailCtrl', function (event, data) {

    });

    // 세부명칭 그리드 조회
    $scope.getDetailList = function(data){

        // 선택한 설정코드 hidden 변수에 set
        $("#hdEnvstCd").val(data);

        var params = {};
        params.vsCd = $("#hdVsCd").val();
        params.envstCd = data;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sys/cd/verEnvMng/getEnvstDtlList.sb", params, function () {
            // 세부명칭 초기값여부 저장 버튼 show
            $("#btnDetailSave").show();
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    };

    // 세부명칭 그리드 저장
    $scope.save = function () {

        // 초기값여부 한개만 설정되어 있는지 확인
        var cnt = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.vsDefltYn === true) {
                cnt++;
            }
        }
        if(cnt !== 1){
            s_alert.pop(messages["verEnvMng.defltYn.chk.msg"]); // 세부명칭의 초기값여부는 한개만 체크하세요.
            return false;
        }

        // 파라미터 설정
        var params = [];
        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = "U";
            $scope.flex.collectionView.itemsEdited[u].defltYn = $scope.flex.collectionView.itemsEdited[u].vsDefltYn === true ? "Y" : "N";
            $scope.flex.collectionView.itemsEdited[u].vsCd = $("#hdVsCd").val();
            $scope.flex.collectionView.itemsEdited[u].envstCd = $("#hdEnvstCd").val();
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/cd/verEnvMng/saveEnvstDtl.sb", params, function () {
            $scope.getDetailList($("#hdEnvstCd").val());
        });
    };

}]);