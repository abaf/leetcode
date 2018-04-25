package com.leetcode.algorithms;

public class TwoSumSoluntion {
    /*
    *
    * Given an array of integers, return indices of the two numbers such that they add up to a specific target.

       You may assume that each input would have exactly one solution, and you may not use the same element twice.

Example:

Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].
    * */

    public int[] twoSum(int[] nums, int target) {
        int[] results = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int diff = target - num;
            for(int j = i+1; j<nums.length;j++){
                if(nums[j] == diff){
                    results[0]=i;
                    results[1]=j;
                }
            }
        }
        return results;
    }
}
