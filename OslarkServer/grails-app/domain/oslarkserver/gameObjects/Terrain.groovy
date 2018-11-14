package oslarkserver.gameObjects

class Terrain extends GameObject{

    TerrainType terrain = TerrainType.NORMAL

    static constraints = {
    }

    enum TerrainType{
        NORMAL("normal"), OBSTRUCTION("obstruction"), WATER("water"), MUCK("muck"), FIRE("fire")

        private String name

        TerrainType(String name){
            this.name = name
        }
    }

    String toJson(){
        return "{super:${super.toJson()}, terrainType:${terrain}}"
    }
}
