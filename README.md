# Mandiri News

**Mandiri News** is a news portal application that uses the newsapi.org Public API, Mandiri News is built using the Modern UI Toolkit **Jetpack Compose** and uses many other Jetpack Libraries such as **Paging 3**, **Room**, **Hilt Dependency Inject**, and **Datastore**. To perform Network Request Mandiri News uses the **Retrofit** Library, Mandiri News has used **Reactive Programming** with **Coroutines Flow**. Mandiri News has used the **Offline - First Concept**, where the data displayed to users is data that comes from the **Local Database (Room)**. Mandiri News has several main features, namely:
1. **Home Page** that displays the latest news and all news that displays specific news with pagination.
2. **Location page** displays a list of countries, which later when selecting the country. Then the latest news will display news based on the selected country.
3. **Search page** is used to search for news based on the title the user wants, news is displayed using pagination so that the user can continue to see the news until the last page.
4. **Search History page** displays a list of searches that have been made by users, users can directly click on the search history to immediately search for the previous news. Or users can delete the search history that has been done.

## Screen List

### Home
| Placeholder | Headline News| All News |
|--|--|--|
| ![1 Home - Placeholder](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/43e4507a-a4ae-4171-b379-6c24be146600) | ![2 Home - Headline News](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/d14ee75f-9afb-4e7d-b3ca-5b500721deea) | ![3 Home - All News](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/cce0b54f-73b4-490b-acd3-74c8e99346ac) |

#### Demo
<iframe width="560" height="315" src="https://www.youtube.com/embed/Odi-cVbIlhY" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

### Location
| Half | Full | Search |
|--|--|--|
| ![4 Location - Half](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/7d0ccdb8-6397-4018-9181-4d8a227ee65d) | ![5 Location - Full](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/2a0a58f7-6bd6-4865-a910-e6221506e2f8) | ![6 Location - Searching](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/544eb94b-8649-4bff-9244-bdd81a1adf1f) |

| Dialog | Selected |
|--|--|
| ![7 Location - Dialog](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/75ba34b5-a6a3-412d-a7d8-ffe2bf7ecb3c) | ![8 Location - Selected](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/0558259a-8465-4bd2-8fe5-eee42c761072) |

#### Demo
<iframe width="560" height="315" src="https://www.youtube.com/embed/SkFlf_P6TPM" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

### Search
| Recent | Delete Dialog | Searching |
|--|--|--|
| ![9 Search - Recent](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/529fa8c3-6b20-47d8-9947-81e53d23f0a9) | ![10 Search - Delete Dialog](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/acc17973-126b-4900-b34a-7149b986000e) | ![11 Search - Searching](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/74fe77fd-8a53-40ba-bb9a-13335507eef3) |

| Result | Not Found | No Connection |
|--|--|--|
| ![12 Search - Result](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/931843bc-b6ed-45d3-963f-29050d01cea6) | ![13 Search - Not Found](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/a619d57b-ad7b-408b-9b54-55003c8a349d) | ![14 Search - No Connection](https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/5f63befe-e80c-4cf5-a708-8551f80d02e7) |

#### Demo Search - Normal
<iframe width="560" height="315" src="https://www.youtube.com/embed/uOykBnI3bnw" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

#### Demo Search - Not Found
<iframe width="560" height="315" src="https://www.youtube.com/embed/HJ0ODe2ytUY" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

#### Demo Search - No Connection
<iframe width="560" height="315" src="https://www.youtube.com/embed/R9bOKsu82hM" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

### Demo Offline Mode
<iframe width="560" height="315" src="https://www.youtube.com/embed/659Q-vkj7Rc" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
