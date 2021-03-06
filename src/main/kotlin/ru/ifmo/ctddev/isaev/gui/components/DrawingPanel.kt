package ru.ifmo.ctddev.isaev.gui.components

import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.SwingUtilities

class DrawingPanel(w: Int, h: Int, count: Int) : CustomPanel(w, h, count), MouseMotionListener, MouseListener {

    init {

        addMouseMotionListener(this)
        addMouseListener(this)
    }

    override fun mouseDragged(e: MouseEvent) {
        paintSections(e)
    }

    override fun mouseMoved(e: MouseEvent) {}

    override fun mouseClicked(e: MouseEvent) {
        paintSections(e)
    }

    override fun mousePressed(e: MouseEvent) {}

    override fun mouseReleased(e: MouseEvent) {}

    override fun mouseEntered(e: MouseEvent) {}

    override fun mouseExited(e: MouseEvent) {}

    private fun paintSections(e: MouseEvent) {
        val sectionToModify = sections
                .mapIndexed { index, section -> Pair(index, section) }
                .filter { pair ->
                    val it = pair.second
                    e.x > it.x && e.x < it.x + it.width && e.y > it.y && e.y < it.y + it.height
                }.map { it.first }

        sectionToModify.forEach { i ->
            if (SwingUtilities.isLeftMouseButton(e)) {
                setActive(i, true)
            } else if (SwingUtilities.isRightMouseButton(e)) {
                setActive(i, false)
            }
        }

        repaint()
    }

    private fun setActive(i: Int, isActive: Boolean) {
        val x = i / count
        val y = i % count
        sections[i].isActive = isActive
        sections[sec(x - 1, y)].isActive = isActive
        sections[sec(x + 1, y)].isActive = isActive
        sections[sec(x, y - 1)].isActive = isActive
        sections[sec(x, y + 1)].isActive = isActive
    }

    private fun sec(x: Int, y: Int): Int {
        return count * x + y
    }
}
