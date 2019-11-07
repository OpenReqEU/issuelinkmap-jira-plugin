# OpenReq Issue Link Map - Jira plugin

This jira plugin was created as a result of the OpenReq project funded by the European Union Horizon 2020 Research and Innovation programme under grant agreement No 732463.

This jira plugin visualizes the links between issues inside Qt’s JIRA. It uses the microservices of UH ([API references](https://api.openreq.eu/#/services/milla)) and the similarity detection service of UPC ([API references](https://api.openreq.eu/#/services/similarity-detection)).
The OpenReq services [milla](https://github.com/OpenReqEU/milla), [mallikas](https://github.com/OpenReqEU/mallikas), [keljucaas](https://github.com/OpenReqEU/keljucaas), [mulperi](https://github.com/OpenReqEU/mulperi), [palmu](https://github.com/OpenReqEU/palmu), [nikke](https://github.com/OpenReqEU/nikke) and the [similarity detection](https://github.com/OpenReqEU/similarity-detection) are used by this jira plugin and need to be running in order for the jira plugin to function.

## Technical description
### Technologies used
- Tomcat
- Vis.js
- Google Gson
- Okhttp3
- Maven
- Bootstrap
- Jira by Atlassian

### Functionalities of the OpenReq Issue Link Map
#### Currently available features
- *Visualization* of the issue link map of issues in [Qt’s jira](https://bugreports-test.qt.io/secure/Dashboard.jspa)

While in jira the user can only see the direct links, this tool enables the user to go more in-depth and also view indirect links between jira items.

- Quick *info* for selected issue

Essential information of an issue is displayed on the right-hand side.

- *Navigating* the link network

The user can drag items and add or remove depth.

- *Consistency Checker*

Checking if the issues in the link map do not have conflicting link type and priority and displaying the result of a diagnosis that identifies potential inconsistencies.
- *Filtering* the issue link map for specified properties

To support users in navigating the issue link map, they can filter the visualized issues by type, status and priority.

- *Accepting & rejecting* proposed links

The results of a link detection are also visualized, this view should can be toggled on and off for a specific issue. While the link detection is enabled the user is given a list where he can decide what type of link should be used or if this proposed link should not be a link.

- *Full Integration* into jira as a *jira plug-in*

Depending on the permission level of the users, they might not see private jira issues. Accepted & rejected recommended links are written back to the jira database if the user is authorized to do so.

#### Functionalities in development

- *Add more filter options*

Adding more filter options, e.g to also filter edges.

- *Show inconsistencies in issue link map*

Inconsistencies should be visually emphasized in the issue link map.

#### Functionalities planned
- *Editing* links

By right-clicking a link, users can change the link type, switch direction or remove the link.

- *Editing* issues

By selecting an issue, users can change the issue properties.

- *Adding* new links

Users can search for and add existing issues into the graph and link them to an issue already in the issue link map.

### Accessing the application
The application is accessible [here](https://bugreports-test.qt.io/secure/LinkMapWebworkAction.jspa)

## How to install
You first need to deploy the following OpenReq microservices on your jira server:
- [mallikas](https://github.com/OpenReqEU/mallikas)
- [mulperi](https://github.com/OpenReqEU/mulperi)
- [keljucaas](https://github.com/OpenReqEU/keljucaas)
- [palmu](https://github.com/OpenReqEU/palmu)
- [milla](https://github.com/OpenReqEU/milla)
- [nikke](https://github.com/OpenReqEU/nikke)
- [similarity detection](https://github.com/OpenReqEU/similarity-detection)

Mallikas is a database that jira plugin accesses over milla, so it needs to contain the issues you want to visualize and needs to be updated accordingly.

### Adapt the jira plugin
You might need to adapt the colorPaletteStatus and arrowPaletteType in issueid-controller.jsp to the lingo used in your jira.

### Create .jar
This is a maven project, so use
```
atlas-mvn package
```
to create the .jar needed to add the plug-in to your jira.

### How to use
You can directly search for an issue [here](https://bugreports-test.qt.io/secure/LinkMapWebworkAction.jspa).

![Search1](https://github.com/OpenReqEU/qthulhu/blob/master/pics/Search1.png)

for example Issue: QTWB-30 and layer 2.

![Search2](https://github.com/OpenReqEU/qthulhu/blob/master/pics/Search2.png)

which will then give you the LinkMap for issue QTWB-30 with layer 2.

![LinkMapEx](https://github.com/OpenReqEU/qthulhu/blob/master/pics/ExampleLinkMap.png)

Alternatively, you can access the test instance of Qt's jira https://bugreports-test.qt.io/secure/Dashboard.jspa and search for an issue. On the view page you can scroll down Issue Links, underneath this you will find OpenReq Dependency Browser (old name, needs to be updated to OpenReq Link Map)

![Search3](https://github.com/OpenReqEU/qthulhu/blob/master/pics/Search3.png)

You then can use the node filter, to filter the link map for the status, type or priority. Per default, issues with status done are not shown.

![Filter](https://github.com/OpenReqEU/qthulhu/blob/master/pics/Filter.png)

To check a release plan for inconsistencies in the issues and links, you can use the consistency checker. It searches the link map up to depth 5 for inconsistencies. It also lists all releases and the found inconsistencies.

![ConsistencyCheck](https://github.com/OpenReqEU/qthulhu/blob/master/pics/ConsistencyCheck.png)

The link detection searches for an issue potentially linked issues which are not linked in Jira. These missing links are then recommended and can be accepted or rejected by the users. Currently these changes are not written to the Jira database.

![LinkDetection](https://github.com/OpenReqEU/qthulhu/blob/master/pics/LinkDetection.png)


#### What does this application do?
Visualising the link network, recommending missing links and checking the consistency of your release plans.

### What’s next?
- Editing links on the fly

### Challenges
Only the public issues are available. If a person has a Qt account, they should be able to see all the issues In the link map that they would be able to see in JIRA. Additionally, the ability to accept, reject and edit links need to be tied to the permisisons the user has in Jira.

### Technical structure
![techstructure](https://github.com/OpenReqEU/qthulhu/blob/master/pics/TechnicalStructure.png)
The service sends a request (JIRA keys, e.g. “QTWB-30”) for data to milla and receives a JSON with the issue data. This data is then visualized.

## Notes for Developers
I tried to comment most of the code to make it understandable.
The Java part just gets the data in a vis.js friendlier format. The javascript & HTML/CSS will get some more functionality in the next iterations.

## How to contribute
See [OpenReq project contribution guidelines](https://github.com/OpenReqEU/OpenReq/blob/master/CONTRIBUTING.md).

## License
Free use of this software is granted under the terms of the [EPL version 2 (EPL2.0)](https://www.eclipse.org/legal/epl-2.0/).
