package com.Library.LibraryApplication.Repositories;

import com.Library.LibraryApplication.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    public AppUser findByEmail(String email);
}
