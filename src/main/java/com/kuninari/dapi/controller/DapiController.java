package com.kuninari.dapi.controller;

import com.kuninari.dapi.domain.Device;
import com.kuninari.dapi.domain.State;
import com.kuninari.dapi.service.DapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/devices")
public class DapiController {

    @Autowired
    private DapiService dapiService;

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        return new ResponseEntity<>(dapiService.createDevice(device), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable Long id, @RequestBody Device device) {
        Optional<Device> updated = dapiService.updateDevice(id, device);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.ok(Map.of("message", "Device Not Found or it is in use. No changes made."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDevice(@PathVariable Long id) {
        Optional<Device> device = dapiService.getDevice(id);
        if (device.isPresent()) {
            return ResponseEntity.ok(device.get());
        } else {
            return ResponseEntity.ok(Map.of());
        }
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        return new ResponseEntity<>(dapiService.getAllDevices(), HttpStatus.OK);
    }

    @GetMapping("/brands/{brand}")
    public ResponseEntity<List<Device>> getDevicesByBrand(@PathVariable String brand) {
        return new ResponseEntity<>(dapiService.getDevicesByBrand(brand), HttpStatus.OK);
    }

    @GetMapping("/states/{state}")
    public ResponseEntity<List<Device>> getDevicesByState(@PathVariable State state) {
        return new ResponseEntity<>(dapiService.getDevicesByState(state), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        if (dapiService.deleteDevice(id)) {
            return ResponseEntity.ok(Map.of("message", "Device deleted."));
        } else {
            return ResponseEntity.ok(Map.of("message", "Device Not Found or it is in use. No changes made."));
        }
    }
}
