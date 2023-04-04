# COMP90082 Software Project 2022 SM2 WO-Bluering

- [COMP90082 Software Project 2022 SM2 WO-Bluering](#comp90082-software-project-2022-sm2-wo-bluering)
  - [Project Background](#project-background)
  - [Project Overview](#project-overview)
  - [Deployment](#deployment)
  - [Folder Structure](#folder-structure)
  - [Branches/Naming Conventions](#branchesnaming-conventions)
  - [Changelog](#changelog)
      - [Sprint 4 Changelog (2022-11-04)](#sprint-4-changelog-2022-11-04)
      - [Sprint 3 Changelog (2022-10-20)](#sprint-3-changelog-2022-10-20)
      - [Sprint 2 Changelog (2022-09-19)](#sprint-2-changelog-2022-09-19)
      - [Sprint 1 Changelog (2022-08-22)](#sprint-1-changelog-2022-08-22)


# Project Background

## About the Client
**Tri-alliance** is the largest triathlon community in Australia, based in Melbourne and currently trains over 200 triathletes weekly under the guidance of eight qualified and experienced coaches. 

Link: https://vic.tri-alliance.com/about/why-tri-alliance/

**Ollie Allan (Client)** - Head Coach, Level 2. Ollie has experience in triathlon for 16+ years and has extensive qualifications, including Level 2 Triathlon Coach, Level 1 Cycling, Swimming, Strength & Conditioning Coach, Bachelor of Arts Recreation Management and more.

Link: https://vic.tri-alliance.com/ollie-allan/

## About the System

Currently, Tri-alliance is using CoachingMate to track and analyse the performance of athletes. It is developed by WordPress, which serves both athletes and coaches. Garmin watch data has been integrated into the current platform, and the Tri-alliance data platform is hosted on Heroku. 

### CoachingMate

CoachingMate is a cloud-based ecosystem integrated with wearable devices such as Garmin watches, tracking detailed information about the workouts being conducted and providing instructions for the athlete to keep track of their progress.

### Garmin Watches and API
Garmin Watches and Garmin Connect APIs offer an opportunity for sports platforms to be integrated with the user's health and wellness tracking or workout experience.

Link: https://developer.garmin.com/gc-developer-program/overview/



# Project Overview
The project is built on top of previous GA-Boxjelly project, which has implemented a dashboard with features such as user registration and login, connecting and syncing data from Garmin API, and viewing activity lists. Our main goal is to build a Machine Learning Module that uses the synced real-time training data to estimate an athlete's Training Zone on pace and dynamically adjust it over time. The project also implements and visualizes Daniel's Table as a benchmark, which instead uses athelets' race performance to estimate their Training Zone. The project also aims to make race projections and visualize runners' performance improvements in a more interactive way to motivate athletes.

### Using Machine Learning Technology
We will to use real-time data and redefine the problem as a regression task, predicting an athlete's current threshold pace based on his/her recent training data. The threshold pace can then be used to set the athlete's Training Zone. We will implement Daniel's Table formula and display the calculated Training Zone results as a benchmark or an initialization of a new user who has no live data.

### Reuse Previous GA Project Code
In order to access live data as soon as possible, we decided not to build our own bashboard, but to use the previous GA project code, which has implemented a dashboard with features such as user registration and login, connecting and syncing data from Garmin API, and viewing activity lists. We have deployed front-end and back-end for athletes so that athletes can log in to the website and connect to their Garmin Account. We can now obtain real-time sports data of athletes.

# Folder Structure

This repository is used to store project-related docs/code/design/data. The main documentation about the project is located in the 'docs/confluence export' directory, which will be updated every sprint.

```python
├── docs/                     # Documentation files
│    └──confluence export     # Exported .pdf files from 'confluence'
├── src/                      # Source code
├── tests/                    # User/system tests
├── prototypes/low fidelity/  # Low fidelity files (screens, mockups and so on)
├── prototypes/high fidelity/ # High fidelity files (screens, source files and so on)
├── ui/                       # All the images created for the prototypes 
├── data samples/             # Data (inputs) to simulate/demonstrate the prototype
└── README.md
```

# Deployment

## Frontend

(See the frontend deploy page in confluence export for details.)

## Backend

(See the backend deploy page in confluence export for details.)

## Development Environment
- Frontend:
  - Node.js: v16.15.0
  - react: 16.9.34
- Backend:
  - Apache Maven: 3.8.6
  - Java version: OpenJDK 11
  - MongoDB: 5.0.12

# Branches/Naming Conventions

**Branch Naming Convention**: `${TYPE}-${DESCRIPTION}`

- Branch Names are all uppercase.
- TYPE: 
  - **BUG** – The bug which needs to be fixed soon
  - **WIP** – The work is in progress, and I am aware it will not finish soon

- DESCRIPTION: Briefly explain what the branch does.

**Commit Naming Convention**: `${type}(${summary}): ${description}`

- In principle, commit messages should use all lowercase letters. Except for proper nouns or variables.
- type:
  - **feat** (feature)
  - **fix** (bug fix)
  - **doc** (documentation)
  - **style** (formatting, missing semi colons, …)
  - **refactor**
  - **test** (when adding missing tests)
  - **chore** (maintain)

- summary: Summarise the description in a few words.
- description: The description of the commit.

**Examples** 

| Branches                 | Commit                              |
| ------------------------ | ----------------------------------- |
| BUG-LOGO-ALIGNMENT-ISSUE | doc(swagger): make swagger useable  |
| WIP-IOC-CONTAINER-ADDED  | feat(login): finish login function. |


# Changelog
## Sprint 4 Changelog (2022-11-04)

2022-11-04 **Docs**: Latest content of Confluence exported as pdf and uploaded.

2022-11-03 **Source Code**: (User stories part three ) New calculation options have been added in table pages .


## Sprint 3 Changelog (2022-10-20)

2022-10-20 **Docs**: Latest content of Confluence exported as pdf and uploaded.

2022-10-19 **Source Code**: (User stories part two) Tables code uploaded.

2022-10-15 **Source Code**: (User stories part three ) Machine learning pythone code uploaded.

2022-09-26 **Source Code**: (User stories part one ) Login and register code uploaded.



## Sprint 2 Changelog (2022-09-19)

2022-09-19 **Source Code**: Current backend code uploaded.

2022-09-18 **Docs**: Latest content of Confluence exported as pdf and uploaded.

2022-09-18 **Source Code**: Current frontend code for proof of concept uploaded.

2022-09-12 **Data Sample**: Historical training data is retrieved, filtered and uploaded.



## Sprint 1 Changelog (2022-08-22)

2022-08-21 **Docs**: Latest content of Confluence exported as pdf and uploaded.

2022-08-18 **Environment**: Github repository established.

2022-08-09 **Environment**: Confluence and Trello space established.

