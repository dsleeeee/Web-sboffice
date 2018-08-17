<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/resource/solbipos/css/cmm/style.css" rel="stylesheet" type="text/css" media="all"/>
<link href="/resource/solbipos/css/cmm/devCustom.css" rel="stylesheet" type="text/css" media="all"/>
<%--<link href="/resource/solbipos/css/cmm/wijmo.css" rel="stylesheet" type="text/css"/>--%>
<link href="/resource/vendor/wijmo/css/wijmo.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" type="text/css" href="/resource/vendor/awesome-font/css/font-awesome.min.css" />

<script type="text/javascript" src="/resource/solbipos/js/common/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resource/vendor/prefixfree/prefixfree.js" charset="utf-8"></script>

<script type="text/javascript" src="/resource/vendor/wijmo/js/wijmo.min.js" charset="utf-8"></script>
<%-- wijmo LicenseKey 설정 --%>
<script type="text/javascript">
    wijmo.setLicenseKey('127.0.0.1|192.168.0.85|192.168.0.170,999199446559797#B0HbhZmOiI7ckJye0ICbuFkI1pjIEJCLi4TPBhjNXRmd7Z4R9BXOrI5aUNjdJ56dFJTYrsWVjJDdvRlSP9kTFRTSQF7ZhNlN7pGavI4ToJkNK36b6JjSiJHMDVVT9hke8UzcGZFME9kYadnV6p7SGlGeMx4YMZGOkpGThdGaQdUdXllWhF7KMZUTlJDVJpWRwonWsdUdy2ycY3SY5UDTwZ5T5hFSCdjY03SWYpXUNVnNRhjazM6c5NlZp3kcJZUWoF4SsZnZ8hHZtNjd7QFThd6KI3SZFFUQEFEMwJGT72ScwUXdNlkMtFzUZR6LwJGMRVjeshTak54L8YUOzskd8skQihnYWt4NYZWZZZlWwRWaHRHM4w4ZNtCV0tCbJp6ajx4UUVGaq5mdkdGNyNHdmlGV6MWdBZjRMBHdWhzTvk7TidlRvREbNBXWxIHbZd6dh54ThtkdLxkcjF6dZNjbGNGNYZ6Mw8EMVlnNmVWZpZTYiojITJCLiMkR7cTMCFjNiojIIJCLwYTN9YjM8UzN0IicfJye35XX3JSSwIjUiojIDJCLi86bpNnblRHeFBCI4VWZoNFelxmRg2Wbql6ViojIOJyes4nI5kkTRJiOiMkIsIibvl6cuVGd8VEIgIXZ7VWaWRncvBXZSBybtpWaXJiOi8kI1xSfis4N8gkI0IyQiwiIu3Waz9WZ4hXRgAydvJVa4xWdNBybtpWaXJiOi8kI1xSfiQjR6QkI0IyQiwiIu3Waz9WZ4hXRgACUBx4TgAybtpWaXJiOi8kI1xSfiMzQwIkI0IyQiwiIlJ7bDBybtpWaXJiOi8kI1xSfiUFO7EkI0IyQiwiIu3Waz9WZ4hXRgACdyFGaDxWYpNmbh9WaGBybtpWaXJiOi8kI1tlOiQmcQJCLiIDM4QzNwACOwgDM8EDMyIiOiQncDJCLiAzNx8CMugjNx8iM9EDL5gjLw8CO6EjLykTMsEjLw8CMucjMxIiOiMXbEJCLiQqisz0jtTYurTphsLiOiEmTDJCLicTO7kTN5YDN4kTOxkTO9IiOiQWSiwSfiIjd8EDMyIiOiIXZ6JLLcN');
</script>

<script type="text/javascript" src="/resource/vendor/wijmo/js/grid/wijmo.grid.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/grid/wijmo.grid.xlsx.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/output/wijmo.xlsx.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/input/wijmo.input.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/nav/wijmo.nav.min.js" charset="utf-8"></script>
<%-- wijmo xlsx 와 관련됨 --%>
<script type="text/javascript" src="/resource/vendor/jszip/js/jszip.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/chart/wijmo.chart.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/chart/wijmo.chart.animation.js" charset="utf-8"></script>

<!-- <script type="text/javascript" src="/resource/vendor/spin/spin.js" charset="utf-8"></script> -->
<script type="text/javascript" src="/resource/solbipos/js/common/common.js?ver=20180801" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/grid.comm.js?ver=20180801" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/gen.comm.js?ver=20180801" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/alert.js?ver=20180801" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/paging.js?ver=20180801" charset="utf-8"></script>

<script type="text/javascript" src="/resource/solbipos/js/variables/lang.js?ver=20180801" charset="utf-8"></script>
