package utilz;

import java.awt.image.BufferedImage;

public class Node {
    BufferedImage data;
    Node next;

    public Node(BufferedImage data) {
        this.data = data;
        this.next = null;
    }

}
