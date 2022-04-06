package ru.htr.ui.painting

import java.awt.*

class PointPainter(private val plane: CartesianPlane) : Painter {
    var pointColor: Color = Color.BLACK

    var points = mutableMapOf<Double, Double>()
    override fun paint(g: Graphics) {
        with(g as Graphics2D) {
            color = pointColor
            stroke = BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)
            with(plane) {
                for (i in 0..points.size - 1) {
                    fillOval(
                        xCrt2Scr(points.keys.elementAt(i)) - 3,
                        plane.yCrt2Scr(points.values.elementAt(i)) - 3,
                        6,
                        6
                    )
                }
            }
        }
    }
}