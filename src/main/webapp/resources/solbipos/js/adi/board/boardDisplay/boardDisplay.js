/****************************************************************
 *
 * 파일명 : boardDisplay.js
 * 설  명 : 일반게시판노출순서 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회구분
var gubunComboData = [
    {"name":"제목","value":"1"},
    {"name":"내용","value":"2"},
    {"name":"제목 + 내용","value":"3"},
    {"name":"작성자","value":"4"}
];

// 열람구분
var gubunReadComboData = [
    {"name":"전체","value":""},
    {"name":"미열람","value":"N"},
    {"name":"열람","value":"Y"}
];

/**
 *  일반게시판노출순서 그리드 생성
 */
app.controller('boardDisplayCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardDisplayCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("gubunCombo", gubunComboData); // 조회구분
    $scope._setComboData("gubunReadCombo", gubunReadComboData); // 열람구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.viewYnDataMap = new wijmo.grid.DataMap(gubunReadComboData, 'value', 'name'); // 열람구분
        $scope.targetFgDataMap = new wijmo.grid.DataMap(targetFgData, 'value', 'name'); //공개대상
        $scope.apprFgDataMap = new wijmo.grid.DataMap(apprFgData, 'value', 'name'); //승인여부

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.dispSeq = messages["boardDisplay.dispSeq"];
        dataItem.backColorYn = messages["boardDisplay.backColorYn"];
        dataItem.title = messages["boardDisplay.title"];
        dataItem.viewCnt = messages["boardDisplay.viewCnt"];
        dataItem.viewYn = messages["boardDisplay.viewYn"];
        dataItem.targetFg = messages["boardDisplay.targetFg"];
        dataItem.apprFg = messages["boardDisplay.apprFg"];
        dataItem.agencyNm = messages["boardDisplay.agencyNm"];
        dataItem.userNm = messages["boardDisplay.userNm"];
        dataItem.noticeDate = messages["boardDisplay.noticeDate"];
        dataItem.remark = messages["boardDisplay.remark"];

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

    // <-- 검색 호출 -->
    $scope.$on("boardDisplayCtrl", function(event, data) {
        $scope.searchBoardDisplay();
        event.preventDefault();
    });

    $scope.searchBoardDisplay = function(){
        var params = {};
        params.boardCd = boardCd;

        $scope._inquiryMain("/adi/board/boardDisplay/boardDisplay/getBoardDisplayList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

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
            var dispSeq = 1;

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.flex.collectionView.items[i].status = "U";
                $scope.flex.collectionView.items[i].boardCd = $scope.flex.collectionView.items[i].boardCd;
                $scope.flex.collectionView.items[i].boardSeqNo = $scope.flex.collectionView.items[i].boardSeqNo;
                $scope.flex.collectionView.items[i].backColorYn = $scope.flex.collectionView.items[i].backColorYn;
                $scope.flex.collectionView.items[i].dispSeq = dispSeq;
                params.push($scope.flex.collectionView.items[i]);
                dispSeq++;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/boardDisplay/boardDisplay/getBoardDisplaySave.sb", params, function(){
                // 조회
                $scope.searchBoardDisplay()
            });
        });
    };

    // 노출제외
    $scope.displayRemove = function() {
        // 노출제외 하시겠습니까?
        $scope._popConfirm(messages["boardDisplay.displayRemoveConfirm"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "D";
                    $scope.flex.collectionView.items[i].boardCd = $scope.flex.collectionView.items[i].boardCd;
                    $scope.flex.collectionView.items[i].boardSeqNo = $scope.flex.collectionView.items[i].boardSeqNo;
                    $scope.flex.collectionView.items[i].topYn = "N";
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/boardDisplay/boardDisplay/getBoardDisplaySave.sb", params, function(){
                // 조회
                $scope.searchBoardDisplay()
            });
        });
    };

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(store) {
        $scope.selectedStore = store;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // 상위노출게시물선택
    $scope.displayAdd = function() {
        var params = {};
        params.boardCd = boardCd;
        params.gubunCombo = $scope.gubunCombo;
        if($scope.gubunName == undefined) {
            $scope.gubunName = "";
        }
        params.gubunName = $scope.gubunName;
        params.gubunReadCombo = $scope.gubunReadCombo;
        $scope.setSelectedStore(params);

        $scope.wjBoardDisplayAddLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 상위노출게시물선택 팝업 핸들러 추가
        $scope.wjBoardDisplayAddLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('boardDisplayAddCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

}]);