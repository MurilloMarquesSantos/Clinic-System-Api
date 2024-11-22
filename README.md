<h1>Clinic System Api</h1>
<p>This application was built to streamline appointment scheduling for clinics, making it simple for users to book consultations, check available doctors and their specialties, and view open time slots, and email sending for confirmation. Users can easily make, view, and cancel bookings, while system administrators have access to monitoring tools for system health and performance metrics</p>

<h2>Features:</h2>
<ul>
    <li><strong>User Authentication: </strong>Secure registration and login using Spring Security.</li>
    <li><strong>Password Management: </strong>Change passwords via secure email links.</li>
    <li><strong>Oauth2 with JWT: </strong>Provides robust security and stateless authentication.</li>
    <li><strong>Role-based authorization: </strong>Access control based on roles (ADMIN, USER, DOCTOR).</li>
    <li><strong>Error handling: </strong>Detailed exception handling for clear API responses.</li>
    <li><strong>API documentation: </strong> Interactive Swagger UI for endpoint testing.</li>
    <li><strong>Monitoring Tools: </strong></li>
    <li><strong>Prometheus: </strong>Collect metrics for performance analysis.</li>
    <li><strong>Grafana Dashboards: </strong>Visualize system metrics with preconfigured templates.</li>
</ul>

<h2>Technologies</h2>
<ul>
    <li>Spring Boot 3.3</li>
    <li>Spring Security 6.3</li>
    <li>Spring Data</li>
    <li>Oauth2</li>
    <li>JavaMail</li>
    <li>Maven</li>
    <li>Swagger</li>
    <li>Prometheus</li>
    <li>Grafana</li>
</ul>

<h2>Requirements</h2>
<ul>
    <li>Java 17</li>
    <li>Docker (recommended) or MySQL</li>
</ul>

<h2>Getting Started</h2>
<p>To run the application firstly, you need to manually add roles into the database:</p>
    <li>Run in the terminal: <code>docker-compose up</code></li>
    <li>Once you have access to the database, add the roles:</li>
<ol>
    <li><strong>Id: 1, ROLE : ADMIN</strong></li>
    <li><strong>Id: 2, ROLE : USER</strong></li>
    <li><strong>Id: 3, ROLE : DOCTOR</strong></li>
</ol>

<p>There is a class InitialConfig in "config" made for tests only. This class on running the Api will add some info
to the database</p>
<h3>Running Tests</h3>
    <ul>
    <li>To run <strong>unit tests</strong>: <code>mvn test</code></li>
    </ul>

<h2>JavaMail</h2>
<p>If you have any kind of problem with the email config, this YouTube video can help you fix it <a href="https://www.youtube.com/watch?v=ugIUObNHZdo" target="_blank">JavaMail Video</a></p>


<h2>Usage</h2>
<h3>Swagger API Documentation</h3>
<p>To view the API documentation, run the application and navigate to: <br><code>http://localhost:8080/swagger-ui.html</code><br>All endpoints include detailed summaries and descriptions and the body in JSON format as well.</p>

<h3>Prometheus Monitoring</h3>
<p>Prometheus can be accessed at: <br><code>http://localhost:9090/</code></p>

<h3>Grafana Dashboards</h3>
<p>To view metrics in Grafana:</p>
<ol>
    <li>Run the application.</li>
    <li>Navigate to <code>http://localhost:3000</code> (default Grafana URL).</li>
    <li>Create a new data source using Prometheus with this URL: <code>http://prometheus:9090</code>.</li>
    <li>A recommended dashboard is <a href="https://grafana.com/grafana/dashboards/4701-jvm-micrometer/" target="_blank">JVM (Micrometer)</a>, which provides detailed JVM and application metrics.</li>
</ol>

<h2>Actuator Endpoints</h2>
<ul>
    <li><code>/actuator/info</code> – Basic application information</li>
    <li><code>/actuator/metrics</code> – Application metrics</li>
    <li><code>/actuator/health</code> – Health status</li>
    <li><code>/actuator/prometheus</code> – Prometheus metrics endpoint</li>
</ul>