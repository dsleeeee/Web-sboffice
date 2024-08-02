/****************************************************************
 *
 * 파일명 : virtualAccount.js
 * 설  명 : 가상계좌내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.24     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 가상계좌 상태구분
var depositFgFgData = [
    {"name":"전체","value":""},
    {"name":"발급요청","value":"0"},
    {"name":"발급성공","value":"1"},
    {"name":"발급실패","value":"2"},
    {"name":"거래중지요청","value":"3"},
    {"name":"거래중지성공","value":"4"},
    {"name":"거래중지실패","value":"5"},
    {"name":"입금","value":"6"},
    {"name":"입금기간만료","value":"7"}

];

// 현금영수발급여부
var receiptYnData = [
    {"name":"전체","value":""},
    {"name":"미발급","value":"N"},
    {"name":"발급","value":"Y"}
];

/**
 *  가상계좌내역 그리드 생성
 */
app.controller('virtualAccountCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('virtualAccountCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("depositFgCombo", depositFgFgData); // 가상계좌 상태구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.depositFgFgDataMap = new wijmo.grid.DataMap(depositFgFgData, 'value', 'name'); // 가상계좌 상태구분
        $scope.vaBankcodeDataMap = new wijmo.grid.DataMap(vaBankcodeComboData, 'value', 'name'); // 발급할 계좌의 은행코드
        $scope.receiptYnDataMap = new wijmo.grid.DataMap(receiptYnData, 'value', 'name'); // 현금영수발급여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("virtualAccountCtrl", function(event, data) {
        $scope.searchVirtualAccount();
        event.preventDefault();
    });

    $scope.searchVirtualAccount = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.depositFg = $scope.depositFg;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/iostock/loan/virtualAccount/virtualAccount/getVirtualAccountList.sb", params, function (){});
    };
    // <-- //검색 호출 -->

    // 가상계좌 입금 생성
    $scope.loanVirtualAccount = function(){
        var params = {};
        if(orgnFg == "HQ") {
            params.storeCd = $("#virtualAccountStoreCd").val();
            params.storeCdNm = $("#virtualAccountStoreNm").val();
        }
        params.va_mny = $("#txtVaMny").val().replaceAll(",", ""); // 가상계좌 발급금액

        if(orgnFg == "HQ") {
            if(params.storeCd == "") {
                $scope._popMsg(messages["cmm.require.selectStore"]); // 매장을 선택해 주세요.
                return;
            }
        }

        if (params.va_mny == "" || params.va_mny == null) {
            $scope._popMsg(messages["virtualAccount.vaMnyBlankAlert"]); // 가상계좌 발급금액을 입력해주세요.
            return;
        } else {
            // 숫자만 입력
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test(params.va_mny)) {
                $scope._popMsg(messages["virtualAccount.vaMnyNumberChkAlert"]); // 가상계좌 발급금액은 숫자만 입력해주세요.
                return;
            }

            if(parseInt(params.va_mny) < 1){
                $scope._popMsg(messages["virtualAccount.vaMnyInChkAlert"]); // 가상계좌 발급금액은 1원 이상 입력해주세요.
                return;
            }
        }

        $scope.setSelectedStore(params);
        $scope.wjVirtualAccountRegisterLayer.show(true);
        event.preventDefault();
    };

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

        // 가상계좌 입금 생성 팝업 핸들러 추가
        $scope.wjVirtualAccountRegisterLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('virtualAccountRegisterCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

}]);