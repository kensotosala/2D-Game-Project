package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.LoadSave;
import utilz.PauseButtons;
import utilz.URMButtons;
import utilz.VolumeButtons;

public class PauseOverlay {

	private URMButtons urmButtons = new URMButtons();
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX;
	private int bgY;
	private int bgW;
	private int bgH;
	private SoundButton musicButton;
	private SoundButton sfxButton;
	private UrmButton menuB;
	private UrmButton replayB;
	private UrmButton unpauseB;
	private LoadSave loadSave = new LoadSave();
	private VolumeButtons volumeButtonObject = new VolumeButtons();
	private VolumeButton volumeButton;
	private PauseButtons pauseButton = new PauseButtons();
	private final int TILES_DEFAULT_SIZE = 32;
	private final float SCALE = 2f;
	private final int TILES_IN_WIDTH = 26;
	private final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	private final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();

		createSoundButtons();
		createUrmButtons();
		createVolumeButton();
	}

	private void createVolumeButton() {
		int vX = (int) (309 * SCALE);
		int vY = (int) (278 * SCALE);
		volumeButton = new VolumeButton(vX, vY, volumeButtonObject.SLIDER_WIDTH, volumeButtonObject.VOLUME_HEIGHT,
				volumeButtonObject);
	}

	private void createUrmButtons() {
		int menuX = (int) (313 * SCALE);
		int replayX = (int) (387 * SCALE);
		int unpauseX = (int) (462 * SCALE);
		int bY = (int) (325 * SCALE);

		menuB = new UrmButton(menuX, bY, urmButtons.URM_SIZE, urmButtons.URM_SIZE, 2);
		replayB = new UrmButton(replayX, bY, urmButtons.URM_SIZE, urmButtons.URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, bY, urmButtons.URM_SIZE, urmButtons.URM_SIZE, 0);
	}

	private void createSoundButtons() {
		int soundX = (int) (450 * SCALE);
		int musicY = (int) (140 * SCALE);
		int sfxY = (int) (186 * SCALE);
		musicButton = new SoundButton(soundX, musicY, pauseButton.SOUND_SIZE, pauseButton.SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, pauseButton.SOUND_SIZE, pauseButton.SOUND_SIZE);
	}

	private void loadBackground() {
		backgroundImg = loadSave.GetSpriteAtlas(loadSave.PAUSE_BACKGROUND);
		bgW = (int) (backgroundImg.getWidth() * SCALE);
		bgH = (int) (backgroundImg.getHeight() * SCALE);
		bgX = GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (25 * SCALE);
	}

	public void update() {
		musicButton.update();
		sfxButton.update();

		menuB.update();
		replayB.update();
		unpauseB.update();

		volumeButton.update();
	}

	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);

		// UrmButtons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);

		// Volume Button
		volumeButton.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if (isIn(e, menuB))
			menuB.setMousePressed(true);
		else if (isIn(e, replayB))
			replayB.setMousePressed(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed())
				musicButton.setMuted(!musicButton.isMuted());

		} else if (isIn(e, sfxButton)) {
			if (sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
		} else if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
			}
		} else if (isIn(e, replayB)) {
			if (replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}
		} else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed())
				playing.unpauseGame();
		}

		musicButton.resetBools();
		sfxButton.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		volumeButton.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}