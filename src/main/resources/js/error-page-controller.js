AJS.toInit(function() {
    $(document).ready(function () {
        var url_string = window.location.href;
        var url = new URL(url_string);
        issue = url.searchParams.get("issue");
        error = url.searchParams.get("error");
        if (issue !== null && typeof issue !== "undefined")
        {
            document.getElementById("error_placeholder").innerText = "Searched issue key " + issue.toUpperCase() +
                " does not exist or access to it is limited, please check that you wrote the issue key correctly. \n" +
            "If you think this is a bug please use the 'Report a bug' feature above."
        } else
        {
            document.getElementById("error_placeholder").innerText = "There was an error initialising the page: " + error +
                "\nPlease use the 'Report a bug' feature above."
        }
    });
});