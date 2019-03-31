package com.tcg.ashbox.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.tcg.ashbox.controller.KeyboardController;
import com.tcg.ashbox.entity.components.B2dBodyComponent;
import com.tcg.ashbox.entity.components.PlayerComponent;
import com.tcg.ashbox.entity.components.StateComponent;

public class PlayerControlSystem extends IteratingSystem {

    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<B2dBodyComponent> bodm;
    ComponentMapper<StateComponent> sm;
    KeyboardController controller;

    public PlayerControlSystem(KeyboardController keyCon) {
        super(Family.all(PlayerComponent.class).get());
        controller = keyCon;
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(B2dBodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2dBody = bodm.get(entity);
        StateComponent state = sm.get(entity);
        if (b2dBody.body.getLinearVelocity().y > 0) {
            state.set(StateComponent.State.FALLING);
        }
        if (Float.compare(b2dBody.body.getLinearVelocity().y, 0f) == 0) {
            if (state.get() == StateComponent.State.FALLING) {
                state.set(StateComponent.State.NORMAL);
            }
            if (Float.compare(b2dBody.body.getLinearVelocity().x, 0) != 0) {
                state.set(StateComponent.State.MOVING);
            }
        }
        if (controller.left) {
            b2dBody.body.setLinearVelocity(
                    MathUtils.lerp(b2dBody.body.getLinearVelocity().x, -5f, 0.2f),
                    b2dBody.body.getLinearVelocity().y
            );
        }
        if (controller.right) {
            b2dBody.body.setLinearVelocity(
                    MathUtils.lerp(b2dBody.body.getLinearVelocity().x, 5f, 0.2f),
                    b2dBody.body.getLinearVelocity().y
            );
        }
        if (!controller.left && !controller.right) {
            b2dBody.body.setLinearVelocity(
                    MathUtils.lerp(b2dBody.body.getLinearVelocity().x, 0, 0.1f),
                    b2dBody.body.getLinearVelocity().y
            );
        }
        if (controller.up && (state.get() == StateComponent.State.NORMAL || state.get() == StateComponent.State.MOVING)) {
            b2dBody.body.applyLinearImpulse(0, 75f, b2dBody.body.getWorldCenter().x, b2dBody.body.getWorldCenter().y, true);
            state.set(StateComponent.State.JUMPING);
        }

    }
}
