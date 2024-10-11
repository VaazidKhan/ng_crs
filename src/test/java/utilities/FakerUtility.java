package utilities;

import com.github.javafaker.Faker;

public class FakerUtility {

    private static Faker faker = new Faker();

    // Generate random first name
    public static String getFirstName() {
        return faker.name().firstName();
    }

    // Generate random last name
    public static String getLastName() {
        return faker.name().lastName();
    }

    // Generate random full name
    public static String getFullName() {
        return faker.name().fullName();
    }

    // Generate random email
    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    // Generate random phone number
    public static String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    // Generate random username
    public static String getUserName() {
        return faker.name().username();
    }

    // Generate random address
    public static String getAddress() {
        return faker.address().fullAddress();
    }

    // Generate random city
    public static String getCity() {
        return faker.address().city();
    }

    // Generate random country
    public static String getCountry() {
        return faker.address().country();
    }

    // Generate random zip code
    public static String getZipCode() {
        return faker.address().zipCode();
    }
    
    // Generate random ID (for test data)
    public static String getRandomID() {
        return faker.idNumber().valid();
    }
    
    // Generate random password
    public static String getPassword() {
        return faker.internet().password();
    }
}
