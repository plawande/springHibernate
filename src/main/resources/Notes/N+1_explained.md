Ok, so what is this N+1 problem with Hibernate?

Let's take an example of many-to-one relationship here (Vehicle -> Person)

The query used is below.

    Select v from Vehicle v

```
Vehicle.java
@ManyToOne
@JoinColumn(name="person_id")
private Person person;

Person.java
@OneToMany(mappedBy="person", cascade=CascadeType.ALL, orphanRemoval = true)
private List<Vehicle> vehicles;
```

The queries generated are below.

~~~roomsql
Hibernate: 
    select
        vehicle0_.id as id1_5_,
        vehicle0_.number as number2_5_,
        vehicle0_.person_id as person_i4_5_,
        vehicle0_.type as type3_5_ 
    from
        person_vehicle vehicle0_
Hibernate: 
    select
        person0_.id as id1_2_0_,
        person0_.name as name2_2_0_,
        passport1_.id as id1_3_1_,
        passport1_.issued_on as issued_o2_3_1_,
        passport1_.number as number3_3_1_,
        passport1_.person_id as person_i5_3_1_,
        passport1_.valid_till as valid_ti4_3_1_,
        profile2_.person_id as person_i3_4_2_,
        profile2_.city as city1_4_2_,
        profile2_.phone_no as phone_no2_4_2_ 
    from
        person_base person0_ 
    left outer join
        person_passport passport1_ 
            on person0_.id=passport1_.person_id 
    left outer join
        person_profile profile2_ 
            on person0_.id=profile2_.person_id 
    where
        person0_.id=?
Hibernate: 
    select
        person0_.id as id1_2_0_,
        person0_.name as name2_2_0_,
        passport1_.id as id1_3_1_,
        passport1_.issued_on as issued_o2_3_1_,
        passport1_.number as number3_3_1_,
        passport1_.person_id as person_i5_3_1_,
        passport1_.valid_till as valid_ti4_3_1_,
        profile2_.person_id as person_i3_4_2_,
        profile2_.city as city1_4_2_,
        profile2_.phone_no as phone_no2_4_2_ 
    from
        person_base person0_ 
    left outer join
        person_passport passport1_ 
            on person0_.id=passport1_.person_id 
    left outer join
        person_profile profile2_ 
            on person0_.id=profile2_.person_id 
    where
        person0_.id=?
Hibernate: 
    select
        person0_.id as id1_2_0_,
        person0_.name as name2_2_0_,
        passport1_.id as id1_3_1_,
        passport1_.issued_on as issued_o2_3_1_,
        passport1_.number as number3_3_1_,
        passport1_.person_id as person_i5_3_1_,
        passport1_.valid_till as valid_ti4_3_1_,
        profile2_.person_id as person_i3_4_2_,
        profile2_.city as city1_4_2_,
        profile2_.phone_no as phone_no2_4_2_ 
    from
        person_base person0_ 
    left outer join
        person_passport passport1_ 
            on person0_.id=passport1_.person_id 
    left outer join
        person_profile profile2_ 
            on person0_.id=profile2_.person_id 
    where
        person0_.id=?
~~~

If you see the queries generated you will realise that all vehicles are fetched by one query and <br />
then to fetch the persons associated with the vehicles, it fires one query per-person. <br />
So basically N queries for N persons. Thus, a total of N+1 queries. <br /><br />
Similarly the same problem will be generated even if we use (fetch=FetchType.LAZY) on Vehicle.java

```
Vehicle.java
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="person_id")
private Person person;

Person.java
@OneToMany(mappedBy="person", cascade=CascadeType.ALL, orphanRemoval = true)
private List<Vehicle> vehicles;
```
The first query which will be fired is as below.
~~~roomsql
Hibernate: 
    select
        vehicle0_.id as id1_5_,
        vehicle0_.number as number2_5_,
        vehicle0_.person_id as person_i4_5_,
        vehicle0_.type as type3_5_ 
    from
        person_vehicle vehicle0_
~~~
After this, while the session is still active, if we iterate over the List<Vehicle> to get the Person associated with each <br />
Individual queries just like the above case-1 will be fired. Thus, the N+1 problem. <br /><br />
How to resolve this problem? The below query needs to be used. Basically a join fetch is necessary.<br />
Keep both sides of the relation lazy as a practice.

    Select v from Vehicle v join fetch v.person p

The queries which will be fired are as below.
~~~roomsql
Hibernate: 
    select
        vehicle0_.id as id1_5_0_,
        person1_.id as id1_2_1_,
        vehicle0_.number as number2_5_0_,
        vehicle0_.person_id as person_i4_5_0_,
        vehicle0_.type as type3_5_0_,
        person1_.name as name2_2_1_ 
    from
        person_vehicle vehicle0_ 
    inner join
        person_base person1_ 
            on vehicle0_.person_id=person1_.id
Hibernate: 
    select
        passport0_.id as id1_3_0_,
        passport0_.issued_on as issued_o2_3_0_,
        passport0_.number as number3_3_0_,
        passport0_.person_id as person_i5_3_0_,
        passport0_.valid_till as valid_ti4_3_0_ 
    from
        person_passport passport0_ 
    where
        passport0_.person_id=?
Hibernate: 
    select
        profile0_.person_id as person_i3_4_0_,
        profile0_.city as city1_4_0_,
        profile0_.phone_no as phone_no2_4_0_ 
    from
        person_profile profile0_ 
    where
        profile0_.person_id=?
Hibernate: 
    select
        passport0_.id as id1_3_0_,
        passport0_.issued_on as issued_o2_3_0_,
        passport0_.number as number3_3_0_,
        passport0_.person_id as person_i5_3_0_,
        passport0_.valid_till as valid_ti4_3_0_ 
    from
        person_passport passport0_ 
    where
        passport0_.person_id=?
Hibernate: 
    select
        profile0_.person_id as person_i3_4_0_,
        profile0_.city as city1_4_0_,
        profile0_.phone_no as phone_no2_4_0_ 
    from
        person_profile profile0_ 
    where
        profile0_.person_id=?
Hibernate: 
    select
        passport0_.id as id1_3_0_,
        passport0_.issued_on as issued_o2_3_0_,
        passport0_.number as number3_3_0_,
        passport0_.person_id as person_i5_3_0_,
        passport0_.valid_till as valid_ti4_3_0_ 
    from
        person_passport passport0_ 
    where
        passport0_.person_id=?
Hibernate: 
    select
        profile0_.person_id as person_i3_4_0_,
        profile0_.city as city1_4_0_,
        profile0_.phone_no as phone_no2_4_0_ 
    from
        person_profile profile0_ 
    where
        profile0_.person_id=?
~~~

If you observe carefully there is only one join query fired to fetch Vehicle and Person basic details.<br />
So here the N+1 problem is avoided. yayy!!! <br />
The rest of the queries you see for Passport and Profile have one-to-one bidirectional relationships with Person. How they are generated is already explained in join-fetch_working.md.
