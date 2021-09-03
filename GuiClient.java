package lab4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.*;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;




public class GuiClient extends JFrame implements ActionListener, MouseListener,Serializable, KeyListener {
	
	private static final long serialVersionUID = -2253155641100317203L;
	private Vector<packet> listData = new Vector<packet>();
	private JList log;
	private JTextField mess = new JTextField();
	
	private JScrollPane scroll ;
	private JTextField txtServerName = new JTextField(10);
	private JTextField txtUser ;
	private JLabel portlb = new JLabel();
	private JButton send = new JButton("Gửi");
	private JButton delete = new JButton("Xóa Lịch sử");
	private JPanel panelControl = new JPanel(new GridLayout(4,2));
	private JPanel panelTop = new JPanel(new BorderLayout());

	

//	packet packet;
	
	public Client client;

	private JPanel contentPane;
	
	
	private String username;
	private Thread thread 	= null;
	private boolean mysend 	= false;
	
	
	private  JPanel imagePanel 	= new JPanel();
	JPanel panelfile;
	private JButton sendImg 	= new JButton("Gửi ảnh");
	private JButton sendFile 	= new JButton("Gửi File");
	private int port = Constant.port;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiClient frame = new GuiClient("phan");
					
				} catch (Exception e) {
				
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiClient(String username) {
		super( username);
		
		client = new Client(); 
		this.username = username;
		txtServerName.setText("localhost");
		txtUser = new JTextField();
		txtUser.setText(username);

		panelControl.add(new JLabel("Server Name"));
		panelControl.add(txtServerName);
		panelControl.add(new JLabel("User :"));
		panelControl.add(txtUser);
		JLabel lblPort = new JLabel("Port :");
		panelControl.add(lblPort);
		portlb.setText("");
		panelControl.add(portlb);
		

		panelTop.add(panelControl, BorderLayout.EAST);
		log = new JList(listData);
		
		log.setCellRenderer(new CustomCell());
		scroll = new JScrollPane(log);
	
		scroll.setPreferredSize(getPreferredSize());
		scroll.createVerticalScrollBar();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel panel = new JPanel(new BorderLayout());
		mess.addKeyListener(this);
		panel.add(mess,BorderLayout.CENTER);
		panel.add(send, BorderLayout.EAST);

		
		
		// tao panel gui anh và file
		imagePanel.setLayout(new FlowLayout());
		imagePanel.add(sendFile);
		sendFile.addActionListener(this);
		imagePanel.add(delete);
		delete.addActionListener(this);
		imagePanel.add(sendImg);
		sendImg.addActionListener(this);

		log.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JList theList = (JList) e.getSource(); 

				int index = theList.locationToIndex(e.getPoint()); 
				packet packetSelect = (packet)theList.getModel().getElementAt(index);
				if( packetSelect.getFile() != null) {
					File file = packetSelect.getFile(); 
					if(JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn tải file: "+ file.getName()) == 0) 
							dowloadfile(file);
				}
						
				
			}
			
		});
		
		panel.add(imagePanel, BorderLayout.SOUTH);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelTop, BorderLayout.NORTH);
		contentPane.add(scroll,BorderLayout.CENTER);
		contentPane.add(panel,BorderLayout.SOUTH);

		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 466);
		send.addActionListener(this);
		
		try {// set icon giao dien---------------------------
			Image iconmes = ImageIO.read(new File("D:\\download\\logo_mess.png"));
			this.setIconImage(iconmes); 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		
		}
		GClient_receirve();
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// gui tin nhan ------------------------
		if(e.getActionCommand().equals("Gửi")) {
			GClient_sendMess();
			
			log.updateUI();
			mess.setText("");
			
		}

		// gui file ----------------------------		
		if(e.getActionCommand().equals("Gửi File")) {
			GClient_sendFile();
			log.updateUI();
			mess.setText("");

			
		}
		
// xóa lich sử --------------------------------------------------
		if(e.getActionCommand().equals("Xóa Lịch sử")) {
			resetData();
			log.updateUI();
			mess.setText("");
			
		}
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent e1) {}
	@Override
	
	
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
			GClient_sendMess();
	
			  log.updateUI();
			  mess.setText("");
			  
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
// load lại giao dien gửi file -----------------------
	public void loadfileUI(packet pacSendfile) {
	
	}
// tao file  moi ---------------------------------------------------
	
	public void dowloadfile(File file) {
		File file1 = file;
		File file2 = new File("C:\\Users\\Admin\\Downloads\\"+file1.getName());
		try {
			file2.createNewFile();

			byte[] buffer = new byte[1024];
			int length ; 
			InputStream inputFile = new FileInputStream(file1);
			
			OutputStream outFile = new FileOutputStream(file2);
			while((length = inputFile.read(buffer)) > 0) {
				outFile.write(buffer, 0, length);
			}
			JOptionPane.showMessageDialog(null, "Tải thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Tải thất bại");

		}
	}
	// reset giao dien tin nhắn ------------------------------	
		public void resetData() {
			listData.removeAllElements();
		}
	// client receive mess
	public void GClient_receirve() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					packet packetReceive = client.receiveMess();
					if( packetReceive != null) {
						listData.add(packetReceive);
						log.updateUI();
					}
				}
				
			}
		}).start();
	}
	
	// get thời gian hiện tại 
	public String getDateTime() {
		String d = String.valueOf( java.time.LocalDate.now());
		String h = String.valueOf(java.time.LocalTime.now());
		String[] timeArr = h.split("\\.");
		System.out.print(h);
		System.out.println(timeArr.length);
		return d + " " + timeArr[0] ; 
	}

	// gui client gửi tin 
	public void GClient_sendMess() {
		try {

			packet packetSend1 = new packet(username, mess.getText(), getDateTime(), null,null);
			try {
				client.sendMess(packetSend1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				
			}
			packet packetSend = new packet("me", mess.getText(), getDateTime(), null, null);
			listData.add(packetSend);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		
		}
	}

	public void GClient_sendFile() {
		JOptionPane.showMessageDialog(null, "Mời bạn chọn file cần gửi:");
		JFileChooser fc = new JFileChooser();
		int re = fc.showDialog(fc, "Chọn");
		if(re == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile(); 
			packet paket_sendFile = new packet(txtUser.getText() , "vừa gủi một file: " + file.getName(), getDateTime(), null, file);
			packet paket_sendFile1 = new packet("me" , "vừa gủi một file: " + file.getName(), getDateTime(), null, file);

			if(client.sendMess(paket_sendFile))
				listData.add(paket_sendFile1);
		}
	}
}

