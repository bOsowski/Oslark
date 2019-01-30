package com.bosowski.oslark.components

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.ai.steer.SteerableAdapter
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class SteeringComponent(val body: Body): SteerableAdapter<Vector2>() {

    var _tagged: Boolean = false
    var _boundingRadius: Float = 10f
    var _maxLinearSpeed: Float = 10f
    var _maxLinearAcceleration: Float = 10f
    var _maxAngularSpeed: Float = 10f
    var _maxAngularAcceleration = 10f

    override fun setTagged(tagged: Boolean) {
        _tagged = tagged
    }

    override fun setOrientation(orientation: Float) {
    }

    override fun getZeroLinearSpeedThreshold(): Float {
        return 0f
    }

    override fun setZeroLinearSpeedThreshold(value: Float) {

    }

    override fun getMaxAngularSpeed(): Float {
        return _maxAngularSpeed
    }

    override fun getMaxLinearSpeed(): Float {
        return _maxLinearSpeed
    }

    override fun getAngularVelocity(): Float {
        return body.angularVelocity
    }

    override fun getMaxAngularAcceleration(): Float {
        return _maxAngularAcceleration
    }

    override fun getLinearVelocity(): Vector2 {
        return body.linearVelocity
    }

    override fun setMaxLinearSpeed(maxLinearSpeed: Float) {
        _maxLinearSpeed = maxLinearSpeed
    }

    override fun getMaxLinearAcceleration(): Float {
        return _maxLinearAcceleration
    }

    override fun getPosition(): Vector2 {
        return body.position
    }

    override fun isTagged(): Boolean {
        return _tagged
    }

    override fun setMaxLinearAcceleration(maxLinearAcceleration: Float) {
        _maxLinearAcceleration = maxLinearAcceleration
    }

    override fun setMaxAngularSpeed(maxAngularSpeed: Float) {
        _maxAngularSpeed = maxAngularSpeed
    }

    override fun getOrientation(): Float {
        return body.angle
    }

    override fun setMaxAngularAcceleration(maxAngularAcceleration: Float) {
        _maxAngularAcceleration = maxAngularAcceleration
    }

    override fun angleToVector(outVector: Vector2?, angle: Float): Vector2 {
        val newOutVector = Vector2()
        newOutVector.x = (- Math.sin(angle.toDouble())).toFloat()
        newOutVector.y = Math.cos(angle.toDouble()).toFloat()
        return newOutVector
    }

    override fun vectorToAngle(vector: Vector2?): Float {
        return Math.atan2(-1 * vector?.x?.toDouble()!!, vector.y.toDouble()).toFloat()
    }

    override fun getBoundingRadius(): Float {
        return _boundingRadius
    }
}