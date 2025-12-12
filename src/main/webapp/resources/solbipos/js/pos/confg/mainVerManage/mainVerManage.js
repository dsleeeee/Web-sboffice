/****************************************************************
 *
 * 파일명 : mainVerManage.js
 * 설  명 : POS 메인버전관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.07     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 등록여부
var registYnComboData = [
    {"name": "전체", "value": ""},
    {"name": "등록", "value": "Y"},
    {"name": "미등록", "value": "N"}
];

/**
 *  POS 메인버전관리 그리드 생성
 */
app.controller('mainVerManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mainVerManageCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("registYnCombo", registYnComboData); // 등록여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.progFgDataMap = new wijmo.grid.DataMap(progFgData, 'value', 'name'); // 프로그램구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 메인버전 등록
                if (col.binding === "mainVerRegist") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (item[("mainVerRegist")] === "등록") {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }

                // 메인버전 삭제
                if (col.binding === "mainVerDel") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (item[("mainVerDel")] === "삭제") {
                    wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                // 메인버전 등록 클릭시 상세정보 조회
                if (col.binding === "mainVerRegist") {
                    // 값이 있으면 링크 효과
                    if (selectedRow[("mainVerRegist")] === "등록") {
                        if(selectedRow.hqOfficeCd === 'A0001') {
                            $scope._popMsg("[" + selectedRow.hqOfficeCd + "] " + selectedRow.hqOfficeNm + " " + messages["mainVerManage.reg.chk.msg"]);  // [A0001] 보나비 등록제한. 관리자에게 문의하십시오.
                            return false;
                        } else {
                            $scope.setSelectedStore(selectedRow);
                            $scope.wjMainVerRegistLayer.show(true);
                            event.preventDefault();
                        }
                    }
                }

                // 메인버전 삭제 클릭시 상세정보 조회
                if (col.binding === "mainVerDel") {
                    // 값이 있으면 링크 효과
                    if (selectedRow[("mainVerDel")] === "삭제") {
                        $scope.mainVerDel(selectedRow);
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
        dataItem.hqOfficeCd = messages["cmm.hqOfficeCd"];
        dataItem.hqOfficeNm = messages["cmm.hqOfficeNm"];
        dataItem.storeCnt = messages["mainVerManage.storeCnt"];
        dataItem.mainVerRegist = messages["mainVerManage.mainVer"];
        dataItem.mainVerDel = messages["mainVerManage.mainVer"];
        dataItem.fileDesc = messages["mainVerManage.fileDesc"];
        dataItem.progFg = messages["mainVerManage.progFg"];

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
    $scope.$on("mainVerManageCtrl", function(event, data) {
        $scope.searchMainVerManage();
        event.preventDefault();
    });

    $scope.searchMainVerManage = function(){
        var params = {};
        params.srchHqOfficeCd = $scope.srchHqOfficeCd;
        params.srchHqOfficeNm = $scope.srchHqOfficeNm;
        params.registYn = $scope.registYn;

        $scope._inquiryMain("/pos/confg/mainVerManage/mainVerManage/getMainVerManageList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(store) {
        $scope.selectedStore = store;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 메인버전 등록 팝업 핸들러 추가
        $scope.wjMainVerRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('mainVerRegistCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

    // 메인버전 삭제
    $scope.mainVerDel = function(data){
        // 메인버전 삭제하시겠습니까?
        var msg = "[" + data.hqOfficeCd + " " + data.hqOfficeNm + "] " + messages["mainVerManage.mainVerDelConfirm"];
        $scope._popConfirm(msg, function() {
            var params = {};
            params.hqOfficeCd = data.hqOfficeCd;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/pos/confg/mainVerManage/mainVerManage/getMainVerManageDel.sb", params, function(){
                // 조회
                $scope.searchMainVerManage();
            });
        });
    };

}]);