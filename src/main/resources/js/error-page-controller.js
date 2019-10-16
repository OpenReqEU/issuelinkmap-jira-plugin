AJS.toInit(function() {
    $(document).ready(function () {
        var url_string = window.location.href;
        var url = new URL(url_string);
        issue = url.searchParams.get("issue").toUpperCase();
        document.getElementById("issue_placeholder").innerText = "(" + issue + ")";
    });
});