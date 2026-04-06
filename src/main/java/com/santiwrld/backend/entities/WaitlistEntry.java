package com.santiwrld.backend.entities;

import com.santiwrld.backend.config.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WaitlistEntry extends AuditEntity {
    @Id
    @SequenceGenerator(name = "waitlist",
            sequenceName = "waitlist",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waitlist")
    @Column(unique = true, nullable = false, name = "waitlist_id")
    private Long Id;
    @Column(name = "waitlist_email", unique = true, nullable = false)
    private String email;


}
