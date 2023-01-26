/*
 * File: BlankKarel.java
 * ---------------------
 * This class is a blank one that you can change at will.
 */
import stanford.karel.*;
import java.awt.*;
public class BlankKarel extends SuperKarel {
	static int steps = 0,row = 1, col = 1;
	public void run()
	{
		setBeepersInBag(1000);
		scanArea();
		if((row==1 && col==1) || (row==1 && col==2) || (row==2 && col ==1)) {//One chamber
			reset();return;
		}
		if((row==2 && col>0) || (row> 0 && col==2) || (row==1 && col>0) || (row> 0 && col==1)){//Special cases
			specialCase(); printResult(); reset();
		}
		 else if(((row%2==0 && col%2==0) || (row%2==1 && col%2==1)) && col==row) {//Even == Even  or  Odd == Odd
			diagonalShape(); printResult(); reset();
		}
		else if((row%2==0 && col%2==0)) {  // Even even
			even_Even(); printResult(); reset();
		}
		else if((row%2==1 && col%2==1)) { // Odd odd
			odd_Odd(); printResult(); reset();
		}
		else if((row%2!=0 && col%2==0) || (row%2==0 && col%2!=0)){// Even Odd
			even_odd(); printResult(); reset();
		}
	}
	private void printResult(){
		System.out.println("Row: " + row + "\nCol: " + col + "\nSteps: " + steps + "\nBeeper used: " + (1000-getBeepersInBag()));
	}
	private void reset(){
		row = 1; col = 1; steps = 0;
	}
	private void scanArea(){
		while (frontIsClear()){
			move(); col++;
		}
		turnLeft();
		while (frontIsClear()){
			move(); row++;
		}}
	private void zigZagShape(){ //Special case to handle 2*3==> 3 chambers // 2*4==> 4 chambers
		putBeeper(); turnLeft(); move(); turnLeft(); move(); putBeeper(); turnLeft(); move(); turnRight();
		if(frontIsClear()) {
			move(); turnAround();
		}}
	private void specialCase(){
		if(row==2&&col==2){
			putBeeper(); turnLeft(); move(); turnLeft(); move(); putBeeper();
		}
		else if(row<=4 && col == 2) {
			for (int i = 0; i < row - 2; i++) {
				zigZagShape();
			}
			if (row % 2 == 1)
				putBeeper();
		}
		else if(row==3 && col == 1){
			turnLeft(); turnLeft(); move(); putBeeper();
		}
		else if(row==4 && col == 1){
			turnLeft(); turnLeft(); move(); putBeeper();move();putBeeper();
		}
		else if((row>4 && row<9) && (col==2 || col==1)) {
			int t = 2;
			while (t-- > 0) {
				if (col == 2) {
					turnLeft(); move(); turnLeft();
				} else {
					turnAround();
					if(t==0)
					 return;}
				if(col%2==0) {
					for (int i = 0; i <= row / 2; i++) {
						if (i % 2 == 0)
							fill(2);
						else
 						  move();
					}
					move();}
				else{
					for (int i = 0; i <= row/2; i++) {
						if (i % 2 == 0)
							fill(1);
						else
							move();

					}move();}
				putBeeper();
			}}
		else if(row>8 && col==2 || col == 1){
			if(col==2) {
				turnLeft(); move(); turnLeft(); putBeeper();
			}
			else{
				turnAround(); putBeeper();
			}
			int height = row;
			while(height%4!=0){
				height--; fill(1);
			}
			if(col==2)
				moveToFill(2,height);
			else
				moveToFill(1, height);
		}
		else if(col<=4 && row == 2) {
			for (int i = 0; i < col - 2; i++) {
				zigZagShape2(); turnRight(); turnRight(); move(); turnLeft();
				if(i==0) {
					move(); turnRight();
				}}
			if (col % 2 == 1)
				putBeeper();
		}
		else if(col==3 && row == 1) {
			turnLeft(); move(); putBeeper();
		}
		else if(col==4 && row == 1) {
			turnLeft(); move(); putBeeper();move();putBeeper();
		}
		else if(col>8 && row==2 || row == 1){
			if(row==2) {
				turnLeft();
				if(col%2==1)
				 putBeeper();
			}
			else{
				turnLeft(); putBeeper();
			}
			int height = col;
			while(height%4!=0){
				height--; fill(1);
			}
			if(row==2)
				moveToFill2(2,height);
			else
				moveToFill2(1, height);
		}}
	private void zigZagShape2(){
		putBeeper(); turnLeft(); move(); turnLeft(); move(); putBeeper();
	}
	private void fill(int n){
		if(n==1) {
			move();putBeeper();}
		else{
			putBeeper();move();
		}}
	private void moveToFill(int n, int dis){
		if(n==2){
				turnToMove(dis); turnLeft(); move(); turnLeft(); putBeeper(); turnToMove(dis);
			while(frontIsClear()){
				move();putBeeper();
			}}
		else{
			turnToMove(dis);
		}}
	private void moveToFill2(int n, int dis){
		if(n==2){
			if(col%2==0)
			 putBeeper(); turnToMove2(dis); turnLeft(); move(); turnLeft(); putBeeper(); turnToMove2(dis);
			while(frontIsClear()){
				move(); putBeeper();
			}}
		else{
			turnToMove2(dis);
		}}
	private void turnToMove(int dis){
		for (int i = 0; i < dis-(row/4); i+=(row / 4)) {
			for (int j = 0; j < (row / 4); j++) {
				move();
			}
			putBeeper();
		}
	}
	private void turnToMove2(int dis){
		for (int i = 0; i < dis-(col/4); i+=(col / 4)) {
			for (int j = 0; j < (col / 4); j++) {
				move();
			}
			putBeeper();
		}}
	private void even_odd(){
		if(row%2==0) {
			emptyLine(1); plusShape(1,0,1,true,true);
			turnAround(); plusShape(2,1,0,false,true);
			curve(); plusShape(1,0,1,true,true);
			turnAround(); plusShape(2,1,0,false,true);}
		else {
			emptyLine(0); plusShape(1,1,0,true,true);
			curve(); plusShape(2,0,1,true,true);
			turnAround(); plusShape(1,1,0,false,true);
			curve(); plusShape(2,1,1,true,false);
		}}
	private void odd_Odd(){
		emptyLine(0); plusShape(1,0,0,true,true);
		turnAround(); plusShape(2,0,0,false,true);
		turnAround(); plusShape(1,0,0,false,true);
	}
	private void even_Even(){
		emptyLine(1); plusShape(1,1,1, true, true);
		curve(); plusShape(2,1,1, true, true);
		curve(); plusShape(1,1, 1,true, true);
		curve(); plusShape(2,1,1, true, true);
	}
	private void plusShape(int n, int m1,int m2, boolean flagA, boolean flagB){
		if(n==1){
			rowMoves(m1,flagA); turnRight(); colMoves(m2,flagB);
		}
		else if(n==2) {
			colMoves(m1,flagA); turnRight(); rowMoves(m2, flagB);
		}
	}
	private void emptyLine(int n){
		turnAround(); colMoves(n,false); turnRight(); putBeeper();
	}
	private void curve(){
		turnLeft();move();turnLeft();putBeeper();
	}
	private void rowMoves(int n, boolean flag){
		for (int i = 0; i < (col/2)-n; i++) {
			move();
			if(flag)
				putBeeper();
		}}
	private void colMoves(int n, boolean flag){
		for (int i = 0; i < (row/2)-n; i++) {
			move();
			if(flag)
				putBeeper();
		}}
	private void diagonalShape(){
		turnLeft();
		while(frontIsClear()) {
			putBeeper();move();turnLeft();move();turnRight();
		}
		putBeeper();turnAround();
		while (frontIsClear()){
			move();
		}
		turnAround();
		while(frontIsClear()){
			if(beepersPresent()) {
				crossLine();continue;
			}
			putBeeper();crossLine();
		}
		putBeeper();}
	private void crossLine(){
		move();turnRight();move();turnLeft();}
	@Override
	public void move(){
		super.move(); steps++;}
}