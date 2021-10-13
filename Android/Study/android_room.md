# 안드로이드 Room DB

안드로이드 앱에서 데이터를 저장하는 방법은 크게 `텍스트 파일` `SharedPreference` `SQLite DB` 세 가지 입니다. 

`텍스트 파일`은 지정 된 외부저장소 경로에 File 입출력을 통해 저장하는 방법이며, 내가 저장한 데이터를 다른 앱에서도 확인이 가능하기 때문에 편리하지만 보안적인 부분이 보장되지 않습니다.

`SharedPreference`는 내부저장소 경로의 xml 파일에 key-value 형태로 데이터를 저장하는 방법입니다. 일반적으로 다음과 경로에 내 앱에서만 열어볼 수 있도록 데이터가 저장됩니다. (/data/data/앱패키지명/sharedPreferences/파일명.xml) 

`SQLite DB`는 파일보다는 동작이 느리지만, 객체 간 관계를 기반으로 다량의 구조화 된 데이터를 편하게 관리하기 위해 사용합니다.

이 방법들은 각각 장단점이 존재하기 때문에 관리하고자 하는 데이터의 속성에 따라 어떤 방법을 사용해 데이터를 저장할지 선택할 수 있습니다.

오늘은 이 중 `SQLite DB`를 효율적으로 사용할 수 있게 도와주는 Jetpack 라이브러리인 `Room`에 대해 소개하겠습니다.

<br/>

## Room이란?
안드로이드 앱에서 SQLite를 추상화하여 데이터를 쉽고 편리하게 관리하려고 사용하는 라이브러리입니다.

SQLite는 앱 내에서 구조화 된 데이터를 처리하기 위한 훌륭한 방법이지만, 다음과 같은 불편함이 있습니다.

>- SQLite는 데이터모델 클래스에 대한 의존성이 강해서 모델이 변경됐을 때, 관련 된 쿼리를 모두 찾아서 수동으로 업데이트 해야합니다.
>
>
>- SQLite만 사용해서 DB를 관리하려면 SQL 쿼리와 객체 변환 간에 많은 `보일러 플레이트 코드`가 발생합니다.

`Room`은 이러한 문제들을 `Annotation` 기법으로 자동화하여 해결합니다. ([Annotation 포스팅 바로가기](https://velog.io/@eia51/Annotation-%EC%99%84%EC%A0%84-%EC%A0%95%EB%B3%B5%EA%B8%B0))

<br/>

## Room의 구성?
Room은 `Entity` `DAO` `Database` 이렇게 세 가지 요소가 다음과 같은 구조로 구성됩니다.
![](https://images.velog.io/images/eia51/post/f23fb094-99ce-482e-9b69-25ae99ac5985/room.png)

### Entity
데이터베이스의 테이블을 표현합니다. 클래스에 `@Entity` 어노테이션을 붙여 엔터티 클래스를 정의 할 수 있습니다.

### DAO (Data Access Object)
인터페이스에 `@Dao` 어노테이션을 붙여 정의하며, 데이터베이스 접근에 사용되는 추상 메소드들을 포함합니다.

### Database
데이터베이스 홀더를 포함하는 DB의 주 진입점입니다.

데이터베이스 클래스를 만들기 위한 조건은 다음과 같습니다.
- RoomDatabase를 상속한 추상클래스에 `@Database` 어노테이션을 추가합니다.
- 포함 할 엔터티의 목록을 어노테이션 내에 명시해야합니다.
- 파라미터를 갖지 않고, `@Dao` 클래스를 반환하는 추상메소드를 포함합니다.

<br/>

## 프로젝트에 Room 설정하기
### 의존성 추가
앱 모듈의 build.gradle에 다음 사항을 추가합니다.
```java
dependencies {
	def room_version = "2.3.0"
	implementation "androidx.room:room-runtime:room_version"
	annotationProcessor "androidx.room:room-compiler:room_version"
}
```

위 의존성 추가가 정상적으로 되지 않는다면, 프로젝트의 build.gradle에 다음 내용이 있는지 확인하여 없으면 추가해줘야 합니다.
```java
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
```

<br/>

### 엔터티 정의하기
- 엔터티 클래스의 필드는 `public`으로 만들거나, `getter` `setter` 메소드를 제공해줘야 Room에서 접근 가능합니다. (`getter` `setter` 메소드는 기본적으로  JavaBeans의 컨벤션을 따릅니다.)

- 엔터티는 최소 한 개의 필드를 `@PrimaryKey` 어노테이션을 사용해 기본키로 선언해야 합니다. 
  - 복합키를 사용하고자 한다면, `@Entity(primaryKeys={"id", "name"}` 과 같이 선언할 수 있습니다.
  - Room이 Primary Key를 자동으로 생성하게 하고 싶다면 `@PrimaryKey(autoGenerate = true)`와 같이 사용합니다.

- 엔터티의 테이블 이름을 `@Entity(tableName="이름")`과 같이 정의 할 수 있습니다.

- 엔터티의 필드를 테이블의 열로 만들고 싶지 않다면(단순히 앱 내에서 임시로 사용하고자 한다면) 필드에 `@Ignore` 어노테이션을 붙이거나, `@Entity(ignoredColumns="필드명")`과 같이 사용 할 수 있습니다.

- `minSDK>=16 AND RoomVer >= 2.1` 라면 FTS4를 사용할 수 있습니다. 
  ( * FTS4는 전체 텍스트 검색을 효율적으로 할 수 있게 하는 가상 테이블 모듈입니다.)
  
  `@Fts4` 어노테이션을 추가하고, 기본키를 포함하는 경우에는 <U>반드시 int 자료형과 rowid라는 컬렁명</U>을 가져야합니다.
  
- `@Embedded` 어노테이션을 붙여 객체의 특정 필드들을 별도로 분리 된 테이블로 저장 할 수 있습니다.

```java
@Fts4
@Entity(tableName="User")
public class User {
    @PrimaryKey
    @ColumnInfo(name="rowid")
    private int id;    
    
    private String name;
    
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

<br/>

### 엔터티 관계 정의하기
Room에서는 <U>* 객체 간 직접적인 관계 지정을 할 수 없지만</U>, 엔터티 간 외래키 제약 조건을 정의할 수 있습니다.
> **Room에서는 왜 객체 간 직접 관계를 지정 할 수 없을까요?**
>
>DB에서 해당 객체 모델로 관계 매핑하는 것은 MainThread에서 수행되며, MainThread에서 디스크에 대한 정보를 쿼리할 경우 심각한 성능 문제가 따릅니다. 프레임드랍으로 인해 화면의 버벅임이 발생 될 수 있습니다.
>
> 이러한 이유로 직접적인 관계는 정의 할 수 없으나, 데이터를 불러올 때 Entity를 확장하는 자바클래스에 `@Relation` 어노테이션을 붙여 관계 제한을 회피 할 수 있습니다. 이 때 `@Relation` 어노테이션이 붙는 필드는 반드시 `List` or `Set` 타입의 변수여야 합니다.

다음은 Book 엔터티에 `@ForeignKey` 어노테이션을 사용해 User라는 엔터티와의 간접적인 관계를 정의하는 샘플입니다. (샘플에서는 getter, setter를 구현하지 않기 위해 접근자를 pubic으로 선언했습니다.)
```java
@Entity (foreignKeys=@ForeignKey(entity=User.class, 
 				parentColumns = "id",
                    		childColumns = "user_id"))
public class Book {
    @PrimaryKey
    public int bookId;
    public String title;
    @ColumnInfo(name="user_id")
    public int userId;
}
```
user_id라는 외래키를 통해 0개 이상의 Book 인스턴스가 하나의 User 인스턴스에 연결 될 수 있으므로, 이는 User와 Book의 일 대 다 관계를 모델링합니다.

이러한 관계를 일 대 다 관계가 아닌, 다 대 다 관계로 모델링하고자 한다면, `@Entity`의 foreignKeys 필드에 단일 `@ForeignKey` 어노테이션이 아닌, `{@ForeignKey=..., @ForwignKey=...}` 형태의 어노테이션 리스트를 전달하면 됩니다.

<br/>

### 뷰 정의하기
Room 2.1 이상 버전에선 데이터베이스 `View`를 지원합니다. `View`는 쿼리를 클래스로 캡슐화 한 개념이며, `SELECT` 쿼리만 수행 가능합니다.

```java
@DatabaseView ("SELECT user.id, user,name, user.departmentId, 
		department.name AS departmentName 
        	FROM user INNER JOIN department 
            	ON user.departmentId=department.id")
public class UserDetail {
	public long id;
    public String name;
    public long departmentId;
    public String departmentName;
}
```

그리고 이렇게 만든 뷰를 데이터베이스에 포함하려면 다음과 같이 합니다.

```java
@Database(entities={User.class}, views={UserDetail.class}, version=1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

<br/>

### DAO를 통한 데이터 접근
- `Room`에서 앱 데이터를 접근하기 위해선 `DAO`를 사용해야 합니다. `DAO`는 DB에 접근하기 위한 추상 메소드를 포함해야 합니다.

- `DAO`는 인터페이스 또는 추상 클래스로 만들며, 컴파일 타임에 정의 된 어노테이션을 프로세싱하여 각 `DAO`의 구현체 코드가 만들어지게 됩니다.

- `Room`은 MainThread에서의 쿼리를 허용하지 않습니다. 
  - Builder에서 allowMainThreadQueries()를 호출하면 쿼리가 가능하나 권장하지 않습니다.
  - `LiveData` `Flowable` 인스턴스를 반환하도록 쿼리하면 MainThread에서도 예외적으로 쿼리가 가능합니다.

- 쿼리 결과가 변경 될 때 마다 UI도 자동으로 업데이트 하고자 한다면, 쿼리 메소드 반환형으로 `LiveData`를 사용하면 됩니다. 컴파일 타임에 데이터 변경 시 `LiveData`를 갱신하는데 필요한 모든 코드들이 같이 생성 됩니다.

- 쿼리 결과를 RxJava의 반응형 타입으로 지정하고 싶다면 앱모듈의 `build.gradle`에 다음 내용을 포함해야 합니다. `implementation 'androidx.room:room-rxjava2:2.3.0'`  
   위 의존성을 포함하면 Single, Completable, Flowable, Maybe 등의 타입도 사용 가능합니다.
   
- 여러개의 쿼리를 `@Transaction` 어노테이션을 사용하여 한 개의 트랜잭션으로 묶을 수 있습니다.
   
```java
@Dao
public interface MyDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE) //or IGNORE
    public void insertUsers(User... users);
    
    @Update
    public void updateUsers(User... users);
    
    @Delete
    public void deleteUsers(User... users);
    
    @Query("SELECT * from user")
    public LiveData<List<User>> loadAllUsers();
    
    @Query("SELECT * FROM user WHERE age > :minAge")
    public LiveData<List<User>> loadAllUsersOlderThan(int minAge);
    
    @Query("SELECT * FROM user WHERE region IN (:regions)")
    public LiveData<List<User>> loadUsersInRegion(List<String> regions);
}
```
이렇듯 `Room`을 사용한다면, 모든 쿼리를 손수 입력해 사용해야 하는 `SQLite`를 사용 할 때 보다 많은 양의 코드를 줄일 수 있습니다. 이렇게 줄여진 코드는 컴파일 타임에 어노테이션 프로세싱 과정에서 생성됩니다. ~~(사랑해요 어노테이션)~~

<br/>

### 타입컨버터 사용
때로는 Entity에 선언 된 데이터포맷과 DB에 저장되는 포맷이 다른 경우가 있을 수 있습니다. 이를테면 객체는 `Date` 포맷으로 가지고 있지만, DB로 저장 될 때는 `Long` 타입의 타임스탬프로 변환하여 저장하고자 할 때가 이러한 경우입니다.

이런 상황에서 우리는 `@TypeConverter` 라는 어노테이션을 사용해 문제를 해결 할 수 있습니다.

```java
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
    	return value == null ? null : new Date(value);
    }
    
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
    	return data == null ? null : date.getTime();
    }
}

@Database(eitities={User.class}, version=1)
@TypeConverter({Converter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}

@Entity
public class User {
    public Date birthday;
}

//매개변수로 Date를 받지만 Converter를 거쳐 Date가 Long 타입으로 변경되어 쿼리에 적용됩니다.
@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE birthday BETWEEN :from AND :to")
    List<User> findUsersBornBetweenDates(Date from, Date to);
}
```

<br/>

### Room Migration 
기존 사용하던 앱의 Room db에 변경이 발생했을 때, Database의 version을 올리고 이에 따른 변경 내용을 마이그레이션 할 수 있습니다.

```java 
static final Migration MIGRATION_1_2 = new Migration(1, 2) {
	@Override
    public void migrate(SupportSQLiteDatabase db) {
    	db.execSQL("마이그레이션 할 SQL 쿼리1..");
    }
}

static final Migration MIGRATION_2_3 = new Migration(2, 3) {
	@Override
    public void migrate(SupportSQLiteDatabase db) {
    	db.execSQL("마이그레이션 할 SQL 쿼리2..");
    }
}

Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "database-name")
	.addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
```

마이그레이션 테스트를 위해선 각 버전의 DB 스키마에 대한 정보를 알고 있어야 합니다. 다음 코드를 통해 컴파일타임에 DB 스키마를 JSON 파일로 추출 할 수 있습니다.

```java
android {
	...
	defaultConfig {
    	...
    	javaCompileOptions {
        	annotationProcessorOptions {
            	arguments = ["room.schemaLocation":"$projectDir/schemas".toString()]
            }
        }
    }
}
```
테스트를 위해선 반드시 스키마를 나타내는 JSON 파일을 추출하여 버전 관리시스템으로 관리하도록 해야 합니다. 

<br/>

> **누락 된 마이그레이션 경로 정상 처리하기**
> 
> 데이터베이스 버전이 업그레이드 되어서 마이그레이션 해야 하는 상황에 마이그레이션이 실패할 경우 `IllegalStateException`이 발생합니다. 이 상황에서 앱이 중단 될 수 있는데, 앱의 중단을 막기 위해 다음 메소드를 DB 생성 시 빌더에서 호출 할 수 있습니다.
> ```java
>Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "database-name")
>	.fallbackToDestructiveMigration()
>	.build()
>```

<br/>
<br/>

---

### 참고 자료
[Room 구조](https://soeun-87.tistory.com/21)

[Room 데이터베이스를 사용해야 하는 이유](https://jakubpchmiel.com/better-ways-of-using-room/)

아키텍처를 알아야 앱 개발이 보인다 - 옥수환 

