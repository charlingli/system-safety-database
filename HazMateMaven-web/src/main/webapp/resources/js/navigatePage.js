document.onclick = clickHandler;
function clickHandler() {
    if (window.location.href.endsWith("timeout.xhtml")) {
        window.location.replace('../faces/login.xhtml');
    } else if (window.location.href.endsWith("privileges.xhtml")) {
        window.history.back();
    }
}