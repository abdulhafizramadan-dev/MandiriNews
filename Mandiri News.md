---


---

<h1 id="mandiri-news">Mandiri News</h1>
<p><strong>Mandiri News</strong> is a news portal application that uses the <a href="http://newsapi.org">newsapi.org</a> Public API, Mandiri News is built using the Modern UI Toolkit <strong>Jetpack Compose</strong> and uses many other Jetpack Libraries such as <strong>Paging 3</strong>, <strong>Room</strong>, <strong>Hilt Dependency Inject</strong>, and <strong>Datastore</strong>. To perform Network Request Mandiri News uses the <strong>Retrofit</strong> Library, Mandiri News has used <strong>Reactive Programming</strong> with <strong>Coroutines Flow</strong>. Mandiri News has used the <strong>Offline - First Concept</strong>, where the data displayed to users is data that comes from the <strong>Local Database (Room)</strong>. Mandiri News has several main features, namely:</p>
<ol>
<li><strong>Home Page</strong> that displays the latest news and all news that displays specific news with pagination.</li>
<li><strong>Location page</strong> displays a list of countries, which later when selecting the country. Then the latest news will display news based on the selected country.</li>
<li><strong>Search page</strong> is used to search for news based on the title the user wants, news is displayed using pagination so that the user can continue to see the news until the last page.</li>
<li><strong>Search History page</strong> displays a list of searches that have been made by users, users can directly click on the search history to immediately search for the previous news. Or users can delete the search history that has been done.</li>
</ol>
<p><a href="https://github.com/abdulhafizramadan-dev/MandiriNews/releases/download/v.1/app-debug.apk">Download Mandiri News</a></p>
<p><a href="https://youtube.com/playlist?list=PLmMnsnLKsZuAfjSRijQzygo1bCweTGxFN">View Full Demo On Youtube</a></p>
<h2 id="screen-list">Screen List</h2>
<h3 id="home">Home</h3>

<table>
<thead>
<tr>
<th>Placeholder</th>
<th>Headline News</th>
<th>All News</th>
</tr>
</thead>
<tbody>
<tr>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/43e4507a-a4ae-4171-b379-6c24be146600" alt="1 Home - Placeholder"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/d14ee75f-9afb-4e7d-b3ca-5b500721deea" alt="2 Home - Headline News"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/cce0b54f-73b4-490b-acd3-74c8e99346ac" alt="3 Home - All News"></td>
</tr>
</tbody>
</table><h4 id="demo">Demo</h4>
<iframe width="560" height="315" src="https://www.youtube.com/embed/Odi-cVbIlhY" title="YouTube video player" allowfullscreen=""></iframe>
<h3 id="location">Location</h3>

<table>
<thead>
<tr>
<th>Half</th>
<th>Full</th>
<th>Search</th>
</tr>
</thead>
<tbody>
<tr>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/7d0ccdb8-6397-4018-9181-4d8a227ee65d" alt="4 Location - Half"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/2a0a58f7-6bd6-4865-a910-e6221506e2f8" alt="5 Location - Full"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/544eb94b-8649-4bff-9244-bdd81a1adf1f" alt="6 Location - Searching"></td>
</tr>
</tbody>
</table>
<table>
<thead>
<tr>
<th>Dialog</th>
<th>Selected</th>
</tr>
</thead>
<tbody>
<tr>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/75ba34b5-a6a3-412d-a7d8-ffe2bf7ecb3c" alt="7 Location - Dialog"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/0558259a-8465-4bd2-8fe5-eee42c761072" alt="8 Location - Selected"></td>
</tr>
</tbody>
</table><h4 id="demo-1">Demo</h4>
<iframe width="560" height="315" src="https://www.youtube.com/embed/SkFlf_P6TPM" title="YouTube video player" allowfullscreen=""></iframe>
<h3 id="search">Search</h3>

<table>
<thead>
<tr>
<th>Recent</th>
<th>Delete Dialog</th>
<th>Searching</th>
</tr>
</thead>
<tbody>
<tr>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/529fa8c3-6b20-47d8-9947-81e53d23f0a9" alt="9 Search - Recent"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/acc17973-126b-4900-b34a-7149b986000e" alt="10 Search - Delete Dialog"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/74fe77fd-8a53-40ba-bb9a-13335507eef3" alt="11 Search - Searching"></td>
</tr>
</tbody>
</table>
<table>
<thead>
<tr>
<th>Result</th>
<th>Not Found</th>
<th>No Connection</th>
</tr>
</thead>
<tbody>
<tr>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/931843bc-b6ed-45d3-963f-29050d01cea6" alt="12 Search - Result"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/a619d57b-ad7b-408b-9b54-55003c8a349d" alt="13 Search - Not Found"></td>
<td><img src="https://github.com/abdulhafizramadan-dev/MandiriNews/assets/111162360/5f63befe-e80c-4cf5-a708-8551f80d02e7" alt="14 Search - No Connection"></td>
</tr>
</tbody>
</table><h4 id="demo-search---normal">Demo Search - Normal</h4>
<iframe width="560" height="315" src="https://www.youtube.com/embed/uOykBnI3bnw" title="YouTube video player" allowfullscreen=""></iframe>
<h4 id="demo-search---not-found">Demo Search - Not Found</h4>
<iframe width="560" height="315" src="https://www.youtube.com/embed/HJ0ODe2ytUY" title="YouTube video player" allowfullscreen=""></iframe>
<h4 id="demo-search---no-connection">Demo Search - No Connection</h4>
<iframe width="560" height="315" src="https://www.youtube.com/embed/R9bOKsu82hM" title="YouTube video player" allowfullscreen=""></iframe>
<h3 id="demo-offline-mode">Demo Offline Mode</h3>
<iframe width="560" height="315" src="https://www.youtube.com/embed/659Q-vkj7Rc" title="YouTube video player" allowfullscreen=""></iframe>

