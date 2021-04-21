package com.lab4.demo;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        }  else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static Object newUser() {
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName(ERole.REG_USER);
        roles.add(role);

        return User.builder()
                .id(randomLong())
                .roles(roles)
                .password(randomString())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    private static Object newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    private static Book newBook(){
        return Book.builder()
                .id(randomLong())
                .author(randomString())
                .price(randomFloat())
                .quantity(randomBoundedInt(40))
                .genre(randomString())
                .title(randomString())
                .build();

    }

    private static BookDTO newBookDTO(){
        return BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomBoundedInt(20))
                .build();

    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static Float randomFloat(){ return new Random().nextFloat();}

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
