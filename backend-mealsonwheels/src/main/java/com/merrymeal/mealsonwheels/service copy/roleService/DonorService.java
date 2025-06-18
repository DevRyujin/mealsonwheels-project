package com.merrymeal.mealsonwheels_backend.service.roleService;

import java.math.BigDecimal;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.DonorDTO;

public interface DonorService {
    DonorDTO getCurrentDonorProfile();
    DonorDTO donate(BigDecimal amount);
}
