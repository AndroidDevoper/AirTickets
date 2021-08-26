package com.example.airtickets;

import android.util.Log;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        List<String> listDate = new ArrayList<>();
        listDate.add("23/10/2000");
        listDate.add("20/10/2000");
        listDate.add("21/9/2000");
        Collections.sort(listDate, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        System.out.println(listDate.toString());
    }
}