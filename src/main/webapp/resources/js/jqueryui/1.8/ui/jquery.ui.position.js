/*
 * jQuery UI Position 1.8.17
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Position
 */
(function (a, b) {
    a.ui = a.ui || {};
    var c = /left|center|right/, d = /top|center|bottom/, e = "center", f = {}, g = a.fn.position, h = a.fn.offset;
    a.fn.position = function (b) {
        if (!b || !b.of)return g.apply(this, arguments);
        b = a.extend({}, b);
        var h = a(b.of), i = h[0], j = (b.collision || "flip").split(" "), k = b.offset ? b.offset.split(" ") : [0, 0], l, m, n;
        i.nodeType === 9 ? (l = h.width(), m = h.height(), n = {top:0, left:0}) : i.setTimeout ? (l = h.width(), m = h.height(), n = {top:h.scrollTop(), left:h.scrollLeft()}) : i.preventDefault ? (b.at = "left top", l = m = 0, n = {top:b.of.pageY, left:b.of.pageX}) : (l = h.outerWidth(), m = h.outerHeight(), n = h.offset()), a.each(["my", "at"], function () {
            var a = (b[this] || "").split(" ");
            a.length === 1 && (a = c.test(a[0]) ? a.concat([e]) : d.test(a[0]) ? [e].concat(a) : [e, e]), a[0] = c.test(a[0]) ? a[0] : e, a[1] = d.test(a[1]) ? a[1] : e, b[this] = a
        }), j.length === 1 && (j[1] = j[0]), k[0] = parseInt(k[0], 10) || 0, k.length === 1 && (k[1] = k[0]), k[1] = parseInt(k[1], 10) || 0, b.at[0] === "right" ? n.left += l : b.at[0] === e && (n.left += l / 2), b.at[1] === "bottom" ? n.top += m : b.at[1] === e && (n.top += m / 2), n.left += k[0], n.top += k[1];
        return this.each(function () {
            var c = a(this), d = c.outerWidth(), g = c.outerHeight(), h = parseInt(a.curCSS(this, "marginLeft", !0)) || 0, i = parseInt(a.curCSS(this, "marginTop", !0)) || 0, o = d + h + (parseInt(a.curCSS(this, "marginRight", !0)) || 0), p = g + i + (parseInt(a.curCSS(this, "marginBottom", !0)) || 0), q = a.extend({}, n), r;
            b.my[0] === "right" ? q.left -= d : b.my[0] === e && (q.left -= d / 2), b.my[1] === "bottom" ? q.top -= g : b.my[1] === e && (q.top -= g / 2), f.fractions || (q.left = Math.round(q.left), q.top = Math.round(q.top)), r = {left:q.left - h, top:q.top - i}, a.each(["left", "top"], function (c, e) {
                a.ui.position[j[c]] && a.ui.position[j[c]][e](q, {targetWidth:l, targetHeight:m, elemWidth:d, elemHeight:g, collisionPosition:r, collisionWidth:o, collisionHeight:p, offset:k, my:b.my, at:b.at})
            }), a.fn.bgiframe && c.bgiframe(), c.offset(a.extend(q, {using:b.using}))
        })
    }, a.ui.position = {fit:{left:function (b, c) {
        var d = a(window), e = c.collisionPosition.left + c.collisionWidth - d.width() - d.scrollLeft();
        b.left = e > 0 ? b.left - e : Math.max(b.left - c.collisionPosition.left, b.left)
    }, top:function (b, c) {
        var d = a(window), e = c.collisionPosition.top + c.collisionHeight - d.height() - d.scrollTop();
        b.top = e > 0 ? b.top - e : Math.max(b.top - c.collisionPosition.top, b.top)
    }}, flip:{left:function (b, c) {
        if (c.at[0] !== e) {
            var d = a(window), f = c.collisionPosition.left + c.collisionWidth - d.width() - d.scrollLeft(), g = c.my[0] === "left" ? -c.elemWidth : c.my[0] === "right" ? c.elemWidth : 0, h = c.at[0] === "left" ? c.targetWidth : -c.targetWidth, i = -2 * c.offset[0];
            b.left += c.collisionPosition.left < 0 ? g + h + i : f > 0 ? g + h + i : 0
        }
    }, top:function (b, c) {
        if (c.at[1] !== e) {
            var d = a(window), f = c.collisionPosition.top + c.collisionHeight - d.height() - d.scrollTop(), g = c.my[1] === "top" ? -c.elemHeight : c.my[1] === "bottom" ? c.elemHeight : 0, h = c.at[1] === "top" ? c.targetHeight : -c.targetHeight, i = -2 * c.offset[1];
            b.top += c.collisionPosition.top < 0 ? g + h + i : f > 0 ? g + h + i : 0
        }
    }}}, a.offset.setOffset || (a.offset.setOffset = function (b, c) {
        /static/.test(a.curCSS(b, "position")) && (b.style.position = "relative");
        var d = a(b), e = d.offset(), f = parseInt(a.curCSS(b, "top", !0), 10) || 0, g = parseInt(a.curCSS(b, "left", !0), 10) || 0, h = {top:c.top - e.top + f, left:c.left - e.left + g};
        "using"in c ? c.using.call(b, h) : d.css(h)
    }, a.fn.offset = function (b) {
        var c = this[0];
        if (!c || !c.ownerDocument)return null;
        if (b)return this.each(function () {
            a.offset.setOffset(this, b)
        });
        return h.call(this)
    }), function () {
        var b = document.getElementsByTagName("body")[0], c = document.createElement("div"), d, e, g, h, i;
        d = document.createElement(b ? "div" : "body"), g = {visibility:"hidden", width:0, height:0, border:0, margin:0, background:"none"}, b && jQuery.extend(g, {position:"absolute", left:"-1000px", top:"-1000px"});
        for (var j in g)d.style[j] = g[j];
        d.appendChild(c), e = b || document.documentElement, e.insertBefore(d, e.firstChild), c.style.cssText = "position: absolute; left: 10.7432222px; top: 10.432325px; height: 30px; width: 201px;", h = a(c).offset(
            function (a, b) {
                return b
            }).offset(), d.innerHTML = "", e.removeChild(d), i = h.top + h.left + (b ? 2e3 : 0), f.fractions = i > 21 && i < 22
    }()
})(jQuery)