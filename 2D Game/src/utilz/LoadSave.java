package utilz;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.Game;

public class LoadSave {
    private static final String LEVEL_1_PATH = "src/resources/Levels/level_1.tmx";
    private static final int COLLISION_TILE_ID = 1; // Define the collision tile ID

    public static int[][] getLevelData() {
        // Load and extract the level data from the Tiled map
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(LEVEL_1_PATH));

            doc.getDocumentElement().normalize();
            NodeList layerList = doc.getElementsByTagName("layer");

            int mainLayerIndex = -1;
            int obstaclesLayerIndex = -1;

            // Find the indexes of the "Main" and "Obstacles" layers
            for (int i = 0; i < layerList.getLength(); i++) {
                Element layerElement = (Element) layerList.item(i);
                String layerName = layerElement.getAttribute("name");

                if (layerName.equals("Main")) {
                    mainLayerIndex = i;
                } else if (layerName.equals("Obstacles")) {
                    obstaclesLayerIndex = i;
                }
            }

            if (mainLayerIndex == -1) {
                throw new IllegalStateException("The 'Main' layer is not found in the XML document.");
            }

            if (obstaclesLayerIndex == -1) {
                throw new IllegalStateException("The 'Obstacles' layer is not found in the XML document.");
            }

            // Assuming the level data is stored in the "Main" tile layer
            Element mainLayerElement = (Element) layerList.item(mainLayerIndex);
            Element mainDataElement = (Element) mainLayerElement.getElementsByTagName("data").item(0);
            String mainDataString = mainDataElement.getTextContent().trim();

            String[] mainRows = mainDataString.split("\n");
            int numRows = mainRows.length;
            int numCols = mainRows[0].split(",").length;

            int[][] levelData = new int[numRows][numCols];

            for (int row = 0; row < numRows; row++) {
                String[] columns = mainRows[row].split(",");
                for (int col = 0; col < numCols; col++) {
                    levelData[row][col] = Integer.parseInt(columns[col].trim());
                }
            }

            // Process the "Obstacles" object layer for collision data
            Element obstaclesLayerElement = (Element) layerList.item(obstaclesLayerIndex);
            NodeList objectList = obstaclesLayerElement.getElementsByTagName("object");

            if (objectList != null && objectList.getLength() > 0) {
                for (int i = 0; i < objectList.getLength(); i++) {
                    Element objectElement = (Element) objectList.item(i);
                    int x = Math.round(Float.parseFloat(objectElement.getAttribute("x")));
                    int y = Math.round(Float.parseFloat(objectElement.getAttribute("y")));
                    int width = Math.round(Float.parseFloat(objectElement.getAttribute("width")));
                    int height = Math.round(Float.parseFloat(objectElement.getAttribute("height")));

                    // Mark the corresponding tiles in the level data as collision tiles
                    int startTileX = x / Game.TILES_SIZE;
                    int startTileY = y / Game.TILES_SIZE;
                    int endTileX = (x + width) / Game.TILES_SIZE;
                    int endTileY = (y + height) / Game.TILES_SIZE;

                    for (int row = startTileY; row <= endTileY; row++) {
                        for (int col = startTileX; col <= endTileX; col++) {
                            if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
                                levelData[row][col] = COLLISION_TILE_ID;
                            }
                        }
                    }
                }
            }

            return levelData;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null; // Handle error case appropriately
    }
}
