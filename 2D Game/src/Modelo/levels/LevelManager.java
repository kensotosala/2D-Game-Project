package levels; // Paquete que contiene la clase LevelManager

import java.awt.Graphics; // Importar la clase Graphics para dibujar en la pantalla
import java.awt.image.BufferedImage; // Importar la clase BufferedImage para manejar imágenes

import gamestates.Gamestate; // Importar la clase Gamestate del paquete gamestates
import main.Game; // Importar la clase Game del paquete main
import utilz.LoadSave; // Importar la clase LoadSave del paquete utilz

public class LevelManager { // Declarar la clase LevelManager

	private final int TILES_DEFAULT_SIZE = 32; // Tamaño predeterminado de los bloques
	private final float SCALE = 2f; // Escala para el tamaño de los bloques
	private final int TILES_IN_HEIGHT = 14; // Número de bloques en la altura del nivel
	private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE); // Tamaño final de los bloques después de la escala

	private Game game; // Instancia de la clase Game para acceder a la lógica del juego
	private BufferedImage[] levelSprite; // Arreglo de imágenes para representar los bloques del nivel
	private Node head; // Nodo principal de la LinkedList que contiene los niveles
	private int lvlIndex = 0; // Índice del nivel actual que se está cargando
	LoadSave loadSave = new LoadSave(); // Instancia de la clase LoadSave para cargar los recursos del juego

	public LevelManager(Game game) { // Constructor de la clase LevelManager
		this.game = game; // Asignar la instancia de Game proporcionada al atributo game
		importOutsideSprites(); // Cargar las imágenes de los bloques del nivel desde el archivo
		buildAllLevels(); // Construir todos los niveles y almacenarlos en la LinkedList
	}

	// Clase Node para implementar la LinkedList que almacena los niveles
	private class Node { // Declarar la clase Node
		Level data; // Datos del nivel que se almacenan en el nodo
		Node next; // Referencia al siguiente nodo en la LinkedList

		public Node(Level data) { // Constructor de la clase Node
			this.data = data; // Asignar el nivel proporcionado al atributo data
			next = null; // Inicializar la referencia al siguiente nodo como null
		}
	}

	public void loadNextLevel() { // Método para cargar el siguiente nivel
		lvlIndex++; // Incrementar el índice del nivel actual
		if (lvlIndex >= getAmountOfLevels()) { // Si el índice es mayor o igual al número total de niveles
			lvlIndex = 0; // Reiniciar el índice para volver al primer nivel
			System.out.println("No more levels! Game Completed!"); // Imprimir un mensaje indicando que se completó el juego
			Gamestate.state = Gamestate.MENU; // Cambiar el estado del juego a MENU (menú principal)
		}

		Level newLevel = getLevelAtIndex(lvlIndex); // Obtener el nivel actual desde la LinkedList
		game.getPlaying().getEnemyManager().loadEnemies(newLevel); // Cargar los enemigos del nivel en el EnemyManager
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData()); // Cargar los datos del nivel en el jugador
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset()); // Establecer el desplazamiento máximo del nivel
	}

	private void buildAllLevels() { // Método para construir todos los niveles
		BufferedImage[] allLevels = loadSave.GetAllLevels(); // Obtener todas las imágenes de los niveles desde el archivo
		head = null; // Inicializar la LinkedList con el nodo principal como null
		Node current = null; // Nodo auxiliar para recorrer la LinkedList

		for (int i = 0; i < allLevels.length; i++) { // Iterar a través de todas las imágenes de los niveles
			BufferedImage img = allLevels[i]; // Obtener la imagen del nivel actual
			Level newLevel = new Level(img); // Crear una instancia de la clase Level con la imagen
			Node newNode = new Node(newLevel); // Crear un nuevo nodo con el Level
			if (head == null) { // Si el nodo principal es null (LinkedList vacía)
				head = newNode; // Asignar el nuevo nodo como nodo principal
				current = head; // Establecer el nodo actual como el nodo principal
			} else { // Si la LinkedList ya contiene nodos
				current.next = newNode; // Establecer el siguiente nodo del nodo actual como el nuevo nodo
				current = current.next; // Mover el nodo actual al siguiente nodo recién agregado
			}
		}
	}

	private void importOutsideSprites() { // Método para cargar las imágenes de los bloques del nivel desde el archivo
		BufferedImage img = loadSave.GetSpriteAtlas(loadSave.LEVEL_ATLAS); // Obtener la imagen del archivo
		levelSprite = new BufferedImage[48]; // Crear un arreglo para almacenar las imágenes de los bloques

		for (int j = 0; j < 4; j++) // Iterar a través de las filas de bloques en la imagen
			for (int i = 0; i < 12; i++) { // Iterar a través de las columnas de bloques en la imagen
				int index = j * 12 + i; // Calcular el índice para almacenar la imagen en el arreglo
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32); // Recortar y almacenar la imagen en el arreglo
			}
	}

	public void draw(Graphics g, int lvlOffset) { // Método para dibujar los bloques del nivel en la pantalla
		for (int j = 0; j < TILES_IN_HEIGHT; j++) // Iterar a través de las filas de bloques en el nivel
			for (int i = 0; i < getCurrentLevel().getLevelData()[0].length; i++) { // Iterar a través de las columnas de bloques en el nivel
				int index = getCurrentLevel().getSpriteIndex(i, j); // Obtener el índice de la imagen del bloque
				g.drawImage(levelSprite[index], TILES_SIZE * i - lvlOffset, TILES_SIZE * j, TILES_SIZE, // Dibujar la imagen del bloque en la posición de la pantalla
						TILES_SIZE, null);
			}
	}

	public void update() { // Método para actualizar el LevelManager (no hace nada en esta implementación)

	}

	public Level getCurrentLevel() { // Método para obtener el nivel actual que se está cargando
		return getLevelAtIndex(lvlIndex); // Obtener el nivel desde la LinkedList usando el índice actual
	}

	public int getAmountOfLevels() { // Método para obtener el número total de niveles en la LinkedList
		int count = 0; // Variable para contar los nodos en la LinkedList
		Node current = head; // Nodo auxiliar para recorrer la LinkedList

		while (current != null) { // Mientras haya nodos en la LinkedList
			count++; // Incrementar el contador de niveles
			current = current.next; // Mover al siguiente nodo
		}
		return count; // Devolver el número total de niveles
	}

	private Level getLevelAtIndex(int index) { // Método para obtener el nivel en un índice específico de la LinkedList
		int count = 0; // Variable para contar los nodos en la LinkedList
		Node current = head; // Nodo auxiliar para recorrer la LinkedList

		while (current != null) { // Mientras haya nodos en la LinkedList
			if (count == index) { // Si el índice actual coincide con el índice deseado
				return current.data; // Devolver el nivel almacenado en el nodo actual
			}
			count++; // Incrementar el contador de niveles
			current = current.next; // Mover al siguiente nodo
		}

		// Si el índice está fuera del rango válido, lanzar una excepción
		throw new IndexOutOfBoundsException("Index out of range: " + index);
	}
}
