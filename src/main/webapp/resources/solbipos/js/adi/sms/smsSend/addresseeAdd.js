/****************************************************************
 *
 * 파일명 : addresseeAdd.js
 * 설  명 : 수신자추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  수신자추가 팝업 조회 그리드 생성
 */
app.controller('addresseeAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('addresseeAddCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._getComboDataQuery('072', 'smsRecvYnAddresseeAddCombo', 'A'); // SMS수신구분
    $scope._getComboDataQuery('007', 'serviceFgAddresseeAddCombo', 'A'); // 재직구분
    if(orgnFg == "MASTER") {
        $scope._getComboDataQuery('002', 'srchOrgnFgAddresseeAddCombo', 'A'); // 소속구분
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.smsRecvYnDataMap = new wijmo.grid.DataMap(smsRecvYnData, 'value', 'name'); // SMS수신구분
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name'); // 재직구분
    };

    // <-- 검색 호출 -->
    $scope.$on("addresseeAddCtrl", function(event, data) {
        $scope.searchAddresseeAdd();
        event.preventDefault();
    });

    $scope.searchAddresseeAdd = function(){
        var params = {};
        params.smsRecvYn = $scope.smsRecvYnAddresseeAddCombo;
        params.serviceFg = $scope.serviceFgAddresseeAddCombo;
        if(orgnFg == "MASTER") {
            params.srchOrgnFg = $scope.srchOrgnFgAddresseeAddCombo;
        }

        $scope._inquiryMain("/adi/sms/smsSend/smsSend/getAddresseeAddList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 적용
    $("#funcApply").click(function(e){
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].membrNo = "";
                $scope.flex.collectionView.items[i].membrNm = $scope.flex.collectionView.items[i].empNm;
                $scope.flex.collectionView.items[i].telNo = $scope.flex.collectionView.items[i].mpNo;
                $scope.flex.collectionView.items[i].orgnFg = $scope.flex.collectionView.items[i].orgnFg;
                $scope.flex.collectionView.items[i].orgnCd = $scope.flex.collectionView.items[i].orgnCd;
                $scope.flex.collectionView.items[i].userId = $scope.flex.collectionView.items[i].userId;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 수신자목록 셋팅
        var addresseeAddScope = agrid.getScope('smsSendCtrl');
        addresseeAddScope.receiveNameSet(params);

        // 팝업 닫기
        $scope.close();
    });

    // 팝업 닫기
    $scope.close = function(){
        $scope.empNm = "";
        $scope.userId = "";
        $scope.mpNo = "";
        $scope.srchSmsRecvYnAddresseeAddCombo.selectedIndex = 0;
        $scope.srchServiceFgAddresseeAddCombo.selectedIndex = 0;
        if(orgnFg == "MASTER") {
            $scope.srchOrgnFgAddresseeAddCombo.selectedIndex = 0;
        }

        $scope.wjAddresseeAddLayer.hide();
    };
}]);