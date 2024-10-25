package co.viplove.choot.poc;

import lombok.Data;

/**
 This class represents a match between two persons
 It contains the two persons involved in the match
 and provides getter and setter methods for accessing and modifying the person data.
 This class can be used to create a match object that can be stored in a database or used in application logic.
 It can also be extended to include additional properties or methods related to the match if needed.
 For example, you could add a timestamp to indicate when the match was created or a status to indicate if the match is active or inactive.
 Overall, this class serves as a simple data structure to represent a match between two persons in the application.
 It can be used in conjunction with other classes and services to implement matching functionality in the application.
 The PersonMatch class can be integrated with the Neo4j database to store and retrieve match data as needed.
 This allows for efficient querying and management of match relationships between persons in the application.
 Additionally, the class can be enhanced with validation logic to ensure that the persons involved in the match are valid and meet certain criteria.
 Overall, the PersonMatch class is a fundamental part of the matching functionality in the application and can be further developed to meet specific requirements.
 It provides a clear and organized way to represent matches between persons and can be easily integrated with other components of the application.
 The class can also be tested independently to ensure that it behaves as expected and meets the needs of the application.
 In summary, the PersonMatch class is a key component of the matching functionality in the application and can be extended and enhanced as needed.
 It serves as a simple yet effective way to represent matches between persons and can be integrated with the Neo4j database for efficient data management.
 The class can be further developed to include additional features
 and can be tested to ensure its reliability and correctness in the application.
 */
@Data
public class PersonMatch {
    private Person person;
    private Match  match;
    private Person likedPerson;

    public PersonMatch(Person person, Match match, Person likedPerson) {
        this.person = person;
        this.match = match;
        this.likedPerson = likedPerson;
    }
}
  // This class represents a match between two persons
  // It contains the two persons involved in the match
  // and provides getter and setter methods for accessing and modifying the person data.
  // This class can be used to create a match object that can be stored in a database or used in application logic.
  // It can also be extended to include additional properties or methods related to the match if needed.
  // For example, you could add a timestamp to indicate when the match was created or a status to indicate if the match is active or inactive.
  // Overall, this class serves as a simple data structure to represent a match between two persons in the application.
  // It can be used in conjunction with other classes and services to implement matching functionality in the application.
  // The PersonMatch class can be integrated with the Neo4j database to store and retrieve match data as needed.
  // This allows for efficient querying and management of match relationships between persons in the application.
  // Additionally, the class can be enhanced with validation logic to ensure that the persons involved in the match are valid and meet certain criteria.
  // Overall, the PersonMatch class is a fundamental part of the matching functionality in the application and can be further developed to meet specific requirements.
  // It provides a clear and organized way to represent matches between persons and can be easily integrated with other components of the application.
  // The class can also be tested independently to ensure that it behaves as expected and meets the needs of the application.
  // In summary, the PersonMatch class is a key component of the matching functionality in the application and can be extended and enhanced as needed.
  // It serves as a simple yet effective way to represent matches between persons and can be integrated with the Neo4j database for efficient data management.
  // The class can be further developed to include additional features
  // and can be tested to ensure its reliability and correctness in the application.
