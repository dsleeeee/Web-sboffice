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
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      return function(t, e) {
        void 0 === t && (t = 0), void 0 === e && (e = 0), this.x = t, this.y = e
      }
    }();
    e.DataPoint = i;
    var n = function(t) {
      function e(e) {
        var i = t.call(this) || this;
        return i._engine = e, i
      }
      return __extends(e, t), Object.defineProperty(e.prototype, "engine", {
        get: function() {
          return this._engine
        },
        enumerable: !0,
        configurable: !0
      }), e
    }(t.CancelEventArgs);
    e.RenderEventArgs = n;
    var r = function(t) {
      function e(e, i, n) {
        var r = t.call(this, e) || this;
        return r._index = i, r._count = n, r
      }
      return __extends(e, t), Object.defineProperty(e.prototype, "index", {
        get: function() {
          return this._index
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "count", {
        get: function() {
          return this._count
        },
        enumerable: !0,
        configurable: !0
      }), e
    }(n);
    e.SeriesRenderingEventArgs = r;
    var s;
    ! function(t) {
      t[t.Png = 0] = "Png", t[t.Jpeg = 1] = "Jpeg", t[t.Svg = 2] = "Svg"
    }(s = e.ImageFormat || (e.ImageFormat = {}));
    var a = function(i) {
      function n() {
        var n = null !== i && i.apply(this, arguments) || this;
        return n._palette = null, n._selectionMode = e.SelectionMode.None, n._defPalette = e.Palettes.standard, n._notifyCurrentChanged = !0, n._legendHost = null, n._needBind = !1, n.rendering = new t.Event, n.rendered = new t.Event, n.selectionChanged = new t.Event, n
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "itemsSource", {
        get: function() {
          return this._items
        },
        set: function(e) {
          this._items != e && (this._cv && (this._cv.currentChanged.removeHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this), this._cv = null), this._items = e, this._cv = t.asCollectionView(e), null != this._cv && (this._cv.currentChanged.addHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this)), this._clearCachedValues(), this._bindChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "collectionView", {
        get: function() {
          return this._cv
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "palette", {
        get: function() {
          return this._palette
        },
        set: function(e) {
          e != this._palette && (this._palette = t.asArray(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "plotMargin", {
        get: function() {
          return this._plotMargin
        },
        set: function(t) {
          t != this._plotMargin && (this._plotMargin = t, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "legend", {
        get: function() {
          return this._legend
        },
        set: function(i) {
          i != this._legend && (this._legend = t.asType(i, e.Legend), null != this._legend && (this._legend._chart = this))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "header", {
        get: function() {
          return this._header
        },
        set: function(e) {
          e != this._header && (this._header = t.asString(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "footer", {
        get: function() {
          return this._footer
        },
        set: function(e) {
          e != this._footer && (this._footer = t.asString(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "headerStyle", {
        get: function() {
          return this._headerStyle
        },
        set: function(t) {
          t != this._headerStyle && (this._headerStyle = t, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "footerStyle", {
        get: function() {
          return this._footerStyle
        },
        set: function(t) {
          t != this._footerStyle && (this._footerStyle = t, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectionMode", {
        get: function() {
          return this._selectionMode
        },
        set: function(i) {
          (i = t.asEnum(i, e.SelectionMode)) != this._selectionMode && (this._selectionMode = i, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemFormatter", {
        get: function() {
          return this._itemFormatter
        },
        set: function(e) {
          e != this._itemFormatter && (this._itemFormatter = t.asFunction(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.onRendered = function(t) {
        this.rendered.raise(this, t)
      }, n.prototype.onRendering = function(t) {
        this.rendering.raise(this, t)
      }, n.prototype.saveImageToFile = function(t) {
        var e, i, n, r;
        t && 0 !== t.length && -1 !== t.indexOf(".") || (t = "image.png"), r = t.split("."), e = r[0], i = r[1].toLowerCase(), n = s[i[0].toUpperCase() + i.substring(1)], this.saveImageToDataUrl(n, function(t) {
          h.downloadImage(t, e, i)
        })
      }, n.prototype.saveImageToDataUrl = function(e, i) {
        var n = t.asEnum(e, s, !1),
          r = s[n].toLowerCase();
        r && r.length && this._exportToImage(r, function(t) {
          i.call(i, t)
        })
      }, n.prototype._exportToImage = function(e, i) {
        var n, r = new Image,
          s = this._currentRenderEngine.element;
        n = h.getDataUri(s), "svg" === e ? i.call(null, n) : (r.onload = function() {
          var n, a = document.createElement("canvas"),
            o = s.parentNode || s,
            h = t.getElementRect(o);
          a.width = h.width, a.height = h.height;
          var l = a.getContext("2d");
          l.fillStyle = "#ffffff", l.fillRect(0, 0, h.width, h.height);
          var c = window.getComputedStyle(o, null).getPropertyValue("padding-left").replace("px", ""),
            u = window.getComputedStyle(o, null).getPropertyValue("padding-top").replace("px", "");
          l.drawImage(r, +c || 0, +u || 0), n = a.toDataURL("image/" + e), i.call(null, n), a = null
        }, r.src = n)
      }, n.prototype.refresh = function(t) {
        void 0 === t && (t = !0), i.prototype.refresh.call(this, t), this.isUpdating || this._refreshChart()
      }, n.prototype.onSelectionChanged = function(t) {
        this.selectionChanged.raise(this, t)
      }, n.prototype.onLostFocus = function(t) {
        this._tooltip && this._tooltip.isVisible && this._tooltip.hide(), i.prototype.onLostFocus.call(this, t)
      }, n.prototype._cvCollectionChanged = function(t, e) {
        this._clearCachedValues(), this._bindChart()
      }, n.prototype._cvCurrentChanged = function(t, e) {
        this._notifyCurrentChanged && this._bindChart()
      }, n.prototype._getColor = function(t) {
        var e = this._defPalette;
        return null != this._palette && this._palette.length > 0 && (e = this._palette), e[t % e.length]
      }, n.prototype._getColorLight = function(t) {
        var e = this._getColor(t);
        return this._getLightColor(e)
      }, n.prototype._getLightColor = function(e) {
        var i = new t.Color(e);
        return null != i && -1 === e.indexOf("-") && (1 == i.a && -1 == e.indexOf("rgba") && (i.a *= .7), e = i.toString()), e
      }, n.prototype._bindChart = function() {
        this._needBind = !0, this.invalidate()
      }, n.prototype._clearCachedValues = function() {}, n.prototype._render = function(i, n) {
        void 0 === n && (n = !0);
        this.hostElement;
        var r = this._getHostSize(),
          s = r.width,
          a = r.height;
        if (0 != s) {
          if (isNaN(s) && (s = e.FlexChart._WIDTH), (0 == a || isNaN(a)) && (a = e.FlexChart._HEIGHT), i.beginRender(), s > 0 && a > 0) {
            i.setViewportSize(s, a), this._rectChart = new t.Rect(0, 0, s, a), this._prepareRender();
            var o = new t.Rect(0, 0, s, a);
            this._chartRectId = "chartRect" + (1e6 * Math.random()).toFixed(), i.addClipRect(o, this._chartRectId), this._renderHeader(i, o), this._renderFooter(i, o), this._renderLegends(i, o), this._renderChart(i, o, n)
          }
          i.endRender()
        }
      }, n.prototype._renderHeader = function(t, i) {
        t.startGroup(e.FlexChart._CSS_HEADER, this._chartRectId), i = this._drawTitle(t, i, this.header, this.headerStyle, !1), t.endGroup()
      }, n.prototype._renderFooter = function(t, i) {
        t.startGroup(e.FlexChart._CSS_FOOTER, this._chartRectId), i = this._drawTitle(t, i, this.footer, this.footerStyle, !0), t.endGroup()
      }, n.prototype._renderLegends = function(i, n) {
        var r, s, a = this.legend,
          o = n.width,
          h = n.height,
          l = a._getPosition(o, h);
        switch (r = a._getDesiredSize(i, l, o, h), l) {
          case e.Position.Right:
            o -= r.width, s = new t.Point(o, n.top + .5 * (h - r.height));
            break;
          case e.Position.Left:
            n.left += r.width, o -= r.width, s = new t.Point(0, n.top + .5 * (h - r.height));
            break;
          case e.Position.Top:
            h -= r.height, s = new t.Point(.5 * (o - r.width), n.top), n.top += r.height;
            break;
          case e.Position.Bottom:
            h -= r.height, s = new t.Point(.5 * (o - r.width), n.top + h)
        }
        n.width = o, n.height = h, r ? (this._legendHost = i.startGroup(e.FlexChart._CSS_LEGEND, this._chartRectId), this._rectLegend = new t.Rect(s.x, s.y, r.width, r.height), this.legend._render(i, s, l, r.width, r.height), i.endGroup()) : (this._legendHost = null, this._rectLegend = null)
      }, n.prototype._prepareRender = function() {}, n.prototype._renderChart = function(t, e, i) {}, n.prototype._performBind = function() {}, n.prototype._getDesiredLegendSize = function(t, e, i, n) {
        return null
      }, n.prototype._renderLegend = function(t, e, i, n, r, s) {}, n.prototype._getHitTestItem = function(t) {
        return null
      }, n.prototype._getHitTestValue = function(t) {
        return null
      }, n.prototype._getHitTestLabel = function(t) {
        return null
      }, n.prototype._refreshChart = function() {
        this._needBind && (this._needBind = !1, this._performBind()), this.hostElement && this._render(this._currentRenderEngine)
      }, n.prototype._drawTitle = function(i, n, r, s, a) {
        var o = e.FlexChart._CSS_TITLE,
          h = a ? e.FlexChart._CSS_FOOTER : e.FlexChart._CSS_HEADER,
          l = null;
        if (a ? this._rectFooter = null : this._rectHeader = null, null != r) {
          var c = null,
            u = null,
            _ = null,
            p = null;
          s && (s.fontSize && (c = s.fontSize), s.foreground && (u = s.foreground), s.fill && (u = s.fill), s.fontFamily && (_ = s.fontFamily), s.halign && (p = s.halign)), i.fontSize = c, i.fontFamily = _, l = i.measureString(r, o, h, s), n.height -= l.height, u || (u = e.FlexChart._FG), i.textFill = u, a ? ("left" == p ? e.FlexChart._renderText(i, r, new t.Point(n.left, n.bottom), 0, 0, o, h, s) : "right" == p ? e.FlexChart._renderText(i, r, new t.Point(n.left + n.width, n.bottom), 2, 0, o, h, s) : e.FlexChart._renderText(i, r, new t.Point(n.left + .5 * n.width, n.bottom), 1, 0, o, h, s), this._rectFooter = new t.Rect(n.left, n.bottom, n.width, l.height)) : (this._rectHeader = new t.Rect(n.left, n.top, n.width, l.height), n.top += l.height, "left" == p ? e.FlexChart._renderText(i, r, new t.Point(n.left, 0), 0, 0, o, h, s) : "right" == p ? e.FlexChart._renderText(i, r, new t.Point(n.left + n.width, 0), 2, 0, o, h, s) : e.FlexChart._renderText(i, r, new t.Point(n.left + .5 * n.width, 0), 1, 0, o, h, s)), i.textFill = null, i.fontSize = null, i.fontFamily = null
        }
        return n
      }, n.prototype.pageToControl = function(t, e) {
        return this._toControl(t, e)
      }, n.prototype._toControl = function(e, i) {
        t.isNumber(e) && t.isNumber(i) ? e = new t.Point(e, i) : e instanceof MouseEvent && (e = t.mouseToPage(e)), t.asType(e, t.Point);
        var n = e.clone(),
          r = this._getHostOffset();
        n.x -= r.x, n.y -= r.y;
        var s = this._getHostComputedStyle();
        if (s) {
          var a = parseInt(s.paddingLeft.replace("px", ""));
          a && !isNaN(a) && (n.x -= a);
          var o = parseInt(s.paddingTop.replace("px", ""));
          o && !isNaN(o) && (n.y -= o)
        }
        return n
      }, n.prototype._highlightItems = function(e, i, n) {
        if (n)
          for (r = 0; r < e.length; r++) t.addClass(e[r], i);
        else
          for (var r = 0; r < e.length; r++) t.removeClass(e[r], i)
      }, n.prototype._parseMargin = function(e) {
        var i = {};
        if (t.isNumber(e) && !isNaN(e)) i.top = i.bottom = i.left = i.right = t.asNumber(e);
        else if (t.isString(e)) {
          var n = t.asString(e).split(" ", 4),
            r = NaN,
            s = NaN,
            a = NaN,
            o = NaN;
          n && (4 == n.length ? (r = parseFloat(n[0]), o = parseFloat(n[1]), s = parseFloat(n[2]), a = parseFloat(n[3])) : 2 == n.length ? (r = s = parseFloat(n[0]), a = o = parseFloat(n[1])) : 1 == n.length && (r = s = a = o = parseFloat(n[1])), isNaN(r) || (i.top = r), isNaN(s) || (i.bottom = s), isNaN(a) || (i.left = a), isNaN(o) || (i.right = o))
        }
        return i
      }, n.prototype._showToolTip = function(t, e) {
        var i = this,
          n = this._tooltip.showDelay;
        i._clearTimeouts(), i.isDisabled || (n > 0 ? i._toShow = setTimeout(function() {
          i._tooltip.show(i.hostElement, t, e), i._tooltip.hideDelay > 0 && (i._toHide = setTimeout(function() {
            i._tooltip.hide()
          }, i._tooltip.hideDelay))
        }, n) : (i._tooltip.show(i.hostElement, t, e), i._tooltip.hideDelay > 0 && (i._toHide = setTimeout(function() {
          i._tooltip.hide()
        }, i._tooltip.hideDelay))))
      }, n.prototype._hideToolTip = function() {
        this._clearTimeouts(), this._tooltip.hide()
      }, n.prototype._clearTimeouts = function() {
        this._toShow && (clearTimeout(this._toShow), this._toShow = null), this._toHide && (clearTimeout(this._toHide), this._toHide = null)
      }, n.prototype._getHostOffset = function() {
        var e = t.getElementRect(this.hostElement);
        return new t.Point(e.left, e.top)
      }, n.prototype._getHostSize = function() {
        var e = new t.Size,
          i = this.hostElement,
          n = this._getHostComputedStyle(),
          r = i.offsetWidth,
          s = i.offsetHeight;
        if (n) {
          var a = parseFloat(n.paddingLeft.replace("px", "")),
            o = parseFloat(n.paddingRight.replace("px", "")),
            h = parseFloat(n.paddingTop.replace("px", "")),
            l = parseFloat(n.paddingBottom.replace("px", ""));
          isNaN(a) || (r -= a), isNaN(o) || (r -= o), isNaN(h) || (s -= h), isNaN(l) || (s -= l);
          var c = parseFloat(n.borderLeftWidth.replace("px", "")),
            u = parseFloat(n.borderRightWidth.replace("px", "")),
            _ = parseFloat(n.borderTopWidth.replace("px", "")),
            p = parseFloat(n.borderBottomWidth.replace("px", ""));
          isNaN(c) || (r -= c), isNaN(u) || (r -= u), isNaN(_) || (s -= _), isNaN(p) || (s -= p), e.width = r, e.height = s
        }
        return e
      }, n.prototype._getHostComputedStyle = function() {
        var t = this.hostElement;
        return t && t.ownerDocument && t.ownerDocument.defaultView ? t.ownerDocument.defaultView.getComputedStyle(this.hostElement) : null
      }, n.prototype._find = function(t, e) {
        for (var i = [], n = 0; n < t.childElementCount; n++) {
          var r = t.childNodes.item(n);
          if (e.indexOf(r.nodeName) >= 0) i.push(r);
          else {
            var s = this._find(r, e);
            if (s.length > 0)
              for (var a = 0; a < s.length; a++) i.push(s[a])
          }
        }
        return i
      }, n._WIDTH = 300, n._HEIGHT = 200, n._SELECTION_THRESHOLD = 15, n
    }(t.Control);
    e.FlexChartBase = a;
    var o = function() {
      function e() {
        this._keys = {}, this._keys.seriesName = null, this._keys.pointIndex = null, this._keys.x = null, this._keys.y = null, this._keys.value = null, this._keys.name = null
      }
      return e.prototype.replace = function(e, i) {
        var n = this;
        return t.format(e, {}, function(t, e, r, s) {
          return n.getValue(e, i, r)
        })
      }, e.prototype.getValue = function(e, i, n) {
        switch (e) {
          case "seriesName":
            return i.series ? i.series.name : "";
          case "pointIndex":
            return null != i.pointIndex ? i.pointIndex.toFixed() : "";
          case "x":
            return n ? t.Globalize.format(i.x, n) : i._xfmt;
          case "y":
            return n ? t.Globalize.format(i.y, n) : i._yfmt;
          case "value":
            return n ? t.Globalize.format(i.value, n) : i.value;
          case "name":
            return i.name
        }
        return i.item && (0 == e.indexOf("item.") && (e = e.substr(5)), e in i.item) ? n ? t.Globalize.format(i.item[e], n) : i.item[e] : ""
      }, e
    }();
    e._KeyWords = o;
    var h = function() {
      function e() {}
      return e.downloadImage = function(e, i, n) {
        var r = document.createElement("a"),
          s = "image/" + n;
        if (navigator.msSaveOrOpenBlob) {
          e = e.substring(e.indexOf(",") + 1);
          var a, o, h, l = atob(e),
            c = [];
          for (a = 0; a < l.length; a += 512) {
            o = l.slice(a, a + 512);
            for (var u = new Array(o.length), _ = 0; _ < o.length; _++) u[_] = o.charCodeAt(_);
            var p = new Uint8Array(u);
            c.push(p)
          }
          h = new Blob(c, {
            type: s
          }), navigator.msSaveOrOpenBlob(h, i + "." + n)
        } else r.download = i + "." + n, r.href = e, document.body.appendChild(r), r.addEventListener("click", function(e) {
          t.removeChild(r)
        }), r.click()
      }, e.getDataUri = function(i) {
        var n, r, s, a, o, h, l, c, u, _, p = document.createElement("div"),
          d = i.cloneNode(!0);
        return "svg" == i.tagName ? (r = (n = t.getElementRect(i.parentNode || i)).width || 0, s = n.height || 0, a = i.viewBox.baseVal && 0 !== i.viewBox.baseVal.width ? i.viewBox.baseVal.width : r, o = i.viewBox.baseVal && 0 !== i.viewBox.baseVal.height ? i.viewBox.baseVal.height : s) : (r = (h = i.getBBox()).x + h.width, s = h.y + h.height, d.setAttribute("transform", d.getAttribute("transform").replace(/translate\(.*?\)/, "")), a = r, o = s, (c = document.createElementNS("http://www.w3.org/2000/svg", "svg")).appendChild(d), d = c), d.setAttribute("version", "1.1"), d.setAttributeNS(e.xmlns, "xmlns", "http://www.w3.org/2000/svg"), d.setAttributeNS(e.xmlns, "xmlns:xlink", "http://www.w3.org/1999/xlink"), d.setAttribute("width", r), d.setAttribute("height", s), d.setAttribute("viewBox", "0 0 " + a + " " + o), t.addClass(d, i.parentNode && i.parentNode.getAttribute("class") || ""), p.appendChild(d), l = e.getStyles(i), (u = document.createElement("style")).setAttribute("type", "text/css"), u.innerHTML = "<![CDATA[\n" + l + "\n]]>", (_ = document.createElement("defs")).appendChild(u), d.insertBefore(_, d.firstChild), "data:image/svg+xml;base64," + window.btoa(window.unescape(encodeURIComponent(e.doctype + p.innerHTML)))
      }, e.getStyles = function(t) {
        var e = "",
          i = document.styleSheets;
        return null == i || 0 === i.length ? null : ([].forEach.call(i, function(i) {
          var n;
          try {
            if (null == i.cssRules || 0 === i.cssRules.length) return !0
          } catch (t) {
            if ("SecurityError" == t.name) return console.log("SecurityError. Can't read: " + i.href), !0
          }
          n = i.cssRules, [].forEach.call(n, function(i) {
            var n, r = i.style;
            if (null == r) return !0;
            try {
              n = t.querySelector(i.selectorText)
            } catch (t) {
              console.warn('Invalid CSS selector "' + i.selectorText + '"', t)
            }
            n ? e += i.selectorText + " { " + r.cssText + " }\n" : i.cssText.match(/^@font-face/) && (e += i.cssText + "\n")
          })
        }), e)
      }, e.doctype = '<?xml version="1.0" standalone="no"?><!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">', e.xmlns = "http://www.w3.org/2000/xmlns/", e
    }()
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function a(n, r) {
        var s = i.call(this, n, null, !0) || this;
        s._areas = [], s._keywords = new e._KeyWords, s._startAngle = 0, s._innerRadius = 0, s._offset = 0, s._reversed = !1, s._isAnimated = !1, s._selectedItemPosition = e.Position.None, s._selectedItemOffset = 0, s._rotationAngle = 0, s._center = new t.Point, s._selectedOffset = new t.Point, s._selectedIndex = -1, s._angles = [], s._colRowLens = [], s._values = [], s._labels = [], s._pels = [], s._sum = 0, s.applyTemplate("wj-control wj-flexchart wj-flexpie", null, null), s._currentRenderEngine = new e._SvgRenderEngine(s.hostElement), s._legend = new e.Legend(s), s._tooltip = new e.ChartTooltip, s._tooltip.content = "<b>{name}</b><br/>{value}", s._tooltip.showDelay = 0, s._lbl = new e.PieDataLabel, s._lbl._chart = s;
        var a = s;
        return s.hostElement.addEventListener("mousemove", function(e) {
          var i = a._tooltip;
          if (i.content && !a.isTouching) {
            var n = a.hitTest(e);
            if (n.distance <= i.threshold) {
              var r = a._getLabelContent(n, a.tooltip.content);
              a._showToolTip(r, new t.Rect(e.clientX, e.clientY, 5, 5))
            } else a._hideToolTip()
          }
        }), s.hostElement.addEventListener("click", function(i) {
          var n = !0;
          if (a.selectionMode == e.SelectionMode.Point) {
            var r = a.hitTest(i),
              s = e.FlexChart._SELECTION_THRESHOLD;
            a.tooltip && a.tooltip.threshold && (s = a.tooltip.threshold), r.distance <= s ? (r.pointIndex != a._selectionIndex && a.selectedItemPosition != e.Position.None && (n = !1), r.pointIndex != a._selectionIndex && a._select(r.pointIndex, !0)) : a._selectedIndex >= 0 && a._select(null)
          }
          if (n && a.isTouching) {
            var o = a._tooltip;
            if (o.content)
              if ((r = a.hitTest(i)).distance <= o.threshold) {
                var h = a._getLabelContent(r, a.tooltip.content);
                a._showToolTip(h, new t.Rect(i.clientX, i.clientY, 5, 5))
              } else a._hideToolTip()
          }
        }), s.hostElement.addEventListener("mouseleave", function(t) {
          a._hideToolTip()
        }), s.initialize(r), s.refresh(), s
      }
      return __extends(a, i), Object.defineProperty(a.prototype, "binding", {
        get: function() {
          return this._binding
        },
        set: function(e) {
          e != this._binding && (this._binding = t.asString(e, !0), this._bindChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "bindingName", {
        get: function() {
          return this._bindingName
        },
        set: function(e) {
          e != this._bindingName && (this._bindingName = t.asString(e, !0), this._bindChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "startAngle", {
        get: function() {
          return this._startAngle
        },
        set: function(e) {
          e != this._startAngle && (this._startAngle = t.asNumber(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "offset", {
        get: function() {
          return this._offset
        },
        set: function(e) {
          e != this._offset && (this._offset = t.asNumber(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "innerRadius", {
        get: function() {
          return this._innerRadius
        },
        set: function(e) {
          e != this._innerRadius && (this._innerRadius = t.asNumber(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "reversed", {
        get: function() {
          return this._reversed
        },
        set: function(e) {
          e != this._reversed && (this._reversed = t.asBoolean(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "selectedItemPosition", {
        get: function() {
          return this._selectedItemPosition
        },
        set: function(e) {
          (e = t.asEnum(e, t.chart.Position, !0)) != this._selectedItemPosition && (this._selectedItemPosition = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "selectedItemOffset", {
        get: function() {
          return this._selectedItemOffset
        },
        set: function(e) {
          e != this._selectedItemOffset && (this._selectedItemOffset = t.asNumber(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "isAnimated", {
        get: function() {
          return this._isAnimated
        },
        set: function(t) {
          t != this._isAnimated && (this._isAnimated = t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "tooltip", {
        get: function() {
          return this._tooltip
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "dataLabel", {
        get: function() {
          return this._lbl
        },
        set: function(t) {
          t != this._lbl && (this._lbl = t, this._lbl && (this._lbl._chart = this))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "selectedIndex", {
        get: function() {
          return this._selectedIndex
        },
        set: function(e) {
          if (e != this._selectedIndex) {
            var i = t.asNumber(e, !0);
            this._select(i, !0)
          }
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype._getLabelsForLegend = function() {
        return this._labels
      }, a.prototype.hitTest = function(t, i) {
        var n = this._toControl(t, i),
          r = new e.HitTestInfo(this, n),
          s = null;
        if (e.FlexChart._contains(this._rectHeader, n)) r._chartElement = e.ChartElement.Header;
        else if (e.FlexChart._contains(this._rectFooter, n)) r._chartElement = e.ChartElement.Footer;
        else if (e.FlexChart._contains(this._rectLegend, n)) r._chartElement = e.ChartElement.Legend, null !== (s = this.legend._hitTest(n)) && s >= 0 && s < this._areas.length && r._setData(null, s);
        else if (e.FlexChart._contains(this._rectChart, n)) {
          for (var a, o = this._areas.length, h = NaN, l = 0; l < o; l++) {
            var c = n.clone();
            if (0 != this._rotationAngle) {
              var u = this._center.x,
                _ = this._center.y,
                p = -u + c.x,
                d = -_ + c.y,
                f = Math.sqrt(p * p + d * d),
                g = Math.atan2(d, p) - this._rotationAngle * Math.PI / 180;
              c.x = u + f * Math.cos(g), c.y = _ + f * Math.sin(g)
            }
            l == this._selectedIndex && (c.x -= this._selectedOffset.x, c.y -= this._selectedOffset.y);
            var y = this._areas[l];
            if (y.contains(c) && (r._setData(null, y.tag), r._dist = 0, l != this._selectedIndex)) break;
            var m = y.distance(c);
            void 0 !== m && (isNaN(h) || m < h) && (h = m, a = y)
          }
          0 !== r._dist && null != a && (r._setData(null, a.tag), r._dist = h), r._chartElement = e.ChartElement.ChartArea
        } else r._chartElement = e.ChartElement.None;
        return r
      }, a.prototype._performBind = function() {
        if (this._initData(), this._cv) {
          this._selectionIndex = this._cv.currentPosition;
          var t = this._cv.items;
          if (t)
            for (var e = t.length, i = 0; i < e; i++) {
              var n = t[i];
              this._sum += Math.abs(this._getBindData(n, this._values, this._labels, this.binding, this.bindingName))
            }
        }
      }, a.prototype._initData = function() {
        this._sum = 0, this._values = [], this._labels = []
      }, a.prototype._getBindData = function(e, i, n, r, s) {
        var a, o = 0;
        r && (a = e[r]);
        o = 0;
        if (t.isNumber(a) ? o = t.asNumber(a) : a && (o = parseFloat(a.toString())), !isNaN(o) && isFinite(o) ? i.push(o) : (o = 0, i.push(o)), s && e) {
          var h = e[s];
          h && (h = h.toString()), n.push(h)
        } else n.push(o.toString());
        return o
      }, a.prototype._render = function(t, n) {
        void 0 === n && (n = !0), this._selectionAnimationID && clearInterval(this._selectionAnimationID), this.onRendering(new e.RenderEventArgs(t)), i.prototype._render.call(this, t, n)
      }, a.prototype._prepareRender = function() {
        this._areas = []
      }, a.prototype._renderChart = function(i, n, r) {
        var s = this._rectChart.clone(),
          o = (new t.Size(s.width, s.height), n.width),
          h = n.height;
        this._pieGroup = i.startGroup("wj-slice-group", null, !0);
        var l = this._parseMargin(this.plotMargin),
          c = this.dataLabel,
          u = c.content && c.position == e.PieLabelPosition.Outside,
          _ = u ? (t.isNumber(c.offset) ? c.offset : 0) + 24 : 0;
        isNaN(l.left) && (l.left = u ? _ : a._MARGIN), isNaN(l.right) && (l.right = u ? _ : a._MARGIN), isNaN(l.top) && (l.top = u ? _ : a._MARGIN), isNaN(l.bottom) && (l.bottom = u ? _ : a._MARGIN), n.top += l.top;
        h = n.height - (l.top + l.bottom);
        n.height = h > 0 ? h : 24, n.left += l.left;
        o = n.width - (l.left + l.right);
        n.width = o > 0 ? o : 24, this._renderData(i, n, this._pieGroup), i.endGroup(), this._rotationAngle = 0, this._highlightCurrent(), this.dataLabel.content && this.dataLabel.position != e.PieLabelPosition.None && this._renderLabels(i), this.onRendered(new e.RenderEventArgs(i))
      }, a.prototype._getDesiredLegendSize = function(e, i, n, r) {
        var s = new t.Size,
          a = this,
          o = new t.Size(n, r),
          h = a._getLabelsForLegend(),
          l = h.length,
          c = 0,
          u = 0;
        this._colRowLens = [];
        for (var _ = 0; _ < l; _++) {
          var p = a._measureLegendItem(e, h[_]);
          i ? (u + p.height > r && (s.height = r, this._colRowLens.push(c), c = 0, u = 0), c < p.width && (c = p.width), u += p.height) : (c + p.width > n && (s.width = n, this._colRowLens.push(u), u = 0, c = 0), u < p.height && (u = p.height), c += p.width)
        }
        return i ? (s.height < u && (s.height = u), this._colRowLens.push(c), s.width = this._colRowLens.reduce(function(t, e) {
          return t + e
        }, 0), s.width > o.width / 2 && (s.width = o.width / 2)) : (s.width < c && (s.width = c), this._colRowLens.push(u), s.height = this._colRowLens.reduce(function(t, e) {
          return t + e
        }, 0), s.height > o.height / 2 && (s.height = o.height / 2)), s
      }, a.prototype._renderLegend = function(e, i, n, r, s, a) {
        for (var o = this, h = o._rectLegend, l = o._getLabelsForLegend(), c = l.length, u = 0, _ = i.clone(), p = 0; p < c; p++) {
          var d = o._measureLegendItem(e, l[p]);
          r ? _.y + d.height > h.top + h.height + 1 && (_.x += this._colRowLens[u], u++, _.y = i.y) : _.x + d.width > h.left + h.width + 1 && (_.y += this._colRowLens[u], u++, _.x = i.x);
          var f = new t.Rect(_.x, _.y, d.width, d.height);
          o._drawLegendItem(e, f, p, l[p]), n.push(f), r ? _.y += d.height : _.x += d.width
        }
      }, a.prototype._renderData = function(t, e, i) {
        this._pels = [], this._angles = [];
        var n = this._sum,
          r = this.startAngle + 180,
          s = this.innerRadius,
          a = this.offset;
        if (n > 0) {
          var o = r * Math.PI / 180,
            h = e.left + .5 * e.width,
            l = e.top + .5 * e.height,
            c = Math.min(.5 * e.width, .5 * e.height);
          this._center.x = h, this._center.y = l;
          var u = Math.max(a, this.selectedItemOffset);
          u > 0 && (a *= c /= 1 + u), this._radius = c;
          var _ = s * c;
          this._renderPie(t, c, _, o, a), this._highlightCurrent()
        }
      }, a.prototype._renderPie = function(t, e, i, n, r) {
        this._renderSlices(t, this._values, this._sum, e, i, n, 2 * Math.PI, r)
      }, a.prototype._getCenter = function() {
        return this._center
      }, a.prototype._renderSlices = function(t, e, i, n, r, s, a, o) {
        for (var h, l, c, u = e.length, _ = s, p = 1 == this.reversed, d = this._center, f = 1 === u ? 360 : 359.9 / 360, g = 0; g < u; g++) {
          l = d.x, c = d.y, h = t.startGroup("wj-slice"), t.fill = this._getColorLight(g), t.stroke = this._getColor(g);
          var y = Math.abs(e[g]),
            m = Math.abs(y - i) < 1e-10 ? a : a * y / i;
          m = Math.min(m, a * f);
          var b = p ? _ - .5 * m : _ + .5 * m;
          o > 0 && m < a && (l += o * Math.cos(b), c += o * Math.sin(b)), this._renderSlice(t, l, c, b, g, n, r, _, m, a), p ? _ -= m : _ += m, t.endGroup(), this._pels.push(h)
        }
      }, a.prototype._renderSlice = function(i, n, r, s, a, o, h, l, c, u) {
        var _ = this,
          p = !!this.reversed;
        if (this._angles.push(s), this.itemFormatter) {
          var d = new e.HitTestInfo(this, new t.Point(n + o * Math.cos(s), r + o * Math.sin(s)), e.ChartElement.SeriesSymbol);
          d._setData(null, a), this.itemFormatter(i, d, function() {
            _._drawSlice(i, a, p, n, r, o, h, l, c)
          })
        } else this._drawSlice(i, a, p, n, r, o, h, l, c)
      }, a.prototype._getSelectedItemOffset = function(t, e) {
        var i = 0,
          n = 0,
          r = 0;
        return t == this._selectedIndex && this.selectedItemOffset > 0 && (r = this.selectedItemOffset), r > 0 && (i = Math.cos(e) * r * this._radius, n = Math.sin(e) * r * this._radius), {
          x: i,
          y: n
        }
      }, a.prototype._renderLabels = function(i) {
        var r = this._areas.length,
          s = this.dataLabel,
          a = s.position,
          o = "wj-data-label",
          h = this._rotationAngle,
          l = s.connectingLine,
          c = s.offset ? s.offset : 0;
        i.stroke = "null", i.fill = "transparent", i.strokeWidth = 1, i.startGroup("wj-data-labels");
        for (var u = 0; u < r; u++) {
          var _ = this._areas[u];
          if (_) {
            var p = _.radius,
              d = _.langle + h,
              f = 1,
              g = 1;
            a == e.PieLabelPosition.Center || a === e.PieLabelPosition.Radial || a === e.PieLabelPosition.Circular ? p *= .5 * (1 + (_.innerRadius || 0) / _.radius) : ((d = n.clampAngle(d)) <= -170 || d >= 170 ? (f = 2, g = 1) : d >= -100 && d <= -80 ? (f = 1, g = 2) : d >= -10 && d <= 10 ? (f = 0, g = 1) : d >= 80 && d <= 100 ? (f = 1, g = 0) : -180 < d && d < -90 ? (f = 2, g = 2) : -90 <= d && d < 0 ? (f = 0, g = 2) : 0 < d && d < 90 ? (f = 0, g = 0) : 90 < d && d < 180 && (f = 2, g = 0), a == e.PieLabelPosition.Inside && (f = 2 - f, g = 2 - g)), d *= Math.PI / 180;
            var y = this._getSelectedItemOffset(u, d),
              m = y.x,
              b = y.y,
              v = p;
            a == e.PieLabelPosition.Outside ? v += c : a == e.PieLabelPosition.Inside && (v -= c);
            var x = _.center.x,
              w = _.center.y,
              S = x - this._center.x,
              P = w - this._center.y;
            if (0 != this._rotationAngle) {
              var C = Math.sqrt(S * S + P * P),
                T = Math.atan2(P, S) + this._rotationAngle * Math.PI / 180;
              x = this._center.x + C * Math.cos(T), w = this._center.y + C * Math.sin(T)
            }
            var M = new t.Point(x + m + v * Math.cos(d), w + b + v * Math.sin(d));
            s.border && a != e.PieLabelPosition.Center && (0 == f ? M.x += 2 : 2 == f && (M.x -= 2), 0 == g ? M.y += 2 : 2 == g && (M.y -= 2));
            var A = new e.HitTestInfo(this, M);
            A._setData(null, u);
            var N = this._getLabelContent(A, s.content),
              L = new e.DataLabelRenderEventArgs(i, A, M, N);
            if (s.onRendering && (s.onRendering(L) ? (N = L.text, M = L.point) : N = null), N) {
              var I, E = 180 * Math.atan2(w - M.y, x - M.x) / Math.PI;
              if (E = (E + 360) % 360, a === e.PieLabelPosition.Radial || a === e.PieLabelPosition.Circular ? (a === e.PieLabelPosition.Radial ? E > 90 && E < 270 && (E += 180) : (E > 180 && E < 360 && (E += 180), E -= 90), (I = e.FlexChart._renderRotatedText(i, N, M, f, g, M, E, o).getBBox()).left = I.x, I.top = I.y) : I = e.FlexChart._renderText(i, N, M, f, g, o), s.border && i.drawRect(I.left - 2, I.top - 2, I.width + 4, I.height + 4, "wj-data-label-border"), l) {
                var k = new t.Point(x + m + p * Math.cos(d), w + b + p * Math.sin(d));
                i.drawLine(M.x, M.y, k.x, k.y, "wj-data-label-line")
              }
            }
          }
        }
        i.endGroup()
      }, a.prototype._drawSlice = function(e, i, n, a, o, h, l, c, u) {
        var _;
        n ? l > 0 ? (0 != u && e.drawDonutSegment(a, o, h, l, c - u, u), (_ = new s(new t.Point(a, o), h, l, c - u, u, this.startAngle)).tag = i, this._areas.push(_)) : (0 != u && e.drawPieSegment(a, o, h, c - u, u), (_ = new r(new t.Point(a, o), h, c - u, u, this.startAngle)).tag = i, this._areas.push(_)) : (l > 0 ? (0 != u && e.drawDonutSegment(a, o, h, l, c, u), (_ = new s(new t.Point(a, o), h, l, c, u, this.startAngle)).tag = i, this._areas.push(_)) : (0 != u && e.drawPieSegment(a, o, h, c, u), (_ = new r(new t.Point(a, o), h, c, u, this.startAngle)).tag = i, this._areas.push(_)), c += u)
      }, a.prototype._measureLegendItem = function(i, n) {
        var r = new t.Size;
        if (r.width = e.Series._LEGEND_ITEM_WIDTH, r.height = e.Series._LEGEND_ITEM_HEIGHT, n) {
          var s = i.measureString(n, e.FlexChart._CSS_LABEL, e.FlexChart._CSS_LEGEND);
          r.width += s.width, r.height < s.height && (r.height = s.height)
        }
        return r.width += 3 * e.Series._LEGEND_ITEM_MARGIN, r.height += 2 * e.Series._LEGEND_ITEM_MARGIN, r
      }, a.prototype._drawLegendItem = function(i, n, r, s) {
        i.strokeWidth = 1;
        var a = e.Series._LEGEND_ITEM_MARGIN,
          o = null,
          h = null;
        null === o && (o = this._getColorLight(r)), null === h && (h = this._getColor(r)), i.fill = o, i.stroke = h;
        var l = n.top + .5 * n.height,
          c = e.Series._LEGEND_ITEM_WIDTH,
          u = e.Series._LEGEND_ITEM_HEIGHT;
        i.drawRect(n.left + a, l - .5 * u, c, u, null), null != s && e.FlexChart._renderText(i, s.toString(), new t.Point(n.left + u + 2 * a, l), 0, 1, e.FlexChart._CSS_LABEL)
      }, a.prototype._getLabelContent = function(e, i) {
        return t.isString(i) ? this._keywords.replace(i, e) : t.isFunction(i) ? i(e) : null
      }, a.prototype._select = function(t, i) {
        if (void 0 === i && (i = !1), this._highlight(!1, this._selectionIndex), this._selectionIndex = t, this.selectionMode == e.SelectionMode.Point) {
          var n = this._cv;
          n && (this._notifyCurrentChanged = !1, n.moveCurrentToPosition(t), this._notifyCurrentChanged = !0)
        }
        null == t ? (this._selectedIndex = -1, this.invalidate()) : this.isAnimated && this.selectedItemPosition != e.Position.None || !(this.selectedItemOffset > 0 || this.selectedItemPosition != e.Position.None) ? this._highlight(!0, this._selectionIndex, i) : (this._selectedIndex = t, this.invalidate()), this.onSelectionChanged()
      }, a.prototype._highlightCurrent = function() {
        if (this.selectionMode != e.SelectionMode.None) {
          var t = -1,
            i = this._cv;
          i && (t = i.currentPosition), this._highlight(!0, t)
        }
      }, a.prototype._highlight = function(t, i, n) {
        if (void 0 === n && (n = !1), this.selectionMode == e.SelectionMode.Point && void 0 !== i && null !== i && i >= 0) {
          var r = this._pels[i];
          if (t) {
            if (r) {
              r.parentNode.appendChild(r);
              var s = this._find(r, ["ellipse"]);
              this._highlightItems(this._find(r, ["path", "ellipse"]), e.FlexChart._CSS_SELECTION, t)
            }
            var a = this._angles[i];
            if (this.selectedItemPosition != e.Position.None && 0 != a) {
              var o = 0;
              this.selectedItemPosition == e.Position.Left ? o = 180 : this.selectedItemPosition == e.Position.Top ? o = -90 : this.selectedItemPosition == e.Position.Bottom && (o = 90);
              var h = o * Math.PI / 180 - a;
              h *= 180 / Math.PI, n && this.isAnimated ? this._animateSelectionAngle(h, .5) : (this._rotationAngle = h, this._pieGroup.transform.baseVal.getItem(0).setRotate(h, this._center.x, this._center.y))
            }
            var l = this.selectedItemOffset;
            if (l > 0 && s && 0 == s.length) {
              var c = this._selectedOffset.x = Math.cos(a) * l * this._radius,
                u = this._selectedOffset.y = Math.sin(a) * l * this._radius;
              r && r.setAttribute("transform", "translate(" + c.toFixed() + "," + u.toFixed() + ")")
            }
            this._selectedIndex = i
          } else r && (r.parentNode.insertBefore(r, r.parentNode.childNodes.item(i)), r.removeAttribute("transform"), this._highlightItems(this._find(r, ["path", "ellipse"]), e.FlexChart._CSS_SELECTION, t)), this._selectedIndex == i && (this._selectedIndex = -1)
        }
      }, a.prototype._animateSelectionAngle = function(i, r) {
        var s = n.clampAngle(this._rotationAngle),
          a = (i = n.clampAngle(i)) - s,
          o = this,
          h = s,
          l = o._pieGroup;
        o._selectionAnimationID && clearInterval(this._selectionAnimationID), this._selectionAnimationID = t.animate(function(t) {
          l == o._pieGroup && (o._rotationAngle = s = h + a * t, o._pieGroup.transform.baseVal.getItem(0).setRotate(s, o._center.x, o._center.y), 1 == t && clearInterval(o._selectionAnimationID), t > .99 && (o.selectedItemOffset > 0 || o.selectedItemPosition != e.Position.None) && o.invalidate())
        }, 1e3 * r)
      }, a.prototype._getHitTestItem = function(t) {
        var e = null,
          i = null;
        return (e = null != this._cv ? this._cv.items : this.itemsSource) && t < e.length && (i = e[t]), i
      }, a.prototype._getHitTestValue = function(t) {
        return this._values[t]
      }, a.prototype._getHitTestLabel = function(t) {
        return this._labels[t]
      }, a._MARGIN = 4, a
    }(e.FlexChartBase);
    e.FlexPie = i;
    var n = function() {
        function t() {}
        return t.clampAngle = function(t, e) {
          void 0 === e && (e = 0);
          var i = (t + 180) % 360 - 180;
          return i < -180 + (e < 0 ? e + 360 : e) && (i += 360), i
        }, t
      }(),
      r = function() {
        function t(t, e, i, r, s) {
          void 0 === s && (s = 0), this._isFull = !1, this._center = t, this._radius = e, this._originAngle = i, this._originSweep = r, r >= 2 * Math.PI && (this._isFull = !0), this._sweep = .5 * r * 180 / Math.PI, this._angle = n.clampAngle(180 * i / Math.PI + this._sweep), this._radius2 = e * e, this._startAngle = s
        }
        return t.prototype.contains = function(t) {
          var e = t.x - this._center.x,
            i = t.y - this._center.y;
          if (e * e + i * i <= this._radius2) {
            var r = 180 * Math.atan2(i, e) / Math.PI,
              s = n.clampAngle(this._angle, this._startAngle) - n.clampAngle(r, this._startAngle);
            if (this._isFull || Math.abs(s) <= this._sweep) return !0
          }
          return !1
        }, t.prototype.distance = function(t) {
          if (this.contains(t)) return 0;
          var e = t.x - this._center.x,
            i = t.y - this._center.y,
            r = e * e + i * i,
            s = 180 * Math.atan2(i, e) / Math.PI,
            a = n.clampAngle(this._angle, this._startAngle) - n.clampAngle(s, this._startAngle);
          return this._isFull || Math.abs(a) <= this._sweep ? Math.sqrt(r) - this._radius : void 0
        }, Object.defineProperty(t.prototype, "center", {
          get: function() {
            return this._center
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "radius", {
          get: function() {
            return this._radius
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "langle", {
          get: function() {
            return this._angle
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "angle", {
          get: function() {
            return this._originAngle
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "sweep", {
          get: function() {
            return this._originSweep
          },
          enumerable: !0,
          configurable: !0
        }), t
      }();
    e._PieSegment = r;
    var s = function() {
      function t(t, e, i, r, s, a) {
        void 0 === a && (a = 0), this._isFull = !1, this._center = t, this._radius = e, this._iradius = i, this._originAngle = r, this._originSweep = s, s >= 2 * Math.PI && (this._isFull = !0), this._sweep = .5 * s * 180 / Math.PI, this._angle = n.clampAngle(180 * r / Math.PI + this._sweep), this._radius2 = e * e, this._iradius2 = i * i, this._startAngle = a
      }
      return t.prototype.contains = function(t) {
        var e = t.x - this._center.x,
          i = t.y - this._center.y,
          r = e * e + i * i;
        if (r >= this._iradius2 && r <= this._radius2) {
          var s = 180 * Math.atan2(i, e) / Math.PI,
            a = n.clampAngle(this._angle, this._startAngle) - n.clampAngle(s, this._startAngle);
          if (this._isFull || Math.abs(a) <= this._sweep) return !0
        }
        return !1
      }, t.prototype.distance = function(t) {
        if (this.contains(t)) return 0
      }, Object.defineProperty(t.prototype, "center", {
        get: function() {
          return this._center
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "radius", {
        get: function() {
          return this._radius
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "langle", {
        get: function() {
          return this._angle
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "angle", {
        get: function() {
          return this._originAngle
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "sweep", {
        get: function() {
          return this._originSweep
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "innerRadius", {
        get: function() {
          return this._iradius
        },
        enumerable: !0,
        configurable: !0
      }), t
    }();
    e._DonutSegment = s
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Stacked = 1] = "Stacked", t[t.Stacked100pc = 2] = "Stacked100pc"
    }(i = e.Stacking || (e.Stacking = {}));
    var n;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Series = 1] = "Series", t[t.Point = 2] = "Point"
    }(n = e.SelectionMode || (e.SelectionMode = {}));
    var r = function(r) {
      function h(s, h) {
        var l = r.call(this, s, null, !0) || this;
        l._series = new t.collections.ObservableArray, l._axes = new e.AxisCollection, l._pareas = new e.PlotAreaCollection, l._interpolateNulls = !1, l._legendToggle = !1, l._symbolSize = 10, l._dataInfo = new a, l.__barPlotter = null, l.__linePlotter = null, l.__areaPlotter = null, l.__bubblePlotter = null, l.__financePlotter = null, l.__funnelPlotter = null, l._plotters = [], l._rotated = !1, l._stacking = i.None, l._xlabels = [], l._xvals = [], l._lblAreas = [], l._colRowLens = [], l._bindingSeparator = ",", l.seriesVisibilityChanged = new t.Event, l.applyTemplate("wj-control wj-flexchart", null, null);
        var c = l;
        return c._series.collectionChanged.addHandler(function() {
          for (var e = c._series, i = 0; i < e.length; i++) {
            var n = t.tryCast(e[i], t.chart.SeriesBase);
            if (!n) throw "chartSeries array must contain SeriesBase objects.";
            n._chart = c, n.axisX && null == n.axisX._chart && (n.axisX._chart = c, c.axes.push(n.axisX)), n.axisY && null == n.axisY._chart && (n.axisY._chart = c, c.axes.push(n.axisY))
          }
          c.invalidate()
        }), l._currentRenderEngine = new e._SvgRenderEngine(l.hostElement), l._hitTester = new e._HitTester(l), l._legend = new e.Legend(l), l._tooltip = new o, l._tooltip.showDelay = 0, l._lbl = new e.DataLabel, l._lbl._chart = l, l._initAxes(), c._axes.collectionChanged.addHandler(function() {
          for (var e = c._axes, i = 0; i < e.length; i++) {
            var n = t.tryCast(e[i], t.chart.Axis);
            if (!n) throw "axes array must contain Axis objects.";
            n._chart = c
          }
          c.invalidate()
        }), c._pareas.collectionChanged.addHandler(function() {
          for (var e = c._pareas, i = 0; i < e.length; i++) {
            var n = t.tryCast(e[i], t.chart.PlotArea);
            if (!n) throw "plotAreas array must contain PlotArea objects.";
            n._chart = c
          }
          c.invalidate()
        }), l._keywords = new e._KeyWords, l.hostElement.addEventListener("click", function(e) {
          var i = c._tooltip;
          if (i.content && c.isTouching) {
            var n = c.hitTest(e);
            if (n.distance <= i.threshold) {
              var r = c._getLabelContent(n, c._tooltip.content);
              c._showToolTip(r, new t.Rect(e.clientX, e.clientY, 5, 5))
            } else c._hideToolTip()
          }
        }), l.hostElement.addEventListener("mousemove", function(e) {
          var i = c._tooltip;
          if (i.content && !c.isTouching) {
            var n = c.hitTest(e);
            if (n.distance <= i.threshold) {
              var r = c._getLabelContent(n, c._tooltip.content);
              c._showToolTip(r, new t.Rect(e.clientX, e.clientY, 5, 5))
            } else c._hideToolTip()
          }
        }), l.hostElement.addEventListener("mouseleave", function(t) {
          c._hideToolTip()
        }), l.hostElement.addEventListener("click", function(t) {
          if (c.selectionMode != n.None) {
            var i = c._hitTestData(t),
              r = e.FlexChart._SELECTION_THRESHOLD;
            c.tooltip && c.tooltip.threshold && (r = c.tooltip.threshold), i.distance <= r && i.series ? c._select(i.series, i.pointIndex) : c.selectionMode == n.Series && (i = c.hitTest(t)).chartElement == e.ChartElement.Legend && i.series ? c._select(i.series, null) : c._select(null, null)
          }!0 === c.legendToggle && (i = c.hitTest(t)).chartElement == e.ChartElement.Legend && i.series && (i.series.visibility == e.SeriesVisibility.Legend ? i.series.visibility = e.SeriesVisibility.Visible : i.series.visibility == e.SeriesVisibility.Visible && (i.series.visibility = e.SeriesVisibility.Legend), c.focus())
        }), l.initialize(h), l
      }
      return __extends(h, r), h.prototype._initAxes = function() {
        this._axisX = new e.Axis(e.Position.Bottom), this._axisY = new e.Axis(e.Position.Left), this._axisX.majorGrid = !1, this._axisX.name = "axisX", this._axisY.majorGrid = !0, this._axisY.majorTickMarks = e.TickMark.None, this._axisY.name = "axisY", this._axisX._chart = this, this._axisY._chart = this, this._axes.push(this._axisX), this._axes.push(this._axisY)
      }, Object.defineProperty(h.prototype, "series", {
        get: function() {
          return this._series
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "axes", {
        get: function() {
          return this._axes
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "axisX", {
        get: function() {
          return this._axisX
        },
        set: function(i) {
          if (i != this._axisX) {
            var n = this._axisX = t.asType(i, e.Axis);
            this.beginUpdate(), n && (void 0 === n.majorGrid && (n.majorGrid = !1), void 0 === n.name && (n.name = "axisX"), void 0 == n.position && (n.position = e.Position.Bottom), n._axisType = e.AxisType.X, n._chart = this), this.endUpdate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "axisY", {
        get: function() {
          return this._axisY
        },
        set: function(i) {
          if (i != this._axisY) {
            var n = this._axisY = t.asType(i, e.Axis);
            this.beginUpdate(), n && (void 0 === n.majorGrid && (n.majorGrid = !0), void 0 === n.name && (n.name = "axisY"), n.majorTickMarks = e.TickMark.None, void 0 == n.position && (n.position = e.Position.Left), n._axisType = e.AxisType.Y, n._chart = this), this.endUpdate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "plotAreas", {
        get: function() {
          return this._pareas
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "binding", {
        get: function() {
          return this._binding
        },
        set: function(e) {
          e != this._binding && (this._binding = t.asString(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "bindingX", {
        get: function() {
          return this._bindingX
        },
        set: function(e) {
          e != this._bindingX && (this._bindingX = t.asString(e, !0), this._bindChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "symbolSize", {
        get: function() {
          return this._symbolSize
        },
        set: function(e) {
          e != this._symbolSize && (this._symbolSize = t.asNumber(e, !1, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "interpolateNulls", {
        get: function() {
          return this._interpolateNulls
        },
        set: function(e) {
          e != this._interpolateNulls && (this._interpolateNulls = t.asBoolean(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "legendToggle", {
        get: function() {
          return this._legendToggle
        },
        set: function(e) {
          e != this._legendToggle && (this._legendToggle = t.asBoolean(e))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "tooltip", {
        get: function() {
          return this._tooltip
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "dataLabel", {
        get: function() {
          return this._lbl
        },
        set: function(i) {
          i != this._lbl && (this._lbl = t.asType(i, e.DataLabel), this._lbl && (this._lbl._chart = this))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "selection", {
        get: function() {
          return this._selection
        },
        set: function(i) {
          i != this._selection && (this._selection = t.asType(i, e.SeriesBase, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), h.prototype.onSeriesVisibilityChanged = function(t) {
        this.seriesVisibilityChanged.raise(this, t)
      }, h.prototype.hitTest = function(t, i) {
        var n = this._toControl(t, i),
          r = new e.HitTestInfo(this, n),
          s = null;
        if (e.FlexChart._contains(this._rectHeader, n)) r._chartElement = e.ChartElement.Header;
        else if (e.FlexChart._contains(this._rectFooter, n)) r._chartElement = e.ChartElement.Footer;
        else if (e.FlexChart._contains(this._rectLegend, n)) r._chartElement = e.ChartElement.Legend, null !== (s = this.legend._hitTest(n)) && s >= 0 && s < this.series.length && (this._getChartType() === e.ChartType.Bar ? r._setData(this.series[this.series.length - 1 - s]) : r._setData(this.series[s]));
        else if (e.FlexChart._contains(this._rectChart, n)) {
          var a = this._hitTestLabels(n);
          if (a) r._chartElement = e.ChartElement.DataLabel, r._dist = 0, r._setDataPoint(a.tag);
          else {
            for (var o = this._hitTester.hitTest(n), h = null, l = null, c = this.series.length - 1; c >= 0; c--)
              if (this.series[c].hitTest !== e.Series.prototype.hitTest) {
                var u = this.series[c].hitTest(t);
                if (u && ((!h || u.distance < h.distance) && (h = u, l = c), 0 === u.distance)) break
              }
            o && o.area ? h && h.distance < o.distance ? r = h : h && h.distance == o.distance && l > o.area.tag.seriesIndex ? r = h : (r._setDataPoint(o.area.tag), r._dist = o.distance) : h && (r = h);
            var _ = !1;
            this.axes.some(function(t) {
              if (e.FlexChart._contains(t._axrect, n)) return t.axisType === e.AxisType.X ? r._chartElement = e.ChartElement.AxisX : r._chartElement = e.ChartElement.AxisY, _ = !0, !0
            }), _ || (e.FlexChart._contains(this._plotRect, n) ? r._chartElement = e.ChartElement.PlotArea : e.FlexChart._contains(this._rectChart, n) && (r._chartElement = e.ChartElement.ChartArea))
          }
        } else r._chartElement = e.ChartElement.None;
        return r
      }, h.prototype.pointToData = function(e, i) {
        return t.isNumber(e) && t.isNumber(i) && (e = new t.Point(e, i)), e instanceof MouseEvent ? (e = new t.Point(e.pageX, e.pageY), e = this._toControl(e)) : e = e.clone(), e.x = this.axisX.convertBack(e.x), e.y = this.axisY.convertBack(e.y), e
      }, h.prototype.dataToPoint = function(e, i) {
        t.isNumber(e) && t.isNumber(i) && (e = new t.Point(e, i)), t.asType(e, t.Point);
        var n = e.clone();
        return n.x = this.axisX.convert(n.x), n.y = this.axisY.convert(n.y), n
      }, h.prototype._copy = function(e, i) {
        if ("series" == e) {
          this.series.clear();
          for (var n = t.asArray(i), r = 0; r < n.length; r++) {
            var s = this._createSeries();
            t.copy(s, n[r]), this.series.push(s)
          }
          return !0
        }
        return !1
      }, h.prototype._createSeries = function() {
        return new e.Series
      }, h.prototype._clearCachedValues = function() {
        for (var t = 0; t < this._series.length; t++) {
          var e = this._series[t];
          null == e.itemsSource && e._clearValues()
        }
      }, h.prototype._performBind = function() {
        if (this._xDataType = null, this._xlabels.splice(0), this._xvals.splice(0), this._cv) {
          var e = this._cv.items;
          if (e) {
            for (var i = e.length, n = 0; n < i; n++) {
              var r = e[n];
              if (this._bindingX) {
                var s = r[this._bindingX];
                t.isNumber(s) ? (this._xvals.push(t.asNumber(s)), this._xDataType = t.DataType.Number) : t.isDate(s) && (this._xvals.push(t.asDate(s).valueOf()), this._xDataType = t.DataType.Date), this._xlabels.push(r[this._bindingX])
              }
            }
            this._xvals.length == i ? this._xlabels.splice(0) : this._xvals.splice(0)
          }
        }
      }, h.prototype._hitTestSeries = function(t, i) {
        var n = this._toControl(t),
          r = new e.HitTestInfo(this, n),
          s = this._hitTester.hitTestSeries(n, i);
        return s && s.area && (r._setDataPoint(s.area.tag), r._chartElement = e.ChartElement.PlotArea, r._dist = s.distance), r
      }, h.prototype._hitTestData = function(t) {
        var i = this._toControl(t),
          n = new e.HitTestInfo(this, i),
          r = this._hitTester.hitTest(i, !0);
        return r && r.area && (n._setDataPoint(r.area.tag), n._dist = r.distance), n
      }, h.prototype._hitTestLabels = function(t) {
        for (var e = null, i = this._lblAreas.length, n = 0; n < i; n++)
          if (this._lblAreas[n].contains(t)) {
            e = this._lblAreas[n];
            break
          }
        return e
      }, h._dist2 = function(t, e) {
        var i = t.x - e.x,
          n = t.y - e.y;
        return i * i + n * n
      }, h._dist = function(t, i, n) {
        return Math.sqrt(e.FlexChart._distToSegmentSquared(t, i, n))
      }, h._distToSegmentSquared = function(i, n, r) {
        var s = e.FlexChart._dist2(n, r);
        if (0 == s) return e.FlexChart._dist2(i, n);
        var a = ((i.x - n.x) * (r.x - n.x) + (i.y - n.y) * (r.y - n.y)) / s;
        return a < 0 ? e.FlexChart._dist2(i, n) : a > 1 ? e.FlexChart._dist2(i, r) : e.FlexChart._dist2(i, new t.Point(n.x + a * (r.x - n.x), n.y + a * (r.y - n.y)))
      }, h.prototype._isRotated = function() {
        return this._getChartType() == e.ChartType.Bar ? !this._rotated : this._rotated
      }, h.prototype._getChartType = function() {
        return null
      }, h.prototype._prepareRender = function() {
        this._hitTester.clear()
      }, h.prototype._renderChart = function(i, n, r) {
        var s = this._rectChart.clone(),
          a = new t.Size(s.width, s.height);
        n.width, n.height;
        (m = this._getPlotter(null)).stacking = this._stacking, this._curPlotter != m && (this._curPlotter && this._curPlotter.unload(), this._curPlotter = m), m.load();
        var o = this._isRotated();
        this._dataInfo.analyse(this._series, o, m.stacking, this._xvals.length > 0 ? this._xvals : null, this.axisX._getLogBase() > 0, this.axisY._getLogBase() > 0);
        var h = m.adjustLimits(this._dataInfo, n.clone());
        if (o) {
          var l = this._dataInfo.getDataTypeX();
          l || (l = this._xDataType), this.axisX._updateActualLimits(this._dataInfo.getDataTypeY(), h.left, h.right), this.axisY._updateActualLimits(l, h.top, h.bottom, this._xlabels, this._xvals)
        } else {
          var c = this._dataInfo.getDataTypeX();
          c || (c = this._xDataType), this.axisX._updateActualLimits(c, h.left, h.right, this._xlabels, this._xvals), this.axisY._updateActualLimits(this._dataInfo.getDataTypeY(), h.top, h.bottom)
        }
        var u = this._getAxes();
        if (this._updateAuxAxes(u, o), this._layout(n, a, i), i.startGroup(e.FlexChart._CSS_PLOT_AREA), i.fill = "transparent", i.stroke = null, this.plotAreas.length > 0)
          for (f = 0; f < this.plotAreas.length; f++) this.plotAreas[f]._render(i);
        else {
          var _ = this._plotRect;
          i.drawRect(_.left, _.top, _.width, _.height)
        }
        i.endGroup();
        var p = this._series.length;
        this._clearPlotters();
        for (var d = {}, f = 0; f < p; f++) {
          var g = this._series[f];
          if (((S = g.visibility) == e.SeriesVisibility.Visible || S == e.SeriesVisibility.Plot) && g.getValues(0)) {
            var y = g._getAxisY(),
              m = this._getPlotter(g);
            if (!y || y == this.axisY || m instanceof e._BarPlotter) m.seriesCount++;
            else {
              var b = y._uniqueId;
              d[b] ? d[b].count += 1 : d[b] = {
                count: 1,
                index: 0
              }
            }
          }
        }
        if (this.onRendering(new e.RenderEventArgs(i)), this._getChartType() !== e.ChartType.Funnel)
          for (f = 0; f < u.length; f++) {
            var v = u[f];
            w = v.axisType == e.AxisType.X ? i.startGroup(e.FlexChart._CSS_AXIS_X, this._chartRectId) : i.startGroup(e.FlexChart._CSS_AXIS_Y, this._chartRectId), v._hostElement = r ? w : v._hostElement, v._render(i), i.endGroup()
          }
        i.startGroup("wj-series-group"), this._plotrectId = "plotRect" + (1e6 * Math.random()).toFixed(), i.addClipRect(this._plotRect, this._plotrectId);
        for (f = 0; f < p; f++) {
          var x = this._series[f];
          x._pointIndexes = [];
          m = this._getPlotter(x);
          x._plotter = m;
          var w = i.startGroup(x.cssClass, m.clipping ? this._plotrectId : null);
          x._hostElement = r ? w : x._hostElement;
          var S = x.visibility,
            P = x.axisX,
            C = x.axisY;
          if (P || (P = this.axisX), C || (C = this.axisY), S == e.SeriesVisibility.Visible || S == e.SeriesVisibility.Plot) {
            var T, M, A = d[C._uniqueId];
            A ? (T = A.index, M = A.count, A.index++, x.onRendering(i, T, M) || m.plotSeries(i, P, C, x, this, T, M)) : (T = m.seriesIndex, M = m.seriesCount, m.seriesIndex++, x.onRendering(i, T, M) || m.plotSeries(i, P, C, x, this, T, M)), x.onRendered(i)
          }
          i.endGroup()
        }
        i.endGroup(), this._lblAreas = [], this.dataLabel.content && this.dataLabel.position != e.LabelPosition.None && this._renderLabels(i), this._highlightCurrent(), this.onRendered(new e.RenderEventArgs(i))
      }, h.prototype._getDesiredLegendSize = function(i, n, r, s) {
        var a = new t.Size,
          o = this.series,
          h = o.length,
          l = 0,
          c = 0;
        this._colRowLens = [];
        for (var u = 0; u < h; u++) {
          var _ = t.tryCast(o[u], t.chart.SeriesBase),
            p = _.visibility;
          if (_.name && p != e.SeriesVisibility.Hidden && p != e.SeriesVisibility.Plot)
            for (var d = _.legendItemLength(), f = 0; f < d; f++) {
              var g = _.measureLegendItem(i, f);
              n ? (l + g.height > s && (a.height = s, this._colRowLens.push(c), c = 0, l = 0), c < g.width && (c = g.width), l += g.height) : (c + g.width > r && (a.width = r, this._colRowLens.push(l), l = 0, c = 0), l < g.height && (l = g.height), c += g.width)
            }
        }
        return n ? (a.height < l && (a.height = l), this._colRowLens.push(c), a.width = this._colRowLens.reduce(function(t, e) {
          return t + e
        }, 0), a.width > r / 2 && (a.width = r / 2)) : (a.width < c && (a.width = c), this._colRowLens.push(l), a.height = this._colRowLens.reduce(function(t, e) {
          return t + e
        }, 0), a.height > s / 2 && (a.height = s / 2)), a
      }, h.prototype._renderLegend = function(i, n, r, s, a, o) {
        var h, l = this.series,
          c = l.length,
          u = n.clone(),
          _ = 0;
        if (this._getChartType() === e.ChartType.Bar)
          for (p = c - 1; p >= 0; p--) h = t.tryCast(l[p], t.chart.SeriesBase), _ = this._renderLegendElements(i, h, n, u, r, s, a, o, _);
        else
          for (var p = 0; p < c; p++) h = t.tryCast(l[p], t.chart.SeriesBase), _ = this._renderLegendElements(i, h, n, u, r, s, a, o, _)
      }, h.prototype._renderLegendElements = function(i, n, r, s, a, o, h, l, c) {
        var u = this._rectLegend,
          _ = c;
        if (!n) return _;
        var p = n.visibility;
        if (!n.name || p == e.SeriesVisibility.Hidden || p == e.SeriesVisibility.Plot) return n._legendElement = null, a.push(null), _;
        var d = n.legendItemLength(),
          f = i.startGroup(n.cssClass);
        p == e.SeriesVisibility.Legend ? (f.setAttribute("opacity", "0.5"), n._legendElement = f) : p == e.SeriesVisibility.Visible ? n._legendElement = f : n._legendElement = null;
        for (var g = 0; g < d; g++) {
          var y = n.measureLegendItem(i, g);
          o ? s.y + y.height > u.top + u.height + 1 && (s.x += this._colRowLens[_], _++, s.y = r.y) : s.x + y.width > u.left + u.width + 1 && (s.y += this._colRowLens[_], _++, s.x = r.x);
          var m = new t.Rect(s.x, s.y, y.width, y.height);
          p != e.SeriesVisibility.Legend && p != e.SeriesVisibility.Visible || n.drawLegendItem(i, m, g), a.push(m), o ? s.y += y.height : s.x += y.width
        }
        return i.endGroup(), _
      }, h.prototype._renderLabels = function(t) {
        var e = this.series,
          i = e.length;
        t.stroke = "null", t.fill = "transparent", t.strokeWidth = 1;
        var n = h._CSS_DATA_LABELS;
        t.startGroup(n);
        for (var r = 0; r < i; r++) {
          var s = e[r],
            a = this._hitTester._map[r];
          a && s._renderLabels(t, a, this, this._lblAreas)
        }
        t.endGroup()
      }, h.prototype._getAxes = function() {
        for (var t = [this.axisX, this.axisY], e = this.series.length, i = 0; i < e; i++) {
          var n = this.series[i],
            r = n.axisX;
          r && -1 === t.indexOf(r) && t.push(r);
          var s = n.axisY;
          s && -1 === t.indexOf(s) && t.push(s)
        }
        return t
      }, h.prototype._clearPlotters = function() {
        for (var t = this._plotters.length, e = 0; e < t; e++) this._plotters[e].clear()
      }, h.prototype._initPlotter = function(t) {
        t.chart = this, t.dataInfo = this._dataInfo, t.hitTester = this._hitTester, this._plotters.push(t)
      }, Object.defineProperty(h.prototype, "_barPlotter", {
        get: function() {
          return null === this.__barPlotter && (this.__barPlotter = new e._BarPlotter, this._initPlotter(this.__barPlotter)), this.__barPlotter
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "_linePlotter", {
        get: function() {
          return null === this.__linePlotter && (this.__linePlotter = new e._LinePlotter, this._initPlotter(this.__linePlotter)), this.__linePlotter
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "_areaPlotter", {
        get: function() {
          return null === this.__areaPlotter && (this.__areaPlotter = new e._AreaPlotter, this._initPlotter(this.__areaPlotter)), this.__areaPlotter
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "_bubblePlotter", {
        get: function() {
          return null === this.__bubblePlotter && (this.__bubblePlotter = new e._BubblePlotter, this._initPlotter(this.__bubblePlotter)), this.__bubblePlotter
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "_financePlotter", {
        get: function() {
          return null === this.__financePlotter && (this.__financePlotter = new e._FinancePlotter, this._initPlotter(this.__financePlotter)), this.__financePlotter
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(h.prototype, "_funnelPlotter", {
        get: function() {
          return null === this.__funnelPlotter && (this.__funnelPlotter = new e._FunnelPlotter, this._initPlotter(this.__funnelPlotter)), this.__funnelPlotter
        },
        enumerable: !0,
        configurable: !0
      }), h.prototype._getPlotter = function(t) {
        var i = this._getChartType(),
          n = !1;
        if (t) {
          var r = t._getChartType();
          null !== r && void 0 !== r && r != i && (i = r, n = !0)
        }
        var s;
        switch (i) {
          case e.ChartType.Column:
            this._barPlotter.isVolume = !1, this._barPlotter.width = .7, s = this._barPlotter;
            break;
          case e.ChartType.Bar:
            this._barPlotter.rotated = !this._rotated, this._barPlotter.isVolume = !1, this._barPlotter.width = .7, s = this._barPlotter;
            break;
          case e.ChartType.Line:
            this._linePlotter.hasSymbols = !1, this._linePlotter.hasLines = !0, this._linePlotter.isSpline = !1, s = this._linePlotter;
            break;
          case e.ChartType.Scatter:
            this._linePlotter.hasSymbols = !0, this._linePlotter.hasLines = !1, this._linePlotter.isSpline = !1, s = this._linePlotter;
            break;
          case e.ChartType.LineSymbols:
            this._linePlotter.hasSymbols = !0, this._linePlotter.hasLines = !0, this._linePlotter.isSpline = !1, s = this._linePlotter;
            break;
          case e.ChartType.Area:
            this._areaPlotter.isSpline = !1, s = this._areaPlotter;
            break;
          case e.ChartType.Bubble:
            s = this._bubblePlotter;
            break;
          case e.ChartType.Candlestick:
            (a = this._financePlotter).isCandle = !0, a.isEqui = !1, a.isArms = !1, a.isVolume = !1, s = a;
            break;
          case e.ChartType.HighLowOpenClose:
            var a = this._financePlotter;
            a.isCandle = !1, a.isEqui = !1, a.isArms = !1, a.isVolume = !1, s = a;
            break;
          case e.ChartType.Spline:
            this._linePlotter.hasSymbols = !1, this._linePlotter.hasLines = !0, this._linePlotter.isSpline = !0, s = this._linePlotter;
            break;
          case e.ChartType.SplineSymbols:
            this._linePlotter.hasSymbols = !0, this._linePlotter.hasLines = !0, this._linePlotter.isSpline = !0, s = this._linePlotter;
            break;
          case e.ChartType.SplineArea:
            this._areaPlotter.isSpline = !0, s = this._areaPlotter;
            break;
          case e.ChartType.Funnel:
            s = this._funnelPlotter;
            break;
          default:
            throw "Invalid chart type."
        }
        return s.rotated = this._rotated, i == e.ChartType.Bar && (s.rotated = !s.rotated), n && (s.rotated = this._isRotated()), s
      }, h.prototype._layout = function(t, e, i) {
        this.plotAreas.length > 0 ? this._layoutMultiple(t, e, i) : this._layoutSingle(t, e, i)
      }, h.prototype._layoutSingle = function(i, n, r) {
        for (var s = i.width, a = i.height, o = new t.Size(s, .75 * a), h = new t.Size(a, .75 * s), l = 0, c = 0, u = s, _ = a, p = 0, d = 0, f = s, g = a, y = this._getAxes(), m = 0; m < y.length; m++) {
          var b = (C = y[m]).origin,
            v = C._getPosition();
          if (C.axisType == e.AxisType.X) {
            (S = C._getHeight(r, s)) > o.height && (S = o.height), C._desiredSize = new t.Size(o.width, S);
            var x = C._hasOrigin = t.isNumber(b) && b > this.axisY._getMinNum() && b < this.axisY._getMaxNum(),
              w = Math.min(.25 * s, C._annoSize.width);
            if (v == e.Position.Bottom)
              if (l = Math.max(l, .5 * w), u = Math.min(u, s - .5 * w), x) {
                M = this._convertY(b, d, g);
                g -= Math.max(0, M + S - g)
              } else g -= S;
            else if (v == e.Position.Top)
              if (l = Math.max(l, .5 * w), u = Math.min(u, s - .5 * w), x) {
                M = this._convertY(b, d, g);
                d += Math.max(0, d - (M - S))
              } else d += S
          } else if (C.axisType == e.AxisType.Y) {
            var S = C._getHeight(r, a);
            S > h.height && (S = h.height), C._desiredSize = new t.Size(h.width, S);
            x = C._hasOrigin = t.isNumber(b) && b > this.axisX._getMinNum() && b < this.axisX._getMaxNum();
            if (v == e.Position.Left)
              if (C._actualAngle < 0 ? _ = Math.min(_, a - C._annoSize.height) : C._actualAngle > 0 ? c = Math.max(c, C._annoSize.height) : (c = Math.max(c, C._annoSize.height), _ = Math.min(_, a - C._annoSize.height)), x) {
                N = this._convertX(b, p, f);
                p += Math.max(0, p - (N - S))
              } else p += S;
            else if (v == e.Position.Right)
              if (C._actualAngle > 0 ? _ = Math.min(_, a - C._annoSize.height) : C._actualAngle < 0 ? c = Math.max(c, C._annoSize.height) : (c = Math.max(c, C._annoSize.height), _ = Math.min(_, a - C._annoSize.height)), x) {
                N = this._convertX(b, p, f);
                f -= Math.max(0, N + S - f)
              } else f -= S
          }
        }
        var P = this._parseMargin(this.plotMargin);
        l = p = isNaN(P.left) ? Math.max(l, p) + i.left : P.left, u = f = isNaN(P.right) ? Math.min(u, f) + i.left : n.width - P.right, c = d = isNaN(P.top) ? Math.max(c, d) + i.top : P.top, _ = g = isNaN(P.bottom) ? Math.min(_, g) + i.top : n.height - P.bottom, s = Math.max(1, u - l), a = Math.max(1, _ - c), this._plotRect = new t.Rect(l, c, s, a), _ <= c && (g = d + 1), r.stroke = null;
        for (m = 0; m < y.length; m++) {
          var C = y[m],
            b = C.origin,
            v = C._getPosition();
          if (C.axisType == e.AxisType.X) {
            var T;
            if (C._hasOrigin) {
              var M = this._convertY(b, this._plotRect.top, this._plotRect.bottom);
              v == e.Position.Bottom ? (T = new t.Rect(l, M, s, C._desiredSize.height), g += Math.max(0, T.bottom - this._plotRect.bottom)) : v == e.Position.Top ? (T = new t.Rect(l, M - C._desiredSize.height, s, C._desiredSize.height), d -= Math.max(0, this._plotRect.top - T.top)) : T = new t.Rect(l, M, s, 1)
            } else v == e.Position.Bottom ? (T = new t.Rect(l, g, s, C._desiredSize.height), g += C._desiredSize.height) : v == e.Position.Top ? (T = new t.Rect(l, d - C._desiredSize.height, s, C._desiredSize.height), d -= C._desiredSize.height) : T = new t.Rect(l, d, s, 1);
            C._layout(T, this._plotRect)
          } else if (C.axisType == e.AxisType.Y) {
            var A;
            if (C._hasOrigin) {
              var N = this._convertX(b, this._plotRect.left, this._plotRect.right);
              v == e.Position.Left ? (A = new t.Rect(N - C._desiredSize.height, c, a, C._desiredSize.height), p -= C._desiredSize.height) : v == e.Position.Right ? (A = new t.Rect(N, c, a, C._desiredSize.height), f += C._desiredSize.height) : A = new t.Rect(N, c, a, 1)
            } else v == e.Position.Left ? (A = new t.Rect(p - C._desiredSize.height, c, a, C._desiredSize.height), p -= C._desiredSize.height) : v == e.Position.Right ? (A = new t.Rect(f, c, a, C._desiredSize.height), f += C._desiredSize.height) : A = new t.Rect(p, c, a, 1);
            C._layout(A, this._plotRect)
          }
        }
      }, h.prototype._layoutMultiple = function(i, n, r) {
        for (var a = i.width, o = i.height, h = [], l = [], c = this._getAxes(), u = c.length, _ = 0; _ < u; _++)
          if ((T = c[_])._plotrect = null, T.axisType == e.AxisType.X) {
            for (var p = T.plotArea ? T.plotArea.column : 0; h.length <= p;) h.push(new s);
            h[p].axes.push(T)
          } else if (T.axisType == e.AxisType.Y) {
            for (var d = T.plotArea ? T.plotArea.row : 0; l.length <= d;) l.push(new s);
            l[d].axes.push(T)
          }
        for (var f = h.length, g = l.length, y = new t.Size(a, .3 * o), m = new t.Size(o, .3 * a), b = 0, v = 0, x = a, w = o, S = 0; S < f; S++) {
          (O = h[S]).right = a, O.bottom = o;
          for (_ = 0; _ < O.axes.length; _++) {
            var P = (T = O.axes[_])._getHeight(r, T.axisType == e.AxisType.X ? a : o);
            P > y.height && (P = y.height);
            var C = new t.Size(y.width, P);
            T._desiredSize = C, 0 == S && (O.left = Math.max(O.left, .5 * T._annoSize.width)), S == f - 1 && (O.right = Math.min(O.right, a - .5 * T._annoSize.width)), (F = T._getPosition()) == e.Position.Bottom ? O.bottom -= C.height : F == e.Position.Top && (O.top += C.height)
          }
        }
        for (Y = 0; Y < g; Y++) {
          (O = l[Y]).right = a, O.bottom = o;
          for (_ = 0; _ < O.axes.length; _++) {
            var T = O.axes[_],
              M = new t.Size(m.width, T._getHeight(r, T.axisType == e.AxisType.X ? a : o));
            M.height > m.height && (M.height = m.height), T._desiredSize = M, 0 == Y && (O.top = Math.max(O.top, .5 * T._annoSize.width)), Y == g - 1 && (O.bottom = Math.min(O.bottom, o - .5 * T._annoSize.width)), (F = T._getPosition()) == e.Position.Left ? O.left += M.height : F == e.Position.Right && (O.right -= M.height)
          }
        }
        for (var A = 0, N = 0, L = a, I = o, S = 0; S < f; S++) {
          O = h[S];
          A = Math.max(A, O.left), N = Math.max(N, O.top), L = Math.min(L, O.right), I = Math.min(I, O.bottom)
        }
        for (Y = 0; Y < g; Y++) {
          O = l[Y];
          A = Math.max(A, O.left), N = Math.max(N, O.top), L = Math.min(L, O.right), I = Math.min(I, O.bottom)
        }
        A = b = Math.max(b, A), L = x = Math.min(x, L), N = v = Math.max(v, N), I = w = Math.min(w, I), this._plotRect = new t.Rect(b, v, x - b, w - v);
        for (var E = this._plotRect.clone(), k = b, D = this.plotAreas._calculateWidths(this._plotRect.width, f), S = 0; S < f; S++) {
          I = w, N = v;
          for (var O = h[S], R = D[S], _ = 0; _ < O.axes.length; _++) {
            var j, F = (T = O.axes[_])._getPosition(),
              V = new t.Rect(k, E.top, R, E.height);
            F == e.Position.Bottom ? (j = new t.Rect(k, I, R, T._desiredSize.height), I += T._desiredSize.height) : F == e.Position.Top && (j = new t.Rect(k, N - T._desiredSize.height, R, T._desiredSize.height), N -= T._desiredSize.height), T._layout(j, V)
          }
          for (_ = 0; _ < this.plotAreas.length; _++)(G = this.plotAreas[_]).column == S && G._setPlotX(k, R);
          k += R
        }
        for (var X = v, B = this.plotAreas._calculateHeights(this._plotRect.height, g), Y = 0; Y < g; Y++) {
          A = b, L = x;
          for (var O = l[Y], z = B[Y], _ = 0; _ < O.axes.length; _++) {
            var F = (T = O.axes[_])._getPosition(),
              V = new t.Rect(E.left, X, E.width, z);
            T._plotrect ? (V.left = T._plotrect.left, V.width = T._plotrect.width) : D && D.length > 0 && (V.width = D[0]);
            var H;
            F == e.Position.Left ? (H = new t.Rect(A - T._desiredSize.height, X, z, T._desiredSize.height), A -= T._desiredSize.height) : F == e.Position.Right && (H = new t.Rect(L, X, z, T._desiredSize.height), L += T._desiredSize.height), T._layout(H, V)
          }
          for (_ = 0; _ < this.plotAreas.length; _++) {
            var G = this.plotAreas[_];
            G.row == Y && G._setPlotY(X, z)
          }
          X += z
        }
      }, h.prototype._convertX = function(t, e, i) {
        var n = this.axisX;
        return n.reversed ? i - (i - e) * (t - n._getMinNum()) / (n._getMaxNum() - n._getMinNum()) : e + (i - e) * (t - n._getMinNum()) / (n._getMaxNum() - n._getMinNum())
      }, h.prototype._convertY = function(t, e, i) {
        var n = this.axisY;
        return n.reversed ? e + (i - e) * (t - n._getMinNum()) / (n._getMaxNum() - n._getMinNum()) : i - (i - e) * (t - n._getMinNum()) / (n._getMaxNum() - n._getMinNum())
      }, h.prototype._getLabelContent = function(e, i) {
        return t.isString(i) ? this._keywords.replace(i, e) : t.isFunction(i) ? i(e) : null
      }, h.prototype._select = function(t, e) {
        var i = !1;
        if (t == this._selection && e == this._selectionIndex || (i = !0), this._selection && this._highlight(this._selection, !1, this._selectionIndex), this._selection = t, this._selectionIndex = e, this._selection && this._highlight(this._selection, !0, this._selectionIndex), this.selectionMode == n.Point) {
          var r = t ? t.collectionView : this._cv;
          r && (this._notifyCurrentChanged = !1, r.moveCurrentToPosition(t ? e : -1), this._notifyCurrentChanged = !0)
        }
        i && this.onSelectionChanged()
      }, h.prototype._highlightCurrent = function() {
        if (this.selectionMode != n.None) {
          var t = this._selection,
            e = -1;
          if (t) {
            var i = t.collectionView;
            i || (i = this._cv), i && (e = i.currentPosition), this._highlight(t, !0, e)
          }
        }
      }, h.prototype._highlight = function(i, r, s) {
        if (i = t.asType(i, e.SeriesBase, !0), this.selectionMode == n.Series) {
          var a = this.series.indexOf(i),
            o = i.hostElement;
          r ? o.parentNode.appendChild(o) : o.parentNode.insertBefore(o, o.parentNode.childNodes.item(a));
          l = this._find(o, ["rect", "ellipse", "polyline", "polygon", "line", "path"]);
          this._highlightItems(l, e.FlexChart._CSS_SELECTION, r), i.legendElement && this._highlightItems(this._find(i.legendElement, ["rect", "ellipse", "line"]), e.FlexChart._CSS_SELECTION, r)
        } else if (this.selectionMode == n.Point) {
          var a = this.series.indexOf(i),
            o = i.hostElement;
          if (r) {
            o.parentNode.appendChild(o);
            var h = i.getPlotElement(s);
            if (h)
              if (h instanceof SVGElement) {
                "g" != h.nodeName && this._highlightItems([h], e.FlexChart._CSS_SELECTION, r);
                l = this._find(h, ["line", "rect", "ellipse", "path", "polygon"]);
                this._highlightItems(l, e.FlexChart._CSS_SELECTION, r)
              } else Array.isArray(h) && this._highlightItems(h, e.FlexChart._CSS_SELECTION, r)
          } else {
            o.parentNode.insertBefore(o, o.parentNode.childNodes.item(a));
            var l = this._find(o, ["rect", "ellipse", "line", "path", "polygon"]);
            this._highlightItems(l, e.FlexChart._CSS_SELECTION, r)
          }
        }
      }, h.prototype._updateAuxAxes = function(i, n) {
        for (var r = 2; r < i.length; r++) {
          var s = i[r];
          s._chart = this;
          for (var a = [], o = 0; o < this.series.length; o++) {
            var h = this.series[o];
            h.axisX != s && h.axisY != s || a.push(h)
          }
          for (var l, c, o = 0; o < a.length; o++) {
            var u = a[o].getDataRect() || a[o]._getDataRect();
            u && (s.axisType == e.AxisType.X && !n || s.axisType == e.AxisType.Y && n ? ((void 0 === l || u.left < l) && (l = u.left), (void 0 === c || u.right > c) && (c = u.right)) : ((void 0 === l || u.top < l) && (l = u.top), (void 0 === c || u.bottom > c) && (c = u.bottom)))
          }
          var _ = a[0].getDataType(0);
          null == _ && (_ = t.DataType.Number), i[r]._updateActualLimits(_, l, c)
        }
      }, h._contains = function(t, e) {
        return !(!t || !e) && (e.x >= t.left && e.x <= t.right && e.y >= t.top && e.y <= t.bottom)
      }, h._intersects = function(t, e) {
        return !(t.left > e.right || t.right < e.left || t.top > e.bottom || t.bottom < e.top)
      }, h._toOADate = function(t) {
        return t.valueOf()
      }, h._fromOADate = function(t) {
        return new Date(t)
      }, h._renderText = function(e, i, n, r, s, a, o, h, l) {
        var c = e.measureString(i, a, o, h),
          u = n.x,
          _ = n.y;
        switch (r) {
          case 1:
            u -= .5 * c.width;
            break;
          case 2:
            u -= c.width
        }
        switch (s) {
          case 1:
            _ += .5 * c.height;
            break;
          case 0:
            _ += c.height
        }
        var p = new t.Rect(u, _ - c.height, c.width, c.height);
        return l ? l(p) ? (e.drawString(i, new t.Point(u, _), a, h), p) : null : (e.drawString(i, new t.Point(u, _), a, h), p)
      }, h._renderRotatedText = function(e, i, n, r, s, a, o, h, l, c) {
        var u = e.measureString(i, h, l, c),
          _ = n.x,
          p = n.y;
        switch (r) {
          case 1:
            _ -= .5 * u.width;
            break;
          case 2:
            _ -= u.width
        }
        switch (s) {
          case 1:
            p += .5 * u.height;
            break;
          case 0:
            p += u.height
        }
        return e.drawStringRotated(i, new t.Point(_, p), a, o, h, c)
      }, h._CSS_AXIS_X = "wj-axis-x", h._CSS_AXIS_Y = "wj-axis-y", h._CSS_LINE = "wj-line", h._CSS_GRIDLINE = "wj-gridline", h._CSS_TICK = "wj-tick", h._CSS_GRIDLINE_MINOR = "wj-gridline-minor", h._CSS_TICK_MINOR = "wj-tick-minor", h._CSS_LABEL = "wj-label", h._CSS_LEGEND = "wj-legend", h._CSS_HEADER = "wj-header", h._CSS_FOOTER = "wj-footer", h._CSS_TITLE = "wj-title", h._CSS_SELECTION = "wj-state-selected", h._CSS_PLOT_AREA = "wj-plot-area", h._CSS_DATA_LABELS = "wj-data-labels", h._FG = "#666", h._epoch = new Date(1899, 11, 30).getTime(), h._msPerDay = 864e5, h
    }(e.FlexChartBase);
    e.FlexChartCore = r;
    var s = function() {
        function t() {
          this._axes = new Array, this.left = 0, this.right = 0, this.top = 0, this.bottom = 0
        }
        return Object.defineProperty(t.prototype, "axes", {
          get: function() {
            return this._axes
          },
          enumerable: !0,
          configurable: !0
        }), t
      }(),
      a = function() {
        function n() {
          this.stackAbs = {}, this._xvals = null
        }
        return n.prototype.analyse = function(r, s, a, o, h, l) {
          var c = this;
          this.minY = NaN, this.maxY = NaN, this.minX = NaN, this.maxX = NaN, this.minXp = NaN, this.minYp = NaN, this.dx = 0;
          var u = {},
            _ = {},
            p = {};
          if (this.dataTypeX = null, this.dataTypeY = null, this._xvals = o, null != o)
            for (var d = o.length, f = 0; f < d; f++) {
              A = o[f];
              if ((isNaN(this.minX) || this.minX > A) && (this.minX = A), (isNaN(this.maxX) || this.maxX < A) && (this.maxX = A), A > 0 && (isNaN(this.minXp) || this.minXp > A) && (this.minXp = A), f > 0) {
                S = Math.abs(A - o[f - 1]);
                !isNaN(S) && (S < this.dx || 0 == this.dx) && (this.dx = S)
              }
            }
          for (f = 0; f < r.length; f++) {
            var g = r[f],
              y = g._getChartType(),
              m = void 0 !== g.chartType,
              b = g.visibility;
            if (b != e.SeriesVisibility.Hidden && b != e.SeriesVisibility.Legend) {
              var v, x = g.getDataRect();
              x && (!isNaN(this.minX) && this.minX < x.left && (v = x.right, x.left = this.minX, x.width = v - this.minX), !isNaN(this.maxX) && this.maxX > x.right && (x.width = this.maxX - x.left), !isNaN(this.minY) && this.minY < x.top && (v = x.bottom, x.top = this.minY, x.height = v - this.minY), !isNaN(this.maxY) && this.maxY > x.bottom && (x.height = this.maxY - x.top));
              var w = null;
              if (s ? g._isCustomAxisY() || (w = g.getValues(1)) : g._isCustomAxisX() || (w = g.getValues(1)), w) {
                this.dataTypeX || (this.dataTypeX = g.getDataType(1));
                for (T = 0; T < w.length; T++) {
                  M = w[T];
                  if (n.isValid(M) && ((isNaN(this.minX) || this.minX > M) && (this.minX = M), (isNaN(this.maxX) || this.maxX < M) && (this.maxX = M), T > 0 && (!y || y == e.ChartType.Column || y == e.ChartType.Bar))) {
                    var S = Math.abs(M - w[T - 1]);
                    !isNaN(S) && S > 0 && (S < this.dx || 0 == this.dx) && (this.dx = S)
                  }
                }
              }
              var P = null,
                C = !1;
              if (s ? (C = g._isCustomAxisX(), P = g.getValues(0)) : (C = g._isCustomAxisY(), P = g.getValues(0)), P && (this.dataTypeY || C || (this.dataTypeY = g.getDataType(0)), isNaN(this.minX) ? this.minX = 0 : w || o || (this.minX = Math.min(this.minX, 0)), isNaN(this.maxX) ? this.maxX = P.length - 1 : w || o || (this.maxX = Math.max(this.maxX, P.length - 1)), !C))
                for (var T = 0; T < P.length; T++) {
                  var M = P[T],
                    A = w ? t.asNumber(w[T], !0) : o ? t.asNumber(o[T], !0) : T;
                  t.isArray(M) ? M.forEach(function(t) {
                    c._parseYVal(t, A, m, p, u, _)
                  }) : this._parseYVal(M, A, m, p, u, _)
                }
              var N = g.getDataRect(new t.Rect(this.minX, this.minY, this.maxX - this.minX, this.maxY - this.minY), x);
              N && (this.minX = N.left, this.maxX = N.right, this.minY = N.top, this.maxY = N.bottom)
            }
          }
          if (a == i.Stacked) {
            for (var L in u) u[L] > this.maxY && (this.maxY = u[L]);
            for (var L in _) _[L] < this.minY && (this.minY = _[L])
          } else if (a == i.Stacked100pc) {
            this.minY = 0, this.maxY = 1;
            for (var L in p) {
              var I = p[L];
              if (isFinite(I) && 0 != I) {
                var E = u[L],
                  k = _[L];
                isFinite(E) && (E = Math.min(E / I, this.maxY)), isFinite(k) && (k = Math.max(k / I, this.minY))
              }
            }
          }
          this.stackAbs = p, h && (s ? this.minY = isNaN(this.minYp) ? 1 : this.minYp : this.minX = isNaN(this.minXp) ? 1 : this.minXp), l && (s ? this.minX = isNaN(this.minXp) ? 1 : this.minXp : this.minY = isNaN(this.minYp) ? 1 : this.minYp)
        }, n.prototype._parseYVal = function(t, e, i, r, s, a) {
          n.isValid(t) && (null != t && (isNaN(this.minY) || this.minY > t) && (this.minY = t), null != t && (isNaN(this.maxY) || this.maxY < t) && (this.maxY = t), t > 0 && (isNaN(this.minYp) || this.minYp > t) && (this.minYp = t), i || (t > 0 ? isNaN(s[e]) ? s[e] = t : s[e] += t : isNaN(a[e]) ? a[e] = t : a[e] += t, isNaN(r[e]) ? r[e] = Math.abs(t) : r[e] += Math.abs(t)))
        }, n.prototype.getMinY = function() {
          return this.minY
        }, n.prototype.getMaxY = function() {
          return this.maxY
        }, n.prototype.getMinX = function() {
          return this.minX
        }, n.prototype.getMaxX = function() {
          return this.maxX
        }, n.prototype.getMinXp = function() {
          return this.minXp
        }, n.prototype.getMinYp = function() {
          return this.minYp
        }, n.prototype.getDeltaX = function() {
          return this.dx
        }, n.prototype.getDataTypeX = function() {
          return this.dataTypeX
        }, n.prototype.getDataTypeY = function() {
          return this.dataTypeY
        }, n.prototype.getStackedAbsSum = function(t) {
          var e = this.stackAbs[t];
          return isFinite(e) ? e : 0
        }, n.prototype.getXVals = function() {
          return this._xvals
        }, n.isValid = function(t) {
          return isFinite(t)
        }, n
      }();
    e._DataInfo = a;
    var o = function(e) {
      function i() {
        var t = e.call(this) || this;
        return t._content = "<b>{seriesName}</b><br/>{x} {y}", t._threshold = 15, t
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "content", {
        get: function() {
          return this._content
        },
        set: function(t) {
          t != this._content && (this._content = t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "threshold", {
        get: function() {
          return this._threshold
        },
        set: function(e) {
          e != this._threshold && (this._threshold = t.asNumber(e))
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(t.Tooltip);
    e.ChartTooltip = o
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i;
    ! function(t) {
      t[t.Column = 0] = "Column", t[t.Bar = 1] = "Bar", t[t.Scatter = 2] = "Scatter", t[t.Line = 3] = "Line", t[t.LineSymbols = 4] = "LineSymbols", t[t.Area = 5] = "Area", t[t.Bubble = 6] = "Bubble", t[t.Candlestick = 7] = "Candlestick", t[t.HighLowOpenClose = 8] = "HighLowOpenClose", t[t.Spline = 9] = "Spline", t[t.SplineSymbols = 10] = "SplineSymbols", t[t.SplineArea = 11] = "SplineArea", t[t.Funnel = 12] = "Funnel"
    }(i = e.ChartType || (e.ChartType = {}));
    var n = function(n) {
      function r(t, e) {
        var r = n.call(this, t, null) || this;
        return r._chartType = i.Column, r.initialize(e), r
      }
      return __extends(r, n), r.prototype._getChartType = function() {
        return this._chartType
      }, Object.defineProperty(r.prototype, "chartType", {
        get: function() {
          return this._chartType
        },
        set: function(e) {
          (e = t.asEnum(e, i)) != this._chartType && (this._chartType = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "rotated", {
        get: function() {
          return this._rotated
        },
        set: function(e) {
          e != this._rotated && (this._rotated = t.asBoolean(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "stacking", {
        get: function() {
          return this._stacking
        },
        set: function(i) {
          (i = t.asEnum(i, e.Stacking)) != this._stacking && (this._stacking = i, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "options", {
        get: function() {
          return this._options
        },
        set: function(t) {
          t != this._options && (this._options = t, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), r
    }(e.FlexChartCore);
    e.FlexChart = n
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Left = 1] = "Left", t[t.Top = 2] = "Top", t[t.Right = 3] = "Right", t[t.Bottom = 4] = "Bottom", t[t.Auto = 5] = "Auto"
    }(i = e.Position || (e.Position = {}));
    var n;
    ! function(t) {
      t[t.X = 0] = "X", t[t.Y = 1] = "Y"
    }(n = e.AxisType || (e.AxisType = {}));
    var r;
    ! function(t) {
      t[t.Auto = 0] = "Auto", t[t.Show = 1] = "Show"
    }(r = e.OverlappingLabels || (e.OverlappingLabels = {}));
    var s;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Outside = 1] = "Outside", t[t.Inside = 2] = "Inside", t[t.Cross = 3] = "Cross"
    }(s = e.TickMark || (e.TickMark = {}));
    var a = function() {
      function a(e) {
        this._GRIDLINE_WIDTH = 1, this._LINE_WIDTH = 1, this._TICK_WIDTH = 1, this._TICK_HEIGHT = 4, this._TICK_OVERLAP = 1, this._TICK_LABEL_DISTANCE = 4, this._minorGrid = !1, this._labels = !0, this._axisLine = !0, this._isTimeAxis = !1, this._labelPadding = 5, this.rangeChanged = new t.Event, this._customConvert = null, this._customConvertBack = null, this._updating = 0, this.__uniqueId = a._id++, this._position = e, e == i.Bottom || e == i.Top ? this._axisType = n.X : (this._axisType = n.Y, this._axisLine = !1), this._minorTickMarks = s.None, this._overlap = r.Auto
      }
      return Object.defineProperty(a.prototype, "hostElement", {
        get: function() {
          return this._hostElement
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "actualMin", {
        get: function() {
          return this._isTimeAxis ? new Date(this._actualMin) : this._actualMin
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "actualMax", {
        get: function() {
          return this._isTimeAxis ? new Date(this._actualMax) : this._actualMax
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "min", {
        get: function() {
          return this._min
        },
        set: function(e) {
          e != this._min && (t.isDate(e) ? this._min = t.asDate(e, !0) : this._min = t.asNumber(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "max", {
        get: function() {
          return this._max
        },
        set: function(e) {
          e != this._max && (t.isDate(e) ? this._max = t.asDate(e, !0) : this._max = t.asNumber(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "reversed", {
        get: function() {
          return this._reversed
        },
        set: function(e) {
          this._reversed != e && (this._reversed = t.asBoolean(e), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "position", {
        get: function() {
          return this._position
        },
        set: function(e) {
          (e = t.asEnum(e, i, !1)) != this._position && (this._position = e, this._position == i.Bottom || this._position == i.Top ? this._axisType = n.X : this._position != i.Left && this._position != i.Right || (this._axisType = n.Y), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "majorUnit", {
        get: function() {
          return this._majorUnit
        },
        set: function(e) {
          e != this._majorUnit && (this._majorUnit = t.asNumber(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "minorUnit", {
        get: function() {
          return this._minorUnit
        },
        set: function(e) {
          e != this._minorUnit && (this._minorUnit = t.asNumber(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "name", {
        get: function() {
          return this._name
        },
        set: function(e) {
          e != this._name && (this._name = t.asString(e, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "title", {
        get: function() {
          return this._title
        },
        set: function(e) {
          e != this._title && (this._title = t.asString(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "format", {
        get: function() {
          return this._format
        },
        set: function(e) {
          e != this._format && (this._format = t.asString(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "majorGrid", {
        get: function() {
          return this._majorGrid
        },
        set: function(e) {
          e != this._majorGrid && (this._majorGrid = t.asBoolean(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "majorTickMarks", {
        get: function() {
          return this._majorTickMarks
        },
        set: function(e) {
          (e = t.asEnum(e, s, !0)) != this._majorTickMarks && (this._majorTickMarks = e, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "minorGrid", {
        get: function() {
          return this._minorGrid
        },
        set: function(e) {
          e != this._minorGrid && (this._minorGrid = t.asBoolean(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "minorTickMarks", {
        get: function() {
          return this._minorTickMarks
        },
        set: function(e) {
          (e = t.asEnum(e, s, !0)) != this._minorTickMarks && (this._minorTickMarks = e, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "axisLine", {
        get: function() {
          return this._axisLine
        },
        set: function(e) {
          e != this._axisLine && (this._axisLine = t.asBoolean(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "labels", {
        get: function() {
          return this._labels
        },
        set: function(e) {
          e != this._labels && (this._labels = t.asBoolean(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "labelAlign", {
        get: function() {
          return this._labelAlign
        },
        set: function(e) {
          e != this._labelAlign && (this._labelAlign = t.asString(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "labelAngle", {
        get: function() {
          return this._labelAngle
        },
        set: function(e) {
          e != this._labelAngle && (this._labelAngle = t.asNumber(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "origin", {
        get: function() {
          return this._origin
        },
        set: function(e) {
          e != this._origin && (this._origin = t.asNumber(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "overlappingLabels", {
        get: function() {
          return this._overlap
        },
        set: function(e) {
          (e = t.asEnum(e, r, !0)) != this._overlap && (this._overlap = e, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "itemsSource", {
        get: function() {
          return this._items
        },
        set: function(e) {
          this._items != e && (this._cv && (this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this), this._cv = null), this._items = e, this._cv = t.asCollectionView(e), null != this._cv && this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "binding", {
        get: function() {
          return this._binding
        },
        set: function(e) {
          e != this._binding && (this._binding = t.asString(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "itemFormatter", {
        get: function() {
          return this._ifmt
        },
        set: function(e) {
          this._ifmt != e && (this._ifmt = t.asFunction(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "logBase", {
        get: function() {
          return this._logBase
        },
        set: function(e) {
          e != this._logBase && (this._logBase = t.asNumber(e, !0, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype._getLogBase = function() {
        return this._chart && this._chart._stacking === e.Stacking.Stacked100pc ? 0 : this.logBase
      }, Object.defineProperty(a.prototype, "plotArea", {
        get: function() {
          return this._parea
        },
        set: function(i) {
          i != this._parea && (this._parea = t.asType(i, e.PlotArea, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "labelPadding", {
        get: function() {
          return this._labelPadding
        },
        set: function(e) {
          e != this._labelPadding && (this._labelPadding = t.asNumber(e, !0, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "_groupClass", {
        get: function() {
          return this.axisType === n.X ? e.FlexChartCore._CSS_AXIS_X : e.FlexChartCore._CSS_AXIS_Y
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype.onRangeChanged = function(t) {
        this.rangeChanged.raise(this, t)
      }, a.prototype._getPosition = function() {
        if (this.axisType == n.X) {
          if (this.position == i.Auto) return i.Bottom
        } else if (this.axisType == n.Y && this.position == i.Auto) return i.Left;
        return this.position
      }, a.prototype._isOverlapped = function(t, e, i, r) {
        var s = this._lbls;
        if (null != s && s.length > 1)
          for (var a = s.length, o = this._values && this._values.length == a ? this._values : null, h = 0, l = 0, c = 0; c < a; c++) {
            var u = o ? o[c] : c;
            if (u >= this._actualMin && u <= this._actualMax) {
              var _ = e * (u - this._actualMin) / (this._actualMax - this._actualMin),
                p = t.measureString(s[c], i, this._groupClass);
              if (this.axisType == n.X) {
                if (c > 0 && Math.abs(_ - h) < Math.max(p.width, l) + 12) return !0;
                h = _, l = p.width
              } else this.axisType, n.Y
            }
          }
        return !1
      }, a.prototype._getHeight = function(i, r) {
        this._actualAngle = null;
        var a = e.FlexChart._CSS_LABEL,
          o = e.FlexChart._CSS_TITLE,
          h = this._actualMax - this._actualMin,
          l = this._nicePrecision(h);
        (l < 0 || l > 15) && (l = 0);
        var c = .1 * h,
          u = this._lbls,
          _ = this.labelAngle;
        if (this.labels && this._chart._getChartType() !== e.ChartType.Funnel) {
          if (c = this._updateAutoFormat(c), null != u && u.length > 0) {
            var p = u.length,
              d = this._values && this._values.length == p ? this._values : null;
            this._annoSize = new t.Size;
            for (var f = 0; f < p; f++) {
              var g = d ? d[f] : f;
              if (g >= this._actualMin && g <= this._actualMax) {
                m = i.measureString(u[f], a, this._groupClass);
                this.axisType, n.X, m.width > this._annoSize.width && (this._annoSize.width = m.width), m.height > this._annoSize.height && (this._annoSize.height = m.height)
              }
            }
            null == _ && this.axisType == n.X && (this._isOverlapped(i, r, a, this.axisType) ? _ = this._actualAngle = -45 : this._actualAngle = 0)
          } else {
            var y = this._formatValue(this._actualMin - c),
              m = i.measureString(y, a, this._groupClass);
            this._annoSize = m, y = this._formatValue(this._actualMax + c), (m = i.measureString(y, a, this._groupClass)).width > this._annoSize.width && (this._annoSize.width = m.width), m.height > this._annoSize.height && (this._annoSize.height = m.height)
          }
          if (_) {
            _ > 90 ? _ = 90 : _ < -90 && (_ = -90);
            var b = _ * Math.PI / 180,
              v = this._annoSize.width,
              x = this._annoSize.height;
            this._annoSize.width = v * Math.abs(Math.cos(b)) + x * Math.abs(Math.sin(b)), this._annoSize.height = v * Math.abs(Math.sin(b)) + x * Math.abs(Math.cos(b))
          }
        } else this._annoSize = new t.Size;
        x = 2 * (this._labelPadding || 5);
        this._axisType == n.X ? x += this._annoSize.height : x += this._annoSize.width + this._TICK_LABEL_DISTANCE + 2;
        var w = this._TICK_HEIGHT,
          S = this._TICK_OVERLAP;
        P == s.Outside ? S = 1 : P == s.Inside ? S = -1 : P == s.Cross && (S = 0);
        var P = this.majorTickMarks;
        return null == P && (P = s.Outside), P != s.None && (x += .5 * (1 + S) * w), this._title && (y = this._title, this._szTitle = i.measureString(y, o, this._groupClass), x += this._szTitle.height), i.fontSize = null, x
      }, a.prototype._updateAutoFormat = function(e) {
        if (this._isTimeAxis) {
          var i = this.format,
            n = .001 * this._getActualRange() / 10,
            r = new l(n * l.TicksPerSecond),
            s = t.isNumber(this._majorUnit) ? l.fromDays(this._majorUnit) : c.NiceTimeSpan(r, i);
          i || (this._tfmt = c.GetTimeDefaultFormat(1e3 * s.TotalSeconds, 0)), e = s.TotalSeconds
        }
        return e
      }, a.prototype._getActualRange = function() {
        return this._actualMax - this._actualMin
      }, a.prototype._updateActualLimitsByChartType = function(t, i, n) {
        if (t && t.length > 0 && !this._isTimeAxis) {
          var r = this._chart._getChartType();
          r != e.ChartType.Column && r != e.ChartType.Bar && (i -= .5, n += .5)
        }
        return {
          min: i,
          max: n
        }
      }, a.prototype._updateActualLimits = function(i, n, r, s, a) {
        void 0 === s && (s = null), void 0 === a && (a = null);
        var o = this._actualMin,
          h = this._actualMax;
        this._isTimeAxis = i == t.DataType.Date;
        var l = this._updateActualLimitsByChartType(s, n, r);
        n = l.min, r = l.max;
        var c = this._min,
          u = this._max;
        if (t.isDate(c) && (c = c.valueOf()), t.isDate(u) && (u = u.valueOf()), this._actualMin = null != c && this._chart && this._chart._stacking !== e.Stacking.Stacked100pc ? c : n, this._actualMax = null != u && this._chart && this._chart._stacking !== e.Stacking.Stacked100pc ? u : r, this._actualMin == this._actualMax && (this._actualMin -= .5, this._actualMax += .5), this._getLogBase() > 0) {
          var _ = this.logBase,
            p = Math.log(_);
          if (!this._max) {
            var d = Math.ceil(Math.log(this._actualMax) / p);
            this._actualMax = Math.pow(_, d)
          }
          if (!this._min) {
            var f = Math.floor(Math.log(this._actualMin) / p);
            this._actualMin = Math.pow(_, f)
          }(this._actualMin <= 0 || !t.isNumber(this._actualMin)) && (this._actualMin = 1), this._actualMax < this._actualMin && (this._actualMax = this._actualMin + 1)
        }
        if ((o != this._actualMin && (t.isNumber(o) || t.isNumber(this._actualMin)) || h != this._actualMax && (t.isNumber(h) || t.isNumber(this._actualMax))) && this.onRangeChanged(), this._items) {
          this._values = [], this._lbls = [];
          var g = this._items.length,
            y = "value",
            m = "text";
          if (this.binding) {
            var b = this.binding.split(",");
            2 == b.length && (y = b[0], m = b[1])
          }
          for (var v = 0; v < g; v++) {
            var x = this._items[v],
              w = x[y];
            t.isNumber(w) && (this._values.push(w), this._lbls.push(x[m]))
          }
        } else this._lbls = s, this._values = a
      }, a.prototype._layout = function(e, i) {
        var r = this.axisType == n.Y;
        this._plotrect = i, this._axrect = r ? new t.Rect(e.left, e.top, e.height, e.width) : e
      }, a.prototype._hasVisibileSeries = function() {
        for (var t, i = this._chart.series, n = 0, r = i.length; n < r; n++)
          if ((t = i[n].visibility) == e.SeriesVisibility.Plot || t == e.SeriesVisibility.Visible) return !0;
        return !1
      }, a.prototype._render = function(n) {
        if (this.position != i.None && this._hasVisibileSeries()) {
          this._vals = {};
          var r = 0;
          this.labelAngle && ((r = this.labelAngle) > 90 ? r = 90 : r < -90 && (r = -90)), null == this.labelAngle && null != this._actualAngle && (r = this._actualAngle);
          var o = e.FlexChart._FG,
            h = this._actualMax - this._actualMin;
          if (t.isNumber(h)) {
            var l = this._calcMajorUnit();
            0 == l && (l = .1 * this._niceTickNumber(h));
            var c = Math.min(a.MAX_MAJOR, Math.floor(h / l) + 1),
              u = [],
              _ = [];
            this._rects = [], this._vals.major = u, this._vals.hasLbls = [];
            var p = Math.floor(this._actualMin / l) * l;
            p < this._actualMin && (p += l);
            var d = !1;
            if (this._lbls && this._lbls.length > 0)
              if (_ = this._lbls, 0 == this._values.length) {
                d = !0;
                for (v = 0; v < _.length; v++) u.push(v)
              } else u = this._values;
            else this._isTimeAxis ? this._createTimeLabels(p, c, u, _) : this._getLogBase() ? this._createLogarithmicLabels(this._actualMin, this._actualMax, this.majorUnit, u, _, !0) : this._createLabels(p, c, l, u, _);
            c = Math.min(u.length, _.length), n.textFill = o;
            var f = this._TICK_HEIGHT,
              g = this._TICK_OVERLAP,
              y = this.majorTickMarks;
            null == y && (y = s.Outside), y == s.Outside ? g = 1 : y == s.Inside ? g = -1 : y == s.Cross && (g = 0);
            for (var m = .5 * (g - 1) * f, b = .5 * (1 + g) * f, v = 0; v < c; v++) {
              var x = !0,
                w = u[v],
                S = _[v],
                P = this.labels;
              if (P && (d || this.itemsSource) && this.majorUnit && v % this.majorUnit != 0 && (P = !1), w >= this._actualMin && w <= this._actualMax) {
                var C = n.textFill;
                x = this._renderLabelsAndTicks(n, v, w, S, r, y, P, m, b), n.textFill = C
              }
              this._vals.hasLbls.push(x)
            }
          }(this.minorGrid || this.minorTickMarks != s.None) && this._renderMinor(n, u, d), n.stroke = o, n.fontSize = null, this._renderLineAndTitle(n), n.stroke = null, n.fontSize = null, n.textFill = null, n.strokeWidth = null
        }
      }, a.prototype._renderLineAndTitle = function(r) {
        var s = this._getPosition(),
          a = this.axisType == n.Y,
          o = s != i.Top && s != i.Right,
          h = e.FlexChart._CSS_TITLE,
          l = e.FlexChart._CSS_LINE;
        if (a)
          if (o) {
            if (this._title) {
              c = new t.Point(this._axrect.left + .5 * this._szTitle.height, this._axrect.top + .5 * this._axrect.height);
              e.FlexChart._renderRotatedText(r, this._title, c, 1, 1, c, -90, h, this._groupClass)
            }
            this.axisLine && r.drawLine(this._axrect.right, this._axrect.top, this._axrect.right, this._axrect.bottom, l)
          } else {
            if (this._title) {
              var c = new t.Point(this._axrect.right - .5 * this._szTitle.height, this._axrect.top + .5 * this._axrect.height);
              e.FlexChart._renderRotatedText(r, this._title, c, 1, 1, c, 90, h, this._groupClass)
            }
            this.axisLine && r.drawLine(this._axrect.left, this._axrect.top, this._axrect.left, this._axrect.bottom, l)
          } else o ? (this.axisLine && r.drawLine(this._axrect.left, this._axrect.top, this._axrect.right, this._axrect.top, l), this._title && e.FlexChart._renderText(r, this._title, new t.Point(this._axrect.left + .5 * this._axrect.width, this._axrect.bottom), 1, 2, h)) : (this.axisLine && r.drawLine(this._axrect.left, this._axrect.bottom, this._axrect.right, this._axrect.bottom, l), this._title && e.FlexChart._renderText(r, this._title, new t.Point(this._axrect.left + .5 * this._axrect.width, this._axrect.top), 1, 0, h))
      }, a.prototype._renderMinor = function(t, e, r) {
        var s = this._getPosition(),
          a = this.axisType == n.Y,
          o = s != i.Top && s != i.Right;
        if (this._getLogBase()) {
          if (this.minorUnit > 0) {
            var h = [];
            this._createLogarithmicLabels(this._actualMin, this._actualMax, this.minorUnit, h, null, !1);
            for (var l = [], c = 0; c < h.length; c++) {
              var u = h[c]; - 1 == e.indexOf(u) && u > this._actualMin && l.push(u)
            }
            this._renderMinors(t, l, a, o)
          }
        } else this._createMinors(t, e, a, o, r)
      }, a.prototype._renderRotatedText = function(t, i, n, r, s, a, o, h, l, c, u) {
        if (this.itemFormatter) {
          var _ = this._getFormattedItem(t, i, n, r, l);
          _ ? (n = _.text, l = _.cls) : n = null
        }
        e.FlexChart._renderRotatedText(t, n, r, s, a, o, h, l, c, u)
      }, a.prototype._getFormattedItem = function(t, e, r, s, a) {
        if (this.itemFormatter) {
          var o = s.clone();
          this.axisType == n.X ? this.position == i.Top ? o.y = this._plotrect.top : o.y = this._plotrect.bottom : this.position == i.Right ? o.x = this._plotrect.right : o.x = this._plotrect.left;
          var h = {
            val: e,
            text: r,
            pos: o,
            cls: a
          };
          return h = this.itemFormatter(t, h)
        }
      }, a.prototype._renderLabelsAndTicks = function(a, o, h, l, c, u, _, p, d) {
        var f = this._getPosition(),
          g = !0,
          y = this.axisType == n.Y,
          m = f != i.Top && f != i.Right,
          b = this.labelPadding || 5,
          v = this._TICK_WIDTH,
          x = this._getLabelAlign(y),
          w = e.FlexChart._CSS_LABEL,
          S = e.FlexChart._CSS_GRIDLINE,
          P = e.FlexChart._CSS_TICK,
          C = e.FlexChart._FG,
          T = e.FlexChart._FG,
          M = this._GRIDLINE_WIDTH,
          A = h != this._actualMin && this.majorGrid;
        if (y) {
          var N = this.convert(h);
          if (A && (a.stroke = C, a.strokeWidth = M, a.drawLine(this._plotrect.left, N, this._plotrect.right, N, S)), g = !1, a.stroke = T, a.strokeWidth = v, m) {
            if (_) {
              g = !0;
              I = new t.Point(this._axrect.right - d - this._TICK_LABEL_DISTANCE - b, N);
              c > 0 ? 90 == c ? this._renderRotatedText(a, h, l, I, 1, 0, I, c, w, this._groupClass) : this._renderRotatedText(a, h, l, I, 2, 1, I, c, w, this._groupClass) : c < 0 ? -90 == c ? this._renderRotatedText(a, h, l, I, 1, 2, I, c, w, this._groupClass) : this._renderRotatedText(a, h, l, I, 2, 1, I, c, w, this._groupClass) : g = this._renderLabel(a, h, l, I, 2, x, w)
            }
            u != s.None && g && a.drawLine(this._axrect.right - p, N, this._axrect.right - d, N, P)
          } else {
            if (_) {
              g = !0;
              I = new t.Point(this._axrect.left + d + this._TICK_LABEL_DISTANCE + b, N);
              c > 0 ? 90 == c ? this._renderRotatedText(a, h, l, I, 1, 2, I, c, w, this._groupClass) : this._renderRotatedText(a, h, l, I, 0, 1, I, c, w, this._groupClass) : c < 0 ? -90 == c ? this._renderRotatedText(a, h, l, I, 1, 0, I, c, w, this._groupClass) : this._renderRotatedText(a, h, l, I, 0, 1, I, c, w, this._groupClass) : g = this._renderLabel(a, h, l, I, 0, x, w)
            }
            u != s.None && g && a.drawLine(this._axrect.left + p, N, this._axrect.left + d, N, P)
          }
        } else {
          var L = this.convert(h);
          if (this.overlappingLabels == r.Auto && this._xCross(L) && (_ = !1), A && _ && (a.stroke = C, a.strokeWidth = M, a.drawLine(L, this._plotrect.top, L, this._plotrect.bottom, S)), a.stroke = T, a.strokeWidth = v, m) {
            if (g = !1, _) {
              I = new t.Point(L, this._axrect.top + d + b);
              g = 0 != c ? this._renderRotatedLabel(a, h, l, I, x, c, w, m) : this._renderLabel(a, h, l, I, x, 0, w)
            }
            u != s.None && g && (L = this.convert(h), a.drawLine(L, this._axrect.top + p, L, this._axrect.top + d, P))
          } else {
            if (_) {
              var I = new t.Point(L, this._axrect.bottom - d - b);
              g = 0 != c ? this._renderRotatedLabel(a, h, l, I, x, c, w, m) : this._renderLabel(a, h, l, I, x, 2, w)
            }
            u != s.None && g && (L = this.convert(h), a.drawLine(L, this._axrect.bottom - p, L, this._axrect.bottom - d, P))
          }
        }
        return g
      }, a.prototype._xCross = function(t) {
        for (var e = this._rects.length, i = 0; i < e; i++) {
          var n = this._rects[i];
          if (t >= n.left && t <= n.right) return !0
        }
        return !1
      }, a.prototype._createMinors = function(e, i, n, r, s) {
        if (i && i.length > 1) {
          for (var o = this.majorUnit ? this._isTimeAxis ? 24 * this.majorUnit * 3600 * 1e3 : this.majorUnit : i[1] - i[0], h = t.isNumber(this.minorUnit) ? this._isTimeAxis ? 24 * this.minorUnit * 3600 * 1e3 : this.minorUnit : .5 * o, l = [], c = i[0]; c > this._actualMin && l.length < a.MAX_MINOR; c -= h) - 1 == i.indexOf(c) && l.push(c);
          for (c = i[0] + h; c < this._actualMax && l.length < a.MAX_MINOR; c += h) - 1 == i.indexOf(c) ? l.push(c) : s && this.majorUnit && c % this.majorUnit != 0 && l.push(c);
          this._renderMinors(e, l, n, r)
        }
      }, a.prototype._renderMinors = function(t, i, n, r) {
        var a = this._TICK_HEIGHT,
          o = this._TICK_WIDTH,
          h = this._TICK_OVERLAP,
          l = e.FlexChart._FG,
          c = this.minorTickMarks,
          u = !0;
        this._vals.minor = i, c == s.Outside ? h = 1 : c == s.Inside ? h = -1 : c == s.Cross ? h = 0 : u = !1;
        for (var _ = .5 * (h - 1) * a, p = .5 * (1 + h) * a, d = i ? i.length : 0, f = this.minorGrid, g = this._plotrect, y = this._GRIDLINE_WIDTH, m = e.FlexChart._FG, b = e.FlexChart._CSS_GRIDLINE_MINOR, v = e.FlexChart._CSS_TICK_MINOR, x = 0; x < d; x++)
          if (i[x] >= this.actualMin && i[x] <= this.actualMax)
            if (n) {
              var w = this.convert(i[x]);
              u && (t.stroke = l, t.strokeWidth = o, r ? t.drawLine(this._axrect.right - _, w, this._axrect.right - p, w, v) : t.drawLine(this._axrect.left + _, w, this._axrect.left + p, w, v)), f && (t.stroke = m, t.strokeWidth = y, t.drawLine(g.left, w, g.right, w, b))
            } else {
              var S = this.convert(i[x]);
              u && (t.stroke = l, t.strokeWidth = o, r ? t.drawLine(S, this._axrect.top + _, S, this._axrect.top + p, v) : t.drawLine(S, this._axrect.bottom - _, S, this._axrect.bottom - p, v)), f && (t.stroke = m, t.strokeWidth = y, t.drawLine(S, g.top, S, g.bottom, b))
            }
      }, a.prototype._renderLabel = function(i, n, s, a, o, h, l) {
        var c = !1;
        if (this.itemFormatter) {
          var u = this._getFormattedItem(i, n, s, a, l);
          u ? (s = u.text, l = u.cls) : s = null
        }
        if (s) {
          var _ = this._rects,
            p = this.overlappingLabels == r.Auto && !t.isNumber(this._actualAngle),
            d = e.FlexChart._renderText(i, s, a, o, h, l, this._groupClass, null, function(t) {
              if (p)
                for (var i = _.length, n = 0; n < i; n++)
                  if (e.FlexChart._intersects(_[n], t)) return !1;
              return !0
            });
          d && (d.left += 4, d.width += 8, _.push(d), c = !0)
        }
        return c
      }, a.prototype._renderRotatedLabel = function(i, n, s, a, o, h, l, c) {
        if (this.itemFormatter) {
          var u = this._getFormattedItem(i, n, s, a, l);
          u ? (s = u.text, l = u.cls) : s = null
        }
        if (s) {
          var _ = i.measureString(s, l, this._groupClass),
            p = .5 * _.height,
            d = .5 * _.width * Math.abs(Math.sin(h * Math.PI / 180)),
            f = .5 * _.width,
            g = .5 * (_.width * Math.abs(Math.cos(h * Math.PI / 180)) + _.height * Math.abs(Math.sin(h * Math.PI / 180))),
            y = new t.Point(a.x, a.y),
            m = new t.Point(a.x, a.y);
          this.labelAlign || (o = 90 == h || -90 == h ? 1 : c ? h > 0 ? 0 : 2 : h > 0 ? 2 : 0), c ? (a.y += p + d, y.y += p + d - .5 * _.height) : (a.y -= p + d - _.height, y.y -= p + d - .5 * _.height);
          var b = 0;
          2 === o ? (y.x -= g, a.x -= f + g, b = y.x + g - _.height - 2) : 0 === o ? (y.x += g, a.x -= f - g, b = y.x - g) : (a.x -= f, b = y.x - _.height / 2);
          var v = new t.Rect(b, m.y, _.height + 2, _.width),
            x = this._rects;
          if (this.overlappingLabels == r.Auto)
            for (var w = x.length, S = 0; S < w; S++)
              if (e.FlexChart._intersects(x[S], v)) return !1;
          return e.FlexChart._renderRotatedText(i, s, a, 0, 2, y, h, l, this._groupClass), this._rects.push(v), !0
        }
        return !1
      }, a.prototype._getLabelAlign = function(t) {
        var e = 1;
        if (this.labelAlign) {
          var i = this.labelAlign.toLowerCase();
          t ? "top" == i ? e = 0 : "bottom" == i && (e = 2) : "left" == i ? e = 0 : "right" == i && (e = 2)
        }
        return e
      }, a.prototype.convert = function(t, e, i) {
        var r = null == e ? this._actualMax : e,
          s = null == i ? this._actualMin : i;
        if (r == s) return 0;
        var a = this._axrect.left,
          o = this._axrect.width,
          h = this._axrect.top,
          l = this._axrect.height;
        if (null != this._customConvert) {
          var c = this._customConvert(t, s, r);
          return this.axisType == n.Y ? h + c * l : a + c * o
        }
        if (this._getLogBase()) {
          if (t <= 0) return NaN;
          var u = Math.log(r / s);
          return this._reversed ? this.axisType == n.Y ? h + Math.log(t / s) / u * l : a + o - Math.log(t / s) / u * o : this.axisType == n.Y ? h + l - Math.log(t / s) / u * l : a + Math.log(t / s) / u * o
        }
        return this._reversed ? this.axisType == n.Y ? h + (t - s) / (r - s) * l : a + o - (t - s) / (r - s) * o : this.axisType == n.Y ? h + l - (t - s) / (r - s) * l : a + (t - s) / (r - s) * o
      }, a.prototype.convertBack = function(t) {
        if (this._actualMax == this._actualMin) return 0;
        var e = this._plotrect.left,
          i = this._plotrect.width,
          r = this._plotrect.top,
          s = this._plotrect.height,
          a = this._actualMax - this._actualMin,
          o = this._getLogBase();
        if (null != this._customConvertBack) return this.axisType == n.Y ? this._customConvertBack((t - r) / s, this._actualMin, this._actualMax) : this._customConvertBack((t - e) / i, this._actualMin, this._actualMax);
        if (o) {
          var h = 0;
          return h = this._reversed ? this.axisType == n.Y ? (t - r) / s : 1 - (t - e) / i : this.axisType == n.Y ? 1 - (t - r) / s : (t - e) / i, Math.pow(o, (Math.log(this._actualMin) + (Math.log(this._actualMax) - Math.log(this._actualMin)) * h) / Math.log(o))
        }
        return this._reversed ? this.axisType == n.Y ? this._actualMin + (t - r) * a / s : this._actualMin + (e + i - t) * a / i : this.axisType == n.Y ? this._actualMax - (t - r) * a / s : this._actualMin + (t - e) * a / i
      }, Object.defineProperty(a.prototype, "axisType", {
        get: function() {
          var t = this._chart;
          if (t) {
            if (t.axisX == this) return n.X;
            if (t.axisY == this) return n.Y
          }
          return this._axisType
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype._getMinNum = function() {
        return this._actualMin
      }, a.prototype._getMaxNum = function() {
        return this._actualMax
      }, a.prototype._beginUpdate = function() {
        this._updating++
      }, a.prototype._endUpdate = function() {
        this._updating--, this._updating <= 0 && this._invalidate()
      }, a.prototype._cancelUpdate = function() {
        this._updating--
      }, Object.defineProperty(a.prototype, "_isUpdating", {
        get: function() {
          return this._updating > 0
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype._invalidate = function() {
        !this._isUpdating && this._chart && this._chart.invalidate()
      }, a.prototype._cvCollectionChanged = function(t, e) {
        this._invalidate()
      }, a.prototype._createLabels = function(t, e, i, n, r) {
        for (var s = 0; s < e; s++) {
          var a = (t + i * s).toFixed(14),
            o = parseFloat(a),
            h = this._formatValue(o);
          n.push(o), r.push(h)
        }
      }, a.prototype._createLogarithmicLabels = function(t, e, n, r, s, a) {
        var o = this._getLogBase(),
          h = Math.log(o),
          l = Math.floor(Math.log(t) / h),
          c = Math.ceil(Math.log(e) / h),
          u = o,
          _ = !0;
        n > 0 && (_ = !1, u = n), u < o && (u = o);
        var p = (c - l + 1) * o / u,
          d = 1;
        if (a) {
          var f = this._getPosition(),
            g = this._getAnnoNumber(f == i.Left || f == i.Right);
          p > g ? d = Math.floor(p / g + 1) : _ && (p <= .2 * g ? u = .2 * o : p <= .1 * g && (u = .1 * o))
        }
        for (var y = l; y <= c; y += d)
          if (_)
            for (var m = Math.pow(o, y), b = 0; b * u < o - 1; b++)(v = m * (1 + b * u)) >= t && v <= e && (0 == b ? (r.unshift(v), s && s.unshift(this._formatValue(v))) : (r.push(v), s && s.push(this._formatValue(v))));
          else {
            var v = Math.pow(u, y);
            v >= t && v <= e && (r.push(v), s && s.push(this._formatValue(v)))
          }
      }, a.prototype._createTimeLabels = function(e, i, r, s) {
        var a = this._actualMin,
          o = this._actualMax,
          h = new Date(a),
          u = new Date(o),
          _ = this._format,
          p = this._getAnnoNumber(this._axisType == n.Y);
        p > 12 && (p = 12);
        var d = .001 * (this._actualMax - this._actualMin) / p,
          f = new l(d * l.TicksPerSecond),
          g = t.isNumber(this._majorUnit) ? l.fromDays(this._majorUnit) : c.NiceTimeSpan(f, _);
        _ || (this._tfmt = _ = c.GetTimeDefaultFormat(1e3 * g.TotalSeconds, 0));
        var y = g.Ticks,
          m = c.RoundTime(a, g.TotalDays, !1);
        isFinite(m) && (a = m);
        var b = c.RoundTime(o, g.TotalDays, !0);
        isFinite(b) && (o = b);
        var v = new Date(a);
        new Date(o);
        if (g.TotalDays >= 365 && !t.isNumber(this._majorUnit)) {
          (v = new Date(h.getFullYear(), 1, 1)) < h && v.setFullYear(v.getFullYear() + 1);
          var x = g.TotalDays / 365;
          x -= x % 1;
          for (P = v; P <= u && x; P.setFullYear(P.getFullYear() + x)) {
            T = P.valueOf();
            r.push(T), s.push(this._formatValue(T))
          }
        } else if (g.TotalDays >= 30 && !t.isNumber(this._majorUnit)) {
          (v = new Date(h.getFullYear(), h.getMonth(), 1)) < h && v.setMonth(v.getMonth() + 1);
          var w = g.TotalDays / 30;
          w -= w % 1;
          for (P = v; P <= u; P.setMonth(P.getMonth() + w)) {
            T = P.valueOf();
            r.push(T), s.push(this._formatValue(T))
          }
        } else {
          var S = 1e3 * y / l.TicksPerSecond,
            P = v,
            C = h.getTime() - P.getTime();
          for (C > S && (P = new Date(P.getTime() + Math.floor(C / S) * S)); P <= u && S; P = new Date(P.getTime() + S))
            if (P >= h) {
              var T = P.valueOf();
              r.push(T), s.push(this._formatValue(T))
            }
        }
      }, a.prototype._formatValue = function(e) {
        if (this._isTimeAxis) return this._format ? t.Globalize.format(new Date(e), this._format) : t.Globalize.format(new Date(e), this._tfmt);
        if (this._format) return t.Globalize.format(e, this._format);
        var i = e == Math.round(e) ? "n0" : "n";
        return t.Globalize.format(e, i)
      }, a.prototype._calcMajorUnit = function() {
        var e = this._majorUnit;
        if (!t.isNumber(e)) {
          var i = this._actualMax - this._actualMin,
            r = this._nicePrecision(i),
            s = i / this._getAnnoNumber(this.axisType == n.Y);
          (e = this._niceNumber(2 * s, -r, !0)) < s && (e = this._niceNumber(s, 1 - r, !1)), e < s && (e = this._niceTickNumber(s))
        }
        return e
      }, a.prototype._getAnnoNumber = function(t) {
        var e = t ? this._annoSize.height : this._annoSize.width,
          i = t ? this._axrect.height : this._axrect.width;
        if (e > 0 && i > 0) {
          var n = Math.floor(i / (e + 6));
          return n <= 0 && (n = 1), n
        }
        return 10
      }, a.prototype._nicePrecision = function(e) {
        if (!t.isNumber(e) || e <= 0) return 0;
        var i, n = Math.log(e) / Math.LN10;
        i = n >= 0 ? Math.floor(n) : Math.ceil(n);
        var r = e / Math.pow(10, i);
        return r < 3 && (i = 1 - i, (r = e / Math.pow(10, i)) < 3 && (i += 1)), i
      }, a.prototype._niceTickNumber = function(t) {
        if (0 == t) return t;
        t < 0 && (t = -t);
        var e = Math.log(t) / Math.LN10,
          i = Math.floor(e),
          n = t / Math.pow(10, i),
          r = 10;
        return n <= 1 ? r = 1 : n <= 2 ? r = 2 : n <= 5 && (r = 5), r * Math.pow(10, i)
      }, a.prototype._niceNumber = function(t, e, i) {
        if (0 == t) return t;
        t < 0 && (t = -t);
        var n = t / Math.pow(10, e),
          r = 10;
        return i ? n < 1.5 ? r = 1 : n < 3 ? r = 2 : n < 4.5 ? r = 4 : n < 7 && (r = 5) : n <= 1 ? r = 1 : n <= 2 ? r = 2 : n <= 5 && (r = 5), r * Math.pow(10, e)
      }, Object.defineProperty(a.prototype, "_uniqueId", {
        get: function() {
          return this.__uniqueId
        },
        enumerable: !0,
        configurable: !0
      }), a.MAX_MAJOR = 1e3, a.MAX_MINOR = 2e3, a._id = 0, a
    }();
    e.Axis = a;
    var o = function(t) {
      function e() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(e, t), e.prototype.getAxis = function(t) {
        var e = this.indexOf(t);
        return e > -1 ? this[e] : null
      }, e.prototype.indexOf = function(t) {
        for (var e = 0; e < this.length; e++)
          if (this[e].name == t) return e;
        return -1
      }, e
    }(t.collections.ObservableArray);
    e.AxisCollection = o;
    var h;
    ! function(t) {
      t[t.tickf7 = -7] = "tickf7", t[t.tickf6 = -6] = "tickf6", t[t.tickf5 = -5] = "tickf5", t[t.tickf4 = -4] = "tickf4", t[t.tickf3 = -3] = "tickf3", t[t.tickf2 = -2] = "tickf2", t[t.tickf1 = -1] = "tickf1", t[t.second = 1] = "second", t[t.minute = 60] = "minute", t[t.hour = 3600] = "hour", t[t.day = 86400] = "day", t[t.week = 604800] = "week", t[t.month = 2678400] = "month", t[t.year = 31536e3] = "year", t[t.maxtime = Number.MAX_VALUE] = "maxtime"
    }(h || (h = {}));
    var l = function() {
        function t(t) {
          this.ticks = t
        }
        return Object.defineProperty(t.prototype, "Ticks", {
          get: function() {
            return this.ticks
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "TotalSeconds", {
          get: function() {
            return this.ticks / 1e7
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "TotalDays", {
          get: function() {
            return this.ticks / 1e7 / 86400
          },
          enumerable: !0,
          configurable: !0
        }), t.fromSeconds = function(e) {
          return new t(1e7 * e)
        }, t.fromDays = function(e) {
          return new t(1e7 * e * 24 * 60 * 60)
        }, t.TicksPerSecond = 1e7, t
      }(),
      c = function() {
        function i(i) {
          t.isDate(i) ? this.init(i) : t.isNumber(i) && this.init(e.FlexChart._fromOADate(i))
        }
        return i.prototype.init = function(t) {
          this.year = t.getFullYear(), this.month = t.getMonth(), this.day = t.getDate(), this.hour = t.getHours(), this.minute = t.getMinutes(), this.second = t.getSeconds()
        }, i.prototype.getTimeAsDateTime = function() {
          this.hour >= 24 && (this.hour -= 24, this.day += 1), this.month < 0 ? (-1 - this.day, this.month = 1) : this.month > 11 && (this.month - 12, this.month = 12), this.day < 1 ? (-1 - this.day, this.day = 1) : this.day > 28 && 2 == this.month ? (this.day - 28, this.day = 28) : this.day > 30 && (4 == this.month || 4 == this.month || 6 == this.month || 9 == this.month || 11 == this.month) ? (this.day - 30, this.day = 30) : this.day > 31 && (this.day - 31, this.day = 31), this.second > 59 && (this.second - 59, this.second = 59);
          return this.minute > 59 && (this.minute - 59, this.minute = 59), new Date(this.year, this.month, this.day, this.hour, this.minute, this.second)
        }, i.prototype.getTimeAsDouble = function() {
          return this.getTimeAsDateTime().valueOf()
        }, i.tround = function(t, e, i) {
          var n = t / e * e;
          return n -= n % 1, i && n != t && (n += e -= e % 1), n
        }, i.RoundTime = function(t, e, n) {
          var r = 24 * e * 60 * 60;
          if (r > 0) {
            var s = new i(t);
            return r < h.minute ? (s.second = this.tround(s.second, r, n), s.getTimeAsDouble()) : (s.second = 0, r < h.hour ? (r /= h.minute, s.minute = this.tround(s.minute, r, n), s.getTimeAsDouble()) : (s.minute = 0, r < h.day ? (r /= h.hour, s.hour = this.tround(s.hour, r, n), s.getTimeAsDouble()) : (s.hour = 0, r < h.month ? (r /= h.day, s.day = this.tround(s.day, r, n), s.getTimeAsDouble()) : (s.day = 1, r < h.year ? (r /= h.month, 1 != s.month && (s.month = this.tround(s.month, r, n)), s.getTimeAsDouble()) : (s.month = 1, r /= h.year, s.year = this.tround(s.year, r, n), s.getTimeAsDouble())))))
          }
          var a = t,
            o = a - r,
            l = o / e * e;
          return n && l != o && (l += e), a = r + l
        }, i.TimeSpanFromTmInc = function(t) {
          var e = l.fromSeconds(1);
          if (t != h.maxtime)
            if (t > h.tickf1) e = l.fromSeconds(t);
            else {
              var i = t,
                n = 1;
              for (i += 7; i > 0;) n *= 10, i--;
              e = new l(n)
            }
          return e
        }, i.manualTimeInc = function(t) {
          var e = h.second;
          if (null == t || 0 == t.length) return e;
          var i = t.indexOf("f");
          if (i >= 0) {
            var n = -1;
            if (i > 0 && "%" == t.substr(i - 1, 1)) n = -1;
            else
              for (var r = 1; r < 6 && !(i + r >= t.length) && "f" == t.substr(i + r, 1); r++) n--;
            e = n
          } else t.indexOf("s") >= 0 ? e = h.second : t.indexOf("m") >= 0 ? e = h.minute : t.indexOf("h") >= 0 || t.indexOf("H") ? e = h.hour : t.indexOf("d") >= 0 ? e = h.day : t.indexOf("M") >= 0 ? e = h.month : t.indexOf("y") >= 0 && (e = h.year);
          return e
        }, i.getNiceInc = function(t, e, i) {
          for (var n = 0; n < t.length; n++) {
            var r = t[n] * i;
            if (e <= r) return r
          }
          return 0
        }, i.NiceTimeSpan = function(t, e) {
          var n = h.second;
          null != e && e.length > 0 && (n = i.manualTimeInc(e));
          var r = 0,
            s = 0;
          if (n < h.second && t.TotalSeconds < 10) {
            for (r = t.Ticks, s = i.TimeSpanFromTmInc(n).Ticks; r > 10 * s;) s *= 10;
            var a = s;
            return r > a && (a *= 2), r > a && (a = 5 * s), r > a && (a = 10 * s), new l(a)
          }
          if (0 == (r = Math.ceil(t.TotalSeconds))) return i.TimeSpanFromTmInc(n);
          if (s = 1, n < h.minute) {
            if (r < h.minute && 0 != (s = i.getNiceInc([1, 2, 5, 10, 15, 30], r, n))) return l.fromSeconds(s);
            n = h.minute
          }
          if (n < h.hour) {
            if (r < h.hour && 0 != (s = i.getNiceInc([1, 2, 5, 10, 15, 30], r, n))) return l.fromSeconds(s);
            n = h.hour
          }
          if (n < h.day) {
            if (r < h.day && 0 != (s = i.getNiceInc([1, 3, 6, 12], r, n))) return l.fromSeconds(s);
            n = h.day
          }
          if (n < h.month) {
            if (r < h.month && 0 != (s = i.getNiceInc([1, 2, 7, 14], r, n))) return l.fromSeconds(s);
            n = h.month
          }
          if (n < h.year) {
            if (r < h.year && 0 != (s = i.getNiceInc([1, 2, 3, 4, 6], r, n))) return l.fromSeconds(s);
            n = h.year
          }
          return s = 100 * h.year, r < s && 0 == (s = i.getNiceInc([1, 2, 5, 10, 20, 50], r, n)) && (s = 100 * h.year), l.fromSeconds(s)
        }, i.NiceTimeUnit = function(t, e) {
          var n = l.fromDays(t);
          return (n = i.NiceTimeSpan(n, e)).TotalDays
        }, i.GetTimeDefaultFormat = function(e, i) {
          if (!t.isNumber(e) || !t.isNumber(i)) return "";
          var n = "s",
            r = l.fromSeconds(.001 * (e - i)),
            s = r.TotalSeconds;
          if (s >= h.year) n = "yyyy";
          else if (s >= h.month) n = "MMM yyyy";
          else if (s >= h.day) n = "MMM d";
          else if (s >= h.hour) n = "ddd H:mm";
          else if (s >= .5 * h.hour) n = "H:mm";
          else if (s >= 1) n = "H:mm:ss";
          else if (s > 0) {
            var a = r.Ticks;
            for (n = "s."; a < l.TicksPerSecond;) a *= 10, n += "f"
          }
          return n
        }, i.secInYear = 86400, i
      }()
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function e(e) {
        this._row = 0, this._col = 0, this._rect = new t.Rect(0, 0, 0, 0), e && t.copy(this, e)
      }
      return Object.defineProperty(e.prototype, "row", {
        get: function() {
          return this._row
        },
        set: function(e) {
          e != this._row && (this._row = t.asInt(e, !0, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "column", {
        get: function() {
          return this._col
        },
        set: function(e) {
          e != this._col && (this._col = t.asInt(e, !0, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "name", {
        get: function() {
          return this._name
        },
        set: function(e) {
          e != this._name && (this._name = t.asString(e, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "width", {
        get: function() {
          return this._width
        },
        set: function(t) {
          t != this._width && (this._width = t, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "height", {
        get: function() {
          return this._height
        },
        set: function(t) {
          t != this._height && (this._height = t, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "style", {
        get: function() {
          return this._style
        },
        set: function(t) {
          t != this._style && (this._style = t, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype._invalidate = function() {
        this._chart && this._chart.invalidate()
      }, e.prototype._render = function(t) {
        t.drawRect(this._rect.left, this._rect.top, this._rect.width, this._rect.height, null, this.style)
      }, e.prototype._setPlotX = function(t, e) {
        this._rect.left = t, this._rect.width = e
      }, e.prototype._setPlotY = function(t, e) {
        this._rect.top = t, this._rect.height = e
      }, e
    }();
    e.PlotArea = i;
    var n = function(t) {
      function e() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(e, t), e.prototype.getPlotArea = function(t) {
        var e = this.indexOf(t);
        return e > -1 ? this[e] : null
      }, e.prototype.indexOf = function(t) {
        for (var e = 0; e < this.length; e++)
          if (this[e].name == t) return e;
        return -1
      }, e.prototype._getWidth = function(t) {
        for (var e = 0; e < this.length; e++) {
          var i = this[e];
          if (i.column == t && 0 == i.row) return i.width
        }
      }, e.prototype._getHeight = function(t) {
        for (var e = 0; e < this.length; e++) {
          var i = this[e];
          if (i.row == t && 0 == i.column) return i.height
        }
      }, e.prototype._calculateWidths = function(t, e) {
        if (e <= 0) throw "ncols";
        for (var i = [], n = 0; n < e; n++) {
          var r = this._getWidth(n);
          i[n] = new s(r)
        }
        return this._calculateLengths(t, e, i)
      }, e.prototype._calculateHeights = function(t, e) {
        if (e <= 0) throw "nrows";
        for (var i = [], n = 0; n < e; n++) {
          var r = this._getHeight(n);
          i[n] = new s(r)
        }
        return this._calculateLengths(t, e, i)
      }, e.prototype._calculateLengths = function(t, e, i) {
        for (var n = [e], r = 0, s = 0, a = 0; a < e; a++) i[a].isAbsolute ? (n[a] = i[a].value, r += n[a]) : i[a].isStar ? s += i[a].value : i[a].isAuto && s++;
        for (var o = (t - r) / s, a = 0; a < e; a++) i[a].isStar ? n[a] = o * i[a].value : i[a].isAuto && (n[a] = o), n[a] < 0 && (n[a] = 0);
        return n
      }, e
    }(t.collections.ObservableArray);
    e.PlotAreaCollection = n;
    var r;
    ! function(t) {
      t[t.Auto = 0] = "Auto", t[t.Pixel = 1] = "Pixel", t[t.Star = 2] = "Star"
    }(r || (r = {}));
    var s = function() {
      function t(t) {
        void 0 === t && (t = null), this._unitType = r.Auto, t && ((t = t.toString()).indexOf("*") >= 0 ? (this._unitType = r.Star, t = t.replace("*", ""), this._value = parseFloat(t), isNaN(this._value) && (this._value = 1)) : (this._unitType = r.Pixel, this._value = parseFloat(t), isNaN(this._value) && (this._unitType = r.Auto, this._value = 1)))
      }
      return Object.defineProperty(t.prototype, "value", {
        get: function() {
          return this._value
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "isStar", {
        get: function() {
          return this._unitType == r.Star
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "isAbsolute", {
        get: function() {
          return this._unitType == r.Pixel
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "isAuto", {
        get: function() {
          return this._unitType == r.Auto
        },
        enumerable: !0,
        configurable: !0
      }), t
    }()
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i;
    ! function(t) {
      t[t.Visible = 0] = "Visible", t[t.Plot = 1] = "Plot", t[t.Legend = 2] = "Legend", t[t.Hidden = 3] = "Hidden"
    }(i = e.SeriesVisibility || (e.SeriesVisibility = {}));
    var n;
    ! function(t) {
      t[t.Dot = 0] = "Dot", t[t.Box = 1] = "Box"
    }(n = e.Marker || (e.Marker = {}));
    var r = function() {
      return function() {}
    }();
    e.DataArray = r;
    var s = function(e) {
      function i(i) {
        var n = e.call(this) || this;
        return n._series = t.asType(i, a), n
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "series", {
        get: function() {
          return this._series
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(t.EventArgs);
    e.SeriesEventArgs = s;
    var a = function() {
      function a(e) {
        this._altStyle = null, this._symbolMarker = n.Dot, this._visibility = i.Visible, this._interpolateNulls = null, this.rendering = new t.Event, this.rendered = new t.Event, e && this.initialize(e)
      }
      return Object.defineProperty(a.prototype, "interpolateNulls", {
        get: function() {
          return null == this._interpolateNulls ? this._chart && this._chart.interpolateNulls : this._interpolateNulls
        },
        set: function(e) {
          e != this._interpolateNulls && (this._interpolateNulls = t.asBoolean(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "style", {
        get: function() {
          return this._style
        },
        set: function(t) {
          t != this._style && (this._style = t, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "altStyle", {
        get: function() {
          return this._altStyle
        },
        set: function(t) {
          t != this._altStyle && (this._altStyle = t, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "symbolStyle", {
        get: function() {
          return this._symbolStyle
        },
        set: function(t) {
          t != this._symbolStyle && (this._symbolStyle = t, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "symbolSize", {
        get: function() {
          return this._symbolSize
        },
        set: function(e) {
          e != this._symbolSize && (this._symbolSize = t.asNumber(e, !0, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "symbolMarker", {
        get: function() {
          return this._symbolMarker
        },
        set: function(e) {
          (e = t.asEnum(e, n, !0)) != this._symbolMarker && (this._symbolMarker = e, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "binding", {
        get: function() {
          return this._binding ? this._binding : this._chart ? this._chart.binding : null
        },
        set: function(e) {
          e != this._binding && (this._binding = t.asString(e, !0), this._clearValues(), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "bindingX", {
        get: function() {
          return this._bindingX ? this._bindingX : this._chart ? this._chart.bindingX : null
        },
        set: function(e) {
          e != this._bindingX && (this._bindingX = t.asString(e, !0), this._clearValues(), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "name", {
        get: function() {
          return this._name
        },
        set: function(t) {
          this._name = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "itemsSource", {
        get: function() {
          return this._itemsSource
        },
        set: function(e) {
          e != this._itemsSource && (this._cv && (this._cv.currentChanged.removeHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this), this._cv = null), this._itemsSource = e, this._cv = t.asCollectionView(e), null != this._cv && (this._cv.currentChanged.addHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this)), this._clearValues(), this._itemsSource = e, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "collectionView", {
        get: function() {
          return this._cv ? this._cv : this._chart ? this._chart.collectionView : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "chart", {
        get: function() {
          return this._chart
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "hostElement", {
        get: function() {
          return this._hostElement
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "legendElement", {
        get: function() {
          return this._legendElement
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "cssClass", {
        get: function() {
          return this._cssClass
        },
        set: function(e) {
          this._cssClass = t.asString(e, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "visibility", {
        get: function() {
          return this._visibility
        },
        set: function(e) {
          (e = t.asEnum(e, i)) != this._visibility && (this._visibility = e, this._clearValues(), this._invalidate(), this._chart && this._chart.onSeriesVisibilityChanged(new s(this)))
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype.hitTest = function(e, i) {
        return t.isNumber(e) && t.isNumber(i) ? e = new t.Point(e, i) : e instanceof MouseEvent && (e = new t.Point(e.pageX, e.pageY)), t.asType(e, t.Point), this._chart ? this._chart._hitTestSeries(e, this._chart.series.indexOf(this)) : null
      }, a.prototype.getPlotElement = function(t) {
        if (this.hostElement && t < this._pointIndexes.length) {
          var e = this._pointIndexes[t];
          if (e < this.hostElement.childNodes.length) return this.hostElement.childNodes[e]
        }
        return null
      }, Object.defineProperty(a.prototype, "axisX", {
        get: function() {
          return this._axisX
        },
        set: function(i) {
          if (i != this._axisX) {
            if (this._axisX = t.asType(i, e.Axis, !0), this._axisX) {
              var n = this._axisX._chart = this._chart;
              n && -1 == n.axes.indexOf(this._axisX) && n.axes.push(this._axisX)
            }
            this._invalidate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "axisY", {
        get: function() {
          return this._axisY
        },
        set: function(i) {
          if (i != this._axisY) {
            if (this._axisY = t.asType(i, e.Axis, !0), this._axisY) {
              var n = this._axisY._chart = this._chart;
              n && -1 == n.axes.indexOf(this._axisY) && n.axes.push(this._axisY)
            }
            this._invalidate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype.initialize = function(e) {
        e && t.copy(this, e)
      }, a.prototype.pointToData = function(e) {
        return t.asType(e, t.Point), e = e.clone(), e.x = this._getAxisX().convertBack(e.x), e.y = this._getAxisY().convertBack(e.y), e
      }, a.prototype.dataToPoint = function(e) {
        return t.asType(e, t.Point), e = e.clone(), e.x = this._getAxisX().convert(e.x), e.y = this._getAxisY().convert(e.y), e
      }, a.prototype.onRendering = function(t, i, n) {
        var r = new e.SeriesRenderingEventArgs(t, i, n);
        return this.rendering.raise(this, r), r.cancel
      }, a.prototype.onRendered = function(t) {
        this.rendered.raise(this, new e.RenderEventArgs(t))
      }, Object.defineProperty(a.prototype, "_chart", {
        get: function() {
          return this.__chart
        },
        set: function(t) {
          t !== this.__chart && (this.__chart = t)
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype._getSymbolSize = function() {
        return null != this.symbolSize ? this.symbolSize : this.chart.symbolSize
      }, Object.defineProperty(a.prototype, "_plotter", {
        get: function() {
          return this.chart && !this.__plotter && (this.__plotter = this.chart._getPlotter(this)), this.__plotter
        },
        set: function(t) {
          t != this.__plotter && (this.__plotter = t)
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype.getDataType = function(t) {
        return 0 == t ? this._valueDataType : 1 == t ? this._xvalueDataType : null
      }, a.prototype.getValues = function(t) {
        if (0 == t) {
          if (null == this._values)
            if (this._valueDataType = null, null != this._cv) {
              i = this._bindValues(this._cv.items, this._getBinding(0));
              this._values = i.values, this._valueDataType = i.dataType
            } else if (null != this.binding && null != this._chart && null != this._chart.collectionView) {
              i = this._bindValues(this._chart.collectionView.items, this._getBinding(0));
              this._values = i.values, this._valueDataType = i.dataType
            }
          return this._values
        }
        if (1 == t) {
          if (null == this._xvalues) {
            this._xvalueDataType = null;
            var e = this;
            if (null != this.bindingX)
              if (null != e._cv) {
                i = this._bindValues(e._cv.items, this.bindingX, !0);
                this._xvalueDataType = i.dataType, this._xvalues = i.values
              } else {
                if (null == this._bindingX) return null;
                if (null != e._chart && null != e._chart.collectionView) {
                  var i = this._bindValues(e._chart.collectionView.items, this.bindingX, !0);
                  this._xvalueDataType = i.dataType, this._xvalues = i.values
                }
              }
          }
          return this._xvalues
        }
        return null
      }, a.prototype.drawLegendItem = function(t, i, n) {
        var r = this._getChartType();
        null == r && (r = this._chart._getChartType());
        var s = this._getLegendStyle(this.style);
        r === e.ChartType.Funnel ? this._drawFunnelLegendItem(t, i, n, s, this.symbolStyle) : this._drawLegendItem(t, i, r, this.name, s, this.symbolStyle)
      }, a.prototype._getLegendStyle = function(t) {
        if (t) {
          var e = {};
          return t.fill && (e.fill = t.fill), t.stroke && (e.stroke = t.stroke), e
        }
      }, a.prototype.measureLegendItem = function(t, i) {
        var n = this._getChartType();
        return null == n && (n = this._chart._getChartType()), n === e.ChartType.Funnel ? this._measureLegendItem(t, this._getFunnelLegendName(i)) : this._measureLegendItem(t, this.name)
      }, a.prototype.legendItemLength = function() {
        var t = this._getChartType();
        return null == t && (t = this._chart._getChartType()), t === e.ChartType.Funnel ? this._chart._xlabels && this._chart._xlabels.length ? this._chart._xlabels.length : this._chart._xvals && this._chart._xvals.length ? this._chart._xvals.length : 1 : 1
      }, a.prototype.getDataRect = function(t, e) {
        return null
      }, a.prototype._getChartType = function() {
        return this._chartType
      }, a.prototype._clearValues = function() {
        this._values = null, this._xvalues = null, this.__plotter = null
      }, a.prototype._getBinding = function(t) {
        var e = this.binding;
        if (e) {
          var i = this.chart ? this.chart._bindingSeparator : ",";
          if (i) {
            var n = e.split(i);
            n && n.length > t && (e = n[t].trim())
          }
        }
        return e
      }, a.prototype._getBindingValues = function(t) {
        var e;
        return null != this._cv ? e = this._cv.items : null != this._chart && null != this._chart.collectionView && (e = this._chart.collectionView.items), this._bindValues(e, this._getBinding(t)).values
      }, a.prototype._getItem = function(t) {
        var e = null,
          i = null;
        return null != this.itemsSource ? i = null != this._cv ? this._cv.items : this.itemsSource : null != this._chart.itemsSource && (i = null != this._chart.collectionView ? this._chart.collectionView.items : this._chart.itemsSource), null != i && (e = i[t]), e
      }, a.prototype._getLength = function() {
        var t = 0,
          e = null;
        return null != this.itemsSource ? e = null != this._cv ? this._cv.items : this.itemsSource : null != this._chart.itemsSource && (e = null != this._chart.collectionView ? this._chart.collectionView.items : this._chart.itemsSource), null != e && (t = e.length), t
      }, a.prototype._setPointIndex = function(t, e) {
        this._pointIndexes[t] = e
      }, a.prototype._getDataRect = function() {
        var e = this.getValues(0),
          i = this.getValues(1);
        if (e) {
          for (var n = NaN, r = NaN, s = NaN, a = NaN, o = e.length, h = 0; h < o; h++) {
            var l = e[h];
            if (isFinite(l) && (isNaN(r) ? r = a = l : l < r ? r = l : l > a && (a = l)), i) {
              var c = i[h];
              isFinite(c) && (isNaN(n) ? n = s = c : c < n ? n = c : l > a && (s = c))
            }
          }
          if (i || (n = 0, s = o - 1), !isNaN(r)) return new t.Rect(n, r, s - n, a - r)
        }
        return null
      }, a.prototype._isCustomAxisX = function() {
        return !!this._axisX && (!this._chart || this._axisX != this.chart.axisX)
      }, a.prototype._isCustomAxisY = function() {
        return !!this._axisY && (!this._chart || this._axisY != this.chart.axisY)
      }, a.prototype._getAxisX = function() {
        var t = null;
        return this.axisX ? t = this.axisX : this.chart && (t = this.chart.axisX), t
      }, a.prototype._getAxisY = function() {
        var t = null;
        return this.axisY ? t = this.axisY : this.chart && (t = this.chart.axisY), t
      }, a.prototype._measureLegendItem = function(i, n) {
        var r = new t.Size;
        if (r.width = e.Series._LEGEND_ITEM_WIDTH, r.height = e.Series._LEGEND_ITEM_HEIGHT, this._name) {
          var s = i.measureString(n, e.FlexChart._CSS_LABEL, e.FlexChart._CSS_LEGEND);
          r.width += s.width, r.height < s.height && (r.height = s.height)
        }
        return r.width += 3 * e.Series._LEGEND_ITEM_MARGIN, r.height += 2 * e.Series._LEGEND_ITEM_MARGIN, r
      }, a.prototype._drawFunnelLegendItem = function(i, n, r, s, a) {
        i.strokeWidth = 1;
        var o = e.Series._LEGEND_ITEM_MARGIN,
          h = null,
          l = null;
        null === h && (h = this._chart._getColorLight(r)), null === l && (l = this._chart._getColor(r)), i.fill = h, i.stroke = l;
        var c = n.top + .5 * n.height,
          u = e.Series._LEGEND_ITEM_WIDTH,
          _ = e.Series._LEGEND_ITEM_HEIGHT,
          p = this._getFunnelLegendName(r);
        i.drawRect(n.left + o, c - .5 * _, u, _, null, a || s), null != p && e.FlexChart._renderText(i, p, new t.Point(n.left + _ + 2 * o, c), 0, 1, e.FlexChart._CSS_LABEL, e.FlexChart._CSS_LEGEND)
      }, a.prototype._getFunnelLegendName = function(i) {
        var n, r = this._chart;
        return r._xlabels && r._xlabels.length && i < r._xlabels.length ? n = r._xlabels[i] : r._xvals && r._xvals.length && i < r._xvals.length && (n = r._xvals[i], r._xDataType === t.DataType.Date && (n = e.FlexChart._fromOADate(n))), null == n && (n = this.name), n.toString()
      }, a.prototype._drawLegendItem = function(i, r, s, a, o, h) {
        i.strokeWidth = 1;
        var l = e.Series._LEGEND_ITEM_MARGIN,
          c = null,
          u = null;
        null === c && (c = this._chart._getColorLight(this._chart.series.indexOf(this))), null === u && (u = this._chart._getColor(this._chart.series.indexOf(this))), i.fill = c, i.stroke = u;
        var _ = r.top + .5 * r.height,
          p = e.Series._LEGEND_ITEM_WIDTH,
          d = e.Series._LEGEND_ITEM_HEIGHT;
        switch (s) {
          case e.ChartType.Area:
          case e.ChartType.SplineArea:
            i.drawRect(r.left + l, _ - .5 * d, p, d, null, o);
            break;
          case e.ChartType.Bar:
          case e.ChartType.Column:
            i.drawRect(r.left + l, _ - .5 * d, p, d, null, h || o);
            break;
          case e.ChartType.Scatter:
          case e.ChartType.Bubble:
            var f = .3 * p,
              g = .3 * d;
            this.symbolMarker == n.Box ? i.drawRect(r.left + l + .5 * p - f, _ - g, 2 * f, 2 * g, null, h || o) : i.drawEllipse(r.left + .5 * p + l, _, f, g, null, h || o);
            break;
          case e.ChartType.Line:
          case e.ChartType.Spline:
            i.drawLine(r.left + l, _, r.left + p + l, _, null, o);
            break;
          case e.ChartType.LineSymbols:
          case e.ChartType.SplineSymbols:
            var f = .3 * p,
              g = .3 * d;
            this.symbolMarker == n.Box ? i.drawRect(r.left + l + .5 * p - f, _ - g, 2 * f, 2 * g, null, h || o) : i.drawEllipse(r.left + .5 * p + l, _, f, g, null, h || o), i.drawLine(r.left + l, _, r.left + p + l, _, null, o);
            break;
          case e.ChartType.Candlestick:
          case e.ChartType.HighLowOpenClose:
            i.drawLine(r.left + l, _, r.left + p + l, _, null, h || o)
        }
        this._name && e.FlexChart._renderText(i, a, new t.Point(r.left + d + 2 * l, _), 0, 1, e.FlexChart._CSS_LABEL, e.FlexChart._CSS_LEGEND)
      }, a.prototype._cvCollectionChanged = function(t, e) {
        this._clearValues(), this._invalidate()
      }, a.prototype._cvCurrentChanged = function(t, e) {
        this._chart && this._chart._notifyCurrentChanged && this._invalidate()
      }, a.prototype._bindValues = function(e, i, n) {
        void 0 === n && (n = !1);
        var s, a, o;
        if (null != e) {
          var h = e.length;
          s = new Array(e.length);
          for (var l = 0; l < h; l++) {
            o = null;
            var c = e[l];
            null != i && (c = c[i]), t.isArray(c) && c.length > 0 && (o = c, c = c[0]), t.isNumber(c) ? (s[l] = c, a = t.DataType.Number) : t.isDate(c) ? (s[l] = c.valueOf(), a = t.DataType.Date) : n && c && (s[l] = l, a = t.DataType.Number), t.isArray(o) && o.length > 0 && (s[l] = o)
          }
        }
        var u = new r;
        return u.values = s, u.dataType = a, u
      }, a.prototype._invalidate = function() {
        this._chart && this._chart.invalidate()
      }, a.prototype._indexToPoint = function(e) {
        if (e >= 0 && e < this._values.length) {
          var i = this._values[e],
            n = this._xvalues ? this._xvalues[e] : e;
          return new t.Point(n, i)
        }
        return null
      }, a.prototype._getSymbolFill = function(t) {
        var e = null;
        return this.symbolStyle && (e = this.symbolStyle.fill), !e && this.style && (e = this.style.fill), !e && this.chart && (e = this.chart._getColorLight(t)), e
      }, a.prototype._getSymbolStroke = function(t) {
        var e = null;
        return this.symbolStyle && (e = this.symbolStyle.stroke), !e && this.style && (e = this.style.stroke), !e && this.chart && (e = this.chart._getColor(t)), e
      }, a.prototype._getAltSymbolStroke = function(t) {
        var e = null;
        return this.altStyle && (e = this.altStyle.stroke), e
      }, a.prototype._getAltSymbolFill = function(t) {
        var e = null;
        return this.altStyle && (e = this.altStyle.fill), e
      }, a.prototype._renderLabels = function(t, e, i, n) {
        this._plotter && this._plotter._renderLabels(t, this, e, i, n)
      }, a._LEGEND_ITEM_WIDTH = 10, a._LEGEND_ITEM_HEIGHT = 10, a._LEGEND_ITEM_MARGIN = 4, a._DEFAULT_SYM_SIZE = 10, a
    }();
    e.SeriesBase = a
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n() {
        return null !== i && i.apply(this, arguments) || this
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "chartType", {
        get: function() {
          return this._chartType
        },
        set: function(i) {
          (i = t.asEnum(i, e.ChartType, !0)) != this._chartType && (this._chartType = i, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), n
    }(e.SeriesBase);
    e.Series = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(t) {
    "use strict"
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function i(t) {
        this._strokeWidth = 1, this._fontSize = null, this._fontFamily = null, this._savedGradient = {}, this._bbCache = {}, this._element = t, this._create(), this._element.appendChild(this._svg), void 0 === i._isff && (i._isff = navigator.userAgent.toLowerCase().indexOf("firefox") >= 0)
      }
      return i.prototype.beginRender = function() {
        for (; this._svg.firstChild;) t.removeChild(this._svg.firstChild);
        this._savedGradient = {}, this._bbCache = {}, this._svg.appendChild(this._defs), this._svg.appendChild(this._textGroup)
      }, i.prototype.endRender = function() {
        t.removeChild(this._textGroup)
      }, i.prototype.setViewportSize = function(t, e) {
        this._svg.setAttribute("width", t.toString()), this._svg.setAttribute("height", e.toString())
      }, Object.defineProperty(i.prototype, "element", {
        get: function() {
          return this._svg
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "fill", {
        get: function() {
          return this._fill
        },
        set: function(t) {
          this._fill = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "fontSize", {
        get: function() {
          return this._fontSize
        },
        set: function(t) {
          this._fontSize = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "fontFamily", {
        get: function() {
          return this._fontFamily
        },
        set: function(t) {
          this._fontFamily = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "stroke", {
        get: function() {
          return this._stroke
        },
        set: function(t) {
          this._stroke = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "strokeWidth", {
        get: function() {
          return this._strokeWidth
        },
        set: function(t) {
          this._strokeWidth = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "textFill", {
        get: function() {
          return this._textFill
        },
        set: function(t) {
          this._textFill = t
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.addClipRect = function(t, e) {
        if (t && e) {
          var n = document.createElementNS(i.svgNS, "clipPath"),
            r = document.createElementNS(i.svgNS, "rect");
          r.setAttribute("x", (t.left - 1).toFixed()), r.setAttribute("y", (t.top - 1).toFixed()), r.setAttribute("width", (t.width + 2).toFixed()), r.setAttribute("height", (t.height + 2).toFixed()), n.appendChild(r), n.setAttribute("id", e), this._svg.appendChild(n)
        }
      }, i.prototype.drawEllipse = function(t, e, n, r, s, a) {
        var o = document.createElementNS(i.svgNS, "ellipse");
        return this._applyColor(o, "stroke", this._stroke), null !== this._strokeWidth && o.setAttribute("stroke-width", this._strokeWidth.toString()), this._applyColor(o, "fill", this._fill), o.setAttribute("cx", t.toFixed(1)), o.setAttribute("cy", e.toFixed(1)), o.setAttribute("rx", n.toFixed(1)), o.setAttribute("ry", r.toFixed(1)), s && o.setAttribute("class", s), this._applyStyle(o, a), this._appendChild(o), o
      }, i.prototype.drawRect = function(t, e, n, r, s, a, o) {
        var h = document.createElementNS(i.svgNS, "rect");
        return this._applyColor(h, "fill", this._fill), this._applyColor(h, "stroke", this._stroke), null !== this._strokeWidth && h.setAttribute("stroke-width", this._strokeWidth.toString()), h.setAttribute("x", t.toFixed(1)), h.setAttribute("y", e.toFixed(1)), n > 0 && n < .05 ? h.setAttribute("width", "0.1") : h.setAttribute("width", n.toFixed(1)), r > 0 && r < .05 ? h.setAttribute("height", "0.1") : h.setAttribute("height", r.toFixed(1)), o && h.setAttribute("clip-path", "url(#" + o + ")"), s && h.setAttribute("class", s), this._applyStyle(h, a), this._appendChild(h), h
      }, i.prototype.drawLine = function(t, e, n, r, s, a) {
        var o = document.createElementNS(i.svgNS, "line");
        return this._applyColor(o, "stroke", this._stroke), null !== this._strokeWidth && o.setAttribute("stroke-width", this._strokeWidth.toString()), o.setAttribute("x1", t.toFixed(1)), o.setAttribute("x2", n.toFixed(1)), o.setAttribute("y1", e.toFixed(1)), o.setAttribute("y2", r.toFixed(1)), s && o.setAttribute("class", s), this._applyStyle(o, a), this._appendChild(o), o
      }, i.prototype.drawLines = function(t, e, n, r, s) {
        if (t && e) {
          var a = Math.min(t.length, e.length);
          if (a > 0) {
            var o = document.createElementNS(i.svgNS, "polyline");
            this._applyColor(o, "stroke", this._stroke), null !== this._strokeWidth && o.setAttribute("stroke-width", this._strokeWidth.toString()), o.setAttribute("fill", "none");
            for (var h = "", l = 0; l < a; l++) h += t[l].toFixed(1) + "," + e[l].toFixed(1) + " ";
            return o.setAttribute("points", h), n && o.setAttribute("class", n), s && o.setAttribute("clip-path", "url(#" + s + ")"), this._applyStyle(o, r), this._appendChild(o), o
          }
        }
        return null
      }, i.prototype.drawSplines = function(t, n, r, s, a) {
        if (t && n) {
          var o = new e._Spline(t, n).calculate(),
            h = o.xs,
            l = o.ys,
            c = Math.min(h.length, l.length);
          if (c > 0) {
            var u = document.createElementNS(i.svgNS, "polyline");
            this._applyColor(u, "stroke", this._stroke), null !== this._strokeWidth && u.setAttribute("stroke-width", this._strokeWidth.toString()), u.setAttribute("fill", "none");
            for (var _ = "", p = 0; p < c; p++) _ += h[p].toFixed(1) + "," + l[p].toFixed(1) + " ";
            return u.setAttribute("points", _), r && u.setAttribute("class", r), a && u.setAttribute("clip-path", "url(#" + a + ")"), this._applyStyle(u, s), this._appendChild(u), u
          }
        }
        return null
      }, i.prototype.drawPolygon = function(t, e, n, r, s) {
        if (t && e) {
          var a = Math.min(t.length, e.length);
          if (a > 0) {
            var o = document.createElementNS(i.svgNS, "polygon");
            this._applyColor(o, "stroke", this._stroke), null !== this._strokeWidth && o.setAttribute("stroke-width", this._strokeWidth.toString()), this._applyColor(o, "fill", this._fill);
            for (var h = "", l = 0; l < a; l++) h += t[l].toFixed(1) + "," + e[l].toFixed(1) + " ";
            return o.setAttribute("points", h), n && o.setAttribute("class", n), s && o.setAttribute("clip-path", "url(#" + s + ")"), this._applyStyle(o, r), this._appendChild(o), o
          }
        }
        return null
      }, i.prototype.drawPieSegment = function(e, n, r, s, a, o, h, l) {
        if (a >= 2 * Math.PI) return this.drawEllipse(e, n, r, r, o, h);
        var c = document.createElementNS(i.svgNS, "path");
        this._applyColor(c, "fill", this._fill), this._applyColor(c, "stroke", this._stroke), null !== this._strokeWidth && c.setAttribute("stroke-width", this._strokeWidth.toString());
        var u = new t.Point(e, n);
        u.x += r * Math.cos(s), u.y += r * Math.sin(s);
        var _ = s + a,
          p = new t.Point(e, n);
        p.x += r * Math.cos(_), p.y += r * Math.sin(_);
        var d = " 0 0,1 ";
        Math.abs(a) > Math.PI && (d = " 0 1,1 ");
        var f = "M " + u.x.toFixed(1) + "," + u.y.toFixed(1);
        return f += " A " + r.toFixed(1) + "," + r.toFixed(1) + d, f += p.x.toFixed(1) + "," + p.y.toFixed(1), f += " L " + e.toFixed(1) + "," + n.toFixed(1) + " z", c.setAttribute("d", f), l && c.setAttribute("clip-path", "url(#" + l + ")"), o && c.setAttribute("class", o), this._applyStyle(c, h), this._appendChild(c), c
      }, i.prototype.drawDonutSegment = function(e, n, r, s, a, o, h, l, c) {
        var u = !1;
        o >= 2 * Math.PI && (u = !0, o -= .001);
        var _ = document.createElementNS(i.svgNS, "path");
        this._applyColor(_, "fill", this._fill), this._applyColor(_, "stroke", this._stroke), null !== this._strokeWidth && _.setAttribute("stroke-width", this._strokeWidth.toString());
        var p = new t.Point(e, n);
        p.x += r * Math.cos(a), p.y += r * Math.sin(a);
        var d = a + o,
          f = new t.Point(e, n);
        f.x += r * Math.cos(d), f.y += r * Math.sin(d);
        var g = new t.Point(e, n);
        g.x += s * Math.cos(d), g.y += s * Math.sin(d);
        var y = new t.Point(e, n);
        y.x += s * Math.cos(a), y.y += s * Math.sin(a);
        var m = " 0 0,1 ",
          b = " 0 0,0 ";
        Math.abs(o) > Math.PI && (m = " 0 1,1 ", b = " 0 1,0 ");
        var v = "M " + p.x.toFixed(3) + "," + p.y.toFixed(3);
        return v += " A " + r.toFixed(3) + "," + r.toFixed(3) + m, v += f.x.toFixed(3) + "," + f.y.toFixed(3), v += u ? " M " + g.x.toFixed(3) + "," + g.y.toFixed(3) : " L " + g.x.toFixed(3) + "," + g.y.toFixed(3), v += " A " + s.toFixed(3) + "," + s.toFixed(3) + b, v += y.x.toFixed(3) + "," + y.y.toFixed(3), u || (v += " z"), _.setAttribute("d", v), c && _.setAttribute("clip-path", "url(#" + c + ")"), h && _.setAttribute("class", h), this._applyStyle(_, l), this._appendChild(_), _
      }, i.prototype.drawString = function(t, e, i, n) {
        var r = this._createText(e, t);
        i && r.setAttribute("class", i), this._applyStyle(r, n), this._appendChild(r);
        var s, a = this._getKey(t, i, this._groupCls);
        return this._bbCache[a] ? (s = this._bbCache[a], r.setAttribute("y", (e.y - (s.y + s.height)).toFixed(1))) : (s = this._getBBox(r), r.setAttribute("y", (e.y - (s.y + s.height - e.y)).toFixed(1))), r
      }, i.prototype.drawStringRotated = function(t, e, n, r, s, a) {
        var o = this._createText(e, t);
        s && o.setAttribute("class", s), this._applyStyle(o, a);
        var h = document.createElementNS(i.svgNS, "g");
        h.setAttribute("transform", "rotate(" + r.toFixed(1) + "," + n.x.toFixed(1) + "," + n.y.toFixed(1) + ")"), h.appendChild(o), this._appendChild(h);
        var l = this._getBBox(o);
        return o.setAttribute("y", (e.y - (l.y + l.height - e.y)).toFixed(1)), o
      }, i.prototype.measureString = function(e, i, n, r) {
        var s = new t.Size(0, 0);
        if (!this._fontFamily && !this._fontSize) {
          var a = this._getKey(e, i, n);
          if (this._bbCache[a]) return this._bbCache[a]
        }
        this._fontSize && this._text.setAttribute("font-size", this._fontSize), this._fontFamily && this._text.setAttribute("font-family", this._fontFamily), i && this._text.setAttribute("class", i), n && this._textGroup.setAttribute("class", n), this._applyStyle(this._text, r), this._setText(this._text, e);
        var o = this._getBBox(this._text);
        if (s.width = o.width, s.height = o.height, this._text.removeAttribute("font-size"), this._text.removeAttribute("font-family"), this._text.removeAttribute("class"), r)
          for (var h in r) this._text.removeAttribute(this._deCase(h));
        if (this._textGroup.removeAttribute("class"), this._text.textContent = null, !this._fontFamily && !this._fontSize) {
          var l = this._getKey(e, i, n);
          this._bbCache[l] = {
            x: o.x,
            y: o.y + 1e3,
            width: o.width,
            height: o.height
          }
        }
        return s
      }, i.prototype.startGroup = function(t, e, n) {
        void 0 === n && (n = !1);
        var r = document.createElementNS(i.svgNS, "g");
        return t && (r.setAttribute("class", t), this._groupCls = t), e && r.setAttribute("clip-path", "url(#" + e + ")"), this._appendChild(r), n && r.transform.baseVal.appendItem(this._svg.createSVGTransform()), this._group = r, r
      }, i.prototype.endGroup = function() {
        if (this._group) {
          var t = this._group.parentNode;
          t == this._svg ? (this._group = null, this._groupCls = null) : (this._group = t, this._groupCls = this._getClass(this._group))
        }
      }, i.prototype.drawImage = function(t, e, n, r, s) {
        var a = document.createElementNS(i.svgNS, "image");
        return a.setAttributeNS(i.xlinkNS, "href", t), a.setAttribute("x", e.toFixed(1)), a.setAttribute("y", n.toFixed(1)), a.setAttribute("width", r.toFixed(1)), a.setAttribute("height", s.toFixed(1)), this._appendChild(a), a
      }, i.prototype._appendChild = function(t) {
        var e = this._group;
        e || (e = this._svg), e.appendChild(t)
      }, i.prototype._create = function() {
        this._svg = document.createElementNS(i.svgNS, "svg"), this._defs = document.createElementNS(i.svgNS, "defs"), this._svg.appendChild(this._defs), this._text = this._createText(new t.Point(-1e3, -1e3), ""), this._textGroup = document.createElementNS(i.svgNS, "g"), this._textGroup.appendChild(this._text), this._svg.appendChild(this._textGroup)
      }, i.prototype._setText = function(t, e) {
        var i = e ? e.toString() : null;
        if (i && i.indexOf("tspan") >= 0) try {
          t.textContent = null;
          for (var n = '<svg xmlns="http://www.w3.org/2000/svg">' + i + "</svg>", r = (new DOMParser).parseFromString(n, "text/xml").documentElement.firstChild; r;) t.appendChild(t.ownerDocument.importNode(r, !0)), r = r.nextSibling
        } catch (t) {
          throw new Error("Error parsing XML string.")
        } else t.textContent = i
      }, i.prototype._getKey = function(t, e, i) {
        return t + (e || "") + (i || "")
      }, i.prototype._createText = function(t, e) {
        var n = document.createElementNS(i.svgNS, "text");
        return this._setText(n, e), n.setAttribute("x", t.x.toFixed(1)), n.setAttribute("y", t.y.toFixed(1)), null != this._textFill && n.setAttribute("fill", this._textFill), this._fontSize && n.setAttribute("font-size", this._fontSize), this._fontFamily && n.setAttribute("font-family", this._fontFamily), n
      }, i.prototype._applyStyle = function(t, e) {
        if (e)
          for (var i in e) "fill" === i || "stroke" === i ? this._applyColor(t, i, e[i]) : t.setAttribute(this._deCase(i), e[i])
      }, i.prototype._deCase = function(t) {
        return t.replace(/[A-Z]/g, function(t) {
          return "-" + t.toLowerCase()
        })
      }, i.prototype._getClass = function(t) {
        var e;
        if (t)
          for (var i = t; i && !(e = i.getAttribute("class")); i = i.parentNode);
        return e
      }, i.prototype._getBBox = function(t) {
        if (!i._isff) return t.getBBox();
        try {
          return t.getBBox()
        } catch (t) {
          return {
            x: 0,
            y: 0,
            width: 0,
            height: 0
          }
        }
      }, i.prototype._applyColor = function(e, r, s) {
        var a = n.tryParse(s);
        if (null != a)
          if (t.isString(a)) e.setAttribute(r, a);
          else {
            if (null == this._savedGradient[s]) {
              var o, h = "gc" + (1e6 * Math.random()).toFixed();
              null != a.x1 ? (o = document.createElementNS(i.svgNS, "linearGradient"), ["x1", "y1", "x2", "y2", "gradientUnits"].forEach(function(t) {
                null != a[t] && o.setAttribute(t, a[t])
              })) : null != a.r && (o = document.createElementNS(i.svgNS, "radialGradient"), ["cx", "cy", "r", "fx", "fy", "fr", "gradientUnits"].forEach(function(t) {
                null != a[t] && o.setAttribute(t, a[t])
              })), a.colors && a.colors && a.colors.length > 0 && a.colors.forEach(function(t) {
                var e = document.createElementNS(i.svgNS, "stop");
                null != t.color && e.setAttribute("stop-color", t.color), null != t.offset && e.setAttribute("offset", t.offset), null != t.opacity && e.setAttribute("stop-opacity", t.opacity), o.appendChild(e)
              }), o.setAttribute("id", h), this._defs.appendChild(o), this._savedGradient[s] = h
            }
            e.setAttribute(r, "url(#" + this._savedGradient[s] + ")")
          }
      }, i.svgNS = "http://www.w3.org/2000/svg", i.xlinkNS = "http://www.w3.org/1999/xlink", i
    }();
    e._SvgRenderEngine = i;
    var n = function() {
      function t() {}
      return t.tryParse = function(e) {
        if (t.parsedColor[e]) return t.parsedColor[e];
        if (null == e || -1 === e.indexOf("-")) return e;
        var i, n = e.replace(/\s+/g, "").split(/\-/g),
          r = n[0][0],
          s = !1,
          a = n[0].match(/\(\S+\)/)[0].replace(/[\(\\)]/g, "").split(/\,/g);
        "l" === r || "L" === r ? (i = {
          x1: "0",
          y1: "0",
          x2: "0",
          y2: "0",
          colors: []
        }, "l" === r && (s = !0), ["x1", "y1", "x2", "y2"].forEach(function(t, e) {
          null != a[e] && (i[t] = s ? 100 * +a[e] + "%" : a[e] + "")
        })) : "r" !== r && "R" !== r || (i = {
          cx: "0",
          cy: "0",
          r: "0",
          colors: []
        }, "r" === r && (s = !0), ["cx", "cy", "r", "fx", "fy", "fr"].forEach(function(t, e) {
          null != a[e] && "" !== a[e] && (i[t] = s ? 100 * +a[e] + "%" : a[e] + "")
        })), s || (i.gradientUnits = "userSpaceOnUse"), t.parsedColor[e] = i;
        var o = n.length - 1;
        return n.forEach(function(t, e) {
          t.indexOf(")") > -1 && (t = t.match(/\)\S+/)[0].replace(")", ""));
          var n = t.split(":"),
            r = {
              color: "black"
            };
          null != n[0] && (r.color = n[0]), null != n[1] ? r.offset = s ? 100 * +n[1] + "%" : n[1] + "" : r.offset = e / o * 100 + "%", null != n[2] && (r.opacity = n[2]), i.colors.push(r)
        }), i
      }, t.parsedColor = {}, t
    }()
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function i(i) {
        this._position = e.Position.Right, this._title = "", this._titleAlign = "left", this._titlePadding = 5, this._areas = new Array, this._sz = new t.Size, this._colRowLens = [], this._chart = i
      }
      return Object.defineProperty(i.prototype, "position", {
        get: function() {
          return this._position
        },
        set: function(i) {
          (i = t.asEnum(i, e.Position)) != this._position && (this._position = i, this._chart && this._chart.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "title", {
        get: function() {
          return this._title
        },
        set: function(e) {
          e != this._title && (this._title = t.asString(e, !1), this._chart && this._chart.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "titleAlign", {
        get: function() {
          return this._titleAlign
        },
        set: function(e) {
          if (e != this._titleAlign) {
            var i = t.asString(e, !1);
            this._titleAlign = "right" === i ? "right" : "center" === i ? "center" : "left", this._chart && this._chart.invalidate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype._getDesiredSize = function(t, i, n, r) {
        if (i == e.Position.None) return null;
        var s = i == e.Position.Right || i == e.Position.Left,
          a = this._chart._getDesiredLegendSize(t, s, n, r);
        if (null != a) {
          if (this.title.length > 0) {
            var o = t.measureString(this.title, "wj-title", "wj-legend");
            a.height += o.height + this._titlePadding, o.width > a.width && (a.width = o.width)
          }
          this._sz = a
        }
        return a
      }, i.prototype._getPosition = function(t, i) {
        return this.position == e.Position.Auto ? t >= i ? e.Position.Right : e.Position.Bottom : this.position
      }, i.prototype._render = function(t, i, n, r, s) {
        this._areas = [];
        var a = n == e.Position.Right || n == e.Position.Left;
        if (t.fill = "transparent", t.stroke = null, t.drawRect(i.x, i.y, this._sz.width, this._sz.height), this.title.length) {
          var o = t.drawString(this.title, i, "wj-title"),
            h = o.getBBox();
          o.setAttribute("y", Number(o.getAttribute("y")) + h.height), "right" === this.titleAlign ? o.setAttribute("x", i.x + r - h.width) : "center" === this.titleAlign && o.setAttribute("x", i.x + r / 2 - h.width / 2);
          var l = h.height + this._titlePadding;
          i.y += l, s -= l
        }
        this._chart._renderLegend(t, i, this._areas, a, r, s)
      }, i.prototype._hitTest = function(t) {
        for (var i = this._areas, n = 0; n < i.length; n++)
          if (i[n] && e.FlexChartCore._contains(i[n], t)) return n;
        return null
      }, i
    }();
    e.Legend = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i;
    ! function(t) {
      t[t.PlotArea = 0] = "PlotArea", t[t.AxisX = 1] = "AxisX", t[t.AxisY = 2] = "AxisY", t[t.ChartArea = 3] = "ChartArea", t[t.Legend = 4] = "Legend", t[t.Header = 5] = "Header", t[t.Footer = 6] = "Footer", t[t.Series = 7] = "Series", t[t.SeriesSymbol = 8] = "SeriesSymbol", t[t.DataLabel = 9] = "DataLabel", t[t.None = 10] = "None"
    }(i = e.ChartElement || (e.ChartElement = {}));
    var n = function() {
      function n(t, e, n) {
        this._pointIndex = null, this._chartElement = i.None, this._chart = t, this._pt = e, this._chartElement = n
      }
      return Object.defineProperty(n.prototype, "chart", {
        get: function() {
          return this._chart
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "point", {
        get: function() {
          return this._pt
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "series", {
        get: function() {
          return this._series
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pointIndex", {
        get: function() {
          return this._pointIndex
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "chartElement", {
        get: function() {
          return this._chartElement
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "distance", {
        get: function() {
          return this._dist
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "item", {
        get: function() {
          if (null == this._item && null !== this.pointIndex)
            if (null != this.series) this._item = this.series._getItem(this.pointIndex);
            else {
              var t = this._chart._getHitTestItem(this.pointIndex);
              t && (this._item = t)
            }
          return this._item
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "x", {
        get: function() {
          return void 0 === this._x && (this._x = this._getValue(1, !1)), this._x
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "y", {
        get: function() {
          return void 0 === this._y && (this._y = this._getValue(0, !1)), this._y
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "value", {
        get: function() {
          var t = this._chart._getHitTestValue(this.pointIndex);
          return null != t ? t : this.y
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "name", {
        get: function() {
          if (void 0 === this._name) {
            var t = this._chart._getHitTestLabel(this.pointIndex);
            return null == t ? this.series.name : t.toString()
          }
          return this._name
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "_xfmt", {
        get: function() {
          return void 0 === this.__xfmt && (this.__xfmt = this._getValue(1, !0)), this.__xfmt
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "_yfmt", {
        get: function() {
          return void 0 === this.__yfmt && (this.__yfmt = this._getValue(0, !0)), this.__yfmt
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._setData = function(t, e) {
        this._series = t, this._pointIndex = e
      }, n.prototype._setDataPoint = function(i) {
        if (i = t.asType(i, e._DataPoint, !0)) {
          this._pointIndex = i.pointIndex;
          var n = t.asType(this._chart, t.chart.FlexChartCore, !0),
            r = i.seriesIndex;
          null !== r && r >= 0 && r < n.series.length && (this._series = n.series[r]), null != i.item && (this._item = i.item), null != i.x && (this._x = i.x), null != i.y && (this._y = i.y), null != i.xfmt && (this.__xfmt = i.xfmt), null != i.yfmt && (this.__yfmt = i.yfmt), null != i.name && (this._name = i.name)
        }
      }, n.prototype._getValue = function(e, i) {
        var n = this._chart._getHitTestValue(this.pointIndex);
        if (null != n) return n;
        var r = null,
          s = this._chart,
          a = this.pointIndex,
          o = s._isRotated();
        if (null !== this.series && null !== a) {
          var h = this.series.getValues(e),
            l = this.series.getDataType(e);
          h && this.pointIndex < h.length ? (r = h[this.pointIndex], l != t.DataType.Date || i || (r = new Date(r))) : 1 == e && (s._xlabels && s._xlabels.length > 0 && a < s._xlabels.length ? r = s._xlabels[a] : s._xvals && a < s._xvals.length && (r = s._xvals[a], s._xDataType != t.DataType.Date || i || (r = new Date(r))))
        }
        return null !== r && i && (o ? 0 == e ? r = this.ax._formatValue(r) : 1 == e && (r = this.ay._formatValue(r)) : 0 == e ? r = this.ay._formatValue(r) : 1 == e && (r = this.ax._formatValue(r))), r
      }, Object.defineProperty(n.prototype, "ax", {
        get: function() {
          return this.series.axisX ? this.series.axisX : this._chart.axisX
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "ay", {
        get: function() {
          return this.series.axisY ? this.series.axisY : this._chart.axisY
        },
        enumerable: !0,
        configurable: !0
      }), n
    }();
    e.HitTestInfo = n
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(t) {
    "use strict";
    var e = function() {
      function t() {}
      return t.standard = ["#88bde6", "#fbb258", "#90cd97", "#f6aac9", "#bfa554", "#bc99c7", "#eddd46", "#f07e6e", "#8c8c8c"], t.cocoa = ["#466bb0", "#c8b422", "#14886e", "#b54836", "#6e5944", "#8b3872", "#73b22b", "#b87320", "#141414"], t.coral = ["#84d0e0", "#f48256", "#95c78c", "#efa5d6", "#ba8452", "#ab95c2", "#ede9d0", "#e96b7d", "#888888"], t.dark = ["#005fad", "#f06400", "#009330", "#e400b1", "#b65800", "#6a279c", "#d5a211", "#dc0127", "#000000"], t.highcontrast = ["#ff82b0", "#0dda2c", "#0021ab", "#bcf28c", "#19c23b", "#890d3a", "#607efd", "#1b7700", "#000000"], t.light = ["#ddca9a", "#778deb", "#cb9fbb", "#b5eae2", "#7270be", "#a6c7a7", "#9e95c7", "#95b0c7", "#9b9b9b"], t.midnight = ["#83aaca", "#e37849", "#14a46a", "#e097da", "#a26d54", "#a584b7", "#d89c54", "#e86996", "#2c343b"], t.modern = ["#2d9fc7", "#ec993c", "#89c235", "#e377a4", "#a68931", "#a672a6", "#d0c041", "#e35855", "#68706a"], t.organic = ["#9c88d9", "#a3d767", "#8ec3c0", "#e9c3a9", "#91ab36", "#d4ccc0", "#61bbd8", "#e2d76f", "#80715a"], t.slate = ["#7493cd", "#f99820", "#71b486", "#e4a491", "#cb883b", "#ae83a4", "#bacc5c", "#e5746a", "#505d65"], t.zen = ["#7bb5ae", "#e2d287", "#92b8da", "#eac4cb", "#7b8bbd", "#c7d189", "#b9a0c8", "#dfb397", "#a9a9a9"], t.cyborg = ["#2a9fd6", "#77b300", "#9933cc", "#ff8800", "#cc0000", "#00cca3", "#3d6dcc", "#525252", "#000000"], t.superhero = ["#5cb85c", "#f0ad4e", "#5bc0de", "#d9534f", "#9f5bde", "#46db8c", "#b6b86e", "#4e5d6c", "#2b3e4b"], t.flatly = ["#18bc9c", "#3498db", "#f39c12", "#6cc1be", "#99a549", "#8f54b5", "#e74c3c", "#8a9899", "#2c3e50"], t.darkly = ["#375a7f", "#00bc8c", "#3498db", "#f39c12", "#e74c3c", "#8f61b3", "#b08725", "#4a4949", "#000000"], t.cerulan = ["#033e76", "#87c048", "#59822c", "#53b3eb", "#fc6506", "#d42323", "#e3bb00", "#cccccc", "#222222"], t
    }();
    t.Palettes = e
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function e(e, i) {
        this.k = .002, this._a = [], this._b = [], this._c = [], this._d = [], this.m = [
          [-.5, 1.5, -1.5, .5],
          [1, -2.5, 2, -.5],
          [-.5, 0, .5, 0],
          [0, 1, 0, 0]
        ], this._x = e, this._y = i;
        var n = this._len = Math.min(e.length, i.length);
        if (n > 3)
          for (var r = 0; r < n - 1; r++) {
            var s = 0 == r ? new t.Point(e[r], i[r]) : new t.Point(e[r - 1], i[r - 1]),
              a = new t.Point(e[r], i[r]),
              o = new t.Point(e[r + 1], i[r + 1]),
              h = r == n - 2 ? new t.Point(e[r + 1], i[r + 1]) : new t.Point(e[r + 2], i[r + 2]),
              l = new t.Point,
              c = new t.Point,
              u = new t.Point,
              _ = new t.Point;
            l.x = s.x * this.m[0][0] + a.x * this.m[0][1] + o.x * this.m[0][2] + h.x * this.m[0][3], c.x = s.x * this.m[1][0] + a.x * this.m[1][1] + o.x * this.m[1][2] + h.x * this.m[1][3], u.x = s.x * this.m[2][0] + a.x * this.m[2][1] + o.x * this.m[2][2] + h.x * this.m[2][3], _.x = s.x * this.m[3][0] + a.x * this.m[3][1] + o.x * this.m[3][2] + h.x * this.m[3][3], l.y = s.y * this.m[0][0] + a.y * this.m[0][1] + o.y * this.m[0][2] + h.y * this.m[0][3], c.y = s.y * this.m[1][0] + a.y * this.m[1][1] + o.y * this.m[1][2] + h.y * this.m[1][3], u.y = s.y * this.m[2][0] + a.y * this.m[2][1] + o.y * this.m[2][2] + h.y * this.m[2][3], _.y = s.y * this.m[3][0] + a.y * this.m[3][1] + o.y * this.m[3][2] + h.y * this.m[3][3], this._a.push(l), this._b.push(c), this._c.push(u), this._d.push(_)
          }
      }
      return e.prototype.calculatePoint = function(t) {
        var e = Math.floor(t);
        e < 0 && (e = 0), e > this._len - 2 && (e = this._len - 2);
        var i = t - e;
        return {
          x: ((this._a[e].x * i + this._b[e].x) * i + this._c[e].x) * i + this._d[e].x,
          y: ((this._a[e].y * i + this._b[e].y) * i + this._c[e].y) * i + this._d[e].y
        }
      }, e.prototype.calculate = function() {
        if (this._len <= 3) return {
          xs: this._x,
          ys: this._y
        };
        var t = [],
          e = [],
          i = this.calculatePoint(0);
        t.push(i.x), e.push(i.y);
        for (var n = this._len * this.k, r = n; r <= this._len - 1; r += n) {
          var s = this.calculatePoint(r);
          (Math.abs(i.x - s.x) >= 3 || Math.abs(i.y - s.y) >= 3) && (t.push(s.x), e.push(s.y), i = s)
        }
        return {
          xs: t,
          ys: e
        }
      }, e
    }();
    e._Spline = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Left = 1] = "Left", t[t.Top = 2] = "Top", t[t.Right = 3] = "Right", t[t.Bottom = 4] = "Bottom", t[t.Center = 5] = "Center"
    }(i = e.LabelPosition || (e.LabelPosition = {}));
    var n;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Inside = 1] = "Inside", t[t.Center = 2] = "Center", t[t.Outside = 3] = "Outside", t[t.Radial = 4] = "Radial", t[t.Circular = 5] = "Circular"
    }(n = e.PieLabelPosition || (e.PieLabelPosition = {}));
    var r = function(e) {
      function i(t, i, n, r) {
        var s = e.call(this, t) || this;
        return s.cancel = !1, s._ht = i, s._pt = n, s._text = r, s
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "point", {
        get: function() {
          return this._pt
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "text", {
        get: function() {
          return this._text
        },
        set: function(e) {
          this._text = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "hitTestInfo", {
        get: function() {
          return this._ht
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(e.RenderEventArgs);
    e.DataLabelRenderEventArgs = r;
    var s = function() {
      function e() {
        this.rendering = new t.Event
      }
      return Object.defineProperty(e.prototype, "content", {
        get: function() {
          return this._content
        },
        set: function(t) {
          t != this._content && (this._content = t, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "border", {
        get: function() {
          return this._bdr
        },
        set: function(e) {
          e != this._bdr && (this._bdr = t.asBoolean(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "offset", {
        get: function() {
          return this._off
        },
        set: function(e) {
          e != this._off && (this._off = t.asNumber(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "connectingLine", {
        get: function() {
          return this._line
        },
        set: function(e) {
          e != this._line && (this._line = t.asBoolean(e, !0), this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype.onRendering = function(t) {
        return this.rendering.raise(this, t), !t.cancel
      }, e.prototype._invalidate = function() {
        this._chart && this._chart.invalidate()
      }, e
    }();
    e.DataLabelBase = s;
    var a = function(e) {
      function n() {
        var t = null !== e && e.apply(this, arguments) || this;
        return t._pos = i.Top, t
      }
      return __extends(n, e), Object.defineProperty(n.prototype, "position", {
        get: function() {
          return this._pos
        },
        set: function(e) {
          (e = t.asEnum(e, i)) != this._pos && (this._pos = e, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), n
    }(s);
    e.DataLabel = a;
    var o = function(e) {
      function i() {
        var t = null !== e && e.apply(this, arguments) || this;
        return t._pos = n.Center, t
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "position", {
        get: function() {
          return this._pos
        },
        set: function(e) {
          (e = t.asEnum(e, n)) != this._pos && (this._pos = e, this._invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(s);
    e.PieDataLabel = o
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i, n = new(function() {
      function e() {
        this._moveMarker = function(t) {
          var e = t.currentTarget,
            i = this._markers,
            n = e.getAttribute("data-markerIndex");
          null != n && i[n].forEach(function(e) {
            e._moveMarker(t)
          })
        }, this._markers = [], this._bindMoveMarker = this._moveMarker.bind(this)
      }
      return e.prototype.attach = function(e) {
        var i, n, r = e.chart.hostElement,
          s = this._markers,
          a = r.getAttribute("data-markerIndex");
        null != a ? (n = s[a]) && t.isArray(n) ? n.push(e) : (s[a] = [e], this._bindMoveEvent(r)) : (i = s.length, n = [e], s.push(n), r.setAttribute("data-markerIndex", i), this._bindMoveEvent(r))
      }, e.prototype.detach = function(t) {
        var e, i, n = t.chart.hostElement,
          r = this._markers,
          s = n.getAttribute("data-markerIndex");
        null != s && ((e = (i = r[s]).indexOf(t)) > -1 && i.splice(e, 1), 0 === i.length && ((e = r.indexOf(i)) > -1 && (r[e] = void 0), this._unbindMoveEvent(n)))
      }, e.prototype._unbindMoveEvent = function(t) {
        var e = this._bindMoveMarker;
        t.removeEventListener("mousemove", e), "ontouchstart" in window && t.removeEventListener("touchmove", e)
      }, e.prototype._bindMoveEvent = function(t) {
        var e = this._bindMoveMarker;
        t.addEventListener("mousemove", e), "ontouchstart" in window && t.addEventListener("touchmove", e)
      }, e
    }());
    ! function(t) {
      t[t.None = 0] = "None", t[t.Vertical = 1] = "Vertical", t[t.Horizontal = 2] = "Horizontal", t[t.Both = 3] = "Both"
    }(i = e.LineMarkerLines || (e.LineMarkerLines = {}));
    var r;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Move = 1] = "Move", t[t.Drag = 2] = "Drag"
    }(r = e.LineMarkerInteraction || (e.LineMarkerInteraction = {}));
    var s;
    ! function(t) {
      t[t.Auto = 2] = "Auto", t[t.Right = 0] = "Right", t[t.Left = 1] = "Left", t[t.Bottom = 4] = "Bottom", t[t.Top = 6] = "Top"
    }(s = e.LineMarkerAlignment || (e.LineMarkerAlignment = {}));
    var a = function() {
      function a(e, i) {
        this._wrapperMousedown = null, this._wrapperMouseup = null, this.positionChanged = new t.Event;
        var n = this;
        n._chart = e, e.rendered.addHandler(n._initialize, n), n._resetDefaultValue(), t.copy(this, i), n._initialize()
      }
      return Object.defineProperty(a.prototype, "chart", {
        get: function() {
          return this._chart
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "isVisible", {
        get: function() {
          return this._isVisible
        },
        set: function(e) {
          var i = this;
          e !== i._isVisible && (i._isVisible = t.asBoolean(e), i._marker && i._toggleVisibility())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "seriesIndex", {
        get: function() {
          return this._seriesIndex
        },
        set: function(e) {
          var i = this;
          e !== i._seriesIndex && (i._seriesIndex = t.asNumber(e, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "horizontalPosition", {
        get: function() {
          return this._horizontalPosition
        },
        set: function(e) {
          var i = this;
          if (e !== i._horizontalPosition) {
            if (i._horizontalPosition = t.asNumber(e, !0), i._horizontalPosition < 0 || i._horizontalPosition > 1) throw "horizontalPosition's value should be in (0, 1).";
            i._marker && i._updateMarkerPosition()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "x", {
        get: function() {
          var t = this,
            e = t._targetPoint.x - t._plotRect.left;
          return t._chart.axisX.convertBack(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "y", {
        get: function() {
          var t = this,
            e = t._targetPoint.y - t._plotRect.top;
          return t._chart.axisY.convertBack(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "content", {
        get: function() {
          return this._content
        },
        set: function(e) {
          e !== this._content && (this._content = t.asFunction(e), this._updateMarkerPosition())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "verticalPosition", {
        get: function() {
          return this._verticalPosition
        },
        set: function(e) {
          var i = this;
          if (e !== i._verticalPosition) {
            if (i._verticalPosition = t.asNumber(e, !0), i._verticalPosition < 0 || i._verticalPosition > 1) throw "verticalPosition's value should be in (0, 1).";
            i._marker && i._updateMarkerPosition()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "alignment", {
        get: function() {
          return this._alignment
        },
        set: function(t) {
          var e = this;
          t !== e._alignment && (e._alignment = t, e._marker && e._updatePositionByAlignment())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "lines", {
        get: function() {
          return this._lines
        },
        set: function(e) {
          (e = t.asEnum(e, i)) != this._lines && (this._lines = e, this._marker && this._resetLinesVisibility())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "interaction", {
        get: function() {
          return this._interaction
        },
        set: function(e) {
          (e = t.asEnum(e, r)) != this._interaction && (this._marker && this._detach(), this._interaction = e, this._marker && this._attach(), this._toggleElesDraggableClass(this._interaction == r.Drag))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "dragThreshold", {
        get: function() {
          return this._dragThreshold
        },
        set: function(e) {
          e != this._dragThreshold && (this._dragThreshold = t.asNumber(e))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "dragContent", {
        get: function() {
          return this._dragContent
        },
        set: function(e) {
          var n = this;
          e !== n._dragContent && (n._dragContent = t.asBoolean(e)), t.toggleClass(n._dragEle, a._CSS_LINE_DRAGGABLE, n._interaction === r.Drag && n._dragContent && n._lines !== i.None)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(a.prototype, "dragLines", {
        get: function() {
          return this._dragLines
        },
        set: function(e) {
          e != this._dragLines && (this._dragLines = t.asBoolean(e))
        },
        enumerable: !0,
        configurable: !0
      }), a.prototype.onPositionChanged = function(t) {
        this.positionChanged.raise(this, t)
      }, a.prototype.remove = function() {
        var t = this,
          e = t._chart;
        t._marker && (e.rendered.removeHandler(t._initialize, t), t._detach(), t._removeMarker(), t._wrapperMoveMarker = null, t._wrapperMousedown = null, t._wrapperMouseup = null)
      }, a.prototype._attach = function() {
        var e = this,
          i = e._chart.hostElement;
        this._interaction !== r.None ? t.addClass(i, a._CSS_TOUCH_DISABLED) : t.removeClass(i, a._CSS_TOUCH_DISABLED), n.attach(e), e._attachDrag()
      }, a.prototype._attachDrag = function() {
        var t = this;
        t._interaction === r.Drag && (t._wrapperMousedown || (t._wrapperMousedown = t._onMousedown.bind(t)), t._wrapperMouseup || (t._wrapperMouseup = t._onMouseup.bind(t)), t._toggleDragEventAttach(!0))
      }, a.prototype._detach = function() {
        var e = this;
        t.removeClass(e._chart.hostElement, a._CSS_TOUCH_DISABLED), n.detach(e), e._detachDrag()
      }, a.prototype._detachDrag = function() {
        var t = this;
        t._interaction === r.Drag && t._toggleDragEventAttach(!1)
      }, a.prototype._toggleDragEventAttach = function(t) {
        var e = this,
          i = e._chart.hostElement,
          n = t ? "addEventListener" : "removeEventListener";
        i[n]("mousedown", e._wrapperMousedown), document[n]("mouseup", e._wrapperMouseup), "ontouchstart" in window && i[n]("touchstart", e._wrapperMousedown), "ontouchend" in window && document[n]("touchend", e._wrapperMouseup)
      }, a.prototype._onMousedown = function(e) {
        var i, n, s, o, h, l = this,
          c = l._getEventPoint(e);
        l._interaction === r.Drag && (o = !(0 === (i = t.getElementRect(l._hLine)).width || 0 === i.height), h = !(0 === (n = t.getElementRect(l._vLine)).width || 0 === n.height), s = t.getElementRect(l._markerContent), l._dragContent && l._pointInRect(c, s) ? (l._capturedEle = l._markerContent, l._contentDragStartPoint = new t.Point(c.x, c.y), l._mouseDownCrossPoint = new t.Point(l._targetPoint.x, l._targetPoint.y)) : o && (Math.abs(i.top - c.y) <= l._dragThreshold || Math.abs(c.y - i.top - i.height) <= l._dragThreshold || c.y >= i.top && c.y <= i.top + i.height) ? (l._capturedEle = l._hLine, l._contentDragStartPoint = void 0, t.addClass(l._chart.hostElement, a._CSS_LINE_DRAGGABLE)) : h && (Math.abs(n.left - c.x) <= l._dragThreshold || Math.abs(c.x - n.left - n.width) <= l._dragThreshold || c.x >= n.left && c.x <= n.left + n.width) && (l._capturedEle = l._vLine, l._contentDragStartPoint = void 0, t.addClass(l._chart.hostElement, a._CSS_LINE_DRAGGABLE)), e.preventDefault())
      }, a.prototype._onMouseup = function(e) {
        var n = this,
          r = n._alignment === s.Auto && n._capturedEle === n._markerContent && n._lines !== i.None;
        n._capturedEle = void 0, n._contentDragStartPoint = void 0, n._mouseDownCrossPoint = void 0, r && (n._updatePositionByAlignment(), n._updatePositionByAlignment()), t.removeClass(n._chart.hostElement, a._CSS_LINE_DRAGGABLE)
      }, a.prototype._moveMarker = function(n) {
        var s, a, o, h, l, c, u = this,
          _ = u._chart,
          p = u._getEventPoint(n),
          d = u._plotRect,
          f = u._interaction === r.Drag,
          g = u._lines === i.Horizontal,
          y = u._lines === i.Vertical,
          m = u._seriesIndex,
          b = t.getElementRect(_.hostElement);
        if (d && u._isVisible && u._interaction !== r.None && (u._interaction !== r.Drag || u._capturedEle && u._lines !== i.None)) {
          if (f && (u._contentDragStartPoint ? (p.x = g ? u._targetPoint.x : u._mouseDownCrossPoint.x + p.x - u._contentDragStartPoint.x, p.y = y ? u._targetPoint.y : u._mouseDownCrossPoint.y + p.y - u._contentDragStartPoint.y) : g || !u._dragLines && u._capturedEle === u._hLine ? p.x = u._targetPoint.x : (y || !u._dragLines && u._capturedEle === u._vLine) && (p.y = u._targetPoint.y)), f && u._lines === i.Horizontal || !u._dragLines && u._capturedEle === u._hLine) {
            if (p.y <= d.top || p.y >= d.top + d.height) return
          } else if (f && u._lines === i.Vertical || !u._dragLines && u._capturedEle === u._vLine) {
            if (p.x <= d.left || p.x >= d.left + d.width) return
          } else if (p.x <= d.left || p.y <= d.top || p.x >= d.left + d.width || p.y >= d.top + d.height) return;
          if (null != m && m >= 0 && m < _.series.length) {
            if (s = _.series[m], null == (a = s.hitTest(new t.Point(p.x, NaN))) || null == a.x || null == a.y) return;
            o = s.axisX || _.axisX, h = s._getAxisY(), l = t.isDate(a.x) ? e.FlexChart._toOADate(a.x) : a.x, l = t.isString(l) ? a.pointIndex : l, c = t.isDate(a.y) ? e.FlexChart._toOADate(a.y) : a.y;
            var v = this._getElementPaddingValuee(_.hostElement, "padding-left"),
              x = this._getElementPaddingValuee(_.hostElement, "padding-top");
            p.x = o.convert(l) + v + b.left, p.y = h.convert(c) + x + b.top
          }
          u._updateMarkerPosition(p), n.preventDefault()
        }
      }, a.prototype._getElementPaddingValuee = function(t, e) {
        return +window.getComputedStyle(t, null).getPropertyValue(e).replace("px", "")
      }, a.prototype._show = function(t) {
        (t || this._marker).style.display = "block"
      }, a.prototype._hide = function(t) {
        (t || this._marker).style.display = "none"
      }, a.prototype._toggleVisibility = function() {
        this._isVisible ? this._show() : this._hide()
      }, a.prototype._resetDefaultValue = function() {
        var e = this;
        e._isVisible = !0, e._alignment = s.Auto, e._lines = i.None, e._interaction = r.None, e._horizontalPosition = null, e._verticalPosition = null, e._content = null, e._seriesIndex = null, e._dragThreshold = 15, e._dragContent = !1, e._dragLines = !1, e._targetPoint = new t.Point
      }, a.prototype._initialize = function() {
        var i, n = this,
          r = n._chart.hostElement.querySelector("." + e.FlexChart._CSS_PLOT_AREA);
        n._plot = r, n._marker || n._createMarker(), r && (n._plotRect = t.getElementRect(r), i = r.getBBox(), n._plotRect.width = i.width, n._plotRect.height = i.height, n._updateMarkerSize(), n._updateLinesSize()), n._updateMarkerPosition(), n._wrapperMoveMarker = n._moveMarker.bind(n), n._attach()
      }, a.prototype._createMarker = function() {
        var e, i, n = this;
        e = document.createElement("div"), t.addClass(e, a._CSS_MARKER), (i = n._getContainer()).appendChild(e), n._markerContainer = i, n._marker = e, n._createChildren()
      }, a.prototype._removeMarker = function() {
        var t = this,
          e = t._markerContainer;
        e.removeChild(t._marker), t._content = null, t._hLine = null, t._vLine = null, e.hasChildNodes() || (t._chart.hostElement.removeChild(t._markerContainer), t._markerContainer = null), t._marker = null
      }, a.prototype._getContainer = function() {
        var t = this._chart.hostElement.querySelector(a._CSS_MARKER_CONTAINER);
        return t || (t = this._createContainer()), t
      }, a.prototype._createContainer = function() {
        var e = document.createElement("div"),
          i = this._chart.hostElement;
        return t.addClass(e, a._CSS_MARKER_CONTAINER), i.insertBefore(e, i.firstChild), e
      }, a.prototype._createChildren = function() {
        var e, i, n, s, o = this,
          h = o._marker;
        (s = document.createElement("div")).style.position = "absolute", s.style.height = "100%", s.style.width = "100%", h.appendChild(s), o._dragEle = s, e = document.createElement("div"), t.addClass(e, a._CSS_MARKER_CONTENT), h.appendChild(e), o._markerContent = e, i = document.createElement("div"), t.addClass(i, a._CSS_MARKER_HLINE), h.appendChild(i), o._hLine = i, n = document.createElement("div"), t.addClass(n, a._CSS_MARKER_VLINE), h.appendChild(n), o._vLine = n, o._toggleElesDraggableClass(o._interaction === r.Drag), o._resetLinesVisibility()
      }, a.prototype._toggleElesDraggableClass = function(e) {
        var n = this;
        t.toggleClass(n._hLine, a._CSS_LINE_DRAGGABLE, e), t.toggleClass(n._vLine, a._CSS_LINE_DRAGGABLE, e), t.toggleClass(n._dragEle, a._CSS_LINE_DRAGGABLE, e && n._dragContent && n._lines !== i.None)
      }, a.prototype._updateMarkerSize = function() {
        var e = this,
          i = e._plotRect,
          n = e._chart.hostElement,
          r = window.getComputedStyle(n, null),
          s = t.getElementRect(n);
        e._marker && (e._marker.style.marginTop = i.top - s.top - (parseFloat(r.getPropertyValue("padding-top")) || 0) + "px", e._marker.style.marginLeft = i.left - s.left - (parseFloat(r.getPropertyValue("padding-left")) || 0) + "px")
      }, a.prototype._updateLinesSize = function() {
        var t = this,
          e = t._plotRect;
        t._hLine && t._vLine && (t._hLine.style.width = e.width + "px", t._vLine.style.height = e.height + "px")
      }, a.prototype._resetLinesVisibility = function() {
        var t = this;
        t._hLine && t._vLine && (t._hide(t._hLine), t._hide(t._vLine), t._lines !== i.Horizontal && t._lines !== i.Both || t._show(t._hLine), t._lines !== i.Vertical && t._lines !== i.Both || t._show(t._vLine))
      }, a.prototype._updateMarkerPosition = function(t) {
        var e, i, n = this,
          r = n._plotRect,
          s = n._targetPoint,
          a = !1;
        n._interaction;
        n._plot && (e = r.left + r.width * (n._horizontalPosition || 0), i = r.top + r.height * (n._verticalPosition || 0), null == n._horizontalPosition && t && (e = t.x), null == n._verticalPosition && t && (i = t.y), e === s.x && i === s.y || (a = !0), s.x = e, s.y = i, n._toggleVisibility(), n._content && n._updateContent(), a && n._raisePositionChanged(e, i), n._updatePositionByAlignment(!!t))
      }, a.prototype._updateContent = function() {
        var t, e = this,
          i = e._chart,
          n = e._targetPoint,
          r = i.hitTest(n);
        t = e._content.call(null, r, n), e._markerContent.innerHTML = t || ""
      }, a.prototype._raisePositionChanged = function(e, i) {
        this._plotRect;
        this.onPositionChanged(new t.Point(e, i))
      }, a.prototype._updatePositionByAlignment = function(t) {
        var e = this,
          i = e._alignment,
          n = e._targetPoint,
          a = e._marker,
          o = 0,
          h = 0,
          l = a.clientWidth,
          c = a.clientHeight,
          u = e._plotRect;
        e._plot && (!e._capturedEle || e._capturedEle && e._capturedEle !== e._markerContent ? (i === s.Auto ? (n.x + l + 12 > u.left + u.width && n.x - l >= 0 && (h = l), o = c, n.y - c < u.top && (o = 0)) : (1 == (1 & i) && (h = l), 2 == (2 & i) && (o = c)), e._interaction === r.Move && 0 === o && 0 === h && null == this.verticalPosition && (h = -12)) : (parseInt(e._hLine.style.top) > 0 && (o = c), parseInt(e._vLine.style.left) > 0 && (h = l)), a.style.left = n.x - h - u.left + "px", a.style.top = n.y - o - u.top + "px", e._hLine.style.top = o + "px", e._hLine.style.left = u.left - n.x + h + "px", e._vLine.style.top = u.top - n.y + o + "px", e._vLine.style.left = h + "px")
      }, a.prototype._getEventPoint = function(e) {
        return e instanceof MouseEvent ? new t.Point(e.pageX, e.pageY) : new t.Point(e.changedTouches[0].pageX, e.changedTouches[0].pageY)
      }, a.prototype._pointInRect = function(t, e) {
        return !(!t || !e) && (t.x >= e.left && t.x <= e.left + e.width && t.y >= e.top && t.y <= e.top + e.height)
      }, a._CSS_MARKER = "wj-chart-linemarker", a._CSS_MARKER_HLINE = "wj-chart-linemarker-hline", a._CSS_MARKER_VLINE = "wj-chart-linemarker-vline", a._CSS_MARKER_CONTENT = "wj-chart-linemarker-content", a._CSS_MARKER_CONTAINER = "wj-chart-linemarker-container", a._CSS_LINE_DRAGGABLE = "wj-chart-linemarker-draggable", a._CSS_TOUCH_DISABLED = "wj-flexchart-touch-disabled", a
    }();
    e.LineMarker = a
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function t(t, e, i, n) {
        this._seriesIndex = t, this._pointIndex = e, this._dataX = i, this._dataY = n
      }
      return Object.defineProperty(t.prototype, "seriesIndex", {
        get: function() {
          return this._seriesIndex
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "pointIndex", {
        get: function() {
          return this._pointIndex
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "dataX", {
        get: function() {
          return this._dataX
        },
        set: function(t) {
          t !== this._dataX && (this._dataX = t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "dataY", {
        get: function() {
          return this._dataY
        },
        set: function(t) {
          t !== this._dataY && (this._dataY = t)
        },
        enumerable: !0,
        configurable: !0
      }), t
    }();
    e._DataPoint = i;
    var n;
    ! function(t) {
      t[t.X = 0] = "X", t[t.Y = 1] = "Y", t[t.XY = 2] = "XY"
    }(n = e._MeasureOption || (e._MeasureOption = {}));
    var r = function() {
      function e(t) {
        this._rect = t
      }
      return Object.defineProperty(e.prototype, "rect", {
        get: function() {
          return this._rect
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype.contains = function(t) {
        var e = this._rect;
        return t.x >= e.left && t.x <= e.right && t.y >= e.top && t.y <= e.bottom
      }, e.prototype.pointDistance = function(t, e, i) {
        var r = e.x - t.x,
          s = e.y - t.y;
        return i == n.X ? Math.abs(r) : i == n.Y ? Math.abs(s) : Math.sqrt(r * r + s * s)
      }, e.prototype.distance = function(e) {
        var i = n.XY;
        null === e.x ? i = n.Y : null === e.y && (i = n.X);
        var r = this._rect;
        return e.x < r.left ? e.y < r.top ? this.pointDistance(e, new t.Point(r.left, r.top), i) : e.y > r.bottom ? this.pointDistance(e, new t.Point(r.left, r.bottom), i) : i == n.Y ? 0 : r.left - e.x : e.x > r.right ? e.y < r.top ? this.pointDistance(e, new t.Point(r.right, r.top), i) : e.y > r.bottom ? this.pointDistance(e, new t.Point(r.right, r.bottom), i) : i == n.Y ? 0 : e.x - r.right : i == n.X ? 0 : e.y < r.top ? r.top - e.y : e.y > r.bottom ? e.y - r.bottom : 0
      }, e
    }();
    e._RectArea = r;
    var s = function() {
      function t(t, e) {
        this._center = t, this.setRadius(e)
      }
      return t.prototype.setRadius = function(t) {
        this._rad = t, this._rad2 = t * t
      }, Object.defineProperty(t.prototype, "center", {
        get: function() {
          return this._center
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.contains = function(t) {
        var e = this._center.x - t.x,
          i = this._center.y - t.y;
        return e * e + i * i <= this._rad2
      }, t.prototype.distance = function(t) {
        var e = isNaN(t.x) ? 0 : this._center.x - t.x,
          i = isNaN(t.y) ? 0 : this._center.y - t.y,
          n = e * e + i * i;
        return n <= this._rad2 ? 0 : Math.sqrt(n) - this._rad
      }, t
    }();
    e._CircleArea = s;
    var a = function() {
      function i(t, e) {
        this._x = [], this._y = [], this._x = t, this._y = e
      }
      return i.prototype.contains = function(t) {
        return !1
      }, i.prototype.distance = function(i) {
        for (var n = NaN, r = 0; r < this._x.length - 1; r++) {
          var s = e.FlexChart._dist(i, new t.Point(this._x[r], this._y[r]), new t.Point(this._x[r + 1], this._y[r + 1]));
          (isNaN(n) || s < n) && (n = s)
        }
        return n
      }, i
    }();
    e._LinesArea = a;
    var o = function() {
      return function() {}
    }();
    e._HitResult = o;
    var h = function() {
      function n(t) {
        this._map = {}, this._chart = t
      }
      return n.prototype.add = function(t, e) {
        this._map[e] && (t.tag || (t.tag = new i(e, NaN, NaN, NaN)), this._map[e].push(t))
      }, n.prototype.clear = function() {
        this._map = {};
        for (var t = this._chart.series, i = 0; i < t.length; i++) t[i].hitTest === e.Series.prototype.hitTest && (this._map[i] = new Array)
      }, n.prototype.hitTest = function(e, i) {
        void 0 === i && (i = !1);
        for (var n = null, r = Number.MAX_VALUE, s = this._chart.series.length - 1; s >= 0; s--) {
          var h = this._map[s];
          if (h) {
            for (var l = h.length - 1; l >= 0; l--) {
              var c = h[l];
              if (!t.tryCast(c, a) || i) {
                var u = c.distance(e);
                if (u < r && (r = u, n = c, 0 == r)) break
              }
            }
            if (0 == r) break
          }
        }
        if (n) {
          var _ = new o;
          return _.area = n, _.distance = r, _
        }
        return null
      }, n.prototype.hitTestSeries = function(t, e) {
        var i = null,
          n = Number.MAX_VALUE,
          r = this._map[e];
        if (r)
          for (var s = r.length - 1; s >= 0; s--) {
            var a = r[s],
              h = a.distance(t);
            if (h < n && (n = h, i = a, 0 == n)) break
          }
        if (i) {
          var l = new o;
          return l.area = i, l.distance = n, l
        }
        return null
      }, n
    }();
    e._HitTester = h
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function i() {
        this._DEFAULT_WIDTH = 2, this._DEFAULT_SYM_SIZE = 10, this.clipping = !0
      }
      return i.prototype.clear = function() {
        this.seriesCount = 0, this.seriesIndex = 0
      }, i.prototype._renderLabels = function(i, n, r, s, a) {
        var o = r.length,
          h = s.dataLabel,
          l = h.border,
          c = h.offset,
          u = h.connectingLine;
        void 0 === c && (c = u ? 16 : 0), l && (c -= 2);
        for (var _ = 0; _ < o; _++) {
          var p = r[_],
            d = t.asType(p.tag, e._DataPoint, !0);
          d && !p.ignoreLabel && this._renderLabel(i, p, d, s, h, n, c, a)
        }
      }, i.prototype._renderLabel = function(t, i, n, r, s, a, o, h) {
        var l = null == s.position ? e.LabelPosition.Top : s.position,
          c = s.border,
          u = s.connectingLine,
          _ = new e.HitTestInfo(r, d);
        _._setDataPoint(n);
        var p = r._getLabelContent(_, s.content),
          d = this._getLabelPoint(a, n);
        if (this._getPointAndPosition(d, l, i, r), r._plotRect.contains(d)) {
          var f = new e.DataLabelRenderEventArgs(t, _, d, p);
          if (s.onRendering && (s.onRendering(f) ? (p = f.text, d = f.point) : p = null), p) {
            var g = this._renderLabelAndBorder(t, p, l, o, d, u, 2, c);
            if (g) {
              var y = new e._RectArea(g);
              y.tag = n, h.push(y)
            }
          }
        }
      }, i.prototype._getPointAndPosition = function(t, i, n, r) {
        if (n instanceof e._RectArea) {
          var s = n;
          r._isRotated() ? t.y = s.rect.top + .5 * s.rect.height : t.x = s.rect.left + .5 * s.rect.width
        }
      }, i.prototype._getLabelPoint = function(e, i) {
        var n = e._getAxisX(),
          r = e._getAxisY();
        return new t.Point(n.convert(i.dataX), r.convert(i.dataY))
      }, i.prototype._renderLabelAndBorder = function(t, i, n, r, s, a, o, h) {
        var l, c = "wj-data-label",
          u = "wj-data-label-line",
          _ = e.FlexChart._CSS_DATA_LABELS;
        switch (n) {
          case e.LabelPosition.Top:
            a && t.drawLine(s.x, s.y, s.x, s.y - r, u), s.y -= o + r, l = e.FlexChart._renderText(t, i, s, 1, 2, c, _);
            break;
          case e.LabelPosition.Bottom:
            a && t.drawLine(s.x, s.y, s.x, s.y + r, u), s.y += o + r, l = e.FlexChart._renderText(t, i, s, 1, 0, c, _);
            break;
          case e.LabelPosition.Left:
            a && t.drawLine(s.x, s.y, s.x - r, s.y, u), s.x -= o + r, l = e.FlexChart._renderText(t, i, s, 2, 1, c, _);
            break;
          case e.LabelPosition.Right:
            a && t.drawLine(s.x, s.y, s.x + r, s.y, u), s.x += o + r, l = e.FlexChart._renderText(t, i, s, 0, 1, c, _);
            break;
          case e.LabelPosition.Center:
            l = e.FlexChart._renderText(t, i, s, 1, 1, c, _)
        }
        return h && l && t.drawRect(l.left - o, l.top - o, l.width + 2 * o, l.height + 2 * o, "wj-data-label-border"), l
      }, i.prototype.getOption = function(e, i) {
        var n = this.chart.options;
        if (i && (n = n ? n[i] : null), n && !t.isUndefined(n[e]) && null !== n[e]) return n[e]
      }, i.prototype.getNumOption = function(e, i) {
        var n = this.chart.options;
        if (i && (n = n ? n[i] : null), n && n[e]) return t.asNumber(n[e], !0)
      }, i.cloneStyle = function(t, e) {
        if (!t) return t;
        var i = {};
        for (var n in t) e && e.indexOf(n) >= 0 || (i[n] = t[n]);
        return i
      }, i.prototype.isValid = function(i, n, r, s) {
        return e._DataInfo.isValid(i) && e._DataInfo.isValid(n) && e.FlexChart._contains(this.chart._plotRect, new t.Point(i, n))
      }, i.prototype.load = function() {}, i.prototype.unload = function() {}, i
    }();
    e._BasePlotter = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n() {
        var t = null !== i && i.apply(this, arguments) || this;
        return t.origin = 0, t.width = .7, t.isVolume = !1, t._volHelper = null, t.stackPosMap = {}, t.stackNegMap = {}, t.stacking = e.Stacking.None, t
      }
      return __extends(n, i), n.prototype.clear = function() {
        i.prototype.clear.call(this), this.stackNegMap[this.chart.axisY._uniqueId] = {}, this.stackPosMap[this.chart.axisY._uniqueId] = {}, this._volHelper = null
      }, n.prototype.load = function() {
        if (i.prototype.load.call(this), this.isVolume) {
          var n, r, s, a, o, h, l, c, u = null,
            _ = null;
          for (h = 0; h < this.chart.series.length; h++) {
            if (n = this.chart.series[h], o = n.getDataType(1) || n.chart._xDataType, r = n._getAxisX(), s = n._getChartType(), (s = null === s || t.isUndefined(s) ? this.chart._getChartType() : s) === e.ChartType.Column) {
              var p = this.chart ? this.chart._bindingSeparator : ",",
                d = n.binding.split(p).length - 1;
              a = n._getBindingValues(d)
            } else a = s === e.ChartType.Candlestick ? n._getBindingValues(4) : null;
            if (o === t.DataType.Date) {
              var f;
              for (l = [], c = [], h = 0; h < n._getLength(); h++) f = n._getItem(h)[n.bindingX].valueOf(), l.push(f), c.push({
                value: f,
                text: t.Globalize.format(new Date(f), r.format || "d")
              })
            } else l = this.dataInfo.getXVals();
            if (u = this.dataInfo.getMinX(), _ = this.dataInfo.getMaxX(), a && a.length > 0) {
              this._volHelper = new e._VolumeHelper(a, l, u, _, o), r._customConvert = this._volHelper.convert.bind(this._volHelper), r._customConvertBack = this._volHelper.convertBack.bind(this._volHelper), c && c.length > 0 && (this._itemsSource = r.itemsSource = c);
              break
            }
          }
        }
      }, n.prototype.unload = function() {
        i.prototype.unload.call(this);
        for (var t, e = 0; e < this.chart.series.length; e++)(t = this.chart.series[e]._getAxisX()) && (t._customConvert = null, t._customConvertBack = null, t.itemsSource && t.itemsSource == this._itemsSource && (this._itemsSource = t.itemsSource = null))
      }, n.prototype.adjustLimits = function(i, n) {
        this.dataInfo = i;
        var r = i.getMinX(),
          s = i.getMaxX(),
          a = i.getMinY(),
          o = i.getMaxY(),
          h = i.getDeltaX();
        h <= 0 && (h = 1), !this.isVolume || this.chart._getChartType() !== e.ChartType.Column && this.chart._getChartType() !== e.ChartType.Candlestick ? this.unload() : this.load();
        for (var l = 0; l < this.chart.series.length; l++) {
          var c = this.chart.series[l],
            u = c._getChartType();
          (u = null === u || t.isUndefined(u) ? this.chart._getChartType() : u) !== e.ChartType.Column && u !== e.ChartType.Bar || this._isRange(c) && c._getBindingValues(1).forEach(function(t) {
            t < a ? a = t : t > o && (o = t)
          })
        }
        return this.rotated ? (this.chart.axisY._getLogBase() || i.getDataTypeY() === t.DataType.Date || (this.origin > o ? o = this.origin : this.origin < a && (a = this.origin)), new t.Rect(a, r - .5 * h, o - a, s - r + h)) : (this.chart.axisY._getLogBase() || i.getDataTypeY() === t.DataType.Date || (this.origin > o ? o = this.origin : this.origin < a && (a = this.origin)), new t.Rect(r - .5 * h, a, s - r + h, o - a))
      }, n.prototype._isRange = function(t) {
        var e = this.chart ? this.chart._bindingSeparator : ",",
          i = (null == t.binding ? "" : t.binding.split(e)).length - 1;
        return this.isVolume ? 2 === i : 1 === i
      }, n.prototype.plotSeries = function(i, n, r, s, a, o, h, l) {
        var c = [],
          u = this.chart.series.indexOf(s),
          _ = t.asType(s, e.SeriesBase),
          p = this.chart.options,
          d = this.width,
          f = 0;
        if (o = o || 0, h = h || 1, p && p.groupWidth) {
          var g = p.groupWidth;
          if (t.isNumber(g)) {
            m = t.asNumber(g);
            isFinite(m) && m > 0 && (f = m, d = 1)
          } else if (t.isString(g)) {
            var y = t.asString(g);
            if (y && y.indexOf("%") >= 0) {
              y = y.replace("%", "");
              m = parseFloat(y);
              isFinite(m) && (m < 0 ? m = 0 : m > 100 && (m = 100), f = 0, d = m / 100)
            } else {
              var m = parseFloat(y);
              isFinite(m) && m > 0 && (f = m, d = 1)
            }
          }
        }
        var b = d / h,
          v = _._getAxisY()._uniqueId,
          x = this.stackNegMap[v],
          w = this.stackPosMap[v],
          S = s.getValues(0),
          P = s.getValues(1),
          C = this._isRange(_),
          T = _._bindValues(null == _._cv ? null == this.chart.collectionView ? null : this.chart.collectionView.items : _._cv.items, _._getBinding(1)).values;
        if (S) {
          if (P || (P = this.dataInfo.getXVals()), P) {
            var M = this.dataInfo.getDeltaX();
            M > 0 && (d *= M, b *= M)
          }
          var A = _._getSymbolFill(u),
            N = _._getAltSymbolFill(u) || A,
            L = _._getSymbolStroke(u),
            I = _._getAltSymbolStroke(u) || L,
            E = S.length;
          null != P && (E = Math.min(E, P.length));
          var k, D, O = this.origin,
            R = 0,
            j = this.stacking != e.Stacking.None,
            F = this.stacking == e.Stacking.Stacked100pc;
          if (void 0 !== _._getChartType() && (j = F = !1), this.rotated) {
            O < n.actualMin ? O = n.actualMin : O > n.actualMax && (O = n.actualMax), _._isCustomAxisY() && (j = F = !1);
            for (var V = n.convert(O), X = r.actualMin, B = r.actualMax, Y = 0; Y < E; Y++) {
              var z = P ? P[Y] : Y,
                H = S[Y],
                G = V;
              if (this._getSymbolOrigin && (G = r.convert(this._getSymbolOrigin(O, Y))), C && T && T.length) {
                it = T[Y];
                e._DataInfo.isValid(it) && (G = n.convert(it))
              }
              if (this._getSymbolStyles) {
                var W = this._getSymbolStyles(Y);
                A = W && W.fill ? W.fill : A, N = W && W.fill ? W.fill : N, L = W && W.stroke ? W.fill : L, I = W && W.stroke ? W.fill : I
              }
              if (k = H > 0 ? A : N, D = H > 0 ? L : I, i.fill = k, i.stroke = D, e._DataInfo.isValid(z) && e._DataInfo.isValid(H))
                if (j) {
                  rt = z + .5 * d;
                  if ((nt = z - .5 * d) < X && rt < X || nt > B && rt > B) continue;
                  nt = r.convert(Math.max(nt, X)), rt = r.convert(Math.min(rt, B));
                  var U;
                  if (F) {
                    var q = this.dataInfo.getStackedAbsSum(z);
                    H /= q
                  }
                  st = 0;
                  H > 0 ? (st = isNaN(w[z]) ? 0 : w[z], U = n.convert(st), at = n.convert(st + H), w[z] = st + H) : (st = isNaN(x[z]) ? 0 : x[z], U = n.convert(st), at = n.convert(st + H), x[z] = st + H), l && c.push(new t.Point(at, r.convert(z)));
                  Z = new t.Rect(Math.min(U, at), Math.min(nt, rt), Math.abs(at - U), Math.abs(rt - nt));
                  if (f > 0) {
                    (Q = 1 - f / Z.height) < 0 && (Q = 0);
                    $ = Z.top + .5 * Z.height;
                    Z.top += ($ - Z.top) * Q, Z.height = Math.min(f, Z.height)
                  }
                  lt = new e._RectArea(Z);
                  this.drawSymbol(i, Z, s, Y, new t.Point(at, Z.top + .5 * Z.height)), s._setPointIndex(Y, R), R++, lt.tag = new e._DataPoint(u, Y, st + H, z), this.hitTester.add(lt, u)
                } else {
                  rt = z - .5 * d + (o + 1) * b;
                  if ((nt = z - .5 * d + o * b) < X && rt < X || nt > B && rt > B) continue;
                  nt = r.convert(Math.max(nt, X)), rt = r.convert(Math.min(rt, B));
                  var K = n.convert(H),
                    Z = new t.Rect(Math.min(K, G), Math.min(nt, rt), Math.abs(G - K), Math.abs(rt - nt));
                  if (l && c.push(new t.Point(K, (nt + rt) / 2)), f > 0) {
                    var J = f / h,
                      Q = 1 - J / Z.height;
                    Q < 0 && (Q = 0);
                    var $ = r.convert(z);
                    Z.top += ($ - Z.top) * Q, Z.height = Math.min(J, Z.height)
                  }
                  lt = new e._RectArea(Z);
                  this.drawSymbol(i, Z, s, Y, new t.Point(K, Z.top + .5 * Z.height)), s._setPointIndex(Y, R), R++, lt.tag = new e._DataPoint(u, Y, H, z), this.hitTester.add(lt, u)
                }
            }
          } else {
            O < r.actualMin ? O = r.actualMin : O > r.actualMax && (O = r.actualMax);
            var V = r.convert(O),
              tt = n.actualMin,
              et = n.actualMax;
            _._isCustomAxisY() && (j = F = !1);
            for (Y = 0; Y < E; Y++) {
              var G = V,
                z = P ? P[Y] : Y,
                H = S[Y];
              if (this._getSymbolOrigin && (G = r.convert(this._getSymbolOrigin(O, Y, E))), C && T && T.length) {
                var it = T[Y];
                e._DataInfo.isValid(it) && (G = r.convert(it))
              }
              if (this._getSymbolStyles && (A = (W = this._getSymbolStyles(Y, E)) && W.fill ? W.fill : A, N = W && W.fill ? W.fill : N, L = W && W.stroke ? W.stroke : L, I = W && W.stroke ? W.stroke : I), k = H > 0 ? A : N, D = H > 0 ? L : I, i.fill = k, i.stroke = D, e._DataInfo.isValid(z) && e._DataInfo.isValid(H))
                if (j) {
                  at = z + .5 * d;
                  if ((U = z - .5 * d) < tt && at < tt || U > et && at > et) continue;
                  if (U = n.convert(U), at = n.convert(at), !e._DataInfo.isValid(U) || !e._DataInfo.isValid(at)) continue;
                  var nt, rt;
                  F && (H /= q = this.dataInfo.getStackedAbsSum(z));
                  var st = 0;
                  H > 0 ? (st = isNaN(w[z]) ? 0 : w[z], nt = r.convert(Math.max(st, r.actualMin)), rt = r.convert(Math.max(st + H, r.actualMin)), w[z] = st + H) : (st = isNaN(x[z]) ? 0 : x[z], nt = r.convert(st), rt = r.convert(st + H), x[z] = st + H), l && c.push(new t.Point(n.convert(z), rt));
                  Z = new t.Rect(Math.min(U, at), Math.min(nt, rt), Math.abs(at - U), Math.abs(rt - nt));
                  if (f > 0) {
                    (Q = 1 - f / Z.width) < 0 && (Q = 0);
                    ht = Z.left + .5 * Z.width;
                    Z.left += (ht - Z.left) * Q, Z.width = Math.min(f, Z.width)
                  }
                  lt = new e._RectArea(Z);
                  this.drawSymbol(i, Z, s, Y, new t.Point(Z.left + .5 * Z.width, rt)), s._setPointIndex(Y, R), R++, lt.tag = new e._DataPoint(u, Y, z, st + H), this.hitTester.add(lt, u)
                } else {
                  var at = z - .5 * d + (o + 1) * b;
                  if ((U = z - .5 * d + o * b) < tt && at < tt || U > et && at > et) continue;
                  if (U = n.convert(U), at = n.convert(at), !e._DataInfo.isValid(U) || !e._DataInfo.isValid(at)) continue;
                  var ot = r.convert(H),
                    Z = new t.Rect(Math.min(U, at), Math.min(ot, G), Math.abs(at - U), Math.abs(G - ot));
                  if (l && c.push(new t.Point((U + at) / 2, ot)), f > 0) {
                    (Q = 1 - (J = f / h) / Z.width) < 0 && (Q = 0);
                    var ht = n.convert(z);
                    Z.left += (ht - Z.left) * Q, Z.width = Math.min(J, Z.width)
                  }
                  var lt = new e._RectArea(Z);
                  this.drawSymbol(i, Z, s, Y, new t.Point(Z.left + .5 * Z.width, ot)), s._setPointIndex(Y, R), R++, lt.tag = new e._DataPoint(u, Y, z, H), this.hitTester.add(lt, u)
                }
            }
          }
          l && c && c.length && l(c)
        }
      }, n.prototype.drawSymbol = function(t, i, n, r, s) {
        var a = this;
        if (this.chart.itemFormatter) {
          t.startGroup();
          var o = new e.HitTestInfo(this.chart, s, e.ChartElement.SeriesSymbol);
          o._setData(n, r), this.chart.itemFormatter(t, o, function() {
            a.drawDefaultSymbol(t, i, n)
          }), t.endGroup()
        } else this.drawDefaultSymbol(t, i, n)
      }, n.prototype.drawDefaultSymbol = function(t, e, i) {
        t.drawRect(e.left, e.top, e.width, e.height, null, i.symbolStyle)
      }, n
    }(e._BasePlotter);
    e._BarPlotter = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n() {
        var t = i.call(this) || this;
        return t.hasSymbols = !1, t.hasLines = !0, t.isSpline = !1, t.stacking = e.Stacking.None, t.stackPos = {}, t.stackNeg = {}, t.clipping = !1, t
      }
      return __extends(n, i), n.prototype.clear = function() {
        i.prototype.clear.call(this), this.stackNeg = {}, this.stackPos = {}
      }, n.prototype.adjustLimits = function(e, i) {
        this.dataInfo = e;
        var n = e.getMinX(),
          r = e.getMinY(),
          s = e.getMaxX(),
          a = e.getMaxY();
        if (this.isSpline && !this.chart.axisY._getLogBase()) {
          var o = .1 * (a - r);
          r -= o, a += o
        }
        return this.rotated ? new t.Rect(r, n, a - r, s - n) : new t.Rect(n, r, s - n, a - r)
      }, n.prototype.plotSeries = function(i, n, r, s, a, o, h, l) {
        var c = [],
          u = t.asType(s, e.SeriesBase),
          _ = this.chart.series.indexOf(s),
          p = s.getValues(0),
          d = s.getValues(1);
        if (p) {
          d || (d = this.dataInfo.getXVals());
          var f = e._BasePlotter.cloneStyle(s.style, ["fill"]),
            g = p.length,
            y = !0;
          d ? g = Math.min(g, d.length) : (y = !1, d = new Array(g));
          var m = this._DEFAULT_WIDTH,
            b = u._getSymbolFill(_),
            v = u._getAltSymbolFill(_) || b,
            x = u._getSymbolStroke(_),
            w = u._getAltSymbolStroke(_) || x,
            S = u._getSymbolSize();
          i.stroke = x, i.strokeWidth = m, i.fill = b;
          var P = new Array,
            C = new Array,
            T = new Array,
            M = this.rotated,
            A = this.stacking != e.Stacking.None && !u._isCustomAxisY(),
            N = this.stacking == e.Stacking.Stacked100pc && !u._isCustomAxisY();
          void 0 !== u._getChartType() && (A = N = !1);
          for (var L = u.interpolateNulls, I = !1, E = 0; E < g; E++) {
            var k = y ? d[E] : E,
              D = p[E];
            if (e._DataInfo.isValid(k) && e._DataInfo.isValid(D)) {
              if (A)
                if (N && (D /= this.dataInfo.getStackedAbsSum(k)), D >= 0) {
                  O = isNaN(this.stackPos[k]) ? 0 : this.stackPos[k];
                  D = this.stackPos[k] = O + D
                } else {
                  var O = isNaN(this.stackNeg[k]) ? 0 : this.stackNeg[k];
                  D = this.stackNeg[k] = O + D
                }
              var R;
              if (M) {
                R = new e._DataPoint(_, E, D, k);
                var j = n.convert(D);
                D = r.convert(k), k = j
              } else R = new e._DataPoint(_, E, k, D), k = n.convert(k), D = r.convert(D);
              if (isNaN(k) || isNaN(D)) I = !0, !0 !== L && (P.push(void 0), C.push(void 0)), T.push(!0);
              else {
                P.push(k), C.push(D), T.push(!1), l && c.push(new t.Point(k, D));
                var F = new e._CircleArea(new t.Point(k, D), .5 * S);
                F.tag = R, this.hitTester.add(F, _)
              }
            } else I = !0, !0 !== L && (P.push(void 0), C.push(void 0)), T.push(!0)
          }
          var V = 0;
          if (this.hasLines)
            if (i.fill = null, I && !0 !== L) {
              for (var X = [], B = [], E = 0; E < g; E++) void 0 === P[E] ? (X.length > 1 && (this._drawLines(i, X, B, null, f, this.chart._plotrectId), this.hitTester.add(new e._LinesArea(X, B), _), V++), X = [], B = []) : (X.push(P[E]), B.push(C[E]));
              X.length > 1 && (this._drawLines(i, X, B, null, f, this.chart._plotrectId), this.hitTester.add(new e._LinesArea(X, B), _), V++)
            } else this._drawLines(i, P, C, null, f, this.chart._plotrectId), this.hitTester.add(new e._LinesArea(P, C), _), V++;
          if ((this.hasSymbols || this.chart.itemFormatter) && S > 0) {
            i.fill = b;
            for (var Y = 0, E = 0; E < g; E++)
              if (!L || !T[E]) {
                var k = P[Y],
                  D = C[Y];
                (!1 === this.hasLines || this.chart.itemFormatter) && (i.fill = p[E] > 0 ? b : v, i.stroke = p[E] > 0 ? x : w), this.isValid(k, D, n, r) && (this._drawSymbol(i, k, D, S, u, E), s._setPointIndex(E, V), V++), Y++
              }
          }
          l && c && c.length && l(c)
        }
      }, n.prototype._drawLines = function(t, e, i, n, r, s) {
        this.isSpline ? t.drawSplines(e, i, n, r, s) : t.drawLines(e, i, n, r, s)
      }, n.prototype._drawSymbol = function(i, n, r, s, a, o) {
        var h = this;
        if (this.chart.itemFormatter) {
          i.startGroup();
          var l = new e.HitTestInfo(this.chart, new t.Point(n, r), e.ChartElement.SeriesSymbol);
          l._setData(a, o), this.chart.itemFormatter(i, l, function() {
            h.hasSymbols && h._drawDefaultSymbol(i, n, r, s, a.symbolMarker, a.symbolStyle)
          }), i.endGroup()
        } else this._drawDefaultSymbol(i, n, r, s, a.symbolMarker, a.symbolStyle)
      }, n.prototype._drawDefaultSymbol = function(t, i, n, r, s, a) {
        s == e.Marker.Dot ? t.drawEllipse(i, n, .5 * r, .5 * r, null, a) : s == e.Marker.Box && t.drawRect(i - .5 * r, n - .5 * r, r, r, null, a)
      }, n
    }(e._BasePlotter);
    e._LinePlotter = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n() {
        var t = i.call(this) || this;
        return t.stacking = e.Stacking.None, t.isSpline = !1, t.stackPos = {}, t.stackNeg = {}, t
      }
      return __extends(n, i), n.prototype.adjustLimits = function(e, i) {
        this.dataInfo = e;
        var n = e.getMinX(),
          r = e.getMinY(),
          s = e.getMaxX(),
          a = e.getMaxY();
        if (this.isSpline) {
          var o = .1 * (a - r);
          this.chart.axisY._getLogBase() || (r -= o), a += o
        }
        return this.rotated ? new t.Rect(r, n, a - r, s - n) : new t.Rect(n, r, s - n, a - r)
      }, n.prototype.clear = function() {
        i.prototype.clear.call(this), this.stackNeg = {}, this.stackPos = {}
      }, n.prototype.plotSeries = function(i, n, r, s, a, o, h, l) {
        var c = [],
          u = this.chart.series.indexOf(s),
          _ = s,
          p = s.getValues(0),
          d = s.getValues(1);
        if (p) {
          var f = p.length;
          if (f) {
            d || (d = this.dataInfo.getXVals());
            var g = !0;
            d ? d.length < f && (f = d.length) : (g = !1, d = new Array(f));
            var y = new Array,
              m = new Array,
              b = new Array,
              v = new Array,
              x = this.stacking != e.Stacking.None && !_._isCustomAxisY(),
              w = this.stacking == e.Stacking.Stacked100pc && !_._isCustomAxisY();
            void 0 !== _._getChartType() && (x = w = !1);
            for (var S = this.rotated, P = !1, C = _.interpolateNulls, T = null, M = null, A = this.chart._plotRect, N = 0; N < f; N++) {
              var L = g ? d[N] : N,
                I = p[N];
              if ((null === T || L > T) && (T = L), (null === M || L < M) && (M = L), e._DataInfo.isValid(L) && e._DataInfo.isValid(I)) {
                var E = S ? r.convert(L) : n.convert(L);
                if (x) {
                  w && (I /= this.dataInfo.getStackedAbsSum(L));
                  var k = 0;
                  I >= 0 ? (k = isNaN(this.stackPos[L]) ? 0 : this.stackPos[L], I = this.stackPos[L] = k + I) : (k = isNaN(this.stackNeg[L]) ? 0 : this.stackNeg[L], I = this.stackNeg[L] = k + I), S ? (k < n.actualMin && (k = n.actualMin), b.push(n.convert(k)), v.push(E)) : (b.push(E), k < r.actualMin && (k = r.actualMin), v.push(r.convert(k)))
                }
                if (S) {
                  D = n.convert(I);
                  isNaN(E) || isNaN(D) ? (P = !0, x || !0 === C || (y.push(void 0), m.push(void 0))) : (y.push(D), m.push(E), e.FlexChart._contains(A, new t.Point(D, E)) && ((O = new e._CircleArea(new t.Point(D, E), this._DEFAULT_SYM_SIZE)).tag = new e._DataPoint(u, N, I, L), this.hitTester.add(O, u)))
                } else {
                  var D = r.convert(I);
                  if (isNaN(E) || isNaN(D)) P = !0, x || !0 === C || (y.push(void 0), m.push(void 0));
                  else if (y.push(E), m.push(D), e.FlexChart._contains(A, new t.Point(E, D))) {
                    var O = new e._CircleArea(new t.Point(E, D), this._DEFAULT_SYM_SIZE);
                    O.tag = new e._DataPoint(u, N, L, I), this.hitTester.add(O, u)
                  }
                }
              } else P = !0, x || !0 === C || (y.push(void 0), m.push(void 0))
            }
            l && y.forEach(function(e, i) {
              null != e && c.push(new t.Point(e, m[i]))
            });
            var R = this._DEFAULT_WIDTH,
              j = a._getColorLight(u),
              F = a._getColor(u),
              V = e._BasePlotter.cloneStyle(s.style, ["fill"]),
              X = e._BasePlotter.cloneStyle(s.style, ["stroke"]);
            if (!x && !0 !== C && P) {
              for (var B = [], Y = [], N = 0; N < f; N++) void 0 === y[N] ? (B.length > 1 && (this.isSpline && (B = (z = this._convertToSpline(B, Y)).xs, Y = z.ys), i.stroke = F, i.strokeWidth = R, i.fill = "none", i.drawLines(B, Y, null, V), this.hitTester.add(new e._LinesArea(B, Y), u), S ? (B.push(n.convert(n.actualMin), n.convert(n.actualMin)), Y.push(r.convert(r.actualMax), r.convert(r.actualMin))) : (B.push(B[B.length - 1], B[0]), Y.push(r.convert(r.actualMin), r.convert(r.actualMin))), i.fill = j, i.stroke = "none", i.drawPolygon(B, Y, null, X)), B = [], Y = []) : (B.push(y[N]), Y.push(m[N]));
              B.length > 1 && (this.isSpline && (B = (z = this._convertToSpline(B, Y)).xs, Y = z.ys), i.stroke = F, i.strokeWidth = R, i.fill = "none", i.drawLines(B, Y, null, V), this.hitTester.add(new e._LinesArea(B, Y), u), S ? (B.push(n.convert(n.actualMin), n.convert(n.actualMin)), Y.push(r.convert(r.actualMax), r.convert(r.actualMin))) : (B.push(B[B.length - 1], B[0]), Y.push(r.convert(r.actualMin), r.convert(r.actualMin))), i.fill = j, i.stroke = "none", i.drawPolygon(B, Y, null, X))
            } else {
              if (this.isSpline) {
                var z = this._convertToSpline(y, m);
                y = z.xs, m = z.ys
              }
              if (x) {
                if (this.isSpline) {
                  var H = this._convertToSpline(b, v);
                  b = H.xs, v = H.ys
                }
                y = y.concat(b.reverse()), m = m.concat(v.reverse())
              } else S ? (y.push(n.convert(n.actualMin), n.convert(n.actualMin)), m[0] > m[m.length - 1] ? m.push(r.convert(T), r.convert(M)) : m.push(r.convert(M), r.convert(T))) : (y[0] > y[y.length - 1] ? y.push(n.convert(M), n.convert(T)) : y.push(n.convert(T), n.convert(M)), m.push(r.convert(r.actualMin), r.convert(r.actualMin)));
              i.fill = j, i.stroke = "none", i.drawPolygon(y, m, null, X), x ? (y = y.slice(0, y.length - b.length), m = m.slice(0, m.length - v.length)) : (y = y.slice(0, y.length - 2), m = m.slice(0, m.length - 2)), i.stroke = F, i.strokeWidth = R, i.fill = "none", i.drawLines(y, m, null, V), this.hitTester.add(new e._LinesArea(y, m), u)
            }
            this._drawSymbols(i, s, u), l && c && c.length && l(c)
          }
        }
      }, n.prototype._convertToSpline = function(t, i) {
        if (t && i) {
          var n = new e._Spline(t, i).calculate();
          return {
            xs: n.xs,
            ys: n.ys
          }
        }
        return {
          xs: t,
          ys: i
        }
      }, n.prototype._drawSymbols = function(i, n, r) {
        if (null != this.chart.itemFormatter)
          for (var s = this.hitTester._map[r], a = 0; a < s.length; a++) {
            var o = t.tryCast(s[a], e._CircleArea);
            if (o) {
              var h = o.tag;
              i.startGroup();
              var l = new e.HitTestInfo(this.chart, o.center, e.ChartElement.SeriesSymbol);
              l._setDataPoint(h), this.chart.itemFormatter(i, l, function() {}), i.endGroup()
            }
          }
      }, n
    }(e._BasePlotter);
    e._AreaPlotter = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n() {
        var t = i.call(this) || this;
        return t._MIN_SIZE = 5, t._MAX_SIZE = 30, t.hasLines = !1, t.hasSymbols = !0, t.clipping = !0, t
      }
      return __extends(n, i), n.prototype.adjustLimits = function(t, n) {
        var r = this.getNumOption("minSize", "bubble");
        this._minSize = r || this._MIN_SIZE;
        var s = this.getNumOption("maxSize", "bubble");
        this._maxSize = s || this._MAX_SIZE;
        for (var a = this.chart.series, o = a.length, h = NaN, l = NaN, c = 0; c < o; c++) {
          var u = a[c];
          if (u._getChartType() == e.ChartType.Bubble || u._chart._getChartType() == e.ChartType.Bubble) {
            var _ = u._getBindingValues(1);
            if (_)
              for (var p = _.length, d = 0; d < p; d++) e._DataInfo.isValid(_[d]) && ((isNaN(h) || _[d] < h) && (h = _[d]), (isNaN(l) || _[d] > l) && (l = _[d]))
          }
        }
        this._minValue = h, this._maxValue = l;
        var f = i.prototype.adjustLimits.call(this, t, n),
          g = this.chart.axisX,
          y = this.chart.axisY;
        if (g._getLogBase() <= 0) {
          var m = (n.width - this._maxSize) / f.width;
          f.left -= .5 * this._maxSize / m, f.width += this._maxSize / m
        }
        if (y._getLogBase() <= 0) {
          var b = (n.height - this._maxSize) / f.height;
          f.top -= .5 * this._maxSize / b, f.height += this._maxSize / b
        }
        return f
      }, n.prototype._drawSymbol = function(i, n, r, s, a, o) {
        var h = this;
        if (null == this._minSize) {
          var l = this.getNumOption("minSize", "bubble");
          this._minSize = l || this._MIN_SIZE
        }
        if (null == this._maxSize) {
          var c = this.getNumOption("maxSize", "bubble");
          this._maxSize = c || this._MAX_SIZE
        }
        var u = a._getItem(o);
        if (u) {
          var _ = a._getBinding(1);
          if (_) {
            var s = u[_];
            if (e._DataInfo.isValid(s)) {
              null == s && (s = this._minValue);
              var p = this._minValue == this._maxValue ? 1 : Math.sqrt((s - this._minValue) / (this._maxValue - this._minValue));
              if (s = this._minSize + (this._maxSize - this._minSize) * p, this.chart.itemFormatter) {
                var d = new e.HitTestInfo(this.chart, new t.Point(n, r), e.ChartElement.SeriesSymbol);
                d._setData(a, o), i.startGroup(), this.chart.itemFormatter(i, d, function() {
                  h._drawDefaultSymbol(i, n, r, s, a.symbolMarker, a.symbolStyle)
                }), i.endGroup()
              } else this._drawDefaultSymbol(i, n, r, s, a.symbolMarker, a.symbolStyle);
              var f = this.hitTester._map[this.chart.series.indexOf(a)];
              if (null != f)
                for (var g = f.length - 1; g >= 0; g--) {
                  var y = f[g];
                  if (y.tag && y.tag.pointIndex == o) {
                    var m = t.tryCast(y, e._CircleArea);
                    m && m.setRadius(.5 * s)
                  }
                }
            }
          }
        }
      }, n
    }(e._LinePlotter);
    e._BubblePlotter = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n() {
        var t = null !== i && i.apply(this, arguments) || this;
        return t.isCandle = !0, t.isArms = !1, t.isEqui = !1, t.isVolume = !1, t._volHelper = null, t._symWidth = .7, t
      }
      return __extends(n, i), n.prototype.clear = function() {
        i.prototype.clear.call(this), this._volHelper = null
      }, n.prototype.load = function() {
        if (i.prototype.load.call(this), this.isVolume) {
          var n, r, s, a, o, h, l, c, u = null,
            _ = null;
          for (h = 0; h < this.chart.series.length; h++) {
            if (n = this.chart.series[h], o = n.getDataType(1) || n.chart._xDataType, r = n._getAxisX(), s = n._getChartType(), s = null === s || t.isUndefined(s) ? this.chart._getChartType() : s, a = s === e.ChartType.Column ? n._getBindingValues(1) : s === e.ChartType.Candlestick ? n._getBindingValues(4) : null, o === t.DataType.Date) {
              var p;
              for (l = [], c = [], h = 0; h < n._getLength(); h++) p = n._getItem(h)[n.bindingX].valueOf(), l.push(p), c.push({
                value: p,
                text: t.Globalize.format(new Date(p), r.format || "d")
              })
            } else l = this.dataInfo.getXVals();
            if (u = this.dataInfo.getMinX(), _ = this.dataInfo.getMaxX(), a && a.length > 0) {
              this._volHelper = new e._VolumeHelper(a, l, u, _, o), r._customConvert = this._volHelper.convert.bind(this._volHelper), r._customConvertBack = this._volHelper.convertBack.bind(this._volHelper), c && c.length > 0 && (this._itemsSource = r.itemsSource = c);
              break
            }
          }
        }
      }, n.prototype.unload = function() {
        i.prototype.unload.call(this);
        for (var t, e = 0; e < this.chart.series.length; e++)(t = this.chart.series[e]._getAxisX()) && (t._customConvert = null, t._customConvertBack = null, t.itemsSource && t.itemsSource == this._itemsSource && (this._itemsSource = t.itemsSource = null))
      }, n.prototype.parseSymbolWidth = function(e) {
        if (this._isPixel = void 0, e)
          if (t.isNumber(e)) {
            var i = t.asNumber(e);
            isFinite(i) && i > 0 && (this._symWidth = i, this._isPixel = !0)
          } else if (t.isString(e)) {
            var n = t.asString(e);
            if (n && n.indexOf("%") >= 0) {
              n = n.replace("%", "");
              r = parseFloat(n);
              isFinite(r) && (r < 0 ? r = 0 : r > 100 && (r = 100), this._symWidth = r / 100, this._isPixel = !1)
            } else {
              var r = parseFloat(e);
              isFinite(r) && r > 0 && (this._symWidth = r, this._isPixel = !0)
            }
          }
      }, n.prototype.adjustLimits = function(i, n) {
        this.dataInfo = i;
        var r = i.getMinX(),
          s = i.getMinY(),
          a = i.getMaxX(),
          o = i.getMaxY(),
          h = i.getDeltaX(),
          l = this.chart._xDataType;
        h <= 0 && (h = 1);
        var c = this.chart.series,
          u = c.length,
          _ = 0;
        this.parseSymbolWidth(this.symbolWidth), !this.isVolume || this.chart._getChartType() !== e.ChartType.Column && this.chart._getChartType() !== e.ChartType.Candlestick ? this.unload() : this.load();
        for (var p = 0; p < u; p++) {
          var d = c[p];
          if (!d._isCustomAxisY()) {
            var f = d._getBinding(1),
              g = d._getBinding(2),
              y = d._getBinding(3),
              m = d._getLength();
            if (m) {
              var b = d._getSymbolSize();
              b > _ && (_ = b);
              for (var v = 0; v < m; v++) {
                var x = d._getItem(v);
                x && [f ? x[f] : null, g ? x[g] : null, y ? x[y] : null].forEach(function(t) {
                  e._DataInfo.isValid(t) && null !== t && ((isNaN(s) || t < s) && (s = t), (isNaN(o) || t > o) && (o = t))
                })
              }
            }
          }
        }
        var w = a - r,
          S = this.chart._plotRect;
        if (S && S.width && !this.isVolume) {
          isNaN(b) && (b = d._getSymbolSize()), b += 2;
          var P = S.width / (S.width - b) * w;
          r -= .5 * (P - w), w = P
        }
        return !(l === t.DataType.Date && this.isVolume || l === t.DataType.Number) || this.chart._getChartType() !== e.ChartType.Column && this.chart._getChartType() !== e.ChartType.Candlestick ? this.chart._isRotated() ? new t.Rect(s, r, o - s, w) : new t.Rect(r, s, w, o - s) : new t.Rect(r - .5 * h, s, a - r + h, o - s)
      }, n.prototype.plotSeries = function(i, n, r, s, a, o, h, l) {
        var c = this,
          u = t.asType(s, e.SeriesBase),
          _ = this.chart.series.indexOf(s),
          p = s.getValues(0),
          d = s.getValues(1),
          f = this._symWidth,
          g = this.chart._isRotated();
        if (p) {
          if (d || (d = this.dataInfo.getXVals()), d) {
            var y = this.dataInfo.getDeltaX();
            y > 0 && !1 === this._isPixel && (f *= y)
          }
          var m = p.length,
            b = !0;
          d ? m = Math.min(m, d.length) : (b = !1, d = new Array(m));
          var v = this._DEFAULT_WIDTH,
            x = u._getSymbolFill(_),
            w = u._getAltSymbolFill(_) || "transparent",
            S = u._getSymbolStroke(_),
            P = u._getAltSymbolStroke(_) || S,
            C = void 0 === this._isPixel ? u._getSymbolSize() : f;
          i.stroke = S, i.strokeWidth = v, i.fill = x;
          for (var T, M, A = u._getBinding(1), N = u._getBinding(2), L = u._getBinding(3), I = g ? r.actualMin : n.actualMin, E = g ? r.actualMax : n.actualMax, k = 0, D = null, O = null, R = 0; R < m; R++)
            if (D = u._getItem(R)) {
              var j = b ? d[R] : R;
              if (e._DataInfo.isValid(j) && I <= j && j <= E) {
                var F = p[R],
                  V = A ? D[A] : null,
                  X = N ? D[N] : null,
                  B = L ? D[L] : null;
                if (i.startGroup(), this.isEqui && null !== O ? O[L] !== D[L] && (T = O[L] < D[L] ? w : x, M = O[L] < D[L] ? P : S) : (T = X < B ? w : x, M = X < B ? P : S), i.fill = T, i.stroke = M, this.chart.itemFormatter) {
                  var Y = new e.HitTestInfo(this.chart, new t.Point(n.convert(j), r.convert(F)), e.ChartElement.SeriesSymbol);
                  Y._setData(u, R), this.chart.itemFormatter(i, Y, function() {
                    c._drawSymbol(i, n, r, _, R, T, C, j, F, V, X, B)
                  })
                } else this._drawSymbol(i, n, r, _, R, T, C, j, F, V, X, B);
                i.endGroup(), s._setPointIndex(R, k), k++
              }
              O = D
            }
        }
      }, n.prototype._drawSymbol = function(i, n, r, s, a, o, h, l, c, u, _, p) {
        var d, f = new e._DataPoint(s, a, l, c),
          g = null,
          y = null,
          m = null,
          b = null,
          v = this.chart._isRotated();
        if (v) {
          var x = r;
          r = n, n = x
        }
        if (!1 === this._isPixel && (m = n.convert(l - .5 * h), b = n.convert(l + .5 * h), m > b)) {
          var w = m;
          m = b, b = w
        }
        l = n.convert(l), !1 !== this._isPixel && (m = l - .5 * h, b = l + .5 * h), this.isCandle ? (e._DataInfo.isValid(_) && e._DataInfo.isValid(p) && (_ = r.convert(_), p = r.convert(p), y = (g = Math.min(_, p)) + Math.abs(_ - p), v ? (i.drawRect(g, m, y - g || 1, b - m || 1), d = new e._RectArea(new t.Rect(g, m, y - g || 1, b - m || 1))) : (i.drawRect(m, g, b - m || 1, y - g || 1), d = new e._RectArea(new t.Rect(m, g, b - m || 1, y - g || 1))), d.tag = f, this.hitTester.add(d, s)), e._DataInfo.isValid(c) && (c = r.convert(c), null !== g && (v ? (i.drawLine(y, l, c, l), d.rect.width = d.rect.width + c) : (i.drawLine(l, g, l, c), d.rect.top = c, d.rect.height = d.rect.height + c))), e._DataInfo.isValid(u) && (u = r.convert(u), null !== y && (v ? (i.drawLine(g, l, u, l), d.rect.left = u, d.rect.width = d.rect.width + u) : (i.drawLine(l, y, l, u), d.rect.height = d.rect.height + u)))) : this.isEqui ? e._DataInfo.isValid(c) && e._DataInfo.isValid(u) && (c = r.convert(c), u = r.convert(u), y = (g = Math.min(c, u)) + Math.abs(c - u), i.drawRect(m, g, b - m || 1, y - g || 1), (d = new e._RectArea(new t.Rect(m, g, b - m || 1, y - g || 1))).tag = f, this.hitTester.add(d, s)) : this.isArms ? (e._DataInfo.isValid(_) && e._DataInfo.isValid(p) && (_ = r.convert(_), p = r.convert(p), y = (g = Math.min(_, p)) + Math.abs(_ - p), i.drawRect(m, g, b - m || 1, y - g || 1)), e._DataInfo.isValid(c) && null !== g && (c = r.convert(c), i.drawLine(l, g, l, c)), e._DataInfo.isValid(u) && null !== y && (u = r.convert(u), i.drawLine(l, y, l, u)), e._DataInfo.isValid(c) && e._DataInfo.isValid(u) && (i.fill = "transparent", y = (g = Math.min(c, u)) + Math.abs(c - u), i.drawRect(m, g, b - m || 1, y - g || 1), (d = new e._RectArea(new t.Rect(m, g, b - m || 1, y - g || 1))).tag = f, this.hitTester.add(d, s))) : (e._DataInfo.isValid(c) && e._DataInfo.isValid(u) && (c = r.convert(c), u = r.convert(u), y = (g = Math.min(c, u)) + Math.abs(c - u), v ? (i.drawLine(u, l, c, l), d = new e._RectArea(new t.Rect(g, m, y - g || 1, b - m || 1))) : (i.drawLine(l, u, l, c), d = new e._RectArea(new t.Rect(m, g, b - m || 1, y - g || 1))), d.tag = f, this.hitTester.add(d, s)), e._DataInfo.isValid(_) && (_ = r.convert(_), v ? i.drawLine(_, m, _, l) : i.drawLine(m, _, l, _)), e._DataInfo.isValid(p) && (p = r.convert(p), v ? i.drawLine(p, l, p, b) : i.drawLine(l, p, b, p)))
      }, n
    }(e._BasePlotter);
    e._FinancePlotter = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function r() {
        var t = null !== i && i.apply(this, arguments) || this;
        return t.stacking = e.Stacking.None, t
      }
      return __extends(r, i), r.prototype.adjustLimits = function(e, i) {
        this.dataInfo = e;
        var n = e.getMinX(),
          r = e.getMinY(),
          s = e.getMaxX(),
          a = e.getMaxY();
        return new t.Rect(n, r, s - n, a - r)
      }, r.prototype.plotSeries = function(i, r, s, a, o, h, l, c) {
        var u = this.chart.series.indexOf(a);
        if (!(u > 0)) {
          var _, p, d, f, g, y = t.asType(a, e.SeriesBase),
            m = this.chart.options,
            b = a.getValues(0),
            v = a.getValues(1),
            x = this.chart._plotRect,
            w = m && m.funnel && null != m.funnel.neckWidth ? m.funnel.neckWidth : .2,
            S = m && m.funnel && null != m.funnel.neckHeight ? m.funnel.neckHeight : 0,
            P = w * x.width,
            C = 0,
            T = 0,
            M = 0,
            A = 0,
            N = x.left,
            L = x.top,
            I = x.width,
            E = x.height;
          if (b) {
            this.rotated && (L += x.height), P = P || 1, v || (v = this.dataInfo.getXVals());
            var k = b.length;
            for (null != v && (k = Math.min(k, v.length)), C = 0; C < k; C++) T += b[C];
            var D, O, R = 0;
            if (m && m.funnel && "rectangle" === m.funnel.type) {
              S = E / k, w = I;
              var j;
              for (C = 0; C < k; C++) {
                var F = v ? v[C] : C,
                  V = b[C],
                  X = y._getSymbolFill(C),
                  B = y._getAltSymbolFill(C) || X,
                  Y = y._getSymbolStroke(C),
                  z = y._getAltSymbolStroke(C) || Y;
                if (this._getSymbolStyles && (X = (q = this._getSymbolStyles(C, k)) && q.fill ? q.fill : X, B = q && q.fill ? q.fill : B, Y = q && q.stroke ? q.stroke : Y, z = q && q.stroke ? q.stroke : z), D = V > 0 ? X : B, O = V > 0 ? Y : z, i.fill = D, i.stroke = O, e._DataInfo.isValid(F) && e._DataInfo.isValid(V)) {
                  j || (j = I / V);
                  var H = j * V;
                  N += (w - H) / 2, this.rotated && (L -= S), i.drawRect(N, L, H, S), G = new n(new t.Point(N, L), H, S, H, S), this.rotated || (L += S), w = H, G.tag = new e._DataPoint(u, C, F, V), this.hitTester.add(G, u), a._setPointIndex(C, R), R++
                }
              }
            } else
              for (M = x.left + x.width * (1 - w) / 2, A = x.top + x.height * (this.rotated ? S : 1 - S), p = (1 - w) * x.width / 2 / (x.height * (1 - S)), !isNaN(p) && isFinite(p) || (I = P, N = M, L = A), _ = x.width * w * x.height + x.width * (1 - w) / 2 * x.height * (1 - S), C = 0; C < k; C++) {
                var G, F = v ? v[C] : C,
                  V = b[C],
                  W = [],
                  U = [],
                  X = y._getSymbolFill(C),
                  B = y._getAltSymbolFill(C) || X,
                  Y = y._getSymbolStroke(C),
                  z = y._getAltSymbolStroke(C) || Y;
                if (this._getSymbolStyles) {
                  var q = this._getSymbolStyles(C, k);
                  X = q && q.fill ? q.fill : X, B = q && q.fill ? q.fill : B, Y = q && q.stroke ? q.stroke : Y, z = q && q.stroke ? q.stroke : z
                }
                if (D = V > 0 ? X : B, O = V > 0 ? Y : z, i.fill = D, i.stroke = O, e._DataInfo.isValid(F) && e._DataInfo.isValid(V)) {
                  var K = _ * V / T;
                  I > P ? (f = this._getTrapezoidOffsetY(I, K, p), this.rotated ? L - f > A ? (W = [N, N + (d = p * f), N + I - d, N + I], U = [L, L - f, L - f, L], G = new n(new t.Point(N, L - f), I, f, I - 2 * d, 0, !0), I -= 2 * d, N += d, L -= f) : (d = p * (f = L - A), g = (K -= this._getTrapezoidArea(I, p, f)) / P, W.push(N, N + d, N + d, N + d + P, N + d + P, N + I), U.push(L, L - f, L - f - g, L - f - g, L - f, L), G = new n(new t.Point(N, L - f - g), I, f + g, P, g, !0), I = P, N += d, L = L - f - g) : L + f < A ? (W = [N, N + (d = p * f), N + I - d, N + I], U = [L, L + f, L + f, L], G = new n(new t.Point(N, L), I, f, I - 2 * d, 0), I -= 2 * d, N += d, L += f) : (d = p * (f = A - L), g = (K -= this._getTrapezoidArea(I, p, f)) / P, W.push(N, N + d, N + d, N + d + P, N + d + P, N + I), U.push(L, L + f, L + f + g, L + f + g, L + f, L), G = new n(new t.Point(N, L), I, f + g, P, g), I = P, N += d, L = L + f + g), i.drawPolygon(W, U)) : (g = K / P, this.rotated && (L -= g), i.drawRect(N, L, I, g), G = new n(new t.Point(N, L), P, g, P, g), this.rotated || (L += g)), G.tag = new e._DataPoint(u, C, F, V), this.hitTester.add(G, u), a._setPointIndex(C, R), R++
                }
              }
          }
        }
      }, r.prototype._getTrapezoidArea = function(t, e, i) {
        var n = i * e;
        return n * i + (t - 2 * n) * i
      }, r.prototype._getTrapezoidOffsetY = function(t, e, i) {
        var n = Math.pow(t / 2 / i, 2) - e / i;
        return t / 2 / i - Math.sqrt(n >= 0 ? n : 0)
      }, r.prototype.drawSymbol = function(t, i, n, r, s) {
        var a = this;
        if (this.chart.itemFormatter) {
          t.startGroup();
          var o = new e.HitTestInfo(this.chart, s, e.ChartElement.SeriesSymbol);
          o._setData(n, r), this.chart.itemFormatter(t, o, function() {
            a.drawDefaultSymbol(t, i, n)
          }), t.endGroup()
        } else this.drawDefaultSymbol(t, i, n)
      }, r.prototype.drawDefaultSymbol = function(t, e, i) {
        t.drawRect(e.left, e.top, e.width, e.height, null, i.symbolStyle)
      }, r.prototype._getPointAndPosition = function(t, i, n, r) {
        var s = n;
        t.x = s.center.x, t.y = s.center.y, i = null == i ? e.LabelPosition.Center : i
      }, r
    }(e._BasePlotter);
    e._FunnelPlotter = i;
    var n = function() {
      function e(e, i, n, r, s, a) {
        void 0 === a && (a = !1), this._startPoint = e, this._width = i, this._height = n, this._neckWidth = r, this._neckHeight = s, this._center = new t.Point(this._startPoint.x + i / 2, this._startPoint.y + n / 2), this._offsetX = (i - r) / 2, this._offsetY = n - s, this._rotated = a
      }
      return e.prototype.contains = function(t) {
        var e = this._startPoint,
          i = this._offsetX,
          n = this._offsetY;
        if (this._rotated) {
          if (t.x >= e.x && t.x <= e.x + this._width && t.y >= e.y && t.y <= e.y + this._height) {
            if (t.x >= e.x + i && t.x <= e.x + this._width - i) return !0;
            if (t.y < e.y + this._neckHeight) return !1;
            if (t.x < this._center.x) return (e.y + this._height - t.y) / (t.x - e.x) < n / i;
            if (t.x > this._center.x) return (e.y + this._height - t.y) / (e.x + this._width - t.x) < n / i
          }
        } else if (t.x >= e.x && t.x <= e.x + this._width && t.y >= e.y && t.y <= e.y + this._height) {
          if (t.x >= e.x + i && t.x <= e.x + this._width - i) return !0;
          if (t.y > e.y + n) return !1;
          if (t.x < this._center.x) return (t.y - e.y) / (t.x - e.x) < n / i;
          if (t.x > this._center.x) return (t.y - e.y) / (e.x + this._width - t.x) < n / i
        }
        return !1
      }, e.prototype.distance = function(t) {
        if (this.contains(t)) return 0;
        var e = this._startPoint,
          i = this._width,
          n = this._height,
          r = this._offsetX,
          s = this._offsetY;
        if (this._rotated) {
          if (t.y > e.y + n) return t.x < e.x ? Math.sqrt(Math.pow(e.x - t.x, 2) + Math.pow(e.y + n - t.y, 2)) : t.x > e.x + i ? Math.sqrt(Math.pow(t.x - e.x - i, 2) + Math.pow(e.y + n - t.y, 2)) : t.y - e.y - n;
          if (t.y < e.y) return t.x < e.x + r ? Math.sqrt(Math.pow(e.x + r - t.x, 2) + Math.pow(t.y - e.y - n, 2)) : t.x > e.x + i - r ? Math.sqrt(Math.pow(t.x - e.x - i + r, 2) + Math.pow(t.y - e.y - n, 2)) : e.y - t.y;
          if (!(t.y < e.y + n - s)) return t.x < e.x + r ? Math.min(Math.sqrt(Math.pow(e.x - t.x, 2) + Math.pow(t.y - e.y - n, 2)), Math.sqrt(Math.pow(t.x - r / 2 - e.x, 2) + Math.pow(t.y - n + s / 2 - e.y, 2)), Math.sqrt(Math.pow(t.x - r - e.x, 2) + Math.pow(t.y - n + s - e.y, 2))) : Math.min(Math.sqrt(Math.pow(t.x - i - e.x, 2) + Math.pow(t.y - n - e.y, 2)), Math.sqrt(Math.pow(t.x - i + r / 2 - e.x, 2) + Math.pow(t.y - n + s / 2 - e.y, 2)), Math.sqrt(Math.pow(t.x - i + r - e.x, 2) + Math.pow(t.y - n + s - e.y, 2)));
          if (t.x < e.x + r) return e.x + r - t.x;
          if (t.x > e.x + i - r) return t.x - e.x - i + r
        } else {
          if (t.y < e.y) return t.x < e.x ? Math.sqrt(Math.pow(e.x - t.x, 2) + Math.pow(e.y - t.y, 2)) : t.x > e.x + i ? Math.sqrt(Math.pow(t.x - e.x - i, 2) + Math.pow(e.y - t.y, 2)) : e.y - t.y;
          if (t.y > e.y + n) return t.x < e.x + r ? Math.sqrt(Math.pow(e.x + r - t.x, 2) + Math.pow(t.y - e.y - n, 2)) : t.x > e.x + i - r ? Math.sqrt(Math.pow(t.x - e.x - i + r, 2) + Math.pow(t.y - e.y - n, 2)) : t.y - e.y - n;
          if (!(t.y > e.y + s)) return t.x < e.x + r ? Math.min(Math.sqrt(Math.pow(e.x - t.x, 2) + Math.pow(t.y - e.y, 2)), Math.sqrt(Math.pow(t.x - r / 2 - e.x, 2) + Math.pow(t.y - s / 2 - e.y, 2)), Math.sqrt(Math.pow(t.x - r - e.x, 2) + Math.pow(t.y - s - e.y, 2))) : Math.min(Math.sqrt(Math.pow(t.x - i - e.x, 2) + Math.pow(t.y - e.y, 2)), Math.sqrt(Math.pow(t.x - i + r / 2 - e.x, 2) + Math.pow(t.y - s / 2 - e.y, 2)), Math.sqrt(Math.pow(t.x - i + r - e.x, 2) + Math.pow(t.y - s - e.y, 2)));
          if (t.x < e.x + r) return e.x + r - t.x;
          if (t.x > e.x + i - r) return t.x - e.x - i + r
        }
      }, Object.defineProperty(e.prototype, "center", {
        get: function() {
          return this._center
        },
        enumerable: !0,
        configurable: !0
      }), e
    }();
    e._FunnelSegment = n
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function e(e, i, n, r, s) {
        this._volumes = t.asArray(e), this._xVals = t.asArray(i), this._xDataMin = t.asNumber(n, !0, !1), this._xDataMax = t.asNumber(r, !0, !1), this._xDataType = t.asEnum(s, t.DataType, !0), this._calcData = [], this._init()
      }
      return e.prototype.convert = function(i, n, r) {
        var s = void 0,
          a = this._calcData.length,
          o = -1;
        if (this._hasXs && this._xDataType === t.DataType.Date) {
          if (-1 === (o = this._xVals.indexOf(i)))
            for (var h = 0; h < this._xVals.length; h++) {
              if (h < this._xVals.length - 1 && this._xVals[h] <= i && i <= this._xVals[h + 1]) {
                o = h;
                break
              }
              if (0 === h && i <= this._xVals[h]) {
                o = h;
                break
              }
              if (h === this._xVals.length - 1 && this._xVals[h] <= i) {
                o = h;
                break
              }
            } - 1 === o && (o = this._xVals.indexOf(Math.floor(i)), o = t.clamp(o, 0, a - 1))
        } else this._hasXs ? -1 === (o = this._xVals.indexOf(i)) && (o = this._xVals.indexOf(Math.floor(i)), o = t.clamp(o, 0, a - 1)) : o = t.clamp(Math.round(i), 0, a - 1);
        return 0 <= o && o < a && (this._hasXs && (i = e.convertToRange(i, 0, a - 1, this._xDataMin, this._xDataMax)), s = ((s = this._calcData[o].value + (i - o) * this._calcData[o].width - .5 * this._calcData[o].width) - (n = this._getXVolume(n))) / ((r = this._getXVolume(r)) - n)), s
      }, e.prototype.convertBack = function(t, i, n) {
        var r, s = void 0,
          a = this._calcData.length,
          o = -1;
        for (r = 0; r < a; r++)
          if (this._calcData[r].x1 <= t && t <= this._calcData[r].x2 || 0 === r && t <= this._calcData[r].x2 || r === a - 1 && this._calcData[r].x1 <= t) {
            o = r;
            break
          }
        return 0 <= o && o < a && (s = t / this._calcData[o].width - this._calcData[o].value / this._calcData[o].width + .5 + r, this._hasXs && (s = e.convertToRange(s, this._xDataMin, this._xDataMax, 0, a - 1))), s
      }, e.prototype._init = function() {
        this._hasXs = null !== this._xVals && this._xVals.length > 0, this._hasXs && !t.isNumber(this._xDataMin) && (this._xDataMin = Math.min.apply(null, this._xVals)), this._hasXs && !t.isNumber(this._xDataMax) && (this._xDataMax = Math.max.apply(null, this._xVals)), this._hasXs && (this._hasXs = t.isNumber(this._xDataMin) && t.isNumber(this._xDataMax)), this._hasXs && this._xDataType === t.DataType.Date && this._fillGaps();
        var e = 0,
          i = 0,
          n = null !== this._volumes && this._volumes.length > 0 ? this._volumes.length : 0;
        for (i = 0; i < n; i++) e += this._volumes[i] || 0;
        var r, s, a = 0;
        for (i = 0; i < n; i++) r = a + (s = (this._volumes[i] || 0) / e), this._calcData.push({
          value: r,
          width: s,
          x1: a,
          x2: r
        }), a = this._calcData[i].value
      }, e.prototype._getXVolume = function(i) {
        var n = this._calcData.length,
          r = -1;
        if (this._hasXs) {
          r = this._xVals.indexOf(i);
          for (var s = 0; s < this._xVals.length; s++) {
            if (s < this._xVals.length - 1 && this._xVals[s] <= i && i <= this._xVals[s + 1]) {
              r = s;
              break
            }
            if (0 === s && i <= this._xVals[s]) {
              r = s;
              break
            }
            if (s === this._xVals.length - 1 && this._xVals[s] <= i) {
              r = s;
              break
            }
          }
        }
        return this._hasXs && (i = e.convertToRange(i, 0, n - 1, this._xDataMin, this._xDataMax)), -1 === r && (r = t.clamp(Math.round(i), 0, n - 1)), this._calcData[r].value + (i - r) * this._calcData[r].width - .5 * this._calcData[r].width
      }, e.convertToRange = function(t, e, i, n, r) {
        return e === i || n === r ? 0 : (t - n) * (i - e) / (r - n) + e
      }, e.prototype._fillGaps = function() {
        if (!(this._xDataType !== t.DataType.Date || null === this._xVals || this._xVals.length <= 0)) {
          var e, i = this._xDataMin,
            n = this._xDataMax;
          for (e = 1; i < n; e++)(i = new Date(i)).setDate(i.getDate() + 1), (i = i.valueOf()) !== this._xVals[e] && (this._xVals.splice(e, 0, i), this._volumes.splice(e, 0, 0))
        }
      }, e
    }();
    e._VolumeHelper = i
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
