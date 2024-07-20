package com.library.util;

import com.library.entity.Author;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.library.entity.Book;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PasswordEncoderUtil {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password123456");
        System.out.println(encodedPassword);


        Runnable runnable=() -> {
            System.out.println("Thread is working");
        };
        Thread thread=new Thread(runnable);
        thread.start();

        Random random = new Random();

        random.ints().limit(10).forEach(System.out::println);
        System.out.println(isInteger(7));

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 6, 7, 8);

        List<Integer> collects = numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());

        //collects.forEach(System.out::println);

        List<Integer> randomNums = Arrays.asList(1,10,9,0, 2, 3, 4, 6, 7, 8);
        List<Integer> collect = randomNums.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        collect.forEach(System.out::println);

        Predicate<Integer> predicate = number -> number % 2 == 0;

        numbers.forEach(num -> {
            if (predicate.test(num))
                System.out.println(num);

        });
        numbers.forEach(System.out::println);

        int min = 1; // starting value of the range
        int max = 7; // ending value of the range

        IntSummaryStatistics stats = numbers.stream()
                .mapToInt(x -> x)
                .summaryStatistics();

        System.out.println("Lowest number in List : " + stats.getMin());
        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Sum of all numbers in List : " + stats.getSum());
        System.out.println("Average of all numbers in List : " + stats.getAverage());


        Set<Integer> numberSet = new HashSet<>(numbers);

        // Find the missing numbers
        List<Integer> missingNumbers = IntStream.rangeClosed(min, max)
                .filter(num -> !numberSet.contains(num))
                .boxed()
                .collect(Collectors.toList());

        System.out.println("Missing numbers in the range: " + missingNumbers);

        List<Book> books=new ArrayList<>();

        List<Author> authorList= books.stream().map(book -> book.getAuthor()).collect(Collectors.toList());


    }

    public static boolean isInteger(int number){
        Predicate<Integer> predicate = numb -> number % 2 == 0;
        if (predicate.test(number)) {
            System.out.println(number);
            return true;
        }

        return false;
    }
}
