import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


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
	private int brzinaPomeranja;
	
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

		addListeners();
		osveziGui();
		setWindow();
		
		play();
	}

	private void setIconsAndSpeed()
	{
	    snake = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("./images/zmija2.jfif"));
	    teloZmije = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("./images/krug.png"));
	    trenProfesor = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("./images/Ana.jpg"));
	    glavaZmije = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("./images/imi.jpg"));
	    brzinaPomeranja = 1000;
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
				JComboBox<Object> p = (JComboBox<Object>)e.getSource();
				
				trenProfesor = new ImageIcon(getClass().getResource("./images/" + p.getSelectedItem().toString() + ".jpg"));
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
				JComboBox<Object> b = (JComboBox<Object>)e.getSource();
				
				switch (b.getSelectedIndex())
				{
					case 0:
						brzinaPomeranja = 1000;
						break;
						
					case 1:
						brzinaPomeranja = 500;
						break;
						
					case 2:
						brzinaPomeranja = 200;
						break;
						
					case 3:
						brzinaPomeranja = 100;
						break;
					
					default:
						break;
				}
				
				requestFocusInWindow();
			}
		});
		
		nova.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
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
		
		profesor = new JComboBox<Object>(new String[]{"Ana", "Marina", "Srdjan"});
		brzina = new JComboBox<Object>(new String[]{"1 sec", "0.5 sec", "0.2 sec", "0.1 sec"});
		
		JButton zmija = new JButton();
		
		zmija.setBackground(Color.WHITE);
		zmija.setBorder(null);
		zmija.setPreferredSize(new Dimension(190, 190));
		zmija.setIcon(snake);
		
		nova = new JButton("Nova");
		
		east.setPreferredSize(new Dimension(200, 600));
		east.add(osobaLabel);
		east.add(profesor);
		east.add(brzinaLabel);
		east.add(brzina);
		east.add(zmija);
		east.add(nova);

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
			int kraj = JOptionPane.showConfirmDialog(this, "Da li zelite novu igru?", "Kraj igre!", JOptionPane.YES_NO_OPTION);
			
			if (kraj == JOptionPane.YES_OPTION)
			{
				engine.initialize();
				osveziGui();
			}
			else
				System.exit(0);
		}
	}

	private void play()
	{
		while (true)
		{
			engine.pomeriSe();
			osveziGui();
			
			try
			{
				Thread.sleep(brzinaPomeranja);
			}
			catch (InterruptedException e)
			{
				
			}
		}
		
	}
}
