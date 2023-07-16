package Modelo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Vista.GamePanel;

public class MouseInputs extends MouseAdapter {
    private GamePanel gamePanel;
    private Player player;

    public MouseInputs(GamePanel gamePanel, Player player) {
        this.gamePanel = gamePanel;
        this.player = player;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse input
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Perform actions based on the mouse input
        // For example, you can check if a button was clicked
        // or perform specific actions based on the mouse coordinates

        // Example: Check if the mouse click is within a certain area
        if (mouseX > 100 && mouseX < 200 && mouseY > 200 && mouseY < 300) {
            // Perform an action for this specific area
        }

        // Example: Perform an action when a mouse button is pressed
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) {
            // Perform an action for the left mouse button
        } else if (button == MouseEvent.BUTTON2) {
            // Perform an action for the middle mouse button
        } else if (button == MouseEvent.BUTTON3) {
            // Perform an action for the right mouse button
        }
    }
}
