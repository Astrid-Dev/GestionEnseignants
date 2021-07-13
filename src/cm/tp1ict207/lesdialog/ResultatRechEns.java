package cm.tp1ict207.lesdialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultatRechEns extends JDialog{

	private JLabel[] chaine ;
	private Panneau pan = new Panneau();
	
	public ResultatRechEns(JFrame parent, String title, boolean modal, String chai, String che)
	{
		super(parent, title, modal);
		
		String[] tab = chai.split("\n");
		chaine = new JLabel[tab.length];
		for(int i = 0; i < tab.length; i++)
		{
			chaine[i] = new JLabel(tab[i]);
		}
		pan = new Panneau(che);
		this.setSize(430, 500);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent();
		this.setVisible(true);
	}
	
	private void initComponent()
	{
		this.setLayout(new BorderLayout());
		JPanel panTitre = new JPanel();
		panTitre.setPreferredSize(new Dimension(430,  50));
		panTitre.setBackground(new Color(255, 127, 39));
		JLabel titre = new JLabel("Resultat de la recherche");
		titre.setForeground(Color.BLUE);
		titre.setPreferredSize(new Dimension(250,  40));
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setFont(new Font("Arial",  Font.ITALIC,  16));
		try {
			BufferedImage img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\cdc45729aa5e951bbd2075536727178e.jpg"));
			JLabel l = new JLabel(new ImageIcon(img));
			panTitre.add(l);
		}catch(IOException e) {
			e.printStackTrace();
		}
		panTitre.add(titre);
		JPanel panC = new JPanel();
		panC.setPreferredSize(new Dimension(400, 350));
		for(int i = 0; i < chaine.length; i++)
		{
			chaine[i].setPreferredSize(new Dimension(400,  20));
			chaine[0].setFont(new Font("Times New Roman",  Font.BOLD,  14));
			chaine[0].setHorizontalAlignment(JLabel.CENTER);
			chaine[0].setForeground(Color.black);
			panC.add(chaine[i]);
		}
		pan.setPreferredSize(new Dimension(150,  150));
		panC.add(pan);
		this.getContentPane().add(panTitre, BorderLayout.NORTH);
		
		this.getContentPane().add(panC, BorderLayout.CENTER);
		
	}
}
