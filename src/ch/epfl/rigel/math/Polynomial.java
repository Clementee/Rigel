package ch.epfl.rigel.math;

public final class Polynomial extends Object {
    
    private double[] coeff;
    private double valuePolynomial;
    private Polynomial polynomial;
    
    private Polynomial() {}
    
    
    // clairement à compléter !!
    public Polynomial of(double coefficientN, double... coefficients) {
        
        if(coefficientN == 0) {
            throw new IllegalArgumentException();
        }
        
        else {
            coeff[0]=coefficientN;
            polynomial = new Polynomial();
            
            for(int i = 0 ; i<coefficients.length;i++) {
                coeff[i+1] = coefficients[i];
        }
            return polynomial;
        }
    }
    
    public double at(double x) {
        
        valuePolynomial = coeff[0];
        for(int i = coeff.length ;i>2;i--) {
            valuePolynomial =+ ((coeff[i]*x+coeff[i-1])*x);
        }
        return valuePolynomial;
    }
    
    @Override
    public String toString() {
        String string = new String();
        
        if(coeff.length==0) {
            return "0";
        }
        else if(coeff.length==1) {
            return "" + coeff[0];
        }
        else if(coeff.length==2){
            return coeff[1]+ "x" +coeff[0];
        }
       
        else {
            for(int j = coeff.length;j>=0;j--) {
                string = coeff[j]+ "x^" + (j-1);
               
                if(coeff[j] ==0) {
                    continue;
                }
               
                if(coeff[j] != 0) {
                    string = string + " " + coeff[j];
                }
                
                if(j==1) {
                    string = string + "x";
                }
                
                if(j>1) {
                    string = string + "x^" + j;
                }
            }
            return string;
            
        }
        
    }

    @Override
    public int hashCode() {
        super.hashCode();
        throw new UnsupportedOperationException();
    }
    
    
    public boolean equals() {
        super.equals(null);
        throw new UnsupportedOperationException();
    }
}
