package com.company.actor;

import java.util.*;

public class TestReturn {

    public static void main(String[] args) {

         List<String> list = new ArrayList<>(List.of("R01", "R00", "R02"));

         list.removeIf("R01"::equals);

        System.out.println("After Removing:"+list);

        int val = new Random().nextInt(5);

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        Optional<Integer> valOpt = testWhileReturn(ids);

        System.out.println(valOpt.get());
    }

    private static Optional<Integer> testWhileReturn(List<Integer> ids) {

        Iterator<Integer> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Integer currentId = iterator.next();
            System.out.println("Current val:"+currentId);
            int val = new Random().nextInt(5);
            System.out.println("DB value:"+val);
            if (currentId == val) {
                System.out.println("Match found with value:"+val);
                return Optional.of(val);
            }
        }
        System.out.println("update the natural ids");
        return Optional.empty();

    }
}
