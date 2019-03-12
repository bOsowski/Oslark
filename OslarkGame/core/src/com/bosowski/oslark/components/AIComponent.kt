package com.bosowski.oslark.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class AIComponent(var action: ActionInterface?): AbstractComponent() {

    override fun awake() {}

    override fun start() {}

    override fun render(batch: SpriteBatch) {}

    override fun destroy() {}

    override fun update(deltaTime: Float){
        action?.perform(deltaTime)
    }

}