import stanford.karel.SuperKarel;
public class Homework extends SuperKarel {
    private int counter=0;
    private int maxDivision=0;
    private int length =1;
    private int width=1;
    private int twoSides=2;
    @Override
    public void move() {
        super.move();
        counter++;
    }

    public void run() {
        setBeepersInBag(1000);
        checkTheMap();
        System.out.println(counter);
        counter=0;length =1;width=1;twoSides=2;maxDivision=0;
    }
    private void divideToColumn(){
        while(frontIsClear()){
            int max= maxDivision;
            while(max!=0){
                if(max==1){
                    turnLeft();
                    if(frontIsBlocked() && facingWest()){turnAround(); placeRowOfBeepers();goBack();turnAround();}
                    else{placeRowOfBeepers();goBack();}
                }
                if(frontIsClear())move();
                max--;
            }
        }
        goToHome();
    }
    private void goForPlusShape(){
        int theCurrentSide;
        if(facingEast())theCurrentSide=length;
        else theCurrentSide= width;
        if(theCurrentSide%2==0){
            int tempSide=theCurrentSide/2;

            while(theCurrentSide>1){
                theCurrentSide--;
                move();
                if(theCurrentSide ==tempSide+1 || theCurrentSide==tempSide){
                    turnLeft();
                    placeRowOfBeepers();
                    goBack();
                }
            }
        }
        else{
            int tempSide=theCurrentSide/2+1;
            while(theCurrentSide>1){
                theCurrentSide--;
                move();
                if(theCurrentSide==tempSide){
                    turnLeft();
                    placeRowOfBeepers();
                    goBack();
                }
            }
        }
        turnLeft();
        twoSides--;
        if(twoSides>0){goForPlusShape();}
        goToHome();
    }
    private void checkTheMap(){
        while (frontIsClear()){
            move();
            length++;
        }
        turnLeft();
        while(frontIsClear()){
            move();
            width++;
        }
        goToHome();
        if(length <3 || width<3){
            calculateTheSpace();
        }else{
            goForPlusShape();
        }
    }
    private void calculateTheSpace(){
        int theBiggerSide;
        if(length>width)theBiggerSide=length;else{ theBiggerSide=width; turnLeft();}

        for( int i=3;i>1;i--){
            if(theBiggerSide==4){  maxDivision =2;break;}

            if(theBiggerSide%4==0 && theBiggerSide/4> maxDivision && theBiggerSide/4<=4){
                maxDivision =theBiggerSide/4; break;}

            if(theBiggerSide%i==0 && theBiggerSide/i> maxDivision && theBiggerSide/i<=4) maxDivision =i;
        }
        if(maxDivision ==0){
            twoSides--;goForPlusShape();
        }
        else{ divideToColumn(); }
    }
    private void placeRowOfBeepers() {
        if(beepersPresent()){
            move();
            if(beepersPresent())return;
        }
        putBeeper();
        while (frontIsClear()) {
            move();
            if(!beepersPresent())putBeeper();
        }
        turnAround();
    }
    private void goToHome(){
        while(!facingWest())turnLeft();
        while(frontIsClear())move();
        turnLeft();
        while (frontIsClear())move();
        turnLeft();
    }
    private void goBack(){
        while (frontIsClear())move();
        turnLeft();

    }
}