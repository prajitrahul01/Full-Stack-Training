package org.example.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Exception.EmailAlreadyExistsException;
import org.example.Exception.InvalidCredentialsException;
import org.example.Exception.NoAdmin;
import org.example.Exception.UserNotFoundException;
import org.example.Model.Gender;
import org.example.Model.Role;
import org.example.Model.User;
import org.example.Model.userForm;
import org.example.Repository.UserRepository;
import org.example.Service.RandomPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RandomPasswordService randomPasswordService;

    public User save(User user){
        user.setPassword(randomPasswordService.generateRandomPassword(14));
        user.setLastLoginTimeStamp(null);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email ID is already registered.");
        }
        return userRepository.save(user);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String isValidUserJson(String json) throws JsonProcessingException {
            ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
            String baseErrorMessage = "The following fields are empty: ";
            StringBuilder errorMessage = new StringBuilder(baseErrorMessage);
            if (!node.has("userName")) {
                errorMessage.append("userName, ");
            }
            if (!node.has("email")) {
                errorMessage.append("email, ");
            }
            if (!node.has("gender")) {
                errorMessage.append("gender, ");
            }
            if (!node.has("userRole")) {
                errorMessage.append("userRole, ");
            }

            if (errorMessage.toString().endsWith(", ")) {
                errorMessage.delete(errorMessage.length() - 2, errorMessage.length()); // Remove last comma and space
            }
            return errorMessage.length() > baseErrorMessage.length() ? errorMessage.toString() : null;
    }

    public User convertJsonToUser(String json) throws Exception {
        System.out.println("Json: "+ json);
        return objectMapper.readValue(json, User.class);
    }


    public List<userForm> getAll() {
        List<userForm> userForms = new ArrayList<>();
        List<User> users = userRepository.findAllUsersExceptAdmin();

        for (User user : users) {
            userForm userForm = new userForm();
            userForm.setUserId(user.getUserId());
            userForm.setUserName(user.getUserName());
            userForm.setEmail(user.getEmail());
            userForm.setUserRole(user.getUserRole());
            userForm.setGender(user.getGender());
            userForm.setLastLoginTimeStamp(user.getLastLoginTimeStamp());
            userForms.add(userForm);
        }

        return userForms;
    }


public String getGenderCount() {
    long maleCount = userRepository.countByGender(User.Gender.MALE);
    long femaleCount = userRepository.countByGender(User.Gender.FEMALE) - 1;
    long otherCount = userRepository.countByGender(User.Gender.OTHER);

    List<JsonNode> genderCountList = new ArrayList<>();
    genderCountList.add(createGenderCountJson("male", "Male", maleCount, "hsl(291, 70%, 50%)"));
    genderCountList.add(createGenderCountJson("female", "Female", femaleCount, "hsl(240, 70%, 50%)"));
    genderCountList.add(createGenderCountJson("other", "Other", otherCount, "hsl(0, 0%, 50%)"));
    // You can add counts for other genders if needed

    ArrayNode result = objectMapper.createArrayNode();
    result.addAll(genderCountList);

    return result.toString();
}

    private JsonNode createGenderCountJson(String id, String label, long value, String color) {
        ObjectNode genderCountJson = objectMapper.createObjectNode();
        genderCountJson.put("id", id);
        genderCountJson.put("label", label);
        genderCountJson.put("value", value);
        genderCountJson.put("color", color);
        return genderCountJson;
    }

    public Boolean existByEmail(String email){
        Optional<User> userObj = Optional.ofNullable(userRepository.getUserByEmail(email));
        if(!userObj.isEmpty())
            return true;
        return false;
    }

    public String userLogin(User user) throws UserNotFoundException, InvalidCredentialsException, NoAdmin {
        User existingUser = userRepository.getUserByEmail(user.getEmail());
        if (existingUser == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!userRepository.existsByEmailAndUserRole(user.getEmail(), User.UserRole.ADMIN)) {
            throw new NoAdmin("User is not authorized to log in");
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return tokenService.createTokenFunction(existingUser.getUserId());
    }
}


