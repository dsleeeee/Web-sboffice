﻿/*
 *
 * Wijmo Library 5.20183.550
 * http://wijmo.com/
 *
 * Copyright(c) GrapeCity, Inc.  All rights reserved.
 *
 * Licensed under the GrapeCity Commercial License.
 * sales@wijmo.com
 * wijmo.com/products/wijmo-5/license/
 *
 */
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";

    function n(e) {
      var t, n = document.createElement("div"),
        o = document.createDocumentFragment();
      for (n.innerHTML = e; t = n.firstChild;) o.appendChild(t);
      return o
    }

    function o(e) {
      return n(e).firstChild
    }

    function r(e) {
      return t._Unit.convertValue(e, t._UnitType.Twip, t._UnitType.Dip)
    }
    var i = null != navigator.userAgent.match(/iPhone|iPad|iPod/i);
    t.isIOS = function() {
      return i
    };
    var a = '<svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 24 24" xml:space="preserve">',
      s = "</svg>";
    t.icons = {
      paginated: '<rect x="16" y= "1" width="1" height="1" /><rect x="17" y= "2" width="1" height="1" /><rect x="18" y= "3" width="1" height="1" /><path d= "M20,5V4h-1v1h-0.6H18h-3V2V1.3V1h1V0h-1h0H5H4H3v24h2v0h13h1.1H20L20,5L20,5z M5,22.1V2h8v5h1h1h3v15.1H5z" /><rect x="6" y= "8" width="10" height="1" /><rect x="6" y= "5" width="5" height="1" /><rect x="6" y= "11" width="10" height="1" /><rect x="6" y= "14" width="10" height="1" /><rect x="6" y= "17" width="10" height="1" /><rect x="6" y= "20" width="10" height="1" />',
      print: '<rect x="5" y= "1" width="14" height="4" /><polygon points= "22,8 22,7 19,7 19,6 5,6 5,7 2,7 2,8 1,8 1,11 1,20 2,20 2,21 5,21 5,11 19,11 19,21 22,21 22,20 23,20 23, 11 23, 8 "/><path d="M6,12v11h12V12H6z M16,21H8v-1h8V21z M16,18H8v-1h8V18z M16,15H8v-1h8V15z" />',
      exports: '<path d="M19.6,23"/><polyline points="5,19 5,2 13,2 13,7 14.3,7 15,7 18,7 18,9 20,9 20,7 20,6.4 20,5 20,4 19,4 19,5 15,5 15,2 15,1 16,1 16,0 15,0 5,0 3,0 3,2 3,19 3,21 5,21 "/><rect x="18" y="3" width="1" height="1"/><rect x="17" y="2" width="1" height="1"/><rect x="16" y="1" width="1" height="1"/><polygon points="17,16.6 20,14.1 17,11.6 17,13.6 13,13.6 13,14.6 17,14.6 "/><rect x="3" y="20.9" width="2" height="3.1"/><rect x="4.5" y="22" width="15.6" height="2"/><rect x="18" y="8.4" width="2" height="1.6"/><rect x="18" y="18" width="2.1" height="6"/>',
      portrait: '<path d="M19,0L19,0L5,0v0H3v24h0.1H5h14h1.7H21V0H19z M12.5,23h-1v-1h1V23z M19,21H5V2h14V21z"/>',
      landscape: '<path d="M24,19L24,19l0-14h0V3H0v0.1V5v14v1.7V21h24V19z M1,12.5v-1h1v1H1z M3,19V5h19v14H3z"/>',
      pageSetup: '<rect x="18" y="1" width="1" height="1"/><rect x="19" y="2" width="1" height="1"/><rect x="20" y="3" width="1" height="1"/><polygon points="22,5 22,4 21,4 21,5 20.4,5 20,5 17,5 17,2 17,1.3 17,1 18,1 18,0 17,0 17,0 7,0 6,0 5,0 5,5 7,5 7,2 15,2 15,7 16,7 17,7 20,7 20,22.1 7,22.1 7,19 5,19 5,24 5.9,24 7,24 20,24 21.1,24 22,24 22,5 "/><rect x="5" y="7" width="2" height="2"/><rect x="5" y="11" width="2" height="2"/><rect x="5" y="15" width="2" height="2"/><rect x="9" y="11" width="2" height="2"/><rect x="1" y="11" width="2" height="2"/><polygon points="9,8 9,8 8,8 8,9 9,9 9,10 10,10 10,8 "/><polygon points="2,9 2,9 2,10 3,10 3,9 4,9 4,8 2,8 "/><polygon points="3,16 3,16 4,16 4,15 3,15 3,14 2,14 2,16 "/><polygon points="10,15 10,15 10,14 9,14 9,15 8,15 8,16 10,16 "/>',
      previousPage: '<circle opacity=".25" cx="12" cy="12" r="12"/><polygon points="5.6,10.7 12,4.4 18.4,10.7 18.4,15 13.5,10.1 13.5,19.6 10.4,19.6 10.4,10.1 5.6,15 " />',
      nextPage: '<circle opacity=".25" cx="12" cy="12" r="12"/><polygon points="18.4,13.3 12,19.6 5.6,13.3 5.6,9 10.5,13.9 10.5,4.4 13.6,4.4 13.6,13.9 18.4,9 " />',
      firstPage: '<circle opacity=".25" cx="12" cy="12" r="12"/><polygon points="6.5,13.1 12,7.8 17.5,13.1 17.5,17.5 13.5,13.5 13.5,19.6 10.4,19.6 10.4,13.5 6.5,17.5 " /><rect x="6.5" y= "4.4" width="10.9" height="2.2" />',
      lastPage: '<circle opacity=".25" cx="12" cy="12" r="12"/><polygon points="17.5,10.9 12,16.2 6.5,10.9 6.5,6.5 10.5,10.5 10.5,4.4 13.6,4.4 13.6,10.5 17.5,6.5 " /><rect x="6.5" y= "17.5" transform="matrix(-1 -8.987357e-011 8.987357e-011 -1 24 37.0909)" width="10.9" height="2.2" />',
      backwardHistory: '<circle opacity=".25" cx="12" cy="12" r="12"/><polygon points="10.7,18.4 4.4,12 10.7,5.6 15,5.6 10.1,10.5 19.6,10.5 19.6,13.6 10.1,13.6 15,18.4 " />',
      forwardHistory: '<circle opacity=".25" cx="12" cy="12" r="12"/><polygon points="13.3,5.6 19.6,12 13.3,18.4 9,18.4 13.9,13.5 4.4,13.5 4.4,10.4 13.9,10.4 9,5.6 " />',
      selectTool: '<polygon points="19.9,13.4 5.6,1.1 5.3,19.9 10.5,14.7 14.3,23.3 16.4,22.4 12.6,13.8 "/>',
      moveTool: '<polygon points="12.5,3 14.5,3 12,0 9.5,3 11.5,3 11.5,21 11.5,21 9.6,21 12,24 14.5,21 12.5,21 "/><polygon points="21,12.5 21,14.5 24,12 21,9.5 21,11.5 3,11.5 3,11.5 3,9.6 0,12 3,14.5 3,12.5 "/>',
      continuousView: '<polygon points="22,0 22,5 9,5 9,0 7,0 7,5 7,7 7,7 24,7 24,7 24,5 24,0 "/><polygon points="23,15 19,15 19,11 20,11 20,10 19,10 18,10 17,10 9,10 7.4,10 7,10 7,24 9,24 9,12 17,12 17,15 17,16.6 17,17 22,17 22,24 24,24 24,17 24,15.1 24,15 24,15 24,14 23,14 "/><rect x="22" y="13" width="1" height="1"/><polygon points="20.9,12 20.9,13 22,13 22,12 21,12 21,11 20,11 20,12 "/><polygon points="4.9,5.2 2.5,2.2 0,5.2 2,5.2 2,9.2 3,9.2 3,5.2 "/><polygon points="2.9,19.2 2.9,15.2 1.9,15.2 1.9,19.2 0,19.2 2.5,22.1 4.9,19.2 "/>',
      singleView: '<rect x="16" y="1" width="1" height="1"/><rect x="17" y="2" width="1" height="1"/><rect x="18" y="3" width="1" height="1"/><path d="M20,5V4h-1v1h-0.6H18h-3V2V1.3V1h1V0h-1h0H5H4H3v24h2v0h13h1.1H20L20,5L20,5z M5,22.1V2h8v5h1h1h3v15.1H5z"/>',
      fitWholePage: '<rect x="16" y="1" width="1" height="1"/><rect x="17" y="2" width="1" height="1"/><rect x="18" y="3" width="1" height="1"/><path d="M20,5V4h-1v1h-0.6H18h-3V2V1.3V1h1V0h-1h0H5H4H3v24h2v0h13h1.1H20L20,5L20,5z M18,22.1H5V2h8v5h1h1h3V22.1z"/><polygon points="17,13.5 15,11 15,13 13,13 13,14 15,14 15,16 "/><polygon points="6,13.5 8,16 8,14 10,14 10,13 8,13 8,11 "/><polygon points="11.5,7 9,9 11,9 11,11 12,11 12,9 14,9 "/><polygon points="11.5,20 14,18 12,18 12,16 11,16 11,18 9,18 "/>',
      fitPageWidth: '<rect x="16" y="1" width="1" height="1"/><rect x="17" y="2" width="1" height="1"/><rect x="18" y="3" width="1" height="1"/><path d="M20,5V4h-1v1h-0.6H18h-3V2V1.3V1h1V0h-1h0H5H4H3v24h2v0h13h1.1H20L20,5L20,5z M5,22.1V2h8v5h1h1h3v15.1H5z"/><polygon points="14,15.5 17,13 14,10.6 14,12.6 13,12.6 13,13.6 14,13.6 "/><polyline points="6,13.1 9,15.6 9,13.6 10,13.6 10,12.6 9,12.6 9,10.6 6,13.1 "/>',
      zoomOut: '<circle opacity=".25" cx="12" cy="12" r="12"/><rect opacity=".75" x="5" y="10" width="14" height="3"/>',
      zoomIn: '<circle opacity=".25" cx="12" cy="12" r="12"/><polygon opacity=".75" points="19,10 13.5,10 13.5,4.5 10.5,4.5 10.5,10 5,10 5,13 10.5,13 10.5,18.5 13.5,18.5 13.5,13 19,13 " />',
      fullScreen: '<path d="M22,0H0v2.8V4v20h1.5H2h20h0.7H24V4V0H22z M7,1h1v1H7V1z M5,1h1v1H5V1z M3,1h1v1H3V1z M22,22H2L2,4h20L22,22z" /><polygon points="19.6,9.9 20,6 16.1,6.4 17.6,7.8 14.7,10.6 15.4,11.3 18.3,8.5"/><polygon points="4.4,16.2 4,20 7.9,19.7 6.5,18.3 9.3,15.5 8.6,14.8 5.8,17.6"/>',
      exitFullScreen: '<path d="M22,0H0v2.8V4v20h1.5H2h20h0.7H24V4V0H22z M7,1h1v1H7V1z M5,1h1v1H5V1z M3,1h1v1H3V1z M22,22H2L2,4h20L22,22z" /><polygon points="9.2,18.6 9.6,14.7 5.7,15.1 7.2,16.5 4.3,19.3 5,20 7.9,17.2"/><polygon points="14.8,7.5 14.4,11.3 18.3,11 16.9,9.6 19.7,6.8 19,6.1 16.2,8.9"/>',
      thumbnails: '<path d="M20,2h-5h-2v2v5v2v0h2v0h5v0h2v0V9V4V2H20z M20,9h-5V4h5V9z"/><path d="M20,13h-5h-2v2v5v2v0h2v0h5v0h2v0v-2v-5v-2H20z M20,20h-5v-5h5V20z"/><path d="M9,13H4H2v2v5v2v0h2v0h5v0h2v0v-2v-5v-2H9z M9,20H4v-5h5V20z"/><rect x="2" y="2" width="9" height="9"/>',
      outlines: '<path d="M22,0H2H0v2v20v2h2h20h2v-2V2V0H22z M2,2h12v20H2V2z M22,22h-6V2h6V22z"/><rect x="17.5" y="5" width="3" height="1" /><rect x="17.5" y="8" width="3" height="1"/><rect x="17.5" y="11" width="3" height="1"/>',
      search: '<circle stroke-width="2" fill="none" cx="9.5" cy="9.5" r="8.5"/><rect x="16.9" y="13.7" transform="matrix(-0.7193 0.6947 -0.6947 -0.7193 44.3315 18.4942)" width="3" height="9"/>',
      searchNext: '<polygon points="12,12.6 4,4.5 4,11.4 12,19.5 20,11.4 20,4.5 "/>',
      searchPrevious: '<polygon points="12,11.4 20,19.5 20,12.6 12,4.5 4,12.6 4,19.5 "/>',
      hamburgerMenu: '<rect x="2" y="4.875" width="20" height="1.5"/><rect x="2" y="11.25" width="20" height="1.5"/><rect x="2" y="17.625" width="20" height="1.5"/>',
      viewMenu: '<path transform="scale(1.5)" d="M8,2.9c-4.4,0-8,2.2-8,5s3.6,5,8,5s8-2.2,8-5S12.4,2.9,8,2.9z M8,11.8c-2.2,0-4-1.8-4-4c0-2.2,1.8-4,4-4s4, 1.8,4,4C12, 10,10.2,11.8,8,11.8z"/><circle class="st0" cx="12" cy="11.85" r="3.45"/>',
      searchOptions: '<polygon points="12,12.6 4,4.5 4,11.4 12,19.5 20,11.4 20,4.5 "/>',
      searchLeft: '<polygon points="11.4,12 19.5,20 12.6,20 4.5,12 12.6,4 19.5,4 "/>',
      searchRight: '<polygon points="12.6,12 4.5,4 11.4,4 19.5,12 11.4,20 4.5,20 "/>',
      showZoomBar: '<path d="M22.8,20.7l-4.4-4.4c1.1-1.6,1.8-3.5,1.8-5.6c0-5.2-4.3-9.5-9.5-9.5s-9.5,4.3-9.5,9.5s4.3,9.5,9.5,9.5 c2.1,0,4-0.7,5.6-1.8l4.4,4.4L22.8,20.7z M4.2,10.7c0-3.6,2.9-6.5,6.5-6.5s6.5,2.9,6.5,6.5s-2.9,6.5-6.5,6.5S4.2,14.3,4.2,10.7z"/><polygon points="7.2,9.2 7.2,7.2 9.2,7.2 9.2,6.2 6.2,6.2 6.2,9.2 "/><polygon points="12.2,7.2 14.2,7.2 14.2,9.2 15.2,9.2 15.2,6.2 12.2,6.2 "/><polygon points="9.2,14.2 7.2,14.2 7.2,12.2 6.2,12.2 6.2,15.2 9.2,15.2 "/><polygon points="14.2,12.2 14.2,14.2 12.2,14.2 12.2,15.2 15.2,15.2 15.2,12.2 "/>',
      rubberbandTool: '\n        <g>\n\t        <polygon points="11.5,2 4,2 2,2 2,4 2,11.5 4,11.5 4,4 11.5,4 \t"/>\n\t        <path d="M16,10V8h-2h-4H8v2v4v2h2h4h2v-2V10z M14,14h-4v-4h4V14z"/>\n\t        <polygon points="20,12 20,19 19,19 19,20 12,20 12,22 20,22 22,22 22,20 22,12 \t"/>\n\t        <rect x="16" y="16" class="st0" width="1" height="1"/>\n\t        <rect x="17" y="17" class="st0" width="1" height="1"/>\n\t        <rect x="18" y="18" class="st0" width="1" height="1"/>\n        </g>',
      magnifierTool: '\n        <circle fill="none" stroke-width="2" stroke-miterlimit="10" cx="9.5" cy="9.5" r="7.5"/>\n        <rect x="17" y="13.7" transform="matrix(0.7193 -0.6947 0.6947 0.7193 -7.4537 17.9238)" class="st1" width="3" height="9"/>\n        <polygon points="14,8.5 10.5,8.5 10.5,5 8.5,5 8.5,8.5 5,8.5 5,10.5 8.5,10.5 8.5,14 10.5,14 10.5,10.5 14,10.5 "/>\n        ',
      rotateDocument: '\n        <g>\n\t        <path d="M18,0H5H3v4v18v2h2h13h2v-2V4V0H18z M18,22H5V4h13V22z"/>\n\t        <polygon points="9,12 13,12 13,14 15,11.5 13,9 13,11 9,11 8,11 8,12 8,17 9,17 \t"/>\n        </g>',
      rotatePage: '\n        <g>\n\t        <rect x="16" y="1" width="1" height="1"/>\n\t        <rect x="17" y="2" width="1" height="1"/>\n\t        <rect x="18" y="3" width="1" height="1"/>\n\t        <path class="st0" d="M19,4v1h-0.6H18h-3V2V1.3V1h1V0h-1H5H4H3v24h2h13h1.1H20V5V4H19z M18,22.1H5V2h8v5h1h1h3V22.1z"/>\n\t        <polygon points="13,11 9,11 8,11 8,12 8,17 9,17 9,12 13,12 13,14 15,11.5 13,9 \t"/>\n        </g>'
    }, t._hiddenCss = "hidden", t._commandTagAttr = "command-tag", t._pointMove = function(t, n, o, r) {
      var i, a = t ? 1 : -1;
      return o instanceof e.Point ? (i = o.x, r = o.y) : (i = o, r = r || 0), new e.Point(n.x + a * i, n.y + a * r)
    };
    var u = function() {
      function e() {
        this._actions = [], this._isStarted = !1
      }
      return e.prototype._any = function() {
        return this._actions.length > 0
      }, e.prototype.queue = function(e) {
        var t = this,
          n = this._any();
        this._actions.push(function() {
          e(), t._continue()
        }), this.isStarted && !n && this._continue()
      }, e.prototype._continue = function() {
        var e = this._actions.shift();
        e && e()
      }, e.prototype.start = function() {
        this._isStarted || (this._isStarted = !0, this._continue())
      }, Object.defineProperty(e.prototype, "isStarted", {
        get: function() {
          return this._isStarted
        },
        enumerable: !0,
        configurable: !0
      }), e
    }();
    t._ActionQueue = u, t._createSvgBtn = function(t) {
      var n = o(a + t + s);
      e.addClass(n, "wj-svg-btn");
      var r = document.createElement("a");
      return r.appendChild(n), e.addClass(r, "wj-btn"), r.tabIndex = 0, r
    }, t._setLandscape = function(e, t) {
      if (e.landscape !== t) {
        e.landscape = t;
        var n = e.width;
        e.width = e.height, e.height = n;
        var o = e.leftMargin;
        t ? (e.leftMargin = e.bottomMargin, e.bottomMargin = e.rightMargin, e.rightMargin = e.topMargin, e.topMargin = o) : (e.leftMargin = e.topMargin, e.topMargin = e.rightMargin, e.rightMargin = e.bottomMargin, e.bottomMargin = o)
      }
    }, t._clonePageSettings = function(e) {
      if (!e) return null;
      var n = {};
      return n.height = e.height ? new t._Unit(e.height) : null, n.width = e.width ? new t._Unit(e.width) : null, n.bottomMargin = e.bottomMargin ? new t._Unit(e.bottomMargin) : null, n.leftMargin = e.leftMargin ? new t._Unit(e.leftMargin) : null, n.rightMargin = e.rightMargin ? new t._Unit(e.rightMargin) : null, n.topMargin = e.topMargin ? new t._Unit(e.topMargin) : null, n.landscape = e.landscape, n.paperSize = e.paperSize, n
    }, t._enumToArray = function(e) {
      var t = [];
      for (var n in e) n && n.length && "_" != n[0] && !isNaN(parseInt(n)) && t.push({
        text: e[n],
        value: n
      });
      return t
    }, t._removeChildren = function(t, n) {
      if (t && t.children)
        for (var o = t.children, r = o.length - 1; r > -1; r--) {
          var i = o[r];
          if (null == n || n(i)) {
            var a = i.querySelector(".wj-control");
            a && (a = e.Control.getControl(a)) && a.dispose(), t.removeChild(i)
          }
        }
    }, t._toDOMs = n, t._toDOM = o, t._addEvent = function(e, t, n, o) {
      for (var r, i = t.split(","), a = 0; a < i.length; a++) r = i[a].trim(), e.addEventListener ? e.addEventListener(r, n, o) : e.attachEvent ? e.attachEvent("on" + r, n) : e["on" + r] = n
    }, t._removeEvent = function(e, t, n) {
      for (var o, r = t.split(","), i = 0; i < r.length; i++) o = r[i].trim(), e.removeEventListener ? e.removeEventListener(o, n) : e.detachEvent ? e.detachEvent("on" + o, n) : e["on" + o] = null
    };
    var c = "wj-state-active",
      l = "wj-state-disabled";
    t._checkImageButton = function(t, n) {
      n ? e.addClass(t, c) : e.removeClass(t, c)
    }, t._disableImageButton = function(t, n) {
      n ? e.addClass(t, l) : e.removeClass(t, l)
    }, t._showImageButton = function(n, o) {
      o ? e.removeClass(n, t._hiddenCss) : e.addClass(n, t._hiddenCss)
    }, t._isDisabledImageButton = function(t) {
      return e.hasClass(t, l)
    }, t._isCheckedImageButton = function(t) {
      return e.hasClass(t, c)
    };
    var p = "__wjEvents";
    t._addWjHandler = function(e, t, n, o) {
      if (e) {
        var r = t[p];
        r || (r = t[p] = {});
        var i = r[e];
        i || (i = r[e] = []), i.push(n)
      }
      t.addHandler(n, o)
    }, t._removeAllWjHandlers = function(e, t) {
      if (e) {
        var n = t[p];
        if (n) {
          var o = n[e];
          o && o.forEach(function(e) {
            return t.removeHandler(e)
          })
        }
      }
    }, t._getErrorMessage = function(n) {
      var o;
      return t._ArReportService.IsError(n) ? o = n.json.Error.Description : (o = n, n.Message && (o = n.Message, n.ExceptionMessage && (o += "<br/>" + n.ExceptionMessage))), o || e.culture.Viewer.errorOccured
    }, t._checkSeparatorShown = function(n) {
      for (var o, r, i, a, s = !0, u = 0; u < n.children.length; u++) r = n.children[u], o = e.hasClass(r, "wj-separator"), i = e.hasClass(r, t._hiddenCss), o || i ? o && (s ? i || e.addClass(r, t._hiddenCss) : (i && e.removeClass(r, t._hiddenCss), a = r), s = !0) : s = !1;
      s && a && e.addClass(a, "hidden")
    }, t._twipToPixel = r, t._pixelToTwip = function(e) {
      return t._Unit.convertValue(e, t._UnitType.Dip, t._UnitType.Twip)
    }, t._hasScrollbar = function(e, t) {
      return t ? e.scrollWidth > e.clientWidth : e.scrollHeight > e.clientHeight
    }, t._transformSvg = function(n, o, r, i, a) {
      i = null == i ? 1 : i;
      var s = n.querySelector("g");
      if (s) {
        var u = "scale(" + i + ")";
        if (null != a) switch (a) {
          case t._RotateAngle.Rotation90:
            u += " rotate(90)", u += " translate(0 " + -r + ")";
            break;
          case t._RotateAngle.Rotation180:
            u += " rotate(180)", u += " translate(" + -o + " " + -r + ")";
            break;
          case t._RotateAngle.Rotation270:
            u += " rotate(270)", u += " translate(" + -o + " 0)"
        }
        s.setAttribute("transform", u), e.isIE && ((n = s.parentNode).removeChild(s), n.appendChild(s))
      }
      return n
    }, t._getTransformedPosition = function(n, o, i, a) {
      var s, u, c = {
        x: r(n.x),
        y: r(n.y),
        width: r(n.width),
        height: r(n.height)
      };
      switch (i) {
        case t._RotateAngle.NoRotate:
          s = c.y, u = c.x;
          break;
        case t._RotateAngle.Rotation90:
          s = c.x, u = o.height.valueInPixel - c.y - c.height;
          break;
        case t._RotateAngle.Rotation180:
          s = o.height.valueInPixel - c.y - c.height, u = o.width.valueInPixel - c.x - c.width;
          break;
        case t._RotateAngle.Rotation270:
          s = o.width.valueInPixel - c.x - c.width, u = c.y
      }
      return new e.Point(u * a, s * a)
    }, t._getRotatedSize = function(e, n) {
      return n === t._RotateAngle.NoRotate || n === t._RotateAngle.Rotation180 ? e : {
        width: e.height,
        height: e.width
      }
    }, t._getPositionByHitTestInfo = function(e) {
      return e ? {
        pageIndex: e.pageIndex,
        pageBounds: {
          x: e.x,
          y: e.y,
          width: 0,
          height: 0
        }
      } : {
        pageIndex: 0,
        pageBounds: {
          x: 0,
          y: 0,
          width: 0,
          height: 0
        }
      }
    }, t._strEndsWith = function(e, t, n) {
      return void 0 === n && (n = !1), n ? e.slice(-t.length).toLowerCase() === t.toLowerCase() : e.slice(-t.length) === t
    }, t._isEqual = function(t, n) {
      return t && n && e.isFunction(t.valueOf) && e.isFunction(n.valueOf) ? t.valueOf() === n.valueOf() : t === n
    }
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";

    function n(e) {
      if (null == e) return null;
      for (var t = [], n = 0, r = e.length; n < r; n++) t = t.concat(o(e[n]));
      return t
    }

    function o(e) {
      var t = e.split("/");
      return t.length > 0 && !t[t.length - 1].length && t.splice(t.length - 1), t
    }

    function r(t) {
      var n = [];
      t = t || {};
      for (var o in t)
        if (null !== t[o] && void 0 !== t[o])
          if (e.isArray(t[o]))
            if (t[o].length > 0)
              for (var r = 0; r < t[o].length; r++) n.push(o + "=" + encodeURIComponent(t[o][r]));
            else n.push(o + "=");
          else n.push(o + "=" + encodeURIComponent(t[o]));
      return n.join("&")
    }

    function i(e) {
      return e + (-1 == e.indexOf("?") ? "?" : "&") + "_=" + (new Date).getTime()
    }
    var a = function() {
      function n(t, n) {
        this._hasOutlines = !1, this._pageCount = 0, this._supportedExportDescriptions = [], this._isLoadCompleted = !1, this._isInstanceCreated = !1, this._isDisposed = !1, this._errors = [], this.pageCountChanged = new e.Event, this.disposed = new e.Event, this.pageSettingsChanged = new e.Event, this.loading = new e.Event, this.loadCompleted = new e.Event, this.queryLoadingData = new e.Event, this._httpHandler = n, this._service = this._createDocumentService(t), this._paginated = t.paginated
      }
      return n.prototype.onQueryLoadingData = function(e) {
        this.queryLoadingData.raise(this, e)
      }, n.prototype._updateIsLoadCompleted = function(e) {
        this._isLoadCompleted !== e && (this._isLoadCompleted = e, e && this.onLoadCompleted())
      }, n.prototype._updateIsDisposed = function(e) {
        this._isDisposed !== e && (this._isDisposed = e, this.onDisposed())
      }, n.prototype._getIsDisposed = function() {
        return this._isDisposed
      }, n.prototype._checkHasOutlines = function(e) {
        return e.hasOutlines
      }, n.prototype._checkIsLoadCompleted = function(e) {
        return e.status === t._ExecutionStatus.completed || e.status === t._ExecutionStatus.stopped || e.status === t._ExecutionStatus.loaded
      }, Object.defineProperty(n.prototype, "encodeRequestParams", {
        get: function() {
          return !0
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "executionDateTime", {
        get: function() {
          return this._executionDateTime
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "expiredDateTime", {
        get: function() {
          return this._expiredDateTime
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "errors", {
        get: function() {
          return this._errors
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isLoadCompleted", {
        get: function() {
          return this._isLoadCompleted
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isInstanceCreated", {
        get: function() {
          return this._isInstanceCreated
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isDisposed", {
        get: function() {
          return this._getIsDisposed()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "features", {
        get: function() {
          return this._features
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pageSettings", {
        get: function() {
          return this._pageSettings
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.onPageSettingsChanged = function(t) {
        this.pageSettingsChanged.raise(this, t || new e.EventArgs)
      }, n.prototype.onLoadCompleted = function(t) {
        this.loadCompleted.raise(this, t || new e.EventArgs)
      }, n.prototype.onLoading = function(t) {
        this.loading.raise(this, t || new e.EventArgs)
      }, n.prototype.onDisposed = function(t) {
        this.disposed.raise(this, t || new e.EventArgs)
      }, n.prototype.setPageSettings = function(e) {
        var t = this;
        return this._innerService.setPageSettings(e).then(function(e) {
          return t._updatePageSettings(e)
        })
      }, n.prototype._updatePageSettings = function(e) {
        this._pageSettings = e, this.onPageSettingsChanged()
      }, Object.defineProperty(n.prototype, "_innerService", {
        get: function() {
          return this._service
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "paginated", {
        get: function() {
          return this.pageSettings ? this.pageSettings.paginated : this._paginated
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "hasThumbnails", {
        get: function() {
          return !0
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "hasOutlines", {
        get: function() {
          return this._hasOutlines
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pageCount", {
        get: function() {
          return this._pageCount
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "initialPosition", {
        get: function() {
          return this._initialPosition
        },
        set: function(e) {
          this._initialPosition = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "service", {
        get: function() {
          return this._service
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getSupportedExportDescriptions = function() {
        return this._innerService.getSupportedExportDescriptions()
      }, n.prototype.getBookmark = function(e) {
        return this._innerService.getBookmark(e)
      }, n.prototype.executeCustomAction = function(e) {
        return this._innerService.executeCustomAction(e)
      }, n.prototype.getOutlines = function() {
        return this._innerService.getOutlines()
      }, n.prototype.getFeatures = function() {
        var e = this;
        return this._innerService.getFeatures().then(function(t) {
          e._features = t
        })
      }, n.prototype.dispose = function() {
        var e = this;
        return this._innerService.dispose().then(function() {
          return e._updateIsDisposed(!0)
        })
      }, n.prototype.load = function() {
        var e = this;
        this.onLoading();
        var t = {};
        null != this._paginated && (t["pageSettings.paginated"] = this.paginated);
        var n = new d(t);
        return this.onQueryLoadingData(n), this._innerService.load(n.data).then(function(t) {
          e._updateExecutionInfo(t)
        })
      }, n.prototype._updateExecutionInfo = function(e) {
        null != e && (this._executionDateTime = this._getExecutionDateTime(e), this._expiredDateTime = this._getExpiredDateTime(e), this._updatePageSettings(e.pageSettings), this._features = e.features, this._isInstanceCreated = null != e.status && e.status.status !== t._ExecutionStatus.notFound && e.status.status !== t._ExecutionStatus.cleared, this._updateDocumentStatus(e.status))
      }, n.prototype._updateDocumentStatus = function(e) {
        e && (this._errors = e.errorList, this._initialPosition = e.initialPosition, this._updatePageCount(this._getPageCount(e)), this._expiredDateTime = this._getExpiredDateTime(e), this._hasOutlines = this._checkHasOutlines(e), this._updateIsLoadCompleted(this._checkIsLoadCompleted(e)))
      }, n.prototype._getExecutionDateTime = function(e) {
        return e.loadedDateTime
      }, n.prototype._getExpiredDateTime = function(e) {
        return e.expiredDateTime
      }, n.prototype._getPageCount = function(e) {
        return e.pageCount
      }, n.prototype._updatePageCount = function(e) {
        this._pageCount !== e && (this._pageCount = e, this.onPageCountChanged())
      }, n.prototype.getStatus = function() {
        var e = this;
        return this._innerService.getStatus().then(function(t) {
          e._updateDocumentStatus(t)
        })
      }, n.prototype._createDocumentService = function(e) {
        throw n._abstractMethodException
      }, n.prototype.onPageCountChanged = function(t) {
        this.pageCountChanged.raise(this, t || new e.EventArgs)
      }, n.prototype.export = function(t) {
        var n = this;
        this.getExportedUrl(t).then(function(o) {
          n._innerService.downloadBlob(o).then(function(o) {
            var r = t.format;
            r.match("emf|bmp|gif|jpeg|jpg|png|tif|tiff") && "application/zip" === o.type && (r = "zip"), "zip" === r && "application/x-emf" === o.type && (r = "emf"), e.viewer._saveBlob(o, n._innerService.getFileName() + "." + r)
          })
        })
      }, n.prototype.print = function(t) {
        var n = this;
        if (e.isMobile()) this.export({
          format: "pdf"
        });
        else {
          var o = new e.PrintDocument({
            title: "Document"
          });
          this.renderToFilter({
            format: "html"
          }).then(function(e) {
            o.append(e.responseText), o._getDocument().close(), window.setTimeout(function() {
              n._removeScript(o), n._rotate(o, t), o.print()
            }, 100)
          })
        }
      }, Object.defineProperty(n.prototype, "httpHandler", {
        get: function() {
          return this._httpHandler
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._removeScript = function(e) {
        for (var t = e._getDocument().querySelectorAll("script"), n = 0; n < t.length; n++) {
          var o = t.item(n);
          o.parentElement.removeChild(o)
        }
      }, n.prototype._rotate = function(e, n) {
        if (n && n.length)
          for (var o = e._getDocument().querySelectorAll("svg"), r = 0; r < o.length; r++) {
            var i = n[r];
            if (i) {
              for (var a = o[r], s = document.createElementNS("http://www.w3.org/2000/svg", "g"); a.hasChildNodes();) s.appendChild(a.firstChild);
              a.appendChild(s);
              var u = {
                  width: new l(a.width.baseVal.value),
                  height: new l(a.height.baseVal.value)
                },
                c = t._getRotatedSize(u, i),
                p = a.parentNode;
              p.style.width = c.width.valueInPixel + "px", p.style.height = c.height.valueInPixel + "px", a.setAttribute("width", c.width.valueInPixel.toString() + "px"), a.setAttribute("height", c.height.valueInPixel.toString() + "px"), t._transformSvg(o[r], u.width.valueInPixel, u.height.valueInPixel, 1, i)
            }
          }
      }, n.prototype.renderToFilter = function(e) {
        return this._innerService.renderToFilter(e)
      }, n.prototype.getRenderToFilterUrl = function(e) {
        return this._innerService.getRenderToFilterUrl(e)
      }, n.prototype.getExportedUrl = function(e, t) {
        var n = this;
        void 0 === t && (t = !1);
        var o = new p;
        return this._innerService.getExportedUrl(e).then(function(e) {
          var r = new u(e, null);
          t && n.httpHandler.beforeSend(r), o.resolve(r.url)
        }), o
      }, n.prototype.search = function(e) {
        return this._innerService.search(e)
      }, n._abstractMethodException = "It is an abstract method, please implement it.", n
    }();
    t._DocumentSource = a, t._statusJsonReviver = function(n, o) {
      if (e.isString(o)) {
        if (t._strEndsWith(n, "DateTime")) return new Date(o);
        if ("width" === n || "height" === n || t._strEndsWith(n, "Margin")) return new l(o)
      }
      return o
    };
    var s = function() {
      function t(e, t) {
        this._url = "", this._url = e.serviceUrl || "", this._documentPath = e.filePath, this._httpHandler = t
      }
      return Object.defineProperty(t.prototype, "serviceUrl", {
        get: function() {
          return this._url
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "filePath", {
        get: function() {
          return this._documentPath
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.getStatus = function() {
        throw a._abstractMethodException
      }, t.prototype.setPageSettings = function(e) {
        throw a._abstractMethodException
      }, t.prototype.getBookmark = function(e) {
        throw a._abstractMethodException
      }, t.prototype.executeCustomAction = function(e) {
        throw a._abstractMethodException
      }, t.prototype.load = function(e) {
        throw a._abstractMethodException
      }, t.prototype.dispose = function() {
        throw a._abstractMethodException
      }, t.prototype.getOutlines = function() {
        throw a._abstractMethodException
      }, t.prototype.renderToFilter = function(e) {
        throw a._abstractMethodException
      }, t.prototype.search = function(e) {
        throw a._abstractMethodException
      }, t.prototype.getRenderToFilterUrl = function(e) {
        throw a._abstractMethodException
      }, t.prototype.getExportedUrl = function(e) {
        throw a._abstractMethodException
      }, t.prototype.getSupportedExportDescriptions = function() {
        throw a._abstractMethodException
      }, t.prototype.getFeatures = function() {
        throw a._abstractMethodException
      }, t.prototype.getPingTimeout = function() {
        return 100
      }, t.prototype.getFileName = function() {
        var e = /([^\\/]+)$/.exec(this.filePath)[1];
        return e.lastIndexOf(".") >= 0 ? e.substr(0, e.lastIndexOf(".")) : e
      }, t.prototype.downloadDataUrl = function(e) {
        var t = new p;
        return this.downloadBlob(e).then(function(e) {
          var n = new FileReader;
          n.onloadend = function(e) {
            t.resolve(n.result)
          }, n.readAsDataURL(e)
        }), t
      }, t.prototype.downloadBlob = function(e) {
        var t = new p;
        return this.httpRequest(e, {
          beforeSend: function(e) {
            e.responseType = "blob"
          }
        }).then(function(e) {
          t.resolve(e.response)
        }), t
      }, t.prototype.httpRequest = function(t, n) {
        var o = this;
        n = n || {};
        var r = new p,
          i = n.error,
          a = n.success;
        return n.error = function(t) {
          e.isFunction(i) && i.call(o, t), r.reject(t)
        }, n.success = function(t) {
          e.isFunction(a) && a.call(o, t), r.resolve(t)
        }, e.viewer._httpRequest(t, this._httpHandler, n), r
      }, t
    }();
    t._DocumentService = s, t._pageSettingsJsonReviver = function(n, o) {
      return e.isString(o) && ("width" === n || "height" === n || t._strEndsWith(n, "Margin")) ? new l(o) : o
    }, t._appendQueryString = function(e, t) {
      t = t || {};
      var n = [];
      for (var o in t) n.push(o + "=" + t[o]);
      return n.length && (e += (e.indexOf("?") < 0 ? "?" : "&") + n.join("&")), e
    }, t._joinUrl = function() {
      for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
      for (var r = [], i = 0, a = e.length; i < a; i++) {
        var s = e[i];
        s && ("string" != typeof s ? r = r.concat(n(s)) : r.push(o(s).join("/")))
      }
      return r.join("/")
    }, t._joinStringUrl = n, t._prepareStringUrl = o, t._httpRequest = function(t, n, o) {
      if (o && o.cache || (t = i(t)), o && "GET" !== (o.method || "GET").toUpperCase()) {
        if (o.data && !1 !== o.urlEncode) {
          var a = r(o.data);
          null != a && (o.data = a)
        }!1 !== o.urlEncode && (o.requestHeaders ? o.requestHeaders["Content-Type"] || (o.requestHeaders["Content-Type"] = "application/x-www-form-urlencoded") : o.requestHeaders = {
          "Content-Type": "application/x-www-form-urlencoded"
        })
      }
      n && n.requestHeaders && ((o = o || {}).requestHeaders = o.requestHeaders || {}, Object.keys(n.requestHeaders).forEach(function(e) {
        o.requestHeaders[e] = n.requestHeaders[e]
      }));
      var s = new u(t, o);
      return n && n.beforeSend && n.beforeSend(s), e.httpRequest(s.url, s.settings)
    }, t._saveBlob = function(e, t) {
      if (e && e instanceof Blob && t)
        if (navigator.msSaveBlob) navigator.msSaveBlob(e, t);
        else {
          var n = document.createElement("a"),
            o = function(e) {
              var t = document.createEvent("MouseEvents");
              t.initMouseEvent("click", !0, !1, window, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, null), e.dispatchEvent(t)
            };
          if ("download" in n) {
            var r = window.URL || window.webkitURL || window,
              i = r.createObjectURL(e);
            n.href = i, n.target = "_blank", n.download = t, o(n), n = null, window.setTimeout(function() {
              r.revokeObjectURL(i)
            }, 3e4)
          } else {
            var a = new FileReader;
            a.onloadend = function(e) {
              n.download = t, n.href = a.result, o(n), n = null
            }, a.readAsDataURL(e)
          }
        }
    }, t._objToParams = r;
    var u = function(e) {
      function t(t, n) {
        var o = e.call(this) || this;
        return o._url = t, o._settings = n, o
      }
      return __extends(t, e), Object.defineProperty(t.prototype, "url", {
        get: function() {
          return this._url
        },
        set: function(e) {
          this._url = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "settings", {
        get: function() {
          return this._settings
        },
        set: function(e) {
          this._settings = e
        },
        enumerable: !0,
        configurable: !0
      }), t
    }(e.EventArgs);
    t.RequestEventArgs = u, t._disableCache = i;
    var c;
    ! function(e) {
      e[e.Document = 0] = "Document", e[e.Inch = 1] = "Inch", e[e.Mm = 2] = "Mm", e[e.Pica = 3] = "Pica", e[e.Point = 4] = "Point", e[e.Twip = 5] = "Twip", e[e.InHs = 6] = "InHs", e[e.Display = 7] = "Display", e[e.Cm = 8] = "Cm", e[e.Dip = 9] = "Dip"
    }(c = t._UnitType || (t._UnitType = {}));
    var l = function() {
      function t(n, o) {
        if (void 0 === o && (o = c.Dip), t._initUnitTypeDic(), e.isObject(n)) {
          var r = n;
          n = r.value, o = r.units
        } else if (e.isString(n)) {
          var i = parseFloat(n);
          isNaN(i) || (o = t._unitTypeDic[n.substr(i.toString().length)], n = i)
        }
        this._value = n, this._units = o, this._valueInPixel = t.convertValue(n, o, c.Dip)
      }
      return t._initUnitTypeDic = function() {
        if (!t._unitTypeDic) {
          t._unitTypeDic = {};
          for (var e in t._unitTypes) t._unitTypeDic[t._unitTypeDic[e] = t._unitTypes[e]] = e
        }
      }, Object.defineProperty(t.prototype, "value", {
        get: function() {
          return this._value
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "units", {
        get: function() {
          return this._units
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "valueInPixel", {
        get: function() {
          return this._valueInPixel
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.toString = function() {
        return t.toString(this)
      }, t.toString = function(e) {
        return null == e.value ? "" : e.value + t._unitTypeDic[e.units]
      }, t.convertValue = function(e, n, o) {
        if (n === o) return e;
        var r;
        switch (n) {
          case c.Document:
            r = e / t._DocumentUnitsPerInch;
            break;
          case c.Inch:
            r = e;
            break;
          case c.Mm:
            r = e / t._MmPerInch;
            break;
          case c.Pica:
            r = e / t._PicaPerInch;
            break;
          case c.Point:
            r = e / t._PointsPerInch;
            break;
          case c.Twip:
            r = e / t._TwipsPerInch;
            break;
          case c.InHs:
            r = e / 100;
            break;
          case c.Display:
            r = e / t._DisplayPerInch;
            break;
          case c.Cm:
            r = e / t._CmPerInch;
            break;
          case c.Dip:
            r = e / t._DipPerInch;
            break;
          default:
            throw "Invalid from _UnitType: " + n
        }
        switch (o) {
          case c.Document:
            return r * t._DocumentUnitsPerInch;
          case c.Inch:
            return r;
          case c.Mm:
            return r * t._MmPerInch;
          case c.Pica:
            return r * t._PicaPerInch;
          case c.Point:
            return r * t._PointsPerInch;
          case c.Twip:
            return r * t._TwipsPerInch;
          case c.InHs:
            return 100 * r;
          case c.Display:
            return r * t._DisplayPerInch;
          case c.Cm:
            return r * t._CmPerInch;
          case c.Dip:
            return r * t._DipPerInch;
          default:
            throw "Invalid to _UnitType: " + o
        }
      }, t._MmPerInch = 25.4, t._DocumentUnitsPerInch = 300, t._PointsPerInch = 72, t._TwipsPerInch = 1440, t._PicaPerInch = 6, t._CmPerInch = t._MmPerInch / 10, t._DisplayPerInch = 75, t._DipPerInch = 96, t._unitTypes = {
        doc: c.Document,
        in : c.Inch,
        mm: c.Mm,
        pc: c.Pica,
        pt: c.Point,
        tw: c.Twip,
        inhs: c.InHs,
        dsp: c.Display,
        cm: c.Cm,
        dip: c.Dip,
        px: c.Dip
      }, t
    }();
    t._Unit = l;
    var p = function() {
      function e() {
        this._callbacks = []
      }
      return e.prototype.then = function(e, t) {
        return this._callbacks.push({
          onFulfilled: e,
          onRejected: t
        }), this
      }, e.prototype.catch = function(e) {
        return this.then(null, e)
      }, e.prototype.resolve = function(e) {
        var t = this;
        return setTimeout(function() {
          try {
            t.onFulfilled(e)
          } catch (e) {
            t.onRejected(e)
          }
        }, 0), this
      }, e.prototype.reject = function(e) {
        var t = this;
        return setTimeout(function() {
          t.onRejected(e)
        }, 0), this
      }, e.prototype.onFulfilled = function(e) {
        for (var t; t = this._callbacks.shift();)
          if (t.onFulfilled) {
            var n = t.onFulfilled(e);
            void 0 !== n && (e = n)
          }
      }, e.prototype.onRejected = function(e) {
        for (var t; t = this._callbacks.shift();)
          if (t.onRejected) {
            var n = t.onRejected(e);
            return void this.onFulfilled(n)
          }
        throw e
      }, e
    }();
    t._Promise = p;
    var h = function(e) {
      function t(t) {
        var n = e.call(this) || this;
        return n._promises = t, n._init(), n
      }
      return __extends(t, e), t.prototype._init = function() {
        var e = this;
        if (this._promises && this._promises.length) {
          var t = this._promises.length,
            n = 0,
            o = [],
            r = !1;
          this._promises.some(function(i) {
            return i.then(function(i) {
              r || (o.push(i), ++n >= t && e.resolve(o))
            }).catch(function(t) {
              r = !0, e.reject(t)
            }), r
          })
        } else this.reject("No promises in current composited promise.")
      }, t
    }(p);
    t._CompositedPromise = h;
    ! function(e) {
      e[e.Custom = 0] = "Custom", e[e.Letter = 1] = "Letter", e[e.LetterSmall = 2] = "LetterSmall", e[e.Tabloid = 3] = "Tabloid", e[e.Ledger = 4] = "Ledger", e[e.Legal = 5] = "Legal", e[e.Statement = 6] = "Statement", e[e.Executive = 7] = "Executive", e[e.A3 = 8] = "A3", e[e.A4 = 9] = "A4", e[e.A4Small = 10] = "A4Small", e[e.A5 = 11] = "A5", e[e.B4 = 12] = "B4", e[e.B5 = 13] = "B5", e[e.Folio = 14] = "Folio", e[e.Quarto = 15] = "Quarto", e[e.Standard10x14 = 16] = "Standard10x14", e[e.Standard11x17 = 17] = "Standard11x17", e[e.Note = 18] = "Note", e[e.Number9Envelope = 19] = "Number9Envelope", e[e.Number10Envelope = 20] = "Number10Envelope", e[e.Number11Envelope = 21] = "Number11Envelope", e[e.Number12Envelope = 22] = "Number12Envelope", e[e.Number14Envelope = 23] = "Number14Envelope", e[e.CSheet = 24] = "CSheet", e[e.DSheet = 25] = "DSheet", e[e.ESheet = 26] = "ESheet", e[e.DLEnvelope = 27] = "DLEnvelope", e[e.C5Envelope = 28] = "C5Envelope", e[e.C3Envelope = 29] = "C3Envelope", e[e.C4Envelope = 30] = "C4Envelope", e[e.C6Envelope = 31] = "C6Envelope", e[e.C65Envelope = 32] = "C65Envelope", e[e.B4Envelope = 33] = "B4Envelope", e[e.B5Envelope = 34] = "B5Envelope", e[e.B6Envelope = 35] = "B6Envelope", e[e.ItalyEnvelope = 36] = "ItalyEnvelope", e[e.MonarchEnvelope = 37] = "MonarchEnvelope", e[e.PersonalEnvelope = 38] = "PersonalEnvelope", e[e.USStandardFanfold = 39] = "USStandardFanfold", e[e.GermanStandardFanfold = 40] = "GermanStandardFanfold", e[e.GermanLegalFanfold = 41] = "GermanLegalFanfold", e[e.IsoB4 = 42] = "IsoB4", e[e.JapanesePostcard = 43] = "JapanesePostcard", e[e.Standard9x11 = 44] = "Standard9x11", e[e.Standard10x11 = 45] = "Standard10x11", e[e.Standard15x11 = 46] = "Standard15x11", e[e.InviteEnvelope = 47] = "InviteEnvelope", e[e.LetterExtra = 50] = "LetterExtra", e[e.LegalExtra = 51] = "LegalExtra", e[e.TabloidExtra = 52] = "TabloidExtra", e[e.A4Extra = 53] = "A4Extra", e[e.LetterTransverse = 54] = "LetterTransverse", e[e.A4Transverse = 55] = "A4Transverse", e[e.LetterExtraTransverse = 56] = "LetterExtraTransverse", e[e.APlus = 57] = "APlus", e[e.BPlus = 58] = "BPlus", e[e.LetterPlus = 59] = "LetterPlus", e[e.A4Plus = 60] = "A4Plus", e[e.A5Transverse = 61] = "A5Transverse", e[e.B5Transverse = 62] = "B5Transverse", e[e.A3Extra = 63] = "A3Extra", e[e.A5Extra = 64] = "A5Extra", e[e.B5Extra = 65] = "B5Extra", e[e.A2 = 66] = "A2", e[e.A3Transverse = 67] = "A3Transverse", e[e.A3ExtraTransverse = 68] = "A3ExtraTransverse", e[e.JapaneseDoublePostcard = 69] = "JapaneseDoublePostcard", e[e.A6 = 70] = "A6", e[e.JapaneseEnvelopeKakuNumber2 = 71] = "JapaneseEnvelopeKakuNumber2", e[e.JapaneseEnvelopeKakuNumber3 = 72] = "JapaneseEnvelopeKakuNumber3", e[e.JapaneseEnvelopeChouNumber3 = 73] = "JapaneseEnvelopeChouNumber3", e[e.JapaneseEnvelopeChouNumber4 = 74] = "JapaneseEnvelopeChouNumber4", e[e.LetterRotated = 75] = "LetterRotated", e[e.A3Rotated = 76] = "A3Rotated", e[e.A4Rotated = 77] = "A4Rotated", e[e.A5Rotated = 78] = "A5Rotated", e[e.B4JisRotated = 79] = "B4JisRotated", e[e.B5JisRotated = 80] = "B5JisRotated", e[e.JapanesePostcardRotated = 81] = "JapanesePostcardRotated", e[e.JapaneseDoublePostcardRotated = 82] = "JapaneseDoublePostcardRotated", e[e.A6Rotated = 83] = "A6Rotated", e[e.JapaneseEnvelopeKakuNumber2Rotated = 84] = "JapaneseEnvelopeKakuNumber2Rotated", e[e.JapaneseEnvelopeKakuNumber3Rotated = 85] = "JapaneseEnvelopeKakuNumber3Rotated", e[e.JapaneseEnvelopeChouNumber3Rotated = 86] = "JapaneseEnvelopeChouNumber3Rotated", e[e.JapaneseEnvelopeChouNumber4Rotated = 87] = "JapaneseEnvelopeChouNumber4Rotated", e[e.B6Jis = 88] = "B6Jis", e[e.B6JisRotated = 89] = "B6JisRotated", e[e.Standard12x11 = 90] = "Standard12x11", e[e.JapaneseEnvelopeYouNumber4 = 91] = "JapaneseEnvelopeYouNumber4", e[e.JapaneseEnvelopeYouNumber4Rotated = 92] = "JapaneseEnvelopeYouNumber4Rotated", e[e.Prc16K = 93] = "Prc16K", e[e.Prc32K = 94] = "Prc32K", e[e.Prc32KBig = 95] = "Prc32KBig", e[e.PrcEnvelopeNumber1 = 96] = "PrcEnvelopeNumber1", e[e.PrcEnvelopeNumber2 = 97] = "PrcEnvelopeNumber2", e[e.PrcEnvelopeNumber3 = 98] = "PrcEnvelopeNumber3", e[e.PrcEnvelopeNumber4 = 99] = "PrcEnvelopeNumber4", e[e.PrcEnvelopeNumber5 = 100] = "PrcEnvelopeNumber5", e[e.PrcEnvelopeNumber6 = 101] = "PrcEnvelopeNumber6", e[e.PrcEnvelopeNumber7 = 102] = "PrcEnvelopeNumber7", e[e.PrcEnvelopeNumber8 = 103] = "PrcEnvelopeNumber8", e[e.PrcEnvelopeNumber9 = 104] = "PrcEnvelopeNumber9", e[e.PrcEnvelopeNumber10 = 105] = "PrcEnvelopeNumber10", e[e.Prc16KRotated = 106] = "Prc16KRotated", e[e.Prc32KRotated = 107] = "Prc32KRotated", e[e.Prc32KBigRotated = 108] = "Prc32KBigRotated", e[e.PrcEnvelopeNumber1Rotated = 109] = "PrcEnvelopeNumber1Rotated", e[e.PrcEnvelopeNumber2Rotated = 110] = "PrcEnvelopeNumber2Rotated", e[e.PrcEnvelopeNumber3Rotated = 111] = "PrcEnvelopeNumber3Rotated", e[e.PrcEnvelopeNumber4Rotated = 112] = "PrcEnvelopeNumber4Rotated", e[e.PrcEnvelopeNumber5Rotated = 113] = "PrcEnvelopeNumber5Rotated", e[e.PrcEnvelopeNumber6Rotated = 114] = "PrcEnvelopeNumber6Rotated", e[e.PrcEnvelopeNumber7Rotated = 115] = "PrcEnvelopeNumber7Rotated", e[e.PrcEnvelopeNumber8Rotated = 116] = "PrcEnvelopeNumber8Rotated", e[e.PrcEnvelopeNumber9Rotated = 117] = "PrcEnvelopeNumber9Rotated", e[e.PrcEnvelopeNumber10Rotated = 118] = "PrcEnvelopeNumber10Rotated"
    }(t._PaperKind || (t._PaperKind = {}));
    var d = function(e) {
      function t(t) {
        var n = e.call(this) || this;
        return n._data = t || {}, n
      }
      return __extends(t, e), Object.defineProperty(t.prototype, "data", {
        get: function() {
          return this._data
        },
        enumerable: !0,
        configurable: !0
      }), t
    }(e.EventArgs);
    t.QueryLoadingDataEventArgs = d
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";

    function n(e) {
      return JSON.parse(e, t._statusJsonReviver)
    }
    var o = function(e) {
      function n() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(n, e), n.prototype.render = function() {
        throw t._DocumentSource._abstractMethodException
      }, n
    }(t._DocumentService);
    t._ReportServiceBase = o;
    var r = function(n) {
      function o(t, o) {
        var r = n.call(this, t, o) || this;
        return r._status = s.notFound, r.statusChanged = new e.Event, r
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "autoRun", {
        get: function() {
          return !0
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "hasParameters", {
        get: function() {
          throw t._DocumentSource._abstractMethodException
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "status", {
        get: function() {
          return this._status
        },
        set: function(t) {
          t !== this._status && (this._status = t, this.onStatusChanged(e.EventArgs.empty))
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.getParameters = function() {
        throw t._DocumentSource._abstractMethodException
      }, o.prototype.setParameters = function(e) {
        throw t._DocumentSource._abstractMethodException
      }, o.prototype.render = function() {
        var e = this;
        return this._innerService.render().then(function(t) {
          return e._updateDocumentStatus(t)
        })
      }, o.prototype.executeCustomAction = function(e) {
        var t = this;
        return this._innerService.executeCustomAction(e).then(function(e) {
          return t._updateDocumentStatus(e)
        })
      }, o.prototype.onStatusChanged = function(e) {
        this.statusChanged.raise(this, e)
      }, Object.defineProperty(o.prototype, "_innerService", {
        get: function() {
          return this.service
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._updateDocumentStatus = function(e) {
        e && (this.status = e.status), n.prototype._updateDocumentStatus.call(this, e)
      }, o
    }(t._DocumentSource);
    t._ReportSourceBase = r;
    var i = function(n) {
      function o(e, t) {
        var o = n.call(this, e, t) || this;
        return o._hasParameters = !1, o
      }
      return __extends(o, n), o.getReportNames = function(e, t, n) {
        return a.getReportNames(e, t, n)
      }, o.getReports = function(t, n, o, r) {
        return e.isBoolean(o) && (o = {
          recursive: o
        }), a.getReports(t, n, o, r)
      }, Object.defineProperty(o.prototype, "reportName", {
        get: function() {
          return this._innerService ? this._innerService.reportName : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "hasParameters", {
        get: function() {
          return this._hasParameters
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.load = function() {
        return n.prototype.load.call(this)
      }, o.prototype.cancel = function() {
        var e = this;
        return this._innerService.cancel().then(function(t) {
          return e._updateDocumentStatus(t)
        })
      }, o.prototype.dispose = function() {
        return n.prototype.dispose.call(this)
      }, o.prototype.setParameters = function(e) {
        var t = this;
        return this._innerService.setParameters(e).then(function(e) {
          return void(t._parameters = e)
        })
      }, o.prototype.getParameters = function() {
        var e = this;
        if (this._parameters && this._parameters.length) {
          var n = new t._Promise;
          return n.resolve(this._parameters), n
        }
        return this._innerService.getParameters().then(function(t) {
          return void(e._parameters = t)
        })
      }, o.prototype._getIsDisposed = function() {
        return n.prototype._getIsDisposed.call(this) || this._innerService.isCleared
      }, o.prototype._updateExecutionInfo = function(e) {
        null == e || this.isDisposed || (this._hasParameters = !!e.hasParameters, n.prototype._updateExecutionInfo.call(this, e))
      }, o.prototype._checkIsLoadCompleted = function(e) {
        return e.status === s.completed || e.status === s.stopped
      }, o.prototype._createDocumentService = function(e) {
        return new a(e, this.httpHandler)
      }, Object.defineProperty(o.prototype, "_innerService", {
        get: function() {
          return this.service
        },
        enumerable: !0,
        configurable: !0
      }), o
    }(r);
    t._Report = i;
    var a = function(o) {
      function r(e, t) {
        var n = o.call(this, e, t) || this;
        return n._reportName = e.reportName, n
      }
      return __extends(r, o), Object.defineProperty(r.prototype, "isCleared", {
        get: function() {
          return !this._instanceId && this._status == s.cleared
        },
        enumerable: !0,
        configurable: !0
      }), r.getReportNames = function(e, t, n) {
        return r.getReports(e, t, null, n).then(function(e) {
          if (!e) return null;
          var t = [];
          return e.items.forEach(function(e) {
            e.type === u.Report && t.push(e.name)
          }), t
        })
      }, r.getReports = function(n, o, r, i) {
        var a = new t._Promise,
          s = t._joinUrl(n, o);
        return e.viewer._httpRequest(s, i, {
          data: r,
          success: function(e) {
            a.resolve(JSON.parse(e.responseText))
          },
          error: function(e) {
            return a.reject(e)
          }
        }), a
      }, Object.defineProperty(r.prototype, "reportName", {
        get: function() {
          return this._reportName
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.getBookmark = function(e) {
        var n = new t._Promise;
        return this._checkReportInstanceController(n) ? (this.httpRequest(this._getReportInstancesUrl(r._bookmarkAction, e)).then(function(e) {
          n.resolve(JSON.parse(e.responseText))
        }), n) : n
      }, r.prototype.executeCustomAction = function(e) {
        var t = {};
        return t[r._customActionParam] = e.data, this.render(t)
      }, r.prototype.getStatus = function() {
        var e = new t._Promise;
        return this.httpRequest(this._statusLocation).then(function(n) {
          e.resolve(JSON.parse(n.responseText, t._statusJsonReviver))
        }), e
      }, r.prototype.getDocumentStatus = function() {
        return this._getReportCache()
      }, r.prototype._getReportCache = function() {
        var e = new t._Promise;
        return this._checkReportInstanceController(e) ? (this.httpRequest(this._getReportInstancesUrl()).then(function(t) {
          e.resolve(n(t.responseText))
        }), e) : e
      }, r.prototype.getParameters = function() {
        var e = new t._Promise;
        return this._checkReportInstanceController(e) ? (this.httpRequest(this._getReportInstancesUrl(r._parametersAction)).then(function(t) {
          e.resolve(JSON.parse(t.responseText))
        }), e) : e
      }, r.prototype._getUrlMainPart = function() {
        return t._joinUrl(this.serviceUrl, this.filePath, this.reportName)
      }, r.prototype._getReportUrl = function() {
        for (var e = [], n = 0; n < arguments.length; n++) e[n] = arguments[n];
        return t._joinUrl(this._getUrlMainPart(), r._reportCommand, e)
      }, r.prototype._getReportInstancesUrl = function() {
        for (var e = [], n = 0; n < arguments.length; n++) e[n] = arguments[n];
        return t._joinUrl(this._getUrlMainPart(), r._instancesCommand, this._instanceId, e)
      }, r.prototype._checkReportController = function(e) {
        return !(null == this.serviceUrl || !this.filePath || (t._strEndsWith(this.filePath, ".flxr", !0) || t._strEndsWith(this.filePath, ".xml", !0)) && !this.reportName) || (e && e.reject(r._invalidReportControllerError), !1)
      }, r.prototype._checkReportInstanceController = function(e) {
        return !(!this._checkReportController(e) || !this._instanceId) || (e && e.reject(r._invalidReportCacheControllerError), !1)
      }, r.prototype._getError = function(e) {
        var t = e.responseText;
        try {
          t && (t = JSON.parse(t))
        } finally {
          return t
        }
      }, r.prototype.render = function(e) {
        var o = this,
          i = new t._Promise;
        return this._checkReportInstanceController(i) ? (this.httpRequest(this._getReportInstancesUrl(r._renderAction), {
          method: "POST",
          data: e
        }).then(function(e) {
          var t = 202 === e.status ? {
            status: s.rendering
          } : n(e.responseText);
          o._status = t.status, i.resolve(t)
        }, function(e) {
          i.reject(o._getError(e))
        }), i) : i
      }, r.prototype.renderToFilter = function(e) {
        var n = this,
          o = new t._Promise;
        return this._checkReportInstanceController(o) ? (this.getRenderToFilterUrl(e).then(function(e) {
          n.httpRequest(e, {
            cache: !0
          }).then(function(e) {
            o.resolve(e)
          }, function(e) {
            o.reject(n._getError(e))
          })
        }), o) : o
      }, r.prototype.load = function(e) {
        var o = this,
          r = new t._Promise;
        return this._checkReportController(r) ? (this.httpRequest(this._getReportInstancesUrl(), {
          method: "POST",
          data: e
        }).then(function(e) {
          var t = n(e.responseText);
          o._instanceId = t.id, o._status = s.loaded, o._outlinesLocation = t.outlinesLocation, o._statusLocation = t.statusLocation, o._pageSettingsLocation = t.pageSettingsLocation, o._featuresLocation = t.featuresLocation, o._parametersLocation = t.parametersLocation, r.resolve(t)
        }, function(e) {
          r.reject(o._getError(e))
        }), r) : r
      }, r.prototype.cancel = function() {
        var e = this,
          o = new t._Promise;
        return this._checkReportInstanceController(o) ? this._status !== s.rendering ? (o.reject("Cannot execute cancel when the report is not rendering."), o) : (this.httpRequest(this._getReportInstancesUrl(r._cancelAction), {
          method: "POST"
        }).then(function(t) {
          var r = n(t.responseText);
          e._status = r.status.status, o.resolve(r)
        }), o) : o
      }, r.prototype.dispose = function() {
        var e = this,
          n = new t._Promise;
        return this._checkReportInstanceController() ? (this.httpRequest(this._getReportInstancesUrl(), {
          method: "DELETE"
        }).then(function(t) {
          e._status = s.cleared, e._instanceId = "", n.resolve()
        }), n) : n
      }, r.prototype.getOutlines = function() {
        var e = new t._Promise;
        return this._checkReportInstanceController(e) ? (this.httpRequest(this._getReportInstancesUrl(r._outlinesAction)).then(function(t) {
          e.resolve(JSON.parse(t.responseText))
        }), e) : e
      }, r.prototype.getRenderToFilterUrl = function(e) {
        var n = new t._Promise,
          o = null;
        return this._checkReportInstanceController() && (o = this._getReportInstancesUrl(r._exportAction), o = t._disableCache(o), o = t._appendQueryString(o, e), n.resolve(o)), n.resolve(o), n
      }, r.prototype.getExportedUrl = function(e) {
        return this.getRenderToFilterUrl(e)
      }, r.prototype.search = function(e) {
        var n = new t._Promise;
        return this._checkReportInstanceController(n) ? (this.httpRequest(this._getReportInstancesUrl(r._searchAction), {
          data: e
        }).then(function(e) {
          n.resolve(JSON.parse(e.responseText))
        }), n) : n
      }, r.prototype.setPageSettings = function(e) {
        var n = this,
          o = new t._Promise;
        if (!this._checkReportInstanceController(o)) return o;
        var i = this._getReportInstancesUrl(r._pageSettingsAction);
        return this.httpRequest(i, {
          method: "PUT",
          data: e
        }).then(function(e) {
          o.resolve(JSON.parse(e.responseText, t._pageSettingsJsonReviver))
        }, function(e) {
          o.reject(n._getError(e))
        }), o
      }, r.prototype.setParameters = function(e) {
        var n = this,
          o = new t._Promise;
        if (!this._checkReportInstanceController(o)) return o;
        Object.keys(e).forEach(function(t) {
          null === e[t] && (e[t] = "")
        });
        var i = this._getReportInstancesUrl(r._parametersAction);
        return this.httpRequest(i, {
          method: "PATCH",
          data: e
        }).then(function(e) {
          o.resolve(JSON.parse(e.responseText))
        }, function(e) {
          o.reject(n._getError(e))
        }), o
      }, r.prototype.getSupportedExportDescriptions = function() {
        var e = new t._Promise;
        return this._checkReportInstanceController(e) ? (this.httpRequest(this._getReportInstancesUrl(r._supportedFormatsAction)).then(function(t) {
          e.resolve(JSON.parse(t.responseText))
        }), e) : e
      }, r.prototype.getFeatures = function() {
        var e = new t._Promise;
        return this._checkReportInstanceController(e) ? (this.httpRequest(this._featuresLocation).then(function(t) {
          e.resolve(JSON.parse(t.responseText))
        }), e) : e
      }, r._reportCommand = "$report", r._instancesCommand = "$instances", r._customActionParam = "actionString", r._renderAction = "render", r._searchAction = "search", r._cancelAction = "stop", r._outlinesAction = "outlines", r._exportAction = "export", r._parametersAction = "parameters", r._bookmarkAction = "bookmarks", r._pageSettingsAction = "pagesettings", r._supportedFormatsAction = "supportedformats", r._invalidReportControllerError = "Cannot call the service without service url, document path or report name.", r._invalidReportCacheControllerError = "Cannot call the service when service url is not set or the report is not loaded.", r
    }(o);
    t._ReportService = a, t._parseReportExecutionInfo = n;
    var s = function() {
      function e() {}
      return e.loaded = "Loaded", e.rendering = "Rendering", e.completed = "Completed", e.stopped = "Stopped", e.cleared = "Cleared", e.notFound = "NotFound", e
    }();
    t._ExecutionStatus = s;
    ! function(e) {
      e[e.Boolean = 0] = "Boolean", e[e.DateTime = 1] = "DateTime", e[e.Time = 2] = "Time", e[e.Date = 3] = "Date", e[e.Integer = 4] = "Integer", e[e.Float = 5] = "Float", e[e.String = 6] = "String"
    }(t._ParameterType || (t._ParameterType = {}));
    var u;
    ! function(e) {
      e[e.Folder = 0] = "Folder", e[e.File = 1] = "File", e[e.Report = 2] = "Report"
    }(u = t.CatalogItemType || (t.CatalogItemType = {}))
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(e) {
    "use strict";

    function t(t) {
      return JSON.parse(t, e._statusJsonReviver)
    }
    var n = function(t) {
      function n(n, o) {
        var r = t.call(this, n, o) || this;
        return r._status = e._ExecutionStatus.notFound, r
      }
      return __extends(n, t), Object.defineProperty(n.prototype, "status", {
        get: function() {
          return this._status
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "_innerService", {
        get: function() {
          return this.service
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._createDocumentService = function(e) {
        return new o(e, this.httpHandler)
      }, n.prototype.load = function() {
        return t.prototype.load.call(this)
      }, n.prototype._updateStatus = function(e) {
        this._status !== e && (this._status = e)
      }, n.prototype.getStatus = function() {
        var t = this,
          n = new e.QueryLoadingDataEventArgs;
        return this.onQueryLoadingData(n), this._innerService.getStatus(n.data).then(function(e) {
          return t._updateDocumentStatus(e)
        })
      }, n.prototype.renderToFilter = function(t) {
        var n = new e.QueryLoadingDataEventArgs;
        return this.onQueryLoadingData(n), this._innerService.renderToFilter(t, n.data)
      }, n.prototype._updateDocumentStatus = function(e) {
        null != e && (this._updateStatus(e.status), t.prototype._updateDocumentStatus.call(this, e))
      }, n
    }(e._DocumentSource);
    e._PdfDocumentSource = n;
    var o = function(n) {
      function o() {
        return null !== n && n.apply(this, arguments) || this
      }
      return __extends(o, n), o.prototype._getPdfUrl = function() {
        for (var t = [], n = 0; n < arguments.length; n++) t[n] = arguments[n];
        return e._joinUrl(this.serviceUrl, this.filePath, o._pdfCommand, t)
      }, o.prototype._getPdfStatus = function(n) {
        var o = this,
          r = new e._Promise;
        return this._checkPdfController(r) ? (this.httpRequest(this._getPdfUrl(), {
          data: n
        }).then(function(n) {
          var i = t(n.responseText);
          o._status = e._ExecutionStatus.loaded, o._statusLocation = i.statusLocation, o._featuresLocation = i.featuresLocation, r.resolve(i)
        }, function(e) {
          r.reject(e.statusText)
        }), r) : r
      }, o.prototype._checkPdfController = function(e) {
        return !(null == this.serviceUrl || !this.filePath) || (e && e.reject(o._invalidPdfControllerError), !1)
      }, o.prototype.dispose = function() {
        var t = new e._Promise;
        return t.resolve(), t
      }, o.prototype.load = function(e) {
        return this._getPdfStatus(e)
      }, o.prototype.getStatus = function(t) {
        var n = new e._Promise;
        return this.httpRequest(this._statusLocation, {
          data: t
        }).then(function(e) {
          n.resolve(JSON.parse(e.responseText))
        }), n
      }, o.prototype.renderToFilter = function(t, n) {
        var o = this,
          r = new e._Promise;
        return this._checkPdfController(r) ? (this.getRenderToFilterUrl(t).then(function(e) {
          o.httpRequest(e, {
            data: n,
            cache: !0
          }).then(function(e) {
            r.resolve(e)
          })
        }), r) : r
      }, o.prototype.getRenderToFilterUrl = function(t) {
        var n = new e._Promise,
          r = null;
        return this._checkPdfController() && (r = this._getPdfUrl(o._exportAction), r = e._disableCache(r), r = e._appendQueryString(r, t)), n.resolve(r), n
      }, o.prototype.getExportedUrl = function(e) {
        return this.getRenderToFilterUrl(e)
      }, o.prototype.getSupportedExportDescriptions = function() {
        var t = new e._Promise;
        return this._checkPdfController(t) ? (this.httpRequest(this._getPdfUrl(o._supportedFormatsAction)).then(function(e) {
          t.resolve(JSON.parse(e.responseText))
        }), t) : t
      }, o.prototype.getFeatures = function() {
        var t = new e._Promise;
        return this._checkPdfController(t) ? (this.httpRequest(this._featuresLocation).then(function(e) {
          t.resolve(JSON.parse(e.responseText))
        }), t) : t
      }, o.prototype.search = function(t) {
        var n = new e._Promise;
        return this._checkPdfController(n) ? (this.httpRequest(this._getPdfUrl(o._searchAction), {
          data: t
        }).then(function(e) {
          n.resolve(JSON.parse(e.responseText))
        }), n) : n
      }, o._pdfCommand = "$pdf", o._exportAction = "export", o._supportedFormatsAction = "supportedformats", o._searchAction = "search", o._invalidPdfControllerError = "Cannot call the service when service url is not set or the pdf is not loaded.", o
    }(e._DocumentService);
    e._PdfDocumentService = o, e._parseExecutionInfo = t
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  e.viewer || (e.viewer = {}), e._addCultureInfo("Viewer", {
    cancel: "Cancel",
    ok: "OK",
    bottom: "Bottom:",
    top: "Top:",
    right: "Right:",
    left: "Left:",
    margins: "Margins(inches)",
    orientation: "Orientation:",
    paperKind: "Paper Kind:",
    pageSetup: "Page Setup",
    landscape: "Landscape",
    portrait: "Portrait",
    pageNumber: "Page Number",
    zoomFactor: "Zoom Factor",
    paginated: "Print Layout",
    print: "Print",
    search: "Search",
    matchCase: "Match case",
    wholeWord: "Match whole word only",
    searchResults: "Search Results",
    previousPage: "Previous Page",
    nextPage: "Next Page",
    firstPage: "First Page",
    lastPage: "Last Page",
    backwardHistory: "Backward",
    forwardHistory: "Forward",
    pageCount: "Page Count",
    selectTool: "Select Tool",
    moveTool: "Move Tool",
    continuousMode: "Continuous Page View",
    singleMode: "Single Page View",
    wholePage: "Fit Whole Page",
    pageWidth: "Fit Page Width",
    zoomOut: "Zoom Out",
    zoomIn: "Zoom In",
    rubberbandTool: "Zoom by Selection",
    magnifierTool: "Magnifier",
    rotatePage: "Rotate Page",
    rotateDocument: "Rotate Document",
    exports: "Export",
    fullScreen: "Full Screen",
    exitFullScreen: "Exit Full Screen",
    hamburgerMenu: "Tools",
    showSearchBar: "Show Search Bar",
    viewMenu: "Layout Options",
    searchOptions: "Search Options",
    matchCaseMenuItem: "Match Case",
    wholeWordMenuItem: "Match Whole Word",
    thumbnails: "Page Thumbnails",
    outlines: "Document Map",
    loading: "Loading...",
    pdfExportName: "Adobe PDF",
    docxExportName: "Open XML Word",
    xlsxExportName: "Open XML Excel",
    docExportName: "Microsoft Word",
    xlsExportName: "Microsoft Excel",
    mhtmlExportName: "Web archive (MHTML)",
    htmlExportName: "HTML document",
    rtfExportName: "RTF document",
    metafileExportName: "Compressed metafiles",
    csvExportName: "CSV",
    tiffExportName: "Tiff images",
    bmpExportName: "BMP images",
    emfExportName: "Enhanced metafile",
    gifExportName: "GIF images",
    jpgExportName: "JPEG images",
    jpegExportName: "JPEG images",
    pngExportName: "PNG images",
    abstractMethodException: "It is an abstract method, please implement it.",
    cannotRenderPageNoViewPage: "Cannot render page without document source and view page.",
    cannotRenderPageNoDoc: "Cannot render page without document source and view page.",
    exportFormat: "Export format:",
    exportOptionTitle: "Export options",
    documentRestrictionsGroup: "Document restrictions",
    passwordSecurityGroup: "Password security",
    outputRangeGroup: "Output range",
    documentInfoGroup: "Document info",
    generalGroup: "General",
    docInfoTitle: "Title",
    docInfoAuthor: "Author",
    docInfoManager: "Manager",
    docInfoOperator: "Operator",
    docInfoCompany: "Company",
    docInfoSubject: "Subject",
    docInfoComment: "Comment",
    docInfoCreator: "Creator",
    docInfoProducer: "Producer",
    docInfoCreationTime: "Creation time",
    docInfoRevisionTime: "Revision time",
    docInfoKeywords: "Keywords",
    embedFonts: "Embed TrueType fonts",
    pdfACompatible: "PDF/A compatible (level 2B)",
    useCompression: "Use compression",
    useOutlines: "Generate outlines",
    allowCopyContent: "Allow content copying or extraction",
    allowEditAnnotations: "Allow annotations editing",
    allowEditContent: "Allow content editing",
    allowPrint: "Allow printing",
    ownerPassword: "Permissions (owner) password:",
    userPassword: "Document open (user) password:",
    encryptionType: "Encryption level:",
    paged: "Paged",
    showNavigator: "Show Navigator",
    navigatorPosition: "Navigator Position",
    singleFile: "Single File",
    tolerance: "Tolerance when detecting text bounds (points):",
    pictureLayer: "Use separate picture layer",
    metafileType: "Metafile Type:",
    monochrome: "Monochrome",
    resolution: "Resolution:",
    outputRange: "Page range:",
    outputRangeInverted: "Inverted",
    showZoomBar: "Zoom Bar",
    searchPrev: "Search Previous",
    searchNext: "Search Next",
    checkMark: "✓",
    exportOk: "Export...",
    cannotSearch: "Search requires a document source to be specified.",
    parameters: "Parameters",
    requiringParameters: "Please input parameters.",
    nullParameterError: "Value cannot be null.",
    invalidParameterError: "Invalid input.",
    parameterNoneItemsSelected: "(none)",
    parameterAllItemsSelected: "(all)",
    parameterSelectAllItemText: "(Select All)",
    selectParameterValue: "(select value)",
    apply: "Apply",
    errorOccured: "An error has occured."
  })
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = function() {
      function n() {
        this._currentIndex = -1, this.currentChanged = new e.Event, this.searchStarted = new e.Event, this.searchCompleted = new e.Event
      }
      return Object.defineProperty(n.prototype, "current", {
        get: function() {
          return this._currentIndex < 0 ? null : this._searchResult[this._currentIndex]
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "currentIndex", {
        get: function() {
          return this._currentIndex
        },
        set: function(e) {
          var t = this;
          e !== this._currentIndex && this._getSearchResults().then(function(n) {
            t._currentIndex = e, t._onCurrentChanged()
          })
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "documentSource", {
        get: function() {
          return this._documentSource
        },
        set: function(e) {
          this._documentSource = e, this.clear()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "matchCase", {
        get: function() {
          return this._matchCase
        },
        set: function(e) {
          this._matchCase !== e && (this._matchCase = e, this._needUpdate = !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "searchResult", {
        get: function() {
          return this._searchResult
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "text", {
        get: function() {
          return this._text
        },
        set: function(e) {
          this._text !== e && (this._text = e, this._needUpdate = !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "wholeWord", {
        get: function() {
          return this._wholeWord
        },
        set: function(e) {
          this._wholeWord !== e && (this._wholeWord = e, this._needUpdate = !0)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clear = function() {
        this._clearResults(), this._text = null, this._matchCase = !1, this._wholeWord = !1, this._currentIndex = -1
      }, n.prototype.search = function(e) {
        var t = this;
        this._needUpdate && this._clearResults(), this._getSearchResults().then(function(n) {
          var o = t._searchResult.length;
          e ? (t._currentIndex--, t._currentIndex < 0 && (t._currentIndex = o - 1)) : (t._currentIndex++, t._currentIndex >= o && (t._currentIndex = 0)), t._currentIndex = Math.max(Math.min(t._currentIndex, o - 1), 0), t._onCurrentChanged()
        })
      }, n.prototype._clearResults = function() {
        this._currentIndex = -1, this._searchResult = null
      }, n.prototype._getSearchResults = function() {
        var n = this,
          o = new t._Promise;
        return this._searchResult ? (o.resolve(this._searchResult), o) : this.documentSource ? null == this._text || 0 === this._text.length ? o : (this._needUpdate = !1, this._onSearchStarted(), this.documentSource.search({
          text: this.documentSource.encodeRequestParams ? encodeURIComponent(this.text) : this.text,
          matchCase: this.matchCase,
          wholeWord: this.wholeWord
        }).then(function(e) {
          n._searchResult = e, n._onSearchCompleted()
        })) : (o.reject(e.culture.Viewer.cannotSearch), o)
      }, n.prototype._onCurrentChanged = function() {
        this.currentChanged.raise(this, new e.EventArgs)
      }, n.prototype._onSearchStarted = function() {
        this.searchStarted.raise(this, new e.EventArgs)
      }, n.prototype._onSearchCompleted = function() {
        this.searchCompleted.raise(this, new e.EventArgs)
      }, n
    }();
    t._SearchManager = n
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    window.TouchEvent = window.TouchEvent || function() {}, window.PointerEvent = window.PointerEvent || function() {}, window.Touch = window.Touch || function() {};
    var n = {
        startName: "touchstart",
        moveName: "touchmove",
        endName: ["touchend", "touchcancel", "touchleave"].join(",")
      },
      o = {
        startName: ["pointerdown", "pointerenter"].join(","),
        moveName: "pointermove",
        endName: ["pointerup", "pointercancel", "pointerleave"].join(",")
      };
    t._getTouchEventMap = function() {
      return "ontouchstart" in window ? n : o
    };
    var r;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Left = 1] = "Left", e[e.Up = 2] = "Up", e[e.Right = 3] = "Right", e[e.Down = 4] = "Down"
    }(r = t._TouchDirection || (t._TouchDirection = {}));
    var i = function() {
      function t(e) {
        this._systemTouchInfo = e, e instanceof Touch ? this._id = e.identifier : this._id = e.pointerId, this._target = e.target, this._screenX = e.screenX, this._screenY = e.screenY, this._clientX = e.clientX, this._clientY = e.clientY
      }
      return t.getCenter = function(t, n) {
        return new e.Point(t.x + (n.x - t.x) / 2, t.y + (n.y - t.y) / 2)
      }, t.getCenterClient = function(n, o) {
        return t.getCenter(new e.Point(n.clientX, n.clientY), new e.Point(o.clientX, o.clientY))
      }, t.getCenterScreen = function(n, o) {
        return t.getCenter(new e.Point(n.screenX, n.screenY), new e.Point(o.screenX, o.screenY))
      }, t.getDistance = function(e, t) {
        var n = Math.abs(e.clientX - t.clientX),
          o = Math.abs(e.clientY - t.clientY);
        return Math.sqrt(n * n + o * o)
      }, t._getDirection = function(e, t) {
        var n = t.clientX - e.clientX,
          o = t.clientY - e.clientY;
        return Math.abs(n) >= Math.abs(o) ? n > 0 ? r.Right : n < 0 ? r.Left : r.None : o > 0 ? r.Down : o < 0 ? r.Up : r.None
      }, Object.defineProperty(t.prototype, "id", {
        get: function() {
          return this._id
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "systemTouchInfo", {
        get: function() {
          return this._systemTouchInfo
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "screenX", {
        get: function() {
          return this._screenX
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "screenY", {
        get: function() {
          return this._screenY
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "clientX", {
        get: function() {
          return this._clientX
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "clientY", {
        get: function() {
          return this._clientY
        },
        enumerable: !0,
        configurable: !0
      }), t
    }();
    t._TouchInfo = i
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(e) {
    var t = function() {
      function e() {
        this._timeInterval = 50, this._speedInterval = 5
      }
      return Object.defineProperty(e.prototype, "timeInterval", {
        get: function() {
          return this._timeInterval
        },
        set: function(e) {
          this._timeInterval = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "speedInterval", {
        get: function() {
          return this._speedInterval
        },
        set: function(e) {
          this._speedInterval = e
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype.stop = function() {
        null != this._timer && (clearInterval(this._timer), this._timer = null)
      }, e.prototype.start = function(e, t, n) {
        var o = this;
        if (this.stop(), n) {
          var r = e >= 0 ? 1 : -1,
            i = t >= 0 ? 1 : -1,
            a = Math.abs(e * this._timeInterval),
            s = Math.abs(t * this._timeInterval),
            u = Math.max(a, s),
            c = Math.floor(u / this.speedInterval),
            l = Math.floor(a / c),
            p = Math.floor(s / c);
          this._timer = setInterval(function() {
            a -= l, s -= p, a = Math.max(0, a), s = Math.max(0, s), a && s ? n(a * r, s * i) : o.stop()
          }, this._timeInterval)
        }
      }, e
    }();
    e._SpeedReducer = t
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    var n;
    ! function(e) {
      e[e.Start = 0] = "Start", e[e.Move = 1] = "Move", e[e.End = 2] = "End"
    }(n = t._TouchEventType || (t._TouchEventType = {}));
    var o = function(e) {
      function o(r) {
        var i = e.call(this) || this;
        return r instanceof o ? (i._systemEvent = r.systemEvent, i._type = r.type, i._touchInfos = r.touchInfos, i) : (i._systemEvent = r, t._TouchManager._registerTouchInfo(r), i._type = t._TouchManager._isTouchStart(r.type) ? n.Start : t._TouchManager._isTouchEnd(r.type) ? n.End : n.Move, i._touchInfos = t._TouchManager._allTouchInfos ? t._TouchManager._allTouchInfos.slice() : [], i)
      }
      return __extends(o, e), Object.defineProperty(o.prototype, "timeStamp", {
        get: function() {
          return this.systemEvent.timeStamp
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "touchInfos", {
        get: function() {
          return this._touchInfos
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "systemEvent", {
        get: function() {
          return this._systemEvent
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "target", {
        get: function() {
          return this.systemEvent.target
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "currentTarget", {
        get: function() {
          return this.systemEvent.currentTarget
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "type", {
        get: function() {
          return this._type
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "pointersCount", {
        get: function() {
          return this.touchInfos.length
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "cancelBubble", {
        get: function() {
          return this._systemEvent.cancelBubble
        },
        set: function(e) {
          this._systemEvent.cancelBubble = e
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.preventDefault = function() {
        this._systemEvent.preventDefault()
      }, o
    }(e.EventArgs);
    t._TouchEventArgs = o;
    var r = function(e) {
      function t() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(t, e), t.prototype.raise = function(t, n) {
        e.prototype.raise.call(this, t, n)
      }, t
    }(e.Event);
    t._TouchEvent = r;
    var i = function() {
      function i(n) {
        var o = this;
        this.touchMove = new r, this.touchStart = new r, this.touchEnd = new r;
        var a = e.getElement(n);
        this._element = a;
        var s = i.getTrigger(a);
        if (s) {
          var u = this._onTouchEvent.bind(this);
          return s.touchMove.addHandler(u), s.touchStart.addHandler(u), s.touchEnd.addHandler(u), void(this._disposeAction = function() {
            s.touchMove.removeHandler(u), s.touchStart.removeHandler(u), s.touchEnd.removeHandler(u), o._disposeAction = null
          })
        }
        var c = t._getTouchEventMap(),
          l = this._onSystemTouchEvent.bind(this);
        t._addEvent(a, c.startName, l), t._addEvent(a, c.moveName, l), t._addEvent(a, c.endName, l), i.bindElement(a, this), this._disposeAction = function() {
          t._removeEvent(a, c.startName, l), t._removeEvent(a, c.moveName, l), t._removeEvent(a, c.endName, l), i.unbindElement(a), o._disposeAction = null
        }
      }
      return i.bindElement = function(e, t) {
        if (e[i._elementDataName]) throw "Cannot bind multi _TouchTrigger on the same element.";
        e[i._elementDataName] = t
      }, i.unbindElement = function(e) {
        e[i._elementDataName] = null
      }, i.getTrigger = function(e) {
        return e[i._elementDataName]
      }, i.prototype._onSystemTouchEvent = function(e) {
        var t = this._createTouchEventArgs(e);
        t && this._onTouchEvent(this, t)
      }, i.prototype._createTouchEventArgs = function(e) {
        return t._TouchManager._isTouchEvent(e) ? new o(e) : null
      }, i.prototype.dispose = function() {
        this._disposeAction && this._disposeAction()
      }, Object.defineProperty(i.prototype, "hostElement", {
        get: function() {
          return this._element
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype._onTouchEvent = function(e, t) {
        switch (t.type) {
          case n.Start:
            return void this.onTouchStart(t);
          case n.Move:
            return void this.onTouchMove(t);
          case n.End:
            this.onTouchEnd(t)
        }
      }, i.prototype.onTouchEnd = function(e) {
        this.touchEnd.raise(this, e)
      }, i.prototype.onTouchStart = function(e) {
        this.touchStart.raise(this, e)
      }, i.prototype.onTouchMove = function(e) {
        this.touchMove.raise(this, e)
      }, i._elementDataName = "__wjTouchTrigger", i
    }();
    t._TouchTrigger = i
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    var n = function(n) {
      function o(t, o, r) {
        var i = n.call(this, t) || this;
        return i._panType = null == r ? t.type : r, i._client = new e.Point(i.touchInfo.clientX, i.touchInfo.clientY), i._screen = new e.Point(i.touchInfo.screenX, i.touchInfo.screenY), o && (i._clientDelta = new e.Point(i.client.x - o.client.x, i.client.y - o.client.y), i._screenDelta = new e.Point(i.screen.x - o.screen.x, i.screen.y - o.screen.y)), i
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "type", {
        get: function() {
          return this._panType
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "clientDelta", {
        get: function() {
          return this._clientDelta
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "screenDelta", {
        get: function() {
          return this._screenDelta
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "client", {
        get: function() {
          return this._client
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "screen", {
        get: function() {
          return this._screen
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "pointersCount", {
        get: function() {
          return this.type == t._TouchEventType.End ? 0 : 1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "touchInfo", {
        get: function() {
          return this.touchInfos[0] || {}
        },
        enumerable: !0,
        configurable: !0
      }), o
    }(t._TouchEventArgs);
    t._PanEventArgs = n;
    var o = function(e) {
      function t() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(t, e), t.prototype.raise = function(t, n) {
        e.prototype.raise.call(this, t, n)
      }, t
    }(t._TouchEvent);
    t._PanEvent = o;
    var r = function(e) {
      function r() {
        var t = null !== e && e.apply(this, arguments) || this;
        return t.panMove = new o, t.panStart = new o, t.panEnd = new o, t
      }
      return __extends(r, e), r.prototype.onPanEnd = function(e) {
        this.panEnd.raise(this, e)
      }, r.prototype.onPanStart = function(e) {
        this.panStart.raise(this, e)
      }, r.prototype.onPanMove = function(e) {
        this.panMove.raise(this, e)
      }, r.prototype._prepareMove = function(e) {
        var t = this;
        this._prePanEventArgs = e, this._panEvents.queue(function() {
          t.onPanMove(e)
        })
      }, r.prototype._prepareStart = function(e) {
        var t = this;
        this._prePanEventArgs = e, this._panEvents.queue(function() {
          t.onPanStart(e)
        }), this._clearPanStartTimer(), this._panStartTimer = setTimeout(function() {
          t._panEvents && t._panEvents.start(), t._clearPanStartTimer()
        }, r._threhold)
      }, r.prototype._prepareEnd = function(e) {
        var o = this;
        this._prePanEventArgs = null, this._panEvents.queue(function() {
          var r = e instanceof n ? e : new n(e, null, t._TouchEventType.End);
          o.onPanEnd(r), o._stopPan()
        })
      }, r.prototype._clearPanStartTimer = function() {
        null != this._panStartTimer && (clearTimeout(this._panStartTimer), this._panStartTimer = null)
      }, r.prototype._tryStopPan = function(e) {
        this._panEvents && this._panEvents.isStarted ? this._prepareEnd(e) : this._stopPan()
      }, r.prototype._stopPan = function() {
        this._clearPanStartTimer(), this._panEvents = null, this._prePanEventArgs = null
      }, r.prototype._processPan = function(e) {
        var n = this._createPanEventArgs(e);
        if (n) switch (n.type) {
          case t._TouchEventType.Start:
            return void this._prepareStart(n);
          case t._TouchEventType.Move:
            return void this._prepareMove(n);
          case t._TouchEventType.End:
            return void this._prepareEnd(n)
        } else this._tryStopPan(e)
      }, r.prototype.onTouchStart = function(t) {
        e.prototype.onTouchStart.call(this, t), this._processPan(t)
      }, r.prototype.onTouchMove = function(t) {
        e.prototype.onTouchMove.call(this, t), this._processPan(t)
      }, r.prototype.onTouchEnd = function(t) {
        e.prototype.onTouchEnd.call(this, t);
        var n = this._createPanEventArgs(t);
        n ? this._prepareEnd(n) : this._tryStopPan(t)
      }, r.prototype._createPanEventArgs = function(e) {
        if (e.type == t._TouchEventType.End && 0 != e.pointersCount || e.type != t._TouchEventType.End && 1 != e.pointersCount) return null;
        var o = new n(e, this._prePanEventArgs);
        if (o.type != t._TouchEventType.Start) {
          if (!this._panEvents) return null
        } else this._panEvents = new t._ActionQueue;
        return o
      }, r._threhold = 10, r
    }(t._TouchTrigger);
    t._PanTrigger = r
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    var n = function(n) {
      function o(o, i, a, s) {
        var u = n.call(this, a) || this;
        u._duration = s, u._startTouchInfo = o, u._endTouchInfo = i;
        var c = t._pointMove(!1, new e.Point(u.endTouchInfo.clientX, u.endTouchInfo.clientY), new e.Point(u.startTouchInfo.clientX, u.startTouchInfo.clientY));
        return u._speed = new e.Point(r.getSpeed(c.x, u.duration), r.getSpeed(c.y, u.duration)), u._direction = t._TouchInfo._getDirection(u.startTouchInfo, u.endTouchInfo), u
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "duration", {
        get: function() {
          return this._duration
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "startTouchInfo", {
        get: function() {
          return this._startTouchInfo
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "endTouchInfo", {
        get: function() {
          return this._endTouchInfo
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "speed", {
        get: function() {
          return this._speed
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "pointersCount", {
        get: function() {
          return 1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "direction", {
        get: function() {
          return this._direction
        },
        enumerable: !0,
        configurable: !0
      }), o
    }(t._PanEventArgs);
    t._SwipeEventArgs = n;
    var o = function(e) {
      function t() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(t, e), t.prototype.raise = function(t, n) {
        e.prototype.raise.call(this, t, n)
      }, t
    }(t._PanEvent);
    t._SwipeEvent = o;
    var r = function(e) {
      function r() {
        var t = null !== e && e.apply(this, arguments) || this;
        return t.swipe = new o, t
      }
      return __extends(r, e), r.getSpeed = function(e, t) {
        return e / t
      }, r.prototype.onPanStart = function(t) {
        e.prototype.onPanStart.call(this, t), this._panStartEventArgs = t
      }, r.prototype.onPanMove = function(t) {
        e.prototype.onPanMove.call(this, t), this._prePanMoveEventArgs = t
      }, r.prototype.onPanEnd = function(t) {
        e.prototype.onPanEnd.call(this, t);
        var n = this._createSwipeEventArgs(t);
        n && this.onSwipe(n), this._panStartEventArgs = null, this._prePanMoveEventArgs = null
      }, r.prototype.onSwipe = function(e) {
        this.swipe.raise(this, e)
      }, r.prototype._createSwipeEventArgs = function(e) {
        if (!this._panStartEventArgs || !this._prePanMoveEventArgs) return null;
        var o = e.timeStamp - this._panStartEventArgs.timeStamp;
        return o > r.maxDuration ? null : t._TouchInfo.getDistance(this._panStartEventArgs.touchInfo, this._prePanMoveEventArgs.touchInfo) < r.minDistance ? null : new n(this._panStartEventArgs.touchInfo, this._prePanMoveEventArgs.touchInfo, e, o)
      }, r.minDistance = 50, r.maxDuration = 300, r
    }(t._PanTrigger);
    t._SwipeTrigger = r
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    var n = function(n) {
      function o(o, r, i) {
        var a = n.call(this, o) || this;
        return a._zoom = 1, a._pinchType = r, a._pre = i || {}, a.type == t._TouchEventType.End ? a : (a._pinchDistance = t._TouchInfo.getDistance(a.touchInfos[0], a.touchInfos[1]), a._centerClient = t._TouchInfo.getCenterClient(a.touchInfos[0], a.touchInfos[1]), a._centerScreen = t._TouchInfo.getCenterScreen(a.touchInfos[0], a.touchInfos[1]), a.type == t._TouchEventType.Start ? a : (a._zoom = a.pinchDistance / a.prePinchDistance, a._centerClientDelta = new e.Point(a.centerClient.x - a.preCenterClient.x, a.centerClient.y - a.preCenterClient.y), a._centerScreenDelta = new e.Point(a.centerScreen.x - a.preCenterScreen.x, a.centerScreen.y - a.preCenterScreen.y), a))
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "zoom", {
        get: function() {
          return this._zoom
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "pointersCount", {
        get: function() {
          return this.type == t._TouchEventType.End ? 0 : 2
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "prePinchDistance", {
        get: function() {
          return this._pre.pinchDistance
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "pinchDistance", {
        get: function() {
          return this._pinchDistance
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "centerScreenDelta", {
        get: function() {
          return this._centerScreenDelta
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "centerClientDelta", {
        get: function() {
          return this._centerClientDelta
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "centerClient", {
        get: function() {
          return this._centerClient
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "preCenterClient", {
        get: function() {
          return this._pre.centerClient
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "centerScreen", {
        get: function() {
          return this._centerScreen
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "preCenterScreen", {
        get: function() {
          return this._pre.centerScreen
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "type", {
        get: function() {
          return this._pinchType
        },
        enumerable: !0,
        configurable: !0
      }), o
    }(t._TouchEventArgs);
    t._PinchEventArgs = n;
    var o = function(e) {
      function t() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(t, e), t.prototype.raise = function(t, n) {
        e.prototype.raise.call(this, t, n)
      }, t
    }(t._TouchEvent);
    t._PinchEvent = o;
    var r = function(e) {
      function r() {
        var t = null !== e && e.apply(this, arguments) || this;
        return t.pinch = new o, t
      }
      return __extends(r, e), r.prototype.onPinch = function(e) {
        this.pinch.raise(this, e)
      }, r.prototype.onTouchStart = function(e) {
        this._process(e)
      }, r.prototype.onTouchend = function(e) {
        this._process(e)
      }, r.prototype.onTouchMove = function(e) {
        this._process(e)
      }, r.prototype._onPinching = function(e) {
        var o = new n(e, this._preEventArgs ? t._TouchEventType.Move : t._TouchEventType.Start, this._preEventArgs);
        this.onPinch(o), this._preEventArgs = o
      }, r.prototype._onPinchEnd = function(e) {
        if (this._preEventArgs) {
          var o = new n(e, t._TouchEventType.End, this._preEventArgs);
          this.onPinch(o), this._preEventArgs = null
        }
      }, r.prototype._process = function(e) {
        if (2 == e.pointersCount) switch (e.type) {
          case t._TouchEventType.Start:
          case t._TouchEventType.Move:
            return void this._onPinching(e);
          case t._TouchEventType.End:
            return void this._onPinchEnd(e)
        } else this._onPinchEnd(e)
      }, r
    }(t._TouchTrigger);
    t._PinchTrigger = r
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(e) {
    var t = function() {
      function t(t, n) {
        void 0 === n && (n = !0);
        var o = this;
        this.touchMove = new e._TouchEvent, this.touchStart = new e._TouchEvent, this.touchEnd = new e._TouchEvent, this.panMove = new e._PanEvent, this.panStart = new e._PanEvent, this.panEnd = new e._PanEvent, this.swipe = new e._SwipeEvent, this.pinch = new e._PinchEvent, this._trigger = new e._SwipeTrigger(t), this._trigger.touchStart.addHandler(function(e, t) {
          return o.onTouchStart(t)
        }), this._trigger.touchMove.addHandler(function(e, t) {
          return o.onTouchMove(t)
        }), this._trigger.touchEnd.addHandler(function(e, t) {
          return o.onTouchEnd(t)
        }), this._trigger.panStart.addHandler(function(e, t) {
          return o.onPanStart(t)
        }), this._trigger.panMove.addHandler(function(e, t) {
          return o.onPanMove(t)
        }), this._trigger.panEnd.addHandler(function(e, t) {
          return o.onPanEnd(t)
        }), this._trigger.swipe.addHandler(function(e, t) {
          return o.onSwipe(t)
        }), this._pinchTrigger = new e._PinchTrigger(t), this._pinchTrigger.pinch.addHandler(function(e, t) {
          return o.onPinch(t)
        }), this.removeDefaultTouch = n
      }
      return t._isTouchEvent = function(e) {
        return e instanceof TouchEvent || (e.pointerType || "").toLowerCase() === t._touchPointerName
      }, t._isTouchStart = function(n) {
        return t._eventTypeContains(n, e._getTouchEventMap().startName)
      }, t._isTouchEnd = function(n) {
        return t._eventTypeContains(n, e._getTouchEventMap().endName)
      }, t._isTouchMove = function(n) {
        return t._eventTypeContains(n, e._getTouchEventMap().moveName)
      }, t._eventTypeContains = function(e, t) {
        var n = t.split(",");
        e = e.toLowerCase();
        for (var o = 0, r = n.length; o < r; o++) {
          var i = n[o].trim().toLowerCase();
          if (e.indexOf(i) > -1) return !0
        }
        return !1
      }, t._registerTouchInfo = function(n) {
        if (t._isTouchEvent(n))
          if (n instanceof TouchEvent) {
            t._allTouchInfos = [];
            for (var o = 0, r = n.touches.length; o < r; o++) t._allTouchInfos.push(new e._TouchInfo(n.touches.item(o)))
          } else if (n instanceof PointerEvent) {
            if (t._allTouchInfos = t._allTouchInfos || [], t._isTouchEnd(n.type)) {
              for (var i = 0, r = t._allTouchInfos.length; i < r; i++)
                if (t._allTouchInfos[i].id == n.pointerId) return void t._allTouchInfos.splice(i, 1);
              return
            }
            for (var i = 0, r = t._allTouchInfos.length; i < r; i++)
              if (t._allTouchInfos[i].id == n.pointerId) return void(t._allTouchInfos[i] = new e._TouchInfo(n));
            t._allTouchInfos.push(new e._TouchInfo(n))
          }
      }, t.prototype.onPinch = function(e) {
        this.pinch.raise(this, e)
      }, t.prototype.onSwipe = function(e) {
        this.swipe.raise(this, e)
      }, t.prototype.onTouchEnd = function(e) {
        this.touchEnd.raise(this, e)
      }, t.prototype.onTouchStart = function(e) {
        this.touchStart.raise(this, e)
      }, t.prototype.onTouchMove = function(e) {
        this.touchMove.raise(this, e)
      }, t.prototype.onPanEnd = function(e) {
        this.panEnd.raise(this, e)
      }, t.prototype.onPanStart = function(e) {
        this.panStart.raise(this, e)
      }, t.prototype.onPanMove = function(e) {
        this.panMove.raise(this, e)
      }, Object.defineProperty(t.prototype, "removeDefaultTouch", {
        get: function() {
          return this._removeDefaultTouch
        },
        set: function(e) {
          this._removeDefaultTouch = e;
          var t = this.hostElement.style;
          e ? (t.touchAction = "none", t.msTouchAction = "none") : (t.touchAction = this._defaultTouchAction, t.msTouchAction = this._defaultMsTouchAction)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "hostElement", {
        get: function() {
          return this._pinchTrigger.hostElement
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "contentElement", {
        get: function() {
          return this.hostElement.children.length ? this.hostElement.children[0] : null
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.dispose = function() {
        this._pinchTrigger && this._pinchTrigger.dispose(), this._trigger && this._trigger.dispose(), this.removeDefaultTouch && (this.removeDefaultTouch = !1)
      }, t._touchPointerName = "touch", t._allTouchInfos = [], t
    }();
    e._TouchManager = t
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = function(n) {
      function o(o) {
        var r = n.call(this, o) || this;
        r._uiUpdating = !1;
        e.assert(null != e.input.ComboBox, "Missing dependency: _PageSetupEditor requires wijmo.input.");
        var i;
        i = r.getTemplate(), r.applyTemplate("wj-control", i, {
          _divPaperKind: "div-paper-kind",
          _divOrientation: "div-page-orientation",
          _divMarginsLeft: "div-margins-left",
          _divMarginsRight: "div-margins-right",
          _divMarginsTop: "div-margins-top",
          _divMarginsBottom: "div-margins-bottom",
          _gPaperKind: "g-paperkind",
          _gOrientation: "g-orientation",
          _gMargins: "g-margins",
          _gLeft: "g-left",
          _gRight: "g-right",
          _gTop: "g-top",
          _gBottom: "g-bottom"
        });
        var a = {
          format: "n2",
          min: 0,
          max: 4,
          step: .25,
          valueChanged: r._updateValue.bind(r)
        };
        return r._numMarginsLeft = new e.input.InputNumber(r._divMarginsLeft, a), r._numMarginsRight = new e.input.InputNumber(r._divMarginsRight, a), r._numMarginsTop = new e.input.InputNumber(r._divMarginsTop, a), r._numMarginsBottom = new e.input.InputNumber(r._divMarginsBottom, a), r._cmbPaperKind = new e.input.ComboBox(r._divPaperKind, {
          itemsSource: t._enumToArray(t._PaperKind),
          displayMemberPath: "text",
          selectedValuePath: "value",
          isEditable: !1
        }), r._cmbPaperKind.selectedIndexChanged.addHandler(r._updateValue, r), r._cmbOrientation = new e.input.ComboBox(r._divOrientation, {
          itemsSource: [e.culture.Viewer.portrait, e.culture.Viewer.landscape],
          isEditable: !1
        }), r._cmbOrientation.selectedIndexChanged.addHandler(r._updateValue, r), r._globalize(), r
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "pageSettings", {
        get: function() {
          return this._pageSettings
        },
        set: function(e) {
          var n = t._clonePageSettings(e);
          this._pageSettings = n, this._updateUI(), this._cmbPaperKind.focus()
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gPaperKind.textContent = t.paperKind, this._gOrientation.textContent = t.orientation, this._gMargins.textContent = t.margins, this._gLeft.textContent = t.left, this._gRight.textContent = t.right, this._gTop.textContent = t.top, this._gBottom.textContent = t.bottom;
        var n = this._cmbOrientation.selectedIndex;
        this._cmbOrientation.itemsSource = [e.culture.Viewer.portrait, e.culture.Viewer.landscape], this._cmbOrientation.selectedIndex = n
      }, o.prototype._updateValue = function() {
        if (!this._uiUpdating) {
          var n = this.pageSettings;
          n && (n.bottomMargin = new t._Unit(this._numMarginsBottom.value, t._UnitType.Inch), n.leftMargin = new t._Unit(this._numMarginsLeft.value, t._UnitType.Inch), n.rightMargin = new t._Unit(this._numMarginsRight.value, t._UnitType.Inch), n.topMargin = new t._Unit(this._numMarginsTop.value, t._UnitType.Inch), n.paperSize = this._cmbPaperKind.selectedValue, t._setLandscape(n, this._cmbOrientation.text === e.culture.Viewer.landscape), this._updateUI())
        }
      }, o.prototype._updateUI = function() {
        this._uiUpdating = !0;
        var n = this.pageSettings,
          o = function(e, n) {
            e.value = t._Unit.convertValue(n.value, n.units, t._UnitType.Inch)
          };
        n && (this._cmbPaperKind.selectedValue = n.paperSize, this._cmbOrientation.text = n.landscape ? e.culture.Viewer.landscape : e.culture.Viewer.portrait, o(this._numMarginsLeft, n.leftMargin), o(this._numMarginsRight, n.rightMargin), o(this._numMarginsTop, n.topMargin), o(this._numMarginsBottom, n.bottomMargin)), this._uiUpdating = !1
      }, o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e), e && this._globalize()
      }, o.controlTemplate = '<div><div style="padding:12px;"><table style="table-layout:fixed"><tr><td wj-part="g-paperkind"></td><td><div wj-part="div-paper-kind"></div></td></tr><tr><td wj-part="g-orientation"></td><td><div wj-part="div-page-orientation"></div></td></tr></table><fieldset style="margin-top: 12px"><legend wj-part="g-margins"></legend><table style="table-layout:fixed"><tr><td wj-part="g-left"></td><td><div wj-part="div-margins-left"></div></td></tr><tr><td wj-part="g-right"></td><td><div wj-part="div-margins-right"></div></td></tr><tr><td wj-part="g-top"></td><td><div wj-part="div-margins-top"></div></td></tr><tr><td wj-part="g-bottom"></td><td><div wj-part="div-margins-bottom"></div></td></tr></table></fieldset></div></div>', o
    }(e.Control);
    t._PageSetupEditor = n;
    var o = function(n) {
      function o(t) {
        var o = n.call(this, t) || this;
        return o._options = {}, o._optionLabels = null, o._groupTitleField = null, e.addClass(t, "wj-export-editor"), o
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "options", {
        get: function() {
          return this._options
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "exportDescription", {
        get: function() {
          return this._exportDescription
        },
        set: function(e) {
          this._exportDescription = e, this._options = {}, e && (this._options.format = this.exportDescription.format, this._render())
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._skipOption = function(e) {
        return o._skippedOptions.indexOf(e) >= 0
      }, o.prototype._render = function() {
        if (t._removeChildren(this.hostElement), this.exportDescription) {
          document.createElement("table");
          var e = this.exportDescription.optionDescriptions,
            n = {};
          if (e)
            for (var r = 0; r < e.length; r++) {
              var i = e[r];
              this._skipOption(i.name) || (i.group ? (n[i.group] || (n[i.group] = []), n[i.group].push(i)) : (n[o._generalGroupName] || (n[o._generalGroupName] = []), i.group = o._generalGroupName, n[o._generalGroupName].push(i)))
            }
          for (var a in n) this.hostElement.appendChild(this._generateGroup(n[a]));
          this._updateEditors()
        }
      }, o.prototype._generateEditor = function(n) {
        var r;
        if (e.isArray(n.allowedValues)) r = this._generateComboEditor(n);
        else switch (n.type) {
          case "bool":
            r = this._generateBoolEditor(n);
            break;
          case "int":
          case "float":
            r = this._generateNumberEditor(n);
            break;
          case "unit":
            n.defaultValue = new t._Unit(n.defaultValue);
          case "string":
          default:
            r = this._generateStringEditor(n)
        }
        return r.setAttribute(o._optionIdAttr, n.name), r
      }, o.prototype._generateComboEditor = function(t) {
        for (var n, o = this, r = [], i = document.createElement("div"), a = 0; a < t.allowedValues.length; a++) r.push(t.allowedValues[a]);
        return n = new e.input.ComboBox(i), n.isEditable = !1, n.itemsSource = r, n.selectedValue = t.defaultValue, n.selectedIndexChanged.addHandler(function(e) {
          o._setOptionValue(t.name, e.selectedValue.toString())
        }), i
      }, o.prototype._generateBoolEditor = function(n) {
        var o, r = this;
        if (n.nullable) {
          o = document.createElement("div");
          var i = new e.input.ComboBox(o),
            a = [];
          i.isEditable = !1, i.displayMemberPath = "name", i.selectedValuePath = "value", a.push({
            name: "None",
            value: null
          }), a.push({
            name: "True",
            value: !0
          }), a.push({
            name: "False",
            value: !1
          }), i.itemsSource = a, i.selectedValue = n.defaultValue, i.selectedIndexChanged.addHandler(function(e) {
            r._setOptionValue(n.name, e.selectedValue), r._updateEditors(n.name)
          })
        } else {
          (o = document.createElement("input")).type = "checkbox";
          var s = e.changeType(n.defaultValue, e.DataType.Boolean, null);
          o.checked = s, t._addEvent(o, "click", function() {
            r._setOptionValue(n.name, o.checked), r._updateEditors(n.name)
          })
        }
        return o
      }, o.prototype._generateNumberEditor = function(t) {
        var n, o, r = this,
          i = "int" === t.type;
        return n = document.createElement("div"), o = new e.input.InputNumber(n), o.format = i ? "n0" : "n2", o.isRequired = !t.nullable, o.value = t.defaultValue, o.valueChanged.addHandler(function(e) {
          r._setOptionValue(t.name, e.value)
        }), n
      }, o.prototype._generateStringEditor = function(e) {
        var n, o = this;
        return n = document.createElement("input"), e.name.match(/password/i) ? n.type = "password" : n.type = "text", n.value = e.defaultValue, t._addEvent(n, "change,keyup,paste,input", function() {
          o._setOptionValue(e.name, n.value)
        }), n
      }, o.prototype._generateGroup = function(t) {
        var n = document.createElement("fieldset"),
          r = document.createElement("legend"),
          i = t[0].group;
        e.addClass(n, "wj-exportformats-group"), r.innerHTML = this._groupTitle[i], r.setAttribute(o._optionNameAttr, i), n.appendChild(r);
        for (var a = document.createElement("table"), s = 0; s < t.length; s++) {
          var u = t[s],
            c = document.createElement("tr"),
            l = document.createElement("td"),
            p = document.createElement("td");
          l.innerHTML = this._getOptionLabel(u.name), l.setAttribute(o._optionNameAttr, u.name), p.appendChild(this._generateEditor(u)), c.appendChild(l), c.appendChild(p), a.appendChild(c)
        }
        return n.appendChild(a), n
      }, o.prototype._updateEditors = function(t) {
        if ("pdfACompatible" !== t && t) {
          if ("paged" === t || !t) {
            var n = this.hostElement.querySelector("[" + o._optionIdAttr + '="showNavigator"]');
            if (n) {
              var r = e.changeType(this._getOptionValue("paged"), e.DataType.Boolean, null);
              r ? (n.checked = this._previousShowNavigator, this._setOptionValue("showNavigator", this._previousShowNavigator)) : (this._previousShowNavigator = n.checked, n.checked = !1, this._setOptionValue("showNavigator", !1)), n.disabled = !r
            }
          }
        } else {
          var i = this.hostElement.querySelector("[" + o._optionIdAttr + '="embedFonts"]');
          if (i) {
            var a = e.changeType(this._getOptionValue("pdfACompatible"), e.DataType.Boolean, null);
            a ? (this._previousEmbedFonts = i.checked, i.checked = !0, this._setOptionValue("embedFonts", !0)) : (i.checked = this._previousEmbedFonts, this._setOptionValue("embedFonts", this._previousEmbedFonts)), i.disabled = a
          }
        }
      }, o.prototype._getOptionLabel = function(e) {
        var t = this._optionLabelsText[e];
        return t || e[0].toUpperCase() + e.substring(1)
      }, o.prototype._getOptionDescByName = function(e) {
        var t = null;
        return this._exportDescription.optionDescriptions.some(function(n) {
          return n.name === e && (t = n, !0)
        }), t
      }, o.prototype._getOptionValue = function(e) {
        return void 0 !== this._options[e] ? this._options[e] : this._getOptionDescByName(e).defaultValue
      }, o.prototype._setOptionValue = function(e, t) {
        var n = this._getOptionDescByName(e),
          o = n.defaultValue;
        "unit" === n.type && (o = n.defaultValue.toString()), t !== o ? this._options[e] = t : void 0 !== this._options[e] && delete this._options[e]
      }, Object.defineProperty(o.prototype, "_optionLabelsText", {
        get: function() {
          if (!this._optionLabels) {
            var t = e.culture.Viewer;
            this._optionLabels = {
              title: t.docInfoTitle,
              author: t.docInfoAuthor,
              manager: t.docInfoManager,
              operator: t.docInfoOperator,
              company: t.docInfoCompany,
              subject: t.docInfoSubject,
              comment: t.docInfoComment,
              creator: t.docInfoCreator,
              producer: t.docInfoProducer,
              creationTime: t.docInfoCreationTime,
              revisionTime: t.docInfoRevisionTime,
              keywords: t.docInfoKeywords,
              embedFonts: t.embedFonts,
              pdfACompatible: t.pdfACompatible,
              useCompression: t.useCompression,
              useOutlines: t.useOutlines,
              allowCopyContent: t.allowCopyContent,
              allowEditAnnotations: t.allowEditAnnotations,
              allowEditContent: t.allowEditContent,
              allowPrint: t.allowPrint,
              ownerPassword: t.ownerPassword,
              userPassword: t.userPassword,
              encryptionType: t.encryptionType,
              paged: t.paged,
              showNavigator: t.showNavigator,
              navigatorPosition: t.navigatorPosition,
              singleFile: t.singleFile,
              tolerance: t.tolerance,
              pictureLayer: t.pictureLayer,
              metafileType: t.metafileType,
              monochrome: t.monochrome,
              resolution: t.resolution,
              outputRange: t.outputRange,
              outputRangeInverted: t.outputRangeInverted
            }
          }
          return this._optionLabels
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_groupTitle", {
        get: function() {
          return this._groupTitleField || (this._groupTitleField = {
            documentRestrictions: e.culture.Viewer.documentRestrictionsGroup,
            passwordSecurity: e.culture.Viewer.passwordSecurityGroup,
            outputRange: e.culture.Viewer.outputRangeGroup,
            documentInfo: e.culture.Viewer.documentInfoGroup,
            general: e.culture.Viewer.generalGroup
          }), this._groupTitleField
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._globalize = function() {
        for (var e = this.hostElement.querySelectorAll("[" + o._optionNameAttr + "]"), t = 0; t < e.length; t++) {
          var n = e[t];
          n instanceof HTMLLegendElement ? n.innerHTML = this._groupTitle[n.getAttribute(o._optionNameAttr)] : n.innerHTML = this._getOptionLabel(n.getAttribute(o._optionNameAttr))
        }
      }, o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e), e && (this._optionLabels = null, this._groupTitleField = null, this._globalize())
      }, o._optionIdAttr = "option-id", o._optionNameAttr = "option-name", o._skippedOptions = ["shapesWord2007Compatible", "sheetName", "navigatorPositions"], o._generalGroupName = "general", o
    }(e.Control);
    t._ExportOptionEditor = o
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = function(n) {
      function o(o) {
        var r = n.call(this, o) || this;
        r._disposed = !1, r.svgButtonClicked = new e.Event;
        var i;
        return i = r.getTemplate(), r.applyTemplate("wj-toolbar", i, {
          _toolbarWrapper: "toolbar-wrapper",
          _toolbarContainer: "toolbar-container",
          _toolbarLeft: "toolbar-left",
          _toolbarRight: "toolbar-right"
        }), t._addEvent(r._toolbarLeft, "mouseover", function() {
          r._scrollLeft()
        }), t._addEvent(r._toolbarLeft, "mouseout", function() {
          r._clearToolbarMoveTimer()
        }), t._addEvent(r._toolbarRight, "mouseover", function() {
          r._scrollRight()
        }), t._addEvent(r._toolbarRight, "mouseout", function() {
          r._clearToolbarMoveTimer()
        }), r
      }
      return __extends(o, n), o.prototype.applyTemplate = function(n, o, r) {
        var i = this.hostElement;
        if (e.addClass(i, n), i.appendChild(t._toDOMs(o)), r)
          for (var a in r) {
            var s = r[a];
            if (this[a] = i.querySelector('[wj-part="' + s + '"]'), null == this[a] && i.getAttribute("wj-part") == s && (this[a] = o), null == this[a]) throw 'Missing template part: "' + s + '"'
          }
        return i
      }, o.prototype.dispose = function() {
        this._disposed = !0, n.prototype.dispose.call(this)
      }, o.prototype._clearToolbarMoveTimer = function() {
        null != this._toolbarMoveTimer && (clearTimeout(this._toolbarMoveTimer), this._toolbarMoveTimer = null)
      }, o.prototype._scrollRight = function() {
        var e = this,
          t = this._toolbarContainer.offsetWidth - this._toolbarWrapper.offsetWidth,
          n = this._toolbarWrapper.style,
          r = (n.left ? parseInt(n.left) : 0) - o._moveStep;
        this._checkMoveButtonEnabled(), r < t || (n.left = r + "px", this._toolbarMoveTimer = setTimeout(function() {
          return e._scrollRight()
        }, o._moveInterval))
      }, o.prototype._scrollLeft = function() {
        var e = this,
          t = this._toolbarWrapper.style,
          n = (t.left ? parseInt(t.left) : 0) + o._moveStep;
        this._checkMoveButtonEnabled(), n > 0 || (t.left = n + "px", this._toolbarMoveTimer = setTimeout(function() {
          return e._scrollLeft()
        }, o._moveInterval))
      }, o.prototype._checkMoveButtonEnabled = function() {
        var t = this._toolbarLeft.getBoundingClientRect().width,
          n = this._toolbarWrapper.offsetLeft - t + o._moveStep,
          r = n <= 0,
          i = e.hasClass(this._toolbarLeft, o._enabledCss);
        r ? i || e.addClass(this._toolbarLeft, o._enabledCss) : i && e.removeClass(this._toolbarLeft, o._enabledCss);
        var a = this._toolbarRight.getBoundingClientRect().width,
          s = this._toolbarContainer.getBoundingClientRect().width - this._toolbarWrapper.getBoundingClientRect().width,
          u = (n = this._toolbarWrapper.offsetLeft - a - o._moveStep) >= s,
          c = e.hasClass(this._toolbarRight, o._enabledCss);
        u ? c || e.addClass(this._toolbarRight, o._enabledCss) : c && e.removeClass(this._toolbarRight, o._enabledCss)
      }, o.prototype._showToolbarMoveButton = function(e) {
        var t = e ? "visible" : "hidden";
        this._toolbarLeft.style.visibility = t, this._toolbarRight.style.visibility = t, this._checkMoveButtonEnabled()
      }, o.prototype._globalize = function() {}, o.prototype.resetWidth = function() {
        var e = this._toolbarLeft.getBoundingClientRect().width,
          t = this._toolbarRight.getBoundingClientRect().width,
          n = this.hostElement.getBoundingClientRect().width;
        this._toolbarContainer.style.width = "1500px", this._toolbarWrapper.style.width = "auto";
        var o = this._toolbarWrapper.getBoundingClientRect().width + 2;
        this._toolbarWrapper.style.width = o + "px", this._toolbarContainer.style.width = n - e - t + "px";
        var r = e + t + o > n;
        this._showToolbarMoveButton(r), r || (this._toolbarWrapper.style.left = "0px")
      }, o.prototype.addSeparator = function() {
        var e = document.createElement("span");
        return e.className = "wj-separator", this._toolbarWrapper.appendChild(e), e
      }, o.prototype.onSvgButtonClicked = function(e) {
        this.svgButtonClicked.raise(this, e)
      }, o.prototype.addCustomItem = function(n, o) {
        e.isString(n) && (n = t._toDOM(n)), null != o && n.setAttribute(t._commandTagAttr, o.toString()), this._toolbarWrapper.appendChild(n)
      }, o.prototype.addSvgButton = function(n, o, r, i) {
        var a = this,
          s = t._createSvgBtn(o);
        return s.title = n, s.setAttribute(t._commandTagAttr, r.toString()), this._toolbarWrapper.appendChild(s), t._addEvent(s, "click,keydown", function(n) {
          var o = n || window.event;
          !("keydown" === o.type && o.keyCode === e.Key.Enter || "click" === o.type) || t._isDisabledImageButton(s) || !i && t._isCheckedImageButton(s) || a.onSvgButtonClicked({
            commandTag: r
          })
        }), s
      }, o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e), e && this._globalize()
      }, o._moveStep = 4, o._moveInterval = 5, o._enabledCss = "enabled", o.controlTemplate = '<div class="wj-toolbar-move left" wj-part="toolbar-left"><span class="wj-glyph-left"></span></div><div class="wj-toolbarcontainer" wj-part="toolbar-container"><div class="wj-toolbarwrapper wj-btn-group" wj-part="toolbar-wrapper"></div></div><div class="wj-toolbar-move right" wj-part="toolbar-right"><span class="wj-glyph-right"></span></div>', o
    }(e.Control);
    t._Toolbar = n;
    var o = function(n) {
      function o(e, o) {
        var r = n.call(this, e) || this;
        r._viewer = o, r._initToolbarItems();
        var i = function() {
          return r.isDisabled = !r._viewer._getDocumentSource()
        };
        return r._viewer._documentSourceChanged.addHandler(i), i(), r._viewer._viewerActionStatusChanged.addHandler(function(e, n) {
          var o = n.action,
            i = r.hostElement.querySelector('[command-tag="' + o.actionType.toString() + '"]');
          t._checkImageButton(i, o.checked), t._disableImageButton(i, o.disabled), t._showImageButton(i, o.shown), t._checkSeparatorShown(r._toolbarWrapper)
        }), r
      }
      return __extends(o, n), o.prototype._initToolbarItems = function() {
        throw e.culture.Viewer.abstractMethodException
      }, o.prototype.onSvgButtonClicked = function(e) {
        n.prototype.onSvgButtonClicked.call(this, e), this._viewer._executeAction(parseInt(e.commandTag))
      }, Object.defineProperty(o.prototype, "viewer", {
        get: function() {
          return this._viewer
        },
        enumerable: !0,
        configurable: !0
      }), o._initToolbarZoomValue = function(n, o) {
        var r, i, a, s, u = e.Control.getControl(n),
          c = document.createElement("div"),
          l = t.ViewerBase._defaultZoomValues;
        c.className = "wj-input-zoom", u.addCustomItem(c, t._ViewerActionType.ZoomValue), (r = new e.input.ComboBox(c)).deferUpdate(function() {
          for (a = 0; a < l.length; a++)
            for (s = a + 1; s < l.length; s++) l[a].value > l[s].value && (i = l[a], l[a] = l[s], l[s] = i);
          r.itemsSource = l, r.isEditable = !0, r.displayMemberPath = "name", r.selectedValuePath = "value", r.selectedValue = 1
        }), r.selectedIndexChanged.addHandler(function() {
          if (r.isDroppedDown) {
            var e = r.selectedValue;
            if (null == e) {
              var t = r.text.replace(",", "");
              e = parseFloat(t), isNaN(e) && (e = 100), e *= .01
            }
            o.zoomFactor = e
          }
        }), t._addEvent(c, "keypress", function(t) {
          var n, i = t || window.event,
            a = r.text;
          i.keyCode === e.Key.Enter && (a.lastIndexOf("%") === a.length - 1 && (a = a.substring(0, r.text.length - 1)), a = a.replace(",", ""), n = parseFloat(a), isNaN(n) ? r.text = e.Globalize.format(o.zoomFactor, "p0") : (e.setSelectionRange(r.inputElement, 0, 0), o.zoomFactor = .01 * n))
        }), t._addEvent(c.querySelector(".wj-form-control"), "blur", function(t) {
          r.text = e.Globalize.format(o.zoomFactor, "p0")
        }), o.zoomFactorChanged.addHandler(function() {
          r.isDroppedDown = !1, r.text = e.Globalize.format(o.zoomFactor, "p0")
        })
      }, o._initToolbarPageNumberInput = function(n, o) {
        var r, i = e.Control.getControl(n),
          a = document.createElement("div"),
          s = document.createElement("span"),
          u = function() {
            var e = o._getDocumentSource();
            e && null != e.pageCount && (r.value = o.pageIndex + 1)
          },
          c = function() {
            var e = o._getDocumentSource();
            e && null != e.pageCount && (s.innerHTML = e.pageCount.toString(), r.max = e.pageCount, r.min = Math.min(e.pageCount, 1), u())
          },
          l = function() {
            var e = o._getDocumentSource();
            e && (c(), t._addWjHandler(o._documentEventKey, e.pageCountChanged, c), t._addWjHandler(o._documentEventKey, e.loadCompleted, c))
          };
        a.className = "wj-pagenumber", i.addCustomItem(a, t._ViewerActionType.PageNumber), (r = new e.input.InputNumber(a)).format = "n0", t._addEvent(a, "keyup", function(t) {
          (t || window.event).keyCode === e.Key.Enter && o.moveToPage(r.value - 1)
        }), t._addEvent(r.inputElement, "blur", function(e) {
          o.moveToPage(r.value - 1)
        }), i.addCustomItem('<span class="slash">/</span>'), s.className = "wj-pagecount", i.addCustomItem(s, t._ViewerActionType.PageCountLabel), o.pageIndexChanged.addHandler(u), o._getDocumentSource() && l(), o._documentSourceChanged.addHandler(l)
      }, o
    }(n);
    t._ViewerToolbarBase = o;
    var r = function(n) {
      function r(e, t) {
        return n.call(this, e, t) || this
      }
      return __extends(r, n), r.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gPaginated.title = t.paginated, this._gPrint.title = t.print, this._gExports.title = t.exports, this._gPortrait.title = t.portrait, this._gLandscape.title = t.landscape, this._gPageSetup.title = t.pageSetup, this._gFirstPage.title = t.firstPage, this._gPreviousPage.title = t.previousPage, this._gNextPage.title = t.nextPage, this._gLastPage.title = t.lastPage, this._gBackwardHistory.title = t.backwardHistory, this._gForwardHistory.title = t.forwardHistory, this._gSelectTool.title = t.selectTool, this._gMoveTool.title = t.moveTool, this._gContinuousMode.title = t.continuousMode, this._gSingleMode.title = t.singleMode, this._gWholePage.title = t.wholePage, this._gPageWidth.title = t.pageWidth, this._gZoomOut.title = t.zoomOut, this._gZoomIn.title = t.zoomIn, this._gRubberbandTool.title = t.rubberbandTool, this._gMagnifierTool.title = t.magnifierTool, this._gRotateDocument.title = t.rotateDocument, this._gRotatePage.title = t.rotatePage, this._gFullScreen.title = t.fullScreen
      }, r.prototype._initToolbarItems = function() {
        var n = e.culture.Viewer;
        this._gPaginated = this.addSvgButton(n.paginated, t.icons.paginated, t._ViewerActionType.TogglePaginated, !0), this._gPrint = this.addSvgButton(n.print, t.icons.print, t._ViewerActionType.Print), this._gExports = this.addSvgButton(n.exports, t.icons.exports, t._ViewerActionType.ShowExportsPanel), this.addSeparator(), this._gPortrait = this.addSvgButton(n.portrait, t.icons.portrait, t._ViewerActionType.Portrait), this._gLandscape = this.addSvgButton(n.landscape, t.icons.landscape, t._ViewerActionType.Landscape), this._gPageSetup = this.addSvgButton(n.pageSetup, t.icons.pageSetup, t._ViewerActionType.ShowPageSetupDialog), this.addSeparator(), this._gFirstPage = this.addSvgButton(n.firstPage, t.icons.firstPage, t._ViewerActionType.FirstPage), this._gPreviousPage = this.addSvgButton(n.previousPage, t.icons.previousPage, t._ViewerActionType.PrePage), this._gNextPage = this.addSvgButton(n.nextPage, t.icons.nextPage, t._ViewerActionType.NextPage), this._gLastPage = this.addSvgButton(n.lastPage, t.icons.lastPage, t._ViewerActionType.LastPage), o._initToolbarPageNumberInput(this.hostElement, this.viewer), this.addSeparator(), this._gBackwardHistory = this.addSvgButton(n.backwardHistory, t.icons.backwardHistory, t._ViewerActionType.Backward), this._gForwardHistory = this.addSvgButton(n.forwardHistory, t.icons.forwardHistory, t._ViewerActionType.Forward), this.addSeparator(), this._gSelectTool = this.addSvgButton(n.selectTool, t.icons.selectTool, t._ViewerActionType.SelectTool), this._gMoveTool = this.addSvgButton(n.moveTool, t.icons.moveTool, t._ViewerActionType.MoveTool), this._gContinuousMode = this.addSvgButton(n.continuousMode, t.icons.continuousView, t._ViewerActionType.Continuous), this._gSingleMode = this.addSvgButton(n.singleMode, t.icons.singleView, t._ViewerActionType.Single), this.addSeparator(), this._gWholePage = this.addSvgButton(n.wholePage, t.icons.fitWholePage, t._ViewerActionType.FitWholePage), this._gPageWidth = this.addSvgButton(n.pageWidth, t.icons.fitPageWidth, t._ViewerActionType.FitPageWidth), this._gZoomOut = this.addSvgButton(n.zoomOut, t.icons.zoomOut, t._ViewerActionType.ZoomOut), this._gZoomIn = this.addSvgButton(n.zoomIn, t.icons.zoomIn, t._ViewerActionType.ZoomIn), this._gRubberbandTool = this.addSvgButton(n.rubberbandTool, t.icons.rubberbandTool, t._ViewerActionType.RubberbandTool), this._gMagnifierTool = this.addSvgButton(n.magnifierTool, t.icons.magnifierTool, t._ViewerActionType.MagnifierTool), this._gRotateDocument = this.addSvgButton(n.rotateDocument, t.icons.rotateDocument, t._ViewerActionType.RotateDocument), this._gRotatePage = this.addSvgButton(n.rotatePage, t.icons.rotatePage, t._ViewerActionType.RotatePage), o._initToolbarZoomValue(this.hostElement, this.viewer), this._gFullScreen = this.addSvgButton(n.fullScreen, t.icons.fullScreen, t._ViewerActionType.ToggleFullScreen, !0)
      }, r
    }(o);
    t._ViewerToolbar = r;
    var i = function(n) {
      function r(e, t) {
        return n.call(this, e, t) || this
      }
      return __extends(r, n), r.prototype._initToolbarItems = function() {
        var n = e.culture.Viewer;
        this._gPrint = this.addSvgButton(n.print, t.icons.print, t._ViewerActionType.Print), this.addSeparator(), this._gPreviousPage = this.addSvgButton(n.previousPage, t.icons.previousPage, t._ViewerActionType.PrePage), this._gNextPage = this.addSvgButton(n.nextPage, t.icons.nextPage, t._ViewerActionType.NextPage), o._initToolbarPageNumberInput(this.hostElement, this.viewer), this.addSeparator(), this._gZoomOut = this.addSvgButton(n.zoomOut, t.icons.zoomOut, t._ViewerActionType.ZoomOut), this._gZoomIn = this.addSvgButton(n.zoomIn, t.icons.zoomIn, t._ViewerActionType.ZoomIn), this._gExitFullScreen = this.addSvgButton(n.exitFullScreen, t.icons.exitFullScreen, t._ViewerActionType.ToggleFullScreen, !0)
      }, r.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gPrint.title = t.print, this._gPreviousPage.title = t.previousPage, this._gNextPage.title = t.nextPage, this._gZoomOut.title = t.zoomOut, this._gZoomIn.title = t.zoomIn, this._gExitFullScreen.title = t.exitFullScreen
      }, r
    }(o);
    t._ViewerMiniToolbar = i;
    var a = function(t) {
      function n(n, o) {
        var r = t.call(this, n, o) || this;
        return e.addClass(r.hostElement, "mobile"), r
      }
      return __extends(n, t), n
    }(o);
    t._ViewerMobileToolbarBase = a;
    var s = function(n) {
      function r(e, t) {
        return n.call(this, e, t) || this
      }
      return __extends(r, n), r.prototype._initToolbarItems = function() {
        var n = e.culture.Viewer;
        this._gShowHamburgerMenu = this.addSvgButton(n.hamburgerMenu, t.icons.hamburgerMenu, t._ViewerActionType.ShowHamburgerMenu), this.viewer._initHamburgerMenu(this._gShowHamburgerMenu), this._gPrevPage = this.addSvgButton(n.previousPage, t.icons.previousPage, t._ViewerActionType.PrePage), this._gNextPage = this.addSvgButton(n.nextPage, t.icons.nextPage, t._ViewerActionType.NextPage), o._initToolbarPageNumberInput(this.hostElement, this.viewer), this._gShowViewMenu = this.addSvgButton(n.viewMenu, t.icons.viewMenu, t._ViewerActionType.ShowViewMenu), this.viewer._initViewMenu(this._gShowViewMenu), this._gShowSearchBar = this.addSvgButton(n.showSearchBar, t.icons.search, t._ViewerActionType.ShowSearchBar, !0), this._gFullScreen = this.addSvgButton(n.fullScreen, t.icons.fullScreen, t._ViewerActionType.ToggleFullScreen, !0)
      }, r.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gShowHamburgerMenu.title = t.hamburgerMenu, this._gPrevPage.title = t.previousPage, this._gNextPage.title = t.nextPage, this._gShowViewMenu.title = t.viewMenu, this._gShowSearchBar.title = t.showSearchBar, this._gFullScreen.title = t.fullScreen
      }, r
    }(a);
    t._ViewerMobileToolbar = s;
    var u = function(n) {
      function r(t, o) {
        var r = n.call(this, t, o) || this;
        return e.addClass(r.hostElement, "wj-zoombar"), r
      }
      return __extends(r, n), r.prototype._initToolbarItems = function() {
        var n = e.culture.Viewer;
        this._gZoomOut = this.addSvgButton(n.zoomOut, t.icons.zoomOut, t._ViewerActionType.ZoomOut), o._initToolbarZoomValue(this.hostElement, this.viewer), this._gZoomIn = this.addSvgButton(n.zoomIn, t.icons.zoomIn, t._ViewerActionType.ZoomIn)
      }, r.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gZoomOut.title = t.zoomOut, this._gZoomIn.title = t.zoomIn
      }, r
    }(a);
    t._ViewerZoomBar = u;
    var c = function(n) {
      function o(e, t) {
        return n.call(this, e, t) || this
      }
      return __extends(o, n), o.prototype._initToolbarItems = function() {
        var n = e.culture.Viewer;
        this._gSearchOptions = this.addSvgButton(n.searchOptions, t.icons.searchOptions, t._ViewerActionType.ShowSearchOptions), this.viewer._initSearchOptionsMenu(this._gSearchOptions), this._initSearchInput(), this._initSearchBtnGroups()
      }, o.prototype._initSearchInput = function() {
        var e = this,
          n = '<div class="wj-searchcontainer"><input class="wj-searchbox" wj-part="search-box" type="text"/><div class="wj-btn-group"><button class="wj-btn wj-btn-search">' + t._createSvgBtn(t.icons.search).innerHTML + "</button></div></div>",
          o = t._toDOM(n),
          r = o.querySelector('input[type="text"]'),
          i = o.querySelector(".wj-btn-search");
        t._addEvent(r, "input", function() {
          e.viewer._searchManager.text = r.value
        }), t._addEvent(i, "click", function() {
          e.viewer._searchManager.search()
        }), this.viewer._searchManager.searchStarted.addHandler(function() {
          r.disabled = !0
        }), this.viewer._searchManager.searchCompleted.addHandler(function() {
          r.disabled = !1
        }), this.addCustomItem(o)
      }, o.prototype._initSearchBtnGroups = function() {
        var n = t._toDOM('<div class="wj-searchbtn-groups wj-btn-group wj-toolbarwrapper"></div>'),
          o = e.culture.Viewer;
        this._gSearchPrev = this.addSvgButton(o.searchPrev, t.icons.searchLeft, t._ViewerActionType.SearchPrev), this._gSearchNext = this.addSvgButton(o.searchNext, t.icons.searchRight, t._ViewerActionType.SearchNext), n.appendChild(this._gSearchPrev), n.appendChild(this._gSearchNext), this.addCustomItem(n)
      }, o.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gSearchOptions.title = t.searchOptions, this._gSearchPrev.title = t.searchPrev, this._gSearchNext.title = t.searchNext
      }, o
    }(a);
    t._SearchBar = c
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n;
    ! function(e) {
      e[e.NoRotate = 0] = "NoRotate", e[e.Rotation90 = 1] = "Rotation90", e[e.Rotation180 = 2] = "Rotation180", e[e.Rotation270 = 3] = "Rotation270"
    }(n = t._RotateAngle || (t._RotateAngle = {}));
    var o = function() {
      function o(t, o, r) {
        this._content = null, this._rotateAngle = n.NoRotate, this.linkClicked = new e.Event, this._documentSource = t, this._index = o, this._size = r
      }
      return Object.defineProperty(o.prototype, "index", {
        get: function() {
          return this._index
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "size", {
        get: function() {
          return this._size
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "rotateAngle", {
        get: function() {
          return this._rotateAngle
        },
        set: function(e) {
          this._rotateAngle = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "content", {
        get: function() {
          return this._content
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.getContent = function() {
        var n = this,
          o = new t._Promise,
          r = this._documentSource;
        return r ? this._content ? (o.resolve(this._content), o) : (r.renderToFilter({
          format: "html",
          paged: r.paginated,
          outputRange: (this.index + 1).toString()
        }).then(function(e) {
          if (n._documentSource === r) {
            var i = document.createElement("div");
            i.innerHTML = n._processSvgResponse(n._addGlobalUniqueId(e.responseText));
            for (var a = i.querySelector("svg"), s = document.createElementNS("http://www.w3.org/2000/svg", "g"); a.hasChildNodes();) s.appendChild(a.firstChild);
            a.appendChild(s), n._size = {
              width: new t._Unit(a.width.baseVal.value),
              height: new t._Unit(a.height.baseVal.value)
            }, n._content = a, n._processActionLinks(a, function(e) {
              t._addEvent(e, "click", function() {
                n._onLinkClicked(new t._LinkClickedEventArgs(e))
              })
            }), o.resolve(n._content)
          }
        }), o) : (o.reject(e.culture.Viewer.cannotRenderPageNoDoc), o)
      }, o.prototype._processSvgResponse = function(e) {
        return e
      }, o.prototype._extractSize = function(e) {
        if (e) {
          var n = e.querySelector("svg");
          if (n) return {
            width: new t._Unit(n.width.baseVal.value),
            height: new t._Unit(n.height.baseVal.value)
          }
        }
        return null
      }, o.prototype._onLinkClicked = function(e) {
        this.linkClicked.raise(this, e)
      }, o.prototype._processActionLinks = function(e, t) {
        for (var n = e.querySelectorAll("a"), r = 0; r < n.length; r++) {
          var i = n.item(r),
            a = i.href ? i.href.baseVal : "";
          if (a.indexOf("navigate") > 0) {
            var s = o._bookmarkReg.exec(a);
            s && (s[1] && s[1].length > 0 ? (i.href.baseVal = o._invalidHref, i.setAttribute(o._bookmarkAttr, s[1]), t(i)) : i.removeAttribute("xlink:href"))
          } else o._customActionReg.test(a) && (i.href.baseVal = o._invalidHref, i.setAttribute(o._customActionAttr, a.substr(3)), t(i))
        }
      }, o.prototype._addGlobalUniqueId = function(e) {
        var t = (new Date).getTime().toString();
        return e = e.replace(o._idReg, "$1$2" + t + "$3$4"), e = e.replace(o._idReferReg, "$1$2" + t + "$3$4")
      }, o._bookmarkReg = /javascript\:navigate\(['|"](.*)['|"]\)/, o._bookmarkAttr = "bookmark", o._customActionReg = /^CA\:/, o._customActionAttr = "customAction", o._idReg = /(\<[^\>]+)(id=['|"])(\w+)(['|"])/g, o._idReferReg = /(\<[^\>]+)(url\(#)(\w+)(\))/g, o._invalidHref = "javascript:void(0)", o
    }();
    t._Page = o;
    var r = function(n) {
      function o(o) {
        var r = n.call(this, o) || this;
        r._viewMode = t.ViewMode.Single, r.pageIndexChanged = new e.Event, r.zoomFactorChanged = new e.Event, r.zoomModeChanged = new e.Event, r.positionChanged = new e.Event, r.rotateAngleChanged = new e.Event;
        var i;
        return i = r.getTemplate(), r.applyTemplate("wj-viewpanel-container", i, {
          _singlePageView: "single-pageview",
          _continuousPageView: "continuous-pageview"
        }), r._initPageView(), r
      }
      return __extends(o, n), o.prototype.applyTemplate = function(n, o, r) {
        var i = this.hostElement;
        if (e.addClass(i, n), i.appendChild(t._toDOMs(o)), r)
          for (var a in r) {
            var s = r[a];
            if (this[a] = i.querySelector('[wj-part="' + s + '"]'), null == this[a] && i.getAttribute("wj-part") == s && (this[a] = o), null == this[a]) throw 'Missing template part: "' + s + '"'
          }
        return i
      }, Object.defineProperty(o.prototype, "pageIndex", {
        get: function() {
          return this._activePageView.pageIndex
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "pages", {
        get: function() {
          return this._activePageView.pages
        },
        set: function(e) {
          this._activePageView.pages = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "zoomMode", {
        get: function() {
          return this._activePageView.zoomMode
        },
        set: function(e) {
          this._activePageView.zoomMode = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "zoomFactor", {
        get: function() {
          return this._activePageView.zoomFactor
        },
        set: function(e) {
          this._activePageView.zoomFactor = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "panMode", {
        get: function() {
          return this._activePageView.panMode
        },
        set: function(e) {
          this._activePageView.panMode = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "viewMode", {
        get: function() {
          return this._viewMode
        },
        set: function(e) {
          this._viewMode !== e && (this._viewMode = e, this._updateActivePageView())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "scrollTop", {
        get: function() {
          return this._activePageView.scrollTop
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "scrollLeft", {
        get: function() {
          return this._activePageView.scrollLeft
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_activePageViewElement", {
        get: function() {
          return this.viewMode === t.ViewMode.Single ? this._singlePageView : this._continuousPageView
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.onPageIndexChanged = function() {
        this.pageIndexChanged.raise(this)
      }, o.prototype.onZoomFactorChanged = function(e, t) {
        this.zoomFactorChanged.raise(this, {
          oldValue: e,
          newValue: t
        })
      }, o.prototype.onZoomModeChanged = function(e, t) {
        this.zoomModeChanged.raise(this, {
          oldValue: e,
          newValue: t
        })
      }, o.prototype.onPositionChanged = function() {
        this.positionChanged.raise(this)
      }, o.prototype.onRotateAngleChanged = function() {
        this.rotateAngleChanged.raise(this)
      }, o.prototype._updateActivePageView = function() {
        var t = this._activePageView.pageIndex,
          n = this._activePageView.pages,
          o = this._activePageView.zoomFactor,
          r = this._activePageView.zoomMode,
          i = this._activePageView.panMode;
        this._removePageViewHandlers(this._activePageView), this._activePageView = e.Control.getControl(this._activePageViewElement), this._addPageViewHandlers(this._activePageView), this._activePageView.pages || (this._activePageView.pages = n), this._activePageView.invalidate(), this._activePageView.moveToPage(t), this._activePageView.zoomFactor = o, this._activePageView.zoomMode = r, this._activePageView.panMode = i, this._updatePageViewsVisible()
      }, o.prototype._initPageView = function() {
        this._activePageView = new u(this._singlePageView), this._addPageViewHandlers(this._activePageView), new c(this._continuousPageView), this._updatePageViewsVisible()
      }, o.prototype._addPageViewHandlers = function(e) {
        var t = this;
        this._activePageView.pageIndexChanged.addHandler(function() {
          t.onPageIndexChanged()
        }), this._activePageView.zoomFactorChanged.addHandler(function(e, n) {
          t.onZoomFactorChanged(n.oldValue, n.newValue)
        }), this._activePageView.zoomModeChanged.addHandler(function(e, n) {
          t.onZoomModeChanged(n.oldValue, n.newValue)
        }), this._activePageView.positionChanged.addHandler(function() {
          t.onPositionChanged()
        }), this._activePageView.rotateAngleChanged.addHandler(function() {
          t.onRotateAngleChanged()
        })
      }, o.prototype._removePageViewHandlers = function(e) {
        e.pageIndexChanged.removeHandler(this.onPageIndexChanged, this), e.zoomFactorChanged.removeHandler(this.onZoomFactorChanged, this), e.zoomModeChanged.removeHandler(this.onZoomModeChanged, this), e.positionChanged.removeHandler(this.onPositionChanged, this), e.rotateAngleChanged.removeHandler(this.onRotateAngleChanged, this)
      }, o.prototype._updatePageViewsVisible = function() {
        this.viewMode === t.ViewMode.Single ? (e.removeClass(this._singlePageView, t._hiddenCss), e.hasClass(this._continuousPageView, t._hiddenCss) || e.addClass(this._continuousPageView, t._hiddenCss)) : (e.removeClass(this._continuousPageView, t._hiddenCss), e.hasClass(this._singlePageView, t._hiddenCss) || e.addClass(this._singlePageView, t._hiddenCss))
      }, o.prototype.moveToPage = function(e) {
        return this._activePageView.moveToPage(e)
      }, o.prototype.moveToPosition = function(e) {
        return this._activePageView.moveToPosition(e)
      }, o.prototype.rotatePageTo = function(e, t) {
        this._activePageView.rotatePageTo(e, t)
      }, o.prototype.hitTest = function(e, t) {
        return this._activePageView.hitTest(e, t)
      }, o.prototype.resetPages = function() {
        e.Control.getControl(this._singlePageView).resetPages(), e.Control.getControl(this._continuousPageView).resetPages()
      }, o.prototype.refresh = function(t) {
        void 0 === t && (t = !0), n.prototype.refresh.call(this, t), e.Control.getControl(this._activePageViewElement).invalidate(), this._activePageView.refresh()
      }, o.prototype.isPageContentLoaded = function(e) {
        return this._activePageView.isPageContentLoaded(e)
      }, o.controlTemplate = '<div class="wj-pageview" wj-part="single-pageview"></div><div class="wj-pageview" wj-part="continuous-pageview"></div>', o
    }(e.Control);
    t._CompositePageView = r;
    var i = function(o) {
      function r(n) {
        var r = o.call(this, n) || this;
        r._autoHeightCalculated = !1, r._startX = null, r._startY = null, r._panMode = !1, r._pageIndex = -1, r._zoomFactor = 1, r._zoomMode = t.ZoomMode.Custom, r._zoomModeUpdating = !1, r.pageIndexChanged = new e.Event, r.zoomFactorChanged = new e.Event, r.zoomModeChanged = new e.Event, r.positionChanged = new e.Event, r.rotateAngleChanged = new e.Event;
        var i;
        return i = r.getTemplate(), r.applyTemplate("wj-pageview", i, r._getTemplateParts()), r._fBorderBoxMode = "border-box" === getComputedStyle(n).boxSizing, r._init(), r
      }
      return __extends(r, o), r.prototype._getTemplateParts = function() {
        return {
          _pagesWrapper: "pages-wrapper"
        }
      }, r.prototype._getPagesContainer = function() {
        return this.hostElement
      }, r.prototype._init = function() {
        this._bindEvents()
      }, r.prototype.dispose = function() {
        this._touchManager && this._touchManager.dispose(), o.prototype.dispose.call(this)
      }, r.prototype._bindTouchEvents = function(e) {
        var t = this;
        e.touchStart.addHandler(function() {
          t.hostElement.focus()
        }), e.panMove.addHandler(function(t, n) {
          e.hostElement.scrollTop -= n.clientDelta.y, e.hostElement.scrollLeft -= n.clientDelta.x
        }), e.pinch.addHandler(this._zoomByPinch, this)
      }, r.prototype._initTouchEvents = function() {
        var e = this._pagesWrapper.parentElement,
          n = new t._TouchManager(e);
        this._touchManager = n, this._bindTouchEvents(n)
      }, Object.defineProperty(r.prototype, "_borderBoxMode", {
        get: function() {
          return this._fBorderBoxMode
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype._zoomByPinch = function(n, o) {
        if (o.preventDefault(), o.type == t._TouchEventType.Move) {
          var r = e.getElementRect(n.contentElement),
            i = getComputedStyle(n.contentElement),
            a = parseInt(i.marginTop),
            s = parseInt(i.marginLeft),
            u = t._pointMove(!1, o.preCenterClient, r.left - s, r.top - a);
          this._zoom(n.hostElement, o.zoom, u, o.centerClientDelta)
        }
      }, r.prototype._getFixedPosition = function(t) {
        return new e.Point(r._pageMargin, r._pageMargin + this._getAbovePageCount(t.y) * r._pageMargin)
      }, r.prototype._getAbovePageCount = function(e) {
        return 0
      }, r.prototype._zoom = function(n, o, r, i) {
        var a = t._pointMove(!1, r, n.scrollLeft, n.scrollTop),
          s = this._getFixedPosition(r),
          u = this.zoomFactor;
        this.zoomFactor = this.zoomFactor * o;
        var c = this.zoomFactor / u,
          l = new e.Point((r.x - s.x) * c + s.x, (r.y - s.y) * c + s.y),
          p = t._pointMove(!0, a, i),
          h = t._pointMove(!1, l, p);
        n.scrollTop = Math.round(h.y), n.scrollLeft = Math.round(h.x)
      }, Object.defineProperty(r.prototype, "pageIndex", {
        get: function() {
          return this._pageIndex
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "pages", {
        get: function() {
          return this._pages
        },
        set: function(e) {
          this._pages = e, this._reserveViewPage()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "scrollTop", {
        get: function() {
          return this.hostElement.scrollTop
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "scrollLeft", {
        get: function() {
          return this.hostElement.scrollLeft
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "zoomFactor", {
        get: function() {
          return this._zoomFactor
        },
        set: function(e) {
          if (e = Math.max(.05, Math.min(10, e)), this._zoomFactor != e) {
            var n = this._zoomFactor;
            this._zoomFactor = e, this._updatePageViewTransform(), this._zoomModeUpdating || (this.zoomMode = t.ZoomMode.Custom), this._onZoomFactorChanged(n, e)
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "zoomMode", {
        get: function() {
          return this._zoomMode
        },
        set: function(e) {
          if (this._zoomMode != e) {
            this._zoomModeUpdating = !0;
            var t = this._zoomMode;
            this._calcZoomModeZoom(e), this._zoomMode = e, this._onZoomModeChanged(t, e), this._zoomModeUpdating = !1
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "panMode", {
        get: function() {
          return this._panMode
        },
        set: function(e) {
          this._panMode != e && (this._panMode = e)
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype._bindEvents = function() {
        var e = this;
        t._addEvent(document, "mousemove", function(t) {
          null !== e._startX && null !== e._startY && e._panning(t)
        }), t._addEvent(document, "mouseup", function(t) {
          e._stopPanning()
        }), this._initTouchEvents()
      }, r.prototype._startPanning = function(e) {
        this._startX = e.screenX, this._startY = e.screenY
      }, r.prototype._panning = function(e) {
        var t = this._getPagesContainer();
        t.scrollLeft += this._startX - e.screenX, t.scrollTop += this._startY - e.screenY, this._startX = e.screenX, this._startY = e.screenY
      }, r.prototype._stopPanning = function() {
        this._startX = null, this._startY = null
      }, r.prototype._onPageIndexChanged = function() {
        this.pageIndexChanged.raise(this)
      }, r.prototype._onZoomFactorChanged = function(e, t) {
        this.zoomFactorChanged.raise(this, {
          oldValue: e,
          newValue: t
        })
      }, r.prototype._onZoomModeChanged = function(e, t) {
        this.zoomModeChanged.raise(this, {
          oldValue: e,
          newValue: t
        })
      }, r.prototype._onPositionChanged = function() {
        this.positionChanged.raise(this)
      }, r.prototype._onRotateAngleChanged = function() {
        this.rotateAngleChanged.raise(this)
      }, r.prototype._onPageLoaded = function(e) {}, r.prototype._renderViewPage = function(n, o) {
        var r, i = this,
          a = new t._Promise;
        if (o = o < 0 ? 0 : o, !n) return a.reject(e.culture.Viewer.cannotRenderPageNoViewPage), a;
        if (t._removeChildren(n), this._pages[o].content) {
          var s = this._pages[o].content;
          return n.appendChild(s), this._setPageTransform(n, o), a.resolve(o), a
        }
        return r = document.createElement("div"), r.className = "wj-loading", r.style.height = n.style.height, r.style.lineHeight = n.style.height, r.innerHTML = e.culture.Viewer.loading, n.appendChild(r), this._pages[o].getContent().then(function(e) {
          if (i.pages) {
            var r = e;
            t._removeChildren(n), n.appendChild(r), i._setPageTransform(n, o), i._onPageLoaded(o), a.resolve(o)
          }
        }).catch(function(e) {
          r.innerHTML = t._getErrorMessage(e)
        }), a
      }, r.prototype._reserveViewPage = function() {
        throw e.culture.Viewer.abstractMethodException
      }, r.prototype._getViewPortHeight = function() {
        var e = this._pagesWrapper.currentStyle || window.getComputedStyle(this._pagesWrapper);
        return this.hostElement.offsetHeight - parseFloat(e.marginBottom) - parseFloat(e.marginTop)
      }, r.prototype._getViewPortWidth = function() {
        var e = this._pagesWrapper.currentStyle || window.getComputedStyle(this._pagesWrapper);
        return this.hostElement.offsetWidth - parseFloat(e.marginLeft) - parseFloat(e.marginRight)
      }, r.prototype._setPageTransform = function(e, n) {
        var o;
        if (e && !(n < 0)) {
          var r = this._getPageSize(n),
            i = this._pages[n].rotateAngle;
          e.style.height = r.height.valueInPixel * this._zoomFactor + "px", e.style.width = r.width.valueInPixel * this._zoomFactor + "px", (o = e.querySelector("g")) && (o.parentNode.setAttribute("height", r.height.valueInPixel * this._zoomFactor + "px"), o.parentNode.setAttribute("width", r.width.valueInPixel * this._zoomFactor + "px"), t._transformSvg(o.parentNode, this._pages[n].size.width.valueInPixel, this._pages[n].size.height.valueInPixel, this._zoomFactor, i))
        }
      }, r.prototype._addViewPage = function() {
        var e = this,
          n = document.createElement("div");
        return n.className = "wj-view-page", t._addEvent(n, "mousedown", function(t) {
          e._panMode && e._startPanning(t)
        }), t._addEvent(n, "dragstart", function(t) {
          e._panMode && t.preventDefault()
        }), this._pagesWrapper.appendChild(n), n
      }, r.prototype._getPageSize = function(e) {
        if (e < 0 || e >= this._pages.length) return null;
        var n = this._pages[e];
        return t._getRotatedSize(n.size, n.rotateAngle)
      }, r.prototype._render = function(t) {
        throw e.culture.Viewer.abstractMethodException
      }, r.prototype._moveToPagePosition = function(t) {
        throw e.culture.Viewer.abstractMethodException
      }, r.prototype._updatePageViewTransform = function() {
        throw e.culture.Viewer.abstractMethodException
      }, r.prototype._updatePageIndex = function(e) {
        this.pages && (e = this.resolvePageIndex(e), this._pageIndex !== e && (this._pageIndex = e, this._onPageIndexChanged()))
      }, r.prototype.moveToPage = function(e) {
        return this.moveToPosition({
          pageIndex: e
        })
      }, r.prototype.resolvePageIndex = function(e) {
        return Math.min((this.pages || []).length - 1, Math.max(e, 0))
      }, r.prototype.moveToPosition = function(e) {
        var n = this,
          o = e.pageIndex || 0,
          r = new t._Promise,
          i = this.pageIndex;
        return !this.pages || o < 0 ? (r.resolve(o), r) : (o = this.resolvePageIndex(o), e.pageIndex = o, o !== i ? (this._updatePageIndex(o), r = this._render(o)) : r.resolve(o), r.then(function() {
          n.pages && (e.samePage = o === i, n._moveToPagePosition(e))
        }), r.then(function(e) {
          return o
        }).then(function() {
          n._calcZoomModeZoom(), n._onPositionChanged()
        }))
      }, r.prototype._calcZoomModeZoom = function(e) {
        switch (this._zoomModeUpdating = !0, e = null == e ? this.zoomMode : e) {
          case t.ZoomMode.PageWidth:
            this._zoomToViewWidth();
            break;
          case t.ZoomMode.WholePage:
            this._zoomToView()
        }
        this._zoomModeUpdating = !1
      }, r.prototype._zoomToView = function() {
        var e = this._getViewPortHeight(),
          t = this._getViewPortWidth(),
          n = this._getPageSize(this.pageIndex);
        n && (this.zoomFactor = Math.min(e / n.height.valueInPixel, t / n.width.valueInPixel))
      }, r.prototype._zoomToViewWidth = function() {
        throw e.culture.Viewer.abstractMethodException
      }, r.prototype._getTransformedPoint = function(t, o, r) {
        o /= this.zoomFactor, r /= this.zoomFactor;
        var i = this.pages[t],
          a = i.size;
        switch (i.rotateAngle) {
          case n.Rotation90:
            s = o;
            o = a.height.valueInPixel - r, r = s;
            break;
          case n.Rotation180:
            o = a.height.valueInPixel - o, r = a.width.valueInPixel - r;
            break;
          case n.Rotation270:
            var s = o;
            o = r, r = a.width.valueInPixel - s
        }
        return new e.Point(r, o)
      }, r.prototype._hitTestPagePosition = function(e) {
        if (!e || e.pageIndex < 0) return null;
        var n = e.y,
          o = e.x,
          i = e.pageIndex;
        n -= r._pageMargin + r._pageBorderWidth, o -= r._pageMargin + r._pageBorderWidth;
        var a = this._getTransformedPoint(e.pageIndex, n, o);
        n = a.y, o = a.x;
        var s = t._pixelToTwip(n),
          u = t._pixelToTwip(o),
          c = this.pages[i].size;
        return {
          pageIndex: i,
          x: u,
          y: s,
          hitWorkingArea: n >= 0 && n <= c.height.valueInPixel && o >= 0 && o <= c.width.valueInPixel
        }
      }, r.prototype.rotatePageTo = function(e, t) {
        this._pages[e].rotateAngle = t, this._updatePageViewTransform(), this._onRotateAngleChanged()
      }, r.prototype.hitTest = function(e, t) {
        if (this._pointInViewPanelClientArea(e, t)) {
          var n = this._panelViewPntToPageView(e, t);
          return this._hitTestPagePosition(n)
        }
        return null
      }, r.prototype.resetPages = function() {
        this._pageIndex = -1, this._pages = null, t._removeChildren(this._pagesWrapper), this._addViewPage(), this.invalidate()
      }, r.prototype.refresh = function(e) {
        void 0 === e && (e = !0), o.prototype.refresh.call(this, e), this._autoHeightCalculated = !1, !this.pages || 0 == this.pages.length || this.pageIndex < 0 || this.pageIndex >= this.pages.length || (this._render(this.pageIndex), this._calcZoomModeZoom())
      }, r.prototype.isPageContentLoaded = function(e) {
        var t = this.pages;
        return !!t && e >= 0 && e < t.length && !!t[e].content
      }, r.prototype._hitTestPageIndex = function(t) {
        throw e.culture.Viewer.abstractMethodException
      }, r.prototype._pointInViewPanelClientArea = function(t, n) {
        throw e.culture.Viewer.abstractMethodException
      }, r.prototype._panelViewPntToPageView = function(t, n) {
        throw e.culture.Viewer.abstractMethodException
      }, r._pageMargin = 30, r._pageBorderWidth = 1, r.controlTemplate = '<div class="wj-pages-wrapper" wj-part="pages-wrapper"></div>', r
    }(e.Control);
    t._PageViewBase = i;
    var a = function(e) {
      function t() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(t, e), t.getScrollbarWidth = function(e) {
        var n, o;
        return t._scrollbarWidth && !e || ((n = document.createElement("div")).style.width = "50px", n.style.height = "50px", n.style.overflow = "auto", document.body.appendChild(n), (o = document.createElement("div")).style.height = "60px", n.appendChild(o), t._scrollbarWidth = n.offsetWidth - n.clientWidth, document.body.removeChild(n)), t._scrollbarWidth
      }, t._scrollbarWidth = null, t
    }(e.Control);
    t._Scroller = a;
    var s = function(n) {
      function o(o) {
        var r = n.call(this, o) || this;
        r._height = 100, r._max = 100, r._desiredValue = 0, r.valueChanged = new e.Event;
        var i;
        return i = r.getTemplate(), r.applyTemplate(null, i, {
          _wrapper: "wrapper"
        }), r.hostElement.style.width = a.getScrollbarWidth() + 1 + "px", t._addEvent(r.hostElement, "scroll", function() {
          r.onValueChanged()
        }), r
      }
      return __extends(o, n), o.prototype.onValueChanged = function() {
        this._desiredValue != this.value && this.valueChanged.raise(this)
      }, o.prototype.preventScrollEvent = function() {
        this._desiredValue = this.hostElement.scrollTop
      }, Object.defineProperty(o.prototype, "height", {
        get: function() {
          return this._height
        },
        set: function(e) {
          e !== this._height && (this._height = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "value", {
        get: function() {
          return this.hostElement.scrollTop
        },
        set: function(e) {
          this.hostElement.scrollTop = e, this.preventScrollEvent()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "max", {
        get: function() {
          return this._max
        },
        set: function(e) {
          this._max !== e && (this._max = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e);
        var t = this._height + "px";
        this.hostElement.style.height !== t && (this.hostElement.style.height = t);
        var o = this._max + this.hostElement.clientHeight + "px";
        this._wrapper.style.height !== o && (this._wrapper.style.height = o), this.preventScrollEvent()
      }, o.controlTemplate = '<div class="wj-vscroller-wrapper" wj-part="wrapper"></div>', o
    }(a);
    t._VScroller = s;
    var u = function(n) {
      function o(t) {
        var o = n.call(this, t) || this;
        return o._innerNavigating = !1, o._virtualScrollMode = !0, e.addClass(t, "wj-pageview-single"), o
      }
      return __extends(o, n), o.prototype._init = function() {
        n.prototype._init.call(this), this._initScroller(), this._initEvents()
      }, o.prototype._initScroller = function() {
        var e = this;
        new s(this._vscroller).valueChanged.addHandler(function() {
          setTimeout(function() {
            return e._doScrollerValueChanged()
          })
        })
      }, o.prototype._initEvents = function() {
        var e = this;
        t._addEvent(this._pagesContainer, "wheel", function(t) {
          e._doContainerWheel(t)
        }), t._addEvent(this._pagesContainer, "scroll", function(t) {
          e._doContainerScroll()
        }), t._addEvent(this._pagesContainer, "keydown", function(t) {
          e._doContainerKeyDown()
        }), t._addEvent(this._pagesContainer, "click", function(t) {
          e._pagesContainer.focus()
        })
      }, o.prototype._bindTouchEvents = function(e) {
        var o = this;
        n.prototype._bindTouchEvents.call(this, e), e.swipe.addHandler(function(e, n) {
          switch (n.direction) {
            case t._TouchDirection.Down:
              var r = o.resolvePageIndex(o.pageIndex - 1);
              r != o.pageIndex && o.moveToPage(r);
              break;
            case t._TouchDirection.Up:
              var i = o.resolvePageIndex(o.pageIndex + 1);
              i != o.pageIndex && o.moveToPage(i)
          }
        })
      }, o.prototype._getTemplateParts = function() {
        return {
          _pagesWrapper: "pages-wrapper",
          _pagesContainer: "pages-container",
          _vscroller: "vscroller"
        }
      }, o.prototype.applyTemplate = function(n, o, r) {
        var i = this.hostElement;
        if (e.addClass(i, n), i.appendChild(t._toDOMs(o)), r)
          for (var a in r) {
            var s = r[a];
            if (this[a] = i.querySelector('[wj-part="' + s + '"]'), null == this[a] && i.getAttribute("wj-part") == s && (this[a] = o), null == this[a]) throw 'Missing template part: "' + s + '"'
          }
        return i
      }, Object.defineProperty(o.prototype, "virtualScrollMode", {
        get: function() {
          return this._virtualScrollMode
        },
        set: function(e) {
          this._virtualScrollMode !== e && (this._virtualScrollMode = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_isScrollerVisible", {
        get: function() {
          return this._virtualScrollMode && this.pages && this.pages.length > 1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_scroller", {
        get: function() {
          return e.Control.getControl(this._vscroller)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_hasPageVScrollBar", {
        get: function() {
          return t._hasScrollbar(this._pagesContainer)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_hasPageHScrollBar", {
        get: function() {
          return t._hasScrollbar(this._pagesContainer, !0)
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._getPagesContainer = function() {
        return this._pagesContainer
      }, o.prototype._getPageHeightWithoutZoom = function(e) {
        var t = this._pagesContainer.clientHeight,
          n = this._getPageSize(e).height.valueInPixel + 2 * i._pageMargin;
        return Math.max(n, t)
      }, o.prototype._updateScroller = function() {
        if (this._isScrollerVisible) {
          var e = this._scroller;
          e.height = this._pagesContainer.clientHeight;
          for (var t = 0, n = 0; n < this.pages.length; n++) t += this._getPageHeightWithoutZoom(n);
          e.max = t, this._updateScrollerValue()
        }
      }, o.prototype._updateScrollerValue = function() {
        if (this._isScrollerVisible) {
          var e = this.pageIndex;
          if (!(e < 0)) {
            var t = 0;
            this._pagesContainer.scrollTop > 0 && (t = this._pagesContainer.scrollTop / (this._pagesContainer.scrollHeight - this._pagesContainer.clientHeight));
            for (var n = this._scroller, o = this.pages.length, r = 0, i = 0; i < e; i++) r += this._getPageHeightWithoutZoom(i);
            r += this._getPageHeightWithoutZoom(e) * t, e >= o - 1 && !this._hasPageVScrollBar && (r = n.max), n.value = r
          }
        }
      }, o.prototype._doScrollerValueChanged = function() {
        if (this._isScrollerVisible) {
          for (var e = this._scroller, t = this.pages.length, n = e.value, o = 0, r = 1; o < t; o++) {
            var i = this._getPageHeightWithoutZoom(o);
            if (!(n > i)) {
              r = n / i;
              break
            }
            n -= i
          }
          o >= t && (o = t - 1), this._innerMoveToPage(o, r)
        }
      }, o.prototype._doContainerWheel = function(e) {
        if (this._isScrollerVisible && 0 != e.deltaY) {
          var t = e.deltaY < 0;
          if (this._hasPageVScrollBar)
            if (t) {
              if (this._pagesContainer.scrollTop > 0) return
            } else if (this._pagesContainer.scrollTop < this._pagesContainer.scrollHeight - this._pagesContainer.clientHeight) return;
          t ? this._innerMoveToPreviousPageAtBottom(e) : this._innerMoveToNextPageAtTop(e)
        }
      }, o.prototype._doContainerScroll = function() {
        this._isScrollerVisible && this._pagesContainer.scrollTop != this._desiredPageScrollTop && (this._updateScrollerValue(), this._onPositionChanged())
      }, o.prototype._doContainerKeyDown = function() {
        if (this._isScrollerVisible) {
          var t = event;
          if (this._hasPageVScrollBar) switch (t.keyCode) {
            case e.Key.PageDown:
              this._pagesContainer.scrollTop >= this._pagesContainer.scrollHeight - this._pagesContainer.clientHeight && this._innerMoveToNextPageAtTop(t);
              break;
            case e.Key.PageUp:
              0 == this._pagesContainer.scrollTop && this._innerMoveToPreviousPageAtBottom(t)
          } else switch (t.keyCode) {
            case e.Key.Down:
            case e.Key.PageDown:
              this._innerMoveToNextPageAtTop(t);
              break;
            case e.Key.Up:
            case e.Key.PageUp:
              this._innerMoveToPreviousPageAtBottom(t)
          }
        }
      }, o.prototype._preventContainerScroll = function() {
        this._desiredPageScrollTop = this._pagesContainer.scrollTop
      }, o.prototype._innerMoveToPreviousPageAtBottom = function(e) {
        this.pageIndex > 0 && (e && e.preventDefault(), this._innerMoveToPage(this.pageIndex - 1, 1), this._updateScrollerValue())
      }, o.prototype._innerMoveToNextPageAtTop = function(e) {
        this.pageIndex < this.pages.length - 1 && (e && e.preventDefault(), this._innerMoveToPage(this.pageIndex + 1, 0), this._updateScrollerValue())
      }, o.prototype._innerMoveToPage = function(e, t) {
        var n = this;
        this._innerNavigating = !0;
        var o = {
          pageIndex: e
        };
        this.moveToPosition(o).then(function(e) {
          n._innerMoveToPagePosition(t), n._innerNavigating = !1
        })
      }, o.prototype._innerMoveToPagePosition = function(e) {
        if (this._hasPageVScrollBar) {
          var t = (this._pagesContainer.scrollHeight - this._pagesContainer.clientHeight) * e;
          this._pagesContainer.scrollTop = t, this._preventContainerScroll()
        }
      }, o.prototype.moveToPosition = function(e) {
        var t = n.prototype.moveToPosition.call(this, e);
        return this._innerNavigating || this._updateScrollerValue(), t
      }, o.prototype._moveToPagePosition = function(e) {
        var n = e.pageBounds || {
            x: 0,
            y: 0,
            width: 0,
            height: 0
          },
          o = e.pageBounds ? i._pageMargin : 0,
          r = this.pages[this.pageIndex],
          a = t._getTransformedPosition(n, r.size, r.rotateAngle, this.zoomFactor);
        e.samePage || (this._pagesContainer.scrollTop = a.y + o, this._pagesContainer.scrollLeft = a.x + o)
      }, o.prototype._hitTestPageIndex = function(e) {
        return this.pageIndex
      }, o.prototype._pointInViewPanelClientArea = function(e, t) {
        return e >= 0 && t >= 0 && e < this._pagesContainer.clientWidth && t < this._pagesContainer.clientHeight
      }, o.prototype._panelViewPntToPageView = function(e, t) {
        if (this.pageIndex < 0) return null;
        var n = this._pagesContainer.scrollTop + t,
          o = 0;
        if (this._pagesContainer.scrollLeft > 0) o = this._pagesContainer.scrollLeft + e;
        else {
          var r = this._pagesContainer.getBoundingClientRect();
          o = e - (this._pagesWrapper.getBoundingClientRect().left - r.left) + i._pageMargin
        }
        return {
          x: o,
          y: n,
          pageIndex: this.pageIndex
        }
      }, o.prototype._render = function(e) {
        return this._renderViewPage(this._pagesWrapper.querySelector(".wj-view-page"), e)
      }, o.prototype._guessPageIndex = function() {
        return this.pageIndex
      }, o.prototype._reserveViewPage = function() {
        t._removeChildren(this._pagesWrapper), this._addViewPage(), this.invalidate()
      }, o.prototype._updatePageViewTransform = function() {
        var e;
        e = this._pagesWrapper.querySelector(".wj-view-page"), this._setPageTransform(e, this.pageIndex)
      }, o.prototype._onPageLoaded = function(e) {
        n.prototype._onPageLoaded.call(this, e), this._updateScroller()
      }, o.prototype._onZoomFactorChanged = function(e, t) {
        n.prototype._onZoomFactorChanged.call(this, e, t), this._updateScroller()
      }, o.prototype._zoomToViewWidth = function() {
        var e, t;
        e = this._getViewPortHeight(), t = this._getViewPortWidth();
        var n = this._getPageSize(this.pageIndex);
        if (n) {
          var o = n.height.valueInPixel,
            r = n.width.valueInPixel;
          t / r > e / o && (t -= a.getScrollbarWidth()), this.zoomFactor = t / r
        }
      }, o.prototype.refresh = function(t) {
        void 0 === t && (t = !0), n.prototype.refresh.call(this, t), this._isScrollerVisible ? e.addClass(this.hostElement, "virtual") : e.removeClass(this.hostElement, "virtual"), this._updateScroller()
      }, o.controlTemplate = '<div class="wj-pageview-pagescontainer" wj-part="pages-container" tabindex="0">   <div class="wj-pages-wrapper" wj-part="pages-wrapper"></div></div><div class="wj-pageview-vscroller" wj-part="vscroller" tabindex="-1"></div> ', o
    }(i);
    t._SinglePageView = u;
    var c = function(o) {
      function r(t) {
        var n = o.call(this, t) || this;
        return e.addClass(t, "wj-pageview-continuous"), n
      }
      return __extends(r, o), r.prototype._init = function() {
        var e = this;
        o.prototype._init.call(this), t._addEvent(this.hostElement, "click", function(t) {
          e.hostElement.focus()
        }), t._addEvent(this.hostElement, "scroll", function() {
          e._onPositionChanged(), clearTimeout(e._scrollingTimer), e._scrollingTimer = setTimeout(function() {
            e._ensurePageIndexPosition()
          }, 200)
        }), this.zoomFactorChanged.addHandler(function() {
          clearTimeout(e._zoomFactorTimer), e._zoomFactorTimer = setTimeout(function() {
            e._ensurePageIndexPosition()
          }, 200)
        })
      }, r.prototype.dispose = function() {
        clearTimeout(this._scrollingTimer), clearTimeout(this._zoomFactorTimer), this._disposeBodyStopSwipe && this._disposeBodyStopSwipe(), o.prototype.dispose.call(this)
      }, r.prototype._stopSwip = function() {
        this._swipeSpeedReducer && this._swipeSpeedReducer.stop()
      }, r.prototype._bindTouchEvents = function(e) {
        var n = this;
        o.prototype._bindTouchEvents.call(this, e), e.touchStart.addHandler(this._stopSwip, this), e.swipe.addHandler(function(o, r) {
          n._swipeSpeedReducer || (n._swipeSpeedReducer = new t._SpeedReducer), n._swipeSpeedReducer.start(r.speed.x, r.speed.y, function(t, o) {
            var r = e.hostElement.scrollLeft,
              i = e.hostElement.scrollTop;
            e.hostElement.scrollLeft -= t, e.hostElement.scrollTop -= o, r == e.hostElement.scrollLeft && i == e.hostElement.scrollTop && n._stopSwip()
          })
        });
        var r = new t._TouchManager(document.body, !1),
          i = this._stopSwip.bind(this);
        t._addEvent(document.body, "mousedown", i, !0), r.touchStart.addHandler(i), this._disposeBodyStopSwipe = function() {
          n._stopSwip(), t._removeEvent(document.body, "mousedown", i), r.touchStart.removeHandler(i), r.dispose(), n._disposeBodyStopSwipe = null
        }
      }, r.prototype._getAbovePageCount = function(e) {
        return this._hitTestPageIndex(e)
      }, r.prototype.refresh = function(e) {
        this._stopSwip(), o.prototype.refresh.call(this, e)
      }, r.prototype._hitTestPageIndex = function(e) {
        if (!this.pages) return this.pageIndex;
        for (var t = 0, n = 0; t < this.pages.length; t++)
          if (n += this._getPageSize(t).height.valueInPixel * this.zoomFactor + i._pageMargin, e < n) {
            if (n - e < 1) continue;
            break
          }
        return Math.min(t, this.pages.length - 1)
      }, r.prototype._guessPageIndex = function() {
        return this.pages && this.hostElement.scrollHeight - this.hostElement.clientHeight <= this.hostElement.scrollTop ? this.pages.length - 1 : this._hitTestPageIndex(this.hostElement.scrollTop)
      }, r.prototype._render = function(e) {
        var n = this.pages.length,
          o = e - r._preFetchPageCount,
          i = e + r._preFetchPageCount,
          a = [];
        o = o < 0 ? 0 : o, i = i > n - 1 ? n - 1 : i;
        for (var s = o; s <= i; s++) a.push(this._renderViewPage(this._pagesWrapper.querySelectorAll(".wj-view-page").item(s), s));
        return new t._CompositedPromise(a)
      }, r.prototype._moveToPagePosition = function(e) {
        this._stopSwip();
        for (var o = 0, r = 0, a = !e.pageBounds, s = e.pageBounds || {
          x: 0,
          y: 0,
          width: 0,
          height: 0
        }, u = a ? 0 : i._pageMargin, c = this.pages[this.pageIndex], l = 0; l < e.pageIndex; l++) o += this._getPageSize(l).height.valueInPixel * this.zoomFactor + i._pageMargin, this._borderBoxMode || (o += 2 * i._pageBorderWidth);
        var p = t._getTransformedPosition(s, c.size, a ? n.NoRotate : c.rotateAngle, this.zoomFactor);
        o += p.y + u, r += p.x + u, r += this._getPageViewOffsetLeft(e.pageIndex), this.hostElement.scrollTop = o, this.hostElement.scrollLeft = r
      }, r.prototype._pointInViewPanelClientArea = function(e, t) {
        return e >= 0 && t >= 0 && e < this.hostElement.clientWidth && t < this.hostElement.clientHeight
      }, r.prototype._panelViewPntToPageView = function(e, t) {
        var n = this.hostElement.scrollTop + t,
          o = 0;
        if (this.hostElement.scrollLeft > 0) o = this.hostElement.scrollLeft + e;
        else {
          var r = this.hostElement.getBoundingClientRect();
          o = e - (this._pagesWrapper.getBoundingClientRect().left - r.left) + i._pageMargin
        }
        var a = this._hitTestPageIndex(n);
        if (a < 0) return null;
        o -= this._getPageViewOffsetLeft(a);
        for (var s = 0; s < a; s++) n -= this._getPageSize(s).height.valueInPixel * this.zoomFactor + i._pageMargin, this._borderBoxMode || (n -= 2 * i._pageBorderWidth);
        return {
          x: o,
          y: n,
          pageIndex: a
        }
      }, r.prototype._reserveViewPage = function() {
        t._removeChildren(this._pagesWrapper);
        for (var e = 0; e < (this.pages || []).length; e++) {
          var n = this._addViewPage(),
            o = this._getPageSize(e);
          n.style.height = o.height.valueInPixel * this.zoomFactor + "px", n.style.width = o.width.valueInPixel * this.zoomFactor + "px"
        }
      }, r.prototype._updatePageViewTransform = function() {
        if (this.pages && this.pages.length) {
          var e;
          e = this._pagesWrapper.querySelectorAll(".wj-view-page");
          for (var t = 0; t < e.length; t++) this._setPageTransform(e.item(t), t)
        }
      }, r.prototype._zoomToViewWidth = function() {
        if (this.pages && 0 != this.pages.length) {
          var e, t;
          e = this._getViewPortHeight(), t = this._getViewPortWidth();
          for (var n = 0, o = 0, r = 0; r < this.pages.length; r++) {
            var i = this._getPageSize(r);
            i.width.valueInPixel > n && (n = i.width.valueInPixel), o += i.height.valueInPixel
          }
          t / n > e / o && (t -= a.getScrollbarWidth()), this.zoomFactor = t / n
        }
      }, r.prototype._ensurePageIndexPosition = function() {
        var e = this._guessPageIndex();
        this.pageIndex !== e && (this._render(e), this._updatePageIndex(e))
      }, r.prototype._getPageViewOffsetLeft = function(e) {
        var t = this._pagesWrapper.querySelectorAll(".wj-view-page").item(e);
        return t ? t.offsetLeft - this._pagesWrapper.offsetLeft : 0
      }, r._preFetchPageCount = 3, r
    }(i);
    t._ContinuousPageView = c
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = function(n) {
      function r(t) {
        var o = n.call(this, t) || this;
        o._idCounter = 0, o._tabPages = [], o._tabPageDic = {}, o.tabPageActived = new e.Event, o.tabPageVisibilityChanged = new e.Event, o.expanded = new e.Event, o.collapsed = new e.Event;
        var r = o.getTemplate();
        return o.applyTemplate("wj-control", r, {
          _headersContainer: "wj-headers",
          _contentsContainer: "wj-contents"
        }), o
      }
      return __extends(r, n), r.prototype.applyTemplate = function(n, o, r) {
        var i = this.hostElement;
        if (e.addClass(i, n), i.appendChild(t._toDOMs(o)), r)
          for (var a in r) {
            var s = r[a];
            if (this[a] = i.querySelector('[wj-part="' + s + '"]'), null == this[a] && i.getAttribute("wj-part") == s && (this[a] = o), null == this[a]) throw 'Missing template part: "' + s + '"'
          }
        return i
      }, Object.defineProperty(r.prototype, "tabPages", {
        get: function() {
          return this._tabPages
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.getTabPage = function(e) {
        return this._tabPageDic[e]
      }, r.prototype.getFirstShownTabPage = function(e) {
        var t;
        return this._tabPages.some(function(n) {
          return !n.isHidden && n !== e && (t = n, !0)
        }), t
      }, Object.defineProperty(r.prototype, "visibleTabPagesCount", {
        get: function() {
          var e = 0;
          return this._tabPages.forEach(function(t) {
            t.isHidden || e++
          }), e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "activedTabPage", {
        get: function() {
          var e;
          return this._tabPages.some(function(t) {
            return !!t.isActived && (e = t, !0)
          }), e
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.removePage = function(e) {
        var t;
        if (t = "string" == typeof e ? this.getTabPage(e) : e) {
          var n = t.id,
            o = this._tabPages.indexOf(t);
          if (!(o < 0)) {
            if (this._tabPages.splice(o, 1), this._tabPageDic[n] = void 0, !this.isCollapsed && t.isActived) {
              var r = this.getFirstShownTabPage();
              r ? this.active(r) : this.collapse()
            }
            this._headersContainer.removeChild(t.header), this._contentsContainer.removeChild(t.outContent)
          }
        }
      }, r.prototype.addPage = function(n, r, i) {
        var a = this,
          s = this._getNewTabPageId(),
          u = document.createElement("li"),
          c = '<div class="wj-tabpane"><div class="wj-tabtitle-wrapper"><h3 class="wj-tabtitle">' + n + '</h3><span class="wj-close">×</span></div><div class="wj-tabcontent-wrapper"><div class="wj-tabcontent-inner"></div></div></div>',
          l = t._toDOM(c),
          p = t._createSvgBtn(r);
        u.appendChild(p), i = null == i ? this._tabPages.length : i, (i = Math.min(Math.max(i, 0), this._tabPages.length)) >= this._tabPages.length ? (this._headersContainer.appendChild(u), this._contentsContainer.appendChild(l)) : (this._headersContainer.insertBefore(u, this._tabPages[i].header), this._contentsContainer.insertBefore(l, this._tabPages[i].outContent)), t._addEvent(l.querySelector(".wj-close"), "click", function() {
          a.collapse()
        }), t._addEvent(u.querySelector("a"), "click,keydown", function(t) {
          var n = a.getTabPage(s);
          n && ("keydown" === t.type && t.keyCode === e.Key.Enter || "click" === t.type) && a.active(n)
        });
        var h = new o(l, u, s);
        return i >= this._tabPages.length ? this._tabPages.push(h) : this._tabPages.splice(i, 0, h), this._tabPageDic[s] = h, this.isCollapsed || this.activedTabPage || this.active(h), h
      }, Object.defineProperty(r.prototype, "isCollapsed", {
        get: function() {
          return e.hasClass(this.hostElement, r._collapsedCss)
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.hide = function(n) {
        var o = "string" == typeof n ? this.getTabPage(n) : n;
        o && (e.hasClass(o.header, t._hiddenCss) || (e.addClass(o.header, t._hiddenCss), this.onTabPageVisibilityChanged(o), this.deactive(o)))
      }, r.prototype.show = function(n) {
        var o = "string" == typeof n ? this.getTabPage(n) : n;
        o && e.hasClass(o.header, t._hiddenCss) && (e.removeClass(o.header, t._hiddenCss), this.onTabPageVisibilityChanged(o), this.isCollapsed || this.activedTabPage || this.active(o))
      }, r.prototype.deactive = function(n) {
        var o = "string" == typeof n ? this.getTabPage(n) : n;
        if (o && o.isActived) {
          e.removeClass(o.outContent, r._activedCss), t._checkImageButton(o.header.querySelector("a"), !1);
          var i = this.getFirstShownTabPage(o);
          i ? this.active(i) : this.collapse()
        }
      }, r.prototype.active = function(n) {
        var o = "string" == typeof n ? this.getTabPage(n) : n;
        o && (this.expand(), o.isActived || (this._clearActiveStyles(), this.show(o), e.addClass(o.outContent, r._activedCss), t._checkImageButton(o.header.querySelector("a"), !0), this.onTabPageActived()))
      }, r.prototype.enable = function(e, t) {
        void 0 === t && (t = !0);
        var n = "string" == typeof e ? this.getTabPage(e) : e;
        n && n.enable(t)
      }, r.prototype.enableAll = function(e) {
        void 0 === e && (e = !0), this._tabPages.forEach(function(t) {
          t.enable(e)
        })
      }, r.prototype.onTabPageActived = function() {
        this.tabPageActived.raise(this, new e.EventArgs)
      }, r.prototype.onTabPageVisibilityChanged = function(e) {
        this.tabPageVisibilityChanged.raise(this, {
          tabPage: e
        })
      }, r.prototype.onExpanded = function() {
        this.expanded.raise(this, new e.EventArgs)
      }, r.prototype.onCollapsed = function() {
        this.collapsed.raise(this, new e.EventArgs)
      }, r.prototype.collapse = function() {
        this.isCollapsed || (this._clearActiveStyles(), e.addClass(this.hostElement, r._collapsedCss), this.onCollapsed())
      }, r.prototype.expand = function() {
        if (this.isCollapsed) {
          if (e.removeClass(this.hostElement, r._collapsedCss), !this.activedTabPage) {
            var t = this.getFirstShownTabPage();
            t && this.active(t)
          }
          this.onExpanded()
        }
      }, r.prototype.toggle = function() {
        this.isCollapsed ? this.expand() : this.collapse()
      }, r.prototype._clearActiveStyles = function() {
        this._tabPages.forEach(function(n) {
          e.removeClass(n.outContent, r._activedCss), t._checkImageButton(n.header.querySelector("a"), !1)
        })
      }, r.prototype._getNewTabPageId = function() {
        for (; this._tabPageDic[(this._idCounter++).toString()];);
        return this._idCounter.toString()
      }, r._activedCss = "active", r._collapsedCss = "collapsed", r.controlTemplate = '<ul class="wj-nav wj-btn-group" wj-part="wj-headers"></ul><div class="wj-tabcontent" wj-part="wj-contents"></div>', r
    }(e.Control);
    t._SideTabs = n;
    var o = function() {
      function o(e, t, n) {
        this._header = t, this._outContent = e, this._content = e.querySelector(".wj-tabcontent-inner"), this._id = n
      }
      return Object.defineProperty(o.prototype, "isActived", {
        get: function() {
          return e.hasClass(this.outContent, n._activedCss)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isHidden", {
        get: function() {
          return e.hasClass(this.header, t._hiddenCss)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "id", {
        get: function() {
          return this._id
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "header", {
        get: function() {
          return this._header
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "content", {
        get: function() {
          return this._content
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "outContent", {
        get: function() {
          return this._outContent
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.enable = function(t) {
        void 0 === t && (t = !0), e.enable(this._header, t), e.enable(this._content, t)
      }, o.prototype.format = function(e) {
        e(this)
      }, o
    }();
    t._TabPage = o
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = function(n) {
      function o(e, o, r) {
        var i = n.call(this, document.createElement("div"), r) || this;
        return i.owner = o, i.hostElement.style.display = "none", i.owner.appendChild(i.hostElement), i.showDropDownButton = !1, i.itemClicked.addHandler(i._onItemClicked, i), i.formatItem.addHandler(i._formatItem, i), i._viewer = e, i._bindMenuItems(), i.displayMemberPath = "title", i.selectedValuePath = "commandTag", i._viewer._viewerActionStatusChanged.addHandler(function(e, n) {
          var o = i.dropDown.querySelector("[" + t._commandTagAttr + '="' + n.action.actionType.toString() + '"]');
          i._updateActionStatusCore(o, n.action)
        }), i
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "viewer", {
        get: function() {
          return this._viewer
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._bindMenuItems = function() {
        this.itemsSource = this._initItems()
      }, o.prototype._initItems = function() {
        throw e.culture.Viewer.abstractMethodException
      }, o.prototype._internalFormatItem = function(e, n) {
        if (e && void 0 !== e.commandTag) {
          if (t._removeChildren(n), e.icon) {
            var o = document.createElement("span");
            o.appendChild(t._createSvgBtn(e.icon)), n.insertBefore(o, n.firstChild)
          }
          n.setAttribute(t._commandTagAttr, e.commandTag.toString()), this._updateActionStatus(n, e.commandTag)
        }
      }, o.prototype._formatItem = function(e, t) {
        this._internalFormatItem(this.itemsSource[t.index], t.item)
      }, o.prototype._onItemClicked = function(e) {
        this._viewer._executeAction(parseInt(e.selectedItem.commandTag))
      }, o.prototype._updateActionStatus = function(e, t) {
        this._updateActionStatusCore(e, {
          actionType: t,
          checked: this._viewer._actionIsChecked(t),
          disabled: this._viewer._actionIsDisabled(t),
          shown: this._viewer._actionIsShown(t)
        })
      }, o.prototype._updateActionStatusCore = function(e, n) {
        t._checkImageButton(e, n.checked), t._disableImageButton(e, n.disabled), t._showImageButton(e, n.shown)
      }, o.prototype._updateItemsStatus = function() {
        for (var e = this.dropDown.querySelectorAll("[" + t._commandTagAttr + "]"), n = 0; n < e.length; n++) {
          var o = e[n],
            r = o.getAttribute(t._commandTagAttr);
          null != r && this._updateActionStatus(o, parseInt(r))
        }
        t._checkSeparatorShown(this.dropDown)
      }, o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e), e && this._bindMenuItems(), this.isDroppedDown && this.showMenu()
      }, o.prototype.showMenu = function(t) {
        this.selectedIndex = -1, e.showPopup(this.dropDown, this.owner, t, !1, !1), this.dropDown.style.color = this._viewer.hostElement.style.color, e.addClass(this.dropDown, "wj-btn-group-vertical"), e.addClass(this.dropDown, "wj-viewer-menu"), this._updateItemsStatus(), this.dropDown.focus()
      }, o
    }(e.input.Menu);
    t._ViewerMenuBase = n;
    var o = function(n) {
      function o(e, t, o) {
        return n.call(this, e, t, o) || this
      }
      return __extends(o, n), o.prototype._initItems = function() {
        var n = [],
          o = e.culture.Viewer;
        return n.push({
          title: o.thumbnails,
          icon: t.icons.thumbnails,
          commandTag: t._ViewerActionType.ShowThumbnails
        }), n.push({
          title: o.outlines,
          icon: t.icons.outlines,
          commandTag: t._ViewerActionType.ShowOutlines
        }), n.push({
          title: o.exports,
          icon: t.icons.exports,
          commandTag: t._ViewerActionType.ShowExportsPanel
        }), n.push({
          title: t.ViewerBase._seperatorHtml
        }), n.push({
          title: o.portrait,
          icon: t.icons.portrait,
          commandTag: t._ViewerActionType.Portrait
        }), n.push({
          title: o.landscape,
          icon: t.icons.landscape,
          commandTag: t._ViewerActionType.Landscape
        }), n.push({
          title: o.pageSetup,
          icon: t.icons.pageSetup,
          commandTag: t._ViewerActionType.ShowPageSetupPanel
        }), n.push({
          title: t.ViewerBase._seperatorHtml
        }), n.push({
          title: o.showZoomBar,
          icon: t.icons.showZoomBar,
          commandTag: t._ViewerActionType.ShowZoomBar
        }), n.push({
          title: t.ViewerBase._seperatorHtml
        }), n.push({
          title: o.paginated,
          icon: t.icons.paginated,
          commandTag: t._ViewerActionType.TogglePaginated
        }), n.push({
          title: o.print,
          icon: t.icons.print,
          commandTag: t._ViewerActionType.Print
        }), n.push({
          title: t.ViewerBase._seperatorHtml
        }), n.push({
          title: o.backwardHistory,
          icon: t.icons.backwardHistory,
          commandTag: t._ViewerActionType.Backward
        }), n.push({
          title: o.forwardHistory,
          icon: t.icons.forwardHistory,
          commandTag: t._ViewerActionType.Forward
        }), n
      }, o
    }(n);
    t._HamburgerMenu = o;
    var r = function(n) {
      function o(e, t, o) {
        return n.call(this, e, t, o) || this
      }
      return __extends(o, n), o.prototype._initItems = function() {
        var n = [];
        return n.push({
          title: e.culture.Viewer.singleMode,
          icon: t.icons.singleView,
          commandTag: t._ViewerActionType.Single
        }), n.push({
          title: e.culture.Viewer.continuousMode,
          icon: t.icons.continuousView,
          commandTag: t._ViewerActionType.Continuous
        }), n.push({
          title: t.ViewerBase._seperatorHtml
        }), n.push({
          title: e.culture.Viewer.wholePage,
          icon: t.icons.fitWholePage,
          commandTag: t._ViewerActionType.FitWholePage
        }), n.push({
          title: e.culture.Viewer.pageWidth,
          icon: t.icons.fitPageWidth,
          commandTag: t._ViewerActionType.FitPageWidth
        }), n
      }, o
    }(n);
    t._ViewMenu = r;
    var i = function(n) {
      function o(e, t, o) {
        return n.call(this, e, t, o) || this
      }
      return __extends(o, n), o.prototype._initItems = function() {
        var n = [];
        return n.push({
          title: e.culture.Viewer.wholeWordMenuItem,
          commandTag: t._ViewerActionType.SearchMatchWholeWord
        }), n.push({
          title: e.culture.Viewer.matchCaseMenuItem,
          commandTag: t._ViewerActionType.SearchMatchCase
        }), n
      }, o.prototype._internalFormatItem = function(t, o) {
        if (n.prototype._internalFormatItem.call(this, t, o), t && void 0 !== t.commandTag) {
          var r = document.createElement("span");
          r.innerHTML = e.culture.Viewer.checkMark, e.addClass(r, "checkIcon"), o.insertBefore(r, o.firstChild)
        }
      }, o.prototype._updateActionStatus = function(t, o) {
        n.prototype._updateActionStatus.call(this, t, o), this.viewer._actionIsChecked(o) && e.addClass(t, "checked")
      }, o
    }(n);
    t._SearchOptionsMenu = i
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n;
    ! function(e) {
      e[e.SelectTool = 0] = "SelectTool", e[e.MoveTool = 1] = "MoveTool", e[e.RubberbandTool = 2] = "RubberbandTool", e[e.MagnifierTool = 3] = "MagnifierTool"
    }(n = t.MouseMode || (t.MouseMode = {}));
    var o;
    ! function(e) {
      e[e.Single = 0] = "Single", e[e.Continuous = 1] = "Continuous"
    }(o = t.ViewMode || (t.ViewMode = {}));
    var r;
    ! function(e) {
      e[e.Custom = 0] = "Custom", e[e.PageWidth = 1] = "PageWidth", e[e.WholePage = 2] = "WholePage"
    }(r = t.ZoomMode || (t.ZoomMode = {}));
    var i = function(e) {
      function t(t) {
        var n = e.call(this) || this;
        return n._a = t, n
      }
      return __extends(t, e), Object.defineProperty(t.prototype, "element", {
        get: function() {
          return this._a
        },
        enumerable: !0,
        configurable: !0
      }), t
    }(e.EventArgs);
    t._LinkClickedEventArgs = i;
    var a;
    ! function(e) {
      e[e.Bookmark = 0] = "Bookmark", e[e.Custom = 1] = "Custom"
    }(a = t._ActionKind || (t._ActionKind = {}));
    var s = function() {
      function t() {
        this._items = [{}], this._position = 0, this.statusChanged = new e.Event
      }
      return t.prototype._onStatusChanged = function() {
        this.statusChanged.raise(this, new e.EventArgs)
      }, Object.defineProperty(t.prototype, "current", {
        get: function() {
          return this._items[this._position]
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.clear = function() {
        this._items = [{}], this._position = 0, this._onStatusChanged()
      }, t.prototype.add = function() {
        this._items.splice(++this._position), this._items.push({}), this._onStatusChanged()
      }, t.prototype.forward = function() {
        if (!this.canForward()) return null;
        var e = this._items[++this._position];
        return this._onStatusChanged(), e
      }, t.prototype.backward = function() {
        if (!this.canBackward()) return null;
        var e = this._items[--this._position];
        return this._onStatusChanged(), e
      }, t.prototype.canForward = function() {
        return this._position < this._items.length - 1
      }, t.prototype.canBackward = function() {
        return this._position > 0
      }, t
    }();
    t._HistoryManager = s;
    var u = function(i) {
      function u(r, a) {
        var u = i.call(this, r, a, !0) || this;
        return u._pages = [], u._pageIndex = 0, u._mouseMode = n.SelectTool, u._viewMode = o.Single, u._needBind = !1, u._historyManager = new s, u._fullScreen = !1, u._miniToolbarPinnedTimer = null, u._autoHeightCalculated = !1, u._searchManager = new t._SearchManager, u._thresholdWidth = 767, u._historyMoving = !1, u._documentSourceChanged = new e.Event, u.pageIndexChanged = new e.Event, u.viewModeChanged = new e.Event, u.selectMouseModeChanged = new e.Event, u.mouseModeChanged = new e.Event, u.fullScreenChanged = new e.Event, u.zoomFactorChanged = new e.Event, u.queryLoadingData = new e.Event, u.beforeSendRequest = new e.Event, u._viewerActionStatusChanged = new e.Event, u._documentEventKey = (new Date).getTime().toString(), u._init(a), u
      }
      return __extends(u, i), u.prototype._getProductInfo = function() {
        return "QNI5,ViewerBase"
      }, Object.defineProperty(u.prototype, "serviceUrl", {
        get: function() {
          return this._serviceUrl
        },
        set: function(e) {
          e != this._serviceUrl && (this._serviceUrl = e, this._needBindDocumentSource(), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "filePath", {
        get: function() {
          return this._filePath
        },
        set: function(e) {
          e != this._filePath && (this._filePath = e, this._needBindDocumentSource(), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "requestHeaders", {
        get: function() {
          return this._requestHeaders
        },
        set: function(e) {
          this._requestHeaders = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "thresholdWidth", {
        get: function() {
          return this._thresholdWidth
        },
        set: function(e) {
          e != this._thresholdWidth && (this._thresholdWidth = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "_innerPaginated", {
        get: function() {
          return this._documentSource && !this._needBind ? this._documentSource.paginated : this._paginated
        },
        set: function(t) {
          this._documentSource && !this._needBind ? this._setPaginated(t) : this._paginated = null == t ? null : e.asBoolean(t), this._setViewerAction(l.TogglePaginated, !0)
        },
        enumerable: !0,
        configurable: !0
      }), u.prototype.reload = function() {
        this._needBindDocumentSource(), this.invalidate()
      }, u.prototype.refresh = function(t) {
        if (void 0 === t && (t = !0), i.prototype.refresh.call(this, t), this._needBind && (this._setDocumentSource(this._getSource()), this._needBind = !1), t) {
          var n = e.Control.getControl(this._toolbar);
          n && n.refresh();
          var o = e.Control.getControl(this._miniToolbar);
          o && o.refresh();
          var r = e.Control.getControl(this._mobileToolbar);
          r && r.refresh();
          var a = e.Control.getControl(this._zoomBar);
          a && a.refresh();
          var s = e.Control.getControl(this._searchBar);
          s && s.refresh(), this._hamburgerMenu && this._hamburgerMenu.refresh(), this._viewMenu && this._viewMenu.refresh(), this._searchOptionsMenu && this._searchOptionsMenu.refresh(), u._exportItems = null, this._updateExportTab(!0), this._globalize(), this._updateLayout()
        }
        this._resetMiniToolbarPosition(), this._resetToolbarWidth(), this._resetViewPanelContainerWidth(), this._autoHeightCalculated = !1
      }, u.prototype._updateLayout = function() {
        this._switchTemplate(this._isMobileTemplate())
      }, u.prototype._switchTemplate = function(t) {
        var n = this.hostElement.querySelector(".wj-viewer-outer"),
          o = e.Control.getControl(this._sidePanel),
          r = o.getTabPage(this._pageSetupPageId);
        t ? (e.addClass(n, "mobile"), o.show(r)) : (e.removeClass(n, "mobile"), o.hide(r))
      }, u.prototype._getSource = function() {
        return this.filePath ? new t._DocumentSource({
          serviceUrl: this._serviceUrl,
          filePath: this._filePath
        }, this) : null
      }, u.prototype._needBindDocumentSource = function() {
        this._needBind = !0
      }, u.prototype._supportsPageSettingActions = function() {
        return !1
      }, u.prototype._isMobileTemplate = function() {
        return this.thresholdWidth > this.hostElement.getBoundingClientRect().width
      }, u.prototype._init = function(e) {
        var t = this;
        this._createChildren(), this._autoCalculateHeight(), this._resetToolbarWidth(), this._resetViewPanelContainerWidth(), this._bindEvents(), this._initTools(), this.deferUpdate(function() {
          t.initialize(e)
        })
      }, u.prototype._initTools = function() {
        var e = this;
        this._rubberband = new d(document.createElement("div"), this._viewpanelContainer, this._pageView), this._rubberband.applied.addHandler(function(n, o) {
          var r = o.rect,
            i = e._pageView.hitTest(r.left, r.top),
            a = e._viewpanelContainer.getBoundingClientRect();
          r.width > r.height ? e._pageView.zoomFactor *= a.width / r.width : e._pageView.zoomFactor *= a.height / r.height, e._pageView.moveToPosition(t._getPositionByHitTestInfo(i))
        }), this._magnifier = new _(document.createElement("div"), this._viewpanelContainer, this._pageView)
      }, u.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gSearchTitle.textContent = t.search, this._gMatchCase.innerHTML = "&nbsp;&nbsp;&nbsp;" + t.matchCase, this._gWholeWord.innerHTML = "&nbsp;&nbsp;&nbsp;" + t.wholeWord, this._gSearchResults.textContent = t.searchResults, this._gThumbnailsTitle.textContent = t.thumbnails, this._gOutlinesTitle.textContent = t.outlines, this._gPageSetupTitle.textContent = t.pageSetup, this._gPageSetupApplyBtn.textContent = t.ok, this._gExportsPageTitle.textContent = t.exports, this._gExportsPageApplyBtn.textContent = t.exportOk, this._gExportFormatTitle.textContent = t.exportFormat
      }, u.prototype._autoCalculateHeight = function() {
        if (this._shouldAutoHeight()) {
          var e = this._viewpanelContainer.style.height;
          this._viewpanelContainer.style.height = "100%", this._viewerContainer.style.height = Math.max(this._viewpanelContainer.getBoundingClientRect().height, u._viewpanelContainerMinHeight) + "px", this._viewpanelContainer.style.height = e
        }
      }, u.prototype._bindEvents = function() {
        var n = this;
        t._addEvent(window, "unload", function() {
          n._documentSource && n._documentSource.dispose()
        }), t._addEvent(document, "mousemove", function(e) {
          if (n.fullScreen && n._miniToolbar) {
            var t = n._checkMiniToolbarVisible(e);
            null != n._miniToolbarPinnedTimer && t ? (clearTimeout(n._miniToolbarPinnedTimer), n._miniToolbarPinnedTimer = null, n._showMiniToolbar(t)) : null == n._miniToolbarPinnedTimer && n._showMiniToolbar(t)
          }
        }), t._addEvent(document, "keydown", function(t) {
          t.keyCode === e.Key.Escape && (n.fullScreen = !1)
        }), this._historyManager.statusChanged.addHandler(this._onHistoryManagerStatusUpdated, this), this._onHistoryManagerStatusUpdated(), this._pageView.pageIndexChanged.addHandler(function() {
          n._addHistory(!1, !0), n._updatePageIndex(n._pageView.pageIndex)
        }), this._pageView.zoomFactorChanged.addHandler(function(e, t) {
          n.zoomMode === r.Custom && n._addHistory(!1, !0, {
            zoomFactor: t.oldValue
          }), n.onZoomFactorChanged()
        }), this._pageView.zoomModeChanged.addHandler(function(e, t) {
          n._addHistory(!1, !0, {
            zoomMode: t.oldValue
          }), n._updateZoomModeActions()
        }), this._pageView.positionChanged.addHandler(function() {
          setTimeout(function() {
            n._historyManager.current.position = n._getCurrentPosition()
          }, u._historyTimeout)
        });
        var o = !1;
        this._searchManager.currentChanged.addHandler(function() {
          if (!o) {
            var e = n._searchManager.current;
            e && (o = !0, n._highlightPosition(e.pageIndex, e.boundsList), o = !1)
          }
        })
      }, u.prototype._checkMiniToolbarVisible = function(e) {
        var t = e.clientX,
          n = e.clientY,
          o = this._miniToolbar.getBoundingClientRect(),
          r = o.left - 60,
          i = o.right + 60,
          a = o.top - 60,
          s = o.bottom + 60;
        return t >= r && t <= i && n >= a && n <= s
      }, u.prototype._showMiniToolbar = function(e) {
        var t, n = parseFloat(getComputedStyle(this._miniToolbar, "").opacity),
          o = this._miniToolbar;
        t = e ? setInterval(function() {
          n >= .8 ? window.clearInterval(t) : (n += .01, o.style.opacity = n.toString())
        }, 1) : setInterval(function() {
          n < 0 ? window.clearInterval(t) : (n -= .01, o.style.opacity = n.toString())
        }, 1)
      }, u.prototype._goToBookmark = function(e) {
        var t = this;
        this._documentSource && e.data && this._documentSource.getBookmark(e.data).then(function(e) {
          e && t._scrollToPosition(e, !0)
        })
      }, u.prototype._executeCustomAction = function(e) {
        var n = this;
        if (this._documentSource && e.data) {
          this._initialPosition = {
            pageIndex: this._pageIndex,
            pageBounds: {
              x: 0,
              y: 0,
              width: 0,
              height: 0
            }
          }, this._resetDocument(), this._showViewPanelMessage(), this._setDocumentRendering();
          var o = this._documentSource;
          this._documentSource.executeCustomAction(e).then(function(e) {
            n._initialPosition = e || n._initialPosition, n._getStatusUtilCompleted(o)
          }).catch(function(e) {
            n._showViewPanelErrorMessage(t._getErrorMessage(e))
          })
        }
      }, u.prototype._getStatusUtilCompleted = function(e) {
        var t = this;
        !e || e.isLoadCompleted || e.isDisposed || e.getStatus().then(function(n) {
          t._documentSource === e && setTimeout(function() {
            return t._getStatusUtilCompleted(e)
          }, e._innerService.getPingTimeout())
        }).catch(function(e) {
          t._showViewPanelErrorMessage(e)
        })
      }, u.prototype._initChildren = function() {
        this._initPageView(), this._initToolbar(), this._initSidePanel(), this._initSplitter(), this._initFooter(), this._initSearchBar(), this._initMiniToolbar()
      }, u.prototype._initSearchBar = function() {
        new t._SearchBar(this._searchBar, this), this._showSearchBar(!1)
      }, u.prototype._showSearchBar = function(n) {
        var o = this.hostElement.querySelector(".wj-viewer-outer");
        n ? (e.removeClass(this._searchBar, t._hiddenCss), e.addClass(o, "with-searchbar")) : (e.addClass(this._searchBar, t._hiddenCss), e.removeClass(o, "with-searchbar"))
      }, u.prototype._initFooter = function() {
        var e = this;
        new t._ViewerZoomBar(this._zoomBar, this), t._addEvent(this._footer.querySelector(".wj-close"), "click", function() {
          e._showFooter(!1)
        })
      }, u.prototype._showFooter = function(n) {
        var o = this.hostElement.querySelector(".wj-viewer-outer");
        n ? (e.removeClass(this._footer, t._hiddenCss), e.addClass(o, "with-footer")) : (e.addClass(this._footer, t._hiddenCss), e.removeClass(o, "with-footer"))
      }, u.prototype._createChildren = function() {
        var e = this.getTemplate();
        this.applyTemplate("wj-viewer wj-control", e, {
          _viewpanelContainer: "viewpanel-container",
          _toolbar: "toolbar",
          _mobileToolbar: "mobile-toolbar",
          _miniToolbar: "mini-toolbar",
          _leftPanel: "viewer-left-panel",
          _sidePanel: "side-panel",
          _viewerContainer: "viewer-container",
          _splitter: "splitter",
          _footer: "viewer-footer",
          _zoomBar: "zoom-bar",
          _searchBar: "search-bar"
        }), this._initChildren()
      }, u.prototype._initPageView = function() {
        new t._CompositePageView(this._viewpanelContainer).viewMode = this.viewMode
      }, Object.defineProperty(u.prototype, "_pageView", {
        get: function() {
          return e.Control.getControl(this._viewpanelContainer)
        },
        enumerable: !0,
        configurable: !0
      }), u.prototype._initSplitter = function() {
        var e = this;
        t._addEvent(this._splitter, "click", function() {
          return e._toggleSplitter()
        })
      }, u.prototype._toggleSplitter = function(t) {
        var n = this._splitter.querySelector("span"),
          o = e.Control.getControl(this._sidePanel);
        if (!0 === t) {
          if (e.hasClass(n, "wj-glyph-right")) return
        } else if (!1 === t) {
          if (e.hasClass(n, "wj-glyph-left")) return
        } else t = e.hasClass(n, "wj-glyph-left");
        if (t) o.collapse(), n.className = "wj-glyph-right";
        else {
          if (0 === o.visibleTabPagesCount) return;
          n.className = "wj-glyph-left", o.expand()
        }
        this._resetViewPanelContainerWidth()
      }, u.prototype._resetMiniToolbarPosition = function() {
        if (this._miniToolbar) {
          var e = this.hostElement.getBoundingClientRect().width,
            t = this._miniToolbar.getBoundingClientRect().width;
          this._miniToolbar.style.left = (e - t) / 2 + "px"
        }
      }, u.prototype._resetToolbarWidth = function() {
        e.Control.getControl(this._toolbar).resetWidth()
      }, u.prototype._resetViewPanelContainerWidth = function() {
        !this._isMobileTemplate() && this.hostElement.getBoundingClientRect().width <= u._narrowWidthThreshold ? e.addClass(this.hostElement, u._narrowCss) : e.removeClass(this.hostElement, u._narrowCss);
        var t = this._splitter ? this._splitter.getBoundingClientRect().width : 0,
          n = this._leftPanel ? this._leftPanel.getBoundingClientRect().width : 0;
        this._viewpanelContainer.style.width = this._viewerContainer.getBoundingClientRect().width - t - n + "px", this._pageView.invalidate()
      }, u.prototype._shouldAutoHeight = function() {
        return ("100%" === this.hostElement.style.height || "auto" === this.hostElement.style.height) && !this.fullScreen
      }, u.prototype._initSidePanel = function() {
        var n = this,
          o = new t._SideTabs(this._sidePanel);
        o.collapse(), o.collapsed.addHandler(function() {
          n._toggleSplitter(!0)
        }), o.expanded.addHandler(function() {
          n._toggleSplitter(!1);
          var t = n._splitter ? n._splitter.getBoundingClientRect().width : 0;
          n._sidePanel.getBoundingClientRect().width + t > n._viewerContainer.getBoundingClientRect().width && e.addClass(n._sidePanel, "collapsed")
        }), o.tabPageVisibilityChanged.addHandler(function(e, t) {
          (!t.tabPage.isHidden && 1 == o.visibleTabPagesCount || t.tabPage.isHidden && 0 == o.visibleTabPagesCount) && n._resetViewPanelContainerWidth()
        }), this._initSidePanelThumbnails(), this._initSidePanelOutlines(), this._initSidePanelSearch(), this._initSidePanelExports(), this._initSidePanelPageSetup()
      }, u.prototype._clearPreHightLights = function() {
        this._pages.forEach(function(e) {
          var t;
          if (e.content) {
            t = e.content.querySelectorAll(".highlight");
            for (var n = 0; n < t.length; n++) t.item(n).parentNode.removeChild(t.item(n))
          }
        })
      }, u.prototype._highlightPosition = function(e, n) {
        var o, r = this,
          i = (this._pageIndex, this._pageView.scrollTop, this._pageView.scrollLeft, {
            pageIndex: e,
            pageBounds: n.length > 0 ? n[0] : null
          });
        this._scrollToPosition(i, !0).then(function(e) {
          r._clearPreHightLights(), r._pages[r.pageIndex].getContent().then(function(e) {
            o = e.querySelector("g");
            for (var r = 0; r < n.length; r++) {
              var i = document.createElementNS("http://www.w3.org/2000/svg", "rect");
              i.setAttributeNS(null, "x", t._twipToPixel(n[r].x).toString()), i.setAttributeNS(null, "y", t._twipToPixel(n[r].y).toString()), i.setAttributeNS(null, "height", t._twipToPixel(n[r].height).toString()), i.setAttributeNS(null, "width", t._twipToPixel(n[r].width).toString()), i.setAttributeNS(null, "class", "highlight"), o.appendChild(i)
            }
          })
        })
      }, u.prototype._scrollToPosition = function(e, t) {
        return !0 === t && this._addHistory(!0, !1), e.pageIndex = e.pageIndex || 0, this._pageView.moveToPosition(e)
      }, u.prototype._initSidePanelSearch = function() {
        var n = this,
          o = e.Control.getControl(this._sidePanel),
          r = o.addPage(e.culture.Viewer.search, t.icons.search);
        this._gSearchTitle = r.outContent.querySelector(".wj-tabtitle"), r.format(function(i) {
          var a = '<div class="wj-searchcontainer"><input class="wj-searchbox" wj-part="search-box" type="text"/><div class="wj-btn-group"><button class="wj-btn wj-btn-searchpre">' + t._createSvgBtn(t.icons.searchPrevious).innerHTML + '</button><button class="wj-btn wj-btn-searchnext">' + t._createSvgBtn(t.icons.searchNext).innerHTML + '</button></div></div><div class="wj-searchoption"><label><span wj-part="g-matchCase">&nbsp;&nbsp;&nbsp;' + e.culture.Viewer.matchCase + '</span><input type="checkbox" wj-part="match-case" /></label></div><div class="wj-searchoption"><label><span wj-part="g-wholeWord">&nbsp;&nbsp;&nbsp;' + e.culture.Viewer.wholeWord + '</span><input type="checkbox" wj-part="whole-word" /></label></div><h3 wj-part="g-searchResults" class="wj-searchresult">' + e.culture.Viewer.searchResults + "</h3>",
            s = t._toDOMs(a);
          n._gMatchCase = s.querySelector('[wj-part="g-matchCase"]'), n._gWholeWord = s.querySelector('[wj-part="g-wholeWord"]'), n._gSearchResults = s.querySelector('[wj-part="g-searchResults"]'), i.outContent.querySelector(".wj-tabtitle-wrapper").appendChild(s);
          var u = i.outContent.querySelectorAll('input[type="checkbox"]')[0],
            c = i.outContent.querySelectorAll('input[type="checkbox"]')[1],
            l = i.outContent.querySelector('input[type="text"]'),
            p = i.outContent.querySelector(".wj-btn-searchpre"),
            h = i.outContent.querySelector(".wj-btn-searchnext");
          e.addClass(i.content.parentElement, "search-wrapper"), e.addClass(i.content, "wj-searchresultlist");
          var d = new e.input.ListBox(i.content),
            _ = !1,
            g = !1;
          d.formatItem.addHandler(function(t, n) {
            var o = n.item,
              r = n.data,
              i = document.createElement("div"),
              a = document.createElement("div");
            o.innerHTML = "", a.innerHTML = r.nearText, a.className = "wj-search-text", i.innerHTML = "Page " + (r.pageIndex + 1), i.className = "wj-search-page", e.addClass(o, "wj-search-item"), o.setAttribute("tabIndex", "-1"), o.appendChild(a), o.appendChild(i)
          }), d.selectedIndexChanged.addHandler(function() {
            return n._searchManager.currentIndex = d.selectedIndex
          });
          var m = function(e) {
            void 0 === e && (e = !0);
            for (var t = r.outContent.querySelectorAll("input"), n = 0; n < t.length; n++) t.item(n).disabled = !e
          };
          n._searchManager.searchStarted.addHandler(function() {
            m(!1)
          }), n._searchManager.searchCompleted.addHandler(function() {
            _ = !0, n._clearPreHightLights(), d.itemsSource = n._searchManager.searchResult, _ = !1, m(!0)
          }), n._searchManager.currentChanged.addHandler(function() {
            _ || g || n._searchManager.current && (g = !0, d.selectedIndex = n._searchManager.currentIndex, g = !1)
          });
          var f = function() {
            n._searchManager.clear(), d.itemsSource = null, u.checked = !1, c.checked = !1, l.value = "", n._documentSource && n._documentSource.features && (!n._documentSource.paginated || n._documentSource.features.textSearchInPaginatedMode) ? o.show(i) : o.hide(i)
          };
          n._documentSourceChanged.addHandler(function() {
            n._documentSource && t._addWjHandler(n._documentEventKey, n._documentSource.loadCompleted, f), f()
          }), t._addEvent(u, "click", function() {
            n._searchManager.matchCase = u.checked
          }), t._addEvent(c, "click", function() {
            n._searchManager.wholeWord = c.checked
          }), t._addEvent(l, "input", function() {
            n._searchManager.text = l.value
          }), t._addEvent(l, "keyup", function(t) {
            var o = t || window.event;
            o.keyCode === e.Key.Enter && n._searchManager.search(o.shiftKey)
          }), t._addEvent(h, "click", function() {
            return n._searchManager.search()
          }), t._addEvent(p, "click", function() {
            return n._searchManager.search(!0)
          }), t._addEvent(i.header, "keydown", function(t) {
            var o, r = n._toolbar;
            t.keyCode === e.Key.Tab && (o = r.querySelector("[tabIndex=0]") || r.querySelector('input:not([type="hidden"])') || r) && o.focus && (o.focus(), t.preventDefault())
          })
        })
      }, u.prototype._initSidePanelOutlines = function() {
        var n = this,
          o = e.Control.getControl(this._sidePanel),
          r = o.addPage(e.culture.Viewer.outlines, t.icons.outlines);
        this._gOutlinesTitle = r.outContent.querySelector(".wj-tabtitle"), this._outlinesPageId = r.id, r.format(function(r) {
          e.addClass(r.content, "wj-outlines-tree");
          var i = new e.grid.FlexGrid(r.content);
          i.initialize({
            autoGenerateColumns: !1,
            columns: [{
              binding: "caption",
              width: "*"
            }],
            isReadOnly: !0,
            childItemsPath: "children",
            allowResizing: e.grid.AllowResizing.None,
            headersVisibility: e.grid.HeadersVisibility.None
          }), i.itemFormatter = function(e, t, n, o) {
            var r;
            r = o.firstElementChild ? o.firstElementChild.outerHTML : "&nbsp;&nbsp;&nbsp;&nbsp;";
            var i = e.rows[t].dataItem;
            o.innerHTML = r + "<a>" + i.caption + "</a>"
          };
          var a = !0;
          i.selectionChanged.addHandler(function(t, r) {
            if (!a) {
              var i = r.panel.rows[r.row];
              if (i) {
                var s = i.dataItem;
                if (s.position) n._scrollToPosition(s.position, !0);
                else if (s.target) {
                  if (!n._documentSource) return;
                  n._documentSource.getBookmark(s.target).then(function(o) {
                    s.position = o, t.getSelectedState(r.row, r.col) != e.grid.SelectedState.None && n._scrollToPosition(o, !0)
                  }, function(e) {})
                }
                n._isMobileTemplate() && o.collapse()
              }
            }
          });
          var s = !1,
            u = function() {
              s || o.isCollapsed || !r.isActived || r.isHidden || (i.refresh(), s = !0)
            },
            c = function() {
              if (!n._documentSource) return i.itemsSource = null, void o.hide(r);
              t._addWjHandler(n._documentEventKey, n._documentSource.loadCompleted, function() {
                if (!n._documentSource.hasOutlines) return i.itemsSource = null, void o.hide(r);
                n._documentSource.getOutlines().then(function(e) {
                  s = !1, i.itemsSource = e, o.show(r), u(), a = !1
                })
              })
            };
          n._documentSourceChanged.addHandler(c), o.expanded.addHandler(u), o.tabPageActived.addHandler(u), c()
        })
      }, u.prototype._initSidePanelThumbnails = function() {
        var n = this,
          o = e.Control.getControl(this._sidePanel),
          r = o.addPage(e.culture.Viewer.thumbnails, t.icons.thumbnails);
        this._gThumbnailsTitle = r.outContent.querySelector(".wj-tabtitle"), this._thumbnailsPageId = r.id, r.format(function(r) {
          e.addClass(r.content, "wj-thumbnaillist");
          var i = new e.input.ListBox(r.content),
            a = null,
            s = !1;
          i.formatItem.addHandler(function(o, r) {
            var i = r.item,
              a = r.data;
            if (i.innerHTML = "", n._pageView.pages) {
              var s = document.createElement("div"),
                l = t._toDOM('<svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xml: space = "preserve" ></svg>'),
                p = document.createElementNS("http://www.w3.org/2000/svg", "g"),
                h = document.createElementNS("http://www.w3.org/2000/svg", "image"),
                d = document.createElement("div"),
                _ = n._pageView.pages[r.index],
                g = _.size.height.valueInPixel * u._thumbnailWidth / _.size.width.valueInPixel;
              e.addClass(i, "wj-thumbnail-item"), a().then(function(e) {
                h.setAttributeNS("http://www.w3.org/1999/xlink", "href", e)
              }), h.setAttribute("x", "0"), h.setAttribute("y", "0"), h.setAttribute("width", u._thumbnailWidth.toString()), h.setAttribute("height", g.toString()), e.addClass(s, "wj-pagethumbnail"), s.setAttribute("tabIndex", "-1"), p.appendChild(h), l.appendChild(p), s.appendChild(l), i.appendChild(s), c(l, _), d.className = "page-index", d.innerHTML = (r.index + 1).toString(), i.appendChild(d)
            }
          }), i.selectedIndexChanged.addHandler(function() {
            s || i.selectedIndex < 0 || i.selectedIndex == n._pageIndex || (n.moveToPage(i.selectedIndex), n._isMobileTemplate() && o.collapse())
          }), n.pageIndexChanged.addHandler(function() {
            return i.selectedIndex = n._pageIndex
          });
          var c = function(e, n) {
              var o = u._thumbnailWidth,
                r = n.size.height.valueInPixel * o / n.size.width.valueInPixel,
                i = e.parentNode;
              switch (t._transformSvg(e, o, r, 1, n.rotateAngle), n.rotateAngle) {
                case t._RotateAngle.Rotation90:
                case t._RotateAngle.Rotation270:
                  var a = r;
                  r = o, o = a
              }
              i.style.width = o + "px", i.style.height = r + "px", e.setAttribute("width", o.toString()), e.setAttribute("height", r.toString())
            },
            l = function() {
              if (!n._documentSource || !n._documentSource.isLoadCompleted || !n._documentSource.pageCount) return null;
              for (var e = [], o = 0; o < n._documentSource.pageCount; o++) ! function(o) {
                e.push(function() {
                  var e = new t._Promise;
                  return n._documentSource.getExportedUrl({
                    format: "png",
                    resolution: 50,
                    outputRange: o + 1
                  }).then(function(t) {
                    n._documentSource && n._documentSource._innerService && !n._documentSource.isDisposed || e.resolve(null), n._documentSource._innerService.downloadDataUrl(t).then(function(t) {
                      e.resolve(t)
                    }, function(t) {
                      return e.resolve(null)
                    })
                  }, function(t) {
                    return e.resolve(null)
                  }), e
                })
              }(o);
              return e
            },
            p = function() {
              !o.isCollapsed && r.isActived && (a = a || l(), r.isActived && i.itemsSource !== a && i.deferUpdate(function() {
                s = !0, i.itemsSource = a, i.selectedIndex = n._pageIndex, s = !1
              }))
            },
            h = function() {
              if (!n._documentSource || !n._documentSource.paginated || !n._documentSource.hasThumbnails) return o.hide(r), void(i.itemsSource = null);
              o.show(r), a = null, p()
            },
            d = function() {
              if (!n._documentSource || !n._documentSource.hasThumbnails) return o.hide(r), void(i.itemsSource = null);
              t._addWjHandler(n._documentEventKey, n._documentSource.loadCompleted, h), t._addWjHandler(n._documentEventKey, n._documentSource.pageCountChanged, h), t._addWjHandler(n._documentEventKey, n._documentSource.pageSettingsChanged, h), h()
            };
          n._documentSourceChanged.addHandler(d), d(), o.expanded.addHandler(function() {
            o.activedTabPage.id === n._thumbnailsPageId && p()
          }), o.tabPageActived.addHandler(function() {
            o.activedTabPage.id === n._thumbnailsPageId && p()
          }), p(), n._pageView.rotateAngleChanged.addHandler(function() {
            for (var e = i.hostElement.querySelectorAll("svg"), t = 0; t < e.length; t++) c(e.item(t), n._pageView.pages[t])
          })
        })
      }, u.prototype._initSidePanelExports = function() {
        var n = this,
          o = e.Control.getControl(this._sidePanel),
          r = o.addPage(e.culture.Viewer.exports, t.icons.exports);
        this._gExportsPageTitle = r.outContent.querySelector(".wj-tabtitle"), this._exportsPageId = r.id, r.format(function(r) {
          var i = '<div class="wj-exportcontainer"><label wj-part="g-wj-exportformat">' + e.culture.Viewer.exportFormat + '</label><div class="wj-exportformats"></div></div>',
            a = t._toDOMs(i);
          r.outContent.querySelector(".wj-tabtitle-wrapper").appendChild(a), n._gExportFormatTitle = r.outContent.querySelector('[wj-part="g-wj-exportformat"]');
          var s = r.outContent.querySelector(".wj-exportformats");
          new e.input.ComboBox(s), e.addClass(r.content.parentElement, "wj-exportformats-wrapper");
          var u = document.createElement("div"),
            c = new t._ExportOptionEditor(u);
          r.content.appendChild(u);
          var l = '<div class="wj-exportformats-footer"><a wj-part="btn-apply" class="wj-btn wj-applybutton" tabindex="-1">' + e.culture.Viewer.exportOk + "</a></div>",
            p = t._toDOMs(l);
          r.content.appendChild(p);
          var h = r.content.querySelector('[wj-part="btn-apply"]');
          n._gExportsPageApplyBtn = h, t._addEvent(h, "click", function() {
            n._documentSource.export(c.options), o.collapse()
          })
        });
        var i = !0;
        this._documentSourceChanged.addHandler(function() {
          i = !0, n._documentSource && t._addWjHandler(n._documentEventKey, n._documentSource.loadCompleted, function() {
            i && n._ensureExportFormatsLoaded().then(function() {
              n._updateExportTab(), i = null == n._exportFormats
            })
          })
        }), o.tabPageActived.addHandler(function() {
          i && o.activedTabPage.id === n._exportsPageId && n._ensureExportFormatsLoaded().then(function() {
            n._updateExportTab(), i = null == n._exportFormats
          })
        })
      }, u.prototype._ensureExportFormatsLoaded = function() {
        var e = this;
        if (!this._exportFormats && this._documentSource && !this._documentSource.isDisposed && this._documentSource.isInstanceCreated) return this._documentSource.getSupportedExportDescriptions().then(function(t) {
          e._exportFormats = t, e._setViewerAction(l.ShowExportsPanel)
        });
        var n = new t._Promise;
        return n.resolve(), n
      }, u.prototype._updateExportTab = function(n) {
        var o = this;
        if (this._exportFormats) {
          var r = e.Control.getControl(this._sidePanel).getTabPage(this._exportsPageId),
            i = e.Control.getControl(r.outContent.querySelector(".wj-exportformats")),
            a = e.Control.getControl(r.content.firstElementChild);
          if (n) a.refresh(), i.itemsSource && i.itemsSource.forEach(function(e) {
            e.name = o._exportItemDescriptions[e.format].name
          }), i.refresh();
          else {
            var s = [];
            this._exportFormats.forEach(function(e) {
              var n = o._exportItemDescriptions[e.format];
              t.isIOS() && !n.supportIOS || (e.name = n.name, s.push(e))
            }), i.selectedIndexChanged.addHandler(function() {
              a.exportDescription = i.selectedItem
            }), i.itemsSource = s, i.displayMemberPath = "name", i.selectedValuePath = "format", i.selectedIndex = -1
          }
        }
      }, u.prototype._initSidePanelPageSetup = function() {
        var n = this,
          o = e.Control.getControl(this._sidePanel),
          r = o.addPage(e.culture.Viewer.pageSetup, t.icons.pageSetup);
        this._gPageSetupTitle = r.outContent.querySelector(".wj-tabtitle"), this._pageSetupPageId = r.id, r.format(function(r) {
          var i = document.createElement("div"),
            a = new t._PageSetupEditor(i);
          r.content.appendChild(i), e.addClass(i, "wj-pagesetupcontainer");
          var s = '<div class="wj-pagesetup-footer"><a wj-part="btn-apply" class="wj-btn wj-applybutton" tabindex="-1">' + e.culture.Viewer.ok + "</a></div>",
            u = t._toDOMs(s);
          r.content.appendChild(u);
          var c = r.content.querySelector('[wj-part="btn-apply"]');
          n._gPageSetupApplyBtn = c, t._addEvent(c, "click", function() {
            n._setPageSettings(a.pageSettings), o.collapse()
          });
          var l = function() {
              a.pageSettings = n._documentSource.pageSettings
            },
            p = function() {
              n._documentSource && (t._addWjHandler(n._documentEventKey, n._documentSource.pageSettingsChanged, l), l())
            };
          n._documentSourceChanged.addHandler(p), p()
        })
      }, u.prototype._executeAction = function(i) {
        if (!this._actionIsDisabled(i)) switch (i) {
          case l.TogglePaginated:
            this._innerPaginated = !this._innerPaginated;
            break;
          case l.Print:
            this._documentSource && this._documentSource.print(this._pages.map(function(e) {
              return e.rotateAngle
            }));
            break;
          case l.ShowExportsPanel:
            e.Control.getControl(this._sidePanel).active(this._exportsPageId);
            break;
          case l.Portrait:
            this._setPageLandscape(!1);
            break;
          case l.Landscape:
            this._setPageLandscape(!0);
            break;
          case l.ShowPageSetupDialog:
            this.showPageSetupDialog();
            break;
          case l.FirstPage:
            this.moveToPage(0);
            break;
          case l.LastPage:
            this._moveToLastPage();
            break;
          case l.PrePage:
            this.moveToPage(this._pageIndex - 1);
            break;
          case l.NextPage:
            this.moveToPage(this._pageIndex + 1);
            break;
          case l.Backward:
            this._moveBackwardHistory();
            break;
          case l.Forward:
            this._moveForwardHistory();
            break;
          case l.SelectTool:
            this.mouseMode = n.SelectTool;
            break;
          case l.MoveTool:
            this.mouseMode = n.MoveTool;
            break;
          case l.Continuous:
            this._addHistory(!1, !0), this.viewMode = o.Continuous;
            break;
          case l.Single:
            this._addHistory(!1, !0), this.viewMode = o.Single;
            break;
          case l.FitPageWidth:
            this.zoomMode = r.PageWidth;
            break;
          case l.FitWholePage:
            this.zoomMode = r.WholePage;
            break;
          case l.ZoomOut:
            this._zoomBtnClicked(!1, u._defaultZoomValues);
            break;
          case l.ZoomIn:
            this._zoomBtnClicked(!0, u._defaultZoomValues);
            break;
          case l.ToggleFullScreen:
            this.fullScreen = !this.fullScreen;
            break;
          case l.ShowHamburgerMenu:
            this._hamburgerMenu.showMenu();
            break;
          case l.ShowViewMenu:
            this._viewMenu.showMenu();
            break;
          case l.ShowSearchBar:
            this._showSearchBar(e.hasClass(this._searchBar, t._hiddenCss)), this._setViewerAction(l.ShowSearchBar);
            break;
          case l.ShowThumbnails:
            e.Control.getControl(this._sidePanel).active(this._thumbnailsPageId);
            break;
          case l.ShowOutlines:
            e.Control.getControl(this._sidePanel).active(this._outlinesPageId);
            break;
          case l.ShowPageSetupPanel:
            e.Control.getControl(this._sidePanel).active(this._pageSetupPageId);
            break;
          case l.ShowZoomBar:
            this._showFooter(!0);
            break;
          case l.SearchPrev:
            this._searchManager.search(!0);
            break;
          case l.SearchNext:
            this._searchManager.search();
            break;
          case l.ShowSearchOptions:
            this._searchOptionsMenu.showMenu(!0);
            break;
          case l.SearchMatchCase:
            this._searchManager.matchCase = !this._searchManager.matchCase;
            break;
          case l.SearchMatchWholeWord:
            this._searchManager.wholeWord = !this._searchManager.wholeWord;
            break;
          case l.RubberbandTool:
            this.mouseMode = n.RubberbandTool;
            break;
          case l.MagnifierTool:
            this.mouseMode = n.MagnifierTool;
            break;
          case l.RotateDocument:
            this._rotateDocument();
            break;
          case l.RotatePage:
            this._rotatePage()
        }
      }, u.prototype._initSearchOptionsMenu = function(e) {
        this._searchOptionsMenu = new t._SearchOptionsMenu(this, e)
      }, u.prototype._initHamburgerMenu = function(e) {
        this._hamburgerMenu = new t._HamburgerMenu(this, e)
      }, u.prototype._initViewMenu = function(e) {
        this._viewMenu = new t._ViewMenu(this, e)
      }, u.prototype._initToolbar = function() {
        new t._ViewerToolbar(this._toolbar, this), new t._ViewerMobileToolbar(this._mobileToolbar, this)
      }, u.prototype._clearExportFormats = function() {
        this._exportFormats = null
      }, Object.defineProperty(u.prototype, "_exportItemDescriptions", {
        get: function() {
          return u._exportItems || (u._exportItems = {
            pdf: {
              name: e.culture.Viewer.pdfExportName,
              supportIOS: !0
            },
            doc: {
              name: e.culture.Viewer.docExportName,
              supportIOS: !1
            },
            docx: {
              name: e.culture.Viewer.docxExportName,
              supportIOS: !1
            },
            rtf: {
              name: e.culture.Viewer.rtfExportName,
              supportIOS: !1
            },
            xlsx: {
              name: e.culture.Viewer.xlsxExportName,
              supportIOS: !0
            },
            xls: {
              name: e.culture.Viewer.xlsExportName,
              supportIOS: !0
            },
            mhtml: {
              name: e.culture.Viewer.mhtmlExportName,
              supportIOS: !0
            },
            html: {
              name: e.culture.Viewer.htmlExportName,
              supportIOS: !0
            },
            zip: {
              name: e.culture.Viewer.metafileExportName,
              supportIOS: !1
            },
            csv: {
              name: e.culture.Viewer.csvExportName,
              supportIOS: !0
            },
            tiff: {
              name: e.culture.Viewer.tiffExportName,
              supportIOS: !0
            },
            bmp: {
              name: e.culture.Viewer.bmpExportName,
              supportIOS: !1
            },
            emf: {
              name: e.culture.Viewer.emfExportName,
              supportIOS: !1
            },
            gif: {
              name: e.culture.Viewer.gifExportName,
              supportIOS: !1
            },
            jpeg: {
              name: e.culture.Viewer.jpegExportName,
              suportIOS: !1
            },
            jpg: {
              name: e.culture.Viewer.jpegExportName,
              supportIOS: !1
            },
            png: {
              name: e.culture.Viewer.pngExportName,
              supportIOS: !1
            },
            xml: {
              name: "XML document",
              supportIOS: !1
            }
          }), u._exportItems
        },
        enumerable: !0,
        configurable: !0
      }), u.prototype._actionIsChecked = function(i) {
        switch (i) {
          case l.TogglePaginated:
            return !0 === this._innerPaginated;
          case l.Landscape:
            return !(!this._documentSource || !this._documentSource.pageSettings) && this._documentSource.pageSettings.landscape;
          case l.Portrait:
            return !(!this._documentSource || !this._documentSource.pageSettings) && !this._documentSource.pageSettings.landscape;
          case l.SelectTool:
            return this.mouseMode === n.SelectTool;
          case l.MoveTool:
            return this.mouseMode === n.MoveTool;
          case l.RubberbandTool:
            return this.mouseMode === n.RubberbandTool;
          case l.MagnifierTool:
            return this.mouseMode === n.MagnifierTool;
          case l.Continuous:
            return this.viewMode == o.Continuous;
          case l.Single:
            return this.viewMode == o.Single;
          case l.ToggleFullScreen:
            return this.fullScreen;
          case l.FitPageWidth:
            return this.zoomMode == r.PageWidth;
          case l.FitWholePage:
            return this.zoomMode == r.WholePage;
          case l.SearchMatchCase:
            return this._searchManager.matchCase;
          case l.SearchMatchWholeWord:
            return this._searchManager.wholeWord;
          case l.ShowSearchBar:
            return !e.hasClass(this._searchBar, t._hiddenCss)
        }
        return !1
      }, u.prototype._isDocumentSourceLoaded = function() {
        return this._documentSource && this._documentSource.isLoadCompleted
      }, u.prototype._actionIsDisabled = function(e) {
        if (!(this._isDocumentSourceLoaded() && this._documentSource.pageCount > 0)) return !0;
        switch (e) {
          case l.TogglePaginated:
            return null == this._innerPaginated;
          case l.ShowExportsPanel:
            return !this._exportFormats || 0 === this._exportFormats.length;
          case l.Landscape:
          case l.Portrait:
          case l.ShowPageSetupDialog:
          case l.ShowPageSetupPanel:
            return !this._documentSource || !this._documentSource.pageSettings || !this._documentSource.paginated;
          case l.FirstPage:
          case l.PrePage:
            return this._pageIndex <= 0;
          case l.LastPage:
          case l.NextPage:
            return this._pageIndex >= this._documentSource.pageCount - 1;
          case l.Backward:
            return !this._historyManager.canBackward();
          case l.Forward:
            return !this._historyManager.canForward();
          case l.Continuous:
          case l.Single:
            return !this._documentSource || !this._documentSource.paginated;
          case l.ZoomOut:
            return this.zoomFactor <= u._defaultZoomValues[0].value;
          case l.ZoomIn:
            var t = u._defaultZoomValues;
            return this.zoomFactor >= t[t.length - 1].value
        }
        return !1
      }, u.prototype._actionIsShown = function(t) {
        var n = this._documentSource ? this._documentSource.features : null;
        switch (t) {
          case l.TogglePaginated:
            return n && n.paginated && n.nonPaginated;
          case l.Landscape:
          case l.Portrait:
          case l.ShowPageSetupDialog:
          case l.ShowPageSetupPanel:
            return n ? n.pageSettings : this._supportsPageSettingActions();
          case l.SelectTool:
          case l.MoveTool:
          case l.MagnifierTool:
          case l.RubberbandTool:
            return !e.isMobile();
          case l.ShowSearchBar:
            return n && (!this._documentSource.paginated || n.textSearchInPaginatedMode);
          case l.ShowOutlines:
            return this._documentSource && this._documentSource.hasOutlines;
          case l.ShowThumbnails:
            return this._documentSource && this._documentSource.hasThumbnails
        }
        return !0
      }, u.prototype._onViewerActionStatusChanged = function(e) {
        this._viewerActionStatusChanged.raise(this, e)
      }, u.prototype._setViewerAction = function(e, t, n, o) {
        var r = {
          actionType: e,
          disabled: t || this._actionIsDisabled(e),
          checked: n || this._actionIsChecked(e),
          shown: o || this._actionIsShown(e)
        };
        this._onViewerActionStatusChanged({
          action: r
        })
      }, u.prototype._updateViewerActions = function() {
        this._updatePageSettingsActions(), this._updateViewModeActions(), this._updateMouseModeActions(), this._setViewerAction(l.ShowExportsPanel)
      }, u.prototype._updateViewModeActions = function() {
        this._setViewerAction(l.Continuous), this._setViewerAction(l.Single)
      }, u.prototype._updatePageSettingsActions = function() {
        this._setViewerAction(l.TogglePaginated), this._setViewerAction(l.Landscape), this._setViewerAction(l.Portrait), this._setViewerAction(l.ShowPageSetupDialog)
      }, u.prototype._updateMouseModeActions = function() {
        this._setViewerAction(l.SelectTool), this._setViewerAction(l.MoveTool), this._setViewerAction(l.MagnifierTool), this._setViewerAction(l.RubberbandTool)
      }, u.prototype._updateZoomModeActions = function() {
        this._setViewerAction(l.FitPageWidth), this._setViewerAction(l.FitWholePage)
      }, u.prototype._updateZoomFactorActions = function() {
        this._setViewerAction(l.ZoomOut), this._setViewerAction(l.ZoomIn)
      }, u.prototype._onPageSettingsUpdated = function() {
        this._updatePageSettingsActions(), this._updateViewModeActions(), this._resetToolbarWidth()
      }, u.prototype._onPageCountUpdated = function() {
        this._updatePageNavActions(), this._resetToolbarWidth()
      }, u.prototype._updatePageNavActions = function() {
        this._setViewerAction(l.FirstPage), this._setViewerAction(l.LastPage), this._setViewerAction(l.PrePage), this._setViewerAction(l.NextPage)
      }, u.prototype._onHistoryManagerStatusUpdated = function() {
        this._setViewerAction(l.Backward), this._setViewerAction(l.Forward)
      }, u.prototype._updateUI = function() {
        var t = this;
        Object.keys(l).forEach(function(e) {
          isNaN(e) || t._setViewerAction(l[l[e]])
        });
        var n = e.Control.getControl(this._sidePanel);
        n && n.enableAll(this._isDocumentSourceLoaded())
      }, u.prototype._updateViewContainerCursor = function() {
        this.mouseMode === n.MoveTool ? e.hasClass(this._viewpanelContainer, "move") || e.addClass(this._viewpanelContainer, "move") : e.hasClass(this._viewpanelContainer, "move") && e.removeClass(this._viewpanelContainer, "move")
      }, u.prototype._updateFullScreenStyle = function() {
        var t = document.body;
        this.fullScreen ? (this._bodyOriginScrollLeft = document.documentElement && document.documentElement.scrollLeft || t.scrollLeft, this._bodyOriginScrollTop = document.documentElement && document.documentElement.scrollTop || t.scrollTop, e.addClass(this.hostElement, "full-screen"), e.addClass(t, "full-screen"), this._hostOriginWidth = this.hostElement.style.width, this._hostOriginHeight = this.hostElement.style.height, this.hostElement.style.width = "100%", this.hostElement.style.height = "100%", window.scrollTo(0, 0)) : (e.removeClass(this.hostElement, "full-screen"), e.removeClass(t, "full-screen"), this.hostElement.style.width = this._hostOriginWidth, this.hostElement.style.height = this._hostOriginHeight, e.isNumber(this._bodyOriginScrollLeft) && (document.documentElement && (document.documentElement.scrollLeft = this._bodyOriginScrollLeft), t.scrollLeft = this._bodyOriginScrollLeft), e.isNumber(this._bodyOriginScrollTop) && (document.documentElement && (document.documentElement.scrollTop = this._bodyOriginScrollTop), t.scrollTop = this._bodyOriginScrollTop)), this.refresh()
      }, u.prototype.showPageSetupDialog = function() {
        this._pageSetupDialog || this._createPageSetupDialog(), this._pageSetupDialog.showWithValue(this._documentSource.pageSettings)
      }, u.prototype._createPageSetupDialog = function() {
        var e = this,
          t = document.createElement("div");
        t.style.display = "none", e.hostElement.appendChild(t), e._pageSetupDialog = new c(t), e._pageSetupDialog.applied.addHandler(function() {
          return e._setPageSettings(e._pageSetupDialog.pageSettings)
        })
      }, u.prototype.zoomToView = function() {
        e._deprecated("zoomToView", "zoomMode"), this._documentSource && (this.zoomMode = r.WholePage)
      }, u.prototype.zoomToViewWidth = function() {
        e._deprecated("zoomToViewWidth", "zoomMode"), this._documentSource && (this.zoomMode = r.PageWidth)
      }, u.prototype._setPageLandscape = function(e) {
        var n = this,
          o = this._documentSource.pageSettings;
        t._setLandscape(o, e), n._setPageSettings(o)
      }, u.prototype._setPaginated = function(e) {
        var t = this._documentSource.features,
          n = this._documentSource.pageSettings;
        t && n && e != n.paginated && (e && t.paginated ? (n.paginated = !0, this._setPageSettings(n)) : !e && t.nonPaginated && (n.paginated = !1, this._setPageSettings(n)))
      }, u.prototype._setPageSettings = function(e) {
        var n = this;
        return this._showViewPanelMessage(), this._setDocumentRendering(), this._documentSource.setPageSettings(e).then(function(e) {
          n._resetDocument(), n._reRenderDocument()
        }).catch(function(e) {
          n._showViewPanelErrorMessage(t._getErrorMessage(e))
        })
      }, u.prototype._showViewPanelErrorMessage = function(e) {
        this._showViewPanelMessage(e, "errormessage")
      }, u.prototype._showViewPanelMessage = function(t, n) {
        var o = this._viewpanelContainer.querySelector(".wj-viewer-loading");
        o || ((o = document.createElement("div")).innerHTML = '<span class="verticalalign"></span><span class="textspan"></span>', this._viewpanelContainer.appendChild(o)), o.className = "wj-viewer-loading", n && e.addClass(o, n);
        var r = o.querySelector(".textspan");
        r && (r.innerHTML = t || e.culture.Viewer.loading)
      }, u.prototype._removeViewPanelMessage = function() {
        var e = this._viewpanelContainer.querySelector(".wj-viewer-loading");
        e && this._viewpanelContainer.removeChild(e)
      }, u.prototype._reRenderDocument = function() {
        this._documentSource && (this._showViewPanelMessage(), this._documentSource.load())
      }, u.prototype._zoomBtnClicked = function(e, t) {
        var n, o;
        for (n = 0; n < t.length; n++) {
          if (t[n].value > this.zoomFactor) {
            o = n - .5;
            break
          }
          if (t[n].value === this.zoomFactor) {
            o = n;
            break
          }
        }
        null == o && (o = t.length - .5), o <= 0 && !e || o >= t.length - 1 && e || (o = e ? Math.floor(o) + 1 : Math.ceil(o) - 1, this.zoomFactor = t[o].value)
      }, u.prototype._getDocumentSource = function() {
        return this._documentSource
      }, u.prototype._setDocumentSource = function(e) {
        var t = this;
        this._loadDocument(e).then(function(n) {
          t._documentSource == e && t._ensureExportFormatsLoaded()
        })
      }, u.prototype._loadDocument = function(e, n, o) {
        var r = this;
        void 0 === n && (n = !1), void 0 === o && (o = !0);
        var i = new t._Promise;
        return this._documentSource !== e || n ? (this._disposeDocument(o), this._documentSource = e, e && (t._addWjHandler(this._documentEventKey, e.loading, function() {
          r._updateUI()
        }, this), t._addWjHandler(this._documentEventKey, e.loadCompleted, this._onDocumentSourceLoadCompleted, this), t._addWjHandler(this._documentEventKey, e.queryLoadingData, function(e, t) {
          r.onQueryLoadingData(t)
        }, this), e.isLoadCompleted ? (this._onDocumentSourceLoadCompleted(), this._keepServiceConnection(), i.resolve()) : (this._showViewPanelMessage(), e.load().then(function(e) {
          r._keepServiceConnection(), i.resolve(e)
        }).catch(function(e) {
          r._showViewPanelErrorMessage(t._getErrorMessage(e))
        }))), this._onDocumentSourceChanged(), i) : i
      }, u.prototype._actionElementClicked = function(e) {
        var t = this._getActionInfo(e);
        t && (t.kind === a.Bookmark ? this._goToBookmark(t) : t.kind === a.Custom && this._executeCustomAction(t))
      }, u.prototype._getActionInfo = function(e) {
        var n = e.getAttribute(t._Page._bookmarkAttr);
        return n ? {
          kind: a.Bookmark,
          data: n
        } : (n = e.getAttribute(t._Page._customActionAttr)) ? {
          kind: a.Custom,
          data: n
        } : null
      }, u.prototype._onDocumentSourceLoadCompleted = function() {
        var e = this,
          t = this._documentSource.errors;
        if (this._documentSource.isLoadCompleted) {
          if (this._removeViewPanelMessage(), this._pages.length = 0, this._documentSource.pageCount <= 0) return void this._updateUI();
          for (var n = {
            width: this._documentSource.pageSettings.width,
            height: this._documentSource.pageSettings.height
          }, r = 0; r < this._documentSource.pageCount; r++) {
            var i = this._createPage(r, n);
            i.linkClicked.addHandler(function(t, n) {
              e._magnifier && e._magnifier.isActive || e._rubberband && e._rubberband.isActive || e._actionElementClicked(n.element)
            }), this._pages.push(i)
          }
          this._pageView.pages = this._pages, this._autoHeightCalculated || (this._autoCalculateHeight(), this._autoHeightCalculated = !0);
          var a = this._documentSource.initialPosition || this._initialPosition;
          if (this._documentSource.initialPosition = null, this._initialPosition = null, a && 0 != a.pageIndex || (this._pageIndex = 0, a = {
            pageIndex: 0
          }), this._scrollToPosition(a), this._updateHistoryCurrent(), this._documentSource.paginated || (this.viewMode = o.Single), t && t.length > 0)
            for (r = 0; r < t.length; r++) t[r] + "\r\n";
          this._updateUI()
        }
      }, u.prototype._createPage = function(e, n) {
        return new t._Page(this._documentSource, e, n)
      }, u.prototype._clearKeepSerConnTimer = function() {
        null != this._keepSerConnTimer && clearTimeout(this._keepSerConnTimer)
      }, u.prototype._keepServiceConnection = function() {
        var e = this;
        this._clearKeepSerConnTimer();
        var t = this._documentSource;
        t && (this._keepSerConnTimer = setTimeout(function() {
          e._documentSource === t && e._documentSource.getStatus().then(function(t) {
            return e._keepServiceConnection()
          })
        }, this._getExpiredTime()))
      }, u.prototype._getExpiredTime = function() {
        if (this._expiredTime) return this._expiredTime;
        var e = this._documentSource;
        return e && e.expiredDateTime && e.executionDateTime ? (this._expiredTime = e.expiredDateTime.getTime() - e.executionDateTime.getTime(), this._expiredTime = Math.max(this._expiredTime - 12e4, 0), this._expiredTime) : 6e3
      }, u.prototype._disposeDocument = function(e) {
        void 0 === e && (e = !0), this._documentSource && (t._removeAllWjHandlers(this._documentEventKey, this._documentSource.disposed), t._removeAllWjHandlers(this._documentEventKey, this._documentSource.pageCountChanged), t._removeAllWjHandlers(this._documentEventKey, this._documentSource.pageSettingsChanged), t._removeAllWjHandlers(this._documentEventKey, this._documentSource.loadCompleted), t._removeAllWjHandlers(this._documentEventKey, this._documentSource.queryLoadingData), e && this._documentSource.dispose()), this._resetDocument()
      }, u.prototype._resetDocument = function() {
        this._pages.length = 0, this._pageView.resetPages(), this._pageIndex = 0, clearTimeout(this._historyTimer), this._historyManager.clear(), this._rubberband && this._rubberband.reset(), this._magnifier && this._magnifier.reset()
      }, u.prototype._setDocumentRendering = function() {
        this._documentSource._updateIsLoadCompleted(!1)
      }, u.prototype.moveToPage = function(e) {
        return this._innerMoveToPage(e)
      }, u.prototype._getCurrentPosition = function() {
        return t._getPositionByHitTestInfo(this._pageView.hitTest(0, 0))
      }, u.prototype._resolvePageIndex = function(e) {
        return Math.min(this._documentSource.pageCount - 1, Math.max(e, 0))
      }, u.prototype._innerMoveToPage = function(e) {
        return this._resolvePageIndex(e) != this.pageIndex && this._addHistory(!1, !0), this._updatePageIndex(e), this._pageView.moveToPage(e)
      }, u.prototype._moveToLastPage = function() {
        var e = new t._Promise;
        return this._ensureDocumentLoadCompleted(e) ? this._innerMoveToPage(this._documentSource.pageCount - 1) : e
      }, u.prototype._moveBackwardHistory = function() {
        if (this._ensureDocumentLoadCompleted() && this._historyManager.canBackward()) {
          var e = this._historyManager.backward();
          this._moveToHistory(e)
        }
      }, u.prototype._moveForwardHistory = function() {
        if (this._ensureDocumentLoadCompleted() && this._historyManager.canForward()) {
          var e = this._historyManager.forward();
          this._moveToHistory(e)
        }
      }, u.prototype._moveToHistory = function(e) {
        var t = this;
        if (e) {
          this._historyMoving = !0, this.viewMode = e.viewMode, e.zoomMode === r.Custom ? this.zoomFactor = e.zoomFactor : this.zoomMode = e.zoomMode, this._scrollToPosition(e.position).then(function(e) {
            t._historyMoving = !1
          });
          for (var n = 0; n < e.pageAngles.length; n++) this._pageView.rotatePageTo(n, e.pageAngles[n])
        }
      }, u.prototype._isPositionEquals = function(e, t, n) {
        return e.pageIndex === t.pageIndex && (!n || (e.pageBounds == t.pageBounds || null != e.pageBounds && null != t.pageBounds && (e.pageBounds.x === t.pageBounds.x && e.pageBounds.y === t.pageBounds.y)))
      }, u.prototype._isPageAnglesChanged = function(e) {
        if (e.length != this._pageView.pages.length) return !0;
        for (var t = e.length, n = 0; n < t; n++)
          if (e[n] !== this._pageView.pages[n].rotateAngle) return !0;
        return !1
      }, u.prototype._updateHistoryCurrent = function() {
        this._historyManager.current.position = this._getCurrentPosition(), this._historyManager.current.zoomMode = this.zoomMode, this._historyManager.current.zoomFactor = this.zoomFactor, this._historyManager.current.viewMode = this.viewMode, this._updateCurrentPageAngles(this._historyManager.current)
      }, u.prototype._innerAddHistory = function(e) {
        var t = this._getCurrentPosition(),
          n = this._historyManager.current;
        this._isPositionEquals(t, n.position, e) && this.viewMode === n.viewMode && this.zoomMode === n.zoomMode && this.zoomFactor === n.zoomFactor && !this._isPageAnglesChanged(n.pageAngles) || (n.position = n.position || this._getCurrentPosition(), n.viewMode = null == n.viewMode ? this.viewMode : n.viewMode, n.zoomMode = null == n.zoomMode ? this.zoomMode : n.zoomMode, n.zoomFactor = null == n.zoomFactor ? this.zoomFactor : n.zoomFactor, n.pageAngles || this._updateCurrentPageAngles(n), this._historyManager.add(), this._updateHistoryCurrent())
      }, u.prototype._addHistory = function(e, t, n) {
        var o = this;
        this.isUpdating || this._historyMoving || !this._isDocumentSourceLoaded() || (t ? (this._mergeHistory(n), null != this._historyTimer && clearTimeout(this._historyTimer), this._historyTimer = setTimeout(function() {
          o._historyTimer = null, o._innerAddHistory(e)
        }, u._historyTimeout)) : this._innerAddHistory(e))
      }, u.prototype._updateCurrentPageAngles = function(e) {
        e.pageAngles || (e.pageAngles = new Array);
        for (var t = this._pageView.pages.length, n = 0; n < t; n++) e.pageAngles[n] = this._pageView.pages[n].rotateAngle
      }, u.prototype._mergeHistory = function(e) {
        var t = this._historyManager.current;
        if (!e) return t.viewMode = this.viewMode, t.zoomMode = this.zoomMode, t.zoomFactor = this.zoomFactor, void this._updateCurrentPageAngles(t);
        if (null != e.viewMode && (t.viewMode = e.viewMode), null != e.zoomMode && (t.zoomMode = e.zoomMode), null != e.zoomFactor && (t.zoomFactor = e.zoomFactor), e.pageAngles) {
          t.pageAngles = new Array;
          for (var n = this._pageView.pages.length, o = 0; o < n; o++) t.pageAngles[o] = e.pageAngles[o]
        }
      }, u.prototype._ensureDocumentLoadCompleted = function(e) {
        return this._documentSource ? !!this._documentSource.isLoadCompleted || (e && e.reject("Cannot set page index when document source is not loaded completely."), !1) : (e && e.reject("Cannot set page index without document source."), !1)
      }, u.prototype._updatePageIndex = function(e) {
        this._documentSource && (e = Math.min(this._documentSource.pageCount - 1, Math.max(e, 0)), this._pageIndex !== e && (this._pageIndex = e, this.onPageIndexChanged()))
      }, u.prototype._getRotatedAngle = function(e) {
        switch (e) {
          case t._RotateAngle.NoRotate:
            return t._RotateAngle.Rotation90;
          case t._RotateAngle.Rotation90:
            return t._RotateAngle.Rotation180;
          case t._RotateAngle.Rotation180:
            return t._RotateAngle.Rotation270;
          case t._RotateAngle.Rotation270:
            return t._RotateAngle.NoRotate
        }
        return t._RotateAngle.NoRotate
      }, u.prototype._rotateDocument = function() {
        this._addHistory(!1, !0);
        for (var e = this._pageView.pages.length, t = 0; t < e; t++) this._pageView.rotatePageTo(t, this._getRotatedAngle(this._pageView.pages[t].rotateAngle))
      }, u.prototype._rotatePage = function() {
        this._addHistory(!1, !0);
        var e = this._pageView.pages[this._pageIndex];
        this._pageView.rotatePageTo(this._pageIndex, this._getRotatedAngle(e.rotateAngle))
      }, Object.defineProperty(u.prototype, "zoomMode", {
        get: function() {
          return this._pageView.zoomMode
        },
        set: function(t) {
          this._pageView.zoomMode = e.asEnum(t, r), this._updateZoomModeActions()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "zoomFactor", {
        get: function() {
          return this._pageView.zoomFactor
        },
        set: function(e) {
          this._pageView.zoomFactor = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "viewMode", {
        get: function() {
          return this._viewMode
        },
        set: function(t) {
          t = e.asEnum(t, o), this._viewMode !== t && (this._viewMode = t, this.onViewModeChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "selectMouseMode", {
        get: function() {
          return this._mouseMode === n.SelectTool
        },
        set: function(t) {
          e._deprecated("selectMouseMode", "mouseMode"), t && this._mouseMode === n.SelectTool || !t && this._mouseMode !== n.SelectTool || (this.mouseMode = t ? n.SelectTool : n.MoveTool)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "mouseMode", {
        get: function() {
          return this._mouseMode
        },
        set: function(t) {
          if (this._mouseMode !== (t = e.asEnum(t, n))) {
            var o = this.selectMouseMode;
            switch (this._mouseMode = t, this._mouseMode) {
              case n.RubberbandTool:
                this._rubberband.activate(), this._magnifier.deactivate();
                break;
              case n.MagnifierTool:
                this._magnifier.activate(), this._rubberband.deactivate();
                break;
              default:
                this._magnifier.deactivate(), this._rubberband.deactivate()
            }
            this.onMouseModeChanged(), o != this.selectMouseMode && this.onSelectMouseModeChanged()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "fullScreen", {
        get: function() {
          return this._fullScreen
        },
        set: function(e) {
          this._fullScreen !== e && (this._fullScreen = e, this.onFullScreenChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(u.prototype, "pageIndex", {
        get: function() {
          return this._pageIndex
        },
        enumerable: !0,
        configurable: !0
      }), u.prototype._initMiniToolbar = function() {
        e.Control.getControl(this._miniToolbar) || (new t._ViewerMiniToolbar(this._miniToolbar, this), e.addClass(this._miniToolbar, "wj-mini-toolbar"))
      }, u.prototype._pinMiniToolbar = function() {
        var e = this;
        this._showMiniToolbar(!0), this._miniToolbarPinnedTimer = setTimeout(function() {
          e._showMiniToolbar(!1), e._miniToolbarPinnedTimer = null
        }, u._miniToolbarPinnedTime)
      }, u.prototype._onDocumentSourceChanged = function(n) {
        this._clearExportFormats(), this._documentSourceChanged.raise(this, n || new e.EventArgs), this._updateViewerActions(), this._onPageSettingsUpdated(), this._onPageCountUpdated(), this._updateViewModeActions(), this._searchManager.documentSource = this._documentSource, this._documentSource && (t._addWjHandler(this._documentEventKey, this._documentSource.pageSettingsChanged, this._onPageSettingsUpdated, this), t._addWjHandler(this._documentEventKey, this._documentSource.pageCountChanged, this._onPageCountUpdated, this), t._addWjHandler(this._documentEventKey, this._documentSource.loadCompleted, this._onPageCountUpdated, this))
      }, u.prototype.onPageIndexChanged = function(t) {
        this.pageIndexChanged.raise(this, t || new e.EventArgs), this._updatePageNavActions()
      }, u.prototype.onViewModeChanged = function(t) {
        this.viewModeChanged.raise(this, t || new e.EventArgs), this._updateViewModeActions(), this._pageView.viewMode = this.viewMode
      }, u.prototype.onSelectMouseModeChanged = function(t) {
        this.selectMouseModeChanged.hasHandlers && e._deprecated("selectMouseModeChanged", "mouseModeChanged"), this.selectMouseModeChanged.raise(this, t)
      }, u.prototype.onMouseModeChanged = function(t) {
        this.mouseModeChanged.raise(this, t || new e.EventArgs), (this.mouseMode !== n.MoveTool && this.mouseMode !== n.SelectTool || !e.isMobile()) && (this._updateMouseModeActions(), this._updateViewContainerCursor(), this._pageView.panMode = this.mouseMode === n.MoveTool)
      }, u.prototype.onFullScreenChanged = function(t) {
        this.fullScreenChanged.raise(this, t || new e.EventArgs), this._setViewerAction(l.ToggleFullScreen), this._updateFullScreenStyle(), this.fullScreen && this._pinMiniToolbar()
      }, u.prototype.onZoomFactorChanged = function(t) {
        this.zoomFactorChanged.raise(this, t || new e.EventArgs), this._updateZoomFactorActions(), this._updateZoomModeActions()
      }, u.prototype.onQueryLoadingData = function(e) {
        this.queryLoadingData.raise(this, e)
      }, u.prototype.onBeforeSendRequest = function(e) {
        this.beforeSendRequest.raise(this, e)
      }, u.prototype.beforeSend = function(e) {
        this.onBeforeSendRequest(e)
      }, u._seperatorHtml = "<div class='wj-separator' style='width:100%;height: 1px;margin: 3px 0;background-color:rgba(0,0,0,.2)'></div>", u._viewpanelContainerMinHeight = 300, u._miniToolbarPinnedTime = 3e3, u._narrowCss = "narrow", u._narrowWidthThreshold = 400, u._thumbnailWidth = 100, u._historyTimeout = 300, u._defaultZoomValues = [{
        name: e.Globalize.format(.05, "p0"),
        value: .05
      }, {
        name: e.Globalize.format(.25, "p0"),
        value: .25
      }, {
        name: e.Globalize.format(.5, "p0"),
        value: .5
      }, {
        name: e.Globalize.format(.75, "p0"),
        value: .75
      }, {
        name: e.Globalize.format(1, "p0"),
        value: 1
      }, {
        name: e.Globalize.format(2, "p0"),
        value: 2
      }, {
        name: e.Globalize.format(3, "p0"),
        value: 3
      }, {
        name: e.Globalize.format(4, "p0"),
        value: 4
      }, {
        name: e.Globalize.format(8, "p0"),
        value: 8
      }, {
        name: e.Globalize.format(10, "p0"),
        value: 10
      }], u.controlTemplate = '<div class="wj-viewer-outer wj-content with-footer"><div wj-part="toolbar"></div><div wj-part="mobile-toolbar"></div><div class="wj-viewer-container" wj-part="viewer-container"><div class="wj-viewer-leftpanel" wj-part="viewer-left-panel"><div class="wj-viewer-tabsleft" wj-part="side-panel"></div></div><div class="wj-viewer-splitter" wj-part="splitter"><button class="wj-btn wj-btn-default"><span class="wj-glyph-right"></span></button></div><div class="wj-viewpanel-container" wj-part="viewpanel-container"></div></div><div wj-part="mini-toolbar"></div><div class="wj-searchbar mobile" wj-part="search-bar"></div><div class="wj-viewer-footer mobile" class="wj-viewer-footer" wj-part="viewer-footer"><div wj-part="zoom-bar"></div><span class="wj-close">×</span></div></div>', u
    }(e.Control);
    t.ViewerBase = u;
    var c = function(n) {
      function o(o) {
        var r = n.call(this, o) || this;
        r.applied = new e.Event;
        var i;
        return r.modal = !0, r.hideTrigger = e.input.PopupTrigger.None, i = r.getTemplate(), r.applyTemplate("wj-control wj-content", i, {
          _gHeader: "g-header",
          _pageSetupEditorElement: "pagesetup-editor",
          _btnApply: "btn-apply",
          _btnCancel: "btn-cancel",
          _btnClose: "a-close"
        }), r._pageSetupEditor = new t._PageSetupEditor(r._pageSetupEditorElement), r._globalize(), r._addEvents(), r
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "pageSettings", {
        get: function() {
          return this._pageSetupEditor.pageSettings
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._globalize = function() {
        var t = e.culture.Viewer;
        this._gHeader.textContent = t.pageSetup, this._btnApply.textContent = t.ok, this._btnCancel.textContent = t.cancel
      }, o.prototype._addEvents = function() {
        var e = this;
        t._addEvent(e._btnClose, "click", function() {
          e.hide()
        }), t._addEvent(e._btnCancel, "click", function() {
          e.hide()
        }), t._addEvent(e._btnApply, "click", function() {
          e._apply(), e.hide()
        })
      }, o.prototype._apply = function() {
        this.onApplied()
      }, o.prototype.onApplied = function() {
        this.applied.raise(this, new e.EventArgs)
      }, o.prototype.showWithValue = function(e) {
        this._pageSetupEditor.pageSettings = e, n.prototype.show.call(this)
      }, o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e), e && (this._globalize(), this._pageSetupEditor.refresh())
      }, o.controlTemplate = '<div><div wj-part="g-header" class="wj-dialog-header"><a class="wj-hide" wj-part="a-close" style="float:right;outline:none;text-decoration:none;padding:0px 6px" href="" tabindex="-1" draggable="false">&times;</a></div><div style="padding:12px;"><div wj-part="pagesetup-editor"></div></div><div class="wj-dialog-footer"><a wj-part="btn-apply" class="wj-hide wj-btn" tabindex="-1"></a>&nbsp;&nbsp;<a wj-part="btn-cancel" class="wj-hide wj-btn" tabindex="-1"></a></div></div>', o
    }(e.input.Popup);
    t._PageSetupDialog = c;
    var l;
    ! function(e) {
      e[e.TogglePaginated = 0] = "TogglePaginated", e[e.Print = 1] = "Print", e[e.Portrait = 2] = "Portrait", e[e.Landscape = 3] = "Landscape", e[e.ShowPageSetupDialog = 4] = "ShowPageSetupDialog", e[e.FirstPage = 5] = "FirstPage", e[e.PrePage = 6] = "PrePage", e[e.NextPage = 7] = "NextPage", e[e.LastPage = 8] = "LastPage", e[e.PageNumber = 9] = "PageNumber", e[e.PageCountLabel = 10] = "PageCountLabel", e[e.Backward = 11] = "Backward", e[e.Forward = 12] = "Forward", e[e.SelectTool = 13] = "SelectTool", e[e.MoveTool = 14] = "MoveTool", e[e.Continuous = 15] = "Continuous", e[e.Single = 16] = "Single", e[e.ZoomOut = 17] = "ZoomOut", e[e.ZoomIn = 18] = "ZoomIn", e[e.ZoomValue = 19] = "ZoomValue", e[e.FitWholePage = 20] = "FitWholePage", e[e.FitPageWidth = 21] = "FitPageWidth", e[e.ToggleFullScreen = 22] = "ToggleFullScreen", e[e.ShowHamburgerMenu = 23] = "ShowHamburgerMenu", e[e.ShowViewMenu = 24] = "ShowViewMenu", e[e.ShowSearchBar = 25] = "ShowSearchBar", e[e.ShowThumbnails = 26] = "ShowThumbnails", e[e.ShowOutlines = 27] = "ShowOutlines", e[e.ShowExportsPanel = 28] = "ShowExportsPanel", e[e.ShowPageSetupPanel = 29] = "ShowPageSetupPanel", e[e.ShowZoomBar = 30] = "ShowZoomBar", e[e.ShowSearchOptions = 31] = "ShowSearchOptions", e[e.SearchPrev = 32] = "SearchPrev", e[e.SearchNext = 33] = "SearchNext", e[e.SearchMatchCase = 34] = "SearchMatchCase", e[e.SearchMatchWholeWord = 35] = "SearchMatchWholeWord", e[e.RubberbandTool = 36] = "RubberbandTool", e[e.MagnifierTool = 37] = "MagnifierTool", e[e.RotateDocument = 38] = "RotateDocument", e[e.RotatePage = 39] = "RotatePage"
    }(l = t._ViewerActionType || (t._ViewerActionType = {}));
    var p = function(t) {
      function n(e, n, o, r, i, a, s) {
        var u = t.call(this, e) || this;
        return u._stopOnClientOut = r, u._css = i, u._activeCss = a, u._visibleCss = s, u._pageView = o, u._viewPanelContainer = n, u._initElement(), u._bindEvents(), u
      }
      return __extends(n, t), n.prototype.activate = function() {
        this._isActive || (this._isActive = !0, e.addClass(this._viewPanelContainer, this._activeCss))
      }, n.prototype.deactivate = function() {
        this._isActive && (this._isActive = !1, e.removeClass(this._viewPanelContainer, this._activeCss))
      }, n.prototype.reset = function() {
        this._innerStop(null)
      }, Object.defineProperty(n.prototype, "isActive", {
        get: function() {
          return this._isActive
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "startPnt", {
        get: function() {
          return this._startPnt
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pageView", {
        get: function() {
          return this._pageView
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "viewPanelContainer", {
        get: function() {
          return this._viewPanelContainer
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._initElement = function() {
        this.applyTemplate(this._css, this.getTemplate(), this._getTemplateParts()), this._viewPanelContainer.appendChild(this.hostElement)
      }, n.prototype._innerStop = function(e) {
        try {
          this._stop(e)
        } finally {
          this._isStarted = !1, this._startPnt = null
        }
      }, n.prototype._getTemplateParts = function() {
        return null
      }, n.prototype._onMouseDown = function(e) {
        var t = this._toClientPoint(e),
          n = this._testPageWorkingAreaHit(t);
        n && this.pageView.isPageContentLoaded(n.pageIndex) && (void 0 !== window.getSelection && getSelection().removeAllRanges(), this._isStarted = !0, this._startPnt = t, this._start(n))
      }, n.prototype._onMouseMove = function(e) {
        if (this._isStarted) {
          var t = this._toClientPoint(e),
            n = this._testPageWorkingAreaHit(t);
          n && this.pageView.isPageContentLoaded(n.pageIndex) ? this._move(t, n) : this._stopOnClientOut && this._stop(t)
        }
      }, n.prototype._onMouseUp = function(e) {
        this._isStarted && this._innerStop(this._toClientPoint(e))
      }, n.prototype._start = function(t) {
        e.addClass(this.hostElement, this._visibleCss)
      }, n.prototype._move = function(e, t) {}, n.prototype._stop = function(t) {
        e.removeClass(this.hostElement, this._visibleCss)
      }, n.prototype._bindEvents = function() {
        var e = this;
        this.addEventListener(this._viewPanelContainer, "mousedown", function(t) {
          e._isActive && e._onMouseDown(t)
        }), this.addEventListener(this._viewPanelContainer, "mousemove", function(t) {
          e._isActive && e._onMouseMove(t)
        }), this.addEventListener(document, "mouseup", function(t) {
          e._isActive && e._onMouseUp(t)
        })
      }, n.prototype._toClientPoint = function(t) {
        var n = this._viewPanelContainer.getBoundingClientRect();
        return new e.Point(t.clientX - n.left, t.clientY - n.top)
      }, n.prototype._testPageWorkingAreaHit = function(e) {
        var t = this._pageView.hitTest(e.x, e.y);
        return t && t.hitWorkingArea ? t : null
      }, n
    }(e.Control);
    t._MouseTool = p;
    var h = function(e) {
      function t(t) {
        var n = e.call(this) || this;
        return n._rect = t, n
      }
      return __extends(t, e), Object.defineProperty(t.prototype, "rect", {
        get: function() {
          return this._rect
        },
        enumerable: !0,
        configurable: !0
      }), t
    }(e.EventArgs);
    t._RubberbandOnAppliedEventArgs = h;
    var d = function(t) {
      function n(n, o, r) {
        var i = t.call(this, n, o, r, !1, "wj-rubberband", "rubberband-actived", "show") || this;
        return i.applied = new e.Event, i
      }
      return __extends(n, t), n.prototype._start = function(e) {
        t.prototype._start.call(this, e), this.hostElement.style.left = this.startPnt.x + "px", this.hostElement.style.top = this.startPnt.y + "px"
      }, n.prototype._move = function(e, t) {
        if (this.startPnt) {
          this.viewPanelContainer.getBoundingClientRect();
          this.hostElement.style.width = e.x - this.startPnt.x + "px", this.hostElement.style.height = e.y - this.startPnt.y + "px"
        }
      }, n.prototype._stop = function(n) {
        if (n) {
          var o = this.hostElement.getBoundingClientRect();
          o.width > 5 && o.height > 5 && this._onApplied(new h(new e.Rect(this.startPnt.x, this.startPnt.y, o.width, o.height)))
        }
        this.hostElement.style.width = "0px", this.hostElement.style.height = "0px", t.prototype._stop.call(this, n)
      }, n.prototype._onApplied = function(e) {
        this.applied.raise(this, e)
      }, n
    }(p);
    t._Rubberband = d;
    var _ = function(e) {
      function n(t, n, o) {
        var r = e.call(this, t, n, o, !0, "wj-magnifier", "magnifier-actived", "show") || this;
        return r._Magnification = 2, r._currentPageIndex = -1, r
      }
      return __extends(n, e), n.prototype.deactivate = function() {
        e.prototype.deactivate.call(this), this._currentPageIndex = -1
      }, n.prototype.reset = function() {
        e.prototype.reset.call(this), this._currentPageIndex = -1
      }, n.prototype._getTemplateParts = function() {
        return {
          _viewPageDiv: "view-page-div"
        }
      }, n.prototype._bindEvents = function() {
        var n = this;
        e.prototype._bindEvents.call(this);
        var o = function() {
          if (!(n._currentPageIndex < 0)) {
            var e = n.pageView.pages[n._currentPageIndex],
              o = t._getRotatedSize(e.size, e.rotateAngle),
              r = n._viewPageDiv.querySelector("svg");
            n._viewPageDiv.style.height = o.height.valueInPixel * n.pageView.zoomFactor * n._Magnification + "px", n._viewPageDiv.style.width = o.width.valueInPixel * n.pageView.zoomFactor * n._Magnification + "px", r.setAttribute("width", n._viewPageDiv.style.width), r.setAttribute("height", n._viewPageDiv.style.height), t._transformSvg(r, e.size.width.valueInPixel, e.size.height.valueInPixel, n._Magnification * n.pageView.zoomFactor, e.rotateAngle)
          }
        };
        this.pageView.zoomFactorChanged.addHandler(function() {
          o()
        }), this.pageView.rotateAngleChanged.addHandler(function() {
          o()
        })
      }, n.prototype._start = function(t) {
        e.prototype._start.call(this, t), this._showMagnifer(this.startPnt, t)
      }, n.prototype._move = function(e, t) {
        this._showMagnifer(e, t)
      }, n.prototype._showMagnifer = function(e, n) {
        var o = this.hostElement.getBoundingClientRect(),
          r = t._getPositionByHitTestInfo(n);
        this.hostElement.style.left = e.x - o.width / 2 + "px", this.hostElement.style.top = e.y - o.height / 2 + "px", this._fillPage(r), this._showHitPosition(r)
      }, n.prototype._fillPage = function(e) {
        var n = this;
        e.pageIndex !== this._currentPageIndex && (this._currentPageIndex = e.pageIndex, this.pageView.pages[this._currentPageIndex].getContent().then(function(e) {
          var o = e.cloneNode(!0);
          n._viewPageDiv.innerHTML = "", n._viewPageDiv.appendChild(o), o.setAttribute("width", new t._Unit(o.getAttribute("width")).valueInPixel * n._Magnification + "px"), o.setAttribute("height", new t._Unit(o.getAttribute("height")).valueInPixel * n._Magnification + "px");
          var r = n.pageView.pages[n._currentPageIndex].size;
          t._transformSvg(o, r.width.valueInPixel, r.height.valueInPixel, n._Magnification * n.pageView.zoomFactor, n.pageView.pages[n._currentPageIndex].rotateAngle), n._viewPageDiv.style.width = o.getAttribute("width"), n._viewPageDiv.style.height = o.getAttribute("height")
        }))
      }, n.prototype._showHitPosition = function(e) {
        var n = this.hostElement.getBoundingClientRect(),
          o = this.pageView.pages[this._currentPageIndex],
          r = t._getTransformedPosition(e.pageBounds, o.size, o.rotateAngle, this.pageView.zoomFactor);
        this._viewPageDiv.style.left = -r.x * this._Magnification + n.width / 2 + "px", this._viewPageDiv.style.top = -r.y * this._Magnification + n.height / 2 + "px"
      }, n.controlTemplate = '<div wj-part="view-page-div" class="wj-view-page"></div>', n
    }(p);
    t._Magnifier = _
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = '<path d="M24,11.9v-2h-4V7h0V5h0h-1h-5V2h0V0h0h-1H1H0h0v2h0v11h0v1h0h1h5v4h0v1h0h1h3v4h0v1h0h1h2.1v-1H11V12h2.1v-2H11h-1h0v2h0v6H7V7h12v2.9h-1v2h5V23h-4.9v1H23h1h0v-1h0L24,11.9L24,11.9z M6,5L6,5l0,2h0v6H1V2h12v3H7H6z"/><path d="M20,20v-3v-1h-1h-1v-1v-1h-1h-3h-1v1v3v1h1h1v2h0h1h3h1h0L20,20L20,20z M14,18v-3h3v1h-1h-1v1v1H14z M17,17v1h-1v-1H17z M16,20v-1h1h1v-1v-1h1v3H16z"/>',
      o = function(o) {
        function i(e, t) {
          var n = o.call(this, e, t) || this;
          return n._initSidePanelParameters(), n
        }
        return __extends(i, o), i.prototype._getProductInfo = function() {
          return "QNI5,ReportViewer"
        }, Object.defineProperty(i.prototype, "reportName", {
          get: function() {
            return this._reportName
          },
          set: function(e) {
            e != this._reportName && (this._reportName = e, this._needBindDocumentSource(), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "paginated", {
          get: function() {
            return this._innerPaginated
          },
          set: function(e) {
            this._innerPaginated = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "parameters", {
          get: function() {
            return this._clientParameters
          },
          set: function(e) {
            e != this._clientParameters && (this._clientParameters = e, this._needBindDocumentSource(), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), i.getReportNames = function(e, n, o) {
          return t._Report.getReportNames(e, n, o)
        }, i.getReports = function(e, n, o, r) {
          return t._Report.getReports(e, n, o, r)
        }, i.prototype.onQueryLoadingData = function(e) {
          var t = this;
          this.parameters && Object.keys(this.parameters).forEach(function(n) {
            return e.data["parameters." + n] = t.parameters[n]
          }), o.prototype.onQueryLoadingData.call(this, e)
        }, i.prototype._globalize = function() {
          o.prototype._globalize.call(this);
          var t = e.culture.Viewer;
          this._gParameterTitle.textContent = t.parameters
        }, i.prototype._executeAction = function(t) {
          if (o.prototype._executeAction.call(this, t), !this._actionIsDisabled(t)) switch (t) {
            case i._parameterCommandTag:
              e.Control.getControl(this._sidePanel).active(this._parametersPageId)
          }
        }, i.prototype._executeCustomAction = function(e) {
          if (this._isArReport() && e.arKind === t._ArActionKind.Drillthrough) {
            var n = JSON.parse(e.data),
              r = this._getDocumentSource();
            r._innerService.setDrillthroughData(n), r._updateIsLoadCompleted(!1), this._loadDocument(r, !0, !1)
          } else o.prototype._executeCustomAction.call(this, e)
        }, i.prototype._actionIsDisabled = function(e) {
          switch (e) {
            case i._parameterCommandTag:
              return !this._innerDocumentSource || !this._innerDocumentSource.hasParameters
          }
          return o.prototype._actionIsDisabled.call(this, e)
        }, i.prototype._initHamburgerMenu = function(e) {
          this._hamburgerMenu = new a(this, e)
        }, i.prototype._initSidePanelParameters = function() {
          var o = this,
            i = e.Control.getControl(this._sidePanel),
            a = i.addPage(e.culture.Viewer.parameters, n, 2);
          this._parametersPageId = a.id, this._gParameterTitle = a.outContent.querySelector(".wj-tabtitle"), a.format(function(n) {
            o._paramsEditor = new r(n.content), o._paramsEditor.commit.addHandler(function() {
              o._innerDocumentSource && o._innerDocumentSource.hasParameters && (o._showViewPanelMessage(), o._innerDocumentSource.setParameters(o._paramsEditor.parameters).then(function(e) {
                var t = e || [],
                  n = t.some(function(e) {
                    return !!e.error
                  });
                o._paramsEditor._reset(), n ? o._paramsEditor.itemsSource = t : (o._resetDocument(), o._paramsEditor.isDisabled = !0, o._renderDocumentSource())
              }).catch(function(e) {
                o._showViewPanelErrorMessage(t._getErrorMessage(e))
              }), o._isMobileTemplate() && i.collapse())
            }), o._paramsEditor.validate.addHandler(function() {
              o._innerDocumentSource && o._innerDocumentSource.hasParameters && (o._paramsEditor.isDisabled = !0, o._innerDocumentSource.setParameters(o._paramsEditor.parameters).then(function(e) {
                o._paramsEditor.itemsSource = e, o._paramsEditor.isDisabled = !1
              }, function(e) {
                o._paramsEditor.isDisabled = !1
              }))
            });
            var a = function() {
                var r = o._innerDocumentSource;
                r.status === t._ExecutionStatus.cleared || r.status === t._ExecutionStatus.notFound ? t._removeChildren(n.content) : r.status === t._ExecutionStatus.rendering ? o._paramsEditor.isDisabled = !0 : r.status === t._ExecutionStatus.completed && (o._paramsEditor.isDisabled = !1), r.status === t._ExecutionStatus.loaded && (r.hasParameters ? r.getParameters().then(function(t) {
                  t.filter(function(e) {
                    return !e.hidden
                  }).length ? (i.show(n), i.active(n)) : i.hide(n), o._innerDocumentSource != r || r.isDisposed || (o._paramsEditor.itemsSource = t, t.filter(function(e) {
                    return null == e.value && !e.nullable && !e.hidden
                  }).length ? (o._showViewPanelMessage(e.culture.Viewer.requiringParameters), o._updateUI(), e.Control.getControl(o._sidePanel).enable(o._parametersPageId)) : (o._paramsEditor.isDisabled = !0, o._renderDocumentSource()))
                }) : i.hide(n))
              },
              s = function() {
                o._innerDocumentSource && (t._addWjHandler(o._documentEventKey, o._innerDocumentSource.statusChanged, a), a())
              };
            o._documentSourceChanged.addHandler(s), s()
          })
        }, Object.defineProperty(i.prototype, "_innerDocumentSource", {
          get: function() {
            return this._getDocumentSource()
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype._loadDocument = function(e, n, r) {
          void 0 === n && (n = !1), void 0 === r && (r = !0);
          var i = this._innerDocumentSource !== e && !n,
            a = o.prototype._loadDocument.call(this, e, n, r);
          return e && i && t._addWjHandler(this._documentEventKey, e.statusChanged, this._onDocumentStatusChanged, this), a
        }, i.prototype._reRenderDocument = function() {
          this._renderDocumentSource()
        }, i.prototype._onDocumentStatusChanged = function() {
          this._innerDocumentSource && this._innerDocumentSource.status === t._ExecutionStatus.loaded && !this._innerDocumentSource.hasParameters && this._innerDocumentSource.autoRun && this._renderDocumentSource()
        }, i.prototype._renderDocumentSource = function() {
          var e = this;
          if (this._innerDocumentSource) {
            this._setDocumentRendering();
            var t = this._innerDocumentSource;
            t.render().then(function(n) {
              return e._getStatusUtilCompleted(t)
            })
          }
        }, i.prototype._disposeDocument = function(e) {
          void 0 === e && (e = !0), this._innerDocumentSource && t._removeAllWjHandlers(this._documentEventKey, this._innerDocumentSource.statusChanged), o.prototype._disposeDocument.call(this, e)
        }, i.prototype._setDocumentRendering = function() {
          this._innerDocumentSource.status = t._ExecutionStatus.rendering, o.prototype._setDocumentRendering.call(this)
        }, i.prototype._getSource = function() {
          return this.filePath ? this._isArReport() ? new t._ArReportSource({
            serviceUrl: this.serviceUrl,
            filePath: this.filePath
          }, this) : new t._Report({
            serviceUrl: this.serviceUrl,
            filePath: this.filePath,
            reportName: this.reportName,
            paginated: this.paginated
          }, this) : null
        }, i.prototype._supportsPageSettingActions = function() {
          return !0
        }, i.prototype.refresh = function(e) {
          void 0 === e && (e = !0), o.prototype.refresh.call(this, e), e && this._paramsEditor.refresh()
        }, i.prototype._isArReport = function() {
          return !!this.filePath && t._strEndsWith(this.filePath, ".rdlx", !0)
        }, i.prototype._createPage = function(e, n) {
          return this._isArReport() ? new t._ArPage(this._getDocumentSource(), e, n) : o.prototype._createPage.call(this, e, n)
        }, i.prototype._actionElementClicked = function(e) {
          if (this._isArReport()) {
            var n = this._getActionInfo(e);
            n && n.arKind === t._ArActionKind.Hyperlink ? window.open(n.data, "_blank") : o.prototype._actionElementClicked.call(this, e)
          } else o.prototype._actionElementClicked.call(this, e)
        }, i.prototype._getActionInfo = function(e) {
          if (!this._isArReport()) return o.prototype._getActionInfo.call(this, e);
          var n = e.attributes["arsvg:data-action-type"].value,
            r = e.attributes["arsvg:data-action-data"].value.replace(/&quot;/g, '"'),
            i = e.attributes["arsvg:data-action-page"].value;
          if (n) switch (n) {
            case t._ArActionKind.Bookmark:
              return {
                kind: t._ActionKind.Bookmark,
                data: r ? i + "|" + r : i,
                arKind: n
              };
            default:
              return {
                kind: t._ActionKind.Custom,
                data: r,
                arKind: n
              }
          }
          return null
        }, i._parameterCommandTag = 99, i
      }(t.ViewerBase);
    t.ReportViewer = o;
    var r = function(n) {
      function o(t) {
        var o = n.call(this, t) || this;
        return o._parameters = {}, o._errors = [], o._errorsVisible = !1, o.commit = new e.Event, o.validate = new e.Event, e.addClass(o.hostElement, "wj-parameterscontainer"), o._updateErrorsVisible(), o
      }
      return __extends(o, n), o.prototype._setErrors = function(e) {
        this._errors = e, this._updateErrorDiv()
      }, Object.defineProperty(o.prototype, "parameters", {
        get: function() {
          return this._parameters
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "itemsSource", {
        get: function() {
          return this._itemSources
        },
        set: function(e) {
          this._itemSources = e, this._parameters = {}, this._render();
          var t = [];
          (e || []).forEach(function(e) {
            e.error && t.push({
              key: e.name,
              value: e.error
            })
          }), this._setErrors(t)
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._reset = function() {
        this._lastEditedParam = null
      }, o.prototype._setErrorsVisible = function(e) {
        this._errorsVisible = e, this._updateErrorsVisible()
      }, o.prototype._updateErrorsVisible = function() {
        this._errorsVisible ? e.removeClass(this.hostElement, o._errorsHiddenCss) : e.addClass(this.hostElement, o._errorsHiddenCss)
      }, o.prototype.onCommit = function() {
        this.commit.raise(this, new e.EventArgs)
      }, o.prototype.onValidate = function() {
        this.validate.raise(this, new e.EventArgs), this._setErrorsVisible(!1)
      }, o.prototype._deferValidate = function(e, t, n) {
        var o = this;
        null != this._validateTimer && (clearTimeout(this._validateTimer), this._validateTimer = null), this._validateTimer = setTimeout(function() {
          null != t && t(), o.onValidate(), null != n && n(), o._lastEditedParam = e, o._validateTimer = null
        }, 500)
      }, o.prototype._updateErrorDiv = function() {
        for (var e = this._errors || [], t = this.hostElement.querySelectorAll(".error"), n = 0; n < t.length; n++) t[n].parentNode.removeChild(t[n]);
        for (n = 0; n < e.length; n++) {
          var r, i = this.hostElement.querySelector("*[" + o._paramIdAttr + '="' + e[n].key + '"]'),
            a = e[n].value;
          i && ((r = document.createElement("div")).innerHTML = a, r.className = "error", i.appendChild(r))
        }
      }, o.prototype._render = function() {
        var n, r = this;
        if (t._removeChildren(this.hostElement, function(e) {
          if (!r._lastEditedParam || e.getAttribute(o._paramIdAttr) !== r._lastEditedParam) return !0;
          n = e
        }), this._itemSources) {
          this._itemSources.forEach(function(i) {
            if (r._lastEditedParam === i.name) return r._lastEditedParam = null, void(n = null);
            if (!i.hidden) {
              var a = document.createElement("div"),
                s = document.createElement("span"),
                u = null;
              if (a.className = "wj-parametercontainer", s.className = "wj-parameterhead", s.innerHTML = i.prompt || i.name, e.isArray(i.allowedValues)) u = r._generateComboEditor(i);
              else switch (i.dataType) {
                case t._ParameterType.Boolean:
                  u = r._generateBoolEditor(i);
                  break;
                case t._ParameterType.DateTime:
                case t._ParameterType.Time:
                case t._ParameterType.Date:
                  u = r._generateDateTimeEditor(i);
                  break;
                case t._ParameterType.Integer:
                case t._ParameterType.Float:
                  u = r._generateNumberEditor(i);
                  break;
                case t._ParameterType.String:
                  u = r._generateStringEditor(i)
              }
              u && (u.className += " wj-parametercontrol", a.setAttribute(o._paramIdAttr, i.name), a.appendChild(s), a.appendChild(u), n ? r.hostElement.insertBefore(a, n) : r.hostElement.appendChild(a))
            }
          });
          var i = document.createElement("input");
          i.type = "button", i.value = e.culture.Viewer.apply, i.className = "wj-applybutton", t._addEvent(i, "click", function() {
            r._validateParameters() && (r._errors = [], r.onCommit()), r._setErrorsVisible(!0)
          }), this.hostElement.appendChild(i)
        }
      }, o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e), e && (this._reset(), this._render())
      }, o.prototype._validateParameters = function() {
        for (var n, r = this.hostElement.querySelectorAll("textarea"), i = [], a = this.itemsSource, s = 0; s < a.length; s++) {
          var u = a[s];
          n = this.hostElement.querySelector("[" + o._paramIdAttr + '="' + u.name + '"]'), null != u.value && "" !== u.value || u.nullable || u.dataType === t._ParameterType.String && u.allowBlank || this.parameters.hasOwnProperty(u.name) || this.parameters[u.name] || n && i.push({
            key: u.name,
            value: e.culture.Viewer.nullParameterError
          })
        }
        for (s = 0; s < r.length; s++) {
          var c = r.item(s),
            l = !0;
          switch (parseInt(c.getAttribute("data-type"))) {
            case t._ParameterType.Date:
            case t._ParameterType.DateTime:
            case t._ParameterType.Time:
              l = o._checkValueType(c.value, e.isDate);
              break;
            case t._ParameterType.Float:
              l = o._checkValueType(c.value, o._isFloat);
              break;
            case t._ParameterType.Integer:
              l = o._checkValueType(c.value, e.isInt)
          }
          l || i.push({
            key: c.parentElement.id,
            value: e.culture.Viewer.invalidParameterError
          })
        }
        return this._setErrors(i), i.length <= 0
      }, o._isFloat = function(e) {
        return !isNaN(parseFloat(e))
      }, o._checkValueType = function(e, t) {
        for (var n = e.split("\n"), o = 0; o < n.length; o++)
          if (!(n[o].trim().length <= 0 || t(n[o].trim()))) return !1;
        return !0
      }, o.prototype._generateComboEditor = function(n) {
        var o, r, a, s = this,
          u = [],
          c = document.createElement("div"),
          l = [],
          p = n.allowedValues && n.allowedValues.length > 0,
          h = p && n.allowedValues.filter(function(e) {
            return t._isEqual(e.value, n.value)
          }).length > 0;
        n.multiValue ? o = new i(c) : (o = new e.input.ComboBox(c), n.nullable ? u.push({
          name: e.culture.Viewer.parameterNoneItemsSelected,
          value: null
        }) : !p || null != n.value && h || u.push({
          name: e.culture.Viewer.selectParameterValue,
          value: null
        })), o.isEditable = !1, o.displayMemberPath = "name", o.selectedValuePath = "value", o.isDisabled = !p;
        for (d = 0; d < n.allowedValues.length; d++) u.push({
          name: n.allowedValues[d].key,
          value: n.allowedValues[d].value
        });
        if (o.itemsSource = u, n.multiValue) {
          if (r = o, p) {
            if (n.value) {
              for (var d = 0; d < n.value.length; d++)
                for (var _ = 0; _ < r.itemsSource.length; _++)
                  if (t._isEqual(r.itemsSource[_].value, n.value[d])) {
                    l.push(r.itemsSource[_]);
                    break
                  }
              r.checkedItems = l
            }
          } else r.checkedItems = [];
          r.checkedItemsChanged.addHandler(function() {
            s._deferValidate(n.name, function() {
              a = [];
              for (var e = 0; e < r.checkedItems.length; e++) a.push(r.checkedItems[e].value);
              s._updateParameters(n, a)
            }, function() {
              a.length > 0 && !n.nullable && s._validateNullValueOfParameter(c)
            })
          })
        } else {
          o.selectedValue = p && h ? n.value : null;
          var g = !1;
          o.selectedIndexChanged.addHandler(function(t) {
            s._deferValidate(n.name, function() {
              g || (s._updateParameters(n, t.selectedValue), t.selectedValue && t.itemsSource[0].name === e.culture.Viewer.selectParameterValue && setTimeout(function() {
                g = !0;
                var e = t.selectedValue,
                  n = t.selectedIndex;
                t.itemsSource.shift(), t.collectionView.refresh(), t.selectedValue = e, t.selectedIndex = n - 1, g = !1
              }))
            }, function() {
              return s._validateNullValueOfParameter(c)
            })
          })
        }
        return c
      }, o.prototype._updateParameters = function(t, n) {
        var o;
        this.itemsSource.some(function(e) {
          return e.name === t.name && (o = e, !0)
        }), this._parameters[t.name] = o.value = t.value = function(t, n) {
          return n && e.isString(t) ? t.split(/[\r\n]+/) : t
        }(n, t.multiValue)
      }, o.prototype._generateBoolEditor = function(n) {
        var o, r, i = this,
          a = [];
        return n.nullable ? (r = document.createElement("div"), (o = new e.input.ComboBox(r)).isEditable = !1, o.displayMemberPath = "name", o.selectedValuePath = "value", a.push({
          name: "None",
          value: null
        }), a.push({
          name: "True",
          value: !0
        }), a.push({
          name: "False",
          value: !1
        }), o.itemsSource = a, o.selectedValue = n.value, o.selectedIndexChanged.addHandler(function(e) {
          i._deferValidate(n.name, function() {
            return i._updateParameters(n, e.selectedValue)
          })
        })) : ((r = document.createElement("input")).type = "checkbox", r.checked = n.value, t._addEvent(r, "click", function() {
          i._deferValidate(n.name, function() {
            return i._updateParameters(n, r.checked)
          })
        })), r
      }, o.prototype._generateStringEditor = function(e) {
        var t, n = this;
        return e.multiValue ? (t = n._createTextarea(e.value, e.dataType), e.maxLength > 0 && (t.maxLength = e.maxLength)) : ((t = document.createElement("input")).type = "text", e.value && (t.value = e.value), e.maxLength > 0 && (t.maxLength = e.maxLength)), n._bindTextChangedEvent(t, e), t
      }, o.prototype._createTextarea = function(n, r) {
        var i, a = document.createElement("textarea"),
          s = [];
        if (r !== t._ParameterType.DateTime && r !== t._ParameterType.Time && r !== t._ParameterType.Date || (i = o._dateTimeFormat), n && n.length > 0)
          if (i) {
            for (var u = 0; u < n.length; u++) s.push(e.Globalize.formatDate(new Date(n[u]), i));
            a.value = s.join("\n")
          } else a.value = n.join("\n");
        return a.wrap = "off", a.setAttribute("data-type", r.toString()), a
      }, o.prototype._bindTextChangedEvent = function(e, n) {
        var o = this;
        t._addEvent(e, "change,keyup,paste,input", function(t) {
          t.target && t.target.disabled || o._deferValidate(n.name, function() {
            return o._updateParameters(n, e.value)
          }, function() {
            e.value && !n.nullable && o._validateNullValueOfParameter(e)
          })
        })
      }, o.prototype._generateNumberEditor = function(n) {
        var o, r, i = this;
        return n.multiValue ? (o = this._createTextarea(n.value, n.dataType), this._bindTextChangedEvent(o, n)) : (o = document.createElement("div"), (r = new e.input.InputNumber(o)).format = n.dataType === t._ParameterType.Integer ? "n0" : "n2", r.isRequired = !n.nullable, n.value && (r.value = n.dataType === t._ParameterType.Integer ? parseInt(n.value) : parseFloat(n.value)), r.valueChanged.addHandler(function(e) {
          i._deferValidate(n.name, function() {
            return i._updateParameters(n, e.value)
          })
        })), o
      }, o.prototype._generateDateTimeEditor = function(n) {
        var r, i, a = this;
        return n.multiValue ? ((r = this._createTextarea(n.value, n.dataType)).title = o._dateTimeFormat, this._bindTextChangedEvent(r, n)) : (r = document.createElement("div"), n.dataType == t._ParameterType.Date ? i = new e.input.InputDate(r) : n.dataType == t._ParameterType.DateTime ? (i = new e.input.InputDateTime(r)).timeStep = 60 : (i = new e.input.InputTime(r)).step = 60, i.isRequired = !1, null != n.value ? i.value = new Date(n.value) : i.value = null, i.isRequired = !n.nullable, i.valueChanged.addHandler(function() {
          a._deferValidate(n.name, function() {
            return a._updateParameters(n, i.value && i.value.toJSON())
          })
        })), r
      }, o.prototype._validateNullValueOfParameter = function(e) {
        var t = this._errors;
        if (t && t.length)
          for (var n = 0; n < t.length; n++)
            if (t[n].key === e.parentElement.getAttribute(o._paramIdAttr)) {
              var r = e.parentElement.querySelector(".error");
              if (r) {
                e.parentElement.removeChild(r), t.splice(n, 1);
                break
              }
            }
      }, o._paramIdAttr = "param-id", o._errorsHiddenCss = "wj-parametererrors-hidden", o._dateTimeFormat = "MM/dd/yyyy HH:mm", o
    }(e.Control);
    t._ParametersEditor = r;
    var i = function() {
      function t(t) {
        this._selectedAll = !1, this._innerCheckedItemsChanged = !1, this.checkedItemsChanged = new e.Event;
        var n = this,
          o = new e.input.MultiSelect(t);
        n._multiSelect = o, o.checkedItemsChanged.addHandler(n.onCheckedItemsChanged, n), o.isDroppedDownChanged.addHandler(n.onIsDroppedDownChanged, n), o.headerFormatter = function() {
          return n._updateHeader()
        }
      }
      return t.prototype._updateHeader = function() {
        var t = this,
          n = t.checkedItems || [],
          o = [],
          r = t.displayMemberPath;
        if (!n.length) return e.culture.Viewer.parameterNoneItemsSelected;
        if (t._selectedAll) return e.culture.Viewer.parameterAllItemsSelected;
        if (r) {
          for (var i = 0; i < n.length; i++) o[i] = n[i][r];
          return o.join(", ")
        }
        return n.join(", ")
      }, t.prototype.onIsDroppedDownChanged = function() {
        this._multiSelect.isDroppedDown && this._updateSelectedAll()
      }, t.prototype.onCheckedItemsChanged = function(e, t) {
        var n = this;
        n._innerCheckedItemsChanged || (n._selectAllItem ? (n._updateSelectedAll(), n.checkedItemsChanged.raise(n, t)) : n.checkedItemsChanged.raise(n, t))
      }, Object.defineProperty(t.prototype, "isEditable", {
        get: function() {
          return this._multiSelect.isEditable
        },
        set: function(e) {
          this._multiSelect.isEditable = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "isDisabled", {
        get: function() {
          return this._multiSelect.isDisabled
        },
        set: function(e) {
          this._multiSelect.isDisabled = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "displayMemberPath", {
        get: function() {
          return this._multiSelect.displayMemberPath
        },
        set: function(e) {
          this._multiSelect.displayMemberPath = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "selectedValuePath", {
        get: function() {
          return this._multiSelect.selectedValuePath
        },
        set: function(e) {
          this._multiSelect.selectedValuePath = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "itemsSource", {
        get: function() {
          return this._itemsSource
        },
        set: function(t) {
          var n = this,
            o = n.displayMemberPath || "name";
          n._itemsSource = t;
          var r = [];
          t && (t.length > 1 ? (n._selectAllItem = {}, n._selectAllItem[o] = e.culture.Viewer.parameterSelectAllItemText, r.push(n._selectAllItem)) : n._selectAllItem = null, r = r.concat(t)), n._multiSelect.itemsSource = r
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "checkedItems", {
        get: function() {
          var e = this,
            t = [];
          e._multiSelect.checkedItems && (t = e._multiSelect.checkedItems.slice());
          var n = t.indexOf(e._selectAllItem);
          return n > -1 && t.splice(n, 1), t
        },
        set: function(e) {
          var t = this;
          t._multiSelect.checkedItems = e, t._selectedAll = !1, t._updateSelectedAll()
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype._updateSelectedAll = function() {
        var e = this;
        if (e._selectAllItem) {
          var t = e._multiSelect.checkedItems || [],
            n = t.indexOf(e._selectAllItem),
            o = n > -1;
          if (e._selectedAll !== o) return e._selectedAll = o, e._innerCheckedItemsChanged = !0, e._selectedAll ? e._multiSelect.checkedItems = e._multiSelect.itemsSource.slice() : e._multiSelect.checkedItems = [], void(e._innerCheckedItemsChanged = !1);
          e._selectedAll = t && e._itemsSource && t.length - (o ? 1 : 0) === e._itemsSource.length, e._selectedAll !== o && (e._innerCheckedItemsChanged = !0, e._selectedAll ? e._multiSelect.checkedItems = t.concat(e._selectAllItem) : ((t = t.slice()).splice(n, 1), e._multiSelect.checkedItems = t), e._innerCheckedItemsChanged = !1)
        }
      }, t
    }();
    t._MultiSelectEx = i;
    var a = function(t) {
      function r(e, n, o) {
        return t.call(this, e, n, o) || this
      }
      return __extends(r, t), r.prototype._initItems = function() {
        var r = t.prototype._initItems.call(this);
        return r.splice(2, 0, {
          title: e.culture.Viewer.parameters,
          icon: n,
          commandTag: o._parameterCommandTag
        }), r
      }, r
    }(t._HamburgerMenu);
    t._ReportHamburgerMenu = a
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(e) {
    "use strict";
    var t = function(t) {
      function n(e, n) {
        return t.call(this, e, n) || this
      }
      return __extends(n, t), n.prototype._getProductInfo = function() {
        return "QNI5,PdfViewer"
      }, Object.defineProperty(n.prototype, "_innerDocumentSource", {
        get: function() {
          return this._getDocumentSource()
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._getSource = function() {
        return this.filePath ? new e._PdfDocumentSource({
          serviceUrl: this.serviceUrl,
          filePath: this.filePath
        }, this) : null
      }, n
    }(e.ViewerBase);
    e.PdfViewer = t
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(e) {
    "use strict";
    ! function(e) {
      e.Hyperlink = "hyperlink", e.Bookmark = "bookmark", e.Drillthrough = "drillthrough", e.Sort = "sort", e.Toggle = "toggle"
    }(e._ArActionKind || (e._ArActionKind = {}));
    ! function(e) {
      e[e.String = 0] = "String", e[e.DateTime = 1] = "DateTime", e[e.Boolean = 2] = "Boolean", e[e.Integer = 3] = "Integer", e[e.Float = 4] = "Float"
    }(e._ArParameterType || (e._ArParameterType = {}));
    ! function(e) {
      e[e.OK = 0] = "OK", e[e.ExpectValue = 1] = "ExpectValue", e[e.HasOutstandingDependencies = 2] = "HasOutstandingDependencies", e[e.ValuesValidationFailed = 3] = "ValuesValidationFailed", e[e.DynamicValuesUnavailable = 4] = "DynamicValuesUnavailable"
    }(e._ArParameterState || (e._ArParameterState = {}))
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(e) {
    "use strict";
    var t = function(e) {
      function t() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(t, e), t.prototype._processSvgResponse = function(t) {
        t = e.prototype._processSvgResponse.call(this, t);
        var n = (new DOMParser).parseFromString(t, "text/xml");
        return t = (new XMLSerializer).serializeToString(n)
      }, t.prototype._processActionLinks = function(e, t) {
        for (var n = e.querySelectorAll("rect[arsvg\\:data-action-type]"), o = 0; o < n.length; o++) t(n[o])
      }, t
    }(e._Page);
    e._ArPage = t
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n;
    ! function(e) {
      e[e.Rpx = 0] = "Rpx", e[e.Rdf = 1] = "Rdf", e[e.Rdlx = 2] = "Rdlx"
    }(n || (n = {}));
    var o;
    ! function(e) {
      e[e.Image = 3] = "Image", e[e.Pdf = 4] = "Pdf", e[e.Html = 5] = "Html", e[e.Word = 6] = "Word", e[e.Xls = 7] = "Xls", e[e.Xml = 8] = "Xml", e[e.Svg = 9] = "Svg"
    }(o = t._ArDocumentFormat || (t._ArDocumentFormat = {}));
    var r;
    ! function(e) {
      e[e.NotStarted = 0] = "NotStarted", e[e.Rendering = 1] = "Rendering", e[e.Rendered = 2] = "Rendered", e[e.Cancelling = 3] = "Cancelling", e[e.Cancelled = 4] = "Cancelled", e[e.Error = 5] = "Error"
    }(r = t._ArLoadState || (t._ArLoadState = {}));
    var i;
    ! function(e) {
      e[e.InvalidCulture = 0] = "InvalidCulture", e[e.InvalidVersion = 1] = "InvalidVersion", e[e.UnknownReportType = 2] = "UnknownReportType", e[e.NoSuchReport = 3] = "NoSuchReport", e[e.ParametersNotSet = 4] = "ParametersNotSet", e[e.RuntimeIsBusy = 5] = "RuntimeIsBusy", e[e.InternalError = 6] = "InternalError", e[e.ParameterNotExists = 7] = "ParameterNotExists", e[e.NoAcceptableFormats = 8] = "NoAcceptableFormats", e[e.InvalidToken = 9] = "InvalidToken", e[e.UnsupportedFormat = 10] = "UnsupportedFormat", e[e.InvalidSetOfParameters = 11] = "InvalidSetOfParameters", e[e.MethodNotSupported = 12] = "MethodNotSupported", e[e.NoValidLicenseFound = 13] = "NoValidLicenseFound"
    }(i = t._ArErrorCode || (t._ArErrorCode = {}));
    var a = function(n) {
      function a() {
        var e = null !== n && n.apply(this, arguments) || this;
        return e._lifeTime = 6e5, e._drillthroughData = null, e._canChangeRenderMode = !1, e._uid = (new Date).getTime().toString(), e._isDisposed = !1, e._hasOutlines = void 0, e
      }
      return __extends(a, n), a.StateToStatus = function(e) {
        switch (e) {
          case r.NotStarted:
            return t._ExecutionStatus.notFound;
          case r.Rendered:
            return t._ExecutionStatus.completed;
          case r.Rendering:
            return t._ExecutionStatus.rendering;
          case r.Cancelled:
            return t._ExecutionStatus.stopped;
          default:
            throw "Not supported state: " + e
        }
      }, a.ConvertFormat = function(e) {
        switch (e) {
          case "doc":
          case "docx":
            return o.Word;
          case "html":
            return o.Svg;
          case "mhtml":
            return o.Html;
          case "pdf":
            return o.Pdf;
          case "svg":
            return o.Svg;
          case "tiff":
          case "png":
            return o.Image;
          case "xml":
            return o.Xml;
          case "xlsx":
          case "xls":
            return o.Xls;
          default:
            throw "Not supported format: " + e
        }
      }, a.IsError = function(e) {
        return !!(e && e.json && e.json.Error)
      }, Object.defineProperty(a.prototype, "isDisposed", {
        get: function() {
          return this._isDisposed
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "autoRun", {
        get: function() {
          return this._autoRun
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "canChangeRenderMode", {
        get: function() {
          return this._canChangeRenderMode
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "parameters", {
        get: function() {
          return this._parameters || []
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype.getStatus = function() {
        var e = this,
          n = new t._Promise;
        return this._isDisposed ? n.resolve({
          status: t._ExecutionStatus.cleared
        }) : this._token ? (this._ajax(this.serviceUrl + "/GetStatus", {
          method: "POST",
          data: {
            token: this._token
          }
        }).then(function(o) {
          var r = a.StateToStatus(o.json.LoadState),
            i = {
              errorList: o.json.Error ? [o.json.Error.Description] : [],
              pageCount: o.json.AvailablePages,
              hasOutlines: !!e._hasOutlines,
              progress: 0,
              status: r
            };
          r === t._ExecutionStatus.completed && null == e._hasOutlines ? e.getOutlines(!0).then(function(t) {
            i.hasOutlines = e._hasOutlines = (t || []).length > 0, n.resolve(i)
          }) : n.resolve(i)
        }, function(t) {
          n.reject(e._getError(t))
        }), n) : n.resolve({
          status: t._ExecutionStatus.notFound
        })
      }, a.prototype.setPageSettings = function(e) {
        throw t._DocumentSource._abstractMethodException
      }, a.prototype.getBookmark = function(e) {
        var n = new t._Promise,
          o = e.split("|");
        return n.resolve({
          pageIndex: parseInt(o[0], 10) - 1,
          pageBounds: function(e) {
            if (e) {
              var n = e.split(" ");
              return {
                x: t._Unit.convertValue(parseFloat(n[0]), t._UnitType.Dip, t._UnitType.Twip),
                y: t._Unit.convertValue(parseFloat(n[1]), t._UnitType.Dip, t._UnitType.Twip)
              }
            }
          }(o[1])
        })
      }, a.prototype.executeCustomAction = function(e) {
        switch (e.arKind) {
          case t._ArActionKind.Drillthrough:
            var n = JSON.parse(e.data);
            return (new t._Promise).resolve(n);
          case t._ArActionKind.Toggle:
            return this.processOnClick(e.data);
          default:
            return (new t._Promise).reject("Not implemented action: " + e.arKind)
        }
      }, a.prototype.load = function(e) {
        var n = this,
          r = [];
        if (e) {
          Object.keys(e).forEach(function(t) {
            0 === t.indexOf("parameters.") && (r.push({
              Name: t.substring("parameters.".length),
              Value: e[t]
            }), delete e[t])
          })
        }
        if (this._drillthroughData) return r.length && this._mergeParameters(r, this._drillthroughData.Parameters || []), this.loadDrillthroughReport(this._drillthroughData);
        var i = new t._Promise;
        return this._ajax(this.serviceUrl + "/OpenReport", {
          method: "POST",
          data: this._merge(e, {
            acceptedFormats: [o.Svg],
            culture: "en-US",
            lifeTime: this._lifeTime / 1e3,
            reportPath: this.filePath,
            version: 4
          })
        }).then(function(e) {
          n._hasDelayedContent = e.json.HasDelayedContent, n._autoRun = !!e.json.AutoRun, n._documentFormat = e.json.DocumentFormat, n._parameters = (e.json.ParameterCollection || []).map(function(e) {
            return n._convertFromServiceParameter(e)
          }), n._token = e.json.Token, n._isDisposed = !1;
          var o = new Date,
            r = new Date(o.getTime() + n._lifeTime);
          return {
            expiredDateTime: r,
            loadedDateTime: o,
            features: {
              nonPaginated: !1,
              paginated: !0,
              pageSettings: !1,
              textSearchInPaginatedMode: !0
            },
            pageSettings: {
              height: new t._Unit(1056, t._UnitType.Dip),
              width: new t._Unit(816, t._UnitType.Dip),
              leftMargin: new t._Unit(48, t._UnitType.Dip),
              rightMargin: new t._Unit(48, t._UnitType.Dip),
              topMargin: new t._Unit(48, t._UnitType.Dip),
              bottomMargin: new t._Unit(48, t._UnitType.Dip),
              paginated: !0
            },
            status: {
              errorList: e.json.Error ? [e.json.Error.Description] : [],
              expiredDateTime: r,
              pageCount: 0,
              progress: 0,
              status: t._ExecutionStatus.loaded
            }
          }
        }).then(function(e) {
          n.getReportProperty("ChangeRenderModeSupported").then(function(t) {
            if (n._canChangeRenderMode = !!t, r && r.length) {
              var o = n._mergeParameters(r, n._parameters);
              n.setParameters(o).then(function(t) {
                i.resolve(e)
              })
            } else i.resolve(e)
          })
        }).catch(function(e) {
          i.reject(n._getError(e))
        }), i
      }, a.prototype.loadDrillthroughReport = function(n) {
        var o = this,
          r = new t._Promise;
        return this._ajax(this.serviceUrl + "/OpenDrillthroughReport", {
          method: "POST",
          data: {
            token: this._token,
            lifeTime: this._lifeTime / 1e3,
            reportPath: n.ReportName
          }
        }).then(function(i) {
          o.dispose(!1).then(function() {
            o._hasDelayedContent = i.json.HasDelayedContent, o._autoRun = !!i.json.AutoRun, o._documentFormat = i.json.DocumentFormat, o._parameters = (i.json.ParameterCollection || []).map(function(e) {
              return o._convertFromServiceParameter(e)
            }), o._token = i.json.Token, o._isDisposed = !1;
            var a = new Date,
              s = new Date(a.getTime() + o._lifeTime),
              u = {
                expiredDateTime: s,
                loadedDateTime: a,
                features: {
                  nonPaginated: !1,
                  paginated: !0,
                  pageSettings: !1,
                  textSearchInPaginatedMode: !0
                },
                pageSettings: {
                  height: new t._Unit(1056, t._UnitType.Dip),
                  width: new t._Unit(816, t._UnitType.Dip),
                  leftMargin: new t._Unit(48, t._UnitType.Dip),
                  rightMargin: new t._Unit(48, t._UnitType.Dip),
                  topMargin: new t._Unit(48, t._UnitType.Dip),
                  bottomMargin: new t._Unit(48, t._UnitType.Dip),
                  paginated: !0
                },
                status: {
                  errorList: i.json.Error ? [i.json.Error.Description] : [],
                  expiredDateTime: s,
                  pageCount: 0,
                  progress: 0,
                  status: t._ExecutionStatus.loaded
                }
              };
            o.getReportProperty("ChangeRenderModeSupported").then(function(t) {
              o._canChangeRenderMode = !!t, e.isArray(n.Parameters) && n.Parameters.length ? o.setParameters(n.Parameters).then(function(e) {
                r.resolve(u)
              }) : r.resolve(u)
            })
          })
        }, function(e) {
          r.reject(o._getError(e))
        }), r
      }, a.prototype.processOnClick = function(e) {
        var n = this,
          o = new t._Promise;
        return this._ajax(this.serviceUrl + "/ProcessOnClick", {
          method: "POST",
          data: {
            token: this._token,
            data: {
              Action: "toggle",
              Data: e
            }
          }
        }).then(function(e) {
          o.resolve({
            status: t._ExecutionStatus.rendering
          })
        }, function(e) {
          o.reject(n._getError(e))
        }), o
      }, a.prototype.getReportProperty = function(e) {
        return this._ajax(this.serviceUrl + "/GetReportProperty", {
          method: "POST",
          data: {
            token: this._token,
            propertyName: e
          }
        }).then(function(e) {
          return e.json.Error ? null : e.json.PropertyValue
        }).catch(function(e) {
          return null
        })
      }, a.prototype.render = function(e) {
        var n = this;
        return this._ajax(this.serviceUrl + "/RunReport", {
          method: "POST",
          data: {
            token: this._token
          }
        }).then(function(e) {
          return n._isDisposed = !1, {
            errorList: e.json.Error ? [e.json.Error.Description] : [],
            status: t._ExecutionStatus.rendering
          }
        })
      }, a.prototype.setDrillthroughData = function(e) {
        this._drillthroughData = e
      }, a.prototype.dispose = function(e) {
        var n = this;
        return void 0 === e && (e = !0), this._token ? this._ajax(this.serviceUrl + "/CloseReport", {
          method: "POST",
          async: e,
          data: {
            token: this._token
          }
        }).then(function() {
          return n._isDisposed = !0, n._token = null, n._hasOutlines = void 0, {
            status: t._ExecutionStatus.cleared
          }
        }) : new t._Promise
      }, a.prototype.getOutlines = function(e) {
        return void 0 === e && (e = !1), this._getBookmarks(-1, 0, e ? 1 : 100, !e)
      }, a.prototype._getError = function(e) {
        var t = e;
        return a.IsError(t) ? 'ErrorCode: "' + t.json.Error.ErrorCode + '". Description: "' + t.json.Error.Description + '"' : e.responseText
      }, a.prototype._getBookmarks = function(e, n, o, r) {
        var i = this;
        void 0 === r && (r = !0);
        var a = new t._Promise;
        return this._ajax(this.serviceUrl + "/GetBookmarks", {
          method: "POST",
          data: {
            token: this._token,
            parentId: e,
            fromChild: n,
            count: o
          }
        }).then(function(s) {
          var u = (s.json.Bookmarks || []).map(function(e) {
              return {
                caption: e.Name,
                children: e.ChildrenCount > 0 ? function() {
                  return i._getBookmarks(e.ID, 0, o)
                } : null,
                position: {
                  pageBounds: {
                    height: t._Unit.convertValue(e.Size.Width || 0, t._UnitType.Inch, t._UnitType.Twip),
                    width: t._Unit.convertValue(e.Size.Height || 0, t._UnitType.Inch, t._UnitType.Twip),
                    x: t._Unit.convertValue(e.Location.X || 0, t._UnitType.Inch, t._UnitType.Twip),
                    y: t._Unit.convertValue(e.Location.Y || 0, t._UnitType.Inch, t._UnitType.Twip)
                  },
                  pageIndex: e.Page
                },
                target: ""
              }
            }),
            c = n + u.length;
          !r || c >= s.json.ChildrenCount || c >= 1e5 ? a.resolve(u) : i._getBookmarks(e, c, o).then(function(e) {
            a.resolve(u.concat(e))
          })
        }), a
      }, a.prototype.renderToFilter = function(e) {
        var n = this,
          o = new t._Promise;
        return this.getRenderToFilterUrl(e).then(function(t) {
          n._ajax(t + "&WebViewerControlClientId=" + n._uid + (e.outputRange ? "&Page=" + e.outputRange : ""), {
            method: "GET",
            parseResponse: !1
          }).then(function(e) {
            o.resolve(e.xhr)
          }, function(t) {
            a.IsError(t) && t.json.Error.ErrorCode == i.RuntimeIsBusy ? setTimeout(function() {
              return n.renderToFilter(e)
            }, n.getPingTimeout()) : o.reject(t)
          })
        }), o
      }, a.prototype.search = function(e) {
        var n = new t._Promise;
        return this._ajax(this.serviceUrl + "/Search", {
          method: "POST",
          data: {
            token: this._token,
            options: {
              Text: e.text,
              MatchCase: e.matchCase,
              WholeWord: e.wholeWord
            },
            startFrom: {
              PageIndex: -1
            },
            numberOfResults: 100
          }
        }).then(function(e) {
          var o = e.json.SearchResults.map(function(e) {
            return {
              nearText: e.DisplayText,
              positionInNearText: e.TextStart,
              pageIndex: e.PageIndex,
              boundsList: [{
                height: t._Unit.convertValue(e.ItemArea.Height || 0, t._UnitType.Inch, t._UnitType.Twip),
                width: t._Unit.convertValue(e.ItemArea.Width || 0, t._UnitType.Inch, t._UnitType.Twip),
                x: t._Unit.convertValue(e.ItemArea.Left || 0, t._UnitType.Inch, t._UnitType.Twip),
                y: t._Unit.convertValue(e.ItemArea.Top || 0, t._UnitType.Inch, t._UnitType.Twip)
              }]
            }
          });
          n.resolve(o)
        }, function(e) {
          return n.reject(e)
        }), n
      }, a.prototype.setParameters = function(e) {
        var n = this,
          o = new t._Promise;
        return this._ajax(this.serviceUrl + "/SetParameters", {
          method: "POST",
          data: {
            token: this._token,
            parametersSetAtClient: (e || []).map(function(e) {
              return n._convertToServiceParameter(e)
            })
          }
        }).then(function(e) {
          o.resolve(n._parameters = (e.json.ParameterCollection || []).map(function(e) {
            return n._convertFromServiceParameter(e)
          }))
        }).catch(function(e) {
          a.IsError(e) && e.json.Error.ErrorCode == i.ParametersNotSet ? o.resolve(n._parameters = (e.json.ParameterCollection || []).map(function(e) {
            return n._convertFromServiceParameter(e)
          })) : o.reject(e)
        }), o
      }, a.prototype.validateParameter = function(e) {
        return this._ajax(this.serviceUrl + "/ValidateParameter", {
          method: "POST",
          data: {
            token: this._token,
            parametersSetAtClient: this._convertToServiceParameter(e)
          }
        }).then(function(e) {
          return null
        })
      }, a.prototype.getRenderToFilterUrl = function(e) {
        var n = this,
          o = new t._Promise;
        return this._ajax(this.serviceUrl + "/GetRenderedReportLink", {
          method: "POST",
          data: {
            token: this._token
          }
        }).then(function(e) {
          o.resolve(e.json.ReportLink.Uri)
        }, function(t) {
          a.IsError(t) && t.json.Error.ErrorCode == i.RuntimeIsBusy ? setTimeout(function() {
            return n.getRenderToFilterUrl(e)
          }, n.getPingTimeout()) : o.reject(t)
        }), o
      }, a.prototype.getExportedUrl = function(n) {
        var r = this,
          s = a.ConvertFormat(n.format),
          u = function() {
            var e = {
              FileName: r.getFileName()
            };
            return s == o.Pdf && (e.EmbedFonts = "All"), s == o.Image && ("png" === n.format && (e.ImageType = "PNG"), n.outputRange && (e.StartPage = n.outputRange.toString(), e.EndPage = n.outputRange.toString()), n.resolution && (e.DpiX = n.resolution.toString(), e.DpiY = n.resolution.toString())), n.printing && s == o.Pdf && (e.PrintOnOpen = !0), e
          }(),
          c = new t._Promise;
        return this._ajax(this.serviceUrl + "/GetExportedReportLink", {
          method: "POST",
          data: {
            token: this._token,
            format: s,
            exportingParameters: u,
            pageRange: null
          }
        }).then(function(t) {
          c.resolve(t.json.ReportLink.Uri + (e.isNumber(n.outputRange) ? "&outputRange=" + n.outputRange : "") + (n.printing ? "" : "&Attachment=1"))
        }, function(e) {
          a.IsError(e) && e.json.Error.ErrorCode == i.RuntimeIsBusy ? setTimeout(function() {
            return r.getExportedUrl(n)
          }, r.getPingTimeout()) : c.reject(e)
        }), c
      }, a.prototype.getPingTimeout = function() {
        return 1e3
      }, a.prototype.getSupportedExportDescriptions = function() {
        var e = new t._Promise;
        return e.resolve([{
          name: "TIFF image",
          format: "tiff"
        }, {
          name: "Adobe PDF",
          format: "pdf"
        }, {
          name: "Web archive (MHTML)",
          format: "mhtml"
        }, {
          name: "Microsoft Word",
          format: "doc"
        }, {
          name: "Microsoft Excel",
          format: "xls"
        }, {
          name: "XML document",
          format: "xml"
        }]), e
      }, a.prototype.getFeatures = function() {
        throw t._DocumentSource._abstractMethodException
      }, a.prototype._ajax = function(e, n) {
        var o = new t._Promise;
        return n = n || {}, n.urlEncode = !1, this.httpRequest(e, n).then(function(e) {
          var t = {
            xhr: e
          };
          try {
            if (e.responseText && !1 !== n.parseResponse && (t.json = JSON.parse(e.responseText).d), t.json && t.json.Error) throw "";
            o.resolve(t)
          } catch (e) {
            o.reject(t)
          }
        }, function(e) {
          o.reject(e)
        }), o
      }, a.prototype._convertFromServiceParameter = function(e) {
        var n = function(e, n) {
          return null != e && t._ArParameterType.DateTime, e
        };
        return e.Value = n(e.Value, e.ParameterType), e.AvailableValues = (e.AvailableValues || []).map(function(t) {
          return {
            Label: t.Label,
            Value: n(t.Value, e.ParameterType)
          }
        }), e
      }, a.prototype._convertToServiceParameter = function(e) {
        var n = function(e, n) {
          return null != e && t._ArParameterType.DateTime, e
        };
        return e.Value = n(e.Value, e.ParameterType), e.Values && e.Values.forEach(function(t) {
          t.Value = n(t.Value, e.ParameterType)
        }), e
      }, a.prototype._merge = function(e, t) {
        if (e && t) {
          for (var n in e) t[n] = e[n];
          return t
        }
      }, a.prototype._mergeParameters = function(e, t) {
        var n = [],
          o = t.map(function(t) {
            var o = t.Name.toLowerCase();
            n.push(o);
            var r = e.filter(function(e) {
              return o === e.Name.toLowerCase()
            });
            return r.length ? (t.Value = r[0].Value, t) : t
          });
        return o.push.apply(o, e.filter(function(e) {
          return n.indexOf(e.Name.toLowerCase()) < 0
        })), o
      }, a.prototype._parseXml = function(e) {
        return (new DOMParser).parseFromString(e, "text/xml")
      }, a
    }(t._DocumentService);
    t._ArReportService = a
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function o() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (o.prototype = n.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = function(n) {
      function o(e, t) {
        return n.call(this, e, t) || this
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "autoRun", {
        get: function() {
          return this._innerService.autoRun
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "encodeRequestParams", {
        get: function() {
          return !1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "hasParameters", {
        get: function() {
          var e = this._innerService.parameters;
          return e && e.length > 0
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "hasThumbnails", {
        get: function() {
          return !1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_innerService", {
        get: function() {
          return this.service
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.getParameters = function() {
        var e = new t._Promise,
          n = this._convertParameters(this._innerService.parameters);
        return e.resolve(n)
      }, o.prototype.setParameters = function(n) {
        var o = this,
          r = new t._Promise,
          i = [],
          a = {};
        return this._innerService.parameters.forEach(function(e) {
          a[e.Name] = e
        }), Object.keys(n).forEach(function(t) {
          var o;
          i.push(o = {
            AllowEmpty: a[t].AllowEmpty,
            DateOnly: a[t].DateOnly,
            MultiLine: a[t].MultiLine,
            MultiValue: a[t].MultiValue,
            Name: t,
            Nullable: a[t].Nullable,
            ParameterType: a[t].ParameterType
          }), o.MultiValue && e.isArray(n[t]) ? o.Values = n[t].map(function(e) {
            return {
              Value: e
            }
          }) : o.Value = n[t]
        }), this._innerService.setParameters(i).then(function(e) {
          o._convertParameters(e.filter(function(e) {
            return e.State !== t._ArParameterState.OK
          }));
          r.resolve(o._convertParameters(e))
        }), r
      }, o.prototype.print = function(e) {
        this.getExportedUrl({
          format: "pdf",
          printing: !0
        }, !0).then(function(e) {
          if (e) try {
            var t = window.open(e, "_blank");
            t && t.focus()
          } catch (t) {
            window.location.assign(e + "&Attachment=1")
          }
        })
      }, o.prototype._createDocumentService = function(e) {
        return new t._ArReportService(e, this.httpHandler)
      }, o.prototype._getIsDisposed = function() {
        return n.prototype._getIsDisposed.call(this) || this._innerService.isDisposed
      }, o.prototype._updateExecutionInfo = function(e) {
        n.prototype._updateExecutionInfo.call(this, e)
      }, o.prototype._checkIsLoadCompleted = function(e) {
        return e.status === t._ExecutionStatus.completed || e.status === t._ExecutionStatus.stopped
      }, o.prototype._convertParameters = function(e) {
        return e.map(function(e) {
          return {
            allowedValues: e.AvailableValues && e.AvailableValues.length ? e.AvailableValues.map(function(e) {
              return {
                key: e.Label,
                value: e.Value
              }
            }) : void 0,
            allowBlank: e.AllowEmpty,
            dataType: e.ParameterType === t._ArParameterType.Boolean ? t._ParameterType.Boolean : e.ParameterType === t._ArParameterType.DateTime ? t._ParameterType.DateTime : e.ParameterType === t._ArParameterType.Float ? t._ParameterType.Float : e.ParameterType === t._ArParameterType.Integer ? t._ParameterType.Integer : t._ParameterType.String,
            error: e.ExtendedErrorInfo,
            hidden: !1,
            name: e.Name,
            nullable: !!e.Nullable,
            maxLength: 0,
            multiValue: e.MultiValue,
            prompt: e.Prompt,
            value: e.MultiValue && e.Values && e.Values.length ? e.Values.map(function(e) {
              return e.Value
            }) : e.Value
          }
        })
      }, o
    }(t._ReportSourceBase);
    t._ArReportSource = n
  }(e.viewer || (e.viewer = {}))
}(wijmo || (wijmo = {}));
