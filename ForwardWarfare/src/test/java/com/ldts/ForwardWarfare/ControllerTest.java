package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.*;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    public void ControllerInvalidTest() {
        Exception playerEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Player(null, TextColor.ANSI.MAGENTA_BRIGHT, name);
        });
        Exception botEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Bot(null, TextColor.ANSI.GREEN_BRIGHT);
        });

        assertEquals("Invalid initial Factory and Base", playerEx.getMessage());
        assertEquals("Invalid initial Factory and Base", botEx.getMessage());
    }

    @Test
    public void ControllerWrongSizeTest() {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()));
        Exception playerEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Player(elements, TextColor.ANSI.GREEN_BRIGHT, name);
        });
        Exception botEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Bot(elements, TextColor.ANSI.GREEN_BRIGHT);
        });

        assertEquals("Invalid initial Factory and Base", playerEx.getMessage());
        assertEquals("Invalid initial Factory and Base", botEx.getMessage());
    }

    @Test
    public void ControllerDefaultTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, name);

        assertEquals(100, player.getCoins());
        Assertions.assertSame(player.getBase(), base);
        assertEquals(1, player.getFacilities().size());
        Assertions.assertSame(factory, player.getFacilities().get(0));
        Assertions.assertTrue(player.getTroops().isEmpty());
    }

    @Test
    public void ControllerBuyAllTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, name);

        assertEquals(100, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 100);
        Assertions.assertTrue(result);
        assertEquals(0, player.getCoins());
        assertEquals(1, player.getTroops().size());
        Assertions.assertSame(troop, player.getTroops().get(0));
    }

    @Test
    public void ControllerBuyLessTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, name);

        assertEquals(100, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 50);
        Assertions.assertTrue(result);
        assertEquals(50, player.getCoins());
        assertEquals(1, player.getTroops().size());
        Assertions.assertSame(troop, player.getTroops().get(0));
    }

    @Test
    public void ControllerBuyMoreTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, name);

        assertEquals(100, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 120);
        assertFalse(result);
        assertEquals(100, player.getCoins());
        Assertions.assertTrue(player.getTroops().isEmpty());
    }

    @Test
    public void EndRoundTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Controller yourInstance = new Bot(elements,TextColor.ANSI.BLUE_BRIGHT);
        yourInstance.endRound();
        assertFalse(yourInstance.canPlay());
        assertNull(yourInstance.getSelection1());
        assertNull(yourInstance.getSelection2());
    }

    @Test
    public void GetSetSelection1Test() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Border borderMock = Mockito.mock(Border.class);
        Controller yourInstance = new Bot(elements, TextColor.ANSI.WHITE_BRIGHT);
        yourInstance.setSelection1(borderMock);
        assertEquals(borderMock, yourInstance.getSelection1());
    }

    @Test
    public void GetSetSelection2Test() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Border borderMock = Mockito.mock(Border.class);
        Controller yourInstance = new Player(elements, TextColor.ANSI.RED_BRIGHT, name);
        yourInstance.setSelection2(borderMock);
        assertEquals(borderMock, yourInstance.getSelection2());
    }

    @Test
    public void DrawTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        TextGraphics graphicsMock = Mockito.mock(TextGraphics.class);
        ControllerBase yourInstance = new Player(elements, TextColor.ANSI.BLUE, name);
        yourInstance.draw(graphicsMock);
        Mockito.verify(graphicsMock,
                Mockito.times(yourInstance.getTroops().size() + yourInstance.getFacilities().size() * 2 + 2))
                .putString(Mockito.any(TerminalPosition.class),Mockito.any(String.class));
    }

    @Test
    public void DrawBorderTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        TextGraphics graphicsMock = Mockito.mock(TextGraphics.class);
        Border selection1Mock = Mockito.mock(Border.class);
        Border selection2Mock = Mockito.mock(Border.class);
        Controller yourInstance = new Bot(elements, new TextColor.RGB(255, 255, 255));
        yourInstance.setSelection1(selection1Mock);
        yourInstance.setSelection2(selection2Mock);
        yourInstance.drawBorder(graphicsMock);
        Mockito.verify(selection1Mock, Mockito.times(1)).draw(graphicsMock);
        Mockito.verify(selection2Mock, Mockito.times(1)).draw(graphicsMock);
    }

    @Test
    public void ResetRoundTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Controller yourInstance = new Player(elements, new TextColor.RGB(255, 0, 255), name);
        int initialCoins = yourInstance.getCoins();
        yourInstance.resetRound();
        assertTrue(yourInstance.canPlay());
        assertEquals(initialCoins + 100 + 30 * yourInstance.getFacilities().size(), yourInstance.getCoins());
    }

    @Test
    public void CanPlayTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        ControllerBase yourInstance = new Bot(elements, TextColor.ANSI.CYAN);
        assertTrue(yourInstance.canPlay()); // Assuming initially canPlay is true
    }
}
