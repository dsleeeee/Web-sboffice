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
    membrClass = [{value: "002", name: "일반"}, {value: "001", name: "기본"}];

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.$apply(function () {
            $scope.data = new wijmo.collections.CollectionView(result);
        });

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
                    $scope.newAdd();
                    var selectedData = s.rows[ht.row].dataItem;
                    $scope.setSelectedMember(selectedData);
                    var scope = agrid.getScope('memberClassDetailCtrl');
                    scope._broadcast('memberClassDetailCtrl', $scope.getSelectedMember());

                    event.preventDefault();
                }
            }
        });
    };
   $scope.$on("memberClassCtrl", function (event, data) {
        // $scope.setSelectedMember(data);
        console.log(data);
        // $scope.detailData.membrClassNm = '';
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
        $scope.detailData = {};
        $scope.detailData.anvsrPointSaveFg = "";
        $scope.detailData.dcAccPointYn = "";
        $scope.detailData.membrClass = "002";
        $scope.detailData.pointSaveFg = "1";
        $scope.detailData.useAccPointYn = "";
        console.log($scope.detailData);
        console.log($scope.detailData.membrClassCd);
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
        $scope.detailData.anvsrPointSaveFg = String(nvl($scope.detailData.anvsrPointSaveFg, '0'));
        $scope.detailData.membrClassManageFg = membrClassManageFg;

        var params = $scope.detailData;

        // 저장
        $scope._postJSONSave.withOutPopUp("/membr/info/grade/grade/classRegist.sb", params, function (response) {
            if(response.data.data.data === 0) {
                $scope._popMsg(response.data.data.message); // 기본등급은 1개 존재해야합니다.
            } else {
                $scope._popMsg(response.data.data.message); // 저장되었습니다.
                $scope.getMember();
                var scope = agrid.getScope('memberClassDetailCtrl');
                scope._gridDataInit();
            }
        });
    };

    // 등급 삭제
    $scope.classDel = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if ($scope.flex.collectionView.items[i].defltYn === "Y") {
                    $scope._popMsg(messages["grade.membr.deflt.yn.chk"]);
                    return false
                }
                $scope.flex.collectionView.items[i].membrClassManageFg = membrClassManageFg;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        $scope._popConfirm(messages["grade.membr.delConfirm"], function() {
            if(params.length > 0){
                $.postJSONArray("/membr/info/grade/grade/removeChk.sb", params, function (result) {
                    if (result.data.deleteChk > 0) {
                        console.log(result.data.membrClassNm);
                        $scope._popMsg("[" + result.data.membrClassCd + "]" + result.data.membrClassNm + messages["grade.membr.chk.yn"]);
                        return false;
                    } else {
                        // 회원 사용여부 '미사용'으로 변경 수행 : 저장URL, 파라미터, 콜백함수
                        $scope._save("/membr/info/grade/grade/remove.sb", params, function () {
                            $scope.getMember();
                            $scope.newAdd();
                            var scope = agrid.getScope('memberClassDetailCtrl');
                            scope._gridDataInit();
                        });
                    }
                });
            }else{
                $scope._popMsg(messages["cmm.not.select"]);
                return false;
            }
        });
    };
    $scope.getMember = function () {
        var params = {};
        params.membrClassManageFg = membrClassManageFg;
        $scope._inquiryMain("/membr/info/grade/view/getMemberClassList.sb", params, function () {
        });
    };

    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {

        //if( $.isEmptyObject($scope.selectedMember) ){

        // 등급코드을 입력하세요.
        /*var msg = messages["grade.membr.grade.cd"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.membrClassCd)) {
            // $scope._popMsg(msg);
            $scope._popMsg(msg, function () {
                $("#membrClassCd").focus();
            });
            return false;
        }*/
        // 등급코드 숫자영문.
        var msg = messages["grade.membr.grade.cd"] + messages["cmm.require.number.en"];
        var numChkregexp = /[^A-za-z0-9]/g;
        if (numChkregexp.test($scope.detailData.membrClassCd)) {
            $scope._popMsg(msg, function () {
                $("#membrClassCd").focus();
            });
            return false;
        }

        // 등급명을 입력하세요.
        var msg = messages["grade.membr.grade.nm"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.membrClassNm)) {
            $scope._popMsg(msg, function () {
                $("#membrClassNm").focus();
            });
            return false;
        }
        // 할인를 입력하세요.
        var msg = messages["grade.membr.dc.rate"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.dcRate)) {
            $scope._popMsg(msg, function () {
                $("#dcRate").focus();
            });
            return false;
        }
        // 할인를 최소 0 최대 100.
        var msg = messages["grade.membr.dc.rate"] + messages["grade.membr.dc.message"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.detailData.dcRate) || $scope.detailData.dcRate < 0 || $scope.detailData.dcRate > 100) {
            $scope._popMsg(msg, function () {
                $("#dcRate").focus();
            });
            return false;
        }
        // 신규가입적립 point 입력하세요.
        var msg = messages["grade.membr.new.join.save.point"] + messages["cmm.require.text"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.detailData.newJoinSavePoint)) {
            $scope._popMsg(msg, function () {
                $("#newJoinSavePoint").focus();
            });
            return false;
        }
        // 최소사용포인트 입력하세요.
        var msg = messages["grade.membr.min.use.point"] + messages["cmm.require.text"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.detailData.minUsePoint)) {
            $scope._popMsg(msg, function () {
                $("#minUsePoint").focus();
            });
            return false;
        }
        // 첫거래적립포인트 입력하세요.
        var msg = messages["grade.membr.first.sale.save.point"] + messages["cmm.require.text"];
        if (numChkregexp.test($scope.detailData.firstSaleSavePoint)) {
            $scope._popMsg(msg, function () {
                $("#firstSaleSavePoint").focus();
            });
            return false;
        }
        // 최대사용포인트 입력하세요.
        var msg = messages["grade.membr.max.use.point"] + messages["cmm.require.text"];
        if (numChkregexp.test($scope.detailData.maxUsePoint)) {
            $scope._popMsg(msg, function () {
                $("#maxUsePoint").focus();
            });
            return false;
        }
        // 할인한도액 입력하세요.
        var msg = messages["grade.membr.dc.max"] + messages["cmm.require.text"];
        if (isNull($scope.detailData.dcLimitAmt)) {
            $scope._popMsg(msg, function () {
                $("#dcLimitAmt").focus();
            });
            return false;
        }
        // 할인한도액은 숫자만 입력가능합니다.
        var msg = messages["grade.membr.dc.max"] + messages["cmm.require.number"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.detailData.dcLimitAmt)) {
            $scope._popMsg(msg, function () {
                $("#dcLimitAmt").focus();
            });
            return false;
        }

        // 기념일포인트 입력하세요.
        var msg = messages["grade.membr.anvsr.save.point"] + messages["cmm.require.text"];
        if (!isNull($scope.membrAnvsrYnCombo.selectedValue)) {
            if (isNull($scope.detailData.anvsrSavePoint)) {
                $scope._popMsg(msg, function () {
                    $("#anvsrSavePoint").focus();
                });
            return false;
            } 
        } else {
            $scope.detailData.anvsrSavePoint = '0';
        }

        // var msg = messages["grade.membr.grade.cd"]+messages["cmm.require.select"];
        // if( isNull( $scope.regStoreCdCombo.selectedValue )) {
        //     $scope._popMsg(msg);
        //     return false;
        // }
        return true;
    };

    // $scope.$watch('detailData.anvsrPointSaveFg', function () {
    //     if ($scope.detailData.anvsrPointSaveFg !== undefined) {
    //         if ($scope.detailData.anvsrPointSaveFg === '') {
    //             $scope.detailData.anvsrSavePoint = '';
    //         }
    //     }
    // });
    //
    // $scope.$watch('detailData.pointSaveFg', function () {
    //     var classDetailScope = agrid.getScope('memberClassDetailCtrl');
    //     if ($scope.detailData.pointSaveFg == 1 && classDetailScope.classData.pointSaveFg == 2 && $scope.detailData.pointSaveFg !== undefined) {
    //         $scope._popMsg(messages["grade.membr.point.chk"]);
    //     }
    // });

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        payCd.unshift({name: "현금영수", value: "00"});
        $scope.payCdDataMap = new wijmo.grid.DataMap(payCd, 'value', 'name');
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
        params.membrOrgnCd = $scope.getSelectedMember().membrOrgnCd;
        params.membrClassCd = $scope.getSelectedMember().membrClassCd;
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd = storeCd;
        params.orgnFg = orgnFg;
        params.membrClassManageFg = membrClassManageFg;

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
            classScope._broadcast('memberClassCtrl', detail);
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
    $scope.pointTotal = function () {
        if ($scope.initData === undefined || $scope.initData === null) {
            $scope._popMsg(messages["grade.membr.point.total.add"]);
        } else {
            $scope._gridDataInit();
            $scope.$broadcast('loadingPopupActive');
            setTimeout(function () {
                $scope.$broadcast('loadingPopupInactive');
                $scope.getTotalAdd();
            }, 100);

        }
    };
    // 추가 row
    $scope.getTotalAdd = function () {
        var gridRepresent = agrid.getScope("memberClassCtrl");
        var selectedRow = gridRepresent.flex.selectedRows[0]._data;
        var params = {};
        console.log(payCd)
        for (var i = 0; i < payCd.length + 1; i++) {
            // for (var i = 20; i < 0;  i--) {
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
                    // $scope._addRow({});
                }
            }
        }
    };

    // 저장
    $scope.pointSave = function () {
        // 파라미터 설정
        var params = new Array();
        var vScope = agrid.getScope('memberClassCtrl');

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            var item = $scope.flex.collectionView.itemsEdited[i];

            if(vScope.pointSaveFgCombo.selectedValue === "2") {
                var numChkregexp = /[^0-9]/g;
                if (numChkregexp.test(item.accRate)) {
                    $scope._popMsg(messages["grade.membr.point.list.amt"] + messages["cmm.require.number"]);
                    return false;
                }
            }

            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            var item = $scope.flex.collectionView.itemsAdded[i];
            if (item.payCd === "선택" || item.payCd === null) {
                $scope._popMsg(messages["grade.membr.pay.code"] + messages["cmm.require.select"]);
                return false;
            }

            if(vScope.pointSaveFgCombo.selectedValue === "2") {
                var numChkregexp = /[^0-9]/g;
                if (numChkregexp.test(item.accRate)) {
                    $scope._popMsg(messages["grade.membr.point.list.amt"] + messages["cmm.require.number"]);
                    return false;
                }
            }

            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/membr/info/grade/grade/getMemberClassPointSave.sb", params, function (result) {
            $scope._popMsg(messages["cmm.saveSucc"]);
            // $scope.data = new wijmo.collections.CollectionView([]);
        });
    };

    // $scope.valueCheck = function () {

    // 삭제
    $scope.pointDel = function () {
        $scope._popConfirm(messages["grade.membr.delConfirm"], function() {
            var params = new Array();
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                $scope.flex.collectionView.itemsRemoved[i].status = "D";
                params.push($scope.flex.collectionView.itemsRemoved[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            // $.postJSONArray("/membr/info/grade/grade/getMemberClassPointDel.sb", params, function (result) {
            $scope._save("/membr/info/grade/grade/getMemberClassPointDel.sb", params, function () {
                $scope.getClassList()
                // $scope.data = new wijmo.collections.CollectionView([]);
            });
        });
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
        for (var i = 0; i < s.rows.length; i++) {
            if (e.row !== i &&  s.rows[i].dataItem.payCd === editVal) {
                $scope._popMsg(messages["cmm.require.duplicate.select"]);
                s.rows[e.row].dataItem.payCd = beginVal;
            }
        }
        // s.rows.forEach((val, i) => {
        //     if (e.row !== i && val.dataItem.payCd === editVal) {
        //         $scope._popMsg(messages["cmm.require.duplicate.select"]);
        //         s.rows[e.row].dataItem.payCd = beginVal;
        //     }
        // });
    }

}]);