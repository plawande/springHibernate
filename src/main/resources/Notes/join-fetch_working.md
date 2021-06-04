>How one-to-one relationships work-out when you have used join fetch?
This looks simple but can get a bit tricky to understand when you try to compare it with something like em.find(). <br /><br /> The query we used is below.

    select p from Person p left join fetch p.vehicles v where p.id = :id

My classes relationships are as below and this would shown be by case-1:


<u>Case-1</u>
```

Person.java
@OneToOne(mappedBy="person", cascade=CascadeType.ALL)
private Passport passport;

@OneToOne(mappedBy="person", cascade=CascadeType.ALL)
private Profile profile;

@OneToMany(mappedBy="person", cascade=CascadeType.ALL, orphanRemoval = true)
private List<Vehicle> vehicles;

Passport.java
@OneToOne(fetch=FetchType.LAZY)
@JoinColumn(name="person_id")
private Person person;

Profile.java
@OneToOne(fetch=FetchType.LAZY)
@JoinColumn(name="person_id")
private Person person;

Vehicle.java
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="person_id")
private Person person;
```

The queries generated are as below.

~~~roomsql
Hibernate:
    select
        person0_.id as id1_0_0_,
        vehicles1_.id as id1_3_1_,
        person0_.name as name2_0_0_,
        vehicles1_.number as number2_3_1_,
        vehicles1_.person_id as person_i4_3_1_,
        vehicles1_.type as type3_3_1_,
        vehicles1_.person_id as person_i4_3_0__,
        vehicles1_.id as id1_3_0__
    from
        person_base person0_
    left outer join
        person_vehicle vehicles1_
            on person0_.id=vehicles1_.person_id
    where
        person0_.id=?
Hibernate:
    select
        passport0_.id as id1_1_0_,
        passport0_.issued_on as issued_o2_1_0_,
        passport0_.number as number3_1_0_,
        passport0_.person_id as person_i5_1_0_,
        passport0_.valid_till as valid_ti4_1_0_
    from
        person_passport passport0_
    where
        passport0_.person_id=?
Hibernate:
    select
        profile0_.id as id1_2_0_,
        profile0_.city as city2_2_0_,
        profile0_.person_id as person_i4_2_0_,
        profile0_.phone_no as phone_no3_2_0_
    from
        person_profile profile0_
    where
        profile0_.person_id=?
~~~

Till here it looks quite simple. Now let's make a change.

<u>Case-2</u>

Remove the (fetch=FetchType.LAZY) from Passport.java

```
Passport.java
@OneToOne
@JoinColumn(name="person_id")
private Person person;
```

The queries generated are as below.

~~~roomsql
Hibernate: 
    select
        person0_.id as id1_0_0_,
        vehicles1_.id as id1_3_1_,
        person0_.name as name2_0_0_,
        vehicles1_.number as number2_3_1_,
        vehicles1_.person_id as person_i4_3_1_,
        vehicles1_.type as type3_3_1_,
        vehicles1_.person_id as person_i4_3_0__,
        vehicles1_.id as id1_3_0__ 
    from
        person_base person0_ 
    left outer join
        person_vehicle vehicles1_ 
            on person0_.id=vehicles1_.person_id 
    where
        person0_.id=?
Hibernate: 
    select
        passport0_.id as id1_1_2_,
        passport0_.issued_on as issued_o2_1_2_,
        passport0_.number as number3_1_2_,
        passport0_.person_id as person_i5_1_2_,
        passport0_.valid_till as valid_ti4_1_2_,
        person1_.id as id1_0_0_,
        person1_.name as name2_0_0_,
        profile2_.id as id1_2_1_,
        profile2_.city as city2_2_1_,
        profile2_.person_id as person_i4_2_1_,
        profile2_.phone_no as phone_no3_2_1_ 
    from
        person_passport passport0_ 
    left outer join
        person_base person1_ 
            on passport0_.person_id=person1_.id 
    left outer join
        person_profile profile2_ 
            on person1_.id=profile2_.person_id 
    where
        passport0_.person_id=?
~~~

Just two queries generated as opposed to three in the 1st case. Weird!
Now revert case-2 (add back (fetch=FetchType.LAZY) on Passport.java) 
and keeping case-1 as base let's make another change.

<u>Case-3</u>

Remove the (fetch=FetchType.LAZY) from Profile.java

```
Profile.java
@OneToOne
@JoinColumn(name="person_id")
private Person person;
```

~~~roomsql
Hibernate: 
    select
        person0_.id as id1_0_0_,
        vehicles1_.id as id1_3_1_,
        person0_.name as name2_0_0_,
        vehicles1_.number as number2_3_1_,
        vehicles1_.person_id as person_i4_3_1_,
        vehicles1_.type as type3_3_1_,
        vehicles1_.person_id as person_i4_3_0__,
        vehicles1_.id as id1_3_0__ 
    from
        person_base person0_ 
    left outer join
        person_vehicle vehicles1_ 
            on person0_.id=vehicles1_.person_id 
    where
        person0_.id=?
Hibernate: 
    select
        passport0_.id as id1_1_0_,
        passport0_.issued_on as issued_o2_1_0_,
        passport0_.number as number3_1_0_,
        passport0_.person_id as person_i5_1_0_,
        passport0_.valid_till as valid_ti4_1_0_ 
    from
        person_passport passport0_ 
    where
        passport0_.person_id=?
Hibernate: 
    select
        profile0_.id as id1_2_2_,
        profile0_.city as city2_2_2_,
        profile0_.person_id as person_i4_2_2_,
        profile0_.phone_no as phone_no3_2_2_,
        person1_.id as id1_0_0_,
        person1_.name as name2_0_0_,
        passport2_.id as id1_1_1_,
        passport2_.issued_on as issued_o2_1_1_,
        passport2_.number as number3_1_1_,
        passport2_.person_id as person_i5_1_1_,
        passport2_.valid_till as valid_ti4_1_1_ 
    from
        person_profile profile0_ 
    left outer join
        person_base person1_ 
            on profile0_.person_id=person1_.id 
    left outer join
        person_passport passport2_ 
            on person1_.id=passport2_.person_id 
    where
        profile0_.person_id=?
~~~

Okay so 3 queries generated, which is different from case-2 where two queries were generated.
This is different from case-1 as well as over there 3 queries were generated but different.
What on Earth is happening?

So let's boil down.

<u>Case-1</u>

    Q-1)Vehicles are join fetched.
    Now it will go in alphabetical order to fetch entities.
    Q-2)A separate select is issued to get Passport entity. The person over here is lazy. Hence it fetches only passport.
    Q-3)A separate select is issued to get Profile entity. The person over here is lazy. Hence it fetches only profile.

<u>Case-2</u>

    Q-1)Vehicles are join fetched.
    Now it will go in alphabetical order to fetch entities.
    Q-2)A separate select is issued to get Passport entity. Inside Passport, it finds that Person is eager and then inside Person it finds that Profile is eager hence all fetched with joins.

<u>Case-3</u>

    Q-1)Vehicles are join fetched.
    Now it will go in alphabetical order to fetch entities.
    Q-2)A separate select is issued to get Passport entity. The person over here is lazy. Hence, it fetches only passport.
    Q-3)A separate select is issued to get Profile entity. Inside Profile, it finds that Person is eager and then inside Person it finds that Passport is eager hence all fetched with joins.

If you observe carefully in case-2 that a third query to fetch Profile. That's coz according to Hibernate optimization algo Profile has already been fetched in 2nd query itself. By Hibernate algo I mean the numbers like 1_2_3_ at the end of every line of the query.

<u>Case-4</u>

Keeping case-1 as base put up Profile.java slightly differently. Instead of using a separate column for storing the Person fk, it gets stored as pk of Profile.

```
@Id
@Column(name="id")
private Long id;

@OneToOne//(fetch=FetchType.LAZY)
@MapsId
private Person person;
```

Over here despite commenting out (fetch=FetchType.LAZY) the queries generated are as below. The @MapsId works a bit differently hence it doesn't move from Profile to Person.

~~~roomsql
Hibernate: 
    select
        person0_.id as id1_0_0_,
        vehicles1_.id as id1_3_1_,
        person0_.name as name2_0_0_,
        vehicles1_.number as number2_3_1_,
        vehicles1_.person_id as person_i4_3_1_,
        vehicles1_.type as type3_3_1_,
        vehicles1_.person_id as person_i4_3_0__,
        vehicles1_.id as id1_3_0__ 
    from
        person_base person0_ 
    left outer join
        person_vehicle vehicles1_ 
            on person0_.id=vehicles1_.person_id 
    where
        person0_.id=?
Hibernate: 
    select
        passport0_.id as id1_1_0_,
        passport0_.issued_on as issued_o2_1_0_,
        passport0_.number as number3_1_0_,
        passport0_.person_id as person_i5_1_0_,
        passport0_.valid_till as valid_ti4_1_0_ 
    from
        person_passport passport0_ 
    where
        passport0_.person_id=?
Hibernate: 
    select
        profile0_.person_id as person_i3_2_0_,
        profile0_.city as city1_2_0_,
        profile0_.phone_no as phone_no2_2_0_ 
    from
        person_profile profile0_ 
    where
        profile0_.person_id=?
~~~





