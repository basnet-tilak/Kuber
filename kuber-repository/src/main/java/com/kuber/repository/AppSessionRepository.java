package com.kuber.repository;

import com.kuber.domain.AppSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSessionRepository extends JpaRepository<AppSession, String> {
}