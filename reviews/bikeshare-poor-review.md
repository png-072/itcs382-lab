Files read: `bikeshare-poor/src/main/java/bikeshare/Main.java`.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | Main.java:24 | The command parser reads fixed array positions without checking argument counts, so incomplete commands depend on a broad exception handler instead of producing a specific validation result. | Should fix | Validate each command's argument count and values before processing it. |
| 2 | Check 1 | Main.java:61 | Return processing removes the member's bike and type before validating the station and minutes, so an invalid return permanently loses the active rental. | Must fix | Validate all return input before removing data from the active-rental maps. |
| 3 | Check 1 | Main.java:67 | Every member type other than integer `1` silently receives the staff rate, including invalid codes. | Must fix | Validate the code against the two supported member types before calculating the fee. |
| 4 | Check 1 | Main.java:66 | Program state is changed before the ride is written, so a file failure leaves the ride returned in memory but absent from `rides.csv`. | Should fix | Save transactionally or restore the in-memory state if persistence fails. |
| 5 | Check 1 | Main.java:43 | `process` is long and handles two different workflows, input conversion, validation, state changes, fee calculation and persistence. | Should fix | Split rent and return into focused methods. |
| 6 | Check 2 | Main.java:11 | `Main` represents the menu, station inventory, active rentals, fee rules, receipt output and CSV persistence, so the class has coincidental cohesion. | Must fix | Separate the user interface, rental rules, stations and persistence into focused types. |
| 7 | Check 2 | Main.java:43 | `process` has logical cohesion and control coupling because the `returning` flag selects either the rent or return algorithm. | Must fix | Replace the flag with separate `rent` and `returnBike` operations. |
| 8 | Check 2 | Main.java:76 | `print` has logical cohesion and control coupling because `receipt` selects two different output formats. | Should fix | Use separate operations for rental confirmation and ride receipts. |
| 9 | Check 2 | Main.java:12 | The three public static mutable maps expose shared state to every caller, creating common coupling and allowing uncontrolled modification. | Must fix | Make state private and let focused objects protect their own data. |
| 10 | Check 2 | Main.java:13 | The parallel `memberBikes` and `memberTypes` maps must always contain matching keys, creating connascence of identity and vulnerable shared state. | Should fix | Store one rental object per member in a single private collection. |
| 11 | Check 2 | Main.java:46 | Integer member type codes create connascence of convention between input handling and the fee algorithm. | Should fix | Replace the integer codes with a `MemberType` enum that owns its rate. |

Check 1 findings: 5.

Check 2 findings: 6.
