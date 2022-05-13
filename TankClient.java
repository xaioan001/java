import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
public class TankClient extends Frame{
	
	public static final int GAME_WIDTH=1000;
	public static final int GAME_HEIGHT=800;
	
	Tank myTank=new Tank(50,50,true,this);
	
	Tank enemyTank=new Tank(200,200,false,this);//�����з�̹��
	
	Explode e=new Explode(80,80,this);  
	List<Missile>missiles=new ArrayList<Missile>();
	
	Image offScreenImage=null;
	
	public Object VK_LEFT;
	 
	public void paint(Graphics g) {
		
		g.drawString("missiles count:"+missiles.size(), 400, 60);
		
		
		for(int i=0;i<missiles.size();i++) {
			Missile m=missiles.get(i);
			//if(!m.isLive())missiles.remove(m);
			m.draw(g);
			m.hitTank(enemyTank);
		}
		e.draw(g);
		myTank.draw(g);
		enemyTank.draw(g);//���ò������з�̹��
	}
	
	
	public void update (Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);//������ɫ
		gOffScreen.fillRect(0,0,1000,800);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0,0, null); 
	}
	public void lanuchFrame() {
		this.setLocation(500,400);
		//���ڴ�С
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("̹�˴�ս");
		//�رմ��� ������
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		//������ɫ
		this.setBackground(Color.BLACK);

		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		
		new Thread(new paintThread()).start();
	}
	
	
	
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.lanuchFrame();
	}
	
	
	//�߳���
	private class paintThread implements Runnable{
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(150);//ÿ100��һ��
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
      
	private class KeyMonitor extends KeyAdapter{
         

		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
            myTank.Keypressed(e);
		}	
	}
}



































