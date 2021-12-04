package org.cambridge.java.classes;
// This class contains the array of integers and
// methods to initialize, print and sort the array
// using Bublesort
public class BubbleSort{
    
    public int[] number ;
    public int size ;
    
    // Sort array of integers using Bublesort method
    public void Sort(){
		int nt = 0;
		int i = this.size - 1;
		int aux02 = 0 - 1;
		int aux04 = 0;
		int aux05 = 0;
		int aux06 = 0;
		int aux07 = 0;
		int j = 0;
		int t = 0;
		while (aux02 < i) {
		    j = 1 ;
		    //aux03 = i+1 ;
		    while (j < (i+1)){
				aux07 = j - 1 ;
				aux04 = this.number[aux07] ;
				aux05 = this.number[j] ;
				if (aux05 < aux04) {
				    aux06 = j - 1 ;
				    t = this.number[aux06] ;
				    this.number[aux06] = this.number[j] ;
				    this.number[j] = t;
				}
				else {
					nt = 0 ;	
				}
				j = j + 1 ;
			}
			i = i - 1 ;
		}
    }

    // Printing method
//    public void Print(){
//		int j = 0;
//		while (j < (this.size)) {
//		    System.out.println(this.number[j]);
//		    j = j + 1 ;
//		}
//    }
}