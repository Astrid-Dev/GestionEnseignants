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

public class FormulaireSupMat extends JDialog{

	private String valCode;
	private JTextField jtf = new JTextField();
	
	private Bouton okButton = new Bouton("Supprimer");
	private Bouton cancelButton = new Bouton("Annuler");
	
	public FormulaireSupMat(JFrame parent, String title, boolean modal)
	{
		super(parent, title, true);
		this.setSize(350,  200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent();
		this.setVisible(true);
	}
	
	private void initComponent()
	{
		this.setLayout(new BorderLayout());
		JPanel panTitre = new JPanel();
		panTitre.setPreferredSize(new Dimension(300,  45));
		JLabel titre = new JLabel("Suppression d'une matière");
		titre.setForeground(Color.BLUE);
		titre.setPreferredSize(new Dimension(300,  40));
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setFont(new Font("Arial",  Font.ITALIC,  16));
		panTitre.add(titre);
		panTitre.setBackground(new Color(237, 28, 36));
		JLabel demandeMatricule = new JLabel("Saisir le code de la matière a supprimée :");
		demandeMatricule.setPreferredSize(new Dimension(300,  30));
		demandeMatricule.setHorizontalAlignment(JLabel.CENTER);
		try {
			MaskFormatter cod = new MaskFormatter("UUU###");
			jtf = new JFormattedTextField(cod);
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedImage img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\0edc40a63eb370a2abb52adb100503db.jpg"));
			JLabel l = new JLabel(new ImageIcon(img));
			this.getContentPane().add(l, BorderLayout.WEST);
		}catch(IOException e) {
			e.printStackTrace();
		}
		jtf.setPreferredSize(new Dimension(80,  30));
		jtf.setHorizontalAlignment(JTextField.CENTER);
		jtf.setForeground(Color.RED);
		jtf.setFont(new Font("Arial", Font.BOLD, 13));
		
		JPanel panText = new JPanel();
		panText.setPreferredSize(new Dimension(300,  80));
		panText.add(demandeMatricule);
		panText.add(jtf);
		panText.setBackground(new Color(153, 217, 234));
		JPanel panBout = new JPanel();
		panBout.setPreferredSize(new Dimension(300,  35));
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(30,  30));
		p.setBackground(new Color(153, 217, 234));
		panBout.add(p);
		panBout.add(cancelButton);
		panBout.add(okButton);
		panBout.setBackground(new Color(153, 217, 234));
		
		this.getContentPane().add(panTitre, BorderLayout.NORTH);
		this.getContentPane().add(panText, BorderLayout.CENTER);
		this.getContentPane().add(panBout, BorderLayout.SOUTH);
		
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(jtf.getText().equals("      ")) {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null,  "Veuillez renseigner un code d'UE !",  "Champ 'Code Matière' non renseigné", JOptionPane.WARNING_MESSAGE);

				}
				else {
					try {
						String url = "jdbc:mysql://localhost:3306/tpict207";
						String user = "root";
						String pass = "";
						Connection conn = DriverManager.getConnection(url, user, pass);
						
						Statement state = conn.createStatement();
						ResultSet res = state.executeQuery("SELECT code FROM Matiere");
						boolean existe = false;
						while(res.next()) {
							if(res.getString("code").equals(jtf.getText()))
								existe = true; 		
						}
						if(!existe) {
							JOptionPane jop = new JOptionPane();
							jop.showMessageDialog(null,  "Impossible de supprimer cette matière car\nelle est introuvable !",  "Erreur lors de la suppression",  JOptionPane.ERROR_MESSAGE);
							setVisible(false);
						}
						else {
							JOptionPane jop2 = new JOptionPane();
							int val = jop2.showConfirmDialog(null,  "Voulez-vous vraiment supprimer\ncette matière ?",  "Certification de la suppression",  JOptionPane.YES_NO_CANCEL_OPTION);
							
							if(val == jop2.OK_OPTION) {
								state.executeUpdate("DELETE FROM Enseignant WHERE codeMat = '" + jtf.getText() + "';");
								state.executeUpdate("DELETE FROM Matiere WHERE code = '" + jtf.getText() + "';");
								JOptionPane jop = new JOptionPane();
								jop.showMessageDialog(null,  "La matière " + jtf.getText() + " a bien été supprimée\nNB: Même les enseignants donnant cette matière ont\nétésupprimés",  "Suppression réussie",  JOptionPane.INFORMATION_MESSAGE);
							}
							
							setVisible(false);
						}
						
					}catch(SQLException e) {
						System.out.println(e);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}
}
