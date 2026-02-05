package dev.filipe.user.service;

import dev.filipe.user.domain.UserModel;
import dev.filipe.user.producer.UserProducer;
import dev.filipe.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    public List<UserModel>  findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public UserModel saveAndPublish(UserModel userModel) {
        userModel = userRepository.save(userModel);
        userProducer.send(userModel);
        return userModel;
    }
}
