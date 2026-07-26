package com.mehroof.chessmaster.ai.search;

import java.util.HashMap;
import java.util.Map;

public class TranspositionTable {

    private final Map<Long, TranspositionEntry> table =
            new HashMap<>();

    public void put(
            long key,
            TranspositionEntry entry) {

        table.put(key, entry);
    }

    public TranspositionEntry get(long key) {

        return table.get(key);
    }

    public void clear() {

        table.clear();
    }

}