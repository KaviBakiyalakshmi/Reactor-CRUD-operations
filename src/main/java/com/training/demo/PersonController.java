package com.training.demo;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import java.util.Optional;





@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public Flux<Person> getAllPersons() {
        return Flux.fromIterable(personRepository.findAll());
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable Long id) {
        return Mono.justOrEmpty(personRepository.findById(id));
    }

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        return Mono.just(personRepository.save(person));
    }
    
    @PutMapping("/{id}")
    public Mono<Person> updatePerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        Optional<Person> existingPersonOpt = personRepository.findById(id);

        if (existingPersonOpt.isPresent()) {
            Person existingPerson = existingPersonOpt.get();
            existingPerson.setFirstName(updatedPerson.getFirstName());
            existingPerson.setLastName(updatedPerson.getLastName());
            return Mono.just(existingPerson);
        } else {
            return Mono.empty();
        }
    }


    
    @DeleteMapping("/{id}")
    public Mono<String> deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return Mono.just("Person with ID " + id + " deleted successfully");
    }

   
    

}

