package com.benverble.views

import kotlinx.html.*

fun HTML.mainLayout(title: String, content: FlowContent.() -> Unit) {
    head {
        title {
            +title
        }
        link(rel = "stylesheet", href = "/static/css/simple.css", type = "text/css")
        link(rel = "stylesheet", href = "/styles.css", type = "text/css")
        link(rel = "icon", href = "/static/img/favicon.ico")
        link(rel = "apple-touch-icon", href="/static/img/apple-touch-icon.png")
    }
    body {
        content()

        footer {
            script(src = "/static/js/script.js") {}
            script(src = "/static/js/htmx.min.js") {}
        }
    }
}

fun FlowContent.testHtmxPage(articleTitle: String, headerContent: FlowContent.(title: String) -> Unit) {
    article {
        header("my-h1") {
            // a bit inside out, but still interesting
            // passing the data from the consumer of this template function
            // just callbacks really
            headerContent(articleTitle)
        }
        // todo: native css transitions for dialog
        dialog {
            id = "test-dialog"
            form {
                attributes["method"] = "dialog"
                button {
                    +"close"
                }
            }
            p {
                +"Modal has a backdrop!"
            }
        }
        button {
            attributes["hx-target"] = "#test-dialog"
            attributes["hx-swap"] = "outerHTML"
            attributes["hx-get"] = "/test-dialog"
            +"show the dialog"
        }
    }
    div {
        a {
            attributes["href"] = "/messages"
            attributes["hx-get"] = "/messages"
            attributes["hx-swap"] = "outerHTML"
            attributes["hx-target"] = "closest div"
            +"get messages"
        }
    }
}