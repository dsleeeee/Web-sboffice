<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="wjStoreSaleAreaRegLayer" control="wjStoreSaleAreaRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:860px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="storeSaleAreaRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeSaleArea.modSaleArea" />
            <a href="#" class="wj-hide btn_close" ng-click="closeReg()"></a>
        </div>
        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="updownSet">
                <%-- 저장 --%>
                <button class="btn_skyblue fr" ng-click="saveStoreSaleArea()"><s:message code="cmm.save"/></button>
                <%-- 매장 --%>
                <div class="sb-select w200px fr mr5">
                  <wj-combo-box
                         id="srchRegStoreCd"
                         items-source="_getComboData('srchRegStoreCd')"
                         display-member-path="name"
                         selected-value-path="value"
                         is-editable="false"
                         control="srchRegStoreCdCombo"
                         selected-index-changed="setRegStoreSaleArea(s)">
                  </wj-combo-box>
                </div>
                <%-- 지사 --%>
                <div class="sb-select w200px fr mr5">
                  <wj-combo-box
                         id="srchRegBranchCd"
                         items-source="_getComboData('srchRegBranchCd')"
                         display-member-path="name"
                         selected-value-path="value"
                         is-editable="false"
                         control="srchRegBranchCdCombo"
                         selected-index-changed="setRegStore(s)">
                  </wj-combo-box>
                </div>
            </div>
            <div id="STORE_MAP" style="border:1px solid gray;width:800px;height:500px;"></div>
            <%-- 타매장 지역 매칭 --%>
            <div class="updownSet mt5">
                <%-- 초기화 --%>
                <button class="btn_skyblue fr" ng-click="resetStoreSaleArea()"><s:message code="cmm.init"/></button>
                <%-- 매장 --%>
                <div class="sb-select w200px fr mr5">
                  <wj-combo-box
                         id="srchMapStoreCd"
                         items-source="_getComboData('srchMapStoreCd')"
                         display-member-path="name"
                         selected-value-path="value"
                         is-editable="false"
                         control="srchMapStoreCdCombo"
                         selected-index-changed="setMapStoreSaleArea(s)">
                  </wj-combo-box>
                </div>
                <%-- 지사 --%>
                <div class="sb-select w200px fr mr5">
                  <wj-combo-box
                         id="srchMapBranchCd"
                         items-source="_getComboData('srchMapBranchCd')"
                         display-member-path="name"
                         selected-value-path="value"
                         is-editable="false"
                         control="srchMapBranchCdCombo"
                         selected-index-changed="setMapStore(s)">
                  </wj-combo-box>
                </div>
            </div>
            <%-- 처음 선택한 지사와 매장(초기화 클릭시 사용) --%>
            <input type="hidden" id="hdRegBranchCd"/>
            <input type="hidden" id="hdRegStoreCd"/>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeSaleArea/storeSaleAreaReg.js?ver=20221125.01" charset="utf-8"></script>

<script type="text/javascript">

    var posRegion = new Array();
    var oMap = null;
    var oPoint = null;
    var oStore = null;
    var getPos = function getPos(pos) {
      posRegion[posRegion.length] = new naver.maps.LatLng(pos.coord.y, pos.coord.x);
      var polyline = new naver.maps.Polygon({
        paths: posRegion,
        strokeOpacity: 0.75,
        fillColor: "#007EEA",
        fillOpacity: 0.3
      });

      polyline.setMap(oMap);
      $(".nmap").css("cursor" , "crosshair");
    };

    // 매장위치 표시
    function init_Map( _gps_x , _gps_y ) {
        $("#STORE_MAP").html("");
        oPoint = new naver.maps.LatLng( _gps_y , _gps_x ) ;
        var markerOptions = {
            position: oPoint,
            icon: {
                url: '/resource/solbipos/css/img/icon_map.png',
                size: new naver.maps.Size(42, 56),
                anchor: new naver.maps.Size(25, 55)
            }
        };

        oStore = new naver.maps.Marker (markerOptions);

        oMap = new naver.maps.Map('STORE_MAP', {
            center : oPoint,
            zoom : 10, //
            enableWheelZoom : true,
            enableDragPan : true,
            enableDblClickZoom : false,
            mapMode : 0,
            activateTrafficMap : false,
            activateBicycleMap : false,
            minMaxLevel : [ 8, 14 ],
            size : new naver.maps.Size(800, 600),
            zoomControl: true,
            zoomControlOptions: {
                position: naver.maps.Position.TOP_LEFT
            }
        });

        oStore.setMap(null);
        oStore.setMap(oMap);


        naver.maps.Event.addListener(oMap, 'click', getPos);
        naver.maps.Event.addListener(oMap, 'dragstart', dragStartFunc);
        naver.maps.Event.addListener(oMap, 'dragend', dragEndFunc);
        $(".nmap").css("cursor" , "crosshair");
    }
    // 영업지역 표시
    function searchregion(paths){

        var polyline = new naver.maps.Polygon({
            paths: paths,
            strokeOpacity: 0.75,
            fillColor: "#007EEA",
            fillOpacity: 0.3
        });

        polyline.setMap(oMap);
        $(".nmap").css("cursor" , "crosshair");
    }


    var sPos = null;
    var ePos = null;
    function dragStartFunc(e) {
       sPos = e.coord;
    }
    function dragEndFunc(e) {
       ePos = e.coord;
       var dragDistance = distance(sPos.x, sPos.y, ePos.x, ePos.y);

       if (dragDistance < 1) return;
       $(".nmap").css("cursor" , "crosshair");
    }
    function distance(x0, y0, x1, y1) {
      var dx = x1 - x0;
      var dy = y1 - y0;
      return Math.sqrt(dx * dx + dy * dy);
    }

</script>