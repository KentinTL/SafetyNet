package utilities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.openclassrooms.safetynet.controller.dto.response.PersonInfos;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.utilities.Tools;

public class ToolsTest {

    @Test
    public void testMapToPersonInfos() {
        // Arrange
        PersonModel personModel = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
        
        // Act
        PersonInfos result = Tools.mapToPersonInfos(personModel);

        // Assert
        assertNotNull(result);
        assertEquals("123 Street", result.getAddress());
        assertEquals("John", result.getFirestName());
        assertEquals("Doe", result.getLastName());
        assertEquals("1234567890", result.getPhone());
    }

    @Test
    public void testGetAge_ValidDate() {
        // Arrange
        String birthDate = "01/01/2000"; // Assuming current year is 2024

        // Act
        int age = Tools.getAge(birthDate);

        // Assert
        assertEquals(24, age);
    }

    @Test
    public void testGetAge_InvalidDate() {
        // Arrange
        String invalidBirthDate = "invalid-date";

        // Act
        int age = Tools.getAge(invalidBirthDate);

        // Assert
        assertEquals(0, age);
    }

    @Test
    public void testIsAdult() {
        // Act & Assert
        assertTrue(Tools.isAdult(19));
        assertFalse(Tools.isAdult(18));
    }

    @Test
    public void testIsChild() {
        // Act & Assert
        assertTrue(Tools.isChild(18));
        assertFalse(Tools.isChild(19));
    }
}

