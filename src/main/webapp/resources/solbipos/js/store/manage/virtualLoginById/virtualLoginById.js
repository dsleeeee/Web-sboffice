/****************************************************************
 *
 * 파일명 : virtualLoginById.js
 * 설  명 : 가상로그인(아이디별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.02.07     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 가상로그인 그리드 생성
 */
app.controller('virtualLoginByIdCtrl',  ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('virtualLoginByIdCtrl', $scope, $http, true));

    // 가상로그인 개수
    $scope.popupCnt = 0;

    // 접속 사용자의 권한
    $scope.userOrgnFg = gvOrgnFg;

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("srchClsFg", clsFg);
    $scope._setComboData("srchStatFg", sysStatFg);

    if($scope.userOrgnFg == "H"){
        $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList);             // 매장브랜드
        $scope._setComboData("momsTeamCombo", momsTeamComboList);                       // 팀별
        $scope._setComboData("momsAcShopCombo", momsAcShopComboList);                   // AC점포별
        $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList);                   // 지역구분
        $scope._setComboData("momsCommercialCombo", momsCommercialComboList);           // 상권
        $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList);               // 점포유형
        $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
        $scope._setComboData("branchCdCombo", branchCdComboList);                       // 그룹
        $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
        $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
        $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
        $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
        $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("virtualLoginByIdCtrl");
        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 사용자아이디
                if(col.binding === "userId"){
                    wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });
        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "userId") {
                    if(selectedRow.storeModYn === 'N'){
                        $scope._popMsg(messages["virtualLoginById.msg.storeModYn"]); // 변경제한매장 입니다.
                        return false;
                    }else {
                        $scope.vLoginProcess(selectedRow.userId);
                    }
                }
            }
        });
    };
    // 가상로그인 그리드 조회
    $scope.$on("virtualLoginByIdCtrl", function(event, data) {
        // 파라미터
        var params = {};
        params.orgnFg = orgnFg;

        if(momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/store/manage/virtualLoginById/virtualLoginById/list.sb", params, function() {

        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    // 가상로그인 수행
    // 최초 가상로그인으로 로그인시에는 vLoginId 가 아닌 vUserId 파라미터로 로그인 후 vLoginId로 사용한다.
    $scope.vLoginProcess = function(value) {

        if (isEmpty(value)) {
            $scope.$apply(function() {
                $scope._popMsg(messages["virtualLogin.vLogin.fail"]);
            });
            return false;
        } else {

            /* post */
            $scope.popupCnt = $scope.popupCnt + 1;

            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "/store/manage/virtualLogin/virtualLogin/vLogin.sb");
            form.setAttribute("target", value);

            var formField = document.createElement("input");
            formField.setAttribute("type", "hidden");
            formField.setAttribute("name", "vUserId");
            formField.setAttribute("value", value);
            form.appendChild(formField);
            document.body.appendChild(form);

            var popup = window.open("", value, "width=1024,height=768,resizable=yes,scrollbars=yes");
            var crono = window.setInterval(function () {
                if (popup.closed !== false) { // !== opera compatibility reasons
                    window.clearInterval(crono);
                    var params = {};
                    params.vUserId = value;
                    if ( popup.document.getElementsByName("sessionId") ) {
                        params.sid = popup.document.getElementsByName("sessionId")[0].value;
                    }

                    $http({
                        method: 'POST',
                        url: "/store/manage/virtualLogin/virtualLogin/vLogout.sb",
                        params: params,
                        headers: {'Content-Type': 'application/json; charset=utf-8'}
                    }).then(function successCallback(response) {

                    }, function errorCallback(response) {
                        $scope._popMsg(response.message);
                        return false;
                    });

                }
            }, 250);
            form.submit();
            document.body.removeChild(form);
        }
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

}]);
