/****************************************************************
 *
 * 파일명 : smsTelNoStop.js
 * 설  명 : 발신번호차단 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  발신번호차단 조회 그리드 생성
 */
app.controller('smsTelNoStopCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsTelNoStopCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("smsTelNoStopCtrl", function(event, data) {
        $scope.searchSmsTelNoStop();
        event.preventDefault();
    });

    $scope.searchSmsTelNoStop = function() {
        var params = {};
        params.srchOrgnCd = $scope.srchOrgnCd;
        params.srchOrgnNm = $scope.srchOrgnNm;

        $scope._inquiryMain("/adi/sms/smsTelNoManage/smsTelNoStop/getSmsTelNoStopList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].telNo = $scope.flex.collectionView.itemsEdited[i].telNo.replaceAll("-", "");
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 전화번호 중복 값 확인
            var chkParams = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].useYn === 'Y') {
                    chkParams.push($scope.flex.collectionView.items[i]);
                }
            }

            var chkTelNo = '';
            for(var i=0; i <chkParams.length; i++){
                for(var j=0; j<chkParams.length; j++){
                    if(i !== j) {
                        if (chkParams[i].telNo == chkParams[j].telNo) {
                            if (chkTelNo.indexOf(chkParams[j].telNo) === -1) {
                                chkTelNo += chkParams[j].telNo + ",";
                            }
                        }
                    }
                }
            }

            if(chkTelNo !== ''){
                chkTelNo = chkTelNo.substr(0 , chkTelNo.length-1);
                var msg = messages["smsTelNoStop.dupTelNo"];
                msg += '<br/>' + "(" + chkTelNo + ")";
                $scope._popMsg(msg);
                return false;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsTelNoManage/smsTelNoStop/getSmsTelNoStopSaveUpdate.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchSmsTelNoStop();
    };

    // 일반번호 인증요청 처리
    $scope.smsGeneralNoManage = function() {
        $scope.wjSmsGeneralNoManageLayer.show(true);
        event.preventDefault();
    };

    // 서류인증신청
    $scope.smsGeneralNoManage2 = function() {
        $scope.wjSmsGeneralNoManage2Layer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 일반번호 인증요청 처리 팝업 핸들러 추가
        $scope.wjSmsGeneralNoManageLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsGeneralNoManageCtrl', null);
            }, 50)
        });

        // 일반번호 인증요청 처리2 팝업 핸들러 추가
        $scope.wjSmsGeneralNoManage2Layer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsGeneralNoManage2Ctrl', null);
            }, 50)
        });
    });
}]);