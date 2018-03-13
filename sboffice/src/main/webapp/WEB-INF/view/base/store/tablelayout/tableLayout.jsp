<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="/resource/graph/styles/tablelayout.css">

<script>
var urlParams = (function(url) {
  var result = new Object();
  var idx = url.lastIndexOf('?');

  if (idx > 0) {
    var params = url.substring(idx + 1).split('&');

    for (var i = 0; i < params.length; i++) {
      idx = params[i].indexOf('=');

      if (idx > 0) {
        result[params[i].substring(0, idx)] = params[i].substring(idx + 1);
      }
    }
  }

  return result;
})(window.location.href);

// Default resources are included in grapheditor resources
mxLoadResources = false;

// urlParams is null when used for embedding
window.urlParams = window.urlParams || {};

window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE  || 10485760;

window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';
window.STENCIL_PATH = window.STENCIL_PATH || '/resource/graph/stencils';
//window.CONFIG_PATH = window.CONFIG_PATH || '/resource/graph/config';

window.TABLELAYOUT_OPEN_URL = window.TABLELAYOUT_OPEN_URL || '/base/store/tablelayout/tablelayout/view.sb';
window.TABLELAYOUT_SAVE_URL = window.TABLELAYOUT_SAVE_URL || '/base/store/tablelayout/tablelayout/save.sb';

window.mxBasePath = window.mxBasePath || '/resource/vender/mxgraph/src';
window.mxLanguage = window.mxLanguage || urlParams['lang'];
window.mxLanguages = window.mxLanguages || ['ko'];

</script>
<script type="text/javascript" src="/resource/vender/mxgraph/mxClient.js"></script>
<!--script type="text/javascript" src="/resource/vender/mxgraph/mxClient.min.js"></script-->
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js"></script>
<script type="text/javascript" src="/resource/graph/js/TableLayout.js"></script>

<div id="container">
  <div id="component"></div>
  <div id="floor"></div>
  <div id="content">
    <div id="splash"><img src="/resource/graph/images/loading.gif"></div>
  </div>
  <div id="format"></div>
</div>

<script>
(function() {
  var tableLayoutInit = TableLayout.prototype.init;
  TableLayout.prototype.init = function() {
    tableLayoutInit.apply(this, arguments);
  };
  
  if (!mxClient.isBrowserSupported()) {
    // Displays an error message if the browser is not supported.
    mxUtils.error('Browser is not supported!', 200, false);
  }
  else {
    // Adds required resources (disables loading of fallback properties, this can only
    // be used if we know that all keys are defined in the language specific file)
    mxResources.loadDefaultBundle = false;
    var bundle = mxResources.getDefaultBundle(RESOURCE_BASE, mxLanguage)
        || mxResources.getSpecialBundle(RESOURCE_BASE, mxLanguage);

    // Fixes possible asynchronous requests
    mxUtils.getAll(
      [ bundle, STYLE_PATH + '/tablelayout.xml' ],
      function(xhr) {
        // Adds bundle text to resources
        mxResources.parse(xhr[0].getText());

        // Configures the default graph theme
        var themes = new Object();
        themes[Graph.prototype.defaultThemeName] = xhr[1].getDocumentElement();

        // Main
        var tablelayout = new TableLayout(document.getElementById('content'), themes);
      },
      function() {
        document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
      });
  }
})();
</script>
