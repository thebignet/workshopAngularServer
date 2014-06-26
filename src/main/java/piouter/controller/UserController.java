package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "{userId:.+}")
    public UserDto getUser(@PathVariable("userId") String id){
        return userService.getUserWithFollowing(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT,value = "{userId:.+}")
    public ResponseDto create(@PathVariable("userId") String id) {
        return userService.create(id);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "{userId:.+}/follow/{followId}")
    public ResponseDto addFollowing(@PathVariable("userId") String id, @PathVariable("followId") String followId){
        return userService.addFollowingToUser(id, followId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE,value = "{userId:.+}/follow/{followId}")
    public ResponseDto removeFollowee(@PathVariable("userId") String id, @PathVariable("followId") String followId){
        return userService.removeFollowingToUser(id, followId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "{userId:.+}/filter/{pattern}")
    public List<UserDto> filterUsers(@PathVariable("userId") String userId,@PathVariable("pattern") String pattern, @RequestParam(value = "filterFollowing", required = false, defaultValue = "false") boolean filterFollowing){
        return userService.getUsersMatching(userId,pattern,filterFollowing);
    }
}
