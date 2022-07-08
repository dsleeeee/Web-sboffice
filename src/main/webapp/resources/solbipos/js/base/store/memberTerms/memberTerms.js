/****************************************************************
 *
 * 파일명 : memberTerms.js
 * 설  명 : 회원약관관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.07.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 파일타입
var fileTypeComboData = [
    {"name":"회원약관","value":"008"}
];

/**
 *  회원약관관리 조회 그리드 생성
 */
app.controller('memberTermsCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberTermsCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("useYn", useYnComboData);
    $scope._setComboData("fileType", fileTypeComboData);

    // 전체기간 체크박스
    $scope.isChecked = true;

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name');
        $scope.fileTypeDataMap = new wijmo.grid.DataMap(fileTypeComboData, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "verSerNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 파일번호
                if (col.binding === "verSerNo") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 파일번호 클릭시 상세정보 조회
                if ( col.binding === "verSerNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectVersion(selectedRow);
                    $scope.wjMemberTermsDtlLayer.show(true);
                    event.preventDefault();
                }
            }
        });

        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
    };

    // <-- 검색 호출 -->
    $scope.$on("memberTermsCtrl", function(event, data) {
        $scope.searchMemberTerms();
        event.preventDefault();
    });

    $scope.searchMemberTerms = function() {
        var params = {};
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        }
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/base/store/memberTerms/memberTerms/getMemberTermsList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
    };

    // 선택
    $scope.selectedVersion;
    $scope.setSelectVersion = function(store) {
        $scope.selectedVersion = store;
    };
    $scope.getSelectVersion = function() {
        return $scope.selectedVersion;
    };

    // 회원약관등록
    $scope.registVersion = function(){
        $scope.getSelectVersion(null);
        $scope.wjMemberTermsRegisLayer.show(true, function(){
            var scope = agrid.getScope('memberTermsRegistCtrl');
            scope.version = null;
            scope.useYn = 'Y';

            $('#file').val(null);
            $scope._pageView('memberTermsCtrl', 1);
        });
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 회원약관관리 신규등록 팝업 핸들러 추가
        $scope.wjMemberTermsRegisLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('memberTermsRegistCtrl', null);
            }, 50)
        });

        // 회원약관관리 상세 팝업 핸들러 추가
        $scope.wjMemberTermsDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('memberTermsDtlCtrl', $scope.getSelectVersion());
            }, 50)
        });

        // 회원약관관리 매장추가 팝업 핸들러 추가
        $scope.wjMemberTermsStoreAddLayer.shown.addHandler(function (s) {});
    });
}]);