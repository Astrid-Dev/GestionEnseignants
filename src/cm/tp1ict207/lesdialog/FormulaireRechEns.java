package cm.tp1ict207.lesdialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class FormulaireRechEns extends JDialog{

	private String valMat;
	private JTextField jtf = new JTextField();
	
	private Bouton okButton = new Bouton("Rechercher");
	private Bouton cancelButton = new Bouton("Annuler");
	
	Panneau pan = new Panneau();
	
	public FormulaireRechEns(JFrame parent, String title, boolean modal)
	{
		super(parent, title, true);
		this.setSize(360,  200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent();
		this.setVisible(true);
	}
	
	private void initComponent()
	{
		this.setLayout(new BorderLayout());
		JPanel panTitre = new JPanel();
		panTitre.setPreferredSize(new Dimension(300,  50));
		panTitre.setBackground(new Color(153, 217, 234));
		JLabel titre = new JLabel("Recherche d'un Enseignant");
		titre.setForeground(Color.BLUE);
		titre.setPreferredSize(new Dimension(300,  40));
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setFont(new Font("Arial",  Font.ITALIC,  16));
		panTitre.add(titre);
		panTitre.setBackground(new Color(181, 230, 9));
		JLabel demandeMatricule = new JLabel("Saisir le matricule de l'enseignant concerné :");
		demandeMatricule.setPreferredSize(new Dimension(300,  30));
		demandeMatricule.setHorizontalAlignment(JLabel.CENTER);
		try {
			MaskFormatter cod = new MaskFormatter("##U####");
			jtf = new JFormattedTextField(cod);
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedImage img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\da789992235a310d99890065e31da356.jpg"));
			JLabel l = new JLabel(new ImageIcon(img));
			this.getContentPane().add(l, BorderLayout.WEST);
		}catch(IOException e) {
			e.printStackTrace();
		}
		jtf.setPreferredSize(new Dimension(80,  30));
		jtf.setHorizontalAlignment(JTextField.CENTER);
		jtf.setForeground(Color.BLUE);
		jtf.setFont(new Font("Arial", Font.BOLD, 13));
		
		JPanel panText = new JPanel();
		panText.setPreferredSize(new Dimension(300,  80));
		panText.add(demandeMatricule);
		panText.add(jtf);
		panText.setBackground(new Color(153, 217, 234));
		JPanel panBout = new JPanel();
		panBout.setPreferredSize(new Dimension(280,  35));
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(40,  30));
		p.setBackground(new Color(153, 217, 234));
		panBout.add(p);
		panBout.add(cancelButton);
		panBout.add(okButton);
		panBout.setBackground(new Color(153, 217, 234));
		
		this.getContentPane().add(panTitre, BorderLayout.NORTH);
		this.getContentPane().add(panText, BorderLayout.CENTER);
		this.getContentPane().add(panBout, BorderLayout.SOUTH);
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String str = "";
				valMat = jtf.getText();
				try {
					String url = "jdbc:mysql://localhost:3306/tpict207";
					String user = "root";
					String pass = "";
					Connection conn = DriverManager.getConnection(url, user, pass);
					
					Statement state = conn.createStatement();
					ResultSet res = state.executeQuery("SELECT matricule FROM Enseignant");
					boolean existe = false;
					while(res.next()) {
						if(res.getString("matricule").equals(valMat))
							existe = true; 		
					}
					
					if(!existe) {
						if(valMat.equals("       "))
						{
							JOptionPane jop = new JOptionPane();
							jop.showMessageDialog(null,  "Veuillez renseigner un matricule\npour la recherche !",  "Champ 'matriucle' non renseigné",  JOptionPane.WARNING_MESSAGE);
						}
						else {
							str = "Aucune information disponible !\nIl se peut que cet enseignant n'a pas été enregistré.";
							JOptionPane jop2 = new JOptionPane();
							jop2.showMessageDialog(null, str,  "Resultat de la recherche",  JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						String phot = "";
						ResultSet res2 = state.executeQuery("SELECT * FROM Enseignant WHERE matricule = '" + valMat + "';");
						while(res2.next()) {
							str += "Informations de l'enseignant : \n";
							str += "Nom : " + res2.getString(1);
							str += "\nPrenom : " + res2.getString(2);
							str += "\nMatricule : " + res2.getString(3); 
							str += "\nAge : " + res2.getInt(4) + " ans"; 
							str += "\nSexe : " + res2.getString(5); 
							str += "\nContact  : " + res2.getString(6); 
							str += "\nMatière : " + res2.getString(7);
							str += "\nLien Photo : " + res2.getString(8).replace('/',  '\\');
							phot = res2.getString(8).replace('/',  '\\');
							str += "\nPhoto : \n";
						}
						new ResultatRechEns(null,  "Resultat",  true,  str,  phot);
						dispose();
					}
					
					
				}catch(SQLException ex) {
					System.out.println(ex);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
}
