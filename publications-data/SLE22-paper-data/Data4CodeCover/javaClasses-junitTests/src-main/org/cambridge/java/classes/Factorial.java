package org.cambridge.java.classes;

public class Factorial {
    public int ComputeFac(int num){
		int num_aux = 0;
		if (num < 1){
			num_aux = 1 ;
		} 
		else {
			num_aux = num * (this.ComputeFac(num-1)) ;
		}  
		return num_aux ;
	}
}