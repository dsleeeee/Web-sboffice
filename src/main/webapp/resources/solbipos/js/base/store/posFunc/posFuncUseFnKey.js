/****************************************************************
 *
 * 파일명 : posFuncUseFnKey.js
 * 설  명 : 포스기능키 사용등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.08    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// angular 그리드 생성
app.controller('posFuncUseFnKeyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posFuncUseFnKeyCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var movedRows = 0;
            var ht = s.hitTest(e);

            if (ht.cellType === wijmo.grid.CellType.Cell) {
                if (ht.cellType === wijmo.grid.CellType.Cell) {
                    var col = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if (col.binding === "useYn") { // 사용여부
                        if(selectedRow.useYn){
                            for (var i = ht.row - 1; i >= 0; i--) {
                                if (ht.row !== i) {
                                    movedRows = i + 1;
                                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                                    $scope.flex.collectionView.items[i] = tmpItem;
                                }
                                $scope.flex.collectionView.commitEdit();
                                $scope.flex.collectionView.refresh();
                            }
                            $scope.flex.select(movedRows, 1);
                        }
                    }
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk           = messages["cmm.chk"];
        dataItem.fnkeyNo        = messages["posFunc.fnkeyNo"];
        dataItem.fnkeyNm        = messages["posFunc.fnkeyNm"];
        dataItem.existFgBefore  = messages["posFunc.existFgBefore"];
        dataItem.existFg        = messages["posFunc.existFg"];
        dataItem.posiAdjYn      = messages["posFunc.posiAdjYn"];
        dataItem.dispSeq        = messages["posFunc.dispSeq"];
        dataItem.colPosi        = messages["posFunc.colPosi"];
        dataItem.rowPosi        = messages["posFunc.rowPosi"];
        dataItem.width          = messages["posFunc.width"];
        dataItem.height         = messages["posFunc.height"];
        dataItem.useYn          = messages["posFunc.useYn"];

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
        }
        // <-- //그리드 헤더2줄 -->
    };

    $scope.$on("posFuncUseFnKeyCtrl", function(event, data) {

        $scope.posFuncUseFnKeyLayer.show(true);

        // 선택한 fnkey 이름 셋팅 및 hidden 에 값 가지고 있기
        $("#fnKeyTitle").text(data.fnkeyNm);
        $("#hdStoreCd").val(data.storeCd);
        $("#hdPosNo").val(data.posNo);
        $("#hdFnkeyFg").val(data.fnkeyFg);

        // 포스기능키목록 그리드 조회
        $scope.getPosFuncDetail(data);

        event.preventDefault();

    });

    // 포스기능키목록 그리드 조회
    $scope.getPosFuncDetail = function(data) {
        var params = {};

        params.storeCd = data.storeCd;
        params.posNo = data.posNo;
        params.fnkeyFg = data.fnkeyFg;

        $scope._inquiryMain("/base/store/posfunc/use/getPosConfDetail.sb", params, function() {}, false);
    }

    // 위로 옮기기 버튼
    $scope.rowMoveUp = function() {
        var movedRows = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (i > 0 && item.gChk) {
                if (!$scope.flex.collectionView.items[i - 1].gChk) {
                    movedRows = i - 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };
    // 아래로 옮기기 버튼
    $scope.rowMoveDown = function() {
        var movedRows = 0;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
                if (!$scope.flex.collectionView.items[i + 1].gChk) {
                    movedRows = i + 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // 저장
    $scope.save = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            // dispSeq 재설정
            var editItems = [];
            for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
                if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                    editItems.push($scope.flex.collectionView.items[s]);
                }
            }

            for (var s = 0; s < editItems.length; s++) {
                editItems[s].dispSeq = (s + 1);
                console.log(editItems);
                $scope.flex.collectionView.editItem(editItems[s]);
                editItems[s].status = "U";
                $scope.flex.collectionView.commitEdit();
            }

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                $scope.flex.collectionView.itemsEdited[u].storeCd = $("#hdStoreCd").val();
                $scope.flex.collectionView.itemsEdited[u].posNo = $("#hdPosNo").val();
                $scope.flex.collectionView.itemsEdited[u].fnkeyFg = $("#hdFnkeyFg").val();
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }


            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/store/posfunc/use/savePosConf.sb', params, function() {

                $scope.posFuncUseFnKeyLayer.hide(true);

                // 저장 후 부모창 그리드 재조회
                showPosFuncList2($("#hdPosNo").val());

            });
        });
    }

}]);