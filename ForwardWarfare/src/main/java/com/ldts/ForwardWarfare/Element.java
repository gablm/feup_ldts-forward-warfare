package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    protected Position position;
    public Element() { }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position pos) {
        this.position = pos;
    }
}
