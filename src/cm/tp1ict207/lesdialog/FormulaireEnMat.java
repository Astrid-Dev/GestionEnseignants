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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class FormulaireEnMat extends JDialog{

	private JLabel titre = new JLabel("Enregistrement d'une nouvelle matière");
	
	private JTextField code = new JTextField();
	private JTextField intitule = new JTextField();
	private JTextArea desccrip = new JTextArea();
	
	private Bouton okButton = new Bouton("Valider");
	private Bouton cancelButton = new Bouton("Annuler");
	
	public FormulaireEnMat(JFrame parent, String title, boolean modal)
	{
		super(parent, title, true);
		this.setSize(500,  500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent();
		this.setVisible(true);
		
	}
	
	private void initComponent()
	{
		this.setLayout(new BorderLayout());
		Font police = new Font("Arial",  Font.ITALIC,  20);
		Font police2 = new Font("Arial", Font.BOLD, 14);
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setFont(police);
		JPanel panTitre = new JPanel();
		panTitre.setPreferredSize(new Dimension(500,  50));
		try {
			BufferedImage img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\e690de33825e39bc6247ce171deb54b7.jpg"));
			JLabel l = new JLabel(new ImageIcon(img));
			panTitre.add(l);
		}catch(IOException e) {
			e.printStackTrace();
		}
		panTitre.add(titre);
		panTitre.setBackground(new Color(181, 230, 9));
		JPanel panCode = new JPanel();
		panCode.setPreferredSize(new Dimension(500, 60));
		panCode.setBorder(BorderFactory.createTitledBorder("Code de la matière"));
		panCode.setPreferredSize(new Dimension(500, 80));
		panCode.setBackground(new Color(153, 217, 234));
		JLabel demandeCode = new JLabel("Saisir un code :");
		try {
			MaskFormatter cod = new MaskFormatter("UUU###");
			code = new JFormattedTextField(cod);
		}catch(ParseException e) {
			e.printStackTrace();
		}
		code.setPreferredSize(new Dimension(100, 30));
		code.setFont(police2);
		code.setForeground(Color.BLUE);
		panCode.add(demandeCode);
		panCode.add(code);
		
		JPanel panMilieu = new JPanel();
		panMilieu.setPreferredSize(new Dimension(500, 400));
		panMilieu.add(panCode);
		
		JPanel panInti = new JPanel();
		panInti.setPreferredSize(new Dimension(500,  140));
		panInti.setBorder(BorderFactory.createTitledBorder("Intitulé de la matière"));
		intitule.setPreferredSize(new Dimension(400,  60));
		panInti.setBackground(new Color(153, 217, 234));
		JLabel demandeInt = new JLabel("Saisir un intitulé :");
		panInti.add(demandeInt);
		panInti.add(intitule);
		panMilieu.add(panInti);
		
		JPanel panDesc = new JPanel();
		panDesc.setPreferredSize(new Dimension(500,  170));
		panDesc.setBorder(BorderFactory.createTitledBorder("Description de la matière"));
		desccrip.setPreferredSize(new Dimension(400,  110));
		JLabel demandeDesc = new JLabel("Saisir une description :");
		panDesc.add(demandeDesc);
		panDesc.setBackground(new Color(153, 217, 234));
		panDesc.add(desccrip);
		panMilieu.add(panDesc);
		
		JPanel panButton = new JPanel();
		JPanel panTemp = new JPanel();
		panTemp.setPreferredSize(new Dimension(30,  20));
		panTemp.setBackground(new Color(153, 217, 234));
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		
		okButton.addActionListener(new okButtonListener());
		panButton.add(cancelButton);
		panButton.add(panTemp);
		panButton.add(okButton);
		panButton.setBackground(new Color(153, 217, 234));
		
		intitule.setFont(new Font("Times New Roman",  Font.BOLD,  14));
		desccrip.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 14));
		
		this.setBackground(new Color(153, 217, 234));
		this.getContentPane().add(panTitre, BorderLayout.NORTH);
		this.getContentPane().add(panMilieu, BorderLayout.CENTER);
		this.getContentPane().add(panButton, BorderLayout.SOUTH);
		
	}
	
	private class okButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String str = "";
			if(code.getText().equals("      ") || intitule.getText().equals("") || desccrip.getText().equals("")) {

				str += "Impossible de créér cette matière car les champs :\n";

				if(code.getText().equals("      "))
					str += "\t-Code\n";
				if(intitule.getText().equals(""))
					str += "\t-Intitulé\n";
				if(desccrip.getText().equals(""))
					str += "\t-Description\n";
				
				str += "ne sont pas renseignées !";
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null,  str,  "Erreur lors de l'enregistrement de la matière",  JOptionPane.ERROR_MESSAGE);
			}
			else {
				if(desccrip.getText().length() < 20) {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null,  "Veuillez entrez plus de 20 caractères pour la description",  "Erreur lors de l'enregistrement de la matière",  JOptionPane.ERROR_MESSAGE);
				}
				else if(intitule.getText().length() < 10) {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null,  "Veuillez entrez plus de 10 caractères pour l'intitulé",  "Erreur lors de l'enregistrement de la matière",  JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						String url = "jdbc:mysql://localhost:3306/tpict207";
						String user = "root";
						String pass = "";
						Connection conn = DriverManager.getConnection(url, user, pass);
						
						Statement state = conn.createStatement();
						ResultSet result = state.executeQuery("SELECT code FROM matiere");
						boolean existe = false;
						while(result.next())
						{
							if(result.getString("code").equals(code.getText()))
								existe = true;
						}
						
						if(existe) {
							JOptionPane jop = new JOptionPane();
							jop.showMessageDialog(null,  "Impossible d'ajouter cette matiere car,\nce code est détenu par une autre matière !",  "Erreur lors de l'enregistrement",  JOptionPane.ERROR_MESSAGE);
						}
						else {
							state.executeUpdate("INSERT INTO Matiere VALUES(('" + code.getText() + "'), ('" + intitule.getText() + "'), ('" + desccrip.getText().replace('\n',  '#') + "'));");
							JOptionPane jop = new JOptionPane();
							jop.showMessageDialog(null, "La Matiére a bien été enregistrée", "Enregistrement réussi", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
						
					}catch(SQLException e) {
						System.out.println(e);
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				}
		
			}
	
		}
		
	}
}
