package cm.tp1ict207.lesdialog;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Bouton extends JButton{

	private String nom;
	
	public Bouton(String nom)
	{
		super(nom);
		this.nom = nom;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		try {
			Image img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\748f275a50637c43fd9fd78d33c9681c.jpg"));
			int a = this.getWidth() + 10;
			this.setPreferredSize(new Dimension(a, this.getHeight()));
			g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			
			if(nom.equals("Ajouter une photo") || nom.equals("Changer la photo")) {
				try {
					BufferedImage img2 = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\e28b2d7945b675a20d99103b96f18e64.jpg"));
					JLabel l = new JLabel(new ImageIcon(img2));
					g2d.drawImage(img2,  0,  -5,  this);
				}catch(IOException e) {
					e.printStackTrace();
				}
				g2d.drawString(this.nom, 90, (this.getHeight() / 2)+ 5);
			}else {
				g2d.drawString(this.nom, 15, (this.getHeight() / 2)+ 5);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
