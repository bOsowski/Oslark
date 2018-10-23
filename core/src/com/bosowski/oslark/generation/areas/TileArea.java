package com.bosowski.oslark.generation.areas;

import java.util.ArrayList;

public class TileArea {
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();


    public ArrayList<ArrayList<Tile>> getTiles(){
        return  tiles;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles){
        this.tiles = tiles;
    }
}
