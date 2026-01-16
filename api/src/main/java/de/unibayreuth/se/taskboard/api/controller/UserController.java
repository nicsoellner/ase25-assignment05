package de.unibayreuth.se.taskboard.api.controller;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.MalformedRequestException;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@OpenAPIDefinition(
        info = @Info(
                title = "TaskBoard",
                version = "0.0.1"
        )
)
@Tag(name = "Users")
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
        private final UserService userService;
        private final UserDtoMapper userDtoMapper;

        @Operation(
                summary = "Get all users.",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(type = "array", implementation = UserDto.class)
                                ),
                                description = "All users as a JSON array."
                        )
                }
        )
        @GetMapping
        public ResponseEntity<List<UserDto>> getAll() {
                List<UserDto> userDtos = userService.getAll()
                        .stream() // Create a stream from the List<User>
                        .map(userDtoMapper::fromBusiness) // Map User to UserDto
                        .toList(); // Collect the results as a List<UserDto>

                return ResponseEntity.ok(userDtos);
        }

        @Operation(
                summary = "Get user by ID.",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class)
                                ),
                                description = "The user with the provided ID as a JSON object."
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "No user with the provided ID could not be found."
                        )
                }
        )
        @GetMapping("/{id}")
        public ResponseEntity<UserDto> byId(@PathVariable String id) {
                try{
                        var user = userService.getById(UUID.fromString(id));
                        return ResponseEntity.ok(userDtoMapper.fromBusiness(user));
                }catch (UserNotFoundException e) {
                        return ResponseEntity.notFound().build();
                }
        }

        @Operation(
                summary = "Creates a new user.",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class)
                                ),
                                description = "The new user as a JSON object."
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "ID present, user could not be created!"
                        )
                }
        )
        @PostMapping
        public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
                try {
                        return ResponseEntity.ok(userDtoMapper.fromBusiness(userService.create(new User(userDto.getName()))));
                }catch (MalformedRequestException e) {
                        return ResponseEntity.badRequest().build();
                }

        }
}