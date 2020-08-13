/****************************************************************
 *
 * 파일명 : dlvr.js
 * 설  명 : 배달지조회및 변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.15    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('dlvrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrCtrl', $scope, $http, $timeout, true));
    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    // 회원등급
    $scope._setComboData("rMemberClass", memberClassList);
    $scope._setComboData("rMemberDlvrLzone", memberDlvrLzone);
    $scope._setComboData("rUseYn", useDataMap);
    memberClassList.unshift({name: "전체", value: ""});
    memberDlvrLzone.unshift({name: "전체", value: ""});

    $scope._getComboDataQuery('072', 'emailRecvYn', 'A');
    $scope._getComboDataQuery('072', 'smsRecvYn', 'A');
    $scope._getComboDataQuery('032', 'anvType', 'A');
    $scope._getComboDataQuery('077', 'periodType', 'A');
    $scope._getComboDataQuery('076', 'weddingYn', 'A');
    $scope._getComboDataQuery('055', 'gendrFg', 'A');
    $scope._getComboDataQuery('067', 'useYn', 'A');

    $scope.dlvrMzone = [];
    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };


    // // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');
        $scope.memberClassDataMap = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
    };


    $scope.$on("dlvrCtrl", function (event, data) {
        $scope.searchDlvrList();
        event.preventDefault();
        var scope = agrid.getScope('dlvrTelCtrl');
        scope._broadcast('dlvrTelList');
    });

//
    $scope.searchDlvrList = function () {
        var params = {};
        params.membrNo = $scope.membrNo
        params.membrNm = $scope.membrNm
        params.memberClass = $scope.memberClassCd;
        params.lZoneListCd = $scope.lZoneListCd;
        params.mZoneListCd = $scope.mZoneListCd;
        params.useYn = $scope.useYn;
        $scope._inquiryMain("/membr/info/dlvr/dlvr/getDlvrList.sb", params, function () {
        }, false);

    };

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


    $scope.$watch('dlvrLzoneCd', function () {
        var params = {};
        params.dlvrLzoneCd = $scope.dlvrLzoneCd;
        $http({
            method: 'POST', //방식
            url: '/membr/info/dlvr/dlvr/getDlvrMzoneList.sb', /* 통신할 URL */
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

    $scope.$watch('totDlvrLzoneCd', function () {
        var params = {};
        params.dlvrLzoneCd = $scope.totDlvrLzoneCd;
        $http({
            method: 'POST', //방식
            url: '/membr/info/dlvr/dlvr/getDlvrMzoneList.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if (_httpStatusCheck(response, true)) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.totDlvrMzone = response.data.data.mZoneList;
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

    // 배달구역 일괄적용
    $scope.dlvrZoneSetting = function () {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].addr = $scope.totDlvrLzoneCd + ' ' + $scope.totDlvrMzoneCd;
                $scope.flex.collectionView.commitEdit();
                $scope.flex.collectionView.refresh();
            }
        }
    };

    // 배달지 사용여부 일괄적용
    $scope.dlvrZoneUseSetting = function () {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].useYn = $scope.totUseYn;
                $scope.flex.collectionView.commitEdit();
                $scope.flex.collectionView.refresh();
            }
        }
    };

    // 전화번호 일괄적용
    $scope.telNoSetting = function () {
        var scope = agrid.getScope('dlvrTelCtrl');
        for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
            if (scope.flex.collectionView.items[i].gChk) {
                scope.flex.collectionView.items[i].telNo = $scope.totTelNo;
                scope.flex.collectionView.commitEdit();
                scope.flex.collectionView.refresh();
            }
        }
    };

    // 전화사용여부 일괄적용
    $scope.telNoUseSetting = function () {
        var scope = agrid.getScope('dlvrTelCtrl');
        for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
            if (scope.flex.collectionView.items[i].gChk) {
                scope.flex.collectionView.items[i].useYn = $scope.totTelUseYn;
                scope.flex.collectionView.commitEdit();
                scope.flex.collectionView.refresh();
            }
        }
    };


    // 엑셀 다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '배달주소지_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 배달주소지 저장
    $scope.infoSave = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/membr/info/dlvr/dlvr/saveDlvr.sb", params, function (result) {
            $scope.searchDlvrList();
        });
    };

    // 배달주소지 삭제
    $scope.infoDelete = function () {
        let params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/membr/info/dlvr/dlvr/deleteDlvr.sb", params, function (result) {
            $scope.searchDlvrList();
        });
    };
}]);


app.controller('dlvrTelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrTelCtrl', $scope, $http, $timeout, true));
    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    // 회원등급
    $scope._setComboData("rMemberClass", memberClassList);
    $scope._setComboData("rUseYn", useDataMap);
    memberClassList.unshift({name: "전체", value: ""});

    $scope._getComboDataQuery('072', 'emailRecvYn', 'A');
    $scope._getComboDataQuery('072', 'smsRecvYn', 'A');
    $scope._getComboDataQuery('032', 'anvType', 'A');
    $scope._getComboDataQuery('077', 'periodType', 'A');
    $scope._getComboDataQuery('076', 'weddingYn', 'A');
    $scope._getComboDataQuery('055', 'gendrFg', 'A');
    $scope._getComboDataQuery('067', 'useYn', 'A');


    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };


    // // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');
        $scope.memberClassDataMap = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
    };

    $scope.$on("dlvrTelList", function (event, data) {
        $scope.searchDlvrTelList();
        event.preventDefault();
    });
    $scope.searchDlvrTelList = function () {
        var params = {};
        params.membrNo = $scope.membrNo;
        params.membrNm = $scope.membrNm;
        params.telNo = $scope.telNo;
        params.useYn = $scope.dlvrTelUseYn;
        $scope._inquiryMain("/membr/info/dlvr/dlvr/getDlvrTelList.sb", params, function () {
        }, false);
    };

    // 엑셀 다운로드
    $scope.telExcelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '배달전화번호_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 배달전화번호 저장
    $scope.infoSave = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/membr/info/dlvr/dlvr/saveDlvrTel.sb", params, function (result) {
            $scope.searchDlvrTelList();
        });
    };

    $scope.infoDelete = function () {
        let params = new Array();
        for (var i = $scope.flex.collectionView.items.length-1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
        $scope.infoDeleteSave();
    }

    // 배달전화번호 삭제
    $scope.infoDeleteSave = function () {
        let params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        $.postJSONArray("/membr/info/dlvr/dlvr/deleteDlvrTel.sb", params, function (result) {
            $scope.searchDlvrTelList();
        });
    }
}]);