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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.MaskFormatter;

public class FormulaireEnEns extends JDialog{

	private Object[][] infosEnr;
	
	private JLabel titre = new JLabel("Enregistrement d'un nouvel enseignant ");
	
	private JTextField nom = new JTextField();
	private JTextField prenom = new JTextField();
	private JTextField matricule = new JTextField();
	private JComboBox age = new JComboBox();
	private JComboBox matiere = new JComboBox();
	
	private JRadioButton feminin = new JRadioButton("Féminin");
	private JRadioButton masculin = new JRadioButton("Masculin");
	private JRadioButton indetermine = new JRadioButton("");
	
	private JTextField telephone = new JTextField();
	
	JPanel panTemp2 = new JPanel();
	private Bouton okButton = new Bouton("Valider");
	private Bouton cancelButton = new Bouton("Annuler");
	private Bouton photoButton = new Bouton("Ajouter une photo");
	
	private Panneau photo;
	
	private String sex = "", ag = "";
	
	private String chemin = "";
	public FormulaireEnEns(JFrame parent, String title, boolean modal)
	{
		super(parent, title, true);
		chemin = "E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\images.jfif";
		this.setSize(800,  545);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent();
		
		this.setVisible(true);
	}
	
	private FormulaireEnEns(JFrame parent, String title, boolean modal, String a, String na, String pa, String ma, int aa, String sa, String ta, String m)
	{
		super(parent, title, true);
		this.chemin = a;
		this.setSize(800,  545);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initComponent();

		nom.setText(na);
		prenom.setText(pa);
		matricule.setText(ma);
		age.setSelectedItem(String.valueOf(aa));
		
		if(sa.equals("")) {
			
		}
		else {
			if(sa.equals(feminin.getText())) {
				feminin.setSelected(true);
				sex = feminin.getText();
			}
			else {
				masculin.setSelected(true);
				sex = masculin.getText();
			}
		}

		
		telephone.setText(ta);
		matiere.setSelectedItem(m);
		this.setVisible(true);
	}
	
	private void initComponent()
	{
		photo = new Panneau(chemin);
		this.setLayout(new BorderLayout());
		Font police = new Font("Arial",  Font.ITALIC,  20);
		Font police2 = new Font("Arial",  Font.BOLD,  14);
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
		
		JPanel panMat = new JPanel();
		panMat.setPreferredSize(new Dimension(400, 80));
		panMat.setBorder(BorderFactory.createTitledBorder("Matricule de l'enseignant"));
		JLabel demandeMat = new JLabel("Saisir un Matricule :");
		try {
			MaskFormatter mat = new MaskFormatter("##U####");
			matricule = new JFormattedTextField(mat);
			matricule.setPreferredSize(new Dimension(120, 30));
			matricule.setFont(police2);
			matricule.setForeground(Color.BLUE);
		}catch(ParseException e) {
			e.printStackTrace();
		}
		panMat.add(demandeMat);
		panMat.add(matricule);
		panMat.setBackground(new Color(153, 217, 234));

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
		
		
		panGauche.add(panNom);
		panGauche.add(panPreNom);
		panGauche.add(panMat);
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
		panTitre.setBackground(new Color(181, 230, 9));
		try {
			BufferedImage img = ImageIO.read(new File("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\59031c8a6fd1904c3a94ecc23dc9b498.jpg"));
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
	
	private class OkButtoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String str = "";
			if(nom.getText().equals("") || prenom.getText().equals("") || matricule.getText().equals("       ") || telephone.getText().equals("6            ") || sex.equals("") || matiere.getSelectedItem().toString().equals("")) {
				
				str = "Impossible de créér l'enseignant car les champs : \n";
				if(nom.getText().equals(""))
					str += "\t-Nom\n";
				if(prenom.getText().equals(""))
					str += "\t-Prenom\n";
				if(matricule.getText().equals("       "))
					str += "\t-Matricule\n";
				if(sex.equals(""))
					str += "\t-Sexe\n";
				if(telephone.getText().equals("6            "))
					str += "\t-Telephone\n";
				if(matiere.getSelectedItem().toString().equals(""))
					str += "\t-Matiere\n";
				
				str += "ne sont pas renseignés !";
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null,  str, "Erreur lors de l'enregistrement de l'enseignant", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					String url = "jdbc:mysql://localhost:3306/tpict207";
					String user = "root";
					String pass = "";
					Connection conn = DriverManager.getConnection(url, user, pass);
					
					Statement state = conn.createStatement();
					ResultSet result = state.executeQuery("SELECT matricule FROM Enseignant");
					boolean existe = false;
					while(result.next())
					{
						if(result.getString("matricule").equals(matricule.getText()))
							existe = true;
					}
					
					if(existe) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null,  "Impossible d'ajouter cette matière car,\nce code est détenu par une autre matière !",  "Erreur lors de l'enregistrement",  JOptionPane.ERROR_MESSAGE);
					}
					else {
						state.executeUpdate("INSERT INTO Enseignant VALUES("
																		+ "('" + nom.getText() + "'),\n"
																			+ " ('" + prenom.getText() + "'),\n"
																			+ " ('" + matricule.getText() + "'),\n"
																			+ "(" + ag + "),\n" 
																			+ "('" + sex + "'),\n"
																			+ " ('" + telephone.getText() + "'),\n"
																			+ "('"+ matiere.getSelectedItem() + "'),\n"
																			+ "('"+ chemin.replace('\\',  '/') + "')\n"
																	+ ");");
						

						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null,  "L'enseignant a bien été enregistré", "Enregistrement réussi",  JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
					}
		
					
				}catch(SQLException e) {
					System.out.println(e);
				}catch(Exception e) {
					e.printStackTrace();
				}
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
			
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				SwingUtilities.updateComponentTreeUI(choose);
			}catch(InstantiationException e4 ) {}
			catch(ClassNotFoundException e1) {}
			catch(UnsupportedLookAndFeelException e2) {}
			catch(IllegalAccessException e3) {}
			choose.setDialogTitle("Sélectionner une image ");
			choose.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filtre = new FileNameExtensionFilter("Images JPG et GIF", "jpg", "gif");
			choose.addChoosableFileFilter(filtre);
			
			
			int res = choose.showOpenDialog(null);
			if(res == JFileChooser.APPROVE_OPTION) {
				String nomav = nom.getText();
				String prenomav = prenom.getText();
				String matriculeav = matricule.getText();
				int ageav = Integer.valueOf(age.getSelectedItem().toString());
				String sexeav = null;
					sexeav = sex;
				String telav = telephone.getText();
				String matie = matiere.getSelectedItem().toString();
				dispose();
				
				new FormulaireEnEns(null,  "sdsd",  true,  choose.getSelectedFile().getPath(), nomav, prenomav, matriculeav, ageav, sexeav, telav, matie);
			}
		}
		
	}
	
	
}
