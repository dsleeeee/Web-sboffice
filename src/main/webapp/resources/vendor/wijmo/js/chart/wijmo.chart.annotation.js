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
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function i() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (i.prototype = n.prototype, new i)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(n) {
      "use strict";
      var i;
      ! function(t) {
        t[t.DataIndex = 0] = "DataIndex", t[t.DataCoordinate = 1] = "DataCoordinate", t[t.Relative = 2] = "Relative", t[t.Absolute = 3] = "Absolute"
      }(i = n.AnnotationAttachment || (n.AnnotationAttachment = {}));
      var o;
      ! function(t) {
        t[t.Center = 0] = "Center", t[t.Top = 1] = "Top", t[t.Bottom = 2] = "Bottom", t[t.Left = 4] = "Left", t[t.Right = 8] = "Right"
      }(o = n.AnnotationPosition || (n.AnnotationPosition = {}));
      var r = function() {
        function n(t) {
          this._resetDefaultValue(), t && this._copy(this, t)
        }
        return Object.defineProperty(n.prototype, "attachment", {
          get: function() {
            return this._attachment
          },
          set: function(e) {
            (e = t.asEnum(e, i)) != this._attachment && (this._attachment = e, this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "point", {
          get: function() {
            return this._point
          },
          set: function(t) {
            null != t.x && null != t.y && (t.x === this._point.x && t.y === this._point.y || (this._point = t, this._repaint()))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "seriesIndex", {
          get: function() {
            return this._seriesIndex
          },
          set: function(e) {
            (e = t.asNumber(e, !1, !0)) != this._seriesIndex && (this._seriesIndex = e, this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "pointIndex", {
          get: function() {
            return this._pointIndex
          },
          set: function(e) {
            e !== this._pointIndex && (this._pointIndex = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "position", {
          get: function() {
            return this._position
          },
          set: function(t) {
            t != this._position && (this._position = t, this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "offset", {
          get: function() {
            return this._offset
          },
          set: function(t) {
            null != t.x && null != t.y && (t.x === this._offset.x && t.y === this._offset.y || (this._offset = t, this._repaint()))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "style", {
          get: function() {
            return null == this._style && (this._style = {}), this._style
          },
          set: function(t) {
            t != this._style && (this._style = t, this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "isVisible", {
          get: function() {
            return this._isVisible
          },
          set: function(e) {
            (e = t.asBoolean(e, !1)) != this._isVisible && (this._isVisible = e, this._toggleVisibility(e))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "tooltip", {
          get: function() {
            return this._tooltip
          },
          set: function(t) {
            this._tooltip = t
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "name", {
          get: function() {
            return this._name
          },
          set: function(t) {
            this._name = t
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.render = function(t) {
          var o, r = this;
          r._element = t.startGroup(r._getCSSClass()), t.fill = "#88bde6", t.strokeWidth = 1, t.stroke = "#000000", r._render(t), t.endGroup(), r._element[n._DATA_KEY] = this, r._isVisible ? r._attachment === i.DataIndex && (!(o = r._layer._chart.series[r._seriesIndex]) || o.visibility !== e.SeriesVisibility.Legend && o.visibility !== e.SeriesVisibility.Hidden || r._toggleVisibility(!1)) : r._toggleVisibility(!1)
        }, n.prototype.destroy = function() {}, n.prototype._copy = function(t, e) {
          for (var n in e) n in t && this._processOptions(n, t, e)
        }, n.prototype._processOptions = function(t, e, n) {
          e[t] = n[t]
        }, n.prototype._resetDefaultValue = function() {
          var n = this;
          n._attachment = i.Absolute, n._point = new e.DataPoint(0, 0), n._seriesIndex = 0, n._pointIndex = 0, n._position = o.Center, n._offset = new t.Point(0, 0), n._isVisible = !0, n._tooltip = ""
        }, n.prototype._toggleVisibility = function(t) {
          var e = t ? "visible" : "hidden";
          this._element && this._element.setAttribute("visibility", e)
        }, n.prototype._getCSSClass = function() {
          return n._CSS_ANNOTATION
        }, n.prototype._render = function(t) {
          this._element = null
        }, n.prototype._repaint = function() {
          this._layer && this._layer._renderAnnotation(this)
        }, n.prototype._convertPoint = function(e) {
          var n, o, r, s, a, _, l, h = this,
            p = h._attachment,
            u = new t.Point;
          switch (h._layer && h._layer._chart && (o = (n = h._layer._chart)._plotRect), p) {
            case i.DataIndex:
              if (!n.series || n.series.length <= h.seriesIndex) break;
              if (a = n.series[h.seriesIndex], !(_ = a._getItem(h.pointIndex))) break;
              r = a.axisX || n.axisX, s = a.axisY || n.axisY, "string" == typeof(l = _[a.bindingX] || _.x) && (l = h.pointIndex), u.x = h._convertDataToLen(o.width, r, l), u.y = h._convertDataToLen(o.height, s, _[a._getBinding(0)] || s.actualMin + .25, !0);
              break;
            case i.DataCoordinate:
              r = n.axisX, s = n.axisY, u.x = h._convertDataToLen(o.width, r, e.x), u.y = h._convertDataToLen(o.height, s, e.y, !0);
              break;
            case i.Relative:
              u.x = o.width * e.x, u.y = o.height * e.y;
              break;
            case i.Absolute:
            default:
              u.x = e.x, u.y = e.y
          }
          return u
        }, n.prototype._convertDataToLen = function(t, e, n, i) {
          void 0 === i && (i = !1);
          var o = null == e.min ? e.actualMin : e.min,
            r = null == e.max ? e.actualMax : e.max;
          if (e._getLogBase()) {
            if (n <= 0) return NaN;
            var s = Math.log(r / o);
            return i ? t * (1 - Math.log(n / o) / s) : t * Math.log(n / o) / s
          }
          return i ? t * (1 - (n - o) / (r - o)) : t * (n - o) / (r - o)
        }, n.prototype._renderCenteredText = function(t, e, n, i, o, r) {
          var s, a;
          this._isValidPoint(n) && (o ? e.drawStringRotated(t, n, n, o, i, r) : e.drawString(t, n, i, r), (s = this._element.querySelector("text")) && (a = s.getBBox(), s.setAttribute("x", (n.x - a.width / 2).toFixed(1)), s.setAttribute("y", (n.y + a.height / 6).toFixed(1))))
        }, n.prototype._adjustOffset = function(t, e) {
          t.x = t.x + e.x, t.y = t.y + e.y
        }, n.prototype._getOffset = function(e) {
          var n = this._getPositionOffset(e);
          return new t.Point(this._offset.x + n.x, this._offset.y + n.y)
        }, n.prototype._getPositionOffset = function(e) {
          var n = new t.Point(0, 0),
            i = this.position,
            r = this._getSize(e);
          return (i & o.Top) === o.Top ? n.y -= r.height / 2 : (i & o.Bottom) === o.Bottom && (n.y += r.height / 2), (i & o.Left) === o.Left ? n.x -= r.width / 2 : (i & o.Right) === o.Right && (n.x += r.width / 2), n
        }, n.prototype._getSize = function(e) {
          return new t.Size
        }, n.prototype._isValidPoint = function(t) {
          return isFinite(t.x) && isFinite(t.y)
        }, n.prototype._measureString = function(t, e, n) {
          var i, o = t;
          return o._textGroup && null == o._textGroup.parentNode ? (o._svg.appendChild(o._textGroup), i = o.measureString(e, n, null, this.style), o.endRender()) : i = o.measureString(e, n, null, this.style), i
        }, n._DATA_KEY = "wj-chart-annotation", n._CSS_ANNOTATION = "wjchart-annotation", n._CSS_ANNO_TEXT = "anno-text", n._CSS_ANNO_SHAPE = "anno-shape", n
      }();
      n.AnnotationBase = r;
      var s = function(e) {
        function n(t) {
          return e.call(this, t) || this
        }
        return __extends(n, e), n.prototype._resetDefaultValue = function() {
          e.prototype._resetDefaultValue.call(this), this._text = "", this.position = o.Top
        }, n.prototype._getCSSClass = function() {
          return e.prototype._getCSSClass.call(this) + " " + n._CSS_TEXT
        }, Object.defineProperty(n.prototype, "text", {
          get: function() {
            return this._text
          },
          set: function(t) {
            var e = this;
            t !== e._text && (e._text = t, e._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._render = function(t) {
          var e, n = this,
            i = n._convertPoint(n.point);
          e = n._getOffset(t), n._adjustOffset(i, e), n._renderCenteredText(n._text, t, i, r._CSS_ANNO_TEXT, null, n.style)
        }, n.prototype._getSize = function(e) {
          return e ? this._measureString(e, this._text, r._CSS_ANNO_TEXT) : new t.Size
        }, n._CSS_TEXT = "wjchart-anno-text", n
      }(r);
      n.Text = s;
      var a = function(t) {
        function e(e) {
          return t.call(this, e) || this
        }
        return __extends(e, t), e.prototype._resetDefaultValue = function() {
          t.prototype._resetDefaultValue.call(this), this._content = ""
        }, e.prototype._getCSSClass = function() {
          return t.prototype._getCSSClass.call(this) + " " + e._CSS_SHAPE
        }, Object.defineProperty(e.prototype, "content", {
          get: function() {
            return this._content
          },
          set: function(t) {
            var e = this;
            t !== e._content && (e._content = t, e._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), e.prototype._render = function(t) {
          var e = this;
          e._shapeContainer = t.startGroup(), t.stroke = "#000", e._renderShape(t), t.stroke = null, t.endGroup(), e._content && e._renderText(t)
        }, e.prototype._getContentCenter = function() {
          return this.point
        }, e.prototype._renderShape = function(t) {}, e.prototype._renderText = function(t) {
          var e, n, i = this;
          e = i._convertPoint(i._getContentCenter()), i._isValidPoint(e) && (n = i._getOffset(), i._adjustOffset(e, n), i._renderCenteredText(i._content, t, e, r._CSS_ANNO_TEXT))
        }, e._CSS_SHAPE = "wjchart-anno-shape", e
      }(r);
      n.Shape = a;
      var _ = function(e) {
        function n(t) {
          return e.call(this, t) || this
        }
        return __extends(n, e), Object.defineProperty(n.prototype, "width", {
          get: function() {
            return this._width
          },
          set: function(e) {
            e !== this._width && (this._width = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "height", {
          get: function() {
            return this._height
          },
          set: function(e) {
            e !== this._height && (this._height = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._resetDefaultValue = function() {
          e.prototype._resetDefaultValue.call(this), this._width = 100, this._height = 80
        }, n.prototype._getCSSClass = function() {
          return e.prototype._getCSSClass.call(this) + " " + n._CSS_ELLIPSE
        }, n.prototype._renderShape = function(t) {
          e.prototype._renderShape.call(this, t);
          var n = this,
            i = n._convertPoint(n.point),
            o = n._width,
            s = n._height,
            a = n._getOffset();
          n._adjustOffset(i, a), n._isValidPoint(i) && t.drawEllipse(i.x, i.y, o / 2, s / 2, r._CSS_ANNO_SHAPE, n.style)
        }, n.prototype._getSize = function() {
          return new t.Size(this.width, this.height)
        }, n._CSS_ELLIPSE = "wjchart-anno-ellipse", n
      }(a);
      n.Ellipse = _;
      var l = function(e) {
        function n(t) {
          return e.call(this, t) || this
        }
        return __extends(n, e), Object.defineProperty(n.prototype, "width", {
          get: function() {
            return this._width
          },
          set: function(e) {
            e !== this._width && (this._width = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "height", {
          get: function() {
            return this._height
          },
          set: function(e) {
            e !== this._height && (this._height = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._resetDefaultValue = function() {
          e.prototype._resetDefaultValue.call(this), this._width = 100, this._height = 80
        }, n.prototype._getCSSClass = function() {
          return e.prototype._getCSSClass.call(this) + " " + n._CSS_RECTANGLE
        }, n.prototype._renderShape = function(t) {
          e.prototype._renderShape.call(this, t);
          var n = this,
            i = n._convertPoint(n.point),
            o = n._width,
            s = n._height,
            a = n._getOffset();
          n._adjustOffset(i, a), n._isValidPoint(i) && t.drawRect(i.x - o / 2, i.y - s / 2, n._width, n._height, r._CSS_ANNO_SHAPE, n.style)
        }, n.prototype._getSize = function() {
          return new t.Size(this.width, this.height)
        }, n._CSS_RECTANGLE = "wjchart-anno-rectangle", n
      }(a);
      n.Rectangle = l;
      var h = function(n) {
        function i(t) {
          return n.call(this, t) || this
        }
        return __extends(i, n), Object.defineProperty(i.prototype, "start", {
          get: function() {
            return this._start
          },
          set: function(t) {
            var e = this;
            null != t.x && null != t.y && (t.x === e._start.x && t.y === e._start.y || (e._start = t, e._repaint()))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "end", {
          get: function() {
            return this._end
          },
          set: function(t) {
            var e = this;
            null != t.x && null != t.y && (t.x === e._end.x && t.y === e._end.y || (e._end = t, e._repaint()))
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype._resetDefaultValue = function() {
          n.prototype._resetDefaultValue.call(this), this._start = new e.DataPoint(0, 0), this._end = new e.DataPoint(0, 0), this.position = o.Top
        }, i.prototype._getCSSClass = function() {
          return n.prototype._getCSSClass.call(this) + " " + i._CSS_LINE
        }, i.prototype._getContentCenter = function() {
          var n = this.start,
            i = this.end;
          return t.isDate(n.x) && t.isDate(i.x) ? new e.DataPoint(new Date(n.x.getTime() + (i.x - n.x) / 2), (n.y + i.y) / 2) : new e.DataPoint((n.x + i.x) / 2, (n.y + i.y) / 2)
        }, i.prototype._renderShape = function(t) {
          n.prototype._renderShape.call(this, t);
          var e, i = this,
            o = i._convertPoint(i._start),
            s = i._convertPoint(i._end);
          i._cS = o, i._cE = s, e = i._getOffset(), i._adjustOffset(o, e), i._adjustOffset(s, e), i._isValidPoint(o) && i._isValidPoint(s) && t.drawLine(o.x, o.y, s.x, s.y, r._CSS_ANNO_SHAPE, i.style)
        }, i.prototype._getSize = function() {
          var e = this._cS,
            n = this._cE;
          return new t.Size(Math.abs(e.x - n.x), Math.abs(e.y - n.y))
        }, i.prototype._renderText = function(t) {
          var e, n, i, o = this,
            s = o._cS,
            a = o._cE;
          e = o._convertPoint(o._getContentCenter()), n = o._getOffset(), o._adjustOffset(e, n), o._isValidPoint(e) && (i = (i = 180 * Math.atan2(a.y - s.y, a.x - s.x) / Math.PI) < -90 ? i + 180 : i > 90 ? i - 180 : i, o._renderCenteredText(o.content, t, e, r._CSS_ANNO_TEXT, i))
        }, i.prototype._renderCenteredText = function(t, e, i, o, r, s) {
          if (null != r) {
            var a, _, l, h;
            l = this._measureString(e, t, o).height / 2, h = r * Math.PI / 180, a = l * Math.sin(h), _ = l * Math.cos(h), i.x = i.x + a, i.y = i.y - _
          }
          n.prototype._renderCenteredText.call(this, t, e, i, o, r, s)
        }, i._CSS_LINE = "wjchart-anno-line", i
      }(a);
      n.Line = h;
      var p = function(n) {
        function i(t) {
          return n.call(this, t) || this
        }
        return __extends(i, n), i.prototype._processOptions = function(e, i, o) {
          var r = this;
          if ("points" === e) {
            var s = o[e];
            t.isArray(s) && s.forEach(function(t) {
              r.points.push(t)
            })
          } else n.prototype._processOptions.call(this, e, i, o)
        }, Object.defineProperty(i.prototype, "points", {
          get: function() {
            return this._points
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype._resetDefaultValue = function() {
          var e = this;
          n.prototype._resetDefaultValue.call(this), e._points = new t.collections.ObservableArray, e._points.collectionChanged.addHandler(function() {
            e._element && e._repaint()
          })
        }, i.prototype._getCSSClass = function() {
          return n.prototype._getCSSClass.call(this) + " " + i._CSS_POLYGON
        }, i.prototype._getContentCenter = function() {
          var t, n = this.points,
            i = n.length,
            o = 0,
            r = 0;
          for (t = 0; t < i; t++) o += n[t].x, r += n[t].y;
          return new e.DataPoint(o / i, r / i)
        }, i.prototype._renderShape = function(t) {
          n.prototype._renderShape.call(this, t);
          var e, i, o = this,
            s = [],
            a = [],
            _ = o._points,
            l = _.length,
            h = o._getOffset();
          for (e = 0; e < l; e++) {
            if (i = o._convertPoint(_[e]), !o._isValidPoint(i)) return;
            o._adjustOffset(i, h), s.push(i.x), a.push(i.y)
          }
          t.drawPolygon(s, a, r._CSS_ANNO_SHAPE, o.style)
        }, i.prototype._getSize = function() {
          var e, n, i, o, r, s, a, _ = this,
            l = _._points.length;
          for (a = [].map.call(_._points, function(t) {
            return _._convertPoint(t)
          }), r = 0; r < l; r++) s = a[r], 0 !== r ? (s.x < e ? e = s.x : s.x > n && (n = s.x), s.y < i ? i = s.y : s.y > o && (o = s.y)) : (e = n = s.x, i = o = s.y);
          return new t.Size(n - e, o - i)
        }, i._CSS_POLYGON = "wjchart-anno-polygon", i
      }(a);
      n.Polygon = p;
      var u = function(e) {
        function n(t) {
          return e.call(this, t) || this
        }
        return __extends(n, e), Object.defineProperty(n.prototype, "radius", {
          get: function() {
            return this._radius
          },
          set: function(e) {
            e !== this._radius && (this._radius = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._resetDefaultValue = function() {
          e.prototype._resetDefaultValue.call(this), this._radius = 100
        }, n.prototype._getCSSClass = function() {
          return e.prototype._getCSSClass.call(this) + " " + n._CSS_CIRCLE
        }, n.prototype._renderShape = function(t) {
          e.prototype._renderShape.call(this, t);
          var n = this,
            i = n._convertPoint(n.point),
            o = n._getOffset();
          n._adjustOffset(i, o), n._isValidPoint(i) && t.drawPieSegment(i.x, i.y, n.radius, 0, 360, r._CSS_ANNO_SHAPE, n.style)
        }, n.prototype._getSize = function() {
          var e = 2 * this.radius;
          return new t.Size(e, e)
        }, n._CSS_CIRCLE = "wjchart-anno-circle", n
      }(a);
      n.Circle = u;
      var c = function(e) {
        function n(t) {
          return e.call(this, t) || this
        }
        return __extends(n, e), Object.defineProperty(n.prototype, "length", {
          get: function() {
            return this._length
          },
          set: function(e) {
            e !== this._length && (this._length = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._resetDefaultValue = function() {
          e.prototype._resetDefaultValue.call(this), this._length = 100
        }, n.prototype._getCSSClass = function() {
          return e.prototype._getCSSClass.call(this) + " " + n._CSS_SQUARE
        }, n.prototype._renderShape = function(t) {
          e.prototype._renderShape.call(this, t);
          var n = this,
            i = n._convertPoint(n.point),
            o = n.length,
            s = n._getOffset();
          n._adjustOffset(i, s), n._isValidPoint(i) && t.drawRect(i.x - o / 2, i.y - o / 2, o, o, r._CSS_ANNO_SHAPE, n.style)
        }, n.prototype._getSize = function() {
          return new t.Size(this.length, this.length)
        }, n._CSS_SQUARE = "wjchart-anno-square", n
      }(a);
      n.Square = c;
      var f = function(e) {
        function n(t) {
          return e.call(this, t) || this
        }
        return __extends(n, e), Object.defineProperty(n.prototype, "width", {
          get: function() {
            return this._width
          },
          set: function(e) {
            e !== this._width && (this._width = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "height", {
          get: function() {
            return this._height
          },
          set: function(e) {
            e !== this._height && (this._height = t.asNumber(e, !1, !0), this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "href", {
          get: function() {
            return this._href
          },
          set: function(t) {
            t !== this._href && (this._href = t, this._repaint())
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._resetDefaultValue = function() {
          e.prototype._resetDefaultValue.call(this), this._width = 100, this._height = 100, this._href = ""
        }, n.prototype._getCSSClass = function() {
          return e.prototype._getCSSClass.call(this) + " " + n._CSS_IMAGE
        }, n.prototype._renderShape = function(t) {
          e.prototype._renderShape.call(this, t);
          var n = this,
            i = n._convertPoint(n.point),
            o = n._href,
            r = n.width,
            s = n.height,
            a = n._getOffset();
          o.length > 0 && n._isValidPoint(i) && (n._adjustOffset(i, a), t.drawImage(o, i.x - r / 2, i.y - s / 2, r, s)), n._applyStyle(n._element, n.style)
        }, n.prototype._getSize = function() {
          return new t.Size(this.width, this.height)
        }, n.prototype._applyStyle = function(t, e) {
          if (e)
            for (var n in e) t.setAttribute(this._deCase(n), e[n])
        }, n.prototype._deCase = function(t) {
          return t.replace(/[A-Z]/g, function(t) {
            return "-" + t.toLowerCase()
          })
        }, n._CSS_IMAGE = "wjchart-anno-image", n
      }(a);
      n.Image = f
    }(e.annotation || (e.annotation = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    ! function(n) {
      "use strict";
      var i = function() {
        function i(e, n) {
          var i = this;
          i._init(e), i._renderGroup(), i._bindTooltip(), n && t.isArray(n) && n.forEach(function(e) {
            var n, o = e.type || "Circle";
            t.chart.annotation[o] && (n = new t.chart.annotation[o](e), i._items.push(n))
          })
        }
        return i.prototype._init = function(e) {
          var n = this;
          n._items = new t.collections.ObservableArray, n._items.collectionChanged.addHandler(n._itemsChanged, n), n._chart = e, n._forceTTShowing = !1, n._annoTTShowing = !1, n._engine = e._currentRenderEngine, e.rendered.addHandler(n._renderAnnotations, n), e.lostFocus.addHandler(n._lostFocus, n)
        }, i.prototype._lostFocus = function(t) {
          this._toggleTooltip(this._tooltip, t, this._chart.hostElement)
        }, Object.defineProperty(i.prototype, "items", {
          get: function() {
            return this._items
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype.getItem = function(t) {
          var e = this.getItems(t);
          return e.length > 0 ? e[0] : null
        }, i.prototype.getItems = function(t) {
          var e = [];
          if (0 === this._items.length || !t || "" === t) return e;
          for (var n = 0; n < this._items.length; n++) t === this._items[n].name && e.push(this._items[n]);
          return e
        }, i.prototype._bindTooltip = function() {
          var n, i = this,
            o = i._chart.hostElement,
            r = i._tooltip;
          r || (r = i._tooltip = new e.ChartTooltip, n = t.Tooltip.prototype.hide, t.Tooltip.prototype.hide = function() {
            i._forceTTShowing || n.call(r)
          }), o && (o.addEventListener("click", function(t) {
            i._toggleTooltip(r, t, o)
          }), document.addEventListener("mousemove", function(t) {
            i._showTooltip() && i._toggleTooltip(r, t, o)
          }))
        }, i.prototype._showTooltip = function() {
          return !this._chart.isTouching
        }, i.prototype._toggleTooltip = function(e, n, i) {
          var o = this,
            r = o._getAnnotation(n.target, i);
          if (r && r.tooltip) o._forceTTShowing = !0, o._annoTTShowing = !0, e.show(o._layerEle, r.tooltip, new t.Rect(n.clientX, n.clientY, 5, 5));
          else {
            if (!o._annoTTShowing) return;
            o._annoTTShowing = !1, o._forceTTShowing = !1, e.hide()
          }
        }, i.prototype._getAnnotation = function(t, e) {
          var i = this._getAnnotationElement(t, e);
          return null == i ? null : i[n.AnnotationBase._DATA_KEY]
        }, i.prototype._getAnnotationElement = function(e, i) {
          if (!e || !i) return null;
          var o = e.parentNode;
          return t.hasClass(e, n.AnnotationBase._CSS_ANNOTATION) ? e : null == o || o === document.body || o === document || o === i ? null : this._getAnnotationElement(o, i)
        }, i.prototype._itemsChanged = function(e, n) {
          var i = n.action,
            o = n.item;
          switch (i) {
            case t.collections.NotifyCollectionChangedAction.Add:
            case t.collections.NotifyCollectionChangedAction.Change:
              o._layer = this, this._renderAnnotation(o);
              break;
            case t.collections.NotifyCollectionChangedAction.Remove:
              this._destroyAnnotation(o);
              break;
            default:
              this._destroyAnnotations(), this._renderAnnotations()
          }
        }, i.prototype._renderAnnotations = function() {
          var t, e = this.items,
            n = e.length;
          for (this._renderGroup(), t = 0; t < n; t++) this._renderAnnotation(e[t])
        }, i.prototype._renderGroup = function() {
          var t = this,
            e = t._engine,
            n = t._chart._plotRect;
          n && (t._layerEle && null != t._layerEle.parentNode || (t._plotrectId = "plotRect" + (1e6 * Math.random()).toFixed(), e.addClipRect({
            left: 0,
            top: 0,
            width: n.width,
            height: n.height
          }, t._plotrectId), t._layerEle = e.startGroup(i._CSS_Layer, t._plotrectId), t._layerEle.setAttribute("transform", "translate(" + n.left + ", " + n.top + ")"), e.endGroup()))
        }, i.prototype._renderAnnotation = function(t) {
          this._layerEle && null != this._layerEle.parentNode && (t._element && t._element.parentNode == this._layerEle && this._layerEle.removeChild(t._element), t.render(this._engine), this._layerEle.appendChild(t._element))
        }, i.prototype._destroyAnnotations = function() {
          var t, e = this.items,
            n = e.length;
          for (t = 0; t < n; t++) this._destroyAnnotation(e[t]);
          this._layerEle.innerHTML = ""
        }, i.prototype._destroyAnnotation = function(t) {
          this._layerEle && this._layerEle.removeChild(t._element), t.destroy()
        }, i._CSS_Layer = "wj-chart-annotationlayer", i
      }();
      n.AnnotationLayer = i
    }(e.annotation || (e.annotation = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
