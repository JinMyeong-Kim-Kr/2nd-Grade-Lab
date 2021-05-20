import java.util.*;
import java.lang.*;

class Polynomial {
	private Term[] termArray;
	private int terms;  // number of nonzero terms

	class Term {
		private double coef; //coefficient
		private int exp; //exponent
	};

	// constructor
	Polynomial(int cap) {
		termArray = new Term[cap];
		terms = 0;
	}

	/**
	*  Add a new term to the end of termArray
	*/
	public void NewTerm(double theCoeff, int theExp) {	
		if(terms == termArray.length)
		{// double capacity of termArray
			termArray = Arrays.copyOf(termArray , termArray.length * 2);
		}
		termArray[terms] = new Term();
		termArray[terms].coef = theCoeff;
		termArray[terms++].exp = theExp;
	}

	public Polynomial Add(Polynomial b) {
		// Return the sum of the polynomials this and b
		Polynomial c = new Polynomial(128);
		int aPos = 0, bPos = 0;
		while((aPos < terms) && (bPos < b.terms))
			if(termArray[aPos].exp == b.termArray[bPos].exp){
				double t=termArray[aPos].coef+b.termArray[bPos].coef;
				if (t != 0.0) c.NewTerm(t, termArray[aPos].exp);
				aPos++; bPos++;
			}
			else if(termArray[aPos].exp < b.termArray[bPos].exp){
				c.NewTerm(b.termArray[bPos].coef, b.termArray[bPos].exp);
				bPos++;
			}
			else {
				c.NewTerm(termArray[aPos].coef, termArray[aPos].exp);
				aPos++;
			}

		// add in remaining terms of *this
		for( ; aPos < terms; aPos++)
			c.NewTerm(termArray[aPos].coef, termArray[aPos].exp);
		for( ; bPos < b.terms; bPos++)
			c.NewTerm(b.termArray[bPos].coef, b.termArray[bPos].exp);
		return c;
	}		


	double Evaluate(double f) {
		double eval = 0.0;
		for(int i = 0; i < terms; i++){ //-1 해야 null 에러 안감
			if(termArray[i].exp != 0){ //지수가 0이 아닐때 계수를 다더함
				eval += termArray[i].coef*Math.pow(f, termArray[i].exp); // 계수 X (밑의 대입할 수, 제곱할 지수)
			}
			else{ //지수가 0인 상수부분을 더함
				eval += termArray[i].coef;
				break; // 상수부분 다더하면 멈춰야함
			}
			
		}						
		return eval;
	}

	// check whether the two polynomials are the same
	boolean Equals(Polynomial p) {
		if(terms != p.terms){ //일단 term 수가 다르면 안같음
			return false;
		}
		for(int j = 0; j < terms; j++){
			if(termArray[j].coef!=p.termArray[j].coef || //계수나 지수 하나라도 다르면 false
			termArray[j].exp!=p.termArray[j].exp){
				return false;
			}
		}
		return true;
	}
}


