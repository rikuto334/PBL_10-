package group10;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * G10_Leader - a robot by (your name here)
 */
public class G10_Leader extends TeamRobot
{
	/**
	 * run: G10_Leader's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			setTurnGunRight(360);
			ahead(150);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double bulE = 2;//弾丸エネルギー
		double aed = getHeadingRadians() + e.getBearingRadians();//敵の絶対角度
		double theta = aed - (e.getHeadingRadians() - 90);//敵の進行方向に垂線を下した時の角度
		double phi;//発射する絶対角度(radian)
		double omega,sigma,psi;//砲塔の回転角度(degree)

		String wall = "sample.Walls";
		String ally = "ys.Test2";

		if(e.getName().contains(wall)) {
			phi = Math.asin(e.getVelocity() * Math.cos(theta) / (20 - 3 * bulE)) + aed;

			omega = Math.toDegrees(phi) - getGunHeading();
			sigma = omega + 360;
			if (Math.abs(omega) > Math.abs(sigma)) {
				psi = sigma;
			} else {
				psi = omega;
			}
			turnGunRight(psi % 360);
			fire(bulE);
			turnGunLeft(45);
		}else if(e.getName().contains(ally)){
			psi = -1;
			setTurnGunRight(45);
		}else{
			psi = 0;
			turnGunRight(psi % 360);
			fire(bulE);
			turnGunLeft(45);
		}


		double x = getX();//戦車の座標
		double y = getY();//戦車の座標
		double a,b;//壁からの距離(符号付)
		double alpha,beta,gamma,delta;
		double X,Y;//斥力のベクトル
		double Fw = 100000000;//壁の斥力
		double Fe = 1000000;//敵の斥力

		if(x > 400){
			a = x - 800;
		}else{
			a = x;
		}

		if(y > 400){
			b = y - 800;
		}else{
			b = y;
		}


		X = -Fe*Math.sin(aed)/(e.getDistance()*e.getDistance()) + Fw/(a*a*a);
		Y = -Fe*Math.cos(aed)/(e.getDistance()*e.getDistance()) + Fw/(b*b*b);
		alpha = Math.atan2(X,Y);
		beta = (Math.toDegrees(alpha) - getHeading()) % 360;
		gamma = beta + 360;

		if(Math.abs(beta) > gamma){
			delta = gamma;
		}else{
			delta = beta;
		}


		System.out.print("psi = ");
		System.out.println(psi);




		setAdjustGunForRobotTurn(true);
		setTurnRight(delta);
		ahead(100);
		setAdjustGunForRobotTurn(false);
	}

	public void onHitRobot(HitRobotEvent e){
		back(100);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		if(e.getBearing() < 0){
			turnRight(180 + e.getBearing());
		}else{
			turnLeft(180 - e.getBearing());
		}

	}
}
