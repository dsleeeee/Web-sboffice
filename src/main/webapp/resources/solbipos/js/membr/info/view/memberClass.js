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
app.controller('memberClassCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberClassCtrl', $scope, $http, false));

    // 기본 회원등급
    membrClass = [{value: "001", name: "기본"}, {value: "002", name: "일반"}];

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    // $scope._setComboData("defaultYn", membrClassList);
    $scope._setComboData("membrClass", membrClass);

    $scope._getComboDataQuery('067', 'useYn', '');
    $scope._getComboDataQuery('067', 'membrDcYn', 'S');
    $scope._getComboDataQuery('067', 'membrPointYn', 'S');
    $scope._getComboDataQuery('067', 'defaultYn', '');
    $scope._getComboDataQuery('054', 'pointSaveFg', '');
    $scope._getComboDataQuery('032', 'membrAnvsrYn', 'S');

    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };
    $scope.userUseYn = false;
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.$apply(function () {
            $scope.data = new wijmo.collections.CollectionView(result);
        });

        if (gvHqOfficeCd === '00000') { // 단독매장
            $scope.userUseYn = true;
        } else { // 프랜차이즈는 본사만 추가 가능
            if (gvStoreCd === '') $scope.userUseYn = true;
        }

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "membrClassType") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        // 페이징???
        // if ($scope._getPagingInfo('curr') > 0) {
        //     params['curr'] = $scope._getPagingInfo('curr');
        // } else {
        //     params['curr'] = 1;
        // }

        // 등급목록
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 회원코드 클릭시 해당 기능 목록 조회
                if (col.binding === "membrClassType") {
                    var selectedData = s.rows[ht.row].dataItem
                    $scope.setSelectedMember(selectedData);
                    var scope = agrid.getScope('memberClassDetailCtrl');
                    scope._broadcast('memberClassDetailCtrl', $scope.getSelectedMember());

                    event.preventDefault();
                }
            }
        });
    };
    $scope.$on("memberClassCtrl", function (event, data) {
        $scope.setSelectedMember(data);
        $scope.detailData = data;
        if ($scope.detailData.defltYn === 'Y') {
            $scope.detailData.membrClass = "001";
        } else {
            $scope.detailData.membrClass = "002";
        }
        event.preventDefault();
    });
    // 신규등록
    $scope.newAdd = function () {
        $scope.detailData = {}
    };
    // 저장
    $scope.classSave = function () {
        if (!$scope.valueCheck()) return false;

        $scope.detailData.useYn = 'Y';
        if ($scope.detailData.membrClass === '001') {
            $scope.detailData.defltYn = 'Y'
        } else {
            $scope.detailData.defltYn = 'N'
        }
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
    // 등급 삭제
    $scope.classDel = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        // 회원 사용여부 '미사용'으로 변경 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/membr/info/grade/grade/remove.sb", params, function () {
            $scope.getMember()
        });

    };
    $scope.getMember = function () {
        var params = {}
        $scope._inquiryMain("/membr/info/grade/view/getMemberClassList.sb", params, function () {
        });
    };

    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {

        //if( $.isEmptyObject($scope.selectedMember) ){

        // 등급코드을 입력하세요.
        var msg = messages["grade.membr.grade.cd"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.membrClassCd)) {
            $scope._popMsg(msg);
            return false;
        }
        // 등급코드 숫자영문.
        var msg = messages["grade.membr.grade.cd"] + messages["cmm.require.number.en"];
        var numChkregexp = /[^A-za-z0-9]/g;
        if (numChkregexp.test($scope.detailData.membrClassCd)) {
            $scope._popMsg(msg);
            return false;
        }

        // 등급명을 입력하세요.
        var msg = messages["grade.membr.grade.nm"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.membrClassNm)) {
            $scope._popMsg(msg);
            return false;
        }
        // 할인를 입력하세요.
        var msg = messages["grade.membr.dc.rate"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.dcRate)) {
            $scope._popMsg(msg);
            return false;
        }
        // 할인를 최소 0 최대 100.
        var msg = messages["grade.membr.dc.rate"] + messages["grade.membr.dc.message"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.detailData.dcRate) || $scope.detailData.dcRate < 0 || $scope.detailData.dcRate > 100) {
            $scope._popMsg(msg);
            return false;
        }
        // 신규가입적립 point 입력하세요.
        var msg = messages["grade.membr.new.join.save.point"] + messages["cmm.require.text"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.detailData.newJoinSavePoint)) {
            $scope._popMsg(msg);
            return false;
        }
        // 최소사용포인트 입력하세요.
        var msg = messages["grade.membr.min.use.point"] + messages["cmm.require.text"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.detailData.minUsePoint)) {
            $scope._popMsg(msg);
            return false;
        }
        // 첫거래적립포인트 입력하세요.
        var msg = messages["grade.membr.first.sale.save.point"] + messages["cmm.require.text"];
        if (numChkregexp.test($scope.detailData.firstSaleSavePoint)) {
            $scope._popMsg(msg);
            return false;
        }
        // 최대사용포인트 입력하세요.
        var msg = messages["grade.membr.max.use.point"] + messages["cmm.require.text"];
        if (numChkregexp.test($scope.detailData.maxUsePoint)) {
            $scope._popMsg(msg);
            return false;
        }
        // 할인한도액 입력하세요.
        var msg = messages["grade.membr.dc.max"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.dcLimitAmt)) {
            $scope._popMsg(msg);
            return false;
        }
        // 할인한도액은 숫자만 입력가능합니다.
        var msg = messages["grade.membr.dc.max"] + messages["cmm.require.number"];
        var numChkregexp = /[^0-9]/g;
        if(numChkregexp.test( $scope.detailData.dcLimitAmt )) {
            $scope._popMsg(msg);
            return false;
        }

        // 기념일포인트 입력하세요.
        var msg = messages["grade.membr.anvsr.save.point"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.anvsrSavePoint)) {
            $scope._popMsg(msg);
            return false;
        }
        // 기념일포인트 선택하세요.
        var msg = messages["grade.membr.anvsr.save.point"] + messages["cmm.require.text"];
        if (isNull($scope.membrAnvsrYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }



        // var msg = messages["grade.membr.grade.cd"]+messages["cmm.require.select"];
        // if( isNull( $scope.regStoreCdCombo.selectedValue )) {
        //     $scope._popMsg(msg);
        //     return false;
        // }
        return true;
    };

    $scope.$watch('detailData.anvsrPointSaveFg', function () {
       if($scope.detailData.anvsrPointSaveFg === '') {
           $scope.detailData.anvsrSavePoint = '';
       }
    });

}]);


app.controller('memberClassDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberClassDetailCtrl', $scope, $http, true));
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
        $scope.payCdDataMap = new wijmo.grid.DataMap(payCd, 'value', 'name');
        if (gvHqOfficeCd === '00000') { // 단독매장
            $scope.userUseYn = true;
        } else { // 프랜차이즈는 본사만 추가 가능
            if (gvStoreCd === '') $scope.userUseYn = true;
        }
        // bindColumnGroup 생성
        // bindColumnGroups(s, dataHeader);

        if ($scope.userUseYn === false) {
            // ReadOnly 효과설정
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "payCd" || col.binding === "membrOrgnCd" || col.binding === "accRate") {
                        col.isReadOnly = true;
                    }
                }
            });
        }
    };
    $scope.$on("memberClassDetailCtrl", function (event, data) {
        $scope.setSelectedMember(data);
        $scope.getClassList();
        event.preventDefault();
    });
    $scope.$on("memberClassDetailInit", function () {
        $scope.data = new wijmo.collections.CollectionView([]);
    })
    // 상세 조회
    $scope.getClassList = function () {

        var params = {};
        params.membrClassCd = $scope.getSelectedMember().membrClassCd;
        params.membrOrgnCd = $scope.getSelectedMember().membrOrgnCd;

        $scope.$broadcast('loadingPopupActive');
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/membr/info/grade/view/getMemberClassDetail.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            var list = response.data.data.list.mcp;
            var detail = response.data.data.list.mcd;
            $scope.classData = detail;
            $scope.initData = list;
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
            } else {
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                $scope.data = data;

            }
            var classScope = agrid.getScope('memberClassCtrl');
            classScope._broadcast('memberClassCtrl', $scope.getSelectedMember(detail));
            $("button").show();

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
    };

    // 포인트 적립옵션 추가
    $scope.pointAdd = function () {
        var gridRepresent = agrid.getScope("memberClassCtrl");
        var selectedRow = gridRepresent.flex.selectedRows[0]._data;
        // 파라미터 설정
        var params = {};

        if ($scope.initData === undefined || $scope.initData === null) {
            $scope._popMsg(messages["grade.membr.add.message"]);
        } else {
            params.status = "I";
            params.gChk = true;
            params.accRate = 0;
            params.payCd = "선택";
            params.membrOrgnCd = selectedRow.membrOrgnCd;
            params.membrClassCd = selectedRow.membrClassCd;
            params.membrClassType = selectedRow.membrClassType;
            // 추가 row
            $scope._addRow(params);
        }

    };

    // 일괄등록 버튼
    $scope.pointTotal = async function () {
        if ($scope.initData === undefined || $scope.initData === null) {
            $scope._popMsg(messages["grade.membr.add.message"]);
        } else {
            $scope.data = await new wijmo.collections.CollectionView([]);
            await $scope.getTotalAdd();
        }
    };
    // 추가 row
    $scope.getTotalAdd = async function () {
        var gridRepresent = agrid.getScope("memberClassCtrl");
        var selectedRow = gridRepresent.flex.selectedRows[0]._data;
        var params = {};
        for (var i = 0; i < payCd.length + 1; i++) {
            if ($scope.classData === undefined) {
                if (i > 0) {
                    params.gChk = true;
                    params.accRate = $scope.membrTotal;
                    params.payCd = payCd[i - 1].value;
                    params.membrOrgnCd = selectedRow.membrOrgnCd;
                    params.membrClassCd = selectedRow.membrClassCd;
                    params.membrClassType = selectedRow.membrClassType;
                    $scope._addRow(params);
                }
            } else {
                if (i > 0) {
                    params.gChk = true;
                    params.accRate = $scope.membrTotal;
                    params.payCd = payCd[i - 1].value;
                    params.membrOrgnCd = selectedRow.membrOrgnCd;
                    params.membrClassCd = selectedRow.membrClassCd;
                    params.membrClassType = selectedRow.membrClassType;
                    $scope._addRow(params);
                } else {
                    $scope._addRow({});
                }
            }
        }
    }

    // 저장
    $scope.pointSave = function () {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            var item = $scope.flex.collectionView.itemsAdded[i];
            if(item.payCd === "선택" || item.payCd === null){
                $scope._popMsg(messages["grade.membr.pay.code"] + messages["cmm.require.select"]);
                return false;
            }
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/membr/info/grade/grade/getMemberClassPointSave.sb", params, function (result) {
            $scope.data = new wijmo.collections.CollectionView([]);
        });
    };

    // $scope.valueCheck = function () {

    // 삭제
    $scope.pointDel = function () {
        for (var i = $scope.flex.collectionView.items.length-1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };


    // 초기값
    var beginVal;
    $scope.beginningEdit = function (s, e) {
        beginVal = s.rows[e.row].dataItem.payCd;
    };
    // 수정값
    var editVal;
    $scope.cellEditEnded = function (s, e) {
        editVal = s.rows[e.row].dataItem.payCd;
        s.rows.forEach((val, i) => {
            if (e.row !== i && val.dataItem.payCd === editVal) {
                $scope._popMsg(messages["cmm.require.duplicate.select"]);
                s.rows[e.row].dataItem.payCd = beginVal;
            }
        });
    }

}]);