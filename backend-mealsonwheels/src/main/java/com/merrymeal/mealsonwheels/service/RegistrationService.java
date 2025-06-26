package com.merrymeal.mealsonwheels.service.old;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.PartnerProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.VolunteerProfileDTO;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {


    private UserRepository userRepository;


    private MemberProfileRepository memberProfileRepository;


    private CaregiverProfileRepository caregiverProfileRepository;


    private VolunteerProfileRepository volunteerProfileRepository;


    private PartnerProfileRepository partnerProfileRepository;


    private PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository,
                               MemberProfileRepository memberProfileRepository,
                               CaregiverProfileRepository caregiverProfileRepository,
                               VolunteerProfileRepository volunteerProfileRepository,
                               PartnerProfileRepository partnerProfileRepository,
                               PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.memberProfileRepository = memberProfileRepository;
        this.caregiverProfileRepository = caregiverProfileRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
        this.partnerProfileRepository = partnerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        // 1. Check if user exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email Already Registered");
        }

        // 2. Create and save user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user); // temporarily save before attaching profile

        // 3. Save corresponding profile based on Role
        switch (request.getRole()) {
            case MEMBER:
                MemberProfileDTO memberDTO = new MemberProfileDTO();
                MemberProfile member = new MemberProfile();
                member.setUser(user);
                member.setDietaryRestrictions(memberDTO.getDietaryRestrictions());
                memberProfileRepository.save(member);
                break;

            case CAREGIVER:
                CaregiverProfileDTO caregiverDTO = new CaregiverProfileDTO();
                CaregiverProfile caregiver = new CaregiverProfile();
                caregiver.setUser(user);
                caregiver.setMemberAddressToAssist(caregiverDTO.getMemberAddressToAssist());
                caregiver.setMemberPhoneNumberToAssist(caregiverDTO.getMemberPhoneNumberToAssist());
                caregiver.setMemberAddressToAssist(caregiverDTO.getMemberAddressToAssist());
                caregiver.setMemberRelationship(caregiverDTO.getMemberRelationship());
                caregiverProfileRepository.save(caregiver);
                break;

            case VOLUNTEER:
                VolunteerProfileDTO volunteerDTO = new VolunteerProfileDTO();
                VolunteerProfile volunteer = new VolunteerProfile();
                volunteer.setUser(user);
                volunteer.setServiceType(volunteerDTO.getServiceType());
                volunteer.setVolunteerDuration(volunteerDTO.getVolunteerDuration());
                volunteer.setAvailableDays(volunteerDTO.getAvailableDays());
                volunteerProfileRepository.save(volunteer);
                break;

            case PARTNER:
                PartnerProfileDTO partnerDTO = new PartnerProfileDTO();
                PartnerProfile partner = new PartnerProfile();
                partner.setUser(user);
                partner.setCompanyName(partnerDTO.getCompanyName());
                partner.setPartnershipDuration(partnerDTO.getPartnershipDuration());
                partnerProfileRepository.save(partner);
                break;

            default:
                throw new IllegalArgumentException("Unsupported user role: " + request.getRole());
        }
    }
}
