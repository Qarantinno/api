package com.qarantinno.api.service.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Algorithm {

    public static double median(List<Integer> data, Precondition precondition) {
        double result;
        switch (precondition) {
            case NIGHT_TIME:
                result = nightTimeMedian(data);
                break;
            case BASIC:
            default:
                result = basicMedian(data);
                break;
        }
        return result;
    }

    private static double basicMedian(List<Integer> data) {
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

    private static double nightTimeMedian(List<Integer> data) {
        List<Integer> noZeroData = data.stream()
                                       .filter(dataItem -> dataItem != 0)
                                       .collect(Collectors.toList());
        return basicMedian(noZeroData);
    }

    public enum Precondition {
        BASIC,
        NIGHT_TIME
    }
}
