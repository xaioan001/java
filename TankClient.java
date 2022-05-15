import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
public class TankClient extends Frame{
	
	public static final int GAME_WIDTH=1000;
	public static final int GAME_HEIGHT=800;
	
	Tank myTank=new Tank(50,50,true,Tank.Direction.STOP,this);
	
	//Tank enemyTank=new Tank(200,200,false,this);//画出敌方坦克
	
	List<Explode>explode=new ArrayList<Explode>();
	List<Missile>missiles=new ArrayList<Missile>();
	List<Tank>tanks=new ArrayList<Tank>();
	
	
	
	Image offScreenImage=null;
	
	//public Object VK_LEFT;
	 
	public void paint(Graphics g) {
		
		g.drawString("missiles count:"+missiles.size(), 400, 60);
		g.drawString("explodes count:"+explode.size(),20,60);
		g.drawString("tanks   count:"+tanks.size(),800,60);//添加坦克数量
		for(int i=0;i<missiles.size();i++) {
			Missile m=missiles.get(i);
			//if(!m.isLive())missiles.remove(m);
			m.hitTanks(tanks);
			
			m.hitTank(myTank);
			m.draw(g);
		}
		//画出爆炸 
		for(int i = 0;i<explode.size();i++) {
			Explode e = explode.get(i);
			e.draw(g);
		}
		//myTank.draw(g);
		//enemyTank.draw(g);//调用并画出敌方坦克
		for(int i=0;i<tanks.size();i++) {//添加敌方坦克
			Tank t=tanks.get(i);
			t.draw(g);
		}
		myTank.draw(g);
	}
	
	
	public void update (Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.YELLOW);//背景颜色
		gOffScreen.fillRect(0,0,1000,800);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0,0, null); 
	}
	public void lanuchFrame() {
		
		for(int i=0;i<10;i++) {//敌人的坦克
			tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this ));
		}
		
		
		this.setLocation(500,400);
		//窗口大小
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("坦克大战");
		//关闭窗口 匿名类
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		//背景颜色
		this.setBackground(Color.YELLOW);

		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		
		new Thread(new paintThread()).start();
	}
	
	
	
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.lanuchFrame();
	}
	
	
	//线程类
	private class paintThread implements Runnable{
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(150);//每100画一次
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



































