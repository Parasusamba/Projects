import java.util.Scanner;
public class App {
    public static void main(String[] args) {
        char res[][]=new char[3][3];
       for(int row=0;row<3;row++){
        for(int col=0;col<3;col++){
            res[row][col]=' ';
        }
       }
       printBoard(res);
       Scanner sc=new Scanner(System.in);
       char player='X';
       int playerCount=0;
       while(true){
            System.out.println("player "+player+" move : ");
            if(playerCount>2){
                movePlayer(res,player);
            }
            else{
                int row=sc.nextInt();
                int col=sc.nextInt();
                if(res[row][col]==' '){
                    res[row][col]=player;
                    printBoard(res);
                }
                else{
                    System.out.println("Invalid move.Try again!");
                    continue;
                }
            }
            if(gameOver(res,player)){
                printBoard(res);
                System.out.println("game Over.Player "+player+" won!");
                break;
            }
            player=(player=='X')?'O':'X';
            if(player=='X'){
                playerCount++;
            }
        }
    }
    public static boolean gameOver(char res[][],char player){
        for(int row=0;row<3;row++){
            if(res[row][0]==player && res[row][1]==player && res[row][2]==player){
                return true; 
            }
        }
        for(int col=0;col<3;col++){
            if(res[0][col]==player && res[1][col]==player && res[2][col]==player){
                return true;
            }
        }
        if(res[0][2]==player && res[1][1]==player && res[2][0]==player){
            return true;
        }
        if(res[0][0]==player && res[1][1]==player && res[2][2]==player){
            return true;
        }
        return false;
    }   
    public static void printBoard(char res[][]){
        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                System.out.print(res[row][col]+" | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
    public static void movePlayer(char res[][],char player){
        Scanner sc=new Scanner(System.in);
        System.out.println(player+" :Which player do you want to move ?");
        System.out.println("From(enter row & col) : ");
        int oldrow =sc.nextInt();
        int oldcol =sc.nextInt();
        if(res[oldrow][oldcol]!=player){
            System.out.println("The player "+player+" is not avialable at this location");
            System.out.println("please enter the new location");
            movePlayer(res,player);
        }
        else{
            boolean space=true;
            while(space){
                System.out.println("To(enter row & col) : ");
                int newrow =sc.nextInt();
                int newcol=sc.nextInt();
                if(res[newrow][newcol]==' '){
                    res[newrow][newcol] = res[oldrow][oldcol];
                    res[oldrow][oldcol] = ' ';
                    printBoard(res);
                    space=false;
                }
                else{
                    System.out.println("the place is alredy occupied.Enter new location :");
                    space=true;
                }
            }
        }
    }
}
