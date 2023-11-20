package com.ldts.ForwardWarfare.Playable.Air;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Position;

public class LightHelicopter extends Playable {
    public LightHelicopter(Position pos) {
        super("Air", 4);
        position = pos;
    }
    public void draw(TextGraphics graphics) {

    }
    protected boolean canMove(Element element) {
        return false;
    }
}
