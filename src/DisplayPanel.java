import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

//Some JPanel and ImageIO code borrowed from the following sources:
//https://www.tutorialspoint.com/how-can-we-set-the-background-color-to-a-jpanel-in-java
//https://stackoverflow.com/questions/7314026/curious-why-my-image-isnt-showing-up

public class DisplayPanel extends JPanel {
	private JFrame frame = new JFrame();
	private int width = 960;
	private int height = 800;
	int imageCount = 11; //total number of images that are going to be on screen (5 philosophers, 5 chopsticks, and a table.)
	BufferedImage[] image = new BufferedImage[imageCount]; //all the sprites that need to be rendered
	
	/**
	 *  Initializes the JFrame
	 */
	DisplayPanel() {
		frame.setTitle("Dining Philosophers");
		setBackground(Color.gray);
		frame.add(this, BorderLayout.CENTER);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		populateFrame();
	}

	/**
	 *  Initializes the sprites in the image[] array, sorting them in a way that will make
	 *  sense visually.
	 */
	private void populateFrame() {
		image[0] = getImageSprite("src/Images/0.png");
		image[1] = getImageSprite("src/Images/4.png");
		image[2] = getImageSprite("src/Images/8.png");
		image[3] = getImageSprite("src/Images/20.png");
		image[4] = getImageSprite("src/Images/21.png");
		image[5] = getImageSprite("src/Images/22.png");
		image[6] = getImageSprite("src/Images/23.png");
		image[7] = getImageSprite("src/Images/24.png");
		image[8] = getImageSprite("src/Images/25.png");
		image[9] = getImageSprite("src/Images/12.png");
		image[10] = getImageSprite("src/Images/16.png");
		refresh();
	}
	
	/**
	 *  Initializes BufferedImage variables in order to call paintComponent()
	 */
	private void refresh() {
		int w = image[0].getWidth(null); //all sprites are the same size
		int h = image[0].getHeight(null);
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		paintComponent(g);
	}
	
	/**
	 * Changes the right sprite index to the correct sprite based off of the state and who wants it.
	 * @param myState The name of the state being switched to
	 * @param index The philosopher making the switch
	 */
	public void setState(String myState, int index)
	{
		int offset = 0;
		if(myState == "THINKING")
		{
			offset = 0;
		}
		else if(myState == "GRABBING")
		{
			offset = 1;
		}
		else if(myState == "EATING")
		{
			offset = 2;
		}
		else if(myState == "STARVING")
		{
			offset = 3;
		}
		
		//each philosopher has 4 sprites, meaning we have to skip over previous ones to get to one down the line.
		int newOffset = index*4 + offset;
		
		if(index < 3)
		{
			image[index] = getImageSprite("src/Images/" + newOffset + ".png");
		}
		else
		{
			// these are in front of the table, so must be rendered after the table and chopsticks, thus their placement in the array differs.
			image[index + 6] = getImageSprite("src/Images/" + newOffset + ".png");
		}
		
		refresh();
	}
	
	/**
	 * Sets a chopstick sprite to null to keep it from being rendered after refreshing.
	 * @param index The chopstick being hidden.
	 */
	public void hideChopstick(int index) {
		image[index+4] = null;
		refresh();
	}
	
	/**
	 * Restores a chopstick's visibility
	 * @param index The chopstick being shown.
	 */
	public void showChopstick(int index) {
		int spriteindex = index + 21; //chopstick sprites start on the 21st image
		image[index+4] = getImageSprite("src/Images/" + spriteindex + ".png");
		refresh();
	}

	/**
	 * Returns an image based off of a filepath.
	 * @param Path The image file location
	 * @return
	 */
	private BufferedImage getImageSprite(String Path) {
		BufferedImage img = null;
		try {
			File f = new File(Path);
			img = ImageIO.read(f);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * Paints the screen by rendering each sprite one after another. Sprites in image[] are
	 * ordered in a way to ensure the correct sorting.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < image.length; i++) {
			if(image[i] != null)
			{
				g.drawImage(image[i], 0, 30, null);
			}
		}
		repaint();
	}

}
