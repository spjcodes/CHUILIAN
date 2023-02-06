package cn.jiayeli.algorithms.serarch;

import jdk.nashorn.internal.runtime.logging.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/6 下午9:18
 */
public class BinarySearchTestCase {
    public static void main(String[] args) {
        List<Integer> dataSet = Arrays.asList(1, 3, 5, 6, 7, 8, 11, 15, 19);
        new BinarySearch<Integer>(dataSet).recursionSearch(10);
//        new BinarySearch<Integer>(dataSet).search(10);

    }
}

// Comparable (x < y) ? -1 : ((x == y) ? 0 : 1);
class BinarySearch<T extends Comparable<T>> {

    List<T> dataSet;

    BinarySearch(List<T> dataSet){
        this.dataSet = dataSet;
    }


    T result;
    public T recursionSearch(T target){
        if (this.dataSet == null || this.dataSet.size() < 1 || target == null) {
            System.out.println("dataSet or is empty !!!");
            return null;
        }
        int leftPoint = 0;
        int rightPoint = dataSet.size()-1;
        return this.recursionSearch(target, leftPoint, rightPoint);
    }


    int midPoint;
    public T recursionSearch(T target, int leftPoint, int rightPoint) {

        if (leftPoint >= rightPoint) {
            System.out.printf("no search data [%s]!!!\n", target);
            return null;
        }
        if (rightPoint - leftPoint == 1) {
            if (dataSet.get(leftPoint) == target) {
                return dataSet.get(leftPoint);
            } else if (dataSet.get(rightPoint) == target) {
                return dataSet.get(rightPoint);
            } else {
                System.out.printf("no search data [%s]!!!\n", target);
                return null;
            }
        }
        midPoint = leftPoint + ((rightPoint - leftPoint) / 2);
        result = dataSet.get(midPoint);
        if (result == target) {
            System.out.printf("search data: %s, in index[%s]\n", target, midPoint);
            return result;
        }

        leftPoint = result.compareTo(target) > 0 ? leftPoint: midPoint++;
        rightPoint = result.compareTo(target) > 0 ? midPoint-- : rightPoint;
        recursionSearch(target, leftPoint, rightPoint);
        return null;
    }

    public T search(T target) {
        if (this.dataSet == null || this.dataSet.size() < 1 || target == null) {
            System.out.println("dataSet or target is empty !!!");
            return null;
        }
        int leftPoint = 0;
        int rightPoint = dataSet.size()-1;
        int midPoint;
        T result;
        while (leftPoint >= rightPoint) {
            midPoint = leftPoint + (rightPoint/2);
            result = dataSet.get(midPoint);
            if (result == target) {
                System.out.printf("search data: %s, in index[%s]\n", target, midPoint);
                return result;
            } else if(result.compareTo(dataSet.get(midPoint)) > 1) {
                leftPoint = midPoint++;
            } else {
                rightPoint = midPoint--;
            }
        }
        System.out.printf("no search target data [%s]!!!\n", target);
        return null;
    }
}
