package com.example.course_ventures.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;	import org.springframework.stereotype.Service;
import com.example.course_ventures.entity.Trainer;
import com.example.course_ventures.enums.Role;
import com.example.course_ventures.exception.TrainerNotFound;
import com.example.course_ventures.repository.TrainerRepository;

	@Service
	public class TrainerService {

	    @Autowired
	    private TrainerRepository repo;

	    @Autowired
	    private UserService userService;

	    
	    // Register Trainer
	    public Trainer saveTrainerInfo(Trainer trainer) {

	        trainer.setRole(Role.TRAINER);

	        return (Trainer) userService.saveUser(trainer);
	    }

	    // Get Trainer By Id
	    	public Trainer findTrainerById(int id) {

	    	    return repo.findById(id)
	    	            .orElseThrow(() -> new TrainerNotFound());
	    	}	    

	    // Get All Trainers
	    public List<Trainer> findAllTrainer() {
	        return repo.findAll();
	    }

	    // Update Trainer
	    public Trainer updateTrainer(int id, Trainer trainer) {

	        Trainer dbTrainer = findTrainerById(id);

	        dbTrainer.setName(trainer.getName());
	        dbTrainer.setEmail(trainer.getEmail());
	        dbTrainer.setSpecilization(trainer.getSpecilization());
	        dbTrainer.setExperience(trainer.getExperience());

	        return repo.save(dbTrainer);
	    }
	    // Delete Trainer
	    public String deleteTrainer(int id) {

	        Trainer trainer = findTrainerById(id);

	        repo.delete(trainer);

	        return "Trainer Deleted Successfully";
	    }
	
}
