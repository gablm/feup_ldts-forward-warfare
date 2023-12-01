package com.ldts.ForwardWarfare.Element.Playable;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;

public abstract class Playable extends Element {
    private int hp = 0;
    private int maxMoves;
    public Playable(int maxMoves) {
        this.maxMoves = maxMoves;
    }
    public void takeHP(int damage) {
        hp -= damage;
    }
    public void setHP(int hp) {
        this.hp = hp;
    }

    public abstract boolean canMove(Element element);
}
