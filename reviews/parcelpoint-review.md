Files read: `parcelpoint/src/main/java/parcelpoint/Locker.java`, `parcelpoint/src/main/java/parcelpoint/Main.java`, `parcelpoint/src/main/java/parcelpoint/Parcel.java`, and `parcelpoint/src/main/java/parcelpoint/ParcelService.java`.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | Main.java:8 | The `Scanner` owns `System.in` but is never closed, so the resource is leaked. | Should fix | Use try-with-resources for the scanner. |
| 2 | Check 1 | Main.java:11 | Splitting only on one literal space and reading fixed array positions lets blank, extra-spaced, incomplete, or malformed commands cause incorrect parsing or runtime exceptions. | Must fix | Validate the command and argument count, and split nonblank input with `\\s+` before conversion. |
| 3 | Check 1 | ParcelService.java:6 | The `java.util.Date` import is unused. | Should fix | Remove the unused import. |
| 4 | Check 1 | ParcelService.java:12 | The `maxParcels` field is never read and therefore has no effect. | Should fix | Remove the field or enforce the limit when storing a parcel. |
| 5 | Check 1 | ParcelService.java:42 | Comparing parcel codes with `==` compares String object identity, so an existing parcel can fail to be found during pickup. | Must fix | Compare the codes with `equals()`. |
| 6 | Check 1 | ParcelService.java:49 | Returning `0` for a missing parcel is indistinguishable from a valid free pickup during the first three days. | Should fix | Return a result that represents not found separately from a zero fee. |
| 7 | Check 1 | ParcelService.java:76 | The `FileWriter` is never closed or flushed, which leaks a resource and can leave `parcels.csv` empty or incomplete. | Must fix | Create the writer in a try-with-resources statement. |
| 8 | Check 1 | ParcelService.java:80 | The empty `catch` block silently discards file-writing failures. | Must fix | Report or propagate the exception with useful context. |
| 9 | Check 2 | Main.java:6 | `main` has procedural cohesion because it parses every command, constructs domain objects, dispatches operations, and formats all command output in one method. | Should fix | Extract command parsing or individual command handlers into focused methods. |
| 10 | Check 2 | ParcelService.java:9 | `ParcelService` represents several responsibilities: locker allocation, pickup, fee calculation, console/SMS output, parcel listing, and CSV persistence, so the class has weak cohesion. | Should fix | Move presentation and persistence behind separate focused classes or interfaces. |
| 11 | Check 2 | ParcelService.java:64 | `printParcels` has logical cohesion because the `forStaff` flag selects two different output behaviors. | Should fix | Replace the flag with separate staff and student listing operations. |
| 12 | Check 2 | ParcelService.java:20 | `getLockers` returns the mutable internal list, allowing callers to add, remove, or replace lockers outside the service. | Must fix | Return an unmodifiable view or expose only the query the caller needs. |
| 13 | Check 2 | Main.java:22 | `Main` reaches through `ParcelService` into its locker collection and then calls `Locker.isFree`, creating stronger coupling to the service's internal representation. | Should fix | Add a `getFreeLockerCount()` query to `ParcelService`. |
| 14 | Check 2 | Parcel.java:7 | The public mutable `status` field exposes representation and lets `ParcelService` change another class's internal state directly at lines 28 and 44, creating content coupling. | Must fix | Make the field private and provide an operation that performs a valid status transition. |
| 15 | Check 2 | Main.java:13 | The `Parcel(String, String, int)` call has connascence of position, and its two adjacent String arguments can be swapped without a compiler error. | Note | Use a named factory or value types that make the argument roles explicit. |
| 16 | Check 2 | Locker.java:5 | The meaning of size codes `1`, `2`, and `3` is shared by `Locker`, `Parcel`, `Main`, and the fee algorithm, creating connascence of convention. | Should fix | Replace the integer convention with a shared enum or value type. |

Check 1 findings: 8.

Check 2 findings: 8.
