package com.example.port;

import com.example.models.Address;
import java.util.Optional;

public interface AddressRepository {
    Address save(Address address);
    Address delete(Address address);
    Optional<Address> findById(Integer id);
}
