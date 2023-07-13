package com.notsohan.tms.controller;

import com.notsohan.tms.model.Package;
import com.notsohan.tms.model.PackageModel;
import com.notsohan.tms.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {

    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService){
        this.packageService = packageService;
    }

    @PostMapping("/insetPackage")
    public String insertPackage(@RequestBody PackageModel packageModel){
        packageService.insertPackage(packageModel);
        return "Success!";
    }

    @GetMapping("/allPackages")
    public List<Package> allPackage(){
        return packageService.allPackage();
    }

    @GetMapping("/packageDetails{id}")
    public Package packageDetails(@PathVariable Long id){
        return packageService.packageDetails(id);
    }
}
