package com.qarantinno.api.service.utils;

import java.util.Collections;
import java.util.List;

public class Algorithm {

    public static double median(List<Integer> data) {
        double result = 0;
        if (!data.isEmpty()) {
            Collections.sort(data);
            double mediumSize = mediumSize(data);
            int mediumFirstIndex = (int) Math.floor(mediumSize) - 1;
            int mediumSecondIndex = (int) Math.ceil(mediumSize) - 1;
            result = (double) (data.get(mediumFirstIndex) + data.get(mediumSecondIndex)) / 2;
        }
        return result;
    }

    private static double mediumSize(List<Integer> data) {
        return ((double) data.size() + 1) / 2;
    }
}
