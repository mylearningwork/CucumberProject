$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("C:/Users/22231/mylatest2/CucumberProject/src/main/java/Features/login.feature");
formatter.feature({
  "line": 1,
  "name": "free CRM Login feature",
  "description": "",
  "id": "free-crm-login-feature",
  "keyword": "Feature"
});
formatter.before({
  "duration": 10864424202,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "free crm login test scenario",
  "description": "",
  "id": "free-crm-login-feature;free-crm-login-test-scenario",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 6,
  "name": "user is on base url",
  "keyword": "Given "
});
formatter.step({
  "line": 8,
  "name": "user enters user id",
  "keyword": "Then "
});
formatter.step({
  "line": 10,
  "name": "user enters password",
  "keyword": "Then "
});
formatter.step({
  "line": 12,
  "name": "then clicks on login button",
  "keyword": "Then "
});
formatter.match({
  "location": "LoginStepDefinitions.user_is_on_base_url()"
});
formatter.result({
  "duration": 96200578,
  "status": "passed"
});
formatter.match({
  "location": "LoginStepDefinitions.user_enters_user_id()"
});
