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
! function(t) {
  ! function(e) {
    ! function(n) {
      "use strict";
      var i = function() {
        function n(t, n) {
          void 0 === n && (n = !1), this._strokeWidth = 1, this._fontSize = null, this._fontFamily = null, this._applyCanvasClip = function(t, e) {
            var n = this._canvasRect[e];
            n && (t.beginPath(), t.rect(n.left, n.top, n.width, n.height), t.clip(), t.closePath())
          }, this._applyCanvasStyles = function(t, e, n, i, a) {
            var r, l, o, s = this,
              p = s._canvas.getContext("2d"),
              h = s.stroke,
              c = s.fill,
              f = s.strokeWidth;
            e && void 0 !== e.stroke && (h = e.stroke), e && void 0 !== e.fill && (c = s._getOpacityColor(e.fill, e["fill-opacity"])), t && (l = window.getComputedStyle(t), o = t.getBBox()), a ? l ? (p.fillStyle = l.fill, r = l.fontStyle + " " + l.fontSize + " " + l.fontFamily, p.font = r, p.font.replace(/\"/g, "'") !== r.replace(/\"/g, "'") && (r = l.fontStyle + " " + l.fontSize + " " + (p.font.split(" ")[1] || "sans-serif"), p.font = r)) : s.fontSize ? (p.fillStyle = s.textFill, p.font = s.fontSize + " " + (s.fontFamily || "sans-serif")) : s._canvasDefaultFont && (p.fillStyle = s._canvasDefaultFont.textFill, r = s._canvasDefaultFont.fontSize + " " + s._canvasDefaultFont.fontFamily, p.font = r, p.font.replace(/\"/g, "'") !== r.replace(/\"/g, "'") && (r = s._canvasDefaultFont.fontSize + " " + (p.font.split(" ")[1] || "sans-serif"), p.font = r)) : (l && (h = l.stroke && "none" !== l.stroke ? l.stroke : h, c = l.fill && "none" !== l.fill ? s._getOpacityColor(l.fill, l["fill-opacity"]) : c, f = l.strokeWidth ? l.strokeWidth : f), "none" !== h && null != h && (this._applyColor("strokeStyle", h, o), p.lineWidth = +f.replace(/px/g, ""), p.stroke()), i && null != c && "transparent" !== c && "none" !== c && (this._applyColor("fillStyle", c, o), p.fill()))
          };
          var i = this;
          i._element = t, i._canvas = document.createElement("canvas"), i._svgEngine = new e._SvgRenderEngine(t), i._element.appendChild(i._canvas), i._applyCSSStyles = n
        }
        return n.prototype.beginRender = function() {
          var t, e = this,
            n = e._svgEngine.element,
            i = e._element;
          e._applyCSSStyles && (e._svgEngine.beginRender(), i = n), e._element.appendChild(n), e._canvasRect = {}, t = window.getComputedStyle(i), e._canvasDefaultFont = {
            fontSize: t.fontSize,
            fontFamily: t.fontFamily,
            textFill: t.color
          }
        }, n.prototype.endRender = function() {
          this._applyCSSStyles && this._svgEngine.endRender(), this._svgEngine.element.parentNode.removeChild(this._svgEngine.element)
        }, n.prototype.setViewportSize = function(t, e) {
          var n, i, a = this,
            r = a._canvas,
            l = (r.getContext("2d"), a.fill);
          a._applyCSSStyles && a._svgEngine.setViewportSize(t, e), r.width = t, r.height = e, n = window.getComputedStyle(a._element).backgroundColor, i = a.stroke, n && (a.stroke = null, a.fill = n, a.drawRect(0, 0, t, e), a.fill = l, a.stroke = i)
        }, Object.defineProperty(n.prototype, "element", {
          get: function() {
            return this._canvas
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "fill", {
          get: function() {
            return this._fill
          },
          set: function(t) {
            this._svgEngine.fill = t, this._fill = t
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "fontSize", {
          get: function() {
            return this._fontSize
          },
          set: function(t) {
            this._svgEngine.fontSize = t;
            var e = null == t || isNaN(t) ? t : t + "px";
            this._fontSize = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "fontFamily", {
          get: function() {
            return this._fontFamily
          },
          set: function(t) {
            this._svgEngine.fontFamily = t, this._fontFamily = t
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "stroke", {
          get: function() {
            return this._stroke
          },
          set: function(t) {
            this._svgEngine.stroke = t, this._stroke = t
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "strokeWidth", {
          get: function() {
            return this._strokeWidth
          },
          set: function(t) {
            this._svgEngine.strokeWidth = t, this._strokeWidth = t
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "textFill", {
          get: function() {
            return this._textFill
          },
          set: function(t) {
            this._svgEngine.textFill = t, this._textFill = t
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.addClipRect = function(t, e) {
          t && e && (this._applyCSSStyles && this._svgEngine.addClipRect(t, e), this._canvasRect[e] = t.clone())
        }, n.prototype.drawEllipse = function(t, e, n, i, a, r) {
          var l, o = this._canvas.getContext("2d");
          return this._applyCSSStyles && (l = this._svgEngine.drawEllipse(t, e, n, i, a, r)), o.save(), o.beginPath(), o.ellipse ? o.ellipse(t, e, n, i, 0, 0, 2 * Math.PI) : (o.translate(t, e), o.scale(1, i / n), o.translate(-t, -e), o.arc(t, e, n, 0, 2 * Math.PI), o.scale(1, 1)), this._applyCanvasStyles(l, r, a, !0), o.restore(), l
        }, n.prototype.drawRect = function(t, e, n, i, a, r, l) {
          var o, s = this._canvas.getContext("2d");
          return this._applyCSSStyles && (o = this._svgEngine.drawRect(t, e, n, i, a, r, l)), s.save(), this._applyCanvasClip(s, l), s.beginPath(), s.rect(t, e, n, i), this._applyCanvasStyles(o, r, a, !0), s.restore(), o
        }, n.prototype.drawLine = function(t, e, n, i, a, r) {
          var l, o = this._canvas.getContext("2d");
          return this._applyCSSStyles && (l = this._svgEngine.drawLine(t, e, n, i, a, r)), o.save(), o.beginPath(), o.moveTo(t, e), o.lineTo(n, i), this._applyCanvasStyles(l, r, a), o.restore(), l
        }, n.prototype.drawLines = function(t, e, n, i, a) {
          if (t && e && 0 !== t.length && 0 !== e.length) {
            var r, l, o = this._canvas.getContext("2d"),
              s = Math.min(t.length, e.length);
            for (this._applyCSSStyles && (r = this._svgEngine.drawLines([0, 1], [1, 0], n, i, a)), o.save(), this._applyCanvasClip(o, a), o.beginPath(), o.moveTo(t[0], e[0]), l = 1; l < s; l++) o.lineTo(t[l], e[l]);
            return this._applyCanvasStyles(r, i, n), o.restore(), r
          }
        }, n.prototype.drawSplines = function(t, n, i, a, r) {
          if (t && n && 0 !== t.length && 0 !== n.length) {
            var l, o, s = this._canvas.getContext("2d"),
              p = new e._Spline(t, n).calculate(),
              h = p.xs,
              c = p.ys,
              f = Math.min(h.length, c.length);
            for (this._applyCSSStyles && (l = this._svgEngine.drawSplines([0, 1], [1, 0], i, a, r)), s.save(), this._applyCanvasClip(s, r), s.beginPath(), s.moveTo(h[0], c[0]), o = 1; o < f; o++) s.lineTo(h[o], c[o]);
            return this._applyCanvasStyles(l, a, i), s.restore(), l
          }
        }, n.prototype.drawPolygon = function(t, e, n, i, a) {
          if (t && e && 0 !== t.length && 0 !== e.length) {
            var r, l, o = this._canvas.getContext("2d"),
              s = Math.min(t.length, e.length);
            for (this._applyCSSStyles && (r = this._svgEngine.drawPolygon(t, e, n, i, a)), o.save(), this._applyCanvasClip(o, a), o.beginPath(), o.moveTo(t[0], e[0]), l = 1; l < s; l++) o.lineTo(t[l], e[l]);
            return o.closePath(), this._applyCanvasStyles(r, i, n, !0), o.restore(), r
          }
        }, n.prototype.drawPieSegment = function(t, e, n, i, a, r, l, o) {
          var s, p = this._canvas.getContext("2d"),
            h = i,
            c = i + a;
          return this._applyCSSStyles && (s = this._svgEngine.drawPieSegment(t, e, n, i, a, r, l, o)), p.save(), this._applyCanvasClip(p, o), p.beginPath(), p.moveTo(t, e), p.arc(t, e, n, h, c, !1), p.lineTo(t, e), this._applyCanvasStyles(s, l, r, !0), p.restore(), s
        }, n.prototype.drawDonutSegment = function(e, n, i, a, r, l, o, s, p) {
          var h, c, f, y = this._canvas.getContext("2d"),
            g = r,
            u = r + l;
          return this._applyCSSStyles && (h = this._svgEngine.drawDonutSegment(e, n, i, a, r, l, o, s, p)), c = new t.Point(e, n), c.x += a * Math.cos(g), c.y += a * Math.sin(g), f = new t.Point(e, n), f.x += a * Math.cos(u), f.y += a * Math.sin(u), y.save(), this._applyCanvasClip(y, p), y.beginPath(), y.moveTo(c.x, c.y), y.arc(e, n, i, g, u, !1), y.lineTo(f.x, f.y), y.arc(e, n, a, u, g, !0), this._applyCanvasStyles(h, s, o, !0), y.restore(), h
        }, n.prototype.drawString = function(t, e, n, i) {
          var a, r = this._canvas.getContext("2d");
          return this._applyCSSStyles && (a = this._svgEngine.drawString(t, e, n, i)), r.save(), r.textBaseline = "bottom", this._applyCanvasStyles(a, i, n, !0, !0), r.fillText(t, e.x, e.y), r.restore(), a
        }, n.prototype.drawStringRotated = function(t, e, n, i, a, r) {
          var l, o = this._canvas.getContext("2d");
          o.measureText(t);
          return this._applyCSSStyles && (l = this._svgEngine.drawStringRotated(t, e, n, i, a, r)), o.save(), o.textBaseline = "bottom", o.translate(n.x, n.y), o.rotate(Math.PI / 180 * i), o.translate(-n.x, -n.y), this._applyCanvasStyles(l, r, a, !0, !0), o.fillText(t, e.x, e.y), o.restore(), l
        }, n.prototype.measureString = function(e, n, i, a) {
          var r, l = l = this._canvas.getContext("2d");
          return this._applyCSSStyles ? this._svgEngine.measureString(e, n, i, a) : (this._applyCanvasStyles(null, null, n, !0, !0), r = l.measureText(e).width, new t.Size(r, 1.5 * parseInt(l.font)))
        }, n.prototype.startGroup = function(t, e, n) {
          void 0 === n && (n = !1);
          var i, a = this._canvas.getContext("2d");
          return this._applyCSSStyles && (i = this._svgEngine.startGroup(t, e, n)), a.save(), this._applyCanvasClip(a, e), i
        }, n.prototype.endGroup = function() {
          this._applyCSSStyles && this._svgEngine.endGroup(), this._canvas.getContext("2d").restore()
        }, n.prototype.drawImage = function(t, e, n, i, a) {
          var r, l = this._canvas.getContext("2d"),
            o = new Image;
          return this._applyCSSStyles && (r = this._svgEngine.drawImage(t, e, n, i, a)), o.onload = function() {
            l.drawImage(o, e, n, i, a)
          }, o.src = t, r
        }, n.prototype._getOpacityColor = function(e, n) {
          var i = new t.Color(e);
          return e.indexOf("url") > -1 ? this.fill : e.indexOf("-") > -1 ? (this.fill = e, e) : (null != n && 1 === i.a && (i.a = isNaN(n) ? 1 : Number(n)), i.toString())
        }, n.prototype._applyColor = function(e, n, i) {
          var r = a.tryParse(n),
            l = this._canvas.getContext("2d");
          if (null != r)
            if (t.isString(r) || null == i) l[e] = r;
            else {
              var o;
              if (null != r.x1) o = r.relative ? l.createLinearGradient(i.x + r.x1 * i.width, i.y + r.y1 * i.height, i.x + r.x2 * i.width, i.y + r.y2 * i.height) : l.createLinearGradient(r.x1, r.y1, r.x2, r.y2);
              else if (null != r.r)
                if (r.relative) {
                  var s = i.x + r.cx * i.width,
                    p = i.y + r.cy * i.height,
                    h = r.r * i.width,
                    c = r.r * i.height / h,
                    f = i.x + (null == r.fx ? r.cx : r.fx) * i.width,
                    y = i.y + (null == r.fy ? r.cy : r.fy) * i.height,
                    g = (null == r.fr ? 0 : r.fr) * i.width,
                    u = (null == r.fr ? 0 : r.fr) * i.height,
                    v = Math.min(g, u);
                  o = l.createRadialGradient(f, y / c, v, s, p / c, h), l.setTransform(1, 0, 0, c, 0, 0)
                } else o = l.createRadialGradient(null == r.fx ? r.cx : r.fx, null == r.fy ? r.cy : r.fy, r.fr || 0, r.cx, r.cy, r.r);
              r.colors && r.colors.length > 0 && null != o && r.colors.forEach(function(e) {
                var n = new t.Color("#000000");
                null != e.color && (n = e.color), null != e.opacity && (n.a = e.opacity), o.addColorStop(e.offset, n.toString())
              }), l[e] = o
            }
        }, n
      }();
      n._CanvasRenderEngine = i;
      var a = function() {
        function e() {}
        return e.tryParse = function(n) {
          if (e.parsedColor[n]) return e.parsedColor[n];
          if (null == n || -1 === n.indexOf("-")) return n;
          var i, a = n.replace(/\s+/g, "").split(/\-/g),
            r = a[0][0],
            l = !1,
            o = a[0].match(/\(\S+\)/)[0].replace(/[\(\\)]/g, "").split(/\,/g);
          "l" === r || "L" === r ? (i = {
            x1: "0",
            y1: "0",
            x2: "0",
            y2: "0",
            colors: []
          }, "l" === r && (l = !0), ["x1", "y1", "x2", "y2"].forEach(function(t, e) {
            null != o[e] && (i[t] = +o[e])
          })) : "r" !== r && "R" !== r || (i = {
            cx: "0",
            cy: "0",
            r: "0",
            colors: []
          }, "r" === r && (l = !0), ["cx", "cy", "r", "fx", "fy", "fr"].forEach(function(t, e) {
            null != o[e] && "" !== o[e] && (i[t] = +o[e])
          })), i.relative = l, e.parsedColor[n] = i;
          var s = a.length - 1;
          return a.forEach(function(e, n) {
            e.indexOf(")") > -1 && (e = e.match(/\)\S+/)[0].replace(")", ""));
            var a = e.split(":"),
              r = {
                color: new t.Color("#000000")
              };
            null != a[0] && (r.color = t.Color.fromString(a[0])), null != a[1] ? r.offset = +a[1] : r.offset = n / s, null != a[2] && (r.opacity = +a[2]), i.colors.push(r)
          }), i
        }, e.parsedColor = {}, e
      }();
      if (t.chart.FlexChartBase && t.chart.FlexChartBase.prototype && t.chart.FlexChartBase.prototype._exportToImage) {
        var r = t.chart.FlexChartBase.prototype._exportToImage;
        t.chart.FlexChartBase.prototype._exportToImage = function(e, n) {
          if ("svg" !== e) {
            var i, a, l = new t.chart.render._CanvasRenderEngine(this.hostElement, !0),
              o = this.rendered;
            this.rendered = new t.Event, this._render(l, !1), (a = l.element).parentNode.removeChild(a), i = a.toDataURL("image/" + e), n.call(null, i), a = null, l = null, this.rendered = o
          } else r.call(this, e, n)
        }
      }
    }(e.render || (e.render = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
