package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class EndGameState extends BaseState {
    public EndGameState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }

    @Override
    public State play(Action action) {
        return null;
    }

    @Override
    public void draw(TextGraphics graphics) {

    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
