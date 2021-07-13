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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class FormulaireModifMat extends JDialog{

	private JLabel titre = new JLabel("Modification d'une matière");
	
	private JComboBox code = new JComboBox();
	private JTextField intitule = new JTextField();
	private JTextArea desccrip = new JTextArea();
	
	private Bouton okButton = new Bouton("Modifier");
	private Bouton cancelButton = new Bouton("Annuler");
	
	private String inAv = "", desAv = "";
	public FormulaireModifMat(JFrame parent, String title, boolean modal)
	{
		super(parent, title, true);
		this.setSize(500,  500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent("");
		bloque();
		this.setVisible(true);
		
	}
	
	private FormulaireModifMat(JFrame parent, String title, boolean modal, String coav, String iav, String dav)
	{
		super(parent, title, true);
		this.setSize(500,  500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent(coav);
		try {
			String url = "jdbc:mysql://localhost:3306/tpict207";
			String user = "root";
			String pass = "";
			Connection conn = DriverManager.getConnection(url, user, pass);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Matiere WHERE code = '" + coav + "';");
			while(result.next())
			{
				this.inAv = result.getString(2);
				this.desAv = result.getString(3).replace('#',  '\n');
			
			}
			
			result.close();
			state.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println(e);
		}catch(Exception e) {
			e.printStackTrace();
		}
		debloque();
		intitule.setText(iav);
		desccrip.setText(dav);
		this.setVisible(true);
		
	}
	
	private void initComponent(String w)
	{
		this.setLayout(new BorderLayout());
		Font police = new Font("Arial",  Font.ITALIC,  20);
		Font police2 = new Font("Arial", Font.BOLD, 14);
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setFont(police);
		JPanel panTitre = new JPanel();
		panTitre.setPreferredSize(new Dimension(500,  50));
		try {
			BufferedImage img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\9f7335b8c07ff8a00ecb89904cb338f0.jpg"));
			JLabel l = new JLabel(new ImageIcon(img));
			panTitre.add(l);
		}catch(IOException e) {
			e.printStackTrace();
		}
		panTitre.add(titre);
		panTitre.setBackground(new Color(255, 242, 0));
		JPanel panCode = new JPanel();
		panCode.setPreferredSize(new Dimension(500, 60));
		panCode.setBorder(BorderFactory.createTitledBorder("Code de la matière"));
		panCode.setPreferredSize(new Dimension(500, 80));
		panCode.setBackground(new Color(153, 217, 234));
		JLabel demandeCode = new JLabel("Choisir un code :");
		code.setPreferredSize(new Dimension(100, 30));
		code.setFont(police2);
		code.setForeground(Color.BLUE);
		panCode.add(demandeCode);
		try {
			String url = "jdbc:mysql://localhost:3306/tpict207";
			String user = "root";
			String pass = "";
			Connection conn = DriverManager.getConnection(url, user, pass);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT code FROM Matiere");
			while(result.next())
			{
				code.addItem(result.getString(1));	
				
			}
			code.addItem("");
			code.setSelectedItem(w);
			result.close();
			state.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println(e);
		}catch(Exception e) {
			e.printStackTrace();
		}
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
		
		code.addActionListener(new CodeListener());
		
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
			if(intitule.getText().equals("") || desccrip.getText().equals("")) {

				str += "Impossible de Modifier cette matière car les champs :\n";

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
					jop.showMessageDialog(null,  "Veuillez entrez plus de 20 caractères pour la description",  "Erreur lors de la modification de la matière",  JOptionPane.ERROR_MESSAGE);
				}
				else if(intitule.getText().length() < 10) {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null,  "Veuillez entrez plus de 10 caractères pour l'intitulé",  "Erreur lors de la modification de la matière",  JOptionPane.ERROR_MESSAGE);
				}
				else if(!intitule.getText().equals(inAv) || !desccrip.getText().equals(desAv)) {
					String ch = "Voulez-vous vraiment modifier les champs : \n";
					
					if(!intitule.getText().equals(inAv))
						ch += "\t-Intitule\n";
					if(!desccrip.getText().equals(desAv))
						ch += "\t-Description\n";
					
					JOptionPane pa = new JOptionPane();
					int choix = pa.showConfirmDialog(null,  ch,  "Demande de confirmation",  JOptionPane.YES_NO_CANCEL_OPTION);
					
					if(choix == JOptionPane.OK_OPTION)
					{
						try {
							String url = "jdbc:mysql://localhost:3306/tpict207";
							String user = "root";
							String pass = "";
							Connection conn = DriverManager.getConnection(url, user, pass);
							
							Statement state = conn.createStatement();
							System.out.println(code.getSelectedItem().toString());
							state.executeUpdate("UPDATE Matiere SET intitule = '" + intitule.getText() + "', description = '" + desccrip.getText().replace('\n',  '#') + "' WHERE code = '" + code.getSelectedItem().toString() + "';");
							JOptionPane jop = new JOptionPane();
							jop.showMessageDialog(null, "La Matiére a bien été modifiée", "Modification réussie", JOptionPane.INFORMATION_MESSAGE);
							dispose();
								
							
						}catch(SQLException e) {
							System.out.println(e);
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
				else {
					
					JOptionPane j = new JOptionPane();
					j.showMessageDialog(null,  "Vous n'avez rien modifié",  "Aucun champ modifié",  JOptionPane.INFORMATION_MESSAGE);
				}
		
			}
	
		}
		
	}
	
	private class CodeListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String cod = code.getSelectedItem().toString();
			String intav = "";
			String descav = "";

			boolean existe = false;
			try {
				String url = "jdbc:mysql://localhost:3306/tpict207";
				String user = "root";
				String pass = "";
				Connection conn = DriverManager.getConnection(url, user, pass);
				Statement state = conn.createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM Matiere WHERE code = '" + cod + "';");
				while(result.next())
				{
					if(result.getString("code").equals(cod))
						existe = true;
					if(existe == true) {
						intav = result.getString(2);
						descav = result.getString(3).replace('#',  '\n');
					}
				
				}
				
				result.close();
				state.close();
				conn.close();
			}catch(SQLException e) {
				System.out.println(e);
			}catch(Exception e) {
				e.printStackTrace();
			}
			dispose();
			if(existe) {
				new FormulaireModifMat(null,  "sdsd",  true, cod,  intav, descav);
			}
			else
				new FormulaireModifMat(null,  "Modification d'infos",  true);
		}
		
	}
	
	private void bloque()
	{
		intitule.setEnabled(false);
		desccrip.setEnabled(false);
		okButton.setEnabled(false);
	}
	
	private void debloque()
	{
		intitule.setEnabled(true);
		desccrip.setEnabled(true);
		okButton.setEnabled(true);
	}
}
