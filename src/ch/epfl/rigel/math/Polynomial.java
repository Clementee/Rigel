package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

public final class Polynomial {
    private double[] tab;

    private Polynomial(double coefficientN, double[] coefficients) {
        tab = new double[coefficients.length + 1];
        tab[0] = coefficientN;
        System.arraycopy(coefficients, 0, tab, 1, coefficients.length);
    }

    public static Polynomial of(double coefficientN, double... coefficients) {
        Preconditions.checkArgument(coefficientN != 0);
        return new Polynomial(coefficientN, coefficients);
    }

    public double at(double x) {
        double value = tab[0];
        for (int i = 1; i < tab.length; i++) {
            value = value * x + tab[i];
        }
        return value;
    }

    @Override
    public String toString() {
        String text = "";
        for (int i = 0; i < tab.length; i++) {
            if (i == 0) {
                if (tab.length == 1) {
                    text += tab[i];
                } else {
                    if (tab.length == 2) {
                        if(tab[i]!=1&&tab[i]!=-1){
                            text += tab[i] + "x";
                        }else{
                            if(tab[i]==1){
                                text+= "x";
                            }else{
                                text+="-x";
                            }
                        }
                    } else {
                        if(tab[i]!=1 && tab[i]!=-1){
                            text += tab[i] + "x^" + (tab.length - 1);
                        }else{
                            if(tab[i]==1){
                                text+="x^"+(tab.length-1);
                            }else{
                                text+="-x^"+(tab.length-1);
                            }
                        }

                    }
                }
            } else {
                if (i == tab.length - 1) {
                    if (tab[i] > 0) {
                        text += "+" + tab[i];
                    } if(tab[i]<0) {
                        text += tab[i];
                    }
                } else {
                    if (i == tab.length - 2) {
                        if (tab[i] > 0) {
                            text += "+" + tab[i] + "x";
                        } if(tab[i]<0) {
                            text += tab[i] + "x";
                        }
                    } else {
                        if (tab[i] > 0 && tab[i] != 1) {
                            text += "+" + tab[i] + "x^" + (tab.length - i-1);
                        }
                        if (tab[i] < 0 && tab[i] != -1) {
                            text += tab[i] + "x^" + (tab.length - i-1);
                        }
                        if(tab[i]==1){
                            text+= "+x^"+(tab.length-i-1);
                        }
                        if(tab[i]==-1){
                            text+= "-x^"+(tab.length-i-1);
                        }
                    }
                }


            }
        }
        return text;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

}
