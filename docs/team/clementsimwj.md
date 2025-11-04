---
  layout: default.md
  title: "John Doe's Project Portfolio Page"
---

### Project: Atlas

Atlas is a desktop address book application built specifically for Food & Beverage Business owners. Users can
easily manage their business stakeholders using various commands. It has a GUI created with JavaFX. It is written in Java, and has
about 10,000 lines of code (10 kLoC).

Given below are my contributions to the project.

* **Archive Feature**: Added the ability to save data locally to another file.
    * What it does: Allows F&B Business owners to archive data of past months/years to reduce clutter in the main data file.
    * Justification: This feature improves the product significantly because business owners can ensure auditability and keep track of past records for legal compliance. At the same time, clearing the main address book reduces cluttering of unnecessary data.
    * Highlights: This enhancement affects existing commands and commands to be added in the future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.
    * Credits: This feature relied on the implementation of the main address book.


* **Unarchive Feature**: Added the ability to restore data from an archived file.
    * What it does: Allows F&B Business owners to restore data from an archived file back into the main data file.
    * Justification: This feature improves the product significantly because business owners can retrieve past records when needed for reference or legal purposes.
    * Highlights: Due to it being related to the archive feature, implementing this feature was slightly easier since the methods in the archive feature already provided ways to retrieve archived data.
    * Credits: This feature relied heavily on the implementation of the main address book.

* **Code contributed**: [RepoSense link]()

* **Project management**:
    * Managed releases `v1.3` - `v1.6` (4 releases) on GitHub

* **Enhancements to existing features**:
    * Updated the GUI color scheme.
    * Made Tag Names more contextualised to F&B Business environment. (Supplier, Customer, Landlord, etc.)
    * Wrote additional tests for existing features to increase coverage.
    * Improved Specificity of Error Messages thrown to users to enable better user experience.
    * Refactored code to improve maintainability and readability.

* **Documentation**:
    * User Guide:
        * Rewrote the entire user guide by contextualising it towards F&B Business Owners
        * Rewrote the explanations, examples and valid inputs for all commands.
        * Formatted the user guide to be more visually appealing and easier to read.
        * Provided pictures, screenshots and even videos for each command.
        * Provided explanation on how the code checks for duplication, user validation, etc.
    * Developer Guide:
        * Added Sequence Diagrams for `archive` and `unarchive` commands.
        * Provided acknowledgement for code reuse.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())


