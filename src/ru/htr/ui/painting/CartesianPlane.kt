package ru.htr.ui.painting

import kotlin.math.max

class CartesianPlane(
    xMin : Double,
    xMax : Double,
    yMin : Double,
    yMax : Double
) {
    private var xSize: Int = 1
    private var ySize: Int = 1
    var width: Int
        get() = xSize - 1
        set(value){
            xSize = max(1,value)
        }
    var height: Int
        get() = ySize - 1
        set(value){
            ySize = max(1,value)
        }
    var xMin: Double = 0.0
        private set
    var xMax: Double = 0.0
        private set
    var yMin: Double = 0.0
        private set
    var yMax: Double = 0.0
        private set

    var xSegment : Pair<Double, Double>
        get() = Pair(xMin,xMax)
        set(value){
            val k = if (value.first == value.second) 0.1 else 0.0
            xMin = value.first - k
            xMax = value.second + k
            if (xMin > xMax) xMin = xMax.also { xMax = xMin }
        }
    var ySegment : Pair<Double, Double>
        get() = Pair(yMin,yMax)
        set(value){
            val k = if (value.first == value.second) 0.1 else 0.0
            yMin = value.first - k
            yMax = value.second + k
            if (yMin > yMax) yMin = yMax.also { yMax = yMin }
        }

    val xDen: Double
        get() = width / (xMax - xMin)

    val yDen: Double
        get() = height / (yMax - yMin)

    init {
        xSegment = Pair(xMin, xMax)
        ySegment = Pair(yMin, yMax)
    }

    fun xCrt2Scr(x: Double): Int {
        var r = (xDen * (x - xMin)).toInt()
        if (r < -width) r = -width
        if (r > 2 * width) r = 2 * width
        return r
    }

    fun xScr2Crt(x: Int): Double {
        var _x = x
        if (_x < -width) _x = -width
        if (_x > 2 * width) _x = 2 * width
        return _x / xDen + xMin
    }

    fun yCrt2Scr(y: Double): Int {
        var r = (yDen * (yMax - y)).toInt()
        if (r < -height) r = -height
        if (r > 2 * height) r = 2 * height
        return r
    }

    fun yScr2Crt(y: Int): Double {
        var _y = y
        if (_y < -height) _y = -height
        if (_y > 2 * height) _y = 2 * height
        return yMax - _y / yDen
    }
}