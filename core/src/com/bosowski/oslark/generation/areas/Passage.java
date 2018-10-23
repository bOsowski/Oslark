package com.bosowski.oslark.generation.areas;

import com.bosowski.oslarkDomains.enums.Direction;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Passage {

    TileArea tiles = new TileArea();

    final static int minWidth = 2;
    final static int maxWidth = 3;
    final int currentWidth;

    public Passage(TileArea firstLocation, TileArea secondLocation){
        this.currentWidth = ThreadLocalRandom.current().nextInt(minWidth, maxWidth+1);
        TileArea firstLocationCentres = getCentres(firstLocation);
        TileArea secondLocationCentres = getCentres(secondLocation);


    }

    public TileArea getCentres(TileArea location){
        int locationWidth = location.getTiles().size();
        int locationHeight = location.getTiles().get(0).size();

        //figure out the center of the firstLocation
        boolean hasHorizontalCentre = locationWidth % 2 == 0;
        boolean hasVerticalCentre = locationHeight % 2 == 0;

        TileArea centres = new TileArea();
        centres.getTiles().add(new ArrayList<>());
        if(hasVerticalCentre && hasHorizontalCentre){
            centres.getTiles().get(0).add(location.getTiles().get(locationWidth/2).get(locationHeight/2));
        }
        else if(hasVerticalCentre){
            float centre = locationWidth/2;
            centres.getTiles().get(0).add(location.getTiles().get((int)Math.ceil(centre)).get(locationHeight/2));
            centres.getTiles().get(0).add(location.getTiles().get((int)Math.floor(centre)).get(locationHeight/2));
        }
        else if(hasHorizontalCentre){
            float centre = locationHeight/2;
            centres.getTiles().get(0).add(location.getTiles().get(locationWidth/2).get((int)Math.ceil(centre)));
            centres.getTiles().get(0).add(location.getTiles().get(locationWidth/2).get((int)Math.floor(centre)));
        }
        else{
            float centreWidth = locationWidth/2;
            float centreHeight = locationHeight/2;
            centres.getTiles().get(0).add(location.getTiles().get((int)Math.ceil(centreWidth)).get((int)Math.ceil(centreHeight)));
            centres.getTiles().get(0).add(location.getTiles().get((int)Math.floor(centreWidth)).get((int)Math.floor(centreHeight)));
            centres.getTiles().get(0).add(location.getTiles().get((int)Math.ceil(centreWidth)).get((int)Math.floor(centreHeight)));
            centres.getTiles().get(0).add(location.getTiles().get((int)Math.floor(centreWidth)).get((int)Math.ceil(centreHeight)));
        }
        return centres;
    }

}
