package ru.htr.ui.painting

import java.awt.*

class VectPainter(private val plane: CartesianPlane) : Painter {
    var vectColor: Color = Color.BLACK

    var vectors = mutableMapOf<Double, Double>()

    override fun paint(g: Graphics) {
        with(g as Graphics2D) {
            color = Color.BLACK
            stroke = BasicStroke(2.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)
            with(plane) {
                for (i in 1..vectors.size - 1) {
                    drawLine(
                        xCrt2Scr(vectors.keys.elementAt(i-1)),
                        yCrt2Scr(vectors.values.elementAt(i-1)),
                        xCrt2Scr(vectors.keys.elementAt(i)),
                        yCrt2Scr(vectors.values.elementAt(i))
                    )
                }
            }
        }
    }

}