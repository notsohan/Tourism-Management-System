package com.notsohan.tms.service;

import com.notsohan.tms.model.Package;
import com.notsohan.tms.model.PackageModel;
import com.notsohan.tms.model.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    private final PackageRepository packageRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository){
        this.packageRepository = packageRepository;
    }

    public void insertPackage(PackageModel packageModel) {
        Package p = new Package();
        p.setPacName(packageModel.getPacName());
        p.setHotelName(packageModel.getHotelName());
        p.setCost(packageModel.getCost());
        p.setLocation(packageModel.getLocation());
        p.setCountry(packageModel.getCountry());

        packageRepository.save(p);
    }

    public List<Package> allPackage() {
        return packageRepository.findAll();
    }

    public Package packageDetails(Long id) {
        return packageRepository.findById(id).get();
    }
}
