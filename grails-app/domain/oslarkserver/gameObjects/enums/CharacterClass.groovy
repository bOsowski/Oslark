package oslarkserver.gameObjects.enums

import oslarkserver.gameObjects.components.Vector3

enum CharacterClass {
    DEFAULT("default")

    final String name

    CharacterClass(String name){
        this.name = name
    }

    static CharacterClass getCharacterClass(String name){
        switch (name){
            case "default":
                return DEFAULT
        }
    }
}