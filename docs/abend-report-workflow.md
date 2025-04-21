# Abend Report Workflow Documentation (TODO)

## Core Domain Class Diagram

```mermaid
classDiagram
    %% Core Domain Objects
    class AbendReportRequest {
        +String jobId
        +String programName
        +String requestProtocol
        +RequestStatus status
    }

    class AbendReport {
        +String jobId
        +String programName
        +Map<String, String> baseLocators
        +String content
    }

    class Blob {
        +byte[] content
        +String id
        +String contentType
    }

    class RequestStatus {
        <<enumeration>>
        PENDING
        COLLECTING_DATA
        PROCESSING
        COMPLETED
        ERROR
    }

    %% Observer Pattern
    class EventPublisher {
        -List<EventObserver> observers
        +registerObserver(EventObserver observer)
        +removeObserver(EventObserver observer)
        +notifyObservers(AbendReportEvent event)
    }

    class EventObserver {
        <<interface>>
        +onEvent(AbendReportEvent event)
    }

    class AbendReportEvent {
        +String requestId
        +EventType type
        +Object payload
    }

    class EventType {
        <<enumeration>>
        REQUEST_INITIALIZED
        DATA_COLLECTION_STARTED
        DATA_COLLECTION_COMPLETED
        REPORT_EXTRACTION_STARTED
        REPORT_EXTRACTION_COMPLETED
        BASE_LOCATORS_SAVED
    }

    %% Chain of Responsibility
    class AbendReportHandler {
        <<interface>>
        +handle(AbendReportEvent event)
        +setNext(AbendReportHandler next)
    }

    class RequestInitializationHandler {
        -EventPublisher publisher
        +handle(AbendReportEvent event)
    }

    class DataCollectionHandler {
        -EventPublisher publisher
        -InMemoryStorage storage
        +handle(AbendReportEvent event)
    }

    class ReportExtractionHandler {
        -EventPublisher publisher
        -InMemoryStorage storage
        +handle(AbendReportEvent event)
    }

    class BaseLocatorHandler {
        -EventPublisher publisher
        -InMemoryStorage storage
        +handle(AbendReportEvent event)
    }

    %% In-Memory Storage
    class InMemoryStorage {
        -Map<String, Object> store
        +save(String key, Object value)
        +get(String key)
        +remove(String key)
    }

    %% Status Management
    class StatusManager {
        -InMemoryStorage storage
        +onEvent(AbendReportEvent event)
    }

    %% Relationships
    AbendReportHandler <|.. RequestInitializationHandler
    AbendReportHandler <|.. DataCollectionHandler
    AbendReportHandler <|.. ReportExtractionHandler
    AbendReportHandler <|.. BaseLocatorHandler
    
    EventObserver <|.. StatusManager
    EventPublisher --> EventObserver
    AbendReportEvent --> EventType
    
    RequestInitializationHandler --> EventPublisher
    DataCollectionHandler --> EventPublisher
    ReportExtractionHandler --> EventPublisher
    BaseLocatorHandler --> EventPublisher
    
    DataCollectionHandler --> InMemoryStorage
    ReportExtractionHandler --> InMemoryStorage
    BaseLocatorHandler --> InMemoryStorage
    StatusManager --> InMemoryStorage
```

### Class/Interface Descriptions

1. **EventPublisher**
   - Central event management
   - Maintains list of observers
   - Notifies observers of workflow events

2. **EventObserver**
   - Interface for components that need to react to events
   - Implemented by StatusManager and other observers

3. **InMemoryStorage**
   - Simple key-value store for workflow data
   - Maintains state during request processing
   - Used by handlers to store/retrieve data

4. **AbendReportHandler**
   - Chain of Responsibility interface
   - Each handler processes its part and publishes events
   - Uses InMemoryStorage for data persistence

5. **StatusManager**
   - Observes workflow events
   - Updates request status in storage
   - Single source of truth for status

6. **Handlers**
   - RequestInitializationHandler: Creates new requests
   - DataCollectionHandler: Collects raw data
   - ReportExtractionHandler: Processes data into report
   - BaseLocatorHandler: Saves base locators

### Workflow

1. Request comes in → RequestInitializationHandler
2. Events flow through handlers via Chain of Responsibility
3. StatusManager observes events and updates status
4. Data passed between handlers using InMemoryStorage
5. Each step publishes events on completion

This simplified design:
- Runs entirely in one service
- Uses Observer pattern for event handling
- Stores all data in memory
- Maintains loose coupling through events
- Keeps single responsibility principle
