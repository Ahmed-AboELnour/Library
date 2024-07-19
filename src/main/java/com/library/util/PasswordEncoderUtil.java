package com.library.util;

import com.library.entity.Author;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.library.entity.Book;
import java.util.*;
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

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 6, 7, 8);

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
}
