# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/png-072/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | Eclipse Temurin 17.0.20.1 |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.137.0 |
| SonarQube for IDE | 6.2.0 |
| AI tool and exact model | OpenAI Codex, GPT-5 |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: ec9b87d

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 7 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 8 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 9 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 10 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 11 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 12 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 2026-10-25 |
| AI tool and exact model | OpenAI Codex, GPT-5 |
| Commit reviewed | ec9b87d |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Opened the folder and read every Java file under `parcelpoint/src/main/java` |
| Output file in the reviews folder | `reviews/parcelpoint-review.md` |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:8 | Scanner is never closed | AI | Resource management | Yes, although its lifetime is the same as the short-lived CLI process. |
| 2 | Main.java:9 | System.out should be replaced by a logger | SonarQube | java:S106 | No for this program, because console output is the required user interface. |
| 3 | Main.java:11 | Command parsing can fail on blank, extra-spaced, incomplete or malformed input | AI | Probable bug | Yes, fixed array positions and numeric conversion are used without validation. |
| 4 | Main.java:15 | System.out should be replaced by a logger | SonarQube | java:S106 | No for this program, because console output is the required user interface. |
| 5 | Main.java:17 | System.out should be replaced by a logger | SonarQube | java:S106 | No for this program, because console output is the required user interface. |
| 6 | Main.java:22 | System.out should be replaced by a logger | SonarQube | java:S106 | No for this program, because console output is the required user interface. |
| 7 | Main.java:22 | Main reaches through ParcelService into its locker collection | AI | Strong coupling | Yes, Main depends on the service's internal collection and Locker. |
| 8 | ParcelService.java:1 | Use java.time instead of java.util.Date | SonarQube | java:S2143 | No as stated, because Date is unused and should be removed rather than replaced. |
| 9 | ParcelService.java:6 | Unused java.util.Date import | AI | Unused import | Yes, no code uses Date. |
| 10 | ParcelService.java:12 | Unused maxParcels field | Both | java:S1068 | Yes, the field is never read. |
| 11 | ParcelService.java:30 | System.out should be replaced by a logger | SonarQube | java:S106 | No for this program, because the SMS text is simulated console output. |
| 12 | ParcelService.java:42 | Parcel codes are compared with `==` instead of `equals()` | Both | java:S4973 | Yes, the test run failed to find P1 and incorrectly returned fee 0.0. |
| 13 | ParcelService.java:49 | Missing parcel and valid free pickup both return 0 | AI | Ambiguous result | Yes, callers cannot distinguish the two outcomes. |
| 14 | ParcelService.java:67 | System.out should be replaced by a logger | SonarQube | java:S106 | No for this program, because console output is the required user interface. |
| 15 | ParcelService.java:69 | System.out should be replaced by a logger | SonarQube | java:S106 | No for this program, because console output is the required user interface. |
| 16 | ParcelService.java:76 | FileWriter is never closed or flushed | Both | java:S2095 | Yes, the test run left parcels.csv empty. |
| 17 | ParcelService.java:80 | IOException catch block is empty | Both | java:S108 | Yes, file failures are silently hidden. |
| 18 | Main.java:6 | main has procedural cohesion and handles too many command-processing steps | AI | Procedural cohesion | Yes, parsing, dispatch and formatting are all combined. |
| 19 | ParcelService.java:9 | ParcelService has multiple responsibilities | AI | Weak class cohesion | Yes, it handles lockers, fees, output and persistence. |
| 20 | ParcelService.java:64 | Boolean flag gives printParcels logical cohesion | AI | Logical cohesion / control coupling | Yes, the flag selects two different output behaviors. |
| 21 | ParcelService.java:20 | getLockers exposes the mutable internal list | AI | Representation exposure | Yes, callers can modify the service's lockers directly. |
| 22 | Parcel.java:7 | Public mutable status creates content coupling | AI | Content coupling | Yes, ParcelService directly changes Parcel's internal state. |
| 23 | Main.java:13 | Parcel constructor depends on positional arguments | AI | Connascence of position | Yes, especially because the first two arguments are both Strings. |
| 24 | Locker.java:5 | Integer size codes are shared across classes | AI | Connascence of convention | Yes, several classes must agree that 1, 2 and 3 have the same meanings. |

2.1 Name two problems that only the AI review found.

The AI found that `ParcelService` has weak class cohesion because it handles locker allocation, fees, output and persistence. It also found content coupling caused by the public mutable `Parcel.status` field.

2.2 Name one problem that only SonarQube found, or write none.

SonarQube alone reported java:S106 for the uses of `System.out`.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

The Scanner resource-leak finding was too strong for this program. The Scanner has the same lifetime as this short command-line process, and closing it would only close `System.in` immediately before the process exits; I checked that the scanner is created once and is not repeatedly leaked.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | | |

Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| | | |
| | | |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
