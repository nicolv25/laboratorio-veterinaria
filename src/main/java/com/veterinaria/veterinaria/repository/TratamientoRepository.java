package com.veterinaria.veterinaria.repository;
import com.veterinaria.veterinaria.entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {}