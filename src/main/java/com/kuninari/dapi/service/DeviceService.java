package com.kuninari.dapi.service;

import com.kuninari.dapi.domain.Device;
import com.kuninari.dapi.domain.State;
import com.kuninari.dapi.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Transactional
    public Optional<Device> updateDevice(Long id, Device newDevice) {
        return deviceRepository.findById(id)
                .filter(existingDevice -> !State.IN_USE.equals(existingDevice.getState()))
                .map(existingDevice -> {
                    existingDevice.setName(newDevice.getName());
                    existingDevice.setBrand(newDevice.getBrand());
                    existingDevice.setState(newDevice.getState());
                    return deviceRepository.save(existingDevice);
                });
    }

    public Optional<Device> getDevice(Long id) {
        return deviceRepository.findById(id);
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> getDevicesByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    public List<Device> getDevicesByState(State state) {
        return deviceRepository.findByState(state);
    }

    @Transactional
    public boolean deleteDevice(Long id) {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            if (State.IN_USE.equals(device.get().getState()))  {
                return false;
            }
            deviceRepository.delete(device.get());
            return true;
        }
        return false;
    }
}
