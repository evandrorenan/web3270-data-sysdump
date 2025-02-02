# Abend Report Workflow Documentation (TODO)

## Core Domain Class Diagram

```mermaid
classDiagram
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

    %% Event System
    class AbendReportEvent {
        <<interface>>
        +getPayload()
        +getEventType()
        +getRequestId()
    }

    class EventType {
        <<enumeration>>
        REQUEST_INITIALIZED
        DATA_COLLECTION_STARTED
        DATA_COLLECTION_COMPLETED
        BLOB_SAVED
        REPORT_EXTRACTION_STARTED
        REPORT_EXTRACTION_COMPLETED
        BASE_LOCATORS_SAVED
    }

    %% Status Management
    class StatusManager {
        <<interface>>
        +onEvent(AbendReportEvent event)
        -updateStatus(String requestId, RequestStatus status)
    }

    class StatusManagerImpl {
        -Map<EventType, RequestStatus> statusMappings
        +onEvent(AbendReportEvent event)
        -updateStatus(String requestId, RequestStatus status)
    }

    %% Chain of Responsibility Interfaces
    class AbendReportHandler {
        <<interface>>
        +handle(AbendReportEvent event)
        +setNext(AbendReportHandler next)
    }

    %% Concrete Handlers
    class RequestInitializationHandler {
        +handle(AbendReportEvent event)
        -generateRequestProtocol()
    }

    class DataCollectionHandler {
        +handle(AbendReportEvent event)
        -collectRawData()
    }

    class BlobStorageHandler {
        +handle(AbendReportEvent event)
        -saveBlob()
    }

    class ReportExtractionHandler {
        +handle(AbendReportEvent event)
        -extractReport()
    }

    class BaseLocatorHandler {
        +handle(AbendReportEvent event)
        -saveBaseLocators()
    }

    AbendReportHandler <|.. RequestInitializationHandler
    AbendReportHandler <|.. DataCollectionHandler
    AbendReportHandler <|.. BlobStorageHandler
    AbendReportHandler <|.. ReportExtractionHandler
    AbendReportHandler <|.. BaseLocatorHandler
    
    AbendReportRequest --> RequestStatus
    BaseLocatorHandler --> AbendReport
    AbendReportEvent --> EventType
    StatusManager <|.. StatusManagerImpl
    StatusManagerImpl --> RequestStatus
    StatusManagerImpl ..> AbendReportEvent : observes
```

### Class/Interface Descriptions

1. **AbendReportHandler (Interface)**
   - Core interface for the Chain of Responsibility pattern
   - Focuses solely on processing logic
   - No status management responsibility

2. **AbendReportEvent (Interface)**
   - Represents events flowing through the system
   - Carries payload and event type information
   - Includes request ID for tracking

3. **EventType (Enumeration)**
   - Defines all possible events in the workflow
   - Allows for easy addition of new event types
   - Used by StatusManager for status mapping

4. **StatusManager (Interface)**
   - Responsible for status management
   - Observes events and updates status accordingly
   - Decoupled from handlers

5. **StatusManagerImpl**
   - Implements status management logic
   - Maintains mappings between events and statuses
   - Single source of truth for status updates

6. **RequestInitializationHandler**
   - Handles initial request creation
   - Generates unique request protocol
   - Emits REQUEST_INITIALIZED event

7. **DataCollectionHandler**
   - Responsible for collecting raw data
   - Emits events for collection start/completion
   - Produces Blob containing raw data

8. **BlobStorageHandler**
   - Manages blob storage operations
   - Emits BLOB_SAVED event
   - Returns blob ID

9. **ReportExtractionHandler**
   - Processes blob data to extract AbendReport
   - Emits extraction start/completion events
   - Creates structured AbendReport object

10. **BaseLocatorHandler**
    - Final handler in the chain
    - Saves base locators from AbendReport
    - Emits BASE_LOCATORS_SAVED event

## Microservices Architecture

```mermaid
graph TB
    subgraph "Abend Report Service"
        A[Request Handler]
        B[Report Processor]
        C[Event Bus]
    end

    subgraph "Data Collection Service"
        D[Data Collector]
        E[Raw Data Storage]
    end

    subgraph "Report Storage Service"
        F[Blob Storage]
        G[Report Repository]
    end

    A --1.Initialize Request--> C
    C --2.Collect Data--> D
    D --3.Store Raw Data--> E
    E --4.Raw Data Stored--> C
    C --5.Process Report--> B
    B --6.Store Report--> F
    F --7.Store Metadata--> G
    
    style A fill:#f9f,stroke:#333,stroke-width:2px
    style D fill:#bbf,stroke:#333,stroke-width:2px
    style F fill:#bfb,stroke:#333,stroke-width:2px
```

### Microservices Description

1. **Abend Report Service**
   - Handles request initialization
   - Orchestrates the overall process
   - Manages report extraction and processing

2. **Data Collection Service**
   - Specialized in raw data collection
   - Handles data storage and retrieval
   - Isolated from report processing concerns

3. **Report Storage Service**
   - Manages blob storage
   - Handles report persistence
   - Stores base locators and metadata

The microservices architecture provides:
- Better scalability
- Independent deployment
- Specialized concerns
- Fault isolation
- Enhanced maintainability
