package com.ldts.ForwardWarfare.Element.Playable.Ground;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;

public class HeavyTank extends Playable {
    public HeavyTank(Position pos) {
        super(2);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics, TextColor textColor) {
        textGraphics.setForegroundColor(textColor != null ? textColor : new TextColor.RGB(80, 80, 80));
        textGraphics.putString(position.toTPos(), "#");
    }
    protected boolean canMove(Element element) {
        return false;
    }
}