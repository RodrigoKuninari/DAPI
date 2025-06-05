package com.kuninari.dapi.service;

import com.kuninari.dapi.domain.Device;
import com.kuninari.dapi.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device createDevice(Device device) {
        Device savedDevice = deviceRepository.save(device);
        return savedDevice;
    }

    public List<Device> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices;
    }
}
