/****************************************************************
 *
 * 파일명 : alimtalkTemplate.js
 * 설  명 : 알림톡 템플릿관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 전송유형
var sendTypeCdComboData = [
    {"name":"전체","value":""}
];
// 공통템플릿 구분
var commonFgDataMapData = [
    {"name":"공용","value":"C"},
    {"name":"계정전용","value":"S"}
];
// 템플릿 승인구분
var apprFgDataMapData = [
    {"name":"전체","value":""},
    {"name":"요청","value":"TSC01"},
    {"name":"검수중","value":"TSC02"},
    {"name":"승인","value":"TSC03"},
    {"name":"반려","value":"TSC04"}
];

/**
 *  알림톡 템플릿관리 조회 그리드 생성
 */
app.controller('alimtalkTemplateCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkTemplateCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("sendTypeCdCombo", sendTypeCdComboData); // 전송유형
    $scope._setComboData("sendTypeDtlCdCombo", sendTypeCdComboData); // 전송유형상세
    $scope._setComboData("apprFgCombo", apprFgDataMapData); // 템플릿 승인구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 전송유형 조회
        $scope.sendTypeCdComboList();

        // 그리드 DataMap 설정
        $scope.commonFgDataMap = new wijmo.grid.DataMap(commonFgDataMapData, 'value', 'name'); // 공통템플릿 구분
        $scope.apprFgDataMap = new wijmo.grid.DataMap(apprFgDataMapData, 'value', 'name'); // 템플릿 승인구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 템플릿코드
                if (col.binding === "templateCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 템플릿코드 클릭시 상세정보 조회
                if ( col.binding === "templateCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedAlimtalkTemplate(selectedRow);
                    $scope.wjAlimtalkTemplateDtlLayer.show(true);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkTemplateCtrl", function(event, data) {
        $scope.searchAlimtalkTemplate();
        event.preventDefault();
    });

    $scope.searchAlimtalkTemplate = function() {
        var params = {};
        params.sendTypeCd = $scope.sendTypeCdCombo;
        params.sendTypeDtlCd = $scope.sendTypeDtlCdCombo;
        params.apprFg = $scope.apprFgCombo;

        $scope._inquiryMain("/adi/alimtalk/alimtalkTemplate/alimtalkTemplate/getAlimtalkTemplateList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 전송유형 콤보박스 -->
    // 전송유형 조회
    $scope.sendTypeCdComboList = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkTemplate/alimtalkTemplate/getSendTypeCdComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var sendTypeCdList = response.data.data.list;
                $scope._setComboData("sendTypeCdCombo", sendTypeCdList); // 전송유형
                sendTypeCdList.unshift({name: "전체", value: ""});

            } else {
                $scope._setComboData("sendTypeCdCombo", sendTypeCdComboData); // 전송유형
            }
        });
    };

    // 전송유형 선택시 전송유형상세 조회
    $scope.sendTypeCdComboChange = function(s) {
        if(s.selectedValue != "") {
            var params = {};
            params.sendTypeCd = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkTemplate/alimtalkTemplate/getSendTypeDtlCdComboList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var sendTypeDtlCdList = response.data.data.list;
                    $scope._setComboData("sendTypeDtlCdCombo", sendTypeDtlCdList); // 전송유형상세
                    sendTypeDtlCdList.unshift({name: "전체", value: ""});

                } else {
                    $scope._setComboData("sendTypeDtlCdCombo", sendTypeCdComboData); // 전송유형상세
                }
            });

        } else {
            $scope._setComboData("sendTypeDtlCdCombo", sendTypeCdComboData); // 전송유형상세
        }
    };
    // <-- //전송유형 콤보박스 -->

    // 알림톡 템플릿등록
    $scope.alimtalkTemplateRegister = function(){
        $scope.wjAlimtalkTemplateRegisterLayer.show(true);
        event.preventDefault();
    };

    // 선택 매장
    $scope.selectedAlimtalkTemplate;
    $scope.setSelectedAlimtalkTemplate = function(store) {
        $scope.selectedAlimtalkTemplate = store;
    };
    $scope.getSelectedAlimtalkTemplate = function(){
        return $scope.selectedAlimtalkTemplate;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 알림톡 템플릿등록 팝업 핸들러 추가
        $scope.wjAlimtalkTemplateRegisterLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('alimtalkTemplateRegisterCtrl', null);
            }, 50)
        });

        // 알림톡 템플릿상세 팝업 핸들러 추가
        $scope.wjAlimtalkTemplateDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('alimtalkTemplateDtlCtrl', $scope.getSelectedAlimtalkTemplate());
            }, 50)
        });
    });

}]);