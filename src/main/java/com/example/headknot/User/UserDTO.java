
package com.example.headknot.User;

import com.example.headknot.Roles.RoleEntity;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private Set<RoleEntity> roles;
    private String email;
}
