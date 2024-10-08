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
      function r() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (r.prototype = i.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var r = function(r) {
        function o(t, e) {
          var i = r.call(this, t, e) || this;
          return i._selectionIndex = 0, i.applyTemplate("wj-sunburst", null, null), i.initialize(e), i.refresh(), i
        }
        return __extends(o, r), Object.defineProperty(o.prototype, "bindingName", {
          get: function() {
            return this._bindName
          },
          set: function(e) {
            e != this._bindName && (t.assert(null == e || t.isArray(e) || t.isString(e), "bindingName should be an array or a string."), this._bindName = e, this._bindChart())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(o.prototype, "childItemsPath", {
          get: function() {
            return this._childItemsPath
          },
          set: function(e) {
            e != this._childItemsPath && (t.assert(null == e || t.isArray(e) || t.isString(e), "childItemsPath should be an array or a string."), this._childItemsPath = e, this._bindChart())
          },
          enumerable: !0,
          configurable: !0
        }), o.prototype._initData = function() {
          r.prototype._initData.call(this), this._processedData = [], this._level = 1, this._legendLabels = [], this._processedItem = []
        }, o.prototype._performBind = function() {
          var t, e = this;
          this._initData(), this._cv && (t = this._cv.items, this._cv.groups && this._cv.groups.length ? this._processedData = i.HierarchicalUtil.parseDataToHierarchical(this._cv, this.binding, this.bindingName, this.childItemsPath) : t && (this._processedData = i.HierarchicalUtil.parseDataToHierarchical(t, this.binding, this.bindingName, this.childItemsPath)), this._processedData && this._processedData.length && (this._sum = this._calculateValueAndLevel(this._processedData, 1), this._processedData.forEach(function(t) {
            e._legendLabels.push(t.name)
          })))
        }, o.prototype._calculateValueAndLevel = function(t, e) {
          var i = this,
            r = 0,
            o = this._values,
            n = this._labels;
          return this._level < e && (this._level = e), t.forEach(function(t) {
            var a;
            t.items ? (a = i._calculateValueAndLevel(t.items, e + 1), t.value = a, o.push(a), n.push(t.name)) : (a = i._getBindData(t, o, n, "value", "name"), t.value = a), r += a
          }), r
        }, o.prototype._renderPie = function(t, e, i, r, o) {
          var n = this._getCenter();
          this._sliceIndex = 0, this._parentRef = {}, this._renderHierarchicalSlices(t, n.x, n.y, this._processedData, this._sum, e, i, r, 2 * Math.PI, o, 1)
        }, o.prototype._renderHierarchicalSlices = function(t, e, i, r, o, n, a, s, l, h, c) {
          var u, _, d, p, m, f, g, v, C, b, y = r.length,
            w = s,
            I = 1 == this.reversed;
          d = (n - a) / this._level, u = n - (this._level - c) * d, _ = a + (c - 1) * d;
          for (var x = 0; x < y; x++) {
            if (v = e, C = i, g = t.startGroup("wj-slice slice-level" + c), 1 === c && (t.fill = this._getColorLight(x), t.stroke = this._getColor(x)), m = r[x], f = Math.abs(m.value), p = Math.abs(f - o) < 1e-10 ? l : l * f / o, b = I ? w - .5 * p : w + .5 * p, h > 0 && p < l && (v += h * Math.cos(b), C += h * Math.sin(b)), m.items) {
              var L = this._sliceIndex;
              for (this._renderHierarchicalSlices(t, v, C, m.items, f, n, a, w, p, 0, c + 1); L < this._sliceIndex; L++) null == this._parentRef[L] && (this._parentRef[L] = this._sliceIndex)
            }
            this._renderSlice(t, v, C, b, this._sliceIndex, u, _, w, p, l), this._processedItem.push(m.item), this._sliceIndex++, I ? w -= p : w += p, t.endGroup(), this._pels.push(g)
          }
        }, o.prototype._getLabelsForLegend = function() {
          return this._legendLabels || []
        }, o.prototype._highlightCurrent = function() {
          this.selectionMode != e.SelectionMode.None && this._highlight(!0, this._selectionIndex)
        }, o.prototype.hitTest = function(t, i) {
          var o = r.prototype.hitTest.call(this, t, i),
            n = this._toControl(t, i);
          if (e.FlexChart._contains(this._rectChart, n)) {
            var a = o.pointIndex,
              s = this._processedItem[a],
              l = new e._DataPoint(null, a, null, null);
            l.item = s, o._setDataPoint(l)
          }
          return o
        }, o.prototype._getSelectedItemOffset = function(t, e) {
          var i = 0,
            r = 0,
            o = 0;
          if (this.selectedItemOffset > 0)
            if (t == this.selectedIndex) o = this.selectedItemOffset;
            else {
              var n = this._getSelectedParentIndex(t);
              if (null != n) {
                var a = this._areas[n];
                this.dataLabel.position;
                o = this.selectedItemOffset, e = ((e = a.langle + this._rotationAngle) % 360 + 360) % 360, e *= Math.PI / 180
              }
            }
          return o > 0 && (i = Math.cos(e) * o * this._radius, r = Math.sin(e) * o * this._radius), {
            x: i,
            y: r
          }
        }, o.prototype._getSelectedParentIndex = function(t) {
          var e = this._parentRef[t];
          return null != e ? e === this.selectedIndex ? e : this._getSelectedParentIndex(e) : null
        }, o
      }(e.FlexPie);
      i.Sunburst = r
    }(e.hierarchical || (e.hierarchical = {}))
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
      function r() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (r.prototype = i.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var r;
      ! function(t) {
        t[t.Squarified = 0] = "Squarified", t[t.Horizontal = 1] = "Horizontal", t[t.Vertical = 2] = "Vertical"
      }(r = i.TreeMapType || (i.TreeMapType = {}));
      var o = function(o) {
        function l(t, i) {
          var r = o.call(this, t, null, !0) || this;
          return r._values = [], r._labels = [], r._areas = [], r._sum = 0, r._keywords = new e._KeyWords, r._processedData = [], r._depth = 1, r._itemIndex = 0, r._processedItem = [], r._maxDepth = -1, r._tmItems = [], r._colRowLens = [], r._defPalette = [{
            titleColor: "#033884",
            maxColor: "#1450a7",
            minColor: "#83b3f9"
          }, {
            titleColor: "#a83100",
            maxColor: "#dc4a0d",
            minColor: "#ffb190"
          }, {
            titleColor: "#006658",
            maxColor: "#008d7a",
            minColor: "#7deddf"
          }, {
            titleColor: "#a10046",
            maxColor: "#df0061",
            minColor: "#ff8cbe"
          }, {
            titleColor: "#784d08",
            maxColor: "#99681a",
            minColor: "#efc989"
          }, {
            titleColor: "#54156f",
            maxColor: "#722a90",
            minColor: "#cf95e7"
          }, {
            titleColor: "#998605",
            maxColor: "#c2ac19",
            minColor: "#ffef8b"
          }, {
            titleColor: "#9a0005",
            maxColor: "#c80c14",
            minColor: "#ff888d"
          }], r.applyTemplate("wj-control wj-flexchart wj-treemap", null, null), r._currentRenderEngine = new e._SvgRenderEngine(r.hostElement), r._legend = new e.Legend(r), r._legend.position = e.Position.None, r._tooltip = new e.ChartTooltip, r._tooltip.content = "<b>{name}</b><br/>{value}", r._tooltip.showDelay = 0, r._lbl = new e.DataLabel, r._lbl.position = e.LabelPosition.Center, r._lbl._chart = r, r.hostElement.addEventListener("mousemove", function(t) {
            r.isTouching || r._toogleTooltip(t)
          }), r.hostElement.addEventListener("click", function(t) {
            var i = !0;
            if (r.maxDepth > 0) {
              var o = r.hitTest(t),
                n = e.FlexChart._SELECTION_THRESHOLD;
              if (r.tooltip && r.tooltip.threshold && (n = r.tooltip.threshold), o.distance <= n && o.pointIndex >= -1 && o.pointIndex < r._areas.length) {
                var a = r._areas[o.pointIndex];
                r._currentItem != a.item && (r._currentItem = a.item, r._refreshChart(), i = !1)
              }
            }
            i && r.isTouching && r._toogleTooltip(t)
          }), r.hostElement.addEventListener("contextmenu", function(t) {
            if (r.maxDepth > 0) {
              var i = r.hitTest(t),
                o = e.FlexChart._SELECTION_THRESHOLD;
              r.tooltip && r.tooltip.threshold && (o = r.tooltip.threshold), i.distance <= o && r._rollUp()
            }
            return t.preventDefault(), !1
          }), r.hostElement.addEventListener("mouseleave", function() {
            r._hideToolTip()
          }), r.initialize(i), r.refresh(), r
        }
        return __extends(l, o), l.prototype._rollUp = function() {
          this._currentItem = this._currentItem && this._currentItem.parent ? this._currentItem.parent : null, this._refreshChart()
        }, l.prototype._toogleTooltip = function(e) {
          var i = this._tooltip;
          if (i.content) {
            var r = this.hitTest(e);
            if (r.distance <= i.threshold) {
              var o = this._getLabelContent(r, this.tooltip.content);
              this._showToolTip(o, new t.Rect(e.clientX, e.clientY, 5, 5))
            } else this._hideToolTip()
          }
        }, Object.defineProperty(l.prototype, "selectionMode", {
          get: function() {
            return e.SelectionMode.None
          },
          set: function(t) {},
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "_treeMapItems", {
          get: function() {
            return this._tmItems
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "tooltip", {
          get: function() {
            return this._tooltip
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "binding", {
          get: function() {
            return this._binding
          },
          set: function(e) {
            e != this._binding && (this._binding = t.asString(e, !0), this._bindChart())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "type", {
          get: function() {
            return null == this._type ? r.Squarified : this._type
          },
          set: function(e) {
            (e = t.asEnum(e, r)) != this._type && (this._type = e, this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "bindingName", {
          get: function() {
            return this._bindingName
          },
          set: function(e) {
            e != this._bindingName && (t.assert(null == e || t.isArray(e) || t.isString(e), "bindingName should be an array or a string."), this._bindingName = e, this._bindChart())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "dataLabel", {
          get: function() {
            return this._lbl
          },
          set: function(t) {
            t != this._lbl && (this._lbl = t, this._lbl && (this._lbl._chart = this))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "childItemsPath", {
          get: function() {
            return this._childItemsPath
          },
          set: function(e) {
            e != this._childItemsPath && (t.assert(null == e || t.isArray(e) || t.isString(e), "childItemsPath should be an array or a string."), this._childItemsPath = e, this._bindChart())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "maxDepth", {
          get: function() {
            return this._maxDepth
          },
          set: function(e) {
            e != this._maxDepth && (this._maxDepth = t.asNumber(e, !0), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "palette", {
          get: function() {
            return this._palette
          },
          set: function(e) {
            e != this._palette && (this._palette = t.asArray(e), this._tmItems && this._tmItems.length > 0 && this._calculateColorForItems(this._tmItems), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), l.prototype._initData = function() {
          this._sum = 0, this._tmItems = [], this._currentItem = null, this._values = [], this._labels = [], this._processedData = [], this._depth = 1, this._processedItem = []
        }, l.prototype._performBind = function() {
          var t;
          this._initData(), this._cv && (t = this._cv.items, this._cv.groups && this._cv.groups.length ? this._processedData = i.HierarchicalUtil.parseDataToHierarchical(this._cv, this.binding, this.bindingName, this.childItemsPath) : t && (this._processedData = i.HierarchicalUtil.parseDataToHierarchical(t, this.binding, this.bindingName, this.childItemsPath)), this._processedData && this._processedData.length && (this._sum = this._calculateValueAndDepth(this._processedData, 1), this._sortData(this._processedData), this._values = [], this._getTMItemsAndLabelsAndValues(this._processedData, this._tmItems, 1, null), this._calculateColorForItems(this._tmItems)))
        }, l.prototype._sortData = function(t) {
          var e = this;
          t.forEach(function(t) {
            t.items && e._sortData(t.items)
          }), t.sort(function(t, e) {
            return e.value - t.value
          })
        }, l.prototype._getTMItemsAndLabelsAndValues = function(t, e, i, r, o) {
          var a = this;
          t && t.length > 0 && t.forEach(function(t, o) {
            var s, l = new n;
            l.items = [], l.parent = r, l.depth = i, t.items && a._getTMItemsAndLabelsAndValues(t.items, l.items, i + 1, l), s = t.name ? t.name : t.value.toString(), l.label = s, l.value = t.value, null != r && (t.value > r.maxValue && (r.maxValue = t.value), t.value < r.minValue && (r.minValue = t.value)), e.push(l), a._labels.push(s), a._values.push(t.value)
          })
        }, l.prototype._calculateColorForItems = function(e, i, r) {
          var o = this,
            n = r;
          e.forEach(function(e, r) {
            var s = i;
            1 === e.depth && (s = o._getColor(r)), e.palette = s;
            var l = e.palette;
            if (t.isString(l)) {
              var h = l,
                c = o._getLightColor(h);
              e.titleFill = h, e.titleStroke = h, e.fill = c, e.stroke = h
            } else if (l.maxColor && l.minColor && l.titleColor)
              if (e.titleFill = l.titleColor, e.titleStroke = l.titleColor, null == e.parent) e.fill = l.maxColor, e.stroke = l.maxColor;
              else {
                null == n && (n = new a(l.minColor, e.minValue, l.maxColor, e.maxValue));
                var u = n._calculateColorByVal(e.value, !0).toString();
                e.fill = u, e.stroke = u
              }
            if (e.items && e.items.length > 0) {
              var _ = new a(l.minColor, e.minValue, l.maxColor, e.maxValue);
              o._calculateColorForItems(e.items, s, _)
            }
          })
        }, l.prototype._getBindData = function(e, i, r) {
          var o, n = 0;
          r && (o = e[r]);
          n = 0;
          return t.isNumber(o) ? n = t.asNumber(o) : o && (n = parseFloat(o.toString())), !isNaN(n) && isFinite(n) ? i.push(n) : (n = 0, i.push(n)), n
        }, l.prototype._calculateValueAndDepth = function(t, e) {
          var i = this,
            r = 0,
            o = this._values;
          return this._depth < e && (this._depth = e), t.forEach(function(t) {
            var n;
            t.items ? (n = i._calculateValueAndDepth(t.items, e + 1), t.value = n, o.push(n)) : (n = i._getBindData(t, o, "value"), t.value = n), r += n
          }), r
        }, l.prototype._prepareRender = function() {
          this._areas = []
        }, l.prototype._renderChart = function(i, r, o) {
          var n, a, s, h = this._rectChart.clone();
          new t.Size(h.width, h.height);
          this.onRendering(new e.RenderEventArgs(i));
          var c = r.width,
            u = r.height;
          this._tmGroup = i.startGroup(null, null, !0);
          var _ = this._parseMargin(this.plotMargin);
          this.dataLabel;
          isNaN(_.left) && (_.left = l._MARGIN), isNaN(_.right) && (_.right = l._MARGIN), isNaN(_.top) && (_.top = l._MARGIN), isNaN(_.bottom) && (_.bottom = l._MARGIN), r.top += _.top;
          u = r.height - (_.top + _.bottom);
          r.height = u > 0 ? u : 24, r.left += _.left;
          c = r.width - (_.left + _.right);
          r.width = c > 0 ? c : 24, this._plotRect = r, n = this._currentItem ? [this._currentItem] : this._tmItems, a = null == this._currentItem || this.maxDepth < 1 ? this.maxDepth : this._currentItem && this._currentItem.items && this._currentItem.items.length && this.maxDepth > 1 ? this.maxDepth : this.maxDepth + 1, s = this._currentItem ? this._currentItem.value : this._sum, this._renderTreeMap(i, r, this._tmGroup, n, s, a), i.endGroup(), this.dataLabel.content && this.dataLabel.position != e.LabelPosition.None && this._renderLabels(i), this.onRendered(new e.RenderEventArgs(i))
        }, l.prototype._renderTreeMap = function(t, e, i, r, o, n) {
          o > 0 && (this._itemIndex = 0, this._resetItemRects(this._tmItems), this._calculateItemRects(e, r, o, 1, n), this._renderHierarchicalTreeMapItems(t, i, e, this._tmItems, o, 1, n))
        }, l.prototype._resetItemRects = function(e) {
          var i = this;
          e.forEach(function(e) {
            e.rect = new t.Rect(0, 0, 0, 0), e.isTitle = !1, e.type = i.type, e.items && e.items.length && i._resetItemRects(e.items)
          })
        }, l.prototype._calculateItemRects = function(t, e, i, o, n) {
          var a = this;
          switch (this.type) {
            case r.Horizontal:
              s.horizontal(e, t, i);
              break;
            case r.Vertical:
              s.vertical(e, t, i);
              break;
            case r.Squarified:
              s.squarified(e, t, i)
          }
          e.forEach(function(t, e) {
            t.rect.clone();
            t.items && t.items.length && (o === n || o > n && n >= 1 || (t.isTitle = !0, a._calculateItemRects(t.itemsRect, t.items, t.value, o + 1, n)))
          })
        }, l.prototype._renderHierarchicalTreeMapItems = function(t, i, r, o, n, a, s) {
          var h, c, u, _, d, p = o.length;
          this.type;
          if (0 !== p)
            for (var m = 0; m < p; m++) h = t.startGroup(l._CSS_ITEMDEPTH + a), c = o[m], u = Math.abs(c.value), _ = c.rect, c.draw(t), d = new e._RectArea(_), c.items && this._renderHierarchicalTreeMapItems(t, h, c.itemsRect, c.items, u, a + 1, s), d.tag = this._itemIndex, d.name = c.label, d.value = u, d.item = c, this._areas.push(d), this._itemIndex++, t.endGroup()
        }, l.prototype._renderLabels = function(i) {
          var r, o = this._areas.length,
            n = this.dataLabel,
            a = n.position,
            s = n.connectingLine,
            l = n.border,
            h = n.offset || 0;
          i.stroke = "null", i.fill = "transparent", i.strokeWidth = 1, i.startGroup("wj-data-labels");
          for (var c = 0; c < o; c++) {
            var u = this._areas[c];
            if (u) {
              var _ = u.rect,
                d = new e.HitTestInfo(this, r);
              d._setData(null, c);
              var p = this._getLabelContent(d, n.content);
              if (r = new t.Point(_.left + _.width / 2, _.top + _.height / 2), p && _.width > 0 && _.height > 0) {
                var m = new e.DataLabelRenderEventArgs(i, d, r, p);
                n.onRendering(m) && (p = m.text, r = m.point, this._renderLabelAndBorder(i, u, _, p, a, h, r, s, 2, l))
              }
            }
          }
          i.endGroup()
        }, l.prototype._renderLabelAndBorder = function(t, i, r, o, n, a, s, l, h, c) {
          var u, _ = "wj-data-label",
            d = "wj-data-label-line";
          switch (n) {
            case e.LabelPosition.Top:
              l && t.drawLine(s.x, s.y, s.x, s.y - a, d), s.y -= h + a, u = this._renderText(t, i, r, o, s, 1, 2, _);
              break;
            case e.LabelPosition.Bottom:
              l && t.drawLine(s.x, s.y, s.x, s.y + a, d), s.y += h + a, u = this._renderText(t, i, r, o, s, 1, 0, _);
              break;
            case e.LabelPosition.Left:
              l && t.drawLine(s.x, s.y, s.x - a, s.y, d), s.x -= h + a, u = this._renderText(t, i, r, o, s, 2, 1, _);
              break;
            case e.LabelPosition.Right:
              l && t.drawLine(s.x, s.y, s.x + a, s.y, d), s.x += h + a, u = this._renderText(t, i, r, o, s, 0, 1, _);
              break;
            case e.LabelPosition.Center:
              u = this._renderText(t, i, r, o, s, 1, 1, _)
          }
          return c && u && t.drawRect(u.left - h, u.top - h, u.width + 2 * h, u.height + 2 * h, "wj-data-label-border"), u
        }, l.prototype._renderText = function(t, i, o, n, a, s, l, h) {
          var c, u = n,
            _ = i.item;
          return c = t.measureString(n, h), this.type === r.Horizontal && _.isTitle ? (c.width > o.height && (u = this._cutText(n, c.width, o.height)), e.FlexChart._renderRotatedText(t, u, a, s, l, a, -90, h), null) : (c.width > o.width && (u = this._cutText(n, c.width, o.width)), e.FlexChart._renderText(t, u, a, s, l, h))
        }, l.prototype._cutText = function(t, e, i) {
          var r = "",
            o = t.length,
            n = Math.floor((1 - (e - i) / e) * o);
          return t.length > 0 && (r = t[0] + (n > 1 ? t.substring(1, n - 1) + ".." : "")), r
        }, l.prototype._measureLegendItem = function(i, r) {
          var o = new t.Size;
          if (o.width = e.Series._LEGEND_ITEM_WIDTH, o.height = e.Series._LEGEND_ITEM_HEIGHT, r) {
            var n = i.measureString(r, e.FlexChart._CSS_LABEL, e.FlexChart._CSS_LEGEND);
            o.width += n.width, o.height < n.height && (o.height = n.height)
          }
          return o.width += 3 * e.Series._LEGEND_ITEM_MARGIN, o.height += 2 * e.Series._LEGEND_ITEM_MARGIN, o
        }, l.prototype._getDesiredLegendSize = function(e, i, r, o) {
          var n = new t.Size,
            a = new t.Size(r, o),
            s = this._tmItems.length,
            l = 0,
            h = 0;
          this._colRowLens = [];
          for (var c = 0; c < s; c++) {
            var u = this._measureLegendItem(e, this._tmItems[c].label);
            i ? (h + u.height > o && (n.height = o, this._colRowLens.push(l), l = 0, h = 0), l < u.width && (l = u.width), h += u.height) : (l + u.width > r && (n.width = r, this._colRowLens.push(h), h = 0, l = 0), h < u.height && (h = u.height), l += u.width)
          }
          return i ? (n.height < h && (n.height = h), this._colRowLens.push(l), n.width = this._colRowLens.reduce(function(t, e) {
            return t + e
          }, 0), n.width > a.width / 2 && (n.width = a.width / 2)) : (n.width < l && (n.width = l), this._colRowLens.push(h), n.height = this._colRowLens.reduce(function(t, e) {
            return t + e
          }, 0), n.height > a.height / 2 && (n.height = a.height / 2)), n
        }, l.prototype._renderLegend = function(e, i, r, o, n, a) {
          for (var s, l = this._rectLegend, h = this._tmItems.length, c = 0, u = i.clone(), _ = 0; _ < h; _++) {
            s = this._tmItems[_].label;
            var d = this._measureLegendItem(e, s);
            o ? u.y + d.height > l.top + l.height + 1 && (u.x += this._colRowLens[c], c++, u.y = i.y) : u.x + d.width > l.left + l.width + 1 && (u.y += this._colRowLens[c], c++, u.x = i.x);
            var p = new t.Rect(u.x, u.y, d.width, d.height);
            this._drawLegendItem(e, p, _, s), r.push(p), o ? u.y += d.height : u.x += d.width
          }
        }, l.prototype._drawLegendItem = function(i, r, o, n) {
          i.strokeWidth = 1;
          var a = e.Series._LEGEND_ITEM_MARGIN,
            s = this._getColor(o),
            l = s && s.maxColor ? s.maxColor : s,
            h = this._getLightColor(l);
          i.fill = l, i.stroke = h;
          var c = r.top + .5 * r.height,
            u = e.Series._LEGEND_ITEM_WIDTH,
            _ = e.Series._LEGEND_ITEM_HEIGHT;
          i.drawRect(r.left + a, c - .5 * _, u, _, null), n && e.FlexChart._renderText(i, n, new t.Point(r.left + _ + 2 * a, c), 0, 1, e.FlexChart._CSS_LABEL)
        }, l.prototype._getLabelContent = function(e, i) {
          return t.isString(i) ? this._keywords.replace(i, e) : t.isFunction(i) ? i(e) : null
        }, l.prototype.hitTest = function(t, i) {
          var r = this._toControl(t, i),
            o = new e.HitTestInfo(this, r),
            n = null;
          if (e.FlexChart._contains(this._rectHeader, r)) o._chartElement = e.ChartElement.Header;
          else if (e.FlexChart._contains(this._rectFooter, r)) o._chartElement = e.ChartElement.Footer;
          else if (e.FlexChart._contains(this._rectLegend, r)) o._chartElement = e.ChartElement.Legend, null !== (n = this.legend._hitTest(r)) && n >= 0 && n < this._areas.length && o._setData(null, n);
          else if (e.FlexChart._contains(this._rectChart, r)) {
            for (var a, s = this._areas.length, l = NaN, h = 0; h < s; h++) {
              var c = r.clone(),
                u = this._areas[h];
              u.contains(c) && (o._setData(null, u.tag), o._dist = 0);
              var _ = u.distance(c);
              void 0 !== _ && (isNaN(l) || _ < l) && (l = _, a = u)
            }
            0 !== o._dist && null != a && (o._setData(null, a.tag), o._dist = l), o._chartElement = e.ChartElement.ChartArea
          } else o._chartElement = e.ChartElement.None;
          return o
        }, l.prototype._getHitTestItem = function(t) {
          var e = null,
            i = null;
          return (e = null != this._cv ? this._cv.items : this.itemsSource) && t < e.length && (i = e[t]), i
        }, l.prototype._getHitTestValue = function(t) {
          return this._values[t]
        }, l.prototype._getHitTestLabel = function(t) {
          return this._labels[t]
        }, l._CSS_ITEMDEPTH = "wj-treemap-item-depth", l._MARGIN = 0, l
      }(e.FlexChartBase);
      i.TreeMap = o;
      var n = function() {
          function e() {
            this.items = [], this.maxValue = Number.MIN_VALUE, this.minValue = Number.MAX_VALUE
          }
          return e.prototype.draw = function(t) {
            var i = this.rect;
            t.strokeWidth = 0, this.isTitle ? (t.fill = this.titleFill, t.stroke = this.titleStroke) : (t.fill = this.fill, t.stroke = this.stroke), t.drawRect(i.left, i.top, i.width, i.height, e._CLASSNAME)
          }, Object.defineProperty(e.prototype, "itemsRect", {
            get: function() {
              var e = this.rect,
                i = this._rect,
                o = 1 === this.depth ? 2 : .5;
              return this.isTitle ? this.type === r.Horizontal ? new t.Rect(e.left + e.width + 1, e.top, i.width - e.width - 2 * o, e.height + 1) : new t.Rect(e.left, e.top + e.height + 1, e.width + 1, i.height - e.height - 2 * o) : new t.Rect(0, 0, 0, 0)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(e.prototype, "rect", {
            get: function() {
              var e = this._rect,
                i = 1 === this.depth ? 2 : .5,
                o = e.width,
                n = e.height,
                a = e.left,
                s = e.top;
              return this.isTitle ? (this.type === r.Horizontal ? (o = e.width > 20 ? 20 : o, o = Math.max(20, o - 2 * i), n = n > 2 * i ? n - 2 * i : 0) : (n = e.height > 20 ? 20 : n, n = Math.max(20, n - 2 * i), o = o > 2 * i ? o - 2 * i : 0), a += i, s += i) : (o = o > 2 * i ? o - 2 * i : 0, n = n > 2 * i ? n - 2 * i : 0), new t.Rect(a, s, o, n)
            },
            set: function(t) {
              t != this._rect && (this._rect = t)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(e.prototype, "isTitle", {
            get: function() {
              return this._isTitle
            },
            set: function(e) {
              var i = t.asBoolean(e, !0);
              i !== this._isTitle && (this._isTitle = i)
            },
            enumerable: !0,
            configurable: !0
          }), e._CLASSNAME = "wj-treemap-item", e
        }(),
        a = function() {
          function e(e, i, r, o, n, a) {
            this.minColor = new t.Color(e), this.minColorValue = i, this.maxColor = new t.Color(r), this.maxColorValue = o, this.midColorValue = this.originalMidColorValue = a, this._calculateMidColorValue(), this.midColor = this.originalMidColor = new t.Color(n), this._calculateMidColor()
          }
          return e.prototype._resetminColor = function(e) {
            this.minColor = new t.Color(e), this._calculateMidColor()
          }, e.prototype._resetmidColor = function(e) {
            this.midColor = this.originalMidColor = new t.Color(e), this._calculateMidColor()
          }, e.prototype._resetmaxColor = function(e) {
            this.maxColor = new t.Color(e), this._calculateMidColor()
          }, e.prototype._resetminColorValue = function(t) {
            this.minColorValue = t, this._calculateMidColorValue()
          }, e.prototype._resetmidColorValue = function(t) {
            this.midColorValue = this.originalMidColorValue = t, this._calculateMidColorValue()
          }, e.prototype._resetmaxColorValue = function(t) {
            this.maxColorValue = t, this._calculateMidColorValue()
          }, e.prototype._calculateMidColorValue = function() {
            null == this.originalMidColorValue && (this.midColorValue = (this.maxColorValue + this.minColorValue) / 2)
          }, e.prototype._calculateMidColor = function() {
            null == this.originalMidColor && (this.midColor = this._calculateColorByVal(this.midColorValue, !0))
          }, e.prototype._calculateColorByVal = function(e, i) {
            void 0 === i && (i = !1);
            var r = this.maxColor,
              o = this.minColor,
              n = this.maxColorValue,
              a = this.minColorValue;
            if (e >= this.maxColorValue) return new t.Color(r.toString());
            if (e <= this.minColorValue) return new t.Color(o.toString());
            if (!i) {
              if (e === this.midColorValue) return new t.Color(this.midColor.toString());
              e < this.midColorValue ? (r = this.midColor, n = this.midColorValue) : (o = this.midColor, a = this.midColorValue)
            }
            return this._getColor(e, r, n, o, a)
          }, e.prototype._getColor = function(e, i, r, o, n) {
            return t.Color.fromRgba(this._getValueByRatio(e, i.r, r, o.r, n), this._getValueByRatio(e, i.g, r, o.g, n), this._getValueByRatio(e, i.b, r, o.b, n), this._getValueByRatio(e, i.a, r, o.a, n))
          }, e.prototype._getValueByRatio = function(t, e, i, r, o) {
            return Math.abs(r + Math.round((t - o) * (e - r) / (i - o)))
          }, e
        }(),
        s = function() {
          function e() {}
          return e.squarified = function(t, i, r) {
            var o = t.slice(),
              n = i.clone(),
              a = n.width * n.height / r;
            do {
              var s = e.getRowedItems(o, n, a);
              e.layoutRowedItems(i, s, n, n.width > n.height)
            } while (o.length)
          }, e.horizontal = function(t, i, r) {
            var o = i.clone();
            t.forEach(function(t) {
              var n = [{
                item: t,
                val: t.value * i.width * i.height / r
              }];
              e.layoutRowedItems(i, n, o, !1)
            })
          }, e.vertical = function(t, i, r) {
            var o = i.clone();
            t.forEach(function(t) {
              var n = [{
                item: t,
                val: t.value * i.width * i.height / r
              }];
              e.layoutRowedItems(i, n, o, !0)
            })
          }, e.getNarrowLen = function(t) {
            return Math.min(t.width, t.height)
          }, e.getRowedItem = function(t, e, i) {
            return {
              item: t,
              val: i * t.value
            }
          }, e.getRowedItems = function(t, i, r) {
            var o = t.shift(),
              n = [],
              a = [],
              s = e.getNarrowLen(i),
              l = e.getRowedItem(o, i, r);
            if (n.push(l), a.push(l), t.length > 0)
              do {
                if (a.push(e.getRowedItem(t[0], i, r)), !(e.worst(n, s) > e.worst(a, s))) break;
                n = a.slice(), t.shift()
              } while (t.length);
            return n
          }, e.layoutRowedItems = function(i, r, o, n) {
            var a, s = o.left,
              l = o.top,
              h = s + o.width,
              c = l + o.height,
              u = e.sumRowedArray(r);
            n ? (a = 0 === o.height ? 0 : u / o.height, s + a >= h && (a = h - s), r.forEach(function(e, i) {
              var o = 0 === a ? 0 : e.val / a;
              (l + o > c || i === r.length - 1) && (o = c - l);
              var n = new t.Rect(s, l, a, o);
              e.item.rect = n, l += o
            }), o.left += a, o.width -= a) : (a = 0 === o.width ? 0 : u / o.width, l + a >= c && (a = c - l), r.forEach(function(e, i) {
              var o = 0 === a ? 0 : e.val / a;
              (s + o > h || i === r.length - 1) && (o = h - s);
              var n = new t.Rect(s, l, o, a);
              e.item.rect = n, s += o
            }), o.top += a, o.height -= a)
          }, e.sumRowedArray = function(t) {
            for (var e = 0, i = t.length, r = 0; r < i; r++) e += t[r].val;
            return e
          }, e.worst = function(t, i) {
            var r, o, n = e.sumRowedArray(t),
              a = n * n,
              s = i * i;
            return r = o = t[0].val, t.forEach(function(t, e) {
              t.val > r ? r = t.val : t.val < o && (o = t.val)
            }), Math.max(s * r / a, a / (s * o))
          }, e
        }()
    }(e.hierarchical || (e.hierarchical = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";
      var i = function() {
        function e() {}
        return e.parseDataToHierarchical = function(i, r, o, n) {
          var a, s = [];
          return i instanceof t.collections.CollectionView && i.groups.length > 0 ? s = e.parseGroupCV(i, r) : i.length > 0 && (t.isString(o) && o.indexOf(",") > -1 && (o = o.split(",")), n ? s = e.parseItems(i, r, o, n) : (a = e.convertFlatData(i, r, o), s = e.parseItems(a, "value", o, "items"))), s
        }, e.parseGroupCV = function(t, e) {
          for (var i = [], r = 0, o = t.groups.length; r < o; r++) {
            var n = this.parseGroups(t.groups[r], e);
            i.push(n)
          }
          return i
        }, e.parseGroups = function(e, i) {
          var r = {};
          if (r.name = e.name, r.nameField = e.groupDescription.propertyName, r.item = e.items, e.groups && e.groups.length) {
            r.items = [];
            for (var o = 0, n = e.groups.length; o < n; o++) {
              var a = this.parseGroups(e.groups[o], i);
              r.items.push(a)
            }
          } else e.isBottomLevel && (r.value = e.getAggregate(t.Aggregate.Sum, i));
          return r
        }, e.parseItems = function(t, i, r, o) {
          var n, a = [],
            s = t.length;
          for (n = 0; n < s; n++) a.push(e.parseItem(t[n], i, r, o));
          return a
        }, e.isFlatItem = function(e, i) {
          return !t.isArray(e[i])
        }, e.convertFlatData = function(i, r, o) {
          var n, a, s = [],
            l = {},
            h = i.length;
          for (n = 0; n < h; n++) a = i[n], e.convertFlatItem(l, a, r, t.isArray(o) ? o : [o]);
          return e.convertFlatToHierarchical(s, l), s
        }, e.convertFlatToHierarchical = function(t, i) {
          var r = i.flatDataOrder;
          r && r.forEach(function(r) {
            var o, n = {},
              a = i[r];
            n[i.field] = r, a.flatDataOrder ? (o = [], e.convertFlatToHierarchical(o, a), n.items = o) : n.value = a, t.push(n)
          })
        }, e.convertFlatItem = function(i, r, o, n) {
          var a, s, l, h;
          return a = n.slice(), s = a.shift(), s = t.isString(s) ? s.trim() : s, null != (l = null == s ? o : r[s]) && (0 === a.length ? (i[l] = r[o] || 0, i.flatDataOrder ? i.flatDataOrder.push(l) : i.flatDataOrder = [l], i.field = s) : (null == i[l] && (i[l] = {}, i.flatDataOrder ? i.flatDataOrder.push(l) : i.flatDataOrder = [l], i.field = s), h = i[l], e.convertFlatItem(h, r, o, a) || (i[l] = r[o])), !0)
        }, e.parseItem = function(i, r, o, n) {
          var a, s, l, h, c, u = {};
          return t.isArray(n) ? h = (c = n.slice()).length ? c.shift().trim() : "" : (c = n, h = n), t.isArray(o) ? (s = null == (s = (a = o.slice()).shift()) ? s : s.trim(), u.nameField = null == s ? r : s, u.name = null == s ? i[r] : i[s], l = i[h], 0 === a.length ? u.value = i[r] : l && t.isArray(l) && l.length > 0 ? u.items = e.parseItems(l, r, a, c) : u.value = i[r] || 0) : (u.nameField = null == o ? r : o, u.name = null == o ? i[r] : i[o], null != (l = i[h]) && t.isArray(l) && l.length > 0 ? u.items = e.parseItems(l, r, o, c) : u.value = i[r]), u.item = i, u
        }, e.parseFlatItem = function(t, e, i, r) {
          t.items || (t.items = [])
        }, e
      }();
      e.HierarchicalUtil = i
    }(e.hierarchical || (e.hierarchical = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
