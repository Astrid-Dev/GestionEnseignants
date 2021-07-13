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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.MaskFormatter;

public class FormulaireModifEns extends JDialog{


	
	private Object[][] infosEnr;
	
	private JLabel titre = new JLabel("Modification d'informations d'un enseignant ");
	
	private JTextField nom = new JTextField();
	private JTextField prenom = new JTextField();
	private JComboBox matricule = new JComboBox();
	private JComboBox age = new JComboBox();
	private JComboBox matiere = new JComboBox();
	
	private JRadioButton feminin = new JRadioButton("Féminin");
	private JRadioButton masculin = new JRadioButton("Masculin");
	private JRadioButton indetermine = new JRadioButton("");
	
	private JTextField telephone = new JTextField();
	
	JPanel panTemp2 = new JPanel();
	private Bouton okButton = new Bouton("Modifier");
	private Bouton cancelButton = new Bouton("Annuler");
	private Bouton photoButton = new Bouton("Changer la photo");
	
	private Panneau photo;
	
	private String sex = "", ag = "";
	
	public static String chemin = "";
	private String nomAvant = "", prenomAvant = "", ageAvant = "", sexeAvant = "", matiereAvant = "", telAvant = "", photoAvant = "";
	public FormulaireModifEns(JFrame parent, String title, boolean modal)
	{
		super(parent, title, true);
		chemin = "E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\images.jfif";
		this.setSize(800,  545);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent("");
		bloque();
		this.setVisible(true);
	}
	
	private FormulaireModifEns(JFrame parent, String title, boolean modal, String a, String na, String pa, String ma, int aa, String sa, String ta, String m)
	{
		super(parent, title, true);
		chemin = a;
		this.setSize(800,  545);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent(ma);

		debloque();
		System.out.println("OK");
		nom.setText(na);
		prenom.setText(pa);
		age.setSelectedItem(String.valueOf(aa));
		
		if(sa.equals(feminin.getText())) {
			feminin.setSelected(true);
			sex = feminin.getText();
		}
		else if(sa.equals(masculin.getText())) {
			masculin.setSelected(true);
			sex = masculin.getText();
		}
		else {
			indetermine.setSelected(true);
		}
		
		telephone.setText(ta);
		matiere.setSelectedItem(m);
		
		
		try {
			String url = "jdbc:mysql://localhost:3306/tpict207";
			String user = "root";
			String pass = "";
			Connection conn = DriverManager.getConnection(url, user, pass);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Enseignant WHERE  matricule = '" + ma + "';");
			while(result.next())
			{
				nomAvant = result.getString(1);
				prenomAvant = result.getString(2);
				ageAvant = String.valueOf(result.getInt(4));
				sexeAvant = result.getString(5);
				telAvant = result.getString(6);
				matiereAvant = result.getString(7);
				photoAvant = result.getString(8).replace('/',  '\\');	
				
			}
			result.close();
			state.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println(e);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		this.setVisible(true);
	}
	
	private void initComponent(String w)
	{
		photo = new Panneau(chemin);
		Font police2 = new Font("Arial",  Font.BOLD,  14);
		JPanel panMat = new JPanel();
		panMat.setPreferredSize(new Dimension(400, 80));
		panMat.setBorder(BorderFactory.createTitledBorder("Matricule de l'enseignant"));
		JLabel demandeMat = new JLabel("Choisir un Matricule :");
		matricule.addItem("");
		try {
			String url = "jdbc:mysql://localhost:3306/tpict207";
			String user = "root";
			String pass = "";
			Connection conn = DriverManager.getConnection(url, user, pass);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT matricule FROM Enseignant");
			while(result.next())
			{
				matricule.addItem(result.getString(1));
				
			}
			matricule.setSelectedItem(w);
			result.close();
			state.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		matricule.addActionListener(new MatriculeListener());
		panMat.add(demandeMat);
		panMat.add(matricule);
		panMat.setBackground(new Color(153, 217, 234));
		this.setLayout(new BorderLayout());
		Font police = new Font("Arial",  Font.ITALIC,  20);
		titre.setFont(police);
		titre.setHorizontalTextPosition(JLabel.CENTER);
		titre.setBackground(new Color(153, 217, 234));
		
		JPanel panGauche = new JPanel();
		panGauche.setPreferredSize(new Dimension(400, 370));
		
		JPanel panNom = new JPanel();
		panNom.setPreferredSize(new Dimension(400, 80));
		panNom.setBorder(BorderFactory.createTitledBorder("Nom de l'enseignant"));
		JLabel demandeNom = new JLabel("Saisir un nom :");
		nom.setPreferredSize(new Dimension(300, 30));
		nom.setFont(police2);
		panNom.add(demandeNom);
		panNom.add(nom);
		panNom.setBackground(new Color(153, 217, 234));
		
		JPanel panPreNom = new JPanel();
		panPreNom.setPreferredSize(new Dimension(400, 80));
		panPreNom.setBorder(BorderFactory.createTitledBorder("Prénom de l'enseignant"));
		JLabel demandePreNom = new JLabel("Saisir un prenom :");
		prenom.setPreferredSize(new Dimension(300, 30));
		prenom.setFont(police2);
		panPreNom.add(demandePreNom);
		panPreNom.add(prenom);
		panPreNom.setBackground(new Color(153, 217, 234));
		

		age.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ag = age.getSelectedItem().toString();
			}
		});
		JPanel panAge = new JPanel();
		panAge.setPreferredSize(new Dimension(400, 60));
		panAge.setBorder(BorderFactory.createTitledBorder("Age de l'enseignant"));
		for(int i = 24; i < 61; i++)
		{
			age.addItem(String.valueOf(i));
			if(i == 28)
				age.setSelectedItem(String.valueOf(i));
		}
		age.setPreferredSize(new Dimension(60, 25));
		JLabel demandeAge = new JLabel("Entrez un age :");
		age.setFont(police2);
		panAge.add(demandeAge);
		panAge.add(age);
		panAge.setBackground(new Color(153, 217, 234));
		
		masculin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sex = ((JRadioButton)arg0.getSource()).getText();
			}
		});
		
		feminin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sex = ((JRadioButton)arg0.getSource()).getText();
			}
		});
		JPanel panSexe = new JPanel();
		panSexe.setPreferredSize(new Dimension(400, 70));
		panSexe.setBorder(BorderFactory.createTitledBorder("Sexe de l'enseignant"));
		JLabel demandeSexe = new JLabel("Choisir un sexe :");
		panSexe.add(demandeSexe);
		panSexe.add(masculin);
		panSexe.add(feminin);
		panSexe.setBackground(new Color(153, 217, 234));
		feminin.setBackground(new Color(153, 217, 234));
		masculin.setBackground(new Color(153, 217, 234));
		ButtonGroup bg = new ButtonGroup();
		bg.add(masculin);
		bg.add(feminin);
		bg.add(indetermine);
		indetermine.setSelected(true);
		
		JPanel panTel = new JPanel();
		panTel.setPreferredSize(new Dimension(400, 80));
		panTel.setBorder(BorderFactory.createTitledBorder("Contact de l'enseignant"));
		JLabel demandeTel = new JLabel("Saisir un N° de Tel :");
		try {
			MaskFormatter tel = new MaskFormatter("6 ## ## ## ##");
			telephone = new JFormattedTextField(tel);
			telephone.setPreferredSize(new Dimension(120, 30));
			telephone.setFont(police2);
			telephone.setForeground(Color.BLUE);
		}catch(ParseException e) {
			e.printStackTrace();
		}
		panTel.add(demandeTel);
		panTel.add(telephone);
		panTel.setBackground(new Color(153, 217, 234));
		
		panGauche.add(panMat);
		panGauche.add(panNom);
		panGauche.add(panPreNom);
		panGauche.add(panAge);
		panGauche.add(panSexe);
		panGauche.add(panTel);
		
		JPanel panDroit = new JPanel();
		panDroit.setPreferredSize(new Dimension(300, 500));
		JPanel panButton = new JPanel();
		panButton.setPreferredSize(new Dimension(300,  120));
		panButton.setLayout(new BorderLayout());
		cancelButton.addActionListener(new SortirListener());
		JPanel panTemp4 = new JPanel();
		panTemp4.add(cancelButton);
		
		JPanel panTemp5 = new JPanel();
		panTemp5.setPreferredSize(new Dimension(30,  30));
		panTemp4.add(panTemp5);
		okButton.addActionListener(new OkButtoListener());
		panTemp5.setBackground(new Color(153, 217, 234));
		panTemp4.add(okButton);
		panButton.add(panTemp4, BorderLayout.SOUTH);
		panDroit.setLayout(new BorderLayout());
		panDroit.add(panButton, BorderLayout.SOUTH);
		panButton.setBackground(new Color(153, 217, 234));
		panTemp2.setPreferredSize(new Dimension(100, 100));
		panTemp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),  "Photo de l'enseignant"));
		panTemp2.setLayout(new BorderLayout());
		panTemp2.setBackground(new Color(153, 217, 234));
		photo.setPreferredSize(new Dimension(200,  200));
		panTemp2.add(photo, BorderLayout.CENTER);
		photoButton.setPreferredSize(new Dimension(100, 20));
		photoButton.addActionListener(new AjoutListener());
		panTemp2.add(photoButton, BorderLayout.SOUTH);
	
		panDroit.add(panTemp2, BorderLayout.CENTER);
		JPanel panTemp3 = new JPanel();
		panTemp3.setPreferredSize(new Dimension(300, 110));
		panTemp3.setBackground(new Color(153, 217, 234));
		JPanel panMatiere = new JPanel();
		panMatiere.setBorder(BorderFactory.createTitledBorder("Matiere de l'enseignant"));
		panMatiere.setBackground(new Color(153, 217, 234));
		JLabel demandeMatiere = new JLabel("Choisir une matière : ");
		matiere.setPreferredSize(new Dimension(100, 30));
		matricule.setPreferredSize(new Dimension(100,  30));
		matiere.setFont(police2);
		try {
			String url = "jdbc:mysql://localhost:3306/tpict207";
			String user = "root";
			String pass = "";
			Connection conn = DriverManager.getConnection(url, user, pass);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT code FROM Matiere");
			while(result.next())
			{
				matiere.addItem(result.getString(1));
				
			}
			result.close();
			state.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println(e);
		}catch(Exception e) {
			e.printStackTrace();
		}
		matiere.addItem("");
		matiere.setSelectedItem("");
		panMatiere.add(demandeMatiere);
		panMatiere.setPreferredSize(new Dimension(300, 80));
		panMatiere.add(matiere);
		panTemp4.setBackground(new Color(153, 217, 234));
		panTemp3.add(panMatiere);
		panDroit.add(panTemp3, BorderLayout.NORTH);
		panDroit.setBackground(new Color(153, 217, 234));
		
		JPanel panTitre = new JPanel();
		JPanel panTemp = new JPanel();
		panTemp.setPreferredSize(new Dimension(20, 30));
		panTemp.setBackground(Color.YELLOW);
		panTitre.setPreferredSize(new Dimension(800, 50));
		panTitre.setBackground(new Color(255, 242, 0));
		try {
			BufferedImage img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\9f7335b8c07ff8a00ecb89904cb338f0.jpg"));
			JLabel l = new JLabel(new ImageIcon(img));
			panTitre.add(l);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		panTitre.add(titre);
		this.getContentPane().setBackground(new Color(153, 217, 234));
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panTitre, BorderLayout.NORTH);
		this.getContentPane().add(panGauche, BorderLayout.WEST);
		this.getContentPane().add(panDroit, BorderLayout.EAST);
		panGauche.setBackground(new Color(153, 217, 234));
		
		
	}
	
	private void bloque()
	{
		nom.setEnabled(false);
		prenom.setEnabled(false);
		age.setEnabled(false);
		feminin.setEnabled(false);
		masculin.setEnabled(false);
		matiere.setEnabled(false);
		photoButton.setEnabled(false);
		telephone.setEnabled(false);
		okButton.setEnabled(false);
	}
	
	private void debloque()
	{
		nom.setEnabled(true);
		prenom.setEnabled(true);
		age.setEnabled(true);
		feminin.setEnabled(true);
		masculin.setEnabled(true);
		matiere.setEnabled(true);
		photoButton.setEnabled(true);
		telephone.setEnabled(true);
		okButton.setEnabled(true);
	}
	private class OkButtoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String str1 = "";
			if(nom.getText().equals("") || prenom.getText().equals("") || telephone.getText().equals("6            ") || sex.equals("") || matiere.getSelectedItem().toString().equals("")) {
				
				str1 = "Impossible de modifier l'enseignant car les champs : \n";
				if(nom.getText().equals(""))
					str1 += "\t-Nom\n";
				if(prenom.getText().equals(""))
					str1 += "\t-Prenom\n";
				if(sex.equals(""))
					str1 += "\t-Sexe\n";
				if(telephone.getText().equals("6            "))
					str1 += "\t-Telephone\n";
				if(matiere.getSelectedItem().toString().equals(""))
					str1 += "\t-Matiere\n";
				
				str1 += "ne sont pas renseignés !";
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null,  str1, "Erreur lors de la modification de l'enseignant", JOptionPane.ERROR_MESSAGE);
			}
			else if(!nom.getText().equals(nomAvant) || !prenom.getText().equals(prenomAvant) || !telephone.getText().equals(telAvant) || !sex.equals(sexeAvant) || !matiere.getSelectedItem().toString().equals(matiereAvant) || !photoAvant.equals(chemin) || !age.getSelectedItem().toString().equals(ageAvant)){
				String str = "";
				int choix = 10;;
				str = "Voulez-vous vraiment modifier le(s) champ(s) suivant(s): \n";
				if(!nom.getText().equals(nomAvant))
					str += "\t-Nom\n";
				if(!prenom.getText().equals(prenomAvant))
					str += "\t-Prenom\n";
				if(!sex.equals(sexeAvant))
					str += "\t-Sexe\n";
				if(!telephone.getText().equals(telAvant))
					str += "\t-Telephone\n";
				if(!matiere.getSelectedItem().toString().equals(matiereAvant))
					str += "\t-Matiere\n";
				if(!photoAvant.equals(chemin))
					str += "\t-Photo\n";
				if(!age.getSelectedItem().toString().equals(ageAvant))
					str += "\t-Age\n";
				
				JOptionPane jop = new JOptionPane();
				choix = jop.showConfirmDialog(null,  str,  "Demande de confirmation",  JOptionPane.YES_NO_CANCEL_OPTION);
				if(choix == JOptionPane.OK_OPTION){
					try {
						String url = "jdbc:mysql://localhost:3306/tpict207";
						String user = "root";
						String pass = "";
						Connection conn = DriverManager.getConnection(url, user, pass);
						
						Statement state = conn.createStatement();
						ResultSet result = state.executeQuery("SELECT matricule FROM Enseignant");
						
						state.executeUpdate("UPDATE Enseignant SET noms = "
																			+ "'" + nom.getText() + "',\n"
																			+ " prenoms = '" + prenom.getText() + "',\n"
																			+ "age = " + ag + ",\n" 
																			+ "sexe = '" + sex + "',\n"
																			+ "telephone = '" + telephone.getText() + "',\n"
																			+ "codeMat = '"+ matiere.getSelectedItem().toString() + "',\n"
																			+ "photo = '"+ chemin.replace('\\',  '/') + "'\n"
																	+ " WHERE matricule = '" + matricule.getSelectedItem().toString() + "';");
						
							
						JOptionPane jop2= new JOptionPane();
						jop2.showMessageDialog(null,  "L'enseignant a bien été modifié", "Modification réussie",  JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
			
						
					}catch(SQLException e) {
						System.out.println(e);
					}catch(Exception e) {
						e.printStackTrace();
					}
					dispose();
				}
				
			}else {
				JOptionPane pa = new JOptionPane();
				pa.showMessageDialog(null,  "Vous n'avez rien modifié !", "Aucune information n'as été modifiée", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
	}
	
	private class SortirListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dispose();
		}
		
	}
	
	private class AjoutListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			
			choose.setDialogTitle("Sélectionner une image ");
			choose.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filtre = new FileNameExtensionFilter("Images JPG et GIF", "jpg", "gif");
			choose.addChoosableFileFilter(filtre);
			
			
			int res = choose.showOpenDialog(null);
			if(res == JFileChooser.APPROVE_OPTION) {
				String nomav = nom.getText();
				String prenomav = prenom.getText();
				String matriculeav = matricule.getSelectedItem().toString();
				int ageav = Integer.valueOf(age.getSelectedItem().toString());
				String sexeav = null;
				if(feminin.isSelected())
					sexeav = feminin.getText();
				if(masculin.isSelected())
					sexeav = masculin.getText();
				if(indetermine.isSelected())
					sexeav = "";
				String telav = telephone.getText();
				String matie = matiere.getSelectedItem().toString();
				dispose();
				new FormulaireModifEns(null,  "sdsd",  true,  choose.getSelectedFile().getPath(), nomav, prenomav, matriculeav, ageav, sexeav, telav, matie);
			}
		}
		
	}
	
	private class MatriculeListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String mat = matricule.getSelectedItem().toString();
			String nomav = "";
			String prenomav = "";
			String ageav = "";
			String sexeav = "";
			String telav = "";
			String matav = "";
			String photoav = "";
			boolean existe = false;
			try {
				String url = "jdbc:mysql://localhost:3306/tpict207";
				String user = "root";
				String pass = "";
				Connection conn = DriverManager.getConnection(url, user, pass);
				Statement state = conn.createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM Enseignant WHERE matricule = '" + mat + "';");
				while(result.next())
				{
					if(result.getString("matricule").equals(mat))
						existe = true;
					if(existe == true) {
						nomav = result.getString(1);
						prenomav = result.getString(2);
						ageav = String.valueOf(result.getInt(4));
						sexeav = result.getString(5);
						telav = result.getString(6);
						matav = result.getString(7);
						photoav = result.getString(8).replace('/',  '\\');
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
				new FormulaireModifEns(null,  "sdsd",  true,  photoav, nomav, prenomav, mat, Integer.valueOf(ageav), sexeav, telav, matav);
			}
			else
				new FormulaireModifEns(null,  "Modification d'infos",  true);
		}
		
	}

}
