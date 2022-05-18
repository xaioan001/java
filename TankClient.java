import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
public class TankClient extends Frame{
	
	public static final int GAME_WIDTH=1000;
	public static final int GAME_HEIGHT=800;
	
	Tank myTank=new Tank(50,50,true,Tank.Direction.STOP,this);
	
	Wall w1=new Wall(300,200,30,150,this),w2=new Wall(500,100,300,60,this);//ding yi liang du qiang
	
	//Tank enemyTank=new Tank(200,200,false,this);//�����з�̹��
	
	List<Explode>explode=new ArrayList<Explode>();
	List<Missile>missiles=new ArrayList<Missile>();
	List<Tank>tanks=new ArrayList<Tank>();
	
	
	
	Image offScreenImage=null;
	 
	Blood  b=new Blood();
	
	//public Object VK_LEFT;
	 
	public void paint(Graphics g) {
		
		g.drawString("missiles count:"+missiles.size(), 400, 60);
		g.drawString("explodes count:"+explode.size(),20,60);
		g.drawString("tanks   count:"+tanks.size(),800,60);//���̹������
		g.drawString("tank       life:"+myTank.getLife(),20,100);//��ʾ����ֵ
        if(tanks.size()<=0) {
        	for(int i=0;i<10;i++) {
    			tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this ));
    		}
        }
		
		for(int i=0;i<missiles.size();i++) {
			Missile m=missiles.get(i);
			//if(!m.isLive())missiles.remove(m);
			m.hitTanks(tanks);
			
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		//������ը 
		for(int i = 0;i<explode.size();i++) {
			Explode e = explode.get(i);
			e.draw(g);
		}
		//myTank.draw(g);
		//enemyTank.draw(g);//���ò������з�̹��
		for(int i=0;i<tanks.size();i++) {//��ӵз�̹��
			Tank t=tanks.get(i);
			t.collidesWithWall(w1);//��̹��ײǽ
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		myTank.draw(g);
		myTank.eat(b);
		w1.draw(g);//��������ǽ
		w2.draw(g);
		b.draw(g);
		
	}
	
	
	public void update (Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.YELLOW);//������ɫ
		gOffScreen.fillRect(0,0,1000,800);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0,0, null); 
	}
	public void lanuchFrame() {
		
		for(int i=0;i<10;i++) {//���˵�̹��
			tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this ));
		}
		
		
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
		this.setBackground(Color.YELLOW);

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



































