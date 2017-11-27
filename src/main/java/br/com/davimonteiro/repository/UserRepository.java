package br.com.davimonteiro.repository;

import br.com.davimonteiro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

}
