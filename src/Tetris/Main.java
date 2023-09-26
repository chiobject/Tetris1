package Tetris;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int amount = input.nextInt();
        int amount_copy = amount;
        int count = 0;
        int min = amount;
        if (amount % 5 == 0 ) min = amount / 5;
        
        while(amount_copy > 0) {
        	if(amount_copy >= 3) {
        		if (amount_copy % 3 == 0 && min > count + amount_copy / 3) {
        			min = count + amount_copy / 3;
        		}
        	}
        	amount_copy -= 5;
        	count += 1;
        }
        if (min == amount)	min = -1;
        System.out.println(min);
    }
}