package ru.htr.ui.painting

import java.awt.*

class CartesianPainter(private val plane: CartesianPlane) : Painter
{
    var fontSize: Int = 16
    var axesColor: Color = Color.BLACK

    override fun paint(g: Graphics) {
        paintAxes(g)
        paintTicks(g)
        paintLabels(g)
    }

    private fun paintAxes(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                color = axesColor
                if (xMin <= 0 && xMax >= 0) {
                    drawLine(xCrt2Scr(0.0), 0, xCrt2Scr(0.0), height)
                } else {
                    drawLine(0, 0, 0, height)
                    drawLine(width, 0, width, height)
                }
                if (yMin <= 0 && yMax >= 0) {
                    drawLine(0, yCrt2Scr(0.0), width, yCrt2Scr(0.0))

                } else {
                    drawLine(0, height, width, height)
                    drawLine(0, 0, width, 0)
                }
            }
        }
    }

    private fun paintTicks(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                val dt = 3
                color = Color.BLACK
                stroke = BasicStroke(1.5F)
                var x0 = xCrt2Scr(0.0)
                var y0 = yCrt2Scr(0.0)

                if (x0 <= 0) x0 = 0
                if (x0 > width) x0 = width
                if (y0 <= 0) y0 = 0
                if (y0 > height) y0 = height

                val kX = when {
                    xMax - xMin < 10 -> {
                        10.0
                    }
                    xMax - xMin < 24 -> {
                        5.0
                    }
                    xMax - xMin < 36 -> {
                        2.5
                    }
                    else -> {
                        1.25
                    }
                }
                for (i in (xMin * kX).toInt() until (xMax * kX).toInt()) {
                    val gap = if (i % 10 == 0) 2 else if (i % 5 == 0) 1 else 0
                    val x = xCrt2Scr((i / kX))
                    drawLine(x, y0 - dt - gap, x, y0 + dt + gap)
                }
                val kY = when {
                    yMax - yMin < 10 -> {
                        10.0
                    }
                    yMax - yMin < 24 -> {
                        5.0
                    }
                    yMax - yMin < 36 -> {
                        2.5
                    }
                    else -> {
                        1.25
                    }
                }
                for (i in (yMin * kY).toInt() until (yMax * kY).toInt()) {
                    val gap = if (i % 10 == 0) 2 else if (i % 5 == 0) 1 else 0
                    val y = yCrt2Scr((i / kY))
                    drawLine(x0 - dt - gap, y, x0 + dt + gap, y)
                }
            }
        }
    }

    private fun paintLabels(g: Graphics) {
        with(g as Graphics2D) {
            with(plane) {
                font = Font("Cambria", Font.BOLD, 14)
                val m = fontMetrics

                var x0 = xCrt2Scr(0.0)
                var y0 = yCrt2Scr(0.0)

                if (x0 <= 0) x0 = 0
                if (x0 > width) x0 = width
                if (y0 <= 0) y0 = 0
                if (y0 > height) y0 = height

                color = Color.RED

                val kX = when {
                    xMax - xMin < 10 -> {
                        10.0
                    }
                    xMax - xMin < 24 -> {
                        5.0
                    }
                    xMax - xMin < 36 -> {
                        2.5
                    }
                    else -> {
                        1.25
                    }
                }

                val kY = when {
                    yMax - yMin < 10 -> {
                        10.0
                    }
                    yMax - yMin < 24 -> {
                        5.0
                    }
                    yMax - yMin < 36 -> {
                        2.5
                    }
                    else -> {
                        1.25
                    }
                }

                for (i in (xMin * kX).toInt() until (xMax * kX).toInt()) {
                    if (i % 5 != 0 || i == 0) continue
                    val x = xCrt2Scr(i / kX)
                    val dy = m.getStringBounds((i / kX).toString(), g).height.toInt()
                    val dx = m.getStringBounds((i / kX).toString(), g).width.toInt()
                    val dt: Int = if (y0 > height - dy) dy + 5 else 0
                    drawString((i / kX).toString(), x - dx / 2, y0 + dy - dt)
                }

                for (i in (yMin * kY).toInt() until (yMax * kY).toInt()) {
                    if (i % 5 != 0 || i == 0) continue
                    val y = yCrt2Scr(i / kY)
                    val dy = m.getStringBounds((i / kY).toString(), g).height.toInt()
                    val dx = m.getStringBounds((i / kY).toString(), g).width.toInt()
                    val dt: Int = if (x0 > width - dx) 10 + dx else 0
                    drawString((i / kY).toString(), x0 + 4 - dt, y + dy / 3)
                }
            }
        }
    }
}