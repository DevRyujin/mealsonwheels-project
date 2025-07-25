package com.merrymeal.mealsonwheels.service.mealOrderService;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.Meal;
import com.merrymeal.mealsonwheels.model.PartnerProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.MealRepository;
import com.merrymeal.mealsonwheels.repository.PartnerProfileRepository;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.util.DistanceUtil;
import com.merrymeal.mealsonwheels.model.MealType;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Base64;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final PartnerProfileRepository partnerProfileRepository;
    private final UserRepository userRepository;

    public MealServiceImpl(MealRepository mealRepository,
                           PartnerProfileRepository partnerProfileRepository,
                           UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.partnerProfileRepository = partnerProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MealDTO saveMeal(MealDTO dto, Long partnerId) {
        Meal meal = mapToEntity(dto);
        meal.setMealCreatedDate(LocalDateTime.now());

        User partnerUser = userRepository.findById(partnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Partner user not found with ID: " + partnerId));
        meal.setPartner(partnerUser);

        return mapToDTO(mealRepository.save(meal));
    }


    @Override
    public List<MealDTO> getAllMeals() {
        return mealRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MealDTO> getMealById(Long id) {
        return mealRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public MealDTO updateMeal(Long id, MealDTO dto) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));

        meal.setMealName(dto.getMealName());
        meal.setMealDesc(dto.getMealDesc());
        meal.setMealType(dto.getMealType());
        meal.setMealDietary(dto.getMealDietary());
        if (dto.getMealPhoto() != null) {
            meal.setMealPhoto(dto.getMealPhoto());
        }

        if (dto.getPartnerId() != null) {
            User partnerUser = userRepository.findById(dto.getPartnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Partner user not found with ID: " + dto.getPartnerId()));
            meal.setPartner(partnerUser);
        }

        return mapToDTO(mealRepository.save(meal));
    }

    @Transactional
    @Override
    public void deleteMeal(Long id) {
        try {
            System.out.println("🔥 Trying to delete meal ID: " + id);

            Meal meal = mealRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));

            System.out.println("✅ Meal found. Deleting...");
            mealRepository.delete(meal);
        } catch (DataIntegrityViolationException e) {
            // Friendly message to frontend
            throw new IllegalStateException("Cannot delete meal. It has already been ordered.");
        }
    }


    @Override
    public void uploadMealPhoto(Long mealId, MultipartFile file) throws IOException {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealId));
        meal.setMealPhoto(file.getBytes());
        meal.setMealPhotoType(file.getContentType()); // store MIME type
        mealRepository.save(meal);
    }


    @Override
    public byte[] getMealPhoto(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealId));
        return meal.getMealPhoto();
    }

    @Override
    public String getMealPhotoType(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealId));
        return meal.getMealPhotoType();
    }

    // === Helper Methods ===
    private MealDTO mapToDTO(Meal meal) {
        String base64Photo = null;
        if (meal.getMealPhoto() != null) {
            base64Photo = Base64.getEncoder().encodeToString(meal.getMealPhoto());
        }

        // 🔍 Get partner location if available
        PartnerProfile partnerProfile = partnerProfileRepository.findByUser(meal.getPartner())
                .orElse(null); // Optional: null-safe

        Double latitude = null;
        Double longitude = null;
        if (partnerProfile != null) {
            latitude = partnerProfile.getCompanyLocationLat();
            longitude = partnerProfile.getCompanyLocationLong();
        }

        return MealDTO.builder()
                .id(meal.getId())
                .mealName(meal.getMealName())
                .mealDesc(meal.getMealDesc())
                .mealType(meal.getMealType())
                .mealDietary(meal.getMealDietary())
                .mealCreatedDate(meal.getMealCreatedDate())
                .mealPhotoType(meal.getMealPhotoType())
                .photoData(base64Photo)
                .partnerId(meal.getPartner() != null ? meal.getPartner().getId() : null)
                .menuId(meal.getMenu() != null ? meal.getMenu().getId() : null)

                //
                .partnerLatitude(latitude)
                .partnerLongitude(longitude)

                .build();
    }



    private Meal mapToEntity(MealDTO dto) {
        Meal meal = new Meal();
        meal.setMealName(dto.getMealName());
        meal.setMealDesc(dto.getMealDesc());
        meal.setMealType(dto.getMealType());
        meal.setMealDietary(dto.getMealDietary());

        if (dto.getPhotoData() != null && dto.getPhotoData().contains(",")) {
            // Example: data:image/png;base64,iVBORw0KGgo...
            String[] parts = dto.getPhotoData().split(",");
            String metadata = parts[0]; // "data:image/png;base64"
            String base64Data = parts[1]; // actual image

            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Data);
            meal.setMealPhoto(decodedBytes);

            String contentType = metadata.substring(metadata.indexOf(":") + 1, metadata.indexOf(";"));
            meal.setMealPhotoType(contentType); // set MIME type (e.g. image/png)
        }

        return meal;
    }

    @Override
    public Meal getMealEntityById(Long mealId) {
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealId));
    }

    public List<MealDTO> getMealsByDistanceForMember(Long memberId) {
        // 1. Get member location
        User memberUser = userRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member user not found with ID: " + memberId));
        Double memberLat = memberUser.getLatitude();
        Double memberLon = memberUser.getLongitude();

        if (memberLat == null || memberLon == null) {
            throw new RuntimeException("❌ Member location is missing.");
        }

        // 2. Fetch all meals
        List<Meal> allMeals = mealRepository.findAll();

        // 3. Filter meals using DistanceUtil
        return allMeals.stream()
                .filter(meal -> {
                    PartnerProfile partner = partnerProfileRepository.findByUser(meal.getPartner()).orElse(null);
                    if (partner == null) return false;

                    Double partnerLat = partner.getCompanyLocationLat();
                    Double partnerLon = partner.getCompanyLocationLong();
                    if (partnerLat == null || partnerLon == null) return false;

                    boolean isFrozenMeal = DistanceUtil.isFrozenMeal(memberLat, memberLon, partnerLat, partnerLon);

                    System.out.println("▶️ Meal: " + meal.getMealName());
                    System.out.println("   Member Location: (" + memberLat + ", " + memberLon + ")");
                    System.out.println("   Partner Location: (" + partnerLat + ", " + partnerLon + ")");

                    double distance = DistanceUtil.calculateDistanceKm(memberLat, memberLon, partnerLat, partnerLon);
                    System.out.println("   ➡️ Calculated Distance: " + distance + " km");
                    System.out.println("   ➡️ isFrozenMeal? " + isFrozenMeal);
                    System.out.println("   ➡️ Meal Type: " + meal.getMealType());
                    System.out.println("   ✅ Will Include? " +
                            ((isFrozenMeal && meal.getMealType() == MealType.FROZEN) ||
                                    (!isFrozenMeal && meal.getMealType() == MealType.HOT)));


                    MealType type = meal.getMealType();
                    if (type == null) return false;

                    if (isFrozenMeal) {
                        return type == MealType.FROZEN;
                    } else {
                        return type == MealType.HOT;
                    }


                })
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealDTO> getMealsByDistanceForCaregiver(Long caregiverId) {
        // 1. Get caregiver location
        User caregiverUser = userRepository.findById(caregiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Caregiver user not found with ID: " + caregiverId));
        Double caregiverLat = caregiverUser.getLatitude();
        Double caregiverLon = caregiverUser.getLongitude();

        if (caregiverLat == null || caregiverLon == null) {
            throw new RuntimeException("❌ Caregiver location is missing.");
        }

        // 2. Fetch all meals
        List<Meal> allMeals = mealRepository.findAll();

        // 3. Filter meals based on logic
        return allMeals.stream()
                .filter(meal -> {
                    PartnerProfile partner = partnerProfileRepository.findByUser(meal.getPartner()).orElse(null);
                    if (partner == null) return false;

                    Double partnerLat = partner.getCompanyLocationLat();
                    Double partnerLon = partner.getCompanyLocationLong();
                    if (partnerLat == null || partnerLon == null) return false;

                    boolean isFrozenMeal = DistanceUtil.isFrozenMeal(caregiverLat, caregiverLon, partnerLat, partnerLon);

                    MealType type = meal.getMealType();
                    if (type == null) return false;

                    if (isFrozenMeal) {
                        return type == MealType.FROZEN;
                    } else {
                        return type == MealType.HOT;
                    }


                })
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


}
