package com.techhive.shivamweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.NewArrivalSettings;

@Repository
public interface NewArrivalSettingsRepository extends JpaRepository<NewArrivalSettings, String>{

}
