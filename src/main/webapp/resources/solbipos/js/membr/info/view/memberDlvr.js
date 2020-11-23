/****************************************************************
 *
 * 파일명 : memberDlvr.js (memberDeli.jsp)
 * 설  명 : 배달지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.00.00     ㄱㄱㄱ      1.0           두어시스템
 * 2020.11.16     김설아      2.0           소스정리 및 오류수정 / 배달주소지 수정,삭제,저장 기능추가
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  배달주소지 그리드 생성
 */
app.controller('memberDlvrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberDlvrCtrl', $scope, $http, false));

    $scope._getComboDataQuery('067', 'useYn', '');

    $scope.dlvrLzone = [];
    $scope.dlvrMzone = [];
    $scope.userUseYn = false;
    $scope.dlvrStoreCd = '';

    // 선택 회원
    $scope.selectedAddr;
    $scope.setSelectedAddr = function (member) {
        $scope.selectedAddr = member;
    };
    $scope.getSelectedAddr = function () {
        return $scope.selectedAddr;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "addr") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 회원선택
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                // 주소 클릭시 상세정보 팝업
                if (col.binding === "addr") {
                    var selectedData = s.rows[ht.row].dataItem;
                    $scope.setSelectedAddr(selectedData);
                    $scope._broadcast('dlvrAddrDetail', selectedData);
                    event.preventDefault();
                }
            }
        });
    };

    // 저장
    $scope.saveAddr = function () {
        if (!$scope.valueCheck()) return false;
        var params = {};
        params.membrOrgnCd = $scope.memberParmas.membrOrgnCd;
        params.membrNo = $scope.memberParmas.membrNo;
        params.regStoreCd = $scope.memberParmas.regStoreCd;
        params.dlvrLzoneCd = $scope.lZoneListCd;
        params.dlvrMzoneCd = $scope.mZoneListCd;
        params.addr = $scope.dlvrAddr;
        params.addrDtl = $scope.dlvrAddr;
        params.useYn = $scope.dlvrUseYn;

        var memberInfoScope = agrid.getScope('memberCtrl');

        $scope._postJSONSave.withPopUp("/membr/info/view/base/registDlvrInfo.sb", params, function (response) {
            $scope._popMsg(messages["cmm.saveSucc"]);
            // $scope.memberRegistLayer.hide();
            memberInfoScope.getMemberList();
            // 초기화
            $scope.saveInitAddr();
            $scope._broadcast('getMemberDlvr', params);
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
                    $scope._popMsg(messages['regist.delivery.data.none']);
                    return false;
                }
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                $scope.data = data;
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
        } else if (res.data.status === 'FAIL') {
            if (isMsg) {
                $scope._popMsg('Ajax Fail By HTTP Request');
            }
            return false;
        } else if (res.data.status === 'SESSION_EXFIRE') {
            if (isMsg) {
                $scope._popMsg(res.data.message, function () {
                    location.href = res.data.url;
                });
            }
            return false;
        } else if (res.data.status === 'SERVER_ERROR') {
            if (isMsg) {
                $scope._popMsg(res.data.message);
            }
            return false;
        } else {
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
                $scope.dlvrMzone = response.data.data.mZoneList;
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
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

    $scope.$on("dlvrAddrDetail", function (event, params, mode) {
        var memberInfoScope = agrid.getScope('memberCtrl');
        $scope.lZoneListCd = params.dlvrLzoneCd;
        $scope.mZoneListCd = params.dlvrMzoneCd;
        $scope.dlvrAddr = params.addrDtl;
        $scope.dlvrUseYn = params.useYn;
        memberInfoScope.getMemberList();

        event.preventDefault();
    });

    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {

        // 배달구역(대) 입력하세요.
        var msg = messages["dlvr.membr.area"] + messages["cmm.require.text"];
        if (isNull($scope.lZoneListCd)) {
            $scope._popMsg(msg);
            return false;
        }
        // 배달구역(중) 입력하세요.
        var msg = messages["dlvr.membr.area"] + messages["cmm.require.text"];
        if (isNull($scope.mZoneListCd)) {
            $scope._popMsg(msg);
            return false;
        }
        // 상세주소 입력하세요.
        var msg = messages["dlvr.membr.areaDetail"] + messages["cmm.require.text"];
        if (isNull($scope.dlvrAddr)) {
            $scope._popMsg(msg);
            return false;
        }
        // 주소 최대길이 체크
        if (nvl($scope.dlvrAddr, '') !== '' && nvl($scope.addrDtl + '', '').getByteLengthForOracle() > 60) {
            msg = messages["regist.delivery.addr.dtl"] + messages["excelUpload.overLength"] + " 60 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }
        return true;
    };

    //수정
    $scope.saveEditAddr = function () {
        var params = $scope.getSelectedAddr();
        params.membrOrgnCd = $scope.memberParmas.membrOrgnCd;
        params.membrNo = $scope.memberParmas.membrNo;
        params.regStoreCd = $scope.memberParmas.regStoreCd;
        params.dlvrLzoneCd = $scope.lZoneListCd;
        params.dlvrMzoneCd = $scope.mZoneListCd;
        params.addrDtl = $scope.dlvrAddr;
        params.useYn = $scope.dlvrUseYn;
        $scope._postJSONSave.withPopUp("/membr/info/view/base/updateDlvrAddrInfo.sb", params, function () {
            // 초기화
            $scope.saveInitAddr();
            $scope._broadcast('getMemberDlvr', params);
        });
    };

    // 삭제
    $scope.saveDelAddr = function () {
        var params = $scope.getSelectedAddr();
        $scope._postJSONSave.withPopUp("/membr/info/view/base/deleteDlvrAddrInfo.sb", params, function () {
            // 초기화
            $scope.saveInitAddr();
            params.regStoreCd = $scope.memberParmas.regStoreCd; // 배달구역 조회시 필요
            $scope._broadcast('getMemberDlvr', params);
        });
    };

    // 초기화
    $scope.saveInitAddr = function () {
        $scope.setSelectedAddr ({});
        $scope.dlvrAddr = "";
        $scope.dlvrUseYn = "";
    };
}]);




/**
 *  배달전화번호 그리드 생성
 */
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
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "telNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 회원선택
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                // 연락처 클릭시 상세정보 팝업
                if (col.binding === "telNo") {
                    var selectedData = s.rows[ht.row].dataItem;
                    $scope.setSelectedMember(selectedData);
                    $scope._broadcast('dlvrTelDetail', selectedData);
                    event.preventDefault();
                }
            }
        });
    };

    $scope.$on("getMemberDlvrTel", function (event, params, mode) {
        $scope.memberParmas = params;
        var url = "/membr/info/view/view/getDlvrTelList.sb";
        $scope.$broadcast('loadingPopupActive');
        // 페이징 처리
        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }
        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: url, /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            $scope.pageData = response.data.data.page;
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (_httpStatusCheck(response, true)) {
                // this callback will be called asynchronously
                // when the response is available
                var list = response.data.data.list;
                if (list.length === undefined || list.length === 0) {
                    $scope.data = new wijmo.collections.CollectionView([]);
                    if (response.data.message) {

                        // 페이징 처리
                        $scope._setPagingInfo('ctrlName', $scope.name);
                        $scope._setPagingInfo('pageScale', 10);
                        $scope._setPagingInfo('curr', 1);
                        $scope._setPagingInfo('totCnt', 1);
                        $scope._setPagingInfo('totalPage', 1);

                        $scope._broadcast('drawPager');
                        $scope._popMsg(messages["regist.delivery.tel.data.none"]);
                    }
                    return false;
                }
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                $scope.data = data;

                // 페이징 처리
                if (response.data.data.page && response.data.data.page.curr) {
                    var pagingInfo = response.data.data.page;
                    $scope._setPagingInfo('ctrlName', $scope.name);
                    $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                    $scope._setPagingInfo('curr', pagingInfo.curr);
                    $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                    $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                    $scope._broadcast('drawPager');
                }
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

        // http 조회 후 status 체크
        $scope._httpStatusCheck = function (res, isMsg) {
            return _httpStatusCheck(res, isMsg);
        };

        // private
        function _httpStatusCheck(res, isMsg) {
            if (res.data.status === 'OK') {
                return true;
            } else if (res.data.status === 'FAIL') {
                if (isMsg) {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                }
                return false;
            } else if (res.data.status === 'SESSION_EXFIRE') {
                if (isMsg) {
                    $scope._popMsg(res.data.message, function () {
                        location.href = res.data.url;
                    });
                }
                return false;
            } else if (res.data.status === 'SERVER_ERROR') {
                if (isMsg) {
                    $scope._popMsg(res.data.message);
                }
                return false;
            } else {
                if (isMsg) {
                    var msg = res.data.status + ' : ' + res.data.message;
                    $scope._popMsg(msg);
                }
                return false;
            }
        }
    });

    $scope.$on("dlvrTelDetail", function (event, params, mode) {
        var memberInfoScope = agrid.getScope('memberCtrl');
        $scope.memberTel.dlvrTelNo = params.telNo;
        $scope.memberTel.dlvrTelUseYn = params.useYn;
        memberInfoScope.getMemberList();

        event.preventDefault();
    });

    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {
        // 전화번호는 숫자만 입력할 수 있습니다.
        // var msg = messages["regist.delivery.tel"]+messages["cmm.require.number"];
        // var numChkregexp = /[^0-9]/g;
        // if(numChkregexp.test( $scope.member.dlvrTelNo )) {
        //     $scope._popMsg(msg);
        //     return false;
        // }

        // 연락처 입력하세요.
        var msg = messages["regist.tel"] + messages["cmm.require.text"];
        if (isNull($scope.memberTel.dlvrTelNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 전화번호 최대길이 체크
        if (nvl($scope.memberTel.dlvrTelNo, '') !== '' && nvl($scope.memberTel.dlvrTelNo + '', '').getByteLengthForOracle() > 14) {
            msg = messages["regist.delivery.tel"] + messages["excelUpload.overLength"] + " 14 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        return true;
    };

    // 저장
    $scope.saveTel = function () {
        if ($scope.memberParmas.regStoreCd) {
            $scope.memberParmas.dlvrStoreCd = $scope.memberParmas.regStoreCd;
        }
        if (!$scope.valueCheck()) return false;
        // 파라미터 설정
        var memberInfoScope = agrid.getScope('memberCtrl');
        var params = {};

        params.membrOrgnCd = $scope.memberParmas.membrOrgnCd;
        params.membrNo = $scope.memberParmas.membrNo;
        params.regStoreCd = $scope.memberParmas.dlvrStoreCd;
        params.telNo = $scope.memberTel.dlvrTelNo;
        params.shortNo = $scope.memberParmas.shortNo;
        params.useYn = $scope.memberTel.dlvrTelUseYn;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/membr/info/view/base/registDlvrTelInfo.sb", params, function (response) {
            $scope._popMsg(messages["cmm.saveSucc"]);
            // $scope.memberRegistLayer.hide();
            memberInfoScope.getMemberList();
            // 초기화
            $scope.saveInit();
            $scope._broadcast('getMemberDlvrTel', params);
        });
    };

    //수정
    $scope.saveEdit = function () {
        var params = $scope.getSelectedMember();
        params.telNo = $scope.memberTel.dlvrTelNo;
        params.useYn = $scope.memberTel.dlvrTelUseYn;
        $scope._postJSONSave.withPopUp("/membr/info/view/base/updateDlvrTelInfo.sb", params, function (result) {
            $scope._popMsg(messages["cmm.saveSucc"]);
            // $scope.$emit("responseGet", result.data.data, $scope.saveMode);
            // $scope.memberRegistLayer.hide();
            // $scope.memberInfoDetailLayer.hide();
            // memberInfoScope.getMemberList();
            // 초기화
            $scope.saveInit();
            $scope._broadcast('getMemberDlvrTel', params);
        });
    };

    // 초기화
    $scope.saveInit = function () {
        $scope.setSelectedMember({});
        $scope.memberTel = {}
    };

    // 삭제
    $scope.saveDel = function () {
        var params = $scope.getSelectedMember();
        params.telNo = $scope.memberTel.dlvrTelNo;
        params.useYn = $scope.memberTel.dlvrTelUseYn;
        $scope._postJSONSave.withPopUp("/membr/info/view/base/deleteDlvrTelInfo.sb", params, function (result) {
            $scope._popMsg(messages["cmm.saveSucc"]);
            // $scope.$emit("responseGet", result.data.data, $scope.saveMode);
            // $scope.memberRegistLayer.hide();
            // $scope.memberInfoDetailLayer.hide();
            // memberInfoScope.getMemberList();
            // 초기화
            $scope.saveInit();
            $scope._broadcast('getMemberDlvrTel', params);
        });
    };

}]);