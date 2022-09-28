package org.cambridge.java.classes;
// This class contains the array of integers and
// methods to initialize, print and sort the array
// using Quicksort
public class QuickSort{
    
    public int[] number ;
    public int size ;
    
    // Sort array of integers using Quicksort method
    public int Sort(int left, int right){
		int v = 0;
		int i = 0;
		int j = 0;
		int nt = 0;
		int t = 0;
		boolean cont01 = false;
		boolean cont02 = false;
		int aux03 = 0;
		if (left < right){
		    v = this.number[right] ;
		    i = left - 1 ;
		    j = right ;
		    cont01 = true ;
		    while (cont01){
				cont02 = true ;
				while (cont02){
				    i = i + 1 ;
				    aux03 = this.number[i] ;
				    if (!(aux03<v)) {
				    	cont02 = false ;
				    }
				    else {
				    	cont02 = true ;
				    } 
				}
				cont02 = true ;
				while (cont02){
				    j = j - 1 ;
				    aux03 = this.number[j] ;
				    if (!(v < aux03)){
				    	cont02 = false ;
				    } 
				    else {
				    	cont02 = true ;
				    }
				}
				t = this.number[i] ;
				this.number[i] = this.number[j] ;
				this.number[j] = t ;
				//aux03 = i + 1 ;
				if ( j < (i+1)) {
					cont01 = false ;
				}
				else {
					cont01 = true ;
				}
		    }
		    this.number[j] = this.number[i] ;
		    this.number[i] = this.number[right] ;
		    this.number[right] = t ;
		    nt = this.Sort(left,i-1);
		    nt = this.Sort(i+1,right);
		}
		else {
			nt = 0 ;
		}
		return 0 ;
    }

    
    // Print array of integers
//    public int Print(){
//		int j = 0;
//		while (j < (this.size)) {
//		    System.out.println(this.number[j]);
//		    j = j + 1 ;
//		}
//		return 0 ;
//    }
}