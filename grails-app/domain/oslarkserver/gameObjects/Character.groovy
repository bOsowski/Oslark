package oslarkserver.gameObjects

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.bosowski.oslarkDomains.AbstractCreatureDomain
import com.bosowski.oslarkDomains.components.Animator
import oslarkserver.User

class Character extends AbstractCreatureDomain{

    static constraints = {
    }

    static belongsTo = [user: User]

    Character(String name, Animator animator, Vector2 scale, boolean collides, Rectangle collisionBox) {
        super(name, animator, scale, collides, collisionBox)
    }

    Character(AbstractCreatureDomain original) {
        super(original)
    }
}
