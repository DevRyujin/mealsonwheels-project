package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.roleDTOs.DonorProfileDTO;

import java.math.BigDecimal;

public interface DonorService {
    DonorProfileDTO getCurrentDonorProfile();
    DonorProfileDTO donate(BigDecimal amount);
    void processDonation(DonorProfileDTO dto);

}
