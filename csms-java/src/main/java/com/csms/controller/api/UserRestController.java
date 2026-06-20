package com.csms.controller.api;

import com.csms.entity.User;
import com.csms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller for Users.
 * Base path: /api/users
 *
 * This controller is additive — it does NOT replace or interfere with
 * the existing AdminUserController (Thymeleaf-based).
 *
 * Security note: The User.password field is annotated with
 * @JsonProperty(access = WRITE_ONLY) on the entity, so hashed passwords
 * are NEVER returned in any REST response from this controller.
 *
 * Avatar upload is not supported via JSON REST API — use the admin UI for that.
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users
     * Returns all users. Password field is excluded from the JSON response.
     */
    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * GET /api/users/{id}
     * Returns a single user by ID. Password is excluded. Returns 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/users
     * Creates a new user.
     * Include a "rawPassword" field in the JSON body to set the password.
     * If omitted, the default password "change-me" is assigned (handled by UserService).
     * Avatar upload is not supported here — use the admin UI.
     * Returns 201 Created.
     *
     * Example request body:
     * {
     *   "firstname": "Jane",
     *   "lastname": "Doe",
     *   "username": "jane.doe",
     *   "type": 2,
     *   "status": 1,
     *   "rawPassword": "securepassword"
     * }
     */
    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserCreateRequest request) {
        User user = request.toUser();
        user.setId(null);
        User saved = userService.save(user, request.getRawPassword(), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * PUT /api/users/{id}
     * Updates an existing user's profile fields. Returns 404 if not found.
     * Provide "rawPassword" to change the password; omit it to keep the existing password.
     * Avatar is not updated via this endpoint.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody UserCreateRequest request) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = request.toUser();
        user.setId(id);
        User updated = userService.save(user, request.getRawPassword(), null);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/users/{id}
     * Deletes a user and their associated avatar file. Returns 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------------------------------------------------
    // Inner DTO — carries rawPassword separately from the User entity so
    // the hashed password field on User is never populated from REST input.
    // -----------------------------------------------------------------------

    /**
     * Request body DTO for creating and updating users via the REST API.
     * Keeps rawPassword separate from the User entity to avoid confusion
     * with the hashed password stored on the entity.
     */
    public static class UserCreateRequest {
        private String firstname;
        private String middlename;
        private String lastname;
        private String username;
        private String rawPassword;
        private Integer type;
        private Integer status;

        public User toUser() {
            User u = new User();
            u.setFirstname(firstname);
            u.setMiddlename(middlename);
            u.setLastname(lastname);
            u.setUsername(username);
            u.setType(type != null ? type : 2);
            u.setStatus(status != null ? status : 1);
            return u;
        }

        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }

        public String getMiddlename() { return middlename; }
        public void setMiddlename(String middlename) { this.middlename = middlename; }

        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getRawPassword() { return rawPassword; }
        public void setRawPassword(String rawPassword) { this.rawPassword = rawPassword; }

        public Integer getType() { return type; }
        public void setType(Integer type) { this.type = type; }

        public Integer getStatus() { return status; }
        public void setStatus(Integer status) { this.status = status; }
    }
}
