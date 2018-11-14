package com.bosowski.oslark.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;


class AtlasBuilder{

    public static void main(String[] args){
        // Adjust this to control the debug outline.
        boolean drawDebugOutline = false;

        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.useIndexes = true;
        settings.maxHeight = 1024;
        settings.maxWidth = 1024;
        settings.debug = drawDebugOutline;
        settings.duplicatePadding = true;
        TexturePacker.process(settings, "sprites_RAW", "atlas", "game.atlas");
        System.out.println( "\n*SUCCESS*");
    }
}


