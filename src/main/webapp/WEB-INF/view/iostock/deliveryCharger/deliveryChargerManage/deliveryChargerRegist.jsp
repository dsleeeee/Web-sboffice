<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<wj-popup id="wjDlvrRegistLayer" control="wjDlvrRegistLayer" show-trigger="Click" hide-trigger="Click" hiding="hiding(s,e)" style="display:none;width:800px;">
    <div id="dlvrRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="dlvrInfoCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="deliveryCharger.registPopTitle" /> <span id="registTitleDlvrNm" class="ml5"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body">
            <form id="dlvrForm" ng-submit="submitForm()">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="deliveryCharger.dlvrCdNm" /><em class="imp">*</em></th>
                        <td><input type="text" id="dlvrCd" class="sb-input" style="width:49%" ng-model="dlvr.dlvrCd" readonly/> <input type="text" id="dlvrNm" class="sb-input" style="width:49%" ng-model="dlvr.dlvrNm" maxlength="18"/></td>
                        <th><s:message code="deliveryCharger.carNo" /><em class="imp">*</em></th>
                        <td><input type="text" id="carNo" class="sb-input w100" maxlength="14" ng-model="dlvr.carNo"/></td>
                    </tr>
                    <tr>
                        <th><s:message code="deliveryCharger.telNo"/></th>
                        <td><input type="text" id="telNo" class="sb-input w100" maxlength="15" ng-model="dlvr.telNo"/></td>
                        <th><s:message code="deliveryCharger.hpNo"/></th>
                        <td><input type="text" id="hpNo" class="sb-input w100" maxlength="15" ng-model="dlvr.hpNo"/></td>
                    </tr>
                    <tr>
                        <th><s:message code="deliveryCharger.useYn"/></th>
                        <td>
                            <select id="useYn" ng-model="dlvr.useYn">
                                <option value="Y"><s:message code="deliveryCharger.useYnY"/></option>
                                <option value="N"><s:message code="deliveryCharger.useYnN"/></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="deliveryCharger.remark"/></th>
                        <td colspan="3">
                            <div>
                                <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="dlvr.remark"></textarea>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="mt10 pdb20 oh bb">
                    <button type="submit" id="btnSave" class="btn_blue fr">저장</button>
                </div>
            </form>
        </div>
    </div>

    <div class="wj-TblWrap ml20 mr20 pdb20" ng-controller="dlvrChgrStorageCtrl">
        <div class="mt20 oh sb-select dkbr">
            <span class="fl bk lh30"><s:message code='deliveryCharger.chargeStorage' /></span>
            <div class="tr">
                <%-- 창고추가 --%>
                <button type="button" id="addStorage" class="btn_skyblue ml5" ng-click="openPopAddStorage()" style="display:none"><s:message code="deliveryCharger.addStorage" /></button>
                <%-- 창고삭제 --%>
                <button type="button" id="delStorage" class="btn_skyblue ml5" ng-click="delStorage()" style="display:none"><s:message code="deliveryCharger.delStorage" /></button>
            </div>
        </div>

        <!--위즈모 테이블-->
        <div class="wj-gridWrap mt10" style="height: 200px;" >
            <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="false"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="deliveryCharger.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="deliveryCharger.storageCd"/>" binding="storageCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="deliveryCharger.storageNm"/>" binding="storageNm" width="*"  align="left"   is-read-only="true"></wj-flex-grid-column>

                <!-- enable column filtering-->
                <wj-flex-grid-filter></wj-flex-grid-filter>
            </wj-flex-grid>
        </div>
        <!--//위즈모 테이블-->
    </div>
</wj-popup>




<script type="text/javascript">
    /** 배송기사 관리 상세 controller */
    app.controller('dlvrInfoCtrl', ['$scope', '$http', 'dlvrVO', function($scope, $http, dlvrVO) {
        $scope.default = { useYn:"Y" };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on('dlvrInfoCtrl', function(event, paramObj) {
            // 배송기사 상세 팝업 오픈
            $scope.wjDlvrRegistLayer.show(true);

            // paramObj 이 있는 경우 배송기사 상세 조회
            if(!$.isEmptyObject(paramObj)) {
                // 타이틀의 배송기사 명칭 세팅
                $("#registTitleDlvrNm").html("["+paramObj.dlvrNm+"]");
                //배송기사 상세 조회
                $scope.searchDlvrInfo(paramObj);

                $("#addStorage").show();
                $("#delStorage").show();
            }
            // 신규등록인 경우
            else {
                $("#registTitleDlvrNm").html("신규등록");
                $scope.dlvr = angular.copy($scope.default);

                $("#addStorage").hide();
                $("#delStorage").hide();
            }

            //관리 창고 그리드에 대한 콘트롤러 호출. 기사코드가 있는경우 관리창고 조회하고 신규등록인 경우는 그리드 초기화
            setTimeout(function() {
                $scope._broadcast('dlvrChgrStorageCtrl', paramObj);
            }, 10);
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 배송기사 상세 조회
        $scope.searchDlvrInfo = function(paramObj) {
            var param = {};
            param.dlvrCd = paramObj.dlvrCd;

            $http({
                method: 'POST', //방식
                url: "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrInfo.sb", /* 통신할 URL */
                params: param, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available

                var data = response.data.data;
                if (data === undefined || data === null) {
                    // 팝업 닫기 및 값 초기화
                    $scope.popupClose();
                    s_alert.pop(response.data.message);
                    return;
                }

                $scope.dlvr = data;
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                s_alert.pop(response.data.message);
                return;
            }).then(function() {
                // "complete" code here
            });
        };

        // 배송기사 저장
        $scope.submitForm = function() {
            //값체크
            if (!valueCheck()) return;

            $http({
                method: 'POST', //방식
                url: "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrSave.sb", /* 통신할 URL */
                params: $scope.dlvr, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available

                // 등록된 배송기사 그리드 새로고침
                $scope._broadcast('dlvrChgrCtrl');

                s_alert.pop(messages["cmm.saveSucc"]);

                if(dlvrVO.getDlvrCd() == "") {
                    // 팝업 닫기 및 값 초기화
                    $scope.popupClose();

                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                s_alert.pop(response.data.message);
                return;
            });
        };

        // 팝업 닫기 및 값 초기화
        $scope.popupClose = function () {
            // 초기화
            $scope.dlvr = angular.copy($scope.default);
            $scope.wjDlvrRegistLayer.hide();
            $("#registTitleDlvrNm").html("");
        }
    }]);


    /** 배송기사 관리 창고 그리드 controller */
    app.controller('dlvrChgrStorageCtrl', ['$scope', '$http', 'dlvrVO', function ($scope, $http, dlvrVO) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('dlvrChgrStorageCtrl', $scope, $http, true));
        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            // $scope._makePickColumns("dlvrChgrStorageCtrl");
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("dlvrChgrStorageCtrl", function(event, data) {
            //TODO : 그리드 초기화시 뭔가의 동작을 해줘야 그리드의 내용이 사라짐. 확인 필요.
            if(dlvrVO.getDlvrCd() == "" || dlvrVO.getDlvrCd() == null) {
                // console.log("==dlvrVO.getDlvrCd() 1==");
                // var cv = new wijmo.collections.CollectionView();
                // cv.refresh();
                // $scope.flex.collectionView.itemsSource = cv;
                // $scope.flex.collectionView.clearChanges();
                // $scope.flex.collectionView.refresh();
                // $scope.data = cv;

                var cv = new wijmo.collections.CollectionView([]);
                cv.trackChanges = true;
                $scope.data = cv;

                // $scope.searchDlvrChgrStorageList();
            }
            else {
                // 그리드 조회
                $scope.searchDlvrChgrStorageList();
            }
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 그리드 조회
        $scope.searchDlvrChgrStorageList = function () {
            // 파라미터
            var params = {};
            params.listScale = 15;
            params.curr = 1;
            params.dlvrCd = dlvrVO.getDlvrCd();
            // params.dlvrCd = data.dlvrCd;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiry("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/storageList.sb", params, "", false, false);
        };

        // 창고 삭제
        $scope.delStorage = function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            $scope._save("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/delStorage.sb", params, function() { $scope.searchDlvrChgrStorageList(); });
        };

        // 창고 추가 팝업 오픈
        $scope.openPopAddStorage = function() {
            $scope._broadcast('dlvrStorageMgrCtrl');
        };
    }]);

    // 값 체크
    function valueCheck() {
        <%-- 기사명을 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.dlvrNm'/> <s:message code='cmm.require.text'/>";
        if($("#dlvrNm").val() === "") {
            s_alert.popOk(msg, function(){ $("#dlvrNm").focus(); });
            return false;
        }

        <%-- 기사명의 길이가 너무 깁니다. --%>
        var msg = "<s:message code='deliveryCharger.dlvrNm'/> <s:message code='deliveryCharger.textOver'/>";
        if($("#dlvrNm").val().getByteLengthForOracle() > 18) {
            s_alert.popOk(msg, function(){ $("#dlvrNm").select(); });
            return false;
        }

        <%-- 차량번호를 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.carNo'/> <s:message code='cmm.require.text'/>";
        if($("#carNo").val() === "") {
            s_alert.popOk(msg, function(){ $("#carNo").focus(); });
            return false;
        }

        <%-- 차량번호의 길이가 너무 깁니다. --%>
        var msg = "<s:message code='deliveryCharger.carNo'/> <s:message code='deliveryCharger.textOver'/>";
        if($("#carNo").val().getByteLengthForOracle() > 14) {
            s_alert.popOk(msg, function(){ $("#carNo").select(); });
            return false;
        }

        <%-- 전화번호는 숫자만 입력할 수 있습니다. --%>
        var msg = "<s:message code='deliveryCharger.telNo'/> <s:message code='cmm.require.number'/>";
        var numChkregexp = /[^0-9]/g;
        if(numChkregexp.test($("#telNo").val())) {
            s_alert.popOk(msg, function(){ $("#telNo").select(); });
            return false;
        }

        <%-- 전화번호를 정확히 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.telNo'/> <s:message code='deliveryCharger.validCheck'/>";
        if($("#telNo").val() !== "" && $("#telNo").val().length < 10) {
            s_alert.popOk(msg, function(){ $("#telNo").select(); });
            return false;
        }

        <%-- 핸드폰번호는 숫자만 입력할 수 있습니다. --%>
        var msg = "<s:message code='deliveryCharger.hpNo'/> <s:message code='cmm.require.number'/>";
        if(numChkregexp.test($("#hpNo").val())) {
            s_alert.popOk(msg, function(){ $("#hpNo").select(); });
            return false;
        }

        <%-- 핸드폰번호를 정확히 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.hpNo'/> <s:message code='deliveryCharger.validCheck'/>";
        if($("#hpNo").val() !== "" && $("#hpNo").val().length < 10) {
            s_alert.popOk(msg, function(){ $("#hpNo").select(); });
            return false;
        }
        return true;
    }
</script>
