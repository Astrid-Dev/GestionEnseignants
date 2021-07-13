package tp1ict207;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;

import cm.tp1ict207.lesdialog.Bouton;
import cm.tp1ict207.lesdialog.FormulaireEnEns;
import cm.tp1ict207.lesdialog.FormulaireEnMat;
import cm.tp1ict207.lesdialog.FormulaireModifEns;
import cm.tp1ict207.lesdialog.FormulaireModifMat;
import cm.tp1ict207.lesdialog.FormulaireRechEns;
import cm.tp1ict207.lesdialog.FormulaireRechMat;
import cm.tp1ict207.lesdialog.FormulaireSupEns;
import cm.tp1ict207.lesdialog.FormulaireSupMat;

public class Fenetre extends JFrame{

	
	private String[] tittresEns = {"NOMS", "PRENOMS", "MATRICULE", "AGE", "SEXE", "TELEPHONE", "MATIERE"};
	private Object[][] elementsEns;
	
	private String[] tittresMat = {"CODE", "INTITULE", "DESCRIPTION"};
	private Object[][] elementsMat;
	
	
	private JLabel titre1 = new JLabel("Matières");
	private JLabel titre2 = new JLabel("Enseignants");
	
	private JTable tableau1;
	private JTable tableau2;
	
	private Bouton boutEnEns = new Bouton("Ajouter un Enseignant");
	private Bouton boutSupEns = new Bouton("Supprimer un Enseignant");
	private Bouton boutCherEns = new Bouton("Rechercher un Enseignant");
	private Bouton boutModifEns = new Bouton("Modifier un Enseignant");
	
	private Bouton boutEnMat = new Bouton("Ajouter une Matiere");
	private Bouton boutSupMat = new Bouton("Supprimer une Matiere");
	private Bouton boutCherMat = new Bouton("Rechercher une Matiere");
	private Bouton boutModifMat = new Bouton("Modifier une Matiere");
	
	public Fenetre()
	{
		this.setTitle("TP1 ICT 207");
		this.setSize(965,  600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		Image icon = Toolkit.getDefaultToolkit().getImage("E:\\Cours\\programmations\\java\\tp1ict207\\src\\cm\\tp1ict207\\lesdialog\\image\\976a03d01507021db1cdef9660393dd9.jpg");
		this.setIconImage(icon);
		
		
		initComponent();
		
		this.setVisible(true);
	}
	
	private void initComponent()
	{
		try {
			String url = "jdbc:mysql://localhost:3306/tpict207";
			String user = "root";
			String pass = "";
			Connection conn = DriverManager.getConnection(url, user, pass);
			Statement state = conn.createStatement();
			ResultSet res = state.executeQuery("SELECT * FROM Matiere");
			int lentgh = 0;
			while(res.next())
				lentgh++;
			
			ResultSet result = state.executeQuery("SELECT * FROM Matiere ORDER BY code");
			elementsMat = new Object[lentgh][tittresMat.length];
			int j = 0;
			while(result.next()) {
				elementsMat[j][0] = result.getString(1);
				elementsMat[j][1] = result.getString(2);
				elementsMat[j][2] = result.getString(3).replace('#', '\n');
				j++;
			}
			result.close();
			
			ResultSet res2 = state.executeQuery("SELECT * FROM Enseignant");
			int lentgh2 = 0;
			
			while(res2.next())
				lentgh2++;
			
			ResultSet result2 = state.executeQuery("SELECT * FROM Enseignant ORDER BY noms");
			elementsEns = new Object[lentgh2][tittresEns.length];
			int i = 0;
			while(result2.next()) {
				elementsEns[i][0] = result2.getString(1);
				elementsEns[i][1] = result2.getString(2);
				elementsEns[i][2] = result2.getString(3);
				elementsEns[i][3] = result2.getString(4);
				elementsEns[i][4] = result2.getString(5);
				elementsEns[i][5] = result2.getString(6);
				elementsEns[i][6] = result2.getString(7);
				i++;
			}
			result2.close();
			state.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println(e);
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.setLayout(new BorderLayout());
		JPanel pTitre1 = new JPanel();
		pTitre1.setPreferredSize(new Dimension(457, 37));
		pTitre1.add(titre1);
		pTitre1.setBackground(new Color(181, 230, 29));
		JPanel pTitre2 = new JPanel();
		pTitre2.setPreferredSize(new Dimension(460,  37));
		pTitre2.setBackground(new Color(181, 230, 9));
		JPanel panTitres = new JPanel();
		pTitre2.add(titre2);
		Font police = new Font("Arial", Font.ITALIC, 20);
		panTitres.setPreferredSize(new Dimension(965, 45));
		panTitres.setLayout(new FlowLayout());
		titre1.setPreferredSize(new Dimension(393, 30));
		titre1.setFont(police);
		titre1.setHorizontalAlignment(JLabel.CENTER);
		titre2.setPreferredSize(new Dimension(393, 30));
		titre2.setFont(police);
		titre2.setHorizontalAlignment(JLabel.CENTER);
		JPanel panTemp2 = new JPanel();
		panTemp2.setPreferredSize(new Dimension(25,  30));
		panTitres.setBackground(new Color(153, 217, 234));
		panTitres.add(pTitre2);
		panTitres.add(panTemp2);
		panTitres.add(pTitre1);
		this.getContentPane().add(panTitres, BorderLayout.NORTH);
		
		JPanel panBoutEns = new JPanel();
		panBoutEns.setPreferredSize(new Dimension(470, 100));
		panBoutEns.setBorder(BorderFactory.createTitledBorder("Gestion des enseignants"));
		panBoutEns.add(boutEnEns);
		panBoutEns.add(boutSupEns);
		panBoutEns.add(boutCherEns);
		panBoutEns.add(boutModifEns);
		panBoutEns.setBackground(new Color(153, 217, 234));
		
		JPanel panBoutMat = new JPanel();
		panBoutMat.setPreferredSize(new Dimension(445, 100));
		panBoutMat.setBorder(BorderFactory.createTitledBorder("Gestion des matières"));
		panBoutMat.add(boutEnMat);
		panBoutMat.add(boutSupMat);
		panBoutMat.add(boutCherMat);
		panBoutMat.add(boutModifMat);
		panBoutMat.setBackground(new Color(153, 217, 234));
		
		JPanel panBas = new JPanel();
		panBas.setPreferredSize(new Dimension(1000, 100));
		panBas.add(panBoutEns);
		JPanel panTemp = new JPanel();
		panTemp.setPreferredSize(new Dimension(15, 100));
		panTemp.setBackground(new Color(153, 217, 234));
		panBas.add(panTemp);
		panBas.add(panBoutMat);
		panBas.setBackground(new Color(153, 217, 234));
		
		this.getContentPane().add(panBas, BorderLayout.SOUTH);
		
		tableau1 = new JTable(elementsEns,  tittresEns) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer,  row,  column);
				Color c1 = new Color(220, 220, 220);
				Color c2 = new Color(255, 255, 255);
				if(!c.getBackground().equals(getSelectionBackground())) {
					Color couleur = (row % 2 == 0 ? c1 : c2);
					c.setBackground(couleur);
					couleur = null;
				}
				return c;
			}
		};
		tableau1.setEnabled(false);
		tableau1.setBackground(new Color(245, 245, 245));
		tableau1.setForeground(Color.black);
		tableau1.setRowMargin(20);
		tableau1.setRowHeight(40);
		tableau1.setFont(new Font("Times New Roman",  Font.BOLD,  15));
		tableau2 = new JTable(elementsMat, tittresMat){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer,  row,  column);
				Color c2 = new Color(220, 220, 220);
				Color c1 = new Color(255, 255, 255);
				if(!c.getBackground().equals(getSelectionBackground())) {
					Color couleur = (row % 2 == 0 ? c1 : c2);
					c.setBackground(couleur);
					couleur = null;
				}
				return c;
			}
		};
		tableau2.setEnabled(false);
		tableau2.setBackground(new Color(245, 245, 245));
		tableau2.setForeground(Color.black);
		tableau2.setRowMargin(20);
		tableau2.setRowHeight(40);
		tableau2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableau1.setPreferredSize(new Dimension(592, 470));
		this.getContentPane().add(new JScrollPane(tableau1), BorderLayout.WEST);
		tableau2.setPreferredSize(new Dimension(393, 470));
		panBas.setBackground(new Color(153, 217, 234));
		this.getContentPane().add(new JScrollPane(tableau2), BorderLayout.EAST);
		
		this.getContentPane().add(panTitres, BorderLayout.NORTH);
		
		boutEnEns.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireEnEns(null,  "Enregistrement",  true);
				dispose();
				new Fenetre();
			}
		});
		
		boutEnMat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireEnMat(null,  "Enregistrement",  true);
				dispose();
				new Fenetre();
			}
		});
		
		boutSupEns.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireSupEns(null,  "Suppression",  true);
				dispose();
				new Fenetre();
			}
		});
		
		boutSupMat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireSupMat(null,  "Suppression",  true);
				dispose();
				new Fenetre();
			}
		});
		
		boutCherEns.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireRechEns(null, "Recherche", true);
			}
		});
		
		boutCherMat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireRechMat(null, "Recherche", true);
			}
		});
		
		boutModifEns.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireModifEns(null,  "Modification d'infos",  true);
				dispose();
				new Fenetre();
			}
		});
		
		boutModifMat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FormulaireModifMat(null,  "Modification de matière",  true);
				dispose();
				new Fenetre();
			}
		});
	}

		
}
