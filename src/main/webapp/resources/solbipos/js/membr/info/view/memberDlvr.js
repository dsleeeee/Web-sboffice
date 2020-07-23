/****************************************************************
 *
 * 파일명 : memberClass.js
 * 설  명 : 회원정보관리 > 회원등급설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.09     이재영      1.0
 *
 * **************************************************************/
var app = agrid.getApp();
var selectedPoint;
app.controller('memberDlvrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberDlvrCtrl', $scope, $http, false));

    $scope._getComboDataQuery('067', 'useYn', '');

    $scope.dlvrLzone = [];
    $scope.dlvrMzone = [];
    $scope.userUseYn = false;
    $scope.dlvrStoreCd = '';
    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // $scope.getMember();
    };
    // 저장
    $scope.save = function () {
        if (!$scope.valueCheck()) return false;
        var params = $scope.detailData;
        $scope._postJSONSave.withOutPopUp("/membr/info/grade/grade/classRegist.sb", params, function (response) {
            if (response.data.status == 'OK') {
                $scope._popMsg(messages["cmm.saveSucc"]);
                $scope.getMember();
                var scope = agrid.getScope('memberClassDetailCtrl');
                scope._broadcast('memberClassDetailInit');
            } else {
                $scope._popMsg(messages["cmm.saveFail"]);
                return false;
            }
        });
    };
    $scope.$on("getMemberDlvr", function (event, params, mode) {
        $scope.memberParmas = params;
        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/membr/info/view/view/getDlvrList.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (_httpStatusCheck(response, true)) {
                // this callback will be called asynchronously
                // when the response is available
                var list = response.data.data.dlvrList;
                $scope.dlvrLzone = response.data.data.lZoneList;
                $scope.dlvrStoreCd = params.regStoreCd;
                if (list.length === undefined || list.length === 0) {
                    $scope.data = new wijmo.collections.CollectionView([]);
                    if (isView && response.data.message) {
                        $scope._popMsg(response.data.message);
                    }
                    return false;
                }
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                $scope.data = data;
                console.log($scope.dlvrLzone);
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    });

    // http 조회 후 status 체크
    $scope._httpStatusCheck = function (res, isMsg) {
        return _httpStatusCheck(res, isMsg);
    };
    // private
    function _httpStatusCheck(res, isMsg) {
        if (res.data.status === 'OK') {
            return true;
        }
        else if (res.data.status === 'FAIL') {
            if (isMsg) {
                $scope._popMsg('Ajax Fail By HTTP Request');
            }
            return false;
        }
        else if (res.data.status === 'SESSION_EXFIRE') {
            if (isMsg) {
                $scope._popMsg(res.data.message, function () {
                    location.href = res.data.url;
                });
            }
            return false;
        }
        else if (res.data.status === 'SERVER_ERROR') {
            if (isMsg) {
                $scope._popMsg(res.data.message);
            }
            return false;
        }
        else {
            if (isMsg) {
                var msg = res.data.status + ' : ' + res.data.message;
                $scope._popMsg(msg);
            }
            return false;
        }
    }

    $scope.$watch('lZoneListCd', function () {
        var params = {};
        params.dlvrLzoneCd = $scope.lZoneListCd;
        params.regStoreCd = $scope.dlvrStoreCd;
        $http({
            method: 'POST', //방식
            url: '/membr/info/view/view/getDlvrMzoneList.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if (_httpStatusCheck(response, true)) {
                // this callback will be called asynchronously
                // when the response is available
                console.log(response);
                $scope.dlvrMzone = response.data.data.mZoneList;
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    });

    $scope.save = function () {
        if (!$scope.valueCheck()) return false;
        var params = {};
        parmas.membrOrgnCd = $scope.memberParmas.membrOrgnCd;
        parmas.membrNo = $scope.memberParmas.membrNo;
        parmas.dlvrStoreCd = $scope.memberParmas.regStoreCd;
        params.dlvrLzoneCd = $scope.lZoneListCd;
        params.dlvrMzoneCd = $scope.mZoneListCd;
        params.addr = $scope.dlvrAddr;
        params.addrDtl = $scope.dlvrAddr;
        params.useYn = $scope.dlvrUseYn;
        $scope._postJSONSave.withOutPopUp("/membr/info/view/base/registDlvrInfo.sb", params, function (response) {
            if (response.data.status == 'OK') {
                $scope._popMsg(messages["cmm.saveSucc"]);
                $scope.getMember();
                var scope = agrid.getScope('memberClassDetailCtrl');
                scope._broadcast('memberClassDetailInit');
            } else {
                $scope._popMsg(messages["cmm.saveFail"]);
                return false;
            }
        });
    };

    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {
        return true;
    };
}]);


app.controller('memberDlvrTelCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberDlvrTelCtrl', $scope, $http, true));
    // 선택 기능 구분
    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };
    $scope.userUseYn = false;
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // $scope.getMember();
    };
    $scope.$on("getMemberDlvrTel", function (event, params, mode) {
        $scope._inquiryMain("/membr/info/view/view/getDlvrTelList.sb", params, function () {
        });
    });
    // 저장
    $scope.save = function () {
        // 파라미터 설정
        var params = {};
        parmas.membrOrgnCd = $scope.memberParmas.membrOrgnCd;
        parmas.membrNo = $scope.memberParmas.membrNo;
        parmas.dlvrStoreCd = $scope.memberParmas.regStoreCd;
        params.telNo = $scope.dlvrTelNo;
        // params.shortNo = $scope.dlvrShortNo;
        params.useYn = $scope.dlvrTelUseYn;
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/membr/info/grade/grade/getMemberClassPointSave.sb", params, function (result) {
            $scope.data = new wijmo.collections.CollectionView([]);
        });
    };

    // $scope.valueCheck = function () {

}]);