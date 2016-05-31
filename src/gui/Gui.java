package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import engine.Engine;
import engine.Smer;
import rang.RangElement;
import rang.RangLista;

public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JLabel pojedeno;
	private JComboBox<Object> profesor;
	private JComboBox<Object> brzina;
	private JButton nova;
	private JButton[][] dugmici;
	private ImageIcon snake;
	private ImageIcon teloZmije;
	private ImageIcon trenProfesor;
	private ImageIcon glavaZmije;
	private Engine engine = new Engine();
	private Timer timer;
	private JLabel[] rang;
	private RangLista ranking = new RangLista();
	
	public Gui()
	{
		initialize();
	}
	
	private void initialize()
	{
		setIconsAndSpeed();
		setHeader();
		setCenter();
		setEast();
		loadRangListaFromFile();
		
		addListeners();
		osveziGui();
		setWindow();
		setRanking();
		
		setTimerAndPlay();
	}
	
	private void setIconsAndSpeed()
	{
		snake = new ImageIcon(getClass().getResource("/snake.png"));
		teloZmije = new ImageIcon(getClass().getResource("/krug.png"));
		trenProfesor = new ImageIcon(getClass().getResource("/Ana.jpg"));
		glavaZmije = new ImageIcon(getClass().getResource("/imi.jpg"));
	}
	
	private void addListeners()
	{
		addKeyListener(new MyKeyListener(engine));
		requestFocusInWindow();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		profesor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				@SuppressWarnings("unchecked")
				JComboBox<Object> p = (JComboBox<Object>) e.getSource();
				
				trenProfesor = new ImageIcon(getClass().getResource("/" + p.getSelectedItem().toString() + ".jpg"));
				osveziGui();
				requestFocusInWindow();
			}
		});
		
		brzina.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				@SuppressWarnings("unchecked")
				JComboBox<Object> b = (JComboBox<Object>) e.getSource();
				int speed = 0;
				
				switch (b.getSelectedIndex())
				{
					case 0:
						speed = 1000;
						break;
					
					case 1:
						speed = 500;
						break;
					
					case 2:
						speed = 200;
						break;
					
					case 3:
						speed = 100;
						break;
					
					default:
						break;
				}
				
				timer.setDelay(speed);
				requestFocusInWindow();
			}
		});
		
		nova.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				showDialogAndRegister();
				setRanking();
				engine.initialize();
				osveziGui();
				requestFocusInWindow(); // NAJBITNIJA STVAR!!!!!!!!!!!!!
			}
		});
		
	}
	
	private void setWindow()
	{
		setResizable(false);
		setSize(new Dimension(800, 650));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
	
	private void setEast()
	{
		JPanel east = new JPanel(new FlowLayout());
		
		JLabel osobaLabel = new JLabel("Izaberi osobu: ");
		JLabel brzinaLabel = new JLabel("Izaberi brzinu: ");
		
		profesor = new JComboBox<Object>(new String[]
		{ "Ana", "Marina", "Srdjan" });
		brzina = new JComboBox<Object>(new String[]
		{ "1 sec", "0.5 sec", "0.2 sec", "0.1 sec" });
		
		JButton zmija = new JButton();
		
		zmija.setBorder(null);
		zmija.setPreferredSize(new Dimension(150, 150));
		zmija.setIcon(snake);
		
		nova = new JButton("Nova");
		
		JLabel rangLista = new JLabel("RANG LISTA");
		rangLista.setFont(new Font("Helvetica", Font.BOLD, 30));
		
		JPanel lista = new JPanel(new GridLayout(10, 1));
		rang = new JLabel[10];
		
		for (int i = 0; i < 10; i++)
		{
			rang[i] = new JLabel();
			rang[i].setPreferredSize(new Dimension(200, 30));
			rang[i].setFont(new Font("Arial", Font.ITALIC, 20));
			lista.add(rang[i]);
		}
		
		east.setPreferredSize(new Dimension(200, 600));
		
		east.add(osobaLabel);
		east.add(profesor);
		east.add(brzinaLabel);
		east.add(brzina);
		east.add(zmija);
		east.add(nova);
		east.add(rangLista);
		east.add(lista);
		
		getContentPane().add(east, BorderLayout.EAST);
	}
	
	private void setCenter()
	{
		JPanel center = new JPanel(new GridLayout(15, 15));
		center.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		dugmici = new JButton[15][15];
		
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{
				dugmici[i][j] = new JButton();
				dugmici[i][j].setBackground(Color.WHITE);
				dugmici[i][j].setPreferredSize(new Dimension(40, 40));
				
				center.add(dugmici[i][j], 15 * i + j);
			}
		
		getContentPane().add(center, BorderLayout.CENTER);
		
	}
	
	private void setHeader()
	{
		JPanel header = new JPanel(new FlowLayout());
		
		pojedeno = new JLabel("Pojeli ste 0 profesora!");
		header.add(pojedeno);
		
		getContentPane().add(header, BorderLayout.NORTH);
	}
	
	private void loadRangListaFromFile()
	{
		Scanner scan = null;
		
		try
		{
			scan = new Scanner(new File("RangLista.txt"));
			
			while (scan.hasNext())
				ranking.dodaj(new RangElement(scan.nextLine(), Integer.parseInt(scan.nextLine())));
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (scan != null) scan.close();
		}
	}
	
	private void setRanking()
	{
		RangElement tren = null;
		
		if (ranking.getPrvi() != null)
			tren = ranking.getPrvi();
		
		for (int i = 0; i < 10 && tren != null; tren = tren.getSledeci(), i++)	
			rang[i].setText(i + 1 + ". " + tren.getIme() + " - " + tren.getPojedeno());
		
	}
	
	private void showDialogAndRegister()
	{
		String ime = JOptionPane.showInputDialog(null, "Unesite ime: ");
		RangElement elem = new RangElement(ime, engine.getPojedeno());
		BufferedWriter bw = null;
		
		ranking.dodaj(elem);
		
		try
		{
			bw = new BufferedWriter(new FileWriter("RangLista.txt", true));
			bw.append(elem.getIme() + "\n" + elem.getPojedeno() + "\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				if (bw != null) bw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	private void osveziGui()
	{
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				switch (engine.getTabla(i, j))
				{
					case PROFESOR:
						dugmici[i][j].setIcon(trenProfesor);
						break;
					
					case TELO:
						dugmici[i][j].setIcon(teloZmije);
						break;
					
					case GLAVA:
						dugmici[i][j].setIcon(glavaZmije);
						break;
					
					case PRAZNO:
						dugmici[i][j].setIcon(null);
						break;
					
					default:
						break;
				}
		
		pojedeno.setText("Pojeli ste " + engine.getPojedeno() + " profesora!");
		
		if (engine.getDaLiJeKraj())
		{
			showDialogAndRegister();
			setRanking();
			
			int kraj = JOptionPane.showConfirmDialog(this, "Da li zelite novu igru?", "Kraj igre!",
					JOptionPane.YES_NO_OPTION);
			if (kraj == JOptionPane.YES_OPTION)
			{
				engine.initialize();
				osveziGui();
			}
			else
				System.exit(0);
			
		}
	}
	
	private void setTimerAndPlay()
	{
		timer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (engine.getTrenutniSmer() != Smer.NEPOZNATO) engine.pomeriSe();
				osveziGui();
			}
		});
		
		timer.start();
	}
	
}
