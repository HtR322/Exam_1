package ru.htr.ui

import ru.htr.ui.painting.CartesianPainter
import ru.htr.ui.painting.CartesianPlane
import ru.htr.ui.painting.PointPainter
import ru.htr.ui.painting.VectPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.GroupLayout
import javax.swing.JFrame

class MainFrame : JFrame() {
    private val minDim = Dimension(600, 700)
    private val mainPanel: GraphicsPanel

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = minDim

        val plane = CartesianPlane(
            -10.0,
            10.0,
            -10.0,
            10.0
        )

        val cartesianPainter = CartesianPainter(plane)
        val pointPainter = PointPainter(plane)
        val vectPainter = VectPainter(plane)

        val painters = mutableListOf(cartesianPainter, pointPainter, vectPainter)

        mainPanel = GraphicsPanel(painters).apply {
            background = Color.WHITE
        }

        mainPanel.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                plane.width = mainPanel.width
                plane.height = mainPanel.height
                mainPanel.repaint()
            }
        })

        mainPanel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.point != null) {
                    if (e.button == 1) {
                        pointPainter.points.put(plane.xScr2Crt(e.point.x), plane.yScr2Crt(e.point.y))
                        vectPainter.vectors.put(plane.xScr2Crt(e.point.x), plane.yScr2Crt(e.point.y))
                        mainPanel.repaint()
                    }
                    if (e.button == 3) {
                        if (painters.size != 1) {
                            for (i in 0 until pointPainter.points.size) {
                                if ((plane.xScr2Crt(e.point.x) + 0.1 > pointPainter.points.keys.elementAt(i) && plane.xScr2Crt(
                                        e.point.x
                                    ) - 0.1 < pointPainter.points.keys.elementAt(i))
                                    && (plane.yScr2Crt(e.point.y) + 0.1 > pointPainter.points.values.elementAt(i) && plane.yScr2Crt(
                                        e.point.y
                                    ) - 0.1 < pointPainter.points.values.elementAt(i))
                                ) {
                                    if (pointPainter.points.size == 1) {
                                        painters.remove(pointPainter)
                                    }
                                    pointPainter.points.remove(pointPainter.points.keys.elementAt(i))
                                    vectPainter.vectors.remove(vectPainter.vectors.keys.elementAt(i))
                                    break
                                }
                            }
                        }
                        mainPanel.repaint()
                    }
                }
            }
        })

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(
                        mainPanel,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE
                    )
                    .addGap(4)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(
                        mainPanel,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE
                    )
                    .addGap(4)
            )
        }
        pack()
        plane.width = mainPanel.width
        plane.height = mainPanel.height
    }
}