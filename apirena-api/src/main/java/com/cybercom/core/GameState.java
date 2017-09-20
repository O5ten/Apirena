package com.cybercom.core;

import com.google.common.collect.Lists;

import java.util.List;

public class GameState {
    private final List<String> map = Lists.newArrayList(
            "..X.X.",
            "X...X.",
            "..X...",
            ".....^");

    public List<String> getMap() {
        return map;
    }
}