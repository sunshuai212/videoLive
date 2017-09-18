package com.aihuishou.httplib.utils;


import android.util.Pair;

import java.util.Comparator;


public class PairSort implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {

        Pair<String, String> leftPair = (Pair<String, String>)lhs;
        Pair<String, String> rightPair = (Pair<String, String>)rhs;

        String leftKey = leftPair.first;
        String rightKey = rightPair.first;

        return leftKey.compareTo( rightKey );
    }
}
