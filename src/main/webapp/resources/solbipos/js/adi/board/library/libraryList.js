/****************************************************************
 *
 * 파일명 : libraryList.js
 * 설  명 : 자료실 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.03.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회구분
var gubunComboData = [
    {"name":"자료명","value":"1"},
    {"name":"작성자","value":"2"}
];

/**
 *  자료실 그리드 생성
 */
app.controller('libraryListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('libraryListCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("gubunCombo", gubunComboData); // 조회구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 자료명
                if (col.binding === "title") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 자료명 클릭시 상세정보 조회
                if ( col.binding === "title") {
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);
                    $scope.wjLibraryDetailLayer.show(true);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("libraryListCtrl", function(event, data) {
        $scope.searchLibraryList();
        event.preventDefault();
    });

    $scope.searchLibraryList = function(){
        var params = {};
        params.listScale = $scope.listScaleLibrary;
        params.boardCd = boardCd;

        $scope._inquiryMain("/adi/board/library/library/getLibraryList.sb", params, function() {}, false);
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

    // 자료실 신규등록 팝업 오픈
    $scope.addInfo = function(){
        $scope.setSelectedStore(null);
        $scope.wjLibraryInfoLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 자료실 신규,수정 팝업 핸들러 추가
        $scope.wjLibraryInfoLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('libraryInfoCtrl', $scope.getSelectedStore());
            }, 50)
        });

        // 자료실 상세 팝업 핸들러 추가
        $scope.wjLibraryDetailLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('libraryDetailCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

}]);