
package org.workflow.User;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

import org.workflow.Roles.RoleEntity;

@Data
public class UserDTO {
    private UUID id;
    private String fullName;
    private Set<RoleEntity> roles;
    private String email;
}
