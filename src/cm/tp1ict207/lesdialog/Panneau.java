package cm.tp1ict207.lesdialog;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panneau extends JPanel{

	private BufferedImage img;
	
	public Panneau(String a)
	{
		super();
		try {
			img = ImageIO.read(new File(a));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Panneau()
	{
		super();
		try {
			img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\e690de33825e39bc6247ce171deb54b7.jpg"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(img,  0,  0,  this.getWidth(), this.getHeight(), this);
		
		
	}
	

}
