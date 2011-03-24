package org.dmack.statsapp.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SA_USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USERNAME" }) })
public class GeneralUser extends User
{

}
