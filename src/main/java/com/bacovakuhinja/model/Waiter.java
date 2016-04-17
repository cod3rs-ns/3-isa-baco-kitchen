package com.bacovakuhinja.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "waiters")
@PrimaryKeyJoinColumn(name = "w_id")
public class Waiter extends Employee{

}
