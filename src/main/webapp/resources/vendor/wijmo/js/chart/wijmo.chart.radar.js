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
        for (var a in e) e.hasOwnProperty(a) && (t[a] = e[a])
      };
    return function(e, a) {
      function r() {
        this.constructor = e
      }
      t(e, a), e.prototype = null === a ? Object.create(a) : (r.prototype = a.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(a) {
      "use strict";
      var r;
      ! function(t) {
        t[t.Column = 0] = "Column", t[t.Scatter = 1] = "Scatter", t[t.Line = 2] = "Line", t[t.LineSymbols = 3] = "LineSymbols", t[t.Area = 4] = "Area"
      }(r = a.RadarChartType || (a.RadarChartType = {}));
      var i = function(i) {
        function n(t, e) {
          var a = i.call(this, t, e) || this;
          return a._chartType = r.Line, a._startAngle = 0, a._totalAngle = 360, a._reversed = !1, a._areas = [], a
        }
        return __extends(n, i), Object.defineProperty(n.prototype, "_radarLinePlotter", {
          get: function() {
            return null == this.__radarLinePlotter && (this.__radarLinePlotter = new a._RadarLinePlotter, this._initPlotter(this.__radarLinePlotter)), this.__radarLinePlotter
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "_radarColumnPlotter", {
          get: function() {
            return null == this.__radarColumnPlotter && (this.__radarColumnPlotter = new a._RadarBarPlotter, this._initPlotter(this.__radarColumnPlotter)), this.__radarColumnPlotter
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._initAxes = function() {
          i.prototype._initAxes.call(this), this.axes.pop(), this.axes.pop(), this.axisX = new a.FlexRadarAxis(e.Position.Bottom), this.axisX.majorGrid = !0, this.axisY = new a.FlexRadarAxis(e.Position.Left), this.axisY.majorTickMarks = e.TickMark.Outside, this.axes.push(this.axisX), this.axes.push(this.axisY)
        }, n.prototype._layout = function(e, a, r) {
          i.prototype._layout.call(this, e, a, r);
          var n = this.axisX._height;
          this._plotRect.top += n / 2;
          var s = this._plotRect;
          this._radius = Math.min(s.width, s.height) / 2, this._center = new t.Point(s.left + s.width / 2, s.top + s.height / 2)
        }, Object.defineProperty(n.prototype, "chartType", {
          get: function() {
            return this._chartType
          },
          set: function(e) {
            (e = t.asEnum(e, r)) != this._chartType && (this._chartType = e, this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "startAngle", {
          get: function() {
            return this._startAngle
          },
          set: function(e) {
            e != this._startAngle && (this._startAngle = t.asNumber(e, !0), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "totalAngle", {
          get: function() {
            return this._totalAngle
          },
          set: function(e) {
            e != this._totalAngle && e >= 0 && (this._totalAngle = t.asNumber(e, !0), this._totalAngle <= 0 && (t.assert(!1, "totalAngle must be greater than 0."), this._totalAngle = 0), this._totalAngle > 360 && (t.assert(!1, "totalAngle must be less than or equal to 360."), this._totalAngle = 360), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "reversed", {
          get: function() {
            return this._reversed
          },
          set: function(e) {
            e != this._reversed && (this._reversed = t.asBoolean(e, !0), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "stacking", {
          get: function() {
            return this._stacking
          },
          set: function(a) {
            (a = t.asEnum(a, e.Stacking)) != this._stacking && (this._stacking = a, this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._getChartType = function() {
          var t = e.ChartType.Line;
          switch (this.chartType) {
            case r.Area:
              t = e.ChartType.Area;
              break;
            case r.Line:
              t = e.ChartType.Line;
              break;
            case r.Column:
              t = e.ChartType.Column;
              break;
            case r.LineSymbols:
              t = e.ChartType.LineSymbols;
              break;
            case r.Scatter:
              t = e.ChartType.Scatter
          }
          return t
        }, n.prototype._getPlotter = function(t) {
          var e = this.chartType,
            a = null;
          if (t) {
            var n = t.chartType;
            null != n && n != e && (e = n, !0)
          }
          switch (e) {
            case r.Line:
              this._radarLinePlotter.hasSymbols = !1, this._radarLinePlotter.hasLines = !0, this._radarLinePlotter.isArea = !1, a = this._radarLinePlotter;
              break;
            case r.LineSymbols:
              this._radarLinePlotter.hasSymbols = !0, this._radarLinePlotter.hasLines = !0, this._radarLinePlotter.isArea = !1, a = this._radarLinePlotter;
              break;
            case r.Area:
              this._radarLinePlotter.hasSymbols = !1, this._radarLinePlotter.hasLines = !0, this._radarLinePlotter.isArea = !0, a = this._radarLinePlotter;
              break;
            case r.Scatter:
              this._radarLinePlotter.hasSymbols = !0, this._radarLinePlotter.hasLines = !1, this._radarLinePlotter.isArea = !1, a = this._radarLinePlotter;
              break;
            case r.Column:
              this._radarColumnPlotter.isVolume = !1, this._radarColumnPlotter.width = .8, a = this._radarColumnPlotter;
              break;
            default:
              a = i.prototype._getPlotter.call(this, t)
          }
          return a
        }, n.prototype._convertPoint = function(e, a) {
          var r = new t.Point,
            i = this._center;
          return r.x = i.x + e * Math.sin(a), r.y = i.y - e * Math.cos(a), r
        }, n.prototype._createSeries = function() {
          return new a.FlexRadarSeries
        }, n.prototype._clearCachedValues = function() {
          i.prototype._clearCachedValues.call(this), this._isPolar = !1, this._areas = []
        }, n.prototype._performBind = function() {
          if (this._xDataType = null, this._xlabels.splice(0), this._xvals.splice(0), this._cv) {
            var e = this._cv.items;
            if (e) {
              for (var a = e.length, r = 0; r < a; r++) {
                var i = e[r];
                if (this.bindingX) {
                  var n = i[this.bindingX];
                  t.isNumber(n) ? (this._xvals.push(t.asNumber(n)), this._xDataType = t.DataType.Number) : t.isDate(n) && (this._xDataType = t.DataType.Date), this._xlabels.push(i[this.bindingX])
                }
              }
              this._xvals.length == a ? this._xlabels.splice(0) : this._xvals.splice(0)
            }
          }
          this._xDataType === t.DataType.Number && (this._isPolar = !0)
        }, n.prototype._prepareRender = function() {
          i.prototype._prepareRender.call(this), this._areas = []
        }, n
      }(e.FlexChartCore);
      a.FlexRadar = i
    }(e.radar || (e.radar = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var a in e) e.hasOwnProperty(a) && (t[a] = e[a])
      };
    return function(e, a) {
      function r() {
        this.constructor = e
      }
      t(e, a), e.prototype = null === a ? Object.create(a) : (r.prototype = a.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(a) {
      "use strict";
      var r = function(r) {
        function i() {
          return null !== r && r.apply(this, arguments) || this
        }
        return __extends(i, r), Object.defineProperty(i.prototype, "chartType", {
          get: function() {
            return this._finChartType
          },
          set: function(e) {
            (e = t.asEnum(e, a.RadarChartType, !0)) != this._finChartType && (this._finChartType = e, this._invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype._getChartType = function() {
          var t;
          switch (this.chartType) {
            case a.RadarChartType.Area:
              t = e.ChartType.Area;
              break;
            case a.RadarChartType.Line:
              t = e.ChartType.Line;
              break;
            case a.RadarChartType.Column:
              t = e.ChartType.Column;
              break;
            case a.RadarChartType.LineSymbols:
              t = e.ChartType.LineSymbols;
              break;
            case a.RadarChartType.Scatter:
              t = e.ChartType.Scatter
          }
          return t
        }, i
      }(e.SeriesBase);
      a.FlexRadarSeries = r
    }(e.radar || (e.radar = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var a in e) e.hasOwnProperty(a) && (t[a] = e[a])
      };
    return function(e, a) {
      function r() {
        this.constructor = e
      }
      t(e, a), e.prototype = null === a ? Object.create(a) : (r.prototype = a.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(a) {
      "use strict";
      var r = function(a) {
        function r() {
          var t = null !== a && a.apply(this, arguments) || this;
          return t._points = [], t._axisLabels = [], t
        }
        return __extends(r, a), r.prototype._render = function(t) {
          var r = this;
          if (this._hasVisibileSeries()) {
            a.prototype._render.call(this, t);
            var i = this._axisLabels;
            if (i.length) {
              var n = function() {
                var a = r.axisType == e.AxisType.X ? "wj-axis-x-labels " + e.FlexChart._CSS_AXIS_X : "wj-axis-y-labels " + e.FlexChart._CSS_AXIS_Y;
                t.startGroup(a), i.forEach(function(a) {
                  var i = a.labelAngle;
                  i > 0 ? e.FlexChart._renderRotatedText(t, a.text, a.pos, a.align, a.vAlign, a.pos, i, a.class) : i < 0 ? e.FlexChart._renderRotatedText(t, a.text, a.pos, a.align, a.vAlign, a.pos, i, a.class) : r._renderLabel(t, a.val, a.text, a.pos, a.align, a.vAlign, a.class)
                }), t.endGroup(), r._axisLabels = [], r._chart.rendered.removeHandler(n)
              };
              this._chart.rendered.addHandler(n, this)
            }
          }
        }, r.prototype._getHeight = function(t, r) {
          var i = a.prototype._getHeight.call(this, t, r);
          return this._axisType == e.AxisType.Y && (i = 4), this._height = 2 * i, 2 * i
        }, r.prototype._getActualRange = function() {
          return this._isTimeAxis && null != this.__actualMax && null != this.__actualMin ? this.__actualMax - this.__actualMin : a.prototype._getActualRange.call(this)
        }, r.prototype._updateActualLimits = function(e, r, i, n, s) {
          var o = this;
          void 0 === n && (n = null), void 0 === s && (s = null), a.prototype._updateActualLimits.call(this, e, r, i, n, s);
          var l, h = this._chart,
            _ = this._lbls,
            c = this.actualMin.valueOf ? this.actualMin.valueOf() : this.actualMin,
            u = this.actualMax.valueOf ? this.actualMax.valueOf() : this.actualMax;
          this._lbls && this === h.axisX && (h._angles = [], this._isTimeAxis && 0 === this._lbls.length && this._values.forEach(function(t) {
            _.push(o._formatValue(t))
          }), l = _.length, h.totalAngle < 360 && (l -= 1), _.forEach(function(t, e) {
            var a = c + e / l * (u - c),
              r = h.startAngle + e / l * h.totalAngle;
            isNaN(r) || isNaN(a) || h._angles.push({
              value: o.convert(a),
              angle: r
            }), o._isTimeAxis ? ((null == o.__actualMin || o.__actualMin > t) && (o.__actualMin = t), (null == o.__actualMax || o.__actualMax < t) && (o.__actualMax = t)) : null == o.__actualMin && null == o.__actualMax || (o.__actualMin = null, o.__actualMax = null)
          }), this._isTimeAxis && this._lbls.length > 0 && (this._updateAutoFormat(0), this._lbls = _.map(function(e) {
            var a = t.asDate(e).valueOf();
            return o._formatValue(a)
          })))
        }, r.prototype._updateActualLimitsByChartType = function(t, a, r) {
          var i = this._chart;
          if (i._getChartType() != e.ChartType.Column && 360 === i.totalAngle && this.axisType === e.AxisType.X)
            if (this._isTimeAxis) {
              var n = (i._xlabels.length || i._xvals.length) - 1;
              r += (r - a) / (n = n < 1 ? 1 : n)
            } else i._isPolar || (r += 1);
          return {
            min: a,
            max: r
          }
        }, r.prototype.convert = function(t, a, r) {
          var i = null == a ? this.actualMax : a,
            n = null == r ? this.actualMin : r,
            s = this._chart;
          if (!s) return NaN;
          if (i == n) return 0;
          if (this.axisType === e.AxisType.X) return s.reversed ? (s.startAngle - (t - n) / (i - n) * s.totalAngle) * Math.PI / 180 : (s.startAngle + (t - n) / (i - n) * s.totalAngle) * Math.PI / 180;
          if (this._getLogBase()) {
            if (t <= 0) return NaN;
            var o = Math.log(i / n);
            return Math.log(t / n) / o * s._radius
          }
          return (t - n) / (i - n) * s._radius
        }, r.prototype._renderLineAndTitle = function(t) {
          var a = this._chart,
            r = e.FlexChart._CSS_LINE,
            i = (a.startAngle - 90) * Math.PI / 180,
            n = a.totalAngle * Math.PI / 180,
            s = a._radius;
          this.axisType === e.AxisType.X && this.axisLine && (t.stroke = e.FlexChart._FG, a._isPolar ? (i = a.reversed ? i - n : i, t.drawPieSegment(a._center.x, a._center.y, s, i, n, r)) : this._renderPolygon(t, s, r))
        }, r.prototype._renderPolygon = function(t, e, a) {
          var r = this._chart,
            i = r._angles,
            n = i.length,
            s = r.axisX.minorGrid,
            o = [],
            l = [];
          if (i.forEach(function(t, a) {
            if (s && a > 0) {
              var n = r._convertPoint(e, t.value - (t.value - i[a - 1].value) / 2);
              o.push(n.x), l.push(n.y)
            }
            var h = r._convertPoint(e, t.value);
            o.push(h.x), l.push(h.y)
          }), r.totalAngle < 360) o.push(r._center.x), l.push(r._center.y);
          else if (s && n >= 2) {
            var h = r._convertPoint(e, i[n - 1].value - (i[n - 2].value - i[n - 1].value) / 2);
            o.push(h.x), l.push(h.y)
          }
          t.drawPolygon(o, l, a)
        }, r.prototype._renderMinors = function(t, a, r, i) {
          var n, s = this,
            o = this._chart,
            l = e.FlexChart._CSS_GRIDLINE_MINOR,
            h = this.minorGrid,
            _ = o._angles,
            c = _.length,
            u = o.axisX.minorGrid,
            p = e.FlexChart._FG,
            d = this._GRIDLINE_WIDTH,
            y = (o.startAngle, Math.PI, o.totalAngle, Math.PI, this._TICK_OVERLAP),
            f = this.minorTickMarks,
            g = !0;
          this._vals.minor = a, f == e.TickMark.Outside ? y = 1 : f == e.TickMark.Inside ? y = -1 : f == e.TickMark.Cross ? y = 0 : g = !1, this.axisType == e.AxisType.Y ? (t.stroke = p, t.strokeWidth = d, a.forEach(function(e) {
            var a = s.convert(e);
            if (h && s._renderYGridLine(t, o, a, l), g && (_.forEach(function(e, r) {
              if (u && r > 0) {
                n = e.value - (e.value - _[r - 1].value) / 2;
                var i = o._convertPoint(a, n);
                s._drawMinorTickLength(t, y, n, i)
              }
              n = e.value;
              var l = o._convertPoint(a, n);
              s._drawMinorTickLength(t, y, n, l)
            }), u && c >= 2)) {
              n = _[c - 1].value - (_[c - 2].value - _[c - 1].value) / 2;
              var r = o._convertPoint(a, n);
              s._drawMinorTickLength(t, y, n, r)
            }
          })) : (t.stroke = p, t.strokeWidth = d, a.forEach(function(e) {
            var a = s.convert(e);
            h && (s._renderXGridLine(t, o, a, l), g && s._drawMinorTickLength(t, y, a - Math.PI / 2, o._convertPoint(o._radius, a)))
          }))
        }, r.prototype._drawMinorTickLength = function(t, a, r, i) {
          var n = this._TICK_HEIGHT,
            s = e.FlexChart._CSS_TICK_MINOR,
            o = .5 * (a - 1) * n * Math.cos(r),
            l = .5 * (1 + a) * n * Math.cos(r),
            h = .5 * (a - 1) * n * Math.sin(r),
            _ = .5 * (1 + a) * n * Math.sin(r);
          t.drawLine(i.x + o, i.y + h, i.x + l, i.y + _, s)
        }, r.prototype._renderLabelsAndTicks = function(a, r, i, n, s, o, l, h, _) {
          this._points = [], s = this.labelAngle || 0;
          var c, u = this._chart,
            p = this.labelPadding || 2,
            d = e.FlexChart._CSS_LABEL,
            y = e.FlexChart._CSS_GRIDLINE,
            f = e.FlexChart._CSS_TICK,
            g = e.FlexChart._FG,
            v = this._TICK_WIDTH,
            x = this.majorGrid,
            m = e.FlexChart._FG,
            A = this._GRIDLINE_WIDTH,
            P = u.startAngle * Math.PI / 180,
            b = (u.totalAngle, Math.PI, 1);
          if (this.axisType == e.AxisType.Y) {
            x = i != this.actualMin && x && i != this.actualMax;
            var T = this.convert(i),
              L = u._convertPoint(T, P);
            if (x && (a.stroke = m, a.strokeWidth = A, this._renderYGridLine(a, u, T, y)), a.stroke = g, a.strokeWidth = v, l) {
              (c = (u.startAngle % 360 + 360) % 360) <= 90 && c >= 75 || c >= 270 && c <= 285 ? b = 2 : (c > 90 && c <= 105 || c < 270 && c >= 255) && (b = 0);
              w = new t.Point(L.x - p - Math.abs(h - _), L.y);
              this._axisLabels.push({
                val: i,
                text: n,
                pos: w,
                align: 2,
                vAlign: b,
                labelAngle: s,
                class: d
              })
            }
            o != e.TickMark.None && a.drawLine(L.x - _ * Math.cos(P), L.y - _ * Math.sin(P), L.x - h * Math.cos(P), L.y - h * Math.sin(P), f)
          } else {
            var M = this.convert(i);
            if (x && (a.stroke = m, a.strokeWidth = A, this._renderXGridLine(a, u, M, y)), a.stroke = g, a.strokeWidth = v, l) {
              var C, k, S, w = u._convertPoint(u._radius + p, M);
              C = u._angles && u._angles.length ? u._angles[r].angle : u.startAngle + (i - this.actualMin) * u.totalAngle / (this.actualMax - this.actualMin), C = (C %= 360) >= 0 ? C : C + 360, k = this._getXLabelVAlign(C), S = this._getXLabelAlign(C), u._isPolar && (n = this._formatValue(C)), s > 0 ? e.FlexChart._renderRotatedText(a, n, w, S, k, w, s, d) : s < 0 ? e.FlexChart._renderRotatedText(a, n, w, S, k, w, s, d) : this._renderLabel(a, i, n, w, S, k, d)
            }
            o != e.TickMark.None && this._renderXTick(a, u, M, f, h, _)
          }
          return !0
        }, r.prototype._renderXGridLine = function(t, e, a, r) {
          var i = e._center,
            n = e._convertPoint(e._radius, a);
          t.drawLine(i.x, i.y, n.x, n.y, r)
        }, r.prototype._renderXTick = function(t, e, a, r, i, n) {
          var s, o;
          e._center;
          s = e._convertPoint(e._radius + i, a), o = e._convertPoint(e._radius + n, a), t.drawLine(s.x, s.y, o.x, o.y, r)
        }, r.prototype._renderYGridLine = function(t, e, a, r) {
          e._angles;
          var i = e._center,
            n = e.startAngle * Math.PI / 180,
            s = e.totalAngle * Math.PI / 180;
          e._isPolar ? (n = (e.startAngle - 90) * Math.PI / 180, n = e.reversed ? n - s : n, t.drawPieSegment(i.x, i.y, a, n, s, r)) : this._renderPolygon(t, a, r)
        }, r.prototype._getXLabelVAlign = function(t) {
          var e = 1,
            a = this._chart,
            r = a.startAngle;
          return a.reversed && (t = (360 + r + (r % 360 - t % 360)) % 360), 0 === t ? e = 2 : 180 === t && (e = 0), e
        }, r.prototype._getXLabelAlign = function(t) {
          var e = 0,
            a = this._chart,
            r = a.startAngle;
          return a.reversed && (t = (360 + r + (r % 360 - t % 360)) % 360), t > 0 && t < 180 ? e = -1 : t > 180 && t < 360 && (e = 1), e + 1
        }, r.prototype._createTimeLabels = function(t, r, i, n) {
          var s = this;
          if (this._axisType == e.AxisType.Y) a.prototype._createTimeLabels.call(this, t, r, i, n);
          else {
            var o = this._values;
            this.format;
            if (!o || 0 === o.length) return;
            o.forEach(function(t) {
              i.push(t), n.push(s._formatValue(t))
            })
          }
        }, r.prototype._niceNumber = function(t, e, r) {
          var i = this._chart,
            n = this.actualMax - this.actualMin,
            s = i.totalAngle;
          return s > 360 && (s %= 360), i._isPolar ? s % 8 == 0 ? n / 8 : s % 6 == 0 ? n / 6 : s % 4 == 0 ? n / 4 : s % 3 == 0 ? n / 3 : s % 2 == 0 ? n / 2 : a.prototype._niceNumber.call(this, t, e, r) : a.prototype._niceNumber.call(this, t, e, r)
        }, r
      }(e.Axis);
      a.FlexRadarAxis = r
    }(e.radar || (e.radar = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var a in e) e.hasOwnProperty(a) && (t[a] = e[a])
      };
    return function(e, a) {
      function r() {
        this.constructor = e
      }
      t(e, a), e.prototype = null === a ? Object.create(a) : (r.prototype = a.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(a) {
      "use strict";
      var r = function(a) {
        function r() {
          var t = null !== a && a.apply(this, arguments) || this;
          return t.isArea = !1, t
        }
        return __extends(r, a), r.prototype._getLabelPoint = function(t, e) {
          var a = t._getAxisX(),
            r = t._getAxisY(),
            i = a.convert(e.dataX),
            n = r.convert(e.dataY);
          return this.chart._convertPoint(n, i)
        }, r.prototype.plotSeries = function(a, r, i, n, s, o, l) {
          var h = t.asType(n, e.SeriesBase),
            _ = this.chart,
            c = h._getChartType() || _._getChartType(),
            u = _.series.indexOf(n),
            p = n.getValues(0),
            d = n.getValues(1);
          if (p) {
            d || (d = this.dataInfo.getXVals());
            var y = e._BasePlotter.cloneStyle(n.style, ["fill"]),
              f = p.length,
              g = !0;
            d ? f = Math.min(f, d.length) : (g = !1, d = new Array(f));
            var v = this._DEFAULT_WIDTH,
              x = h._getSymbolFill(u),
              m = h._getAltSymbolFill(u) || x,
              A = h._getSymbolStroke(u),
              P = h._getAltSymbolStroke(u) || A,
              b = h._getSymbolSize();
            a.stroke = A, a.strokeWidth = v, a.fill = x;
            var T = new Array,
              L = new Array,
              M = i.actualMax,
              C = this.stacking != e.Stacking.None && !h._isCustomAxisY(),
              k = this.stacking == e.Stacking.Stacked100pc && !h._isCustomAxisY();
            void 0 !== h._getChartType() && (C = k = !1);
            for (var S = h.interpolateNulls, w = !1, I = 0; I < f; I++) {
              var N = g ? d[I] : I,
                O = null == p[I] ? 0 : p[I];
              if (e._DataInfo.isValid(N) && e._DataInfo.isValid(O)) {
                if (C)
                  if (k && (O /= this.dataInfo.getStackedAbsSum(N)), O >= 0) {
                    j = isNaN(this.stackPos[N]) ? 0 : this.stackPos[N];
                    O = this.stackPos[N] = j + O
                  } else {
                    var j = isNaN(this.stackNeg[N]) ? 0 : this.stackNeg[N];
                    O = this.stackNeg[N] = j + O
                  }
                O = Math.min(O, M);
                var R;
                R = new e._DataPoint(u, I, N, O);
                var X = r.convert(N),
                  F = i.convert(O),
                  D = this.chart._convertPoint(F, X);
                if (N = D.x, O = D.y, isNaN(N) || isNaN(O)) w = !0, !0 !== S && (T.push(void 0), L.push(void 0));
                else {
                  T.push(N), L.push(O);
                  var V = new e._CircleArea(new t.Point(N, O), .5 * b);
                  V.tag = R, this.hitTester.add(V, u)
                }
              } else w = !0, !0 !== S && (T.push(void 0), L.push(void 0))
            }
            var G = 0;
            if (this.hasLines)
              if (this.isArea ? a.fill = x || s._getColorLight(u) : a.fill = "none", w && !0 !== S) {
                for (var Y = [], E = [], I = 0; I < f; I++) void 0 === T[I] ? (Y.push(void 0), E.push(0)) : (Y.push(T[I]), E.push(L[I]));
                Y.length > 1 && (_._isPolar && c !== e.ChartType.Area ? this._drawLines(a, Y, E, null, y, this.chart._plotrectId) : (_.totalAngle < 360 && (Y.push(_._center.x), E.push(_._center.y)), a.drawPolygon(Y, E, null, y, this.chart._plotrectId)), this.hitTester.add(new e._LinesArea(Y, E), u), G++)
              } else _._isPolar && c !== e.ChartType.Area ? this._drawLines(a, T, L, null, y, this.chart._plotrectId) : (_.totalAngle < 360 && (T.push(_._center.x), L.push(_._center.y)), a.drawPolygon(T, L, null, y, this.chart._plotrectId)), this.hitTester.add(new e._LinesArea(T, L), u), G++;
            a.fill = x;
            for (I = 0; I < f; I++) {
              var N = T[I],
                O = L[I];
              !1 === this.hasLines && (a.fill = p[I] > 0 ? x : m, a.stroke = p[I] > 0 ? A : P), this.isValid(N, O, r, i) && ((this.hasSymbols || this.chart.itemFormatter) && b > 0 && this._drawSymbol(a, N, O, b, h, I), n._setPointIndex(I, G), G++)
            }
          }
        }, r
      }(e._LinePlotter);
      a._RadarLinePlotter = r
    }(e.radar || (e.radar = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var a in e) e.hasOwnProperty(a) && (t[a] = e[a])
      };
    return function(e, a) {
      function r() {
        this.constructor = e
      }
      t(e, a), e.prototype = null === a ? Object.create(a) : (r.prototype = a.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(a) {
      "use strict";
      var r = function(a) {
        function r() {
          return null !== a && a.apply(this, arguments) || this
        }
        return __extends(r, a), r.prototype.adjustLimits = function(e, a) {
          this.dataInfo = e;
          var r = e.getMinX(),
            i = e.getMinY(),
            n = e.getMaxX(),
            s = e.getMaxY(),
            o = e.getDeltaX();
          return o <= 0 && (o = 1), this.chart.totalAngle < 360 && (o = 0), this.unload(), this.chart.axisY._getLogBase() || (this.origin > s ? s = this.origin : this.origin < i && (i = this.origin)), new t.Rect(r, i, n - r + o, s - i)
        }, r.prototype._getLabelPoint = function(t, e) {
          var a = t._getAxisX(),
            r = t._getAxisY(),
            i = a.convert(e.dataX),
            n = r.convert(e.dataY);
          return this.chart._convertPoint(n, i)
        }, r.prototype.plotSeries = function(a, r, i, n, s, o, l) {
          var h = this.chart.series.indexOf(n),
            _ = t.asType(n, e.SeriesBase),
            c = (this.chart.options, this.width),
            u = this.chart,
            p = -90 * Math.PI / 180;
          o = o || 0;
          var d, y = _._getAxisY()._uniqueId,
            f = (this.stackNegMap[y], this.stackPosMap[y]),
            g = this.stacking != e.Stacking.None,
            v = this.stacking == e.Stacking.Stacked100pc,
            x = n.getValues(0),
            m = n.getValues(1);
          if (x) {
            m || (m = this.dataInfo.getXVals());
            var A;
            (A = m ? u.totalAngle / m.length : u.totalAngle / (r.actualMax - r.actualMin)) > 0 && (c = g ? A * c * Math.PI / 180 : A * Math.pow(c, o + 1) * Math.PI / 180);
            var P = _._getSymbolFill(h),
              b = _._getAltSymbolFill(h) || P,
              T = _._getSymbolStroke(h),
              L = _._getAltSymbolStroke(h) || T,
              M = x.length;
            null != m && (M = Math.min(M, m.length));
            var C, k, S = this.origin,
              w = 0;
            void 0 !== _._getChartType() && (g = v = !1), S < i.actualMin ? S = i.actualMin : S > i.actualMax && (S = i.actualMax);
            i.convert(S);
            var I = r.actualMin,
              N = r.actualMax,
              O = i.actualMax;
            _._isCustomAxisY() && (g = v = !1), u._areas[h] || (u._areas[h] = []);
            for (var j = 0; j < M; j++) {
              var R = m ? m[j] : j,
                X = null == x[j] ? 0 : x[j];
              if (this._getSymbolOrigin && i.convert(this._getSymbolOrigin(S, j, M)), this._getSymbolStyles) {
                var F = this._getSymbolStyles(j, M);
                P = F && F.fill ? F.fill : P, b = F && F.fill ? F.fill : b, T = F && F.stroke ? F.stroke : T, L = F && F.stroke ? F.stroke : L
              }
              if (C = X > 0 ? P : b, k = X > 0 ? T : L, a.fill = C, a.stroke = k, e._DataInfo.isValid(R) && e._DataInfo.isValid(X)) {
                if (g) {
                  var D = R - .5 * c,
                    V = R + .5 * c;
                  if (D < I && V < I || D > N && V > N) continue;
                  if (D = r.convert(D), V = r.convert(V), !e._DataInfo.isValid(D) || !e._DataInfo.isValid(V)) continue;
                  var G, Y;
                  v && (X /= this.dataInfo.getStackedAbsSum(R));
                  var E = isNaN(f[R]) ? 0 : f[R];
                  G = E, Y = E + X, f[R] = E + X;
                  var B = r.convert(R),
                    W = i.convert(G),
                    H = i.convert(Y);
                  B -= c / 2, a.drawDonutSegment(u._center.x, u._center.y, H, W, B + p, c, null, _.symbolStyle), (d = new e._DonutSegment(new t.Point(u._center.x, u._center.y), H, W, B + p, c, u.startAngle || 0)).tag = new e._DataPoint(h, j, R, E + X), this.hitTester.add(d, h)
                } else {
                  var B = r.convert(R),
                    K = i.convert(Math.min(X, O));
                  u._convertPoint(K, B);
                  B -= c / 2, a.drawPieSegment(u._center.x, u._center.y, K, B + p, c, null, _.symbolStyle), (d = new e._PieSegment(new t.Point(u._center.x, u._center.y), K, B + p, c, u.startAngle)).tag = new e._DataPoint(h, j, R, X), this.hitTester.add(d, h)
                }
                u._areas[h].push(d), n._setPointIndex(j, w), w++
              }
            }
          }
        }, r
      }(e._BarPlotter);
      a._RadarBarPlotter = r
    }(e.radar || (e.radar = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
