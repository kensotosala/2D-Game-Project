/**
 * Clase que maneja los eventos del mouse en el panel de juego.
 */
package Modelo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Vista.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    /**
     * Constructor de la clase MouseInputs.
     * 
     * @param gamePanel El panel de juego al que se asociará esta clase.
     */
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Método invocado cuando el mouse se arrastra por el panel de juego.
     * No se realiza ninguna acción en este método.
     * 
     * @param e El evento del mouse.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // No se implementa ninguna acción en este método.
    }

    /**
     * Método invocado cuando el mouse se mueve sobre el panel de juego.
     * Actualiza la posición del rectángulo en el panel de juego.
     * 
     * @param e El evento del mouse.
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Método invocado cuando se hace clic en el panel de juego.
     * Imprime un mensaje en la consola.
     * 
     * @param e El evento del mouse.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked!");
        // Se imprime un mensaje en la consola cuando se hace clic en el mouse.
    }

    /**
     * Método invocado cuando se presiona un botón del mouse en el panel de juego.
     * No se realiza ninguna acción en este método.
     * 
     * @param e El evento del mouse.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // No se implementa ninguna acción en este método.
    }

    /**
     * Método invocado cuando se suelta un botón del mouse en el panel de juego.
     * No se realiza ninguna acción en este método.
     * 
     * @param e El evento del mouse.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No se implementa ninguna acción en este método.
    }

    /**
     * Método invocado cuando el mouse entra en el área del panel de juego.
     * No se realiza ninguna acción en este método.
     * 
     * @param e El evento del mouse.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // No se implementa ninguna acción en este método.
    }

    /**
     * Método invocado cuando el mouse sale del área del panel de juego.
     * No se realiza ninguna acción en este método.
     * 
     * @param e El evento del mouse.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // No se implementa ninguna acción en este método.
    }
}
