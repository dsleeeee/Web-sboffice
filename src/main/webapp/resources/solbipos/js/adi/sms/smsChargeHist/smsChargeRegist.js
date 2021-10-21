/****************************************************************
 *
 * 파일명 : smsChargeRegist.js
 * 설  명 : SMS임의충전 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.20     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  SMS임의충전 팝업 조회 그리드 생성
 */
app.controller('smsChargeRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsChargeRegistCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("clsFgCombo", clsFgComboData); // 용도
    $scope._setComboData("sysStatFgCombo", sysStatFgComboData); // 상태
    if(orgnFg == "MASTER") {
        $scope._getComboDataQuery('002', 'srchOrgnFgCombo', 'A'); // 소속구분
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFgComboData, 'value', 'name'); // 용도
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgComboData, 'value', 'name'); // 상태

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 소속코드
                if (col.binding === "orgnCd") {
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

                // 소속코드 클릭시 상세정보 조회
                if ( col.binding === "orgnCd") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    $("#selectOrgnCd").val(selectedRow.orgnCd);
                    $("#selectOrgnNm").val(selectedRow.orgnNm);
                    $("#smsChargeAmt").val("0");
                    $("#smsBaseAmt").val(selectedRow.smsAmt);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("smsChargeRegistCtrl", function(event, data) {
        $scope.searchSmsChargeRegist();
        event.preventDefault();
    });

    $scope.searchSmsChargeRegist = function(){
        var params = {};

        $scope._inquiryMain("/adi/sms/smsChargeHist/smsChargeHist/getSmsChargeRegistList.sb", params, function() {
            $("#selectOrgnCd").val("");
            $("#selectOrgnNm").val("");
            $("#smsChargeAmt").val("");
            $("#smsBaseAmt").val("");
        }, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {
        if($("#selectOrgnCd").val() === "" || $("#selectOrgnCd").val() === undefined) {
            $scope._popMsg(messages["smsChargeRegist.orgnCdBlank"]); // 소속코드를 선택해주세요.
            return false;
        }
        if($("#smsChargeAmt").val() === "" || $("#smsChargeAmt").val() === undefined || $("#smsChargeAmt").val() === "0") {
            $scope._popMsg(messages["smsChargeRegist.chargeSmsAmtBlank"]); // 충전금액을 입력해주세요.
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var params = {};
            params.selectOrgnCd = $("#selectOrgnCd").val();
            params.selectOrgnNm = $("#selectOrgnNm").val();
            params.chargeAmt = $("#smsChargeAmt").val();
            params.baseChargeAmt = $("#smsBaseAmt").val();

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsChargeHist/smsChargeHist/getSmsChargeRegistSave.sb", params, function(){ $scope.allSearch(); });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        // 팝업 닫기
        $scope.close();
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.srchOrgnCd = "";
        $scope.srchOrgnNm = "";
        $scope.srchClsFgCombo.selectedIndex = 0;
        $scope.srchSysStatFgCombo.selectedIndex = 0;
        if(orgnFg == "MASTER") {
            $scope.srchOrgnFgCombo.selectedIndex = 0;
        }

        $("#selectOrgnCd").val("");
        $("#selectOrgnNm").val("");
        $("#smsChargeAmt").val("");
        $("#smsBaseAmt").val("");

        $scope.wjSmsChargeRegistLayer.hide();

        // 재조회
        var smsChargeHistScope = agrid.getScope('smsChargeHistCtrl');
        smsChargeHistScope.searchSmsChargeHist();
    };
}]);