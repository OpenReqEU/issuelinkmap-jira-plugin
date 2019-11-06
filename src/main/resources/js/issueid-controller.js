//is called when the page is opened for the first time
//assigns all "onclick" functions
AJS.toInit(function ()
{
    $(document).ready(function ()
    {
        var url_string = window.location.href;
        var url = new URL(url_string);
        issue = url.searchParams.get("issue").toUpperCase();
        var depthParam = url.searchParams.get("depth");
        if ((depthParam === null) || (depthParam === ""))
        {
            depth = 1;
        } else
        {
            depth = parseInt(depthParam, 10)
        }
        document.getElementById('issue-headline').innerText = "Issue Links of " + issue;
        callTransitiveClosure();
        initNodesEdges();
        infoTab();
        calculatePositions();
        nodes.add(allNodesArray[0]);
        nodes.add(allNodesArray[1]);
        edges.add(depth0Edges);
        edges.add(depth1Edges);
        updateDepthButtons();
        if (depth >= 2)
        {
            add2layer()
        }
        if (depth >= 3)
        {
            add3layer();
        }
        if (depth >= 4)
        {
            add4layer();
        }
        if (depth === 5)
        {
            add5layer();
        }
        filterNodes();
        initNetwork();
        resizeCanvas();
        $(window).resize(function ()
        {
            resizeCanvas();
        });
        setTimeout(function ()
        {
            network.fit();
        }, 1000);
    });

    document.getElementById('depth-1-btn').onclick = function depth1()
    {
        var oldDepth = depth;
        depth = 1;
        if (oldDepth > depth)
        {
            nodes.remove(allNodesArray[2]);
            nodes.remove(allNodesArray[3]);
            nodes.remove(allNodesArray[4]);
            nodes.remove(allNodesArray[5]);
            edges.remove(depth3Edges);
            edges.remove(depth4Edges);
            edges.remove(depth5Edges);
            edges.remove(depth2Edges);
        }
        network.fit();
        updateDepthButtons();
    };

    document.getElementById('depth-2-btn').onclick = function depth2()
    {
        var oldDepth = depth;
        depth = 2;
        if (oldDepth > depth)
        {
            nodes.remove(allNodesArray[3]);
            nodes.remove(allNodesArray[4]);
            nodes.remove(allNodesArray[5]);
            edges.remove(depth3Edges);
            edges.remove(depth4Edges);
            edges.remove(depth5Edges);
        }
        if (oldDepth < depth)
        {
            add2layer();
        }
        network.fit();
        updateDepthButtons();
    };

    document.getElementById('depth-3-btn').onclick = function depth3()
    {
        var oldDepth = depth;
        depth = 3;
        if (oldDepth > depth)
        {
            nodes.remove(allNodesArray[4]);
            nodes.remove(allNodesArray[5]);
            edges.remove(depth4Edges);
            edges.remove(depth5Edges);
        }
        if (oldDepth === 1)
        {
            add2layer();
            add3layer();
        }
        if (oldDepth === 2)
        {
            add3layer();
        }
        network.fit();
        updateDepthButtons();
    };

    document.getElementById('depth-4-btn').onclick = function depth4()
    {
        var oldDepth = depth;
        depth = 4;
        if (oldDepth > depth)
        {
            nodes.remove(allNodesArray[5]);
            edges.remove(depth5Edges);
        }
        if (oldDepth === 1)
        {
            add2layer();
            add3layer();
            add4layer();
        }
        if (oldDepth === 2)
        {
            add3layer();
            add4layer();
        }
        if (oldDepth === 3)
        {
            add4layer();
        }
        network.fit();
        updateDepthButtons();
    };

    document.getElementById('depth-5-btn').onclick = function depth5()
    {
        var oldDepth = depth;
        depth = 5;
        if (oldDepth === 1)
        {
            add2layer();
            add3layer();
            add4layer();
            add5layer();
        }
        if (oldDepth === 2)
        {
            add3layer();
            add4layer();
            add5layer();
        }
        if (oldDepth === 3)
        {
            add4layer();
            add5layer();
        }
        if (oldDepth === 4)
        {
            add5layer();
        }
        network.fit();
        updateDepthButtons();
    };

    //Similarity detection functionality
    // Showing and removing proposed issues
    document.getElementById('sd-tab').onclick = function ()
    {
        proposedLinks();
    };

    document.getElementById('info-tab').onclick = function ()
    {
        infoTab();
    };

    document.getElementById('cc-tab').onclick = function checkConsistency()
    {
        if (proposedViewActive)
        {
            nodes.remove(proposedNodeElements);
            edges.remove(proposedEdgeElements);
            proposedViewActive = false;
        }
        infoTabActive = false;

        if (!consistencyChecked)
        {
            try
            {
                var xhr = new XMLHttpRequest();

                var url = "../rest/issuesearch/1.0/getConsistencyCheckForRequirement?requirementId=" + issue + "&analysisOnly=true";
                xhr.open("GET", url, true);

                document.getElementById('ccResult').innerHTML = '<h5><font color=\"#0052CC\">Pending...</font></h5>';
                document.getElementById('ccReleasesButton').innerHTML = "Searching for releases in link map...";
                xhr.onreadystatechange = function ()
                {
                    if (xhr.readyState === 4 && xhr.status === 200)
                    {
                        var jsonPart = xhr.responseText.substring(xhr.responseText.indexOf("{"));
                        var json = JSON.parse(jsonPart);

                        console.log(jsonPart);
                        console.log("Analysis only");
                        console.log(json);

                        var releases = json.response[0].Releases;
                        var regsInReleases = "";
                        console.log(releases);
                        for (var i = 0; i < releases.length; i++)
                        {
                            regsInReleases = regsInReleases + "<strong>Release " + releases[i].Release + "</strong><br>";
                            for (var k = 0; k < releases[i].RequirementsAssigned.length - 1; k++)
                            {
                                regsInReleases = regsInReleases + releases[i].RequirementsAssigned[k] + ", "
                            }

                            regsInReleases = regsInReleases + releases[i].RequirementsAssigned[releases[i].RequirementsAssigned.length - 1] + "<br>"
                        }
                        var ccMessage = "";

                        var ignoredRelList = "";
                        var relIgnored = json.response[0].RelationshipsIgnored;
                        // console.log(relIgnored)

                        if(relIgnored.length !== 0)
                        {
                            ignoredRelList = ignoredRelList + "<br>" +
                                "<table style='width: 100%'><tr>\n" +
                                "<th>Issue Keys</th>" +
                                "<th>Link type</th>" +
                                "</tr>";
                            for (var j = 0; j < relIgnored.length; j++)
                            {
                                ignoredRelList = ignoredRelList + "<tr><td>" + relIgnored[j].To + ", " + relIgnored[j].From + "</a></td><td>" + relIgnored[j].Type + "</td></tr>";
                            }
                            ignoredRelList = ignoredRelList + "</table>";
                        }
                        else
                        {
                            ignoredRelList = "<br>" + "There are no ignored links.";
                        }

                        if (json.response[0].Consistent)
                        {
                            ccMessage = ccMessage.concat("<h5><font color=\"#0052cc\">Release plan is consistent.</font></h5>");

                        } else
                        {
                            ccMessage = ccMessage.concat("<h5><font color=\"#CC0000\">Release plan is inconsistent.</font></h5>");
                            document.getElementById('ccInconsistencisBtn').style.display = "inline-block";
                        }
                        document.getElementById('ccResult').innerHTML = "<br>".concat(ccMessage).concat("<br>");
                        document.getElementById('ccReleases').innerHTML = "<br>".concat(regsInReleases).concat("<br>");
                        document.getElementById('ccReleasesButton').innerHTML = "Releases found";

                        document.getElementById("ccRelIgnored").style.display = "inline-block";
                        document.getElementById("ccRelIgnoredButton").style.display = "inline-block";
                        document.getElementById('ccRelIgnored').innerHTML = ignoredRelList;

                        consistencyChecked = true;
                    }
                };

                xhr.send(null);
            } catch (err)
            {
                alert(err);
                document.getElementById('ccResult').innerHTML = "there was an error...";
            }
        }
    };

    document.getElementById('filter-tab').onclick = function filterNodesTab()
    {
        // if (proposedViewActive)
        // {
        //     nodes.remove(proposedNodeElements);
        //     edges.remove(proposedEdgeElements);
        //     proposedViewActive = false;
        // }
        // infoTabActive = false;
        filterNodes();
    };

    document.getElementById('todos-toggle').onclick = function ()
    {
        //toggle(document.getElementById('todos-toggle'));
        toggle(this);
    };
    document.getElementById('done-toggle').onclick = function ()
    {
        //toggle(document.getElementById('done-toggle'));
        toggle(this);
    };
    document.getElementById('all-toggle').onclick = function ()
    {
        //toggleAll(document.getElementById('all-toggle'));
        toggleAll(this);
    };
    document.getElementById('type-toggle').onclick = function ()
    {
        //toggle(document.getElementById('type-toggle'));
        toggle(this);
    };
    document.getElementById('prio-toggle').onclick = function ()
    {
        //toggle(document.getElementById('prio-toggle'));
        toggle(this);
    };

    document.getElementById('filter-search').onclick = function ()
    {
        filterNodes();
    };

    //eventlistener for accordion tabs (ConsistencCechk)
    acc = document.getElementsByClassName("accordion");
    for (var i = 0; i < acc.length; i++)
    {
        acc[i].addEventListener("click", function ()
        {
            /* Toggle between adding and removing the "active" class,
            to highlight the button that controls the panel */
            this.classList.toggle("active");

            /* Toggle between hiding and showing the active panel */
            var panel = this.nextElementSibling;
            if (panel.style.display === "block")
            {
                panel.style.display = "none";
            } else
            {
                panel.style.display = "block";
            }
        });
    }
});

$('#search-id').submit(function ()
{
    $('#loader').show();
});

//getting the data for the network and depth btn disabling

var issue;
var depth;
var max_depth;
var nodeEdgeObject;
var currentIssue;

var helpNodeSet = [];
var filteredNodes = [];
var filterArray = [];
var distances = [240, 240, 240, 240, 240];
var deprDistance = 240;
var maxNodesPerLayer;
var priorityArray = ["P0: Blocker", "P1: Critical", "P2: Important", "P3: Somewhat important", "P4: Low", "P5: Not important", "", "Not Evaluated"];


//proposed View active boolean
var proposedViewActive = false;
//has the Consistency Checker been called
var consistencyChecked = false;
//infoTab View active boolean
var infoTabActive = true;
//saves the Issue that links get proposed for
var propLinksIssue;

var nodeElements = [];
var edgeElements = [];

var acc;

function callTransitiveClosure()
{
    try
    {
        var xhr = new XMLHttpRequest();
        var url = "../rest/issuesearch/1.0/getTransitiveClosureOfRequirement?requirementId=" + issue;
        xhr.open("GET", url, false);
        xhr.onreadystatechange = function ()
        {
            if (xhr.readyState === 4 && xhr.status === 200)
            {
                nodeEdgeObject = JSON.parse(xhr.responseText);
                max_depth = nodeEdgeObject.max_depth;
                if (max_depth < depth)
                {
                    depth = max_depth;
                }
                if (typeof (nodeEdgeObject['0']['nodes']['0']) === "undefined")
                {
                    window.location.replace('./ErrorPageAction.jspa?issue=' + issue)
                } else
                {
                    currentIssue = nodeEdgeObject['0']['nodes']['0']['id'];
                }
            }
        };
        xhr.send(null);
    } catch (err)
    {
        alert(err);
    }
}

//Help Functions
//builds URL for search form
function buildURL()
{
    var url = "IssueSearchWebworkAction.jspa?issue=" + document.getElementById("issueInput").value;
    var search_form = document.getElementById("search-id");
    search_form.action = url;
}

//function to help find a specific item depending on its identifier
function findElement(arr, propName, propValue)
{
    for (var i = 0; i < arr.length; i++)
    {
        if (arr[i][propName] === propValue)
            return arr[i];
    }
}

function findInAllNodes(id)
{
    var elem;
    for (var i = 0; i < allNodesArray.length; i++)
    {
        elem = findElement(allNodesArray[i], "id", id);
        if (typeof elem !== "undefined")
        {
            return elem;
        }
    }
}

function getIndexInAll(id)
{
    for (var i = 0; i <= 5; i++)
    {
        for (var j = 0; j < allNodesArray[i].length; j++)
        {
            if (allNodesArray[i][j].id === id)
            {
                return [i, j];
            }
        }
    }
}

function checkElement(arr, propName, propValue)
{
    for (var i = 0; i < arr.length; i++)
    {
        if (arr[i][propName] === propValue)
            return true;
    }
    return false;
}

function checkNodesContains(id)
{
    return (nodes.get(id) !== null);
}

//the type of a proposed link is proposed where as the type of an accepted link is smth like duplicates, similar, etc.
function findProposed(status, type)
{
    if (status === "proposed")
        return "proposed";
    else
    {
        return type;
    }
}

function getCheckedCheckboxes()
{
    //var checkboxes = document.querySelectorAll('input[name="' + checkboxName + 'Status"]:checked'), values = [];
    var checkboxes = document.querySelectorAll(':checked'), values = [];
    Array.prototype.forEach.call(checkboxes, function (el)
    {
        values.push(el.value);
    });
    return values;
}

function toggle(source)
{
    var checkboxes = document.getElementsByName(source.name);
    for (var i = 0; i < checkboxes.length; i++)
    {
        checkboxes[i].checked = source.checked;
    }
}


function toggleAll(source)
{
    var types = ["ToDoStatus", "ProgStatus", "StuckStatus", "DoneStatus", "Status"];
    for (var i = 0; i < types.length; i++)
    {
        toggle({name: types[i], checked: source.checked});
    }
}

function isFiltered(status, type, priority)
{
    return !(filterArray.includes(status) && filterArray.includes(type) && filterArray.includes(priority));
}

/**
 * arrange nodes in circles, with the currentIssue in the center
 */
function calculatePositions()
{
    if (typeof allNodesArray[0][0] !== "undefined")
    {
        distances[0] = 0;
        deprDistance = 240;
        if (allNodesArray[1].length > 12)
        {
            deprDistance *= Math.sqrt(allNodesArray[1].length / 12);
        }
        maxNodesPerLayer = 1;
        // the one element with depth 0 is in the center
        allNodesArray[0][0].x = 0;
        allNodesArray[0][0].y = 0;
        allNodesArray[0][0].angle = 0;
        allNodesArray[0][0].fixed = true;
        allNodesArray[0][0].heightConstraint = 60;
        allNodesArray[0][0].widthConstraint = 135;
        allNodesArray[0][0].font = {multi: true, size: 24};
        // allNodesArray[1] is layer one and surrounds the center
        for (var i = 0; i < allNodesArray[1].length; i++)
        {
            positionsDepthOne(allNodesArray[1].length, i);
        }
        for (var i = 2; i <= max_depth; i++)
        {
            positionsOuterRings(i);
        }
    }
}

function positionsDepthOne(maxElements, currentElement)
{
    var angle = 360 / maxElements;
    var direction;
    var resultingAngle;
    maxNodesPerLayer = Math.max(maxNodesPerLayer, allNodesArray[1].length);
    // increasing the radius by 16 roughly increases circumference by 100
    distances[1] = Math.max(allNodesArray[1].length * 16, 240);

    // if depth 1 has only one element it will be displayed below the center
    if (maxElements === 1)
    {
        direction = getDirectionByAngle(0);
        resultingAngle = 0;
    }
    // if there are two elements they will be displayed next to each other below the center
    else if (maxElements === 2)
    {
        direction = getDirectionByAngle(-45 + 90 * currentElement);
        resultingAngle = -45 + 90 * currentElement;
    }
    // if the amount of nodes is odd the first element is displayed above the center and the rest in a circle around the center
    else if (maxElements % 2)
    {
        direction = getDirectionByAngle(180 + (angle * currentElement));
        resultingAngle = 180 + angle * currentElement;
    }
    // even amount: first element on the top right, rest circle around center
    else
    {
        direction = getDirectionByAngle(45 + (angle * currentElement));
        resultingAngle = 45 + angle * currentElement;
    }
    // allNodesArray[1][currentElement].x = distances[1] * direction.x;
    // allNodesArray[1][currentElement].y = distances[1] * direction.y;

    allNodesArray[1][currentElement].x = deprDistance * direction.x;
    allNodesArray[1][currentElement].y = deprDistance * direction.y;
    allNodesArray[1][currentElement].angle = resultingAngle;
}

function positionsOuterRings(depth)
{
    var connectionsOut = [];
    var index;
    var direction;
    var angleDiff;

    // increasing the radius by 16 roughly increases circumference by 100
    distances[depth] = Math.max(allNodesArray[depth].length * 16, distances[depth - 1] + 240);

    maxNodesPerLayer = Math.max(maxNodesPerLayer, allNodesArray[depth].length);

    for (var i = 0; i < allNodesArray[depth - 1].length; i++)
    {
        connectionsOut = findConnectedNodesOuter(allNodesArray[depth - 1][i]);
        allNodesArray[depth - 1][i].connections = connectionsOut;
        for (var j = 0; j < connectionsOut.length; j++)
        {
            index = getIndexInAll(connectionsOut[j]);
            angleDiff = Math.min(15, 360 / allNodesArray[depth].length);
            angleDiff *= Math.ceil(j / 2);

            if (j % 2)
            {// j == odd
                angleDiff *= -1;
            }
            direction = getDirectionByAngle(allNodesArray[depth - 1][i].angle + angleDiff);

            // allNodesArray[index[0]][index[1]].x = distances[depth]  * direction.x;
            // allNodesArray[index[0]][index[1]].y = distances[depth] * direction.y;

            allNodesArray[index[0]][index[1]].x = deprDistance * depth * direction.x;
            allNodesArray[index[0]][index[1]].y = deprDistance * depth * direction.y;
            allNodesArray[index[0]][index[1]].angle = allNodesArray[depth - 1][i].angle + angleDiff;
        }
    }
}

//calculates positions for proposed issues if the selected issue is in layer 0
function calculateProposedDepthOnePositions(j, maxElements)
{
    var angle = 360 / maxElements;
    var direction;

    // if depth 1 has only one element it will be displayed below the center
    if (maxElements === 1)
    {
        direction = getDirectionByAngle(0);
    }
    // if there are two elements they will be displayed next to each other below the center
    else if (maxElements === 2)
    {
        direction = getDirectionByAngle(-45 + 90 * j);
    }
    // if the amount of nodes is odd the first element is displayed above the center and the rest in a circle around the center
    else if (maxElements % 2)
    {
        direction = getDirectionByAngle(180 + (angle * j));
    }
    // even amount: first element on the top right, rest circle around center
    else
    {
        direction = getDirectionByAngle(45 + (angle * j));
    }
    var coord_x = 0.6 * distances[1] * direction.x;
    var coord_y = 0.6 * distances[1] * direction.y;
    return {x: coord_x, y: coord_y};
}

//calculates positions for proposed issues if the selected issue is not layer 0
function calculateProposedOuterPositions(issueInfo, j)
{
    var index = getIndexInAll(issueInfo.nodeid);
    var angleDiff = Math.min(15, 360 / proposedNodesEdges['nodes'].length);
    angleDiff *= Math.ceil(j / 2);
    if (j % 2)
    {// j == odd
        angleDiff *= -1;
    }
    j++;
    var direction = getDirectionByAngle(allNodesArray[index[0]][index[1]].angle + angleDiff);

    var coord_x = (distances[issueInfo.depth] + 120) * direction.x;
    var coord_y = (distances[issueInfo.depth] + 120) * direction.y;
    return {x: coord_x, y: coord_y};
}

function getDirectionByAngle(angle)
{
    var direction = {};
    direction.x = Math.sin(angle * (Math.PI / 180));
    direction.y = Math.cos(angle * (Math.PI / 180));
    return direction;
}

function getAngleByRelativePosition(fromPoint, point)
{
    var dx = point.x - fromPoint.x;
    var dy = point.y - fromPoint.y;
    return Math.atan2(dx, dy) * 180 / Math.PI;
}

function findConnectedNodesOuter(paraElem)
{
    var connections = findConnectedNodes(paraElem.id);
    var result = [];
    var elem;
    for (var i = 0; i < connections.length; i++)
    {
        elem = findInAllNodes(connections[i]);
        if ((typeof elem !== "undefined") && (paraElem.level === elem.level - 1))
        {
            result.push(elem.id)
        }
    }
    return result;
}

function findConnectedNodes(id)
{
    var result = [];
    for (var i = 0; i < allEdges[0].length; i++)
    {
        if ((id === allEdges[0][i].from) && !result.includes(allEdges[0][i].to))
        {
            result.push(allEdges[0][i].to);
        }
        if ((id === allEdges[0][i].to) && !result.includes(allEdges[0][i].from))
        {
            result.push(allEdges[0][i].from);
        }
    }
    return result;
}

//Palettes

//color map for status according to bucketing in Kanban board
//Open blue, Blocked red, In Progress yellow, Done green
var colorPaletteStatus = {
    'Open': 'blue',
    'Reopened': 'blue',
    'Accepted': 'blue',
    'Reported': 'blue',
    'To-Do': 'blue',
    'To Do': 'blue',
    'Blocked': 'red',
    'On hold': 'red',
    'Need more info': 'red',
    'Waiting 3rd party': 'red',
    'In Progress': 'yellow',
    'Implemented': 'yellow',
    'Resolved': 'green',
    'Closed': 'green',
    'Withdrawn': 'green',
    'Rejected': 'green',
    'Done': 'green',
    'Verified': 'green',
    'not in database': 'unknown',
    'confidential': 'unknown',
    'not specified': 'unknown',
    'undefined': 'unknown'
};
//map to create the correct type of error, links like duplicates do not have a direction
var arrowPaletteType = {
    'contributes': 'to',
    'damages': 'to',
    'refines': 'to', //work breakdown, test
    'requires': 'to', //dependency
    'incompatible': '',
    'decomposition': 'to', //sub-task, epic
    'similar': '', //relates
    'duplicates': '', //duplicate
    'replaces': 'to' //replaces
};
//map to visually differentiate between accepted and proposed links
var edgeStatusPalette = {
    'accepted': false,
    'proposed': true
};

//disables the layer buttons if the depth would be smaller than 1 or bigger than 5
function updateDepthButtons()
{
    if (1 > max_depth)
    {
        $("#depth-1-btn").prop("disabled", true);
    } else
    {
        $("#depth-1-btn").removeAttr('disabled');
    }
    if (2 > max_depth)
    {
        $("#depth-2-btn").prop("disabled", true);
    } else
    {
        $("#depth-2-btn").removeAttr('disabled');
    }
    if (3 > max_depth)
    {
        $("#depth-3-btn").prop("disabled", true);
    } else
    {
        $("#depth-3-btn").removeAttr('disabled');
    }
    if (4 > max_depth)
    {
        $("#depth-4-btn").prop("disabled", true);
    } else
    {
        $("#depth-4-btn").removeAttr('disabled');
    }
    if (5 > max_depth)
    {
        $("#depth-5-btn").prop("disabled", true);
    } else
    {
        $("#depth-5-btn").removeAttr('disabled');
    }
    if (1 === depth)
    {
        $("#depth-1-btn").attr("class", "button layer button-effect active");
    } else
    {
        $("#depth-1-btn").attr('class', "button layer button-effect");
    }
    if (2 === depth)
    {
        $("#depth-2-btn").attr("class", "button layer button-effect active");
    } else
    {
        $("#depth-2-btn").attr('class', "button layer button-effect");
    }
    if (3 === depth)
    {
        $("#depth-3-btn").attr("class", "button layer button-effect active");
    } else
    {
        $("#depth-3-btn").attr('class', "button layer button-effect");
    }
    if (4 === depth)
    {
        $("#depth-4-btn").attr("class", "button layer button-effect active");
    } else
    {
        $("#depth-4-btn").attr('class', "button layer button-effect");
    }
    if (5 === depth)
    {
        $("#depth-5-btn").attr("class", "button layer button-effect active");
    } else
    {
        $("#depth-5-btn").attr('class', "button layer button-effect");
    }
}


function add5layer()
{
    nodes.add(allNodesArray[5]);
    edges.add(depth5Edges)
}

function add4layer()
{
    nodes.add(allNodesArray[4]);
    edges.add(depth4Edges);
}

function add3layer()
{
    nodes.add(allNodesArray[3]);
    edges.add(depth3Edges);
}

function add2layer()
{
    nodes.add(allNodesArray[2]);
    edges.add(depth2Edges);
}


function createDepthLevelNodes(nodeEdgeObject)
{
    var depthLevelNodes = [];
    $.each(nodeEdgeObject, function (i, v)
    {
        helpNodeSet.push(v);
        var nodedepth = v['depth'];
        var ID = v['nodeid'];
        var nodekey = v['id'];
        var nodetype = v['requirement_type'];
        var nodename = v['name'];
        var nodestatus = v['status'];
        var noderesolution = v['resolution'];
        var nodegroup = colorPaletteStatus[nodestatus] || "unkown";
        if (nodedepth === 0)
        {
            nodegroup = nodegroup.concat("_center");
        }
        var nodehidden = v['layer'] > depth;
        var nodelabel = "";
        var nodeprio = v['priority'].toString();
        if (v['priority'] === 6)
        {
            nodeprio = "5";
        }
        if (typeof nodetype === "undefined")
        {
            if (v['resolution'] === "confidential")
            {
                nodetype = "confidential";
                nodeprio = "confidential";
            } else if (v['resolution'] === "not in database")
            {
                nodetype = "not in database"
                nodeprio = "not in database"
            } else
            {
                nodetype = "not specified"
            }
        }
        if (!(nodetype == null))
        {
            if (nodetype === "confidential")
            {
                nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n").concat("confidential");
            } else if (v['resolution'] === "not in database")
            {
                nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n").concat("not in database");
            } else
            {
                nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n").concat(titleCase(nodetype.toString()));
                nodelabel = nodelabel.concat("\n").concat(nodestatus).concat(", ").concat(noderesolution);
            }
        } else
            nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n not specified");
        var nodetitle = "";
        // if (nodename.toString().length > 20)
        // {
        //     nodetitle = nodetitle.concat(nodename.toString().substring(0, 20)).concat("...\n");
        // } else
        // {
        //     nodetitle = nodetitle.concat(nodename.toString().substring(0, 20)).concat("\n");
        // }
        //blub
        var lenOfLine = 20;
        var titleWords = nodename.toString().split(" ");
        var lenCounter = 0;
        for (var j = 0; j < titleWords.length; j++)
        {
            if(lenCounter > lenOfLine)
            {
                nodetitle = nodetitle + "<br>";
                lenCounter = 0;
            }
            nodetitle = nodetitle + titleWords[j] + " ";
            lenCounter = lenCounter + titleWords[j].length;
        }

        depthLevelNodes.push({
            id: ID,
            label: nodelabel,
            group: nodegroup,
            shape: 'box',
            title: nodetitle,
            level: nodedepth,
            status: nodestatus,
            resolution: noderesolution,
            hidden: nodehidden,
            type: nodetype,
            priority: nodeprio,
        });
    });
    return depthLevelNodes;
}

function createDepthLevelEdges(nodeEdgeObject)
{
    var depthLevelEdges = [];
    $.each(nodeEdgeObject, function (i, v)
    {
        var edgestatus = v['status'];
        var fromID = v['node_fromid'];
        var toID = v['node_toid'];
        var edgelabel = "";
        if (typeof v['description'] === "undefined")
        {
            edgelabel = findProposed(v['status'], v['dependency_type']);
        } else
        {
            edgelabel = findProposed(v['status'], v['description']['0']);
        }
        var edgearrow = arrowPaletteType[edgelabel];
        var edgedashes = edgeStatusPalette[edgestatus];
        depth0Edges.push({
            from: fromID,
            to: toID,
            arrows: edgearrow,
            label: edgelabel,
            color: {color: '#53586b', inherit: false},
            width: 2,
            dashes: edgedashes
        });
    });
    return depthLevelEdges;
}

var allNodesArray;
var depth0Edges;
var depth1Edges;
var depth2Edges;
var depth3Edges;
var depth4Edges;
var depth5Edges;
var allEdges;
var nodes;
var edges;

function initNodesEdges()
{
    allNodesArray = [];
    allNodesArray[0] = createDepthLevelNodes(nodeEdgeObject['0']['nodes']);
    depth0Edges = createDepthLevelEdges(nodeEdgeObject['0']['edges']);
    allNodesArray[1] = createDepthLevelNodes(nodeEdgeObject['1']['nodes']);
    depth1Edges = createDepthLevelEdges(nodeEdgeObject['1']['edges']);
    allNodesArray[2] = createDepthLevelNodes(nodeEdgeObject['2']['nodes']);
    depth2Edges = createDepthLevelEdges(nodeEdgeObject['2']['edges']);
    allNodesArray[3] = createDepthLevelNodes(nodeEdgeObject['3']['nodes']);
    depth3Edges = createDepthLevelEdges(nodeEdgeObject['3']['edges']);
    allNodesArray[4] = createDepthLevelNodes(nodeEdgeObject['4']['nodes']);
    depth4Edges = createDepthLevelEdges(nodeEdgeObject['4']['edges']);
    allNodesArray[5] = createDepthLevelNodes(nodeEdgeObject['5']['nodes']);
    depth5Edges = createDepthLevelEdges(nodeEdgeObject['5']['edges']);
    //var allNodes = allNodesArray[0].concat(allNodesArray[1]).concat(allNodesArray[2]).concat(allNodesArray[3]).concat(allNodesArray[4]).concat(allNodesArray[5]);

    allEdges = [depth0Edges, depth1Edges, depth2Edges, depth3Edges, depth4Edges, depth5Edges];


    //create an array with nodes

    nodes = new vis.DataSet(nodeElements);
    // create an array with edges
    edges = new vis.DataSet(edgeElements);
}

var linkDetectionResponse;

var testFilter;

function selectFilterTest(filter)
{
    testFilter = filter;
}

var proposedIssueOrderLDR = [];

function proposedLinks()
{
    infoTabActive = false;
    if (propLinksIssue !== currentIssue || !proposedViewActive)
    {
        propLinksIssue = currentIssue;

        try
        {
            nodes.remove(proposedNodeElements);
            edges.remove(proposedEdgeElements);

            proposedNodeElements = [];
            proposedEdgeElements = [];
            proposedNodesEdges = [];
            proposedIssuesList = [];


            var xhr = new XMLHttpRequest();
            var url = "../rest/issuesearch/1.0/getTopProposedDependenciesOfRequirement?requirementId=" + currentIssue + "&maxResults=" + "5";

            xhr.open("GET", url, true);

            document.getElementById('ddResult').innerHTML = '<h5><font color=\"#0052CC\">Pending...</font></h5>';
            var issueInfo = findElement(nodeEdgeObject.nodes, "id", currentIssue);
            var level = issueInfo.depth + 1;

            xhr.onreadystatechange = function ()
            {
                if (xhr.readyState === 4 && xhr.status === 200)
                {
                    proposedNodesEdges = JSON.parse(xhr.responseText);
                    //add nodes
                    var j = 0;
                    $.each(proposedNodesEdges['nodes'], function (i, v)
                    {
                        var ID = v['nodeid'];
                        var nodekey = v['id'];
                        var nodetype = v['requirement_type'];
                        var nodename = v['name'];
                        var nodestatus = v['status'];
                        var noderesolution = v['resolution'];
                        var nodehidden = v['layer'] > depth;
                        var nodegroup = colorPaletteStatus[nodestatus] || "unknown";
                        var nodelabel = "";
                        var nodeprio = v['priority'].toString();
                        if (v['priority'] === 6)
                        {
                            nodeprio = "5";
                        }
                        if (typeof nodetype === "undefined")
                        {
                            if (v['resolution'] === "confidential")
                            {
                                nodetype = "confidential";
                                nodeprio = "confidential";
                            } else if (v['resolution'] === "not in database")
                            {
                                nodetype = "not in database";
                                nodeprio = "not in database";
                            } else
                            {
                                nodetype = "not specified"
                            }
                        }
                        if (!(nodetype == null))
                        {
                            if (nodetype === "confidential")
                            {
                                nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n").concat("confidential");
                            } else if (nodetype === "not in database")
                            {
                                nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n").concat("not in database");
                            } else
                            {
                                nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n").concat(titleCase(nodetype.toString()));
                                nodelabel = nodelabel.concat("\n").concat(nodestatus).concat(", ").concat(noderesolution);
                            }
                        } else
                            nodelabel = nodelabel + "<b>".concat(nodekey).concat("</b>").concat("\n not specified");
                        var nodetitle = "";
                        if (nodename.toString().length > 20)
                        {
                            nodetitle = nodetitle.concat(nodename.toString().substring(0, 20)).concat("...\n");
                        } else
                        {
                            nodetitle = nodetitle.concat(nodename.toString().substring(0, 20)).concat("\n")
                        }

                        //calculate positions for the proposed issue
                        var positions;
                        if (issue === propLinksIssue)
                        {
                            //  -1 because the currently selected issue is returned in the proposed issues
                            positions = calculateProposedDepthOnePositions(j, proposedNodesEdges['nodes'].length - 1);
                        } else
                        {
                            positions = calculateProposedOuterPositions(issueInfo, j);
                        }
                        j++;
                        if (!checkNodesContains(ID))
                        {
                            proposedNodeElements.push({
                                id: ID,
                                label: nodelabel,
                                group: nodegroup,
                                shape: 'ellipse',
                                title: nodetitle,
                                level: level,
                                hidden: nodehidden,
                                x: positions.x,
                                y: positions.y
                            });
                            // proposedIssuesList.push({
                            //     id: nodekey
                            // })
                        }
                    });

                    var proposedSortingArray = [];
                    //add edges
                    $.each(proposedNodesEdges['edges'], function (i, v)
                    {
                        var edgestatus = v['status'];
                        var fromID = v['node_fromid'];
                        var toID = v['node_toid'];
                        var fromName = v['fromid'];
                        var toName = v['toid'];
                        var edgelabel = findProposed(v['status'], v['dependency_type']);
                        var edgearrow = arrowPaletteType[edgelabel];
                        var dependency_score = v['dependency_score'];

                        if (!(checkNodesContains(fromID) && checkNodesContains(toID)))
                        {
                            proposedEdgeElements.push({
                                from: fromID,
                                to: toID,
                                arrows: edgearrow,
                                label: "proposed",
                                color: {color: '#53586b', inherit: false},
                                width: 2,
                                dashes: true
                            });
                        }
                        proposedSortingArray.push({
                            fromID: fromID,
                            fromName: fromName,
                            toID: toID,
                            toName: toName,
                            score: dependency_score
                        })

                    });

                    sortProposed(proposedSortingArray);
                    numberOfProposedLinks = proposedEdgeElements.length;
                    linkDetectionResponse = Array(numberOfProposedLinks);

                    nodes.add(proposedNodeElements);
                    edges.add(proposedEdgeElements);

                    proposedViewActive = true;
                    if (proposedIssuesList.length === 0)
                    {
                        document.getElementById('ddResult').innerHTML = '<h5><font color="#0052CC">No proposed links for issue ' + currentIssue + '.</font></h5>';
                    } else
                    {
                        var stringList = '<h5><font color=\"#0052CC\">Proposed Links of ' + currentIssue + "</font></h5>" +
                            "<table style='width: 100%'><tr>\n" +
                            "<th>Issue Key</th>" +
                            "<th>Link type</th>" +
                            "<th>Accept</th>" +
                            "<th>Reject</th>" +
                            "</tr>";
                        var selectionList = '<div class="custom-select">';
                        var acceptBtn = "<button class='button accept button-effect-accept' role='radio' onclick=\"registerClick(this)\" id=";
                        var rejectBtn = "<button class='button reject button-effect-reject' role='radio' onclick=\"registerClick(this)\" id=";
                        for (var i = 0; i < proposedIssuesList.length; i++)
                        {
                            stringList = stringList + "<tr><td><a href='https://bugreports-test.qt.io/browse/" + proposedIssuesList[i].id + "' target='_blank'>" + proposedIssuesList[i].id + "</a></td><td>" + selectionList + "<select id=" + i + "s>" +
                                "<option value='REQUIRES'>dependency</option>" +
                                "<option value='DUPLICATES'>duplicate</option>" +
                                "<option value='DECOMPOSITION'>epic</option>" +
                                "<option value='CONTRIBUTES'>relates</option>" +
                                "<option value='REPLACES'>replacement</option>" +
                                "<option value='DECOMPOSITION'>subtask</option>" +
                                "<option value='REFINES'>work breakdown</option>" +
                                "</select></div></td><td>"
                                + acceptBtn + i + "a" + proposedIssuesList[i].id + ">&#x2713</button></td><td>"
                                + rejectBtn + i + "r" + proposedIssuesList[i].id + ">&#x2717</button></td></tr>";
                        }
                        stringList = stringList + "<td><button class='button button-effect' onclick ='sendLinkData()'>Save</button></td><td></td><td></td><td></td></table>";
                        document.getElementById('ddResult').innerHTML = stringList;
                    }
                    console.log(proposedIssuesList);
                }

            };
            xhr.send(null);

        } catch (err)
        {
            document.getElementById('ddResult').innerHTML = "We are sorry, there was an error getting the proposed dependencies...";
            alert(err);
        }
    }
}

function sortProposed(array)
{
    for (var i = 0; i < array.length; i++)
    {
        var maxScore = 0;
        var maxIndex = 0;
        var nameToAdd;
        for (var k = 0; k < array.length; k++)
        {
            if (array[k].score > maxScore)
            {
                maxScore = array[k].score;
                maxIndex = k;
                array[k].score = -1;
            }
        }
        if (checkNodesContains(array[maxIndex].fromID) && !checkNodesContains(array[maxIndex].toID))
        {
            nameToAdd = array[maxIndex].toName;
        } else if (!checkNodesContains(array[maxIndex].fromID) && checkNodesContains(array[maxIndex].toID))
        {
            nameToAdd = array[maxIndex].fromName;
        }
        proposedIssuesList.push({
            id: nameToAdd
        });
    }
}


function registerClick(elem)
{
    if (elem.id.charAt(1) === 'r')
    {
        var btnid = "#" + elem.id;
        if ($(btnid).hasClass('reject'))
        {
            var otherbtnid = "#" + elem.id.charAt(0) + "a" + elem.id.substring(2);
            if ($(otherbtnid).hasClass('accepted'))
            {
                $(otherbtnid).removeClass('accepted');
                $(otherbtnid).addClass('accept');
            }
            $(btnid).removeClass('reject');
            $(btnid).addClass('rejected');
            linkDetectionResponse[elem.id.charAt(0)] = "reject";
            proposedIssueOrderLDR[elem.id.charAt(0)] = elem.id.substring(2);
        } else
        {
            $(btnid).removeClass('rejected');
            $(btnid).addClass('reject');
            delete linkDetectionResponse[elem.id.charAt(0)];
            delete proposedIssueOrderLDR[elem.id.charAt(0)];
        }
    } else
    {
        var selectid = elem.id.charAt(0) + "s";
        var selectedItem = document.getElementById(selectid).value;
        var btnid = "#" + elem.id;
        if ($(btnid).hasClass('accept'))
        {
            var otherbtnid = "#" + elem.id.charAt(0) + "r" + elem.id.substring(2);
            if ($(otherbtnid).hasClass('rejected'))
            {
                $(otherbtnid).removeClass('rejected');
                $(otherbtnid).addClass('reject');
            }
            $(btnid).removeClass('accept');
            $(btnid).addClass('accepted');
            linkDetectionResponse[elem.id.charAt(0)] = selectedItem;
            proposedIssueOrderLDR[elem.id.charAt(0)] = elem.id.substring(2);
        } else
        {
            $(btnid).removeClass('accepted');
            $(btnid).addClass('accept');
            delete linkDetectionResponse[elem.id.charAt(0)];
            delete proposedIssueOrderLDR[elem.id.charAt(0)];
        }
    }
}

function sendLinkData()
{
    var updatedProposedLinksJSON =
        {
            dependencies: []
        };
    var currentProjectId = currentIssue.substring(0, currentIssue.indexOf("-"));
    var projectsToUpdate = [];

    $.each(proposedNodesEdges['edges'], function (i, v)
    {
        var dep_type = v['dependency_type'].toUpperCase(); //when the type is not overwritten the standard is "similar". The API doesn't accept lowercase input
        var fromid = v['fromid'];
        var toid = v['toid'];
        var id = v['id'];
        var created_at = v['created_at'];
        var dep_score = v['dependency_score'];
        var description = v['description'];


        updatedProposedLinksJSON.dependencies.push({
            created_at: created_at,
            dependency_score: dep_score,
            dependency_type: dep_type,
            description: description,
            fromid: fromid,
            id: id,
            status: "PROPOSED",
            toid: toid,
        });

        // $('#issueInput').val(issueKey);
        // $('#depthInput').val(depth);
        // //
        // document.forms["search-id"].submit();
    });

    for (var i = linkDetectionResponse.length - 1; i >= 0; i--)
    {
        //saves the projectIDs to update those later
        var fromID = updatedProposedLinksJSON.dependencies[i].fromid;
        var toID = updatedProposedLinksJSON.dependencies[i].toid;
        var fromProject = fromID.substring(0, fromID.indexOf("-"));
        var toProject = toID.substring(0, toID.indexOf("-"));


        var index = Math.max(proposedIssueOrderLDR.indexOf(fromID), proposedIssueOrderLDR.indexOf(toID));
        if (index !== -1)
        {
            if (linkDetectionResponse[index] !== undefined)
            {
                if (linkDetectionResponse[index] !== "reject")
                {
                    updatedProposedLinksJSON.dependencies[i].dependency_type = linkDetectionResponse[index].toUpperCase();
                    updatedProposedLinksJSON.dependencies[i].status = "ACCEPTED";
                    updatedProposedLinksJSON.dependencies[i].description[0] = linkDetectionResponse[index];

                    //this will safe all projects that need to be updated
                    if (fromProject === currentProjectId)
                    {
                        if (projectsToUpdate.indexOf(toProject) === -1)
                        {
                            projectsToUpdate.push(toProject);
                        }
                    } else if (toProject === currentProjectId)
                    {
                        if (projectsToUpdate.indexOf(fromProject) === -1)
                        {
                            projectsToUpdate.push(fromProject);
                        }
                    }
                } else
                {
                    updatedProposedLinksJSON.dependencies[i].status = "REJECTED";

                    //this will safe all projects that need to be updated
                    if (fromProject === currentProjectId)
                    {
                        if (projectsToUpdate.indexOf(toProject) === -1)
                        {
                            projectsToUpdate.push(toProject);
                        }
                    } else if (toProject === currentProjectId)
                    {
                        if (projectsToUpdate.indexOf(fromProject) === -1)
                        {
                            projectsToUpdate.push(fromProject);
                        }
                    }
                }
            } else
            {
                updatedProposedLinksJSON.dependencies.splice(i, 1);
            }
        }
    }
    var updatedProposedLinksResponse = JSON.stringify(updatedProposedLinksJSON);

    try
    {

        var xhr = new XMLHttpRequest();
        var url = "../rest/issuesearch/1.0/updateProposedDependencies";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function ()
        {
            if (xhr.readyState === 4 && xhr.status === 200)
            {
                var response = xhr.responseText;
                // if (projectsToUpdate.includes("QTBUG"))
                // {
                //     document.getElementById("ddPending").innerHTML += "<br><br>QTBUG is the largest project and will take a while to update. This can take up to 2 minutes. Thank you for your patience.";
                // }
                // for (var count = 0; count < projectsToUpdate.length; count++)
                // {
                //     sendProjectToMulperi(projectsToUpdate[count]);
                // }
            }
        };
        // takes only the array out of the JSON
        //{ dependencies : [...] }  => [...]
        updatedProposedLinksResponse = updatedProposedLinksResponse.substring(updatedProposedLinksResponse.indexOf(":") + 1, updatedProposedLinksResponse.length - 1);
        xhr.send(updatedProposedLinksResponse);
        document.getElementById("ddPending").innerHTML = "Your request is being processed.<br>"
    } catch
        (err)
    {
        alert(err);
    }
}

var proposedNodeElements = [];
var proposedEdgeElements = [];
var proposedNodesEdges = [];
var proposedIssuesList = [];
var numberOfProposedLinks = 0;

//milla should update by itself
// function sendProjectToMulperi(projectID)
// {
//     try {
//         var xhr = new XMLHttpRequest();
//         var url = "../rest/issuesearch/1.0/updateProposedDependencies";
//         xhr.open("POST", url, true);
//         xhr.setRequestHeader("Content-Type", "application/json");
//         xhr.onreadystatechange = function ()
// {
//             if (xhr.readyState === 4 && xhr.status === 200)
//             {
//                 console.log(projectID + " done");
//                 document.getElementById("ddPending").innerHTML = "The page will now reload.<br>If you have time to read this please reload the page manually.";
//                 location.reload();
//             }
//         };
//         xhr.send("");
//     }
//     catch
//         (err)
// {
//         alert(err);
//     }
// }

function filterNodes()
{
    filterArray = getCheckedCheckboxes();

    $.each(filteredNodes, function (i, v)
    {
        allNodesArray[v.level].push(v);
    });
    filteredNodes = [];
    for (var j = 0; j < 6; j++)
    {
        for (var i = 0; i < allNodesArray[j].length; i++)
        {
            // if the current node has a status that should not be shown it will be
            // spliced out of allNodesArray and pushed into filteredNodes
            if (isFiltered(allNodesArray[j][i].status, allNodesArray[j][i].type, allNodesArray[j][i].priority) && allNodesArray[j][i].level !== 0)
            {
                filteredNodes.push(allNodesArray[j].splice(i, 1)[0]);
                i--;
            }
        }
    }
    // after splicing all filtered nodes out of allNodesArray calculate position will create a circle out of the remaining nodes
    calculatePositions();
    // nodes is cleared
    nodes.clear();
    // and refilled with the correct nodes
    for (var i = 0; i <= depth; i++)
    {
        nodes.add(allNodesArray[i]);
    }
}

function infoTab()
{

    infoTabActive = true;

    if (proposedViewActive)
    {
        nodes.remove(proposedNodeElements);
        edges.remove(proposedEdgeElements);
        proposedViewActive = false;
    }
    //display the initial infobox only if the user put exactly one issue in the input
    //get coressponding JSON
    var issueInfo = findElement(helpNodeSet, "id", currentIssue);
    //get information that should be displayed
    var infoLink = "https://bugreports.qt.io/browse/" + currentIssue;
    var infoLinkTestJIRA = "https://bugreports-test.qt.io/browse/" + currentIssue;
    var infoTitle = issueInfo.name;
    var infoType = issueInfo.requirement_type;
    if (infoType === "issue")
    {
        infoType = "suggestion"
    }
    var infoStatus = issueInfo.status;
    //var infoDescription = issueInfo.issueDescription;
    var infoResolution = issueInfo.resolution;
    var infoEnvironment = issueInfo.environment;
    var infoComponent = issueInfo.components;
    var infoLabel = issueInfo.labels;
    var infoVersion = issueInfo.versions;
    var infoPlatform = issueInfo.platforms;
    var infoFixVersion = issueInfo.fixversion;
    var infoPriority = priorityArray[issueInfo.priority];
    if (typeof infoType === "undefined")
    {
        {
            infoType = "not specified"
        }
    }
    document.getElementById('infoBoxHeading').innerHTML = "".concat(currentIssue);
    if (infoResolution === "confidential")
    {
        document.getElementById("infoOther").innerHTML = "This issue is confidential, you might not be logged into Jira or have the permissions to see it.";
        document.getElementById('infoTable').style.display = "none";
    } else if (infoResolution === "not in database")
    {
        document.getElementById("infoOther").innerHTML = "This issue is not in the database, you might need to wait until the database is updated.";
        document.getElementById('infoTable').style.display = "none";

    } else
    {
        //put the issues in the corressponding part of the website
        //document.getElementById('infoBoxIssueLink').innerHTML = '<a href=\"' + infoLink + '\" class=\"button jira button-effect center\" target="_blank">View Issue in Qt JIRA</a>';
        document.getElementById('infoBoxIssueLink').innerHTML = '<a href=\"' + infoLinkTestJIRA + '\" class=\"button jira button-effect center\" target="_blank">View Issue in Qt Test JIRA</a>';
        document.getElementById('infoBoxIssueStatus').innerHTML = infoStatus;
        document.getElementById('infoBoxIssueType').innerHTML = '<img src="../download/resources/openreq.qt.issuelinkmap-jira-plugin.issuelinkmap-jira-plugin:issuelinkmap-jira-plugin-resources/images/type/' + infoType + '.png" width="20" height="20" align="middle"/>'.concat(" ").concat(titleCase(infoType));
        document.getElementById('infoBoxIssuePrio').innerHTML = '<img src="../download/resources/openreq.qt.issuelinkmap-jira-plugin.issuelinkmap-jira-plugin:issuelinkmap-jira-plugin-resources/images/prio/' + issueInfo.priority + '.png" width="20" height="20" align="middle"/>'.concat(" ").concat(infoPriority);
        document.getElementById('infoBoxIssueSummary').innerHTML = infoTitle;
        document.getElementById('infoBoxIssueResolution').innerHTML = infoResolution;
        document.getElementById('infoBoxIssueEnv').innerHTML = infoEnvironment;
        document.getElementById('infoBoxIssueComponent').innerHTML = infoComponent;
        document.getElementById('infoBoxIssueLabel').innerHTML = infoLabel;
        document.getElementById('infoBoxIssueVersion').innerHTML = infoVersion;
        document.getElementById('infoBoxIssueFix').innerHTML = infoFixVersion;
        document.getElementById('infoBoxIssuePlatform').innerHTML = infoPlatform;
        document.getElementById("infoOther").style.display = "none";
    }
}


// Create the network after the page is loaded and the network containing div is rendered


var network;

function initNetwork()
{
    // create a network
    var networkContainer = document.getElementById('issueLinkMap');
    // provide the data in the vis format
    var data = {
        nodes: nodes,
        edges: edges
    };

    //specify options such as physics
    var options = {
        //size of the network
        // autoResize: true,
        // height: '1000px',
        // width: '80%',
        //specify the different groups
        //TODO: There must be an easier way to create these groups
        "groups": {
            "yellow": {
                color: {background: '#ffd351', border: 'none'},
                borderWidth: 0,
                font: {color: '#594200', multi: 'html', size: 14}
            },
            "green": {
                color: {background: '#14882c', border: 'none'},
                borderWidth: 0,
                font: {color: 'white', multi: 'html', size: 14}
            },
            "blue": {
                color: {background: '#4a6685', border: 'none'},
                borderWidth: 0,
                font: {color: 'white', multi: 'html', size: 14}
            },
            "red": {
                color: {background: '#ce0000', border: 'none'},
                borderWidth: 0,
                font: {color: 'white', multi: 'html', size: 14}
            },
            "unknown": {
                color: {background: '#cecfd5', border: '#09102b'},
                borderWidth: 0,
                font: {color: 'black', multi: 'html', size: 14}
            },
            "yellow_center": {
                color: {background: '#ffd351', border: 'none'},
                borderWidth: 0,
                font: {color: '#594200', multi: 'html', size: 20}
            },
            "green_center": {
                color: {background: '#14882c', border: 'none'},
                borderWidth: 0,
                font: {color: 'white', multi: 'html', size: 20}
            },
            "blue_center": {
                color: {background: '#4a6685', border: 'none'},
                borderWidth: 0,
                font: {color: 'white', multi: 'html', size: 20}
            },
            "red_center": {
                color: {background: '#ce0000', border: 'none'},
                borderWidth: 0,
                font: {color: 'white', multi: 'html', size: 20}
            },
            "unknown_center": {
                color: {background: '#cecfd5', border: '#09102b'},
                borderWidth: 0,
                font: {color: 'black', multi: 'html', size: 20}
            }
        },
        //node design
        "nodes": {
            "font": {
                "face": 'Arial'
            },
            "margin": {
                "top": 10,
                "right": 10,
                "bottom": 10,
                "left": 10
            },
            "title": "HTML"
        },
        //edge design
        "edges": {
            "font": {
                "face": 'Lato',
                "align": 'bottom'
            },
            "smooth": {
                "type": "curvedCW",
                "forceDirection": "none",
                "roundness": 0   // This is max roundness
            }
        },
        "interaction": {
            "multiselect": false,
            "navigationButtons": true
        },
        "physics": {
            "enabled": false,
            'forceAtlas2Based': {
                'gravitationalConstant': 26,
                'centralGravity': 0.005,
                'springLength': 230,
                'springConstant': 0.18,
                'avoidOverlap': 10
            },
            'maxVelocity': 146,
            'solver': 'forceAtlas2Based',
            'timestep': 0.35,
            'stabilization': {
                'enabled': true,
                "iterations": 1000,
                "updateInterval": 25
                // "minVelocity": 50,
                // // "repulsion": {
                // //     "nodeDistance": 150
                // // },
                // "barnesHut":
                //     {
                //         "avoidOverlap": 1
                //     },
                // "stabilization": {
                //     "enabled": true,
                //     "iterations": 50, // maximum number of iteration to stabilize
                //     "updateInterval": 1,
                //     "onlyDynamicEdges": false,
                //     "fit": true
            }
        }
    };
    //initialize network
    network = new vis.Network(networkContainer, data, options);

    network.on("stabilizationIterationsDone", function ()
    {
        network.setOptions({physics: false});
    });

    //     network.fit();
    // });

    //interact with network
    //if a node is selected display information in infobox
    network.on("selectNode", function (params)
    {
        params.event = "[original event]";

        var node = nodes.get(params.nodes);
        var issueID = node[0].id;
        var issueNode = findElement(nodeEdgeObject.nodes, "nodeid", issueID);
        if (typeof issueNode !== 'undefined')
        {
            currentIssue = issueNode.id;
            if (infoTabActive)
            {
                infoTab();
            }
            if (proposedViewActive)
            {
                //proposedLinks() will only be called if the selected node is not a proposed one
                var isAlreadyProposed = false;
                $.each(proposedNodesEdges['nodes'], function (i, v)
                {
                    //includes returns true if the values are the same and if currentIssue is "[v.id]-mock"
                    if (currentIssue.includes(v.id))
                    {
                        isAlreadyProposed = true;
                    }
                });
                if (!isAlreadyProposed)
                {
                    proposedLinks();
                }
            }
        }
    });

    //doubleclicking searches for the clicked issue
    network.on("doubleClick", function (params)
    {
        params.event = "[original event]";
        var node = nodes.get(params.nodes);
        if (typeof node[0] !== "undefined")
        {
            var issueID = node[0].id;
            var issueNode = findElement(nodeEdgeObject.nodes, "nodeid", issueID);
            if (typeof issueNode !== 'undefined' && issueNode.id !== issue)
            {
                var issueKey = issueNode.id;
                $('#issueInput').val(issueKey);
                $('#depthInput').val(depth);
                //
                buildURL();
                document.forms["search-id"].submit();
            }
        }
    });
}

function resizeCanvas()
{
    $('#issueLinkMap').height($(document).height() * 0.70)
}

function getInconsistencies()
{
    try
    {
        var xhr = new XMLHttpRequest();

        var url = "../rest/issuesearch/1.0/getConsistencyCheckForRequirement?requirementId=" + issue + "&analysisOnly=false";
        xhr.open("GET", url, true);
        xhr.onreadystatechange = function ()
        {
            if (xhr.readyState === 4 && xhr.status === 200)
            {
                var jsonPart = xhr.responseText.substring(xhr.responseText.indexOf("{"));
                console.log(jsonPart);
                var json = JSON.parse(jsonPart);

                console.log("DIAGNOSIS");
                console.log(json);

                var relList = "";

                var relInc = json.response[0].RelationshipsInconsistent;
                relList = relList + "<br>" +
                    "<table style='width: 100%'><tr>\n" +
                    "<th>Issue Keys</th>" +
                    "<th>Link type</th>" +
                    "</tr>";
                for (var i = 0; i < relInc.length; i++)
                {
                    relList = relList + "<tr><td>" + relInc[i].To + ", " + relInc[i].From + "</a></td><td>" + relInc[i].Type + "</td></tr>";
                }
                relList = relList + "</table>";
                document.getElementById("ccRelInc").style.display = "inline-block";
                document.getElementById("ccRelIncButton").style.display = "inline-block";
                document.getElementById('ccRelInc').innerHTML = relList;
                document.getElementById('ccRelIncButton').innerHTML = "Inconsistent items";
                document.getElementById('ccInconsistencisBtn').style.display = "none";
            }
        };

        xhr.send(null);
    } catch (err)
    {
        alert(err);
        document.getElementById('ccResult').innerHTML = "there was an error...";
    }
}

function titleCase(str)
{
    if (typeof str === "undefined")
    {
        return "not specified";
    }
    if (str === "confidential")
    {
        return "confidential";
    }
    if (str === "not in database")
    {
        return "not in database";
    }
    var splitStr = str.toLowerCase().split(' ');
    for (var i = 0; i < splitStr.length; i++)
    {
        // You do not need to check if i is larger than splitStr length, as your for does that for you
        // Assign it back to the array
        splitStr[i] = splitStr[i].charAt(0).toUpperCase() + splitStr[i].substring(1);
    }
    // Directly return the joined string
    return splitStr.join(' ');
}