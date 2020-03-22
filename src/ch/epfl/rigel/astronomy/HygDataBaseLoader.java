package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static ch.epfl.rigel.astronomy.HygDataBaseLoader.index;
import static java.nio.charset.StandardCharsets.US_ASCII;

public enum HygDataBaseLoader implements StarCatalogue.Loader{
    INSTANCE;

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        LinkedList<String[]> input = new LinkedList<>();
        try(InputStreamReader inputStreamReader = new InputStreamReader(inputStream, US_ASCII)){
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
                while(bufferedReader.ready()){
                    String inputLine = bufferedReader.readLine();
                    input.add(inputLine.split(","));
                }
        }
            int starHip;
            String starName ="";
            String valInt = "";
            double starRaRad, starDecRad, starMagnitude, starColorInd;
            Iterator<String[]> i = input.iterator();
            while(i.hasNext()){
                String[]tabInter = i.next();
                valInt = tabInter[index.HIP.ordinal()];
                if(valInt.equals("")){
                    starHip = 0;
                }else{
                    starHip = Integer.parseInt(valInt);
                }
                valInt = tabInter[index.PROPER.ordinal()];
                if(valInt.equals("")){
                    if(tabInter[index.BAYER.ordinal()].equals("")){
                        starName = "?"+tabInter[index.CON.ordinal()];
                    }else{
                        starName = valInt;
                    }
                }
                starRaRad = Double.parseDouble(tabInter[index.RARAD.ordinal()]);
                starDecRad=  Double.parseDouble(tabInter[index.DECRAD.ordinal()]);
                EquatorialCoordinates starEqCoordinates = EquatorialCoordinates.of(starRaRad, starDecRad);

                valInt = tabInter[index.MAG.ordinal()];
                if(valInt.equals("")){
                    starMagnitude = 0;
                }else{
                    starMagnitude = Double.parseDouble(valInt);
                }
                valInt = tabInter[index.CI.ordinal()];
                if(valInt.equals("")){
                    starColorInd = 0;
                }else{
                    starColorInd = Double.parseDouble(valInt);
                }
                builder = builder.addStar(new Star(starHip,starName,starEqCoordinates,(float)starMagnitude,(float)starColorInd));
            }

    }
}
    private enum index{
        ID, HIP, HD, HR, GL, BF, PROPER, RA, DEC, DIST, PMRA, PMDEC,
        RV, MAG, ABSMAG, SPECT, CI, X, Y, Z, VX, VY, VZ,
        RARAD, DECRAD, PMRARAD, PMDECRAD, BAYER, FLAM, CON,
        COMP, COMP_PRIMARY, BASE, LUM, VAR, VAR_MIN, VAR_MAX;

        index(){}
    }
}
