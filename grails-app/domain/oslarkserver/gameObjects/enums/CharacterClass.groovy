package oslarkserver.gameObjects.enums

enum CharacterClass {
    KNIGHT("knight"), WIZARD("wizard"), ELF("elf")

    final String name

    CharacterClass(String name){
        this.name = name
    }
}