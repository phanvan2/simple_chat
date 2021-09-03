package lab4;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class GuiPacket extends JPanel implements Serializable {
	private static final long serialVersionUID = 1L;

	public GuiPacket(packet packe) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel userlb = new JLabel(packe.getUser());
		JPanel panel1 = new JPanel(new GridLayout(1, 2));
		JLabel datelb = new JLabel(packe.getDate());
		JTextArea text = new JTextArea(packe.getMess());
		
		Font font = new Font("Arial", Font.BOLD, 16);
		userlb.setForeground(Color.BLUE);
		userlb.setFont(font);
		
		panel1.add(userlb);
		panel1.add(datelb);
		
		if(packe.getIcon() != null) {
			try {
				
				byte[] img = Base64.getDecoder().decode(packe.getIcon());
				ImageIcon  img1 = new ImageIcon(img);
				JLabel imageicon = new JLabel(img1);
				
				panel.add(imageicon , BorderLayout.SOUTH);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		if( packe.getFile() != null) {
//			JButton btnFile = new JButton("file: "+ packe.getFile().getName());
//			panel.add(btnFile, BorderLayout.SOUTH);
//		}

		panel.add(text, BorderLayout.CENTER);
		panel.add(panel1, BorderLayout.NORTH);
		
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	public static void main(String []args) {
		JFrame b = new JFrame();
		packet p = null;
		try {
			File file = new File("C:\\Users\\Admin\\Downloads\\IMG_3715.JPG"); 
			BufferedImage image = ImageIO.read(new File("C:\\Users\\Admin\\Downloads\\IMG_3715.JPG"));
			
			ImageIcon im = new ImageIcon(image.getScaledInstance(200, 80, image.SCALE_SMOOTH));
			
			p = new packet("helolo",
							"mess", 
							" 2/12/210",
							"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",
							file );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GuiPacket c = new GuiPacket(p);
		b.add(c);
		b.setVisible(true);
		b.setSize(500,500);
	}
	
}
class CustomCell implements ListCellRenderer{
	boolean ok = true ;
	int index1 = -1; 
	@Override
	public JPanel getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
		// TODO Auto-generated method stub
		JPanel panel = new GuiPacket((packet)value);


		if(isSelected) {
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.print("he;;o");

			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.print("he;;o");

			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.print("he;;o");

			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.print("he;;o");

			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.print("he;;o");
				
			}
		});
		}
		return panel;

	}
	
	
	
}


