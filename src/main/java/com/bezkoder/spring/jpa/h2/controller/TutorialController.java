package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.model.TutorialEntity;
import com.bezkoder.spring.jpa.h2.model.TutorialNonEntityPOJO;
import com.bezkoder.spring.jpa.h2.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;

	@GetMapping("/tutorials")
	public ResponseEntity<List<TutorialEntity>> getAllTutorials(@RequestParam(required = false) String title) {
		try {
			List<TutorialEntity> tutorialEntities = new ArrayList<TutorialEntity>();

			if (title == null)
				tutorialRepository.findAll().forEach(tutorialEntities::add);
			else
				tutorialRepository.findByTitleContaining(title).forEach(tutorialEntities::add);

			if (tutorialEntities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorialEntities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<TutorialEntity> getTutorialById(@PathVariable("id") long id) {
		Optional<TutorialEntity> tutorialData = tutorialRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/tutorials/fromTitle/{title}")
	public ResponseEntity<TutorialNonEntityPOJO> getTutorialById1(@PathVariable("title") String title) {
		TutorialNonEntityPOJO tutorialData = tutorialRepository.getTutorialFromTitleForPOJO(title);

		if (tutorialData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<TutorialNonEntityPOJO>(tutorialData, HttpStatus.OK);
		}
	}

	@PostMapping("/tutorials")
	public ResponseEntity<TutorialEntity> createTutorial(@RequestBody TutorialEntity tutorialEntity) {
		try {
			TutorialEntity _tutorialEntity = tutorialRepository
					.save(new TutorialEntity(tutorialEntity.getTitle(), tutorialEntity.getDescription(), false));
			return new ResponseEntity<>(_tutorialEntity, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<TutorialEntity> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialEntity tutorialEntity) {
		Optional<TutorialEntity> tutorialData = tutorialRepository.findById(id);

		if (tutorialData.isPresent()) {
			TutorialEntity _tutorialEntity = tutorialData.get();
			_tutorialEntity.setTitle(tutorialEntity.getTitle());
			_tutorialEntity.setDescription(tutorialEntity.getDescription());
			_tutorialEntity.setPublished(tutorialEntity.isPublished());
			return new ResponseEntity<>(tutorialRepository.save(_tutorialEntity), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			tutorialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/tutorials/published")
	public ResponseEntity<List<TutorialEntity>> findByPublished() {
		try {
			List<TutorialEntity> tutorialEntities = tutorialRepository.findByPublished(true);

			if (tutorialEntities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorialEntities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
