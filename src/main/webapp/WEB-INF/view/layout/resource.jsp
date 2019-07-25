<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- favicon 요청시 404 에러를 피하기 위한 설정 : TODO 추후 favicon 등록할것 --%>
<link rel="shortcut icon" href="data:image/x-icon" type="image/x-icon">

<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/cmm/style.css?ver=2018112001" media="all"/>
<link rel="stylesheet" type="text/css" href="/resource/vendor/wijmo/css/wijmo.css?ver=520183550"/>
<link rel="stylesheet" type="text/css" href="/resource/solbipos/css/cmm/wijmo.solbi.custom.css?ver=2018112001"/>
<link rel="stylesheet" type="text/css" href="/resource/vendor/awesome-font/css/font-awesome.min.css?ver=470" />
<%-- third-party plug-in --%>
<script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="/resource/vendor/bootstrap/bootstrap.min.js?ver=337"></script>
<script type="text/javascript" src="/resource/vendor/angularjs/angular.min.js?ver=175"></script>
<script type="text/javascript" src="/resource/vendor/angularjs/ui-bootstrap-tpls-2.5.0.min.js"></script>
<script type="text/javascript" src="/resource/vendor/angularjs/angular-sanitize.min.js?ver=175"></script>
<script type="text/javascript" src="/resource/vendor/angularjs/angular-route.min.js?ver=175"></script>
<script type="text/javascript" src="/resource/vendor/prefixfree/prefixfree.min.js?ver=106" charset="utf-8"></script>
<%-- wijmo plug-in --%>
<script type="text/javascript" src="/resource/vendor/wijmo/js/wijmo.min.js?ver=520183550" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/wijmo.license.key.js?ver=20181231" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/cultures/wijmo.culture.ko.min.js?ver=520183550" charset="utf-8"></script>
<%-- wijmo-modules plug-in --%>
<script type="text/javascript" src="/resource/vendor/wijmo/js/grid/wijmo.grid.min.js?ver=520183550" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/grid/wijmo.grid.xlsx.min.js?ver=520183550" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/output/wijmo.xlsx.min.js?ver=520183550" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/input/wijmo.input.min.js?ver=520183550" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/nav/wijmo.nav.min.js?ver=520183550" charset="utf-8"></script>
<%-- wijmo-xlsx plug-in --%>
<script type="text/javascript" src="/resource/vendor/jszip/js/jszip.min.js?ver=315 " charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/chart/wijmo.chart.min.js?ver=520183550" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/chart/wijmo.chart.animation.min.js?ver=520183550" charset="utf-8"></script>
<%-- wijmo-angularJS plug-in --%>
<script type="text/javascript" src="/resource/vendor/wijmo/js/wijmo.angular.min.js?ver=520183550" charset="utf-8"></script>
<%-- solbi plug-in --%>
<script type="text/javascript" src="/resource/solbipos/js/common/common.js?ver=2019072401" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/StringUtil.js?ver=2018100401" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/DateUtil.js?ver=2018100401" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/grid.comm.js?ver=2018121701" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/grid.comm.angular.js?ver=2018121701" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/gen.comm.js?ver=2018102701" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/alert.min.js?ver=2018100401" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/paging.min.js?ver=2018100401" charset="utf-8"></script>
<%-- wijmo DragAndDrop ColumnPicker --%>
<script type="text/javascript" src="/resource/solbipos/js/common/ColumnPicker.min.js?ver=2018091501" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/DragDropTouch.min.js?ver=2018091501" charset="utf-8"></script>
<%-- solbi common language.properties --%>
<script type="text/javascript" src="/resource/solbipos/js/variables/lang.js?ver=2018111501" charset="utf-8"></script>
