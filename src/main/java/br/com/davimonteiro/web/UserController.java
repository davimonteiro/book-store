package br.com.davimonteiro.web;

import br.com.davimonteiro.domain.User;
import br.com.davimonteiro.repository.UserRepository;
import com.google.common.base.Throwables;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Inject
    private UserRepository repository;

    //------------------- Create an entity ------------------ //

    @RequestMapping(method = POST, consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity create(@RequestBody User entity) {
        User result = repository.save(entity);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }


    //------------------- Retrieve all entity ------------------ //

    @RequestMapping(method = GET, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity findAll() {
        List<User> users = this.repository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }


    //------------------- Retrieve one entity ------------------ //

    @RequestMapping(value = "/{id}", method = GET, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity findById(@PathVariable Long id) {
        User entity = repository.findOne(id);
        if (isNull(entity)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(entity, HttpStatus.OK);
    }


    //------------------- Update an entity ------------------ //

    @RequestMapping(value = "/{id}", method = PUT, consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable Long id, @RequestBody User entity) {

        User currentEntity = repository.findOne(id);

        if (isNull(currentEntity)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        try {
            BeanUtils.copyProperties(currentEntity, entity);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

        repository.save(currentEntity);

        return new ResponseEntity(currentEntity, HttpStatus.OK);
    }


    //------------------- Delete an entity ------------------ //

    @RequestMapping(value = "/{id}", method = DELETE, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity detele(@PathVariable Long id) {
        repository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
