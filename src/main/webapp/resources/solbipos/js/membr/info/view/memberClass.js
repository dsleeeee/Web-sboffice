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
    if (membrClassList.length == 0) {
        membrClassList = [{value: "", name: "선택"}, {value: "001", name: "기본등급"}];
    }

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    // $scope._setComboData("defaultYn", membrClassList);

    $scope._getComboDataQuery('067', 'useYn', '');
    $scope._getComboDataQuery('067', 'membrDcYn', '');
    $scope._getComboDataQuery('067', 'membrPointYn', '');
    $scope._getComboDataQuery('067', 'defaultYn', '');
    $scope._getComboDataQuery('054', 'pointSaveFg', '');
    $scope._getComboDataQuery('032', 'membrAnvsrYn', '');

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
        $scope.detailData = data
        event.preventDefault();
    });
    // 신규등록
    $scope.newAdd = function () {
        $scope.detailData = {}
    };
    // 저장
    $scope.classSave = function () {
        $scope.detailData.useYn = 'Y';
        var params = $scope.detailData;
        $scope._postJSONSave.withOutPopUp("/membr/info/grade/grade/classRegist.sb", params, function (response) {
            if (response.data.status == 'OK') {
                $scope._popMsg(messages["cmm.saveSucc"]);
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
        $scope.data = new wijmo.collections.CollectionView(result);
    }
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
        console.log(selectedRow);
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

    // 삭제
    $scope.pointDel = function () {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
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
                s.setCellData(e.row, e.col, beginVal);
            }
        });
    }
    // // 위로 옮기기
    // $scope.up = function(){
    //     var movedRows = 0;
    //     for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
    //         var item = $scope.flex.collectionView.items[i];
    //         if (i > 0 && item.gChk) {
    //             if (!$scope.flex.collectionView.items[i - 1].gChk) {
    //                 movedRows = i - 1;
    //                 var tmpItem = $scope.flex.collectionView.items[movedRows];
    //                 $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
    //                 $scope.flex.collectionView.items[i] = tmpItem;
    //                 $scope.flex.collectionView.commitEdit();
    //                 $scope.flex.collectionView.refresh();
    //             }
    //         }
    //     }
    //     $scope.flex.select(movedRows, 1);
    // };
    //
    // // 아래로 옮기기
    // $scope.down = function(){
    //     var movedRows = 0;
    //     for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
    //         var item = $scope.flex.collectionView.items[i];
    //         if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
    //             if (!$scope.flex.collectionView.items[i + 1].gChk) {
    //                 movedRows = i + 1;
    //                 var tmpItem = $scope.flex.collectionView.items[movedRows];
    //                 $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
    //                 $scope.flex.collectionView.items[i] = tmpItem;
    //                 $scope.flex.collectionView.commitEdit();
    //                 $scope.flex.collectionView.refresh();
    //             }
    //         }
    //     }
    //     $scope.flex.select(movedRows, 1);
    // };
    //
    // // 행 추가
    // $scope.add = function(){
    //     // 파라미터 설정
    //     var params = {};
    //     params.fnkeyNo = '자동채번';
    //     params.posiAdjYn = false;
    //     params.fnkeyUseYn0 = false;
    //     params.fnkeyUseYn1 = false;
    //     params.useYn = true;
    //     // 추가기능 수행 : 파라미터
    //     $scope._addRow(params, 2);
    // };
    //
    // // 행 삭제
    // $scope.del = function(){
    //     for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
    //         var item = $scope.flex.collectionView.items[i];
    //         if(item.gChk){
    //             $scope.flex.collectionView.removeAt(i);
    //         }
    //     }
    // };
    //
    // // 저장
    // $scope.save = function(){
    //     $scope.flex.collectionView.commitEdit();
    //
    //     // 파라미터 설정
    //     var params = [];
    //
    //     for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
    //         $scope.flex.collectionView.itemsRemoved[d].status = 'D';
    //         $scope.flex.collectionView.itemsRemoved[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
    //         params.push($scope.flex.collectionView.itemsRemoved[d]);
    //     }
    //
    //     // dispSeq 재설정
    //     var editItems = [];
    //     for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
    //         if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
    //             editItems.push($scope.flex.collectionView.items[s]);
    //         }
    //     }
    //
    //     for (var s = 0; s < editItems.length; s++) {
    //         editItems[s].dispSeq = (s + 1);
    //         $scope.flex.collectionView.editItem(editItems[s]);
    //         $scope.flex.collectionView.commitEdit();
    //     }
    //
    //     for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
    //         $scope.flex.collectionView.itemsEdited[u].status = 'U';
    //         $scope.flex.collectionView.itemsEdited[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
    //         params.push($scope.flex.collectionView.itemsEdited[u]);
    //     }
    //     for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
    //         $scope.flex.collectionView.itemsAdded[i].status = 'I';
    //         $scope.flex.collectionView.itemsAdded[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
    //         params.push($scope.flex.collectionView.itemsAdded[i]);
    //     }
    //
    //     // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    //     $scope._save('/pos/confg/func/func/save.sb', params, function() {
    //         // 저장 후 재조회
    //         $scope._broadcast('funcCtrl', $scope.getSelectedFuncFg());
    //     });
    // };

}]);