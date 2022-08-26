package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.com.obss.jip.bookportal.component.JwtTokenUtil;
import tr.com.obss.jip.bookportal.dto.*;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.service.ImageService;
import tr.com.obss.jip.bookportal.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final JwtTokenUtil tokenUtil;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getUsers(
            @RequestParam(defaultValue = "200") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "") String sortField,
            @RequestParam(defaultValue = "") String sortOrder,
            @RequestParam(defaultValue = "") String searchParam) {

        PaginationRequest paginationRequest =
                new PaginationRequest(size, page, sortField, sortOrder, searchParam);
        return new ResponseDto(true, null, userService.getPage(paginationRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getUser(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, userService.getUserDto(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto, RoleType.ROLE_USER);

        return new ResponseDto(true, "User created successfully", null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteThisUser(@RequestHeader("Authorization") String token) {
        Long id = tokenUtil.getIdFromToken(token.substring(7));
        userService.deleteUser(id);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto updateUser(
            @PathVariable(name = "id") Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.updateUser(id, updateUserDto);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto updateThisUser(
            @RequestBody @Valid UpdateUserDto updateUserDto,
            @RequestHeader("Authorization") String token) {
        Long id = tokenUtil.getIdFromToken(token.substring(7));
        userService.updateUser(id, updateUserDto);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PostMapping("/favorite/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto addFavoriteBook(
            @PathVariable Long bookId, @RequestHeader("Authorization") String token) {
        Long id = tokenUtil.getIdFromToken(token.substring(7));
        userService.addFavoriteBook(id, bookId);

        return new ResponseDto(true, "Favorite book added successfully", null);
    }

    @PostMapping("/read/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto addReadBook(
            @PathVariable Long bookId, @RequestHeader("Authorization") String token) {
        Long id = tokenUtil.getIdFromToken(token.substring(7));
        userService.addReadBook(id, bookId);

        return new ResponseDto(true, "Read book added successfully", null);
    }

    @DeleteMapping("/favorite/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto removeFavoriteBook(
            @PathVariable Long bookId, @RequestHeader("Authorization") String token) {
        Long id = tokenUtil.getIdFromToken(token.substring(7));
        userService.removeFavoriteBook(id, bookId);

        return new ResponseDto(true, "Favorite book removed successfully", null);
    }

    @DeleteMapping("/read/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto removeReadBook(
            @PathVariable Long bookId, @RequestHeader("Authorization") String token) {
        Long id = tokenUtil.getIdFromToken(token.substring(7));
        userService.removeReadBook(id, bookId);

        return new ResponseDto(true, "Read book removed successfully", null);
    }

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getProfile(@RequestHeader("Authorization") String token) {
        Long id = tokenUtil.getIdFromToken(token.substring(7));
        UserDto userDto = userService.getUserDto(id);

        return new ResponseDto(true, null, userDto);
    }

    @PostMapping("/{userId}/image")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseDto uploadImageAdmin(
            @RequestParam("image") MultipartFile multipartImage, @PathVariable Long userId) {
        userService.addImage(userId, multipartImage);

        return new ResponseDto(true, "Image saved successfully", null);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseDto uploadImageUser(
            @RequestParam("image") MultipartFile multipartImage,
            @RequestHeader("Authorization") String token) {

        Long userId = tokenUtil.getIdFromToken(token.substring(7));
        userService.addImage(userId, multipartImage);

        return new ResponseDto(true, "Image saved successfully", null);
    }

    @GetMapping(value = "/{userId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource getProfileImage(@PathVariable Long userId) {
        byte[] image = userService.getImage(userId);

        return new ByteArrayResource(image);
    }
}
