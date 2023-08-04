package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {

	public final String PLAYER_ATLAS = "resources/player_sprites_test.png";
	public final String LEVEL_ATLAS = "resources/OutsideSpritesSonic2.png";
	public final String MENU_BUTTONS = "resources/button_atlas.png";
	public final String MENU_BACKGROUND = "resources/menu_background.png";
	public final String PAUSE_BACKGROUND = "resources/pause_menu.png";
	public final String SOUND_BUTTONS = "resources/sound_button.png";
	public final String URM_BUTTONS = "resources/urm_buttons.png";
	public final String VOLUME_BUTTONS = "resources/volume_buttons.png";
	public final String MENU_BACKGROUND_IMG = "resources/background_menu.png";
	public final String CRABMEAT_SPRITE = "resources/crabmeat-sprite.png";
	public final String STATUS_BAR = "resources/health_power_bar.png";
	public final String COMPLETED_IMG = "resources/completed_sprite.png";

	private Node head;

	public BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		try {
			InputStream is = LoadSave.class.getClassLoader().getResourceAsStream(fileName);
			if (is != null) {
				img = ImageIO.read(is);
				is.close();
			} else {
				throw new IllegalArgumentException("Resource not found: " + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private void bubbleSortFiles(File[] files) {
		int n = files.length;
		boolean swapped;
		for (int i = 0; i < n - 1; i++) {
			swapped = false;
			for (int j = 0; j < n - i - 1; j++) {
				if (Integer.parseInt(files[j].getName().replace(".png", "")) > Integer
						.parseInt(files[j + 1].getName().replace(".png", ""))) {
					// Intercambiar elementos
					File temp = files[j];
					files[j] = files[j + 1];
					files[j + 1] = temp;
					swapped = true;
				}
			}
			// Si no hubo intercambios en la iteración, entonces la matriz está ordenada
			if (!swapped)
				break;
		}
	}

	public void add(BufferedImage data) {
		Node newNode = new Node(data);
		if (head == null) {
			head = newNode;
		} else {
			Node current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = newNode;
		}
	}

	public int size() {
		int count = 0;
		Node current = head;
		while (current != null) {
			count++;
			current = current.next;
		}
		return count;
	}

	public BufferedImage get(int index) {
		int count = 0;
		Node current = head;
		while (current != null) {
			if (count == index) {
				return current.data;
			}
			count++;
			current = current.next;
		}
		throw new IndexOutOfBoundsException("Index out of range: " + index);
	}

	public BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/resources/lvls");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();
		bubbleSortFiles(files); // Llamada al Bubble Sort para ordenar los archivos

		for (int i = 0; i < files.length; i++) {
			try {
				BufferedImage img = ImageIO.read(files[i]);
				add(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BufferedImage[] imgs = new BufferedImage[size()];
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = get(i);
		}

		return imgs;
	}
}
