# Graph Report - ColdStorage-main  (2026-06-20)

## Corpus Check
- 43 files · ~13,849 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 834 nodes · 1634 edges · 37 communities (32 shown, 5 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 97 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Community 0|Community 0]]
- [[_COMMUNITY_Community 1|Community 1]]
- [[_COMMUNITY_Community 2|Community 2]]
- [[_COMMUNITY_Community 3|Community 3]]
- [[_COMMUNITY_Community 4|Community 4]]
- [[_COMMUNITY_Community 5|Community 5]]
- [[_COMMUNITY_Community 6|Community 6]]
- [[_COMMUNITY_Community 7|Community 7]]
- [[_COMMUNITY_Community 8|Community 8]]
- [[_COMMUNITY_Community 9|Community 9]]
- [[_COMMUNITY_Community 10|Community 10]]
- [[_COMMUNITY_Community 11|Community 11]]
- [[_COMMUNITY_Community 12|Community 12]]
- [[_COMMUNITY_Community 13|Community 13]]
- [[_COMMUNITY_Community 14|Community 14]]
- [[_COMMUNITY_Community 15|Community 15]]
- [[_COMMUNITY_Community 16|Community 16]]
- [[_COMMUNITY_Community 17|Community 17]]
- [[_COMMUNITY_Community 18|Community 18]]
- [[_COMMUNITY_Community 19|Community 19]]
- [[_COMMUNITY_Community 20|Community 20]]
- [[_COMMUNITY_Community 21|Community 21]]
- [[_COMMUNITY_Community 22|Community 22]]
- [[_COMMUNITY_Community 23|Community 23]]
- [[_COMMUNITY_Community 24|Community 24]]
- [[_COMMUNITY_Community 27|Community 27]]
- [[_COMMUNITY_Community 28|Community 28]]
- [[_COMMUNITY_Community 29|Community 29]]
- [[_COMMUNITY_Community 30|Community 30]]
- [[_COMMUNITY_Community 31|Community 31]]
- [[_COMMUNITY_Community 32|Community 32]]
- [[_COMMUNITY_Community 33|Community 33]]
- [[_COMMUNITY_Community 34|Community 34]]
- [[_COMMUNITY_Community 35|Community 35]]

## God Nodes (most connected - your core abstractions)
1. `User` - 27 edges
2. `BookingService` - 27 edges
3. `BookingList` - 24 edges
4. `Booking List View` - 23 edges
5. `BookingDetail` - 20 edges
6. `Storage` - 19 edges
7. `PublicController` - 18 edges
8. `Inquiry` - 17 edges
9. `UserCreateRequest` - 16 edges
10. `InquiryService` - 16 edges

## Surprising Connections (you probably didn't know these)
- `DatabaseUserDetailsService` --references--> `UserRepository`  [EXTRACTED]
  C:/Users/client/Desktop/csms DBMS/csms-java/src/main/java/com/csms/config/DatabaseUserDetailsService.java → csms-java/src/main/java/com/csms/config/DatabaseUserDetailsService.java
- `WebConfig` --references--> `String`  [EXTRACTED]
  C:/Users/client/Desktop/csms DBMS/csms-java/src/main/java/com/csms/config/WebConfig.java → csms-java/src/main/java/com/csms/config/WebConfig.java
- `AdminController` --references--> `BookingService`  [EXTRACTED]
  C:/Users/client/Desktop/csms DBMS/csms-java/src/main/java/com/csms/controller/AdminController.java → csms-java/src/main/java/com/csms/controller/AdminController.java
- `AdminController` --references--> `StorageService`  [EXTRACTED]
  C:/Users/client/Desktop/csms DBMS/csms-java/src/main/java/com/csms/controller/AdminController.java → csms-java/src/main/java/com/csms/controller/AdminController.java
- `AdminInquiryController` --references--> `InquiryService`  [EXTRACTED]
  C:/Users/client/Desktop/csms DBMS/csms-java/src/main/java/com/csms/controller/AdminInquiryController.java → csms-java/src/main/java/com/csms/controller/AdminInquiryController.java

## Communities (37 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.06
Nodes (29): String, FileUploadService, List, Long, MultipartFile, Optional, PasswordEncoder, String (+21 more)

### Community 1 - "Community 1"
Cohesion: 0.07
Nodes (35): Booking Detail View, Booking List View, Double, Integer, LocalDateTime, Long, String, BookingDetail (+27 more)

### Community 2 - "Community 2"
Cohesion: 0.05
Nodes (45): SystemInfoRestController, Override, String, Optional, String, SystemInfo, MultipartFile, String (+37 more)

### Community 3 - "Community 3"
Cohesion: 0.07
Nodes (31): BookingService, GetMapping, InquiryService, Model, StorageService, String, Inquiry, Integer (+23 more)

### Community 4 - "Community 4"
Cohesion: 0.08
Nodes (22): StorageRestController, Double, Integer, LocalDateTime, Long, String, DeleteMapping, GetMapping (+14 more)

### Community 5 - "Community 5"
Cohesion: 0.07
Nodes (19): Long, String, Long, Override, String, Long, String, Long (+11 more)

### Community 6 - "Community 6"
Cohesion: 0.10
Nodes (26): Integer, List, Long, Optional, Storage, String, Integer, List (+18 more)

### Community 7 - "Community 7"
Cohesion: 0.14
Nodes (9): Integer, LocalDateTime, Long, String, Integer, LocalDateTime, Long, String (+1 more)

### Community 8 - "Community 8"
Cohesion: 0.14
Nodes (23): BookingService, GetMapping, InquiryService, Long, Map, Model, ModelAttribute, PostMapping (+15 more)

### Community 9 - "Community 9"
Cohesion: 0.17
Nodes (17): FileUploadService, List, Long, MultipartFile, Optional, Storage, StorageRepository, Transactional (+9 more)

### Community 10 - "Community 10"
Cohesion: 0.19
Nodes (17): GetMapping, Long, Model, MultipartFile, PostMapping, Storage, StorageService, String (+9 more)

### Community 11 - "Community 11"
Cohesion: 0.19
Nodes (17): GetMapping, Long, Model, MultipartFile, PostMapping, String, User, UserService (+9 more)

### Community 12 - "Community 12"
Cohesion: 0.23
Nodes (16): BookingDetailRepository, BookingDetail, List, Long, Optional, String, Transactional, BookingDetail (+8 more)

### Community 13 - "Community 13"
Cohesion: 0.20
Nodes (13): Override, String, User, UserRepository, Collection, DatabaseUserDetailsService, Override, String (+5 more)

### Community 14 - "Community 14"
Cohesion: 0.21
Nodes (15): BookingService, GetMapping, Integer, Long, Model, PostMapping, StorageService, String (+7 more)

### Community 15 - "Community 15"
Cohesion: 0.18
Nodes (14): BookingRepository, BookingList, Integer, List, Long, Optional, String, BookingList (+6 more)

### Community 16 - "Community 16"
Cohesion: 0.20
Nodes (10): About Us Page, Admin Dashboard, Admin Navigation Fragment, Inquiry List View, Storage List View, Storage Management, System Settings, User List View (+2 more)

### Community 17 - "Community 17"
Cohesion: 0.22
Nodes (13): GetMapping, InquiryService, Long, Model, PostMapping, String, AdminInquiryController, GetMapping (+5 more)

### Community 18 - "Community 18"
Cohesion: 0.18
Nodes (15): GetMapping, Map, Model, MultipartFile, PostMapping, String, SystemInfoService, AdminSystemController (+7 more)

### Community 19 - "Community 19"
Cohesion: 0.37
Nodes (8): AuthenticationEntryPoint, AuthenticationSuccessHandler, AuthService, Bean, SecurityConfig, PasswordEncoder, HttpSecurity, SecurityFilterChain

### Community 20 - "Community 20"
Cohesion: 0.35
Nodes (7): Override, String, CharSequence, LegacyAwarePasswordEncoder, Override, String, PasswordEncoder

### Community 21 - "Community 21"
Cohesion: 0.60
Nodes (5): Admin Login, Public Booking Form, Public Home Page, Public Navigation Fragment, Public Storage List

### Community 22 - "Community 22"
Cohesion: 0.33
Nodes (3): String, CsmsApplication, String

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (36): 1. Clone the Repository, 2. Setup Database, 3. Configure Application, 4. Build the Project, Build Failures, Building for Production, code:bash (git clone <repository-url>), code:properties (server.port=8082) (+28 more)

### Community 30 - "Community 30"
Cohesion: 0.11
Nodes (15): UserCreateRequest, UserRestController, DeleteMapping, GetMapping, Integer, List, Long, PostMapping (+7 more)

### Community 31 - "Community 31"
Cohesion: 0.17
Nodes (15): BookingRestController, BookingDetail, BookingList, BookingService, DeleteMapping, GetMapping, Integer, List (+7 more)

### Community 32 - "Community 32"
Cohesion: 0.19
Nodes (12): InquiryRestController, DeleteMapping, GetMapping, Inquiry, InquiryService, List, Long, PostMapping (+4 more)

### Community 33 - "Community 33"
Cohesion: 0.29
Nodes (7): Optional, User, UserRepository, Optional, User, UserRepository, AuthService

### Community 34 - "Community 34"
Cohesion: 0.36
Nodes (6): Model, ModelAttribute, SystemInfoService, AdminModelAdvice, Model, ModelAttribute

## Knowledge Gaps
- **101 isolated node(s):** `recordToolUse.sh script`, `java.compile.nullAnalysis.mode`, `String`, `String`, `Override` (+96 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `BookingService` connect `Community 1` to `Community 3`, `Community 12`, `Community 14`, `Community 15`?**
  _High betweenness centrality (0.174) - this node is a cross-community bridge._
- **Why does `Booking List View` connect `Community 1` to `Community 0`, `Community 16`, `Community 7`?**
  _High betweenness centrality (0.111) - this node is a cross-community bridge._
- **Why does `StorageService` connect `Community 9` to `Community 3`, `Community 14`?**
  _High betweenness centrality (0.091) - this node is a cross-community bridge._
- **What connects `recordToolUse.sh script`, `java.compile.nullAnalysis.mode`, `String` to the rest of the system?**
  _101 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.06340326340326341 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.07330618289522399 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.05456095481670929 - nodes in this community are weakly interconnected._