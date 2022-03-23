#H2
Generated using [DbSchema](https://dbschema.com)




<a name='layout1'>### Layout
![img](Database_Schemaayout.svg)


##Tables

1. [PUBLIC.AUTHORS](#PUBLIC.AUTHORS) 
2. [PUBLIC.AUTHORS_REVIEWS](#PUBLIC.AUTHORS_REVIEWS) 
3. [PUBLIC.GENRES](#PUBLIC.GENRES) 
4. [PUBLIC.MOVIES](#PUBLIC.MOVIES) 
5. [PUBLIC.MOVIES_GENRE](#PUBLIC.MOVIES_GENRE) 
6. [PUBLIC.MOVIES_REVIEWS](#PUBLIC.MOVIES_REVIEWS) 
7. [PUBLIC.REVIEWS](#PUBLIC.REVIEWS) 

### Table AUTHORS 
| Idx | Field Name | Data Type |
|---|---|---|
| *🔑 ⬋ | <a name='PUBLIC.AUTHORS_ID'>ID</a>| bigint  |
|  | <a name='PUBLIC.AUTHORS_NAME'>NAME</a>| varchar&#40;255&#41;  |
| Indexes |
| 🔑 | CONSTRAINT&#95;4 || ON ID|


### Table AUTHORS_REVIEWS 
| Idx | Field Name | Data Type |
|---|---|---|
| *🔎 ⬈ | <a name='PUBLIC.AUTHORS_REVIEWS_AUTHOR_ID'>AUTHOR&#95;ID</a>| bigint  |
| *🔍 ⬈ | <a name='PUBLIC.AUTHORS_REVIEWS_REVIEWS_ID'>REVIEWS&#95;ID</a>| bigint  |
| Indexes |
| 🔍  | UK&#95;3HFEW5V3TOJS08OSHJ80P4O5X&#95;INDEX&#95;7 || ON REVIEWS&#95;ID|
| 🔎  | FKT2J4CYVX1OOCO72DFAMEHOEIW&#95;INDEX&#95;7 || ON AUTHOR&#95;ID|
| Foreign Keys |
|  | FKT2J4CYVX1OOCO72DFAMEHOEIW | ( AUTHOR&#95;ID ) ref [PUBLIC&#46;AUTHORS](#AUTHORS) (ID) |
|  | FK9649I6RFYP69NG2BMN3AF35A7 | ( REVIEWS&#95;ID ) ref [PUBLIC&#46;REVIEWS](#REVIEWS) (ID) |


### Table GENRES 
| Idx | Field Name | Data Type |
|---|---|---|
| *🔑 ⬋ | <a name='PUBLIC.GENRES_ID'>ID</a>| bigint  |
|  | <a name='PUBLIC.GENRES_NAME'>NAME</a>| varchar&#40;255&#41;  |
| Indexes |
| 🔑 | CONSTRAINT&#95;7 || ON ID|


### Table MOVIES 
| Idx | Field Name | Data Type |
|---|---|---|
| *🔑 ⬋ | <a name='PUBLIC.MOVIES_ID'>ID</a>| bigint  DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_F761F1A9_53AA_4674_9A2C_0753909D436B" |
|  | <a name='PUBLIC.MOVIES_CLASSIFICATION'>CLASSIFICATION</a>| varchar&#40;255&#41;  |
|  | <a name='PUBLIC.MOVIES_COUNTRY'>COUNTRY</a>| varchar&#40;255&#41;  |
|  | <a name='PUBLIC.MOVIES_CREATED_AT'>CREATED&#95;AT</a>| timestamp  |
| *| <a name='PUBLIC.MOVIES_DURATION'>DURATION</a>| integer  |
|  | <a name='PUBLIC.MOVIES_LANGUAGE'>LANGUAGE</a>| varchar&#40;255&#41;  |
|  | <a name='PUBLIC.MOVIES_NAME'>NAME</a>| varchar&#40;255&#41;  |
|  | <a name='PUBLIC.MOVIES_RATING'>RATING</a>| double  |
| *| <a name='PUBLIC.MOVIES_YEAR'>YEAR</a>| integer  |
| Indexes |
| 🔑 | CONSTRAINT&#95;8 || ON ID|


### Table MOVIES_GENRE 
| Idx | Field Name | Data Type |
|---|---|---|
| *🔎 ⬈ | <a name='PUBLIC.MOVIES_GENRE_MOVIE_ID'>MOVIE&#95;ID</a>| bigint  |
| *🔎 ⬈ | <a name='PUBLIC.MOVIES_GENRE_GENRE_ID'>GENRE&#95;ID</a>| bigint  |
| Indexes |
| 🔎  | FKAXUMV92GRLO4XTHFCQWIWCOK6&#95;INDEX&#95;6 || ON MOVIE&#95;ID|
| 🔎  | FKT0HUT1Q5N87DIRMB0GT98K2WP&#95;INDEX&#95;6 || ON GENRE&#95;ID|
| Foreign Keys |
|  | FKT0HUT1Q5N87DIRMB0GT98K2WP | ( GENRE&#95;ID ) ref [PUBLIC&#46;GENRES](#GENRES) (ID) |
|  | FKAXUMV92GRLO4XTHFCQWIWCOK6 | ( MOVIE&#95;ID ) ref [PUBLIC&#46;MOVIES](#MOVIES) (ID) |


### Table MOVIES_REVIEWS 
| Idx | Field Name | Data Type |
|---|---|---|
| *🔎 ⬈ | <a name='PUBLIC.MOVIES_REVIEWS_MOVIE_ID'>MOVIE&#95;ID</a>| bigint  |
| *🔍 ⬈ | <a name='PUBLIC.MOVIES_REVIEWS_REVIEWS_ID'>REVIEWS&#95;ID</a>| bigint  |
| Indexes |
| 🔍  | UK&#95;2245K6VFO7UYEWR1BWECMYJIE&#95;INDEX&#95;7 || ON REVIEWS&#95;ID|
| 🔎  | FKP68Q5NC8JEHAFB7LNOPTBX6H5&#95;INDEX&#95;7 || ON MOVIE&#95;ID|
| Foreign Keys |
|  | FKP68Q5NC8JEHAFB7LNOPTBX6H5 | ( MOVIE&#95;ID ) ref [PUBLIC&#46;MOVIES](#MOVIES) (ID) |
|  | FKNUTA0DXPKFONOCY3VELQUEJOG | ( REVIEWS&#95;ID ) ref [PUBLIC&#46;REVIEWS](#REVIEWS) (ID) |


### Table REVIEWS 
| Idx | Field Name | Data Type |
|---|---|---|
| *🔑 ⬋ | <a name='PUBLIC.REVIEWS_ID'>ID</a>| bigint  |
| *| <a name='PUBLIC.REVIEWS_ARCHIVED'>ARCHIVED</a>| boolean  |
|  | <a name='PUBLIC.REVIEWS_REVIEW'>REVIEW</a>| varchar&#40;255&#41;  |
| 🔎 ⬈ | <a name='PUBLIC.REVIEWS_AUTHOR_ID'>AUTHOR&#95;ID</a>| bigint  |
| 🔎 ⬈ | <a name='PUBLIC.REVIEWS_MOVIE_ID'>MOVIE&#95;ID</a>| bigint  |
| Indexes |
| 🔑 | CONSTRAINT&#95;6 || ON ID|
| 🔎  | FK87TLQYA0RQ8IJFJSCLDPVVDYQ&#95;INDEX&#95;6 || ON MOVIE&#95;ID|
| 🔎  | FKTRNVQP8P8QCKC01N5IE74KUGV&#95;INDEX&#95;6 || ON AUTHOR&#95;ID|
| Foreign Keys |
|  | FKTRNVQP8P8QCKC01N5IE74KUGV | ( AUTHOR&#95;ID ) ref [PUBLIC&#46;AUTHORS](#AUTHORS) (ID) |
|  | FK87TLQYA0RQ8IJFJSCLDPVVDYQ | ( MOVIE&#95;ID ) ref [PUBLIC&#46;MOVIES](#MOVIES) (ID) |



