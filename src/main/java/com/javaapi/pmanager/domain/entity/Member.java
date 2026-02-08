// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)
// O MEMBER NÃO É MAIS ÚTIL (UTILIZAR A USER)

package com.javaapi.pmanager.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "secret", nullable = false, length = 36)
    private String secret;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}
