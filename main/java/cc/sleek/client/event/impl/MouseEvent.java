package cc.sleek.client.event.impl;

import cc.sleek.client.event.Event;

public class MouseEvent extends Event {
    private int button;

    public MouseEvent(int button) {
        this.button = button;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }
}