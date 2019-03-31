package com.tcg.ashbox.entity.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {

    public enum Type {
        ENEMY, SCENERY, OTHER, PLAYER
    }

    public Type type;

}
