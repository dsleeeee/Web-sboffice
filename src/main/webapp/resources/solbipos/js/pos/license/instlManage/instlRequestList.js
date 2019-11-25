/****************************************************************
 *
 * 파일명 : instlRequestList.js
 * 설  명 : 설치관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  설치요청 목록 그리드
 **********************************************************************/
app.controller('instlRequestListCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instlRequestListCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    // $scope._setComboData("clsFg", clsFg);
    // $scope._setComboData("sysStatFg", sysStatFg);
    $scope._setComboData("srchInstReqListFg", [
        {"name":"전체","value":""},
        {"name":"설치의뢰","value":"0"},
        {"name":"신규설치","value":"1"},
        {"name":"재설치","value":"2"}
    ]);

    // 선택 포스
    $scope.selectedPos;
    $scope.setSelectedPos = function(pos) {
        $scope.selectedPos = pos;
    };
    $scope.getSelectedPos = function(){
        return $scope.selectedPos;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.instFgDataMap = new wijmo.grid.DataMap(instFgData, 'value', 'name');
        $scope.reasonDatMap = new wijmo.grid.DataMap(reasonData, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
          if (e.panel === s.cells) {
            var col = s.columns[e.col];
            if (col.binding === "posNo") {
              //wijmo.addClass(e.cell, 'wijLink');
                wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
            }
          }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "posNo") {
                    var params      = {};
                    params.agencyCd = selectedRow.agencyCd;
                    params.storeCd  = selectedRow.storeCd;
                    params.storeNm  = selectedRow.storeNm;
                    params.posNo = selectedRow.posNo;
                    $scope._broadcast('instlRequestDtlCtrl', params);
                }
            }
        });
    };

    $scope.$on("instlRequestListCtrl", function(event, data) {
        $scope.getInstallList();
        event.preventDefault();
    });

    // 설치요청목록 조회
    $scope.getInstallList = function(){
        var params = {};
        if(orgnFg != null && orgnFg == 'AGENCY') {
            params.agencyCd = orgnCd;
        }

        $scope._inquiryMain("/pos/license/instlManage/getInstlRequestList.sb", params, function() {
        });
    };

    // 설치요청 팝업 오픈
    $scope.requestInstall = function(){

        $scope.setSelectedPos(null);
        $scope.installRegistPopupLayer.show(true, function(){
            $scope.getInstallList();
        });
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 설치요청 팝업 핸들러 추가
        $scope.installRegistPopupLayer.shown.addHandler(function (s) {
        });
    });
}]);