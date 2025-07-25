package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.Task;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.model.VolunteerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByVolunteer(User volunteer);
    List<Task> findByRider(User rider);// ✅ Correct
}
