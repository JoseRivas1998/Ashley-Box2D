package com.tcg.ashbox.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.tcg.ashbox.entity.components.CollisionComponent;
import com.tcg.ashbox.entity.components.PlayerComponent;
import com.tcg.ashbox.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());
        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent cc = cm.get(entity);

        Entity collidedEntity = cc.collisionEntity;

        if(collidedEntity != null) {
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if(type != null) {
                switch (type.type) {
                    case ENEMY:
                        System.out.println("Player hit enemy");
                        break;
                    case SCENERY:
                        System.out.println("Player hit scenery");
                        break;
                    case OTHER:
                        System.out.println("Player hit other");
                        break;
                    case PLAYER:
                        break;
                }
                cc.collisionEntity = null;
            }
        }

    }
}
