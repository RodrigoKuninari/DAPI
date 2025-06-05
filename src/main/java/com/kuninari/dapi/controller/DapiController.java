package com.kuninari.dapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DapiController {

    @GetMapping
    public List<Integer> getAllDevices() {
        return List.of(1,2,3);
    }
}
