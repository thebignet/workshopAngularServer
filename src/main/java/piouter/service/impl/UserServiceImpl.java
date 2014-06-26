package piouter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.entity.User;
import piouter.repository.UserRepository;
import piouter.service.UserService;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional
    public UserDto getUserWithFollowing(String id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            userRepository.save(user = new User(id));
        }
        return getUserDto(user);
    }

    @Override
    public ResponseDto create(String id) {
        ResponseDto responseDto;
        if (userRepository.exists(id)) {
            responseDto = new ResponseDto(1, "User already exists");
        } else {
            userRepository.save(new User(id));
            responseDto = ResponseDto.OK;
        }
        return responseDto;
    }

    private Predicate<User> notMe(String userId) {
        return u -> {
            log.info("ici et la : " + !u.getId().equals(userId));
            return !u.getId().equals(userId);
        };
    }

    private Predicate<User> withoutFollowers(String userId) {
        User me = userRepository.findOne(userId);
        Set<User> myFollowings = me.getFollowing();
        return u -> {
            log.info("Checking if " + u.toString() + " belongs to " + myFollowings.toString());
            return !myFollowings.contains(u);
        };
    }

    @Transactional
    public List<UserDto> getUsersMatching(String userId, String pattern, boolean withoutFollowers) {
        Predicate<User> filterUsers = notMe(userId);
        if (withoutFollowers) {
            filterUsers = filterUsers.and(withoutFollowers(userId));
        }
        return userRepository.findMatchingIdIgnoreCaseOrderById(makePattern(pattern)).stream().filter(filterUsers)
                .collect(Collectors.mapping(user -> getSimpleUserDto(user), Collectors.toList()));
    }

    @Override
    @Transactional
    public ResponseDto addFollowingToUser(String id, String followId) {
        ResponseDto responseDto;
        User user = userRepository.findOne(id);
        User following = userRepository.findOne(followId);
        if (user == null || following == null) {
            responseDto = new ResponseDto(1, "Unknown user");
        } else {
            user.addFollowing(following);
            responseDto = ResponseDto.OK;
        }
        return responseDto;
    }

    @Override
    @Transactional
    public ResponseDto removeFollowingToUser(String id, String followId) {
        ResponseDto responseDto = ResponseDto.OK;
        User user = userRepository.findOne(id);
        if (user == null) {
            responseDto = new ResponseDto(1, "User unknown : " + id);
        } else {
            User following = userRepository.findOne(followId);
            if (following == null) {
                responseDto = new ResponseDto(1, "User unknown : " + followId);
            } else {
                user.removeFollowing(following);
            }
        }
        return responseDto;
    }

    @Transactional
    public Collection<UserDto> getFollowers(String id) {
        Collection<User> followers = userRepository.followers(id);
        Collection<UserDto> followersDto = new ArrayList<>(followers.size());
        followers.forEach(f -> followersDto.add(new UserDto(f.getId(), Collections.EMPTY_LIST)));
        return followersDto;
    }

    private UserDto getUserDto(User user) {
        UserDto userDto = null;
        if (user != null) {
            String id = user.getId();
            Collection<User> following = user.getFollowing();
            Collection<UserDto> followingDto = new ArrayList<UserDto>(following.size());
            following.forEach(u -> followingDto.add(getSimpleUserDto(u)));
            userDto = new UserDto(id, followingDto);
        }
        return userDto;
    }

    private UserDto getSimpleUserDto(User user) {
        if (user != null) {
            return new UserDto(user.getId(), Collections.emptyList());
        }
        return null;
    }

    private static String makePattern(String pattern) {
        return "%" + pattern.toLowerCase() + "%";
    }

}
