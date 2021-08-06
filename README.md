# nocode
**nocode** is a testing dependecy built for API testing. Where tester's can automate API's using this without writing a code.

------------
### Steps
1. Create a maven project and the below depndency to pom.xml and build the project.
```xml
<dependency>
        <groupId>com.github.pavandv9</groupId>
        <artifactId>nocode</artifactId>
        <version>[LATEST_VERSION](https://mvnrepository.com/artifact/com.github.pavandv9/nocode "LATEST_VERSION")</version>
</dependency>
```
> Click on the LATEST_VERSION to find the versions and replace LATEST_VERSION with version number.

2. Create `test` and `testdata` folder under `src/main/resources`. 
> If resources folder is not available go to file-> new-> source folder-> enter the folder name as src/main/resources-> finish.

3. Create a test cases json file under test folder.
```json
Example: sanity.json
[
    {
        "scenario": "Veriy able to get the user with id 2",
        "execute": "y",
        "request": {
            "url": "https://reqres.in/api/users/2",
            "method": "GET"
        },
        "validate": {
            "statuscode": 200,
            "resbody": {
                "data.id": "2",
                "data.first_name": "Janet",
                "support.url": "https://reqres.in/#support-heading"
            }
        }
    }
]
```
4. Create `config.properties` under `src/main/resources` add below fields.
- execution_file=sanity.json
- env=stage

5. Create any TestClass and add `NoCodeRunner.run();` in main method and execute it.
```java
Example: FirstTest.java

	public class FirstTest {
		public static void main(String[] args) {
			NoCodeRunner.run();
		}
	}
```

------------
##### Note:
1. Use above format for writing test cases.
2. Execute flag should be either y or n (uppercase/lowercase)
3. Method should be get, post, put, delete, patch (uppercase/lowercase)
4. Response body validation should be complete jsonpath like data.id, support.url etc...
5. All the response body values should be in string. Ex: "2"(works), 2(doesn't work)


