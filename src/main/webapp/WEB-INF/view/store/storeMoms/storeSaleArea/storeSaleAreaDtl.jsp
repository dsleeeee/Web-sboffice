<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="wjStoreSaleAreaDtlLayer" control="wjStoreSaleAreaDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:860px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="storeSaleAreaDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeSaleArea.srchSaleArea" />
            <a href="#" class="wj-hide btn_close" ng-click="closeDtl()"></a>
        </div>
        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="updownSet">
                <%-- 수정 --%>
                <button class="btn_skyblue fr" ng-click="modStoreSaleArea()"><s:message code="storeSaleArea.modify"/></button>
                <%-- 매장 --%>
                <div class="sb-select w200px fr mr5">
                  <wj-combo-box
                         id="srchDtlStoreCd"
                         items-source="_getComboData('srchDtlStoreCd')"
                         display-member-path="name"
                         selected-value-path="value"
                         is-editable="false"
                         control="srchDtlStoreCdCombo"
                         selected-index-changed="setDtlStoreSaleArea(s)">
                  </wj-combo-box>
                </div>
                <%-- 지사 --%>
                <div class="sb-select w200px fr mr5">
                  <wj-combo-box
                         id="srchDtlBranchCd"
                         items-source="_getComboData('srchDtlBranchCd')"
                         display-member-path="name"
                         selected-value-path="value"
                         is-editable="false"
                         control="srchDtlBranchCdCombo"
                         selected-index-changed="setDtlStore(s)">
                  </wj-combo-box>
                </div>
            </div>
            <div id="STORE_MAP_DTL" style="border:1px solid gray;width:800px;height:500px;"></div>
            <div class="updownSet mt5">
                <%-- 서울, 경기지역 --%>
                <button class="btn_skyblue fr" ng-click="metropolitanSaleArea()"><s:message code="storeSaleArea.metropolitan"/></button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeSaleArea/storeSaleAreaDtl.js?ver=20221125.01" charset="utf-8"></script>

<script type="text/javascript">

    var oMap_dtl = null;
    var oPoint_dtl = null;
    var oStore_dtl = null;

    // 매장위치 표시
    function init_Map_dtl( _gps_x , _gps_y ) {
        $("#STORE_MAP_DTL").html("");
        oPoint_dtl = new naver.maps.LatLng( _gps_y , _gps_x ) ;
            var markerOptions = {
            position: oPoint_dtl,
            icon: {
                url: '/resource/solbipos/css/img/icon_map.png',
                size: new naver.maps.Size(42, 56),
                anchor: new naver.maps.Size(25, 55)
            }
        };

        oStore_dtl = new naver.maps.Marker (markerOptions);

        oMap_dtl = new naver.maps.Map('STORE_MAP_DTL', {
            center : oPoint_dtl,
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

        oStore_dtl.setMap(null);
        oStore_dtl.setMap(oMap_dtl);
    }

    // 영업지역 표시
    function searchregion_dtl(paths){

       var polyline = new naver.maps.Polygon({
            paths: paths,
            strokeOpacity: 0.75,
            fillColor: "#007EEA",
            fillOpacity: 0.3
        });

        polyline.setMap(oMap_dtl);
    }



    var aPolygon;
    var aPolyline;

    // 영업지역 표시(수도권매장 전체표시)
    function searchregion_metropolitan(paths, storeNm, lati, longi){

        aPolygon = aPolyline = new naver.maps.Polygon({
            paths: paths,
            strokeOpacity: 0.6,
            strokeColor: '#ff0000',
            strokeWeight: 3,
            fillColor: "#007EEA",
            fillOpacity: 0.3,
            clickable: true,
            map: oMap_dtl,
            title: storeNm
        });

        var aPoint = new naver.maps.LatLng(lati, longi);
        var amarkerOptions = {
            position: aPoint,
            icon: {
                url: '/resource/solbipos/css/img/icon_map.png',
                size: new naver.maps.Size(42, 56),
                anchor: new naver.maps.Size(25, 55)
            },
            title:storeNm
        };

        var aStore = new naver.maps.Marker (amarkerOptions, {title: storeNm});
        aStore.setMap(null);
        aStore.setMap(oMap_dtl);

        var infowindow1 = new naver.maps.InfoWindow({
            content: storeNm
        });

        naver.maps.Event.addListener(aStore, "mouseover", function(e) {
            infowindow1.setOptions({
                content: e.overlay.title
            });
            infowindow1.open(oMap_dtl, e.overlay.position);
        });

        naver.maps.Event.addListener(aStore, "mouseout", function(e) {
            infowindow1.close();
        });
    }

</script>
