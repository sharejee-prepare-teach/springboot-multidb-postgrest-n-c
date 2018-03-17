package com.foobar.foo.domain;

import javax.persistence.*;

@Entity
@Table(name = "foo")
public class Foo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "FOO")
  private String foo;

  Foo(String foo) {
    this.foo = foo;
  }

  Foo() {
    // Default constructor needed by JPA
  }

  public String getFoo() {
    return foo;
  }

}
