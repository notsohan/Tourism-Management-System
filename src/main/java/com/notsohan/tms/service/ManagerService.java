package com.notsohan.tms.service;

import com.notsohan.tms.model.Manager;
import com.notsohan.tms.model.ManagerModel;
import com.notsohan.tms.model.ManagerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerService(ManagerRepository managerRepository,
                          PasswordEncoder passwordEncoder){
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(ManagerModel managerModel) {
        Manager manager = new Manager();
        manager.setFirstName(managerModel.getFirstName());
        manager.setLastName(managerModel.getLastName());
        manager.setEmail(managerModel.getEmail());
        manager.setGender(managerModel.getGender());
        manager.setPhoneNo(managerModel.getPhoneNo());
        manager.setPassword(passwordEncoder.encode(managerModel.getPassword()));

        managerRepository.save(manager);
    }

    public Manager findByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    public void changePassword(Manager manager, String password) {
        manager.setPassword(passwordEncoder.encode(password));
        managerRepository.save(manager);
    }

    public boolean checkIfValidOldPassword(Manager manager, String oldPassword) {
        return passwordEncoder.matches(oldPassword, manager.getPassword());
    }
}
