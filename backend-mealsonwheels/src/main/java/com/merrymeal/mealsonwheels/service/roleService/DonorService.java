package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.roleDTOs.DonorDTO;

import java.math.BigDecimal;

public interface DonorService {
    DonorDTO getCurrentDonorProfile();
    DonorDTO donate(BigDecimal amount);
}
