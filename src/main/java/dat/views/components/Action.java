package dat.views.components;

import jiconfont.IconCode;

import java.awt.*;
import java.awt.event.MouseAdapter;

public record Action(String name, IconCode iconCode, Color hoverForeground, MouseAdapter action) {
}